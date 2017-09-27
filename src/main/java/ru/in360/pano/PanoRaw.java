/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 27.09.17 14:12 Anton Fomchenko 360@in360.ru
 */

package ru.in360.pano;


import java.io.File;
import java.util.List;

class PanoRaw {
    protected List<File> sides;
    protected File thumb;
    protected double lng;
    protected double lat;
    protected double heading;
    protected File previewImage;

    public PanoRaw(List<File> sides, File thumb, File previewImage, double lng, double lat, double heading) {
        this.sides = sides;
        this.thumb = thumb;
        this.lng = lng;
        this.lat = lat;
        this.heading = heading;
        this.previewImage = previewImage;
    }
}
