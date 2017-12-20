package us.aaron_johnson.gameengine.GameEngine.Base;

import android.graphics.Canvas;

/**
 * Created by combu on 12/18/2017.
 * Base class for global controller objects.
 */


public abstract class Controller{
    public GameView gameView;

    public Controller(GameView gameView){
        this.gameView = gameView;
    }

    public void init(){

    }

    public void update(){

    }

    public void draw(){

    }

    public void end(){

    }
}
