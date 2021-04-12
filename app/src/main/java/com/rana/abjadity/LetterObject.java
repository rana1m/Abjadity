package com.rana.abjadity;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

public class LetterObject {
    public int Image;
    public String name;
    public double power;
    public boolean isCatch;
    public Location location;

    public LetterObject(int image, String name, double power,double lat,double lag) {
        Image = image;
        this.name = name;
        this.power = power;
        this.isCatch = false;
        location = new Location(name);
        location.setLatitude(lat);
        location.setLongitude(lag);

    }

    public LetterObject(int image, String name, double power, LatLng randomLocation) {
        Image = image;
        this.name = name;
        this.power = power;
        this.isCatch = false;
        location = new Location(name);
        location.setLatitude(randomLocation.latitude);
        location.setLongitude(randomLocation.longitude);
    }
}
