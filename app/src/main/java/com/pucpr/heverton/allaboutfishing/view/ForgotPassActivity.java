package com.pucpr.heverton.allaboutfishing.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.pucpr.heverton.allaboutfishing.R;
import com.pucpr.heverton.allaboutfishing.model.Users;
import com.pucpr.heverton.allaboutfishing.remote.ApiUtils;
import com.pucpr.heverton.allaboutfishing.remote.PostService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText emailForgot;

    private PostService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_forgot_pass);

        emailForgot = findViewById(R.id.txtMailForgot);

        findViewById(R.id.btnSendEmailForgotPass).setOnClickListener(this);
        findViewById(R.id.btnBackLoginForgot).setOnClickListener(this);
        findViewById(R.id.btnBackSignupForgot).setOnClickListener(this);

        mService = ApiUtils.getPostService();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == android.R.id.home) {
            Intent intent1 = new Intent(this, LoginActivity.class);
            startActivityForResult(intent1, 1);
            finish();
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btnSendEmailForgotPass) {
            resetPass(emailForgot.getText().toString());
        }else if(i == R.id.btnBackSignupForgot){
            Intent intent1 = new Intent(this, RegisterActivity.class);
            startActivityForResult(intent1,1);
            finish();
        }else if(i == R.id.btnBackLoginForgot){
            Intent intent1 = new Intent(this, LoginActivity.class);
            startActivityForResult(intent1,1);
            finish();
        }
    }

    public void resetPass(String email) {

        if (!validateForm()) {
            return;
        }

        if(!TextUtils.isEmpty(email)) {
            mService.reset_pass(email).enqueue(new Callback<Users>() {
                @Override
                public void onResponse(@NonNull Call<Users> call, @NonNull Response<Users> response) {
                    if(response.isSuccessful()) {
                        if(response.body().getResponse().equals("failed")) {

                            Toast.makeText(ForgotPassActivity.this,"Email not found!",Toast.LENGTH_LONG).show();

                        }else if(response.body().getResponse().equals("success")){

                            Intent intent = new Intent(ForgotPassActivity.this, LoginActivity.class);
                            intent.putExtra("CHECK",1);
                            startActivity(intent);

                        }
                    }else{
                        int statusCode = response.code();
                        Log.e("FORGOT_PASS_ACTIVITY", "Call REST return: "+statusCode);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Users> call, @NonNull Throwable t) {
                    Log.e("FORGOT_PASS_ACTIVITY", "Unable to submit post to API."+t);
                }
            });
        }

    }



    private boolean validateForm() {
        boolean valid = true;

        String email = emailForgot.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailForgot.setError("Required.");
            valid = false;
        } else {
            emailForgot.setError(null);
        }

        return valid;
    }

    @Override
    public void onBackPressed() {
        Intent intent1 = new Intent(this,LoginActivity.class);
        startActivityForResult(intent1,1);
        finish();
        super.onBackPressed();
    }
}
