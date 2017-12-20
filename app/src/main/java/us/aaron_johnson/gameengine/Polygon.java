package us.aaron_johnson.gameengine;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import us.aaron_johnson.gameengine.GameEngine.Base.Camera;
import us.aaron_johnson.gameengine.GameEngine.Base.GameObject;
import us.aaron_johnson.gameengine.GameEngine.Math.Euclidean.Vector2;

/**
 * Created by combu on 11/27/2017.
 */

public class Polygon extends GameObject {
    protected int numberOfPoints;
    protected Vector2 center;
    protected double radius;

    protected Vector2 points[];
    protected double da = 0;
    protected Paint paint;
    protected Path path;
    protected double offsetAngle = 0;

    public Polygon(Vector2 center, double radius, int numberOfPoints) {
        this.numberOfPoints = numberOfPoints;
        this.center = center;
        this.radius = radius;

        paint = new Paint();
        paint.setColor(Color.WHITE);
        path = new Path();
        points = new Vector2[numberOfPoints];
    }

    public void setOffsetAngle(double angle){
        offsetAngle = angle;
        updatePath();
    }

    protected void updatePath(){
        path.reset();
        path.moveTo(center.x, center.y);

        for(int p = 0; p < numberOfPoints; ++p){
            double angle = da * p;
            points[p] = new Vector2(
                    (float)(radius * Math.cos(angle + offsetAngle) + center.x),
                    (float)(radius * Math.sin(angle + offsetAngle) + center.y)
            );

            if(p == 0){
                path.moveTo(points[p].x, points[p].y);
            }else{
                path.lineTo(points[p].x, points[p].y);
            }
        }

        path.lineTo(points[0].x, points[0].y);
    }

    @Override
    public void init() {
        da = Math.PI * 2.0 / numberOfPoints;
        updatePath();
    }

    @Override
    public void update(final float dt) {

    }

    @Override
    public void draw(Canvas canvas, Camera camera) {
        canvas.drawPath(path, paint);
    }
}
