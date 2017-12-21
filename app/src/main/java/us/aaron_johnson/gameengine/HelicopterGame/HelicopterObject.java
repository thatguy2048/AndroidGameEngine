package us.aaron_johnson.gameengine.HelicopterGame;

import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import us.aaron_johnson.gameengine.GameEngine.Audio.AudioController;
import us.aaron_johnson.gameengine.GameEngine.Base.Camera;
import us.aaron_johnson.gameengine.GameEngine.Base.Component;
import us.aaron_johnson.gameengine.GameEngine.Base.GameObject;
import us.aaron_johnson.gameengine.GameEngine.Math.Euclidean.Vector2;
import us.aaron_johnson.gameengine.GameEngine.Physics.CircleCollider;
import us.aaron_johnson.gameengine.GameEngine.Physics.ColliderController;
import us.aaron_johnson.gameengine.MainActivity;

/**
 * The helicopter.
 */

public class HelicopterObject extends GameObject {
    protected Level level;
    protected static int collisionSoundID = Integer.MIN_VALUE;

    public boolean hasCollided = false;

    protected Vector2 sizeInUnits;
    protected CircleCollider collider;
    protected Bitmap bitmap;
    protected Bitmap scaledBitmap;
    protected boolean scaled = false;

    Paint paint = new Paint();

    public HelicopterObject(Vector2 sizeInUnits, Level level, Bitmap bitmap) {
        this.level = level;
        this.bitmap = bitmap;
        this.sizeInUnits = sizeInUnits;

        addTransform();

        //Add the level binding component
        LevelBoundComponent lbc = new LevelBoundComponent(this);
        lbc.setLevel(level, Math.min(sizeInUnits.x, sizeInUnits.y));
        this.components.add(lbc);

        //Add the circular collider for the end of the level
        collider = new CircleCollider(this, Math.min(sizeInUnits.x, sizeInUnits.y));
        ColliderController.addColliderToObject(this, collider);

        //load the audio clip for failure
        if(collisionSoundID == Integer.MIN_VALUE){
            collisionSoundID = AudioController.loadSound("boo.mp3");
        }

        paint.setColor(Color.GREEN);
    }

    public void setDrawCollider(boolean toDraw){
        collider.drawArea = toDraw;
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void update(float dt) {
        super.update(dt);
    }

    @Override
    public void draw(Canvas canvas, Camera camera) {
        super.draw(canvas, camera);

        if(!scaled){
            //scale the image
            //this only needs to be done once, but can only be done once the camera is available
            scaled = true;
            scaledBitmap = Bitmap.createScaledBitmap(bitmap,camera.worldXDistanceToScreen(sizeInUnits.x),camera.worldYDistanceToScreen(sizeInUnits.y),false);
        }

        canvas.drawBitmap(scaledBitmap, camera.worldXToScreen(transform.position.x - sizeInUnits.x/2),camera.worldYToScreen(transform.position.y + sizeInUnits.y/2), null);
    }

    @Override
    public void onEvent(Class componenetClass, Component trigger, Object value) {
        //check for an event from the level bound component
        if(componenetClass == LevelBoundComponent.class){
            if((boolean)value == true){ //inside level

            }else if(!hasCollided){ //outside level
                hasCollided = true;
                Log.d("Helicopter Event","Circle Collision");
                if(AudioController.playSound(collisionSoundID)){
                    Log.d("Helicopter Event","Played boo");
                }else{
                    Log.d("Helicopter Event","Failed to boo");
                }

                gameView.onGameObjectEvent(this, getClass(), true);
            }
        }else if(componenetClass == CircleCollider.class) {
            transform.velocity = new Vector2();
            transform.acceleration = new Vector2();
        }else{
            super.onEvent(componenetClass, trigger, value);
        }
    }
}
