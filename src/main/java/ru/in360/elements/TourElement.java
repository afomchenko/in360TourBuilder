/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 27.09.17 14:12 Anton Fomchenko 360@in360.ru
 */

package ru.in360.elements;


import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.Serializable;

public interface TourElement extends Serializable {
    Element getXMLElement(Document doc);
}
