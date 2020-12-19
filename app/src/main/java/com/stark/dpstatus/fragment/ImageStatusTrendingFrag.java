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
import com.stark.dpstatus.adapter.ImageStatusRecyclerViewAdapter;
import com.stark.dpstatus.model.ImageStatus;

import java.util.ArrayList;
import java.util.List;

public class ImageStatusTrendingFrag extends Fragment {

    private RecyclerView recyclerView;
    private List<ImageStatus> imageStatuses;
    private DatabaseReference databaseReference;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_status_trending, container, false);

        progressBar = view.findViewById(R.id.imageStatusTrendingProgressBar);
        recyclerView = view.findViewById(R.id.imageTrendingRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        imageStatuses = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference(ImageStatus.FIREBASE_TRENDING_STATUS_PATH);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ImageStatus tempImageStatus = snapshot.getValue(ImageStatus.class);
                    tempImageStatus.setId(Integer.parseInt(snapshot.getKey()));
                    imageStatuses.add(tempImageStatus);

                }
                recyclerView.setAdapter(new ImageStatusRecyclerViewAdapter((ArrayList<ImageStatus>) imageStatuses, getActivity()));
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;

    }
}