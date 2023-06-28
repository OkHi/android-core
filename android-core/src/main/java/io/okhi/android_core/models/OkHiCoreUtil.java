package io.okhi.android_core.models;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
            if(filteredPkgNames(pkgName)){
                componentList.add(pkgName);
            }
        }
        // TODO: 28/06/2023 Add an implementation to send the list to the server 
        Log.e("All_apps_list", ":" + componentList);
    }

    private static Boolean filteredPkgNames(String packageName){
      // We can add these dynamically from the server
        // Brand system
        return !packageName.toLowerCase(Locale.ROOT).contains("android") // Android system
                && !packageName.toLowerCase(Locale.ROOT).contains("google") // Android system
                && !packageName.toLowerCase(Locale.ROOT).contains("mediatek") // Manufacturer chip system
                && !packageName.toLowerCase(Locale.ROOT).contains("transsion") // Brand system
                && !packageName.toLowerCase(Locale.ROOT).contains("infinix") // Brand system
                && !packageName.toLowerCase(Locale.ROOT).contains("tecno") // Brand system
                && !packageName.toLowerCase(Locale.ROOT).contains("samsung");
    }
}

