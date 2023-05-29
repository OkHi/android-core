package io.okhi.android_core;

import static com.google.common.truth.Truth.assertThat;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Base64;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import io.okhi.android_core.models.Constant;
import io.okhi.android_core.models.OkHiAppContext;
import io.okhi.android_core.models.OkHiAppMeta;
import io.okhi.android_core.models.OkHiAuth;
import io.okhi.android_core.models.OkHiException;

public class OkHiAuthTest {

    OkHiAuth auth;
    OkHiAuth.Builder authBuilder;

    OkHiAppMeta okHiAppMeta;
    OkHiAppContext okHiAppContext;
    Context context = Mockito.mock(Context.class);
    PackageInfo packageInfo = Mockito.mock(PackageInfo.class);
    PackageManager packageManager = Mockito.mock(PackageManager.class);
    ApplicationInfo applicationInfo = Mockito.mock(ApplicationInfo.class);
    Bundle bundle = Mockito.mock(Bundle.class);
    private String branchId = "AUTH_BRANCH_ID";
    private String clientKey = "AUTH_CLIENT_KEY";
    private String accessToken;
    Base64 B64 = Mockito.mock(Base64.class);

    @Before
    public void set_up_auth() throws OkHiException, PackageManager.NameNotFoundException {

        packageInfo.versionName = "app_version";
        packageInfo.versionCode = 100;

        Mockito.when(context.getPackageName()).thenReturn("app_name");
        Mockito.when(context.getPackageManager()).thenReturn(packageManager);
        Mockito.when(packageManager.getPackageInfo(context.getPackageName(), 0)).thenReturn(packageInfo);

        okHiAppMeta = OkHiAppMeta.getAppMeta(context);


        okHiAppContext = new OkHiAppContext(context, "sandbox", "android", "external", okHiAppMeta);
        Mockito.when(packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA)).thenReturn(applicationInfo);

        Mockito.when(bundle.getString(Constant.AUTH_ENV_META_KEY)).thenReturn("sandbox");
        Mockito.when(bundle.getString(Constant.AUTH_BRANCH_ID_META_KEY)).thenReturn(branchId);
        Mockito.when(bundle.getString(Constant.AUTH_CLIENT_KEY_META_KEY)).thenReturn(clientKey);
        applicationInfo.metaData = bundle;
        accessToken = branchId + ":" + clientKey;

        authBuilder = new OkHiAuth.Builder(context);
        authBuilder.withContext(okHiAppContext);
        auth = authBuilder.build();

    }


//    @Test
//    public void getAccessTokenTest(){
////        try{
////            auth.getAccessToken();
////        } catch (Exception e){
////            assertThat(e).isInstanceOf(OkHiException.class);
////        }
//    }
}

