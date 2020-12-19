package com.stark.dpstatus.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stark.dpstatus.R;
import com.stark.dpstatus.adapter.TextStatusCategoryRecyclerViewAdapter;
import com.stark.dpstatus.adapter.TextStatusRecyclerViewAdapter;
import com.stark.dpstatus.model.TextStatus;

import java.util.ArrayList;
import java.util.List;

public class ShowTextStatusStatusByCategoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<TextStatus> textStatuses = new ArrayList<>();
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_text_status_by_category);
        recyclerView = findViewById(R.id.textCategoryRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String categoryName = getIntent().getStringExtra(TextStatusCategoryRecyclerViewAdapter.TEXT_CATEGORY_NAME);
        String categoryId = getIntent().getStringExtra(TextStatusCategoryRecyclerViewAdapter.TEXT_CATEGORY_ID);

        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle(categoryName);
        supportActionBar.setHomeAsUpIndicator(R.drawable.ic_back_arrow);
        supportActionBar.setDisplayHomeAsUpEnabled(true);


        Toast.makeText(getApplicationContext(), categoryName, Toast.LENGTH_LONG).show();

        databaseReference = FirebaseDatabase.getInstance().getReference().child(TextStatus.FIREBASE_TEXT_STATUS_PATH);
        databaseReference.orderByChild("categoryId").equalTo(Integer.parseInt(categoryId.trim())).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    textStatuses.add(snapshot.getValue(TextStatus.class));
                }
                recyclerView.setAdapter(new TextStatusRecyclerViewAdapter((ArrayList<TextStatus>) textStatuses, ShowTextStatusStatusByCategoryActivity.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}