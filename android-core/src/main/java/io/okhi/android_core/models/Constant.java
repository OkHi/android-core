package io.okhi.android_core.models;

import java.util.concurrent.TimeUnit;

public class Constant {
    public static String API_VERSION = "v5";
    public static String DEV_BASE_URL = "https://dev-api.okhi.io/" + API_VERSION;
    public static String SANDBOX_BASE_URL = "https://sandbox-api.okhi.io/" + API_VERSION;
    public static String PROD_BASE_URL = "https://api.okhi.io/" + API_VERSION;
    public static String ANONYMOUS_SIGN_IN_ENDPOINT = "/auth/anonymous-signin";

    public static int TIME_OUT = 30;
    public static TimeUnit TIME_OUT_UNIT = TimeUnit.SECONDS;
    public static final int INVALID_PHONE_RESPONSE_CODE = 400;
    public static final int UNAUTHORIZED_RESPONSE_CODE = 401;
}
