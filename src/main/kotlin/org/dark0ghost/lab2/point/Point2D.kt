package org.dark0ghost.lab2.point


data class Point2D constructor(var x: Double, var y: Double){
    constructor(x: Int = 0, y: Int = 0) : this(x.toDouble(), y.toDouble())
    constructor(): this(0.0, 0.0)
}

infix operator fun Point2D.plus(point2D: Point2D): Point2D = Point2D(x + point2D.x, y + point2D.y)

infix operator fun Point2D.minus(point2D: Point2D): Point2D = Point2D(x - point2D.x, y - point2D.y)

