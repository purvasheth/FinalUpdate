package com.example.purva.vaccineupdate1.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.purva.vaccineupdate1.Adapter.recyclerAdapter;
import com.example.purva.vaccineupdate1.Model.VaccineTimeTable;
import com.example.purva.vaccineupdate1.R;

public class FragmentPending extends Fragment {


    public static FragmentPending newInstance() {
        FragmentPending fragment = new FragmentPending();
        return fragment;
    }

    /*
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }
    */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        RecyclerView recyclerView = new RecyclerView(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new recyclerAdapter(getContext(), VaccineTimeTable.getdata()));


        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return recyclerView;
    }
}
