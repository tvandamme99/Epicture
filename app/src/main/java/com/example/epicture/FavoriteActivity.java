package com.example.epicture;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;


public class FavoriteActivity extends AppCompatActivity {
    private MaterialButton home_btn;
    private MaterialButton favorites_btn;
    private MaterialButton search_btn;
    private MaterialButton profil_btn;
    private static JSONArray mItems;

    public static void callBackPhoto(JSONArray items)
    {
        mItems = items;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        this.home_btn = findViewById(R.id.home_button);
        this.favorites_btn = findViewById(R.id.favorites_button);
        this.search_btn = findViewById(R.id.search_button);
        this.profil_btn = findViewById(R.id.profil_button);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        HttpHandler httpHandler = new HttpHandler(FavoriteActivity.this, this);

        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next_activity = new Intent(getApplicationContext(), PhotosActivity.class);
                startActivity(next_activity);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });
        favorites_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next_activity = new Intent(getApplicationContext(), FavoriteActivity.class);
                startActivity(next_activity);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next_activity = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(next_activity);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });
        profil_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next_activity = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(next_activity);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next_activity = new Intent(getApplicationContext(), Upload_Images.class);
                startActivity(next_activity);
            }
        });
        Filters();
        httpHandler.fetchData();
    }

    private static void Filters() {
        HttpHandler.base = "account/";
        HttpHandler.section = LoginActivity.account_username;
        HttpHandler.sort = "/gallery_favorites";
        HttpHandler.page = "";
        HttpHandler.showV = "";
        HttpHandler.query = "";
    }
}