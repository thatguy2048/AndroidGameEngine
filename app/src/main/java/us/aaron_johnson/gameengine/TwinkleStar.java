package us.aaron_johnson.gameengine;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import us.aaron_johnson.gameengine.GameEngine.Base.Camera;
import us.aaron_johnson.gameengine.GameEngine.Base.GameObject;
import us.aaron_johnson.gameengine.GameEngine.Base.GameView;
import us.aaron_johnson.gameengine.GameEngine.Math.Euclidean.Vector2;

/**
 * Created by combu on 11/27/2017.
 */

public class TwinkleStar extends GameObject {
    protected int twinkleRate;
    protected Vector2 position;
    protected Paint paint;
    protected int currentAlpha = 0;
    protected boolean incomming = true;
    protected int totalTime = 0;
    protected GameView gv;

    public TwinkleStar(GameView gv, Vector2 position, int twinkleRate) {
        this.twinkleRate = twinkleRate;
        this.position = position;
        this.gv = gv;

        paint = new Paint();
    }

    protected int getAlpha() {
        return 255*(twinkleRate-totalTime) / twinkleRate;
    }

    public void updateAlpha(){
        if(incomming) {
            totalTime += gv.getDeltaTime();
            if(totalTime >= twinkleRate) {
                totalTime = twinkleRate;
                incomming = false;
            }
        }else {
            totalTime -= gv.getDeltaTime();
            if(totalTime <= 0) {
                totalTime = 0;
                incomming = true;
            }
        }

        currentAlpha = getAlpha();
    }

    @Override
    public void init() {

    }

    @Override
    public void update(final float dt) {
        updateAlpha();
    }

    @Override
    public void draw(Canvas canvas, Camera camera) {
        paint.setColor(Color.argb(currentAlpha,255,255,255));
        canvas.drawPoint(position.x, position.y, paint);
    }
}
