/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 27.09.17 14:12 Anton Fomchenko 360@in360.ru
 */

package ru.in360.elements;

public interface Layer extends TourElement {
    String getName();

    void setName(String name);

    LayerType getType();

    void setType(LayerType type);

    boolean isKeep();

    void setKeep(boolean keep);

    Align getAlign();

    void setAlign(Align align);

    String getWidth();

    void setWidth(String width);

    String getHeight();

    void setHeight(String height);

    int getX();

    void setX(int x);

    int getY();

    void setY(int y);

    int getY_opened();

    void setY_opened(int y_opened);

    int getY_closed();

    void setY_closed(int y_closed);

    int getX_opened();

    void setX_opened(int x_opened);

    int getX_closed();

    void setX_closed(int x_closed);

    boolean isMaskchildren();

    void setMaskchildren(boolean maskchildren);

    Style getStyle();

    void setStyle(Style style);

    String getLayer();

    void setLayer(String layer);

    boolean isVisible();

    void setVisible(boolean visible);

    double getAlpha();

    void setAlpha(double alpha);

    String getBackgroundalpha();

    void setBackgroundalpha(String backgroundalpha);

    boolean isBackground();

    void setBackground(boolean background);

    String getBackgroundcolor();

    void setBackgroundcolor(String backgroundcolor);

    String getUrl();

    void setUrl(String url);

    double getScale();

    void setScale(double scale);

    String getAlturl();

    void setAlturl(String alturl);

    String getDevices();

    void setDevices(String devices);

    String getParent();

    void setParent(String parent);

    boolean isEnabled();

    void setEnabled(boolean enabled);

    boolean isHandcursor();

    void setHandcursor(boolean handcursor);

    boolean isScalechildren();

    void setScalechildren(boolean scalechildren);

    int getZorder();

    void setZorder(int zorder);

    Align getEdge();

    void setEdge(Align edge);

    double getRotate();

    void setRotate(double rotate);

    String getCrop();

    void setCrop(String crop);

    String getOnovercrop();

    void setOnovercrop(String onovercrop);

    String getOndowncrop();

    void setOndowncrop(String ondowncrop);

    String getBgcolor();

    void setBgcolor(String bgcolor);

    double getBgalpha();

    void setBgalpha(double bgalpha);

    String getBgborder();

    void setBgborder(String bgborder);

    String getBgroundedge();

    void setBgroundedge(String bgroundedge);

    String getBgshadow();

    void setBgshadow(String bgshadow);

    boolean isBgcapture();

    void setBgcapture(boolean bgcapture);

    Action getOnover();

    void setOnover(Action onover);

    Action getOnhover();

    void setOnhover(Action onhover);

    Action getOnout();

    void setOnout(Action onout);

    Action getOnclick();

    void setOnclick(Action onclick);

    Action getOndown();

    void setOndown(Action ondown);

    Action getOnup();

    void setOnup(Action onup);

    Action getOnloaded();

    void setOnloaded(Action onloaded);

    public enum LayerType {
        IMAGE("image"), CONTAINER("container");
        private String label;

        LayerType(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    public enum Align {
        NONE(""), LEFTTOP("lefttop"), LEFT("left"), LEFTBOTTOM("leftbottom"), TOP("top"),
        CENTER("center"), BOTTOM("bottom"), RIGHTTOP("righttop"), RIGHT("right"), RIGHTBOTTOM("rightbottom");
        private String label;

        Align(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }
}
