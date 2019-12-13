package com.example.restclient;

import android.content.Context;
import android.content.Intent;
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

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private ArrayList<Comment> comments = new ArrayList<>();
    private Context context;

    public CommentAdapter(Context context, ArrayList<Comment> comments){
        this.comments = comments;
        this.context = context;
    }
    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_list_item, parent, false);
        return new CommentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder holder, int position) {
        holder.commentEmail.setText("Email: " + comments.get(position).getEmail());
        holder.commentName.setText("Name: " + comments.get(position).getName());
        holder.commentBody.setText("Body: " + comments.get(position).getText());
       // holder.postId.setText(String.valueOf("ID: " + posts.get(position).getId()));
        // holder.postUserId.setText(String.valueOf("User ID: " + posts.get(position).getUserId()));
        //holder.postTitle.setText("Title:" + posts.get(position).getTitle());
        //holder.postBody.setText("Body " + posts.get(position).getText() + "\n");

    }
    public void addComment(Comment comment){
        comments.add(0, comment);
        notifyItemChanged(0);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView commentEmail, commentName, commentBody;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            commentEmail = (TextView) itemView.findViewById(R.id.comment_email);
            commentName = (TextView) itemView.findViewById(R.id.comment_name);
            commentBody = (TextView) itemView.findViewById(R.id.comment_body);
            /*postUserId = (TextView) itemView.findViewById(R.id.post_userid);
            postId = (TextView) itemView.findViewById(R.id.post_id);
            postTitle = (TextView) itemView.findViewById(R.id.post_title);
            postBody = (TextView) itemView.findViewById(R.id.post_body);*/
            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context.getApplicationContext(), "Recycler View Clicked", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(v.getContext(), UserActivity.class);
                    intent.putExtra("userId", posts.get(getAdapterPosition()).getUserId());
                    intent.putExtra("title", posts.get(getAdapterPosition()).getTitle());
                    intent.putExtra("body", posts.get(getAdapterPosition()).getText());
                    v.getContext().startActivity(intent);
                }
            });*/

        }
    }
}
