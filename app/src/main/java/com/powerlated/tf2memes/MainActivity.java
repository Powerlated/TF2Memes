package com.powerlated.tf2memes;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {
    MediaPlayer music, sound, sfx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Activity activity = (Activity) this;
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        fullscreen();
        setContentView(R.layout.activity_main);
    }

    public void fullscreen() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    boolean musicPlayed;

    @Override
    public void onStart() {
        super.onStart();
        playMusic();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopMusic();
    }

    private void stopMusic() {
        music = MediaPlayer.create(MainActivity.this,R.raw.music);
        music.stop();
        Log.d("TF2Memes", "Stopped music");
    }

    private void playMusic() {
        if (musicPlayed) return;
        musicPlayed = true;
        music = MediaPlayer.create(MainActivity.this,R.raw.music);
        music.start();
        Log.d("TF2Memes", "Played music");
    }

    private void playSound() {
        sound = MediaPlayer.create(MainActivity.this,R.raw.sound);
        sound.start();
        Log.d("TF2Memes", "Played sound");
    }

    private void playSfx() {
        sfx = MediaPlayer.create(MainActivity.this,R.raw.sfx);
        sfx.start();
        Log.d("TF2Memes", "Played sfx");
    }

}
