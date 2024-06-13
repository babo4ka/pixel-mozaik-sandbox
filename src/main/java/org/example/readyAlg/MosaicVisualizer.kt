package org.example.readyAlg

import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.Image
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class MosaicVisualizer {

    private val firstLevelColor = Color(16, 21, 24)
    private val secondLevelColor = Color(27, 35, 46)
    private val thirdLevelColor = Color(83, 93, 103)
    private val fourthLevelColor = Color(153, 161, 172)
    private val fifthLevelColor = Color(237, 241, 240)

    private val first = ImageIO.read(File("first.png"))
    private val second = ImageIO.read(File("second.png"))
    private val third = ImageIO.read(File("third.png"))
    private val fourth = ImageIO.read(File("fourth.png"))
    private val fifth = ImageIO.read(File("fifth.png"))

    fun visualizeMosaic(image: BufferedImage, size:Int):BufferedImage{
        val width = image.width
        val height = image.height

        val resized = BufferedImage(width*size, height*size, BufferedImage.TYPE_INT_RGB)
        val resizedG = resized.createGraphics()

        var inputImageX = 0
        var inputImageY = 0


        for(x in 0..<resized.width-size step size){
            for(y in 0..resized.height-size step size){
                val color = Color(image.getRGB(inputImageX, inputImageY))
                //resizedG.color = color
                resizedG.drawImage(image(color), null, x, y)
                //resizedG.fillRect(x, y, width*size, height*size)
                inputImageY++
            }
            inputImageY = 0
            inputImageX++
        }

        resizedG.dispose()
        return resized
    }

    fun image(color: Color):BufferedImage{
        return if(color == firstLevelColor){
            first
        }else if(color == secondLevelColor){
            second
        }else if (color == thirdLevelColor){
            third
        }else if(color == fourthLevelColor){
            fourth
        }else{
            fifth
        }
    }
}