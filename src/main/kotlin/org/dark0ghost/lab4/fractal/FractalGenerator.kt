package org.dark0ghost.lab4.fractal

import java.awt.geom.Rectangle2D


/**
 * This class provides the common interface and operations for fractal
 * generators that can be viewed in the Fractal Explorer.
 */
interface FractalGenerator {
    /**
     * Sets the specified rectangle to contain the initial range suitable for
     * the fractal being generated.
     */
    fun getInitialRange(range: Rectangle2D.Double)

    /**
     * Updates the current range to be centered at the specified coordinates,
     * and to be zoomed in or out by the specified scaling factor.
     */
    fun recenterAndZoomRange(
        range: Rectangle2D.Double,
        centerX: Double, centerY: Double, scale: Double
    ) {
        val newWidth = range.width * scale
        val newHeight = range.height * scale
        range.x = centerX - newWidth / 2
        range.y = centerY - newHeight / 2
        range.width = newWidth
        range.height = newHeight
    }

    /**
     * Given a coordinate *x* + *iy* in the complex plane,
     * computes and returns the number of iterations before the fractal
     * function escapes the bounding area for that point.  A point that
     * doesn't escape before the iteration limit is reached is indicated
     * with a result of -1.
     */
    fun numIterations(complex: Pair<Double, Double>): Int

    companion object {
        /**
         * This static helper function takes an integer coordinate and converts it
         * into a double-precision value corresponding to a specific range.  It is
         * used to convert pixel coordinates into double-precision values for
         * computing fractals, etc.
         *
         * @param rangeMin the minimum value of the floating-point range
         * @param rangeMax the maximum value of the floating-point range
         *
         * @param size the size of the dimension that the pixel coordinate is from.
         * For example, this might be the image width, or the image height.
         *
         * @param coord the coordinate to compute the double-precision value for.
         * The coordinate should fall in the range [0, size].
         */
        fun getCoord(
            rangeMin: Double, rangeMax: Double,
            size: UInt, coord: Int
        ): Double {
            require(coord in 0 until size.toInt())
            val range = rangeMax - rangeMin
            return rangeMin + range * coord.toDouble() / size.toDouble()
        }
    }
}
