package io.okhi.okhiandroidcore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import io.okhi.okhi_auth.OkHiAppContext;
import io.okhi.okhi_auth.OkHiAuth;
import io.okhi.okhi_auth.OkHiDeveloperType;
import io.okhi.okhi_auth.OkHiMode;
import io.okhi.okhi_auth.OkHiPlatformType;

public class MainActivity extends AppCompatActivity {

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
    }
}
