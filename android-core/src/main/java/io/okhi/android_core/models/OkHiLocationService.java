package io.okhi.android_core.models;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Timer;
import java.util.TimerTask;

import io.okhi.android_core.interfaces.OkHiRequestHandler;

import static io.okhi.android_core.models.Constant.FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS;
import static io.okhi.android_core.models.Constant.LOCATION_GPS_ACCURACY;
import static io.okhi.android_core.models.Constant.LOCATION_WAIT_DELAY;
import static io.okhi.android_core.models.Constant.UPDATE_INTERVAL_IN_MILLISECONDS;

public class OkHiLocationService {
    private Context context;
    private Activity activity;
    private OkHiRequestHandler<Boolean> requestHandler;
    private SettingsClient settingsClient;
    private LocationSettingsRequest locationSettingsRequest = buildLocationSettingsRequest();
    private OkHiException exception = new OkHiException(OkHiException.SERVICE_UNAVAILABLE_CODE, "Location services are currently unavailable");
    private static LocationCallback locationCallback;
    private static LocationResult locationResult;

    public OkHiLocationService(Activity activity) {
        this.context = activity.getApplicationContext();
        this.activity = activity;
        this.settingsClient = LocationServices.getSettingsClient(context);
    }

    public static boolean isLocationServicesEnabled(Context context) {
        int locationMode = getLocationMode(context);
        if (locationMode != Settings.Secure.LOCATION_MODE_OFF) {
            if (locationMode == Settings.Secure.LOCATION_MODE_BATTERY_SAVING || locationMode == Settings.Secure.LOCATION_MODE_HIGH_ACCURACY) {
                return true;
            }
            return false;
        }
        return false;
    }

    public static void openLocationServicesSettings(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        activity.startActivityForResult(intent, Constant.OPEN_LOCATION_SERVICES_SETTINGS_REQUEST_CODE, new Bundle());
    }

    private static int getLocationMode (Context context) {
        try {
            return Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
        } catch (Exception e) {
            e.printStackTrace();
            return Settings.Secure.LOCATION_MODE_OFF;
        }
    }

    private LocationSettingsRequest buildLocationSettingsRequest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        return builder.build();
    }

    private void onLocationSettingsResponse(Task<LocationSettingsResponse> task) {
        try {
            LocationSettingsResponse response = task.getResult(ApiException.class);
        } catch (ApiException e) {
            if (e.getStatusCode() == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
                try {
                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    resolvable.startResolutionForResult(activity, Constant.ENABLE_LOCATION_SERVICES_REQUEST_CODE);
                } catch (Exception error) {
                    requestHandler.onResult(false);
                    e.printStackTrace();
                }
            }
        }
    }

    public void requestEnableLocationServices(OkHiRequestHandler<Boolean> handler) {
        if (isLocationServicesEnabled(context)) {
            handler.onResult(true);
            return;
        }
        int locationMode = getLocationMode(context);
        if (locationMode == Settings.Secure.LOCATION_MODE_OFF) {
            this.requestHandler = handler;
            settingsClient.checkLocationSettings(locationSettingsRequest).addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
                @Override
                public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                    onLocationSettingsResponse(task);
                }
            });
        } else {
            openLocationServicesSettings(activity);
            try {
                Toast.makeText(context, "Please enable \"High accuracy\" location settings", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Handler handler = new Handler();
        if (requestCode == Constant.ENABLE_LOCATION_SERVICES_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        requestHandler.onResult(true);
                    }
                }, Constant.SERVICE_WAIT_DELAY);
            } else {
                requestHandler.onResult(false);
            }
        }
    }

    public static void getCurrentLocation(final Context context, final OkHiRequestHandler<Location> handler)  {
        boolean isLocationPermissionGranted = OkHiPermissionService.isLocationPermissionGranted(context);
        boolean isLocationServicesEnabled = OkHiLocationService.isLocationServicesEnabled(context);
        OkHiException permissionException = new OkHiException(OkHiException.PERMISSION_DENIED_CODE, "Location permission is not granted");
        OkHiException serviceException = new OkHiException(OkHiException.SERVICE_UNAVAILABLE_CODE, "Location services are unavailable");
        final FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(context);
        final Timer timer = new Timer();
        if (!isLocationPermissionGranted) {
            handler.onError(permissionException);
        }
        if (!isLocationServicesEnabled) {
            handler.onError(serviceException);
        }
        timer.schedule(new TimerTask() {
            public void run() {
                if (locationResult == null) {
                    handler.onError(new OkHiException(OkHiException.SERVICE_UNAVAILABLE_CODE, "Last location isn't yet available"));
                } else {
                    handler.onResult(locationResult.getLastLocation());
                }
                client.removeLocationUpdates(locationCallback);
                timer.cancel();
            }
        }, LOCATION_WAIT_DELAY);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(final LocationResult locationUpdate) {
                super.onLocationResult(locationUpdate);
                if (locationUpdate.getLastLocation() != null) {
                    locationResult = locationUpdate;
                    if (locationUpdate.getLastLocation().getAccuracy() <= LOCATION_GPS_ACCURACY) {
                        handler.onResult(locationResult.getLastLocation());
                        client.removeLocationUpdates(locationCallback);
                        timer.cancel();
                    }
                }
            }
        };
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            handler.onError(permissionException);
        }
        client.requestLocationUpdates(getLocationRequest(), locationCallback, Looper.getMainLooper());
    }

    private static LocationRequest getLocationRequest() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setExpirationDuration(Constant.LOCATION_REQUEST_EXPIRATION_DURATION);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        locationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        return locationRequest;
    }
}
