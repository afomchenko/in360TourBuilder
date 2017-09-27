/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 27.09.17 14:12 Anton Fomchenko 360@in360.ru
 */

package ru.in360.elements;


public interface View extends TourElement {
    double getHlookat();

    void setHlookat(double hlookat);

    double getVlookat();

    void setVlookat(double vlookat);

    double getFov();

    void setFov(double fov);

    FOVType getFovtype();

    void setFovtype(FOVType fovtype);

    double getMaxpixelzoom();

    void setMaxpixelzoom(double maxpixelzoom);

    double getFovmin();

    void setFovmin(double fovmin);

    double getFovmax();

    void setFovmax(double fovmax);

    LimitView getLimitview();

    void setLimitview(LimitView limitview);

    public enum FOVType {HFOV, VFOV, DFOV, MFOV}

    public enum LimitView {
        OFF("off"), AUTO("auto"), LOOKAT("lookat"), RANGE("range"), FULLRANGE("fullrange"), OFFRANGE("offrange");
        private String label;

        LimitView(String label) {
            this.label = label;
        }
    }
}
