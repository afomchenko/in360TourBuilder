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


import org.apache.log4j.Logger;
import ru.in360.beans.TourInfo;
import ru.in360.pano.Pano;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class TourProject implements Serializable {
    private final static Lock lock = new ReentrantLock();
    private static final long serialVersionUID = -8676518388266115774L;

    final static Logger logger = Logger.getLogger(TourProject.class);

    private static TourProject instance;
    public int complete = 0;
    private int panoCounter = 1;
    private String projectName;
    private File projectFolder;
    private File skinFolder;
    private File pluginsFolder;
    private File viewerJsFile;
    private File tempFolder;
    private File viewerSwfFile;
    private PanoCollection panoramaStorage;
    private FTPUploader ftpUploader;
    private String bingMapsKey = "";

    private TourProject() {
        panoramaStorage = new PanoCollection();
        ftpUploader = new FTPUploader();
    }

    private TourProject(String projectName, File projectFolder, File skinFolder, File viewerJsFile, File viewerSwfFile) {
        this.projectName = projectName;

        this.projectFolder = projectFolder;
        this.skinFolder = new File(projectFolder.getPath() + "/skin/");
        skinFolder.mkdirs();
        this.pluginsFolder = new File(projectFolder.getPath() + "/viewer/plugins/");
        pluginsFolder.mkdirs();

    }

    public String getBingMapsKey() {
        return bingMapsKey;
    }

    public void setBingMapsKey(String bingMapsKey) {
        this.bingMapsKey = bingMapsKey;
    }

    public static TourProject getInstance() {
        try {
            lock.lock();
            if (instance == null) {
                instance = new TourProject();
            }
            return instance;
        } finally {
            lock.unlock();
        }
    }

//    public void updatePaths(){
//        this.skinFolder = new File(projectFolder.getPath() + "/skin/");
//        this.pluginsFolder = new File(projectFolder.getPath() + "/viewer/plugins/");
//        this.viewerJsFile = new File(projectFolder.getPath() + "/viewer/krpano.js");
//        this.viewerSwfFile = new File(projectFolder.getPath() + "/viewer/krpano.swf");
//        this.tempFolder = new File(projectFolder.getPath() + "/temp/");
//    }

    public int getComplete() {
        return complete;
    }

    public void setComplete(int complete) {
        this.complete = complete;
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        instance = this;
    }

    private Object readResolve() {
        return instance;
    }

    public File getTempFolder() {
        return tempFolder;
    }

    public PanoCollection getPanoramaStorage() {
        return panoramaStorage;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        try {
            lock.lock();
            //if (projectName == null)
            this.projectName = projectName;
        } finally {
            lock.unlock();
        }
    }

    public File getProjectFolder() {
        return projectFolder;
    }

    public void setProjectFolder(File projectFolder) {
        try {
            lock.lock();
            this.projectFolder = projectFolder;
            this.skinFolder = new File(projectFolder.getPath() + "/skin/");
            skinFolder.mkdirs();
            tempFolder = new File(projectFolder.getPath() + "/temp/");
            tempFolder.mkdirs();
            pluginsFolder = new File(projectFolder.getPath() + "/viewer/plugins/");
            pluginsFolder.mkdirs();
            this.viewerJsFile = new File(projectFolder.getPath() + "/viewer/krpano.js");
            this.viewerSwfFile = new File(projectFolder.getPath() + "/viewer/krpano.swf");
            updatePanoTiles();

        } finally {
            lock.unlock();
        }
    }

    private void updatePanoTiles() {
        for (Pano pano : panoramaStorage.getPanoramas()) {
            pano.updateFilePaths();
        }

    }

    public File getSkinFolder() {
        return skinFolder;
    }

    public void setSkinFolder(File skinFolder) {
        this.skinFolder = skinFolder;
    }

    public void addSkinFolder(File newSkinFolder) {
        try {
            FileUtils.copyFolder(newSkinFolder, this.skinFolder);
        } catch (IOException e) {
            logger.error(e);
        }
    }

    public void addPluginsFolder(File newPluginsFolder) {
        try {
            FileUtils.copyFolder(newPluginsFolder, this.pluginsFolder);
        } catch (IOException e) {
            logger.error(e);
        }
    }

    public File getViewerSwfFile() {
        return viewerSwfFile;
    }

    public void setViewers(File viewerJsFile, File viewerSwfFile) {
        if (viewerJsFile != null) {
            this.viewerJsFile = new File(projectFolder.getPath() + "/viewer/krpano.js");
            try {
                this.viewerJsFile.mkdirs();
                Files.copy(viewerJsFile.toPath(), this.viewerJsFile.toPath(), REPLACE_EXISTING);
            } catch (IOException e) {
                logger.error(e);
            }
        }
        if (viewerSwfFile != null) {
            this.viewerSwfFile = new File(projectFolder.getPath() + "/viewer/krpano.swf");
            try {
                this.viewerSwfFile.mkdirs();
                Files.copy(viewerSwfFile.toPath(), this.viewerSwfFile.toPath(), REPLACE_EXISTING);
            } catch (IOException e) {
                logger.error(e);
            }
        }
    }

    public File buildTour() {

        File htmlFile = new File(projectFolder.getPath() + "/index.html");
        try (BufferedWriter writerHTML = new BufferedWriter(new FileWriter(htmlFile))) {
            writerHTML.write(SceneTemplates.htmlBuild.replace("%TITLE%", this.getProjectName()));


            TourInfoFactory.getTourMarshaller()
                    .marshal(TourInfoFactory.createTourInfo(panoramaStorage.getScenes()), new File(projectFolder.getPath() + "/ru/in360/tour.xml"));
        } catch (IOException | JAXBException e) {
            logger.error(e);
        }
        return htmlFile;
    }

    public void saveProject() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(projectFolder.getPath() + "/project.proj")))) {
            oos.writeObject(this);
            oos.flush();
        } catch (IOException e1) {
            logger.error(e1);
        }
    }

    public void uploadToFTP() {
        buildTour();
        try {
            complete = 0;
            ftpUploader.upload(new File(projectFolder.getPath() + "/index.html"), new URI("/" + projectName));
            complete += 10;
            ftpUploader.upload(new File(projectFolder.getPath() + "/ru/in360/tour.xml"), new URI("/" + projectName));
            complete += 10;
            ftpUploader.upload(skinFolder, new URI("/" + projectName));
            complete += 10;
            ftpUploader.upload(new File(projectFolder.getPath() + "/viewer"), new URI("/" + projectName));
            complete += 10;

            for (Pano pano : panoramaStorage.getPanoramas()) {
                if (!pano.isUploaded()) {
                    ftpUploader.upload(pano.getTiles(), new URI("/" + projectName + "/tiles"));
                    pano.setUploaded(true);
                    complete += 5;
                    if (complete >= 95) {
                        complete = 95;
                    }
                }
            }
        } catch (URISyntaxException e) {
            logger.error(e);
        }
    }

    public File getViewerJsFile() {
        return viewerJsFile;
    }

    public int getPanoCounter() {
        return panoCounter++;
    }

    public FTPUploader getFtpUploader() {
        return ftpUploader;
    }

    public void setFtpUploader(FTPUploader ftpUploader) {
        this.ftpUploader = ftpUploader;
    }
}
