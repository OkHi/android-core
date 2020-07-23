package io.okhi.android_core.models;

import androidx.annotation.NonNull;

public class OkHiPlatform {
    private String name = OkHiPlatformTypes.ANDROID;

    public OkHiPlatform() {}

    public OkHiPlatform(@NonNull String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
