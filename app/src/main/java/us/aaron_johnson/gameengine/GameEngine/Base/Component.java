package us.aaron_johnson.gameengine.GameEngine.Base;

/**
 * Created by combu on 12/18/2017.
 */

public abstract class Component {
    public GameObject gameObject;

    public void init(){

    }

    public void update(float dt){

    }

    public Component(GameObject gameObject){
        this.gameObject = gameObject;
    }
}
