package com.adjust.sdk;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;
import java.util.ArrayList;
import java.util.List;

public class SdkClickHandler extends HandlerThread implements ISdkClickHandler {
    private BackoffStrategy backoffStrategy = AdjustFactory.getSdkClickBackoffStrategy();
    private Handler internalHandler = new Handler(getLooper());
    private ILogger logger = AdjustFactory.getLogger();
    private List<ActivityPackage> packageQueue;
    private boolean paused;

    public SdkClickHandler(boolean z) {
        super(Constants.LOGTAG, 1);
        setDaemon(true);
        start();
        init(z);
    }

    public void init(boolean z) {
        this.paused = !z;
        this.packageQueue = new ArrayList();
    }

    public void pauseSending() {
        this.paused = true;
    }

    public void resumeSending() {
        this.paused = false;
        sendNextSdkClick();
    }

    public void sendSdkClick(final ActivityPackage activityPackage) {
        this.internalHandler.post(new Runnable() {
            public void run() {
                SdkClickHandler.this.packageQueue.add(activityPackage);
                SdkClickHandler.this.logger.debug("Added sdk_click %d", Integer.valueOf(SdkClickHandler.this.packageQueue.size()));
                SdkClickHandler.this.logger.verbose("%s", activityPackage.getExtendedString());
                SdkClickHandler.this.sendNextSdkClick();
            }
        });
    }

    private void sendNextSdkClick() {
        this.internalHandler.post(new Runnable() {
            public void run() {
                if (!SdkClickHandler.this.paused && !SdkClickHandler.this.packageQueue.isEmpty()) {
                    ActivityPackage activityPackage = (ActivityPackage) SdkClickHandler.this.packageQueue.get(0);
                    int retries = activityPackage.getRetries();
                    if (retries > 0) {
                        long waitingTime = Util.getWaitingTime(retries, SdkClickHandler.this.backoffStrategy);
                        String format = Util.SecondsDisplayFormat.format(((double) waitingTime) / 1000.0d);
                        SdkClickHandler.this.logger.verbose("Sleeping for %s seconds before retrying sdk_click for the %d time", format, Integer.valueOf(retries));
                        SystemClock.sleep(waitingTime);
                    }
                    SdkClickHandler.this.sendSdkClickInternal(activityPackage);
                    SdkClickHandler.this.packageQueue.remove(0);
                    SdkClickHandler.this.sendNextSdkClick();
                }
            }
        });
    }

    private void sendSdkClickInternal(ActivityPackage activityPackage) {
        try {
            if (Util.readHttpResponse(Util.createPOSTHttpsURLConnection(Constants.BASE_URL + activityPackage.getPath(), activityPackage.getClientSdk(), activityPackage.getParameters(), this.packageQueue.size() - 1), activityPackage).jsonResponse == null) {
                retrySending(activityPackage);
            }
        } catch (Throwable e) {
            logErrorMessage(activityPackage, "Sdk_click failed to encode parameters", e);
        } catch (Throwable e2) {
            logErrorMessage(activityPackage, "Sdk_click request timed out. Will retry later", e2);
            retrySending(activityPackage);
        } catch (Throwable e22) {
            logErrorMessage(activityPackage, "Sdk_click request failed. Will retry later", e22);
            retrySending(activityPackage);
        } catch (Throwable e222) {
            logErrorMessage(activityPackage, "Sdk_click runtime exception", e222);
        }
    }

    private void retrySending(ActivityPackage activityPackage) {
        int increaseRetries = activityPackage.increaseRetries();
        this.logger.error("Retrying sdk_click package for the %d time", Integer.valueOf(increaseRetries));
        sendSdkClick(activityPackage);
    }

    private void logErrorMessage(ActivityPackage activityPackage, String str, Throwable th) {
        String failureMessage = activityPackage.getFailureMessage();
        String reasonString = Util.getReasonString(str, th);
        this.logger.error(String.format("%s. (%s)", new Object[]{failureMessage, reasonString}), new Object[0]);
    }
}
