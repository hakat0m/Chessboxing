package com.facebook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.apptentive.android.sdk.BuildConfig;
import com.facebook.android.Facebook;
import com.facebook.android.R;
import com.facebook.internal.Utility;
import com.gumtree.android.auth.google.model.SmartLockSaveResult;
import com.gumtree.android.postad.services.converter.PaymentConverter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

class AuthorizationClient implements Serializable {
    static final String EVENT_EXTRAS_DEFAULT_AUDIENCE = "default_audience";
    static final String EVENT_EXTRAS_IS_LEGACY = "is_legacy";
    static final String EVENT_EXTRAS_LOGIN_BEHAVIOR = "login_behavior";
    static final String EVENT_EXTRAS_MISSING_INTERNET_PERMISSION = "no_internet_permission";
    static final String EVENT_EXTRAS_NEW_PERMISSIONS = "new_permissions";
    static final String EVENT_EXTRAS_NOT_TRIED = "not_tried";
    static final String EVENT_EXTRAS_PERMISSIONS = "permissions";
    static final String EVENT_EXTRAS_REQUEST_CODE = "request_code";
    static final String EVENT_EXTRAS_TRY_LEGACY = "try_legacy";
    static final String EVENT_EXTRAS_TRY_LOGIN_ACTIVITY = "try_login_activity";
    static final String EVENT_NAME_LOGIN_COMPLETE = "fb_mobile_login_complete";
    private static final String EVENT_NAME_LOGIN_METHOD_COMPLETE = "fb_mobile_login_method_complete";
    private static final String EVENT_NAME_LOGIN_METHOD_START = "fb_mobile_login_method_start";
    static final String EVENT_NAME_LOGIN_START = "fb_mobile_login_start";
    static final String EVENT_PARAM_AUTH_LOGGER_ID = "0_auth_logger_id";
    static final String EVENT_PARAM_ERROR_CODE = "4_error_code";
    static final String EVENT_PARAM_ERROR_MESSAGE = "5_error_message";
    static final String EVENT_PARAM_EXTRAS = "6_extras";
    static final String EVENT_PARAM_LOGIN_RESULT = "2_result";
    static final String EVENT_PARAM_METHOD = "3_method";
    private static final String EVENT_PARAM_METHOD_RESULT_SKIPPED = "skipped";
    static final String EVENT_PARAM_TIMESTAMP = "1_timestamp_ms";
    private static final String TAG = "Facebook-AuthorizationClient";
    private static final String WEB_VIEW_AUTH_HANDLER_STORE = "com.facebook.AuthorizationClient.WebViewAuthHandler.TOKEN_STORE_KEY";
    private static final String WEB_VIEW_AUTH_HANDLER_TOKEN_KEY = "TOKEN";
    private static final long serialVersionUID = 1;
    private transient AppEventsLogger appEventsLogger;
    transient BackgroundProcessingListener backgroundProcessingListener;
    transient boolean checkedInternetPermission;
    transient Context context;
    AuthHandler currentHandler;
    List<AuthHandler> handlersToTry;
    Map<String, String> loggingExtras;
    transient OnCompletedListener onCompletedListener;
    AuthorizationRequest pendingRequest;
    transient StartActivityDelegate startActivityDelegate;

    class AuthorizationRequest implements Serializable {
        private static final long serialVersionUID = 1;
        private final String applicationId;
        private final String authId;
        private final SessionDefaultAudience defaultAudience;
        private boolean isLegacy = false;
        private boolean isRerequest = false;
        private final SessionLoginBehavior loginBehavior;
        private List<String> permissions;
        private final String previousAccessToken;
        private final int requestCode;
        private final transient StartActivityDelegate startActivityDelegate;

        AuthorizationRequest(SessionLoginBehavior sessionLoginBehavior, int i, boolean z, List<String> list, SessionDefaultAudience sessionDefaultAudience, String str, String str2, StartActivityDelegate startActivityDelegate, String str3) {
            this.loginBehavior = sessionLoginBehavior;
            this.requestCode = i;
            this.isLegacy = z;
            this.permissions = list;
            this.defaultAudience = sessionDefaultAudience;
            this.applicationId = str;
            this.previousAccessToken = str2;
            this.startActivityDelegate = startActivityDelegate;
            this.authId = str3;
        }

        StartActivityDelegate getStartActivityDelegate() {
            return this.startActivityDelegate;
        }

        List<String> getPermissions() {
            return this.permissions;
        }

        void setPermissions(List<String> list) {
            this.permissions = list;
        }

        SessionLoginBehavior getLoginBehavior() {
            return this.loginBehavior;
        }

        int getRequestCode() {
            return this.requestCode;
        }

        SessionDefaultAudience getDefaultAudience() {
            return this.defaultAudience;
        }

        String getApplicationId() {
            return this.applicationId;
        }

        boolean isLegacy() {
            return this.isLegacy;
        }

        void setIsLegacy(boolean z) {
            this.isLegacy = z;
        }

        String getPreviousAccessToken() {
            return this.previousAccessToken;
        }

        boolean needsNewTokenValidation() {
            return (this.previousAccessToken == null || this.isLegacy) ? false : true;
        }

        String getAuthId() {
            return this.authId;
        }

        boolean isRerequest() {
            return this.isRerequest;
        }

        void setRerequest(boolean z) {
            this.isRerequest = z;
        }
    }

    interface BackgroundProcessingListener {
        void onBackgroundProcessingStarted();

        void onBackgroundProcessingStopped();
    }

    interface OnCompletedListener {
        void onCompleted(Result result);
    }

    class Result implements Serializable {
        private static final long serialVersionUID = 1;
        final Code code;
        final String errorCode;
        final String errorMessage;
        Map<String, String> loggingExtras;
        final AuthorizationRequest request;
        final AccessToken token;

        enum Code {
            SUCCESS(SmartLockSaveResult.SUCCESS),
            CANCEL("cancel"),
            ERROR("error");
            
            private final String loggingValue;

            private Code(String str) {
                this.loggingValue = str;
            }

            String getLoggingValue() {
                return this.loggingValue;
            }
        }

        private Result(AuthorizationRequest authorizationRequest, Code code, AccessToken accessToken, String str, String str2) {
            this.request = authorizationRequest;
            this.token = accessToken;
            this.errorMessage = str;
            this.code = code;
            this.errorCode = str2;
        }

        static Result createTokenResult(AuthorizationRequest authorizationRequest, AccessToken accessToken) {
            return new Result(authorizationRequest, Code.SUCCESS, accessToken, null, null);
        }

        static Result createCancelResult(AuthorizationRequest authorizationRequest, String str) {
            return new Result(authorizationRequest, Code.CANCEL, null, str, null);
        }

        static Result createErrorResult(AuthorizationRequest authorizationRequest, String str, String str2) {
            return createErrorResult(authorizationRequest, str, str2, null);
        }

        static Result createErrorResult(AuthorizationRequest authorizationRequest, String str, String str2, String str3) {
            return new Result(authorizationRequest, Code.ERROR, null, TextUtils.join(": ", Utility.asListNoNulls(new String[]{str, str2})), str3);
        }
    }

    AuthorizationClient() {
    }

    void setContext(Context context) {
        this.context = context;
        this.startActivityDelegate = null;
    }

    void setContext(Activity activity) {
        this.context = activity;
        this.startActivityDelegate = new 1(this, activity);
    }

    void startOrContinueAuth(AuthorizationRequest authorizationRequest) {
        if (getInProgress()) {
            continueAuth();
        } else {
            authorize(authorizationRequest);
        }
    }

    void authorize(AuthorizationRequest authorizationRequest) {
        if (authorizationRequest != null) {
            if (this.pendingRequest != null) {
                throw new FacebookException("Attempted to authorize while a request is pending.");
            } else if (!authorizationRequest.needsNewTokenValidation() || checkInternetPermission()) {
                this.pendingRequest = authorizationRequest;
                this.handlersToTry = getHandlerTypes(authorizationRequest);
                tryNextHandler();
            }
        }
    }

    void continueAuth() {
        if (this.pendingRequest == null || this.currentHandler == null) {
            throw new FacebookException("Attempted to continue authorization without a pending request.");
        } else if (this.currentHandler.needsRestart()) {
            this.currentHandler.cancel();
            tryCurrentHandler();
        }
    }

    boolean getInProgress() {
        return (this.pendingRequest == null || this.currentHandler == null) ? false : true;
    }

    void cancelCurrentHandler() {
        if (this.currentHandler != null) {
            this.currentHandler.cancel();
        }
    }

    boolean onActivityResult(int i, int i2, Intent intent) {
        if (this.pendingRequest == null || i != this.pendingRequest.getRequestCode()) {
            return false;
        }
        return this.currentHandler.onActivityResult(i, i2, intent);
    }

    private List<AuthHandler> getHandlerTypes(AuthorizationRequest authorizationRequest) {
        List arrayList = new ArrayList();
        SessionLoginBehavior loginBehavior = authorizationRequest.getLoginBehavior();
        if (loginBehavior.allowsKatanaAuth()) {
            if (!authorizationRequest.isLegacy()) {
                arrayList.add(new GetTokenAuthHandler(this));
            }
            arrayList.add(new KatanaProxyAuthHandler(this));
        }
        if (loginBehavior.allowsWebViewAuth()) {
            arrayList.add(new WebViewAuthHandler(this));
        }
        return arrayList;
    }

    boolean checkInternetPermission() {
        if (this.checkedInternetPermission) {
            return true;
        }
        if (checkPermission("android.permission.INTERNET") != 0) {
            complete(Result.createErrorResult(this.pendingRequest, this.context.getString(R.string.com_facebook_internet_permission_error_title), this.context.getString(R.string.com_facebook_internet_permission_error_message)));
            return false;
        }
        this.checkedInternetPermission = true;
        return true;
    }

    void tryNextHandler() {
        if (this.currentHandler != null) {
            logAuthorizationMethodComplete(this.currentHandler.getNameForLogging(), EVENT_PARAM_METHOD_RESULT_SKIPPED, null, null, this.currentHandler.methodLoggingExtras);
        }
        while (this.handlersToTry != null && !this.handlersToTry.isEmpty()) {
            this.currentHandler = (AuthHandler) this.handlersToTry.remove(0);
            if (tryCurrentHandler()) {
                return;
            }
        }
        if (this.pendingRequest != null) {
            completeWithFailure();
        }
    }

    private void completeWithFailure() {
        complete(Result.createErrorResult(this.pendingRequest, "Login attempt failed.", null));
    }

    private void addLoggingExtra(String str, String str2, boolean z) {
        if (this.loggingExtras == null) {
            this.loggingExtras = new HashMap();
        }
        if (this.loggingExtras.containsKey(str) && z) {
            str2 = ((String) this.loggingExtras.get(str)) + "," + str2;
        }
        this.loggingExtras.put(str, str2);
    }

    boolean tryCurrentHandler() {
        boolean z = false;
        if (!this.currentHandler.needsInternetPermission() || checkInternetPermission()) {
            z = this.currentHandler.tryAuthorize(this.pendingRequest);
            if (z) {
                logAuthorizationMethodStart(this.currentHandler.getNameForLogging());
            } else {
                addLoggingExtra(EVENT_EXTRAS_NOT_TRIED, this.currentHandler.getNameForLogging(), true);
            }
        } else {
            addLoggingExtra(EVENT_EXTRAS_MISSING_INTERNET_PERMISSION, PaymentConverter.DEFAULT_PAYMENT_METHOD_ID, false);
        }
        return z;
    }

    void completeAndValidate(Result result) {
        if (result.token == null || !this.pendingRequest.needsNewTokenValidation()) {
            complete(result);
        } else {
            validateSameFbidAndFinish(result);
        }
    }

    void complete(Result result) {
        if (this.currentHandler != null) {
            logAuthorizationMethodComplete(this.currentHandler.getNameForLogging(), result, this.currentHandler.methodLoggingExtras);
        }
        if (this.loggingExtras != null) {
            result.loggingExtras = this.loggingExtras;
        }
        this.handlersToTry = null;
        this.currentHandler = null;
        this.pendingRequest = null;
        this.loggingExtras = null;
        notifyOnCompleteListener(result);
    }

    OnCompletedListener getOnCompletedListener() {
        return this.onCompletedListener;
    }

    void setOnCompletedListener(OnCompletedListener onCompletedListener) {
        this.onCompletedListener = onCompletedListener;
    }

    BackgroundProcessingListener getBackgroundProcessingListener() {
        return this.backgroundProcessingListener;
    }

    void setBackgroundProcessingListener(BackgroundProcessingListener backgroundProcessingListener) {
        this.backgroundProcessingListener = backgroundProcessingListener;
    }

    StartActivityDelegate getStartActivityDelegate() {
        if (this.startActivityDelegate != null) {
            return this.startActivityDelegate;
        }
        return this.pendingRequest != null ? new 2(this) : null;
    }

    int checkPermission(String str) {
        return this.context.checkCallingOrSelfPermission(str);
    }

    void validateSameFbidAndFinish(Result result) {
        if (result.token == null) {
            throw new FacebookException("Can't validate without a token");
        }
        RequestBatch createReauthValidationBatch = createReauthValidationBatch(result);
        notifyBackgroundProcessingStart();
        createReauthValidationBatch.executeAsync();
    }

    RequestBatch createReauthValidationBatch(Result result) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        String token = result.token.getToken();
        3 3 = new 3(this, arrayList);
        String previousAccessToken = this.pendingRequest.getPreviousAccessToken();
        createGetProfileIdRequest(previousAccessToken).setCallback(3);
        createGetProfileIdRequest(token).setCallback(3);
        createGetPermissionsRequest(previousAccessToken).setCallback(new 4(this, arrayList2, arrayList3));
        RequestBatch requestBatch = new RequestBatch(new Request[]{r6, r0, r1});
        requestBatch.setBatchApplicationId(this.pendingRequest.getApplicationId());
        requestBatch.addCallback(new 5(this, arrayList, result, arrayList2, arrayList3));
        return requestBatch;
    }

    Request createGetPermissionsRequest(String str) {
        Bundle bundle = new Bundle();
        bundle.putString(Facebook.TOKEN, str);
        return new Request(null, "me/permissions", bundle, HttpMethod.GET, null);
    }

    Request createGetProfileIdRequest(String str) {
        Bundle bundle = new Bundle();
        bundle.putString("fields", "id");
        bundle.putString(Facebook.TOKEN, str);
        return new Request(null, "me", bundle, HttpMethod.GET, null);
    }

    private AppEventsLogger getAppEventsLogger() {
        if (this.appEventsLogger == null || !this.appEventsLogger.getApplicationId().equals(this.pendingRequest.getApplicationId())) {
            this.appEventsLogger = AppEventsLogger.newLogger(this.context, this.pendingRequest.getApplicationId());
        }
        return this.appEventsLogger;
    }

    private void notifyOnCompleteListener(Result result) {
        if (this.onCompletedListener != null) {
            this.onCompletedListener.onCompleted(result);
        }
    }

    private void notifyBackgroundProcessingStart() {
        if (this.backgroundProcessingListener != null) {
            this.backgroundProcessingListener.onBackgroundProcessingStarted();
        }
    }

    private void notifyBackgroundProcessingStop() {
        if (this.backgroundProcessingListener != null) {
            this.backgroundProcessingListener.onBackgroundProcessingStopped();
        }
    }

    private void logAuthorizationMethodStart(String str) {
        Bundle newAuthorizationLoggingBundle = newAuthorizationLoggingBundle(this.pendingRequest.getAuthId());
        newAuthorizationLoggingBundle.putLong(EVENT_PARAM_TIMESTAMP, System.currentTimeMillis());
        newAuthorizationLoggingBundle.putString(EVENT_PARAM_METHOD, str);
        getAppEventsLogger().logSdkEvent(EVENT_NAME_LOGIN_METHOD_START, null, newAuthorizationLoggingBundle);
    }

    private void logAuthorizationMethodComplete(String str, Result result, Map<String, String> map) {
        logAuthorizationMethodComplete(str, result.code.getLoggingValue(), result.errorMessage, result.errorCode, map);
    }

    private void logAuthorizationMethodComplete(String str, String str2, String str3, String str4, Map<String, String> map) {
        Bundle newAuthorizationLoggingBundle;
        if (this.pendingRequest == null) {
            newAuthorizationLoggingBundle = newAuthorizationLoggingBundle(BuildConfig.FLAVOR);
            newAuthorizationLoggingBundle.putString(EVENT_PARAM_LOGIN_RESULT, Code.ERROR.getLoggingValue());
            newAuthorizationLoggingBundle.putString(EVENT_PARAM_ERROR_MESSAGE, "Unexpected call to logAuthorizationMethodComplete with null pendingRequest.");
        } else {
            newAuthorizationLoggingBundle = newAuthorizationLoggingBundle(this.pendingRequest.getAuthId());
            if (str2 != null) {
                newAuthorizationLoggingBundle.putString(EVENT_PARAM_LOGIN_RESULT, str2);
            }
            if (str3 != null) {
                newAuthorizationLoggingBundle.putString(EVENT_PARAM_ERROR_MESSAGE, str3);
            }
            if (str4 != null) {
                newAuthorizationLoggingBundle.putString(EVENT_PARAM_ERROR_CODE, str4);
            }
            if (!(map == null || map.isEmpty())) {
                newAuthorizationLoggingBundle.putString(EVENT_PARAM_EXTRAS, new JSONObject(map).toString());
            }
        }
        newAuthorizationLoggingBundle.putString(EVENT_PARAM_METHOD, str);
        newAuthorizationLoggingBundle.putLong(EVENT_PARAM_TIMESTAMP, System.currentTimeMillis());
        getAppEventsLogger().logSdkEvent(EVENT_NAME_LOGIN_METHOD_COMPLETE, null, newAuthorizationLoggingBundle);
    }

    static Bundle newAuthorizationLoggingBundle(String str) {
        Bundle bundle = new Bundle();
        bundle.putLong(EVENT_PARAM_TIMESTAMP, System.currentTimeMillis());
        bundle.putString(EVENT_PARAM_AUTH_LOGGER_ID, str);
        bundle.putString(EVENT_PARAM_METHOD, BuildConfig.FLAVOR);
        bundle.putString(EVENT_PARAM_LOGIN_RESULT, BuildConfig.FLAVOR);
        bundle.putString(EVENT_PARAM_ERROR_MESSAGE, BuildConfig.FLAVOR);
        bundle.putString(EVENT_PARAM_ERROR_CODE, BuildConfig.FLAVOR);
        bundle.putString(EVENT_PARAM_EXTRAS, BuildConfig.FLAVOR);
        return bundle;
    }

    private static String getE2E() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("init", System.currentTimeMillis());
        } catch (JSONException e) {
        }
        return jSONObject.toString();
    }

    private void logWebLoginCompleted(String str, String str2) {
        AppEventsLogger newLogger = AppEventsLogger.newLogger(this.context, str);
        Bundle bundle = new Bundle();
        bundle.putString("fb_web_login_e2e", str2);
        bundle.putLong("fb_web_login_switchback_time", System.currentTimeMillis());
        bundle.putString("app_id", str);
        newLogger.logSdkEvent("fb_dialogs_web_login_dialog_complete", null, bundle);
    }
}
