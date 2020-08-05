package io.okhi.android_core.models;

import android.util.Base64;

import androidx.annotation.NonNull;

public class OkHiAuth {
    private String accessToken;
    private OkHiAppContext context;

    private OkHiAuth(Builder builder) {
        this.accessToken = builder.accessToken;
        this.context = builder.context;
    }

    public static class Builder {
        private String branchId;
        private String clientKey;
        private String accessToken;
        private OkHiAppContext context = new OkHiAppContext.Builder(OkHiMode.SANDBOX).build();

        public Builder(@NonNull String branchId, @NonNull String clientKey) {
            this.branchId = branchId;
            this.clientKey = clientKey;
        }

        public Builder withContext(OkHiAppContext context) {
            this.context = context;
            return this;
        }

        public OkHiAuth build() {
            String concat = branchId + ":" + clientKey;
            this.accessToken = Base64.encodeToString(concat.getBytes(), Base64.NO_WRAP);
            return new OkHiAuth(this);
        }
    }
    public String getAccessToken() {
        return "Token " + accessToken;
    }

    public OkHiAppContext getContext() {
        return context;
    }
}
