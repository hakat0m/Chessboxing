package com.comscore.analytics;

import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import com.apptentive.android.sdk.BuildConfig;
import com.comscore.applications.ApplicationMeasurement;
import com.comscore.applications.EventType;
import com.comscore.applications.KeepAlive;
import com.comscore.measurement.Measurement;
import com.comscore.measurement.MeasurementDispatcher;
import com.comscore.utils.CSLog;
import com.comscore.utils.CacheFlusher;
import com.comscore.utils.ConnectivityChangeReceiver;
import com.comscore.utils.Constants;
import com.comscore.utils.CustomExceptionHandler;
import com.comscore.utils.Date;
import com.comscore.utils.DispatchQueue;
import com.comscore.utils.OfflineMeasurementsCache;
import com.comscore.utils.Storage;
import com.comscore.utils.TransmissionMode;
import com.comscore.utils.Utils;
import com.comscore.utils.id.IdHelper;
import com.comscore.utils.task.TaskExecutor;
import com.gumtree.android.postad.DraftAd;
import com.gumtree.android.postad.payment.di.PostAdPaymentModule;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration$Builder;
import java.io.InputStream;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

public class Core {
    protected static final long x = 300;
    protected AtomicInteger A = new AtomicInteger(0);
    protected AtomicInteger B = new AtomicInteger(0);
    protected long C;
    protected long D;
    protected long E;
    protected long F;
    protected long G;
    protected long H;
    protected long I;
    protected long J;
    protected long K;
    protected SessionState L = SessionState.INACTIVE;
    protected long M;
    protected long N;
    protected long O;
    protected int P;
    protected int Q;
    protected int R;
    protected long S;
    protected long T;
    protected long U;
    protected int V;
    protected long W;
    protected long X;
    protected Runnable Y;
    protected String Z;
    OfflineMeasurementsCache a;
    String aa;
    Context ab;
    protected final HashMap<String, String> ac = new HashMap();
    protected final HashMap<String, String> ad = new HashMap();
    boolean ae = true;
    protected long af = 0;
    protected boolean ag = false;
    protected UncaughtExceptionHandler ah = Thread.getDefaultUncaughtExceptionHandler();
    boolean ai;
    TransmissionMode aj;
    TransmissionMode ak;
    String[] al;
    private IdHelper am;
    private boolean an = true;
    private boolean ao;
    Storage b;
    KeepAlive c;
    CacheFlusher d;
    @Deprecated
    DispatchQueue e;
    TaskExecutor f;
    MeasurementDispatcher g;
    ConnectivityChangeReceiver h;
    protected Runnable i;
    protected Runnable j;
    protected long k;
    protected boolean l = true;
    protected boolean m = true;
    boolean n = false;
    AtomicInteger o = new AtomicInteger();
    long p;
    AtomicInteger q = new AtomicInteger(0);
    long r;
    long s;
    String t;
    String u;
    boolean v = true;
    String w;
    protected ApplicationState y = ApplicationState.INACTIVE;
    protected AtomicInteger z = new AtomicInteger(0);

    public Core() {
        reset();
    }

    private void A() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("lastApplicationAccumulationTimestamp");
        arrayList.add("lastSessionAccumulationTimestamp");
        a("lastActivityTime", arrayList);
        a("ns_ap_fg", "foregroundTransitionsCount");
        a("installTime", "installId");
        a("ns_ap_ver", "previousVersion");
    }

    private String a(String str, Properties properties, boolean z) {
        if (properties != null) {
            String property = properties.getProperty(str);
            if (property != null) {
                this.b.set(str, property);
                return property;
            }
        }
        return (z && this.b.has(str).booleanValue()) ? this.b.get(str) : null;
    }

    private void a(TransmissionMode transmissionMode) {
        if (this.an) {
            this.aj = transmissionMode;
        }
    }

    private void b(TransmissionMode transmissionMode) {
        if (this.an) {
            this.ak = transmissionMode;
        }
    }

    private void b(String str) {
        if (this.an && this.am != null) {
            this.am.setPublisherSecret(str);
            this.am.generateIds();
        }
    }

    private void b(String str, String str2) {
        if (this.an) {
            this.ac.put(str, str2);
        }
    }

    private void c(String str) {
        if (this.an) {
            this.aa = str;
            if (this.b != null) {
                this.b.set("appName", this.aa);
            }
        }
    }

    private void d(String str) {
        if (this.an) {
            a(isSecure() ? "https://sb.scorecardresearch.com/p2?" : "http://b.scorecardresearch.com/p2?");
            b("c2", str);
        }
    }

    @Deprecated
    public static Core getInstance() {
        return comScore.getCore();
    }

    protected Measurement a(EventType eventType, HashMap<String, String> hashMap, String str) {
        return ApplicationMeasurement.newApplicationMeasurement(this, eventType, hashMap, str);
    }

    protected IdHelper a(Context context, Storage storage) {
        return new IdHelper(context, storage, this);
    }

    protected void a() {
        this.b = b();
        this.g = e();
        a(this.b);
        this.e = c();
        this.c = f();
        this.a = g();
        this.d = h();
        this.h = i();
        j();
        this.am = a(this.ab, this.b);
    }

    void a(int i, boolean z) {
        if (this.an) {
            w();
            if (i < 60) {
                i = 60;
            }
            this.l = z;
            this.k = (long) (i * PostAdPaymentModule.REDIRECT_TO_MANAGE_ADS_TIMEOUT);
            if (this.y == ApplicationState.FOREGROUND) {
                v();
            } else if (this.y == ApplicationState.BACKGROUND_UX_ACTIVE && !this.l) {
                v();
            }
        }
    }

    protected void a(ApplicationState applicationState) {
        CSLog.d(this, "Leaving application state: " + applicationState);
        switch (ab.a[applicationState.ordinal()]) {
            case HighlightView.GROW_NONE /*1*/:
                this.h.start();
                this.c.start(3000);
                this.ab.registerReceiver(this.h, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
                this.d.start();
                return;
            case HighlightView.GROW_LEFT_EDGE /*2*/:
                w();
                return;
            case ImageLoaderConfiguration$Builder.DEFAULT_THREAD_POOL_SIZE /*3*/:
                setCurrentActivityName(null);
                w();
                return;
            default:
                return;
        }
    }

    protected void a(ApplicationState applicationState, ApplicationState applicationState2) {
        if (this.an && applicationState2 != ApplicationState.INACTIVE && isAutoStartEnabled() && !this.n) {
            notify(EventType.START, this.ad, false);
        }
    }

    protected void a(SessionState sessionState) {
        if (this.an) {
            CSLog.d(this, "Leaving session state: " + sessionState);
            long unixTime = Date.unixTime();
            switch (ab.b[sessionState.ordinal()]) {
                case HighlightView.GROW_NONE /*1*/:
                    if (this.Y != null) {
                        this.f.removeEnqueuedTask(this.Y);
                        this.Y = null;
                    }
                    this.U = unixTime;
                    break;
                case HighlightView.GROW_LEFT_EDGE /*2*/:
                    break;
                case ImageLoaderConfiguration$Builder.DEFAULT_THREAD_POOL_SIZE /*3*/:
                    break;
                case HighlightView.GROW_RIGHT_EDGE /*4*/:
                    if (!p()) {
                        this.M = (unixTime - this.X) + this.M;
                        return;
                    }
                    return;
                default:
                    return;
            }
            this.T = unixTime;
            this.S = unixTime;
        }
    }

    protected void a(SessionState sessionState, SessionState sessionState2) {
    }

    void a(EventType eventType, HashMap<String, String> hashMap) {
        if (!this.an) {
            return;
        }
        if (z()) {
            x();
            return;
        }
        y();
        if (!(this.n || eventType == EventType.START)) {
            this.g.sendMeasurmement(a(EventType.START, new HashMap(), this.Z), false);
        }
        if (eventType != EventType.CLOSE) {
            this.g.sendMeasurmement(a(eventType, Utils.mapOfStrings(hashMap), this.Z), false);
        }
    }

    protected void a(Storage storage) {
        A();
        this.g.loadEventData();
    }

    void a(String str) {
        if (this.an) {
            int indexOf = str.indexOf(63);
            if (indexOf < 0) {
                str = str + '?';
            } else if (indexOf < str.length() - 1) {
                for (String split : str.substring(indexOf + 1).split("&")) {
                    String[] split2 = split.split("=");
                    if (split2.length == 2) {
                        setLabel(split2[0], split2[1], false);
                    } else if (split2.length == 1) {
                        setLabel("name", split2[0], false);
                    }
                }
                str = str.substring(0, indexOf + 1);
            }
            this.Z = str;
        }
    }

    void a(String str, String str2) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(str2);
        a(str, arrayList);
    }

    void a(String str, ArrayList<String> arrayList) {
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            String str2 = (String) it.next();
            String str3 = this.b.get(str);
            String str4 = this.b.get(str2);
            if (Utils.isNotEmpty(str3) && Utils.isEmpty(str4)) {
                this.b.set(str2, str3);
            }
        }
        this.b.remove(str);
    }

    protected void a(boolean z) {
        if (this.an) {
            long unixTime = Date.unixTime();
            long j = unixTime - this.K;
            switch (ab.a[this.y.ordinal()]) {
                case HighlightView.GROW_NONE /*1*/:
                    this.H += j;
                    this.E = j + this.E;
                    break;
                case HighlightView.GROW_LEFT_EDGE /*2*/:
                    this.F += j;
                    this.D = j + this.D;
                    break;
                case ImageLoaderConfiguration$Builder.DEFAULT_THREAD_POOL_SIZE /*3*/:
                    this.G += j;
                    this.C = j + this.C;
                    break;
            }
            this.K = unixTime;
            if (z) {
                this.b.set("lastApplicationAccumulationTimestamp", Long.toString(this.K));
                this.b.set("foregroundTransitionsCount", Long.toString((long) this.B.get()));
                this.b.set("accumulatedForegroundTime", Long.toString(this.G));
                this.b.set("accumulatedBackgroundTime", Long.toString(this.F));
                this.b.set("accumulatedInactiveTime", Long.toString(this.H));
                this.b.set("totalForegroundTime", Long.toString(this.C));
                this.b.set("totalBackgroundTime", Long.toString(this.D));
                this.b.set("totalInactiveTime", Long.toString(this.E));
            }
        }
    }

    public void allowLiveTransmission(TransmissionMode transmissionMode, boolean z) {
        if (!this.an || transmissionMode == null) {
            return;
        }
        if (!z) {
            a(transmissionMode);
        } else if (this.f != null && getLiveTransmissionMode() != transmissionMode) {
            this.f.execute(new i(this, transmissionMode), z);
        }
    }

    public void allowOfflineTransmission(TransmissionMode transmissionMode, boolean z) {
        if (!this.an || transmissionMode == null) {
            return;
        }
        if (!z) {
            b(transmissionMode);
        } else if (this.f != null && getOfflineTransmissionMode() != transmissionMode) {
            this.f.execute(new j(this, transmissionMode), z);
        }
    }

    protected Storage b() {
        return new Storage(this.ab);
    }

    protected void b(ApplicationState applicationState) {
        if (this.an) {
            CSLog.d(this, "Entering application state: " + applicationState);
            switch (ab.a[applicationState.ordinal()]) {
                case HighlightView.GROW_NONE /*1*/:
                    this.h.stop();
                    this.c.stop();
                    this.d.stop();
                    try {
                        this.ab.unregisterReceiver(this.h);
                    } catch (IllegalArgumentException e) {
                    }
                    w();
                    return;
                case HighlightView.GROW_LEFT_EDGE /*2*/:
                    if (!this.l) {
                        v();
                        return;
                    }
                    return;
                case ImageLoaderConfiguration$Builder.DEFAULT_THREAD_POOL_SIZE /*3*/:
                    v();
                    this.B.getAndIncrement();
                    return;
                default:
                    return;
            }
        }
    }

    protected void b(SessionState sessionState) {
        if (this.an) {
            CSLog.d(this, "Entering session state: " + sessionState);
            switch (ab.b[sessionState.ordinal()]) {
                case HighlightView.GROW_NONE /*1*/:
                    q();
                    o();
                    break;
                case HighlightView.GROW_LEFT_EDGE /*2*/:
                    break;
                case ImageLoaderConfiguration$Builder.DEFAULT_THREAD_POOL_SIZE /*3*/:
                    break;
                default:
                    return;
            }
            r();
            p();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected void b(boolean r7) {
        /*
        r6 = this;
        r0 = r6.an;
        if (r0 != 0) goto L_0x0005;
    L_0x0004:
        return;
    L_0x0005:
        r0 = com.comscore.utils.Date.unixTime();
        r2 = r6.X;
        r2 = r0 - r2;
        r4 = com.comscore.analytics.ab.b;
        r5 = r6.L;
        r5 = r5.ordinal();
        r4 = r4[r5];
        switch(r4) {
            case 1: goto L_0x00d8;
            case 2: goto L_0x00df;
            case 3: goto L_0x00e6;
            default: goto L_0x001a;
        };
    L_0x001a:
        r6.X = r0;
        if (r7 == 0) goto L_0x0004;
    L_0x001e:
        r0 = r6.b;
        r1 = "lastSessionAccumulationTimestamp";
        r2 = r6.X;
        r2 = java.lang.Long.toString(r2);
        r0.set(r1, r2);
        r0 = r6.b;
        r1 = "lastApplicationSessionTimestamp";
        r2 = r6.S;
        r2 = java.lang.Long.toString(r2);
        r0.set(r1, r2);
        r0 = r6.b;
        r1 = "lastUserSessionTimestamp";
        r2 = r6.T;
        r2 = java.lang.Long.toString(r2);
        r0.set(r1, r2);
        r0 = r6.b;
        r1 = "lastActiveUserSessionTimestamp";
        r2 = r6.U;
        r2 = java.lang.Long.toString(r2);
        r0.set(r1, r2);
        r0 = r6.b;
        r1 = "accumulatedApplicationSessionTime";
        r2 = r6.M;
        r2 = java.lang.Long.toString(r2);
        r0.set(r1, r2);
        r0 = r6.b;
        r1 = "accumulatedActiveUserSessionTime";
        r2 = r6.O;
        r2 = java.lang.Long.toString(r2);
        r0.set(r1, r2);
        r0 = r6.b;
        r1 = "accumulatedUserSessionTime";
        r2 = r6.N;
        r2 = java.lang.Long.toString(r2);
        r0.set(r1, r2);
        r0 = r6.b;
        r1 = "activeUserSessionCount";
        r2 = r6.R;
        r2 = (long) r2;
        r2 = java.lang.Long.toString(r2);
        r0.set(r1, r2);
        r0 = r6.b;
        r1 = "userSessionCount";
        r2 = r6.Q;
        r2 = (long) r2;
        r2 = java.lang.Long.toString(r2);
        r0.set(r1, r2);
        r0 = r6.b;
        r1 = "lastUserInteractionTimestamp";
        r2 = r6.W;
        r2 = java.lang.Long.toString(r2);
        r0.set(r1, r2);
        r0 = r6.b;
        r1 = "userInteractionCount";
        r2 = r6.V;
        r2 = java.lang.Integer.toString(r2);
        r0.set(r1, r2);
        r0 = r6.b;
        r1 = "previousGenesis";
        r2 = r6.J;
        r2 = java.lang.Long.toString(r2);
        r0.set(r1, r2);
        r0 = r6.b;
        r1 = "genesis";
        r2 = r6.I;
        r2 = java.lang.Long.toString(r2);
        r0.set(r1, r2);
        r0 = r6.b;
        r1 = "applicationSessionCountKey";
        r2 = r6.P;
        r2 = java.lang.Integer.toString(r2);
        r0.set(r1, r2);
        goto L_0x0004;
    L_0x00d8:
        r4 = r6.O;
        r4 = r4 + r2;
        r6.O = r4;
        r6.U = r0;
    L_0x00df:
        r4 = r6.N;
        r4 = r4 + r2;
        r6.N = r4;
        r6.T = r0;
    L_0x00e6:
        r4 = r6.M;
        r2 = r2 + r4;
        r6.M = r2;
        r6.S = r0;
        goto L_0x001a;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.comscore.analytics.Core.b(boolean):void");
    }

    @Deprecated
    protected DispatchQueue c() {
        return new DispatchQueue(this);
    }

    void c(boolean z) {
        this.ae = z;
    }

    protected TaskExecutor d() {
        return new TaskExecutor(this);
    }

    protected void d(boolean z) {
        if (this.an) {
            this.n = z;
        }
    }

    public void disableAutoUpdate() {
        if (this.an) {
            w();
            this.l = true;
            this.k = -1;
        }
    }

    protected MeasurementDispatcher e() {
        return new MeasurementDispatcher(this);
    }

    public void enableAutoUpdate(int i, boolean z, boolean z2) {
        if (!this.an) {
            return;
        }
        if (!z2) {
            a(i, z);
        } else if (this.f != null) {
            this.f.execute(new x(this, i, z), z2);
        }
    }

    protected KeepAlive f() {
        return new KeepAlive(this, 86400000);
    }

    public void flush(boolean z) {
        if (this.an && this.f != null) {
            this.f.execute(new v(this), z);
        }
    }

    protected OfflineMeasurementsCache g() {
        return new OfflineMeasurementsCache(this);
    }

    public int getActiveUserSessionCountDelta(boolean z) {
        int i = -1;
        if (this.R >= 0) {
            i = this.R;
            if (z && this.an) {
                this.R = 0;
                this.b.set("activeUserSessionCount", Integer.toString(this.R));
            }
        }
        return i;
    }

    public long getActiveUserSessionTimeDelta(boolean z) {
        long j = this.O;
        if (z && this.an) {
            this.O = 0;
            this.b.set("accumulatedActiveUserSessionTime", Long.toString(this.O));
        }
        return j;
    }

    public Context getAppContext() {
        return this.ab;
    }

    public String getAppName() {
        if ((this.aa == null || this.aa.length() == 0) && this.ab != null) {
            String packageName = this.ab.getPackageName();
            PackageManager packageManager = this.ab.getPackageManager();
            try {
                CharSequence applicationLabel = packageManager.getApplicationLabel(packageManager.getApplicationInfo(packageName, 0));
                if (applicationLabel != null) {
                    setAppName(applicationLabel.toString(), false);
                }
            } catch (NameNotFoundException e) {
                this.aa = this.b.get("appName");
            }
        }
        return this.aa;
    }

    public int getApplicationSessionCountDelta(boolean z) {
        int i = this.P;
        if (z && this.an) {
            this.P = 0;
            this.b.set("applicationSessionCountKey", Integer.toString(this.P));
        }
        return i;
    }

    public long getApplicationSessionTimeDelta(boolean z) {
        long j = this.M;
        if (z && this.an) {
            this.M = 0;
            this.b.set("accumulatedApplicationSessionTime", Long.toString(this.M));
        }
        return j;
    }

    public ApplicationState getApplicationState() {
        return this.y;
    }

    public String getAutoStartLabel(String str) {
        return (String) this.ad.get(str);
    }

    public HashMap<String, String> getAutoStartLabels() {
        return this.ad;
    }

    public long getAutoUpdateInterval() {
        return this.k;
    }

    public long getBackgroundTimeDelta(boolean z) {
        long j = this.F;
        if (z && this.an) {
            this.F = 0;
            this.b.set("accumulatedBackgroundTime", Long.toString(this.F));
        }
        return j;
    }

    public long getBackgroundTotalTime(boolean z) {
        long j = this.D;
        if (z && this.an) {
            this.D = 0;
            this.b.set("totalBackgroundTime", Long.toString(this.D));
        }
        return j;
    }

    public CacheFlusher getCacheFlusher() {
        return this.d;
    }

    public long getCacheFlushingInterval() {
        return this.af;
    }

    public int getCacheMaxBatchFiles() {
        return this.a != null ? this.a.getCacheMaxBatchFiles() : 100;
    }

    public int getCacheMaxFlushesInARow() {
        return this.a != null ? this.a.getCacheMaxPosts() : 10;
    }

    public int getCacheMaxMeasurements() {
        return this.a != null ? this.a.getCacheMaxMeasurements() : 2000;
    }

    public long getCacheMeasurementExpiry() {
        return this.a != null ? this.a.getCacheMeasurementExpiry() : 31;
    }

    public long getCacheMinutesToRetry() {
        return this.a != null ? this.a.getCacheWaitMinutes() : 30;
    }

    public int getColdStartCount() {
        return this.q.get();
    }

    public long getColdStartId() {
        return this.p;
    }

    public ConnectivityChangeReceiver getConnectivityReceiver() {
        return this.h;
    }

    public String getCrossPublisherId() {
        return this.am == null ? null : this.am.getCrossPublisherId();
    }

    public String getCurrentActivityName() {
        return this.w;
    }

    public String getCurrentVersion() {
        return this.t;
    }

    public String getCustomerC2() {
        return (String) getLabels().get("c2");
    }

    public boolean getErrorHandlingEnabled() {
        return this.ag;
    }

    public long getFirstInstallId() {
        return this.s;
    }

    public long getForegroundTimeDelta(boolean z) {
        long j = this.G;
        if (z && this.an) {
            this.G = 0;
            this.b.set("accumulatedForegroundTime", Long.toString(this.G));
        }
        return j;
    }

    public long getForegroundTotalTime(boolean z) {
        long j = this.C;
        if (z && this.an) {
            this.C = 0;
            this.b.set("totalForegroundTime", Long.toString(this.C));
        }
        return j;
    }

    public int getForegroundTransitionsCountDelta(boolean z) {
        int i = this.B.get();
        if (z && this.an) {
            this.B.set(0);
            this.b.set("foregroundTransitionsCount", Long.toString((long) this.B.get()));
        }
        return i;
    }

    public long getGenesis() {
        return this.I;
    }

    public IdHelper getIdHelper() {
        return this.am;
    }

    public long getInactiveTimeDelta(boolean z) {
        long j = this.H;
        if (z && this.an) {
            this.H = 0;
            this.b.set("accumulatedInactiveTime", Long.toString(this.H));
        }
        return j;
    }

    public long getInactiveTotalTime(boolean z) {
        long j = this.E;
        if (z && this.an) {
            this.E = 0;
            this.b.set("totalInactiveTime", Long.toString(this.E));
        }
        return j;
    }

    public long getInstallId() {
        return this.r;
    }

    public KeepAlive getKeepAlive() {
        return this.c;
    }

    public String getLabel(String str) {
        return (String) this.ac.get(str);
    }

    public HashMap<String, String> getLabels() {
        return this.ac;
    }

    public TransmissionMode getLiveTransmissionMode() {
        return this.aj;
    }

    public MeasurementDispatcher getMeasurementDispatcher() {
        return this.g;
    }

    public String[] getMeasurementLabelOrder() {
        return this.al;
    }

    public OfflineMeasurementsCache getOfflineCache() {
        return this.a;
    }

    public TransmissionMode getOfflineTransmissionMode() {
        return this.ak;
    }

    public String getPixelURL() {
        return this.Z;
    }

    public long getPreviousGenesis() {
        return this.J;
    }

    public String getPreviousVersion() {
        String str = this.u;
        if (this.u != null && this.u.length() > 0) {
            this.b.remove("previousVersion");
            this.u = null;
        }
        return str;
    }

    public String getPublisherSecret() {
        return this.am == null ? BuildConfig.FLAVOR : this.am.getPublisherSecret();
    }

    public DispatchQueue getQueue() {
        return this.e;
    }

    public int getRunsCount() {
        return this.o.get();
    }

    @Deprecated
    public String getSalt() {
        return getPublisherSecret();
    }

    public SessionState getSessionState() {
        return this.L;
    }

    public Storage getStorage() {
        return this.b;
    }

    public TaskExecutor getTaskExecutor() {
        return this.f;
    }

    public int getUserInteractionCount(boolean z) {
        int i = this.V;
        if (z && this.an) {
            this.V = 0;
            this.b.set("userInteractionCount", Integer.toString(this.V));
        }
        return i;
    }

    public int getUserSessionCountDelta(boolean z) {
        int i = -1;
        if (this.Q >= 0) {
            i = this.Q;
            if (z && this.an) {
                this.Q = 0;
                this.b.set("userSessionCount", Integer.toString(this.Q));
            }
        }
        return i;
    }

    public long getUserSessionTimeDelta(boolean z) {
        long j = this.N;
        if (z && this.an) {
            this.N = 0;
            this.b.set("accumulatedUserSessionTime", Long.toString(this.N));
        }
        return j;
    }

    public String getVersion() {
        return "3.1508.28";
    }

    public String getVisitorId() {
        return this.am == null ? null : this.am.getVisitorId();
    }

    protected CacheFlusher h() {
        return new CacheFlusher(this);
    }

    public boolean handleColdStart() {
        if (!this.an || this.n) {
            return false;
        }
        this.n = true;
        this.q.getAndIncrement();
        this.b.set("coldStartCount", String.valueOf(this.q));
        this.p = Date.unixTime();
        return true;
    }

    protected ConnectivityChangeReceiver i() {
        return new ConnectivityChangeReceiver(this);
    }

    public void incrementRunsCount() {
        if (this.an) {
            this.o.getAndIncrement();
            this.b.set("runs", Long.toString((long) this.o.get()));
        }
    }

    public boolean isAutoStartEnabled() {
        return this.v;
    }

    public boolean isAutoUpdateEnabled() {
        return this.k > 0;
    }

    public boolean isEnabled() {
        return this.an;
    }

    public boolean isKeepAliveEnabled() {
        return this.ae;
    }

    public boolean isSecure() {
        return this.ai;
    }

    protected void j() {
        this.K = Utils.getLong(this.b.get("lastApplicationAccumulationTimestamp"), -1);
        this.X = Utils.getLong(this.b.get("lastSessionAccumulationTimestamp"), -1);
        this.S = Utils.getLong(this.b.get("lastApplicationSessionTimestamp"), -1);
        this.T = Utils.getLong(this.b.get("lastUserSessionTimestamp"), -1);
        this.U = Utils.getLong(this.b.get("lastActiveUserSessionTimestamp"), -1);
        this.B.set(Utils.getInteger(this.b.get("foregroundTransitionsCount")));
        this.G = Utils.getLong(this.b.get("accumulatedForegroundTime"));
        this.F = Utils.getLong(this.b.get("accumulatedBackgroundTime"));
        this.H = Utils.getLong(this.b.get("accumulatedInactiveTime"));
        this.C = Utils.getLong(this.b.get("totalForegroundTime"));
        this.D = Utils.getLong(this.b.get("totalBackgroundTime"));
        this.E = Utils.getLong(this.b.get("totalInactiveTime"));
        this.M = Utils.getLong(this.b.get("accumulatedApplicationSessionTime"));
        this.O = Utils.getLong(this.b.get("accumulatedActiveUserSessionTime"));
        this.N = Utils.getLong(this.b.get("accumulatedUserSessionTime"));
        this.R = Utils.getInteger(this.b.get("activeUserSessionCount"), -1);
        this.Q = Utils.getInteger(this.b.get("userSessionCount"), -1);
        this.W = Utils.getLong(this.b.get("lastUserInteractionTimestamp"), -1);
        this.V = Utils.getInteger(this.b.get("userInteractionCount"), 0);
        this.P = Utils.getInteger(this.b.get("applicationSessionCountKey"), 0);
        this.t = k();
        this.J = Utils.getLong(this.b.get("previousGenesis"), 0);
        this.I = Utils.getLong(this.b.get("genesis"), -1);
        if (this.I < 0) {
            this.I = Date.unixTime();
            this.J = 0;
            this.S = this.I;
            this.P++;
        } else {
            if (!p()) {
                this.M += Date.unixTime() - this.X;
                this.b.set("accumulatedApplicationSessionTime", Long.toString(this.M));
            }
            this.S = this.I;
        }
        this.s = Utils.getLong(this.b.get("firstInstallId"), -1);
        if (this.s < 0) {
            this.s = this.I;
            this.r = this.I;
            this.b.set("currentVersion", this.t);
            this.b.set("firstInstallId", String.valueOf(this.s));
            this.b.set("installId", String.valueOf(this.r));
        } else {
            if (this.b.has("previousVersion").booleanValue()) {
                this.u = this.b.get("previousVersion");
            }
            String str = this.b.get("currentVersion");
            if (str.equals(this.t)) {
                this.r = Utils.getLong(this.b.get("installId"), -1);
            } else {
                this.u = str;
                this.b.set("previousVersion", this.u);
                this.r = this.I;
                this.b.set("installId", String.valueOf(this.r));
            }
            this.b.set("currentVersion", this.t);
        }
        this.b.set("genesis", Long.toString(this.I));
        this.b.set("previousGenesis", Long.toString(this.J));
        long unixTime = Date.unixTime();
        if (this.K >= 0) {
            long j = unixTime - this.K;
            this.H += j;
            this.b.set("accumulatedInactiveTime", Long.toString(this.H));
            this.E = j + this.E;
            this.b.set("totalInactiveTime", Long.toString(this.E));
        }
        this.K = unixTime;
        this.X = unixTime;
        this.b.set("lastApplicationAccumulationTimestamp", Long.toString(this.K));
        this.b.set("lastSessionAccumulationTimestamp", Long.toString(this.X));
        this.b.set("lastApplicationSessionTimestamp", Long.toString(this.S));
        if (!this.b.has("runs").booleanValue()) {
            this.b.set("runs", DraftAd.NEW_AD_ID);
        }
        this.o.set(Utils.getInteger(this.b.get("runs")));
        this.q.set(Utils.getInteger(this.b.get("coldStartCount")));
    }

    protected String k() {
        String packageName = this.ab.getPackageName();
        String str = DraftAd.NEW_AD_ID;
        try {
            return this.ab.getPackageManager().getPackageInfo(packageName, 0).versionName;
        } catch (NameNotFoundException e) {
            return str;
        }
    }

    protected void l() {
        if (this.ab != null) {
            InputStream open = this.ab.getResources().getAssets().open("comScore.properties");
            Properties properties = new Properties();
            properties.load(open);
            Constants.DEBUG = Utils.getBoolean(a("Debug", properties, false));
            this.ai = Utils.getBoolean(a("Secure", properties, false));
            String a = a("PublisherSecret", properties, true);
            if (a != null) {
                b(a);
            }
            a = a("AppName", properties, true);
            if (a != null) {
                c(a);
            }
            a = a("CustomerC2", properties, false);
            if (a != null) {
                d(a);
            }
            a = a("PixelURL", properties, false);
            if (a != null) {
                a(a);
            }
            a = a("OfflineURL", properties, false);
            if (a != null) {
                this.a.setUrl(a);
            }
            a = a("LiveTransmissionMode", properties, false);
            if (a != null) {
                try {
                    this.aj = TransmissionMode.valueOf(a.toUpperCase(Locale.getDefault()));
                } catch (IllegalArgumentException e) {
                    this.aj = TransmissionMode.DEFAULT;
                }
            }
            try {
                a = a("OfflineTransmissionMode", properties, false);
                if (a != null) {
                    try {
                        this.ak = TransmissionMode.valueOf(a.toUpperCase(Locale.getDefault()));
                    } catch (IllegalArgumentException e2) {
                        this.ak = TransmissionMode.DEFAULT;
                    }
                }
                this.ae = Utils.getBoolean(a("KeepAliveEnabled", properties, false), true);
                int integer = Utils.getInteger(a("CacheMaxSize", properties, false), -1);
                if (integer >= 0) {
                    this.a.setCacheMaxMeasurements(integer);
                }
                integer = Utils.getInteger(a("CacheMaxBatchSize", properties, false), -1);
                if (integer >= 0) {
                    this.a.setCacheMaxBatchFiles(integer);
                }
                integer = Utils.getInteger(a("CacheMaxFlushesInARow", properties, false), -1);
                if (integer >= 0) {
                    this.a.setCacheMaxPosts(integer);
                }
                integer = Utils.getInteger(a("CacheMinutesToRetry", properties, false), -1);
                if (integer >= 0) {
                    this.a.setCacheWaitMinutes(integer);
                }
                integer = Utils.getInteger(a("CacheExpiryInDays", properties, false), -1);
                if (integer >= 0) {
                    this.a.setCacheMeasurementExpiry(integer);
                }
                long j = Utils.getLong(a("CacheFlushingInterval", properties, false), -1);
                if (j >= 0) {
                    this.af = j;
                    if (this.d != null) {
                        this.d.update();
                    }
                }
                setErrorHandlingEnabled(Utils.getBoolean(a("ErrorHandlingEnabled", properties, false)));
                this.v = Utils.getBoolean(a("AutoStartEnabled", properties, false), true);
                boolean z = Utils.getBoolean(a("AutoUpdateInForegroundOnly", properties, false), true);
                int integer2 = Utils.getInteger(a("AutoUpdateInterval", properties, false), -1);
                if (integer2 >= 60) {
                    a(integer2, z);
                }
            } catch (Exception e3) {
                if (Constants.DEBUG) {
                    CSLog.printStackTrace(e3);
                }
            }
        }
    }

    protected Context m() {
        return this.ab;
    }

    protected void n() {
        if (this.an) {
            if (this.f.containsTask(this.j)) {
                this.f.removeEnqueuedTask(this.j);
                this.j = null;
            }
            long unixTime = Date.unixTime();
            ApplicationState applicationState = this.z.get() > 0 ? ApplicationState.FOREGROUND : this.A.get() > 0 ? ApplicationState.BACKGROUND_UX_ACTIVE : ApplicationState.INACTIVE;
            SessionState sessionState = unixTime - this.W < 300000 ? SessionState.ACTIVE_USER : this.A.get() > 0 ? SessionState.USER : this.z.get() > 0 ? SessionState.APPLICATION : SessionState.INACTIVE;
            ApplicationState applicationState2 = this.y;
            SessionState sessionState2 = this.L;
            if (applicationState != applicationState2 || sessionState != sessionState2) {
                this.j = new af(this, applicationState2, applicationState, sessionState2, sessionState);
                if (!this.m || applicationState == ApplicationState.FOREGROUND) {
                    this.j.run();
                    this.j = null;
                    return;
                }
                this.f.execute(this.j, x);
            }
        }
    }

    public void notify(EventType eventType, HashMap<String, String> hashMap, boolean z) {
        if (!this.an) {
            return;
        }
        if (!z) {
            a(eventType, (HashMap) hashMap);
        } else if (this.f != null) {
            this.f.execute(new z(this, eventType, hashMap), z);
        }
    }

    protected void o() {
        if (this.an) {
            if (this.Y != null) {
                this.f.removeEnqueuedTask(this.Y);
                this.Y = null;
            }
            this.Y = new UserInteractionTask(this);
            this.f.execute(this.Y, 300000);
        }
    }

    public void onEnterForeground() {
        if (this.an && this.f != null) {
            this.f.execute(new ac(this), true);
        }
    }

    public void onExitForeground() {
        if (this.an && this.f != null) {
            this.f.execute(new ad(this), true);
        }
    }

    public void onUserInteraction() {
        if (this.an && this.f != null) {
            this.f.execute(new ae(this), true);
        }
    }

    public void onUxActive() {
        if (this.an && this.f != null) {
            this.f.execute(new l(this), true);
        }
    }

    public void onUxInactive() {
        if (this.an && this.f != null) {
            this.f.execute(new w(this), true);
        }
    }

    protected boolean p() {
        boolean z = false;
        if (this.an) {
            long unixTime = Date.unixTime();
            if (unixTime - this.S > 1800000) {
                this.J = this.I;
                this.I = unixTime;
                this.P++;
                z = true;
            }
            this.S = unixTime;
        }
        return z;
    }

    protected void q() {
        if (this.an) {
            long unixTime = Date.unixTime();
            if (unixTime - this.U >= 300000) {
                this.R++;
            }
            this.U = unixTime;
        }
    }

    protected void r() {
        if (this.an) {
            long unixTime = Date.unixTime();
            if (unixTime - this.T >= 300000) {
                this.Q++;
            }
            this.T = unixTime;
        }
    }

    public void reset() {
        this.aj = TransmissionMode.DEFAULT;
        this.ak = TransmissionMode.DEFAULT;
        this.ai = false;
        this.al = Constants.LABELS_ORDER;
        this.y = ApplicationState.INACTIVE;
        this.L = SessionState.INACTIVE;
        this.n = false;
        this.o.set(0);
        this.p = -1;
        this.q.set(0);
        this.s = -1;
        this.r = -1;
        this.t = null;
        this.u = null;
        this.z.set(0);
        this.A.set(0);
        this.C = 0;
        this.D = 0;
        this.E = 0;
        this.F = 0;
        this.G = 0;
        this.H = 0;
        this.M = 0;
        this.O = 0;
        this.N = 0;
        this.I = -1;
        this.J = 0;
        this.R = -1;
        this.Q = -1;
        this.V = 0;
        this.W = -1;
        this.K = -1;
        this.X = -1;
        this.S = -1;
        this.T = -1;
        this.U = -1;
        this.r = -1;
        this.s = -1;
        disableAutoUpdate();
        if (this.j != null) {
            this.f.removeEnqueuedTask(this.j);
            this.j = null;
        }
        if (this.Y != null) {
            this.f.removeEnqueuedTask(this.Y);
            this.Y = null;
        }
        if (this.c != null) {
            this.c.cancel();
        }
        if (this.d != null) {
            this.d.stop();
        }
        if (this.f != null) {
            this.f.removeAllEnqueuedTasks();
        }
        if (this.b != null) {
            this.b.close();
        }
    }

    public void resetVisitorId() {
        this.f.execute(new a(this), true);
    }

    protected void s() {
        a(true);
    }

    public void setAppContext(Context context) {
        if (this.ab == null && context != null) {
            this.ab = context;
            this.f = d();
            this.f.execute(new ag(this), true);
        }
    }

    public void setAppName(String str, boolean z) {
        if (!this.an) {
            return;
        }
        if (!z) {
            c(str);
        } else if (this.f != null) {
            this.f.execute(new e(this, str), z);
        }
    }

    public void setAutoStartEnabled(boolean z, boolean z2) {
        if (this.an) {
            this.f.execute(new n(this, z), z2);
        }
    }

    public void setAutoStartLabel(String str, String str2) {
        if (this.an) {
            this.ad.put(str, str2);
        }
    }

    public void setAutoStartLabels(HashMap<String, String> hashMap) {
        if (this.an && hashMap != null) {
            this.ad.putAll(Utils.mapOfStrings(hashMap));
        }
    }

    public void setCacheFlushingInterval(long j, boolean z) {
        if (this.an && this.f != null && this.af != j) {
            this.f.execute(new t(this, j), z);
        }
    }

    public void setCacheMaxBatchFiles(int i, boolean z) {
        if (this.an && this.f != null && this.a != null) {
            this.f.execute(new p(this, i), z);
        }
    }

    public void setCacheMaxFlushesInARow(int i, boolean z) {
        if (this.an && this.f != null && this.a != null) {
            this.f.execute(new q(this, i), z);
        }
    }

    public void setCacheMaxMeasurements(int i, boolean z) {
        if (this.an && this.f != null && this.a != null) {
            this.f.execute(new o(this, i), z);
        }
    }

    public void setCacheMeasurementExpiry(int i, boolean z) {
        if (this.an && this.f != null && this.a != null) {
            this.f.execute(new s(this, i), z);
        }
    }

    public void setCacheMinutesToRetry(int i, boolean z) {
        if (this.an && this.f != null && this.a != null) {
            this.f.execute(new r(this, i), z);
        }
    }

    public void setCurrentActivityName(String str) {
        this.w = str;
    }

    public void setCustomerC2(String str, boolean z) {
        if (this.an && str != null && str.length() != 0) {
            if (!z) {
                d(str);
            } else if (this.f != null) {
                this.f.execute(new h(this, str), z);
            }
        }
    }

    public void setDebug(boolean z) {
        if (this.an) {
            this.f.execute(new m(this, z), true);
        }
    }

    public void setEnabled(boolean z) {
        this.f.execute(new aa(this, z), true);
    }

    public void setErrorHandlingEnabled(boolean z) {
        if (this.an) {
            this.ag = z;
            if (z) {
                Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler(this));
            } else if (Thread.getDefaultUncaughtExceptionHandler() != this.ah) {
                Thread.setDefaultUncaughtExceptionHandler(this.ah);
            }
        }
    }

    public void setKeepAliveEnabled(boolean z, boolean z2) {
        if (!this.an) {
            return;
        }
        if (!z2) {
            c(z);
        } else if (this.f != null) {
            this.f.execute(new c(this, z), z2);
        }
    }

    public void setLabel(String str, String str2, boolean z) {
        if (!this.an) {
            return;
        }
        if (!z) {
            b(str, str2);
        } else if (this.f != null) {
            this.f.execute(new g(this, str, str2), z);
        }
    }

    public void setLabels(HashMap<String, String> hashMap, boolean z) {
        if (this.an && hashMap != null && this.f != null) {
            this.f.execute(new f(this, hashMap), z);
        }
    }

    public void setMeasurementLabelOrder(String[] strArr, boolean z) {
        if (this.an && this.f != null && strArr != this.al && strArr != null && strArr.length > 0) {
            this.f.execute(new u(this, strArr), z);
        }
    }

    public void setOfflineURL(String str) {
        if (this.an && str != null && str.length() != 0 && this.f != null) {
            this.f.execute(new b(this, str), true);
        }
    }

    public void setPixelURL(String str, boolean z) {
        if (this.an && str != null && str.length() != 0) {
            if (!z) {
                a(str);
            } else if (this.f != null) {
                this.f.execute(new ah(this, str), z);
            }
        }
    }

    public void setPublisherSecret(String str, boolean z) {
        if (this.an && str != null && str.length() != 0 && this.f != null) {
            this.f.execute(new d(this, str), z);
        }
    }

    public void setSecure(boolean z, boolean z2) {
        if (!this.an) {
            return;
        }
        if (!z2) {
            this.ai = z;
        } else if (this.f != null) {
            this.f.execute(new k(this, z), z2);
        }
    }

    protected void t() {
        b(true);
    }

    protected OfflineMeasurementsCache u() {
        return this.a;
    }

    public void update() {
        update(true);
    }

    public void update(boolean z) {
        if (this.an) {
            if (this.f.containsTask(this.j)) {
                this.f.removeEnqueuedTask(this.j);
                this.j.run();
                this.j = null;
            }
            a(z);
            b(z);
        }
    }

    protected void v() {
        if (this.an) {
            w();
            if (this.k >= 60000) {
                this.i = new y(this);
                this.f.execute(this.i, this.k, true, this.k);
            }
        }
    }

    protected void w() {
        if (this.an && this.i != null) {
            this.f.removeEnqueuedTask(this.i);
            this.i = null;
        }
    }

    protected void x() {
    }

    protected void y() {
    }

    boolean z() {
        return this.ab == null || this.am.isPublisherSecretEmpty() || this.Z == null || this.Z.length() == 0;
    }
}
