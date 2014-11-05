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

import java.util.List;


public interface ImagePano extends TourElement {
    void addLevel(ImageLevel level);

    ImageType getType();

    void setType(ImageType type);

    String getHfov();

    void setHfov(String hfov);

    String getVfov();

    void setVfov(String vfov);

    double getVoffset();

    void setVoffset(double voffset);

    boolean isMultires();

    void setMultires(boolean multires);

    double getMultiresthreshold();

    void setMultiresthreshold(double multiresthreshold);

    boolean isProgressive();

    void setProgressive(boolean progressive);

    int getTilesize();

    void setTilesize(int tilesize);

    int getBaseindex();

    void setBaseindex(int baseindex);

    int getFrames();

    void setFrames(int frames);

    int getFrame();

    void setFrame(int frame);

    String getPrealign();

    void setPrealign(String prealign);

    List<ImageLevel> getLevels();

    void setLevels(List<ImageLevel> levels);

    public enum ImageType {CUBE, CUBESTRIP, CYLINDER, SPHERE, NUL;}
}
