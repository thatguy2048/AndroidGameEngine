package us.aaron_johnson.gameengine.GameEngine.Base;

import android.graphics.Canvas;

/**
 * Base for game object components
 */

public abstract class Component{
    public GameObject gameObject;

    public Component(GameObject gameObject){
        this.gameObject = gameObject;
    }

    public void init(){}

    public void update(final float dt){}

    public void draw(Canvas canvas, Camera camera){}
}
