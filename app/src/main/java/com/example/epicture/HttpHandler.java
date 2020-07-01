package com.example.epicture;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpHandler {
    Activity activity;
    Context context;

    public HttpHandler(Context context, Activity activity) {
        this.context=context;
        this.activity=activity;
    }

    private static final String TAG = "HttpHandler";
    private static String clientId = "bb0c749c6403fd2";
    private static OkHttpClient httpClient;

    private static String mAccessToken;


    // URL BUILDER VARIABLES
    public static String base = "gallery/";
    public static String section = "hot/";
    public static String sort = "viral/";
    public static String page = "0.json";
    public static String showV = "";
    public static String query = "";
    public static String mUrl = "";

    public void fetchData() {
        httpClient = new OkHttpClient.Builder().build();
        mUrl = "https://api.imgur.com/3/" + base + section + sort + page + showV + query;
        Log.d("TAG", ": URl is: " + mUrl);

        Request request = new Request.Builder()
                .url(mUrl)
                .addHeader("Authorization", "Client-ID " + clientId)
                .header("User-Agent", "epicture")
                .build();
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("TAG", "An error has occurred " + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject data = new JSONObject(response.body().string());
                    JSONArray items = data.getJSONArray("data");
                    final List<Photo> photos = new ArrayList<Photo>();
                    try {
                        for(int i = 0; i < items.length(); i++) {
                            JSONObject item = items.getJSONObject(i);
                            Photo photo = new Photo();
                            if(item.getBoolean("is_album")) {
                                photo.id = item.getString("cover");
                            } else {
                                photo.id = item.getString("id");
                            }
                            photo.title = item.getString("title");
                            photos.add(photo);

                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    render(photos);
                                }
                            });
                        }
                    } catch (Exception e) {
                        Log.e("TAG" , "Something went wrong.");
                    }
                } catch (Exception e) {
                    Log.e("TAG", "Something went wrong.");
                }
            }
        });
    }

    private static class PhotoVH extends RecyclerView.ViewHolder {
        ImageView photo;
        TextView title;
        public PhotoVH(View itemView) {
            super(itemView);
        }
    }

    private void render(final List<Photo> photos) {

        final RecyclerView rv = (RecyclerView) activity.findViewById(R.id.rv_of_photos);
        rv.setLayoutManager(new LinearLayoutManager(activity));
        final RecyclerView.Adapter<PhotoVH> adapter = new RecyclerView.Adapter<PhotoVH>() {
            @Override
            public PhotoVH onCreateViewHolder(ViewGroup parent, int viewType) {
                PhotoVH vh = new PhotoVH(activity.getLayoutInflater().inflate(R.layout.item, null));
                vh.photo = (ImageView) vh.itemView.findViewById(R.id.photo);
                vh.title = (TextView) vh.itemView.findViewById(R.id.title);
                return vh;
            }
            @Override
            public void onBindViewHolder(PhotoVH holder, int position) {
                Picasso.with(activity).load("https://i.imgur.com/" +
                        photos.get(position).id + ".jpg").into(holder.photo);
                holder.title.setText(photos.get(position).title);
            }
            @Override
            public int getItemCount() {
                return photos.size();
            }
        };
        rv.setAdapter(adapter);
    }
}
