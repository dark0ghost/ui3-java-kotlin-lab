package org.dark0ghost.lab3.dependencies

import kotlin.math.sqrt

object AStarPathfinder {
    /**
     * This constant holds a maximum cutoff limit for the cost of paths.  If a
     * particular waypoint happens to exceed this cost limit, the waypoint is
     * discarded.
     */
    private const val COST_LIMIT = 1e6f

    /**
     * Attempts to compute a path that navigates between the start and end
     * locations of the specified map.  If a path can be found, the waypoint of
     * the *final* step in the path is returned; that waypoint can be
     * used to walk backwards to the starting point.  If no path can be found,
     * `null` is returned.
     */
    fun computePath(map: Map2D): Waypoint? {
        // Variables necessary for the A* search.
        val state = AStarState(map)
        val finishLoc = map.finish

        // Set up a starting waypoint to kick off the A* search.
        val start = Waypoint(map.start, null)
        start.setCosts(0f, estimateTravelCost(start.location, finishLoc))
        state.addOpenWaypoint(start)
        var finalWaypoint: Waypoint? = null
        var foundPath = false
        while (!foundPath && state.numOpenWaypoints() > 0) {
            // Find the "best" (i.e. lowest-cost) waypoint so far.
            val best: Waypoint? = state.minOpenWaypoint

            // If the best location is the finish location then we're done!
            if (best != null) {
                if (best.location == finishLoc) {
                    finalWaypoint = best
                    foundPath = true
                }
                // Add/update all neighbors of the current best location.  This is
                // equivalent to trying all "next steps" from this location.
                takeNextStep(best, state)

                // Finally, move this location from the "open" list to the "closed"
                // list.
                state.closeWaypoint(best.location)
            }
        }
        return finalWaypoint
    }

    /**
     * This static helper method takes a waypoint, and generates all valid "next
     * steps" from that waypoint.  The new waypoints are added to the "open
     * waypoints" collection of the passed-in A* state object.
     */
    private fun takeNextStep(currWP: Waypoint, state: AStarState) {
        val loc = currWP.location
        val map: Map2D = state.map
        for (y in loc.yCoord - 1..loc.yCoord + 1) {
            for (x in loc.xCoord - 1..loc.xCoord + 1) {
                val nextLoc = Location(x, y)

                // If "next location" is outside the map, skip it.
                if (!map.contains(nextLoc)) continue

                // If "next location" is this location, skip it.
                if (nextLoc === loc) continue

                // If this location happens to already be in the "closed" set
                // then continue on with the next location.
                if (state.isLocationClosed(nextLoc)) continue

                // Make a waypoint for this "next location."
                val nextWP = Waypoint(nextLoc, currWP)

                // OK, we cheat and use the cost estimate to compute the actual
                // cost from the previous cell.  Then, we add in the cost from
                // the map cell we step onto, to incorporate barriers etc.
                var prevCost = currWP.previousCost +
                        estimateTravelCost(
                            currWP.location,
                            nextWP.location
                        )
                prevCost += map.getCellValue(nextLoc).toFloat()

                // Skip this "next location" if it is too costly.
                if (prevCost >= COST_LIMIT) continue
                nextWP.setCosts(
                    prevCost,
                    estimateTravelCost(nextLoc, map.finish)
                )

                // Add the waypoint to the set of open waypoints.  If there
                // happens to already be a waypoint for this location, the new
                // waypoint only replaces the old waypoint if it is less costly
                // than the old one.
                state.addOpenWaypoint(nextWP)
            }
        }
    }

    /**
     * Estimates the cost of traveling between the two specified locations.
     * The actual cost computed is just the straight-line distance between the
     * two locations.
     */
    private fun estimateTravelCost(currLoc: Location, destLoc: Location): Float {
        val dx = destLoc.xCoord - currLoc.xCoord
        val dy = destLoc.yCoord - currLoc.yCoord
        return sqrt((dx * dx + dy * dy).toDouble()).toFloat()
    }
}
