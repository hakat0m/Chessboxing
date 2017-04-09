package com.gumtree.android.post_ad.gallery.utils;

import java.io.File;

public interface ImageStorage {
    void clear();

    File getNewTempFile();
}
