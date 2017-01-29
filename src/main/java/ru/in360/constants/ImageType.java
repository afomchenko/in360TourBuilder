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
