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
    private TextView textViewResult, username, userId;
    ArrayList<Post> posts = new ArrayList<>();
    private PostAdapter postAdapter;
    private RecyclerView recyclerView;
    private Retrofit retrofit;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        textViewResult = findViewById(R.id.text_view_result);
        recyclerView = findViewById(R.id.post_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        username = findViewById(R.id.username);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<List<Post>> call = jsonPlaceHolderApi.getPosts();


        // Get all the posts
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

                posts = new ArrayList<Post>(response.body());

                // For each post, get the associated user
                for (int i = 0; i < posts.size(); i++) {
                    fetchUser(i, posts.get(i).getUserId());
                }


                postAdapter = new PostAdapter(MainActivity.this, posts);
                recyclerView.setAdapter(postAdapter);

                /*for (Post post : posts) {
                    String content A= "";
                    content += "ID: " + post.getId() + "\n";
                    content += "User ID: " + post.getUserId() + "\n";
                    content += "Title: " + post.getTitle() + "\n";
                    content += "Text: " + post.getText() + "\n\n";

                    textViewResult.append(content);
                }*/
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });

//        Post post = new Post();
//        Call<User> callTwo = jsonPlaceHolderApi.getUser(post.getId());
//
//        callTwo.enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//
//                if (!response.isSuccessful()) {
//                    System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAA AQUI");
//                    //textViewResult.setText("Code: " + response.code());
//                    return;
//                }
//                //Log.e("USERACTIVITY" );
//
//                //user = new User(response.body());
//                User user = response.body();
//                username.setText("User Name: " + user.getName());
//
//                /*for (Post post : posts) {
//                    String content = "";
//                    content += "ID: " + post.getId() + "\n";
//                    content += "User ID: " + post.getUserId() + "\n";
//                    content += "Title: " + post.getTitle() + "\n";
//                    content += "Text: " + post.getText() + "\n\n";
//
//                    textViewResult.append(content);
//                }*/
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//                textViewResult.setText(t.getMessage());
//            }
//        });
    }

    public void fetchUser(final int position, int userId) {
        Call<User> call = jsonPlaceHolderApi.getUser(userId);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();

                posts.get(position).setUser(user);
                postAdapter.updateBlogPost(position, posts.get(position));
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
}