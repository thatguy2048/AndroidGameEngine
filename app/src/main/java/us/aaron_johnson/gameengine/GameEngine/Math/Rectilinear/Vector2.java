package us.aaron_johnson.gameengine.GameEngine.Math.Rectilinear;

/**
 * Created by combu on 12/9/2017.
 */

public class Vector2 extends Vector {
    public int y;

    public Vector2() {
        super();
        y = 0;
    }

    public Vector2(int x, int y) {
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

    public void scale(int s) {
        super.scale(s);
        y *= s;
    }

    public void invScale(int is){
        super.invScale(is);
        y /= is;
    }

    public int sqrMag() {
        return x*x+y*y;
    }

    public int magnitude(){
        return (int)Math.sqrt(sqrMag());
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

    public static int Distance(final Vector2 a, final Vector2 b){
        return Math.abs(b.y-a.y) + Vector.Distance(a,b);
    }
}
