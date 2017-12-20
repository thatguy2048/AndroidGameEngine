package us.aaron_johnson.gameengine.GameEngine.Physics;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import us.aaron_johnson.gameengine.GameEngine.Base.Camera;
import us.aaron_johnson.gameengine.GameEngine.Base.GameObject;
import us.aaron_johnson.gameengine.GameEngine.Math.Euclidean.Vector2;

/**
 * Created by combu on 12/18/2017.
 */

public class CircleCollider extends Collider {
    public float radius = 1f;
    protected Paint paint;
    public boolean drawArea = false;

    public CircleCollider(GameObject gameObject, float radius) {
        super(gameObject, ColliderType.CIRCLE);
        this.radius = radius;

        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setAlpha(32);
    }

    @Override
    public boolean checkAgainst(Collider other) {
        boolean output = false;
        if(other.type == ColliderType.CIRCLE){
            float distance = Vector2.Distance(this.gameObject.transform.position, other.gameObject.transform.position);
            float _radius = this.radius + ((CircleCollider) other).radius;
            output = (distance <= _radius);
        }else if(other.type == ColliderType.SQUARE){

        }
        return output;
    }

    @Override
    public boolean isWithin(Vector2 position) {
        return Vector2.Distance(this.gameObject.transform.position, position) <= radius;
    }

    @Override
    public void onCollide(Collider other) {
        gameObject.onEvent(CircleCollider.class, this, other);
    }

    @Override
    public void draw(Canvas canvas, Camera camera) {
        super.draw(canvas, camera);
        if(drawArea){
            canvas.drawCircle(
                    camera.worldXToScreen(gameObject.transform.position.x),
                    camera.worldYToScreen(gameObject.transform.position.y),
                    camera.worldXDistanceToScreen(radius),
                    paint
            );
        }

    }
}
