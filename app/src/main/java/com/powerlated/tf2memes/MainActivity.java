package com.powerlated.tf2memes;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.VideoView;

import com.squareup.seismic.ShakeDetector;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity implements ShakeDetector.Listener {

    static int pootisTime;
    static float pitch = 1.0f;
    static boolean done;
    static boolean soundPlaying;
    static boolean musicFinished;

    MediaPlayer music, sound, sfx;

    long time = System.currentTimeMillis();

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
        playVideo();
        if (done) return;
        done = true;
        playMusic();
        playSfx();
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
        music.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
    }

    private void playMusic() {
        if (musicPlayed) return;
        musicPlayed = true;
        music = MediaPlayer.create(MainActivity.this,R.raw.music);
        music.start();
        Log.d("TF2Memes", "Played music");
        music.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mp.release();
                musicFinished = true;
            }
        });
    }

    private void playSound(boolean pootis) {

        if (!musicFinished) return;

        if (pootis) {
            Random r = new Random();
            float finalX = r.nextFloat() * (1.25f - 0.75f) + 0.75f;
            PlaybackParams params = new PlaybackParams();
            params.setPitch(finalX);
            sound = MediaPlayer.create(this, R.raw.pootis);
            sound.setPlaybackParams(params);
            sound.start();
        } else {
            int randomNumber = ThreadLocalRandom.current().nextInt(1, 20 + 1);

            if (soundPlaying) return;

            soundPlaying = true;

            PlaybackParams params = new PlaybackParams();

            if (randomNumber == 1 || randomNumber == 2 || randomNumber == 3 || randomNumber == 4 || randomNumber == 5) {
                sound = MediaPlayer.create(this, R.raw.medic_taunt);
            } else if (randomNumber == 6 || randomNumber == 7 || randomNumber == 8) {
                sound = MediaPlayer.create(this, R.raw.pootis);
                pootisTime = ThreadLocalRandom.current().nextInt(10, 25 + 1);
            } else {
                sound = MediaPlayer.create(this, R.raw.sound);
                params.setPitch(pitch);
                sound.setPlaybackParams(params);
            }

            Log.d("TF2Memes", "PootisNumber: " + randomNumber);

            sound.start();
        }
        Log.d("TF2Memes", "Played sound");
        sound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mp.release();
                pitch += 0.1;
                if (pootisTime != 0) {
                    playSound(true);
                    pootisTime--;
                    return;
                }
                soundPlaying = false;
            }
        });
    }

    private void playSfx() {
        sfx = MediaPlayer.create(MainActivity.this,R.raw.sfx);
        sfx.start();
        Log.d("TF2Memes", "Played sfx");
        sfx.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
    }

    private void playVideo() {
        VideoView videoView = (VideoView) findViewById(R.id.videoViewRelative);
        Uri uri = Uri.parse("android.resource://" +getPackageName() + "/" +R.raw.door_effect);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
        {
            public void onCompletion(MediaPlayer mp)
            {

            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        playSound(false);
        return true;
    }

    @Override
    public void hearShake() {
        playSound(false);
    }
}
