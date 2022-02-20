package org.dark0ghost.lab2.test

import org.dark0ghost.lab2.point.Point2D
import org.dark0ghost.lab2.point.minus
import org.dark0ghost.lab2.point.plus
import org.junit.jupiter.api.Test

class PointTest {

    @Test
    fun testInit(){
        var myPoint = Point2D()

        assert(myPoint.x == 0.toDouble())
        assert(myPoint.y == 0.toDouble())

        myPoint = Point2D(5, 3)

        assert(myPoint.x == 5.toDouble())
        assert(myPoint.y == 3.toDouble())
    }

    @Test
    fun testEQ(){
        var myPoint1 = Point2D()
        var myPoint2 = Point2D()

        assert(myPoint1 == myPoint2)

        myPoint1 = Point2D(2, 1)
        myPoint2 = Point2D(2, 1)

        assert(myPoint1 == myPoint2)

        myPoint1 = Point2D(2)
        myPoint2 = Point2D(2, 1)

        assert(myPoint1 != myPoint2)
    }

    @Test
    fun testSetter(){
        val myPoint= Point2D()
        myPoint.x = 1.toDouble()

        assert(myPoint.x == 1.0)

        myPoint.x = 19.toDouble()

        assert(myPoint.x == 19.0)
    }

    @Test
    fun testPlus(){
        val myPointZero = Point2D()

        val myPoint1 = Point2D(1, 9)

        val resPoint2D = myPointZero + myPoint1

        assert(myPoint1 == resPoint2D)
    }

    @Test
    fun testMinus(){
        val myPoint1 = Point2D(2, 10)

        val myPoint2 = Point2D(1, 9)

        val resPoint2D = myPoint1 - myPoint2

        val testData = Point2D(1, 1)

        assert(testData == resPoint2D)
    }
}