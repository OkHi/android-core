package io.okhi.android_core.models;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import io.okhi.android_core.interfaces.OkHiRequestHandler;

public class OkHiPlayService {
    private OkHiRequestHandler<Boolean> requestHandler;
    private GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
    private Activity activity;
    private OkHiException exception = new OkHiException(OkHiException.SERVICE_UNAVAILABLE_CODE, "Google play services is currently unavailable");

    public OkHiPlayService(Activity activity) {
        this.activity = activity;
    }

    public static boolean isGooglePlayServicesAvailable(Context context) {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(context);
        return result == ConnectionResult.SUCCESS;
    }

    public void requestEnableGooglePlayServices(final OkHiRequestHandler<Boolean> requestHandler) {
        this.requestHandler = requestHandler;
        int result = googleAPI.isGooglePlayServicesAvailable(activity.getApplicationContext());
        if (result != ConnectionResult.SUCCESS) {
            if (googleAPI.isUserResolvableError(result)) {
                Dialog dialog = googleAPI.getErrorDialog(activity, result, Constant.ENABLE_GOOGLE_PLAY_SERVICES_REQUEST_CODE);
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        requestHandler.onResult(false);
                    }
                });
                dialog.show();
            } else {
                // device not supported
                OkHiCoreUtil.captureException(new Exception("Google play services not supported on device"));
                requestHandler.onError(exception);
            }
        } else {
            requestHandler.onResult(true);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constant.ENABLE_GOOGLE_PLAY_SERVICES_REQUEST_CODE) {
            Handler handle = new Handler();
            handle.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (isGooglePlayServicesAvailable(activity)) {
                        requestHandler.onResult(true);
                    } else {
                        requestHandler.onResult(false);
                    }
                }
            }, Constant.SERVICE_WAIT_DELAY);
        }
    }
}
