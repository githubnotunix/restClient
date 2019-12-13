package com.example.restclient;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
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

    @POST("comments")
    @FormUrlEncoded
    Call<Comment> createComments(
        @Field("postId") int postId,
        @Field("email") String email,
        @Field("name") String name,
        @Field("body") String body
        );

}
