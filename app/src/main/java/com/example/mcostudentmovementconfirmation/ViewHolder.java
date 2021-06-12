package com.example.mcostudentmovementconfirmation;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class ViewHolder extends RecyclerView.ViewHolder {

    View view;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        view = itemView;
    }

    public void setdetails(Context context, String imageName, String imageUrl){

        TextView mimagename = view.findViewById(R.id.item_text);
        ImageView mImageurl = view.findViewById(R.id.myImageView);

        mimagename.setText(imageName);
        Picasso.get().load(imageUrl).into(mImageurl);

        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        itemView.startAnimation(animation);

    }
}
