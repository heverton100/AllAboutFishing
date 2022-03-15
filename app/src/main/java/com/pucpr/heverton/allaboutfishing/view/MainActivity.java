package com.pucpr.heverton.allaboutfishing.view;

import android.content.Intent;
import android.os.Handler;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.pucpr.heverton.allaboutfishing.R;
import com.pucpr.heverton.allaboutfishing.model.Places;
import com.pucpr.heverton.allaboutfishing.remote.ApiUtils;
import com.pucpr.heverton.allaboutfishing.remote.PostService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private PostService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        mService = ApiUtils.getPostService();
        loadAPI();

        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                mostrarLogin();
            }
        }, 2000);
    }

    private void mostrarLogin() {
        Intent intent = new Intent(MainActivity.this,
                LoginActivity.class);
        startActivityForResult(intent,1);
        finish();
    }

    private void loadAPI() {

        Call<List<Places>> call = mService.getPlaces();

        call.enqueue(new Callback<List<Places>>() {
            @Override
            public void onResponse(@NonNull Call<List<Places>> call, @NonNull Response<List<Places>> response) {

                if(response.isSuccessful()) {
                    Log.d("MainActivity", "SUCCESS");
                }else {
                    int statusCode = response.code();
                    Log.d("MainActivity", "Call REST return: "+statusCode);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Places>> call, @NonNull Throwable t) {
                Log.d("MainActivity", "Error in Call REST");
            }
        });
    }
}
