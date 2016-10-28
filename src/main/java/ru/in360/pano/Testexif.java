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


package ru.in360.pano;

import org.w3c.dom.NodeList;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.MemoryCacheImageInputStream;
import javax.swing.*;

public class Testexif {
    private static String JPEGMetaFormat = "javax_imageio_jpeg_image_1.0";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    runSequence();
                } catch (Exception e) {
                    System.exit(-1);
                }
            }
        });
    }

    private static void showMessageAndExit(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
        System.exit(0);
    }

    public static void runSequence() throws java.io.IOException {
        if (!ImageIO.getImageReadersByFormatName("tif").hasNext()) {
            showMessageAndExit("You will need a tiff ImageReader plugin to " +
                    "parse exif data.  Please download \"JAI-ImageIO\".");
        }

        JOptionPane.showMessageDialog(null, "Please select a jpeg file with " +
                "EXIF metadata");

        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(chooser) == JFileChooser.CANCEL_OPTION) {
            System.exit(0);
        }

        java.io.File toOpen = chooser.getSelectedFile();

        ImageInputStream in = ImageIO.createImageInputStream(toOpen);
        java.util.Iterator<ImageReader> readers = ImageIO.getImageReaders(in);

        if (!readers.hasNext()) {
            showMessageAndExit("The selected file was not an image file, or " +
                    "not a type that ImageIO recognized.");
        }

        ImageReader reader = null;
        while (readers.hasNext()) {
            ImageReader tmp = readers.next();
            if (JPEGMetaFormat.equals(tmp.getOriginatingProvider().getNativeImageMetadataFormatName())) {
                reader = tmp;
                break;
            }
        }
        if (reader == null) {
            showMessageAndExit("The selected file was not a jpeg file.");
        }

        reader.setInput(in, true, false);

        byte[] exifRAW = getEXIF(reader.getImageMetadata(0));
        if (exifRAW == null) {
            showMessageAndExit("The selected jpeg file did not contain any " +
                    "exif data.");
        }

        reader.dispose();
        in.close();

        IIOMetadata exifMeta = getTiffMetaFromEXIF(exifRAW);

        JFrame frame = new JFrame();
        frame.setContentPane(new JScrollPane(parseExifMeta(exifMeta)));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private static JTable parseExifMeta(IIOMetadata exifMeta) {
        //Specification of "com_sun_media_imageio_plugins_tiff_image_1.0"
        //http://download.java.net/media/jai-imageio/javadoc/1.1/com/sun/media/imageio/plugins/tiff/package-summary.html

        javax.swing.table.DefaultTableModel tags =
                new javax.swing.table.DefaultTableModel();

        tags.addColumn("Tag #");
        tags.addColumn("Name");
        tags.addColumn("Value(s)");

        IIOMetadataNode root = (IIOMetadataNode)
                exifMeta.getAsTree("com_sun_media_imageio_plugins_tiff_image_1.0");


        NodeList imageDirectories = root.getElementsByTagName("TIFFIFD");
        for (int i = 0; i < imageDirectories.getLength(); i++) {
            IIOMetadataNode directory = (IIOMetadataNode) imageDirectories.item(i);

            NodeList tiffTags = directory.getElementsByTagName("TIFFField");
            for (int j = 0; j < tiffTags.getLength(); j++) {
                IIOMetadataNode tag = (IIOMetadataNode) tiffTags.item(j);

                String tagNumber = tag.getAttribute("number");
                String tagName = tag.getAttribute("name");
                String tagValue;


                StringBuilder tmp = new StringBuilder();
                IIOMetadataNode values = (IIOMetadataNode) tag.getFirstChild();

                if ("TIFFUndefined".equals(values.getNodeName())) {
                    tmp.append(values.getAttribute("value"));
                } else {
                    NodeList tiffNumbers = values.getChildNodes();
                    for (int k = 0; k < tiffNumbers.getLength(); k++) {
                        tmp.append(((IIOMetadataNode) tiffNumbers.item(k)).getAttribute("value"));
                        tmp.append(",");
                    }
                    tmp.deleteCharAt(tmp.length() - 1);
                }

                tagValue = tmp.toString();

                tags.addRow(new String[]{tagNumber, tagName, tagValue});
            }
        }
        return new JTable(tags);
    }

    /**
     * Returns the EXIF information from the given metadata if present.  The
     * metadata is assumed to be in <pre>javax_imageio_jpeg_image_1.0</pre> format.
     * If EXIF information was not present then null is returned.
     */
    public static byte[] getEXIF(IIOMetadata meta) {
        //http://java.sun.com/javase/6/docs/api/javax/imageio/metadata/doc-files/jpeg_metadata.html

        //javax_imageio_jpeg_image_1.0
        //-->markerSequence
        //---->unknown (attribute: "MarkerTag" val: 225 (for exif))

        IIOMetadataNode root = (IIOMetadataNode) meta.getAsTree(JPEGMetaFormat);

        IIOMetadataNode markerSeq = (IIOMetadataNode)
                root.getElementsByTagName("markerSequence").item(0);

        NodeList unkowns = markerSeq.getElementsByTagName("unknown");
        for (int i = 0; i < unkowns.getLength(); i++) {
            IIOMetadataNode marker = (IIOMetadataNode) unkowns.item(i);
            if ("225".equals(marker.getAttribute("MarkerTag"))) {
                return (byte[]) marker.getUserObject();
            }
        }
        return null;
    }

    /**
     * Uses a TIFFImageReader plugin to parse the given exif data into tiff
     * tags.  The returned IIOMetadata is in whatever format the tiff ImageIO
     * plugin uses.  If there is no tiff plugin, then this method returns null.
     */
    public static IIOMetadata getTiffMetaFromEXIF(byte[] exif) {
        java.util.Iterator<ImageReader> readers =
                ImageIO.getImageReadersByFormatName("tif");

        ImageReader reader;
        if (!readers.hasNext()) {
            return null;
        } else {
            reader = readers.next();
        }

        //skip the 6 byte exif header
        ImageInputStream wrapper = new MemoryCacheImageInputStream(
                new java.io.ByteArrayInputStream(exif, 6, exif.length - 6));
        reader.setInput(wrapper, true, false);

        IIOMetadata exifMeta;
        try {
            exifMeta = reader.getImageMetadata(0);
        } catch (Exception e) {
            //shouldn't happen
            throw new Error(e);
        }

        reader.dispose();
        return exifMeta;
    }
}