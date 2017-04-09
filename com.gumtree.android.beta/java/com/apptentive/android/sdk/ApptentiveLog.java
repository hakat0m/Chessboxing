package com.apptentive.android.sdk;

import android.util.Log;
import java.util.IllegalFormatException;

public class ApptentiveLog {
    private static final String TAG = "Apptentive";
    private static Level logLevel = Level.DEFAULT;

    public enum Level {
        VERBOSE(2),
        DEBUG(3),
        INFO(4),
        WARN(5),
        ERROR(6),
        ASSERT(7),
        DEFAULT(INFO.getLevel());
        
        private int level;

        private Level(int i) {
            this.level = i;
        }

        public int getLevel() {
            return this.level;
        }

        public static Level parse(String str) {
            try {
                return valueOf(str);
            } catch (IllegalArgumentException e) {
                Log.println(4, ApptentiveLog.TAG, "Error parsing unknown ApptentiveLog.Level: " + str);
                return DEFAULT;
            }
        }

        public boolean canLog(Level level) {
            return level.getLevel() >= getLevel();
        }
    }

    public static void overrideLogLevel(Level level) {
        logLevel = level;
    }

    private static void doLog(Level level, Throwable th, String str, Object... objArr) {
        if (canLog(level) && str != null) {
            if (objArr.length > 0) {
                try {
                    str = String.format(str, objArr);
                } catch (IllegalFormatException e) {
                    str = "Error formatting log message [level=" + level + "]: " + str;
                    level = Level.ERROR;
                }
            }
            Log.println(level.getLevel(), TAG, str);
            if (th != null) {
                if (th.getMessage() != null) {
                    Log.println(level.getLevel(), TAG, th.getMessage());
                }
                while (th != null) {
                    Log.println(level.getLevel(), TAG, Log.getStackTraceString(th));
                    th = th.getCause();
                }
            }
        }
    }

    public static boolean canLog(Level level) {
        return logLevel.canLog(level);
    }

    public static void v(String str, Object... objArr) {
        doLog(Level.VERBOSE, null, str, objArr);
    }

    public static void v(String str, Throwable th, Object... objArr) {
        doLog(Level.VERBOSE, th, str, objArr);
    }

    public static void d(String str, Object... objArr) {
        doLog(Level.DEBUG, null, str, objArr);
    }

    public static void d(String str, Throwable th, Object... objArr) {
        doLog(Level.DEBUG, th, str, objArr);
    }

    public static void i(String str, Object... objArr) {
        doLog(Level.INFO, null, str, objArr);
    }

    public static void i(String str, Throwable th, Object... objArr) {
        doLog(Level.INFO, th, str, objArr);
    }

    public static void w(String str, Object... objArr) {
        doLog(Level.WARN, null, str, objArr);
    }

    public static void w(String str, Throwable th, Object... objArr) {
        doLog(Level.WARN, th, str, objArr);
    }

    public static void e(String str, Object... objArr) {
        doLog(Level.ERROR, null, str, objArr);
    }

    public static void e(String str, Throwable th, Object... objArr) {
        doLog(Level.ERROR, th, str, objArr);
    }

    public static void a(String str, Object... objArr) {
        doLog(Level.ASSERT, null, str, objArr);
    }

    public static void a(String str, Throwable th, Object... objArr) {
        doLog(Level.ASSERT, th, str, objArr);
    }
}
