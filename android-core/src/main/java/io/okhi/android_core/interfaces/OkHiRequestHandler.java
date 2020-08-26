package io.okhi.android_core.interfaces;

import io.okhi.android_core.models.OkHiException;

public interface OkHiRequestHandler<T> {
    void onResult(T result);

    void onError(OkHiException exception);
}
