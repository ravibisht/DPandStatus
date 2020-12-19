package com.stark.dpstatus.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.stark.dpstatus.R;
import com.stark.dpstatus.activity.ShowImageStatusByCategoryActivity;
import com.stark.dpstatus.model.ImageStatusCategory;

import java.util.List;

public class ImageStatusCategoryAdapter extends RecyclerView.Adapter<ImageStatusCategoryAdapter.ViewHolder> {

    public static final String IMAGE_STATUS_CATEGORY_ID = "IMAGE_STATUS_CATEGORY_ID";
    public static final String IMAGE_STATUS_CATEGORY_NAME = "IMAGE_STATUS_CATEGORY_NAME";

    private List<ImageStatusCategory> imageStatusCategories;
    private Context mContext;

    public ImageStatusCategoryAdapter(List<ImageStatusCategory> imageStatusCategories, Context context) {
        this.imageStatusCategories = imageStatusCategories;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_status_category_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(mContext)
                .load(imageStatusCategories.get(position).getImage())
                .into(holder.categoryIV)
                .onLoadStarted(mContext.getDrawable(R.drawable.ic_loading_image));

        holder.categoryTitleTV.setText(imageStatusCategories.get(position).getCategoryName());

        holder.materialCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ShowImageStatusByCategoryActivity.class);
                intent.putExtra(IMAGE_STATUS_CATEGORY_NAME, imageStatusCategories.get(position).getCategoryName());
                intent.putExtra(IMAGE_STATUS_CATEGORY_ID, imageStatusCategories.get(position).getId());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return imageStatusCategories.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView materialCardView;
        ImageView categoryIV;
        TextView categoryTitleTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            materialCardView = itemView.findViewById(R.id.categoryCardView);
            categoryIV = itemView.findViewById(R.id.categoryImage);
            categoryTitleTV = itemView.findViewById(R.id.categoryText);
        }
    }
}
