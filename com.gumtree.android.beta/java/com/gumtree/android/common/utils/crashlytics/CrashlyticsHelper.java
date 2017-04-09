package com.gumtree.android.common.utils.crashlytics;

import android.content.Context;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import java.net.UnknownHostException;

public class CrashlyticsHelper {
    private static CrashlyticsHelper instance;

    protected CrashlyticsHelper() {
    }

    public static CrashlyticsHelper getInstance() {
        if (instance == null) {
            instance = new CrashlyticsHelper();
        }
        return instance;
    }

    public void catchGumtreeException(Throwable th) {
        if (!(th instanceof UnknownHostException)) {
            Crashlytics.logException(th);
        }
    }

    public void catchCustomLogging(String str) {
        Crashlytics.log(4, "GUMTREE", str);
    }

    public void initialize(Context context) {
        Fabric.with(context, new Kit[]{new Crashlytics()});
    }

    public void logBreadcrumb(String str) {
        Crashlytics.log(4, "GUMTREE BC", str);
    }
}
