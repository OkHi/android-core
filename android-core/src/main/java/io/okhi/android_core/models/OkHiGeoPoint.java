package io.okhi.android_core.models;

import androidx.annotation.NonNull;

class OkHiGeoPoint {
    private double lat;
    private double lon;
    public OkHiGeoPoint(@NonNull double lat, @NonNull double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLon() {
        return lon;
    }

    public double getLat() {
        return lat;
    }
}
