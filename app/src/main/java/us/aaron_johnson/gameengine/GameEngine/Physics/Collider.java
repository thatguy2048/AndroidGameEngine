package us.aaron_johnson.gameengine.GameEngine.Physics;

import android.util.Log;

import java.util.List;

import us.aaron_johnson.gameengine.GameEngine.Base.Component;
import us.aaron_johnson.gameengine.GameEngine.Base.GameObject;
import us.aaron_johnson.gameengine.GameEngine.Base.Transform2D;
import us.aaron_johnson.gameengine.GameEngine.Math.Euclidean.Vector2;

/**
 * Created by combu on 12/18/2017.
 * Base class for all other colliders
 */
public abstract class Collider extends Component{
    public enum ColliderType{
        NONE,
        CIRCLE,
        SQUARE,
        RECT,
        POLY
    }

    public static ColliderController controller;

    public ColliderType type = ColliderType.NONE;

    public Collider(GameObject gameObject,  ColliderType type) {
        super(gameObject);
        this.type = type;
    }

    public boolean checkAgainst(Collider other){
        return isWithin(other) || other.isWithin(this);
    }

    public boolean isWithin(Collider other){
        return isWithin(other.gameObject.transform);
    }

    public boolean isWithin(Transform2D other){
        return isWithin(other.position);
    }

    public abstract boolean isWithin(Vector2 position);

    public void onCollide(Collider other){
        gameObject.onEvent(Collider.class, this, other);
    }
}
