package org.example.readyAlg;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MosaicMain {

    static final String inputImagePath = "ch.jpg";
    static final String outputImagePath = "myoutput4.jpg";

    static final String visualOutput = "visual.jpg";
    static final String secvisualOutput = "secvisual.jpg";
    public static void main(String[] args) throws IOException {

        BufferedImage img = ImageIO.read(new File(inputImagePath));

        MosaicMaker mm = new MosaicMaker();

        BufferedImage mosaic = mm.getMosaic(img, 16);

        ImageIO.write(mosaic, "jpg", new File(outputImagePath));

        MosaicVisualizer mv = new MosaicVisualizer();

//        BufferedImage visual = mv.visualizeMosaic(mosaic, 9);
//
//        ImageIO.write(visual, "jpg", new File(visualOutput));

        BufferedImage visual = mv.vis(mosaic, 9);

        ImageIO.write(visual, "jpg", new File(secvisualOutput));
    }
}
