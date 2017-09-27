/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 27.09.17 14:12 Anton Fomchenko 360@in360.ru
 */

package ru.in360.elements;

import java.util.Map;


public interface Scene extends TourElement {
    String getName();

    String getTitle();

    void setTitle(String title);

    Action getOnstart();

    void setOnstart(Action onstart);

    String getThumburl();

    void setThumburl(String thumburl);

    View getView();

    void setView(View view);

    Preview getPreview();

    void setPreview(Preview preview);

    ImagePano getImage();

    void setImage(ImagePano image);

    public double getHeadingOffset();

    public void setHeadingOffset(double headingOffset);

    Map<String, Hotspot> getSceneHotspots();

    void addHotspot(Hotspot hotspot);

    void removeIncomingHotspot(String nameFrom);

    void registerIncomingHotspots(String nameFrom, Hotspot hotspot);

    void setCoordinates(double lat, double lng, double heading);
}
