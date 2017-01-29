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