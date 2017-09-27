/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 27.09.17 14:12 Anton Fomchenko 360@in360.ru
 */

package ru.in360;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;/**
 * This utility class implements method for uploading a whole directory from
 * local computer to a remote FTP server, based on Apache Commons Net library.
 *
 * @author www.codejava.net
 */
public class FTPUtil {


    public static void saveFilesToServer(FTPClient ftp, String remoteDest, File localSrc) throws IOException {

        System.out.println("Connected to server .");


        System.out.println("remote directory: " + remoteDest);
        ftp.changeWorkingDirectory(remoteDest);
        ftp.setFileType(FTPClient.BINARY_FILE_TYPE);

        System.out.println("upload: " + localSrc.getPath());
        upload(localSrc, ftp);
        System.out.println(ftp.getReplyString());

    }

    public static void upload(File src, FTPClient ftp) throws IOException {
        if (src.isDirectory()) {
            ftp.makeDirectory(src.getName());
            ftp.changeWorkingDirectory(src.getName());
            for (File file : src.listFiles()) {
                upload(file, ftp);
            }
            ftp.changeToParentDirectory();
        } else {
            InputStream srcStream = null;
            try {
                srcStream = src.toURI().toURL().openStream();
                ftp.storeFile(src.getName(), srcStream);
            } finally {
                IOUtils.closeQuietly(srcStream);
            }
        }
    }
}