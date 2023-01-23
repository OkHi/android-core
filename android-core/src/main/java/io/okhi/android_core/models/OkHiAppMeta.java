package io.okhi.android_core.models;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class OkHiAppMeta {
  private String name;
  private String version;
  private int versionCode;

  private OkHiAppMeta(String appName, String versionName, int versionCode) {
    this.name = appName;
    this.version = versionName;
    this.versionCode = versionCode;
  }

  public static OkHiAppMeta getAppMeta(Context context) throws OkHiException {
    try {
      int versionCode = getAppVersionCode(context);
      String versionName = getAppVersionName(context);
      String appName = getApplicationPackageName(context);
      return new OkHiAppMeta(appName, versionName, versionCode);
    } catch (Exception e) {
      throw new OkHiException(OkHiException.UNKNOWN_ERROR_CODE, e.getMessage());
    }
  }

  private static int getAppVersionCode(Context context) {
    try {
      PackageInfo packageInfo = context.getPackageManager()
        .getPackageInfo(context.getPackageName(), 0);
      return packageInfo.versionCode;
    } catch (PackageManager.NameNotFoundException e) {
      // should never happen
      throw new RuntimeException("Could not get package name: " + e);
    }
  }

  private static String getAppVersionName(Context context) {
    try {
      PackageInfo packageInfo = context.getPackageManager()
        .getPackageInfo(context.getPackageName(), 0);
      return packageInfo.versionName;
    } catch (PackageManager.NameNotFoundException e) {
      // should never happen
      throw new RuntimeException("Could not get package name: " + e);
    }
  }

  private static String getApplicationPackageName (Context context) {
    return context.getPackageName();
  }

  public int getVersionCode() {
    return versionCode;
  }

  public String getName() {
    return name;
  }

  public String getVersion() {
    return version;
  }
}
