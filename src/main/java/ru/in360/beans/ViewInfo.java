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
 * 06.11.14 1:54 Anton Fomchenko 360@in360.ru
 */

package ru.in360.beans;

import ru.in360.constants.FOVType;
import ru.in360.constants.LimitView;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "view")
public class ViewInfo {

    private Double hlookat;
    private Double vlookat;
    private Double fov;
    private FOVType fovtype = FOVType.MFOV;
    private Double maxpixelzoom;
    private Double fovmin;
    private Double fovmax;
    private LimitView limitview = LimitView.AUTO;

    public ViewInfo() {
        fov = 70D;
        hlookat = 0.0D;
        vlookat = 0.0D;
        maxpixelzoom = 1.0D;
        fovmin = 50D;
        fovmax = 120D;
    }

    public ViewInfo(double hlookat, double vlookat, double fov) {
        this();
        this.hlookat = hlookat;
        this.vlookat = vlookat;
        this.fov = fov;
    }

    @XmlAttribute
    public double getHlookat() {
        return hlookat;
    }

    public void setHlookat(double hlookat) {
        if (hlookat > 180) {
            this.hlookat = hlookat - 360;
        } else if (hlookat < -180) {
            this.hlookat = hlookat + 360;
        } else {
            this.hlookat = hlookat;
        }
    }

    @XmlAttribute
    public double getVlookat() {
        return vlookat;
    }

    public void setVlookat(double vlookat) {
        if (vlookat > 90D) {
            this.vlookat = vlookat - 180D;
        } else if (vlookat < -90D) {
            this.vlookat = vlookat + 180D;
        } else {
            this.vlookat = vlookat;
        }
    }

    @XmlAttribute
    public double getFov() {
        return fov;
    }

    public void setFov(double fov) {
        if (fov > 179D) {
            this.fov = 179D;
        } else {
            this.fov = Math.abs(fov);
        }
    }

    @XmlAttribute
    public FOVType getFovtype() {
        return fovtype;
    }

    public void setFovtype(FOVType fovtype) {
        this.fovtype = fovtype;
    }

    @XmlAttribute
    public double getMaxpixelzoom() {
        return maxpixelzoom;
    }

    public void setMaxpixelzoom(double maxpixelzoom) {
        this.maxpixelzoom = Math.abs(maxpixelzoom);
    }

    @XmlAttribute
    public double getFovmin() {
        return fovmin;
    }

    public void setFovmin(double fovmin) {
        if (fovmin > 179D) {
            this.fovmin = 179D;
        } else {
            this.fovmin = Math.abs(fovmin);
        }
    }

    @XmlAttribute
    public double getFovmax() {
        return fovmax;
    }

    public void setFovmax(double fovmax) {
        if (fovmax > 179D) {
            this.fovmax = 179D;
        } else {
            this.fovmax = Math.abs(fovmax);
        }
    }

    @XmlAttribute
    public LimitView getLimitview() {
        return limitview;
    }

    public void setLimitview(LimitView limitview) {
        this.limitview = limitview;
    }
}
