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

//import com.sun.image.codec.jpeg.JPEGCodec;
//import com.sun.image.codec.jpeg.JPEGImageEncoder;
//import com.sun.media.jai.codec.*;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.media.jai.PlanarImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageConverter {

//    static Image loadImage(byte[] data) throws IOException {
//        Image image = null;
//        SeekableStream stream = new ByteArraySeekableStream(data);
//        String[] names = ImageCodec.getDecoderNames(stream);
//        ImageDecoder dec =
//                ImageCodec.createImageDecoder(names[0], stream, null);
//        RenderedImage im = dec.decodeAsRenderedImage();
//        image = PlanarImage.wrapRenderedImage(im).getAsBufferedImage();
//
//        data = null;
//        im = null;
//        stream.close();
//        //System.gc();
//        return image;
//    }
//
//    public static void toBitmap(File in, File out) throws IOException {
//
//        try {
//            SeekableStream s = new FileSeekableStream(in);
//            TIFFDecodeParam param = null;
//            ImageDecoder dec = ImageCodec.createImageDecoder("tiff", s, param);
//            RenderedImage op = dec.decodeAsRenderedImage();
//            FileOutputStream fos = new FileOutputStream(out);
//            JPEGImageEncoder jpeg = JPEGCodec.createJPEGEncoder(fos);
//
//            jpeg.encode(op.getData());
//            fos.close();
//        } catch (java.io.IOException ioe) {
//            System.out.println(ioe);
//        }
//    }


//
//    public static BufferedImage toBitmap(String filename) throws IOException {
//
//        BufferedImage image = ImageIO.read(new File(filename));
//        if (image != null) {
//            System.out.println("convert...");
//            BufferedImage convertedImage = new BufferedImage(image.getWidth(),
//                    image.getHeight(), BufferedImage.TYPE_INT_RGB);
//            convertedImage.createGraphics().drawRenderedImage(image, null);
//            ImageIO.write(convertedImage, "bmp", new File(filename + ".bmp"));
//        } else System.out.println("unknown format");
//        return image;
//    }

    public static void writeJPG(BufferedImage img, File outputImg) throws IOException {
        try (ImageOutputStream ios = ImageIO.createImageOutputStream(outputImg)) {
            ImageWriter writer = ImageIO.getImageWritersByFormatName("jpeg").next();
            ImageWriteParam param = writer.getDefaultWriteParam();
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(1.0f); // Highest quality
            writer.setOutput(ios);
            writer.write(img);
            ios.flush();
        }
        //ImageIO.write(tile, "bmp", outputTile);
    }

}
