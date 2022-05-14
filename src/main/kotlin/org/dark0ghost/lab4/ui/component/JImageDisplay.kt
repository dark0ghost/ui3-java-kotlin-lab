package org.dark0ghost.lab4.ui.component



import java.awt.Dimension
import java.awt.Graphics
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_RGB
import javax.swing.JComponent


open class JImageDisplay(height: UInt, width: UInt): JComponent() {
    private val bufferedImage: BufferedImage = BufferedImage(width.toInt(), height.toInt(), TYPE_INT_RGB)

    init {
        preferredSize = Dimension(width.toInt(), height.toInt())
        super.setPreferredSize(Dimension(width.toInt(), height.toInt()))
    }

    fun clearImage() {
        val blankArray = IntArray(width * height)
        bufferedImage.setRGB(0, 0, width, height, blankArray, 0, 1)
    }

    fun drawPixel(x: Int, y: Int, color: Int) = bufferedImage.setRGB(x, y, color)


    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        g.drawImage(bufferedImage, 0, 0, bufferedImage.width, bufferedImage.height, null);
    }
}