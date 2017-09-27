/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 27.09.17 14:12 Anton Fomchenko 360@in360.ru
 */

package ru.in360;

import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URI;


public class FTPUploader implements Serializable {

    private static final long serialVersionUID = -4135812741477796868L;
    String server = "46.8.19.232";
    int port = 21;
    String user = "admin";
    String pass = "";
    String remoteProject = "/web/ailes.ru/public_html/in360";


    public String getRemoteProject() {
        return remoteProject;
    }

    public void setRemoteProject(String remoteProject) {
        this.remoteProject = remoteProject;
    }

    public boolean uploadFolder(File src, URI dest) {
        FTPClient ftpClient = new FTPClient();

        try {
            // connect and login to the server
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);

            // use local passive mode to pass firewall
            ftpClient.enterLocalPassiveMode();

            System.out.println("Connected");

            FTPUtil.saveFilesToServer(ftpClient, remoteProject, src);

            // log out and disconnect from the server
            ftpClient.logout();
            ftpClient.disconnect();

            System.out.println("Disconnected");
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean upload(File src, URI dest) {
        FTPClient ftpClient = new FTPClient();

        try {
            // connect and login to the server
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);

            // use local passive mode to pass firewall
            ftpClient.enterLocalPassiveMode();

            String path = "";
            for (String dir : (remoteProject + dest.getPath()).split("/")) {
                ftpClient.makeDirectory(path + "/" + dir);
                path = path + "/" + dir;
            }

            System.out.println("Connected");

            System.out.println(remoteProject + dest.getPath());
            FTPUtil.saveFilesToServer(ftpClient, remoteProject + dest.getPath(), src);
            // log out and disconnect from the server
            ftpClient.logout();
            ftpClient.disconnect();

            System.out.println("Disconnected");
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) throws IllegalArgumentException {
        if (true)
            this.server = server;
        else throw new IllegalArgumentException("invalid ip: " + server);
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) throws IllegalArgumentException {
        if (port > 0 && port < 65535)
            this.port = port;
        else throw new IllegalArgumentException("invalid port number: " + port);
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}