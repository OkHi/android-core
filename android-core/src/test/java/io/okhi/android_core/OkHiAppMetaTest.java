package io.okhi.android_core;

import static com.google.common.truth.Truth.assertThat;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import org.junit.Test;
import org.mockito.Mockito;

import io.okhi.android_core.models.OkHiAppMeta;
import io.okhi.android_core.models.OkHiException;

public class OkHiAppMetaTest {
    private String name = "okhi_name";
    private String version = "okhi_version";
    private int versionCode = 100;
    OkHiAppMeta okHiAppMeta;
    Context context = Mockito.mock(Context.class);
    PackageInfo packageInfo = Mockito.mock(PackageInfo.class);
    PackageManager packageManager = Mockito.mock(PackageManager.class);

    @Test
    public void getAppMetaErrorTest() {
        try{
            okHiAppMeta = OkHiAppMeta.getAppMeta(context);
        } catch (Exception e){
            assertThat(e).isInstanceOf(OkHiException.class);
        }
    }

//    @Test
//    public void NameNotFoundExceptionTest() {
//
//        try{
//            packageInfo.versionName = version;
//            packageInfo.versionCode = 100;
//
//            Mockito.when(context.getPackageName()).thenReturn(name);
//            Mockito.when(context.getPackageManager()).thenReturn(packageManager);
//            Mockito.when(packageManager.getPackageInfo(context.getPackageName(), 0)).thenThrow(new RuntimeException("Could not get package name: "));
//
//            okHiAppMeta = OkHiAppMeta.getAppMeta(context);
//        } catch (Exception e){
//            assertThat(e).isInstanceOf(RuntimeException.class);
//        }
//    }

    @Test
    public void getAppMetaTest() throws OkHiException, PackageManager.NameNotFoundException {
        packageInfo.versionName = version;
        packageInfo.versionCode = 100;

        Mockito.when(context.getPackageName()).thenReturn(name);
        Mockito.when(context.getPackageManager()).thenReturn(packageManager);
        Mockito.when(packageManager.getPackageInfo(context.getPackageName(), 0)).thenReturn(packageInfo);

        okHiAppMeta = OkHiAppMeta.getAppMeta(context);
        assertThat(okHiAppMeta.getName()).isEqualTo(name);
        assertThat(okHiAppMeta.getVersion()).isEqualTo(version);
        assertThat(okHiAppMeta.getVersionCode()).isEqualTo(versionCode);

    }
}
