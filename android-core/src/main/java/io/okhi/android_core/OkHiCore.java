package io.okhi.android_core;

import android.content.Context;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;

import io.okhi.android_core.interfaces.OkHiRequestHandler;
import io.okhi.android_core.models.Constant;
import io.okhi.android_core.models.OkHiAuth;
import io.okhi.android_core.models.OkHiException;
import io.okhi.android_core.models.OkHiMode;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.TlsVersion;

import static io.okhi.android_core.models.OkHiCoreUtil.generateOkHiException;

public class OkHiCore {
    private String BASE_URL;
    protected OkHiAuth auth;

    public OkHiCore(@NonNull OkHiAuth auth) {
        this.auth = auth;
        if (auth.getContext().getMode().equals(Constant.OKHI_DEV_MODE)) {
            BASE_URL = Constant.DEV_BASE_URL;
        } else if (auth.getContext().getMode().equals(OkHiMode.PROD)) {
            BASE_URL = Constant.PROD_BASE_URL;
        } else {
            BASE_URL = Constant.SANDBOX_BASE_URL;
        }
    }
    
    public OkHiCore(@NonNull Context context) throws OkHiException {
        this.auth = new OkHiAuth.Builder(context).build();
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
            @NonNull
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
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), payload.toString());
        Request request = new Request.Builder()
                .url(BASE_URL + Constant.ANONYMOUS_SIGN_IN_ENDPOINT)
                .headers(getHeaders())
                .post(body)
                .build();
        getHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.onError(new OkHiException(OkHiException.NETWORK_ERROR_CODE, OkHiException.NETWORK_ERROR_MESSAGE));
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    if (response.isSuccessful()) {
                        JSONObject res = new JSONObject(Objects.requireNonNull(response.body()).string());
                        if (res.has("authorization_token")) {
                            handler.onResult(res.getString("authorization_token"));
                        } else {
                            handler.onError(new OkHiException(OkHiException.UNKNOWN_ERROR_CODE, OkHiException.UNKNOWN_ERROR_MESSAGE));
                        }
                    } else {
                        handler.onError(generateOkHiException(response));
                    }
                } catch (Exception e) {
                    handler.onError(new OkHiException(OkHiException.UNKNOWN_ERROR_CODE, OkHiException.UNKNOWN_ERROR_MESSAGE));
                }
            }
        });
    }



    private OkHttpClient getHttpClient() {
        ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.COMPATIBLE_TLS)
                .supportsTlsExtensions(true)
                .tlsVersions(TlsVersion.TLS_1_2, TlsVersion.TLS_1_1, TlsVersion.TLS_1_0)
                .cipherSuites(
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA,
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA,
                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA,
                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA,
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_RC4_128_SHA,
                        CipherSuite.TLS_ECDHE_RSA_WITH_RC4_128_SHA,
                        CipherSuite.TLS_DHE_RSA_WITH_AES_128_CBC_SHA,
                        CipherSuite.TLS_DHE_DSS_WITH_AES_128_CBC_SHA,
                        CipherSuite.TLS_DHE_RSA_WITH_AES_256_CBC_SHA)
                .build();
        return new OkHttpClient.Builder()
                .connectionSpecs(Collections.singletonList(spec))
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
