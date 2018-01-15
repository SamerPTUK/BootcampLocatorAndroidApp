package com.samer.bootcamplocator.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.samer.bootcamplocator.R;
import com.samer.bootcamplocator.model.Data;

/**
 * Created by Samer AlShurafa on 1/14/2018.
 */

public class LocationsViewHolder extends RecyclerView.ViewHolder{

    private TextView locationTitle, locationAddress;
    private ImageView locationImage;

    public LocationsViewHolder(View itemView) {
        super(itemView);

        locationTitle = itemView.findViewById(R.id.locationTitle);
        locationAddress = itemView.findViewById(R.id.locationAddress);
        locationImage = itemView.findViewById(R.id.locationImage);
    }


    public void updateUI(Data location) {
        String url = location.getImageURL();
        int resource = locationImage.getResources().getIdentifier(url, null, locationImage.getContext().getPackageName());
        locationImage.setImageResource(resource);

        locationTitle.setText(location.getLocationTitle());
        locationAddress.setText(location.getLocationAddress());
    }

}
