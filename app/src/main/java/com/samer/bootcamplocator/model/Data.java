package com.samer.bootcamplocator.model;

/**
 * Created by Samer AlShurafa on 1/14/2018.
 */

public class Data {

    public final String DRAWABLE = "drawable/";

    private float latitude;
    private float longitude;
    private String locationTitle;
    private String locationAddress;
    private String locationImrUrl;

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public String getLocationTitle() {
        return locationTitle;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public String getLocationImrUrl() {
        return locationImrUrl;
    }

    public String getImageURL() {
        return DRAWABLE + getLocationImrUrl();
    }

    public Data(float latitude, float longitude, String locationTitle, String locationAddress, String locationImrUrl) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.locationTitle = locationTitle;
        this.locationAddress = locationAddress;
        this.locationImrUrl = locationImrUrl;
    }

}
