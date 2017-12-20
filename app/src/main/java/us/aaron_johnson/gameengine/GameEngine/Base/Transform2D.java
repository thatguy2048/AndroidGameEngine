package us.aaron_johnson.gameengine.GameEngine.Base;

/**
 * Created by combu on 12/9/2017.
 */

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import us.aaron_johnson.gameengine.GameEngine.Math.Euclidean.Vector2;

/**Contains position and rotation information for a game object.
 * May also contain child or parent transforms
 */
public class Transform2D {
    //world position
    public Vector2 position = new Vector2();

    //0-36500
    public Vector2 rotation = new Vector2();

    //parent transform
    public Transform2D parent = null;

    //child transforms
    //Child objects will be moved when the parent is updated
    public List<Transform2D> children = new ArrayList<>();

    //The game object to whom this transform belongs
    public GameObject gameObject;

    //current velocity units/seconds
    public Vector2 velocity = new Vector2();

    //current acceleration in units/seconds^2
    public Vector2 acceleration = new Vector2();

    public Transform2D(GameObject gameObject){
        this.gameObject = gameObject;
    }

    public Transform2D(GameObject gameObject, Transform2D parent){
        this.gameObject = gameObject;
        this.parent = parent;
    }


    public boolean hasChild(Transform2D childToSearchFor){
        for(Transform2D child : children){
            if(child == childToSearchFor){
                return true;
            }
        }
        return false;
    }

    public boolean addChild(Transform2D newChild){
        if(hasChild(newChild)){
            return false;
        }else{
            newChild.parent = this;
            children.add(newChild);
            return true;
        }
    }

    protected Vector2 calculatePositionChange(final float dt){
        Vector2 positionChange = new Vector2(acceleration);
        positionChange.scale(dt/2.0f);
        positionChange.add(velocity);
        positionChange.scale(dt);
        return positionChange;
    }

    //update the position based on the elapsed number of milliseconds, given by dt
    public void update(final float dt){
        Vector2 v1 = new Vector2(acceleration);
        v1.scale(dt);
        v1.add(velocity);

        Vector2 positionChange = Vector2.Add(velocity,v1);
        positionChange.scale(dt * .5f);
        for(Transform2D child : children){
            child.update(positionChange, dt);
        }

        position.add(positionChange);
        velocity = v1;
    }

    //update based on parents position
    public void update(final Vector2 parentPositionChange, final float dt){
        Vector2 positionChange = calculatePositionChange(dt);
        positionChange.add(parentPositionChange);
        for(Transform2D child : children){
            child.update(positionChange, dt);
        }
        position.add(positionChange);
    }
}
