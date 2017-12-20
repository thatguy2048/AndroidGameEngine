package us.aaron_johnson.gameengine.GameEngine.Base;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import java.util.List;

import us.aaron_johnson.gameengine.GameEngine.Math.Euclidean.Vector2;

/**
 * Created by combu on 12/9/2017.
 */

//Render objects relative to the camera to a canvas
public class Camera{
    GameView view;
    public Vector2 position = new Vector2();
    public float[] positionInPixels = new float[]{0,0};
    public int layersToRender = Common.Layer.ALL ^ Common.Layer.GUI;


    int pixelSize[] = new int[]{0,0};

    Vector2 unitSize = new Vector2();
    Vector2 halfUnitSize = new Vector2();
    Vector2 pixelsPerUnit = new Vector2(1,1);
    Rect bounds = new Rect();
    protected Transform2D toFollow = null;
    protected boolean followX = false;
    protected boolean followY = false;

    public Camera(GameView gameView){
        this.view = gameView;
    }

    public final int[] sizeInPixels(){  return pixelSize;   }
    public final Vector2 sizeInUnits(){ return unitSize;    }

    //follow a transform with the camera
    public void followTransform(Transform2D transform){
        followTransform(transform, true, true);
    }
    public void followTransform(Transform2D transform, boolean xPosition, boolean yPosition){
        toFollow = transform;
        followX = xPosition;
        followY = yPosition;
    }

    //Set the size of the camera view, both in pixes and game units
    public void setSize(int widthInPixels, int heightInPixels, float widthInUnits, float heightInUnits){
        Log.d("Camera Set Size","Pixel Size: "+widthInPixels+","+heightInPixels+" Unit Size: "+widthInUnits+","+heightInUnits);
        pixelSize[0] = widthInPixels;
        pixelSize[1] = heightInPixels;
        unitSize.x = widthInUnits;
        unitSize.y = heightInUnits;

        halfUnitSize.x = unitSize.x * 0.5f;
        halfUnitSize.y = unitSize.y * 0.5f;

        pixelsPerUnit.x = ((float)widthInPixels) / widthInUnits;
        pixelsPerUnit.y = ((float)heightInPixels) / heightInUnits;

        positionInPixels[0] = worldXToScreen(position.x,false);
        positionInPixels[1] = worldYToScreen(position.y, false);
    }

    //methods to draw all game objects the camera is told about

    public void draw(Canvas canvas, List<GameObject> objects){
        if(toFollow != null){
            if(followX){
                this.position.x = toFollow.position.x;
            }
            if(followY){
                this.position.y = toFollow.position.y;
            }
        }

        for(GameObject object: objects){
            if((object.drawLayer | layersToRender) != 0){
                object.draw(canvas, this);
            }
        }
    }

    public void drawAll(Canvas canvas, List<GameObject> objects){
        if(toFollow != null){
            this.position = toFollow.position;
        }

        for(GameObject object: objects) {
            object.draw(canvas, this);
        }
    }

    //Methods used to transform game space to screen space

    public int worldXDistanceToScreen(final float dist){
        return (int)(dist * pixelsPerUnit.x);
    }

    public int worldYDistanceToScreen(final float dist){
        return (int)(dist * pixelsPerUnit.y);
    }

    public int worldXToScreen(final float x){
        //return (int)((((x-position.x) + halfUnitSize.x) * pixelsPerUnit.x));
        return worldXToScreen(x, true);
    }

    public int worldYToScreen(final float y){
        //return (int)((((y-position.y) - halfUnitSize.y) * pixelsPerUnit.y));
        return worldYToScreen(y, true);
    }

    public int worldXToScreen(final float x, boolean offset){
        int output;
        if(offset){
            output = worldXToScreen(x - position.x, false);
        }else{
            output = (int)((x + halfUnitSize.x) * pixelsPerUnit.x);
        }
        return output;
    }

    public int worldYToScreen(final float y, boolean offset){
        if(offset)  return worldYToScreen(y - position.y, false);
        else    return (int)(((-1*(y - halfUnitSize.y)) * pixelsPerUnit.y));
    }
}
