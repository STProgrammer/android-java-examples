package com.wfamedia.retrofithttpgson1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = findViewById(R.id.tvResult);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonHolderApi jsonHolderApi = retrofit.create(JsonHolderApi.class);

        /**
        Call<List<Post>> call = jsonHolderApi.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    tvResult.setText("Feilkode: " + response.code());
                    return;
                }
                StringBuilder content = new StringBuilder();
                List<Post> posts = response.body();
                for (Post post: posts) {
                    content.append("Id: " + post.getId());
                    content.append("\nUserid: " + post.getUserId());
                    content.append("\nTitle: " + post.getTitle());
                    content.append("\nBody: " + post.getBody() + "\n\n");
                }
                tvResult.setText(content.toString());
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                tvResult.setText(t.getMessage());
            }
        });
         */

        // Henter en Post:
        /**
        Call<Post> call2 = jsonHolderApi.getPost(4);
        call2.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call2, Response<Post> response) {
                if (!response.isSuccessful()) {
                    tvResult.setText("Feilkode: " + response.code());
                    return;
                }
                StringBuilder content = new StringBuilder();
                Post post = response.body();
                content.append("Id: ").append(post.getId());
                content.append("\nUserid: ").append(post.getUserId());
                content.append("\nTitle: ").append(post.getTitle());
                content.append("\nBody: ").append(post.getBody()).append("\n\n");

                tvResult.setText(content.toString());
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                tvResult.setText(t.getMessage());
            }
        });*/

        // Poster en Post vha. POST:
        Post newPost = new Post(1, 9999, "Min tittel", "Min tekst....");
        Call<Post> call3 = jsonHolderApi.postPost(newPost);
        call3.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call3, Response<Post> response) {
                if (!response.isSuccessful()) {
                    tvResult.setText("Feilkode: " + response.code());
                    return;
                }
                StringBuilder content = new StringBuilder();
                Post post = response.body();
                content.append("Id: ").append(post.getId());
                content.append("\nUserid: ").append(post.getUserId());
                content.append("\nTitle: ").append(post.getTitle());
                content.append("\nBody: ").append(post.getBody()).append("\n\n");

                tvResult.setText(content.toString());
            }

            @Override
            public void onFailure(Call<Post> call3, Throwable t) {
                tvResult.setText(t.getMessage());
            }
        });
    }
}