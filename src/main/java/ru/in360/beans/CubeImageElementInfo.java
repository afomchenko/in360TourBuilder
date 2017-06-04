package ru.in360.beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "cube")
public class CubeImageElementInfo extends ImageElementInfo {
    public CubeImageElementInfo() {
    }

    public CubeImageElementInfo(String url) {
        super();
        super.url = url;
    }
}
