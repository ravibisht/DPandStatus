package com.stark.dpstatus.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stark.dpstatus.R;
import com.stark.dpstatus.adapter.ImageStatusCategoryAdapter;
import com.stark.dpstatus.adapter.ImageStatusRecyclerViewAdapter;
import com.stark.dpstatus.model.ImageStatus;

import java.util.ArrayList;
import java.util.List;

public class ShowImageStatusByCategoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<ImageStatus> imageStatuses;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image_status_by_category);

        recyclerView = findViewById(R.id.imageCategoryStatus);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        imageStatuses = new ArrayList<>();

        Toolbar toolbar = findViewById(R.id.activity_image_status_toolbar);
        TextView textView = findViewById(R.id.activity_image_status_TV);
        textView.setText(getIntent().getStringExtra(ImageStatusCategoryAdapter.IMAGE_STATUS_CATEGORY_NAME));
        ImageView backButton = findViewById(R.id.activity_image_status_back_btn);
        backButton.setOnClickListener(v -> onBackPressed());

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        databaseReference = FirebaseDatabase.getInstance().getReference(ImageStatus.FIREBASE_IMAGE_STATUS_PATH);

        int categoryId = getIntent().getIntExtra(ImageStatusCategoryAdapter.IMAGE_STATUS_CATEGORY_ID, 0);
        String categoryName = getIntent().getStringExtra(ImageStatusCategoryAdapter.IMAGE_STATUS_CATEGORY_NAME);

        Toast.makeText(this, categoryName, Toast.LENGTH_SHORT).show();

        databaseReference.orderByChild("categoryId").equalTo(categoryId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ImageStatus tempImageStatus = snapshot.getValue(ImageStatus.class);
                    tempImageStatus.setId(Integer.parseInt(snapshot.getKey()));
                    imageStatuses.add(tempImageStatus);

                }
                recyclerView.setAdapter(new ImageStatusRecyclerViewAdapter((ArrayList<ImageStatus>) imageStatuses, ShowImageStatusByCategoryActivity.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}