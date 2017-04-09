package com.gumtree;

@Deprecated
public abstract class Log {
    private static Log instance;

    protected abstract void debug(String str, String str2);

    protected abstract void debug(String str, String str2, Throwable th);

    protected abstract void error(String str, String str2);

    protected abstract void error(String str, String str2, Throwable th);

    protected abstract void info(String str, String str2);

    protected abstract void info(String str, String str2, Throwable th);

    protected abstract void verbose(String str, String str2);

    protected abstract void verbose(String str, String str2, Throwable th);

    protected abstract void warn(String str, String str2);

    protected abstract void warn(String str, String str2, Throwable th);

    protected Log() {
    }

    public static void setInstance(Log log) {
        instance = log;
    }

    public static void v(String str, String str2) {
        instance.verbose(str, str2);
    }

    public static void v(String str, String str2, Throwable th) {
        instance.verbose(str, str2, th);
    }

    public static void d(String str, String str2) {
        instance.debug(str, str2);
    }

    public static void d(String str, String str2, Throwable th) {
        instance.debug(str, str2, th);
    }

    public static void i(String str, String str2) {
        instance.info(str, str2);
    }

    public static void i(String str, String str2, Throwable th) {
        instance.info(str, str2, th);
    }

    public static void w(String str, String str2) {
        instance.warn(str, str2);
    }

    public static void w(String str, String str2, Throwable th) {
        instance.warn(str, str2, th);
    }

    public static void e(String str, String str2) {
        instance.error(str, str2);
    }

    public static void e(String str, String str2, Throwable th) {
        instance.error(str, str2, th);
    }
}
