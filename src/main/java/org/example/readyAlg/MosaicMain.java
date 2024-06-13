package org.example.readyAlg;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MosaicMain {

    static final String inputImagePath = "pic-main.jpg";
    static final String outputImagePath = "myoutput4.jpg";

    static final String visualOutput = "visual.jpg";

    public static void main(String[] args) throws IOException {

        BufferedImage img = ImageIO.read(new File(inputImagePath));

        MosaicMaker mm = new MosaicMaker();

        BufferedImage mosaic = mm.getMosaic(img, 4);

        ImageIO.write(mosaic, "jpg", new File(outputImagePath));

        MosaicVisualizer mv = new MosaicVisualizer();

        BufferedImage visual = mv.visualizeMosaic(mosaic, 32);

        ImageIO.write(visual, "jpg", new File(visualOutput));

    }
}
