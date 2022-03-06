package org.dark0ghost.lab4

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposePanel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.dark0ghost.lab4.fractal.FractalGenerator
import org.dark0ghost.lab4.fractal.Mandelbrot
import org.dark0ghost.lab4.ui.component.JImageDisplay
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.geom.Rectangle2D
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.SwingUtilities
import javax.swing.WindowConstants

internal typealias SizeDisplay = Pair<UInt, UInt>

private val sizeDisplay = 800u to 600u

fun main() = SwingUtilities.invokeLater {
    val jImageDisplay = JImageDisplay(sizeDisplay.first, sizeDisplay.second)
    jImageDisplay.addMouseListener(Mouse())
    val window = JFrame()

    // creating ComposePanel
    val composePanel = ComposePanel()

    window.apply {
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        contentPane.apply {
            add(jImageDisplay,BorderLayout.CENTER)
            add(composePanel, BorderLayout.CENTER)
        }
        pack()
        isVisible = true
    }

    composePanel.setContent {
        ComposeContent()
    }

    window.setSize(sizeDisplay.first.toInt(), sizeDisplay.second.toInt())
    window.isVisible = true
}

@Composable
fun ComposeContent() {
    val counter = remember { mutableStateOf(0) }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Row {
            Button(onClick = {
                counter.value++
                generator.getInitialRange(range)
                drawFractal()
            }) {
                Text(text = "Reset display", color = Color.White)
            }
        }
    }
}

private val jImageDisplay: JImageDisplay = JImageDisplay(sizeDisplay.first, sizeDisplay.second)

private val generator: FractalGenerator = Mandelbrot()

private val range: Rectangle2D.Double = Rectangle2D.Double()


private class Mouse : MouseAdapter() {
    override fun mouseClicked(e: MouseEvent) {
        super.mouseClicked(e)
        e.apply {
            val cord = drawPixel(x, y)
            generator.recenterAndZoomRange(range, cord[0], cord[1], 1.5)
        }
    }
}

private class AListener: ActionListener {
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
            val rgbColor: Int = java.awt.Color.HSBtoRGB(hue, 1f, 1f)
            jImageDisplay.drawPixel(x, y, rgbColor)
        }
    }
    return listOf(xCoord, yCoord)
}


internal val SizeDisplay.display
    get() = first * second



// https://github.com/JetBrains/compose-jb/tree/master/tutorials/Swing_Integration