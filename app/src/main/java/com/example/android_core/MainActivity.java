package com.example.android_core;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import io.okhi.android_core.OkHi;
import io.okhi.android_core.interfaces.OkHiPermission;
import io.okhi.android_core.interfaces.OkHiPermissionHandler;
import io.okhi.android_core.interfaces.OkHiRequestHandler;
import io.okhi.android_core.models.OkHiException;
import io.okhi.android_core.models.OkHiPermissionService;
import io.okhi.android_core.models.OkPreference;

public class MainActivity extends AppCompatActivity {

  OkHi okHi;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    try {
      CoreTest test = new CoreTest(this);
      test.testAnonymousSignWithPhoneNumber(Secret.TEST_PHONE);
      test.testAnonymousSignWithUserId(Secret.TEST_USER_ID);
      // TODO: add in test for fetching current location
      okHi = new OkHi(this);
    } catch (OkHiException exception) {
      exception.printStackTrace();
    }
  }

  class Handler implements OkHiRequestHandler<Boolean> {
    @Override
    public void onResult(Boolean result) {
      startAddressVerification();
    }

    @Override
    public void onError(OkHiException exception) {
      showMessage(exception.getMessage());
    }
  }

  public void startAddressVerification() {
    boolean canStartAddressVerification = canStartAddressVerification();
    if (canStartAddressVerification) {
      showMessage("We are able to start address verification");
    }
  }

  private boolean canStartAddressVerification() {
    if (!OkHi.isLocationPermissionGranted(getApplicationContext())) {
      okHi.requestLocationPermission("Hey we need location permission", "Pretty please..", new Handler());
    } else if (!OkHi.isBackgroundLocationPermissionGranted(getApplicationContext())) {
      okHi.requestBackgroundLocationPermission("Hey we need location permission", "Pretty please..", new Handler());
    } else if (!OkHi.isGooglePlayServicesAvailable(getApplicationContext())) {
      okHi.requestEnableGooglePlayServices(new Handler());
    } else if (!OkHi.isLocationServicesEnabled(getApplicationContext())) {
      okHi.requestEnableLocationServices(new Handler());
    } else {
      return true;
    }
    return false;
  }

  private void showMessage(String message) {
    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    okHi.onActivityResult(requestCode, resultCode, data);
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    okHi.onRequestPermissionsResult(requestCode, permissions, grantResults, new OkHiPermissionHandler() {
      @Override
      public void onPermissionResult(OkHiPermission permission, boolean status) {
        Log.v("Location Perm", permission.toString() + ":" +status);
      }
    });
  }

  public void handleButtonTap(View v) {
//        startAddressVerification();
    okHi.requestLocationPermission(new OkHiRequestHandler<Boolean>() {
      @Override
      public void onResult(Boolean result) {
        Log.v("Permission","When In Use " + result);
        okHi.requestBackgroundLocationPermission(new OkHiRequestHandler<Boolean>() {
          @Override
          public void onResult(Boolean result) {
            Log.v("Permission","Always " + result);
          }

          @Override
          public void onError(OkHiException exception) {

          }
        });
      }
      @Override
      public void onError(OkHiException exception) {

      }
    });
  }

  public void handleLocationPerm(View view) {

  }

  public void handleDataAdd(View view){

    try {
      // Prefix key with address ID
      //Place 1
      OkPreference.setItem("name1xuai2345", "Dennis Pritt", this);
      OkPreference.setItem("id1xuai2345", "1xuai2345",this);
      OkPreference.setItem("color1xuai2345", "#008000",this);

      //Place 2
      OkPreference.setItem("nametryuyw6578", "Dennis Oval",this);
      OkPreference.setItem("idtryuyw6578", "1xuai2345",this);
      OkPreference.setItem("colortryuyw6578", "#ff1100",this);
    } catch (OkHiException e) {
      Log.e("OkHiException", e.toString());
    }
  }

  public void handleDataFetch(View view){

    try {
      String placeName1 = OkPreference.getItem("name1xuai2345",this);
      String placeColor1 = OkPreference.getItem("color1xuai2345",this);
      Log.e("Place 1", "Name: " + placeName1 + " Color: " + placeColor1);

      String placeName2 = OkPreference.getItem("nametryuyw6578",this);
      String placeColor2 = OkPreference.getItem("colortryuyw6578",this);
      Log.e("Place 2", "Name: " + placeName2 + " Color: " + placeColor2);
    } catch (OkHiException e) {
      Log.e("OkHiException", e.toString());
    }
  }

  public void handleProtectedApps(View view) {
    try {
      OkHiPermissionService.openProtectedAppsSettings(this, 12345);
    } catch (OkHiException e) {
      Log.e("OkHiException", e.getCode());
      Log.e("OkHiException", e.getMessage());
    }
  }

  public void handlePushNotificationRequest(View view){
    Log.v("PermissionRequest", "called");
    okHi.requestNotificationPermission(new OkHiRequestHandler<Boolean>() {
      @Override
      public void onResult(Boolean result) {
        Log.v("PermissionRequest", result + "");
      }
      @Override
      public void onError(OkHiException exception) {
        exception.printStackTrace();
      }
    });
  }
}