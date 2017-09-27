/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 27.09.17 14:12 Anton Fomchenko 360@in360.ru
 */

package ru.in360.constants;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "type")
@XmlEnum
public enum ImageType {
    CUBE,
    CUBESTRIP,
    CYLINDER,
    SPHERE,
    NUL;

    public String value() {
        return name();
    }

    public static ImageType fromValue(String v) {
        return valueOf(v);
    }
}
