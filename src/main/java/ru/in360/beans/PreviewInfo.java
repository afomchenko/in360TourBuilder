package ru.in360.beans;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.net.URI;

@XmlRootElement(name = "preview")
public class PreviewInfo {
    @XmlAttribute
    public String type;
    @XmlAttribute
    public String url;

    public PreviewInfo() {
    }

    public PreviewInfo(String url) {
        this.url = url;
    }

    public PreviewInfo(URI url) {
        this.url = url.getPath();
    }

    public PreviewInfo(String type, String url) {
        this.type = type;
        this.url = url;
    }
}
