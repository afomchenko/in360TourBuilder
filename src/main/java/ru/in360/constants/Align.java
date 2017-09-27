/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 27.09.17 14:12 Anton Fomchenko 360@in360.ru
 */

package ru.in360.constants;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;
import java.util.stream.Stream;

@XmlType(name = "align")
@XmlEnum
public enum Align {
    @XmlEnumValue("")           NONE(""),
    @XmlEnumValue("lefttop")    LEFTTOP("lefttop"),
    @XmlEnumValue("left")       LEFT("left"),
    @XmlEnumValue("leftbottom") LEFTBOTTOM("leftbottom"),
    @XmlEnumValue("top")        TOP("top"),
    @XmlEnumValue("center")     CENTER("center"),
    @XmlEnumValue("bottom")     BOTTOM("bottom"),
    @XmlEnumValue("righttop")   RIGHTTOP("righttop"),
    @XmlEnumValue("right")      RIGHT("right"),
    @XmlEnumValue("rightbottom")RIGHTBOTTOM("rightbottom");

    private String label;

    Align(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }

    public String value() {
        return label;
    }

    public static Align fromValue(String v) {
        return Stream.of(Align.values()).filter(e -> e.label.equals(v)).findFirst().orElse(NONE);
    }
}
