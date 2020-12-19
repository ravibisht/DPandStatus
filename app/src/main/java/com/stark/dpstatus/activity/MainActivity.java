package com.stark.dpstatus.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stark.dpstatus.R;
import com.stark.dpstatus.Util.NotificationHelper;
import com.stark.dpstatus.adapter.TabLayoutViewPagerAdapter;
import com.stark.dpstatus.fragment.ImageStatusCategoryFrag;
import com.stark.dpstatus.fragment.ImageStatusLatestFrag;
import com.stark.dpstatus.fragment.ImageStatusTrendingFrag;
import com.stark.dpstatus.fragment.TextStatusCategoryFrag;
import com.stark.dpstatus.fragment.TextStatusFrag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabLayoutViewPagerAdapter tabLayoutViewPagerAdapter;
    private List<Fragment> allFragment;
    private List<String> titles;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle(getString(R.string.app_name));
        getSupportActionBar().setElevation(0);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        notificationIntentHandler();

        initItems();


    }


    private void initItems() {
        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewPager);
        allFragment = new ArrayList<>();
        titles = new ArrayList<>();

        initData();

        tabLayoutViewPagerAdapter = new TabLayoutViewPagerAdapter(getSupportFragmentManager(), allFragment, titles);
        viewPager.setAdapter(tabLayoutViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        fetchTitleFromFirebase();

    }

    private void initData() {

        allFragment = Arrays.asList(new TextStatusFrag(),
                new ImageStatusLatestFrag(),
                new ImageStatusTrendingFrag(),
                new ImageStatusCategoryFrag(),
                new TextStatusCategoryFrag());

        titles = getTitlesFromSharePreferences();

    }


    private boolean fetchTitleFromFirebase() {

        databaseReference = FirebaseDatabase.getInstance().getReference("AppConfigTitle");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> tempTitles = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    tempTitles.add(snapshot.child("title").getValue(String.class));
                }
                titles = tempTitles;
                tabLayoutViewPagerAdapter.setTitles(tempTitles);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return true;
    }

    private void notificationIntentHandler() {

        Intent incomingIntent = getIntent();
        Bundle extras = incomingIntent.getExtras();

        if (extras == null) {
            return;
        }
        for (String key : extras.keySet()) {
            Log.i(TAG, key + " : " + incomingIntent.getExtras().get(key));
        }

        String title = extras.getString("title");
        String message = extras.getString("body");

        Intent outGoing = new Intent(MainActivity.this, StatusActivity.class);

        if (extras.getString("image_url") != null) {

            outGoing.putExtra(StatusActivity.INTENT_ORIGIN, NotificationHelper.IMAGE_NOTIFICATION_ORIGIN);
            outGoing.putExtra(NotificationHelper.NOTIFICATION_IMAGE_URL, extras.getString("image_url"));
            getIntent().removeExtra("image_url");
            startActivity(outGoing);

        } else if (message != null && message.length() > 0) {

            getIntent().removeExtra("body");
            outGoing.putExtra(StatusActivity.INTENT_ORIGIN, NotificationHelper.TEXT_NOTIFICATION_ORIGIN);
            outGoing.putExtra(NotificationHelper.NOTIFICATION_MESSAGE, message);
            outGoing.putExtra(NotificationHelper.NOTIFICATION_TITLE, "");
            startActivity(outGoing);

        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        saveTitleToSharePreferences();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveTitleToSharePreferences();
    }


    private void saveTitleToSharePreferences() {

        SharedPreferences.Editor sharedPreferences = getSharedPreferences("AppConfigTitles", MODE_PRIVATE).edit();
        sharedPreferences.clear();
        sharedPreferences.putInt("titles_size", titles.size());

        int i = 1;

        for (String key : titles) {
            sharedPreferences.putString("title_" + i, key);
            i++;
        }

        sharedPreferences.apply();
        sharedPreferences.commit();
    }


    private ArrayList<String> getTitlesFromSharePreferences() {

        SharedPreferences sharedPreferences = getSharedPreferences("AppConfigTitles", MODE_PRIVATE);

        int size = sharedPreferences.getInt("titles_size", 5);

        ArrayList<String> tempArrayList = new ArrayList<>();

        tempArrayList.add(sharedPreferences.getString("title_1", "loading"));
        tempArrayList.add(sharedPreferences.getString("title_2", "loading"));
        tempArrayList.add(sharedPreferences.getString("title_3", "loading"));
        tempArrayList.add(sharedPreferences.getString("title_4", "loading"));
        tempArrayList.add(sharedPreferences.getString("title_5", "loading"));

        return tempArrayList;
    }

}

