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
//adapter class that helps display the recycler view for all the user comments
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private ArrayList<Comment> comments = new ArrayList<>();
    private Context context;

    public CommentAdapter(Context context, ArrayList<Comment> comments){
        this.comments = comments;
        this.context = context;
    }
    //method that inflates cardview data
    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_list_item, parent, false);
        return new CommentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder holder, int position) {
        //print out the comment data
        holder.commentEmail.setText("Email: " + comments.get(position).getEmail());
        holder.commentName.setText("Name: " + comments.get(position).getName());
        holder.commentBody.setText("Body: " + comments.get(position).getText());
    }

    //function that adds comments based on the index position
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
            //set all texviews to their correct id
            commentEmail = (TextView) itemView.findViewById(R.id.comment_email);
            commentName = (TextView) itemView.findViewById(R.id.comment_name);
            commentBody = (TextView) itemView.findViewById(R.id.comment_body);

        }
    }
}
