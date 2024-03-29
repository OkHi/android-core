package io.okhi.android_core.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class OkHiException extends Exception {

  public static final String NETWORK_ERROR_CODE = "network_error";
  public static final String NETWORK_ERROR_MESSAGE = "Unable to establish a connection with OkHi servers";
  public static final String UNKNOWN_ERROR_CODE = "unknown_error";
  public static final String UNKNOWN_ERROR_MESSAGE = "Unable to process the request. Something went wrong";
  public static final String INVALID_PHONE_CODE = "invalid_phone";
  public static final String INVALID_PHONE_MESSAGE = "Invalid phone number provided. Please make sure its in MSISDN standard format";
  public static final String UNAUTHORIZED_CODE = "unauthorized";
  public static final String UNAUTHORIZED_MESSAGE = "Invalid credentials provided";
  public static final String PERMISSION_DENIED_CODE = "permission_denied";
  public static final String SERVICE_UNAVAILABLE_CODE = "service_unavailable";
  public static final String UNSUPPORTED_DEVICE = "unsupported_device";

  public static final String GOOGLE_PLAY_SERVICES_UNAVAILABLE = "google_play_services_unavailable";

  public static final String LOCATION_PERMISSION_DENIED = "location_permission_denied";

  public static final String BACKGROUND_LOCATION_PERMISSION_DENIED = "background_location_permission_denied";

  public static final String LOCATION_SERVICES_UNAVAILABLE = "background_location_permission_denied";
  private final String code;
  private final String message;

  public OkHiException(@NonNull String code, @NonNull String message) {
    super(message);
    this.code = code;
    this.message = message;
  }

  @Nullable
  @Override
  public String getMessage() {
    return message;
  }

  public String getCode() {
    return code;
  }
}
