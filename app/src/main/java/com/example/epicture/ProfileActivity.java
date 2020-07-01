package com.example.epicture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;

import java.io.File;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.epicture.LoginActivity.account_username;
import static com.example.epicture.LoginActivity.access_token;


public class ProfileActivity  extends AppCompatActivity {

    private MaterialButton home_btn;
    private MaterialButton favorites_btn;
    private MaterialButton search_btn;
    private MaterialButton profil_btn;
    private TextView name_profile;
    private static final String clientId = "bb0c749c6403fd2";
    private static final int PICK_IMAGE_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    public static String filePath = "";
    public static File file;
    private FloatingActionButton fab;
    private static JSONArray mItems;

    public static void callBackPhoto(JSONArray items)
    {
        mItems = items;
    }

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        HttpHandler httpHandler = new HttpHandler(ProfileActivity.this, this);

        this.home_btn = findViewById(R.id.home_button);
        this.favorites_btn = findViewById(R.id.favorites_button);
        this.search_btn = findViewById(R.id.search_button);
        this.profil_btn = findViewById(R.id.profil_button);
        this.name_profile = findViewById(R.id.name_profile);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next_activity = new Intent(getApplicationContext(), Upload_Images.class);
                startActivity(next_activity);
            }
        });
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
        name_profile.setText(account_username);
        Filters();
        httpHandler.fetchData();
    }
    public static void Filters() {
        HttpHandler.base = "account/";
        HttpHandler.section = LoginActivity.account_username;
        HttpHandler.sort = "/gallery_profile";
        HttpHandler.page = "";
        HttpHandler.showV = "";
    }
}


