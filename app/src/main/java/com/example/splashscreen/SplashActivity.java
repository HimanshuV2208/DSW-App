package com.example.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //adding text view
        TextView nameText = findViewById(R.id.nameText);

        //creating animation
        Animation animationFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);

        //fade in animation
        nameText.startAnimation(animationFadeIn);

        //intent to go from this activity to sign in activity
        Intent goSignIn = new Intent(SplashActivity.this, SignInActivity.class);

        //for creating new thread to rum simultaneously
        new Handler().postDelayed(new Runnable() {
            @Override

            //abstract method containing instructions to run in parallel thread
            public void run() {
                startActivity(goSignIn);

                //removes splash screen from activity stack
                finish();
            }
        }, 3000);
    }
}