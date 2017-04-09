package com.comscore.analytics;

import android.content.Context;
import com.comscore.applications.EventType;
import com.comscore.utils.TransmissionMode;
import java.util.HashMap;

public class comScore {
    private static Core a = new Core();

    public static void aggregate(HashMap<String, String> hashMap) {
        a.notify(EventType.AGGREGATE, hashMap, true);
    }

    public static void allowLiveTransmission(TransmissionMode transmissionMode) {
        a.allowLiveTransmission(transmissionMode, true);
    }

    public static void allowOfflineTransmission(TransmissionMode transmissionMode) {
        a.allowOfflineTransmission(transmissionMode, true);
    }

    public static void disableAutoUpdate() {
        a.disableAutoUpdate();
    }

    public static void enableAutoUpdate() {
        enableAutoUpdate(60, true);
    }

    public static void enableAutoUpdate(int i) {
        enableAutoUpdate(i, true);
    }

    public static void enableAutoUpdate(int i, boolean z) {
        a.enableAutoUpdate(i, z, true);
    }

    public static void flushCache() {
        a.flush(true);
    }

    public static String getAppName() {
        return a.getAppName();
    }

    public static String getAutoStartLabel(String str) {
        return a.getAutoStartLabel(str);
    }

    public static HashMap<String, String> getAutoStartLabels() {
        return a.getAutoStartLabels();
    }

    public static long getCacheFlushingInterval() {
        return a.getCacheFlushingInterval();
    }

    public static int getCacheMaxBatchFiles() {
        return a.getCacheMaxBatchFiles();
    }

    public static int getCacheMaxFlushesInARow() {
        return a.getCacheMaxFlushesInARow();
    }

    public static int getCacheMaxMeasurements() {
        return a.getCacheMaxMeasurements();
    }

    public static long getCacheMeasurementExpiry() {
        return a.getCacheMeasurementExpiry();
    }

    public static long getCacheMinutesToRetry() {
        return a.getCacheMinutesToRetry();
    }

    public static Core getCore() {
        return a;
    }

    public static String getCustomerC2() {
        return a.getCustomerC2();
    }

    public static boolean getErrorHandlingEnabled() {
        return a.getErrorHandlingEnabled();
    }

    public static long getGenesis() {
        return a.getGenesis();
    }

    public static String getLabel(String str) {
        return a.getLabel(str);
    }

    public static HashMap<String, String> getLabels() {
        return a.getLabels();
    }

    public static TransmissionMode getLiveTransmissionMode() {
        return a.getLiveTransmissionMode();
    }

    public static String[] getMeasurementLabelOrder() {
        return a.getMeasurementLabelOrder();
    }

    public static TransmissionMode getOfflineTransmissionMode() {
        return a.getOfflineTransmissionMode();
    }

    public static String getPixelURL() {
        return a.getPixelURL();
    }

    public static String getPublisherSecret() {
        return a.getPublisherSecret();
    }

    public static String getVersion() {
        return a.getVersion();
    }

    public static String getVisitorID() {
        return a.getVisitorId();
    }

    public static void hidden() {
        a.notify(EventType.HIDDEN, new HashMap(), true);
    }

    public static void hidden(HashMap<String, String> hashMap) {
        a.notify(EventType.HIDDEN, hashMap, true);
    }

    public static boolean isAutoStartEnabled() {
        return a.isAutoStartEnabled();
    }

    public static boolean isEnabled() {
        return a.isEnabled();
    }

    public static boolean isKeepAliveEnabled() {
        return a.isKeepAliveEnabled();
    }

    public static boolean isSecure() {
        return a.isSecure();
    }

    public static void onEnterForeground() {
        a.onEnterForeground();
    }

    public static void onExitForeground() {
        a.onExitForeground();
    }

    public static void onUserInteraction() {
        a.onUserInteraction();
    }

    public static void onUxActive() {
        a.onUxActive();
    }

    public static void onUxInactive() {
        a.onUxInactive();
    }

    public static void setAppContext(Context context) {
        a.setAppContext(context);
    }

    public static void setAppName(String str) {
        a.setAppName(str, true);
    }

    public static void setAutoStartEnabled(boolean z) {
        a.setAutoStartEnabled(z, true);
    }

    public static void setAutoStartLabel(String str, String str2) {
        a.setAutoStartLabel(str, str2);
    }

    public static void setAutoStartLabels(HashMap<String, String> hashMap) {
        a.setAutoStartLabels(hashMap);
    }

    public static void setCacheFlushingInterval(long j) {
        a.setCacheFlushingInterval(j, true);
    }

    public static void setCacheMaxBatchFiles(int i) {
        a.setCacheMaxBatchFiles(i, true);
    }

    public static void setCacheMaxFlushesInARow(int i) {
        a.setCacheMaxFlushesInARow(i, true);
    }

    public static void setCacheMaxMeasurements(int i) {
        a.setCacheMaxMeasurements(i, true);
    }

    public static void setCacheMeasurementExpiry(int i) {
        a.setCacheMeasurementExpiry(i, true);
    }

    public static void setCacheMinutesToRetry(int i) {
        a.setCacheMinutesToRetry(i, true);
    }

    public static void setCustomerC2(String str) {
        a.setCustomerC2(str, true);
    }

    public static void setDebug(boolean z) {
        a.setDebug(z);
    }

    public static void setEnabled(boolean z) {
        a.setEnabled(z);
    }

    public static void setErrorHandlingEnabled(boolean z) {
        a.setErrorHandlingEnabled(z);
    }

    public static void setKeepAliveEnabled(boolean z) {
        a.setKeepAliveEnabled(z, true);
    }

    public static void setLabel(String str, String str2) {
        a.setLabel(str, str2, true);
    }

    public static void setLabels(HashMap<String, String> hashMap) {
        a.setLabels(hashMap, true);
    }

    public static void setMeasurementLabelOrder(String[] strArr) {
        a.setMeasurementLabelOrder(strArr, true);
    }

    public static void setOfflineURL(String str) {
        a.setOfflineURL(str);
    }

    public static void setPixelURL(String str) {
        a.setPixelURL(str, true);
    }

    public static void setPublisherSecret(String str) {
        a.setPublisherSecret(str, true);
    }

    public static void setSecure(boolean z) {
        a.setSecure(z, true);
    }

    public static void start() {
        a.notify(EventType.START, new HashMap(), true);
    }

    public static void start(HashMap<String, String> hashMap) {
        a.notify(EventType.START, hashMap, true);
    }

    public static void update() {
        a.update();
    }

    public static void view() {
        a.notify(EventType.VIEW, new HashMap(), true);
    }

    public static void view(HashMap<String, String> hashMap) {
        a.notify(EventType.VIEW, hashMap, true);
    }

    public static void waitForTasks() {
        a.getTaskExecutor().waitForTasks();
    }

    public boolean isAutoUpdateEnabled() {
        return a.isAutoUpdateEnabled();
    }
}
