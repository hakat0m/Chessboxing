package com.gumtree.android.common.providers;

import android.net.Uri;

public interface QueryHelper {
    String[] getProjections();

    String getSortOrder();

    Uri getUri();

    String[] getWhereParams(String... strArr);

    String getWhereQuery();
}
