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


import ru.in360.pano.Pano;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class LinkCollection implements Serializable {
    private static final long serialVersionUID = -8277528455461610002L;
    //    private static Lock lock = new ReentrantLock();
//    private static LinkStorage instance;
    Map<LinkPair, Integer> distanceMap;

    public LinkCollection() {
        this.distanceMap = new HashMap<>();
    }

//    public static LinkStorage getInstance() {
//        try {
//            lock.lock();
//            if (instance == null) {
//                instance = new LinkStorage();
//            }
//            return instance;
//        } finally {
//            lock.unlock();
//        }
//    }

    public static void main(String[] args) {
        LinkCollection l = new LinkCollection();

        Pano a = new Pano("pano1");
        a.setLng(0);
        a.setLat(0);
        Pano b = new Pano("pano1");
        b.setLng(5);
        b.setLat(5);
        System.out.println(l.calcDistance(a, b));

    }

    public void addLink(Pano a, Pano b) {
        int distance = (int) calcDistance(a, b);
        distanceMap.put(new LinkPair(a.getId(), b.getId()), distance);
        System.err.println("added link " + a.getId() + " " + b.getId() + " " + distance);
    }

    public void removeLink(Pano a, Pano b) {
        System.err.println("removed link " + a.getId() + " " + b.getId());
        distanceMap.remove(new LinkPair(a.getId(), b.getId()));

    }

    public int getDistance(Pano fromPano, Pano toPano) {
        return distanceMap.get(new LinkPair(fromPano.getId(), toPano.getId()));
    }

//    public List<String> getNearest(Pano pano){
//        List<String> list = new ArrayList<>();
//        distanceMap.get()
//    }

    public double calcDistance(Pano a, Pano b) {
        double R = 6371000;
        double phi1 = Math.toRadians(a.getLat());
        double phi2 = Math.toRadians(b.getLat());
        double deltaPhi = Math.toRadians(b.getLat() - a.getLat());
        double deltaTheta = Math.toRadians(b.getLng() - a.getLng());

        double x = deltaTheta * Math.cos((phi1 + phi2) / 2);
        double y = deltaPhi;
        double d = Math.sqrt(x * x + y * y) * R;
        return d;
    }

    public double calcHeading(Pano a, Pano b) {
        double phi1 = Math.toRadians(a.getLat());
        double phi2 = Math.toRadians(b.getLat());
        double deltaTheta = Math.toRadians(b.getLng() - a.getLng());

        double y = Math.sin(deltaTheta) * Math.cos(phi2);
        double x = Math.cos(phi1) * Math.sin(phi2) -
                Math.sin(phi1) * Math.cos(phi2) * Math.cos(deltaTheta);
        double heading = Math.toDegrees(Math.atan2(y, x));
        return heading;
    }


    private class LinkPair implements Serializable {
        private static final long serialVersionUID = 4296059908416441707L;
        private int a;
        private int b;

        private LinkPair(int a, int b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof LinkPair)) return false;

            LinkPair linkPair = (LinkPair) o;

            if (a == linkPair.a && b == linkPair.b) return true;
            else if (a == linkPair.b && b == linkPair.a) return true;
            else return false;
        }

        @Override
        public int hashCode() {
            int result = a + b;
            return result;
        }
    }
}

