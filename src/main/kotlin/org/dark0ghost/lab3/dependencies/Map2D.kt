package org.dark0ghost.lab3.dependencies

/**
 * This class represents a simple two-dimensional map composed of square cells.
 * Each cell specifies the cost of traversing that cell.
 */
class Map2D constructor(width: UInt, height: UInt) {

    constructor(width: Float, height: Float): this(width.toUInt(), height.toUInt())

    private val height: UInt = height
    private val width: UInt = width

    /**
     * The actual map data that the pathfinding algorithm needs to navigate.
     */
    private val cells: Array<IntArray> = Array(width.toInt()) { IntArray(height.toInt()) }

    /** The starting location for performing the A* pathfinding.  */
    var start: Location = Location(0, height.toInt() / 2)

    /** The ending location for performing the A* pathfinding.  */
    var finish: Location = Location(width.toInt() - 1, height.toInt() / 2)

    /**
     * Returns true if the specified coordinates are contained within the map
     * area.
     */
    fun contains(x: Int, y: Int): Boolean {
        return ((x >= 0) && (x < width.toInt()) && (y >= 0) && (y < height.toInt()))
    }

    /** Returns true if the location is contained within the map area.  */
    infix operator fun contains(loc: Location): Boolean {
        return contains(loc.xCoord, loc.yCoord)
    }

    /** Returns the stored cost value for the specified cell.  */
    fun getCellValue(x: Int, y: Int): Int {
        return cells[x][y]
    }

    /** Returns the stored cost value for the specified cell.  */
    fun getCellValue(loc: Location): Int {
        return getCellValue(loc.xCoord, loc.yCoord)
    }

    /** Sets the cost value for the specified cell.  */
    fun setCellValue(x: Int, y: Int, value: Int) {
        cells[x][y] = value
    }
}
