package us.aaron_johnson.gameengine.GameEngine.Base;

import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by combu on 11/29/2017.
 */

public abstract class GameObject {
    public Transform2D transform = null;
    int drawLayer = Common.Layer.MID;
    public List<Component> components = new ArrayList<>();

    public Transform2D addTransform(){
        this.transform = new Transform2D(this);
        return this.transform;
    }
    public Transform2D addTransform(Transform2D parent){
        this.transform = new Transform2D(this, parent);
        return this.transform;
    }

    public void init(){
        for(Component c: components){
            c.init();
        }
    }

    public void update(final float dt){
        for(Component c: components){
            c.update(dt);
        }

        if(transform.parent == null){
            transform.update(dt);
        }
    }

    public void draw(Canvas canvas, Camera camera){
        for(Component c: components){
            c.draw(canvas, camera);
        }
    }

    public void onEvent(Class componenetClass, Component trigger, Object value){
        Log.d("GameObject Event","Component Class: "+componenetClass.toString());
    }
}
