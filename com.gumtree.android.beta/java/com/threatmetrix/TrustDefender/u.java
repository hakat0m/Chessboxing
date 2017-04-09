package com.threatmetrix.TrustDefender;

import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import com.apptentive.android.sdk.BuildConfig;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

class u implements ValueCallback<String> {
    private static final String d = w.a(u.class);
    CountDownLatch a = null;
    public String b;
    public final ArrayList<String> c = new ArrayList();

    public /* synthetic */ void onReceiveValue(Object obj) {
        obj = (String) obj;
        if (obj != null) {
            if (obj.length() == 2 && obj.equals("\"\"")) {
                obj = BuildConfig.FLAVOR;
            } else if (obj.length() > 1) {
                obj = obj.substring(1, obj.length() - 1);
            }
        }
        a(obj, "onReceiveValue");
    }

    u(CountDownLatch countDownLatch) {
        a(countDownLatch);
    }

    public final void a(CountDownLatch countDownLatch) {
        if (this.a != null) {
            w.c(d, "existing latch: " + this.a.hashCode() + " with count: " + this.a.getCount());
            w.c(d, "Setting latch when latch already has non-null value");
        }
        this.a = countDownLatch;
        if (this.a != null) {
            w.c(d, "new latch: " + countDownLatch.hashCode() + " with count: " + countDownLatch.getCount());
        }
    }

    private void a(String str, String str2) {
        try {
            String str3;
            CountDownLatch countDownLatch = this.a;
            if (str == null) {
                str3 = "null";
            } else {
                str3 = str;
            }
            long j = 0;
            if (countDownLatch != null) {
                j = countDownLatch.getCount();
            }
            w.c(d, "in " + str2 + "(" + str3 + ") count = " + j);
            this.b = str;
            if (str == null) {
                this.c.add(BuildConfig.FLAVOR);
            } else {
                this.c.add(str);
            }
            if (countDownLatch != null) {
                w.c(d, "countdown latch: " + countDownLatch.hashCode() + " with count: " + countDownLatch.getCount());
                countDownLatch.countDown();
                if (str2 == null) {
                    str2 = "null";
                }
                if (countDownLatch == null) {
                    w.c(d, "in " + str2 + "() with null latch");
                    return;
                } else {
                    w.c(d, "in " + str2 + "() count = " + countDownLatch.getCount() + " and " + (countDownLatch == this.a ? "latch constant" : "latch changed"));
                    return;
                }
            }
            w.a(d, "in " + str2 + "() latch == null");
        } catch (Exception e) {
            String str4 = d;
        }
    }

    @JavascriptInterface
    public final void a(String str) {
        a(str, "getString");
    }
}
