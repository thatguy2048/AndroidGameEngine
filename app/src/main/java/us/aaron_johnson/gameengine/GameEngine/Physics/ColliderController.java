package us.aaron_johnson.gameengine.GameEngine.Physics;

import java.util.ArrayList;
import java.util.List;

import us.aaron_johnson.gameengine.GameEngine.Base.Controller;
import us.aaron_johnson.gameengine.GameEngine.Base.GameObject;
import us.aaron_johnson.gameengine.GameEngine.Base.GameView;

/**
 * Intended to ensure the collision calculations are performed only once an update, instead of for each object.
 */

public class ColliderController extends Controller{
    public static List<Collider> gameObjects = new ArrayList<>();

    public ColliderController(GameView gameView) {
        super(gameView);
    }

    public static void addCollider(Collider newCollider){
        gameObjects.add(newCollider);
    }

    public static void addColliderToObject(GameObject gameObject, Collider newCollider){
        gameObject.components.add(newCollider);
        addCollider(newCollider);
    }

    @Override
    public void update() {
        super.update();
        int sz = gameObjects.size();
        for(int i = 0; i < sz-1; ++i){
            for(int j = i+1; j < sz; ++j){
                check(gameObjects.get(i), gameObjects.get(j));
            }
        }
    }

    protected void check(Collider c1, Collider c2){
        if(c1.checkAgainst(c2)){
            c1.onCollide(c2);
            c2.onCollide(c1);
        }
    }
}
