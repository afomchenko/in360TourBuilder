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

@XmlType(name = "layerType")
@XmlEnum
public enum LayerType {
    @XmlEnumValue("image")     IMAGE("image"),
    @XmlEnumValue("container") CONTAINER("container");

    private String label;

    LayerType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }

    public String value() {
        return label;
    }

    public static LayerType fromValue(String v) {
        return Stream.of(LayerType.values()).filter(e -> e.label.equals(v)).findFirst().orElse(IMAGE);
    }
}