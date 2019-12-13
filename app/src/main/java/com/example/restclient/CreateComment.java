package com.example.restclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateComment extends AppCompatActivity {
    private EditText eEmail, eName, eBody, textViewResult;
    private Button add_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_comment);
        //set ids equal to each other
        eEmail = (EditText)findViewById(R.id.commentemail);
        eName = (EditText)findViewById(R.id.commentname);
        eBody = (EditText) findViewById(R.id.commentbody);
        //button that adds a new comment to the user comments
        add_button= (Button) findViewById(R.id.add_comment_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call retrofit
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://jsonplaceholder.typicode.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
                //retrofit will then get the information below
                Call<Comment> call = jsonPlaceHolderApi.createComments(
                        1,
                        eEmail.getText().toString(),
                        eName.getText().toString(),
                        eBody.getText().toString());

                call.enqueue(new Callback<Comment>() {
                    @Override
                    public void onResponse(Call<Comment> call, Response<Comment> response) {

                        if (!response.isSuccessful()) {
                            textViewResult.setText("Code: " + response.code());
                            return;
                        }
                        //get everything inside the comment object
                        Comment comment = response.body();
                        Intent intent = new Intent();
                        intent.putExtra("comment", comment);
                        //set the result to okay to see how it passes through
                        setResult(Activity.RESULT_OK, intent);
                        finish();//finish the activity because we are done with this page
                    }
                    @Override
                    public void onFailure(Call<Comment> call, Throwable t) {
                        textViewResult.setText(t.getMessage());
                    }
                });
            }
        });
    }

}
