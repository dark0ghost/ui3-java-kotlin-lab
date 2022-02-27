package org.dark0ghost.lab3.dependencies

/**
 * This class stores the basic state necessary for the A* algorithm to compute a
 * path across a map.  This state includes a collection of "open waypoints" and
 * another collection of "closed waypoints."  In addition, this class provides
 * the basic operations that the A* pathfinding algorithm needs to perform its
 * processing.
 */
class AStarState(map: Map2D?) {
    /** Returns the map that the A* pathfinder is navigating.  */
    /** This is a reference to the map that the A* algorithm is navigating.  */
    val map: Map2D

    /**
     * Initialize a new state object for the A* pathfinding algorithm to use.
     */
    init {
        if (map == null) throw NullPointerException("map cannot be null")
        this.map = map
    }// TODO:  Implement.

    /**
     * This method scans through all open waypoints, and returns the waypoint
     * with the minimum total cost.  If there are no open waypoints, this method
     * returns `null`.
     */
    val minOpenWaypoint: Waypoint?
        get() =// TODO:  Implement.
            null

    /**
     * This method adds a waypoint to (or potentially updates a waypoint already
     * in) the "open waypoints" collection.  If there is not already an open
     * waypoint at the new waypoint's location then the new waypoint is simply
     * added to the collection.  However, if there is already a waypoint at the
     * new waypoint's location, the new waypoint replaces the old one *only
     * if* the new waypoint's "previous cost" value is less than the current
     * waypoint's "previous cost" value.
     */
    fun addOpenWaypoint(newWP: Waypoint?): Boolean {
        // TODO:  Implement.
        return false
    }

    /** Returns the current number of open waypoints.  */
    fun numOpenWaypoints(): Int {
        // TODO:  Implement.
        return 0
    }

    /**
     * This method moves the waypoint at the specified location from the
     * open list to the closed list.
     */
    fun closeWaypoint(loc: Location) {
        // TODO:  Implement.
    }

    /**
     * Returns true if the collection of closed waypoints contains a waypoint
     * for the specified location.
     */
    fun isLocationClosed(loc: Location): Boolean {
        // TODO:  Implement.
        return false
    }
}
