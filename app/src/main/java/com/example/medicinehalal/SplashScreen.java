package com.example.medicinehalal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_SCREEN=5000;

    Animation top_animation, bottom_animation;
    ImageView img_splash;
    TextView logotxt_splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Animation
        top_animation= AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottom_animation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        //hooks
        img_splash = findViewById(R.id.logoimg);
        logotxt_splash = findViewById(R.id.logotxt);

        img_splash.setAnimation(top_animation);
        logotxt_splash.setAnimation(bottom_animation);

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                Intent intent = new Intent(SplashScreen.this , LoginRegisterOption.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);
    }
}