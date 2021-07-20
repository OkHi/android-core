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
    private String photo;
    private String propertyNumber;
    private String country;
    private String state;
    private String city;

    public OkHiLocation(String id, double lat, double lon) {
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
        this.photo = builder.photo;
        this.propertyNumber = builder.propertyNumber;
        this.country = builder.country;
        this.state = builder.state;
        this.city = builder.city;
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
        private String photo;
        private String propertyNumber;
        private String country;
        private String state;
        private String city;

        public Builder(String id, double lat, double lon) {
            this.id = id;
            this.lat = lat;
            this.lon = lon;
        }

        public Builder setPlaceId(String placeId) {
            this.placeId = placeId;
            return this;
        }

        public Builder setPlusCode(String plusCode) {
            this.plusCode = plusCode;
            return this;
        }

        public Builder setPropertyName(String propertyName) {
            this.propertyName = propertyName;
            return this;
        }

        public Builder setStreetName(String streetName) {
            this.streetName = streetName;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setSubtitle(String subtitle) {
            this.subtitle = subtitle;
            return this;
        }

        public Builder setDirections(String directions) {
            this.directions = directions;
            return this;
        }

        public Builder setOtherInformation(String otherInformation) {
            this.otherInformation = otherInformation;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setStreetViewPanoId(String streetViewPanoId) {
            this.streetViewPanoId = streetViewPanoId;
            return this;
        }

        public Builder setStreetViewPanoUrl(String streetViewPanoUrl) {
            this.streetViewPanoUrl = streetViewPanoUrl;
            return this;
        }

        public Builder setUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder setPhoto(String photo) {
            this.photo = photo;
            return this;
        }

        public Builder setPropertyNumber(String propertyNumber) {
            this.propertyNumber = propertyNumber;
            return this;
        }

        public Builder setCountry(String country) {
            this.country = country;
            return this;
        }

        public Builder setState(String state) {
            this.state = state;
            return this;
        }

        public Builder setCity(String city) {
            this.city = city;
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

    public String getPhoto() {
        return photo;
    }

    public String getPropertyNumber() {
        return propertyNumber;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }
}
