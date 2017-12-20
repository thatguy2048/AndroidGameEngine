package us.aaron_johnson.gameengine;

import java.util.Random;

import us.aaron_johnson.gameengine.GameEngine.GameObjectContainer;
import us.aaron_johnson.gameengine.GameEngine.Base.GameView;
import us.aaron_johnson.gameengine.GameEngine.Math.Euclidean.Vector2;

/**
 * Created by combu on 11/11/2017.
 */

public class StarField extends GameObjectContainer {
    protected int numberOfPoints;
    protected GameView gv;

    public StarField(GameView gameView, int numberOfPoints, int twinkleRate, int width, int height) {
        super(numberOfPoints);
        this.gv = gameView;
        this.numberOfPoints = numberOfPoints;
        Random random = new Random();

        for(int i = 0; i < numberOfPoints; ++i){
            objects.add(
                    new TwinkleStar(gv, new Vector2(random.nextInt(width), random.nextInt(height)), random.nextInt(twinkleRate-1000)+1000)
            );
        }
    }
}
