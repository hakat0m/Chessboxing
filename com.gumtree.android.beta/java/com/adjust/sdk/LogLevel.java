package com.adjust.sdk;

public enum LogLevel {
    VERBOSE(2),
    DEBUG(3),
    INFO(4),
    WARN(5),
    ERROR(6),
    ASSERT(7);
    
    final int androidLogLevel;

    private LogLevel(int i) {
        this.androidLogLevel = i;
    }

    public int getAndroidLogLevel() {
        return this.androidLogLevel;
    }
}
