package com.example.restclient;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {
    @GET("posts")
    Call<List<Post>> getPosts();

    @GET("users/{userId}")
    Call<User> getUser(@Path("userId") int userId);

    @GET("comments")
    Call<List<Comment>> getComments(@Query("postId") int postId);

    @GET("posts")
    Call<List<Post>> getUserPosts(@Query("userId") int userId);
}
