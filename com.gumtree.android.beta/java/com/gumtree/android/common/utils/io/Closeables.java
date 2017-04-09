package com.gumtree.android.common.utils.io;

import java.io.Closeable;
import java.io.IOException;

public final class Closeables {
    private Closeables() {
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
            }
        }
    }
}
