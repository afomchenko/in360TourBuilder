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

package ru.in360.elements;


import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class LayerImpl implements Layer {
    private static final long serialVersionUID = -5708757875213590946L;
    boolean keep;
    private String name;
    private LayerType type = LayerType.IMAGE;
    private Align align;
    private String width;
    private String height;
    private int x;
    private int y;
    private int y_opened;
    private int y_closed;
    private int x_opened;
    private int x_closed;
    private boolean maskchildren;
    private Style style;
    private String layer;
    private boolean visible;
    private double alpha;
    private String backgroundalpha;
    private boolean background;
    private String backgroundcolor;
    private String url;
    private double scale;

    private String alturl;
    private String devices;
    private String parent;
    private boolean enabled;
    private boolean handcursor;
    private boolean scalechildren;
    private int zorder;
    private Align edge;
    private double rotate;

    private String crop;
    private String onovercrop;
    private String ondowncrop;

    private String bgcolor;
    private double bgalpha;

    private String bgborder;
    private String bgroundedge;
    private String bgshadow;
    private boolean bgcapture;

    private Action onover;
    private Action onhover;
    private Action onout;
    private Action onclick;
    private Action ondown;
    private Action onup;
    private Action onloaded;

    public LayerImpl() {
    }

    public LayerImpl(String name) {
        this.name = name;
        this.scale = 1;

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public LayerType getType() {
        return type;
    }

    @Override
    public void setType(LayerType type) {
        this.type = type;
    }

    @Override
    public boolean isKeep() {
        return keep;
    }

    @Override
    public void setKeep(boolean keep) {
        this.keep = keep;
    }

    @Override
    public Align getAlign() {
        return align;
    }

    @Override
    public void setAlign(Align align) {
        this.align = align;
    }

    @Override
    public String getWidth() {
        return width;
    }

    @Override
    public void setWidth(String width) {
        this.width = width;
    }

    @Override
    public String getHeight() {
        return height;
    }

    @Override
    public void setHeight(String height) {
        this.height = height;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int getY_opened() {
        return y_opened;
    }

    @Override
    public void setY_opened(int y_opened) {
        this.y_opened = y_opened;
    }

    @Override
    public int getY_closed() {
        return y_closed;
    }

    @Override
    public void setY_closed(int y_closed) {
        this.y_closed = y_closed;
    }

    @Override
    public int getX_opened() {
        return x_opened;
    }

    @Override
    public void setX_opened(int x_opened) {
        this.x_opened = x_opened;
    }

    @Override
    public int getX_closed() {
        return x_closed;
    }

    @Override
    public void setX_closed(int x_closed) {
        this.x_closed = x_closed;
    }

    @Override
    public boolean isMaskchildren() {
        return maskchildren;
    }

    @Override
    public void setMaskchildren(boolean maskchildren) {
        this.maskchildren = maskchildren;
    }

    @Override
    public Style getStyle() {
        return style;
    }

    @Override
    public void setStyle(Style style) {
        this.style = style;
    }

    @Override
    public String getLayer() {
        return layer;
    }

    @Override
    public void setLayer(String layer) {
        this.layer = layer;
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public double getAlpha() {
        return alpha;
    }

    @Override
    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    @Override
    public String getBackgroundalpha() {
        return backgroundalpha;
    }

    @Override
    public void setBackgroundalpha(String backgroundalpha) {
        this.backgroundalpha = backgroundalpha;
    }

    @Override
    public boolean isBackground() {
        return background;
    }

    @Override
    public void setBackground(boolean background) {
        this.background = background;
    }

    @Override
    public String getBackgroundcolor() {
        return backgroundcolor;
    }

    @Override
    public void setBackgroundcolor(String backgroundcolor) {
        this.backgroundcolor = backgroundcolor;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public double getScale() {
        return scale;
    }

    @Override
    public void setScale(double scale) {
        this.scale = scale;
    }

    @Override
    public String getAlturl() {
        return alturl;
    }

    @Override
    public void setAlturl(String alturl) {
        this.alturl = alturl;
    }

    @Override
    public String getDevices() {
        return devices;
    }

    @Override
    public void setDevices(String devices) {
        this.devices = devices;
    }

    @Override
    public String getParent() {
        return parent;
    }

    @Override
    public void setParent(String parent) {
        this.parent = parent;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean isHandcursor() {
        return handcursor;
    }

    @Override
    public void setHandcursor(boolean handcursor) {
        this.handcursor = handcursor;
    }

    @Override
    public boolean isScalechildren() {
        return scalechildren;
    }

    @Override
    public void setScalechildren(boolean scalechildren) {
        this.scalechildren = scalechildren;
    }

    @Override
    public int getZorder() {
        return zorder;
    }

    @Override
    public void setZorder(int zorder) {
        this.zorder = zorder;
    }

    @Override
    public Align getEdge() {
        return edge;
    }

    @Override
    public void setEdge(Align edge) {
        this.edge = edge;
    }

    @Override
    public double getRotate() {
        return rotate;
    }

    @Override
    public void setRotate(double rotate) {
        this.rotate = rotate;
    }

    @Override
    public String getCrop() {
        return crop;
    }

    @Override
    public void setCrop(String crop) {
        this.crop = crop;
    }

    @Override
    public String getOnovercrop() {
        return onovercrop;
    }

    @Override
    public void setOnovercrop(String onovercrop) {
        this.onovercrop = onovercrop;
    }

    @Override
    public String getOndowncrop() {
        return ondowncrop;
    }

    @Override
    public void setOndowncrop(String ondowncrop) {
        this.ondowncrop = ondowncrop;
    }

    @Override
    public String getBgcolor() {
        return bgcolor;
    }

    @Override
    public void setBgcolor(String bgcolor) {
        this.bgcolor = bgcolor;
    }

    @Override
    public double getBgalpha() {
        return bgalpha;
    }

    @Override
    public void setBgalpha(double bgalpha) {
        this.bgalpha = bgalpha;
    }

    @Override
    public String getBgborder() {
        return bgborder;
    }

    @Override
    public void setBgborder(String bgborder) {
        this.bgborder = bgborder;
    }

    @Override
    public String getBgroundedge() {
        return bgroundedge;
    }

    @Override
    public void setBgroundedge(String bgroundedge) {
        this.bgroundedge = bgroundedge;
    }

    @Override
    public String getBgshadow() {
        return bgshadow;
    }

    @Override
    public void setBgshadow(String bgshadow) {
        this.bgshadow = bgshadow;
    }

    @Override
    public boolean isBgcapture() {
        return bgcapture;
    }

    @Override
    public void setBgcapture(boolean bgcapture) {
        this.bgcapture = bgcapture;
    }

    @Override
    public Action getOnover() {
        return onover;
    }

    @Override
    public void setOnover(Action onover) {
        this.onover = onover;
    }

    @Override
    public Action getOnhover() {
        return onhover;
    }

    @Override
    public void setOnhover(Action onhover) {
        this.onhover = onhover;
    }

    @Override
    public Action getOnout() {
        return onout;
    }

    @Override
    public void setOnout(Action onout) {
        this.onout = onout;
    }

    @Override
    public Action getOnclick() {
        return onclick;
    }

    @Override
    public void setOnclick(Action onclick) {
        this.onclick = onclick;
    }

    @Override
    public Action getOndown() {
        return ondown;
    }

    @Override
    public void setOndown(Action ondown) {
        this.ondown = ondown;
    }

    @Override
    public Action getOnup() {
        return onup;
    }

    @Override
    public void setOnup(Action onup) {
        this.onup = onup;
    }

    @Override
    public Action getOnloaded() {
        return onloaded;
    }

    @Override
    public void setOnloaded(Action onloaded) {
        this.onloaded = onloaded;
    }


    @Override
    public Element getXMLElement(Document doc) {
        Element layerTag = doc.createElement("layer");
        layerTag.setAttribute("name", this.name);
        if (url != null) layerTag.setAttribute("url", url);
        layerTag.setAttribute("scale", Double.toString(scale));
        if (onclick != null) layerTag.setAttribute("onclick", onclick.getActionContentString());
        if (ondown != null) layerTag.setAttribute("ondown", ondown.getActionContentString());
        if (onhover != null) layerTag.setAttribute("onhover", onhover.getActionContentString());
        if (onloaded != null) layerTag.setAttribute("onloaded", onloaded.getActionContentString());
        if (onout != null) layerTag.setAttribute("onout", onout.getActionContentString());
        if (onover != null) layerTag.setAttribute("onover", onover.getActionContentString());
        if (onup != null) layerTag.setAttribute("onup", onup.getActionContentString());

        return layerTag;
    }
}
