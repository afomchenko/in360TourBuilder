/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 27.09.17 14:12 Anton Fomchenko 360@in360.ru
 */

package ru.in360.elements;

import ru.in360.elements.impl.ImageLevel;

import java.util.List;


public interface ImagePano extends TourElement {
    void addLevel(ImageLevel level);

    ImageType getType();

    void setType(ImageType type);

    String getHfov();

    void setHfov(String hfov);

    String getVfov();

    void setVfov(String vfov);

    double getVoffset();

    void setVoffset(double voffset);

    boolean isMultires();

    void setMultires(boolean multires);

    double getMultiresthreshold();

    void setMultiresthreshold(double multiresthreshold);

    boolean isProgressive();

    void setProgressive(boolean progressive);

    int getTilesize();

    void setTilesize(int tilesize);

    int getBaseindex();

    void setBaseindex(int baseindex);

    int getFrames();

    void setFrames(int frames);

    int getFrame();

    void setFrame(int frame);

    String getPrealign();

    void setPrealign(String prealign);

    List<ImageLevel> getLevels();

    void setLevels(List<ImageLevel> levels);

    public enum ImageType {CUBE, CUBESTRIP, CYLINDER, SPHERE, NUL;}
}
