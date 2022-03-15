package com.pucpr.heverton.allaboutfishing.view;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pucpr.heverton.allaboutfishing.R;
import com.pucpr.heverton.allaboutfishing.model.Users;
import com.pucpr.heverton.allaboutfishing.remote.ApiUtils;
import com.pucpr.heverton.allaboutfishing.remote.PostService;
import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    private PostService mService;
    private ActivityResultLauncher<Intent> someActivityResultLauncher;
    private String email_sp, name_sp, phone_sp, image_sp, image_temp;
    private Integer id_sp;
    private ImageView iv;

    private TextView txtName,txtEmail;

    private EditText et_name, et_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getSupportActionBar().setTitle(R.string.title_edit_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mService = ApiUtils.getPostService();

        //checking the permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            finish();
            startActivity(intent);
            return;
        }

        txtName = findViewById(R.id.lblNameEditProfile);
        txtEmail = findViewById(R.id.lblEmailEditProfile);

        et_name = findViewById(R.id.etNameEditProfile);
        et_phone = findViewById(R.id.etPhoneEditProfile);

        iv = findViewById(R.id.imgEditProfile);


        findViewById(R.id.imgEditProfile).setOnClickListener(v -> {
            Intent i2 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            someActivityResultLauncher.launch(i2);
        });

        findViewById(R.id.btnUpdateProfile).setOnClickListener(v -> {

            if (image_temp != null){
                image_sp = image_temp;
            }

            updateProfile(id_sp,et_name.getText().toString(),et_phone.getText().toString(),image_sp);

        });

        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Intent data = result.getData();
                            //the image URI
                            Uri selectedImage = data.getData();

                            //calling the upload file method after choosing the file
                            uploadFile(selectedImage, "My Image", email_sp);
                        }
                    }
                });

        SharedPreferences sharedPreferences = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        email_sp = sharedPreferences.getString("user_email","");
        name_sp = sharedPreferences.getString("user_name","");
        phone_sp = sharedPreferences.getString("user_phone","");
        image_sp = sharedPreferences.getString("user_image","");
        id_sp = sharedPreferences.getInt("user_id",0);

        txtName.setText(name_sp);
        txtEmail.setText(email_sp);
        et_phone.setText(phone_sp);
        et_name.setText(name_sp);

        if (image_sp.isEmpty()){
            image_sp="PATH IMAGE";
        }

        Picasso.get()
                .load(image_sp).resize(100, 100).into(iv);
    }

    private void uploadFile(Uri fileUri, String desc, String email) {

        //creating a file
        File file = new File(getRealPathFromURI(fileUri));

        //creating request body for file
        RequestBody requestFile = RequestBody.create(MediaType.parse(getContentResolver().getType(fileUri)), file);
        RequestBody descBody = RequestBody.create(MediaType.parse("text/plain"), desc);
        RequestBody emailBody = RequestBody.create(MediaType.parse("text/plain"), email);

        //creating a call and calling the upload image method
        Call<Users> call = mService.uploadImage(requestFile, descBody, emailBody);

        //finally performing the call
        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(@NonNull Call<Users> call, @NonNull Response<Users> response) {
                if (response.isSuccessful()) {
                    image_temp = response.body().getUrl_image();
                    Picasso.get()
                            .load(response.body().getUrl_image()).resize(100, 100).into(iv);
                } else {
                    Toast.makeText(getApplicationContext(), "Some error occurred...", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(this, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            finish();
        }
        return true;
    }

    private void updateProfile(Integer id, String name, String phone, String url_image) {

        mService.update(id,name,phone,url_image).enqueue(new Callback<Users>() {
            @Override
            public void onResponse(@NonNull Call<Users> call, @NonNull Response<Users> response) {
                if(response.isSuccessful()) {
                    if(response.body().getResponse().equals("failed")) {

                        Toast.makeText(EditProfileActivity.this,"Failed!",Toast.LENGTH_LONG).show();

                    }else if(response.body().getResponse().equals("success")){

                        Toast.makeText(EditProfileActivity.this,"Update Successful!",Toast.LENGTH_LONG).show();

                        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("user_name",name);
                        editor.putString("user_phone",phone);
                        editor.putString("user_image",url_image);
                        editor.commit();

                        txtName.setText(name);

                    }
                }else{
                    Log.e("UPDATEEEEE", "TESTANDPNAJKF."+id+name+phone+url_image+response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Users> call, @NonNull Throwable t) {
                Log.e("LOG ERROR", "Unable to submit post to API."+t);
            }
        });
    }

}