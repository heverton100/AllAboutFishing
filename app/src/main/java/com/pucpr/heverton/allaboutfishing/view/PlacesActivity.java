package com.pucpr.heverton.allaboutfishing.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.pucpr.heverton.allaboutfishing.R;
import com.pucpr.heverton.allaboutfishing.adapter.PlacesAdapter;
import com.pucpr.heverton.allaboutfishing.model.Places;
import com.pucpr.heverton.allaboutfishing.remote.ApiUtils;
import com.pucpr.heverton.allaboutfishing.remote.PostService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlacesActivity extends AppCompatActivity {

    private PlacesAdapter mAdapter;
    private PostService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        getSupportActionBar().setTitle(R.string.title_places);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mService = ApiUtils.getPostService();

        RecyclerView recyclerView = findViewById(R.id.rvPlaces);
        mAdapter = new PlacesAdapter(this, new ArrayList<Places>(0));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(mAdapter);
        recyclerView.setHasFixedSize(true);

        returnPlaces();

    }

    private void returnPlaces() {

        Call<List<Places>> call = mService.getPlaces();

        call.enqueue(new Callback<List<Places>>() {
            @Override
            public void onResponse(@NonNull Call<List<Places>> call, @NonNull Response<List<Places>> response) {

                if(response.isSuccessful()){
                    mAdapter.updatePlaces(response.body());
                    Log.d("MainActivity", "Success.");
                }else {
                    int statusCode = response.code();
                    Log.d("MainActivity", "Call REST return: "+statusCode);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Places>> call, @NonNull Throwable t) {
                Log.d("MainActivity", "Error in call REST"+t);
            }
        });

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }
}