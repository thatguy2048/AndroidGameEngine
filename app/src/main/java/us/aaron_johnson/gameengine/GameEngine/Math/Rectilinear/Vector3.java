package us.aaron_johnson.gameengine.GameEngine.Math.Rectilinear;

/**
 * Created by combu on 12/9/2017.
 */

public class Vector3 extends Vector2{
    public int z;

    public Vector3() {
        super();
        z = 0;
    }

    public Vector3(int x, int y, int z) {
        super(x, y);
        this.z = z;
    }

    public Vector3(final Vector3 other){
        super(other);
        this.z = other.z;
    }


    public void add(Vector3 other){
        super.add(other);
        z += other.z;
    }

    public void sub(Vector3 other){
        super.sub(other);
        z -= other.z;
    }

    public void scale(int s) {
        super.scale(s);
        z *= s;
    }

    public void invScale(int is){
        super.invScale(is);
        z /= is;
    }

    public int sqrMag() {
        return super.sqrMag() + z*z;
    }

    public int magnitude(){
        return (int)Math.sqrt(sqrMag());
    }

    public void normalize() {
        invScale(magnitude());
    }

    public static Vector3 Add(final Vector3 a, final Vector3 b){
        return new Vector3(a.x+b.x, a.y+b.y, a.z+b.z);
    }

    public static Vector3 Sub(final Vector3 a, final Vector3 b){
        return new Vector3(a.x-b.x, a.y-b.y, a.z-b.z);
    }

    public static float DotProduct(final Vector3 a, final Vector3 b){
        return Vector2.DotProduct(a,b)+a.z*b.z;
    }

    public static int Distance(final Vector3 a, final Vector3 b){
        return Math.abs(b.z-a.z) + Vector2.Distance(a,b);
    }
}
