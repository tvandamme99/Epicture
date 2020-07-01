package com.example.epicture;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;

import android.os.Handler;
import android.view.Display;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class Loading extends AppCompatActivity {

    private ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;
        int middle_height = -(height/2) + 330;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        }, 1500);
        Animation moveToTop;
        moveToTop = new TranslateAnimation(0, 0, 0, middle_height);
        moveToTop.setDuration(2300);
        moveToTop.setFillAfter(true);
        this.logo = findViewById(R.id.logo_epicture);
        logo.startAnimation(moveToTop);
    }
}

