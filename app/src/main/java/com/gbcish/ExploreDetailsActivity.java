package com.gbcish;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.gbcish.Adapters.ViewPagerAdapter;
import com.gbcish.CommonFunctions.Utils;
import com.gbcish.models.PostImages;
import com.gbcish.models.PostModel;

import java.util.ArrayList;

public class ExploreDetailsActivity extends AppCompatActivity {

    private TextView tv_title1, tv_category1, tv_rent, tv_description1,tv_address;
    private ImageView post_image;
    // creating object of ViewPager
    ViewPager mViewPager;
    ViewPagerAdapter viewPagerAdapter;
    ArrayList<PostImages> images;
    Button bt_get_direction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_details);


        Intent it =getIntent();
        final PostModel postModel= (PostModel) it.getSerializableExtra("PostModel");
        String pt=postModel.getPost_category();
        String p1t=postModel.getPost_city();

        bt_get_direction=findViewById(R.id.bt_get_direction);
        bt_get_direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(ExploreDetailsActivity.this,MapActivity.class);
                intent.putExtra("SelectedAdd",postModel);
                startActivity(intent);
            }
        });
        tv_title1=findViewById(R.id.tv_title1);
        tv_category1=findViewById(R.id.tv_category1);
        tv_rent=findViewById(R.id.tv_rent);
        tv_description1=findViewById(R.id.tv_description1);
        tv_address=findViewById(R.id.tv_address);
        post_image=findViewById(R.id.post_image);

        tv_title1.setText(postModel.getPost_title());
        tv_category1.setText(postModel.getPost_category());
        tv_rent.setText(postModel.getPost_rent()+" "+"CAD");
        tv_description1.setText(postModel.getPost_description());

        tv_address.setText(postModel.getFullAddress());

        // Initializing the ViewPager Object
        mViewPager = (ViewPager)findViewById(R.id.viewPagerMain);

        // Initializing the ViewPagerAdapter
        viewPagerAdapter = new ViewPagerAdapter(ExploreDetailsActivity.this,postModel.getImageUrl() );

        // Adding the Adapter to the ViewPager
        mViewPager.setAdapter(viewPagerAdapter);
//        RequestOptions requestOptions = new RequestOptions();
//        requestOptions.placeholder(Utils.getRandomDrawbleColor());
//        requestOptions.error(Utils.getRandomDrawbleColor());
//        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
//        requestOptions.centerCrop();
//        Glide.with(this)
//                .load(postModel.getImageUrl().get(0).imageUrl)
//                .apply(requestOptions)
//                .listener(new RequestListener<Drawable>() {
//                    @Override
//                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
////                        holder.progressBar.setVisibility(View.GONE);
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
////                        holder.progressBar.setVisibility(View.GONE);
//                        return false;
//                    }
//
//                })
//                .transition(DrawableTransitionOptions.withCrossFade())
//                .into(post_image);

    }
}