package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Zhopa {

    public static void main(String[] args) throws IOException {
        BufferedImage icon = ImageIO.read(new File("fifth_lego.png"));

        BufferedImage img = new BufferedImage(128, 128, BufferedImage.TYPE_INT_RGB);

        Graphics2D g = (Graphics2D) img.createGraphics();
        g.drawImage(icon, 0, 0, null);

        ImageIO.write(img, "jpg", new File("zhopa.png"));
    }
}
