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


public class SceneTemplates {
    public static final String htmlPreview = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "\t<title>Virtual Tour</title>\n" +
            "\t<meta name=\"viewport\" content=\"target-densitydpi=device-dpi, width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, minimal-ui\" />\n" +
            "\t<meta name=\"apple-mobile-web-app-capable\" content=\"yes\" />\n" +
            "\t<meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\" />\n" +
            "\t<meta http-equiv=\"x-ua-compatible\" content=\"IE=edge\" />\n" +
            "\t<style>\n" +
            "\t\t@-ms-viewport { width: device-width; }\n" +
            "\t\t@media only screen and (min-device-width: 800px) { html { overflow:hidden; } }\n" +
            "\t\thtml { height:100%; }\n" +
            "\t\tbody { height:100%; overflow:hidden; margin:0; padding:0; font-family:Arial, Helvetica, sans-serif; font-size:16px; color:#FFFFFF; background-color:#000000; }\n" +
            "\t</style>\n" +
            "</head>\n" +
            "<body>\n" +
            "\n" +
            "<script src=\"../viewer/krpano.js\"></script>\n" +
            "\n" +
            "<div id=\"pano\" style=\"width:100%;height:100%;\">\n" +
            "\t<noscript><table style=\"width:100%;height:100%;\"><tr style=\"vertical-align:middle;\"><td><div style=\"text-align:center;\">ERROR:<br/><br/>Javascript not activated<br/><br/></div></td></tr></table></noscript>\n" +
            "\t<script>\n" +
            "\t\tembedpano({swf:\"../viewer/krpano.swf\", xml:\"krpano.xml\", target:\"pano\", html5:\"auto\", passQueryParameters:true});\n" +
            "\t</script>\n" +
            "</div>\n" +
            "\n" +
            "</body>\n" +
            "</html>";

    public static final String htmlBuild = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "\t<title>in360 | %TITLE%</title>\n" +
            "\t<meta name=\"viewport\" content=\"target-densitydpi=device-dpi, width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0\" />\n" +
            "\t<meta name=\"apple-mobile-web-app-capable\" content=\"yes\" />\n" +
            "\t<meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\" />\n" +
            "\t<meta http-equiv=\"x-ua-compatible\" content=\"IE=edge\" />\n" +
            "\t<style>\n" +
            "\t\t@-ms-viewport { width:device-width; }\n" +
            "\t\t@media only screen and (min-device-width:800px) { html { overflow:hidden; } }\n" +
            "\t\thtml { height:100%; }\n" +
            "\t\tbody { height:100%; overflow:hidden; margin:0; padding:0; font-family:Arial, Helvetica, sans-serif; font-size:16px; color:#FFFFFF; background-color:#000000; }\n" +
            "\t</style>\n" +
            "</head>\n" +
            "<body>\n" +
//            "\n" +
//            "<script>\n" +
//            "  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){\n" +
//            "  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),\n" +
//            "  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)\n" +
//            "  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');\n" +
//            "  ga('create', 'UA-36046209-1', 'in360.ru');\n" +
//            "  ga('send', 'pageview');\n" +
//            "</script>\n" +
//            "\n" +
            "<script src=\"viewer/krpano.js\"></script>\n" +
            "\n" +
            "<div id=\"pano\" style=\"width:100%;height:100%;\">\n" +
            "\t<noscript><table style=\"width:100%;height:100%;\"><tr style=\"valign:middle;\"><td><div style=\"text-align:center;\">ERROR:<br/><br/>Javascript not activated<br/><br/></div></td></tr></table></noscript>\n" +
            "\t<script>\n" +
            "\t\tembedpano({swf:\"viewer/krpano.swf\", xml:\"tour.xml\", target:\"pano\", html5:\"prefer\", passQueryParameters:true});\n" +
            "\t</script>\n" +
            "</div>\n" +
            "\n" +
            "</body>\n" +
            "</html>";


    public static final String xmlPreview = "<krpano onstart=\"loadscene(preview,null,MERGE);\">\n" +
            "<layer name=\"map\" keep=\"true\" url=\"%SWFPATH%/../viewer/plugins/bingmaps.swf\" html5_url=\"%SWFPATH%/../viewer/plugins/googlemaps.js\" align=\"lefttop\" x=\"0\" y=\"0\" lat=\"%LATITUDE%\" lng=\"%LONGITUDE%\"  width=\"30%\" height=\"30%\" key=\"%BINGMAPSKEY%\" \n" +
            "maptype=\"satellite\"  zoom=\"15\"  >\n" +
            "<spot name=\"spt\" active=\"true\" headingoffset=\"%HEADINGOFFSET%\" heading=\"%HEADING%\" lat=\"%LATITUDE%\" lng=\"%LONGITUDE%\" onclick=\"loadscene(preview,null,MERGE,BLEND(1));activatespot();\"/>\n" +
            "<radar visible=\"true\" zoomwithmap=\"true\" size=\"50\" />\n" +
            "<maptypecontrol visible=\"true\" buttonalign=\"V\" />\n" +
            "</layer>\n" +
            "<scene name=\"preview\" \n" +
            "heading=\"%HEADING%\" lat=\"%LATITUDE%\" lng=\"%LONGITUDE%\" \n" +
            ">\n" +
            "<view   hlookat=\"%HLOOKAT%\" vlookat=\"%VLOOKAT%\" fov=\"%FOV%\" />\n" +
            "<image>\n" +
            "<left  url=\"left.jpg\" />\n" +
            "<front url=\"front.jpg\" />\n" +
            "<right url=\"right.jpg\" />\n" +
            "<back  url=\"back.jpg\" />\n" +
            "<up    url=\"up.jpg\" />\n" +
            "<down  url=\"down.jpg\" />\n" +
            "</image>\n" +
            "</scene>\n" +
            // "<events name=\"loadmap\" keep=\"true\" onloadcomplete=\"copy(layer[map].lat, scene[preview].lat); copy(layer[map].lng, scene[preview].lng);copy(layer[map].spot[spt].lat, scene[preview].lat); copy(layer[map].spot[spt].lng, scene[preview].lng); copy(layer[map].spot[spt].heading, scene[preview].heading);\"/>\n"+
            "<events name=\"fovdisplay\" keep=\"true\" onviewchanged=\"show_fov_text();\" />\n" +
            "<action name=\"show_fov_text\">\n" +
            "copy(fov,view.fov);\n" +
            "roundval(fov,2);\n" +
            "copy(vtilt,view.vlookat);\n" +
            "roundval(vtilt,2);\n" +
            "copy(htilt,view.hlookat);\n" +
            "mod(htilt,360);\n" +
            "roundval(htilt,2);\n" +
            "txtadd(plugin[textfield_v].html, '[b]View Vertical :[/b] ', get(vtilt), '°');\n" +
            "txtadd(plugin[textfield_h].html, '[b]View Horizontal :[/b] ', get(htilt), '°');\n" +
            "txtadd(plugin[textfield_fov].html, '[b]Field of view :[/b] ', get(fov), '°');\n" +
            "</action>\n" +
            "<plugin name=\"textfield_v\" url=\"%SWFPATH%/../viewer/plugins/textfield.swf\" \n" +
            "align=\"lefttop\" x=\"10\" y=\"30\"\n" +
            "zorder=\"10\"\n" +
            "borderwidth=\"0\" bordercolor=\"0xFFFFFF\"\n" +
            "background=\"true\"\n" +
            "backgroundcolor=\"0xFFFFFF\"\n" +
            "selectable=\"false\"\n" +
            "autosize=\"center\"\n" +
            "keep = \"true\"\n" +
            "/>\n" +
            "<plugin name=\"textfield_h\" url=\"%SWFPATH%/../viewer/plugins/textfield.swf\" \n" +
            "align=\"lefttop\" x=\"10\" y=\"10\"\n" +
            "zorder=\"10\"\n" +
            "borderwidth=\"0\" bordercolor=\"0xFFFFFF\"\n" +
            "background=\"true\"\n" +
            "backgroundcolor=\"0xFFFFFF\"\n" +
            "selectable=\"false\"\n" +
            "autosize=\"center\"\n" +
            "\t\tkeep = \"true\"\n" +
            "/>\n" +
            "<plugin name=\"textfield_fov\" url=\"%SWFPATH%/../viewer/plugins/textfield.swf\" \n" +
            "align=\"lefttop\" x=\"10\" y=\"50\"\n" +
            "zorder=\"10\"\n" +
            "borderwidth=\"0\" bordercolor=\"0xFFFFFF\"\n" +
            "background=\"true\"\n" +
            "backgroundcolor=\"0xFFFFFF\"\n" +
            "selectable=\"false\"\n" +
            "autosize=\"center\"\n" +
            "keep = \"true\"\n" +
            "/>\n" +
            "</krpano>\n";
}
