package io.okhi.android_core;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Before;
import org.junit.Test;

import io.okhi.android_core.models.OkHiException;

public class OkHiExceptionTest {

    OkHiException exception;
    String code = "400";

    @Before
    public void setup_exception(){
        exception = new OkHiException(
                code,
                OkHiException.UNKNOWN_ERROR_MESSAGE
        );
    }

    @Test
    public void get_message_test(){
        String message = exception.getMessage();
        assertThat(message).isEqualTo(OkHiException.UNKNOWN_ERROR_MESSAGE);
    }

    @Test
    public void get_code_test(){
        String exception_code = exception.getCode();
        assertThat(exception_code).isEqualTo(code);
    }

    @Test
    public void is_instance_of_exception(){
        assertThat(exception).isInstanceOf(Exception.class);
    }
}
