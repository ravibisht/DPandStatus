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
import com.stark.dpstatus.adapter.TextStatusRecyclerViewAdapter;
import com.stark.dpstatus.model.TextStatus;

import java.util.ArrayList;
import java.util.List;

public class TextStatusFrag extends Fragment {

    public static List<TextStatus> statusFragTextStatuses;
    private DatabaseReference firebaseDatabaseReference;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View item = inflater.inflate(R.layout.fragment_text_status, container, false);
        progressBar = item.findViewById(R.id.textStatusProgressBar);
        recyclerView = item.findViewById(R.id.statusRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        statusFragTextStatuses = new ArrayList<>();

        firebaseDatabaseReference = FirebaseDatabase.getInstance().getReference(TextStatus.FIREBASE_TEXT_STATUS_PATH);
        firebaseDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                statusFragTextStatuses.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    statusFragTextStatuses.add(snapshot.getValue(TextStatus.class));
                }

                recyclerView.setAdapter(new TextStatusRecyclerViewAdapter((ArrayList<TextStatus>) statusFragTextStatuses, getContext()));
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return item;
    }


}