package io.okhi.android_core.models;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class OkHiLocation implements Serializable {
    private String id;
    private double lat;
    private double lon;
    private String placeId;
    private String plusCode;
    private String propertyName;
    private String streetName;
    private String title;
    private String subtitle;
    private String directions;
    private String otherInformation;
    private String url;
    private String streetViewPanoId;
    private String streetViewPanoUrl;
    private String userId;

    public OkHiLocation(@NonNull String id, double lat, double lon) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
    }

    private OkHiLocation(Builder builder) {
        this.id = builder.id;
        this.lat = builder.lat;
        this.lon = builder.lon;
        this.placeId = builder.placeId;
        this.plusCode = builder.plusCode;
        this.propertyName = builder.propertyName;
        this.streetName = builder.streetName;
        this.title = builder.title;
        this.subtitle = builder.subtitle;
        this.directions = builder.directions;
        this.otherInformation = builder.otherInformation;
        this.url = builder.url;
        this.streetViewPanoId = builder.streetViewPanoId;
        this.streetViewPanoUrl = builder.streetViewPanoUrl;
        this.userId = builder.userId;
    }

    public static class Builder {
        private String id;
        private double lat;
        private double lon;
        private String placeId;
        private String plusCode;
        private String propertyName;
        private String streetName;
        private String title;
        private String subtitle;
        private String directions;
        private String otherInformation;
        private String url;
        private String streetViewPanoId;
        private String streetViewPanoUrl;
        private String userId;

        public Builder(@NonNull String id, double lat, double lon) {
            this.id = id;
            this.lat = lat;
            this.lon = lon;
        }

        public Builder setPlaceId(@NonNull String placeId) {
            this.placeId = placeId;
            return this;
        }

        public Builder setPlusCode(@NonNull String plusCode) {
            this.plusCode = plusCode;
            return this;
        }

        public Builder setPropertyName(@NonNull String propertyName) {
            this.propertyName = propertyName;
            return this;
        }

        public Builder setStreetName(@NonNull String streetName) {
            this.streetName = streetName;
            return this;
        }

        public Builder setTitle(@NonNull String title) {
            this.title = title;
            return this;
        }

        public Builder setSubtitle(@NonNull String subtitle) {
            this.subtitle = subtitle;
            return this;
        }

        public Builder setDirections(@NonNull String directions) {
            this.directions = directions;
            return this;
        }

        public Builder setOtherInformation(@NonNull String otherInformation) {
            this.otherInformation = otherInformation;
            return this;
        }

        public Builder setUrl(@NonNull String url) {
            this.url = url;
            return this;
        }

        public Builder setStreetViewPanoId(@NonNull String streetViewPanoId) {
            this.streetViewPanoId = streetViewPanoId;
            return this;
        }

        public Builder setStreetViewPanoUrl(@NonNull String streetViewPanoUrl) {
            this.streetViewPanoUrl = streetViewPanoUrl;
            return this;
        }

        public Builder setUserId(@NonNull String userId) {
            this.userId = userId;
            return this;
        }

        public OkHiLocation build() {
            return new OkHiLocation(this);
        }
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

    public String getTitle() {
        return title;
    }

    public String getDirections() {
        return directions;
    }

    public String getOtherInformation() {
        return otherInformation;
    }

    public String getPlaceId() {
        return placeId;
    }

    public String getPlusCode() {
        return plusCode;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getStreetViewPanoId() {
        return streetViewPanoId;
    }

    public String getStreetViewPanoUrl() {
        return streetViewPanoUrl;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getUrl() {
        return url;
    }

    public String getUserId() {
        return userId;
    }
}
