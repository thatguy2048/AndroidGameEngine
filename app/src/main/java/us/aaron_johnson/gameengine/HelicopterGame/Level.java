package us.aaron_johnson.gameengine.HelicopterGame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import us.aaron_johnson.gameengine.GameEngine.Base.Camera;
import us.aaron_johnson.gameengine.GameEngine.Base.GameObject;
import us.aaron_johnson.gameengine.GameEngine.Base.Transform2D;
import us.aaron_johnson.gameengine.GameEngine.Math.Euclidean.Vector2;
import us.aaron_johnson.gameengine.GameEngine.Math.Utils;

/**
 * Generates a new level, given the desired specifications.
 * The level randomly creates points (within a range) for the helicopter to fly through.
 */


public class Level extends GameObject {

    public float heightInUnits = 100;
    public Vector2 openRange = new Vector2(0.4f, 0.9f);
    public int numberOfPoints = 25;
    public float unitsPerPoint = 10;
    public Paint paint = new Paint();

    public Vector2 position = new Vector2();

    public Level(int numberOfPoints, float unitsPerPoint, float heightInUnits){
        super();
        this.numberOfPoints = numberOfPoints;
        this.unitsPerPoint = unitsPerPoint;
        this.heightInUnits = heightInUnits;

        paint.setColor(Color.CYAN);
    }

    protected float openRangeDiff;

    protected Random random = new Random();

    //The point information
    protected List<Vector2> points = new ArrayList<Vector2>();

    protected boolean notGenerated = true;

    protected Path topPath = new Path();
    protected Path bottomPath = new Path();

    protected List<Vector2> topPoints = new ArrayList<Vector2>();
    protected List<Vector2> bottomPoints = new ArrayList<Vector2>();

    public final List<Vector2> getTopPoints(){
        return topPoints;
    }

    public final List<Vector2> getBottomPoints(){
        return bottomPoints;
    }

    protected int halfWidth = 0;
    protected  int halfHeight = 0;

    protected Region topRegion = new Region();
    protected Region bottomRegion = new Region();

    public void reGenerate(){
        notGenerated = false;
    }

    //ten game units are generated for each point
    protected void generateLevel(Camera camera){
        if(notGenerated) {
            notGenerated = false;
            points.clear();

            openRangeDiff = openRange.y - openRange.x;

            for (int i = 0; i < numberOfPoints; ++i) {
                points.add(generatePoint());
            }

            convertPointsToPositions(camera);

            RectF rect = new RectF();
            topPath.computeBounds(rect, true);
            topRegion.setPath(topPath, new Region((int)rect.top, (int)rect.right, (int)rect.top, (int)rect.bottom));

            rect = new RectF();
            bottomPath.computeBounds(rect, true);
            bottomRegion.setPath(bottomPath, new Region((int)rect.top, (int)rect.right, (int)rect.top, (int)rect.bottom));
        }
    }

    protected Vector2 generatePoint(){
        float openArea = random.nextFloat() * openRangeDiff + openRange.x;
        Vector2 output = new Vector2(openArea, random.nextFloat() * (1.0f - openArea));
        output.scale(heightInUnits);
        return output;
    }

    protected void convertPointsToPositions(Camera camera){
        float xOffset = 0f;
        int screenWidth = camera.sizeInPixels()[0];
        float openAreaInUnits, bottomInUnits, topInUnits;
        float halfHeightInUnits = camera.sizeInUnits().y / 2f;
        int tmpx, tmpTopY, tmpBottomY;

        topPoints.clear();
        bottomPoints.clear();

        halfWidth = screenWidth/2;
        halfHeight = camera.sizeInPixels()[1]/2;

        topPath.reset();
        bottomPath.reset();

        topPath.moveTo(0, 0);
        bottomPath.moveTo(0, camera.sizeInPixels()[1]);

        float rightPointUnits = camera.sizeInUnits().x / 2f;
        float topPointUnits = camera.sizeInUnits().y / 2f;

        float leftPointUnits = -1 * rightPointUnits;
        float bottomPointUnits = -1 * topPointUnits;

        topPoints.add(new Vector2(leftPointUnits, topPointUnits));
        bottomPoints.add(new Vector2(leftPointUnits, bottomPointUnits));

        topPath.lineTo(camera.sizeInPixels()[0], camera.sizeInPixels()[1] * 0.05f);
        bottomPath.lineTo(camera.sizeInPixels()[0], camera.sizeInPixels()[1] * 0.95f);

        topPoints.add(new Vector2(rightPointUnits, topPointUnits * 0.95f));
        bottomPoints.add(new Vector2(rightPointUnits, bottomPointUnits * 0.95f));

        for(int i = 0; i < numberOfPoints; ++i){
            xOffset = (i+1) * unitsPerPoint - camera.sizeInUnits().x / 2;
            xOffset += screenWidth;
            openAreaInUnits = points.get(i).x;
            topInUnits = halfHeightInUnits - points.get(i).y;
            bottomInUnits = topInUnits - openAreaInUnits;

            tmpx = camera.worldXToScreen(xOffset, false);
            tmpTopY = camera.worldYToScreen(topInUnits, false);
            tmpBottomY = camera.worldYToScreen(bottomInUnits, false);

            topPath.lineTo(tmpx, tmpTopY);
            bottomPath.lineTo(tmpx, tmpBottomY);

            topPoints.add(new Vector2(xOffset, topInUnits));
            bottomPoints.add(new Vector2(xOffset, bottomInUnits));

            if(i == numberOfPoints-1){
                topPath.lineTo(tmpx, 0);
                bottomPath.lineTo(tmpx, camera.sizeInPixels()[1]);
            }
        }

        /*
        for(int i = 0; i < topPoints.size(); ++i){
            Log.d("Points","Top: "+topPoints.get(i).toString()+" Bottom: "+bottomPoints.get(i).toString());
        }
        */
    }

    @Override
    public void init() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void draw(Canvas canvas, Camera camera) {
        generateLevel(camera);

        int camXOffset = camera.worldXToScreen(camera.position.x, false) - halfWidth;
        //float camYOffset = camera.positionInPixels[1] - halfHeight;

        Path topDraw = new Path(topPath);
        topDraw.offset(-1 * camXOffset, 0);
        canvas.drawPath(topDraw, paint);

        Path bottomDraw = new Path(bottomPath);
        bottomDraw.offset(-1 * camXOffset, 0);
        canvas.drawPath(bottomDraw, paint);
    }

    //get the min and max y position range for a given x position
    public Vector2 getRangeAt(float xPos){
        float x1, x2;
        Vector2 output = new Vector2(Float.MAX_VALUE, Float.MIN_VALUE);
        for(int i = 0; i < topPoints.size()-1; ++i){
            x1 = topPoints.get(i).x;
            x2 = topPoints.get(i+1).x;
            if(x1 <= xPos && x2 >= xPos){
                //found intersection
                float r = (xPos - x1) / (x2 - x1);
                output.x = Utils.lerp(topPoints.get(i).y, topPoints.get(i+1).y, r);
                output.y = Utils.lerp(bottomPoints.get(i).y, bottomPoints.get(i+1).y, r);
            }
        }
        return output;
    }


    public boolean withinRange(int posx, int posy){
        return !(topRegion.contains(posx, posy) || bottomRegion.contains(posx, posy));
    }

    public boolean withinRange(final Vector2 pos){
        return withinRange((int)pos.x, (int)pos.y);
    }
}
