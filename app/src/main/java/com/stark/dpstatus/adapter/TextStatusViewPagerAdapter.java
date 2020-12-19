package com.stark.dpstatus.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.stark.dpstatus.R;
import com.stark.dpstatus.Util.RandomBackgroundColorGenerator;
import com.stark.dpstatus.model.TextStatus;

import java.util.List;

import static android.content.Context.CLIPBOARD_SERVICE;

public class TextStatusViewPagerAdapter extends RecyclerView.Adapter<TextStatusViewPagerAdapter.ViewHolder> {
    public static final String TEXT_STATUS_ORIGIN = "TEXT_STATUS_ORIGIN";
    private List<TextStatus> textStatuses;
    private Context mContext;

    public TextStatusViewPagerAdapter(List<TextStatus> textStatuses, Context context) {
        this.textStatuses = textStatuses;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_status_horizontal_list_item, parent, false);
        //view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(textStatuses.get(position).getTextStatus());
        holder.shareAll.setOnClickListener(v -> {
            Intent shareAllIntent = new Intent();
            shareAllIntent.setAction(Intent.ACTION_SEND);
            shareAllIntent.setType("text/plain");
            shareAllIntent.putExtra(Intent.EXTRA_TEXT, textStatuses.get(position).getTextStatus());
            mContext.startActivity(shareAllIntent);
        });

        holder.shareCopy.setOnClickListener(v -> {
            ClipboardManager clipboardManager = (ClipboardManager) mContext.getSystemService(CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("text", textStatuses.get(position).getTextStatus());
            if (clipboardManager != null) {
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(mContext, "Status Copied", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(mContext, "Something went wrong !", Toast.LENGTH_SHORT).show();
        });

        holder.shareWhatsApp.setOnClickListener(v -> {

            Intent sendStatusWhatsApp = new Intent();
            sendStatusWhatsApp.setAction(Intent.ACTION_SEND);
            sendStatusWhatsApp.setType("text/plain");
            sendStatusWhatsApp.putExtra(Intent.EXTRA_TEXT, textStatuses.get(position).getTextStatus());
            sendStatusWhatsApp.setPackage("com.whatsapp");

            if (sendStatusWhatsApp.resolveActivity(mContext.getPackageManager()) != null) {
                mContext.startActivity(sendStatusWhatsApp);
            } else
                Toast.makeText(mContext, "Please Install WhatsApp to share Status ", Toast.LENGTH_SHORT).show();

        });

        holder.shareInstagram.setOnClickListener(v -> {
            Intent shareInstagramIntent = new Intent();
            shareInstagramIntent.setPackage("com.instagram.android");
            shareInstagramIntent.setAction(Intent.ACTION_SEND);
            shareInstagramIntent.setType("text/plain");
            shareInstagramIntent.putExtra(Intent.EXTRA_TEXT, textStatuses.get(position).getTextStatus());

            if (shareInstagramIntent.resolveActivity(mContext.getPackageManager()) != null) {
                mContext.startActivity(shareInstagramIntent);
            } else
                Toast.makeText(mContext, "Please Install Instagram to share Status", Toast.LENGTH_SHORT).show();
        });

        if (textStatuses.get(position).getBackgroundColor() != null && !textStatuses.get(position).getBackgroundColor().equals("default")) {
            holder.cardView.setCardBackgroundColor(Color.parseColor(textStatuses.get(position).getBackgroundColor()));
        } else {
            holder.cardView.setCardBackgroundColor(mContext.getColor(RandomBackgroundColorGenerator.getRandomColor()));

        }
        if (textStatuses.get(position).getFontColor() != null && !textStatuses.get(position).getFontColor().equals("default")) {
            holder.textView.setTextColor(Color.parseColor(textStatuses.get(position).getFontColor()));
        }
    }

    @Override
    public int getItemCount() {
        return textStatuses.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        private ImageView shareCopy, shareAll, shareWhatsApp, shareInstagram;

        private MaterialCardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.hrStatusText);
            shareAll = itemView.findViewById(R.id.hrShareAll);
            shareCopy = itemView.findViewById(R.id.hrShareCopy);
            shareWhatsApp = itemView.findViewById(R.id.hrShareWhatsApp);
            shareInstagram = itemView.findViewById(R.id.hrShareInstagrame);
            cardView = itemView.findViewById(R.id.hrStatusCardView);
        }
    }
}
