package org.example;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PixelsArt3 {

    static final String inputImagePath = "pic-main.jpg";
    static final String outputImagePath = "myoutput3.jpg";

    static int min = Integer.MAX_VALUE;
    static int max = Integer.MIN_VALUE;


    static final Color first = new Color(16, 21, 24);
    static int firstBound;
    static final Color second = new Color(27, 35, 46);
    static int secondBound;
    static final Color third = new Color(83, 93, 103);
    static int thirdBound;
    static final Color fourth = new Color(153, 161, 172);
    static int fourthBound;
    static final Color fifth = new Color(237, 241, 240);
    static int fifthBound;

    static final int size = 4;


    public static void main(String[] args) throws IOException {

        BufferedImage img = ImageIO.read(new File(inputImagePath));

        //showImage(img);

        BufferedImage resized = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D resizedG = resized.createGraphics();


        int[][] averageColors = new int[img.getWidth() / size][img.getHeight() / size];
        int xPos = 0;
        int yPos = 0;


        long colorIntsSum = 0;
        int colorIntsCounter = 0;

        for (int x = 0; x < img.getWidth() - size; x += size) {
            for (int y = 0; y < img.getHeight() - size; y += size) {
                int redC = 0, greenC = 0, blueC = 0;
                int redMax = Integer.MIN_VALUE, greenMax = Integer.MIN_VALUE, blueMax = Integer.MIN_VALUE;

                for (int xIn = 0; xIn < size - 1; xIn++) {
                    for (int yIn = 0; yIn < size - 1; yIn++) {
                        int colorInt = img.getRGB(x + xIn, y + yIn);
                        Color c = new Color(colorInt);
                        redC += c.getRed();
                        greenC += c.getGreen();
                        blueC += c.getBlue();

                        redMax = Math.max(c.getRed(), redMax);
                        greenMax = Math.max(c.getGreen(), greenMax);
                        blueMax = Math.max(c.getBlue(), blueMax);
                    }
                }

                int pixelsCount = size * size;
                //Color c = new Color(redMax, greenMax, blueMax);
                int gray = (redC/pixelsCount + greenC/pixelsCount + blueC/pixelsCount) /3;
                Color c = new Color(gray, gray, gray);
                int colorInt = c.getRGB();
                if (colorInt > max) max = colorInt;
                if (colorInt < min) min = colorInt;
                colorIntsCounter++;
                colorIntsSum += colorInt;


                averageColors[xPos][yPos] = colorInt;
                yPos++;
            }
            yPos = 0;
            xPos++;
        }

//        int divider = (max-min) / 5;
//
//        firstBound = min + divider;
//        secondBound = firstBound + divider;
//        thirdBound = secondBound + divider;
//        fourthBound = thirdBound + divider;
//        fifthBound = fourthBound + divider;

//        System.out.println(firstBound);
//        System.out.println(secondBound);
//        System.out.println(thirdBound);
//        System.out.println(fourthBound);
//        System.out.println(fifthBound);

        long avg = colorIntsSum / colorIntsCounter;

        setBounds(min, (avg - min), (max - avg));

        System.out.println("max: " + max);
        System.out.println("min: " + min);
        System.out.println("diff: " + (max - min));
        System.out.println("sum: " + colorIntsSum);
        System.out.println("avg: " + avg);

        System.out.println("diff btw max and avg: " + (max - avg));
        System.out.println("diff btw min and avg: " + (avg - min));

        for (int i = 0; i < img.getWidth() / size; i++) {
            for (int j = 0; j < img.getHeight() / size; j++) {
                averageColors[i][j] = getGrayColor(averageColors[i][j]).getRGB();
            }
        }

        int newImageX = 0;
        int newImageY = 0;

        for (int i = 0; i < img.getWidth() / size; i++) {
            for (int j = 0; j < img.getHeight() / size; j++) {
                resizedG.setColor(new Color(averageColors[i][j], true));
                resizedG.fillRect(newImageX, newImageY, size, size);
                newImageY += size;
                //System.out.print(averageColors[i][j] + " ");
            }
            newImageY = 0;
            newImageX += size;
            //System.out.println();
        }

        resizedG.dispose();

        showImage(resized);

        ImageIO.write(resized, "jpg", new File(outputImagePath));


    }


    static void showImage(BufferedImage img) {
        JLabel picLabel = new JLabel(new ImageIcon(img));
        JPanel jPanel = new JPanel();
        jPanel.add(picLabel);

        JFrame f = new JFrame();
        f.setSize(new Dimension(img.getWidth(), img.getHeight()));
        f.add(jPanel);
        f.setVisible(true);
    }


    static Color getGrayColor(int num) {
        if (isBetween(num, min, firstBound)) {
            return first;
        } else if (isBetween(num, firstBound, secondBound)) {
            return second;
        } else if (isBetween(num, secondBound, thirdBound)) {
            return third;
        } else if (isBetween(num, thirdBound, fourthBound)) {
            return fourth;
        } else {
            return fifth;
        }

    }

    static boolean isBetween(int num, int min, int max) {
        return num <= max && num >= min;
    }


    static void setBounds(int min, long minAvgDiff, long maxAvgDiff) {
        long leftDivider, rightDivider;

        if (minAvgDiff > maxAvgDiff) {
            leftDivider = minAvgDiff / 3;
            rightDivider = maxAvgDiff / 2;

            firstBound = (int) (min + leftDivider);
            secondBound = (int) (firstBound + leftDivider);
            thirdBound = (int) (secondBound + leftDivider);

        } else {
            leftDivider = minAvgDiff / 2;
            rightDivider = maxAvgDiff / 3;

            firstBound = (int) (min + leftDivider);
            secondBound = (int) (firstBound + leftDivider);

            thirdBound = (int) (secondBound + rightDivider);
        }
        fourthBound = (int) (thirdBound + rightDivider);
        fifthBound = (int) (fourthBound + rightDivider);
    }
}
