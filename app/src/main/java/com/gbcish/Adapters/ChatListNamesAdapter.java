package com.gbcish.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
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
import com.gbcish.models.ReceiveChatMessages;

import java.util.ArrayList;
import java.util.List;

public class ChatListNamesAdapter extends RecyclerView.Adapter<ChatListNamesAdapter.MyViewHolder> {

    private List<ReceiveChatMessages> receivedChatMessages;
    private List<ReceiveChatMessages>  filteredreceivedChatMessages;

    private Context context;
    private OnItemClickListener onItemClickListener;

    public ChatListNamesAdapter(List<ReceiveChatMessages> postModels, Context context) {
        this.receivedChatMessages = postModels;
        this.context = context;
        this.filteredreceivedChatMessages=postModels;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.messages_custom_list, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final ReceiveChatMessages model = filteredreceivedChatMessages.get(position);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(Utils.getRandomDrawbleColor());
        requestOptions.error(Utils.getRandomDrawbleColor());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();
//
//        Glide.with(context)
//                .load(Uri.parse(model.getImageUrl().get(0).imageUrl))
//                .into(holder.imageView);

//        Glide.with(context)
//                .load(model.getImageUrl().get(0).imageUrl)
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
//                .into(holder.imageView);


    holder.tvName.setText(model.getMessages().getFrom());
    holder.linearLayout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(position, model);
        }
    });


    }

//    @Override
//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//                String charString = charSequence.toString();
//                if (charString.isEmpty()) {
//                    filteredreceivedChatMessages = receivedChatMessages;
//                } else {
//                    List<PostModel> filteredList = new ArrayList<>();
//                    for (PostModel row : receivedChatMessages) {
//
//                        // name match condition. this might differ depending on your requirement
//                        // here we are looking for name or phone number match
//                        if (row.getPost_category().toLowerCase().contains(charString.toLowerCase()) || row.getPost_city().contains(charSequence)) {
//                            filteredList.add(row);
//                        }
//                    }
//
//                    filteredreceivedChatMessages = filteredList;
//                }
//
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = filteredreceivedChatMessages;
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                filteredreceivedChatMessages = (ArrayList<PostModel>) filterResults.values;
//                notifyDataSetChanged();
//            }
//        };
//    }
    @Override
    public int getItemCount() {
        return filteredreceivedChatMessages.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public interface OnItemClickListener {
        void onItemClick(int position, ReceiveChatMessages postModel);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvName,tvemail;
        ImageView imageView;
        LinearLayout linearLayout;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvemail= itemView.findViewById(R.id.tvEmail);
            linearLayout= itemView.findViewById(R.id.linearLayout);



        }
    }
}
