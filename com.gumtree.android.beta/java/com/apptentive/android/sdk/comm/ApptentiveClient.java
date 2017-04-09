package com.apptentive.android.sdk.comm;

import android.content.SharedPreferences;
import android.text.TextUtils;
import com.adjust.sdk.Constants;
import com.apptentive.android.sdk.ApptentiveInternal;
import com.apptentive.android.sdk.ApptentiveLog;
import com.apptentive.android.sdk.BuildConfig;
import com.apptentive.android.sdk.model.AppRelease;
import com.apptentive.android.sdk.model.ConversationTokenRequest;
import com.apptentive.android.sdk.model.Device;
import com.apptentive.android.sdk.model.Event;
import com.apptentive.android.sdk.model.Person;
import com.apptentive.android.sdk.model.Sdk;
import com.apptentive.android.sdk.model.SurveyResponse;
import com.apptentive.android.sdk.module.messagecenter.model.ApptentiveMessage;
import com.apptentive.android.sdk.module.messagecenter.model.ApptentiveMessage.Type;
import com.apptentive.android.sdk.module.messagecenter.model.CompoundMessage;
import com.apptentive.android.sdk.util.Util;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.zip.GZIPInputStream;

public class ApptentiveClient {
    public static final int API_VERSION = 5;
    public static final int DEFAULT_HTTP_CONNECT_TIMEOUT = 45000;
    public static final int DEFAULT_HTTP_SOCKET_TIMEOUT = 45000;
    private static final String ENDPOINT_CONFIGURATION = "/conversation/configuration";
    private static final String ENDPOINT_CONVERSATION = "/conversation";
    private static final String ENDPOINT_CONVERSATION_FETCH = "/conversation?count=%s&after_id=%s&before_id=%s";
    private static final String ENDPOINT_DEVICES = "/devices";
    private static final String ENDPOINT_EVENTS = "/events";
    private static final String ENDPOINT_INTERACTIONS = "/interactions";
    private static final String ENDPOINT_MESSAGES = "/messages";
    private static final String ENDPOINT_PEOPLE = "/people";
    private static final String ENDPOINT_SURVEYS_POST = "/surveys/%s/respond";
    private static final String USER_AGENT_STRING = "Apptentive/%s (Android)";

    /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$apptentive$android$sdk$comm$ApptentiveClient$Method = new int[Method.values().length];
        static final /* synthetic */ int[] $SwitchMap$com$apptentive$android$sdk$module$messagecenter$model$ApptentiveMessage$Type = new int[Type.values().length];

        static {
            try {
                $SwitchMap$com$apptentive$android$sdk$comm$ApptentiveClient$Method[Method.GET.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$apptentive$android$sdk$comm$ApptentiveClient$Method[Method.PUT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$apptentive$android$sdk$comm$ApptentiveClient$Method[Method.POST.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$apptentive$android$sdk$module$messagecenter$model$ApptentiveMessage$Type[Type.CompoundMessage.ordinal()] = 1;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$apptentive$android$sdk$module$messagecenter$model$ApptentiveMessage$Type[Type.unknown.ordinal()] = 2;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    enum Method {
        GET,
        PUT,
        POST
    }

    public static ApptentiveHttpResponse getConversationToken(ConversationTokenRequest conversationTokenRequest) {
        return performHttpRequest(ApptentiveInternal.getInstance().getApptentiveApiKey(), ENDPOINT_CONVERSATION, Method.POST, conversationTokenRequest.toString());
    }

    public static ApptentiveHttpResponse getAppConfiguration() {
        return performHttpRequest(ApptentiveInternal.getInstance().getApptentiveConversationToken(), ENDPOINT_CONFIGURATION, Method.GET, null);
    }

    public static ApptentiveHttpResponse getMessages(Integer num, String str, String str2) {
        String str3 = ENDPOINT_CONVERSATION_FETCH;
        Object[] objArr = new Object[3];
        objArr[0] = num == null ? BuildConfig.FLAVOR : num.toString();
        if (str == null) {
            str = BuildConfig.FLAVOR;
        }
        objArr[1] = str;
        if (str2 == null) {
            str2 = BuildConfig.FLAVOR;
        }
        objArr[2] = str2;
        return performHttpRequest(ApptentiveInternal.getInstance().getApptentiveConversationToken(), String.format(str3, objArr), Method.GET, null);
    }

    public static ApptentiveHttpResponse postMessage(ApptentiveMessage apptentiveMessage) {
        switch (AnonymousClass1.$SwitchMap$com$apptentive$android$sdk$module$messagecenter$model$ApptentiveMessage$Type[apptentiveMessage.getType().ordinal()]) {
            case HighlightView.GROW_NONE /*1*/:
                return performMultipartFilePost(ApptentiveInternal.getInstance().getApptentiveConversationToken(), ENDPOINT_MESSAGES, apptentiveMessage.marshallForSending(), ((CompoundMessage) apptentiveMessage).getAssociatedFiles());
            default:
                return new ApptentiveHttpResponse();
        }
    }

    public static ApptentiveHttpResponse postEvent(Event event) {
        return performHttpRequest(ApptentiveInternal.getInstance().getApptentiveConversationToken(), ENDPOINT_EVENTS, Method.POST, event.marshallForSending());
    }

    public static ApptentiveHttpResponse putDevice(Device device) {
        return performHttpRequest(ApptentiveInternal.getInstance().getApptentiveConversationToken(), ENDPOINT_DEVICES, Method.PUT, device.marshallForSending());
    }

    public static ApptentiveHttpResponse putSdk(Sdk sdk) {
        return performHttpRequest(ApptentiveInternal.getInstance().getApptentiveConversationToken(), ENDPOINT_CONVERSATION, Method.PUT, sdk.marshallForSending());
    }

    public static ApptentiveHttpResponse putAppRelease(AppRelease appRelease) {
        return performHttpRequest(ApptentiveInternal.getInstance().getApptentiveConversationToken(), ENDPOINT_CONVERSATION, Method.PUT, appRelease.marshallForSending());
    }

    public static ApptentiveHttpResponse putPerson(Person person) {
        return performHttpRequest(ApptentiveInternal.getInstance().getApptentiveConversationToken(), ENDPOINT_PEOPLE, Method.PUT, person.marshallForSending());
    }

    public static ApptentiveHttpResponse postSurvey(SurveyResponse surveyResponse) {
        return performHttpRequest(ApptentiveInternal.getInstance().getApptentiveConversationToken(), String.format(ENDPOINT_SURVEYS_POST, new Object[]{surveyResponse.getId()}), Method.POST, surveyResponse.marshallForSending());
    }

    public static ApptentiveHttpResponse getInteractions() {
        return performHttpRequest(ApptentiveInternal.getInstance().getApptentiveConversationToken(), ENDPOINT_INTERACTIONS, Method.GET, null);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.apptentive.android.sdk.comm.ApptentiveHttpResponse performHttpRequest(java.lang.String r9, java.lang.String r10, com.apptentive.android.sdk.comm.ApptentiveClient.Method r11, java.lang.String r12) {
        /*
        r2 = 2;
        r4 = 1;
        r7 = 0;
        r0 = new java.lang.StringBuilder;
        r0.<init>();
        r1 = getEndpointBase();
        r0 = r0.append(r1);
        r0 = r0.append(r10);
        r0 = r0.toString();
        r1 = "Performing %s request to %s";
        r2 = new java.lang.Object[r2];
        r3 = r11.name();
        r2[r7] = r3;
        r2[r4] = r0;
        com.apptentive.android.sdk.ApptentiveLog.d(r1, r2);
        r2 = new com.apptentive.android.sdk.comm.ApptentiveHttpResponse;
        r2.<init>();
        r1 = com.apptentive.android.sdk.util.Util.isNetworkConnectionPresent();
        if (r1 != 0) goto L_0x003b;
    L_0x0032:
        r0 = "Network unavailable.";
        r1 = new java.lang.Object[r7];
        com.apptentive.android.sdk.ApptentiveLog.d(r0, r1);
        r0 = r2;
    L_0x003a:
        return r0;
    L_0x003b:
        r1 = 0;
        r3 = new java.net.URL;	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x01e1 }
        r3.<init>(r0);	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x01e1 }
        r0 = r3.openConnection();	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x01e1 }
        r0 = (java.net.HttpURLConnection) r0;	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x01e1 }
        r1 = "User-Agent";
        r3 = getUserAgentString();	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r0.setRequestProperty(r1, r3);	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r1 = "Connection";
        r3 = "Keep-Alive";
        r0.setRequestProperty(r1, r3);	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r1 = 45000; // 0xafc8 float:6.3058E-41 double:2.2233E-319;
        r0.setConnectTimeout(r1);	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r1 = 45000; // 0xafc8 float:6.3058E-41 double:2.2233E-319;
        r0.setReadTimeout(r1);	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r1 = "Authorization";
        r3 = new java.lang.StringBuilder;	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r3.<init>();	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r4 = "OAuth ";
        r3 = r3.append(r4);	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r3 = r3.append(r9);	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r3 = r3.toString();	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r0.setRequestProperty(r1, r3);	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r1 = "Accept-Encoding";
        r3 = "gzip";
        r0.setRequestProperty(r1, r3);	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r1 = "Accept";
        r3 = "application/json";
        r0.setRequestProperty(r1, r3);	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r1 = "X-API-Version";
        r3 = 5;
        r3 = java.lang.String.valueOf(r3);	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r0.setRequestProperty(r1, r3);	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r1 = com.apptentive.android.sdk.comm.ApptentiveClient.AnonymousClass1.$SwitchMap$com$apptentive$android$sdk$comm$ApptentiveClient$Method;	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r3 = r11.ordinal();	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r1 = r1[r3];	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        switch(r1) {
            case 1: goto L_0x00be;
            case 2: goto L_0x0128;
            case 3: goto L_0x0137;
            default: goto L_0x009e;
        };	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
    L_0x009e:
        r1 = new java.lang.StringBuilder;	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r1.<init>();	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r3 = "Unrecognized method: ";
        r1 = r1.append(r3);	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r3 = r11.name();	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r1 = r1.append(r3);	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r1 = r1.toString();	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r3 = 0;
        r3 = new java.lang.Object[r3];	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        com.apptentive.android.sdk.ApptentiveLog.e(r1, r3);	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r0 = r2;
        goto L_0x003a;
    L_0x00be:
        r1 = "GET";
        r0.setRequestMethod(r1);	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
    L_0x00c3:
        r3 = r0.getResponseCode();	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r2.setCode(r3);	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r1 = r0.getResponseMessage();	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r2.setReason(r1);	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r1 = new java.lang.StringBuilder;	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r1.<init>();	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r4 = "Response Status Line: ";
        r1 = r1.append(r4);	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r4 = r0.getResponseMessage();	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r1 = r1.append(r4);	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r1 = r1.toString();	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r4 = 0;
        r4 = new java.lang.Object[r4];	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        com.apptentive.android.sdk.ApptentiveLog.d(r1, r4);	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r4 = new java.util.HashMap;	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r4.<init>();	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r1 = r0.getHeaderFields();	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r1 = r1.entrySet();	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r5 = r1.iterator();	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
    L_0x00ff:
        r1 = r5.hasNext();	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        if (r1 == 0) goto L_0x0146;
    L_0x0105:
        r1 = r5.next();	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r1 = (java.util.Map.Entry) r1;	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r6 = r1.getKey();	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r1 = r1.getValue();	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r1 = (java.util.List) r1;	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r1 = r1.toString();	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r4.put(r6, r1);	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        goto L_0x00ff;
    L_0x011d:
        r0 = move-exception;
        r1 = "Error communicating with server.";
        r3 = new java.lang.Object[r7];
        com.apptentive.android.sdk.ApptentiveLog.w(r1, r0, r3);
    L_0x0125:
        r0 = r2;
        goto L_0x003a;
    L_0x0128:
        r1 = "PUT";
        sendPostPutRequest(r0, r1, r12);	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        goto L_0x00c3;
    L_0x012e:
        r0 = move-exception;
        r1 = "Timeout communicating with server.";
        r3 = new java.lang.Object[r7];
        com.apptentive.android.sdk.ApptentiveLog.w(r1, r0, r3);
        goto L_0x0125;
    L_0x0137:
        r1 = "POST";
        sendPostPutRequest(r0, r1, r12);	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        goto L_0x00c3;
    L_0x013d:
        r0 = move-exception;
        r1 = "MalformedUrlException";
        r3 = new java.lang.Object[r7];
        com.apptentive.android.sdk.ApptentiveLog.w(r1, r0, r3);
        goto L_0x0125;
    L_0x0146:
        r2.setHeaders(r4);	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r1 = "HTTP %d: %s";
        r4 = 2;
        r4 = new java.lang.Object[r4];	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r5 = 0;
        r6 = r0.getResponseCode();	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r6 = java.lang.Integer.valueOf(r6);	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r4[r5] = r6;	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r5 = 1;
        r6 = r0.getResponseMessage();	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r4[r5] = r6;	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        com.apptentive.android.sdk.ApptentiveLog.d(r1, r4);	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r1 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        if (r3 < r1) goto L_0x01c5;
    L_0x0167:
        r1 = 300; // 0x12c float:4.2E-43 double:1.48E-321;
        if (r3 >= r1) goto L_0x01c5;
    L_0x016b:
        r1 = r2.isZipped();	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r1 = getResponse(r0, r1);	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r2.setContent(r1);	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r1 = "Response: %s";
        r3 = 1;
        r3 = new java.lang.Object[r3];	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r4 = 0;
        r5 = r2.getContent();	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r3[r4] = r5;	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        com.apptentive.android.sdk.ApptentiveLog.v(r1, r3);	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        goto L_0x0125;
    L_0x0186:
        r1 = move-exception;
        r8 = r1;
        r1 = r0;
        r0 = r8;
    L_0x018a:
        r3 = "IOException";
        r4 = new java.lang.Object[r7];
        com.apptentive.android.sdk.ApptentiveLog.w(r3, r0, r4);
        r0 = r2.isZipped();	 Catch:{ IOException -> 0x01bb }
        r0 = getErrorResponse(r1, r0);	 Catch:{ IOException -> 0x01bb }
        r2.setContent(r0);	 Catch:{ IOException -> 0x01bb }
        r0 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x01bb }
        r0.<init>();	 Catch:{ IOException -> 0x01bb }
        r1 = "Response: ";
        r0 = r0.append(r1);	 Catch:{ IOException -> 0x01bb }
        r1 = r2.getContent();	 Catch:{ IOException -> 0x01bb }
        r0 = r0.append(r1);	 Catch:{ IOException -> 0x01bb }
        r0 = r0.toString();	 Catch:{ IOException -> 0x01bb }
        r1 = 0;
        r1 = new java.lang.Object[r1];	 Catch:{ IOException -> 0x01bb }
        com.apptentive.android.sdk.ApptentiveLog.w(r0, r1);	 Catch:{ IOException -> 0x01bb }
        goto L_0x0125;
    L_0x01bb:
        r0 = move-exception;
        r1 = "Can't read error stream.";
        r3 = new java.lang.Object[r7];
        com.apptentive.android.sdk.ApptentiveLog.w(r1, r0, r3);
        goto L_0x0125;
    L_0x01c5:
        r1 = r2.isZipped();	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r1 = getErrorResponse(r0, r1);	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r2.setContent(r1);	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r1 = "Response: %s";
        r3 = 1;
        r3 = new java.lang.Object[r3];	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r4 = 0;
        r5 = r2.getContent();	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        r3[r4] = r5;	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        com.apptentive.android.sdk.ApptentiveLog.w(r1, r3);	 Catch:{ IllegalArgumentException -> 0x011d, SocketTimeoutException -> 0x012e, MalformedURLException -> 0x013d, IOException -> 0x0186 }
        goto L_0x0125;
    L_0x01e1:
        r0 = move-exception;
        goto L_0x018a;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.apptentive.android.sdk.comm.ApptentiveClient.performHttpRequest(java.lang.String, java.lang.String, com.apptentive.android.sdk.comm.ApptentiveClient$Method, java.lang.String):com.apptentive.android.sdk.comm.ApptentiveHttpResponse");
    }

    private static void sendPostPutRequest(HttpURLConnection httpURLConnection, String str, String str2) throws IOException {
        Throwable th;
        ApptentiveLog.d("%s body: %s", str, str2);
        httpURLConnection.setRequestMethod(str);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setRequestProperty("Content-Type", "application/json");
        if (!TextUtils.isEmpty(str2)) {
            Closeable bufferedWriter;
            try {
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream(), Constants.ENCODING));
                try {
                    bufferedWriter.write(str2);
                    if (bufferedWriter != null) {
                        bufferedWriter.flush();
                        Util.ensureClosed(bufferedWriter);
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (bufferedWriter != null) {
                        bufferedWriter.flush();
                        Util.ensureClosed(bufferedWriter);
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                bufferedWriter = null;
                if (bufferedWriter != null) {
                    bufferedWriter.flush();
                    Util.ensureClosed(bufferedWriter);
                }
                throw th;
            }
        }
    }

    private static com.apptentive.android.sdk.comm.ApptentiveHttpResponse performMultipartFilePost(java.lang.String r17, java.lang.String r18, java.lang.String r19, java.util.List<com.apptentive.android.sdk.model.StoredFile> r20) {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Exception block dominator not found, method:com.apptentive.android.sdk.comm.ApptentiveClient.performMultipartFilePost(java.lang.String, java.lang.String, java.lang.String, java.util.List):com.apptentive.android.sdk.comm.ApptentiveHttpResponse. bs: [B:51:0x01fa, B:91:0x028e]
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:86)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:286)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:173)
*/
        /*
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = getEndpointBase();
        r1 = r1.append(r2);
        r0 = r18;
        r1 = r1.append(r0);
        r1 = r1.toString();
        r2 = "Performing multipart POST to %s";
        r3 = 1;
        r3 = new java.lang.Object[r3];
        r4 = 0;
        r3[r4] = r1;
        com.apptentive.android.sdk.ApptentiveLog.d(r2, r3);
        r2 = "Multipart POST body: %s";
        r3 = 1;
        r3 = new java.lang.Object[r3];
        r4 = 0;
        r3[r4] = r19;
        com.apptentive.android.sdk.ApptentiveLog.d(r2, r3);
        r3 = new com.apptentive.android.sdk.comm.ApptentiveHttpResponse;
        r3.<init>();
        r2 = com.apptentive.android.sdk.util.Util.isNetworkConnectionPresent();
        if (r2 != 0) goto L_0x0042;
    L_0x0038:
        r1 = "Network unavailable.";
        r2 = 0;
        r2 = new java.lang.Object[r2];
        com.apptentive.android.sdk.ApptentiveLog.d(r1, r2);
        r1 = r3;
    L_0x0041:
        return r1;
    L_0x0042:
        r9 = "\r\n";
        r10 = "--";
        r2 = java.util.UUID.randomUUID();
        r11 = r2.toString();
        r4 = 0;
        r2 = 0;
        r5 = new java.net.URL;	 Catch:{ FileNotFoundException -> 0x02ff, MalformedURLException -> 0x02fb, SocketTimeoutException -> 0x02f7, IOException -> 0x02ee, all -> 0x02e8 }
        r5.<init>(r1);	 Catch:{ FileNotFoundException -> 0x02ff, MalformedURLException -> 0x02fb, SocketTimeoutException -> 0x02f7, IOException -> 0x02ee, all -> 0x02e8 }
        r1 = r5.openConnection();	 Catch:{ FileNotFoundException -> 0x02ff, MalformedURLException -> 0x02fb, SocketTimeoutException -> 0x02f7, IOException -> 0x02ee, all -> 0x02e8 }
        r1 = (java.net.HttpURLConnection) r1;	 Catch:{ FileNotFoundException -> 0x02ff, MalformedURLException -> 0x02fb, SocketTimeoutException -> 0x02f7, IOException -> 0x02ee, all -> 0x02e8 }
        r4 = 1;
        r1.setDoInput(r4);	 Catch:{ FileNotFoundException -> 0x02ff, MalformedURLException -> 0x02fb, SocketTimeoutException -> 0x02f7, IOException -> 0x02f2, all -> 0x02e8 }
        r4 = 1;	 Catch:{ FileNotFoundException -> 0x02ff, MalformedURLException -> 0x02fb, SocketTimeoutException -> 0x02f7, IOException -> 0x02f2, all -> 0x02e8 }
        r1.setDoOutput(r4);	 Catch:{ FileNotFoundException -> 0x02ff, MalformedURLException -> 0x02fb, SocketTimeoutException -> 0x02f7, IOException -> 0x02f2, all -> 0x02e8 }
        r4 = 0;	 Catch:{ FileNotFoundException -> 0x02ff, MalformedURLException -> 0x02fb, SocketTimeoutException -> 0x02f7, IOException -> 0x02f2, all -> 0x02e8 }
        r1.setUseCaches(r4);	 Catch:{ FileNotFoundException -> 0x02ff, MalformedURLException -> 0x02fb, SocketTimeoutException -> 0x02f7, IOException -> 0x02f2, all -> 0x02e8 }
        r4 = 45000; // 0xafc8 float:6.3058E-41 double:2.2233E-319;	 Catch:{ FileNotFoundException -> 0x02ff, MalformedURLException -> 0x02fb, SocketTimeoutException -> 0x02f7, IOException -> 0x02f2, all -> 0x02e8 }
        r1.setConnectTimeout(r4);	 Catch:{ FileNotFoundException -> 0x02ff, MalformedURLException -> 0x02fb, SocketTimeoutException -> 0x02f7, IOException -> 0x02f2, all -> 0x02e8 }
        r4 = 45000; // 0xafc8 float:6.3058E-41 double:2.2233E-319;	 Catch:{ FileNotFoundException -> 0x02ff, MalformedURLException -> 0x02fb, SocketTimeoutException -> 0x02f7, IOException -> 0x02f2, all -> 0x02e8 }
        r1.setReadTimeout(r4);	 Catch:{ FileNotFoundException -> 0x02ff, MalformedURLException -> 0x02fb, SocketTimeoutException -> 0x02f7, IOException -> 0x02f2, all -> 0x02e8 }
        r4 = "POST";	 Catch:{ FileNotFoundException -> 0x02ff, MalformedURLException -> 0x02fb, SocketTimeoutException -> 0x02f7, IOException -> 0x02f2, all -> 0x02e8 }
        r1.setRequestMethod(r4);	 Catch:{ FileNotFoundException -> 0x02ff, MalformedURLException -> 0x02fb, SocketTimeoutException -> 0x02f7, IOException -> 0x02f2, all -> 0x02e8 }
        r4 = "Content-Type";	 Catch:{ FileNotFoundException -> 0x02ff, MalformedURLException -> 0x02fb, SocketTimeoutException -> 0x02f7, IOException -> 0x02f2, all -> 0x02e8 }
        r5 = new java.lang.StringBuilder;	 Catch:{ FileNotFoundException -> 0x02ff, MalformedURLException -> 0x02fb, SocketTimeoutException -> 0x02f7, IOException -> 0x02f2, all -> 0x02e8 }
        r5.<init>();	 Catch:{ FileNotFoundException -> 0x02ff, MalformedURLException -> 0x02fb, SocketTimeoutException -> 0x02f7, IOException -> 0x02f2, all -> 0x02e8 }
        r6 = "multipart/mixed;boundary=";	 Catch:{ FileNotFoundException -> 0x02ff, MalformedURLException -> 0x02fb, SocketTimeoutException -> 0x02f7, IOException -> 0x02f2, all -> 0x02e8 }
        r5 = r5.append(r6);	 Catch:{ FileNotFoundException -> 0x02ff, MalformedURLException -> 0x02fb, SocketTimeoutException -> 0x02f7, IOException -> 0x02f2, all -> 0x02e8 }
        r5 = r5.append(r11);	 Catch:{ FileNotFoundException -> 0x02ff, MalformedURLException -> 0x02fb, SocketTimeoutException -> 0x02f7, IOException -> 0x02f2, all -> 0x02e8 }
        r5 = r5.toString();	 Catch:{ FileNotFoundException -> 0x02ff, MalformedURLException -> 0x02fb, SocketTimeoutException -> 0x02f7, IOException -> 0x02f2, all -> 0x02e8 }
        r1.setRequestProperty(r4, r5);	 Catch:{ FileNotFoundException -> 0x02ff, MalformedURLException -> 0x02fb, SocketTimeoutException -> 0x02f7, IOException -> 0x02f2, all -> 0x02e8 }
        r4 = "Authorization";	 Catch:{ FileNotFoundException -> 0x02ff, MalformedURLException -> 0x02fb, SocketTimeoutException -> 0x02f7, IOException -> 0x02f2, all -> 0x02e8 }
        r5 = new java.lang.StringBuilder;	 Catch:{ FileNotFoundException -> 0x02ff, MalformedURLException -> 0x02fb, SocketTimeoutException -> 0x02f7, IOException -> 0x02f2, all -> 0x02e8 }
        r5.<init>();	 Catch:{ FileNotFoundException -> 0x02ff, MalformedURLException -> 0x02fb, SocketTimeoutException -> 0x02f7, IOException -> 0x02f2, all -> 0x02e8 }
        r6 = "OAuth ";	 Catch:{ FileNotFoundException -> 0x02ff, MalformedURLException -> 0x02fb, SocketTimeoutException -> 0x02f7, IOException -> 0x02f2, all -> 0x02e8 }
        r5 = r5.append(r6);	 Catch:{ FileNotFoundException -> 0x02ff, MalformedURLException -> 0x02fb, SocketTimeoutException -> 0x02f7, IOException -> 0x02f2, all -> 0x02e8 }
        r0 = r17;	 Catch:{ FileNotFoundException -> 0x02ff, MalformedURLException -> 0x02fb, SocketTimeoutException -> 0x02f7, IOException -> 0x02f2, all -> 0x02e8 }
        r5 = r5.append(r0);	 Catch:{ FileNotFoundException -> 0x02ff, MalformedURLException -> 0x02fb, SocketTimeoutException -> 0x02f7, IOException -> 0x02f2, all -> 0x02e8 }
        r5 = r5.toString();	 Catch:{ FileNotFoundException -> 0x02ff, MalformedURLException -> 0x02fb, SocketTimeoutException -> 0x02f7, IOException -> 0x02f2, all -> 0x02e8 }
        r1.setRequestProperty(r4, r5);	 Catch:{ FileNotFoundException -> 0x02ff, MalformedURLException -> 0x02fb, SocketTimeoutException -> 0x02f7, IOException -> 0x02f2, all -> 0x02e8 }
        r4 = "Accept";	 Catch:{ FileNotFoundException -> 0x02ff, MalformedURLException -> 0x02fb, SocketTimeoutException -> 0x02f7, IOException -> 0x02f2, all -> 0x02e8 }
        r5 = "application/json";	 Catch:{ FileNotFoundException -> 0x02ff, MalformedURLException -> 0x02fb, SocketTimeoutException -> 0x02f7, IOException -> 0x02f2, all -> 0x02e8 }
        r1.setRequestProperty(r4, r5);	 Catch:{ FileNotFoundException -> 0x02ff, MalformedURLException -> 0x02fb, SocketTimeoutException -> 0x02f7, IOException -> 0x02f2, all -> 0x02e8 }
        r4 = "X-API-Version";	 Catch:{ FileNotFoundException -> 0x02ff, MalformedURLException -> 0x02fb, SocketTimeoutException -> 0x02f7, IOException -> 0x02f2, all -> 0x02e8 }
        r5 = 5;	 Catch:{ FileNotFoundException -> 0x02ff, MalformedURLException -> 0x02fb, SocketTimeoutException -> 0x02f7, IOException -> 0x02f2, all -> 0x02e8 }
        r5 = java.lang.String.valueOf(r5);	 Catch:{ FileNotFoundException -> 0x02ff, MalformedURLException -> 0x02fb, SocketTimeoutException -> 0x02f7, IOException -> 0x02f2, all -> 0x02e8 }
        r1.setRequestProperty(r4, r5);	 Catch:{ FileNotFoundException -> 0x02ff, MalformedURLException -> 0x02fb, SocketTimeoutException -> 0x02f7, IOException -> 0x02f2, all -> 0x02e8 }
        r4 = "User-Agent";	 Catch:{ FileNotFoundException -> 0x02ff, MalformedURLException -> 0x02fb, SocketTimeoutException -> 0x02f7, IOException -> 0x02f2, all -> 0x02e8 }
        r5 = getUserAgentString();	 Catch:{ FileNotFoundException -> 0x02ff, MalformedURLException -> 0x02fb, SocketTimeoutException -> 0x02f7, IOException -> 0x02f2, all -> 0x02e8 }
        r1.setRequestProperty(r4, r5);	 Catch:{ FileNotFoundException -> 0x02ff, MalformedURLException -> 0x02fb, SocketTimeoutException -> 0x02f7, IOException -> 0x02f2, all -> 0x02e8 }
        r7 = new java.io.DataOutputStream;	 Catch:{ FileNotFoundException -> 0x02ff, MalformedURLException -> 0x02fb, SocketTimeoutException -> 0x02f7, IOException -> 0x02f2, all -> 0x02e8 }
        r4 = r1.getOutputStream();	 Catch:{ FileNotFoundException -> 0x02ff, MalformedURLException -> 0x02fb, SocketTimeoutException -> 0x02f7, IOException -> 0x02f2, all -> 0x02e8 }
        r7.<init>(r4);	 Catch:{ FileNotFoundException -> 0x02ff, MalformedURLException -> 0x02fb, SocketTimeoutException -> 0x02f7, IOException -> 0x02f2, all -> 0x02e8 }
        r2 = new java.lang.StringBuilder;	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r2.<init>();	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r2 = r2.append(r10);	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r2 = r2.append(r11);	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r2 = r2.append(r9);	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r2 = r2.toString();	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r7.writeBytes(r2);	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r2 = new java.lang.StringBuilder;	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r2.<init>();	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r4 = "Content-Disposition: form-data; name=\"message\"";	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r2 = r2.append(r4);	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r2 = r2.append(r9);	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r2 = r2.toString();	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r7.writeBytes(r2);	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r2 = new java.lang.StringBuilder;	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r2.<init>();	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r4 = "Content-Type: text/plain;charset=UTF-8";	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r2 = r2.append(r4);	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r2 = r2.append(r9);	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r2 = r2.toString();	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r7.writeBytes(r2);	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r7.writeBytes(r9);	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r2 = "UTF-8";	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r0 = r19;	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r2 = r0.getBytes(r2);	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r7.write(r2);	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r7.writeBytes(r9);	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        if (r20 == 0) goto L_0x0228;	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
    L_0x0124:
        r12 = r20.iterator();	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
    L_0x0128:
        r2 = r12.hasNext();	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        if (r2 == 0) goto L_0x0228;	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
    L_0x012e:
        r2 = r12.next();	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r2 = (com.apptentive.android.sdk.model.StoredFile) r2;	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r4 = 0;
        r5 = r2.getLocalFilePath();	 Catch:{ IOException -> 0x0207 }
        r6 = r2.getSourceUriOrPath();	 Catch:{ IOException -> 0x0207 }
        r13 = new java.io.File;	 Catch:{ IOException -> 0x0207 }
        r13.<init>(r5);	 Catch:{ IOException -> 0x0207 }
        r8 = r13.exists();	 Catch:{ IOException -> 0x0207 }
        if (r8 != 0) goto L_0x0176;	 Catch:{ IOException -> 0x0207 }
    L_0x0148:
        r8 = 0;	 Catch:{ IOException -> 0x0207 }
        r14 = r2.getMimeType();	 Catch:{ IOException -> 0x0207 }
        r14 = com.apptentive.android.sdk.util.Util.isMimeTypeImage(r14);	 Catch:{ IOException -> 0x0207 }
        if (r14 == 0) goto L_0x016d;	 Catch:{ IOException -> 0x0207 }
    L_0x0153:
        r8 = com.apptentive.android.sdk.util.image.ImageUtil.createScaledDownImageCacheFile(r6, r5);	 Catch:{ IOException -> 0x0207 }
    L_0x0157:
        if (r8 != 0) goto L_0x0176;
    L_0x0159:
        com.apptentive.android.sdk.util.Util.ensureClosed(r4);	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        goto L_0x0128;
    L_0x015d:
        r1 = move-exception;
        r2 = r7;
    L_0x015f:
        r4 = "Error getting file to upload.";	 Catch:{ all -> 0x02eb }
        r5 = 0;	 Catch:{ all -> 0x02eb }
        r5 = new java.lang.Object[r5];	 Catch:{ all -> 0x02eb }
        com.apptentive.android.sdk.ApptentiveLog.e(r4, r1, r5);	 Catch:{ all -> 0x02eb }
        com.apptentive.android.sdk.util.Util.ensureClosed(r2);
    L_0x016a:
        r1 = r3;
        goto L_0x0041;
    L_0x016d:
        r14 = 0;
        r14 = com.apptentive.android.sdk.util.Util.createLocalStoredFile(r6, r5, r14);	 Catch:{ IOException -> 0x0207 }
        if (r14 == 0) goto L_0x0157;	 Catch:{ IOException -> 0x0207 }
    L_0x0174:
        r8 = 1;	 Catch:{ IOException -> 0x0207 }
        goto L_0x0157;	 Catch:{ IOException -> 0x0207 }
    L_0x0176:
        r8 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x0207 }
        r8.<init>();	 Catch:{ IOException -> 0x0207 }
        r8 = r8.append(r10);	 Catch:{ IOException -> 0x0207 }
        r8 = r8.append(r11);	 Catch:{ IOException -> 0x0207 }
        r8 = r8.append(r9);	 Catch:{ IOException -> 0x0207 }
        r8 = r8.toString();	 Catch:{ IOException -> 0x0207 }
        r7.writeBytes(r8);	 Catch:{ IOException -> 0x0207 }
        r8 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x0207 }
        r8.<init>();	 Catch:{ IOException -> 0x0207 }
        r14 = android.text.TextUtils.isEmpty(r6);	 Catch:{ IOException -> 0x0207 }
        if (r14 == 0) goto L_0x0317;	 Catch:{ IOException -> 0x0207 }
    L_0x0199:
        r6 = "Content-Disposition: form-data; name=\"file[]\"; filename=\"%s\"";	 Catch:{ IOException -> 0x0207 }
        r14 = 1;	 Catch:{ IOException -> 0x0207 }
        r14 = new java.lang.Object[r14];	 Catch:{ IOException -> 0x0207 }
        r15 = 0;	 Catch:{ IOException -> 0x0207 }
        r14[r15] = r5;	 Catch:{ IOException -> 0x0207 }
        r5 = java.lang.String.format(r6, r14);	 Catch:{ IOException -> 0x0207 }
        r5 = r8.append(r5);	 Catch:{ IOException -> 0x0207 }
        r5.append(r9);	 Catch:{ IOException -> 0x0207 }
        r5 = "Content-Type: ";	 Catch:{ IOException -> 0x0207 }
        r5 = r8.append(r5);	 Catch:{ IOException -> 0x0207 }
        r2 = r2.getMimeType();	 Catch:{ IOException -> 0x0207 }
        r2 = r5.append(r2);	 Catch:{ IOException -> 0x0207 }
        r2.append(r9);	 Catch:{ IOException -> 0x0207 }
        r2 = r8.toString();	 Catch:{ IOException -> 0x0207 }
        r7.writeBytes(r2);	 Catch:{ IOException -> 0x0207 }
        r7.writeBytes(r9);	 Catch:{ IOException -> 0x0207 }
        r5 = new java.io.FileInputStream;	 Catch:{ IOException -> 0x0207 }
        r5.<init>(r13);	 Catch:{ IOException -> 0x0207 }
        r2 = r5.available();	 Catch:{ IOException -> 0x0313, all -> 0x030f }
        r6 = 262144; // 0x40000 float:3.67342E-40 double:1.295163E-318;	 Catch:{ IOException -> 0x0313, all -> 0x030f }
        r4 = java.lang.Math.min(r2, r6);	 Catch:{ IOException -> 0x0313, all -> 0x030f }
        r8 = new byte[r4];	 Catch:{ IOException -> 0x0313, all -> 0x030f }
        r2 = 0;	 Catch:{ IOException -> 0x0313, all -> 0x030f }
        r2 = r5.read(r8, r2, r4);	 Catch:{ IOException -> 0x0313, all -> 0x030f }
    L_0x01dd:
        if (r2 <= 0) goto L_0x01f1;	 Catch:{ IOException -> 0x0313, all -> 0x030f }
    L_0x01df:
        r2 = 0;	 Catch:{ IOException -> 0x0313, all -> 0x030f }
        r7.write(r8, r2, r4);	 Catch:{ IOException -> 0x0313, all -> 0x030f }
        r2 = r5.available();	 Catch:{ IOException -> 0x0313, all -> 0x030f }
        r4 = java.lang.Math.min(r2, r6);	 Catch:{ IOException -> 0x0313, all -> 0x030f }
        r2 = 0;	 Catch:{ IOException -> 0x0313, all -> 0x030f }
        r2 = r5.read(r8, r2, r4);	 Catch:{ IOException -> 0x0313, all -> 0x030f }
        goto L_0x01dd;
    L_0x01f1:
        com.apptentive.android.sdk.util.Util.ensureClosed(r5);	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r7.writeBytes(r9);	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        goto L_0x0128;
    L_0x01f9:
        r1 = move-exception;
    L_0x01fa:
        r2 = "Error constructing url for file upload.";	 Catch:{ all -> 0x02e3 }
        r4 = 0;	 Catch:{ all -> 0x02e3 }
        r4 = new java.lang.Object[r4];	 Catch:{ all -> 0x02e3 }
        com.apptentive.android.sdk.ApptentiveLog.e(r2, r1, r4);	 Catch:{ all -> 0x02e3 }
        com.apptentive.android.sdk.util.Util.ensureClosed(r7);
        goto L_0x016a;
    L_0x0207:
        r2 = move-exception;
    L_0x0208:
        r5 = "Error writing file bytes to HTTP connection.";	 Catch:{ all -> 0x0215 }
        r6 = 0;	 Catch:{ all -> 0x0215 }
        r6 = new java.lang.Object[r6];	 Catch:{ all -> 0x0215 }
        com.apptentive.android.sdk.ApptentiveLog.d(r5, r2, r6);	 Catch:{ all -> 0x0215 }
        r5 = 1;	 Catch:{ all -> 0x0215 }
        r3.setBadPayload(r5);	 Catch:{ all -> 0x0215 }
        throw r2;	 Catch:{ all -> 0x0215 }
    L_0x0215:
        r2 = move-exception;
    L_0x0216:
        com.apptentive.android.sdk.util.Util.ensureClosed(r4);	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        throw r2;	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
    L_0x021a:
        r1 = move-exception;
    L_0x021b:
        r1 = "Timeout communicating with server.";	 Catch:{ all -> 0x02e3 }
        r2 = 0;	 Catch:{ all -> 0x02e3 }
        r2 = new java.lang.Object[r2];	 Catch:{ all -> 0x02e3 }
        com.apptentive.android.sdk.ApptentiveLog.w(r1, r2);	 Catch:{ all -> 0x02e3 }
        com.apptentive.android.sdk.util.Util.ensureClosed(r7);
        goto L_0x016a;
    L_0x0228:
        r2 = new java.lang.StringBuilder;	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r2.<init>();	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r2 = r2.append(r10);	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r2 = r2.append(r11);	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r2 = r2.append(r10);	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r2 = r2.append(r9);	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r2 = r2.toString();	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r7.writeBytes(r2);	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r7.flush();	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r7.close();	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r2 = r1.getResponseCode();	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r3.setCode(r2);	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r2 = r1.getResponseMessage();	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r3.setReason(r2);	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r4 = 0;
        r5 = 0;
        r6 = r1.getInputStream();	 Catch:{ all -> 0x0302 }
        r4 = new java.io.ByteArrayOutputStream;	 Catch:{ all -> 0x030a }
        r4.<init>();	 Catch:{ all -> 0x030a }
        r2 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r2 = new byte[r2];	 Catch:{ all -> 0x0277 }
    L_0x0267:
        if (r6 == 0) goto L_0x029e;	 Catch:{ all -> 0x0277 }
    L_0x0269:
        r5 = 0;	 Catch:{ all -> 0x0277 }
        r8 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;	 Catch:{ all -> 0x0277 }
        r5 = r6.read(r2, r5, r8);	 Catch:{ all -> 0x0277 }
        if (r5 <= 0) goto L_0x029e;	 Catch:{ all -> 0x0277 }
    L_0x0272:
        r8 = 0;	 Catch:{ all -> 0x0277 }
        r4.write(r2, r8, r5);	 Catch:{ all -> 0x0277 }
        goto L_0x0267;
    L_0x0277:
        r2 = move-exception;
        r5 = r6;
    L_0x0279:
        com.apptentive.android.sdk.util.Util.ensureClosed(r5);	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        com.apptentive.android.sdk.util.Util.ensureClosed(r4);	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        throw r2;	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
    L_0x0280:
        r2 = move-exception;
        r16 = r2;
        r2 = r1;
        r1 = r16;
    L_0x0286:
        r4 = "Error executing file upload.";	 Catch:{ all -> 0x02e3 }
        r5 = 0;	 Catch:{ all -> 0x02e3 }
        r5 = new java.lang.Object[r5];	 Catch:{ all -> 0x02e3 }
        com.apptentive.android.sdk.ApptentiveLog.e(r4, r1, r5);	 Catch:{ all -> 0x02e3 }
        r1 = r3.isZipped();	 Catch:{ IOException -> 0x02d9 }
        r1 = getErrorResponse(r2, r1);	 Catch:{ IOException -> 0x02d9 }
        r3.setContent(r1);	 Catch:{ IOException -> 0x02d9 }
    L_0x0299:
        com.apptentive.android.sdk.util.Util.ensureClosed(r7);
        goto L_0x016a;
    L_0x029e:
        r2 = r4.toString();	 Catch:{ all -> 0x0277 }
        r3.setContent(r2);	 Catch:{ all -> 0x0277 }
        com.apptentive.android.sdk.util.Util.ensureClosed(r6);	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        com.apptentive.android.sdk.util.Util.ensureClosed(r4);	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r2 = "HTTP %d: %s";	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r4 = 2;	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r4 = new java.lang.Object[r4];	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r5 = 0;	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r6 = r1.getResponseCode();	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r6 = java.lang.Integer.valueOf(r6);	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r4[r5] = r6;	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r5 = 1;	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r6 = r1.getResponseMessage();	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r4[r5] = r6;	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        com.apptentive.android.sdk.ApptentiveLog.d(r2, r4);	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r2 = "Response: %s";	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r4 = 1;	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r4 = new java.lang.Object[r4];	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r5 = 0;	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r6 = r3.getContent();	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        r4[r5] = r6;	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        com.apptentive.android.sdk.ApptentiveLog.v(r2, r4);	 Catch:{ FileNotFoundException -> 0x015d, MalformedURLException -> 0x01f9, SocketTimeoutException -> 0x021a, IOException -> 0x0280 }
        com.apptentive.android.sdk.util.Util.ensureClosed(r7);
        goto L_0x016a;
    L_0x02d9:
        r1 = move-exception;
        r2 = "Can't read error stream.";	 Catch:{ all -> 0x02e3 }
        r4 = 0;	 Catch:{ all -> 0x02e3 }
        r4 = new java.lang.Object[r4];	 Catch:{ all -> 0x02e3 }
        com.apptentive.android.sdk.ApptentiveLog.w(r2, r1, r4);	 Catch:{ all -> 0x02e3 }
        goto L_0x0299;
    L_0x02e3:
        r1 = move-exception;
    L_0x02e4:
        com.apptentive.android.sdk.util.Util.ensureClosed(r7);
        throw r1;
    L_0x02e8:
        r1 = move-exception;
        r7 = r2;
        goto L_0x02e4;
    L_0x02eb:
        r1 = move-exception;
        r7 = r2;
        goto L_0x02e4;
    L_0x02ee:
        r1 = move-exception;
        r7 = r2;
        r2 = r4;
        goto L_0x0286;
    L_0x02f2:
        r4 = move-exception;
        r7 = r2;
        r2 = r1;
        r1 = r4;
        goto L_0x0286;
    L_0x02f7:
        r1 = move-exception;
        r7 = r2;
        goto L_0x021b;
    L_0x02fb:
        r1 = move-exception;
        r7 = r2;
        goto L_0x01fa;
    L_0x02ff:
        r1 = move-exception;
        goto L_0x015f;
    L_0x0302:
        r2 = move-exception;
        r16 = r5;
        r5 = r4;
        r4 = r16;
        goto L_0x0279;
    L_0x030a:
        r2 = move-exception;
        r4 = r5;
        r5 = r6;
        goto L_0x0279;
    L_0x030f:
        r2 = move-exception;
        r4 = r5;
        goto L_0x0216;
    L_0x0313:
        r2 = move-exception;
        r4 = r5;
        goto L_0x0208;
    L_0x0317:
        r5 = r6;
        goto L_0x0199;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.apptentive.android.sdk.comm.ApptentiveClient.performMultipartFilePost(java.lang.String, java.lang.String, java.lang.String, java.util.List):com.apptentive.android.sdk.comm.ApptentiveHttpResponse");
    }

    public static String getUserAgentString() {
        return String.format(USER_AGENT_STRING, new Object[]{BuildConfig.VERSION_NAME});
    }

    private static String getEndpointBase() {
        SharedPreferences sharedPrefs = ApptentiveInternal.getInstance().getSharedPrefs();
        String string = sharedPrefs.getString("serverUrl", null);
        if (string != null) {
            return string;
        }
        string = "https://api.apptentive.com";
        sharedPrefs.edit().putString("serverUrl", string).apply();
        return string;
    }

    public static String getResponse(HttpURLConnection httpURLConnection, boolean z) throws IOException {
        Closeable inputStream;
        Throwable th;
        String str = null;
        if (httpURLConnection != null) {
            try {
                inputStream = httpURLConnection.getInputStream();
                if (inputStream != null) {
                    if (z) {
                        try {
                            inputStream = new GZIPInputStream(inputStream);
                        } catch (Throwable th2) {
                            th = th2;
                            Util.ensureClosed(inputStream);
                            throw th;
                        }
                    }
                    str = Util.readStringFromInputStream(inputStream, Constants.ENCODING);
                    Util.ensureClosed(inputStream);
                } else {
                    Util.ensureClosed(inputStream);
                }
            } catch (Throwable th3) {
                Throwable th4 = th3;
                inputStream = null;
                th = th4;
                Util.ensureClosed(inputStream);
                throw th;
            }
        }
        return str;
    }

    public static String getErrorResponse(HttpURLConnection httpURLConnection, boolean z) throws IOException {
        Throwable th;
        String str = null;
        if (httpURLConnection != null) {
            Closeable errorStream;
            try {
                errorStream = httpURLConnection.getErrorStream();
                if (errorStream != null && z) {
                    try {
                        errorStream = new GZIPInputStream(errorStream);
                    } catch (Throwable th2) {
                        th = th2;
                        Util.ensureClosed(errorStream);
                        throw th;
                    }
                }
                str = Util.readStringFromInputStream(errorStream, Constants.ENCODING);
                Util.ensureClosed(errorStream);
            } catch (Throwable th3) {
                Throwable th4 = th3;
                errorStream = null;
                th = th4;
                Util.ensureClosed(errorStream);
                throw th;
            }
        }
        return str;
    }
}
