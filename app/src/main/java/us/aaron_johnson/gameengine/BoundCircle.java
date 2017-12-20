package us.aaron_johnson.gameengine;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import us.aaron_johnson.gameengine.GameEngine.Base.Camera;
import us.aaron_johnson.gameengine.GameEngine.Base.Component;
import us.aaron_johnson.gameengine.GameEngine.Base.Transform2D;
import us.aaron_johnson.gameengine.GameEngine.Math.Euclidean.Vector2;
import us.aaron_johnson.gameengine.GameEngine.Shapes.Circle;
import us.aaron_johnson.gameengine.HelicopterGame.BoundGameObject;
import us.aaron_johnson.gameengine.HelicopterGame.Level;
import us.aaron_johnson.gameengine.HelicopterGame.LevelBoundComponent;

/**
 * Created by combu on 12/13/2017.
 */

/*
public class BoundCircle extends BoundGameObject {
    Level level = null;
    public float radius = 1;
    public Paint paint = new Paint();


    public BoundCircle(Level level, float boundRadius, float radius, Transform2D parent){
        super(level, boundRadius);
        paint.setColor(Color.GREEN);
        this.radius = radius;
        addTransform();
        this.transform.parent = parent;
    }

    @Override
    public void update(final float dt){
        super.update(dt);
        Log.d("Bound Position",transform.position.toString());
    }

    @Override
    public void draw(Canvas canvas, Camera camera) {
        super.draw(canvas, camera);

        canvas.drawCircle(
                camera.worldXToScreen(transform.position.x),
                camera.worldYToScreen(transform.position.y),
                camera.worldXDistanceToScreen(radius),
                paint);
    }

    @Override
    protected void onEnterLevel() {
        Log.d("BoundCircle","Enter Level");
    }

    @Override
    protected void onExitLevel() {
        Log.d("BoundCircle","Exit Level");
    }
}
*/
public class BoundCircle extends Circle{


    public BoundCircle(Level level, float boundRadius, float radius, Transform2D parent){
        super(radius);
        this.radius = radius;
        addTransform();
        this.transform.parent = parent;
        LevelBoundComponent lbc = new LevelBoundComponent(this);
        lbc.setLevel(level, boundRadius);
        this.components.add(lbc);
    }

    @Override
    public void onEvent(Class componenetClass, Component trigger, Object value) {
        if(componenetClass == LevelBoundComponent.class){
            if((boolean)value == true){ //inside level
                Log.d("BoundCircle","Enter Level");
            }else{ //outside level
                Log.d("BoundCircle","Exit Level");
            }
        }else {
            super.onEvent(componenetClass, trigger, value);
        }
    }
}