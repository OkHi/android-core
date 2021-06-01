package io.okhi.android_core.models;

import android.content.Context;

import java.util.HashMap;

import io.sentry.Sentry;
import io.sentry.SentryOptions;
import io.sentry.android.core.SentryAndroid;
import io.sentry.android.core.SentryAndroidOptions;
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
        sentryUserDetails.put("firstName", user.getFirstName());
        sentryUserDetails.put("lastName", user.getLastName());
        sentryUserDetails.put("userId", user.getId());
        User sentryUser = new User();
        sentryUser.setUsername(user.getPhone());
        sentryUser.setId(user.getId());
        sentryUser.setOthers(sentryUserDetails);
        Sentry.setUser(sentryUser);
    }

    public static void setUserException(String phone) {
        HashMap<String, String> sentryUserDetails = new HashMap<>();
        sentryUserDetails.put("phone", phone);
        User sentryUser = new User();
        sentryUser.setOthers(sentryUserDetails);
        sentryUser.setUsername(phone);
        Sentry.setUser(sentryUser);
    }

    public static void setUserException(String userId, String phone) {
        HashMap<String, String> sentryUserDetails = new HashMap<>();
        sentryUserDetails.put("userId", userId);
        sentryUserDetails.put("phone", phone);
        User sentryUser = new User();
        sentryUser.setOthers(sentryUserDetails);
        sentryUser.setId(userId);
        sentryUser.setUsername(phone);
        Sentry.setUser(sentryUser);
    }

    public static void captureException(OkHiException exception) {
        String message = exception.getMessage();
        exception.setMessage(exception.getCode());
        Sentry.setExtra("message", message);
        Sentry.captureException(exception);
    }

    public static void captureException(Exception exception) {
        Sentry.setExtra("message", exception.getMessage());
        Sentry.captureException(exception);
    }

    public static void setEnvironment(Context context, final String env) {
        SentryAndroid.init(context, new Sentry.OptionsConfiguration<SentryAndroidOptions>() {
            @Override
            public void configure(SentryAndroidOptions options) {
                options.setEnvironment(env);
            }
        });
    }

    public static void initErrorTracking(Context context) {
        SentryAndroid.init(context, new Sentry.OptionsConfiguration<SentryAndroidOptions>() {
            @Override
            public void configure(SentryAndroidOptions options) {
                options.setDsn("https://examplePublicKey@o0.ingest.sentry.io/0");
            }
        });
    }
}
