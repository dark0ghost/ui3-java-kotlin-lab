package org.dark0ghost.lab3

import org.dark0ghost.lab3.dependencies.AStarApp


/**
 * Entry-point for the application.  No command-line arguments are
 * recognized at this time.
 */
fun main(args: Array<String>) {
    val app = AStarApp(40, 30)
    app.start()
}