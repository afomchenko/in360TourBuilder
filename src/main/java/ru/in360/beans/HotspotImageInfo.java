/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 27.09.17 14:12 Anton Fomchenko 360@in360.ru
 */

package ru.in360.beans;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "hotspot")
public class HotspotImageInfo extends TourElement {
    private static int hotspotCount = 0;

    @XmlAttribute
    public StyleInfo style;
    @XmlAttribute
    public Double ath;
    @XmlAttribute
    public Double atv;
    @XmlAttribute
    public String linkedscene;
    @XmlAttribute
    public Double hview;
    @XmlAttribute
    public Double vview;
    @XmlAttribute
    public Double fovview;
    @XmlAttribute
    public Double hcenter;
    @XmlAttribute
    public Double vcenter;

    public HotspotImageInfo(String linkedscene, double ath, double atv) {
        this.name = "spot_" + hotspotCount++;
        this.ath = ath;
        this.atv = atv;
        this.linkedscene = linkedscene;
        this.style = new StyleInfo("skin_hotspotstyle");
    }

    public HotspotImageInfo(String name, String linkedscene, double ath, double atv) {
        this.name = name;
        this.ath = ath;
        this.atv = atv;
        this.linkedscene = linkedscene;
        this.style = new StyleInfo("skin_hotspotstyle");
    }

    public static int getHotspotCount() {
        return hotspotCount;
    }

    public static void setHotspotCount(int hotspotCount) {
        HotspotImageInfo.hotspotCount = hotspotCount;
    }

}
