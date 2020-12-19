package com.stark.dpstatus.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.stark.dpstatus.R;
import com.stark.dpstatus.activity.StatusActivity;
import com.stark.dpstatus.model.ImageStatus;

import java.util.ArrayList;

public class ImageStatusRecyclerViewAdapter extends RecyclerView.Adapter<ImageStatusRecyclerViewAdapter.ViewHolder> {

    public static final String IMAGE_STATUS_POSITION = "IMAGE_STATUS_POSITION";
    public static final String IMAGE_STATUS_PARCELABLE = "IMAGE_STATUS_PARCELABLE";

    public ArrayList<ImageStatus> imageStatuses;
    private Context mContext;

    public ImageStatusRecyclerViewAdapter(ArrayList<ImageStatus> imageStatuses, Context context) {
        this.imageStatuses = imageStatuses;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_status_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(mContext)
                .load(imageStatuses.get(position).getImage())
                .into(holder.statusImage)
                .onLoadStarted(mContext.getDrawable(R.drawable.ic_loading_image));

        holder.cardView.setOnClickListener(v -> {
            Intent send = new Intent(mContext, StatusActivity.class);
            send.putExtra(IMAGE_STATUS_POSITION, position);
            send.putParcelableArrayListExtra(IMAGE_STATUS_PARCELABLE, imageStatuses);
            send.putExtra(StatusActivity.INTENT_ORIGIN, ImageStatusViewPagerAdapter.IMAGE_STATUS_ORIGIN);
            mContext.startActivity(send);
        });

    }

    @Override
    public int getItemCount() {
        return imageStatuses.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView statusImage;
        private MaterialCardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            statusImage = itemView.findViewById(R.id.statusImage);
            cardView = itemView.findViewById(R.id.imgStatusCardView);
        }
    }
}
