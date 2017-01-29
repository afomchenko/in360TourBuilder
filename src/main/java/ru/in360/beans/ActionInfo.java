package ru.in360.beans;

import ru.in360.constants.Autorun;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "action")
public class ActionInfo extends TourElement {
    @XmlAttribute
    public Autorun autorun;
    @XmlAttribute
    public Boolean secure;

    @XmlMixed
    public List<String> content = new ArrayList<>();

    int count = 0;

    public ActionInfo(String name, Autorun autorun, boolean secure) {
        this.name = name;
        this.autorun = autorun;
        this.secure = secure;
    }

    public ActionInfo() {
    }

    public void addActionContent(String actionContent) {
        content.add(actionContent);
    }
}
