package com.stark.dpstatus.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stark.dpstatus.R;
import com.stark.dpstatus.adapter.ImageStatusCategoryAdapter;
import com.stark.dpstatus.model.ImageStatusCategory;

import java.util.ArrayList;
import java.util.List;

public class ImageStatusCategoryFrag extends Fragment {


    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private List<ImageStatusCategory> imageStatusCategories;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_image_status_category, container, false);
        progressBar = view.findViewById(R.id.imageCategoryProgressBar);
        imageStatusCategories = new ArrayList<>();
        recyclerView = view.findViewById(R.id.categoryRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        databaseReference = FirebaseDatabase.getInstance().getReference(ImageStatusCategory.FIREBASE_IMAGE_STATUS_CATEGORY_PATH);
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                imageStatusCategories.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ImageStatusCategory tempImageStatusCategory = snapshot.getValue(ImageStatusCategory.class);
                    tempImageStatusCategory.setId(Integer.parseInt(snapshot.getKey()));
                    imageStatusCategories.add(tempImageStatusCategory);
                }
                recyclerView.setAdapter(new ImageStatusCategoryAdapter(imageStatusCategories, getActivity()));
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }
}