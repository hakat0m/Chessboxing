package com.soundcloud.android.crop;

import android.app.Activity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.Iterator;

abstract class MonitoredActivity extends Activity {
    private final ArrayList<LifeCycleListener> listeners = new ArrayList();

    public interface LifeCycleListener {
        void onActivityCreated(MonitoredActivity monitoredActivity);

        void onActivityDestroyed(MonitoredActivity monitoredActivity);

        void onActivityStarted(MonitoredActivity monitoredActivity);

        void onActivityStopped(MonitoredActivity monitoredActivity);
    }

    MonitoredActivity() {
    }

    public void addLifeCycleListener(LifeCycleListener lifeCycleListener) {
        if (!this.listeners.contains(lifeCycleListener)) {
            this.listeners.add(lifeCycleListener);
        }
    }

    public void removeLifeCycleListener(LifeCycleListener lifeCycleListener) {
        this.listeners.remove(lifeCycleListener);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((LifeCycleListener) it.next()).onActivityCreated(this);
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((LifeCycleListener) it.next()).onActivityDestroyed(this);
        }
    }

    protected void onStart() {
        super.onStart();
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((LifeCycleListener) it.next()).onActivityStarted(this);
        }
    }

    protected void onStop() {
        super.onStop();
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((LifeCycleListener) it.next()).onActivityStopped(this);
        }
    }
}
