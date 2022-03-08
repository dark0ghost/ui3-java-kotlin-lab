package org.dark0ghost.lab4

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposePanel
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.dark0ghost.lab4.fractal.FractalGenerator
import org.dark0ghost.lab4.fractal.FractalGenerator.Companion.getCoord
import org.dark0ghost.lab4.fractal.Mandelbrot
import org.dark0ghost.lab4.ui.component.JImageDisplay
import java.awt.BorderLayout
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.geom.Rectangle2D
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.SwingUtilities
import javax.swing.WindowConstants


internal typealias SizeDisplay = Pair<UInt, UInt>

private val sizeDisplay = 600u to 600u

private val jImageDisplay: JImageDisplay = JImageDisplay(sizeDisplay.first, sizeDisplay.second)

private val generator: FractalGenerator = Mandelbrot()

private var range: Rectangle2D.Double = Rectangle2D.Double()

fun main() = SwingUtilities.invokeLater {
    generator.getInitialRange(range)
    jImageDisplay.addMouseListener(Mouse())
    jImageDisplay.layout = BorderLayout()
    val window = JFrame("Lab4")
    val button = JButton("Reset")

    //button.preferredSize = Dimension(100, 100)
    button.addActionListener {
        generator.getInitialRange(range)
        drawFractal()
    }

    // creating ComposePanel
    val composePanel = ComposePanel()

    window.apply {
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        contentPane.apply {
            add(jImageDisplay)
            add(button, BorderLayout.SOUTH)
            //add(composePanel, BorderLayout.SOUTH)
        }
        isResizable = false
        isVisible = true
        setSize(sizeDisplay.first.toInt(), sizeDisplay.second.toInt())
    }

    composePanel.setContent {
        ComposeContent()
    }

    window.pack()
    drawFractal()
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

private class Mouse : MouseAdapter() {
    override fun mouseClicked(e: MouseEvent) {
        generator.apply {
            val x = e.x
            val xCoord: Double = getCoord(
                range.x,
                range.x + range.width, sizeDisplay.display, x
            )

            val y = e.y
            val yCoord: Double = getCoord(
                range.y,
                range.y + range.height, sizeDisplay.display, y
            )
            recenterAndZoomRange(range, xCoord, yCoord, 0.5)
        }
        drawFractal()
    }
}


private fun drawFractal() {
    runBlocking {
        (0u until sizeDisplay.first).forEach { x ->
            (0u until sizeDisplay.second).forEach { y ->
                launch(Dispatchers.Default) {
                    println("x - $x, y - $y")
                    drawPixel(x.toInt(), y.toInt())
                }
            }
        }
    }
    println("repaint")
    jImageDisplay.repaint()
}




fun drawPixel(x: Int, y: Int) {
    val xCoord = getCoord(
        range.x, range.x + range.width,
        sizeDisplay.display, x
    )
    val yCoord = getCoord(
        range.y, range.y + range.width,
        sizeDisplay.display, y
    )
    when (val numIter = generator.numIterations(xCoord to yCoord)) {
        -1 -> jImageDisplay.drawPixel(x, y, 0)
        else -> {
            val hue = 0.7f + numIter.toFloat() / 200f
            val rgbColor: Int = java.awt.Color.HSBtoRGB(hue, 1f, 1f)
            println("rgb color $rgbColor - iter $numIter")
            jImageDisplay.drawPixel(x, y, rgbColor)
        }
    }
}


internal val SizeDisplay.display
    get() = first



// https://github.com/JetBrains/compose-jb/tree/master/tutorials/Swing_Integration