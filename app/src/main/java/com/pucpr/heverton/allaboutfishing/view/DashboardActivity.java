package com.pucpr.heverton.allaboutfishing.view;

import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import com.pucpr.heverton.allaboutfishing.R;
import com.pucpr.heverton.allaboutfishing.model.Weathers;
import com.pucpr.heverton.allaboutfishing.remote.ApiUtils;
import com.pucpr.heverton.allaboutfishing.remote.GpsTracker;
import com.pucpr.heverton.allaboutfishing.remote.PostService;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView tv_city, tv_temperature, tv_desc_weather, tv_date_today, tv,tv2;
    ImageView iv;

    private PostService mService;
    private GpsTracker gpsTracker;

    String x,y,z;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("All About Fishing");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Toast.makeText(DashboardActivity.this, "COMING SOON!",
                Toast.LENGTH_SHORT).show());

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mService = ApiUtils.getPostService();

        View hView = navigationView.inflateHeaderView(R.layout.nav_header_dashboard);
        tv = hView.findViewById(R.id.tvNameUserDash);
        tv2 = hView.findViewById(R.id.tvMailUserDash);

        SharedPreferences sharedPreferences = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        x = sharedPreferences.getString("user_name","");
        y = sharedPreferences.getString("user_email","");
        z = sharedPreferences.getString("user_image","");
        tv.setText(x);
        tv2.setText(y);

        iv = hView.findViewById(R.id.ivPerfilDash);

        if (z.isEmpty()){
            z="PATH IMAGE";
        }
        Picasso.get()
                .load(z).resize(100, 100).into(iv);

        iv.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, EditProfileActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.cvPlaces).setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, PlacesActivity.class);
            startActivity(intent);
        });

        tv_city = findViewById(R.id.tvCity);
        tv_temperature = findViewById(R.id.tvTemperature);
        tv_desc_weather = findViewById(R.id.tvDescWeather);
        tv_date_today = findViewById(R.id.tvDateDash);

        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        gpsTracker = new GpsTracker(this);
        if(gpsTracker.canGetLocation()){
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
            hereLocation(latitude,longitude);
        }else{
            gpsTracker.showSettingsAlert();
            Log.e("DASHBOARD_ACTIVITY", "ERROR GPS TRACKER");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        x = sharedPreferences.getString("user_name","");
        z = sharedPreferences.getString("user_image","");

        tv.setText(x);

        Picasso.get()
                .load(z).resize(100, 100).into(iv);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_places) {
            Intent intent = new Intent(DashboardActivity.this, PlacesActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_map) {

        } else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_logout) {

            Intent intent = new Intent(DashboardActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void returnWeather(String city, String state) {

        mService.getWeather(city,state).enqueue(new Callback<Weathers>() {
            @Override
            public void onResponse(@NonNull Call<Weathers> call, @NonNull Response<Weathers> response) {
                if(response.isSuccessful()) {
                    if(response.body().getResponse().equals("success")) {

                        tv_city.setText(response.body().getCity());
                        tv_desc_weather.setText(response.body().getCondition());
                        tv_temperature.setText(response.body().getTemperature().toString()+"°");

                    }else{

                        Log.e("DASHBOARD_ACTIVITY", "FAILED API WEATHER");

                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Weathers> call, @NonNull Throwable t) {
                Log.e("DASHBOARD_ACTIVITY", "Unable to submit post to API."+t);
            }
        });
    }

    public void hereLocation(double lat, double lon){

        Geocoder geocoder = new Geocoder(DashboardActivity.this, Locale.getDefault());
        List<Address> addressList;

        try{

            addressList = geocoder.getFromLocation(lat,lon,1);

            if(addressList != null && addressList.size() > 0){

                returnWeather(addressList.get(0).getSubAdminArea(),addressList.get(0).getAdminArea());

            }else{
                Toast.makeText(DashboardActivity.this,"ERROR LOCATION", Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
