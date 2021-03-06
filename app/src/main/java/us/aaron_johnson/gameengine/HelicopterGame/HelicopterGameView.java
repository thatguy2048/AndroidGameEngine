package us.aaron_johnson.gameengine.HelicopterGame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;

import us.aaron_johnson.gameengine.BoundCircle;
import us.aaron_johnson.gameengine.GameEngine.Audio.AudioController;
import us.aaron_johnson.gameengine.GameEngine.Base.Common;
import us.aaron_johnson.gameengine.GameEngine.Base.GameObject;
import us.aaron_johnson.gameengine.GameEngine.Base.GameView;
import us.aaron_johnson.gameengine.GameEngine.Images.ImageController;
import us.aaron_johnson.gameengine.GameEngine.Math.Euclidean.Vector2;
import us.aaron_johnson.gameengine.GameEngine.Physics.CircleCollider;
import us.aaron_johnson.gameengine.GameEngine.Physics.Collider;
import us.aaron_johnson.gameengine.GameEngine.Physics.ColliderController;
import us.aaron_johnson.gameengine.MainActivity;
import us.aaron_johnson.gameengine.R;

/**
 * An extension of game view, specifically for the helicopter game.
 */

public class HelicopterGameView extends GameView {
    public AudioController audioController;
    public ColliderController colliderController;
    public ImageController imageController;
    public Level background;

    protected HelicopterObject helo;

    protected boolean finished = false;

    public HelicopterGameView(Context context, Point screenSize) {
        super(context, screenSize);

        //initialize controllers

        colliderController = (ColliderController)addController(new ColliderController(this));
        Collider.controller = colliderController;

        audioController = (AudioController)addController(new AudioController(this));
        audioController.setContext(context);

        imageController = (ImageController)addController(new ImageController(this, getResources()));

        //create level
        screenSizeInUnits = new Point(427, 240);
        background = new Level(100,100,screenSizeInUnits.y);

        //The end object
        EndCircle ec = new EndCircle(250, null);
        ec.transform.position.x = 100*115;

        //The helicopter
        Bitmap helicopterBitmap = imageController.loadBitmap(R.drawable.helicopter);
        float ratio = helicopterBitmap.getWidth() * 1f / helicopterBitmap.getHeight();
        helo = new HelicopterObject(new Vector2(50,50/ratio), background, helicopterBitmap);
        helo.transform.velocity.x += 50;
        helo.transform.acceleration.y -= 30;
        helo.setDrawCollider(true);

        //tell the camera to follow the helicopter
        getCamera().followTransform(helo.transform, true, false);

        //add the new game objects
        addGameObject(Common.Layer.MID, helo);
        addGameObject(Common.Layer.BACK, background);
        addGameObject(Common.Layer.MID, ec);
    }

    protected void loadMusic(){
        if(audioController.loadMusic("sfx/HocusPocus.mp3")){
            Log.d("Music","Loaded");
        }else{
            Log.d("Music","Failed to load asset");
        }
    }

    private static void LOGD(String message){
        Log.d("HelicopterGame",message);
    }

    @Override
    protected void onStart() {
        LOGD("onStart");
        loadMusic();
        audioController.startMusic();
    }

    @Override
    protected void onUpdate(float dt) {

    }

    @Override
    protected void onDraw() {

    }

    @Override
    protected void onStop() {
        LOGD("onStop");
        audioController.pauseMusic();
    }

    @Override
    protected void onScreenTouch(MotionEvent touchEvent) {
        LOGD("Screen Touch: "+touchEvent.toString());
        if(!finished) {
            //helicopter controls on screen touch
            if (touchEvent.getAction() == MotionEvent.ACTION_DOWN) {
                helo.transform.acceleration.y = 50;
            } else if (touchEvent.getAction() == MotionEvent.ACTION_UP) {
                helo.transform.acceleration.y = -30;
            }
        }
    }

    @Override
    protected void onPause() {
        LOGD("onPause");
        audioController.pauseMusic();
    }

    @Override
    protected void onResume() {
        LOGD("onResume");
        audioController.startMusic();
    }

    public void OnEndCollision(){
        LOGD("End Collision");
        AudioController.mediaPlayer.reset();

        helo.transform.velocity = new Vector2();
        helo.transform.acceleration = new Vector2();
        finished = true;
    }

    public void OnLevelCollision(){
        LOGD("Level Collision");
        AudioController.mediaPlayer.setVolume(0.5f, 0.5f);
        helo.transform.velocity = new Vector2();
        helo.transform.acceleration = new Vector2();
        finished = true;
    }

    @Override
    public void onGameObjectEvent(GameObject gameObject, Class gameObjectClass, Object data) {
        LOGD("Event Helicopter Game View "+gameObjectClass.toString());
        if(gameObjectClass == HelicopterObject.class){
            //assume the helicopter collided with the level
            OnLevelCollision();
        }else if(gameObjectClass == EndCircle.class){
            //assume the helicopter has completed the game
            OnEndCollision();
        }
    }
}
