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
 * 06.11.14 1:54 Anton Fomchenko 360@in360.ru
 */

package ru.in360.elements.impl;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ru.in360.elements.View;

public class ViewImpl implements View {
    private static final long serialVersionUID = -669125992556407306L;
    private double hlookat;
    private double vlookat;
    private double fov;
    private FOVType fovtype = FOVType.MFOV;
    private double maxpixelzoom;
    private double fovmin;
    private double fovmax;
    private LimitView limitview = LimitView.AUTO;

    public ViewImpl() {
        fov = 70D;
        hlookat = 0.0D;
        vlookat = 0.0D;
        maxpixelzoom = 1.0D;
        fovmin = 50D;
        fovmax = 120D;
    }

    public ViewImpl(double hlookat, double vlookat, double fov) {
        this();
        this.hlookat = hlookat;
        this.vlookat = vlookat;
        this.fov = fov;
    }

    @Override
    public double getHlookat() {
        return hlookat;
    }

    @Override
    public void setHlookat(double hlookat) {
        if (hlookat > 180) this.hlookat = hlookat - 360;
        else if (hlookat < -180) this.hlookat = hlookat + 360;
        else this.hlookat = hlookat;
    }

    @Override
    public double getVlookat() {
        return vlookat;
    }

    @Override
    public void setVlookat(double vlookat) {
        if (vlookat > 90D) this.vlookat = vlookat - 180D;
        else if (vlookat < -90D) this.vlookat = vlookat + 180D;
        else this.vlookat = vlookat;
    }

    @Override
    public double getFov() {
        return fov;
    }

    @Override
    public void setFov(double fov) {
        if (fov > 179)
            this.fov = 179;
        else
            this.fov = Math.abs(fov);
    }

    @Override
    public FOVType getFovtype() {
        return fovtype;
    }

    @Override
    public void setFovtype(FOVType fovtype) {
        this.fovtype = fovtype;
    }

    @Override
    public double getMaxpixelzoom() {
        return maxpixelzoom;
    }

    @Override
    public void setMaxpixelzoom(double maxpixelzoom) {
        this.maxpixelzoom = Math.abs(maxpixelzoom);
    }

    @Override
    public double getFovmin() {
        return fovmin;
    }

    @Override
    public void setFovmin(double fovmin) {
        if (fovmin > 179D)
            this.fovmin = 179D;
        else
            this.fovmin = Math.abs(fovmin);
    }

    @Override
    public double getFovmax() {
        return fovmax;
    }

    @Override
    public void setFovmax(double fovmax) {
        if (fovmax > 179D)
            this.fovmax = 179D;
        else
            this.fovmax = Math.abs(fovmax);
    }

    @Override
    public LimitView getLimitview() {
        return limitview;
    }

    @Override
    public void setLimitview(LimitView limitview) {
        this.limitview = limitview;
    }

    @Override
    public Element getXMLElement(Document doc) {
        Element viewTag = doc.createElement("view");
        viewTag.setAttribute("hlookat", Double.toString(hlookat));
        viewTag.setAttribute("vlookat", Double.toString(vlookat));
        viewTag.setAttribute("fovtype", fovtype.toString());
        viewTag.setAttribute("fov", Double.toString(fov));
        viewTag.setAttribute("maxpixelzoom", Double.toString(maxpixelzoom));
        viewTag.setAttribute("fovmin", Double.toString(fovmin));
        viewTag.setAttribute("fovmax", Double.toString(fovmax));
        viewTag.setAttribute("limitview", limitview.toString());
        return viewTag;
    }
}
