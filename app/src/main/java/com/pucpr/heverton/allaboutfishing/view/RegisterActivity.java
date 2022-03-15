package com.pucpr.heverton.allaboutfishing.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEmailField;
    private EditText mPasswordField;
    private EditText mNameField;
    private EditText mPhoneField;

    private PostService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);

        mEmailField = findViewById(R.id.txtEmailRegister);
        mPasswordField = findViewById(R.id.txtPassRegister);
        mNameField = findViewById(R.id.txtNameRegister);
        mPhoneField = findViewById(R.id.txtPhoneRegister);

        findViewById(R.id.btnSignup).setOnClickListener(this);
        findViewById(R.id.btnGoLogin).setOnClickListener(this);

        mService = ApiUtils.getPostService();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private boolean validateForm() {
        boolean valid = true;

        String name = mNameField.getText().toString();
        if (TextUtils.isEmpty(name)) {
            mNameField.setError("Campo Obrigatório!");
            valid = false;
        } else {
            mNameField.setError(null);
        }

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Campo Obrigatório!");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Campo Obrigatório!");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        return valid;
    }

    private void createAccount(String name, String email, String password, String phone) {
        if (!validateForm()) {
            return;
        }
        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            mService.register(name,email,password,phone).enqueue(new Callback<Users>() {
                @Override
                public void onResponse(@NonNull Call<Users> call, @NonNull Response<Users> response) {
                    if(response.isSuccessful()) {
                        if(response.body().getResponse().equals("failed")) {

                            Toast.makeText(RegisterActivity.this,"Email Already Registered!",Toast.LENGTH_LONG).show();

                        }else if(response.body().getResponse().equals("success")){

                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            intent.putExtra("CHECK",1);
                            startActivity(intent);

                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Users> call, @NonNull Throwable t) {
                    Log.e("LOG ERROR", "Unable to submit post to API."+t);
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btnSignup) {
            createAccount(mNameField.getText().toString(),
                    mEmailField.getText().toString(),
                    mPasswordField.getText().toString(),
                    mPhoneField.getText().toString()
            );

        }else if(i == R.id.btnGoLogin){
            Intent intent = new Intent(this,LoginActivity.class);
            startActivityForResult(intent,1);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent1 = new Intent(this,LoginActivity.class);
        startActivityForResult(intent1,1);
        finish();
        super.onBackPressed();
    }
}
