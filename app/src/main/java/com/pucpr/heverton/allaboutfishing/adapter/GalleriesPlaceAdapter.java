package com.pucpr.heverton.allaboutfishing.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pucpr.heverton.allaboutfishing.R;
import com.pucpr.heverton.allaboutfishing.model.Galleries;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GalleriesPlaceAdapter extends RecyclerView.Adapter<GalleriesPlaceAdapter.ViewHolder> {

    Context mContext;
    private List<Galleries> mGalleries;

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imgPlace;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPlace = itemView.findViewById(R.id.imgGalleryItem);
        }

    }

    public GalleriesPlaceAdapter(Context mContext, List<Galleries> mGalleries) {
        this.mContext = mContext;
        this.mGalleries = mGalleries;
    }

    @NonNull
    @Override
    public GalleriesPlaceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View placeView = inflater.inflate(R.layout.cell_gallery,parent,false);

        return new GalleriesPlaceAdapter.ViewHolder(placeView);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleriesPlaceAdapter.ViewHolder holder, int position) {

        final Galleries gallery = mGalleries.get(position);
        ImageView iv = holder.imgPlace;
        Picasso.get().load(gallery.getUrlPhoto()).into(iv);
    }

    @Override
    public int getItemCount() {
        return mGalleries.size();
    }

    public void updateGalleries(List<Galleries> galleries) {
        mGalleries = galleries;
        notifyDataSetChanged();
    }

}