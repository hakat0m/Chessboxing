package com.adjust.sdk;

import android.util.Log;
import java.util.Arrays;
import java.util.Locale;

public class Logger implements ILogger {
    private static String formatErrorMessage = "Error formating log message: %s, with params: %s";
    private LogLevel logLevel;

    public Logger() {
        setLogLevel(LogLevel.INFO);
    }

    public void setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    public void setLogLevelString(String str) {
        if (str != null) {
            try {
                setLogLevel(LogLevel.valueOf(str.toUpperCase(Locale.US)));
            } catch (IllegalArgumentException e) {
                error("Malformed logLevel '%s', falling back to 'info'", str);
            }
        }
    }

    public void verbose(String str, Object... objArr) {
        if (this.logLevel.androidLogLevel <= 2) {
            try {
                Log.v(Constants.LOGTAG, String.format(Locale.US, str, objArr));
            } catch (Exception e) {
                Log.e(Constants.LOGTAG, String.format(Locale.US, formatErrorMessage, new Object[]{str, Arrays.toString(objArr)}));
            }
        }
    }

    public void debug(String str, Object... objArr) {
        if (this.logLevel.androidLogLevel <= 3) {
            try {
                Log.d(Constants.LOGTAG, String.format(Locale.US, str, objArr));
            } catch (Exception e) {
                Log.e(Constants.LOGTAG, String.format(Locale.US, formatErrorMessage, new Object[]{str, Arrays.toString(objArr)}));
            }
        }
    }

    public void info(String str, Object... objArr) {
        if (this.logLevel.androidLogLevel <= 4) {
            try {
                Log.i(Constants.LOGTAG, String.format(Locale.US, str, objArr));
            } catch (Exception e) {
                Log.e(Constants.LOGTAG, String.format(Locale.US, formatErrorMessage, new Object[]{str, Arrays.toString(objArr)}));
            }
        }
    }

    public void warn(String str, Object... objArr) {
        if (this.logLevel.androidLogLevel <= 5) {
            try {
                Log.w(Constants.LOGTAG, String.format(Locale.US, str, objArr));
            } catch (Exception e) {
                Log.e(Constants.LOGTAG, String.format(Locale.US, formatErrorMessage, new Object[]{str, Arrays.toString(objArr)}));
            }
        }
    }

    public void error(String str, Object... objArr) {
        if (this.logLevel.androidLogLevel <= 6) {
            try {
                Log.e(Constants.LOGTAG, String.format(Locale.US, str, objArr));
            } catch (Exception e) {
                Log.e(Constants.LOGTAG, String.format(Locale.US, formatErrorMessage, new Object[]{str, Arrays.toString(objArr)}));
            }
        }
    }

    public void Assert(String str, Object... objArr) {
        try {
            Log.println(7, Constants.LOGTAG, String.format(Locale.US, str, objArr));
        } catch (Exception e) {
            Log.e(Constants.LOGTAG, String.format(Locale.US, formatErrorMessage, new Object[]{str, Arrays.toString(objArr)}));
        }
    }
}
