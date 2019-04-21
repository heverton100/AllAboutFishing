package com.pucpr.heverton.allaboutfishing;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText emailForgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Esqueci a Senha");

        emailForgot = findViewById(R.id.txtMailLogin);
        findViewById(R.id.btnTrocarSenha).setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        switch (id){
            case android.R.id.home:
                Intent intent1 = new Intent(this,LoginActivity.class);
                startActivityForResult(intent1,1);
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btnTrocarSenha) {
            redefinirSenha();
        }
    }

    public void redefinirSenha() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress = emailForgot.getText().toString();

        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgotPassActivity.this, "Email Enviado!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        Intent intent1 = new Intent(this,LoginActivity.class);
        startActivityForResult(intent1,1);
        finish();
        super.onBackPressed();
    }
}
