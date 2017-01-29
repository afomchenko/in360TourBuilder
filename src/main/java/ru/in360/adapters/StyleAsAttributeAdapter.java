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
