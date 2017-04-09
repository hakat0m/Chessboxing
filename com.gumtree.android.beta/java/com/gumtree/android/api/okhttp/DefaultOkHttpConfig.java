package com.gumtree.android.api.okhttp;

public class DefaultOkHttpConfig implements OkHttpConfig {
    private static final long CONNECTION_TIMEOUT = 30;
    private static final long READ_TIMEOUT = 30;
    private static final long WRITE_TIMEOUT = 30;

    public long getConnectTimeout() {
        return WRITE_TIMEOUT;
    }

    public long getReadTimeout() {
        return WRITE_TIMEOUT;
    }

    public long getWriteTimeout() {
        return WRITE_TIMEOUT;
    }
}
