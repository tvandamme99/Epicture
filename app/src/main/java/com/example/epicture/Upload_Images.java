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
import com.google.android.material.textfield.TextInputLayout;

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


public class Upload_Images  extends AppCompatActivity {

    private TextView textUpload;
    private TextInputLayout title;
    private ImageButton back;
    private ImageView Image;
    private MaterialButton send_image;
    private ProgressBar loading;
    private static final String clientId = "bb0c749c6403fd2";
    private static final int PICK_IMAGE_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    public static String filePath = "";
    public static File file;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_upload__images);

        this.Image = findViewById(R.id.image_taken);
        this.send_image = findViewById(R.id.send_image);
        this.loading = findViewById(R.id.loading);
        this.title = findViewById(R.id.textTitle);
        loading.setVisibility(View.GONE);
        send_image.setVisibility(View.GONE);
        title.setVisibility(View.GONE);
        send_image.setEnabled(false);
        this.back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        title.addOnEditTextAttachedListener(new TextInputLayout.OnEditTextAttachedListener() {
            @Override
            public void onEditTextAttached(TextInputLayout textInputLayout) {
                send_image.setEnabled(textInputLayout.toString().length() != 0);
            }
        });

        Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSION_CODE);
                    } else {
                        pickImageFromGallery();
                    }
                }
            }
        });


        send_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.setVisibility(View.VISIBLE);
                send_image.setVisibility(View.GONE);
                new UploadImage().execute();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loading.setVisibility(View.GONE);
                        send_image.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(),"Sent :)",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }, 2000);
            }
        });
    }

    private class UploadImage extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            final MediaType mediaType = MediaType.parse("image/jpeg");
            Log.d("TAG ", filePath);

            RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("image", file.getName(), RequestBody.create(mediaType,file))
                    .build();
            Request request = new Request.Builder()
                    .url("https://api.imgur.com/3/image")
                    .method("POST", requestBody)
                    .addHeader("Authorization", "Bearer " + access_token)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                Log.d("TAG", response.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery();
                } else {
                    Toast.makeText(this, "Permission denied..!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_CODE && resultCode == RESULT_OK && null != data) {
            send_image.setVisibility(View.VISIBLE);
            title.setVisibility(View.VISIBLE);
            Uri uri = data.getData();
            Image.setImageURI(uri);
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
            String document_id = cursor.getString(0);
            document_id = document_id.substring(document_id.lastIndexOf(":")+1);
            cursor.close();

            cursor = getContentResolver().query(
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
            cursor.moveToFirst();
            filePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            cursor.close();
            file = new File(filePath);

            Log.d("TAG", filePath);
        }
    }
}


