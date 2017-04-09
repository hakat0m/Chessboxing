package com.apptentive.android.sdk;

import com.apptentive.android.sdk.util.Util;

public class SessionEvent {
    private Action action;
    private String activityName;
    private long id;
    private long time;

    public enum Action {
        START,
        STOP
    }

    public SessionEvent(long j, Action action, String str) {
        this.time = j;
        this.action = action;
        this.activityName = str;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long j) {
        this.id = j;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long j) {
        this.time = j;
    }

    public String getActivityName() {
        return this.activityName;
    }

    public void setActivityName(String str) {
        this.activityName = str;
    }

    public Action getAction() {
        return this.action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public boolean isStartEvent() {
        return this.action == Action.START;
    }

    public boolean isStopEvent() {
        return this.action == Action.STOP;
    }

    public String getDebugString() {
        String str = "#%d : %s : %s : %s";
        Object[] objArr = new Object[4];
        objArr[0] = Long.valueOf(this.id);
        objArr[1] = this.action.name() + (isStopEvent() ? " " : BuildConfig.FLAVOR);
        objArr[2] = this.activityName;
        objArr[3] = Util.dateToIso8601String(this.time);
        return String.format(str, objArr);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof SessionEvent)) {
            return false;
        }
        SessionEvent sessionEvent = (SessionEvent) obj;
        if (getAction() == sessionEvent.getAction() && getActivityName().equals(sessionEvent.getActivityName()) && getTime() == sessionEvent.getTime()) {
            return true;
        }
        return false;
    }
}
