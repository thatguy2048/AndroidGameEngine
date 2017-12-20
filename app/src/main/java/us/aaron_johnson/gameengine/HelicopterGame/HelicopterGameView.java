package us.aaron_johnson.gameengine.HelicopterGame;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;

import us.aaron_johnson.gameengine.GameEngine.Audio.AudioController;
import us.aaron_johnson.gameengine.GameEngine.Base.Common;
import us.aaron_johnson.gameengine.GameEngine.Base.GameView;
import us.aaron_johnson.gameengine.GameEngine.Physics.Collider;
import us.aaron_johnson.gameengine.GameEngine.Physics.ColliderController;
import us.aaron_johnson.gameengine.MainActivity;

/**
 * Created by combu on 12/19/2017.
 */

public class HelicopterGameView extends GameView {
    public AudioController audioController;
    public ColliderController colliderController;
    public Level background;

    public HelicopterGameView(Context context, Point screenSize) {
        super(context, screenSize);

        colliderController = (ColliderController)addController(new ColliderController(this));
        Collider.controller = colliderController;

        audioController = (AudioController)addController(new AudioController(this));
        audioController.setContext(context);

        screenSizeInUnits = new Point(427, 240);
        background = new Level(100,100,screenSizeInUnits.y);

        EndCircle ec = new EndCircle(250, null);
        ec.transform.position.x = 100*115;

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

    public static void OnEndCollision(){
        LOGD("End Collision");
        AudioController.mediaPlayer.reset();
    }

    public static void OnLevelCollision(){
        LOGD("Level Collision");
    }
}
