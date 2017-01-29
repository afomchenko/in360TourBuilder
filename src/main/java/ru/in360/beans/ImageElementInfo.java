package ru.in360.beans;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ImageElementInfo {
    @XmlAttribute
    public String url;
}
