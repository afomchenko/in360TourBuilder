/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 27.09.17 14:12 Anton Fomchenko 360@in360.ru
 */

package ru.in360.elements;

public interface Style extends TourElement {
    String getName();

    void setName(String name);

    void addElement(String name, String element);

    void removeElement(String name);
}
