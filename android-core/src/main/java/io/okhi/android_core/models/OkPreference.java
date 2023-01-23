package io.okhi.android_core.models;

import android.content.Context;
import android.content.SharedPreferences;

public class OkPreference {

  public static void setItem(String key, String value, Context context) throws OkHiException {
    try {
      SharedPreferences prefs = context.getSharedPreferences(Constant.OKHI_PREFERENCE_NAME, Context.MODE_PRIVATE);
      SharedPreferences.Editor editor = prefs.edit();
      editor.putString(key, value).apply();
    } catch (Exception e) {
      e.printStackTrace();
      throw new OkHiException(OkHiException.UNKNOWN_ERROR_CODE, e.getMessage() != null ? e.getMessage() : OkHiException.UNKNOWN_ERROR_MESSAGE);
    }
  }

  public static String getItem(String key, Context context) throws OkHiException {
    try {
      SharedPreferences prefs = context.getSharedPreferences(Constant.OKHI_PREFERENCE_NAME, Context.MODE_PRIVATE);
      String value = prefs.getString(key, "");
      if(value.isEmpty()){
        return null;
      }
      return value;
    } catch (Exception e) {
      e.printStackTrace();
      throw new OkHiException(OkHiException.UNKNOWN_ERROR_CODE, e.getMessage() != null ? e.getMessage() : OkHiException.UNKNOWN_ERROR_MESSAGE);
    }
  }
}
