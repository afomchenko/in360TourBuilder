/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 27.09.17 14:12 Anton Fomchenko 360@in360.ru
 */

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
