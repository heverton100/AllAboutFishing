package com.pucpr.heverton.allaboutfishing.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.pucpr.heverton.allaboutfishing.R;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEmailField;
    private EditText mPasswordField;
    private EditText mNameField;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_registro);




        mEmailField = findViewById(R.id.txtMailRegistro);
        mPasswordField = findViewById(R.id.txtSenhaRegistro);
        mNameField = findViewById(R.id.txtNomeRegistro);

        findViewById(R.id.btnCadastrar).setOnClickListener(this);
        findViewById(R.id.btnVoltarLogin).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }


    private boolean validateForm() {
        boolean valid = true;

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


    private void createAccount(String email, String password) {
        if (!validateForm()) {
            return;
        }

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(user!= null)
                            {
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(mNameField.getText().toString())
                                        .build();

                                user.updateProfile(profileUpdates)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    //Log.d("TESTING", "User profile updated.");
                                                }
                                            }
                                        });
                            }
                            Toast.makeText(RegistroActivity.this, "Você foi Cadastrado com sucesso!",
                                    Toast.LENGTH_SHORT).show();
                            backLogin();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegistroActivity.this, "Falha no Cadastro",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        // [END create_user_with_email]
    }



    public void backLogin(){

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }



    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btnCadastrar) {
            createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());

        }else if(i == R.id.btnVoltarLogin){
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
