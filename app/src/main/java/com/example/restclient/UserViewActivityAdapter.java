package com.example.restclient;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
//adapter class that displays all the posts associated with a specified user
public class UserViewActivityAdapter extends RecyclerView.Adapter<UserViewActivityAdapter.ViewHolder> {

    private ArrayList<Post> posts = new ArrayList<>();
    private Context context;

    public UserViewActivityAdapter(Context context, ArrayList<Post> posts){
        this.posts = posts;
        this.context = context;
    }
    @NonNull
    @Override
    public UserViewActivityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_posts_list_item, parent, false);
        return new UserViewActivityAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewActivityAdapter.ViewHolder holder, int position) {
        //display all the text
        holder.postId.setText(String.valueOf("ID: " + posts.get(position).getId()));
        holder.postUserId.setText(String.valueOf("User ID: " + posts.get(position).getUserId()));
        holder.postTitle.setText("Title:" + posts.get(position).getTitle());
        holder.postBody.setText("Body " + posts.get(position).getText() + "\n");
        try {
            holder.postUserName.setText(posts.get(position).getUser().getUsername());
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void updateBlogPost(int position, Post newPost) {
        posts.set(position, newPost);//update position of post
        notifyItemChanged(position);//update system
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView postUserId, postUserName, postId, postTitle, postBody;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            postUserId = (TextView) itemView.findViewById(R.id.user_post_userid);
            postId = (TextView) itemView.findViewById(R.id.user_post_id);
            postTitle = (TextView) itemView.findViewById(R.id.user_post_title);
            postBody = (TextView) itemView.findViewById(R.id.user_post_body);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context.getApplicationContext(), "Recycler View Clicked", Toast.LENGTH_SHORT).show();
                    //pass all the data to the user activity
                    Intent intent = new Intent(v.getContext(), UserActivity.class);
                    intent.putExtra("userId", posts.get(getAdapterPosition()).getUserId());
                    intent.putExtra("title", posts.get(getAdapterPosition()).getTitle());
                    intent.putExtra("body", posts.get(getAdapterPosition()).getText());
                    try {
                        intent.putExtra("user", posts.get(getAdapterPosition()).getUser());
                    } catch (Exception err) {
                        err.printStackTrace();
                    }
                    v.getContext().startActivity(intent);
                }
            });

        }
    }
}
