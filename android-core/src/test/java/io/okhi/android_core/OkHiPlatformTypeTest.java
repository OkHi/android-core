package io.okhi.android_core;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

import io.okhi.android_core.models.OkHiPlatformType;

public class OkHiPlatformTypeTest {


    @Test
    public void test_platform(){
        assertThat(OkHiPlatformType.ANDROID).isEqualTo("android");
        // is of type String
        assertThat(OkHiPlatformType.ANDROID).isInstanceOf(String.class);
    }
}
