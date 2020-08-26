package io.okhi.android_core;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import io.okhi.android_core.interfaces.OkHiRequestHandler;
import io.okhi.android_core.interfaces.OkHiSignInRequestHandler;
import io.okhi.android_core.models.Constant;
import io.okhi.android_core.models.OkHiAuth;
import io.okhi.android_core.models.OkHiException;
import io.okhi.android_core.models.OkHiMode;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHiCore {
    private String BASE_URL;
    private final OkHiAuth auth;

    protected OkHiCore(@NonNull OkHiAuth auth) {
        this.auth = auth;
        if (auth.getContext().getMode().equals(Constant.OKHI_DEV_MODE)) {
            BASE_URL = Constant.DEV_BASE_URL;
        } else if (auth.getContext().getMode().equals(OkHiMode.PROD)) {
            BASE_URL = Constant.PROD_BASE_URL;
        } else {
            BASE_URL = Constant.SANDBOX_BASE_URL;
        }
    }

    protected void anonymousSignWithPhoneNumber(@NonNull String phone, @NonNull String[] scopes, @NonNull final OkHiRequestHandler<String> handler) {
        try {
            JSONObject payload = new JSONObject();
            payload.put("phone", phone);
            payload.put("scopes", new JSONArray(scopes));
            anonymousSign(payload, handler);
        } catch (Exception e) {
            handler.onError(new OkHiException(OkHiException.UNKNOWN_ERROR_CODE, OkHiException.UNKNOWN_ERROR_MESSAGE));
        }
    }

    protected void anonymousSignInWithUserId(@NonNull String userId, @NonNull String[] scopes, @NonNull final OkHiRequestHandler<String> handler) {
        try {
            JSONObject payload = new JSONObject();
            payload.put("user_id", userId);
            payload.put("scopes", new JSONArray(scopes));
            anonymousSign(payload, handler);
        } catch (Exception e) {
            handler.onError(new OkHiException(OkHiException.UNKNOWN_ERROR_CODE, OkHiException.UNKNOWN_ERROR_MESSAGE));
        }
    }

    private void anonymousSign(JSONObject payload, final OkHiRequestHandler<String> handler) {
        RequestBody body = RequestBody.create(payload.toString(), MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(BASE_URL + Constant.ANONYMOUS_SIGN_IN_ENDPOINT)
                .headers(getHeaders())
                .post(body)
                .build();
        getHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                handler.onError(new OkHiException(OkHiException.NETWORK_ERROR_CODE, OkHiException.NETWORK_ERROR_MESSAGE));
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                try {
                    if (response.isSuccessful()) {
                        JSONObject res = new JSONObject(Objects.requireNonNull(response.body()).string());
                        if (res.has("authorization_token")) {
                            handler.onResult(res.getString("authorization_token"));
                        } else {
                            throw new Exception();
                        }
                    } else if (response.code() == Constant.INVALID_PHONE_RESPONSE_CODE) {
                        handler.onError(new OkHiException(OkHiException.INVALID_PHONE_CODE, OkHiException.INVALID_PHONE_MESSAGE));
                    } else if (response.code() == Constant.UNAUTHORIZED_RESPONSE_CODE) {
                        handler.onError(new OkHiException(OkHiException.UNAUTHORIZED_CODE, OkHiException.UNAUTHORIZED_MESSAGE));
                    } else {
                        throw new Exception();
                    }
                } catch (Exception e) {
                    handler.onError(new OkHiException(OkHiException.UNKNOWN_ERROR_CODE, OkHiException.UNKNOWN_ERROR_MESSAGE));
                }
            }
        });
    }

    private OkHttpClient getHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(Constant.TIME_OUT, Constant.TIME_OUT_UNIT)
                .writeTimeout(Constant.TIME_OUT, Constant.TIME_OUT_UNIT)
                .readTimeout(Constant.TIME_OUT, Constant.TIME_OUT_UNIT)
                .build();
    }

    private Headers getHeaders() {
        Headers.Builder builder = new Headers.Builder();
        builder.add("Authorization", auth.getAccessToken());
        return builder.build();
    }
}
