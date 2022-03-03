package org.dark0ghost.lab3.dependencies

import org.dark0ghost.lab3.dependencies.AStarPathfinder.computePath
import org.dark0ghost.lab3.dependencies.component.JMapCell
import java.awt.*
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.SwingUtilities


class AStarApp(w: Int, h: Int) {
    /** The number of grid cells in the X direction.  */
    private val width: Int

    /** The number of grid cells in the Y direction.  */
    private val height: Int

    /** The location where the path starts from.  */
    private val startLoc: Location

    /** The location where the path is supposed to finish.  */
    private val finishLoc: Location

    /**
     * This is a 2D array of UI components that provide display and manipulation
     * of the cells in the map.
     */
    private lateinit var mapCells: Array<Array<JMapCell>>

    /**
     * This inner class handles mouse events in the main grid of map cells, by
     * modifying the cells based on the mouse button state and the initial edit
     * that was performed.
     */
    private inner class MapCellHandler : MouseListener {
        /**
         * This value will be true if a mouse button has been pressed and we are
         * currently in the midst of a modification operation.
         */
        private var modifying = false

        /**
         * This value records whether we are making cells passable or
         * impassable.  Which it is depends on the original state of the cell
         * that the operation was started within.
         */
        private var makePassable = false

        /** Initiates the modification operation.  */
        override fun mousePressed(e: MouseEvent) {
            modifying = true
            val cell: JMapCell = e.source as JMapCell

            // If the current cell is passable then we are making them
            // impassable; if it's impassable then we are making them passable.
            makePassable = !cell.isPassable
            cell.isPassable = makePassable
        }

        /** Ends the modification operation.  */
        override fun mouseReleased(e: MouseEvent?) {
            modifying = false
        }

        /**
         * If the mouse has been pressed, this continues the modification
         * operation into the new cell.
         */
        override fun mouseEntered(e: MouseEvent) {
            if (modifying) {
                val cell: JMapCell = e.source as JMapCell
                cell.isPassable = makePassable
            }
        }

        /** Not needed for this handler.  */
        override fun mouseExited(e: MouseEvent?) {
            // This one we ignore.
        }

        /** Not needed for this handler.  */
        override fun mouseClicked(e: MouseEvent?) {
            // And this one too.
        }
    }

    /**
     * Creates a new instance of AStarApp with the specified map width and
     * height.
     */
    init {
        require(w > 0) { "w must be > 0; got $w" }
        require(h > 0) { "h must be > 0; got $h" }
        width = w
        height = h
        startLoc = Location(2, h / 2)
        finishLoc = Location(w - 3, h / 2)
    }

    /**
     * Simple helper method to set up the Swing user interface.  This is called
     * from the Swing event-handler thread to be threadsafe.
     */
    private fun initGUI() {
        val frame = JFrame("Pathfinder")
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        val contentPane: Container = frame.contentPane
        contentPane.layout = BorderLayout()

        // Use GridBagLayout because it actually respects the preferred size
        // specified by the components it lays out.
        val gbLayout = GridBagLayout()
        val gbConstraints = GridBagConstraints()
        gbConstraints.fill = GridBagConstraints.BOTH
        gbConstraints.weightx = 1.0
        gbConstraints.weighty = 1.0
        gbConstraints.insets[0, 0, 1] = 1
        val mapPanel = JPanel(gbLayout)
        mapPanel.background = Color.GRAY
        mapCells = Array(width){
            Array(height){JMapCell()}
        }
        val cellHandler = MapCellHandler()
        for (y in 0 until height) {
            for (x in 0 until width) {
                mapCells[x][y] = JMapCell()
                gbConstraints.gridx = x
                gbConstraints.gridy = y
                gbLayout.setConstraints(mapCells[x][y], gbConstraints)
                mapPanel.add(mapCells[x][y])
                mapCells[x][y].addMouseListener(cellHandler)
            }
        }
        contentPane.add(mapPanel, BorderLayout.CENTER)
        val findPathButton = JButton("Find Path")
        findPathButton.addActionListener { findAndShowPath() }
        contentPane.add(findPathButton, BorderLayout.SOUTH)
        frame.pack()
        frame.isVisible = true
        mapCells[startLoc.xCoord][startLoc.yCoord].endpoint = true
        mapCells[finishLoc.xCoord][finishLoc.yCoord].endpoint = true
    }

    /** Kicks off the application.  Called from the [.main] method.  */
    fun start() {
        SwingUtilities.invokeLater { initGUI() }
    }

    /**
     * This helper method attempts to compute a path using the current map
     * state.  The implementation is rather slow; a new [Map2D] object is
     * created, and initialized from the current application state.  Then the A*
     * pathfinder is called, and if a path is found, the display is updated to
     * show the path that was found.  (A better solution would use the Model
     * View Controller design pattern.)
     */
    private fun findAndShowPath() {
        // Create a Map2D object containing the current state of the user input.
        val map = Map2D(width, height)
        map.start = startLoc
        map.finish = finishLoc
        for (y in 0 until height) {
            for (x in 0 until width) {
                mapCells[x][y].path = false
                if (mapCells[x][y].isPassable) map.setCellValue(x, y, 0) else map.setCellValue(x, y, Int.MAX_VALUE)
            }
        }

        // Try to compute a path.  If one can be computed, mark all cells in the
        // path.
        var wp = computePath(map)
        while (wp != null) {
            val (xCoord, yCoord) = wp.location
            mapCells[xCoord][yCoord].path = false
            wp = wp.previous
        }
    }
}
