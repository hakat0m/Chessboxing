package com.adjust.sdk;

import android.content.Context;
import com.adjust.sdk.plugin.Plugin;
import com.gumtree.android.model.PostAdsImages;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Reflection {
    public static String getPlayAdId(Context context) {
        try {
            return (String) invokeInstanceMethod(getAdvertisingInfoObject(context), "getId", null, new Object[0]);
        } catch (Throwable th) {
            return null;
        }
    }

    public static Boolean isPlayTrackingEnabled(Context context) {
        try {
            Boolean bool = (Boolean) invokeInstanceMethod(getAdvertisingInfoObject(context), "isLimitAdTrackingEnabled", null, new Object[0]);
            if (bool == null) {
                return null;
            }
            return Boolean.valueOf(!bool.booleanValue());
        } catch (Throwable th) {
            return null;
        }
    }

    public static String getMacAddress(Context context) {
        try {
            return (String) invokeStaticMethod("com.adjust.sdk.plugin.MacAddressUtil", "getMacAddress", new Class[]{Context.class}, context);
        } catch (Throwable th) {
            return null;
        }
    }

    public static String getAndroidId(Context context) {
        try {
            return (String) invokeStaticMethod("com.adjust.sdk.plugin.AndroidIdUtil", "getAndroidId", new Class[]{Context.class}, context);
        } catch (Throwable th) {
            return null;
        }
    }

    private static Object getAdvertisingInfoObject(Context context) throws Exception {
        return invokeStaticMethod("com.google.android.gms.ads.identifier.AdvertisingIdClient", "getAdvertisingIdInfo", new Class[]{Context.class}, context);
    }

    private static boolean isConnectionResultSuccess(Integer num) {
        if (num == null) {
            return false;
        }
        try {
            if (Class.forName("com.google.android.gms.common.ConnectionResult").getField(PostAdsImages.SUCCESS).getInt(null) == num.intValue()) {
                return true;
            }
            return false;
        } catch (Throwable th) {
            return false;
        }
    }

    public static String[] getSupportedAbis() {
        try {
            Object obj = forName("android.os.Build").getField("SUPPORTED_ABIS").get(null);
            if (obj instanceof String[]) {
                return (String[]) obj;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public static String getCpuAbi() {
        try {
            Object obj = forName("android.os.Build").getField("CPU_ABI").get(null);
            if (obj instanceof String) {
                return (String) obj;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public static Class forName(String str) {
        try {
            return Class.forName(str);
        } catch (Throwable th) {
            return null;
        }
    }

    public static Object createDefaultInstance(String str) {
        return createDefaultInstance(forName(str));
    }

    public static Object createDefaultInstance(Class cls) {
        try {
            return cls.newInstance();
        } catch (Throwable th) {
            return null;
        }
    }

    public static Object createInstance(String str, Class[] clsArr, Object... objArr) {
        try {
            return Class.forName(str).getConstructor(clsArr).newInstance(objArr);
        } catch (Throwable th) {
            return null;
        }
    }

    public static Object invokeStaticMethod(String str, String str2, Class[] clsArr, Object... objArr) throws Exception {
        return invokeMethod(Class.forName(str), str2, null, clsArr, objArr);
    }

    public static Object invokeInstanceMethod(Object obj, String str, Class[] clsArr, Object... objArr) throws Exception {
        return invokeMethod(obj.getClass(), str, obj, clsArr, objArr);
    }

    public static Object invokeMethod(Class cls, String str, Object obj, Class[] clsArr, Object... objArr) throws Exception {
        return cls.getMethod(str, clsArr).invoke(obj, objArr);
    }

    public static Map<String, String> getPluginKeys(Context context) {
        Map<String, String> hashMap = new HashMap();
        for (Plugin parameter : getPlugins()) {
            Entry parameter2 = parameter.getParameter(context);
            if (parameter2 != null) {
                hashMap.put(parameter2.getKey(), parameter2.getValue());
            }
        }
        if (hashMap.size() == 0) {
            return null;
        }
        return hashMap;
    }

    private static List<Plugin> getPlugins() {
        List<Plugin> arrayList = new ArrayList(Constants.PLUGINS.size());
        for (String createDefaultInstance : Constants.PLUGINS) {
            Object createDefaultInstance2 = createDefaultInstance(createDefaultInstance);
            if (createDefaultInstance2 != null && (createDefaultInstance2 instanceof Plugin)) {
                arrayList.add((Plugin) createDefaultInstance2);
            }
        }
        return arrayList;
    }
}
