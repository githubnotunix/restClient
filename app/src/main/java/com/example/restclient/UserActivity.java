package com.example.restclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Button;
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

    private TextView userName, userTitle, userBody;
    private TextView textViewResult;
    private RecyclerView crecyclerView;
    private CommentAdapter commentAdapter;
    private Button postButton;
    ArrayList<Comment> comments = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        //display rec view layout
        crecyclerView = findViewById(R.id.comment_recview);
        crecyclerView.setLayoutManager(new LinearLayoutManager(this));
        userName = findViewById(R.id.user_name);
        //button that creates comment
        postButton = (Button) findViewById(R.id.post_button);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateComment.class);
                startActivityForResult(intent, 1);//send the activity while looking at result
            }
        });
        userTitle = findViewById(R.id.user_title);
        userBody = findViewById(R.id.user_body);
        //grab info from last activity
        Intent intent = getIntent();
        int idFromPost = intent.getIntExtra("userId", 0);
        String user_title = intent.getStringExtra("title" );
        String user_body = intent.getStringExtra("body");
        //display the title and body from the previous activity
        userTitle.setText("Title: " + user_title);
        userBody.setText("Body: " + user_body);
        final User user = intent.getParcelableExtra("user");//get the user data for the username
        userName.setText(user.getUsername());
        userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), UserViewActivity.class);
                intent.putExtra("user", user);//pass user data to the next activity
                startActivity(intent);
            }
        });
        //call retrofit
        Retrofit retrofitTwo = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApiTwo = retrofitTwo.create(JsonPlaceHolderApi.class);
        Call<List<Comment>> callTwo = jsonPlaceHolderApiTwo.getComments(idFromPost);
        callTwo.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {

                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }
                //get the content of the comments and display it to the rec view
                comments = new ArrayList<Comment>(response.body());
                commentAdapter = new CommentAdapter(UserActivity.this, comments);
                crecyclerView.setAdapter(commentAdapter);
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //sees if the result went through correctly
        if (requestCode == 1) {
            Comment comment = data.getParcelableExtra("comment");
            commentAdapter.addComment(comment);
        }
    }
}
