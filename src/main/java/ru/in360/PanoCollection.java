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



package ru.in360;

import ru.in360.elements.Scene;
import ru.in360.elements.impl.HotspotImage;
import ru.in360.pano.Pano;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PanoCollection implements Serializable {
    private static final long serialVersionUID = 3724987692157543087L;
    private Map<Integer, Pano> panoramas;
    private LinkCollection linkStorage;// = LinkStorage.getInstance();

    public PanoCollection() {
        panoramas = new HashMap<>();
        linkStorage = new LinkCollection();
    }

    public Collection<Pano> getPanoramas() {
        return panoramas.values();
    }

    public LinkCollection getLinkStorage() {
        return linkStorage;
    }

    public void addPano(Pano pano) {
        addLinks(pano);
        panoramas.put(pano.getId(), pano);
    }

    public void addLinks(Pano newPano) {
        for (Pano pano : panoramas.values()) {
            linkStorage.addLink(newPano, pano);
        }
    }

    public void updateLinks() {
        for (Pano pano : panoramas.values()) {
            addLinks(pano);
        }
    }

    public void deletePano(Pano deletedPano) {
        panoramas.remove(deletedPano.getId());
        deletedPano.clear();
        for (Pano pano : panoramas.values()) {
            linkStorage.removeLink(deletedPano, pano);
        }
        deletedPano = null;
    }


    public Map<Integer, Integer> getNearest(Pano pano) {
        Map<Integer, Integer> nearest = new TreeMap<>();
        for (Pano otherPano : panoramas.values()) {
            if (pano == otherPano) continue;
            int dist = linkStorage.getDistance(pano, otherPano);
            nearest.put(dist, otherPano.getId());
        }
        return nearest;
    }

    public List<Scene> getScenes() {
        List<Scene> list = new ArrayList<>();

        for (Pano pano : panoramas.values()) {
            Scene sc = pano.getScene();
            Map<Integer, Integer> nearestPanos = getNearest(pano);

            sc.getSceneHotspots().clear();

            int i = 0;
            for (Map.Entry<Integer, Integer> entry : nearestPanos.entrySet()) {
                if (i > 4) break;
                System.err.println("add hotspot " + entry.getValue() + ": " + entry.getKey());

                sc.addHotspot(new HotspotImage(panoramas.get(entry.getValue()).getName() + ": " + entry.getKey() + "m",
                        "scene_" + entry.getValue(), linkStorage.calcHeading(pano, panoramas.get(entry.getValue())) + pano.getHeading() - 90, 10));

                i++;
            }
            list.add(pano.getScene());
        }
        return list;
    }

}
