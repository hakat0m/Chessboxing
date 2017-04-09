package com.facebook.android;

import android.content.Context;
import android.os.Bundle;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

@Deprecated
public class AsyncFacebookRunner {
    Facebook fb;

    @Deprecated
    public interface RequestListener {
        void onComplete(String str, Object obj);

        void onFacebookError(FacebookError facebookError, Object obj);

        void onFileNotFoundException(FileNotFoundException fileNotFoundException, Object obj);

        void onIOException(IOException iOException, Object obj);

        void onMalformedURLException(MalformedURLException malformedURLException, Object obj);
    }

    public AsyncFacebookRunner(Facebook facebook) {
        this.fb = facebook;
    }

    @Deprecated
    public void logout(Context context, RequestListener requestListener, Object obj) {
        new 1(this, context, requestListener, obj).start();
    }

    @Deprecated
    public void logout(Context context, RequestListener requestListener) {
        logout(context, requestListener, null);
    }

    @Deprecated
    public void request(Bundle bundle, RequestListener requestListener, Object obj) {
        request(null, bundle, "GET", requestListener, obj);
    }

    @Deprecated
    public void request(Bundle bundle, RequestListener requestListener) {
        request(null, bundle, "GET", requestListener, null);
    }

    @Deprecated
    public void request(String str, RequestListener requestListener, Object obj) {
        request(str, new Bundle(), "GET", requestListener, obj);
    }

    @Deprecated
    public void request(String str, RequestListener requestListener) {
        request(str, new Bundle(), "GET", requestListener, null);
    }

    @Deprecated
    public void request(String str, Bundle bundle, RequestListener requestListener, Object obj) {
        request(str, bundle, "GET", requestListener, obj);
    }

    @Deprecated
    public void request(String str, Bundle bundle, RequestListener requestListener) {
        request(str, bundle, "GET", requestListener, null);
    }

    @Deprecated
    public void request(String str, Bundle bundle, String str2, RequestListener requestListener, Object obj) {
        new 2(this, str, bundle, str2, requestListener, obj).start();
    }
}
