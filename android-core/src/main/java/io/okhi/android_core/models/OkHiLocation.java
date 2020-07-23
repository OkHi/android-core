package io.okhi.android_core.models;

import java.io.Serializable;

public class OkHiLocation implements Serializable {
    private String id;
    private String token;
    private String placeId;
    private String plusCode;
    private String propertyName;
    private String streetName;
    private String title;
    private String subtitle;
    private String directions;
    private String otherInformation;
    private String url;
    private String createdAt;
    private String photo;
    private OkHiGeoPoint geoPoint;
    private OkHiStreetView streetView;
}
