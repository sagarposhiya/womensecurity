package com.example.womensecurity;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    Animation topanim;
    ImageView image;
    MediaPlayer music;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        //animations
        topanim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        //hooks
        image=findViewById(R.id.imageView);

        image.setAnimation(topanim);
        Thread timer=new Thread()
        {
            @Override
            public void run() {
                try {

                    music= MediaPlayer.create(MainActivity.this,R.raw.audio_1);
                    music.start();
                    sleep(2300);

                }
                catch(InterruptedException e)
                {

                }
                finally {
                    Intent i=new Intent(MainActivity.this,splashscreen.class);
                    startActivity(i);
                    finish();

                }
            }
        };
        timer.start();
    }
}