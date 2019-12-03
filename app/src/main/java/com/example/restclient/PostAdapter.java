package com.example.restclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

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
        holder.postId.setText(String.valueOf("ID: " + posts.get(position).getId()));
        holder.postUserId.setText(String.valueOf("User ID: " + posts.get(position).getUserId()));
        holder.postTitle.setText("Title:" + posts.get(position).getTitle());
        holder.postBody.setText("Body " + posts.get(position).getText() + "\n");

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView postUserId, postId, postTitle, postBody;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            postUserId = (TextView) itemView.findViewById(R.id.post_userid);
            postId = (TextView) itemView.findViewById(R.id.post_id);
            postTitle = (TextView) itemView.findViewById(R.id.post_title);
            postBody = (TextView) itemView.findViewById(R.id.post_body);

        }
    }
}
