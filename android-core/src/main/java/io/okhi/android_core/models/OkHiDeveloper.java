package io.okhi.android_core.models;

import androidx.annotation.NonNull;

public class OkHiDeveloper {
    private String name = OkHiDeveloperType.EXTERNAL;
    public OkHiDeveloper() {}
    public OkHiDeveloper(@NonNull String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
