package com.pucpr.heverton.allaboutfishing.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pucpr.heverton.allaboutfishing.R;
import com.pucpr.heverton.allaboutfishing.model.Places;
import com.pucpr.heverton.allaboutfishing.view.PlaceDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.ViewHolder> {

    Context mContext;
    private List<Places> mPlaces;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView txtNamePlace, txtDescPlace;
        public ImageView imgPlace;
        ItemClickListener itemClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNamePlace = itemView.findViewById(R.id.lblNamePlace);
            txtDescPlace = itemView.findViewById(R.id.lblDescPlace);
            imgPlace = itemView.findViewById(R.id.imgPlace);

            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener=itemClickListener;
        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClick(this.getLayoutPosition());
        }
    }

    public PlacesAdapter(Context mContext, List<Places> mPlaces) {
        this.mContext = mContext;
        this.mPlaces = mPlaces;
    }

    @NonNull
    @Override
    public PlacesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View placeView = inflater.inflate(R.layout.cell_places,parent,false);

        return new PlacesAdapter.ViewHolder(placeView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlacesAdapter.ViewHolder holder, int position) {

        final Places place = mPlaces.get(position);

        TextView txtNamePlace = holder.txtNamePlace;
        txtNamePlace.setText(place.getName());

        TextView txtDescPlace = holder.txtDescPlace;
        txtDescPlace.setText(place.getDescription());

        ImageView iv = holder.imgPlace;
        Picasso.get().load(place.getUrlImage()).into(iv);

        holder.setItemClickListener(pos -> openDetailActivity(place.getId().toString(),place.getName()));
    }

    @Override
    public int getItemCount() {
        return mPlaces.size();
    }

    public void updatePlaces(List<Places> places) {
        mPlaces = places;
        notifyDataSetChanged();
    }

    public void openDetailActivity(String x,String y) {
        Intent i = new Intent(mContext, PlaceDetailsActivity.class);
        i.putExtra("PLACE_ID", Integer.parseInt(x));
        i.putExtra("PLACE_NAME",y);
        mContext.startActivity(i);
    }

}
