package ru.in360.adapters;

import ru.in360.beans.ActionInfo;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Arrays;


public class ActionAsAttributeAdapter extends XmlAdapter<String, ActionInfo> {

    @Override
    public ActionInfo unmarshal(String v) throws Exception {
        ActionInfo action = new ActionInfo();
        Arrays.stream(v.split(" ")).forEach(action::addActionContent);
        return action;
    }

    @Override
    public String marshal(ActionInfo v) throws Exception {
        if(v==null){
            return null;
        }
        if (v.content.size() < 1) {
            return "";
        } else {
            return String.join(" ", v.content);
        }
    }
}
