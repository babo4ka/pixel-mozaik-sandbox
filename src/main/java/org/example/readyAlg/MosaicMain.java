package org.example.readyAlg;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MosaicMain {

    static final String inputImagePath = "ch.jpg";
    static final String outputImagePath = "myoutput4.jpg";

    public static void main(String[] args) throws IOException {

        BufferedImage img = ImageIO.read(new File(inputImagePath));

        MosaicMaker mm = new MosaicMaker();

        BufferedImage mosaic = mm.getMosaic(img, 16);

        ImageIO.write(mosaic, "jpg", new File(outputImagePath));
    }
}
