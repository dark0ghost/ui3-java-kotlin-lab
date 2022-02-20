package org.dark0ghost.lab2;

import org.dark0ghost.lab2.point.Point2D;

public class Point2DMain {
    public static void main(String[] args) {
        var myPoint = new Point2D();
        String sb = myPoint.getX() + " " + myPoint.getY();

        System.out.println(sb);


        myPoint = new Point2D(5, 3); // создает точку в (5,3)
        sb = myPoint.getX() + " " + myPoint.getY();
        System.out.println(sb);
    }
}
