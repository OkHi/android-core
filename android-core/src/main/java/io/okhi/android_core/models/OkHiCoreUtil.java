package io.okhi.android_core.models;

import okhttp3.Response;

public class OkHiCoreUtil {
    public static OkHiException generateOkHiException(Response response){
        if (response.code() == Constant.INVALID_PHONE_RESPONSE_CODE) {
            return new OkHiException(OkHiException.INVALID_PHONE_CODE, OkHiException.INVALID_PHONE_MESSAGE);
        } else if (response.code() == Constant.UNAUTHORIZED_RESPONSE_CODE) {
            return new OkHiException(OkHiException.UNAUTHORIZED_CODE, OkHiException.UNAUTHORIZED_MESSAGE);
        } else {
            return new OkHiException(OkHiException.UNKNOWN_ERROR_CODE, OkHiException.UNKNOWN_ERROR_MESSAGE);
        }
    }
}
