package org.example;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PixelsArt3 {

    static final String inputImagePath = "pic-main.jpg";
    static final String outputImagePath = "myoutput3.jpg";

    static int min = Integer.MAX_VALUE;
    static int max = Integer.MIN_VALUE;

    public static void main(String[] args) throws IOException {

        BufferedImage img = ImageIO.read(new File(inputImagePath));



        Graphics2D g = (Graphics2D) img.getGraphics();

        showImage(img);

        BufferedImage resized = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D resizedG = resized.createGraphics();


        int [][] averageColors = new int[img.getWidth()/4][img.getHeight()/4];
        int xPos = 0;
        int yPos = 0;

        List<Integer> colorInts = new ArrayList<>();
        long colorIntsSum = 0;

        for(int x=0; x<img.getWidth()-4;x+=4){
            for(int y=0; y<img.getHeight()-4;y+=4){
                int redC = 0, greenC = 0, blueC = 0;


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
                colorInts.add(colorInt);
                colorIntsSum += colorInt;


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

//        System.out.println(firstBound);
//        System.out.println(secondBound);
//        System.out.println(thirdBound);
//        System.out.println(fourthBound);
//        System.out.println(fifthBound);

        long avg = colorIntsSum/colorInts.size();


        System.out.println("max: " + max);
        System.out.println("min: " + min);
        System.out.println("diff: " + (max - min));
        System.out.println("sum: " + colorIntsSum);
        System.out.println("avg: " + avg);

        System.out.println("diff btw max and avg: " + (max - avg));
        System.out.println("diff btw min and avg: " + (avg - min));

        for(int i=0;i<img.getWidth()/4;i++){
            for(int j=0;j<img.getHeight()/4;j++){
                averageColors[i][j] = getGrayColor(averageColors[i][j]).getRGB();
            }
        }

        int newImageX = 0;
        int newImageY = 0;

        for(int i=0;i<img.getWidth()/4;i++){
            for(int j=0;j<img.getHeight()/4;j++){
                resizedG.setColor(new Color(averageColors[i][j], true));
                //resizedG.drawRect(i, j, 1, 1);
                resizedG.fillRect(newImageX, newImageY, 4, 4);
                newImageY+=4;
                //System.out.print(averageColors[i][j] + " ");
            }
            newImageY=0;
            newImageX+=4;
            //System.out.println();
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


    static final Color first = new Color(16,21,24);
    static int firstBound;
    static final Color second = new Color(27,35,46);
    static int secondBound;
    static final Color third = new Color(83,93,103);
    static int thirdBound;
    static final Color fourth = new Color(153,161,172);
    static int fourthBound;
    static final Color fifth = new Color(237,241,240);
    static int fifthBound;


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

    static int getAverageGray(BufferedImage image, int x, int y, int blockSize) {
        int sumRed = 0, sumGreen = 0, sumBlue = 0;
        for (int i = 0; i < blockSize; i++) {
            for (int j = 0; j < blockSize; j++) {
                int pixel = image.getRGB(x + i, y + j);
                int red = (pixel >> 16) & 0xFF;
                int green = (pixel >> 8) & 0xFF;
                int blue = pixel & 0xFF;
                sumRed += red;
                sumGreen += green;
                sumBlue += blue;
            }
        }
        int numPixels = blockSize * blockSize;
        int avgRed = sumRed / numPixels;
        int avgGreen = sumGreen / numPixels;
        int avgBlue = sumBlue / numPixels;
        return (avgRed + avgGreen + avgBlue) / 3;
    }
}
