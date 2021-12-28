package com.example.myapplication;

import static com.example.myapplication.GamePanel.playEffects;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;


public class MainActivity extends Activity {
    public static RelativeLayout layout;
    private GamePanel gamePanel;

    static final String place = "musicPos";

    public static void share() {
        Intent intent = new Intent();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //Request full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        gamePanel = new GamePanel(this,this);

        //Create a copy of the Bundle
        if (savedInstanceState != null){
            Bundle newBundle = new Bundle(savedInstanceState);
        }

        //Set main renderer
        setContentView(gamePanel);
    }

    @Override
    public void onBackPressed(){
        moveTaskToBack(true);
        GamePanel.rootCondition = false;
        gamePanel.pauseMusic();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        gamePanel.stopMusic();
    }

    @Override
    public void onPause() {
        super.onPause();
        GamePanel.rootCondition = false;
        gamePanel.pauseMusic();
        playEffects.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        GamePanel.rootCondition = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        GamePanel.rootCondition = false;
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onRestart() {
        super.onRestart();
        GamePanel.rootCondition = true;
        playEffects.resume();
        gamePanel.resumeMusic();
    }
}
