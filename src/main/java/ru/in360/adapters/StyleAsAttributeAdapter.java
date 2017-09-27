/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 27.09.17 14:12 Anton Fomchenko 360@in360.ru
 */

package ru.in360.adapters;

import ru.in360.beans.StyleInfo;

import javax.xml.bind.annotation.adapters.XmlAdapter;


public class StyleAsAttributeAdapter extends XmlAdapter<String, StyleInfo> {

    @Override
    public StyleInfo unmarshal(String v) throws Exception {
        StyleInfo style = new StyleInfo();
        style.setName(v);
        return style;
    }

    @Override
    public String marshal(StyleInfo v) throws Exception {
        return v!=null ? v.getName() : null;
    }
}
