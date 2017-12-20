package us.aaron_johnson.gameengine.GameEngine.Math;

/**
 * Created by combu on 12/9/2017.
 */

public class Utils {
    //linear interpolation between two ints
    //r == 0, returns a
    //r == 1, returns b
    public static int lerp(int a, int b, float r){
        return (int)(a + r * (b-a));
    }
    public static float lerp(float a, float b, float r){    return a + r * (b-a);   }

    public static float square(final float a){
        return a*a;
    }

    public static float cube(final float a){
        return a*a*a;
    }
}
