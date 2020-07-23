package io.okhi.android_core.models;

import androidx.annotation.NonNull;

class OkHiStreetView {
    private String panoId;
    private String url;

    OkHiStreetView(@NonNull String panoId, @NonNull String url) {
        this.panoId = panoId;
        this.url = url;
    }

    public String getPanoId() {
        return panoId;
    }

    public String getUrl() {
        return url;
    }
}
