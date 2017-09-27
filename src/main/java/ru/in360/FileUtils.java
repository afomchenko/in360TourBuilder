/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 27.09.17 14:12 Anton Fomchenko 360@in360.ru
 */

package ru.in360;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileUtils {

    public static void copyFolder(File src, File dest)
            throws IOException {

        if (src.isDirectory()) {

            //if directory not exists, create it
            if (!dest.exists()) {
                dest.mkdir();
                System.out.println("Directory copied from "
                        + src + "  to " + dest);
            }

            //list all the directory contents
            String files[] = src.list();

            for (String file : files) {
                //construct the src and dest file structure
                File srcFile = new File(src, file);
                File destFile = new File(dest, file);
                //recursive copy
                copyFolder(srcFile, destFile);
            }

        } else {
            //if file, then copy it
            //Use bytes stream to support all file types
            InputStream in = new FileInputStream(src);
            OutputStream out = new FileOutputStream(dest);

            byte[] buffer = new byte[1024];

            int length;
            //copy the file content in bytes
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }

            in.close();
            out.close();
            System.out.println("File copied from " + src + " to " + dest);
        }
    }

    public static void deleteDirectory(Path path) throws IOException {

        System.out.println("Deleting recursivey : " + path);

        Files.walkFileTree(path, new FileVisitor<Path>() {

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc)
                    throws IOException {

                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult preVisitDirectory(Path dir,
                                                     BasicFileAttributes attrs) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file,
                                             BasicFileAttributes attrs) throws IOException {

                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc)
                    throws IOException {
                System.out.println(exc.toString());
                return FileVisitResult.CONTINUE;
            }
        });
    }


    private void downloadKrpanoViewer() {
        try {
            URL website = new URL("http://krpano.com/download/download.php?file=krpano118winzip");
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            File tempKrpano = File.createTempFile("in360builder/tempfile", ".tmp");
            FileOutputStream fos = new FileOutputStream(tempKrpano);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.close();
            rbc.close();
            System.out.println("Done");


            byte[] buffer = new byte[1024];
            try {
                ZipInputStream zis = new ZipInputStream(new FileInputStream(
                        tempKrpano));
                ZipEntry ze = zis.getNextEntry();
                while (ze != null) {
                    String fileName = ze.getName();

                    if (!fileName.contains("viewer")) {
                        ze = zis.getNextEntry();
                        continue;
                    }

                    File newFile = new File("tourBuild" + File.separator
                            + fileName);

                    System.out.println("file unzip : "
                            + newFile.getAbsoluteFile());
                    if (ze.isDirectory()) {
                        String temp = newFile.getCanonicalPath();
                        new File(temp).mkdirs();
                    } else {
                        FileOutputStream zfos = new FileOutputStream(newFile);
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            zfos.write(buffer, 0, len);
                        }
                        zfos.close();
                    }
                    ze = zis.getNextEntry();
                }
                zis.closeEntry();
                zis.close();
                System.out.println("Unzip of ESS client is completed");

            } catch (IOException ex) {
                ex.printStackTrace();
            }

            System.out.println("Done");


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
