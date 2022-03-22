package com.pucpr.heverton.allaboutfishing.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.MediaStore;
import android.view.MenuItem;

import com.pucpr.heverton.allaboutfishing.view.ui.main.ContactFragment;
import com.pucpr.heverton.allaboutfishing.view.ui.main.GalleryFragment;
import com.pucpr.heverton.allaboutfishing.view.ui.main.PlaceDetailsFragment;
import com.pucpr.heverton.allaboutfishing.view.ui.main.SectionsPagerAdapter;
import com.pucpr.heverton.allaboutfishing.databinding.ActivityPlaceDetailsBinding;

import java.util.ArrayList;
import java.util.List;

public class PlaceDetailsActivity extends AppCompatActivity {

    private ActivityPlaceDetailsBinding binding;
    private ActivityResultLauncher<Intent> someActivityResultLauncher;

    PlaceDetailsFragment fragment1;
    GalleryFragment fragment2;
    ContactFragment fragment3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragment1 = new PlaceDetailsFragment();
        fragment2 = new GalleryFragment();
        fragment3 = new ContactFragment();

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);

        binding = ActivityPlaceDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(),fragments);
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = binding.fab;

        Intent i=this.getIntent();
        String title = i.getStringExtra("PLACE_NAME");
        Integer place_id = i.getIntExtra("PLACE_ID",0);

        Toolbar toolbar = binding.toolbar2;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(title);

        fab.setOnClickListener(v -> {
            Intent i2 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            someActivityResultLauncher.launch(i2);
        });

        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        //the image URI
                        Uri selectedImage = data.getData();

                        Intent i1 = new Intent(binding.getRoot().getContext(), AddPhotoActivity.class);
                        i1.putExtra("TEST_IMAGE",selectedImage.toString());
                        i1.putExtra("PLACE_ID",place_id);
                        binding.getRoot().getContext().startActivity(i1);

                    }
                });

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        }

        return true;
    }
}