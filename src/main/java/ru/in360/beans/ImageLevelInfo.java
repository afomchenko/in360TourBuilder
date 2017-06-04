package ru.in360.beans;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "level")
public class ImageLevelInfo {
    @XmlAttribute
    public Integer tiledimagewidth;
    @XmlAttribute
    public Integer tiledimageheight;
    @XmlElementRef(name="cube", type=CubeImageElementInfo.class)
    public ImageElementInfo imageElement;
}
