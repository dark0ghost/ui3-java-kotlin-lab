package org.dark0ghost.lab3.dependencies

/**
 * This class stores the basic state necessary for the A* algorithm to compute a
 * path across a map.  This state includes a collection of "open waypoints" and
 * another collection of "closed waypoints."  In addition, this class provides
 * the basic operations that the A* pathfinding algorithm needs to perform its
 * processing.
 */
class AStarState(val map: Map2D) {
    /** Returns the map that the A* pathfinder is navigating.  */
    /** This is a reference to the map that the A* algorithm is navigating.  */


    private val openWaypoints: HashMap<Location, Waypoint> = hashMapOf()
    private val closeWaypoints: HashMap<Location, Waypoint> = hashMapOf()


    /**
     * This method scans through all open waypoints, and returns the waypoint
     * with the minimum total cost.  If there are no open waypoints, this method
     * returns `null`.
     */
    val minOpenWaypoint: Waypoint?
        get() = openWaypoints.minOfOrNull { it.value }

    /**
     * This method adds a waypoint to (or potentially updates a waypoint already
     * in) the "open waypoints" collection.  If there is not already an open
     * waypoint at the new waypoint's location then the new waypoint is simply
     * added to the collection.  However, if there is already a waypoint at the
     * new waypoint's location, the new waypoint replaces the old one *only
     * if* the new waypoint's "previous cost" value is less than the current
     * waypoint's "previous cost" value.
     */
    fun addOpenWaypoint(newWP: Waypoint): Boolean {
        openWaypoints[newWP.location]?.let {
            return false
        }
        openWaypoints[newWP.location] = newWP
        return true
    }

    /** Returns the current number of open waypoints.  */
    fun numOpenWaypoints(): Int = openWaypoints.size


    /**
     * This method moves the waypoint at the specified location from the
     * open list to the closed list.
     */
    fun closeWaypoint(loc: Location) {
        val res = openWaypoints[loc] ?: return
        openWaypoints.remove(loc)
        closeWaypoints[loc] = res
    }

    /**
     * Returns true if the collection of closed waypoints contains a waypoint
     * for the specified location.
     */
    fun isLocationClosed(loc: Location): Boolean {
        closeWaypoints[loc] ?: return false
        return true
    }
}

