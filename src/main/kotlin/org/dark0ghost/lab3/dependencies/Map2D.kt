package org.dark0ghost.lab3.dependencies

/**
 * This class represents a simple two-dimensional map composed of square cells.
 * Each cell specifies the cost of traversing that cell.
 */
class Map2D(width: Int, height: Int) {
    /** Returns the width of the map.  */
    /** The width of the map.  */
    private val width: Int
    /** Returns the height of the map.  */
    /** The height of the map.  */
    private val height: Int

    /**
     * The actual map data that the pathfinding algorithm needs to navigate.
     */
    private val cells: Array<IntArray>

    /** The starting location for performing the A* pathfinding.  */
    private var start: Location

    /** The ending location for performing the A* pathfinding.  */
    private var finish: Location

    /** Creates a new 2D map, with the specified width and height.  */
    init {
        if (width <= 0 || height <= 0) {
            throw IllegalArgumentException(
                "width and height must be positive values; got " + width +
                        "x" + height
            )
        }
        this.width = width
        this.height = height
        cells = Array(width) { IntArray(height) }

        // Make up some coordinates for start and finish.
        start = Location(0, height / 2)
        finish = Location(width - 1, height / 2)
    }

    /**
     * This helper method checks the specified coordinates to see if they are
     * within the map's boundaries.  If the coordinates are not within the map
     * then the method throws an `IllegalArgumentException`.
     */
    private fun checkCoords(x: Int, y: Int) {
        if (x < 0 || x > width) {
            throw IllegalArgumentException(
                ("x must be in range [0, " +
                        width + "), got " + x)
            )
        }
        if (y < 0 || y > height) {
            throw IllegalArgumentException(
                ("y must be in range [0, " +
                        height + "), got " + y)
            )
        }
    }

    /**
     * Returns true if the specified coordinates are contained within the map
     * area.
     */
    fun contains(x: Int, y: Int): Boolean {
        return ((x >= 0) && (x < width) && (y >= 0) && (y < height))
    }

    /** Returns true if the location is contained within the map area.  */
    operator fun contains(loc: Location): Boolean {
        return contains(loc.xCoord, loc.yCoord)
    }

    /** Returns the stored cost value for the specified cell.  */
    fun getCellValue(x: Int, y: Int): Int {
        checkCoords(x, y)
        return cells[x][y]
    }

    /** Returns the stored cost value for the specified cell.  */
    fun getCellValue(loc: Location): Int {
        return getCellValue(loc.xCoord, loc.yCoord)
    }

    /** Sets the cost value for the specified cell.  */
    fun setCellValue(x: Int, y: Int, value: Int) {
        checkCoords(x, y)
        cells[x][y] = value
    }

    /**
     * Returns the starting location for the map.  This is where the generated
     * path will begin from.
     */
    fun getStart(): Location {
        return start
    }

    /**
     * Sets the starting location for the map.  This is where the generated path
     * will begin from.
     */
    fun setStart(loc: Location?) {
        if (loc == null) throw NullPointerException("loc cannot be null")
        start = loc
    }

    /**
     * Returns the ending location for the map.  This is where the generated
     * path will terminate.
     */
    fun getFinish(): Location {
        return finish
    }

    /**
     * Sets the ending location for the map.  This is where the generated path
     * will terminate.
     */
    fun setFinish(loc: Location?) {
        if (loc == null) throw NullPointerException("loc cannot be null")
        finish = loc
    }
}
