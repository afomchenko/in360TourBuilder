/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 27.09.17 14:12 Anton Fomchenko 360@in360.ru
 */

package ru.in360.elements;


public interface Hotspot extends TourElement {
    Style getStyle();

    void setStyle(Style style);

    double getAth();

    void setAth(double ath);

    double getAtv();

    void setAtv(double atv);

    double getHview();

    void setHview(double hview);

    double getVview();

    void setVview(double vview);

    double getFovview();

    void setFovview(double fovview);

    double getHcenter();

    void setHcenter(double hcenter);

    double getVcenter();

    void setVcenter(double vcenter);

    String getName();
}
