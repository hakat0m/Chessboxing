package com.gumtree.android.common.gson;

import java.io.InputStream;

public interface Parser<T> {
    T parse(InputStream inputStream);
}
