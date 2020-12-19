package com.stark.dpstatus.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.card.MaterialCardView;
import com.stark.dpstatus.BuildConfig;
import com.stark.dpstatus.R;
import com.stark.dpstatus.model.ImageStatus;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class ImageStatusViewPagerAdapter extends RecyclerView.Adapter<ImageStatusViewPagerAdapter.ViewHolder> {

    public static final String IMAGE_STATUS_ORIGIN = "IMAGE_STATUS_ORIGIN";
    private static final int STORAGE_PERMISSION_CODE = 1;
    private static final String TAG = "ImageStatusViewPager";

    private ArrayList<ImageStatus> imageStatuses;
    private Context mContext;

    public ImageStatusViewPagerAdapter(ArrayList<ImageStatus> imageStatuses, Context mContext) {
        this.imageStatuses = imageStatuses;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_status_horizontal_list_item, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ImageStatus imageStatusTemp = imageStatuses.get(position);

        Glide.with(mContext)
                .load(imageStatusTemp.getImage())
                .into(holder.statusImage)
                .onLoadFailed(mContext.getDrawable(R.drawable.ic_loading_image));

        holder.shareAll.setOnClickListener(v -> {
            Intent sendAll = new Intent();
            sendAll.setAction(Intent.ACTION_SEND);
            sendAll.setType("image/*");
            sendAll.putExtra(Intent.EXTRA_STREAM, getLocalBitmapURI(holder.statusImage.getDrawable()));

            mContext.startActivity(sendAll);
        });

        holder.shareWhatsApp.setOnClickListener(v -> {
            Intent sendWhatsApp = new Intent();
            sendWhatsApp.setType("image/*");
            sendWhatsApp.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            setPermission();

            File createFile = null;
            try {
                Bitmap bitmap = ((BitmapDrawable) holder.statusImage.getDrawable()).getBitmap();

                String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + mContext.getString(R.string.app_name) + "/";
                String fileName = "share_image_whatsApp_temp" + System.currentTimeMillis() + ".jpeg";

                File dir = new File(dirPath);

                boolean dirCreated = dir.exists();

                if (!dirCreated) {
                    dirCreated = dir.mkdir();
                }
                if (dirCreated) {
                    createFile = new File(dirPath, fileName);
                    FileOutputStream fileOutputStream = new FileOutputStream(createFile);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                    fileOutputStream.close();

                } else {
                    Toast.makeText(mContext, "Something Went Wrong ...", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {

            }

            sendWhatsApp.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(createFile));
            sendWhatsApp.setPackage("com.whatsapp");

            if (sendWhatsApp.resolveActivity(mContext.getPackageManager()) != null) {
                mContext.startActivity(sendWhatsApp);
            } else
                Toast.makeText(mContext, "Please Install WhatsApp to share Status ", Toast.LENGTH_SHORT).show();
        });

        holder.shareInstagram.setOnClickListener(v -> {

            Intent shareInstagramIntent = new Intent();
            shareInstagramIntent.setPackage("com.instagram.android");
            shareInstagramIntent.setAction(Intent.ACTION_SEND);
            shareInstagramIntent.setType("image/*");
            shareInstagramIntent.putExtra(Intent.EXTRA_STREAM, getLocalBitmapURI(holder.statusImage.getDrawable()));

            if (shareInstagramIntent.resolveActivity(mContext.getPackageManager()) != null) {
                mContext.startActivity(shareInstagramIntent);
            } else
                Toast.makeText(mContext, "Please Install Instagram to share Status", Toast.LENGTH_SHORT).show();
        });

        holder.shareFacebook.setOnClickListener(v -> {

            Intent shareFacebookIntent = new Intent();
            shareFacebookIntent.setPackage("com.facebook.lite");
            shareFacebookIntent.setAction(Intent.ACTION_SEND);
            shareFacebookIntent.setType("image/*");
            shareFacebookIntent.putExtra(Intent.EXTRA_STREAM, getLocalBitmapURI(holder.statusImage.getDrawable()));

            if (shareFacebookIntent.resolveActivity(mContext.getPackageManager()) != null) {
                mContext.startActivity(shareFacebookIntent);
            } else if (shareFacebookIntent.resolveActivity(mContext.getPackageManager()) == null) {
                shareFacebookIntent.setPackage("com.facebook.katana");
                if (shareFacebookIntent.resolveActivity(mContext.getPackageManager()) != null)
                    mContext.startActivity(shareFacebookIntent);
            } else
                Toast.makeText(mContext, "Please Install Facebook to share Status", Toast.LENGTH_SHORT).show();
        });

        holder.shareDownload.setOnClickListener(v -> {

            Glide.with(mContext).load(imageStatusTemp.getImage())
                    .into(new CustomTarget<Drawable>() {

                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            downloadImageFile(resource);

                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }

                        @Override
                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                            super.onLoadFailed(errorDrawable);
                            Toast.makeText(mContext, "Failed To Load Image Try again After Some Time ", Toast.LENGTH_SHORT).show();
                        }
                    });

        });

    }

    private void downloadImageFile(Drawable resource) {
        setPermission();
        Bitmap bitmap = ((BitmapDrawable) resource).getBitmap();
        Toast.makeText(mContext, "Saving...", Toast.LENGTH_SHORT).show();

        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + mContext.getString(R.string.app_name) + "/";
        String fileName = mContext.getString(R.string.app_name) + "_" + System.currentTimeMillis() + ".jpeg";
        String savedFilePath;

        File dir = new File(dirPath);
        boolean dirCreated = dir.exists();

        if (!dirCreated) {
            dirCreated = dir.mkdir();
        }
        if (dirCreated) {
            File createFile = new File(dirPath, fileName);
            savedFilePath = createFile.getAbsolutePath();

            try {
                FileOutputStream fOut = new FileOutputStream(createFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                fOut.close();
                Toast.makeText(mContext, "Status Saved to : " + savedFilePath, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "Failed to make Folder ", Toast.LENGTH_SHORT).show();
        }
    }


    private Uri getLocalBitmapURI(Drawable drawable) {
        setPermission();
        Uri bmpUri = null;
        try {
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            File file = new File(mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_temp" + System.currentTimeMillis() + ".png");

            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.close();
            bmpUri = FileProvider.getUriForFile(mContext, BuildConfig.APPLICATION_ID + ".provider", file);

        } catch (Exception e) {

        }

        return bmpUri;
    }


    private void setPermission() {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestReadPermission();
        }
    }


    private void requestReadPermission() {
        ActivityCompat.requestPermissions((Activity) mContext,
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }
                , STORAGE_PERMISSION_CODE);
    }


    @Override
    public int getItemCount() {
        return imageStatuses.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView shareAll, shareFacebook, shareWhatsApp, shareInstagram, shareDownload;
        private ImageView statusImage;
        private MaterialCardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shareAll = itemView.findViewById(R.id.hrImgShare);
            shareFacebook = itemView.findViewById(R.id.hrImgShareFacebook);
            shareWhatsApp = itemView.findViewById(R.id.hrImgShareWhatsApp);
            shareInstagram = itemView.findViewById(R.id.hrImgShareInstagrame);
            shareDownload = itemView.findViewById(R.id.hrImgDownload);
            statusImage = itemView.findViewById(R.id.hrImgStatus);

        }
    }
}
