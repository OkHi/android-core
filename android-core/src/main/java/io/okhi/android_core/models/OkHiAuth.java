package io.okhi.android_core.models;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Base64;

import androidx.annotation.NonNull;

public class OkHiAuth {
    private String accessToken;
    private OkHiAppContext context;

    private OkHiAuth(Builder builder) {
        this.accessToken = builder.accessToken;
        this.context = builder.context;
    }

    public OkHiAuth(@NonNull Context context, @NonNull String branchId, @NonNull String clientKey) throws OkHiException {
        this.context = new OkHiAppContext(context, "sandbox", "android", "external");
        String concat = branchId + ":" + clientKey;
        this.accessToken = Base64.encodeToString(concat.getBytes(), Base64.NO_WRAP);
    }

    public OkHiAuth(@NonNull Context context, @NonNull String branchId, @NonNull String clientKey, @NonNull OkHiAppContext appContext) throws OkHiException {
        this.context = appContext;
        String concat = branchId + ":" + clientKey;
        this.accessToken = Base64.encodeToString(concat.getBytes(), Base64.NO_WRAP);
    }

    public static class Builder {
        private String branchId;
        private String clientKey;
        private String accessToken;
        private OkHiAppContext context;

        public Builder(@NonNull Context context) throws OkHiException {
            try {
                this.context = new OkHiAppContext.Builder(context).build();
                ApplicationInfo app = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                Bundle bundle = app.metaData;
                String branchId = bundle.getString(Constant.AUTH_BRANCH_ID_META_KEY);
                String clientKey = bundle.getString(Constant.AUTH_CLIENT_KEY_META_KEY);
                if (branchId == null || clientKey == null) {
                    throw new OkHiException(OkHiException.UNAUTHORIZED_CODE, "Invalid branchId and/or clientKey provided");
                }
                this.branchId = branchId;
                this.clientKey = clientKey;
            } catch (OkHiException e) {
                throw e;
            } catch (Exception e) {
                throw new OkHiException(OkHiException.UNKNOWN_ERROR_CODE, e.getMessage());
            }
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
