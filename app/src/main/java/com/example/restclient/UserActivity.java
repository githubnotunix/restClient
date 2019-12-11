package com.example.restclient;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserActivity extends AppCompatActivity {

    private TextView username, usertitle, userbody;
    private TextView textViewResult;
    //int id_from_post;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        username = findViewById(R.id.user_name);
        usertitle = findViewById(R.id.user_title);
        userbody = findViewById(R.id.user_body);
        Intent intent = getIntent();
        int id_from_post = intent.getIntExtra("userId", 0);
        String user_title = intent.getStringExtra("title" );
        String user_body = intent.getStringExtra("body");
        usertitle.setText("Title: " + user_title);
        userbody.setText("Body: " + user_body);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<User> call = jsonPlaceHolderApi.getUser(id_from_post);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if (!response.isSuccessful()) {
                    System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAA AQUI");
                    //textViewResult.setText("Code: " + response.code());
                    return;
                }
                //Log.e("USERACTIVITY" );

                //user = new User(response.body());
                User user = response.body();
                username.setText("User Name: " + user.getName());

                /*for (Post post : posts) {
                    String content = "";
                    content += "ID: " + post.getId() + "\n";
                    content += "User ID: " + post.getUserId() + "\n";
                    content += "Title: " + post.getTitle() + "\n";
                    content += "Text: " + post.getText() + "\n\n";

                    textViewResult.append(content);
                }*/
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }
}
