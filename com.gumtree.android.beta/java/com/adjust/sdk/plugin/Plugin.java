package com.adjust.sdk.plugin;

import android.content.Context;
import java.util.Map.Entry;

public interface Plugin {
    Entry<String, String> getParameter(Context context);
}
