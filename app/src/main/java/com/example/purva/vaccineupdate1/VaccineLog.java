package com.example.purva.vaccineupdate1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.purva.vaccineupdate1.Fragments.FragmentDone;
import com.example.purva.vaccineupdate1.Fragments.FragmentNearBy;
import com.example.purva.vaccineupdate1.Fragments.FragmentPending;


public class VaccineLog extends AppCompatActivity {
    private Toolbar toolbar;
    Menu menu ;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_done:
                    selectedFragment = FragmentDone.newInstance();
                    transaction.replace(R.id.frame_container, selectedFragment);
                    transaction.commit();
                    return true;
                //  break;
                case R.id.navigation_pending:
                    selectedFragment = FragmentPending.newInstance();
                    transaction.replace(R.id.frame_container, selectedFragment);
                    transaction.commit();
                    return true;
                //    break;
                case R.id.navigation_maps:
                    Intent intent = new Intent(VaccineLog.this, MapsActivity.class);
                    startActivity(intent);
                    return false;
                //    selectedFragment = FragmentNearBy.newInstance();
                //     break;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine_log);

        setUpToolbar();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //  by default displaying the second fragment
        menu = navigation.getMenu();
        menu.findItem(R.id.navigation_pending).setChecked(true);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, FragmentPending.newInstance());
        transaction.commit();

       /* if(getIntent().hasExtra("key")){
            menu.findItem(R.id.navigation_done).setChecked(true);
            FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
            transaction1.replace(R.id.frame_container, FragmentPending.newInstance());
            transaction1.commit();
        }*/
    }

    private void setUpToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Vaccine Log");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        this.finish();
        return super.onOptionsItemSelected(item);
    }

}
