package com.pucpr.heverton.allaboutfishing.view.ui.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pucpr.heverton.allaboutfishing.adapter.GalleriesPlaceAdapter;
import com.pucpr.heverton.allaboutfishing.databinding.FragmentGalleryBinding;
import com.pucpr.heverton.allaboutfishing.model.Galleries;
import com.pucpr.heverton.allaboutfishing.remote.ApiUtils;
import com.pucpr.heverton.allaboutfishing.remote.PostService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    private GalleriesPlaceAdapter mAdapter;
    private PostService mService;

    Integer place_id;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mService = ApiUtils.getPostService();

        binding = FragmentGalleryBinding.inflate(inflater, container,false);
        View root = binding.getRoot();

        RecyclerView recyclerView = binding.rvGalleryPlace;
        mAdapter = new GalleriesPlaceAdapter(root.getContext(), new ArrayList<Galleries>(0));
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(root.getContext(),3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setHasFixedSize(true);

        Intent i = this.getActivity().getIntent();
        place_id = i.getIntExtra("PLACE_ID",0);

        returnGallery(place_id);

        return root;
    }

    private void returnGallery(Integer place_id) {

        Call<List<Galleries>> call = mService.getPlaceGallery(place_id);

        call.enqueue(new Callback<List<Galleries>>() {
            @Override
            public void onResponse(@NonNull Call<List<Galleries>> call, @NonNull Response<List<Galleries>> response) {

                if(response.isSuccessful()){
                    mAdapter.updateGalleries(response.body());
                }else {
                    int statusCode = response.code();
                    Log.e("GALLERY_FRAGMENT", "Call REST return: "+statusCode);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Galleries>> call, @NonNull Throwable t) {
                Log.e("GALLERY_FRAGMENT", "Error in call REST"+t);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        returnGallery(place_id);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}