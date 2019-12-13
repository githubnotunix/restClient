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

        holder.postId.setText(String.valueOf("ID: " + posts.get(position).getId()));
        holder.postUserId.setText(String.valueOf("User ID: " + posts.get(position).getUserId()));
        holder.postTitle.setText("Title:" + posts.get(position).getTitle());
        holder.postBody.setText("Body " + posts.get(position).getText() + "\n");
        // This is in a try catch whenever the posts initially loads without the user data
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

    // Pass position of post to update and object to update with
    public void updateBlogPost(int position, Post newPost) {
        // This will update the post with the 'new' post (it has a user object now)
        posts.set(position, newPost);
        // This will update the recycler view with the new data
        notifyItemChanged(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView postUserId, postUserName, postId, postTitle, postBody;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            postUserId = (TextView) itemView.findViewById(R.id.user_post_userid);
            postId = (TextView) itemView.findViewById(R.id.user_post_id);
            postTitle = (TextView) itemView.findViewById(R.id.user_post_title);
            postBody = (TextView) itemView.findViewById(R.id.user_post_body);
            //postUserName = (TextView) itemView.findViewById(R.id.user_post);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context.getApplicationContext(), "Recycler View Clicked", Toast.LENGTH_SHORT).show();
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
