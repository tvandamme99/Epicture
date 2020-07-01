package com.example.epicture;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import com.google.android.material.button.MaterialButton;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private MaterialButton login_button;
    private static String clientId = "bb0c749c6403fd2";
    private static final String TAG = "HttpHandler";
    public static String access_token;
    public static String account_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.login_button = findViewById(R.id.login_button);


        String uri = getIntent().getDataString();
        access_token = "";
        account_username = "";

        if (uri != null){
            String mainPart = uri.toString().split("#")[1];
            String[] arguments = mainPart.split("&");
            String argument0 = arguments[0];
            String argument4 = arguments[4];
            access_token = argument0.split("=")[1];
            account_username = argument4.split("=")[1];
            Intent next_activity = new Intent(getApplicationContext(), PhotosActivity.class);
            startActivity(next_activity);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        }
   }

     public void login(View view) {
         Uri login_Uri = Uri.parse("https://api.imgur.com/oauth2/authorize?client_id=" + clientId + "&response_type=" + "token");
        Intent connectIntent = new Intent(Intent.ACTION_VIEW, login_Uri);
// Verify it resolves
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(connectIntent, 0);
        boolean isIntentSafe = activities.size() > 0;

// Start an activity if it's safe
        if (isIntentSafe) {
            startActivity(connectIntent);
            finish();
        }
    }
}