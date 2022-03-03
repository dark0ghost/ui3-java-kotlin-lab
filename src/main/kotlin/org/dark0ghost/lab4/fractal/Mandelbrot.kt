package org.dark0ghost.lab4.fractal

import space.kscience.kmath.complex.Complex
import space.kscience.kmath.complex.ComplexField.plus
import space.kscience.kmath.complex.ComplexField.times
import space.kscience.kmath.complex.r
import java.awt.geom.Rectangle2D

class Mandelbrot: FractalGenerator {
    private fun mand(z0: Complex, max: Int = MAX_ITERATIONS): Int {
        var z: Complex = z0
        for (t in 0 until max) {
            if (z.r > 2.0) return t
            z = z.times(z) + (z0)
        }
        return -1
    }


    companion object {
        const val MAX_ITERATIONS = 2000

        private val Pair<Double, Double>.complex
            get() = Complex(first, second)
    }

    /**
     * Sets the specified rectangle to contain the initial range suitable for
     * the fractal being generated.
     */
    override fun getInitialRange(range: Rectangle2D.Double) {
        range.apply {
            x = (-2).toDouble()
            y = -1.5
            height = 3.0
            width = 3.0
        }
    }

    /**
     * Given a coordinate *x* + *iy* in the complex plane,
     * computes and returns the number of iterations before the fractal
     * function escapes the bounding area for that point.  A point that
     * doesn't escape before the iteration limit is reached is indicated
     * with a result of -1.
     */
    override fun numIterations(complexPair: Pair<Double, Double>): Int = mand(complexPair.complex)

}