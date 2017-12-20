package us.aaron_johnson.gameengine.GameEngine.Math.Euclidean;

/**
 * Created by combu on 11/30/2017.
 */

public class Vector3 extends Vector2 {
    public float z;

    public Vector3() {
        super();
        z = 0;
    }

    public Vector3(float x, float y, float z) {
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

    public void scale(float s) {
        super.scale(s);
        z *= s;
    }

    public void invScale(float is){
        super.invScale(is);
        z /= is;
    }

    public float sqrMag() {
        return super.sqrMag() + z*z;
    }

    public float magnitude(){
        return (float)Math.sqrt(sqrMag());
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

    public static float SqrDistance(final Vector3 a, final Vector3 b){
        return Vector2.SqrDistance(a,b)+U.square(b.y-a.y);
    }

    public static float Distance(final Vector3 a, final Vector3 b){
        return (float)Math.sqrt(SqrDistance(a,b));
    }
}
