package com.gbcish.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.List;

public class ExploreAdapter extends RecyclerView.Adapter<ExploreAdapter.MyViewHolder> {

    private List<PostModel> postModelList;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public ExploreAdapter(List<PostModel> postModels, Context context) {
        this.postModelList = postModels;
        this.context = context;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.explore_list_items, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final PostModel model = postModelList.get(position);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(Utils.getRandomDrawbleColor());
        requestOptions.error(Utils.getRandomDrawbleColor());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();
//
//        Glide.with(context)
//                .load(Uri.parse(model.getImageUrl().get(0).imageUrl))
//                .into(holder.imageView);

        Glide.with(context)
                .load(model.getImageUrl().get(0).imageUrl)
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
                .into(holder.imageView);



        holder.title.setText(model.getPost_title());
        holder.price.setText(model.getPost_rent());
        holder.location.setText(model.getPost_city());
        holder.category.setText(model.getPost_category());


    }

    @Override
    public int getItemCount() {
        return postModelList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public interface OnItemClickListener {
        void onItemClick(View view, int position, PostModel postModel);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title, price, category, location;
        ImageView imageView;
        ProgressBar progressBar;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tv_title);
            location = itemView.findViewById(R.id.tv_location);
            price = itemView.findViewById(R.id.tv_price);
            category = itemView.findViewById(R.id.tv_category);
            imageView = itemView.findViewById(R.id.imageView);

//            progressBar = itemView.findViewById(R.id.prograss_load_photo);


        }
    }
}
