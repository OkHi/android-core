package io.okhi.android_core.models;

import android.content.Context;
import android.content.SharedPreferences;

public class OkPreference {

  SharedPreferences prefs;
  SharedPreferences.Editor editor;

  public OkPreference(SharedPreferences sharedPreferences){
    this.prefs = sharedPreferences;
    this.editor = sharedPreferences.edit();
  }

  public void setItem(String key, String value) throws OkHiException {
    try {
      editor.putString(key, value).apply();
    } catch (Exception e) {
      e.printStackTrace();
      throw new OkHiException(OkHiException.UNKNOWN_ERROR_CODE, e.getMessage() != null ? e.getMessage() : OkHiException.UNKNOWN_ERROR_MESSAGE);
    }
  }

  public String getItem(String key) throws OkHiException {
    try {
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
