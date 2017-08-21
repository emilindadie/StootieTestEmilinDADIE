package com.example.emili.stootietestemilindadie;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.emili.stootietestemilindadie.donnee.Stoot;
import com.example.emili.stootietestemilindadie.donnee.StootAdapter;


public class MainActivity extends AppCompatActivity {

    List<Stoot> stootList;
    RecyclerView recyclerView;
    private ProgressBar progressBar;
    RecyclerView.LayoutManager layoutManager;


    private static final String URL_STOOT = "https://bff-mobile-dev.stootie.com/stoots.json?lat=48.8694023&lng=2.3522692&radius=50&stoot_type[]=miss%20ion&page=1&per_page=50";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(MainActivity.this, new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 123);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        stootList = new ArrayList<Stoot>();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        makeStootRequest();
    }

    private void makeStootRequest(){
        Toast.makeText(this, "chargement...", Toast.LENGTH_LONG).show();
        ExtractStoot.extractData(this, stootList, recyclerView, progressBar, URL_STOOT);

    }

    //Action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_update:

                makeStootRequest();
                Toast.makeText(this, "chargement terminé", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
