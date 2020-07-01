package com.example.epicture;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SearchActivity extends AppCompatActivity {
    private MaterialButton home_btn;
    private MaterialButton favorites_btn;
    private MaterialButton search_btn;
    private MaterialButton profil_btn;
    private SearchView mSearchView;
    private static String query;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        final HttpHandler httpHandler = new HttpHandler(SearchActivity.this, this);
        this.home_btn = findViewById(R.id.home_button);
        this.favorites_btn = findViewById(R.id.favorites_button);
        this.search_btn = findViewById(R.id.search_button);
        this.profil_btn = findViewById(R.id.profil_button);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
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

        mSearchView = (SearchView) findViewById(R.id.search);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Filters();
                httpHandler.fetchData();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                query = mSearchView.getQuery().toString();
                return false;
            }
        });
    }

    private static void Filters() {
        Log.d("TAG","filter search");
        HttpHandler.base = "gallery/";
        HttpHandler.section = "search/";
        HttpHandler.sort = "";
        HttpHandler.page = "";
        HttpHandler.showV = "";
        HttpHandler.query= "?q=" + query;
    }
}
