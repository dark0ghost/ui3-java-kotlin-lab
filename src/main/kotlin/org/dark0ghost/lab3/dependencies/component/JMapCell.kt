package org.dark0ghost.lab3.dependencies.component

import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import javax.swing.JComponent


/**
 * This class is a custom Swing component for representing a single map cell in
 * a 2D map.  The cell has several different kinds of state, but the most basic
 * state is whether the cell is passable or not.
 */
class JMapCell(pass: Boolean = true) : JComponent() {
    /** True indicates that the cell is an endpoint, either start or finish.  */
    var endpoint = false
    set(value) {
        field = value
        updateAppearance()
    }

    /** True indicates that the cell is passable; false means it is not.  */
    var isPassable = pass
    set(value) {
        field = value
        updateAppearance()
    }

    /**
     * True indicates that this cell is part of the path between start and end.
     */
    var path = false
    set(value) {
        /** Marks this cell as part of the path discovered by the A* algorithm.  */
        field = value
        updateAppearance()
    }
    /**
     * Construct a new map cell with the specified "passability."  An input of
     * true means the cell is passable.
     */
    /** Construct a new map cell, which is passable by default.  */
    init {
        // Set the preferred cell size, to drive the initial window size.
        preferredSize = CELL_SIZE
    }



    /** Toggles the current "passable" state of the map cell.  */
    fun togglePassable() {
        isPassable = !isPassable
    }



    /**
     * This helper method updates the background color to match the current
     * internal state of the cell.
     */
    private fun updateAppearance() {
        if (isPassable) {
            // Passable cell.  Indicate its state with a border.
            background = Color.WHITE
            if (endpoint) background = Color.CYAN else if (path) background = Color.GREEN
        } else {
            // Impassable cell.  Make it all red.
            background = Color.RED
        }
    }

    /**
     * Implementation of the paint method to draw the background color into the
     * map cell.
     */
    override fun paintComponent(g: Graphics) {
        g.setColor(background)
        g.fillRect(0, 0, width, height)
    }

    companion object {
        private val CELL_SIZE = Dimension(12, 12)
    }
}