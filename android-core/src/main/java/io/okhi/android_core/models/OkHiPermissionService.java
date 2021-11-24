package io.okhi.android_core.models;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.Arrays;

import io.okhi.android_core.interfaces.OkHiRequestHandler;

public class OkHiPermissionService {
    private Activity activity;
    private OkHiRequestHandler<Boolean> requestHandler;
    private OkHiException exception = new OkHiException(OkHiException.PERMISSION_DENIED_CODE, "Location permission denied");

    public OkHiPermissionService(Activity activity) {
        this.activity = activity;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestHandler == null) return;
        if (requestCode == Constant.LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestHandler.onResult(true);
            } else {
                requestHandler.onResult(false);
            }
        }
        if (requestCode == Constant.BACKGROUND_LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (isBackgroundLocationPermissionGranted(activity)) {
                    requestHandler.onResult(true);
                } else {
                    requestHandler.onResult(false);
                }
            } else {
                requestHandler.onResult(false);
            }
        }
    }

    private ArrayList<String> getBackgroundLocationPermissions() {
        ArrayList<String> locationPermissions = getLocationPermissions();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            locationPermissions.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION);
        }
        return locationPermissions;
    }

    private ArrayList<String> getLocationPermissions() {
        return new ArrayList<String>(Arrays.asList(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION));
    }

    public static boolean isBackgroundLocationPermissionGranted(Context context) {
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            int permission = ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_BACKGROUND_LOCATION);
            return permission == PackageManager.PERMISSION_GRANTED;
        }
        return isLocationPermissionGranted(context);
    }

    public static boolean isLocationPermissionGranted(Context context) {
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        int permission = ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
        return permission == PackageManager.PERMISSION_GRANTED;
    }

    public void requestLocationPermission(String rationaleTitle, String rationaleMessage, final OkHiRequestHandler<Boolean> handler) {
        if (isLocationPermissionGranted(activity)) {
            handler.onResult(true);
            return;
        }
        String[] permissions = new String[getLocationPermissions().size()];
        permissions = getLocationPermissions().toArray(permissions);
        requestPermission(permissions, Constant.LOCATION_PERMISSION_REQUEST_CODE, Manifest.permission.ACCESS_FINE_LOCATION, rationaleTitle, rationaleMessage, handler);
    }

    public void requestBackgroundLocationPermission(String rationaleTitle, String rationaleMessage, OkHiRequestHandler<Boolean> handler) {
        if (isBackgroundLocationPermissionGranted(activity)) {
            handler.onResult(true);
            return;
        }
        String[] permissions = new String[getBackgroundLocationPermissions().size()];
        permissions = getBackgroundLocationPermissions().toArray(permissions);
        String rationalPermission = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q ? Manifest.permission.ACCESS_BACKGROUND_LOCATION : Manifest.permission.ACCESS_FINE_LOCATION;
        requestPermission(permissions, Constant.BACKGROUND_LOCATION_PERMISSION_REQUEST_CODE, rationalPermission, rationaleTitle, rationaleMessage, handler);
    }

    public void requestBackgroundLocationPermission(OkHiRequestHandler<Boolean> handler) {
        if (isBackgroundLocationPermissionGranted(activity)) {
            handler.onResult(true);
            return;
        }
        String[] permissions = new String[getBackgroundLocationPermissions().size()];
        permissions = getBackgroundLocationPermissions().toArray(permissions);
        String rationalPermission = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q ? Manifest.permission.ACCESS_BACKGROUND_LOCATION : Manifest.permission.ACCESS_FINE_LOCATION;
        requestPermission(permissions, Constant.BACKGROUND_LOCATION_PERMISSION_REQUEST_CODE, rationalPermission, null, null, handler);
    }

    public void requestLocationPermission(final OkHiRequestHandler<Boolean> handler) {
        if (isLocationPermissionGranted(activity)) {
            handler.onResult(true);
            return;
        }
        String[] permissions = new String[getLocationPermissions().size()];
        permissions = getLocationPermissions().toArray(permissions);
        requestPermission(permissions, Constant.LOCATION_PERMISSION_REQUEST_CODE, Manifest.permission.ACCESS_FINE_LOCATION, null, null, handler);
    }

    private void requestPermission(final String[] permissions, final int permissionRequestCode, String rationalPermission, String rationaleTitle, String rationaleMessage, OkHiRequestHandler<Boolean> handler) {
        this.requestHandler = handler;
        boolean shouldShowRationale = ActivityCompat.shouldShowRequestPermissionRationale(activity, rationalPermission);
        if (!shouldShowRationale || (rationaleTitle == null || rationaleMessage == null)) {
            ActivityCompat.requestPermissions(activity, permissions, permissionRequestCode);
        } else {
            new AlertDialog.Builder(activity)
              .setTitle(rationaleTitle)
              .setMessage(rationaleMessage)
              .setPositiveButton(Constant.PERMISSION_DIALOG_POSITIVE_BUTTON_TEXT, new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialogInterface, int i) {
                      //Prompt the user once explanation has been shown
                      ActivityCompat.requestPermissions(activity, permissions, permissionRequestCode);
                  }
              })
              .setNegativeButton(Constant.PERMISSION_DIALOG_NEGATIVE_BUTTON_TEXT, new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                      requestHandler.onResult(false);
                  }
              })
              .setOnCancelListener(new DialogInterface.OnCancelListener() {
                  @Override
                  public void onCancel(DialogInterface dialog) {
                      requestHandler.onResult(false);
                  }
              })
              .create()
              .show();
        }
    }
}
