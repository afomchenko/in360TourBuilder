/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 27.09.17 14:12 Anton Fomchenko 360@in360.ru
 */

package ru.in360.pano;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.GpsDirectory;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.resizers.configurations.Antialiasing;
import ru.in360.TourProject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public final class BitmapEdit {

    private static Lock lock = new ReentrantLock();
    private static BitmapEdit instance;
    private static int complete = 0;
    private BufferedImage image;
    private File tempFolder;

    private BitmapEdit() {
        tempFolder = TourProject.getInstance().getTempFolder();
//        tempFolder = new File("temp/");
//        tempFolder.mkdirs();
    }

    public static int getComplete() {
        return complete;
    }

    public static BitmapEdit getInstance() {
        try {
            lock.lock();
            if (instance == null) {
                instance = new BitmapEdit();
            }
            return instance;
        } finally {
            lock.unlock();
        }
    }

    public PanoRaw sphToCube(File equiImage, boolean oversample) throws IOException, IllegalArgumentException {

        ArrayList<Side> sides = new ArrayList<>();
        ExecutorService es = Executors.newFixedThreadPool(6);
        List<File> panoramaFilesList = new ArrayList<>();


        complete = 0;

        sides.add(new Side("left", Math.PI * 1.5, 0));
        sides.add(new Side("front", 0, 0));
        sides.add(new Side("right", Math.PI / 2, 0));
        sides.add(new Side("back", Math.PI, 0));
        sides.add(new Side("up", -Math.PI / 2, Math.PI));
        sides.add(new Side("down", Math.PI / 2, -Math.PI));


//        ImageConverter.toBitmap(inputImageFile, inputImageJpeg);

//        FileInputStream in = new FileInputStream(inputImageFile);
//        FileChannel channel = in.getChannel();
//        ByteBuffer buffer = ByteBuffer.allocate((int) channel.size());
//        channel.read(buffer);

//        BufferedImage image2 = (BufferedImage)ImageConverter.loadImage(buffer.array());
//
//        buffer.clear();
//        buffer=null;
        image = ImageIO.read(equiImage);
        if (image.getWidth() != (image.getHeight() * 2)) {
            int w = image.getWidth();
            int h = image.getHeight();
            image.flush();
            image = null;
            throw new IllegalArgumentException("image w*h must be 2*1: " + w + " * " + h);
        }

        complete += 10;

        BufferedImage previewImage = Thumbnails.of(image).size(600, 300).asBufferedImage(); //Scalr.resize(image, Scalr.Method.SPEED, 600,300);

        File prev = new File(tempFolder.getPath() + "/editorpreview.jpg");
        ImageConverter.writeJPG(previewImage, prev);

        complete += 5;

        BufferedImage thumbImage = Thumbnails.of(previewImage).sourceRegion(previewImage.getWidth() / 2, 0, previewImage.getWidth() - previewImage.getWidth() / 2, previewImage.getHeight()).size(240, 240).asBufferedImage();
        //Scalr.resize(previewImage.getSubimage(previewImage.getWidth()/2, 0, previewImage.getWidth()-previewImage.getWidth()/2, previewImage.getHeight()), Scalr.Method.BALANCED, 240);
        File thumb = new File(tempFolder.getPath() + "/thumb.jpg");
        //ImageIO.write(thumbImage,"bmp", thumb);
        ImageConverter.writeJPG(thumbImage, thumb);

        complete += 5;


        if (oversample)
            try {
                image = Thumbnails.of(image).size(36864, 18432).asBufferedImage();
            } catch (OutOfMemoryError e) {
                System.err.println("Low memory, used standart quality");
            }

        complete += 15;

        GeoLocation location = null;
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(equiImage);
            GpsDirectory directory = metadata.getFirstDirectoryOfType(GpsDirectory.class);
            if (directory != null) {
                location = directory.getGeoLocation();
                System.out.println(location.toString());
            }
        } catch (ImageProcessingException e) {
            e.printStackTrace();
        }

        complete += 5;


        double cubeSize = (double) (image.getWidth() / 4);

        Set<Callable<File>> callables = new HashSet<>();
        for (Side side : sides) {
            callables.add(new CubeFaceConverter(side, cubeSize));
        }
        try {
            List<Future<File>> list = es.invokeAll(callables);
            for (Future<File> future : list) {
                panoramaFilesList.add(future.get());
            }
            es.shutdown();
//            es.awaitTermination(5000, TimeUnit.MILLISECONDS);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }


        image.flush();
        image = null;
        //System.gc();

        if (location != null) {
            return new PanoRaw(panoramaFilesList, thumb, prev, location.getLongitude(), location.getLatitude(), 0);
        } else return new PanoRaw(panoramaFilesList, thumb, prev, 0, 0, 0);


    }

    private double[] getLongLat(double x, double y, Side side, double size) {
        double X = x - size / 2;
        double Y = y - size / 2;
        double Z = size / 2;

        double lngOffset = side.lng;
        double latOffset = side.lat;

        double hyp;
        double lng;
        double lat;

        if (latOffset != 0) {
            hyp = Math.sqrt(X * X + Y * Y);
            lng = lngOffset + Math.atan2(Y, X);
            lat = Math.atan2(Z, hyp);
            if (latOffset > 0) {
                lat *= -1;
                lng *= -1;
            }
        } else {
            hyp = Math.sqrt(X * X + Z * Z);
            lng = lngOffset + Math.atan2(X, Z);
            lat = Math.atan2(Y, hyp);
        }

        while (lng < 0)
            lng += Math.PI * 2;
        lng = lng % (Math.PI * 2);

        return new double[]{lng, lat};
    }

    private static class Side {
        String name;
        double lng;
        double lat;

        Side(String name, double lng, double lat) {
            this.name = name;
            this.lng = lng;
            this.lat = lat;
        }
    }

    private class CubeFaceConverter implements Callable<File> {
        private Side side;
        private double cubeSize;
        private BufferedImage tempImage;

        private CubeFaceConverter(Side side, double cubeSize) {
            this.side = side;
            this.cubeSize = cubeSize;

        }


        @Override
        public File call() throws Exception {
            System.out.println("processing " + side.name);
            tempImage = new BufferedImage((int) cubeSize, (int) cubeSize, BufferedImage.TYPE_3BYTE_BGR);
            for (int x = 0; x < cubeSize; x++) {
                for (int y = 0; y < cubeSize; y++) {
                    double[] lnglat = getLongLat(x, y, side, cubeSize);
                    double lng = lnglat[0];
                    double lat = lnglat[1];

                    int coordX = (int) Math.round((lng / Math.PI / 2) * image.getWidth());
                    int coordY = (int) Math.round(((lat + Math.PI / 2) / Math.PI) * image.getHeight());
                    if (coordX >= image.getWidth() || coordY >= image.getHeight()) continue;
                    try {
                        int col = image.getRGB(coordX, coordY);
                        tempImage.setRGB(x, y, col);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println(coordX + " " + coordY);
                    }
                }
            }

            complete += 5;

            File cubeFaceFile = new File(tempFolder.getPath() + "/" + side.name + ".bmp");
            File cubeFaceFileJpg = new File(tempFolder.getPath() + "/" + side.name + ".jpg");
            try {

                BufferedImage tempImageResized = Thumbnails.of(tempImage).size(3072, 3072).antialiasing(Antialiasing.ON).asBufferedImage();
                ImageIO.write(tempImageResized, "bmp", cubeFaceFile);
                ImageConverter.writeJPG(tempImageResized, cubeFaceFileJpg);
                tempImage.flush();
                tempImage = null;
                tempImageResized.flush();
                tempImageResized = null;
                System.gc();

                complete += 5;

            } catch (IOException e) {
                e.printStackTrace();
            }
            return cubeFaceFile;
        }
    }
}
