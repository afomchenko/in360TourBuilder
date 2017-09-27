/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 27.09.17 14:12 Anton Fomchenko 360@in360.ru
 */

package ru.in360.elements;


import java.util.List;

public interface Action extends TourElement {
    String getName();

    void setName(String name);

    Autorun getAutorun();

    void setAutorun(Autorun autorun);

    boolean isSecure();

    void setSecure(boolean secure);

    List<String> getContent();

    void setContent(List<String> content);

    int getCount();

    void addActionContent(String actionContent);

    String getActionContentString();

    Action addContentString(String content);

    public enum Autorun {
        NONE(""), PREINIT("preinit"), ONSTART("onstart");
        private String label;

        Autorun(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }
}
