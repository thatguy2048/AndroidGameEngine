package us.aaron_johnson.gameengine.GameEngine.Math.Euclidean;

import us.aaron_johnson.gameengine.GameEngine.Math.Utils;

/**
 * Created by combu on 11/11/2017.
 */

public class Vector2 extends Vector{
    public float y;

    public Vector2() {
        super();
        y = 0;
    }

    public Vector2(float x, float y) {
        super(x);
        this.y = y;
    }

    public Vector2(final Vector2 other){
        super(other);
        this.y = other.y;
    }


    public void add(final Vector2 other){
        super.add(other);
        y += other.y;
    }

    public void sub(final Vector2 other){
        super.sub(other);
        y -= other.y;
    }

    public void scale(float s) {
        super.scale(s);
        y *= s;
    }

    public void invScale(float is){
        super.invScale(is);
        y /= is;
    }

    public float sqrMag() {
        return x*x+y*y;
    }

    public float magnitude(){
        return (float)Math.sqrt(sqrMag());
    }

    public void normalize() {
        invScale(magnitude());
    }

    @Override
    public String toString() {
        return "("+x+","+y+")";
    }

    public static Vector2 Add(final Vector2 a, final Vector2 b){
        return new Vector2(a.x+b.x, a.y+b.y);
    }

    public static Vector2 Sub(final Vector2 a, final Vector2 b){
        return new Vector2(a.x-b.x, a.y-b.y);
    }

    public static float DotProduct(final Vector2 a, final Vector2 b){
        return Vector.DotProduct(a,b)+a.y*b.y;
    }

    protected static Utils U = new Utils();

    public static float SqrDistance(final Vector2 a, final Vector2 b){
        return U.square(b.x-a.x)+U.square(b.y-a.y);
    }

    public static float Distance(final Vector2 a, final Vector2 b){
        return (float)Math.sqrt(SqrDistance(a,b));
    }
}
