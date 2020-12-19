package com.stark.dpstatus.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stark.dpstatus.R;
import com.stark.dpstatus.Util.NotificationHelper;
import com.stark.dpstatus.adapter.ImageStatusRecyclerViewAdapter;
import com.stark.dpstatus.adapter.ImageStatusViewPagerAdapter;
import com.stark.dpstatus.adapter.TextStatusRecyclerViewAdapter;
import com.stark.dpstatus.adapter.TextStatusViewPagerAdapter;
import com.stark.dpstatus.model.ImageStatus;
import com.stark.dpstatus.model.TextStatus;

import java.util.ArrayList;
import java.util.List;

public class StatusActivity extends AppCompatActivity {

    public static final String INTENT_ORIGIN = "INTENT_ORIGIN";
    private ViewPager2 viewPager2;
    private List<TextStatus> textStatuses;
    private String intentOrigin = "itSelf";
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        //  getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN );
        //  requestWindowFeature(Window.FEATURE_NO_TITLE);

        Toolbar toolbar = findViewById(R.id.activity_status_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        progressBar = findViewById(R.id.activityStatusTextStatusProgressBar);

        viewPager2 = findViewById(R.id.statusViewPager);
        viewPager2.setClipToPadding(false);


        switch (getIntent().getStringExtra(INTENT_ORIGIN) == null ? intentOrigin : getIntent().getStringExtra(INTENT_ORIGIN)) {

            case TextStatusViewPagerAdapter.TEXT_STATUS_ORIGIN:
                int statusPosition = 0;
                statusPosition = getIntent().getIntExtra(TextStatusRecyclerViewAdapter.TEXT_STATUS_POSITION, 0);
                textStatuses = getIntent().getParcelableArrayListExtra(TextStatusRecyclerViewAdapter.TEXT_STATUS_PARCELABLE);
                progressBar.setVisibility(View.GONE);
                viewPager2.setAdapter(new TextStatusViewPagerAdapter(textStatuses, StatusActivity.this));
                viewPager2.setCurrentItem(statusPosition);
                break;

            case ImageStatusViewPagerAdapter.IMAGE_STATUS_ORIGIN:
                int imageStatusPosition = 0;
                imageStatusPosition = getIntent().getIntExtra(ImageStatusRecyclerViewAdapter.IMAGE_STATUS_POSITION, 0);
                ArrayList<ImageStatus> imageStatuses = getIntent().getParcelableArrayListExtra(ImageStatusRecyclerViewAdapter.IMAGE_STATUS_PARCELABLE);
                progressBar.setVisibility(View.GONE);
                viewPager2.setAdapter(new ImageStatusViewPagerAdapter(imageStatuses, StatusActivity.this));
                viewPager2.setCurrentItem(imageStatusPosition);
                break;

            case NotificationHelper.TEXT_NOTIFICATION_ORIGIN:

                List<TextStatus> statusFragTextStatuses = new ArrayList<>();
                String textStatus = getIntent().getStringExtra(NotificationHelper.NOTIFICATION_MESSAGE);

                TextStatus textStatusTemp = new TextStatus();
                textStatusTemp.setTextStatus(textStatus == null ? "  Something Went wrong  " : textStatus);

                DatabaseReference firebaseDatabaseReference;
                firebaseDatabaseReference = FirebaseDatabase.getInstance().getReference(TextStatus.FIREBASE_TEXT_STATUS_PATH);
                firebaseDatabaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        statusFragTextStatuses.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            statusFragTextStatuses.add(snapshot.getValue(TextStatus.class));
                        }

                        progressBar.setVisibility(View.GONE);
                        int notificationStatusPosition = statusFragTextStatuses.size() <= 0 ? 0 : statusFragTextStatuses.size() / 2;
                        statusFragTextStatuses.add(notificationStatusPosition, textStatusTemp);

                        viewPager2.setAdapter(new TextStatusViewPagerAdapter(statusFragTextStatuses, StatusActivity.this));
                        viewPager2.setCurrentItem(notificationStatusPosition);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;

            case NotificationHelper.IMAGE_NOTIFICATION_ORIGIN:
                ArrayList<ImageStatus> notificationImageStatuses = new ArrayList<>();
                String imageUrl = getIntent().getStringExtra(NotificationHelper.NOTIFICATION_IMAGE_URL);

                DatabaseReference databaseReference;
                databaseReference = FirebaseDatabase.getInstance().getReference(ImageStatus.FIREBASE_IMAGE_STATUS_PATH);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        notificationImageStatuses.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            ImageStatus tempImageStatus = snapshot.getValue(ImageStatus.class);
                            tempImageStatus.setId(Integer.parseInt(snapshot.getKey()));
                            notificationImageStatuses.add(tempImageStatus);

                        }
                        progressBar.setVisibility(View.GONE);
                        int imageNotificationStatusPosition = notificationImageStatuses.size() <= 0 ? 0 : notificationImageStatuses.size() / 2;
                        ImageStatus tempNotificationImageStatus = new ImageStatus();
                        tempNotificationImageStatus.setImage(imageUrl);
                        notificationImageStatuses.add(imageNotificationStatusPosition, tempNotificationImageStatus);
                        viewPager2.setAdapter(new ImageStatusViewPagerAdapter(notificationImageStatuses, StatusActivity.this));
                        viewPager2.setCurrentItem(imageNotificationStatusPosition);
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            default:
                break;
        }

    }


    public void statusBackBtn(View view) {
        onBackPressed();
    }
}