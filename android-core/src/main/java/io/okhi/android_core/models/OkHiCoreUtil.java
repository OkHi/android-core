package io.okhi.android_core.models;

import java.util.HashMap;

import io.sentry.Sentry;
import io.sentry.protocol.User;
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

    public static void setUserException(OkHiUser user) {
        HashMap<String, String> sentryUserDetails = new HashMap<>();
        sentryUserDetails.put("phone", user.getPhone());
        User sentryUser = new User();
        sentryUser.setUsername(user.getFirstName() + " " + user.getLastName());
        sentryUser.setId(user.getId());
        sentryUser.setOthers(sentryUserDetails);
        Sentry.setUser(sentryUser);
    }

    public static void setUserException(String phone) {
        HashMap<String, String> sentryUserDetails = new HashMap<>();
        sentryUserDetails.put("phone", phone);
        User sentryUser = new User();
        sentryUser.setOthers(sentryUserDetails);
        Sentry.setUser(sentryUser);
    }

    public static void setUserException(String userId, String phone) {
        HashMap<String, String> sentryUserDetails = new HashMap<>();
        sentryUserDetails.put("userId", userId);
        sentryUserDetails.put("phone", phone);
        User sentryUser = new User();
        sentryUser.setOthers(sentryUserDetails);
        sentryUser.setId(userId);
        Sentry.setUser(sentryUser);
    }

    public static void captureException(OkHiException exception) {
        Sentry.captureException(exception);
    }

    public static void captureException(Exception exception) {
        Sentry.captureException(exception);
    }
}
