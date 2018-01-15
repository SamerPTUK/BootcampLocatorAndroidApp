package com.samer.bootcamplocator.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.samer.bootcamplocator.R;
import com.samer.bootcamplocator.adapters.LocationsAdapter;
import com.samer.bootcamplocator.services.DataService;


public class LocationsListFragment extends Fragment {

    public LocationsListFragment() {

    }


    public static LocationsListFragment newInstance() {
       return new LocationsListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_locations_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_content);
        recyclerView.setHasFixedSize(true);

        LocationsAdapter locationsAdapter = new LocationsAdapter(
                DataService.getInstance().getBootcamoLocationsWithin10MilesOfZip(123456));

        recyclerView.setAdapter(locationsAdapter);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                //System.out.println("Position " + linearLayoutManager.findLastCompletelyVisibleItemPosition());

            }
        });

        return view;
    }

}




