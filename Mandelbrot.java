package lab5;
import java.awt.geom.Rectangle2D;

public class Mandelbrot extends FractalGenerator{
    public void getInitialRange(Rectangle2D.Double range){
        range.x = -2;
        range.y = -1.5;
        range.height = 3;
        range.width = 3;

    }

    public int numIterations(double x, double y){
        double ABSz = 0;
        double xCoord = 0;
        double yCoord = 0;
        double vspom = 0;
        int Iterations = 0;
        while ((ABSz*ABSz)<4.0){
            Iterations++;
            vspom = xCoord * xCoord - yCoord * yCoord + x;
            yCoord = 2*xCoord*yCoord+y;
            xCoord = vspom;
            ABSz = Math.sqrt(xCoord*xCoord+yCoord*yCoord);
        if (Iterations==MAX_ITERATIONS) {return -1;}
        }
        return Iterations;
    };

    @Override
    public String toString() {
        return "Mandelbrot fractal";
    }
}

