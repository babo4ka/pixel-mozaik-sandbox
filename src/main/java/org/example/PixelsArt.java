package org.example;

import org.jetbrains.annotations.Nullable;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PixelsArt {

    static final String inputImagePath = "pic-main.jpg";
    static final String outputImagePath = "myoutput.jpg";

    static int min = Integer.MAX_VALUE;
    static int max = Integer.MIN_VALUE;

    public static void main(String[] args) throws IOException {

        BufferedImage img = ImageIO.read(new File(inputImagePath));



        Graphics2D g = (Graphics2D) img.getGraphics();

        showImage(img);

        BufferedImage resized = new BufferedImage(img.getWidth()/4, img.getHeight()/4, BufferedImage.TYPE_INT_RGB);
        Graphics2D resizedG = resized.createGraphics();
//        resizedG.drawImage(img, 0, 0, img.getWidth()/4, img.getHeight()/4, null);
//        resizedG.dispose();


        int [][] averageColors = new int[img.getWidth()/4][img.getHeight()/4];
        int xPos = 0;
        int yPos = 0;



        for(int x=0; x<img.getWidth()-4;x+=4){
            for(int y=0; y<img.getHeight()-4;y+=4){
                int redC = 0;
                int greenC = 0;
                int blueC = 0;


                for(int xIn = 0;xIn<3;xIn++){
                    for(int yIn=0;yIn<3;yIn++){
                        int colorInt = img.getRGB(x+xIn, y+yIn);
                        Color c = new Color(colorInt);
                        redC += c.getRed();
                        greenC += c.getGreen();
                        blueC += c.getBlue();
                    }
                }

                Color c = new Color(redC/16, greenC/16, blueC/16);
                int colorInt = c.getRGB();
                if(colorInt > max) max = colorInt;
                if(colorInt < min) min = colorInt;

                averageColors[xPos][yPos] = colorInt;
                yPos++;
            }
            yPos = 0;
            xPos++;
        }

        int divider = (max-min) / 5;

        firstBound = min + divider;
        secondBound = firstBound + divider;
        thirdBound = secondBound + divider;
        fourthBound = thirdBound + divider;
        fifthBound = fourthBound + divider;

        System.out.println(firstBound);
        System.out.println(secondBound);
        System.out.println(thirdBound);
        System.out.println(fourthBound);
        System.out.println(fifthBound);


        System.out.println(max);
        System.out.println(min);
        System.out.println(max - min);

        for(int i=0;i<img.getWidth()/4;i++){
            for(int j=0;j<img.getHeight()/4;j++){
                averageColors[i][j] = getGrayColor(averageColors[i][j]).getRGB();
            }
        }

        for(int i=0;i<img.getWidth()/4;i++){
            for(int j=0;j<img.getHeight()/4;j++){
                resizedG.setColor(new Color(averageColors[i][j], true));
                resizedG.drawRect(i, j, 1, 1);
                System.out.print(averageColors[i][j] + " ");
            }
            System.out.println();
        }

        resizedG.dispose();

        showImage(resized);

        ImageIO.write(resized, "jpg", new File(outputImagePath));


    }


    static void showImage(BufferedImage img){
        JLabel picLabel = new JLabel(new ImageIcon(img));
        JPanel jPanel = new JPanel();
        jPanel.add(picLabel);

        JFrame f = new JFrame();
        f.setSize(new Dimension(img.getWidth(), img.getHeight()));
        f.add(jPanel);
        f.setVisible(true);
    }

    static final Color first = new Color(237,241,240);
    static int firstBound;
    static final Color second = new Color(153,161,172);
    static int secondBound;
    static final Color third = new Color(83,93,103);
    static int thirdBound;
    static final Color fourth = new Color(27,35,46);
    static int fourthBound;
    static final Color fifth = new Color(16,21,24);
    static int fifthBound;

    @Nullable
    static Color getGrayColor(int num){
        if(isBetween(num, min, firstBound)){
            return first;
        }else if(isBetween(num, firstBound, secondBound)){
            return second;
        }else if(isBetween(num, secondBound, thirdBound)){
            return third;
        }else if(isBetween(num, thirdBound, fourthBound)){
            return fourth;
        }else {
            return fifth;
        }

    }

    static boolean isBetween(int num, int min, int max){
        return num <=max && num>=min;
    }
}
