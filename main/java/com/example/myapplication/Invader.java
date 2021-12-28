package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

import com.example.myapplication.R;

/**
 * Created by asdfqwer on 7/18/2017.
 */

public class Invader{
    private float X;
    private float Y;
    private float radius;

    private float vx;
    private float vy;

    public static float constantVel;

    private int width;
    private int height;

    private boolean alive;
    private boolean death;
    private boolean touchDown;

    private Animation moving;
    private Animation moving1;
    private Rect destination;
    private AnimationManager animManager;

    private Animation deathAnimation;

    private int imgRadius;

    private double prevPos[];

    public Invader(Context context, float X, float Y, float radius, int width, int height){

        prevPos = new double[2];
        prevPos[0] = X;
        prevPos[1] = Y;

        this.X = X;
        this.Y = -Y;
        this.radius = radius;

        this.width = width;
        this.height = height;
        this.imgRadius = (int) ((float) width/ (float) 10);

        constantVel = radius* (float) 3.5;

        this.alive = true;
        this.death = false;
        this.touchDown = false;

        destination = new Rect();

        BitmapFactory bf = new BitmapFactory();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        Bitmap flash1 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.f0, options);
        Bitmap flash2 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.f1, options);
        Bitmap flash3 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.f2, options);
        Bitmap flash4 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.f3, options);

        Bitmap d0 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.fe0, options);
        Bitmap d1 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.fe1, options);
        Bitmap d2 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.fe2, options);
        Bitmap d3 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.fe3, options);
        Bitmap d4 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.fe4, options);
        Bitmap d5 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.fe5, options);
        Bitmap d6 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.fe6, options);
        Bitmap d7 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.fe7, options);

        moving = new Animation(new Bitmap[]{flash1, flash2, flash3, flash4}, 1);
        moving1 = new Animation(new Bitmap[]{flash1, flash2, flash3, flash4}, (float) .5);
        animManager = new AnimationManager(new Animation[]{moving, moving1});

        deathAnimation = new Animation(new Bitmap[]{d7,d6,d5,d4,d3,d2,d1,d0},(float).2);
        animManager.playAnim(0);
    }

    public void setSize(int w, int h) {
        width = w;
        height = h;
    }

    public boolean getState(){
        return alive;
    }

    public boolean getTouchDown() {
        return touchDown;
    }

    public void draw(Canvas canvas){
        if (alive && !death) {
            animManager.draw(canvas, destination);
        }
        else if (death) {
            deathAnimation.draw(canvas, destination);
        }
    }

    public void update(Point point, float[] linkPos){
        if (alive && !death) {
            destination.set((int) X - imgRadius, (int) -Y - imgRadius, (int) X + imgRadius, (int) -Y + imgRadius);

            animManager.update();

            double angle;
            angle = 0;
            float dy;
            float dx;
            dy = -((float)height / (float)2) - Y;
            dx = ((float)width / (float)2) - X;

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
            } else {
                if (dy > 0) {
                    angle = Math.toRadians(90);
                } else if (dy < 0) {
                    angle = Math.toRadians(270);
                } else if (dx > 0) {
                    angle = Math.toRadians(0);
                } else if (dx < 0) {
                    angle = Math.toRadians(180);
                }
            }
            vx = constantVel * (float) Math.cos(angle);
            vy = constantVel * (float) Math.sin(angle);
            X += vx * ((float) 1 / (float) 30) * ((float)1/(float)2);
            Y += vy * ((float) 1 / (float) 30) * ((float)1/(float)2);
            prevPos[0] = X;
            prevPos[1] = Y;
            //check if dead or alive
            double distance;
            distance = Math.sqrt(Math.pow((double) (linkPos[1] - Y), 2) + Math.pow((double) (linkPos[0] - X), 2));
            if (distance < radius + width / 30) {
                death = true;
                deathAnimation.play();
                GamePanel.deathSound();
            }
            double distance1;
            distance1 = Math.sqrt(Math.pow((double) ((-height / 2) - Y), 2) + Math.pow((double) ((width / 2) - X), 2));
            if (distance1 < (radius * .90) + width / 10) {
                touchDown = true;
            }
        }
        else if (death) {
            if (deathAnimation.updateOne()) {

            }
            else if (!deathAnimation.updateOne()) {
                alive = false;
                death = false;
            }
        }
    }
}
