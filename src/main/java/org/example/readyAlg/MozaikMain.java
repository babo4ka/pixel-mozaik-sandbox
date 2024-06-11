package org.example.readyAlg;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MozaikMain {

    static final String inputImagePath = "pic-main.jpg";
    static final String outputImagePath = "myoutput4.jpg";


    public static void main(String[] args) throws IOException {

        BufferedImage img = ImageIO.read(new File(inputImagePath));

        MozaikMaker mm = new MozaikMaker();

        BufferedImage mozaik = mm.getMozaik(img, 4);

        ImageIO.write(mozaik, "jpg", new File(outputImagePath));
    }
}
