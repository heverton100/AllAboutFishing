package com.pucpr.heverton.allaboutfishing.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.text.TextUtilsCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pucpr.heverton.allaboutfishing.CustomVolleyRequest;
import com.pucpr.heverton.allaboutfishing.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int MY_PERMISSION_REQUEST_LOCATION = 1;
    TextView tvCidade;
    TextView tvTemperatura;
    TextView tvDescricaoClima;
    TextView tvDataHoje;

    RequestQueue rq;

    String request_url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("All About Fishing");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DashboardActivity.this, "Em desenvolvimento!",
                        Toast.LENGTH_SHORT).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        View hView =  navigationView.inflateHeaderView(R.layout.nav_header_dashboard);
        TextView tv = hView.findViewById(R.id.tvNameUserDash);
        TextView tv2 = hView.findViewById(R.id.tvMailUserDash);

        tv.setText(user.getDisplayName());
        tv2.setText(user.getEmail());
        //ImageView iv = hView.findViewById(R.id.ivUser);





        tvCidade = findViewById(R.id.textViewCidade);
        tvTemperatura = findViewById(R.id.tvTemperatura);
        tvDescricaoClima = findViewById(R.id.tvDescricaoClima);
        tvDataHoje = findViewById(R.id.tvDataDash);





        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        try{
            hereLocation(location.getLatitude(), location.getLongitude());
        } catch (Exception e){
            e.printStackTrace();
            Toast.makeText(DashboardActivity.this,"ERRO", Toast.LENGTH_SHORT).show();
        }





        //Log.d("Tag", "signInWithCredential:success" + request_url);
        rq = CustomVolleyRequest.getInstance(this).getRequestQueue();
        sendRequest();


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
            FirebaseAuth.getInstance().signOut();
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

        if (id == R.id.nav_locais) {
            // Handle the camera action
        } else if (id == R.id.nav_dicas) {

        } else if (id == R.id.nav_map) {

        } else if (id == R.id.nav_sobre) {

        } else if (id == R.id.nav_sair) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(DashboardActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode){
            case MY_PERMISSION_REQUEST_LOCATION:{

                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(ContextCompat.checkSelfPermission(DashboardActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){

                        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                        try{
                            hereLocation(location.getLatitude(), location.getLongitude());
                        } catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(DashboardActivity.this,"ERRO", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(DashboardActivity.this, "No permission granted!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }






    public String hereLocation(double lat, double lon){
        String curCity = "";

        Geocoder geocoder = new Geocoder(DashboardActivity.this, Locale.getDefault());
        List<Address> addressList;
        try{

            addressList = geocoder.getFromLocation(lat,lon,1);
            if(addressList != null && addressList.size() > 0){

                curCity = addressList.get(0).getAdminArea()+"-"+addressList.get(0).getSubAdminArea();
                //Log.d("Tag", "signInWithCredential:success" + curCity);
                request_url = "https://allaboutfishing.azurewebsites.net/apitempo.php?vCity="+addressList.get(0).getSubAdminArea()+"&vState="+addressList.get(0).getAdminArea();
            }else{
                Toast.makeText(DashboardActivity.this,"ERRO", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return curCity;

    }






    public void sendRequest() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, request_url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String cidade = response.getString("name");
                    String estado = response.getString("state");
                    String country = response.getString("country");
                    String id = response.getString("id");

                    JSONObject news = response.getJSONObject("data");
                    String temperature = news.getString("temperature");
                    String humidity = news.getString("humidity");
                    String condition = news.getString("condition");

                    tvCidade.setText(cidade+"-"+estado);
                    tvTemperatura.setText(temperature+"º");
                    tvDescricaoClima.setText(condition);


                    DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm",Locale.getDefault());
                    String date = df.format(Calendar.getInstance().getTime());

                    tvDataHoje.setText(date);




                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        CustomVolleyRequest.getInstance(this).addToRequestQueue(jsonObjectRequest);

    }


}
