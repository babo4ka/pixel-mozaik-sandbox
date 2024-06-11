package org.example.readyAlg

import java.awt.Color
import java.awt.image.BufferedImage
import java.nio.Buffer

class MozaikMaker() {

    private val firstLevelColor = Color(16, 21, 24)
    private val secondLevelColor = Color(27, 35, 46)
    private val thirdLevelColor = Color(83, 93, 103)
    private val fourthLevelColor = Color(153, 161, 172)
    private val fifthLevelColor = Color(237, 241, 240)

    private var firstBound = 0
    private var secondBound = 0
    private var thirdBound = 0
    private var fourthBound = 0
    private var fifthBound = 0

    private var max = Int.MIN_VALUE
    private var min = Int.MAX_VALUE


    fun getMozaik(inputImage: BufferedImage, size: Int): BufferedImage {
        val width = inputImage.width
        val height = inputImage.height

        val resizedWidth = width / size
        val resizedHeight = height / size

        val resized = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
        val resizedG = resized.createGraphics()

        var colorIntsSum:Long = 0
        var colorIntsCounter = 0

        val averageColors: Array<Array<Int>> = Array(resizedWidth) { Array(resizedHeight) { 0 } }
        var xPos = 0
        var yPos = 0

        for (x in 0..<width - size step size) {
            for (y in 0..<height - size step size) {
                var redC = 0
                var greenC = 0
                var blueC = 0

                for (xIn in 0..<size - 1) {
                    for (yIn in 0..<size - 1) {
                        val colorInt = inputImage.getRGB(x + xIn, y + yIn)
                        val color = Color(colorInt)

                        redC += color.red
                        greenC += color.green
                        blueC += color.blue
                    }
                }

                val pixelsCount = size * size
                val gray = (redC / pixelsCount + greenC / pixelsCount + blueC / pixelsCount) / 3
                val color = Color(gray, gray, gray)
                val colorInt = color.rgb

                if (colorInt > max) max = colorInt
                if (colorInt < min) min = colorInt

                colorIntsCounter++
                colorIntsSum += colorInt

                averageColors[xPos][yPos] = colorInt
                yPos++
            }
            yPos = 0
            xPos++
        }


        val avg:Long = colorIntsSum / colorIntsCounter
        setBounds(min, (avg - min), (max - avg))

        for (i in 0..<resizedWidth) {
            for (j in 0..<resizedHeight) {
                averageColors[i][j] = getGrayColor(averageColors[i][j]).rgb
            }
        }

        var newImageX = 0
        var newImageY = 0

        for (i in 0..<resizedWidth) {
            for (j in 0..<resizedHeight) {
                resizedG.color = Color(averageColors[i][j], true)
                resizedG.fillRect(newImageX, newImageY, size, size)
                newImageY += size
            }
            newImageY = 0
            newImageX += size
        }

        resizedG.dispose()

        return resized
    }

    private fun getGrayColor(num: Int): Color {
        return if (between(num, min, firstBound)) firstLevelColor
        else if (between(num, firstBound, secondBound)) secondLevelColor
        else if (between(num, secondBound, thirdBound)) thirdLevelColor
        else if (between(num, thirdBound, fourthBound)) fourthLevelColor
        else fifthLevelColor
    }

    private fun setBounds(min: Int, minAvgDiff: Long, maxAvgDiff: Long) {
        val leftDivider: Long
        val rightDivider: Long

        if (minAvgDiff > maxAvgDiff) {
            leftDivider = minAvgDiff / 3
            rightDivider = maxAvgDiff / 2

            firstBound = (min + leftDivider).toInt()
            secondBound = (firstBound + leftDivider).toInt()
            thirdBound = (secondBound + leftDivider).toInt()
        } else {
            leftDivider = minAvgDiff / 2
            rightDivider = maxAvgDiff / 3

            firstBound = (min + leftDivider).toInt()
            secondBound = (firstBound + leftDivider).toInt()

            thirdBound = (secondBound + rightDivider).toInt()
        }

        fourthBound = (thirdBound + rightDivider).toInt()
        fifthBound = (fourthBound + rightDivider).toInt()
    }

    val between: (Int, Int, Int) -> Boolean = { num, min, max -> num in min..max }
}