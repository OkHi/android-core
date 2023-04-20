package io.okhi.android_core.models;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OkHiAppContext {
  private String mode;
  private String platform;
  private String developer;
  private OkHiAppMeta appMeta;

  public OkHiAppContext(@NonNull Context context, @NonNull String mode, @NonNull String platform, @NonNull String developer) throws OkHiException {
    this.mode = mode;
    this.platform = platform;
    this.developer = developer;
    this.appMeta = OkHiAppMeta.getAppMeta(context);
  }

  public OkHiAppContext(@NonNull Context context, @NonNull String mode, @NonNull String platform, @NonNull String developer, @NonNull OkHiAppMeta appMeta) {
    this.mode = mode;
    this.platform = platform;
    this.developer = developer;
    this.appMeta = appMeta;
  }

  private OkHiAppContext(Builder builder) {
    this.mode = builder.mode;
    this.platform = builder.platform;
    this.developer = builder.developer;
    this.appMeta = builder.appMeta;
  }

  public static class Builder {
    private String mode;
    private OkHiAppMeta appMeta;
    private String platform = OkHiPlatformType.ANDROID;
    private String developer = OkHiDeveloperType.EXTERNAL;

    public Builder(@NonNull Context context) throws OkHiException {
      try {
        ApplicationInfo app = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
        Bundle bundle = app.metaData;
        String env = bundle.getString(Constant.AUTH_ENV_META_KEY, "sandbox");
        String developer = bundle.getString(Constant.AUTH_DEVELOPER_META_KEY, "external");
        String platform = bundle.getString(Constant.AUTH_PLATFORM_META_KEY, "android");
        String[] modes = new String[]{"dev", "sandbox", "prod"};
        List<String> modesList = new ArrayList<>(Arrays.asList(modes));
        if (env == null || !modesList.contains(env)) {
          throw new OkHiException(OkHiException.UNAUTHORIZED_CODE, "Invalid env provided in application meta");
        }
        if (developer.equals("okhi") ) {
          this.developer = developer;
        }
        if (platform.equals("android") || platform.equals("react-native")) {
          this.platform = platform;
        }
        this.appMeta = OkHiAppMeta.getAppMeta(context);
        this.mode = env;
      } catch (OkHiException e) {
        throw e;
      } catch (Exception e) {
        throw new OkHiException(OkHiException.UNKNOWN_ERROR_CODE, e.getMessage());
      }
    }
    public Builder setPlatform(@NonNull String platform){
      this.platform = platform;
      return this;
    }
    public Builder setDeveloper(@NonNull String developer){
      this.developer = developer;
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

  public static String getEnv(Context context) {
    try {
      ApplicationInfo app = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
      Bundle bundle = app.metaData;
      return bundle.getString(Constant.AUTH_ENV_META_KEY, null);
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
      return null;
    }
  }
}