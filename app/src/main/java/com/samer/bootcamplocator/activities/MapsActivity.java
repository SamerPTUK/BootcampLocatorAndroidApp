package com.samer.bootcamplocator.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.samer.bootcamplocator.R;
import com.samer.bootcamplocator.fragments.MainFragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;


public class MapsActivity extends FragmentActivity
    implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, LocationListener {


    private GoogleApiClient googleApiClient;
    private MainFragment mainFragment;

    private final static int LOCATION_PERMISSION = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();


        mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.container_main);

        if(mainFragment == null) {
            mainFragment = MainFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container_main, mainFragment)
                    .commit();
        }


        //Fetching the last known location using the FusedLocationProviderApi
        FusedLocationProviderClient fusedLocationProviderClient;

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());

            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {

                @Override
                public void onSuccess(Location location) {
                    if(location != null) {
                        Log.v("MyLocation: ", location.getAltitude() + " " + location.getLongitude());
                        mainFragment.updateUserMarker(location);
                    }
                }

            });
        } // if there is permission ..

    }



    private boolean isNoPermissions() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermissions() {
        Toast.makeText(getApplicationContext(), "You need to give permission to the app to can get your location",
                Toast.LENGTH_LONG).show();
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case LOCATION_PERMISSION:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startLocationServices();
                } else {
                    Toast.makeText(getApplicationContext(), "You need to give permission to the app to can get your location",
                            Toast.LENGTH_LONG).show();
                }
                break;
        }

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if(isNoPermissions()) {
            requestPermissions();
        } else {
            startLocationServices();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.v("MAPS", "Location changed, Longitude: " + location.getLongitude() + ", Altitude: " + location.getAltitude());
        mainFragment.updateUserMarker(location);
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }


    public void startLocationServices() {
        Log.v("MAPS", "Starting Location Services Called");

        try {
            //LocationRequest request = LocationRequest.create().setPriority(LocationRequest.PRIORITY_LOW_POWER);
            //LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, request, this);
            LocationServices.getFusedLocationProviderClient(this);
        } catch (SecurityException ex) {
            Log.v("MAPS", "No permission, " + ex.getMessage());

            requestPermissions();
        }
    }


}
