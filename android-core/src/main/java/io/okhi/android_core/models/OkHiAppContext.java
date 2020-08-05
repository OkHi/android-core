package io.okhi.android_core.models;

import androidx.annotation.NonNull;

import io.okhi.android_core.models.OkHiAppMeta;

public class OkHiAppContext {
    private String mode;
    private String platform;
    private String developer;
    private OkHiAppMeta appMeta;

    private OkHiAppContext(Builder builder) {
        this.mode = builder.mode;
        this.platform = builder.platform;
        this.developer = builder.developer;
        this.appMeta = builder.appMeta;
    }

    public static class Builder {
        private String mode;
        private String platform = OkHiPlatformType.ANDROID;
        private String developer = OkHiDeveloperType.EXTERNAL;
        private OkHiAppMeta appMeta = new OkHiAppMeta();
        public Builder(@NonNull String mode) {
            this.mode = mode;
        }
        public Builder setPlatform(@NonNull String platform){
            this.platform = platform;
            return this;
        }
        public Builder setDeveloper(@NonNull String developer){
            this.developer = developer;
            return this;
        }
        public Builder setAppMeta(@NonNull String name, @NonNull String version, @NonNull int build){
            appMeta.setName(name);
            appMeta.setVersion(version);
            appMeta.setBuild(build);
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

    public String getDeveloper() {
        return developer;
    }

    public String getPlatform() {
        return platform;
    }
}
