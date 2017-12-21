package us.aaron_johnson.gameengine.HelicopterGame;

import android.graphics.Color;
import android.media.MediaRecorder;
import android.provider.MediaStore;
import android.util.Log;

import us.aaron_johnson.gameengine.GameEngine.Audio.AudioController;
import us.aaron_johnson.gameengine.GameEngine.Base.Component;
import us.aaron_johnson.gameengine.GameEngine.Base.Transform2D;
import us.aaron_johnson.gameengine.GameEngine.Physics.CircleCollider;
import us.aaron_johnson.gameengine.GameEngine.Physics.ColliderController;
import us.aaron_johnson.gameengine.GameEngine.Shapes.Circle;

/**
 * Created by combu on 12/19/2017.
 */

public class EndCircle extends Circle {
    protected CircleCollider collider;
    protected static int collisionSoundID = Integer.MIN_VALUE;

    public boolean hasCollided = false;

    public EndCircle(float radius, Transform2D parent) {
        super(radius, parent);

        collider = new CircleCollider(this, radius);
        ColliderController.addColliderToObject(this, collider);

        addTransform();

        paint.setColor(Color.RED);

        if(collisionSoundID == Integer.MIN_VALUE){
            collisionSoundID = AudioController.loadSound("applause.mp3");
        }
    }

    public void onEvent(Class componenetClass, Component trigger, Object value){
        if(componenetClass == CircleCollider.class && !hasCollided){
            hasCollided = true;
            Log.d("End Circle Event","Circle Collision");
            if(AudioController.playSound(collisionSoundID)){
                Log.d("End Circle Event","Played applause");
            }else{
                Log.d("End Circle Event","Failed to applause");
            }
            gameView.onGameObjectEvent(this, getClass(), value);
        }
    }
}
