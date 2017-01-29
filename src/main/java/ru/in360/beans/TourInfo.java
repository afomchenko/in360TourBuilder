package ru.in360.beans;

import ru.in360.adapters.ActionAsAttributeAdapter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

@XmlRootElement(name = "krpano")
public class TourInfo {
    @XmlAttribute
    public String title;
    @XmlAttribute
    public String version;
    @XmlAttribute(name = "onstart")
    @XmlJavaTypeAdapter(ActionAsAttributeAdapter.class)
    public ActionInfo onstart;


    @XmlElementRefs({
            @XmlElementRef(name="include", type=IncludeInfo.class),
            @XmlElementRef(name="skin_settings", type=SkinSettingsInfo.class),
            @XmlElementRef(name="action", type=ActionInfo.class),
            @XmlElementRef(name="layer", type=LayerInfo.class),
            @XmlElementRef(name="scene", type=SceneInfo.class)
    })
    @XmlMixed
    public List<TourElement> elements;
}
