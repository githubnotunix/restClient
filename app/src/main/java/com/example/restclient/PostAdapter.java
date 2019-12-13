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
//class that displays all the posts for the first page
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private ArrayList<Post> posts = new ArrayList<>();
    private Context context;

    public PostAdapter(Context context, ArrayList<Post> posts){
        this.posts = posts;
        this.context = context;
    }
    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_list_item, parent, false);
        return new PostAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder holder, int position) {
        //get the information from the post that we want
        holder.postId.setText(String.valueOf("ID: " + posts.get(position).getId()));
        holder.postTitle.setText("Title:" + posts.get(position).getTitle());
        try {//display the username
            holder.postUserName.setText(posts.get(position).getUser().getUsername());
        } catch (Exception err) {
            err.printStackTrace();
        }
    }
    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void updatePost(int position, Post newPost) {
        posts.set(position, newPost);//update post position
        notifyItemChanged(position);//tell the system that the position changed
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView postUserId, postUserName, postId, postTitle, postBody;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            postId = (TextView) itemView.findViewById(R.id.post_id);
            postTitle = (TextView) itemView.findViewById(R.id.post_title);
            postBody = (TextView) itemView.findViewById(R.id.post_body);
            postUserName = (TextView) itemView.findViewById(R.id.username);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context.getApplicationContext(), "Recycler View Clicked", Toast.LENGTH_SHORT).show();
                    //put all the data into the user activity
                    Intent intent = new Intent(v.getContext(), UserActivity.class);
                    intent.putExtra("userId", posts.get(getAdapterPosition()).getUserId());
                    intent.putExtra("title", posts.get(getAdapterPosition()).getTitle());
                    intent.putExtra("body", posts.get(getAdapterPosition()).getText());
                    try {
                        intent.putExtra("user", posts.get(getAdapterPosition()).getUser());//still passes user through
                    } catch (Exception err) {
                        err.printStackTrace();
                    }
                    v.getContext().startActivity(intent);//pass to user activity
                }
            });

        }
    }
}
