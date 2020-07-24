package io.okhi.android_core.models;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class OkHiLocation implements Serializable {
    private String id;
    private double lat;
    private double lon;

    public OkHiLocation(@NonNull String id, @NonNull double lat, @NonNull double lon) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getId() {
        return id;
    }
}
