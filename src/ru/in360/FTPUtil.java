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



package ru.in360;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
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