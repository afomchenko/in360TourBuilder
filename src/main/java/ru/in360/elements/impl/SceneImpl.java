/*
 * This file is part of in360TourBuilder.
 *
 *     in360TourBuilder is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     in360TourBuilder is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Этот файл — часть in360TourBuilder.
 *
 *    in360TourBuilder - свободная программа: вы можете перераспространять ее и/или
 *    изменять ее на условиях Стандартной общественной лицензии GNU в том виде,
 *    в каком она была опубликована Фондом свободного программного обеспечения;
 *    либо версии 3 лицензии, либо (по вашему выбору) любой более поздней
 *    версии.
 *
 *    in360TourBuilder распространяется в надежде, что она будет полезной,
 *    но БЕЗО ВСЯКИХ ГАРАНТИЙ; даже без неявной гарантии ТОВАРНОГО ВИДА
 *    или ПРИГОДНОСТИ ДЛЯ ОПРЕДЕЛЕННЫХ ЦЕЛЕЙ. Подробнее см. в Стандартной
 *    общественной лицензии GNU.
 *
 *    Вы должны были получить копию Стандартной общественной лицензии GNU
 *    вместе с этой программой. Если это не так, см.
 *    <http://www.gnu.org/licenses/>.
 *
 * 29.10.16 1:08 Anton Fomchenko 360@in360.ru
 */

/*
 * This file is part of in360TourBuilder.
 *
 *     in360TourBuilder is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     in360TourBuilder is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Этот файл — часть in360TourBuilder.
 *
 *    in360TourBuilder - свободная программа: вы можете перераспространять ее и/или
 *    изменять ее на условиях Стандартной общественной лицензии GNU в том виде,
 *    в каком она была опубликована Фондом свободного программного обеспечения;
 *    либо версии 3 лицензии, либо (по вашему выбору) любой более поздней
 *    версии.
 *
 *    in360TourBuilder распространяется в надежде, что она будет полезной,
 *    но БЕЗО ВСЯКИХ ГАРАНТИЙ; даже без неявной гарантии ТОВАРНОГО ВИДА
 *    или ПРИГОДНОСТИ ДЛЯ ОПРЕДЕЛЕННЫХ ЦЕЛЕЙ. Подробнее см. в Стандартной
 *    общественной лицензии GNU.
 *
 *    Вы должны были получить копию Стандартной общественной лицензии GNU
 *    вместе с этой программой. Если это не так, см.
 *    <http://www.gnu.org/licenses/>.
 *
 * 06.11.14 1:47 Anton Fomchenko 360@in360.ru
 */

package ru.in360.elements.impl;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ru.in360.elements.Action;
import ru.in360.elements.Hotspot;
import ru.in360.elements.ImagePano;
import ru.in360.elements.Preview;
import ru.in360.elements.Scene;
import ru.in360.elements.View;

import java.util.HashMap;
import java.util.Map;

public class SceneImpl implements Scene {
    private static final long serialVersionUID = 5954533813150073485L;
    private final String name;
    private String title;
    private Action onstart;
    private String thumburl;
    private double lat;
    private double lng;
    private double heading;
    private double headingOffset;
    private View view;
    private Preview preview;
    private ImagePano image;
    private Map<String, Hotspot> sceneHotspots;
    private Map<String, Hotspot> incomingHotspots;

    public SceneImpl(String title, int id) {
        this.title = title;
        name = "scene_" + id;
        sceneHotspots = new HashMap<>();
        incomingHotspots = new HashMap<>();
    }

    public double getHeadingOffset() {
        return headingOffset;
    }

    public void setHeadingOffset(double headingOffset) {
        this.headingOffset = headingOffset;
    }

    public double getHeading() {
        return heading;
    }

    public void setHeading(double heading) {
        this.heading = heading;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Action getOnstart() {
        return onstart;
    }

    @Override
    public void setOnstart(Action onstart) {
        this.onstart = onstart;
    }

    @Override
    public String getThumburl() {
        return thumburl;
    }

    @Override
    public void setThumburl(String thumburl) {
        this.thumburl = thumburl;
    }

    @Override
    public View getView() {
        return view;
    }

    @Override
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public Preview getPreview() {
        return preview;
    }

    @Override
    public void setPreview(Preview preview) {
        this.preview = preview;
    }

    @Override
    public ImagePano getImage() {
        return image;
    }

    @Override
    public void setImage(ImagePano image) {
        this.image = image;
    }

    @Override
    public Map<String, Hotspot> getSceneHotspots() {
        return sceneHotspots;
    }

    @Override
    public void addHotspot(Hotspot hotspot) {
        this.sceneHotspots.put(hotspot.getName(), hotspot);
    }

    @Override
    public void removeIncomingHotspot(String nameFrom) {
        this.incomingHotspots.remove(nameFrom);
    }

    @Override
    public void registerIncomingHotspots(String nameFrom, Hotspot hotspot) {
        this.incomingHotspots.put(nameFrom, hotspot);
    }

    @Override
    public void setCoordinates(double lng, double lat, double heading) {
        this.lat = lat;
        this.lng = lng;
        this.heading = heading;
    }

    @Override
    public Element getXMLElement(Document doc) {
        Element sceneTag = doc.createElement("scene");
        sceneTag.setAttribute("name", name);
        if (title != null) {
            sceneTag.setAttribute("title", title);
        }
        if (onstart != null) {
            sceneTag.setAttribute("onstart", onstart.getActionContentString());
        }
        if (thumburl != null) {
            sceneTag.setAttribute("thumburl", thumburl);
        }
        if (lat != 0) {
            sceneTag.setAttribute("lat", Double.toString(lat));
            sceneTag.setAttribute("lng", Double.toString(lng));
            sceneTag.setAttribute("heading", Double.toString(heading));
            sceneTag.setAttribute("headingoffset", Double.toString(360D - heading));
        }
        if (view != null) {
            sceneTag.appendChild(view.getXMLElement(doc));
        }
        if (preview != null) {
            sceneTag.appendChild(preview.getXMLElement(doc));
        }
        if (image != null) {
            sceneTag.appendChild(image.getXMLElement(doc));
        }
        if (sceneHotspots.size() > 0) {
            for (Hotspot hotspot : sceneHotspots.values()) {
                sceneTag.appendChild(hotspot.getXMLElement(doc));
            }
        }
        return sceneTag;
    }
}

