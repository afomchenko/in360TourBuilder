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

@XmlType(name = "autorun")
@XmlEnum
public enum Autorun {
    @XmlEnumValue("")           NONE(null),
    @XmlEnumValue("preinit")    PREINIT("preinit"),
    @XmlEnumValue("onstart")    ONSTART("onstart");

    private String label;

    Autorun(String label) {
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

    public static Autorun fromValue(String v) {
        return Stream.of(Autorun.values()).filter(e -> e.label.equals(v)).findFirst().orElse(NONE);
    }
}