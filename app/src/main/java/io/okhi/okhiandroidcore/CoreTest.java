package io.okhi.okhiandroidcore;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;

import io.okhi.android_core.OkHiCore;
import io.okhi.android_core.interfaces.OkHiRequestHandler;
import io.okhi.android_core.models.OkHiAccessScope;
import io.okhi.android_core.models.OkHiAuth;
import io.okhi.android_core.models.OkHiException;
import io.okhi.android_core.models.OkHiLocationService;

public class CoreTest extends OkHiCore {

    private String TAG = "CoreTest";

    protected CoreTest(@NonNull Context context) throws OkHiException {
        super(context);
    }

    public void testAnonymousSignWithPhoneNumber(String phone) {
        String[] scope = {OkHiAccessScope.VERIFY};
        anonymousSignWithPhoneNumber(phone, scope, new OkHiRequestHandler<String>() {
            @Override
            public void onResult(String authorizationToken) {
                Log.v(TAG, "Phone Auth token: " + authorizationToken);
            }

            @Override
            public void onError(OkHiException exception) {
                exception.printStackTrace();
            }
        });
    }

    public void testAnonymousSignWithUserId(String userId) {
        String[] scope = {OkHiAccessScope.VERIFY};
        anonymousSignInWithUserId(userId, scope, new OkHiRequestHandler<String>() {
            @Override
            public void onResult(String authorizationToken) {
                Log.v(TAG, "UserId Auth token: " + authorizationToken);
            }

            @Override
            public void onError(OkHiException exception) {
                exception.printStackTrace();
            }
        });
    }

    public void getCurrentLocation(Context context) {
        OkHiLocationService.getCurrentLocation(context, new OkHiRequestHandler<Location>() {
            @Override
            public void onResult(Location result) {
                Log.i(TAG, result.getAccuracy()+" "+result.getLatitude()+" "+result.getLongitude());
            }

            @Override
            public void onError(OkHiException exception) {
                Log.i(TAG, exception.getCode()+" "+exception.getMessage());
            }
        });
    }
}
