package com.afollestad.materialdialogs.util;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.util.SimpleArrayMap;

public class TypefaceHelper {
    private static final SimpleArrayMap<String, Typeface> cache = new SimpleArrayMap();

    public static Typeface get(Context context, String str) {
        Typeface typeface;
        synchronized (cache) {
            if (cache.containsKey(str)) {
                typeface = (Typeface) cache.get(str);
            } else {
                try {
                    typeface = Typeface.createFromAsset(context.getAssets(), String.format("fonts/%s", new Object[]{str}));
                    cache.put(str, typeface);
                } catch (RuntimeException e) {
                    typeface = null;
                }
            }
        }
        return typeface;
    }
}
