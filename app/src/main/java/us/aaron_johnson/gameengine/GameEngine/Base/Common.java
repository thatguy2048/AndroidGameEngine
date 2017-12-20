package us.aaron_johnson.gameengine.GameEngine.Base;

/**
 * Created by combu on 12/11/2017.
 */

public class Common {
    public static class Layer{
        public static final int NONE = 0;
        public static final int SKY = 1<<0;
        public static final int BACK = 1<<1;
        public static final int MID = 1<<2;
        public static final int FRONT = 1<<3;
        public static final int GUI = 1<<4;

        public static final int ALL = SKY | BACK | MID | FRONT | GUI;
        public static final int COUNT = 5;
    }
}
