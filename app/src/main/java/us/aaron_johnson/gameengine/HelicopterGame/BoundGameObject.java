package us.aaron_johnson.gameengine.HelicopterGame;

import android.util.Log;

import us.aaron_johnson.gameengine.GameEngine.Base.GameObject;
import us.aaron_johnson.gameengine.GameEngine.Math.Euclidean.Vector2;

/**
 * Created by combu on 12/18/2017.
 */

public abstract class BoundGameObject extends GameObject {
    public Level level = null;
    public float checkRadius = 0f;

    protected boolean withinLevel = false;
    protected Vector2 _range;

    public BoundGameObject(Level level, float checkRadius) {
        this.level = level;
        this.checkRadius = checkRadius;
    }

    @Override
    public void update(final float dt){
        super.update(dt);
        _range = level.getRangeAt(transform.position.x);

        if(transform.position.y > _range.x || transform.position.y < _range.y){
            if(withinLevel){
                withinLevel = false;
                Log.d("BoundObject Exit","Position: "+transform.position.toString()+" Range: "+_range.toString());
                onExitLevel();
            }
        }else{
            if(!withinLevel){
                withinLevel = true;
                Log.d("BoundObject Enter","Position: "+transform.position.toString()+" Range: "+_range.toString());
                onEnterLevel();
            }
        }
    }

    protected abstract void onEnterLevel();
    protected abstract void onExitLevel();
}
