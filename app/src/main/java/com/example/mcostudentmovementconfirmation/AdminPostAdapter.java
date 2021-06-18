package com.example.mcostudentmovementconfirmation;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.ColorSpace;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class AdminPostAdapter extends RecyclerView.Adapter<AdminPostAdapter.AdminPostViewHolder> {

    private ArrayList<Model> adminPostList;
    private Context context;

    public AdminPostAdapter(Context context, ArrayList<Model> adminPostList){
        this.context = context;
        this.adminPostList = adminPostList;
    }

    @NonNull
    @Override
    public AdminPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.admin_post_row,parent,false);
        return new AdminPostViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminPostViewHolder holder, int position) {
        Picasso.get().load(adminPostList.get(position).getPostImage()).into(holder.adminImageView);
        holder.adminDescription.setText(adminPostList.get(position).getDescription());
        holder.adminTime.setText(adminPostList.get(position).getTime());
        holder.moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context,holder.moreBtn);
                popupMenu.inflate(R.menu.option);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.menuItem_edit:
                                Intent edit = new Intent(context,UpdatePost.class);
                                edit.putExtra("editPostID",adminPostList.get(position).getPostID());
                                context.startActivity(edit);
                                break;
                            case R.id.menuItem_delete:
                                confirmDialog(adminPostList.get(position).getPostID());
                                break;
                            default:
                                break;

                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }
    private void confirmDialog(String postID)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("Delete Post?");
        builder.setMessage("Are you sure you want to delete?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Post");
                ref.child(postID).removeValue();
                Toast.makeText(context,"Post deleted successfully",Toast.LENGTH_SHORT).show();
                Intent refresh = new Intent(context,AdminPage.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(refresh);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
    @Override
    public int getItemCount() {
        return adminPostList.size();
    }

    public static class AdminPostViewHolder extends RecyclerView.ViewHolder {

        ImageView adminImageView;
        TextView adminDescription, adminTime;
        ImageButton moreBtn;

        public AdminPostViewHolder(@NonNull View itemView) {
            super(itemView);
            adminImageView = itemView.findViewById(R.id.adminimagePost);
            adminDescription = itemView.findViewById(R.id.admindescriptionPost);
            adminTime = itemView.findViewById(R.id.admindatePost);
            moreBtn = itemView.findViewById(R.id.moreBtn);

        }

    }

}
