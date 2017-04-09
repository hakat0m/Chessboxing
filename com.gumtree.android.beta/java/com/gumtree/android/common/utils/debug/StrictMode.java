package com.gumtree.android.common.utils.debug;

import com.gumtree.android.common.utils.Log;
import java.lang.reflect.Method;

public class StrictMode {
    private static final String BUILD = "build";
    private static final String BUILDER = "android.os.StrictMode$ThreadPolicy$Builder";
    private static final String DETECT_ALL = "detectAll";
    private static final String PENALTY_LOG = "penaltyLog";
    private static final String SET_THREAD_POLICY = "setThreadPolicy";
    private static final String STRICT_MODE = "android.os.StrictMode";
    private static final String THREAD_POLICY = "android.os.StrictMode$ThreadPolicy";

    public static void enable() {
        try {
            Class cls = Class.forName(STRICT_MODE, true, Thread.currentThread().getContextClassLoader());
            Class cls2 = Class.forName(THREAD_POLICY, true, Thread.currentThread().getContextClassLoader());
            Class cls3 = Class.forName(BUILDER, true, Thread.currentThread().getContextClassLoader());
            Method method = cls.getMethod(SET_THREAD_POLICY, new Class[]{cls2});
            Method method2 = cls3.getMethod(DETECT_ALL, new Class[0]);
            Object invoke = cls3.getMethod(BUILD, new Class[0]).invoke(cls3.getMethod(PENALTY_LOG, new Class[0]).invoke(method2.invoke(cls3.getConstructor(new Class[0]).newInstance(new Object[0]), new Object[0]), new Object[0]), new Object[0]);
            method.invoke(cls, new Object[]{invoke});
        } catch (Exception e) {
            Log.v("StrictMode", "Strict mode not available.");
        }
    }
}
