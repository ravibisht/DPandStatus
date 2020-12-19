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
import com.stark.dpstatus.activity.StatusActivity;
import com.stark.dpstatus.model.TextStatus;

import java.util.ArrayList;

import static android.content.Context.CLIPBOARD_SERVICE;

public class TextStatusRecyclerViewAdapter extends RecyclerView.Adapter<TextStatusRecyclerViewAdapter.ViewHolder> {

    public static final String TEXT_STATUS_PARCELABLE = "TEXT_STATUS_PARCELABLE";
    public static final String TEXT_STATUS_POSITION = "TEXT_STATUS_POSITION";
    private static final String TAG = "StatusRecyclerViewAdapt";
    public static ArrayList<TextStatus> statusData;
    private Context mContext;


    public TextStatusRecyclerViewAdapter(ArrayList<TextStatus> data, Context context) {
        this.statusData = data;
        this.mContext = context;
    }

    public TextStatusRecyclerViewAdapter() {
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_status_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(statusData.get(position).getTextStatus());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, StatusActivity.class);
                intent.putExtra(TEXT_STATUS_POSITION, position);
                intent.putExtra(StatusActivity.INTENT_ORIGIN, TextStatusViewPagerAdapter.TEXT_STATUS_ORIGIN);
                intent.putParcelableArrayListExtra(TEXT_STATUS_PARCELABLE, statusData);
                mContext.startActivity(intent);
            }
        });

        holder.shareAll.setOnClickListener(v -> {
            Intent shareAllIntent = new Intent();
            shareAllIntent.setAction(Intent.ACTION_SEND);
            shareAllIntent.setType("text/plain");
            shareAllIntent.putExtra(Intent.EXTRA_TEXT, statusData.get(position).getTextStatus());
            mContext.startActivity(shareAllIntent);
        });

        holder.copyStatus.setOnClickListener(v -> {
            ClipboardManager clipboardManager = (ClipboardManager) mContext.getSystemService(CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("text", statusData.get(position).getTextStatus());
            if (clipboardManager != null) {
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(mContext, "Status Copied", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(mContext, "Something went wrong !", Toast.LENGTH_SHORT).show();
        });

        holder.shareWhatsapp.setOnClickListener(v -> {

            Intent sendStatusWhatsApp = new Intent();
            sendStatusWhatsApp.setAction(Intent.ACTION_SEND);
            sendStatusWhatsApp.setType("text/plain");
            sendStatusWhatsApp.putExtra(Intent.EXTRA_TEXT, statusData.get(position).getTextStatus());
            sendStatusWhatsApp.setPackage("com.whatsapp");

            if (sendStatusWhatsApp.resolveActivity(mContext.getPackageManager()) != null) {
                mContext.startActivity(sendStatusWhatsApp);
            } else
                Toast.makeText(mContext, "Please Install WhatsApp to share Status ", Toast.LENGTH_SHORT).show();

        });

        holder.shareInstagrame.setOnClickListener(v -> {
            Intent shareInstagramIntent = new Intent();
            shareInstagramIntent.setPackage("com.instagram.android");
            shareInstagramIntent.setAction(Intent.ACTION_SEND);
            shareInstagramIntent.setType("text/plain");
            shareInstagramIntent.putExtra(Intent.EXTRA_TEXT, statusData.get(position).getTextStatus());

            if (shareInstagramIntent.resolveActivity(mContext.getPackageManager()) != null) {
                mContext.startActivity(shareInstagramIntent);
            } else
                Toast.makeText(mContext, "Please Install Instagram to share Status", Toast.LENGTH_SHORT).show();
        });

        if (statusData.get(position).getBackgroundColor() != null && !statusData.get(position).getBackgroundColor().equals("default")) {
            holder.cardView.setCardBackgroundColor(Color.parseColor(statusData.get(position).getBackgroundColor()));
        } else {
            holder.cardView.setCardBackgroundColor(mContext.getColor(RandomBackgroundColorGenerator.getRandomColor()));
        }
        if (statusData.get(position).getFontColor() != null && !statusData.get(position).getFontColor().equals("default")) {
            holder.textView.setTextColor(Color.parseColor(statusData.get(position).getFontColor()));
        }

    }

    @Override
    public int getItemCount() {
        return statusData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private MaterialCardView cardView;
        private TextView textView;
        private ImageView copyStatus, shareWhatsapp, shareAll, shareInstagrame;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.statusCardView);
            textView = itemView.findViewById(R.id.statusText);
            copyStatus = itemView.findViewById(R.id.shareCopy);
            shareWhatsapp = itemView.findViewById(R.id.shareWhatsApp);
            shareAll = itemView.findViewById(R.id.shareAll);
            shareInstagrame = itemView.findViewById(R.id.shareInstagrame);

        }
    }

}


