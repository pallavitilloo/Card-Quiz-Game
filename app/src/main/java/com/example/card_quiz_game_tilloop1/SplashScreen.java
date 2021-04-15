package com.example.card_quiz_game_tilloop1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    ImageView splash_image;
    Animation topAnimation, downAnimation;
    Handler handler;
    TextView tvWelcome;
    final int TIME_LIMIT = 8000;     // The time delay after splash screen in milliseconds.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        //  This code is to animate the splash image
        splash_image = findViewById(R.id.logo_id);
        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_slide);
        splash_image.startAnimation(topAnimation);

        // This code is to animate the welcome message displayed on the splash screen
        tvWelcome = findViewById(R.id.welcome_msg);
        downAnimation = AnimationUtils.loadAnimation(this, R.anim.down_slide);
        tvWelcome.startAnimation(downAnimation);

        // postDelayed(Runnable, time) method is used to start the second activity with a delay
        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },TIME_LIMIT);
    }
}