package io.okhi.android_core;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import io.okhi.android_core.interfaces.OkHiPermissionHandler;
import io.okhi.android_core.interfaces.OkHiRequestHandler;
import io.okhi.android_core.models.OkHiException;
import io.okhi.android_core.models.OkHiLocationService;
import io.okhi.android_core.models.OkHiPermissionService;
import io.okhi.android_core.models.OkHiPlayService;

public class OkHi {
  private final OkHiPermissionService permissionService;
  private final OkHiPlayService playService;
  private final OkHiLocationService locationService;

  public OkHi(@NonNull AppCompatActivity activity) {
    permissionService = new OkHiPermissionService(activity);
    playService = new OkHiPlayService(activity);
    locationService = new OkHiLocationService(activity);
  }

  public static boolean isLocationPermissionGranted(@NonNull Context context) {
    return OkHiPermissionService.isLocationPermissionGranted(context);
  }

  public static boolean isBackgroundLocationPermissionGranted(@NonNull Context context) {
    return OkHiPermissionService.isBackgroundLocationPermissionGranted(context);
  }

  public static boolean isLocationServicesEnabled(@NonNull Context context) {
    return OkHiLocationService.isLocationServicesEnabled(context);
  }

  public static boolean isGooglePlayServicesAvailable(@NonNull Context context) {
    return OkHiPlayService.isGooglePlayServicesAvailable(context);
  }

  public static void openLocationServicesSettings(@NonNull Activity activity) {
    OkHiLocationService.openLocationServicesSettings(activity);
  }

  public void requestLocationPermission(@NonNull String rationaleTitle, @NonNull String rationaleMessage, final OkHiRequestHandler<Boolean> handler) {
    permissionService.requestLocationPermission(rationaleTitle, rationaleMessage, handler);
  }

  public void requestLocationPermission(final OkHiRequestHandler<Boolean> handler) {
    permissionService.requestLocationPermission(handler);
  }

  public void requestBackgroundLocationPermission(final OkHiRequestHandler<Boolean> handler) {
    permissionService.requestBackgroundLocationPermission(handler);
  }

  public void requestBackgroundLocationPermission(@NonNull String rationaleTitle, @NonNull String rationaleMessage, final OkHiRequestHandler<Boolean> handler) {
    permissionService.requestBackgroundLocationPermission(rationaleTitle, rationaleMessage, handler);
  }

  public void requestEnableGooglePlayServices(@NonNull final OkHiRequestHandler<Boolean> handler) {
    playService.requestEnableGooglePlayServices(handler);
  }

  public void requestEnableLocationServices(@NonNull final OkHiRequestHandler<Boolean> handler) {
    locationService.requestEnableLocationServices(handler);
  }

  public void requestNotificationPermission(@NonNull final OkHiRequestHandler<Boolean> handler) {
    permissionService.requestNotificationPermission(handler);
  }

  public static boolean isPushNotificationPermissionGranted(@NonNull Context context) {
    return OkHiPermissionService.isNotificationPermissionGranted(context);
  }

  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    permissionService.onRequestPermissionsResult(requestCode, permissions, grantResults, null);
  }

  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, @NonNull OkHiPermissionHandler handler) {
    permissionService.onRequestPermissionsResult(requestCode, permissions, grantResults, handler);
  }

  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    playService.onActivityResult(requestCode, resultCode, data);
    locationService.onActivityResult(requestCode, resultCode, data);
  }
}
