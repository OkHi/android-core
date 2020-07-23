package io.okhi.android_core.models;

import androidx.annotation.NonNull;

public class OkHiAppContext {
    private String mode;
    private OkHiPlatform platform;
    private OkHiDeveloper developer;
    private OkHiAppMeta appMeta;

    public OkHiAppContext(Builder builder) {
        this.mode = builder.mode;
        this.platform = builder.platform;
        this.developer = builder.developer;
        this.appMeta = builder.appMeta;
    }

    public static class Builder {
        private String mode;
        private OkHiPlatform platform = new OkHiPlatform();
        private OkHiDeveloper developer = new OkHiDeveloper();
        private OkHiAppMeta appMeta = new OkHiAppMeta();
        public Builder(@NonNull String mode) {
            this.mode = mode;
        }
        public Builder setPlatform(@NonNull OkHiPlatform platform){
            this.platform = platform;
            return this;
        }
        public Builder setDeveloper(@NonNull OkHiDeveloper developer){
            this.developer = developer;
            return this;
        }
        public Builder setAppMeta(@NonNull OkHiAppMeta appMeta){
            this.appMeta = appMeta;
            return this;
        }
        public OkHiAppContext build() {
            return new OkHiAppContext(this);
        }
    }

    public String getMode() {
        return mode;
    }

    public OkHiAppMeta getAppMeta() {
        return appMeta;
    }

    public OkHiDeveloper getDeveloper() {
        return developer;
    }

    public OkHiPlatform getPlatform() {
        return platform;
    }
}
