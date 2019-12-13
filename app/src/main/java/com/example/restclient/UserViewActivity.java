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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
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
    ArrayList<Post> posts = new ArrayList<>();
    private UserViewActivityAdapter userViewActivityAdapter;
    private RecyclerView precyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view);
        //code that displays map fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);
        name = (TextView) findViewById(R.id.usersname);
        username = (TextView) findViewById(R.id.usersusername);
        email = (TextView) findViewById(R.id.usersemail);
        phone = (TextView) findViewById(R.id.usersphone);
        website = (TextView) findViewById(R.id.userswebsite);
        precyclerView = (RecyclerView) findViewById(R.id.user_post_recview);
        precyclerView.setLayoutManager(new LinearLayoutManager(this));
        //grab info from last activity
        Intent intent = getIntent();
        User user = intent.getParcelableExtra("user");
        name.setText("Name: " + user.getName());
        username.setText("User Name: " + user.getUsername());
        email.setText("Email: " + user.getEmail());
        phone.setText("Phone: " + user.getPhone());
        website.setText("Website: " +  user.getWebsite());
        //call retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Post>> call = jsonPlaceHolderApi.getUserPosts(user.getId());//get all posts with userid
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }
                //display post data in rec view
                posts = new ArrayList<Post>(response.body());
                userViewActivityAdapter = new UserViewActivityAdapter(UserViewActivity.this, posts);
                precyclerView.setAdapter(userViewActivityAdapter);
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }
    //displays the google api
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //get the user data
        Intent intent = getIntent();
        User user = intent.getParcelableExtra("user");
        String lat = user.getAddress().getGeo().getLat();
        String lng = user.getAddress().getGeo().getLng();
        //put in the user location and display a marker
        LatLng userLoc = new LatLng(Float.parseFloat(lat), Float.parseFloat(lng));
        mMap.addMarker(new MarkerOptions().position(userLoc).title("User Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(userLoc));
    }
}
