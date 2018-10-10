package com.example.purva.vaccineupdate1.Fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.purva.vaccineupdate1.Adapter.recyclerAdapter;
import com.example.purva.vaccineupdate1.Model.VaccineTimeTable;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class FragmentPending extends Fragment {

    List<VaccineTimeTable> listData;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;

    public static FragmentPending newInstance() {
        FragmentPending fragment = new FragmentPending();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listData = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        getFirebaseDatabase();
    }

    public void getFirebaseDatabase(){

        databaseReference = firebaseDatabase.getReference("users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot vaccineSnapshot = dataSnapshot.child(FirebaseAuth.getInstance().getUid()).child("vaccineList");
                for (DataSnapshot ds : vaccineSnapshot.getChildren()) {
                    VaccineTimeTable info = new VaccineTimeTable();
                    if (ds.getValue(VaccineTimeTable.class).isFlag() == false) {

                        info.setFlag(ds.getValue(VaccineTimeTable.class).isFlag());
                        info.setAfter(ds.getValue(VaccineTimeTable.class).getAfter());
                        info.setCost(ds.getValue(VaccineTimeTable.class).getCost());
                        info.setVac_name(ds.getValue(VaccineTimeTable.class).getVac_name());
                        listData.add(info);
                    }
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
            }

        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        RecyclerView recyclerView = new RecyclerView(getContext());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new recyclerAdapter(getContext(), listData ));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL));

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading Data...");
        progressDialog.show();

        return recyclerView;
    }

}

