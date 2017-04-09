package com.apptentive.android.sdk.lifecycle;

import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import com.apptentive.android.sdk.ApptentiveInternal;
import com.apptentive.android.sdk.ApptentiveLog;
import java.util.concurrent.atomic.AtomicInteger;

public class ApptentiveActivityLifecycleCallbacks implements ActivityLifecycleCallbacks {
    private static final long CHECK_DELAY_SHORT = 1000;
    private Runnable checkFgBgRoutine;
    private Handler delayedChecker = new Handler();
    private AtomicInteger foregroundActivities = new AtomicInteger(0);
    private boolean isAppForeground;

    public void onActivityStarted(Activity activity) {
        boolean z = !this.isAppForeground;
        this.isAppForeground = true;
        if (this.checkFgBgRoutine != null) {
            this.delayedChecker.removeCallbacks(this.checkFgBgRoutine);
            this.checkFgBgRoutine = null;
        }
        if (this.foregroundActivities.getAndIncrement() == 0 && z) {
            appEnteredForeground();
        }
        ApptentiveInternal.getInstance().onActivityStarted(activity);
    }

    public void onActivityResumed(Activity activity) {
        ApptentiveInternal.getInstance().onActivityResumed(activity);
    }

    public void onActivityPaused(Activity activity) {
    }

    public void onActivityDestroyed(Activity activity) {
    }

    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    public void onActivityStopped(Activity activity) {
        if (this.foregroundActivities.decrementAndGet() < 0) {
            ApptentiveLog.e("Incorrect number of foreground Activities encountered. Resetting to 0.", new Object[0]);
            this.foregroundActivities.set(0);
        }
        if (this.checkFgBgRoutine != null) {
            this.delayedChecker.removeCallbacks(this.checkFgBgRoutine);
        }
        Handler handler = this.delayedChecker;
        Runnable anonymousClass1 = new Runnable() {
            public void run() {
                if (ApptentiveActivityLifecycleCallbacks.this.foregroundActivities.get() == 0 && ApptentiveActivityLifecycleCallbacks.this.isAppForeground) {
                    ApptentiveActivityLifecycleCallbacks.this.appEnteredBackground();
                    ApptentiveActivityLifecycleCallbacks.this.isAppForeground = false;
                }
            }
        };
        this.checkFgBgRoutine = anonymousClass1;
        handler.postDelayed(anonymousClass1, CHECK_DELAY_SHORT);
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    private void appEnteredForeground() {
        ApptentiveLog.d("App went to foreground.", new Object[0]);
        ApptentiveInternal.getInstance().onAppEnterForeground();
        appLaunched(ApptentiveInternal.getInstance().getApplicationContext());
    }

    private void appEnteredBackground() {
        ApptentiveLog.d("App went to background.", new Object[0]);
        ApptentiveInternal.getInstance().onAppEnterBackground();
        appExited(ApptentiveInternal.getInstance().getApplicationContext());
    }

    private void appLaunched(Context context) {
        ApptentiveInternal.getInstance().onAppLaunch(context);
    }

    private void appExited(Context context) {
        ApptentiveInternal.getInstance().onAppExit(context);
    }
}
