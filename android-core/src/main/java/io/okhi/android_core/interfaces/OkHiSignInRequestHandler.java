package io.okhi.android_core.interfaces;

import io.okhi.android_core.models.OkHiException;

public interface OkHiSignInRequestHandler {
    void onSuccess(String authorizationToken);
    void onError(OkHiException exception);
}
