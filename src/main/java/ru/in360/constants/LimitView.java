package ru.in360.constants;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;
import java.util.stream.Stream;

@XmlType(name = "limitView")
@XmlEnum
public enum LimitView {
    @XmlEnumValue("off")        OFF("off"),
    @XmlEnumValue("auto")       AUTO("auto"),
    @XmlEnumValue("lookat")     LOOKAT("lookat"),
    @XmlEnumValue("range")      RANGE("range"),
    @XmlEnumValue("fullrange")  FULLRANGE("fullrange"),
    @XmlEnumValue("offrange")   OFFRANGE("offrange");

    private String label;

    LimitView(String label) {
        this.label = label;
    }

    public String value() {
        return label;
    }

    public static LimitView fromValue(String v) {
        return Stream.of(LimitView.values()).filter(e -> e.label.equals(v)).findFirst().orElse(OFF);
    }
}