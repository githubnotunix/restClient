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

    private TextView username, usertitle, userbody;
    private TextView textViewResult;
    private RecyclerView crecyclerView;
    private CommentAdapter commentAdapter;
    private Button postButton;
    ArrayList<Comment> comments = new ArrayList<>();
    //int id_from_post;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        crecyclerView = findViewById(R.id.comment_recview);
        crecyclerView.setLayoutManager(new LinearLayoutManager(this));
        username = findViewById(R.id.user_name);
        postButton = (Button) findViewById(R.id.post_button);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateComment.class);
                // WIll make this activity listen for a result when the CreateComment class finishes
                startActivityForResult(intent, 1);
            }
        });
//        username.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        usertitle = findViewById(R.id.user_title);
        userbody = findViewById(R.id.user_body);
        Intent intent = getIntent();
        int id_from_post = intent.getIntExtra("userId", 0);
        String user_title = intent.getStringExtra("title" );
        String user_body = intent.getStringExtra("body");
        usertitle.setText("Title: " + user_title);
        userbody.setText("Body: " + user_body);
        // Get the user data from the intent
        final User user = intent.getParcelableExtra("user");

        username.setText(user.getUsername());
        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), UserViewActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

//        Call<User> call = jsonPlaceHolderApi.getUser(id_from_post);

//        call.enqueue(new Callback<User>() {
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

//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//                textViewResult.setText(t.getMessage());
//            }
//        });
        Retrofit retrofitTwo = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApiTwo = retrofitTwo.create(JsonPlaceHolderApi.class);

        Call<List<Comment>> callTwo = jsonPlaceHolderApiTwo.getComments(id_from_post);

        callTwo.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {

                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

                comments = new ArrayList<Comment>(response.body());
                commentAdapter = new CommentAdapter(UserActivity.this, comments);
                crecyclerView.setAdapter(commentAdapter);

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
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 1) {
            Comment comment = data.getParcelableExtra("comment");
            commentAdapter.addComment(comment);
        }
    }
}
