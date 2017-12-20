package us.aaron_johnson.gameengine.GameEngine.Base;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by combu on 11/11/2017.
 */

public abstract class GameView extends SurfaceView implements Runnable{
    public enum RunningState{
        NEVER_STARTED,
        RUNNING,
        PAUSED,
        STOPPED
    }

    protected volatile RunningState state = RunningState.NEVER_STARTED;
    protected Thread _thread = null;
    protected long _previous_time;
    protected long _current_time;
    protected long _dt;
    protected volatile long minTime = -1;
    protected Camera camera;
    protected List<Controller> controllers = new ArrayList<>();
    //game objects in lists associated with draw layer
    protected List<List<GameObject>> gameObjects = new ArrayList<List<GameObject>>();

    protected SurfaceHolder surfaceHolder;
    protected Point screenSize;
    protected Point screenSizeInUnits = new Point(427, 240);

    protected Paint mainPaint;
    protected Paint fpsPaint;

    public volatile boolean drawFPS = false;
    protected long[] _dt_arr;
    protected long _dt_avg;
    protected int _dt_next;

    protected boolean first = true;
    protected OnTouchListener otl;

    public GameView(Context context, Point screenSize) {
        super(context);
        camera = new Camera(this);

        this.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        //initialize game object list
        for(int i = 0; i < Common.Layer.COUNT; ++i){
            gameObjects.add(new ArrayList<GameObject>());
        }

        this.screenSize = screenSize;
        surfaceHolder = this.getHolder();

        mainPaint = new Paint();
        fpsPaint = new Paint();
        fpsPaint.setColor(Color.YELLOW);
        fpsPaint.setStyle(Paint.Style.FILL);
        fpsPaint.setTextSize(20);

        _dt_arr = new long[]{0,0,0,0,0};
        _dt_avg = 0;
        _dt_next = 0;

        otl = new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                onScreenTouch(motionEvent);
                return true;
            }
        };
        this.setOnTouchListener(otl);
    }

    public int getScreenWidth(){
        return screenSize.x;
    }

    public int getScreenHeight(){
        return screenSize.y;
    }

    public void addGameObject(int layer, GameObject go){
        gameObjects.get(layer-1).add(go);
    }

    public Controller addController(Controller newController){
        controllers.add(newController);
        return newController;
    }

    protected void updateDT_AVG(){
        _dt_arr[_dt_next] = _dt;
        if(++_dt_next >= _dt_arr.length){
            _dt_next = 0;
        }
        _dt_avg = _dt_arr[0];
        for(int i = 1; i < _dt_arr.length; ++i){
            _dt_avg += _dt_arr[i];
        }
        _dt_avg /= _dt_arr.length;
    }

    @Override
    public void run() {
        _start();
        do {
            _dt = 0;
            _current_time = System.currentTimeMillis();
            _previous_time = _current_time;
            while (state == RunningState.RUNNING) {
                _update();
                _draw();
                _previous_time = _current_time;
                _current_time = System.currentTimeMillis();
                _dt = _current_time - _previous_time;
                if(minTime > 0 && _dt < minTime){
                    try{
                        _thread.sleep(minTime-_dt);
                    }catch (InterruptedException e){
                        Log.e("GAME_THREAD", "Exception When Sleeping: "+e.toString());
                    }
                }
            }
            if(state == RunningState.PAUSED){
                try {
                    _thread.sleep(16);
                }catch (InterruptedException e){
                    Log.e("GAME_THREAD", "Exception When Paused: "+e.toString());
                }
            }
        }while(state == RunningState.PAUSED);
        _stop();
    }

    public Camera getCamera(){
        return camera;
    }

    protected void _start(){
        for (List<GameObject> gol:gameObjects) {
            for(GameObject go:gol){
                go.init();
            }
        }

        for(Controller c : controllers){
            c.init();
        }

        onStart();
    }

    protected void _update(){
        float dt = _dt/1000f;

        for (List<GameObject> gol:gameObjects) {
            for(GameObject go:gol){
                go.update(dt);
            }
        }

        onUpdate(dt);

        for(Controller c : controllers){
            c.update();
        }
    }

    protected void _draw(){
        if(surfaceHolder.getSurface().isValid()) {
            Canvas canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.BLACK);
            if(first){
                first = false;
                camera.setSize(canvas.getWidth(), canvas.getHeight(), screenSizeInUnits.x, screenSizeInUnits.y);
            }


            for(int i = 0; i < Common.Layer.COUNT; ++i) {
                camera.draw(canvas,  gameObjects.get(i));
            }

            if(drawFPS){
                updateDT_AVG();
                canvas.drawText("FPS/DT "+Long.toString(1000/(_dt_avg+1))+"/"+Long.toString(_dt_avg),10,20, fpsPaint);
            }
            surfaceHolder.unlockCanvasAndPost(canvas);
        }

        for(Controller c : controllers){
            c.draw();
        }
    }

    protected void _stop(){
        onStop();

        for(Controller c : controllers){
            c.end();
        }
    }

    protected abstract void onStart();

    protected abstract void onUpdate(float dt);

    protected abstract void onDraw();

    protected abstract void onStop();

    protected abstract void onPause();

    protected abstract void onResume();

    protected abstract void onScreenTouch(MotionEvent touchEvent);

    //returns the amount of time that has passed since the previous frame
    public long getDeltaTime(){
        return _dt;
    }

    public void setMaxFPS(int maxFPS){
        Log.d("GAME_SET_FPS", Integer.toString(maxFPS));
        if(maxFPS > 0 && maxFPS <= 500){
            minTime = (1000 / maxFPS);
        }else{
            minTime = -1;
        }
        Log.d("GAME_SET_FPS", "MinTime: "+Long.toString(minTime));
    }

    private static void LOGD(String message){
        Log.d("GameView",message);
    }

    public boolean start(){
        boolean output = false;
        if(state == RunningState.NEVER_STARTED || state == RunningState.STOPPED){
            output = true;
            state = RunningState.RUNNING;
            _thread = new Thread(this);
            _thread.start();
            LOGD("Start");
        }

        return output;
    }

    public void stop(){
        LOGD("Stop, State: "+state.toString());
        if(state == RunningState.RUNNING || state == RunningState.PAUSED){
            LOGD("Stop, Join Thread");
            //stop
            state = RunningState.STOPPED;
            try {
                _thread.join();
            }catch (InterruptedException e){
                Log.e("GAME_THREAD", "InterruptedException When Stopping: "+e.toString());
            }catch (Exception e){
                Log.e("GAME_THREAD", "Exception When Stopping: "+e.toString());
            }
            LOGD("Stop");
        }
    }

    public void pause(){
        if(state == RunningState.RUNNING){
            //pause
            state = RunningState.PAUSED;
            onPause();
            LOGD("Pause");
        }
    }

    public void resume(){
        if(state == RunningState.PAUSED){
            //resume
            state = RunningState.RUNNING;
            onResume();
            LOGD("Resume");
        }
    }
}
