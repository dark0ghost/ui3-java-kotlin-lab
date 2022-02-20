package org.dark0ghost.lab2.point

import kotlin.math.pow


data class Point2D constructor(var x: Double, var y: Double){
    constructor(x: Int = 0, y: Int = 0) : this(x.toDouble(), y.toDouble())
    constructor(): this(0.0, 0.0)
}

infix operator fun Point2D.plus(point2D: Point2D): Point2D = Point2D((x.pow(2) + point2D.x.pow(2)).pow(0.5), (y.pow(2) + point2D.y.pow(2)).pow(0.5))

infix operator fun Point2D.minus(point2D: Point2D): Point2D = Point2D((x.pow(2) - point2D.x.pow(2)).pow(0.5), (y.pow(2) - point2D.y.pow(2)).pow(0.5))

