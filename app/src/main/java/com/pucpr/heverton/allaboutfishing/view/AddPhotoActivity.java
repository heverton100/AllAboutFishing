package com.pucpr.heverton.allaboutfishing.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.pucpr.heverton.allaboutfishing.R;
import com.pucpr.heverton.allaboutfishing.databinding.ActivityAddPhotoBinding;
import com.pucpr.heverton.allaboutfishing.model.Galleries;
import com.pucpr.heverton.allaboutfishing.remote.ApiUtils;
import com.pucpr.heverton.allaboutfishing.remote.PostService;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPhotoActivity extends AppCompatActivity {

    private ActivityAddPhotoBinding binding;
    private Uri image;

    private PostService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddPhotoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_new_photo);

        mService = ApiUtils.getPostService();

        ImageView iv = binding.imgPhotoDetail;
        Bundle extras = getIntent().getExtras();
        image = Uri.parse(extras.getString("TEST_IMAGE"));
        Integer place_id = extras.getInt("PLACE_ID");

        SharedPreferences sharedPreferences = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        Integer id_sp = sharedPreferences.getInt("user_id",0);

        EditText subtitle = binding.txtSubtitle;

        iv.setImageURI(image);

        Button btn_publish = binding.btnNewPhotoPlace;

        btn_publish.setOnClickListener(v -> newPhoto(image,id_sp,place_id,subtitle.getText().toString()));

    }


    private void newPhoto(Uri fileUri, Integer user_id, Integer place_id, String subtitle) {

        //creating a file
        File file = new File(getRealPathFromURI(fileUri));

        //creating request body for file
        RequestBody requestFile = RequestBody.create(MediaType.parse(getContentResolver().getType(fileUri)), file);
        RequestBody user_id_Body = RequestBody.create(MediaType.parse("text/plain"), user_id.toString());
        RequestBody place_id_Body = RequestBody.create(MediaType.parse("text/plain"), place_id.toString());
        RequestBody subtitle_Body = RequestBody.create(MediaType.parse("text/plain"), subtitle);

        //creating a call and calling the upload image method
        Call<Galleries> call = mService.newPhoto(requestFile, user_id_Body, place_id_Body,subtitle_Body);

        //finally performing the call
        call.enqueue(new Callback<Galleries>() {
            @Override
            public void onResponse(@NonNull Call<Galleries> call, @NonNull Response<Galleries> response) {
                if (response.isSuccessful()) {

                    if(response.body().getResponse().equals("success")){

                        Toast.makeText(getApplicationContext(), "SUCCESS...", Toast.LENGTH_LONG).show();
                        onBackPressed();

                    }else{
                        Toast.makeText(getApplicationContext(), "FAILED...", Toast.LENGTH_LONG).show();
                    }

                }else{
                    int statusCode = response.code();
                    Log.e("ADD_PHOTO_ACTIVITY", "Call REST return: "+statusCode);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Galleries> call, @NonNull Throwable t) {
                Log.e("ADD_PHOTO_ACTIVITY", "Unable to submit post to API."+t);
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
        }
        return true;
    }
}