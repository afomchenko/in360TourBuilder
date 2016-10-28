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
import ru.in360.elements.ImageElement;
import ru.in360.elements.TourElement;

public class ImageLevel implements TourElement {
    private static final long serialVersionUID = -4285373815881176274L;
    private int tiledimagewidth;
    private int tiledimageheight;
    private ImageElement imageElement;


    public ImageLevel(int tiledimagewidth, int tiledimageheight) {
        this.tiledimagewidth = tiledimagewidth;
        this.tiledimageheight = tiledimageheight;
    }

    public ImageLevel(int tiledimagewidth) {
        this.tiledimagewidth = tiledimagewidth;
        this.tiledimageheight = tiledimagewidth;
    }

    public ImageLevel() {
    }

    public void addImageElement(ImageElement element) {
        imageElement = element;
    }

    @Override
    public Element getXMLElement(Document doc) {
        Element levelTag = doc.createElement("level");
        levelTag.setAttribute("tiledimagewidth", Integer.toString(tiledimagewidth));
        levelTag.setAttribute("tiledimageheight", Integer.toString(tiledimageheight));
        if (imageElement != null)
            levelTag.appendChild(imageElement.getXMLElement(doc));
        return levelTag;
    }

    public ImageElement getImageElement() {
        return imageElement;
    }

    public void setImageElement(ImageElement imageElement) {
        this.imageElement = imageElement;
    }

    public int getTiledimagewidth() {
        return tiledimagewidth;
    }

    public void setTiledimagewidth(int tiledimagewidth) {
        this.tiledimagewidth = tiledimagewidth;
    }

    public int getTiledimageheight() {
        return tiledimageheight;
    }

    public void setTiledimageheight(int tiledimageheight) {
        this.tiledimageheight = tiledimageheight;
    }
}
