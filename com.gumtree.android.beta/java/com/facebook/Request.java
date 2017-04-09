package com.facebook;

import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import com.apptentive.android.sdk.BuildConfig;
import com.facebook.RequestBatch.Callback;
import com.facebook.RequestBatch.OnProgressCallback;
import com.facebook.internal.AttributionIdentifiers;
import com.facebook.internal.Logger;
import com.facebook.internal.ServerProtocol;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import com.facebook.model.GraphMultiResult;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphObjectList;
import com.facebook.model.GraphPlace;
import com.facebook.model.GraphUser;
import com.facebook.model.OpenGraphAction;
import com.facebook.model.OpenGraphObject;
import com.facebook.model.OpenGraphObject.Factory;
import com.gumtree.android.common.activities.StatefulActivity;
import com.gumtree.android.common.http.CAPIHttpIntentService;
import com.gumtree.android.postad.services.converter.PaymentConverter;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPOutputStream;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Request {
    private static final String ACCEPT_LANGUAGE_HEADER = "Accept-Language";
    private static final String ACCESS_TOKEN_PARAM = "access_token";
    private static final String ATTACHED_FILES_PARAM = "attached_files";
    private static final String ATTACHMENT_FILENAME_PREFIX = "file";
    private static final String BATCH_APP_ID_PARAM = "batch_app_id";
    private static final String BATCH_BODY_PARAM = "body";
    private static final String BATCH_ENTRY_DEPENDS_ON_PARAM = "depends_on";
    private static final String BATCH_ENTRY_NAME_PARAM = "name";
    private static final String BATCH_ENTRY_OMIT_RESPONSE_ON_SUCCESS_PARAM = "omit_response_on_success";
    private static final String BATCH_METHOD_PARAM = "method";
    private static final String BATCH_PARAM = "batch";
    private static final String BATCH_RELATIVE_URL_PARAM = "relative_url";
    private static final String CONTENT_ENCODING_HEADER = "Content-Encoding";
    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final String FORMAT_JSON = "json";
    private static final String FORMAT_PARAM = "format";
    private static final String ISO_8601_FORMAT_STRING = "yyyy-MM-dd'T'HH:mm:ssZ";
    public static final int MAXIMUM_BATCH_SIZE = 50;
    private static final String ME = "me";
    private static final String MIME_BOUNDARY = "3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f";
    private static final String MY_ACTION_FORMAT = "me/%s";
    private static final String MY_FEED = "me/feed";
    private static final String MY_FRIENDS = "me/friends";
    private static final String MY_OBJECTS_FORMAT = "me/objects/%s";
    private static final String MY_PHOTOS = "me/photos";
    private static final String MY_STAGING_RESOURCES = "me/staging_resources";
    private static final String MY_VIDEOS = "me/videos";
    private static final String OBJECT_PARAM = "object";
    private static final String PICTURE_PARAM = "picture";
    private static final String SDK_ANDROID = "android";
    private static final String SDK_PARAM = "sdk";
    private static final String SEARCH = "search";
    private static final String STAGING_PARAM = "file";
    public static final String TAG = Request.class.getSimpleName();
    private static final String USER_AGENT_BASE = "FBAndroidSDK";
    private static final String USER_AGENT_HEADER = "User-Agent";
    private static final String VIDEOS_SUFFIX = "/videos";
    private static String defaultBatchApplicationId;
    private static volatile String userAgent;
    private static Pattern versionPattern = Pattern.compile("^/?v\\d+\\.\\d+/(.*)");
    private String batchEntryDependsOn;
    private String batchEntryName;
    private boolean batchEntryOmitResultOnSuccess;
    private Callback callback;
    private GraphObject graphObject;
    private String graphPath;
    private HttpMethod httpMethod;
    private String overriddenURL;
    private Bundle parameters;
    private Session session;
    private boolean skipClientToken;
    private Object tag;
    private String version;

    public Request() {
        this(null, null, null, null, null);
    }

    public Request(Session session, String str) {
        this(session, str, null, null, null);
    }

    public Request(Session session, String str, Bundle bundle, HttpMethod httpMethod) {
        this(session, str, bundle, httpMethod, null);
    }

    public Request(Session session, String str, Bundle bundle, HttpMethod httpMethod, Callback callback) {
        this(session, str, bundle, httpMethod, callback, null);
    }

    public Request(Session session, String str, Bundle bundle, HttpMethod httpMethod, Callback callback, String str2) {
        this.batchEntryOmitResultOnSuccess = true;
        this.skipClientToken = false;
        this.session = session;
        this.graphPath = str;
        this.callback = callback;
        this.version = str2;
        setHttpMethod(httpMethod);
        if (bundle != null) {
            this.parameters = new Bundle(bundle);
        } else {
            this.parameters = new Bundle();
        }
        if (this.version == null) {
            this.version = ServerProtocol.getAPIVersion();
        }
    }

    Request(Session session, URL url) {
        this.batchEntryOmitResultOnSuccess = true;
        this.skipClientToken = false;
        this.session = session;
        this.overriddenURL = url.toString();
        setHttpMethod(HttpMethod.GET);
        this.parameters = new Bundle();
    }

    public static Request newPostRequest(Session session, String str, GraphObject graphObject, Callback callback) {
        Request request = new Request(session, str, null, HttpMethod.POST, callback);
        request.setGraphObject(graphObject);
        return request;
    }

    public static Request newMeRequest(Session session, GraphUserCallback graphUserCallback) {
        return new Request(session, ME, null, null, new 1(graphUserCallback));
    }

    public static Request newMyFriendsRequest(Session session, GraphUserListCallback graphUserListCallback) {
        return new Request(session, MY_FRIENDS, null, null, new 2(graphUserListCallback));
    }

    public static Request newUploadPhotoRequest(Session session, Bitmap bitmap, Callback callback) {
        Bundle bundle = new Bundle(1);
        bundle.putParcelable(PICTURE_PARAM, bitmap);
        return new Request(session, MY_PHOTOS, bundle, HttpMethod.POST, callback);
    }

    public static Request newUploadPhotoRequest(Session session, File file, Callback callback) throws FileNotFoundException {
        Parcelable open = ParcelFileDescriptor.open(file, 268435456);
        Bundle bundle = new Bundle(1);
        bundle.putParcelable(PICTURE_PARAM, open);
        return new Request(session, MY_PHOTOS, bundle, HttpMethod.POST, callback);
    }

    public static Request newUploadVideoRequest(Session session, File file, Callback callback) throws FileNotFoundException {
        Parcelable open = ParcelFileDescriptor.open(file, 268435456);
        Bundle bundle = new Bundle(1);
        bundle.putParcelable(file.getName(), open);
        return new Request(session, MY_VIDEOS, bundle, HttpMethod.POST, callback);
    }

    public static Request newGraphPathRequest(Session session, String str, Callback callback) {
        return new Request(session, str, null, null, callback);
    }

    public static Request newPlacesSearchRequest(Session session, Location location, int i, int i2, String str, GraphPlaceListCallback graphPlaceListCallback) {
        if (location == null && Utility.isNullOrEmpty(str)) {
            throw new FacebookException("Either location or searchText must be specified.");
        }
        Bundle bundle = new Bundle(5);
        bundle.putString("type", "place");
        bundle.putInt("limit", i2);
        if (location != null) {
            bundle.putString("center", String.format(Locale.US, "%f,%f", new Object[]{Double.valueOf(location.getLatitude()), Double.valueOf(location.getLongitude())}));
            bundle.putInt(StatefulActivity.NAME_DISTANCE, i);
        }
        if (!Utility.isNullOrEmpty(str)) {
            bundle.putString(StatefulActivity.NAME_QUERY, str);
        }
        return new Request(session, SEARCH, bundle, HttpMethod.GET, new 3(graphPlaceListCallback));
    }

    public static Request newStatusUpdateRequest(Session session, String str, Callback callback) {
        return newStatusUpdateRequest(session, str, (String) null, null, callback);
    }

    private static Request newStatusUpdateRequest(Session session, String str, String str2, List<String> list, Callback callback) {
        Bundle bundle = new Bundle();
        bundle.putString("message", str);
        if (str2 != null) {
            bundle.putString("place", str2);
        }
        if (list != null && list.size() > 0) {
            bundle.putString("tags", TextUtils.join(",", list));
        }
        return new Request(session, MY_FEED, bundle, HttpMethod.POST, callback);
    }

    public static Request newStatusUpdateRequest(Session session, String str, GraphPlace graphPlace, List<GraphUser> list, Callback callback) {
        List arrayList;
        if (list != null) {
            arrayList = new ArrayList(list.size());
            for (GraphUser id : list) {
                arrayList.add(id.getId());
            }
        } else {
            arrayList = null;
        }
        return newStatusUpdateRequest(session, str, graphPlace == null ? null : graphPlace.getId(), arrayList, callback);
    }

    public static Request newCustomAudienceThirdPartyIdRequest(Session session, Context context, Callback callback) {
        return newCustomAudienceThirdPartyIdRequest(session, context, null, callback);
    }

    public static Request newCustomAudienceThirdPartyIdRequest(Session session, Context context, String str, Callback callback) {
        Session activeSession;
        if (session == null) {
            activeSession = Session.getActiveSession();
        } else {
            activeSession = session;
        }
        if (!(activeSession == null || activeSession.isOpened())) {
            activeSession = null;
        }
        if (str == null) {
            if (activeSession != null) {
                str = activeSession.getApplicationId();
            } else {
                str = Utility.getMetadataApplicationId(context);
            }
        }
        if (str == null) {
            throw new FacebookException("Facebook App ID cannot be determined");
        }
        String str2 = str + "/custom_audience_third_party_id";
        AttributionIdentifiers attributionIdentifiers = AttributionIdentifiers.getAttributionIdentifiers(context);
        Bundle bundle = new Bundle();
        if (activeSession == null) {
            String attributionId = attributionIdentifiers.getAttributionId() != null ? attributionIdentifiers.getAttributionId() : attributionIdentifiers.getAndroidAdvertiserId();
            if (attributionIdentifiers.getAttributionId() != null) {
                bundle.putString("udid", attributionId);
            }
        }
        if (Settings.getLimitEventAndDataUsage(context) || attributionIdentifiers.isTrackingLimited()) {
            bundle.putString("limit_event_usage", PaymentConverter.DEFAULT_PAYMENT_METHOD_ID);
        }
        return new Request(activeSession, str2, bundle, HttpMethod.GET, callback);
    }

    public static Request newUploadStagingResourceWithImageRequest(Session session, Bitmap bitmap, Callback callback) {
        Bundle bundle = new Bundle(1);
        bundle.putParcelable(STAGING_PARAM, bitmap);
        return new Request(session, MY_STAGING_RESOURCES, bundle, HttpMethod.POST, callback);
    }

    public static Request newUploadStagingResourceWithImageRequest(Session session, File file, Callback callback) throws FileNotFoundException {
        ParcelFileDescriptorWithMimeType parcelFileDescriptorWithMimeType = new ParcelFileDescriptorWithMimeType(ParcelFileDescriptor.open(file, 268435456), "image/png");
        Bundle bundle = new Bundle(1);
        bundle.putParcelable(STAGING_PARAM, parcelFileDescriptorWithMimeType);
        return new Request(session, MY_STAGING_RESOURCES, bundle, HttpMethod.POST, callback);
    }

    public static Request newPostOpenGraphObjectRequest(Session session, OpenGraphObject openGraphObject, Callback callback) {
        if (openGraphObject == null) {
            throw new FacebookException("openGraphObject cannot be null");
        } else if (Utility.isNullOrEmpty(openGraphObject.getType())) {
            throw new FacebookException("openGraphObject must have non-null 'type' property");
        } else if (Utility.isNullOrEmpty(openGraphObject.getTitle())) {
            throw new FacebookException("openGraphObject must have non-null 'title' property");
        } else {
            String format = String.format(MY_OBJECTS_FORMAT, new Object[]{openGraphObject.getType()});
            Bundle bundle = new Bundle();
            bundle.putString(OBJECT_PARAM, openGraphObject.getInnerJSONObject().toString());
            return new Request(session, format, bundle, HttpMethod.POST, callback);
        }
    }

    public static Request newPostOpenGraphObjectRequest(Session session, String str, String str2, String str3, String str4, String str5, GraphObject graphObject, Callback callback) {
        OpenGraphObject createForPost = Factory.createForPost(OpenGraphObject.class, str, str2, str3, str4, str5);
        if (graphObject != null) {
            createForPost.setData(graphObject);
        }
        return newPostOpenGraphObjectRequest(session, createForPost, callback);
    }

    public static Request newPostOpenGraphActionRequest(Session session, OpenGraphAction openGraphAction, Callback callback) {
        if (openGraphAction == null) {
            throw new FacebookException("openGraphAction cannot be null");
        } else if (Utility.isNullOrEmpty(openGraphAction.getType())) {
            throw new FacebookException("openGraphAction must have non-null 'type' property");
        } else {
            return newPostRequest(session, String.format(MY_ACTION_FORMAT, new Object[]{openGraphAction.getType()}), openGraphAction, callback);
        }
    }

    public static Request newDeleteObjectRequest(Session session, String str, Callback callback) {
        return new Request(session, str, null, HttpMethod.DELETE, callback);
    }

    public static Request newUpdateOpenGraphObjectRequest(Session session, OpenGraphObject openGraphObject, Callback callback) {
        if (openGraphObject == null) {
            throw new FacebookException("openGraphObject cannot be null");
        }
        String id = openGraphObject.getId();
        if (id == null) {
            throw new FacebookException("openGraphObject must have an id");
        }
        Bundle bundle = new Bundle();
        bundle.putString(OBJECT_PARAM, openGraphObject.getInnerJSONObject().toString());
        return new Request(session, id, bundle, HttpMethod.POST, callback);
    }

    public static Request newUpdateOpenGraphObjectRequest(Session session, String str, String str2, String str3, String str4, String str5, GraphObject graphObject, Callback callback) {
        OpenGraphObject createForPost = Factory.createForPost(OpenGraphObject.class, null, str2, str3, str4, str5);
        createForPost.setId(str);
        createForPost.setData(graphObject);
        return newUpdateOpenGraphObjectRequest(session, createForPost, callback);
    }

    public final GraphObject getGraphObject() {
        return this.graphObject;
    }

    public final void setGraphObject(GraphObject graphObject) {
        this.graphObject = graphObject;
    }

    public final String getGraphPath() {
        return this.graphPath;
    }

    public final void setGraphPath(String str) {
        this.graphPath = str;
    }

    public final HttpMethod getHttpMethod() {
        return this.httpMethod;
    }

    public final void setHttpMethod(HttpMethod httpMethod) {
        if (this.overriddenURL == null || httpMethod == HttpMethod.GET) {
            if (httpMethod == null) {
                httpMethod = HttpMethod.GET;
            }
            this.httpMethod = httpMethod;
            return;
        }
        throw new FacebookException("Can't change HTTP method on request with overridden URL.");
    }

    public final String getVersion() {
        return this.version;
    }

    public final void setVersion(String str) {
        this.version = str;
    }

    public final void setSkipClientToken(boolean z) {
        this.skipClientToken = z;
    }

    public final Bundle getParameters() {
        return this.parameters;
    }

    public final void setParameters(Bundle bundle) {
        this.parameters = bundle;
    }

    public final Session getSession() {
        return this.session;
    }

    public final void setSession(Session session) {
        this.session = session;
    }

    public final String getBatchEntryName() {
        return this.batchEntryName;
    }

    public final void setBatchEntryName(String str) {
        this.batchEntryName = str;
    }

    public final String getBatchEntryDependsOn() {
        return this.batchEntryDependsOn;
    }

    public final void setBatchEntryDependsOn(String str) {
        this.batchEntryDependsOn = str;
    }

    public final boolean getBatchEntryOmitResultOnSuccess() {
        return this.batchEntryOmitResultOnSuccess;
    }

    public final void setBatchEntryOmitResultOnSuccess(boolean z) {
        this.batchEntryOmitResultOnSuccess = z;
    }

    public static final String getDefaultBatchApplicationId() {
        return defaultBatchApplicationId;
    }

    public static final void setDefaultBatchApplicationId(String str) {
        defaultBatchApplicationId = str;
    }

    public final Callback getCallback() {
        return this.callback;
    }

    public final void setCallback(Callback callback) {
        this.callback = callback;
    }

    public final void setTag(Object obj) {
        this.tag = obj;
    }

    public final Object getTag() {
        return this.tag;
    }

    @Deprecated
    public static RequestAsyncTask executePostRequestAsync(Session session, String str, GraphObject graphObject, Callback callback) {
        return newPostRequest(session, str, graphObject, callback).executeAsync();
    }

    @Deprecated
    public static RequestAsyncTask executeMeRequestAsync(Session session, GraphUserCallback graphUserCallback) {
        return newMeRequest(session, graphUserCallback).executeAsync();
    }

    @Deprecated
    public static RequestAsyncTask executeMyFriendsRequestAsync(Session session, GraphUserListCallback graphUserListCallback) {
        return newMyFriendsRequest(session, graphUserListCallback).executeAsync();
    }

    @Deprecated
    public static RequestAsyncTask executeUploadPhotoRequestAsync(Session session, Bitmap bitmap, Callback callback) {
        return newUploadPhotoRequest(session, bitmap, callback).executeAsync();
    }

    @Deprecated
    public static RequestAsyncTask executeUploadPhotoRequestAsync(Session session, File file, Callback callback) throws FileNotFoundException {
        return newUploadPhotoRequest(session, file, callback).executeAsync();
    }

    @Deprecated
    public static RequestAsyncTask executeGraphPathRequestAsync(Session session, String str, Callback callback) {
        return newGraphPathRequest(session, str, callback).executeAsync();
    }

    @Deprecated
    public static RequestAsyncTask executePlacesSearchRequestAsync(Session session, Location location, int i, int i2, String str, GraphPlaceListCallback graphPlaceListCallback) {
        return newPlacesSearchRequest(session, location, i, i2, str, graphPlaceListCallback).executeAsync();
    }

    @Deprecated
    public static RequestAsyncTask executeStatusUpdateRequestAsync(Session session, String str, Callback callback) {
        return newStatusUpdateRequest(session, str, callback).executeAsync();
    }

    public final Response executeAndWait() {
        return executeAndWait(this);
    }

    public final RequestAsyncTask executeAsync() {
        return executeBatchAsync(this);
    }

    public static HttpURLConnection toHttpConnection(Request... requestArr) {
        return toHttpConnection(Arrays.asList(requestArr));
    }

    public static HttpURLConnection toHttpConnection(Collection<Request> collection) {
        Validate.notEmptyAndContainsNoNulls(collection, "requests");
        return toHttpConnection(new RequestBatch(collection));
    }

    public static HttpURLConnection toHttpConnection(RequestBatch requestBatch) {
        try {
            URL url;
            if (requestBatch.size() == 1) {
                url = new URL(requestBatch.get(0).getUrlForSingleRequest());
            } else {
                url = new URL(ServerProtocol.getGraphUrlBase());
            }
            try {
                HttpURLConnection createConnection = createConnection(url);
                serializeToUrlConnection(requestBatch, createConnection);
                return createConnection;
            } catch (Throwable e) {
                throw new FacebookException("could not construct request body", e);
            } catch (Throwable e2) {
                throw new FacebookException("could not construct request body", e2);
            }
        } catch (Throwable e22) {
            throw new FacebookException("could not construct URL for request", e22);
        }
    }

    public static Response executeAndWait(Request request) {
        List executeBatchAndWait = executeBatchAndWait(request);
        if (executeBatchAndWait != null && executeBatchAndWait.size() == 1) {
            return (Response) executeBatchAndWait.get(0);
        }
        throw new FacebookException("invalid state: expected a single response");
    }

    public static List<Response> executeBatchAndWait(Request... requestArr) {
        Validate.notNull(requestArr, "requests");
        return executeBatchAndWait(Arrays.asList(requestArr));
    }

    public static List<Response> executeBatchAndWait(Collection<Request> collection) {
        return executeBatchAndWait(new RequestBatch(collection));
    }

    public static List<Response> executeBatchAndWait(RequestBatch requestBatch) {
        Validate.notEmptyAndContainsNoNulls(requestBatch, "requests");
        try {
            return executeConnectionAndWait(toHttpConnection(requestBatch), requestBatch);
        } catch (Throwable e) {
            List<Response> constructErrorResponses = Response.constructErrorResponses(requestBatch.getRequests(), null, new FacebookException(e));
            runCallbacks(requestBatch, constructErrorResponses);
            return constructErrorResponses;
        }
    }

    public static RequestAsyncTask executeBatchAsync(Request... requestArr) {
        Validate.notNull(requestArr, "requests");
        return executeBatchAsync(Arrays.asList(requestArr));
    }

    public static RequestAsyncTask executeBatchAsync(Collection<Request> collection) {
        return executeBatchAsync(new RequestBatch(collection));
    }

    public static RequestAsyncTask executeBatchAsync(RequestBatch requestBatch) {
        Validate.notEmptyAndContainsNoNulls(requestBatch, "requests");
        RequestAsyncTask requestAsyncTask = new RequestAsyncTask(requestBatch);
        requestAsyncTask.executeOnSettingsExecutor();
        return requestAsyncTask;
    }

    public static List<Response> executeConnectionAndWait(HttpURLConnection httpURLConnection, Collection<Request> collection) {
        return executeConnectionAndWait(httpURLConnection, new RequestBatch(collection));
    }

    public static List<Response> executeConnectionAndWait(HttpURLConnection httpURLConnection, RequestBatch requestBatch) {
        List<Response> fromHttpConnection = Response.fromHttpConnection(httpURLConnection, requestBatch);
        Utility.disconnectQuietly(httpURLConnection);
        if (requestBatch.size() != fromHttpConnection.size()) {
            throw new FacebookException(String.format("Received %d responses while expecting %d", new Object[]{Integer.valueOf(fromHttpConnection.size()), Integer.valueOf(requestBatch.size())}));
        }
        runCallbacks(requestBatch, fromHttpConnection);
        HashSet hashSet = new HashSet();
        Iterator it = requestBatch.iterator();
        while (it.hasNext()) {
            Request request = (Request) it.next();
            if (request.session != null) {
                hashSet.add(request.session);
            }
        }
        Iterator it2 = hashSet.iterator();
        while (it2.hasNext()) {
            ((Session) it2.next()).extendAccessTokenIfNeeded();
        }
        return fromHttpConnection;
    }

    public static RequestAsyncTask executeConnectionAsync(HttpURLConnection httpURLConnection, RequestBatch requestBatch) {
        return executeConnectionAsync(null, httpURLConnection, requestBatch);
    }

    public static RequestAsyncTask executeConnectionAsync(Handler handler, HttpURLConnection httpURLConnection, RequestBatch requestBatch) {
        Validate.notNull(httpURLConnection, "connection");
        RequestAsyncTask requestAsyncTask = new RequestAsyncTask(httpURLConnection, requestBatch);
        requestBatch.setCallbackHandler(handler);
        requestAsyncTask.executeOnSettingsExecutor();
        return requestAsyncTask;
    }

    public String toString() {
        return "{Request: " + " session: " + this.session + ", graphPath: " + this.graphPath + ", graphObject: " + this.graphObject + ", httpMethod: " + this.httpMethod + ", parameters: " + this.parameters + "}";
    }

    static void runCallbacks(RequestBatch requestBatch, List<Response> list) {
        int size = requestBatch.size();
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < size; i++) {
            Request request = requestBatch.get(i);
            if (request.callback != null) {
                arrayList.add(new Pair(request.callback, list.get(i)));
            }
        }
        if (arrayList.size() > 0) {
            4 4 = new 4(arrayList, requestBatch);
            Handler callbackHandler = requestBatch.getCallbackHandler();
            if (callbackHandler == null) {
                4.run();
            } else {
                callbackHandler.post(4);
            }
        }
    }

    private static HttpURLConnection createConnection(URL url) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestProperty(USER_AGENT_HEADER, getUserAgent());
        httpURLConnection.setRequestProperty(ACCEPT_LANGUAGE_HEADER, Locale.getDefault().toString());
        httpURLConnection.setChunkedStreamingMode(0);
        return httpURLConnection;
    }

    private void addCommonParameters() {
        String accessToken;
        if (this.session != null) {
            if (!this.session.isOpened()) {
                throw new FacebookException("Session provided to a Request in un-opened state.");
            } else if (!this.parameters.containsKey(ACCESS_TOKEN_PARAM)) {
                accessToken = this.session.getAccessToken();
                Logger.registerAccessToken(accessToken);
                this.parameters.putString(ACCESS_TOKEN_PARAM, accessToken);
            }
        } else if (!(this.skipClientToken || this.parameters.containsKey(ACCESS_TOKEN_PARAM))) {
            accessToken = Settings.getApplicationId();
            String clientToken = Settings.getClientToken();
            if (Utility.isNullOrEmpty(accessToken) || Utility.isNullOrEmpty(clientToken)) {
                Log.d(TAG, "Warning: Sessionless Request needs token but missing either application ID or client token.");
            } else {
                this.parameters.putString(ACCESS_TOKEN_PARAM, accessToken + "|" + clientToken);
            }
        }
        this.parameters.putString(SDK_PARAM, SDK_ANDROID);
        this.parameters.putString(FORMAT_PARAM, FORMAT_JSON);
    }

    private String appendParametersToBaseUrl(String str) {
        Builder encodedPath = new Builder().encodedPath(str);
        for (String str2 : this.parameters.keySet()) {
            Object obj = this.parameters.get(str2);
            if (obj == null) {
                obj = BuildConfig.FLAVOR;
            }
            if (isSupportedParameterType(obj)) {
                encodedPath.appendQueryParameter(str2, parameterToString(obj).toString());
            } else if (this.httpMethod == HttpMethod.GET) {
                throw new IllegalArgumentException(String.format("Unsupported parameter type for GET request: %s", new Object[]{obj.getClass().getSimpleName()}));
            }
        }
        return encodedPath.toString();
    }

    final String getUrlForBatchedRequest() {
        if (this.overriddenURL != null) {
            throw new FacebookException("Can't override URL for a batch request");
        }
        String graphPathWithVersion = getGraphPathWithVersion();
        addCommonParameters();
        return appendParametersToBaseUrl(graphPathWithVersion);
    }

    final String getUrlForSingleRequest() {
        if (this.overriddenURL != null) {
            return this.overriddenURL.toString();
        }
        String graphVideoUrlBase;
        if (getHttpMethod() == HttpMethod.POST && this.graphPath != null && this.graphPath.endsWith(VIDEOS_SUFFIX)) {
            graphVideoUrlBase = ServerProtocol.getGraphVideoUrlBase();
        } else {
            graphVideoUrlBase = ServerProtocol.getGraphUrlBase();
        }
        graphVideoUrlBase = String.format("%s/%s", new Object[]{graphVideoUrlBase, getGraphPathWithVersion()});
        addCommonParameters();
        return appendParametersToBaseUrl(graphVideoUrlBase);
    }

    private String getGraphPathWithVersion() {
        if (versionPattern.matcher(this.graphPath).matches()) {
            return this.graphPath;
        }
        return String.format("%s/%s", new Object[]{this.version, this.graphPath});
    }

    private void serializeToBatch(JSONArray jSONArray, Map<String, Attachment> map) throws JSONException, IOException {
        JSONObject jSONObject = new JSONObject();
        if (this.batchEntryName != null) {
            jSONObject.put(BATCH_ENTRY_NAME_PARAM, this.batchEntryName);
            jSONObject.put(BATCH_ENTRY_OMIT_RESPONSE_ON_SUCCESS_PARAM, this.batchEntryOmitResultOnSuccess);
        }
        if (this.batchEntryDependsOn != null) {
            jSONObject.put(BATCH_ENTRY_DEPENDS_ON_PARAM, this.batchEntryDependsOn);
        }
        String urlForBatchedRequest = getUrlForBatchedRequest();
        jSONObject.put(BATCH_RELATIVE_URL_PARAM, urlForBatchedRequest);
        jSONObject.put(BATCH_METHOD_PARAM, this.httpMethod);
        if (this.session != null) {
            Logger.registerAccessToken(this.session.getAccessToken());
        }
        Iterable arrayList = new ArrayList();
        for (String str : this.parameters.keySet()) {
            Object obj = this.parameters.get(str);
            if (isSupportedAttachmentType(obj)) {
                String format = String.format("%s%d", new Object[]{STAGING_PARAM, Integer.valueOf(map.size())});
                arrayList.add(format);
                map.put(format, new Attachment(this, obj));
            }
        }
        if (!arrayList.isEmpty()) {
            jSONObject.put(ATTACHED_FILES_PARAM, TextUtils.join(",", arrayList));
        }
        if (this.graphObject != null) {
            Iterable arrayList2 = new ArrayList();
            processGraphObject(this.graphObject, urlForBatchedRequest, new 5(this, arrayList2));
            jSONObject.put(BATCH_BODY_PARAM, TextUtils.join("&", arrayList2));
        }
        jSONArray.put(jSONObject);
    }

    private static boolean hasOnProgressCallbacks(RequestBatch requestBatch) {
        for (Callback callback : requestBatch.getCallbacks()) {
            if (callback instanceof OnProgressCallback) {
                return true;
            }
        }
        Iterator it = requestBatch.iterator();
        while (it.hasNext()) {
            if (((Request) it.next()).getCallback() instanceof OnProgressCallback) {
                return true;
            }
        }
        return false;
    }

    private static void setConnectionContentType(HttpURLConnection httpURLConnection, boolean z) {
        if (z) {
            httpURLConnection.setRequestProperty(CONTENT_TYPE_HEADER, "application/x-www-form-urlencoded");
            httpURLConnection.setRequestProperty(CONTENT_ENCODING_HEADER, "gzip");
            return;
        }
        httpURLConnection.setRequestProperty(CONTENT_TYPE_HEADER, getMimeContentType());
    }

    private static boolean isGzipCompressible(RequestBatch requestBatch) {
        Iterator it = requestBatch.iterator();
        while (it.hasNext()) {
            Request request = (Request) it.next();
            for (String str : request.parameters.keySet()) {
                if (isSupportedAttachmentType(request.parameters.get(str))) {
                    return false;
                }
            }
        }
        return true;
    }

    static final void serializeToUrlConnection(RequestBatch requestBatch, HttpURLConnection httpURLConnection) throws IOException, JSONException {
        OutputStream bufferedOutputStream;
        Throwable th;
        Logger logger = new Logger(LoggingBehavior.REQUESTS, "Request");
        int size = requestBatch.size();
        boolean isGzipCompressible = isGzipCompressible(requestBatch);
        HttpMethod httpMethod = size == 1 ? requestBatch.get(0).httpMethod : HttpMethod.POST;
        httpURLConnection.setRequestMethod(httpMethod.name());
        setConnectionContentType(httpURLConnection, isGzipCompressible);
        URL url = httpURLConnection.getURL();
        logger.append("Request:\n");
        logger.appendKeyValue("Id", requestBatch.getId());
        logger.appendKeyValue(CAPIHttpIntentService.URL, url);
        logger.appendKeyValue("Method", httpURLConnection.getRequestMethod());
        logger.appendKeyValue(USER_AGENT_HEADER, httpURLConnection.getRequestProperty(USER_AGENT_HEADER));
        logger.appendKeyValue(CONTENT_TYPE_HEADER, httpURLConnection.getRequestProperty(CONTENT_TYPE_HEADER));
        httpURLConnection.setConnectTimeout(requestBatch.getTimeout());
        httpURLConnection.setReadTimeout(requestBatch.getTimeout());
        if (httpMethod == HttpMethod.POST) {
            httpURLConnection.setDoOutput(true);
            try {
                OutputStream progressOutputStream;
                bufferedOutputStream = new BufferedOutputStream(httpURLConnection.getOutputStream());
                if (isGzipCompressible) {
                    try {
                        bufferedOutputStream = new GZIPOutputStream(bufferedOutputStream);
                    } catch (Throwable th2) {
                        th = th2;
                        if (bufferedOutputStream != null) {
                            bufferedOutputStream.close();
                        }
                        throw th;
                    }
                }
                if (hasOnProgressCallbacks(requestBatch)) {
                    ProgressNoopOutputStream progressNoopOutputStream = new ProgressNoopOutputStream(requestBatch.getCallbackHandler());
                    processRequest(requestBatch, null, size, url, progressNoopOutputStream, isGzipCompressible);
                    progressOutputStream = new ProgressOutputStream(bufferedOutputStream, requestBatch, progressNoopOutputStream.getProgressMap(), (long) progressNoopOutputStream.getMaxProgress());
                } else {
                    progressOutputStream = bufferedOutputStream;
                }
                try {
                    processRequest(requestBatch, logger, size, url, progressOutputStream, isGzipCompressible);
                    if (progressOutputStream != null) {
                        progressOutputStream.close();
                    }
                    logger.log();
                    return;
                } catch (Throwable th3) {
                    th = th3;
                    bufferedOutputStream = progressOutputStream;
                    if (bufferedOutputStream != null) {
                        bufferedOutputStream.close();
                    }
                    throw th;
                }
            } catch (Throwable th4) {
                th = th4;
                bufferedOutputStream = null;
                if (bufferedOutputStream != null) {
                    bufferedOutputStream.close();
                }
                throw th;
            }
        }
        logger.log();
    }

    private static void processRequest(RequestBatch requestBatch, Logger logger, int i, URL url, OutputStream outputStream, boolean z) throws IOException, JSONException {
        Serializer serializer = new Serializer(outputStream, logger, z);
        String batchAppId;
        if (i == 1) {
            Request request = requestBatch.get(0);
            Map hashMap = new HashMap();
            for (String batchAppId2 : request.parameters.keySet()) {
                Object obj = request.parameters.get(batchAppId2);
                if (isSupportedAttachmentType(obj)) {
                    hashMap.put(batchAppId2, new Attachment(request, obj));
                }
            }
            if (logger != null) {
                logger.append("  Parameters:\n");
            }
            serializeParameters(request.parameters, serializer, request);
            if (logger != null) {
                logger.append("  Attachments:\n");
            }
            serializeAttachments(hashMap, serializer);
            if (request.graphObject != null) {
                processGraphObject(request.graphObject, url.getPath(), serializer);
                return;
            }
            return;
        }
        batchAppId2 = getBatchAppId(requestBatch);
        if (Utility.isNullOrEmpty(batchAppId2)) {
            throw new FacebookException("At least one request in a batch must have an open Session, or a default app ID must be specified.");
        }
        serializer.writeString(BATCH_APP_ID_PARAM, batchAppId2);
        Map hashMap2 = new HashMap();
        serializeRequestsAsJSON(serializer, requestBatch, hashMap2);
        if (logger != null) {
            logger.append("  Attachments:\n");
        }
        serializeAttachments(hashMap2, serializer);
    }

    private static boolean isMeRequest(String str) {
        Matcher matcher = versionPattern.matcher(str);
        if (matcher.matches()) {
            str = matcher.group(1);
        }
        if (str.startsWith("me/") || str.startsWith("/me/")) {
            return true;
        }
        return false;
    }

    private static void processGraphObject(GraphObject graphObject, String str, KeyValueSerializer keyValueSerializer) throws IOException {
        Object obj;
        if (isMeRequest(str)) {
            int indexOf = str.indexOf(":");
            int indexOf2 = str.indexOf("?");
            Object obj2 = (indexOf <= 3 || (indexOf2 != -1 && indexOf >= indexOf2)) ? null : 1;
            obj = obj2;
        } else {
            obj = null;
        }
        for (Entry entry : graphObject.asMap().entrySet()) {
            boolean z;
            if (obj == null || !((String) entry.getKey()).equalsIgnoreCase("image")) {
                z = false;
            } else {
                z = true;
            }
            processGraphObjectProperty((String) entry.getKey(), entry.getValue(), keyValueSerializer, z);
        }
    }

    private static void processGraphObjectProperty(String str, Object obj, KeyValueSerializer keyValueSerializer, boolean z) throws IOException {
        Class cls;
        Object obj2;
        Class cls2 = obj.getClass();
        if (GraphObject.class.isAssignableFrom(cls2)) {
            obj = ((GraphObject) obj).getInnerJSONObject();
            cls = obj.getClass();
            obj2 = obj;
        } else if (GraphObjectList.class.isAssignableFrom(cls2)) {
            obj = ((GraphObjectList) obj).getInnerJSONArray();
            cls = obj.getClass();
            obj2 = obj;
        } else {
            cls = cls2;
            obj2 = obj;
        }
        if (JSONObject.class.isAssignableFrom(cls)) {
            JSONObject jSONObject = (JSONObject) obj2;
            if (z) {
                Iterator keys = jSONObject.keys();
                while (keys.hasNext()) {
                    Object[] objArr = new Object[]{str, (String) keys.next()};
                    processGraphObjectProperty(String.format("%s[%s]", objArr), jSONObject.opt((String) keys.next()), keyValueSerializer, z);
                }
            } else if (jSONObject.has("id")) {
                processGraphObjectProperty(str, jSONObject.optString("id"), keyValueSerializer, z);
            } else if (jSONObject.has("url")) {
                processGraphObjectProperty(str, jSONObject.optString("url"), keyValueSerializer, z);
            } else if (jSONObject.has("fbsdk:create_object")) {
                processGraphObjectProperty(str, jSONObject.toString(), keyValueSerializer, z);
            }
        } else if (JSONArray.class.isAssignableFrom(cls)) {
            JSONArray jSONArray = (JSONArray) obj2;
            int length = jSONArray.length();
            for (int i = 0; i < length; i++) {
                processGraphObjectProperty(String.format("%s[%d]", new Object[]{str, Integer.valueOf(i)}), jSONArray.opt(i), keyValueSerializer, z);
            }
        } else if (String.class.isAssignableFrom(cls) || Number.class.isAssignableFrom(cls) || Boolean.class.isAssignableFrom(cls)) {
            keyValueSerializer.writeString(str, obj2.toString());
        } else if (Date.class.isAssignableFrom(cls)) {
            keyValueSerializer.writeString(str, new SimpleDateFormat(ISO_8601_FORMAT_STRING, Locale.US).format((Date) obj2));
        }
    }

    private static void serializeParameters(Bundle bundle, Serializer serializer, Request request) throws IOException {
        for (String str : bundle.keySet()) {
            Object obj = bundle.get(str);
            if (isSupportedParameterType(obj)) {
                serializer.writeObject(str, obj, request);
            }
        }
    }

    private static void serializeAttachments(Map<String, Attachment> map, Serializer serializer) throws IOException {
        for (String str : map.keySet()) {
            Attachment attachment = (Attachment) map.get(str);
            if (isSupportedAttachmentType(attachment.getValue())) {
                serializer.writeObject(str, attachment.getValue(), attachment.getRequest());
            }
        }
    }

    private static void serializeRequestsAsJSON(Serializer serializer, Collection<Request> collection, Map<String, Attachment> map) throws JSONException, IOException {
        JSONArray jSONArray = new JSONArray();
        for (Request serializeToBatch : collection) {
            serializeToBatch.serializeToBatch(jSONArray, map);
        }
        serializer.writeRequestsAsJson(BATCH_PARAM, jSONArray, collection);
    }

    private static String getMimeContentType() {
        return String.format("multipart/form-data; boundary=%s", new Object[]{MIME_BOUNDARY});
    }

    private static String getUserAgent() {
        if (userAgent == null) {
            userAgent = String.format("%s.%s", new Object[]{USER_AGENT_BASE, "3.23.0"});
        }
        return userAgent;
    }

    private static String getBatchAppId(RequestBatch requestBatch) {
        if (!Utility.isNullOrEmpty(requestBatch.getBatchApplicationId())) {
            return requestBatch.getBatchApplicationId();
        }
        Iterator it = requestBatch.iterator();
        while (it.hasNext()) {
            Session session = ((Request) it.next()).session;
            if (session != null) {
                return session.getApplicationId();
            }
        }
        return defaultBatchApplicationId;
    }

    private static <T extends GraphObject> List<T> typedListFromResponse(Response response, Class<T> cls) {
        GraphMultiResult graphMultiResult = (GraphMultiResult) response.getGraphObjectAs(GraphMultiResult.class);
        if (graphMultiResult == null) {
            return null;
        }
        GraphObjectList data = graphMultiResult.getData();
        if (data == null) {
            return null;
        }
        return data.castToListOf(cls);
    }

    private static boolean isSupportedAttachmentType(Object obj) {
        return (obj instanceof Bitmap) || (obj instanceof byte[]) || (obj instanceof ParcelFileDescriptor) || (obj instanceof ParcelFileDescriptorWithMimeType);
    }

    private static boolean isSupportedParameterType(Object obj) {
        return (obj instanceof String) || (obj instanceof Boolean) || (obj instanceof Number) || (obj instanceof Date);
    }

    private static String parameterToString(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        }
        if ((obj instanceof Boolean) || (obj instanceof Number)) {
            return obj.toString();
        }
        if (obj instanceof Date) {
            return new SimpleDateFormat(ISO_8601_FORMAT_STRING, Locale.US).format(obj);
        }
        throw new IllegalArgumentException("Unsupported parameter type.");
    }
}
