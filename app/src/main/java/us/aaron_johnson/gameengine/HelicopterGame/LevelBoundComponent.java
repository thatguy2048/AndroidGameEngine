package us.aaron_johnson.gameengine.HelicopterGame;

import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

import us.aaron_johnson.gameengine.GameEngine.Base.Component;
import us.aaron_johnson.gameengine.GameEngine.Base.GameObject;
import us.aaron_johnson.gameengine.GameEngine.Math.Euclidean.Vector2;

/**
 * Provides an event like a collision for an object and the level bounds.
 */

public class LevelBoundComponent extends Component {
    public Level level = null;
    public float checkRadius = 0f;

    protected Vector2 _range;
    protected boolean withinLevel = true;

    public LevelBoundComponent(GameObject gameObject) {
        super(gameObject);
    }

    public void setLevel(Level level, float checkRadius){
        this.level = level;
        this.checkRadius = checkRadius;

    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        _range = level.getRangeAt(gameObject.transform.position.x);

        if(gameObject.transform.position.y+checkRadius >= _range.x || gameObject.transform.position.y+checkRadius <= _range.y){
            if(withinLevel){
                withinLevel = false;
                onExitLevel();
            }
        }else{
            if(!withinLevel){
                withinLevel = true;
                onEnterLevel();
            }
        }
    }

    protected void onEnterLevel(){
        gameObject.onEvent(LevelBoundComponent.class, this, true);
    }

    protected void onExitLevel(){
        gameObject.onEvent(LevelBoundComponent.class, this, false);
    }
}
