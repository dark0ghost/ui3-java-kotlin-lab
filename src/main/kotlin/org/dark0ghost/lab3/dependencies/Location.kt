package org.dark0ghost.lab3.dependencies

/**
 * This class represents a specific location in a 2D map.  Coordinates are
 * integer values.
 */
data class Location(
    /** X coordinate of this location.  */
    var xCoord: Int = 0,
    /** Y coordinate of this location.  */
    var yCoord: Int = 0
)
