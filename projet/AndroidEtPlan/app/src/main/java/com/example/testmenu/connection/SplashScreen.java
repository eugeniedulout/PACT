package com.example.testmenu.connection;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testmenu.R;

public class SplashScreen extends AppCompatActivity {

    private final int SPLASH_SCREEN_TIMEOUT = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();

        ImageView searHorse = (ImageView) findViewById(R.id.seaHorseImage);
        ImageView rond = (ImageView) findViewById(R.id.rond);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN );
        Animation bouncing = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bubble_animation);
        rond.setAnimation(bouncing);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), Reception.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN_TIMEOUT);



    }
}