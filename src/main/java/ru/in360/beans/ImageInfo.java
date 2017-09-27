/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 27.09.17 14:12 Anton Fomchenko 360@in360.ru
 */

package ru.in360.beans;



import ru.in360.constants.ImageType;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "image")
public class ImageInfo {
    @XmlElement(name = "level")
    List<ImageLevelInfo> levels = new ArrayList<>();
    @XmlElement(name = "mobile")
    List<ImageLevelInfo> mobileLevels  = new ArrayList<>();
    @XmlAttribute
    public ImageType type;
    @XmlAttribute
    public String hfov;
    @XmlAttribute
    public String vfov;
    @XmlAttribute
    public Double voffset;
    @XmlAttribute
    public Boolean multires;
    @XmlAttribute
    public Double multiresthreshold;
    @XmlAttribute
    public Boolean progressive;
    @XmlAttribute
    public Integer tilesize;
    @XmlAttribute
    public Integer baseindex;
    @XmlAttribute
    public Integer frames;
    @XmlAttribute
    public Integer frame;
    @XmlAttribute
    public String prealign;

    public void addLevel(ImageLevelInfo level){
        levels.add(level);
    }

    public void addMobileLevel(ImageLevelInfo mobileLevel){
        mobileLevels.add(mobileLevel);
    }
}
