/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 27.09.17 14:12 Anton Fomchenko 360@in360.ru
 */

package ru.in360;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ru.in360.elements.TourElement;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class TourXMLBuilder {
    private Document doc;
    private Element rootElement;

    public TourXMLBuilder(Document doc) {
        this.doc = doc;
    }

    public TourXMLBuilder(String version, String title, String onStart) throws ParserConfigurationException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = documentBuilderFactory.newDocumentBuilder();
        doc = docBuilder.newDocument();

        rootElement = doc.createElement("krpano");
        rootElement.setAttribute("version", version);
        rootElement.setAttribute("title", title);
        rootElement.setAttribute("onstart", onStart);
        doc.appendChild(rootElement);
    }

    public TourXMLBuilder add(TourElement element) {
        rootElement.appendChild(element.getXMLElement(doc));
        return this;
    }

    public Document build() {
        return doc;
    }
}
