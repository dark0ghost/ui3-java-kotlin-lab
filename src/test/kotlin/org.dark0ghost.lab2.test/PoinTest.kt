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
        val myPoint = Point2D(6, 8)

        val myPoint1 = Point2D(8, 6)

        val resPoint2D = myPoint + myPoint1

        val dataTest = Point2D(10, 10)

        assert(dataTest == resPoint2D)
    }

    @Test
    fun testMinus(){
        val myPoint1 = Point2D(10, 10)

        val myPoint2 = Point2D(6, 8)

        val resPoint2D = myPoint1 - myPoint2

        val testData = Point2D(8, 6)

        assert(testData == resPoint2D)
    }
}