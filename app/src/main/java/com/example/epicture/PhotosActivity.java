package com.example.epicture;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import org.json.JSONArray;
import java.util.List;

public class PhotosActivity extends AppCompatActivity {

    // Local variable
    private MaterialButton home_btn;
    private MaterialButton favorites_btn;
    private MaterialButton search_btn;
    private MaterialButton profil_btn;

    // Constante variable
    private static final String TAG = "PhotoActivity";
    private static final String clientId = "bb0c749c6403fd2";

    // Private shared variable
    private static  List<Photo> mPhotos;
    private static JSONArray mItems;
    private static String mAccessToken;
    private static String userID;

    // Shared variable
    public static String selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        this.home_btn = findViewById(R.id.home_button);
        this.favorites_btn = findViewById(R.id.favorites_button);
        this.search_btn = findViewById(R.id.search_button);
        this.profil_btn = findViewById(R.id.profil_button);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final HttpHandler httpHandler = new HttpHandler(PhotosActivity.this, this);
        final Spinner spinner=(Spinner)findViewById(R.id.spinner);
        String[] filters=getResources().getStringArray(R.array.filters);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.spinner,R.id.text, filters);
        adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,filters){
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView txtView = (TextView) super.getView(position, convertView, parent);
                txtView.setTextColor(Color.WHITE);
                return txtView;
            }
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent){
                TextView txtView = (TextView) super.getDropDownView(position,convertView,parent);
                txtView.setTextColor(Color.BLACK);
                return txtView;
            }
        };
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = parent.getItemAtPosition(position).toString();
                Filters();
                httpHandler.fetchData();
            }
            public void onNothingSelected(AdapterView<?> parent)
            {
                Log.d("TAG", "Error");
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
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next_activity = new Intent(getApplicationContext(), Upload_Images.class);
                startActivity(next_activity);
            }
        });
    }

    private static void Filters() {
        HttpHandler.base = "gallery/";
        HttpHandler.page = "0.json";
        HttpHandler.showV = "?showViral=false";
        HttpHandler.query = "";
        if(selectedItem != null) {
            if (selectedItem.equals("Most Viral")) {
                HttpHandler.section = "hot/";
                HttpHandler.sort = "viral/";
                HttpHandler.showV = "?showViral=true";
            } else if (selectedItem.equals("Newest")) {
                HttpHandler.section = "top/";
                HttpHandler.sort = "time/";
            } else if (selectedItem.equals("Rising")) {
                HttpHandler.section = "user/";
                HttpHandler.sort = "rising/";
            } else {
                Log.d(TAG, "Might be a problem");
            }
        }
    }
 }




