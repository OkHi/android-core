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

  public void requestEnableVerificationServices(@NonNull final OkHiRequestHandler<Boolean> handler) {
    playService.requestEnableGooglePlayServices(new OkHiRequestHandler<Boolean>() {
      @Override
      public void onResult(Boolean result) {
        if (result) {
          locationService.requestEnableLocationServices(new OkHiRequestHandler<Boolean>() {
            @Override
            public void onResult(Boolean result) {
              if (result) {
                permissionService.requestLocationPermission(new OkHiRequestHandler<Boolean>() {
                  @Override
                  public void onResult(Boolean result) {
                    if (result) {
                      permissionService.requestBackgroundLocationPermission(new OkHiRequestHandler<Boolean>() {
                        @Override
                        public void onResult(Boolean result) {
                          if (result) {
                            handler.onResult(true);
                          } else {
                            handleFalseResult(handler, OkHiException.BACKGROUND_LOCATION_PERMISSION_DENIED, "Background location denied.");
                          }
                        }
                        @Override
                        public void onError(OkHiException exception) {
                          handler.onError(exception);
                        }
                      });
                    } else {
                      handleFalseResult(handler, OkHiException.LOCATION_PERMISSION_DENIED, "Location permission denied.");
                    }
                  }
                  @Override
                  public void onError(OkHiException exception) {
                    handler.onError(exception);
                  }
                });
              } else {
                handleFalseResult(handler, OkHiException.LOCATION_SERVICES_UNAVAILABLE, "Location services unavailable.");
              }
            }
            @Override
            public void onError(OkHiException exception) {
              handler.onError(exception);
            }
          });
        } else {
          handleFalseResult(handler, OkHiException.GOOGLE_PLAY_SERVICES_UNAVAILABLE, "Google Play services unavailable.");
        }
      }
      @Override
      public void onError(OkHiException exception) {
        handler.onError(exception);
      }
    });
  }

  private void handleFalseResult(OkHiRequestHandler<Boolean> handler, String code, String message) {
    handler.onError(new OkHiException(code, message));
  }

  public static boolean isNotificationPermissionGranted(@NonNull Context context) {
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
