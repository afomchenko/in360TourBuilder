/*
 * This file is part of in360TourBuilder.
 *
 *     in360TourBuilder is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     in360TourBuilder is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Этот файл — часть in360TourBuilder.
 *
 *    in360TourBuilder - свободная программа: вы можете перераспространять ее и/или
 *    изменять ее на условиях Стандартной общественной лицензии GNU в том виде,
 *    в каком она была опубликована Фондом свободного программного обеспечения;
 *    либо версии 3 лицензии, либо (по вашему выбору) любой более поздней
 *    версии.
 *
 *    in360TourBuilder распространяется в надежде, что она будет полезной,
 *    но БЕЗО ВСЯКИХ ГАРАНТИЙ; даже без неявной гарантии ТОВАРНОГО ВИДА
 *    или ПРИГОДНОСТИ ДЛЯ ОПРЕДЕЛЕННЫХ ЦЕЛЕЙ. Подробнее см. в Стандартной
 *    общественной лицензии GNU.
 *
 *    Вы должны были получить копию Стандартной общественной лицензии GNU
 *    вместе с этой программой. Если это не так, см.
 *    <http://www.gnu.org/licenses/>.
 *
 * 06.11.14 1:47 Anton Fomchenko 360@in360.ru
 */

package ru.in360.pano;


import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.resizers.BicubicResizer;
import net.coobird.thumbnailator.resizers.configurations.Antialiasing;
import org.w3c.dom.Document;
import ru.in360.FileUtils;
import ru.in360.TourProject;
import ru.in360.TourXMLBuilder;
import ru.in360.elements.*;
import ru.in360.elements.impl.*;

import javax.imageio.ImageIO;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

public class Pano implements Serializable {

    private static final int TILESIZE_DEFAULT = 768;
    private static final long serialVersionUID = -2144190016289562750L;

    //private static int counter = 1;
    private final int id;
    public int complete = 0;
    private String name;
    private transient PanoRaw panoRaw;
    private ImagePano imagePano;
    private String tilesFolderString;
    private Scene scene;
    private File tiles;
    private File editorPreview;
    private double lng;
    private double lat;
    private double heading;
    private double hlookat;
    private double vlookat;
    private double fov = 70.0;
    private boolean uploaded = false;

    public Pano(String name) {
        this.name = name;
        this.id = TourProject.getInstance().getPanoCounter();
        this.panoRaw = new PanoRaw(null, null, null, 0, 0, 0);
    }

    public Pano(String name, String fileEquiPath, boolean doOversample) throws IllegalArgumentException {
        this.name = new File(fileEquiPath).getName();
        this.id = TourProject.getInstance().getPanoCounter();
        System.out.println("creating panorama:" + fileEquiPath);
        generateImages(fileEquiPath, doOversample);
    }

    public int getComplete() {
        return complete;
    }

    public File getTiles() {
        return tiles;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pano)) return false;

        Pano pano = (Pano) o;

        if (id != pano.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public double getHlookat() {
        return hlookat;
    }

    public void setHlookat(double hlookat) {
        this.hlookat = hlookat;
    }

    public double getVlookat() {
        return vlookat;
    }

    public void setVlookat(double vlookat) {
        this.vlookat = vlookat;
    }

    public double getFov() {
        return fov;
    }

    public void setFov(double fov) {
        this.fov = fov;
    }

    public Scene getScene() {
        return scene;
    }

    public void generateImages(String path, boolean doOversample) {
        generateImages(new File(path), doOversample);
    }

    public void generateImages(File file, boolean doOversample) {
        try {
            panoRaw = BitmapEdit.getInstance().sphToCube(file, doOversample);
            lng = panoRaw.lng;
            lat = panoRaw.lat;
            heading = panoRaw.heading;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double getHeading() {
        return heading;
    }

    public void setHeading(double heading) {
        this.heading = heading;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public File getPreviewImage() {
        if (panoRaw != null)
            return panoRaw.previewImage;
        else
            return editorPreview;
    }

    public File getThumb() {
        return panoRaw.thumb;
    }

    public List<File> getSides() {
        return panoRaw.sides;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Scene buildScene() {
        try {
            tilesFolderString = "pano" + id + ".tiles/";
            tiles = new File(TourProject.getInstance().getProjectFolder().getPath() + "/tiles/" + tilesFolderString);
            scene = new SceneImpl(this.name, this.id);


            scene.setView(new ViewImpl(hlookat, vlookat, fov));
            scene.setImage(addPano(TILESIZE_DEFAULT));
            scene.setCoordinates(lng, lat, heading);
            scene.setPreview(new PreviewImpl("%SWFPATH%/../tiles/" + tilesFolderString + "preview.jpg"));
            scene.setThumburl(addThumbPano());

            panoRaw = null;
            return scene;
        } catch (OutOfMemoryError e) {
            System.out.println("not enough memory, adding panorama aborted");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateFilePaths(){
        tiles = new File(TourProject.getInstance().getProjectFolder().getPath() + "/tiles/" + tilesFolderString);
        editorPreview = new File(TourProject.getInstance().getProjectFolder().getPath() + "/tiles/" + tilesFolderString + "editorpreview.jpg");
    }

    public Document buildScenePreviewXML() {

        try {
            Action actionImpl = new ActionImpl("startup", ActionImpl.Autorun.NONE, false);
            actionImpl.addActionContent("if(startscene === null, copy(startscene,scene[0].name));");
            actionImpl.addActionContent("loadscene(get(startscene), null, MERGE);");
            Include include = new IncludeImpl("%SWFPATH%/../skin/vtourskin.xml");
            TourXMLBuilder builder = new TourXMLBuilder("1.18", "Virtual Tour", "startup();");

            builder.add(actionImpl);
            builder.add(include);

            SettingsSkin settingsSkin = new SettingsSkin();
            settingsSkin.addElement("bingmaps", "true");
            settingsSkin.addElement("bingmaps_key", TourProject.getInstance().getBingMapsKey());
            settingsSkin.addElement("bingmaps_zoombuttons", "false");
            settingsSkin.addElement("thumbs_width", "120");
            settingsSkin.addElement("thumbs_height", "80");
            settingsSkin.addElement("thumbs_padding", "10");
            settingsSkin.addElement("thumbs_crop", "0|40|240|160");
            settingsSkin.addElement("thumbs_opened", "false");
            settingsSkin.addElement("thumbs_text", "true");
            settingsSkin.addElement("thumbs_dragging", "true");
            settingsSkin.addElement("thumbs_onhoverscrolling", "false");
            settingsSkin.addElement("thumbs_scrollbuttons", "false");
            settingsSkin.addElement("thumbs_scrollindicator", "false");
            settingsSkin.addElement("thumbs_loop", "false");
            settingsSkin.addElement("tooltips_thumbs", "false");
            settingsSkin.addElement("tooltips_hotspots", "true");
            settingsSkin.addElement("tooltips_mapspots", "false");
            settingsSkin.addElement("controlbar_offset", "0");
            builder.add(settingsSkin);


            builder.add(scene);

            return builder.build();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String addThumbPano() throws IOException {
        BufferedImage thumb = ImageIO.read(panoRaw.thumb);
        String thumbPath = TourProject.getInstance().getProjectFolder().getPath() + "/tiles/" + tilesFolderString + "thumb.jpg";
        ImageConverter.writeJPG(thumb, new File(thumbPath));
        return "%SWFPATH%/../tiles/" + tilesFolderString + "thumb.jpg";
    }

    private void addEditorPreview() throws IOException {
        BufferedImage preview = ImageIO.read(panoRaw.previewImage);
        editorPreview = new File(TourProject.getInstance().getProjectFolder().getPath() + "/tiles/" + tilesFolderString + "editorpreview.jpg");
        ImageConverter.writeJPG(preview, editorPreview);
    }

    private ImagePano addPano(int tileSize) {


        ImagePano image = null;


        try {
            BufferedImage previewImage = new BufferedImage(256, 1536, BufferedImage.TYPE_3BYTE_BGR);
            Graphics2D previewGraphics = previewImage.createGraphics();
            //int previewOffset = 0;

            // boolean imageXMLComplete = false;
            ExecutorService es = Executors.newFixedThreadPool(6);
            Set<Callable<ImagePano>> callables = new HashSet<>();
            for (File side : panoRaw.sides) {
                callables.add(new CubeLevelsGenerator(tilesFolderString, side, previewGraphics, tileSize));
            }
            try {
                List<Future<ImagePano>> list = es.invokeAll(callables);
                for (Future<ImagePano> future : list) {

                    image = future.get();
                    break;
                }
                es.shutdown();

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            ImageConverter.writeJPG(previewImage, new File(TourProject.getInstance().getProjectFolder().getPath() + "/tiles/" + tilesFolderString + "preview.jpg"));
            previewGraphics.dispose();
            previewImage.flush();
            previewImage = null;

            addEditorPreview();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    public void updateSceneFromPano() {
        scene.getView().setFov(fov);
        scene.getView().setHlookat(hlookat);
        scene.getView().setVlookat(vlookat);
        scene.setCoordinates(lng, lat, heading);
        scene.setTitle(name);
    }

    public void clear() {
        System.err.println("clear " + this.name);
        if (tiles != null) {
            System.err.println("Tiles folder " + tiles.getAbsolutePath() + " delete");
            try {
                FileUtils.deleteDirectory(tiles.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isUploaded() {
        return uploaded;
    }

    public void setUploaded(boolean uploaded) {
        this.uploaded = uploaded;
    }

    private class CubeLevelsGenerator implements Callable<ImagePano> {

        BufferedImage resizedSideImage = null;
        String tilesFolder;
        int tileSize = 768;
        File side;
        Graphics2D previewGraphics;

        private CubeLevelsGenerator(String tilesFolder, File side, Graphics2D previewGraphics, int tileSize) {
            this.tilesFolder = tilesFolder;
            this.tileSize = tileSize;
            this.side = side;
            this.tilesFolder = tilesFolder;
            this.previewGraphics = previewGraphics;
        }


        @Override
        public ImagePano call() throws Exception {
            ImagePano image = new ImageImpl(ImagePano.ImageType.CUBE, true);
            image.setProgressive(true);
            image.setTilesize(tileSize);
            ImageLevelMobile mobile = new ImageLevelMobile();
            mobile.addImageElement(new ImageElementImpl("cube", "%SWFPATH%/../tiles/" + tilesFolder + "mobile_%s.jpg"));
            image.addLevel(mobile);

            String sideName = Character.toString(side.getName().charAt(0));
            int sizeLevel = 1;
            int resizedWidth = tileSize;
            resizedSideImage = Thumbnails.of(side).size(resizedWidth, resizedWidth).antialiasing(Antialiasing.ON).resizer(new BicubicResizer()).asBufferedImage();

            while (resizedWidth <= 3100) {
                System.out.println("processing " + side.getName() + " " + resizedSideImage.getHeight() + " " + resizedSideImage.getWidth());

                ImageLevel level = new ImageLevel(resizedWidth, resizedWidth);
                //mask format "tiles/Pano.tif.tiles/%s/l3/%v/l3_%s_%v_%h.jpg"
                String maskImagePath = "%SWFPATH%/../tiles/" + tilesFolder + "%s/l" + sizeLevel + "/%v/l" + sizeLevel + "_%s_%v_%h.jpg";
                level.addImageElement(new ImageElementImpl("cube", maskImagePath));
                image.addLevel(level);


                for (int i = 0; i < resizedSideImage.getHeight(); i += tileSize)
                    for (int j = 0; j < resizedSideImage.getWidth(); j += tileSize) {

                        //System.out.println("i = " + i + "j = " + j);
                        BufferedImage tile = resizedSideImage.getSubimage(j, i, tileSize, tileSize);

                        String outputTileFolder = tilesFolder + sideName + "/"
                                + "l" + sizeLevel + "/" + (i / tileSize + 1);
                        File outputTile = new File(TourProject.getInstance().getProjectFolder().getPath() + "/tiles/" + outputTileFolder + File.separator
                                + "l" + sizeLevel + "_" + side.getName().charAt(0) + "_" + (i / tileSize + 1) + "_" + (j / tileSize + 1) + ".jpg");
                        new File(TourProject.getInstance().getProjectFolder().getPath() + "/tiles/" + outputTileFolder).mkdirs();


                        try {
                            ImageConverter.writeJPG(tile, outputTile);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                if (sizeLevel == 2) {
                    File outputMobileTile = new File(TourProject.getInstance().getProjectFolder().getPath() + "/tiles/" + tilesFolder + "mobile_" + sideName + ".jpg");
                    ImageConverter.writeJPG(resizedSideImage, outputMobileTile);
                }

                resizedWidth *= 2;

                if (sizeLevel < 3)
                    resizedSideImage = Thumbnails.of(side).size(resizedWidth, resizedWidth).antialiasing(Antialiasing.ON).resizer(new BicubicResizer()).asBufferedImage();
                sizeLevel++;

            }

            int previewOffset = 0;
            switch (sideName) {
                case "l":
                    previewOffset = 0;
                    break;
                case "f":
                    previewOffset = 256;
                    break;
                case "r":
                    previewOffset = 512;
                    break;
                case "b":
                    previewOffset = 768;
                    break;
                case "u":
                    previewOffset = 1024;
                    break;
                case "d":
                    previewOffset = 1280;
                    break;
                default:
                    break;
            }
            previewGraphics.drawImage(Thumbnails.of(resizedSideImage).size(256, 256).asBufferedImage(), 0, previewOffset, null);

            resizedSideImage.flush();
            resizedSideImage = null;
            // sideImage.flush();
            // sideImage = null;
            System.gc();

            return image;
        }
    }
}
