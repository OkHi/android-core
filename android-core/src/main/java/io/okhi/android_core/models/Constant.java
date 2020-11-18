package io.okhi.android_core.models;

import java.util.concurrent.TimeUnit;

public class Constant {

    // request codes
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    public static final int ENABLE_LOCATION_SERVICES_REQUEST_CODE = 2;
    public static final int OPEN_LOCATION_SERVICES_SETTINGS_REQUEST_CODE = 3;
    public static final int ENABLE_GOOGLE_PLAY_SERVICES_REQUEST_CODE = 4;
    public static final int BACKGROUND_LOCATION_PERMISSION_REQUEST_CODE = 5;

    // timers and delays
    public static final long SERVICE_WAIT_DELAY = 2000;
    public static final long LOCATION_WAIT_DELAY = 20000;

    // dialogs
    public static final String PERMISSION_DIALOG_POSITIVE_BUTTON_TEXT = "GRANT";
    public static final String PERMISSION_DIALOG_NEGATIVE_BUTTON_TEXT = "CANCEL";

    public static String OKHI_DEV_MODE = "dev";
    private static String API_VERSION = "v5";
    public static String DEV_BASE_URL = "https://dev-api.okhi.io/" + API_VERSION;
    public static String SANDBOX_BASE_URL = "https://sandbox-api.okhi.io/" + API_VERSION;
    public static String PROD_BASE_URL = "https://api.okhi.io/" + API_VERSION;
    public static String ANONYMOUS_SIGN_IN_ENDPOINT = "/auth/anonymous-signin";

    public static int TIME_OUT = 30;
    public static TimeUnit TIME_OUT_UNIT = TimeUnit.SECONDS;
    public static final int INVALID_PHONE_RESPONSE_CODE = 400;
    public static final int UNAUTHORIZED_RESPONSE_CODE = 401;

    // location updates
    public static final long LOCATION_GPS_ACCURACY = 100;
    public static final long LOCATION_REQUEST_EXPIRATION_DURATION = 20000;
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 2000;
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;
}
