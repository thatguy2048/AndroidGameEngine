package us.aaron_johnson.gameengine.GameEngine.Shapes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import us.aaron_johnson.gameengine.GameEngine.Base.Camera;
import us.aaron_johnson.gameengine.GameEngine.Base.GameObject;
import us.aaron_johnson.gameengine.GameEngine.Base.Transform2D;

/**
 * Just a circle
 */

public class Circle extends GameObject {
    public float radius = 1;
    protected Paint paint = new Paint();

    public Circle(final float radius){
        super();
        this.radius = radius;
        this.addTransform();
        paint.setColor(Color.GREEN);
    }

    public Circle(final float radius, Transform2D parent){
        super();
        this.radius = radius;
        addTransform();
        this.transform.parent = parent;
        paint.setColor(Color.GREEN);
    }

    @Override
    public void init() {

    }

    @Override
    public void update(final float dt) {
        super.update(dt);
        if(transform.parent == null){
            transform.update(dt);
        }
    }

    @Override
    public void draw(Canvas canvas, Camera camera) {
        //Log.d("Circle Draw","Unit: "+transform.position.toString()+" Pixels: "+camera.worldXToScreen(transform.position.x)+","+camera.worldYToScreen(transform.position.y));
        canvas.drawCircle(
                camera.worldXToScreen(transform.position.x),
                camera.worldYToScreen(transform.position.y),
                camera.worldXDistanceToScreen(radius),
                paint);
    }
}
