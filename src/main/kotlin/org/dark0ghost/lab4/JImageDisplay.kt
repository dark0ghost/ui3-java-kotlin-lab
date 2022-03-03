package org.dark0ghost.lab4

import com.intellij.ide.actions.WindowAction.DecrementHeight
import java.awt.image.BufferedImage
import javax.swing.JComponent

class JImageDisplay(private val height: UInt, private val weight: UInt): JComponent() {
    private lateinit var bufferedImage: BufferedImage
}