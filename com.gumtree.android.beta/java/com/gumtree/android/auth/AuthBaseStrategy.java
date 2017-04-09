package com.gumtree.android.auth;

import android.app.Activity;

public interface AuthBaseStrategy {
    boolean renewToken(int i, Activity activity);
}
