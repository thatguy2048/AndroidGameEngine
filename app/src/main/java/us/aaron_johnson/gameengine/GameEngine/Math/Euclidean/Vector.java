package us.aaron_johnson.gameengine.GameEngine.Math.Euclidean;

/**
 * Created by combu on 11/30/2017.
 */

public class Vector {
    public float x;

    public Vector(){
        x = 0;
    }

    public Vector(float x){
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

    public void scale(float s){
        x *= s;
    }

    public void invScale(float is){
        x /= is;
    }

    public float magnitude(){
        return x;
    }

    public static Vector Add(final Vector a, final Vector b){
        return new Vector(a.x + b.x);
    }

    public static Vector Sub(final Vector a, final Vector b){
        return new Vector(a.x - b.x);
    }

    public static float DotProduct(final Vector a, final Vector b){
        return a.x*b.x;
    }

    public static float Distance(final Vector a, final Vector b){
        return Math.abs(b.x - a.x);
    }
}
