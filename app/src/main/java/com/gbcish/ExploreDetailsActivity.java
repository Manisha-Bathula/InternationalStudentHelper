package com.gbcish;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.internationalstudenthelper.R;
import com.gbcish.CommonFunctions.Utils;
import com.gbcish.models.PostModel;

public class ExploreDetailsActivity extends AppCompatActivity {

    private TextView tv_title1, tv_category1, tv_rent, tv_description1;
    private ImageView post_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_details);

        Intent it =getIntent();
        PostModel postModel= (PostModel) it.getSerializableExtra("PostModel");
        String pt=postModel.getPost_category();
        String p1t=postModel.getPost_city();

        tv_title1=findViewById(R.id.tv_title1);
        tv_category1=findViewById(R.id.tv_category1);
        tv_rent=findViewById(R.id.tv_rent);
        tv_description1=findViewById(R.id.tv_description1);
        post_image=findViewById(R.id.post_image);

        tv_title1.setText(postModel.getPost_title());
        tv_category1.setText(postModel.getPost_category());
        tv_rent.setText(postModel.getPost_rent());
        tv_description1.setText(postModel.getPost_description());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(Utils.getRandomDrawbleColor());
        requestOptions.error(Utils.getRandomDrawbleColor());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();
        Glide.with(this)
                .load(postModel.getImageUrl().get(0).imageUrl)
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                })
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(post_image);

    }
}