package io.okhi.android_core;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import io.okhi.android_core.models.Constant;
import io.okhi.android_core.models.OkHiCoreUtil;
import io.okhi.android_core.models.OkHiException;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;

public class OkHiCoreUtilTest {
    Response response;
    Response.Builder builder;

    Request request = Mockito.mock(Request.class);
    Protocol protocol = Mockito.mock(Protocol.class);

    @Before
    public void setup_test(){
        builder = new  Response.Builder();
        builder.request(request);
        builder.protocol(protocol);
        builder.message("Test message");
    }

    @Test
    public void INVALID_PHONE_RESPONSE_Test(){
        builder.code(Constant.INVALID_PHONE_RESPONSE_CODE);
        response = builder.build();
        OkHiException okHiException = OkHiCoreUtil.generateOkHiException(response);

        assertThat(okHiException.getMessage()).isEqualTo(OkHiException.INVALID_PHONE_MESSAGE);
    }

    @Test
    public void UNAUTHORIZED_RESPONSE_Test(){
        builder.code(Constant.UNAUTHORIZED_RESPONSE_CODE);
        response = builder.build();
        OkHiException okHiException = OkHiCoreUtil.generateOkHiException(response);

        assertThat(okHiException.getMessage()).isEqualTo(OkHiException.UNAUTHORIZED_MESSAGE);
    }

    @Test
    public void UNKNOWN_ERROR_RESPONSE_Test(){
        builder.code(201);
        response = builder.build();
        OkHiException okHiException = OkHiCoreUtil.generateOkHiException(response);

        assertThat(okHiException.getMessage()).isEqualTo(OkHiException.UNKNOWN_ERROR_MESSAGE);
    }

}
