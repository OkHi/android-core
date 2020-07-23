package io.okhi.android_core.models;

import androidx.annotation.NonNull;

public class OkHiAppMeta {
    private String name;
    private String version;
    private int build;

    public OkHiAppMeta() {}

    public OkHiAppMeta(@NonNull String name, @NonNull String version, @NonNull int build) {
        this.name = name;
        this.version = version;
        this.build = build;
    }

    public String getName() {
        return name;
    }

    public int getBuild() {
        return build;
    }

    public String getVersion() {
        return version;
    }
}
