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
    Call<List<Post>> getPosts();//gets the posts

    @GET("users/{userId}")
    Call<User> getUser(@Path("userId") int userId);//connects the users with the userid

    @GET("comments")
    Call<List<Comment>> getComments(@Query("postId") int postId);//get the list of comments with the postid

    @GET("posts")
    Call<List<Post>> getUserPosts(@Query("userId") int userId);//get all the posts but with userid

    @POST("comments")//serialize the comments for easier access
    @FormUrlEncoded
    Call<Comment> createComments(
        @Field("postId") int postId,
        @Field("email") String email,
        @Field("name") String name,
        @Field("body") String body
        );

}
