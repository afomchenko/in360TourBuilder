/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 27.09.17 14:12 Anton Fomchenko 360@in360.ru
 */

package ru.in360;


import org.apache.log4j.Logger;
import ru.in360.beans.ActionInfo;
import ru.in360.beans.IncludeInfo;
import ru.in360.beans.PreviewInfo;
import ru.in360.beans.SceneInfo;
import ru.in360.beans.SkinSettingsInfo;
import ru.in360.beans.TourInfo;
import ru.in360.beans.ViewInfo;
import ru.in360.constants.Autorun;
import ru.in360.exceptions.TourBuildException;
import ru.in360.pano.Pano;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class TourInfoFactory {
    private final static Logger logger = Logger.getLogger(TourInfoFactory.class);

    public static Marshaller getTourMarshaller() {
        try {
        JAXBContext jaxbContext = JAXBContext.newInstance(TourInfo.class, SceneInfo.class, ActionInfo.class, ViewInfo.class);
            Marshaller jaxbMarshaller =  jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            return jaxbMarshaller;
        } catch (JAXBException e) {
            logger.error(e);
            throw new TourBuildException(e);
        }
    }

    public static TourInfo createTourInfo(List<SceneInfo> scenes){
        TourInfo tourInfo = new TourInfo();
        tourInfo.title = "Virtual Tour";
        tourInfo.onstart = createStartupActionShortcut();
        tourInfo.version = "1.18";
        tourInfo.addTourElement(createStartupAction());
        tourInfo.addTourElement(new IncludeInfo("%SWFPATH%/../skin/vtourskin.xml"));
        tourInfo.addTourElement(getProperties());
        scenes.forEach(tourInfo::addTourElement);
        return tourInfo;
    }

    private static ActionInfo createStartupActionShortcut(){
        ActionInfo action = new ActionInfo();
        action.addActionContent("startup();");
        return action;
    }

    private static ActionInfo createStartupAction(){
        ActionInfo action = new ActionInfo();
        action.name = "startup";
        action.autorun = Autorun.NONE;
        action.secure = false;
        action.addActionContent("if(startscene === null, copy(startscene,scene[0].name));");
        action.addActionContent("loadscene(get(startscene), null, MERGE);");
        return action;
    }

    private static SkinSettingsInfo getProperties(){
        SkinSettingsInfo styleInfo = new SkinSettingsInfo();
        styleInfo.addElement("bingmaps", "true");
        styleInfo.addElement("bingmaps_key", TourProject.getInstance().getBingMapsKey());
        styleInfo.addElement("bingmaps_zoombuttons", "false");
        styleInfo.addElement("thumbs_width", "120");
        styleInfo.addElement("thumbs_height", "80");
        styleInfo.addElement("thumbs_padding", "10");
        styleInfo.addElement("thumbs_crop", "0|40|240|160");
        styleInfo.addElement("thumbs_opened", "false");
        styleInfo.addElement("thumbs_text", "true");
        styleInfo.addElement("thumbs_dragging", "true");
        styleInfo.addElement("thumbs_onhoverscrolling", "false");
        styleInfo.addElement("thumbs_scrollbuttons", "false");
        styleInfo.addElement("thumbs_scrollindicator", "false");
        styleInfo.addElement("thumbs_loop", "false");
        styleInfo.addElement("tooltips_thumbs", "false");
        styleInfo.addElement("tooltips_hotspots", "true");
        styleInfo.addElement("tooltips_mapspots", "false");
        styleInfo.addElement("controlbar_offset", "0");
        return styleInfo;
    }

    public static SceneInfo buildSceneInfo(Pano pano) {
        try {
            SceneInfo sceneInfo = new SceneInfo(pano.getId());
            sceneInfo.title = pano.getName();

            sceneInfo.view = new ViewInfo(pano);
            sceneInfo.image = pano.addPano();
            sceneInfo.lng = pano.getLng();
            sceneInfo.lat = pano.getLat();
            sceneInfo.heading = pano.getHeading();
            sceneInfo.preview = new PreviewInfo("%SWFPATH%/../tiles/" + pano.getTilesFolderString() + "preview.jpg");
            sceneInfo.thumburl = pano.addThumbPano();

            return sceneInfo;
        } catch (IOException e) {
            logger.error(e);
        }
        return null;
    }
}
