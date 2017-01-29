package ru.in360.constants;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "FOVtype")
@XmlEnum
public enum FOVType {
    HFOV,
    VFOV,
    DFOV,
    MFOV;

    public String value() {
        return name();
    }

    public static FOVType fromValue(String v) {
        return valueOf(v);
    }
}