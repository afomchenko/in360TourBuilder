/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 27.09.17 14:12 Anton Fomchenko 360@in360.ru
 */

package ru.in360;

import ru.in360.beans.ActionInfo;
import ru.in360.beans.SceneInfo;
import ru.in360.beans.TourInfo;
import ru.in360.beans.ViewInfo;

import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;


import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class BeanXMLTest extends TourTest {

    @Test
    public void actionMarshallerTest() throws Exception {
        ActionInfo action = getAction();
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(action, System.out);
    }

    @Test
    public void viewMarshallerTest() throws Exception {
        ViewInfo view = getView();
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(view, System.out);
    }

    @Test
    public void sceneMarshallerTest() throws Exception {
        SceneInfo scene = getScene(1L);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(scene, System.out);
    }

    @Test
    public void tourMarshallerTest() throws Exception {
        TourInfo tour = tourInfo();
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(tour, System.out);
        File file = new File("target/file.xml");
        jaxbMarshaller.marshal(tour, file);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        TourInfo tourFromXML = (TourInfo) jaxbUnmarshaller.unmarshal(XMLTools.getAsXMLStream(new FileInputStream(file)));
        assertTrue(tour.version.equals(tourFromXML.version));
        assertTrue(tour.title.equals(tourFromXML.title));
        assertTrue(tour.onstart.content.size() == tourFromXML.onstart.content.size());
        assertTrue(tourFromXML.elements.size()==5);

    }

    @Test
    public void tourUnMarshallerTest() throws Exception {
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        InputStream inputXML = this.getClass().getResourceAsStream("tour.xml");
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        TourInfo tourFromXML = (TourInfo) jaxbUnmarshaller.unmarshal(XMLTools.getAsXMLStream(inputXML));
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(tourFromXML, System.out);
    }
}
