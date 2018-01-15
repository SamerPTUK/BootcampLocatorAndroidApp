package com.samer.bootcamplocator.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.samer.bootcamplocator.R;
import com.samer.bootcamplocator.holders.LocationsViewHolder;
import com.samer.bootcamplocator.model.Data;

import java.util.ArrayList;

/**
 * Created by Samer AlShurafa on 1/14/2018.
 */

public class LocationsAdapter extends RecyclerView.Adapter<LocationsViewHolder> {

    private ArrayList<Data> locations;

    public LocationsAdapter(ArrayList<Data> locations) {
        this.locations = locations;
    }

    @Override
    public LocationsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View card = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_location, parent, false);

        return new LocationsViewHolder(card);
    }

    @Override
    public void onBindViewHolder(LocationsViewHolder locationsViewHolder, int position) {
        Data location = locations.get(position);
        locationsViewHolder.updateUI(location);

        locationsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // load details page
            }
        });
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }
}




