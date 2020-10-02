package io.okhi.android_core;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import io.okhi.android_core.interfaces.OkHiRequestHandler;
import io.okhi.android_core.models.OkHiLocationService;
import io.okhi.android_core.models.OkHiPermissionService;
import io.okhi.android_core.models.OkHiPlayService;

public class OkHi {
    private final OkHiPermissionService permissionService;
    private final OkHiPlayService playService;
    private final OkHiLocationService locationService;

    public OkHi(@NonNull Activity activity) {
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

    public void requestBackgroundLocationPermission(@NonNull String rationaleTitle, @NonNull String rationaleMessage, final OkHiRequestHandler<Boolean> handler) {
        permissionService.requestBackgroundLocationPermission(rationaleTitle, rationaleMessage, handler);
    }

    public void requestEnableGooglePlayServices(@NonNull final OkHiRequestHandler<Boolean> handler) {
        playService.requestEnableGooglePlayServices(handler);
    }

    public void requestEnableLocationServices(@NonNull final OkHiRequestHandler<Boolean> handler) {
        locationService.requestEnableLocationServices(handler);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionService.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        playService.onActivityResult(requestCode, resultCode, data);
        locationService.onActivityResult(requestCode, resultCode, data);
    }
}
