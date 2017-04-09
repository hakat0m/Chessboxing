package com.apptentive.android.sdk;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import com.apptentive.android.sdk.ApptentiveInternal.PushAction;
import com.apptentive.android.sdk.model.CustomData;
import com.apptentive.android.sdk.model.ExtendedData;
import com.apptentive.android.sdk.model.StoredFile;
import com.apptentive.android.sdk.module.engagement.EngagementModule;
import com.apptentive.android.sdk.module.messagecenter.MessageManager;
import com.apptentive.android.sdk.module.messagecenter.UnreadMessagesListener;
import com.apptentive.android.sdk.module.messagecenter.model.CompoundMessage;
import com.apptentive.android.sdk.module.metric.MetricModule;
import com.apptentive.android.sdk.module.rating.IRatingProvider;
import com.apptentive.android.sdk.module.survey.OnSurveyFinishedListener;
import com.apptentive.android.sdk.storage.DeviceManager;
import com.apptentive.android.sdk.storage.PersonManager;
import com.apptentive.android.sdk.util.Util;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class Apptentive {
    private static final String INTEGRATION_APPTENTIVE_PUSH = "apptentive_push";
    private static final String INTEGRATION_AWS_SNS = "aws_sns";
    private static final String INTEGRATION_PARSE = "parse";
    private static final String INTEGRATION_PUSH_TOKEN = "token";
    private static final String INTEGRATION_URBAN_AIRSHIP = "urban_airship";
    public static final int PUSH_PROVIDER_AMAZON_AWS_SNS = 3;
    public static final int PUSH_PROVIDER_APPTENTIVE = 0;
    public static final int PUSH_PROVIDER_PARSE = 1;
    public static final int PUSH_PROVIDER_URBAN_AIRSHIP = 2;

    /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$apptentive$android$sdk$ApptentiveInternal$PushAction = new int[PushAction.values().length];

        static {
            try {
                $SwitchMap$com$apptentive$android$sdk$ApptentiveInternal$PushAction[PushAction.pmc.ordinal()] = Apptentive.PUSH_PROVIDER_PARSE;
            } catch (NoSuchFieldError e) {
            }
        }
    }

    public class DateTime extends JSONObject implements Comparable<DateTime> {
        public static final String KEY_TYPE = "_type";
        public static final String SEC = "sec";
        public static final String TYPE = "datetime";

        public DateTime(String str) throws JSONException {
            super(str);
        }

        public DateTime(double d) {
            setDateTime(d);
        }

        public void setDateTime(double d) {
            try {
                put(KEY_TYPE, TYPE);
                put(SEC, d);
            } catch (Throwable e) {
                ApptentiveLog.e("Error creating Apptentive.DateTime.", e, new Object[Apptentive.PUSH_PROVIDER_APPTENTIVE]);
            }
        }

        public double getDateTime() {
            return optDouble(SEC);
        }

        public String toString() {
            return Double.toString(getDateTime());
        }

        public int compareTo(DateTime dateTime) {
            return Double.compare(getDateTime(), dateTime.getDateTime());
        }
    }

    public class Version extends JSONObject implements Comparable<Version> {
        public static final String KEY_TYPE = "_type";
        public static final String TYPE = "version";

        public Version(String str) throws JSONException {
            super(str);
        }

        public Version(long j) {
            setVersion(j);
        }

        public void setVersion(String str) {
            try {
                put(KEY_TYPE, TYPE);
                put(TYPE, str);
            } catch (Throwable e) {
                ApptentiveLog.e("Error creating Apptentive.Version.", e, new Object[Apptentive.PUSH_PROVIDER_APPTENTIVE]);
            }
        }

        public void setVersion(long j) {
            setVersion(Long.toString(j));
        }

        public String getVersion() {
            return optString(TYPE, null);
        }

        public int compareTo(Version version) {
            String version2 = getVersion();
            String version3 = version.getVersion();
            String[] split = version2.split("\\.");
            String[] split2 = version3.split("\\.");
            int max = Math.max(split.length, split2.length);
            for (int i = Apptentive.PUSH_PROVIDER_APPTENTIVE; i < max; i += Apptentive.PUSH_PROVIDER_PARSE) {
                long parseLong;
                long parseLong2;
                if (split.length > i) {
                    parseLong = Long.parseLong(split[i]);
                } else {
                    parseLong = 0;
                }
                if (split2.length > i) {
                    parseLong2 = Long.parseLong(split2[i]);
                } else {
                    parseLong2 = 0;
                }
                if (parseLong < parseLong2) {
                    return -1;
                }
                if (parseLong > parseLong2) {
                    return Apptentive.PUSH_PROVIDER_PARSE;
                }
            }
            return Apptentive.PUSH_PROVIDER_APPTENTIVE;
        }

        public String toString() {
            return getVersion();
        }
    }

    public static void register(Application application) {
        register(application, null);
    }

    public static void register(Application application, String str) {
        ApptentiveLog.i("Registering Apptentive.", new Object[PUSH_PROVIDER_APPTENTIVE]);
        ApptentiveInternal.createInstance(application, str);
        ApptentiveInternal.setLifeCycleCallback();
    }

    public static void setPersonEmail(String str) {
        if (ApptentiveInternal.isApptentiveRegistered()) {
            PersonManager.storePersonEmail(str);
        }
    }

    public static String getPersonEmail() {
        if (ApptentiveInternal.isApptentiveRegistered()) {
            return PersonManager.loadPersonEmail();
        }
        return null;
    }

    public static void setPersonName(String str) {
        if (ApptentiveInternal.isApptentiveRegistered()) {
            PersonManager.storePersonName(str);
        }
    }

    public static String getPersonName() {
        if (ApptentiveInternal.isApptentiveRegistered()) {
            return PersonManager.loadPersonName();
        }
        return null;
    }

    public static void setCustomDeviceData(Map<String, String> map) {
        if (ApptentiveInternal.isApptentiveRegistered()) {
            try {
                CustomData customData = new CustomData();
                for (String str : map.keySet()) {
                    customData.put(str, map.get(str));
                }
                DeviceManager.storeCustomDeviceData(customData);
            } catch (Throwable e) {
                ApptentiveLog.w("Unable to set custom device data.", e, new Object[PUSH_PROVIDER_APPTENTIVE]);
            }
        }
    }

    public static void addCustomDeviceData(String str, String str2) {
        if (ApptentiveInternal.isApptentiveRegistered()) {
            if (str2 != null) {
                str2 = str2.trim();
            }
            ApptentiveInternal.getInstance().addCustomDeviceData(str, str2);
        }
    }

    public static void addCustomDeviceData(String str, Number number) {
        if (ApptentiveInternal.isApptentiveRegistered()) {
            ApptentiveInternal.getInstance().addCustomDeviceData(str, number);
        }
    }

    public static void addCustomDeviceData(String str, Boolean bool) {
        if (ApptentiveInternal.isApptentiveRegistered()) {
            ApptentiveInternal.getInstance().addCustomDeviceData(str, bool);
        }
    }

    private static void addCustomDeviceData(String str, Version version) {
        if (ApptentiveInternal.isApptentiveRegistered()) {
            ApptentiveInternal.getInstance().addCustomDeviceData(str, version);
        }
    }

    private static void addCustomDeviceData(String str, DateTime dateTime) {
        if (ApptentiveInternal.isApptentiveRegistered()) {
            ApptentiveInternal.getInstance().addCustomDeviceData(str, dateTime);
        }
    }

    public static void removeCustomDeviceData(String str) {
        if (ApptentiveInternal.isApptentiveRegistered()) {
            CustomData loadCustomDeviceData = DeviceManager.loadCustomDeviceData();
            if (loadCustomDeviceData != null) {
                loadCustomDeviceData.remove(str);
                DeviceManager.storeCustomDeviceData(loadCustomDeviceData);
            }
        }
    }

    public static void setCustomPersonData(Map<String, String> map) {
        Object[] objArr = new Object[PUSH_PROVIDER_PARSE];
        objArr[PUSH_PROVIDER_APPTENTIVE] = map.toString();
        ApptentiveLog.w("Setting custom person data: %s", objArr);
        if (ApptentiveInternal.isApptentiveRegistered()) {
            try {
                CustomData customData = new CustomData();
                for (String str : map.keySet()) {
                    customData.put(str, map.get(str));
                }
                PersonManager.storeCustomPersonData(customData);
            } catch (Throwable e) {
                ApptentiveLog.e("Unable to set custom person data.", e, new Object[PUSH_PROVIDER_APPTENTIVE]);
            }
        }
    }

    public static void addCustomPersonData(String str, String str2) {
        if (ApptentiveInternal.isApptentiveRegistered()) {
            if (str2 != null) {
                str2 = str2.trim();
            }
            ApptentiveInternal.getInstance().addCustomPersonData(str, str2);
        }
    }

    public static void addCustomPersonData(String str, Number number) {
        if (ApptentiveInternal.isApptentiveRegistered()) {
            ApptentiveInternal.getInstance().addCustomPersonData(str, number);
        }
    }

    public static void addCustomPersonData(String str, Boolean bool) {
        if (ApptentiveInternal.isApptentiveRegistered()) {
            ApptentiveInternal.getInstance().addCustomPersonData(str, bool);
        }
    }

    private static void addCustomPersonData(String str, Version version) {
        if (ApptentiveInternal.isApptentiveRegistered()) {
            ApptentiveInternal.getInstance().addCustomPersonData(str, version);
        }
    }

    private static void addCustomPersonData(String str, DateTime dateTime) {
        if (ApptentiveInternal.isApptentiveRegistered()) {
            ApptentiveInternal.getInstance().addCustomPersonData(str, dateTime);
        }
    }

    public static void removeCustomPersonData(String str) {
        if (ApptentiveInternal.isApptentiveRegistered()) {
            CustomData loadCustomPersonData = PersonManager.loadCustomPersonData();
            if (loadCustomPersonData != null) {
                loadCustomPersonData.remove(str);
                PersonManager.storeCustomPersonData(loadCustomPersonData);
            }
        }
    }

    private static void addIntegration(String str, Map<String, String> map) {
        if (str != null && map != null && ApptentiveInternal.isApptentiveRegistered()) {
            CustomData loadIntegrationConfig = DeviceManager.loadIntegrationConfig();
            try {
                JSONObject jSONObject;
                if (loadIntegrationConfig.isNull(str)) {
                    JSONObject jSONObject2 = new JSONObject();
                    loadIntegrationConfig.put(str, jSONObject2);
                    jSONObject = jSONObject2;
                } else {
                    jSONObject = loadIntegrationConfig.getJSONObject(str);
                }
                for (String str2 : map.keySet()) {
                    jSONObject.put(str2, map.get(str2));
                }
                Object[] objArr = new Object[PUSH_PROVIDER_PARSE];
                objArr[PUSH_PROVIDER_APPTENTIVE] = map.toString();
                ApptentiveLog.d("Adding integration config: %s", objArr);
                DeviceManager.storeIntegrationConfig(loadIntegrationConfig);
                ApptentiveInternal.getInstance().syncDevice();
            } catch (Throwable e) {
                Object[] objArr2 = new Object[PUSH_PROVIDER_URBAN_AIRSHIP];
                objArr2[PUSH_PROVIDER_APPTENTIVE] = str;
                objArr2[PUSH_PROVIDER_PARSE] = map.toString();
                ApptentiveLog.e("Error adding integration: %s, %s", e, objArr2);
            }
        }
    }

    public static void setPushNotificationIntegration(int i, String str) {
        try {
            if (ApptentiveInternal.isApptentiveRegistered()) {
                CustomData integrationConfigurationWithoutPushProviders = getIntegrationConfigurationWithoutPushProviders();
                JSONObject jSONObject = new JSONObject();
                jSONObject.put(INTEGRATION_PUSH_TOKEN, str);
                switch (i) {
                    case PUSH_PROVIDER_APPTENTIVE /*0*/:
                        integrationConfigurationWithoutPushProviders.put(INTEGRATION_APPTENTIVE_PUSH, jSONObject);
                        break;
                    case PUSH_PROVIDER_PARSE /*1*/:
                        integrationConfigurationWithoutPushProviders.put(INTEGRATION_PARSE, jSONObject);
                        break;
                    case PUSH_PROVIDER_URBAN_AIRSHIP /*2*/:
                        integrationConfigurationWithoutPushProviders.put(INTEGRATION_URBAN_AIRSHIP, jSONObject);
                        break;
                    case PUSH_PROVIDER_AMAZON_AWS_SNS /*3*/:
                        integrationConfigurationWithoutPushProviders.put(INTEGRATION_AWS_SNS, jSONObject);
                        break;
                    default:
                        Object[] objArr = new Object[PUSH_PROVIDER_PARSE];
                        objArr[PUSH_PROVIDER_APPTENTIVE] = Integer.valueOf(i);
                        ApptentiveLog.e("Invalid pushProvider: %d", objArr);
                        return;
                }
                DeviceManager.storeIntegrationConfig(integrationConfigurationWithoutPushProviders);
                ApptentiveInternal.getInstance().syncDevice();
            }
        } catch (Throwable e) {
            ApptentiveLog.e("Error setting push integration.", e, new Object[PUSH_PROVIDER_APPTENTIVE]);
        }
    }

    private static CustomData getIntegrationConfigurationWithoutPushProviders() {
        CustomData loadIntegrationConfig = DeviceManager.loadIntegrationConfig();
        if (loadIntegrationConfig != null) {
            loadIntegrationConfig.remove(INTEGRATION_APPTENTIVE_PUSH);
            loadIntegrationConfig.remove(INTEGRATION_PARSE);
            loadIntegrationConfig.remove(INTEGRATION_URBAN_AIRSHIP);
            loadIntegrationConfig.remove(INTEGRATION_AWS_SNS);
        }
        return loadIntegrationConfig;
    }

    public static boolean isApptentivePushNotification(Intent intent) {
        if (ApptentiveInternal.isApptentiveRegistered() && ApptentiveInternal.getApptentivePushNotificationData(intent) != null) {
            return true;
        }
        return false;
    }

    public static boolean isApptentivePushNotification(Bundle bundle) {
        if (ApptentiveInternal.isApptentiveRegistered() && ApptentiveInternal.getApptentivePushNotificationData(bundle) != null) {
            return true;
        }
        return false;
    }

    public static boolean setPendingPushNotification(Intent intent) {
        if (!ApptentiveInternal.isApptentiveRegistered()) {
            return false;
        }
        String apptentivePushNotificationData = ApptentiveInternal.getApptentivePushNotificationData(intent);
        if (apptentivePushNotificationData != null) {
            return ApptentiveInternal.getInstance().setPendingPushNotification(apptentivePushNotificationData);
        }
        return false;
    }

    public static boolean setPendingPushNotification(Bundle bundle) {
        if (!ApptentiveInternal.isApptentiveRegistered()) {
            return false;
        }
        String apptentivePushNotificationData = ApptentiveInternal.getApptentivePushNotificationData(bundle);
        if (apptentivePushNotificationData != null) {
            return ApptentiveInternal.getInstance().setPendingPushNotification(apptentivePushNotificationData);
        }
        return false;
    }

    public static boolean handleOpenedPushNotification(Context context) {
        if (!ApptentiveInternal.isApptentiveRegistered()) {
            return false;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences("APPTENTIVE", PUSH_PROVIDER_APPTENTIVE);
        String string = sharedPreferences.getString("pendingPushNotification", null);
        sharedPreferences.edit().remove("pendingPushNotification").apply();
        if (string == null) {
            return false;
        }
        ApptentiveLog.i("Handling opened Apptentive push notification.", new Object[PUSH_PROVIDER_APPTENTIVE]);
        try {
            JSONObject jSONObject = new JSONObject(string);
            PushAction pushAction = PushAction.unknown;
            if (jSONObject.has(ApptentiveInternal.PUSH_ACTION)) {
                pushAction = PushAction.parse(jSONObject.getString(ApptentiveInternal.PUSH_ACTION));
            }
            switch (AnonymousClass1.$SwitchMap$com$apptentive$android$sdk$ApptentiveInternal$PushAction[pushAction.ordinal()]) {
                case PUSH_PROVIDER_PARSE /*1*/:
                    showMessageCenter(context);
                    return true;
                default:
                    Object[] objArr = new Object[PUSH_PROVIDER_PARSE];
                    objArr[PUSH_PROVIDER_APPTENTIVE] = pushAction.name();
                    ApptentiveLog.v("Unknown Apptentive push notification action: \"%s\"", objArr);
                    return false;
            }
        } catch (Throwable e) {
            ApptentiveLog.w("Error parsing JSON from push notification.", e, new Object[PUSH_PROVIDER_APPTENTIVE]);
            MetricModule.sendError(e, "Parsing Push notification", string);
            return false;
        }
        ApptentiveLog.w("Error parsing JSON from push notification.", e, new Object[PUSH_PROVIDER_APPTENTIVE]);
        MetricModule.sendError(e, "Parsing Push notification", string);
        return false;
    }

    public static void setRatingProvider(IRatingProvider iRatingProvider) {
        if (ApptentiveInternal.isApptentiveRegistered()) {
            ApptentiveInternal.getInstance().setRatingProvider(iRatingProvider);
        }
    }

    public static void putRatingProviderArg(String str, String str2) {
        if (ApptentiveInternal.isApptentiveRegistered()) {
            ApptentiveInternal.getInstance().putRatingProviderArg(str, str2);
        }
    }

    public static boolean showMessageCenter(Context context) {
        return showMessageCenter(context, null);
    }

    public static boolean showMessageCenter(Context context, Map<String, Object> map) {
        boolean z = false;
        try {
            if (ApptentiveInternal.isApptentiveRegistered()) {
                z = ApptentiveInternal.getInstance().showMessageCenterInternal(context, map);
            }
        } catch (Throwable e) {
            ApptentiveLog.w("Error starting Apptentive Activity.", e, new Object[PUSH_PROVIDER_APPTENTIVE]);
            MetricModule.sendError(e, null, null);
        }
        return z;
    }

    public static boolean canShowMessageCenter() {
        if (ApptentiveInternal.isApptentiveRegistered()) {
            return ApptentiveInternal.getInstance().canShowMessageCenterInternal();
        }
        return false;
    }

    @Deprecated
    public static void setUnreadMessagesListener(UnreadMessagesListener unreadMessagesListener) {
        if (ApptentiveInternal.isApptentiveRegistered()) {
            ApptentiveInternal.getInstance().getMessageManager().setHostUnreadMessagesListener(unreadMessagesListener);
        }
    }

    public static void addUnreadMessagesListener(UnreadMessagesListener unreadMessagesListener) {
        if (ApptentiveInternal.isApptentiveRegistered()) {
            ApptentiveInternal.getInstance().getMessageManager().addHostUnreadMessagesListener(unreadMessagesListener);
        }
    }

    public static int getUnreadMessageCount() {
        try {
            if (ApptentiveInternal.isApptentiveRegistered()) {
                return ApptentiveInternal.getInstance().getMessageManager().getUnreadMessageCount();
            }
        } catch (Throwable e) {
            MetricModule.sendError(e, null, null);
        }
        return PUSH_PROVIDER_APPTENTIVE;
    }

    public static void sendAttachmentText(String str) {
        if (ApptentiveInternal.isApptentiveRegistered()) {
            try {
                CompoundMessage compoundMessage = new CompoundMessage();
                compoundMessage.setBody(str);
                compoundMessage.setRead(true);
                compoundMessage.setHidden(true);
                compoundMessage.setSenderId(ApptentiveInternal.getInstance().getPersonId());
                compoundMessage.setAssociatedFiles(null);
                MessageManager messageManager = ApptentiveInternal.getInstance().getMessageManager();
                if (messageManager != null) {
                    messageManager.sendMessage(compoundMessage);
                }
            } catch (Throwable e) {
                ApptentiveLog.w("Error sending attachment text.", e, new Object[PUSH_PROVIDER_APPTENTIVE]);
                MetricModule.sendError(e, null, null);
            }
        }
    }

    public static void sendAttachmentFile(String str) {
        try {
            if (!TextUtils.isEmpty(str) && ApptentiveInternal.isApptentiveRegistered()) {
                CompoundMessage compoundMessage = new CompoundMessage();
                compoundMessage.setBody(null);
                compoundMessage.setRead(true);
                compoundMessage.setHidden(true);
                compoundMessage.setSenderId(ApptentiveInternal.getInstance().getPersonId());
                List arrayList = new ArrayList();
                Context applicationContext = ApptentiveInternal.getInstance().getApplicationContext();
                String generateCacheFilePathFromNonceOrPrefix = Util.generateCacheFilePathFromNonceOrPrefix(applicationContext, compoundMessage.getNonce(), Uri.parse(str).getLastPathSegment());
                String mimeTypeFromUri = Util.getMimeTypeFromUri(applicationContext, Uri.parse(str));
                MimeTypeMap singleton = MimeTypeMap.getSingleton();
                Object extensionFromMimeType = singleton.getExtensionFromMimeType(mimeTypeFromUri);
                if (extensionFromMimeType == null) {
                    extensionFromMimeType = MimeTypeMap.getFileExtensionFromUrl(str);
                }
                if (mimeTypeFromUri == null && extensionFromMimeType != null) {
                    mimeTypeFromUri = singleton.getMimeTypeFromExtension(extensionFromMimeType);
                }
                if (!TextUtils.isEmpty(extensionFromMimeType)) {
                    generateCacheFilePathFromNonceOrPrefix = generateCacheFilePathFromNonceOrPrefix + "." + extensionFromMimeType;
                }
                StoredFile createLocalStoredFile = Util.createLocalStoredFile(str, generateCacheFilePathFromNonceOrPrefix, mimeTypeFromUri);
                if (createLocalStoredFile != null) {
                    createLocalStoredFile.setId(compoundMessage.getNonce());
                    arrayList.add(createLocalStoredFile);
                    compoundMessage.setAssociatedFiles(arrayList);
                    MessageManager messageManager = ApptentiveInternal.getInstance().getMessageManager();
                    if (messageManager != null) {
                        messageManager.sendMessage(compoundMessage);
                    }
                }
            }
        } catch (Throwable e) {
            ApptentiveLog.w("Error sending attachment file.", e, new Object[PUSH_PROVIDER_APPTENTIVE]);
            MetricModule.sendError(e, null, null);
        }
    }

    public static void sendAttachmentFile(byte[] bArr, String str) {
        Closeable byteArrayInputStream;
        Throwable th;
        if (ApptentiveInternal.isApptentiveRegistered()) {
            try {
                byteArrayInputStream = new ByteArrayInputStream(bArr);
                try {
                    sendAttachmentFile((InputStream) byteArrayInputStream, str);
                    Util.ensureClosed(byteArrayInputStream);
                } catch (Throwable th2) {
                    th = th2;
                    Util.ensureClosed(byteArrayInputStream);
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                byteArrayInputStream = null;
                Util.ensureClosed(byteArrayInputStream);
                throw th;
            }
        }
    }

    public static void sendAttachmentFile(InputStream inputStream, String str) {
        if (inputStream != null) {
            try {
                if (ApptentiveInternal.isApptentiveRegistered()) {
                    CompoundMessage compoundMessage = new CompoundMessage();
                    compoundMessage.setBody(null);
                    compoundMessage.setRead(true);
                    compoundMessage.setHidden(true);
                    compoundMessage.setSenderId(ApptentiveInternal.getInstance().getPersonId());
                    List arrayList = new ArrayList();
                    String generateCacheFilePathFromNonceOrPrefix = Util.generateCacheFilePathFromNonceOrPrefix(ApptentiveInternal.getInstance().getApplicationContext(), compoundMessage.getNonce(), null);
                    Object extensionFromMimeType = MimeTypeMap.getSingleton().getExtensionFromMimeType(str);
                    if (!TextUtils.isEmpty(extensionFromMimeType)) {
                        generateCacheFilePathFromNonceOrPrefix = generateCacheFilePathFromNonceOrPrefix + "." + extensionFromMimeType;
                    }
                    StoredFile createLocalStoredFile = Util.createLocalStoredFile(inputStream, generateCacheFilePathFromNonceOrPrefix, generateCacheFilePathFromNonceOrPrefix, str);
                    if (createLocalStoredFile != null) {
                        createLocalStoredFile.setId(compoundMessage.getNonce());
                        arrayList.add(createLocalStoredFile);
                        compoundMessage.setAssociatedFiles(arrayList);
                        ApptentiveInternal.getInstance().getMessageManager().sendMessage(compoundMessage);
                    }
                }
            } catch (Throwable e) {
                ApptentiveLog.w("Error sending attachment file.", e, new Object[PUSH_PROVIDER_APPTENTIVE]);
                MetricModule.sendError(e, null, null);
            }
        }
    }

    public static synchronized boolean engage(Context context, String str) {
        boolean engage;
        synchronized (Apptentive.class) {
            engage = EngagementModule.engage(context, "local", "app", null, str, null, null, (ExtendedData[]) null);
        }
        return engage;
    }

    public static synchronized boolean engage(Context context, String str, Map<String, Object> map) {
        boolean engage;
        synchronized (Apptentive.class) {
            engage = EngagementModule.engage(context, "local", "app", null, str, null, map, (ExtendedData[]) null);
        }
        return engage;
    }

    public static synchronized boolean engage(Context context, String str, Map<String, Object> map, ExtendedData... extendedDataArr) {
        boolean engage;
        synchronized (Apptentive.class) {
            engage = EngagementModule.engage(context, "local", "app", null, str, null, map, extendedDataArr);
        }
        return engage;
    }

    public static synchronized boolean willShowInteraction(String str) {
        boolean canShowInteraction;
        synchronized (Apptentive.class) {
            canShowInteraction = canShowInteraction(str);
        }
        return canShowInteraction;
    }

    public static synchronized boolean canShowInteraction(String str) {
        boolean canShowInteraction;
        synchronized (Apptentive.class) {
            try {
                canShowInteraction = EngagementModule.canShowInteraction("local", "app", str);
            } catch (Throwable e) {
                MetricModule.sendError(e, null, null);
                canShowInteraction = false;
            }
        }
        return canShowInteraction;
    }

    public static void setOnSurveyFinishedListener(OnSurveyFinishedListener onSurveyFinishedListener) {
        ApptentiveInternal instance = ApptentiveInternal.getInstance();
        if (instance != null) {
            instance.setOnSurveyFinishedListener(onSurveyFinishedListener);
        }
    }
}
