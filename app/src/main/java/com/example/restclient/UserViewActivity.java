package com.example.restclient;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;

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

public class UserViewActivity extends FragmentActivity implements OnMapReadyCallback {
    private TextView name, username, email, phone, website, textViewResult;
    private GoogleMap mMap;
    private String lat, lng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_fragemnt);
        mapFragment.getMapAsync(this);
        name = (TextView) findViewById(R.id.usersname);
        username = (TextView) findViewById(R.id.usersusername);
        email = (TextView) findViewById(R.id.usersemail);
        phone = (TextView) findViewById(R.id.usersphone);
        website = (TextView) findViewById(R.id.userswebsite);

        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        Intent intent = getIntent();
        User user = intent.getParcelableExtra("user");
        name.setText("Name: " + user.getName());
        username.setText("User Name: " + user.getUsername());
        email.setText("Email: " + user.getEmail());
        phone.setText("Phone: " + user.getPhone());
        website.setText("Website: " +  user.getWebsite());
        
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://jsonplaceholder.typicode.com/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
//
//        Call<List<Comment>> callTwo = jsonPlaceHolderApi.getComments(id_from_post);
//
//        callTwo.enqueue(new Callback<List<Comment>>() {
//            @Override
//            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
//
//                if (!response.isSuccessful()) {
//                    textViewResult.setText("Code: " + response.code());
//                    return;
//                }
//
//                comments = new ArrayList<Comment>(response.body());
//                commentAdapter = new CommentAdapter(UserViewActivity.this, comments);
//                crecyclerView.setAdapter(commentAdapter);
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
//            public void onFailure(Call<List<Comment>> call, Throwable t) {
//                textViewResult.setText(t.getMessage());
//            }
//        });
//
//        //website.setText(user.getAddress().getGeo().getLat());
//
//        /*FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });*/
//
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //System.out.println("SYDNEY HERE");
        // Add a marker in Sydney, Australia, and move the camera.
        Intent intent = getIntent();
        User user = intent.getParcelableExtra("user");
        String lat = user.getAddress().getGeo().getLat();
        String lng = user.getAddress().getGeo().getLng();

        //LatLng sydney = new LatLng(-34, 151);
        LatLng sydney = new LatLng(Float.parseFloat(lat), Float.parseFloat(lng));
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


}
