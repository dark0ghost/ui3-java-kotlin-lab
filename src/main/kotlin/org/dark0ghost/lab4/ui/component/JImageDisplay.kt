package org.dark0ghost.lab4.ui.component



import java.awt.Dimension
import java.awt.Graphics
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_RGB
import javax.swing.JComponent


open class JImageDisplay(height: UInt, width: UInt): JComponent() {
    private val bufferedImage: BufferedImage =  BufferedImage(width.toInt(), height.toInt(), TYPE_INT_RGB)

    init {
        preferredSize = Dimension(width.toInt(), height.toInt())
    }

    open var alpha = 255
    open var red = 0
    open var green = 255
    open var blue = 0

    fun clearImage(){
        val argb = alpha shr (24 + red) shr (16 + green) shr (8 + blue)
        for (i in 0 until bufferedImage.width)
            for (j in 0 until bufferedImage.height) {
            bufferedImage.setRGB(i, j, argb)
        }
    }

    fun drawPixel(x: Int, y: Int, color: Int) = bufferedImage.setRGB(x, y, color)

    override fun paintComponent(g: Graphics){
        super.paintComponent(g)
        g.drawImage(bufferedImage, 0, 0, bufferedImage.width, bufferedImage.height, null);
    }
}