package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {

        String inputImagePath = "image2.jpg";
        String outputImagePath = "output.jpg";

        int mosaicSize = 100; // Размер мозаики (ширина и высота)
        int blockSize = 10; // Размер пиксельного блока

        try {
            // Загрузить исходное изображение
            BufferedImage sourceImage = ImageIO.read(new File(inputImagePath));

            // Уменьшить размер изображения до размера мозаики
            BufferedImage scaledImage = resizeImage(sourceImage, mosaicSize, mosaicSize);

            // Создать новое изображение для мозаики
            BufferedImage mosaicImage = new BufferedImage(mosaicSize * blockSize, mosaicSize * blockSize, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = mosaicImage.createGraphics();

            // Создать мозаику
            for (int y = 0; y < mosaicSize; y++) {
                for (int x = 0; x < mosaicSize; x++) {
                    // Получить средний уровень серого для пиксельного блока
                    int avgGray = getAverageGray(scaledImage, x * blockSize, y * blockSize, blockSize);

                    // Определить градацию серого
                    int grayLevel = getGrayLevel(avgGray);

                    // Нарисовать пиксельный блок с соответствующим цветом серого
                    drawGrayBlock(g2d, x * blockSize, y * blockSize, blockSize, grayLevel);
                }
            }

            g2d.dispose();

            // Сохранить результирующее изображение
            ImageIO.write(mosaicImage, "jpg", new File(outputImagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static BufferedImage resizeImage(BufferedImage sourceImage, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(sourceImage, 0, 0, width, height, null);
        g2d.dispose();
        return resizedImage;
    }

    private static int getAverageGray(BufferedImage image, int x, int y, int blockSize) {
        int sumGray = 0;
        int validPixels = 0;

        for (int i = x; i < x + blockSize && i < image.getWidth(); i++) {
            for (int j = y; j < y + blockSize && j < image.getHeight(); j++) {
                int pixel = image.getRGB(i, j);
                int red = (pixel >> 16) & 0xFF;
                int green = (pixel >> 8) & 0xFF;
                int blue = pixel & 0xFF;
                int gray = (red + green + blue) / 3;
                sumGray += gray;
                validPixels++;
            }
        }

        if (validPixels > 0) {
            return sumGray / validPixels;
        } else {
            return 0;
        }
    }

    private static int getGrayLevel(int avgGray) {
        if (avgGray < 51) {
            return 0; // Черный
        } else if (avgGray < 102) {
            return 1; // Темно-серый
        } else if (avgGray < 153) {
            return 2; // Средне-серый
        } else if (avgGray < 204) {
            return 3; // Светло-серый   } else {
        }
        return 4; // Белый
    }

    private static void drawGrayBlock(Graphics2D g2d, int x, int y, int blockSize, int grayLevel) {
        Rectangle clipBounds = g2d.getClipBounds();
        if (clipBounds != null) {
            int blockX = x;
            int blockY = y;
            int blockWidth = blockSize;
            int blockHeight = blockSize;

            // Ограничить размер блока, если он выходит за границы изображения
            if (x < 0) {
                blockX = 0;
                blockWidth = Math.min(blockSize, clipBounds.width - x);
            }
            if (y < 0) {
                blockY = 0;
                blockHeight = Math.min(blockSize, clipBounds.height - y);
            }
            if (x + blockSize > clipBounds.width) {
                blockWidth = Math.max(0, clipBounds.width - x);
            }
            if (y + blockSize > clipBounds.height) {
                blockHeight = Math.max(0, clipBounds.height - y);
            }

            // Нарисовать пиксельный блок только в пределах изображения
            if (blockWidth > 0 && blockHeight > 0) {
                switch (grayLevel) {
                    case 0:
                        g2d.setColor(Color.BLACK);
                        break;
                    case 1:
                        g2d.setColor(new Color(51, 51, 51));
                        break;
                    case 2:
                        g2d.setColor(new Color(102, 102, 102));
                        break;
                    case 3:
                        g2d.setColor(new Color(153, 153, 153));
                        break;
                    case 4:
                        g2d.setColor(Color.WHITE);
                        break;
                }
                g2d.fillRect(blockX, blockY, blockWidth, blockHeight);
            }
        }
    }
}