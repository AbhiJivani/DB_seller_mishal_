package com.example.db_seller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.security.PublicKey;

public class Splash_Screen extends AppCompatActivity
{
    ImageView imageView;
    Runnable runnable;
    public static SharedPreferences preferences;
    public static SharedPreferences.Editor editor;
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
       // imageView = findViewById(R.id.splashimg);

        preferences = getSharedPreferences("mypref",MODE_PRIVATE);
        editor= preferences.edit();

//        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.splash_anim);
//        imageView.setAnimation(animation);

        int login = preferences.getInt("login", 0);

        if(login==1) {
            runnable = new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(Splash_Screen.this, Main_Page.class);
                    startActivity(intent);
                    finish();
                }
            };
            Handler handler = new Handler();
            handler.postDelayed(runnable, 5500);
        }
        if(login==0) {
            runnable = new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(Splash_Screen.this, Signup_Page.class);
                    startActivity(intent);
                    finish();
                }
            };
            Handler handler = new Handler();
            handler.postDelayed(runnable, 5500);
        }
    }
}
