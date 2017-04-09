package com.facebook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import com.facebook.internal.AttributionIdentifiers;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphObject.Factory;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AppLinkData {
    private static final String APPLINK_BRIDGE_ARGS_KEY = "bridge_args";
    private static final String APPLINK_METHOD_ARGS_KEY = "method_args";
    private static final String APPLINK_VERSION_KEY = "version";
    public static final String ARGUMENTS_NATIVE_CLASS_KEY = "com.facebook.platform.APPLINK_NATIVE_CLASS";
    public static final String ARGUMENTS_NATIVE_URL = "com.facebook.platform.APPLINK_NATIVE_URL";
    public static final String ARGUMENTS_REFERER_DATA_KEY = "referer_data";
    public static final String ARGUMENTS_TAPTIME_KEY = "com.facebook.platform.APPLINK_TAP_TIME_UTC";
    private static final String BRIDGE_ARGS_METHOD_KEY = "method";
    private static final String BUNDLE_AL_APPLINK_DATA_KEY = "al_applink_data";
    static final String BUNDLE_APPLINK_ARGS_KEY = "com.facebook.platform.APPLINK_ARGS";
    private static final String DEFERRED_APP_LINK_ARGS_FIELD = "applink_args";
    private static final String DEFERRED_APP_LINK_CLASS_FIELD = "applink_class";
    private static final String DEFERRED_APP_LINK_CLICK_TIME_FIELD = "click_time";
    private static final String DEFERRED_APP_LINK_EVENT = "DEFERRED_APP_LINK";
    private static final String DEFERRED_APP_LINK_PATH = "%s/activities";
    private static final String DEFERRED_APP_LINK_URL_FIELD = "applink_url";
    private static final String METHOD_ARGS_REF_KEY = "ref";
    private static final String METHOD_ARGS_TARGET_URL_KEY = "target_url";
    private static final String REFERER_DATA_REF_KEY = "fb_ref";
    private static final String TAG = AppLinkData.class.getCanonicalName();
    private Bundle argumentBundle;
    private JSONObject arguments;
    private String ref;
    private Uri targetUri;

    public static void fetchDeferredAppLinkData(Context context, CompletionHandler completionHandler) {
        fetchDeferredAppLinkData(context, null, completionHandler);
    }

    public static void fetchDeferredAppLinkData(Context context, String str, CompletionHandler completionHandler) {
        Validate.notNull(context, "context");
        Validate.notNull(completionHandler, "completionHandler");
        if (str == null) {
            str = Utility.getMetadataApplicationId(context);
        }
        Validate.notNull(str, "applicationId");
        Settings.getExecutor().execute(new 1(context.getApplicationContext(), str, completionHandler));
    }

    private static void fetchDeferredAppLinkFromServer(Context context, String str, CompletionHandler completionHandler) {
        AppLinkData appLinkData = null;
        GraphObject create = Factory.create();
        create.setProperty("event", DEFERRED_APP_LINK_EVENT);
        Utility.setAppEventAttributionParameters(create, AttributionIdentifiers.getAttributionIdentifiers(context), AppEventsLogger.getAnonymousAppDeviceGUID(context), Settings.getLimitEventAndDataUsage(context));
        create.setProperty("application_package_name", context.getPackageName());
        try {
            JSONObject innerJSONObject;
            create = Request.newPostRequest(null, String.format(DEFERRED_APP_LINK_PATH, new Object[]{str}), create, null).executeAndWait().getGraphObject();
            if (create != null) {
                innerJSONObject = create.getInnerJSONObject();
            } else {
                Object obj = appLinkData;
            }
            if (innerJSONObject != null) {
                Object optString = innerJSONObject.optString(DEFERRED_APP_LINK_ARGS_FIELD);
                long optLong = innerJSONObject.optLong(DEFERRED_APP_LINK_CLICK_TIME_FIELD, -1);
                String optString2 = innerJSONObject.optString(DEFERRED_APP_LINK_CLASS_FIELD);
                String optString3 = innerJSONObject.optString(DEFERRED_APP_LINK_URL_FIELD);
                if (!TextUtils.isEmpty(optString)) {
                    appLinkData = createFromJson(optString);
                    if (optLong != -1) {
                        try {
                            if (appLinkData.arguments != null) {
                                appLinkData.arguments.put(ARGUMENTS_TAPTIME_KEY, optLong);
                            }
                            if (appLinkData.argumentBundle != null) {
                                appLinkData.argumentBundle.putString(ARGUMENTS_TAPTIME_KEY, Long.toString(optLong));
                            }
                        } catch (JSONException e) {
                            Log.d(TAG, "Unable to put tap time in AppLinkData.arguments");
                        }
                    }
                    if (optString2 != null) {
                        try {
                            if (appLinkData.arguments != null) {
                                appLinkData.arguments.put(ARGUMENTS_NATIVE_CLASS_KEY, optString2);
                            }
                            if (appLinkData.argumentBundle != null) {
                                appLinkData.argumentBundle.putString(ARGUMENTS_NATIVE_CLASS_KEY, optString2);
                            }
                        } catch (JSONException e2) {
                            Log.d(TAG, "Unable to put tap time in AppLinkData.arguments");
                        }
                    }
                    if (optString3 != null) {
                        try {
                            if (appLinkData.arguments != null) {
                                appLinkData.arguments.put(ARGUMENTS_NATIVE_URL, optString3);
                            }
                            if (appLinkData.argumentBundle != null) {
                                appLinkData.argumentBundle.putString(ARGUMENTS_NATIVE_URL, optString3);
                            }
                        } catch (JSONException e3) {
                            Log.d(TAG, "Unable to put tap time in AppLinkData.arguments");
                        }
                    }
                }
            }
        } catch (Exception e4) {
            Utility.logd(TAG, "Unable to fetch deferred applink from server");
        }
        completionHandler.onDeferredAppLinkDataFetched(appLinkData);
    }

    public static AppLinkData createFromActivity(Activity activity) {
        Validate.notNull(activity, "activity");
        Intent intent = activity.getIntent();
        if (intent == null) {
            return null;
        }
        AppLinkData createFromAlApplinkData = createFromAlApplinkData(intent);
        if (createFromAlApplinkData == null) {
            createFromAlApplinkData = createFromJson(intent.getStringExtra(BUNDLE_APPLINK_ARGS_KEY));
        }
        if (createFromAlApplinkData == null) {
            return createFromUri(intent.getData());
        }
        return createFromAlApplinkData;
    }

    private static AppLinkData createFromAlApplinkData(Intent intent) {
        Bundle bundleExtra = intent.getBundleExtra(BUNDLE_AL_APPLINK_DATA_KEY);
        if (bundleExtra == null) {
            return null;
        }
        AppLinkData appLinkData = new AppLinkData();
        appLinkData.targetUri = intent.getData();
        if (appLinkData.targetUri == null) {
            String string = bundleExtra.getString(METHOD_ARGS_TARGET_URL_KEY);
            if (string != null) {
                appLinkData.targetUri = Uri.parse(string);
            }
        }
        appLinkData.argumentBundle = bundleExtra;
        appLinkData.arguments = null;
        Bundle bundle = bundleExtra.getBundle(ARGUMENTS_REFERER_DATA_KEY);
        if (bundle != null) {
            appLinkData.ref = bundle.getString(REFERER_DATA_REF_KEY);
        }
        return appLinkData;
    }

    private static AppLinkData createFromJson(String str) {
        if (str == null) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            String string = jSONObject.getString(APPLINK_VERSION_KEY);
            if (!jSONObject.getJSONObject(APPLINK_BRIDGE_ARGS_KEY).getString(BRIDGE_ARGS_METHOD_KEY).equals("applink") || !string.equals("2")) {
                return null;
            }
            AppLinkData appLinkData = new AppLinkData();
            appLinkData.arguments = jSONObject.getJSONObject(APPLINK_METHOD_ARGS_KEY);
            if (appLinkData.arguments.has(METHOD_ARGS_REF_KEY)) {
                appLinkData.ref = appLinkData.arguments.getString(METHOD_ARGS_REF_KEY);
            } else if (appLinkData.arguments.has(ARGUMENTS_REFERER_DATA_KEY)) {
                jSONObject = appLinkData.arguments.getJSONObject(ARGUMENTS_REFERER_DATA_KEY);
                if (jSONObject.has(REFERER_DATA_REF_KEY)) {
                    appLinkData.ref = jSONObject.getString(REFERER_DATA_REF_KEY);
                }
            }
            if (appLinkData.arguments.has(METHOD_ARGS_TARGET_URL_KEY)) {
                appLinkData.targetUri = Uri.parse(appLinkData.arguments.getString(METHOD_ARGS_TARGET_URL_KEY));
            }
            appLinkData.argumentBundle = toBundle(appLinkData.arguments);
            return appLinkData;
        } catch (Throwable e) {
            Log.d(TAG, "Unable to parse AppLink JSON", e);
            return null;
        } catch (FacebookException e2) {
            Log.d(TAG, "Unable to parse AppLink JSON", e2);
            return null;
        }
    }

    private static AppLinkData createFromUri(Uri uri) {
        if (uri == null) {
            return null;
        }
        AppLinkData appLinkData = new AppLinkData();
        appLinkData.targetUri = uri;
        return appLinkData;
    }

    private static Bundle toBundle(JSONObject jSONObject) throws JSONException {
        Bundle bundle = new Bundle();
        Iterator keys = jSONObject.keys();
        while (keys.hasNext()) {
            String str = (String) keys.next();
            Object obj = jSONObject.get(str);
            if (obj instanceof JSONObject) {
                bundle.putBundle(str, toBundle((JSONObject) obj));
            } else if (obj instanceof JSONArray) {
                JSONArray jSONArray = (JSONArray) obj;
                if (jSONArray.length() == 0) {
                    bundle.putStringArray(str, new String[0]);
                } else {
                    Object obj2 = jSONArray.get(0);
                    int i;
                    if (obj2 instanceof JSONObject) {
                        Parcelable[] parcelableArr = new Bundle[jSONArray.length()];
                        for (i = 0; i < jSONArray.length(); i++) {
                            parcelableArr[i] = toBundle(jSONArray.getJSONObject(i));
                        }
                        bundle.putParcelableArray(str, parcelableArr);
                    } else if (obj2 instanceof JSONArray) {
                        throw new FacebookException("Nested arrays are not supported.");
                    } else {
                        String[] strArr = new String[jSONArray.length()];
                        for (i = 0; i < jSONArray.length(); i++) {
                            strArr[i] = jSONArray.get(i).toString();
                        }
                        bundle.putStringArray(str, strArr);
                    }
                }
            } else {
                bundle.putString(str, obj.toString());
            }
        }
        return bundle;
    }

    private AppLinkData() {
    }

    public Uri getTargetUri() {
        return this.targetUri;
    }

    public String getRef() {
        return this.ref;
    }

    @Deprecated
    public JSONObject getArguments() {
        return this.arguments;
    }

    public Bundle getArgumentBundle() {
        return this.argumentBundle;
    }

    public Bundle getRefererData() {
        if (this.argumentBundle != null) {
            return this.argumentBundle.getBundle(ARGUMENTS_REFERER_DATA_KEY);
        }
        return null;
    }
}
