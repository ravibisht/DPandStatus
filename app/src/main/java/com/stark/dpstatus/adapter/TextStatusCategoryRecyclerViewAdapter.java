package com.stark.dpstatus.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import com.stark.dpstatus.activity.ShowTextStatusStatusByCategoryActivity;
import com.stark.dpstatus.model.TextStatusCategory;

import java.util.List;

public class TextStatusCategoryRecyclerViewAdapter extends RecyclerView.Adapter<TextStatusCategoryRecyclerViewAdapter.ViewHolder> {
    public static final String TEXT_CATEGORY_NAME = "TEXT_CATEGORY_NAME";
    public static final String TEXT_CATEGORY_ID = "TEXT_CATEGORY_ID";


    private List<TextStatusCategory> textCategories;
    private Context mContext;

    public TextStatusCategoryRecyclerViewAdapter(List<TextStatusCategory> textCategories, Context context) {
        this.textCategories = textCategories;
        this.mContext = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_status_category_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TextStatusCategory tempTextStatusCategory = textCategories.get(position);
        holder.textView.setText(tempTextStatusCategory.getCategoryName());

        if (tempTextStatusCategory.getFontColor() != null && !tempTextStatusCategory.getFontColor().equals("default")) {
            holder.textView.setTextColor(Color.parseColor(tempTextStatusCategory.getFontColor()));
        }
        if (tempTextStatusCategory.getBackgroundColor() != null && !tempTextStatusCategory.getBackgroundColor().equals("default")) {
            holder.materialCardView.setCardBackgroundColor(Color.parseColor(tempTextStatusCategory.getBackgroundColor()));
        }
        Glide.with(mContext)
                .load(tempTextStatusCategory.getCategoryImage())
                .into(holder.imageView)
                .onLoadStarted(mContext.getDrawable(R.drawable.ic_loading_image));


        holder.materialCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ShowTextStatusStatusByCategoryActivity.class);
                intent.putExtra(TEXT_CATEGORY_NAME, tempTextStatusCategory.getCategoryName());
                intent.putExtra(TEXT_CATEGORY_ID, tempTextStatusCategory.getId());
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return textCategories.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private MaterialCardView materialCardView;
        private ImageView imageView;
        private TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            materialCardView = itemView.findViewById(R.id.statusCategoryCardView);
            imageView = itemView.findViewById(R.id.statusCategoryIV);
            textView = itemView.findViewById(R.id.statusCategoryTV);
        }
    }
}
