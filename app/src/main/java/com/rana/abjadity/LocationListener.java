package com.rana.abjadity;

import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

public class LocationListener implements android.location.LocationListener {
    public static Location location;

    public LocationListener() {
        location=new Location("zero");
        location.setLongitude(0);
        location.setLongitude(0);
    }

    public static LatLng getLocation() {
        return new LatLng(location.getLatitude(),location.getLongitude());
    }

        @Override
        public void onLocationChanged(Location location) {
        this.location=location;
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };
