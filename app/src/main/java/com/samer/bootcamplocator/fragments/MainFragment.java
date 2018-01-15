package com.samer.bootcamplocator.fragments;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.samer.bootcamplocator.R;
import com.samer.bootcamplocator.activities.MapsActivity;
import com.samer.bootcamplocator.model.Data;
import com.samer.bootcamplocator.services.DataService;

import java.util.ArrayList;


public class MainFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mGoogleMap;
    private MarkerOptions markerOptions;
    private LatLng latLng;

    //private LocationManager mLocationManager;

    EditText searchEditText;
    ImageButton searchBtn;

    private LocationsListFragment locationsListFragment;


    public MainFragment() {

    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);



        searchEditText = view.findViewById(R.id.searchEditText);
        searchBtn = view.findViewById(R.id.searchBtn);

        searchEditText.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if((event.getAction() == KeyEvent.ACTION_DOWN) && keyCode == KeyEvent.KEYCODE_ENTER) {
                    return calculateZipCode();
                }

                return false;
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateZipCode();
            }
        });



        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        locationsListFragment = (LocationsListFragment)getActivity().getSupportFragmentManager()
                .findFragmentById(R.id.container_location_list);

        if(locationsListFragment == null) {
            locationsListFragment = LocationsListFragment.newInstance();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_location_list, locationsListFragment).commit();
        }

        hideList();
        return view;
    }




    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        // Enable current location:
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            MapsActivity mapsActivity = new MapsActivity();
            mapsActivity.requestPermissions();
        }
        mGoogleMap.setMyLocationEnabled(true);


        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                hideList();
            }
        });


        //updateMapFoZip(92281);
        // Add a marker in Sydney and move the camera
        //latLng = new LatLng(-34, 151);
        //mGoogleMap.addMarker(new MarkerOptions().position(latLng).title("Marker in Sydney"));
        //mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }







    public void updateUserMarker(Location location) {
        if(markerOptions == null) {
            markerOptions = new MarkerOptions();
        }

        latLng = new LatLng(location.getLatitude(), location.getLongitude());
        mGoogleMap.addMarker(markerOptions.position(latLng).title("My Location"));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));


        /*
        try {
            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
            List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            updateMapFoZip(Integer.parseInt(addressList.get(0).getPostalCode())); // get Zip Code from LatLng location
        } catch (IOException e) {
            e.printStackTrace();
        }
        */

    }


    private void updateMapFoZip(int zipCode) {
        ArrayList<Data> locations = DataService.getInstance().getBootcamoLocationsWithin10MilesOfZip(zipCode);
        Data data;
        MarkerOptions marker;

        for(int i = 0; i < locations.size(); i++) {
            data = locations.get(i);
            latLng = new LatLng(data.getLatitude(), data.getLongitude());
            marker = new MarkerOptions().position(latLng)
                    .title(data.getLocationTitle())
                    .snippet(data.getLocationAddress()).icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pin));
            mGoogleMap.addMarker(marker);
        }

        if(latLng != null)
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }


    private void showList() {
        getActivity().getSupportFragmentManager().beginTransaction().show(locationsListFragment).commit();
    }


    private void hideList() {
        getActivity().getSupportFragmentManager().beginTransaction().hide(locationsListFragment).commit();
    }


    private boolean calculateZipCode() {
        String text = searchEditText.getText().toString();
        int zipCode = 0;

        if(text.length() == 5) {
            try {
                zipCode = Integer.parseInt(text);
            } catch (Exception ex) {
                Log.v("Error casting zipCode", "Message:" + ex.getMessage());
            }

            InputMethodManager inputMethodManager = (InputMethodManager) getActivity()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(searchEditText.getWindowToken(), 0);

            showList();
            updateMapFoZip(zipCode);
            return true;
        }
        else {
            Toast.makeText(getContext(), "Please enter 5 digits only", Toast.LENGTH_LONG).show();
            return false;
        }
    }


}





