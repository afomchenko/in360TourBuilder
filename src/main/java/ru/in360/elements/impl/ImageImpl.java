/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 27.09.17 14:12 Anton Fomchenko 360@in360.ru
 *//*
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



package ru.in360.elements.impl;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ru.in360.elements.ImagePano;

import java.util.ArrayList;
import java.util.List;

public class ImageImpl implements ImagePano {
    private static final long serialVersionUID = 8596033259517472403L;
    List<ImageLevel> levels;
    private ImageType type;
    private String hfov;
    private String vfov;
    private double voffset;
    private boolean multires;
    private double multiresthreshold;
    private boolean progressive;
    private int tilesize;
    private int baseindex;
    private int frames;
    private int frame;
    private String prealign;

    public ImageImpl(ImageType type, boolean multires) {
        this.type = type;
        this.multires = multires;
        levels = new ArrayList<>();
    }

    @Override
    public void addLevel(ImageLevel level) {
        levels.add(level);
    }

    @Override
    public ImageType getType() {
        return type;
    }

    @Override
    public void setType(ImageType type) {
        this.type = type;
    }

    @Override
    public String getHfov() {
        return hfov;
    }

    @Override
    public void setHfov(String hfov) {
        this.hfov = hfov;
    }

    @Override
    public String getVfov() {
        return vfov;
    }

    @Override
    public void setVfov(String vfov) {
        this.vfov = vfov;
    }

    @Override
    public double getVoffset() {
        return voffset;
    }

    @Override
    public void setVoffset(double voffset) {
        this.voffset = voffset;
    }

    @Override
    public boolean isMultires() {
        return multires;
    }

    @Override
    public void setMultires(boolean multires) {
        this.multires = multires;
    }

    @Override
    public double getMultiresthreshold() {
        return multiresthreshold;
    }

    @Override
    public void setMultiresthreshold(double multiresthreshold) {
        this.multiresthreshold = multiresthreshold;
    }

    @Override
    public boolean isProgressive() {
        return progressive;
    }

    @Override
    public void setProgressive(boolean progressive) {
        this.progressive = progressive;
    }

    @Override
    public int getTilesize() {
        return tilesize;
    }

    @Override
    public void setTilesize(int tilesize) {
        this.tilesize = tilesize;
    }

    @Override
    public int getBaseindex() {
        return baseindex;
    }

    @Override
    public void setBaseindex(int baseindex) {
        this.baseindex = baseindex;
    }

    @Override
    public int getFrames() {
        return frames;
    }

    @Override
    public void setFrames(int frames) {
        this.frames = frames;
    }

    @Override
    public int getFrame() {
        return frame;
    }

    @Override
    public void setFrame(int frame) {
        this.frame = frame;
    }

    @Override
    public String getPrealign() {
        return prealign;
    }

    @Override
    public void setPrealign(String prealign) {
        this.prealign = prealign;
    }

    @Override
    public List<ImageLevel> getLevels() {
        return levels;
    }

    @Override
    public void setLevels(List<ImageLevel> levels) {
        this.levels = levels;
    }


    @Override
    public Element getXMLElement(Document doc) {
        Element imageTag = doc.createElement("image");
        if (type != ImageType.NUL) imageTag.setAttribute("type", type.toString());
        imageTag.setAttribute("multires", Boolean.toString(multires));
        imageTag.setAttribute("progressive", Boolean.toString(progressive));
        if (tilesize > 0) imageTag.setAttribute("tilesize", Integer.toString(tilesize));
        if (levels.size() > 0)
            if (multires) {
                for (ImageLevel level : levels) {
                    imageTag.appendChild(level.getXMLElement(doc));
                }
            } else imageTag.appendChild(levels.get(0).getXMLElement(doc));
        return imageTag;
    }
}
