package org.dark0ghost.lab4.ui.explorer

import org.dark0ghost.lab4.fractal.FractalGenerator
import org.dark0ghost.lab4.fractal.Mandelbrot
import org.dark0ghost.lab4.ui.DrawableUI
import org.dark0ghost.lab4.ui.component.JImageDisplay
import java.awt.BorderLayout
import java.awt.Color
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.geom.Rectangle2D
import javax.swing.JButton
import javax.swing.JFrame


internal typealias SizeDisplay = Pair<UInt, UInt>

class FractalExplorer(private val sizeDisplay: SizeDisplay): DrawableUI {
    private val jImageDisplay: JImageDisplay = JImageDisplay(sizeDisplay.first, sizeDisplay.second)

    private val generator: FractalGenerator = Mandelbrot()

    private val range: Rectangle2D.Double = Rectangle2D.Double()

    private lateinit var jFrame: JFrame

    private inner class Mouse : MouseAdapter() {
        override fun mouseClicked(e: MouseEvent) {
            super.mouseClicked(e)
            e.apply {
                val cord = drawPixel(x, y)
                generator.recenterAndZoomRange(range, cord[0], cord[1], 1.5)
            }
        }
    }

    private inner class AListener: ActionListener {
        /**
         * Invoked when an action occurs.
         * @param e the event to be processed
         */
        override fun actionPerformed(e: ActionEvent?) {
            generator.getInitialRange(range)
            drawFractal()
        }

    }

    private fun drawFractal() {
        0u.until(sizeDisplay.first).forEach { x ->
            0u.until(sizeDisplay.second).forEach { y ->
                val x = x.toInt()
                val y = y.toInt()
                drawPixel(x, y)
            }
        }
        jImageDisplay.repaint()
    }


    fun drawPixel(x: Int, y: Int): List<Double> {
        val xCoord = FractalGenerator.getCoord(
            range.x, range.x + range.width,
            sizeDisplay.display, x
        )
        val yCoord = FractalGenerator.getCoord(
            range.y, range.y + range.width,
            sizeDisplay.display, y
        )
        when (val numIter = generator.numIterations(xCoord to yCoord)) {
            -1 -> jImageDisplay.drawPixel(xCoord.toInt(), yCoord.toInt(), 0)
            else -> {
                val hue = 0.7f + numIter.toFloat() / 200f
                val rgbColor: Int = Color.HSBtoRGB(hue, 1f, 1f)
                jImageDisplay.drawPixel(x, y, rgbColor)
            }
        }
        return listOf(xCoord, yCoord)
    }

    override fun createAndShowGUI() {
        generator.getInitialRange(range)
        val jButton = JButton()
        jFrame = JFrame(this.javaClass.name)
        jFrame.apply {
            defaultCloseOperation = JFrame.EXIT_ON_CLOSE
            contentPane.apply {
                add(jImageDisplay, BorderLayout.CENTER)
                add(jButton, BorderLayout.SOUTH)
            }
            pack()
            isVisible = true
            isResizable = !isVisible
        }
        jImageDisplay.addMouseListener(Mouse())
        jButton.addActionListener(AListener())
    }

    companion object {
        internal val SizeDisplay.display
            get() = first * second
    }
}