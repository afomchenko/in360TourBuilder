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

public class HotspotImage implements Hotspot {
    private static final long serialVersionUID = -5728294974663739125L;
    private static int hotspotCount = 0;
    private String name;
    private Style style;
    private double ath;
    private double atv;
    private String linkedscene;
    private double hview;
    private double vview;
    private double fovview;
    private double hcenter;
    private double vcenter;

    public HotspotImage(String linkedscene, double ath, double atv) {
        this.name = "spot_" + hotspotCount++;
        this.ath = ath;
        this.atv = atv;
        this.linkedscene = linkedscene;
        this.style = new StyleImpl("skin_hotspotstyle");
    }

    public HotspotImage(String name, String linkedscene, double ath, double atv) {
        this.name = name;
        this.ath = ath;
        this.atv = atv;
        this.linkedscene = linkedscene;
        this.style = new StyleImpl("skin_hotspotstyle");
    }

    public static int getHotspotCount() {
        return hotspotCount;
    }

    public static void setHotspotCount(int hotspotCount) {
        HotspotImage.hotspotCount = hotspotCount;
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
    public double getAth() {
        return ath;
    }

    @Override
    public void setAth(double ath) {
        this.ath = ath;
    }

    @Override
    public double getAtv() {
        return atv;
    }

    @Override
    public void setAtv(double atv) {
        this.atv = atv;
    }


    @Override
    public double getHview() {
        return hview;
    }

    @Override
    public void setHview(double hview) {
        this.hview = hview;
    }

    @Override
    public double getVview() {
        return vview;
    }

    @Override
    public void setVview(double vview) {
        this.vview = vview;
    }

    @Override
    public double getFovview() {
        return fovview;
    }

    @Override
    public void setFovview(double fovview) {
        this.fovview = fovview;
    }

    @Override
    public double getHcenter() {
        return hcenter;
    }

    @Override
    public void setHcenter(double hcenter) {
        this.hcenter = hcenter;
    }

    @Override
    public double getVcenter() {
        return vcenter;
    }

    @Override
    public void setVcenter(double vcenter) {
        this.vcenter = vcenter;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HotspotImage that = (HotspotImage) o;

        if (linkedscene != null ? !linkedscene.equals(that.linkedscene) : that.linkedscene != null) return false;
        if (!name.equals(that.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public Element getXMLElement(Document doc) {
        Element spotTag = doc.createElement("hotspot");
        spotTag.setAttribute("name", name);
        if (style != null)
            spotTag.setAttribute("style", style.getName());
        spotTag.setAttribute("ath", Double.toString(ath));
        spotTag.setAttribute("atv", Double.toString(atv));
        if (linkedscene != null)
            spotTag.setAttribute("linkedscene", linkedscene);
        if (hview != 0)
            spotTag.setAttribute("hview", Double.toString(hview));
        if (vview != 0)
            spotTag.setAttribute("vview", Double.toString(vview));
        if (fovview != 0)
            spotTag.setAttribute("fovview", Double.toString(fovview));
        if (hcenter != 0)
            spotTag.setAttribute("hcenter", Double.toString(hcenter));
        if (vcenter != 0)
            spotTag.setAttribute("vcenter", Double.toString(vcenter));

        return spotTag;
    }
}
