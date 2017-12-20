package us.aaron_johnson.gameengine.GameEngine;

import android.graphics.Canvas;

import java.util.ArrayList;

import us.aaron_johnson.gameengine.GameEngine.Base.Camera;
import us.aaron_johnson.gameengine.GameEngine.Base.GameObject;

/**
 * Created by combu on 12/4/2017.
 */

//Class to hold multiple game objects
public abstract class GameObjectContainer extends GameObject {
    protected ArrayList<GameObject> objects;

    public GameObjectContainer() {
        this.objects = new ArrayList<>();
    }

    public GameObjectContainer(int numberOfObjects) {
        this.objects = new ArrayList<>(numberOfObjects);
    }

    @Override
    public void init() {
        for(int i = 0; i < objects.size(); ++i){
            objects.get(i).init();
        }
    }

    @Override
    public void update(final float dt) {
        for(int i = 0; i < objects.size(); ++i){
            objects.get(i).update(dt);
        }
    }

    @Override
    public void draw(Canvas canvas, Camera camera) {
        for(int i = 0; i < objects.size(); ++i){
            objects.get(i).draw(canvas, camera);
        }
    }
}
