package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.myapplication.R;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;

/**
 * Created by asdfqwer on 7/15/2017.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{
    Context ctx;
    Activity act;

    private int width;
    private int height;

    private MainThread thread;

    public static boolean rootCondition = true;
    private boolean gameLoop = false;
    private boolean startLoop = true;
    private boolean intermediate = false;
    private boolean intermediate1 = false;
    private boolean end = false;
    private boolean end1 = false;
    private boolean endLoop = false;

    public static SoundPlayer playEffects;
    public static MediaPlayer background;
    public static int length;

    private int count;
    private Point playerPoint;
    private Invader[] invaders;
    private Link link;

    private int currScore;

    private DrawScore score;
    private DrawScore highScore;

    private Animation Swing;
    private Rect SwingLocation;
    private Bitmap start;
    private Rect startLocation;
    private Bitmap share;
    private Rect shareLocation;
    private Bitmap high;
    private Rect highLocation;
    private Rect highLocation1;
    private Bitmap restart;
    private Bitmap Score;
    private Rect ScoreLocation;

    private Animation pattern0;
    private Animation pattern1;
    private Animation pattern2;

    private Animation[] stars = new Animation[21];
    private Rect[] locations = new Rect[21];

    private Animation startAnimation;
    private Animation baseAnimation;
    private Rect baseLocation;
    private int baseRadius;

    private Animation endAnimation;
    private Animation endAnimation1;

    public GamePanel(Context context, Activity activity){
        super(context);

        playEffects = new SoundPlayer(getContext());
        background = MediaPlayer.create(getContext(), R.raw.background);
        background.setVolume(.7f,.7f);

        try {
            loadHighScore(context);
        } catch (Exception e) {e.printStackTrace(); saveHighScore(0, context);}

        currScore = 0;

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        width = metrics.widthPixels;
        height = metrics.heightPixels;

        getHolder().addCallback(this);

        Constants.CURRENT_CONTEXT = context;

        thread = new MainThread(getHolder(), this);
        playerPoint = new Point(0, 0);

        invaders = new Invader[30];

        link = new Link(width/2, height/2, width, height);

        this.act = activity;
        score = new DrawScore(width, height);
        highScore = new DrawScore(width, height);
        setFocusable(true);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        BitmapFactory bf = new BitmapFactory();
        Bitmap q0 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.pa0, options);
        Bitmap q1 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.pa1, options);
        Bitmap q2 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.pa2, options);
        Bitmap q3 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.pa3, options);
        Bitmap q4 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.pa4, options);
        Bitmap q5 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.pa5, options);
        Bitmap q6 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.pa6, options);
        Bitmap q7 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.pa7, options);
        Bitmap q8 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.pa8, options);
        Bitmap q9 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.pa9, options);
        Bitmap q10 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.pa10, options);
        Bitmap q11 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.pa11, options);
        Bitmap q12 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.pa12, options);
        Bitmap q13 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.pa13, options);
        Bitmap q14 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.pa14, options);

        Bitmap ba0 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.ba0, options);
        Bitmap ba1 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.ba1, options);
        Bitmap ba2 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.ba2, options);
        Bitmap ba3 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.ba3, options);
        Bitmap ba4 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.ba4, options);
        Bitmap ba5 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.ba5, options);
        Bitmap ba6 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.ba6, options);
        Bitmap ba7 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.ba7, options);
        Bitmap ba8 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.ba8, options);
        Bitmap ba9 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.ba9, options);
        Bitmap ba10 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.ba10, options);
        Bitmap ba11 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.ba11, options);
        Bitmap ba12 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.ba12, options);
        Bitmap ba13 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.ba13, options);
        Bitmap ba14 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.ba14, options);
        Bitmap ba15 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.ba15, options);
        Bitmap ba16 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.ba16, options);
        Bitmap ba17 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.ba17, options);

        start = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.start, options);
        startLocation = new Rect(width/2 - width/7, height/2 + height/14 - height/23, width/2 + width/7, height/2 + height/14 + height/23);

        share = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.share, options);
        shareLocation = new Rect(width/2 + (int) (width/8.5) - width/44, height/2 + height/6 - height/33, width/2 + (int) (width/8.5) + width/44, height/2 + height/6 + height/33);

        high = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.high, options);
        highLocation = new Rect(width/2 - (int) (width/8.5) - width/40, height/2 + height/6 - height/40, width/2 - (int) (width/8.5) + width/40, height/2 + height/6 + height/40);
        highLocation1 = new Rect(width/2 - (int) (width/8.5) - width/40, height/2 + height/6 - height/40, width/2 - (int) (width/8.5) + width/40, height/2 + height/6 + height/40);

        pattern0 = new Animation(new Bitmap[]{q0, q1, q2, q3, q4, q5, q6, q7, q8, q9, q8, q7, q6, q5, q4, q3, q2, q1},2);
        pattern1 = new Animation(new Bitmap[]{q0, q1, q2, q3, q4, q5, q6, q7, q8, q9, q8, q7, q6, q5, q4, q3, q2, q1},(float)0.5);
        pattern2 = new Animation(new Bitmap[]{q0, q1, q2, q3, q4, q3, q2, q1},(float) .025);

        startAnimation = new Animation(new Bitmap[]{ba0,ba1,ba2,ba3,ba4,ba5,ba6,ba7,ba8,ba9,ba11,ba12,ba13,ba14,ba15,ba16},3);
        baseAnimation = new Animation(new Bitmap[]{ba16, ba17}, 2);

        Score = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.score, options);
        restart = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.restart, options);

        Bitmap[] bitmapArray= new Bitmap[42];
        for (int i = 0; i < 42; i++) {
            bitmapArray[i] = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), context.getResources().getIdentifier("s" + i, "drawable",context.getPackageName()), options);
        }
        Swing = new Animation(bitmapArray, 2);
        Swing.play();
        SwingLocation = new Rect(width/2 - width/4, height/2 - height/20 - height/15, width/2 + width/4, height/2 - height/20 + height/15);
        ScoreLocation = new Rect(width/2 - width/10 - width/9, height/2 - height/10 - height/15, width/2 - width/10 + width/9, height/2 - height/10 + height/15);

        Bitmap[] endArray = new Bitmap[19];
        Bitmap[] endArray1 = new Bitmap[20];
        for (int i = 0; i < 19; i++) {
            endArray[i] = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), context.getResources().getIdentifier("be" + i, "drawable",context.getPackageName()), options);
        }
        for (int i = 0; i < 20; i++) {
            endArray1[i] = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), context.getResources().getIdentifier("bet" + i, "drawable",context.getPackageName()), options);
        }
        endAnimation = new Animation(endArray, (float) .75);
        endAnimation1 = new Animation(endArray1, (float) .4);

        for (int i = 0; i < stars.length; i++) {
            double rand;
            rand = Math.random();
            if (rand <= .5) {
                stars[i] = pattern2;
            }
            else if (rand > .5 && rand <= .90) {
                stars[i] = pattern1;
            }
            else {
                stars[i] = pattern0;
            }
            stars[i].play();

        }

        int size = (int) width*20/1000;
        locations[0] = new Rect((int) Math.floor(0.50849091009332823*width) - size,(int) Math.floor(0.27689303034267165*height)  - size,(int) Math.floor(0.50849091009332823*width)  + size,(int) Math.floor(0.27689303034267165*height)  + size);
        locations[1] = new Rect((int) Math.floor(0.46713982348921945*width) - size,(int) Math.floor(0.8037525260685161*height)  - size,(int) Math.floor(0.46713982348921945*width)  + size,(int) Math.floor(0.8037525260685161*height)  + size);
        locations[2] = new Rect((int) Math.floor(0.7142443375052229*width) - size,(int) Math.floor(0.9639982571588133*height)  - size,(int) Math.floor(0.7142443375052229*width)  + size,(int) Math.floor(0.9639982571588133*height)  + size);
        locations[3] = new Rect((int) Math.floor(0.53070103288002466*width) - size,(int) Math.floor(0.12392868299671085*height)  - size,(int) Math.floor(0.53070103288002466*width)  + size,(int) Math.floor(0.12392868299671085*height)  + size);
        locations[4] = new Rect((int) Math.floor(0.8846508865463473*width) - size,(int) Math.floor(0.7640789683439856*height)  - size,(int) Math.floor(0.8846508865463473*width)  + size,(int) Math.floor(0.7640789683439856*height)  + size);
        locations[5] = new Rect((int) Math.floor(0.659410914225176*width) - size,(int) Math.floor(0.6173469989204067*height)  - size,(int) Math.floor(0.659410914225176*width)  + size,(int) Math.floor(0.6173469989204067*height)  + size);
        locations[6] = new Rect((int) Math.floor(0.3824840137134512*width) - size,(int) Math.floor(0.9009968095213898*height)  - size,(int) Math.floor(0.3824840137134512*width)  + size,(int) Math.floor(0.9009968095213898*height)  + size);
        locations[7] = new Rect((int) Math.floor(0.10656381318417807*width) - size,(int) Math.floor(0.3325468920460871*height)  - size,(int) Math.floor(0.10656381318417807*width)  + size,(int) Math.floor(0.3325468920460871*height)  + size);
        locations[8] = new Rect((int) Math.floor(0.9123829544517978*width) - size,(int) Math.floor(0.2783101549859869*height)  - size,(int) Math.floor(0.9123829544517978*width)  + size,(int) Math.floor(0.2783101549859869*height)  + size);
        locations[9] = new Rect((int) Math.floor(0.29681036413819384*width) - size,(int) Math.floor(0.6086274763072217*height)  - size,(int) Math.floor(0.29681036413819384*width)  + size,(int) Math.floor(0.6086274763072217*height)  + size);
        locations[10] = new Rect((int) Math.floor(0.2685994404961075*width) - size,(int) Math.floor(0.16176171271493145*height)  - size,(int) Math.floor(0.2685994404961075*width)  + size,(int) Math.floor(0.16176171271493145*height)  + size);
        locations[11] = new Rect((int) Math.floor(0.09257407651948613*width) - size,(int) Math.floor(0.013249541152693833*height)  - size,(int) Math.floor(0.09257407651948613*width)  + size,(int) Math.floor(0.013249541152693833*height)  + size);
        locations[12] = new Rect((int) Math.floor(0.8478812985503604*width) - size,(int) Math.floor(0.4516294926492256*height)  - size,(int) Math.floor(0.8478812985503604*width)  + size,(int) Math.floor(0.4516294926492256*height)  + size);
        locations[13] = new Rect((int) Math.floor(0.14743429111586737*width) - size,(int) Math.floor(0.7453823760485218*height)  - size,(int) Math.floor(0.14743429111586737*width)  + size,(int) Math.floor(0.7453823760485218*height)  + size);
        locations[14] = new Rect((int) Math.floor(0.9071377618097232*width) - size,(int) Math.floor(0.7982683808587513*height)  - size,(int) Math.floor(0.9071377618097232*width)  + size,(int) Math.floor(0.7982683808587513*height)  + size);
        locations[15] = new Rect((int) Math.floor(0.16137372245364068*width) - size,(int) Math.floor(0.3077818622452072*height)  - size,(int) Math.floor(0.16137372245364068*width)  + size,(int) Math.floor(0.3077818622452072*height)  + size);
        locations[16] = new Rect((int) Math.floor(0.15510154509374252*width) - size,(int) Math.floor(0.3190507733082256*height)  - size,(int) Math.floor(0.15510154509374252*width)  + size,(int) Math.floor(0.3190507733082256*height)  + size);
        locations[17] = new Rect((int) Math.floor(0.9654849858487682*width) - size,(int) Math.floor(0.18719433207170844*height)  - size,(int) Math.floor(0.9654849858487682*width)  + size,(int) Math.floor(0.18719433207170844*height)  + size);
        locations[18] = new Rect((int) Math.floor(0.9330231105536836*width) - size,(int) Math.floor(0.0860488812172936*height)  - size,(int) Math.floor(0.9330231105536836*width)  + size,(int) Math.floor(0.0860488812172936*height)  + size);
        locations[19] = new Rect((int) Math.floor(0.6326751409471508*width) - size,(int) Math.floor(0.8195382265736695*height)  - size,(int) Math.floor(0.6326751409471508*width)  + size,(int) Math.floor(0.8195382265736695*height)  + size);
        locations[20] = new Rect((int) Math.floor(0.3615028158175746*width) - size,(int) Math.floor(0.8711833143556439*height)  - size,(int) Math.floor(0.3615028158175746*width)  + size,(int) Math.floor(0.8711833143556439*height)  + size);

        baseRadius = width/10;
        baseLocation = new Rect((width/2) - baseRadius, (height/2) - baseRadius, (width/2) + baseRadius, (height/2) + baseRadius);
    }

    public static void deathSound() {
        playEffects.playEffect(1);
    }
    public void pauseMusic() {
        if (background.isPlaying() && background.getCurrentPosition() > 1) {
            length = background.getCurrentPosition();
            System.out.println(length);
            background.pause();
        }
    }
    public void stopMusic() {
        if (background.isPlaying() && background.getCurrentPosition() > 1) {
            background.seekTo(0);
            background.pause();
        }
    }
    public void resumeMusic() {
        System.out.println(length);
        if (!background.isPlaying() && length > 1) {
            System.out.println(length);
            background.seekTo(length);
            background.start();
        }
    }
    public void startMusic() {
        if (!background.isPlaying() && rootCondition){
            background.start();
        }
    }
    public int loadHighScore(Context context) {
        String filename;
        filename = "HighScore";
        FileInputStream inputStream;
        int value1 = 0;
        try {
            inputStream = context.openFileInput(filename);

            byte bytes[];
            bytes = new byte[4];
            bytes[3] = (byte) inputStream.read();
            bytes[2] = (byte) inputStream.read();
            bytes[1] = (byte) inputStream.read();
            bytes[0] = (byte) inputStream.read();

            for (int i = 0; i < bytes.length; i++)
            {
                value1 += ((int) bytes[i] & 0xffL) << (8 * i);
            }
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value1;
    }
    public void saveHighScore(int val, Context context){
        byte[] integer = ByteBuffer.allocate(4).putInt(val).array();
        FileOutputStream outputStream;
        try {
            String filename;
            filename = "HighScore";

            outputStream = context.openFileOutput(filename, context.MODE_PRIVATE);
            for (int i=0;i<integer.length;i++) {
                outputStream.write(integer[i]);
            }
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }
    @Override
    public void surfaceCreated(SurfaceHolder holder){
        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        thread.Pause(false);
        thread.start();
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        thread.Pause(true);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                link.setPulled(true);
                playerPoint.set((int) event.getX(),(int) -event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                link.setPulled(true);
                playerPoint.set((int) event.getX(),(int) -event.getY());
                break;
            case MotionEvent.ACTION_UP:
                link.setPulled(false);
                break;
        }
        return true;
    }

    public void update(){
        if (rootCondition) {
            if (background.getCurrentPosition() == background.getDuration()) {
                length = 0;
            }

            for (int i = 0; i < stars.length; i++) {
                stars[i].update();
            }

            if (startLoop) {
                Swing.update();
                startMusic();

                if (playerPoint.x < (width / 2 + width / 7) && playerPoint.x > (width / 2 - width / 7) && -playerPoint.y < (height / 2 + height / 14 + height / 23) && -playerPoint.y > (height / 2 + height / 14 - height / 23)) {
                    startLoop = false;
                    intermediate = true;
                    stopMusic();
                    startAnimation.play();
                    playEffects.playEffect(2);
                }

                if (playerPoint.x < width / 2 + (int) (width / 8.5) + width / 44 && playerPoint.x > width / 2 + (int) (width / 8.5) - width / 44 && -playerPoint.y < height / 2 + height / 6 + height / 33 && -playerPoint.y > height / 2 + height / 6 - height / 33) {
                    System.out.println(124);
                    playerPoint.set(0, 0);

                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "http://play.google.com/store/apps/details?id=swing.com.game");
                    sendIntent.setType("text/plain");
                    act.startActivity(sendIntent);

                }
            } else if (intermediate) {
                if (startAnimation.updateOne()) {

                } else if (!startAnimation.updateOne()) {
                    baseAnimation.play();
                    intermediate = false;
                    gameLoop = true;
                }

            } else if (intermediate1) {
                for (int i = 0; i < invaders.length; i++) {
                    if (invaders[i] != null) {
                        invaders[i].update(playerPoint, link.getCoord());
                    }
                }
                if (end) {
                    if (endAnimation.updateOne()) {

                    } else if (!endAnimation.updateOne()) {
                        endAnimation.stop();
                        endAnimation1.play();
                        end = false;
                        end1 = true;
                    }
                } else if (end1) {
                    if (endAnimation1.updateOne()) {

                    } else if (!endAnimation1.updateOne()) {
                        endAnimation1.stop();
                        end1 = false;
                        intermediate1 = false;
                        playerPoint.set(0, 0);
                        endLoop = true;
                    }
                }

            } else if (endLoop) {
                startMusic();
                for (int i = 0; i < invaders.length; i++) {
                    if (invaders[i] != null) {
                        invaders[i].update(playerPoint, link.getCoord());
                    }
                }
                if (currScore > loadHighScore(getContext())) {
                    saveHighScore(currScore, getContext());
                }

                if (playerPoint.x < (width / 2 + width / 7) && playerPoint.x > (width / 2 - width / 7) && -playerPoint.y < (height / 2 + height / 14 + height / 23) && -playerPoint.y > (height / 2 + height / 14 - height / 23)) {
                    endLoop = false;
                    intermediate = true;
                    stopMusic();
                    startAnimation.play();
                    playEffects.playEffect(2);
                    link = new Link(width / 2, height / 2, width, height);
                    for (int j = 0; j < invaders.length; j++) {
                        invaders[j] = null;
                    }

                    currScore = 0;
                    playerPoint.set(0, 0);

                    for (int i = 0; i < invaders.length; i++) {
                        if (invaders[i] != null) {
                            invaders[i] = null;
                        }
                    }
                }

                if (playerPoint.x < width / 2 + (int) (width / 8.5) + width / 44 && playerPoint.x > width / 2 + (int) (width / 8.5) - width / 44 && -playerPoint.y < height / 2 + height / 6 + height / 33 && -playerPoint.y > height / 2 + height / 6 - height / 33) {
                    playerPoint.set(0, 0);
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "http://play.google.com/store/apps/details?id=swing.com.game");
                    sendIntent.setType("text/plain");
                    act.startActivity(sendIntent);

                }
            } else if (gameLoop) {
                startMusic();
                baseAnimation.update();

                link.update(playerPoint);

                //spawn new invader every x frames
                if (count == 20) {
                    for (int i = 0; i < invaders.length; i++) {
                        if (invaders[i] == null) {
                            //spawn a new invader at random location
                            int Case;
                            Case = (int) Math.floor(Math.random() * 4) + 1;

                            switch (Case) {
                                case 0:
                                    break;
                                case 1:
                                    invaders[i] = new Invader(getContext(), 0, (float) Math.random() * height, ((float) width / (float) 30), width, height);
                                    break;
                                case 2:
                                    invaders[i] = new Invader(getContext(), width, (float) Math.random() * height, width / 30, width, height);
                                    break;
                                case 3:
                                    invaders[i] = new Invader(getContext(), (float) Math.random() * width, 0, width / 30, width, height);
                                    break;
                                case 4:
                                    invaders[i] = new Invader(getContext(), (float) Math.random() * width, height, width / 30, width, height);
                                    break;
                            }
                            invaders[i].setSize(width, height);
                            break;
                        }
                    }
                    count = 0;
                }
                count++;

                //Update position of existing invaders
                for (int i = 0; i < invaders.length; i++) {
                    if (invaders[i] != null) {
                        invaders[i].update(playerPoint, link.getCoord());
                    }
                }
                //destroy dead invader

                for (int i = 0; i < invaders.length; i++) {
                    if (invaders[i] != null) {
                        if (!invaders[i].getState()) {
                            invaders[i] = null;
                            currScore++;
                        }

                    }
                }
                //check if touch down
                for (int i = 0; i < invaders.length; i++) {
                    if (invaders[i] != null) {
                        if (invaders[i].getTouchDown()) {
                            startLoop = false;
                            gameLoop = false;
                            endLoop = true;
                            playerPoint.set(0, 0);
                            baseAnimation.stop();
                            stopMusic();
                            endAnimation.play();
                            playEffects.playEffect(3);
                            intermediate1 = true;
                            end = true;
                            for (int j = 0; j < invaders.length; j++) {
                                if (invaders[j] != null) {
                                    invaders[j].constantVel = 0;
                                }
                            }
                        }
                    }
                }

            }
        }
    }
    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        canvas.drawColor(Color.rgb(28, 28, 28));

        for (int i = stars.length -1; i > -1; i--) {
            stars[i].draw(canvas, locations[i]);
        }


        if (startLoop) {
            canvas.drawBitmap(start, null, startLocation, new Paint());
            canvas.drawBitmap(high, null, highLocation, new Paint());
            canvas.drawBitmap(share, null, shareLocation, new Paint());
            highScore.draw(canvas, loadHighScore(act), width/60, height/30,(int) ((float) width*(float).57), (int) ((float) height *(float).665), width/30);
            Swing.draw(canvas, SwingLocation);
        }

        else if (intermediate) {
            startAnimation.draw(canvas, baseLocation);
        }

        else if (intermediate1) {
            for (int i = 0; i < invaders.length; i++) {
                if (invaders[i] != null) {
                    invaders[i].draw(canvas);
                }
            }

            if (end) {
                endAnimation.draw(canvas, baseLocation);
            }
            else if (end1) {
                endAnimation1.draw(canvas, baseLocation);
            }
        }
        else if (endLoop) {
            for (int i = 0; i < invaders.length; i++) {
                if (invaders[i] != null) {
                    invaders[i].draw(canvas);
                }
            }

            highScore.draw(canvas, loadHighScore(act), width/60, height/30,(int) ((float) width*(float).57), (int) ((float) height *(float).665), width/30);
            highScore.draw(canvas, currScore, width/50, height/20,(int) ((float) width*(float).665), (int) ((float) height *(float).41), width/26);
            canvas.drawBitmap(Score, null, ScoreLocation, new Paint());
            canvas.drawBitmap(high, null, highLocation1, new Paint());
            canvas.drawBitmap(restart, null, startLocation, new Paint());
            canvas.drawBitmap(share, null, shareLocation, new Paint());
        }
        else if (gameLoop) {
            baseAnimation.draw(canvas, baseLocation);

            for (int i = 0; i < invaders.length; i++) {
                if (invaders[i] != null) {
                    invaders[i].draw(canvas);
                }
            }

            link.draw(canvas);

            Paint paint = new Paint();
            paint.setColor(Color.rgb(0, 0, 0));
            paint.setTextSize(50);

            score.draw(canvas, currScore, width/40, height/30,(int) ((float) width* (float).9), (int)((float) width*(float).05), width/18);
        }
    }
}
