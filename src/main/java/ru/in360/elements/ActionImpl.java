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

import java.util.ArrayList;
import java.util.List;

public class ActionImpl implements Action {
    private static final long serialVersionUID = -2352185120871649461L;
    private String name;
    private Autorun autorun;
    private boolean secure;
    private List<String> content = new ArrayList<>();
    private int count;

    public ActionImpl(String name) {
        this(name, Autorun.NONE, false);
    }

    public ActionImpl(String name, Autorun autorun, boolean secure) {
        this.name = name;
        this.autorun = autorun;
        this.secure = secure;
        count = 0;
    }

    public ActionImpl() {
        this(null, Autorun.NONE, false);
    }

    public Action addContentString(String content) {
        this.content.add(content);
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Autorun getAutorun() {
        return autorun;
    }

    @Override
    public void setAutorun(Autorun autorun) {
        this.autorun = autorun;
    }

    @Override
    public boolean isSecure() {
        return secure;
    }

    @Override
    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    @Override
    public List<String> getContent() {
        return content;
    }

    @Override
    public void setContent(List<String> content) {
        this.content = content;
        count++;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public void addActionContent(String actionContent) {
        content.add(actionContent);
    }

    @Override
    public String getActionContentString() {
        if (content.size() < 1) return "";
        StringBuilder actionBuilder = new StringBuilder("");
        for (String actionContent : content) {
            actionBuilder.append(actionContent);
            if (!actionContent.trim().endsWith(";")) actionBuilder.append(";");
//            actionBuilder.append("\n");
        }
        return actionBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActionImpl actionImpl = (ActionImpl) o;

        if (content != null ? !content.equals(actionImpl.content) : actionImpl.content != null) return false;
        if (!name.equals(actionImpl.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        if (content.size() < 1) return "";
        StringBuilder actionBuilder = new StringBuilder("<action name=\"").append(name).append('\"');
        if (secure != false)
            actionBuilder.append(" secure=\"").append(secure).append('\"');
        if (autorun != Autorun.NONE)
            actionBuilder.append(" autorun=\"").append(autorun.getLabel()).append('\"');
        actionBuilder.append(">\n");
        for (String actionContent : content) {
            actionBuilder.append(actionContent);
            if (!actionContent.trim().endsWith(";")) actionBuilder.append(";");
            actionBuilder.append("\n");
        }
        return actionBuilder.append("</action>\n").toString();
    }

    @Override
    public Element getXMLElement(Document doc) {
        Element actionTag = doc.createElement("action");
        actionTag.setAttribute("name", name);
        if (secure != false)
            actionTag.setAttribute("secure", Boolean.valueOf(secure).toString());
        if (autorun != Autorun.NONE)
            actionTag.setAttribute("autorun", autorun.getLabel());
        for (String actionContent : content) {
            actionTag.setTextContent(getActionContentString());
        }
        return actionTag;
    }
}
