package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

import com.example.myapplication.R;

/**
 * Created by asdfqwer on 7/16/2017.
 */

public class Link{
    private float X;
    private float Y;
    private int imgRadius;

    private float pullStrength;

    private float mass;
    private float fx;
    private float fy;
    private float vx;
    private float vy;

    private float uk;

    private boolean pulled;

    private int width;
    private int height;

    private Animation playerAnimation;
    private Rect playerLocation;

    private float[] prevPos;

    public Link(float X, float Y, int width, int height){

        prevPos = new float[2];

        prevPos[0] = X;
        prevPos[1] = Y;

        this.X = X;
        this.Y = -Y;

        this.pullStrength = 2000;

        this.mass = (float)1.1;
        this.uk = 1000;

        this.fx = 0;
        this.fy = 0;
        this.vx = 0;
        this.vy = 0;

        this.pulled = false;

        this.width = width;
        this.height = height;
        this.imgRadius = (int) ((float) width/ (float)10);

        BitmapFactory bf = new BitmapFactory();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        Bitmap q1 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.pb0, options);
        Bitmap q2 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.pb1, options);
        Bitmap q3 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.pb2, options);
        Bitmap q4 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.pb3, options);
        Bitmap q5 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.pb4, options);
        Bitmap q6 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.pb5, options);


        playerAnimation = new Animation(new Bitmap[]{q1,q2,q3,q4,q5,q6}, 1);
        playerLocation = new Rect((int) X - imgRadius, (int) -Y - imgRadius, (int) X + imgRadius, (int) -Y + imgRadius);
        playerAnimation.play();
    }

    public float[] getCoord() {
        float[] temp;
        temp = new float[2];
        temp[0] = X;
        temp[1] = Y;
        return temp;
    }


    public void setPulled(boolean pull) {
        pulled = pull;
    }
    public void draw(Canvas canvas){
        playerAnimation.draw(canvas, playerLocation);
    }

    public void update(Point point){
        playerAnimation.update();

        if (pulled) {
            double angle;
            angle = 0;
            float dy;
            float dx;
            dy = (float) point.y - (float) prevPos[1];
            dx = (float) point.x - (float) prevPos[0];
            if (dy != 0 && dx != 0) {
                angle = Math.atan(Math.abs(dy / dx));
                if (dy < 0 && dx < 0) {
                    angle += Math.toRadians(180);
                } else if (dy < 0 && dx > 0) {
                    angle = -angle;
                } else if (dy > 0 && dx < 0) {
                    angle = Math.toRadians(180) - angle;
                } else if (dy > 0 && dx > 0) {
                    //angle += Math.toRadians(270);
                }
            }
            else {
                if (dy > 0){
                    angle = Math.toRadians(90);
                }
                else if (dy < 0){
                    angle = Math.toRadians(270);
                }
                else if (dx > 0){
                    angle = Math.toRadians(0);
                }
                else if (dx < 0){
                    angle = Math.toRadians(180);
                }
            }
            fx += pullStrength * Math.cos(angle);
            fy += pullStrength * Math.sin(angle);
        }

        if (Math.abs(vx) > 0 || Math.abs(vy) > 0){
            double angle2;
            angle2 = 0;
            angle2 = Math.atan(Math.abs(vy/ vx));
            if (vy != 0 && vx != 0) {
                if (vy < 0 && vx < 0) {
                    angle2 += Math.toRadians(180);
                } else if (vy < 0 && vx > 0) {
                    angle2 = -angle2;
                } else if (vy > 0 && vx < 0) {
                    angle2 = Math.toRadians(180) - angle2;
                } else if (vy > 0 && vx > 0) {
                    //angle1 += Math.toRadians(270);
                }
            }
            else {
                if (vy > 0){
                    angle2 = Math.toRadians(90);
                }
                else if (vy < 0){
                    angle2 = Math.toRadians(270);
                }
                else if (vx > 0){
                    angle2 = Math.toRadians(0);
                }
                else if (vx < 0){
                    angle2 = Math.toRadians(180);
                }
            }

            fx += uk*mass*Math.cos(angle2 + Math.toRadians(180));
            fy += uk*mass*Math.sin(angle2 + Math.toRadians(180));
        }

        vx += (fx/mass)*((float)1/(float)30);
        vy += (fy/mass)*((float)1/(float)30);
        X += vx*((float)1/(float)30)*((float)width/(float)1000);
        Y += vy*((float)1/(float)30)*((float)width/(float)1000);
        fx = 0;
        fy = 0;

        prevPos[0] = X;
        prevPos[1] = Y;
        playerLocation.set((int) X - imgRadius, (int) -Y - imgRadius, (int) X + imgRadius, (int) -Y + imgRadius);
    }
}
