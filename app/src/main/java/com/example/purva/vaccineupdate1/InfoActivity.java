package com.example.purva.vaccineupdate1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.purva.vaccineupdate1.Adapter.infoAdapter;
import com.example.purva.vaccineupdate1.Model.PointsToRemember;

public class InfoActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        setUpToolbar();
        setUpRecyclerView();
    }

    private void setUpToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Points to Remember");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        this.finish();
        return super.onOptionsItemSelected(item);
    }

    private void setUpRecyclerView()
    {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.pointsRecyclerView);
        infoAdapter adapter = new infoAdapter(this, PointsToRemember.getdata());
        recyclerView.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

}


