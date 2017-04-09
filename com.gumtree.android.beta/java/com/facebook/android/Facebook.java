package com.facebook.android;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.Session.Builder;
import com.facebook.Session.OpenRequest;
import com.facebook.SessionLoginBehavior;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.facebook.TokenCachingStrategy;
import com.gumtree.android.postad.DraftAd;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Facebook {
    @Deprecated
    public static final String ATTRIBUTION_ID_COLUMN_NAME = "aid";
    @Deprecated
    public static final Uri ATTRIBUTION_ID_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.AttributionIdProvider");
    @Deprecated
    public static final String CANCEL_URI = "fbconnect://cancel";
    private static final int DEFAULT_AUTH_ACTIVITY_CODE = 32665;
    @Deprecated
    protected static String DIALOG_BASE_URL = "https://m.facebook.com/dialog/";
    @Deprecated
    public static final String EXPIRES = "expires_in";
    @Deprecated
    public static final String FB_APP_SIGNATURE = "30820268308201d102044a9c4610300d06092a864886f70d0101040500307a310b3009060355040613025553310b3009060355040813024341311230100603550407130950616c6f20416c746f31183016060355040a130f46616365626f6f6b204d6f62696c653111300f060355040b130846616365626f6f6b311d301b0603550403131446616365626f6f6b20436f72706f726174696f6e3020170d3039303833313231353231365a180f32303530303932353231353231365a307a310b3009060355040613025553310b3009060355040813024341311230100603550407130950616c6f20416c746f31183016060355040a130f46616365626f6f6b204d6f62696c653111300f060355040b130846616365626f6f6b311d301b0603550403131446616365626f6f6b20436f72706f726174696f6e30819f300d06092a864886f70d010101050003818d0030818902818100c207d51df8eb8c97d93ba0c8c1002c928fab00dc1b42fca5e66e99cc3023ed2d214d822bc59e8e35ddcf5f44c7ae8ade50d7e0c434f500e6c131f4a2834f987fc46406115de2018ebbb0d5a3c261bd97581ccfef76afc7135a6d59e8855ecd7eacc8f8737e794c60a761c536b72b11fac8e603f5da1a2d54aa103b8a13c0dbc10203010001300d06092a864886f70d0101040500038181005ee9be8bcbb250648d3b741290a82a1c9dc2e76a0af2f2228f1d9f9c4007529c446a70175c5a900d5141812866db46be6559e2141616483998211f4a673149fb2232a10d247663b26a9031e15f84bc1c74d141ff98a02d76f85b2c8ab2571b6469b232d8e768a7f7ca04f7abe4a775615916c07940656b58717457b42bd928a2";
    @Deprecated
    public static final int FORCE_DIALOG_AUTH = -1;
    @Deprecated
    protected static String GRAPH_BASE_URL = "https://graph.facebook.com/";
    private static final String LOGIN = "oauth";
    @Deprecated
    public static final String REDIRECT_URI = "fbconnect://success";
    private static final long REFRESH_TOKEN_BARRIER = 86400000;
    @Deprecated
    protected static String RESTSERVER_URL = "https://api.facebook.com/restserver.php";
    @Deprecated
    public static final String SINGLE_SIGN_ON_DISABLED = "service_disabled";
    @Deprecated
    public static final String TOKEN = "access_token";
    private long accessExpiresMillisecondsAfterEpoch = 0;
    private String accessToken = null;
    private long lastAccessUpdateMillisecondsAfterEpoch = 0;
    private final Object lock = new Object();
    private String mAppId;
    private Activity pendingAuthorizationActivity;
    private String[] pendingAuthorizationPermissions;
    private Session pendingOpeningSession;
    private volatile Session session;
    private boolean sessionInvalidated;
    private SetterTokenCachingStrategy tokenCache;
    private volatile Session userSetSession;

    @Deprecated
    public Facebook(String str) {
        if (str == null) {
            throw new IllegalArgumentException("You must specify your application ID when instantiating a Facebook object. See README for details.");
        }
        this.mAppId = str;
    }

    @Deprecated
    public void authorize(Activity activity, DialogListener dialogListener) {
        authorize(activity, new String[0], DEFAULT_AUTH_ACTIVITY_CODE, SessionLoginBehavior.SSO_WITH_FALLBACK, dialogListener);
    }

    @Deprecated
    public void authorize(Activity activity, String[] strArr, DialogListener dialogListener) {
        authorize(activity, strArr, DEFAULT_AUTH_ACTIVITY_CODE, SessionLoginBehavior.SSO_WITH_FALLBACK, dialogListener);
    }

    @Deprecated
    public void authorize(Activity activity, String[] strArr, int i, DialogListener dialogListener) {
        authorize(activity, strArr, i, i >= 0 ? SessionLoginBehavior.SSO_WITH_FALLBACK : SessionLoginBehavior.SUPPRESS_SSO, dialogListener);
    }

    private void authorize(Activity activity, String[] strArr, int i, SessionLoginBehavior sessionLoginBehavior, DialogListener dialogListener) {
        boolean z = false;
        checkUserSession("authorize");
        this.pendingOpeningSession = new Builder(activity).setApplicationId(this.mAppId).setTokenCachingStrategy(getTokenCache()).build();
        this.pendingAuthorizationActivity = activity;
        if (strArr == null) {
            strArr = new String[0];
        }
        this.pendingAuthorizationPermissions = strArr;
        OpenRequest permissions = new OpenRequest(activity).setCallback(new 1(this, dialogListener)).setLoginBehavior(sessionLoginBehavior).setRequestCode(i).setPermissions(Arrays.asList(this.pendingAuthorizationPermissions));
        Session session = this.pendingOpeningSession;
        if (this.pendingAuthorizationPermissions.length > 0) {
            z = true;
        }
        openSession(session, permissions, z);
    }

    private void openSession(Session session, OpenRequest openRequest, boolean z) {
        openRequest.setIsLegacy(true);
        if (z) {
            session.openForPublish(openRequest);
        } else {
            session.openForRead(openRequest);
        }
    }

    private void onSessionCallback(Session session, SessionState sessionState, Exception exception, DialogListener dialogListener) {
        Bundle authorizationBundle = session.getAuthorizationBundle();
        if (sessionState == SessionState.OPENED) {
            Session session2 = null;
            synchronized (this.lock) {
                if (session != this.session) {
                    session2 = this.session;
                    this.session = session;
                    this.sessionInvalidated = false;
                }
            }
            if (session2 != null) {
                session2.close();
            }
            dialogListener.onComplete(authorizationBundle);
        } else if (exception == null) {
        } else {
            if (exception instanceof FacebookOperationCanceledException) {
                dialogListener.onCancel();
            } else if ((exception instanceof FacebookAuthorizationException) && authorizationBundle != null && authorizationBundle.containsKey("com.facebook.sdk.WebViewErrorCode") && authorizationBundle.containsKey("com.facebook.sdk.FailingUrl")) {
                dialogListener.onError(new DialogError(exception.getMessage(), authorizationBundle.getInt("com.facebook.sdk.WebViewErrorCode"), authorizationBundle.getString("com.facebook.sdk.FailingUrl")));
            } else {
                dialogListener.onFacebookError(new FacebookError(exception.getMessage()));
            }
        }
    }

    private boolean validateServiceIntent(Context context, Intent intent) {
        ResolveInfo resolveService = context.getPackageManager().resolveService(intent, 0);
        if (resolveService == null) {
            return false;
        }
        return validateAppSignatureForPackage(context, resolveService.serviceInfo.packageName);
    }

    private boolean validateAppSignatureForPackage(Context context, String str) {
        try {
            for (Signature toCharsString : context.getPackageManager().getPackageInfo(str, 64).signatures) {
                if (toCharsString.toCharsString().equals(FB_APP_SIGNATURE)) {
                    return true;
                }
            }
            return false;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    @Deprecated
    public void authorizeCallback(int i, int i2, Intent intent) {
        checkUserSession("authorizeCallback");
        Session session = this.pendingOpeningSession;
        if (session != null && session.onActivityResult(this.pendingAuthorizationActivity, i, i2, intent)) {
            this.pendingOpeningSession = null;
            this.pendingAuthorizationActivity = null;
            this.pendingAuthorizationPermissions = null;
        }
    }

    @Deprecated
    public boolean extendAccessToken(Context context, ServiceListener serviceListener) {
        checkUserSession("extendAccessToken");
        Intent intent = new Intent();
        intent.setClassName("com.facebook.katana", "com.facebook.katana.platform.TokenRefreshService");
        if (validateServiceIntent(context, intent)) {
            return context.bindService(intent, new TokenRefreshServiceConnection(this, context, serviceListener), 1);
        }
        return false;
    }

    @Deprecated
    public boolean extendAccessTokenIfNeeded(Context context, ServiceListener serviceListener) {
        checkUserSession("extendAccessTokenIfNeeded");
        if (shouldExtendAccessToken()) {
            return extendAccessToken(context, serviceListener);
        }
        return true;
    }

    @Deprecated
    public boolean shouldExtendAccessToken() {
        checkUserSession("shouldExtendAccessToken");
        return isSessionValid() && System.currentTimeMillis() - this.lastAccessUpdateMillisecondsAfterEpoch >= REFRESH_TOKEN_BARRIER;
    }

    @Deprecated
    public String logout(Context context) throws MalformedURLException, IOException {
        return logoutImpl(context);
    }

    String logoutImpl(Context context) throws MalformedURLException, IOException {
        checkUserSession("logout");
        Bundle bundle = new Bundle();
        bundle.putString("method", "auth.expireSession");
        String request = request(bundle);
        long currentTimeMillis = System.currentTimeMillis();
        synchronized (this.lock) {
            Session session = this.session;
            this.session = null;
            this.accessToken = null;
            this.accessExpiresMillisecondsAfterEpoch = 0;
            this.lastAccessUpdateMillisecondsAfterEpoch = currentTimeMillis;
            this.sessionInvalidated = false;
        }
        if (session != null) {
            session.closeAndClearTokenInformation();
        }
        return request;
    }

    @Deprecated
    public String request(Bundle bundle) throws MalformedURLException, IOException {
        if (bundle.containsKey("method")) {
            return requestImpl(null, bundle, "GET");
        }
        throw new IllegalArgumentException("API method must be specified. (parameters must contain key \"method\" and value). See http://developers.facebook.com/docs/reference/rest/");
    }

    @Deprecated
    public String request(String str) throws MalformedURLException, IOException {
        return requestImpl(str, new Bundle(), "GET");
    }

    @Deprecated
    public String request(String str, Bundle bundle) throws MalformedURLException, IOException {
        return requestImpl(str, bundle, "GET");
    }

    @Deprecated
    public String request(String str, Bundle bundle, String str2) throws FileNotFoundException, MalformedURLException, IOException {
        return requestImpl(str, bundle, str2);
    }

    String requestImpl(String str, Bundle bundle, String str2) throws FileNotFoundException, MalformedURLException, IOException {
        bundle.putString("format", "json");
        if (isSessionValid()) {
            bundle.putString(TOKEN, getAccessToken());
        }
        return Util.openUrl(str != null ? GRAPH_BASE_URL + str : RESTSERVER_URL, str2, bundle);
    }

    @Deprecated
    public void dialog(Context context, String str, DialogListener dialogListener) {
        dialog(context, str, new Bundle(), dialogListener);
    }

    @Deprecated
    public void dialog(Context context, String str, Bundle bundle, DialogListener dialogListener) {
        bundle.putString("display", "touch");
        bundle.putString("redirect_uri", REDIRECT_URI);
        if (str.equals(LOGIN)) {
            bundle.putString("type", "user_agent");
            bundle.putString("client_id", this.mAppId);
        } else {
            bundle.putString("app_id", this.mAppId);
            if (isSessionValid()) {
                bundle.putString(TOKEN, getAccessToken());
            }
        }
        if (context.checkCallingOrSelfPermission("android.permission.INTERNET") != 0) {
            Util.showAlert(context, "Error", "Application requires permission to access the Internet");
        } else {
            new FbDialog(context, str, bundle, dialogListener).show();
        }
    }

    @Deprecated
    public boolean isSessionValid() {
        return getAccessToken() != null && (getAccessExpires() == 0 || System.currentTimeMillis() < getAccessExpires());
    }

    @Deprecated
    public void setSession(Session session) {
        if (session == null) {
            throw new IllegalArgumentException("session cannot be null");
        }
        synchronized (this.lock) {
            this.userSetSession = session;
        }
    }

    private void checkUserSession(String str) {
        if (this.userSetSession != null) {
            throw new UnsupportedOperationException(String.format("Cannot call %s after setSession has been called.", new Object[]{str}));
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    @java.lang.Deprecated
    public final com.facebook.Session getSession() {
        /*
        r7 = this;
        r1 = 0;
        r2 = 0;
    L_0x0002:
        r3 = r7.lock;
        monitor-enter(r3);
        r0 = r7.userSetSession;	 Catch:{ all -> 0x0019 }
        if (r0 == 0) goto L_0x000d;
    L_0x0009:
        r0 = r7.userSetSession;	 Catch:{ all -> 0x0019 }
        monitor-exit(r3);	 Catch:{ all -> 0x0019 }
    L_0x000c:
        return r0;
    L_0x000d:
        r0 = r7.session;	 Catch:{ all -> 0x0019 }
        if (r0 != 0) goto L_0x0015;
    L_0x0011:
        r0 = r7.sessionInvalidated;	 Catch:{ all -> 0x0019 }
        if (r0 != 0) goto L_0x001c;
    L_0x0015:
        r0 = r7.session;	 Catch:{ all -> 0x0019 }
        monitor-exit(r3);	 Catch:{ all -> 0x0019 }
        goto L_0x000c;
    L_0x0019:
        r0 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x0019 }
        throw r0;
    L_0x001c:
        r0 = r7.accessToken;	 Catch:{ all -> 0x0019 }
        r4 = r7.session;	 Catch:{ all -> 0x0019 }
        monitor-exit(r3);	 Catch:{ all -> 0x0019 }
        if (r0 != 0) goto L_0x0025;
    L_0x0023:
        r0 = r2;
        goto L_0x000c;
    L_0x0025:
        if (r4 == 0) goto L_0x004e;
    L_0x0027:
        r0 = r4.getPermissions();
    L_0x002b:
        r3 = new com.facebook.Session$Builder;
        r4 = r7.pendingAuthorizationActivity;
        r3.<init>(r4);
        r4 = r7.mAppId;
        r3 = r3.setApplicationId(r4);
        r4 = r7.getTokenCache();
        r3 = r3.setTokenCachingStrategy(r4);
        r3 = r3.build();
        r4 = r3.getState();
        r5 = com.facebook.SessionState.CREATED_TOKEN_LOADED;
        if (r4 == r5) goto L_0x005e;
    L_0x004c:
        r0 = r2;
        goto L_0x000c;
    L_0x004e:
        r0 = r7.pendingAuthorizationPermissions;
        if (r0 == 0) goto L_0x0059;
    L_0x0052:
        r0 = r7.pendingAuthorizationPermissions;
        r0 = java.util.Arrays.asList(r0);
        goto L_0x002b;
    L_0x0059:
        r0 = java.util.Collections.emptyList();
        goto L_0x002b;
    L_0x005e:
        r4 = new com.facebook.Session$OpenRequest;
        r5 = r7.pendingAuthorizationActivity;
        r4.<init>(r5);
        r4 = r4.setPermissions(r0);
        r0 = r0.isEmpty();
        if (r0 != 0) goto L_0x0092;
    L_0x006f:
        r0 = 1;
    L_0x0070:
        r7.openSession(r3, r4, r0);
        r4 = r7.lock;
        monitor-enter(r4);
        r0 = r7.sessionInvalidated;	 Catch:{ all -> 0x0094 }
        if (r0 != 0) goto L_0x007e;
    L_0x007a:
        r0 = r7.session;	 Catch:{ all -> 0x0094 }
        if (r0 != 0) goto L_0x0097;
    L_0x007e:
        r0 = r7.session;	 Catch:{ all -> 0x0094 }
        r7.session = r3;	 Catch:{ all -> 0x0094 }
        r5 = 0;
        r7.sessionInvalidated = r5;	 Catch:{ all -> 0x0094 }
        r6 = r3;
        r3 = r0;
        r0 = r6;
    L_0x0088:
        monitor-exit(r4);	 Catch:{ all -> 0x0094 }
        if (r3 == 0) goto L_0x008e;
    L_0x008b:
        r3.close();
    L_0x008e:
        if (r0 == 0) goto L_0x0002;
    L_0x0090:
        goto L_0x000c;
    L_0x0092:
        r0 = r1;
        goto L_0x0070;
    L_0x0094:
        r0 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x0094 }
        throw r0;
    L_0x0097:
        r0 = r2;
        r3 = r2;
        goto L_0x0088;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.android.Facebook.getSession():com.facebook.Session");
    }

    @Deprecated
    public String getAccessToken() {
        Session session = getSession();
        if (session != null) {
            return session.getAccessToken();
        }
        return null;
    }

    @Deprecated
    public long getAccessExpires() {
        Session session = getSession();
        if (session != null) {
            return session.getExpirationDate().getTime();
        }
        return this.accessExpiresMillisecondsAfterEpoch;
    }

    @Deprecated
    public long getLastAccessUpdate() {
        return this.lastAccessUpdateMillisecondsAfterEpoch;
    }

    @Deprecated
    public void setTokenFromCache(String str, long j, long j2) {
        checkUserSession("setTokenFromCache");
        synchronized (this.lock) {
            this.accessToken = str;
            this.accessExpiresMillisecondsAfterEpoch = j;
            this.lastAccessUpdateMillisecondsAfterEpoch = j2;
        }
    }

    @Deprecated
    public void setAccessToken(String str) {
        checkUserSession("setAccessToken");
        synchronized (this.lock) {
            this.accessToken = str;
            this.lastAccessUpdateMillisecondsAfterEpoch = System.currentTimeMillis();
            this.sessionInvalidated = true;
        }
    }

    @Deprecated
    public void setAccessExpires(long j) {
        checkUserSession("setAccessExpires");
        synchronized (this.lock) {
            this.accessExpiresMillisecondsAfterEpoch = j;
            this.lastAccessUpdateMillisecondsAfterEpoch = System.currentTimeMillis();
            this.sessionInvalidated = true;
        }
    }

    @Deprecated
    public void setAccessExpiresIn(String str) {
        checkUserSession("setAccessExpiresIn");
        if (str != null) {
            setAccessExpires(str.equals(DraftAd.NEW_AD_ID) ? 0 : System.currentTimeMillis() + (Long.parseLong(str) * 1000));
        }
    }

    @Deprecated
    public String getAppId() {
        return this.mAppId;
    }

    @Deprecated
    public void setAppId(String str) {
        checkUserSession("setAppId");
        synchronized (this.lock) {
            this.mAppId = str;
            this.sessionInvalidated = true;
        }
    }

    private TokenCachingStrategy getTokenCache() {
        if (this.tokenCache == null) {
            this.tokenCache = new SetterTokenCachingStrategy(this, null);
        }
        return this.tokenCache;
    }

    private static String[] stringArray(List<String> list) {
        int size;
        int i = 0;
        if (list != null) {
            size = list.size();
        } else {
            size = 0;
        }
        String[] strArr = new String[size];
        if (list != null) {
            while (i < strArr.length) {
                strArr[i] = (String) list.get(i);
                i++;
            }
        }
        return strArr;
    }

    private static List<String> stringList(String[] strArr) {
        if (strArr != null) {
            return Arrays.asList(strArr);
        }
        return Collections.emptyList();
    }

    @Deprecated
    public static String getAttributionId(ContentResolver contentResolver) {
        return Settings.getAttributionId(contentResolver);
    }
}
