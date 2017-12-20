package us.aaron_johnson.gameengine.GameEngine.Base;

import android.graphics.Canvas;

/**
 * Created by combu on 12/18/2017.
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
