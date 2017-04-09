package com.facebook.stetho.inspector.elements.android;

import android.app.Activity;
import android.app.Application;
import android.os.Build.VERSION;
import android.os.Looper;
import com.facebook.stetho.common.Util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public final class ActivityTracker {
    private static final ActivityTracker sInstance = new ActivityTracker();
    @GuardedBy("Looper.getMainLooper()")
    private final ArrayList<Activity> mActivities = new ArrayList();
    private final List<Activity> mActivitiesUnmodifiable = Collections.unmodifiableList(this.mActivities);
    @Nullable
    private AutomaticTracker mAutomaticTracker;
    private final List<Listener> mListeners = new CopyOnWriteArrayList();

    abstract class AutomaticTracker {
        public abstract void register();

        public abstract void unregister();

        private AutomaticTracker() {
        }

        @Nullable
        public static AutomaticTracker newInstanceIfPossible(Application application, ActivityTracker activityTracker) {
            if (VERSION.SDK_INT >= 14) {
                return new AutomaticTrackerICSAndBeyond(application, activityTracker);
            }
            return null;
        }
    }

    public static ActivityTracker get() {
        return sInstance;
    }

    public void registerListener(Listener listener) {
        this.mListeners.add(listener);
    }

    public void unregisterListener(Listener listener) {
        this.mListeners.remove(listener);
    }

    public boolean beginTrackingIfPossible(Application application) {
        if (this.mAutomaticTracker == null) {
            AutomaticTracker newInstanceIfPossible = AutomaticTracker.newInstanceIfPossible(application, this);
            if (newInstanceIfPossible != null) {
                newInstanceIfPossible.register();
                this.mAutomaticTracker = newInstanceIfPossible;
                return true;
            }
        }
        return false;
    }

    public boolean endTracking() {
        if (this.mAutomaticTracker == null) {
            return false;
        }
        this.mAutomaticTracker.unregister();
        this.mAutomaticTracker = null;
        return true;
    }

    public void add(Activity activity) {
        Util.throwIfNull(activity);
        Util.throwIfNot(Looper.myLooper() == Looper.getMainLooper());
        this.mActivities.add(activity);
        for (Listener onActivityAdded : this.mListeners) {
            onActivityAdded.onActivityAdded(activity);
        }
    }

    public void remove(Activity activity) {
        Util.throwIfNull(activity);
        Util.throwIfNot(Looper.myLooper() == Looper.getMainLooper());
        if (this.mActivities.remove(activity)) {
            for (Listener onActivityRemoved : this.mListeners) {
                onActivityRemoved.onActivityRemoved(activity);
            }
        }
    }

    public List<Activity> getActivitiesView() {
        return this.mActivitiesUnmodifiable;
    }

    public Activity tryGetTopActivity() {
        if (this.mActivitiesUnmodifiable.isEmpty()) {
            return null;
        }
        return (Activity) this.mActivitiesUnmodifiable.get(this.mActivitiesUnmodifiable.size() - 1);
    }
}
