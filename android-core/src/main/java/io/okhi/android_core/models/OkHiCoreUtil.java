package io.okhi.android_core.models;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

public class OkHiCoreUtil {
  public static OkHiException generateOkHiException(Response response){
    if (response.code() == Constant.INVALID_PHONE_RESPONSE_CODE) {
      return new OkHiException(OkHiException.INVALID_PHONE_CODE, OkHiException.INVALID_PHONE_MESSAGE);
    } else if (response.code() == Constant.UNAUTHORIZED_RESPONSE_CODE) {
      return new OkHiException(OkHiException.UNAUTHORIZED_CODE, OkHiException.UNAUTHORIZED_MESSAGE);
    } else {
      return new OkHiException(OkHiException.UNKNOWN_ERROR_CODE, OkHiException.UNKNOWN_ERROR_MESSAGE);
    }
  }

    public static void getAllInstalledApps(Context context){
        List<String> componentList = new ArrayList<>();
        final PackageManager pm = context.getPackageManager();
        @SuppressLint("QueryPermissionsNeeded")
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo packageInfo : packages) {
            String pkgName = packageInfo.packageName;
            if(!pkgName.startsWith("com.android")){
                // Remove all Android packages
                componentList.add(pkgName);
            }
        }

        Log.e("All_apps_list", ":" + componentList);
    }
}

