package us.aaron_johnson.gameengine.GameEngine.Math.Rectilinear;

/**
 * Created by combu on 12/9/2017.
 */

public class Vector {
    public int x;

    public Vector(){
        x = 0;
    }

    public Vector(int x){
        this.x = x;
    }

    public Vector(final Vector other){
        this.x = other.x;
    }

    public void add(final Vector other){
        x += other.x;
    }

    public void sub(final Vector other){
        x -= other.x;
    }

    public void scale(int s){
        x *= s;
    }

    public void invScale(int is){   x /= is;    }

    public int magnitude(){
        return x;
    }

    public static Vector Add(final Vector a, final Vector b){
        return new Vector(a.x + b.x);
    }

    public static Vector Sub(final Vector a, final Vector b){
        return new Vector(a.x - b.x);
    }

    public static int DotProduct(final Vector a, final Vector b){
        return a.x*b.x;
    }

    public static int Distance(final Vector a, final Vector b){
        return Math.abs(b.x-a.x);
    }
}
