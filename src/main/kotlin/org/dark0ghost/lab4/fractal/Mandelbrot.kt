package org.dark0ghost.lab4.fractal

import java.awt.geom.Rectangle2D

class Mandelbrot: FractalGenerator {

    private fun numIterations(x: Double, y: Double): Int {
        /** Start with iterations at 0.  */
        var iteration = 0u
        /** Initialize zreal and zimaginary.  */
        var zreal = 0.0
        var zimaginary = 0.0
        /**
         * Вычисляем Zn = Zn-1 ^ 2 + c, где значения представляют собой комплексные числа, представленные
         * по zreal и zimaginary, Z0 = 0, а c - особая точка в
         * фрактал, который мы показываем (заданный x и y). Это повторяется
         * до Z ^ 2> 4 (абсолютное значение Z больше 2) или максимум
         * достигнуто количество итераций.
         */
        while (iteration < MAX_ITERATIONS &&
            zreal * zreal + zimaginary * zimaginary < 4
        ) {
            val zrealUpdated = zreal * zreal - zimaginary * zimaginary + x
            val zimaginaryUpdated = 2 * zreal * zimaginary + y
            zreal = zrealUpdated
            zimaginary = zimaginaryUpdated
            iteration += 1u
        }
        /**
         * Если количество максимальных итераций достигнуто, возвращаем -1, чтобы
         * указать, что точка не вышла за границу.
         */
        println("iter -- $iteration,x:$x,y:$y")
        return if (iteration == MAX_ITERATIONS) {
            -1
        } else iteration.toInt()
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
    override fun numIterations(complex: Pair<Double, Double>): Int = numIterations(complex.first, complex.second)

    companion object {
        const val MAX_ITERATIONS = 2000u
    }
}