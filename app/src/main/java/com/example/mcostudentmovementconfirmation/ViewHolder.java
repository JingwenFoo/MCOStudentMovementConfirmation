 package com.example.mcostudentmovementconfirmation;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

import com.squareup.picasso.Picasso;

import java.net.URL;

 public class ViewHolder extends RecyclerView.ViewHolder {

    View view;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        view = itemView;
    }

    public void setdetails(Context context,String imageUrl,String imageName){

        ImageView mImageurl = view.findViewById(R.id.myImageView);
        TextView mimagename = view.findViewById(R.id.item_text);

        Glide.with(context).load(imageUrl).into(mImageurl);
        mimagename.setText(imageName);

        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        itemView.startAnimation(animation);

    }

}
