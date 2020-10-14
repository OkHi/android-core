package io.okhi.okhiandroidcore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import io.okhi.android_core.OkHi;
import io.okhi.android_core.interfaces.OkHiRequestHandler;
import io.okhi.android_core.models.OkHiAppContext;
import io.okhi.android_core.models.OkHiAuth;
import io.okhi.android_core.models.OkHiDeveloperType;
import io.okhi.android_core.models.OkHiException;
import io.okhi.android_core.models.OkHiPlatformType;

public class MainActivity extends AppCompatActivity {

    OkHi okHi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OkHiAppContext context = new OkHiAppContext.Builder(Secret.OkHi_MODE)
                .setAppMeta("OkHi Core Test", "v1.0.0", 1)
                .setDeveloper(OkHiDeveloperType.OKHI)
                .setPlatform(OkHiPlatformType.ANDROID)
                .build();
        OkHiAuth auth = new OkHiAuth.Builder(Secret.OkHi_BRANCH_ID, Secret.OKHI_CLIENT_ID)
                .withContext(context)
                .build();
        CoreTest test = new CoreTest(auth);
        test.testAnonymousSignWithPhoneNumber(Secret.TEST_PHONE);
        test.testAnonymousSignWithUserId(Secret.TEST_USER_ID);
        test.getCurrentLocation(this);
        okHi = new OkHi(this);
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
        okHi.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void handleButtonTap(View v) {
        startAddressVerification();
    }
}
