package ru.in360.beans;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.net.URI;

@XmlRootElement(name = "include")
public class IncludeInfo extends TourElement {
    @XmlAttribute
    public String url;

    public IncludeInfo() {
    }

    public IncludeInfo(String urlString) {
        this.url = urlString;
    }

    public IncludeInfo(URI url) {
        this.url = url.getPath();
    }
}
