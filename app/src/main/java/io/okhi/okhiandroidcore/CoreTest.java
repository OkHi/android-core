package io.okhi.okhiandroidcore;

import android.util.Log;

import androidx.annotation.NonNull;

import io.okhi.android_core.OkHiCore;
import io.okhi.android_core.interfaces.OkHiRequestHandler;
import io.okhi.android_core.interfaces.OkHiSignInRequestHandler;
import io.okhi.android_core.models.OkHiAccessScope;
import io.okhi.android_core.models.OkHiAuth;
import io.okhi.android_core.models.OkHiException;

public class CoreTest extends OkHiCore {

    private String TAG = "CoreTest";

    protected CoreTest(@NonNull OkHiAuth auth) {
        super(auth);
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
}
