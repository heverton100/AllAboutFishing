package com.pucpr.heverton.allaboutfishing.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import com.pucpr.heverton.allaboutfishing.R;
import com.pucpr.heverton.allaboutfishing.model.Users;
import com.pucpr.heverton.allaboutfishing.remote.ApiUtils;
import com.pucpr.heverton.allaboutfishing.remote.PostService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mResponse;
    private EditText mEmailField;
    private EditText mPasswordField;
    private PostService mService;

    Integer check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        mEmailField = findViewById(R.id.txtEmail);
        mPasswordField = findViewById(R.id.txtPass);
        mResponse = findViewById(R.id.textviewResponse);

        findViewById(R.id.btnSignin).setOnClickListener(this);
        findViewById(R.id.btnBackSignup).setOnClickListener(this);

        mService = ApiUtils.getPostService();

        Intent i = this.getIntent();
        check = i.getIntExtra("CHECK",0);

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (check == 1){
            mResponse.setVisibility(View.VISIBLE);
        }

    }

    private void signIn(String email, String pass) {
        if (!validateForm()) {
            return;
        }

        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)) {
            mService.login(email,pass).enqueue(new Callback<Users>() {
                @Override
                public void onResponse(@NonNull Call<Users> call, @NonNull Response<Users> response) {
                    if(response.isSuccessful()) {
                        switch (response.body().getResponse()) {
                            case "failed":

                                Toast.makeText(LoginActivity.this, "Incorrect Password!", Toast.LENGTH_LONG).show();

                                break;
                            case "email not found":

                                Toast.makeText(LoginActivity.this, "Email Not Found or Not Verified!", Toast.LENGTH_LONG).show();

                                break;
                            case "success":

                                SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("user_name", response.body().getName());
                                editor.putString("user_email", response.body().getEmail());
                                editor.putInt("user_id", response.body().getId());
                                editor.putString("user_phone", response.body().getPhone());
                                editor.putString("user_image", response.body().getUrl_image());
                                editor.commit();

                                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                                startActivityForResult(intent, 1);

                                break;
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Users> call, @NonNull Throwable t) {
                    Log.e("LOGIN_ACTIVITY", "Unable to submit post to API."+t);
                }
            });
        }

    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        return valid;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btnSignin) {
            signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
        }else if (i == R.id.btnBackSignup) {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivityForResult(intent,1);
            finish();
        }
    }

    public void btnGoForgot(View view){
        Intent intent = new Intent(this, ForgotPassActivity.class);
        startActivityForResult(intent,1);
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        finishAffinity();
        super.onBackPressed();
    }
}
