package com.example.restclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView textViewResult, userName, userId;
    ArrayList<Post> posts = new ArrayList<>();
    private PostAdapter postAdapter;
    private RecyclerView recyclerView;
    private Retrofit retrofit;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.post_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userName = findViewById(R.id.username);
        //call retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        //use retrofit to call all the posts
        Call<List<Post>> call = jsonPlaceHolderApi.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }
                posts = new ArrayList<Post>(response.body());//gets all the post info
                //get the user for each post
                for (int i = 0; i < posts.size(); i++) {
                    fetchUser(i, posts.get(i).getUserId());
                }
                postAdapter = new PostAdapter(MainActivity.this, posts);
                recyclerView.setAdapter(postAdapter);//displays post of rec view
            }
            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }
    //fumction to call user by usering the user id
    public void fetchUser(final int position, int userId) {
        Call<User> call = jsonPlaceHolderApi.getUser(userId);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();//get all user information
                posts.get(position).setUser(user);//get the post based off userid
                postAdapter.updatePost(position, posts.get(position));//display the blog posts
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
}