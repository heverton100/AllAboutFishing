package com.pucpr.heverton.allaboutfishing.view.ui.main;


import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.pucpr.heverton.allaboutfishing.databinding.FragmentPlaceDetailsBinding;
import com.pucpr.heverton.allaboutfishing.model.Places;

import com.pucpr.heverton.allaboutfishing.remote.ApiUtils;
import com.pucpr.heverton.allaboutfishing.remote.PostService;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceDetailsFragment extends Fragment {

    private FragmentPlaceDetailsBinding binding;
    private PostService mService;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        mService = ApiUtils.getPostService();

        binding = FragmentPlaceDetailsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Intent i = this.getActivity().getIntent();
        Integer place_id = i.getIntExtra("PLACE_ID",0);

        mService.getPlace(place_id).enqueue(new Callback<Places>() {
            @Override
            public void onResponse(@NonNull Call<Places> call, @NonNull Response<Places> response) {
                if(response.isSuccessful()) {
                    if(response.body().getResponse().equals("failed")) {

                        Log.d("PLACE_DETAILS_FRAGMENT", "ERROR IN RESPONSE");

                    }else if(response.body().getResponse().equals("success")){

                        binding.txtPlaceNameDetail.setText(response.body().getName());
                        binding.txtCityStateDetail.setText(response.body().getCityName()+", "+response.body().getStateName());
                        binding.txtDescDetail.setText(response.body().getDescription());
                        binding.txtFishDetail.setText(formatString(response.body().getPlaceFishes()));
                        binding.txtServicesDetail.setText(formatString(response.body().getServices()));

                        Picasso.get().load(response.body().getUrlImage()).into(binding.imgPlaceDetail);

                    }
                }else{
                    int statusCode = response.code();
                    Log.e("PLACE_DETAILS_FRAGMENT", "Call REST return: "+statusCode);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Places> call, @NonNull Throwable t) {
                Log.e("PLACE_DETAILS_FRAGMENT", "Unable to submit post to API."+t);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public String formatString(String x){

        String[] y = x.split(",");
        StringBuilder sb = new StringBuilder();
        sb.append("• ");
        for (int i = 0; i < y.length; i++) {
            if((i-1) % 2 == 0 ){
                sb.append(y[i]);
                sb.append("\n•");
            }else{
                sb.append(y[i]);
                sb.append("\t\t•");
            }
        }
        String joined = sb.toString();
        return joined.substring(0, joined.length()-2);
    }
}