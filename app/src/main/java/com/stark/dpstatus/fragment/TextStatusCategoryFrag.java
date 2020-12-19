package com.stark.dpstatus.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stark.dpstatus.R;
import com.stark.dpstatus.adapter.TextStatusCategoryRecyclerViewAdapter;
import com.stark.dpstatus.model.TextStatusCategory;

import java.util.ArrayList;
import java.util.List;


public class TextStatusCategoryFrag extends Fragment {
    public static final String FIREBASE_TEXT_STATUS_CATEGORY_PATH = "textCategories";

    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_text_status_category, container, false);

        progressBar = view.findViewById(R.id.textStatusCategoryProgressBar);
        recyclerView = view.findViewById(R.id.statusCategoryRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        FirebaseDatabase reference = FirebaseDatabase.getInstance();
        databaseReference = reference.getReference(FIREBASE_TEXT_STATUS_CATEGORY_PATH);
        List<TextStatusCategory> textCategories = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                textCategories.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    TextStatusCategory tempTextStatusCategory = snapshot.getValue(TextStatusCategory.class);
                    tempTextStatusCategory.setId(snapshot.getKey());
                    textCategories.add(tempTextStatusCategory);
                }
                recyclerView.setAdapter(new TextStatusCategoryRecyclerViewAdapter(textCategories, getActivity()));
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }
}