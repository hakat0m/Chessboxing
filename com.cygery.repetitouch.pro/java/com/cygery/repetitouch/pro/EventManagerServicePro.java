/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$OnSharedPreferenceChangeListener
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.SystemClock
 *  java.io.File
 *  java.lang.Class
 *  java.lang.Integer
 *  java.lang.InterruptedException
 *  java.lang.NumberFormatException
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 *  java.lang.Thread
 *  java.lang.Throwable
 *  java.util.ArrayList
 *  java.util.Comparator
 *  java.util.Iterator
 *  java.util.NavigableSet
 *  java.util.TreeSet
 */
package com.cygery.repetitouch.pro;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import com.cygery.repetitouch.Event;
import com.cygery.repetitouch.MergeItem;
import com.cygery.repetitouch.UInputDeviceInfo;
import com.cygery.repetitouch.a;
import com.cygery.repetitouch.b;
import com.cygery.repetitouch.c;
import com.cygery.repetitouch.e;
import com.cygery.repetitouch.j;
import com.cygery.repetitouch.l;
import com.cygery.repetitouch.pro.MergeActivity;
import com.cygery.repetitouch.pro.PanelServicePro;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.TreeSet;

public class EventManagerServicePro
extends b
implements SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String N = EventManagerServicePro.class.getName();
    private final Runnable O;

    public EventManagerServicePro() {
        this.O = new Runnable(){

            /*
             * Unable to fully structure code
             * Enabled aggressive exception aggregation
             */
            public void run() {
                var1_1 = 10;
                try {
                    var1_1 = var4_2 = EventManagerServicePro.b(EventManagerServicePro.this).getInt("dimScreenPercentageInt", Integer.parseInt((String)EventManagerServicePro.a(EventManagerServicePro.this).getString("dimScreenPercentage", String.valueOf((int)10))));
                }
                catch (NumberFormatException var2_3) {
                    ** continue;
                }
lbl6: // 2 sources:
                do {
                    EventManagerServicePro.this.startService(new Intent((Context)EventManagerServicePro.this, EventManagerServicePro.c(EventManagerServicePro.this)).setAction("panelservice").putExtra("message", "setbrightness").putExtra("brightness", var1_1));
                    return;
                    break;
                } while (true);
            }
        };
    }

    public static ArrayList<MergeItem> L() {
        return p;
    }

    static /* synthetic */ SharedPreferences a(EventManagerServicePro eventManagerServicePro) {
        return eventManagerServicePro.M;
    }

    public static void a(Context context, String string) {
        c c2 = new c();
        Iterator iterator = p.iterator();
        while (iterator.hasNext()) {
            c2.a((MergeItem)iterator.next());
        }
        p.clear();
        p.add((Object)new MergeItem(context, c2.b(), c2.a(), null, null, string));
        b b2 = EventManagerServicePro.a();
        if (b2 != null) {
            b2.a(c2.b(), c2.a());
        }
    }

    static /* synthetic */ SharedPreferences b(EventManagerServicePro eventManagerServicePro) {
        return eventManagerServicePro.M;
    }

    static /* synthetic */ Class c(EventManagerServicePro eventManagerServicePro) {
        return eventManagerServicePro.H;
    }

    public Runnable D() {
        return this.O;
    }

    void E() {
        if (this.e) {
            if (this.B == null || !this.B.isAlive()) {
                this.l = true;
                if (this.q.b().isEmpty() && !this.w.isEmpty()) {
                    this.q.a(this.w, this.t);
                }
                this.b();
            }
            return;
        }
        com.cygery.utilities.a.c(N, "not initialised");
    }

    void F() {
        this.o.clear();
        this.a(1, 1.0, false, false);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void G() {
        if (this.f()) {
            Object object;
            Object object2 = object = this.D;
            synchronized (object2) {
                this.C.d();
            }
            this.I();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void H() {
        if (this.f()) {
            Object object;
            Object object2 = object = this.D;
            synchronized (object2) {
                this.C.e();
            }
            this.J();
        }
    }

    void I() {
        com.cygery.utilities.a.a(N, "replay paused");
        if (this.L) {
            this.startService(new Intent((Context)this, this.H).setAction("panelservice").putExtra("message", "replaypaused"));
        }
    }

    void J() {
        com.cygery.utilities.a.a(N, "replay resumed");
        if (this.L) {
            this.startService(new Intent((Context)this, this.H).setAction("panelservice").putExtra("message", "replayresumed"));
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    void K() {
        p.clear();
        if (this.o.size() == 0) {
            if (this.w.size() > 0) {
                p.add((Object)new MergeItem((Context)this, this.w, this.t, null, null));
            }
        } else {
            p.add((Object)new MergeItem((Context)this, this.w, this.t, null, (e)this.o.first()));
            e e2 = (e)this.o.first();
            Iterator iterator = this.o.subSet(this.o.first(), false, this.o.last(), true).iterator();
            e e3 = e2;
            while (iterator.hasNext()) {
                e e4 = (e)iterator.next();
                p.add((Object)new MergeItem((Context)this, this.w, this.t, e3, e4));
                e3 = e4;
            }
            p.add((Object)new MergeItem((Context)this, this.w, this.t, (e)this.o.last(), null));
        }
        this.startActivity(new Intent((Context)this, (Class)MergeActivity.class).addFlags(335544320));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void a(int n2, double d2, boolean bl, boolean bl2) {
        Object object;
        com.cygery.utilities.a.a(N, "start replay: " + n2 + " " + d2);
        if (d2 < 0.0) {
            d2 = 1.0;
        }
        if (d2 == 0.0) {
            com.cygery.utilities.a.a(N, "did you really want to replay at no speed?");
            return;
        }
        if (!this.e) {
            com.cygery.utilities.a.c(N, "not initialised");
            return;
        }
        if (this.M.getBoolean("dimScreenOnReplay", false)) {
            int n3;
            try {
                int n4;
                n3 = n4 = Integer.parseInt((String)this.M.getString("dimDelaySeconds", "0"));
            }
            catch (NumberFormatException var8_9) {
                n3 = 0;
            }
            com.cygery.utilities.a.a(N, "dimming in " + n3 + "s");
            this.G.postDelayed(this.O, (long)(n3 * 1000));
        }
        if (this.g()) {
            com.cygery.utilities.a.b(N, "not starting replay because replayThread is used");
            return;
        }
        if (!this.f()) {
            this.b(true);
        }
        Object object2 = object = this.D;
        synchronized (object2) {
            this.C.a(n2);
            this.C.a(d2);
            this.C.b(bl);
            this.C.a(bl2);
            this.C.d(this.M.getBoolean("startReplayPaused", false));
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void a(Intent intent) {
        String string = intent.getStringExtra("message");
        boolean bl = intent.getBooleanExtra("closeafteraction", false);
        final boolean bl2 = intent.getBooleanExtra("silent", false);
        boolean bl3 = intent.getBooleanExtra("block", false);
        if ("stoprecordbyuser".equals((Object)string)) {
            this.a(true);
            if (!bl) return;
            {
                this.stopService(new Intent((Context)this, this.H));
            }
            return;
        }
        if ("stoprecordbyservice".equals((Object)string)) {
            this.a(false);
            if (!bl) return;
            {
                this.stopService(new Intent((Context)this, this.H));
                return;
            }
        }
        if ("stopreplay".equals((Object)string)) {
            if (bl) {
                this.p();
            } else {
                this.d();
            }
            if (!bl) return;
            {
                this.stopService(new Intent((Context)this, this.H));
                return;
            }
        }
        if ("startrecord".equals((Object)string) && this.m()) {
            this.b();
            return;
        }
        if ("startrecordappending".equals((Object)string) && this.m()) {
            this.E();
            return;
        }
        if ("startreplay".equals((Object)string) && this.m()) {
            double d2 = intent.getDoubleExtra("replaySpeed", 0.0);
            if (d2 == 0.0) {
                d2 = this.j;
            }
            this.a(1, d2, bl, bl2);
            return;
        }
        if ("startreplayloop".equals((Object)string) && this.m()) {
            int n2 = intent.getIntExtra("loopCount", -1);
            double d3 = intent.getDoubleExtra("replaySpeed", 0.0);
            if (d3 == 0.0) {
                d3 = this.j;
            }
            this.a(n2, d3, bl, bl2);
            return;
        }
        if ("startreplayeditor".equals((Object)string) && this.m()) {
            this.F();
            return;
        }
        if ("pausereplay".equals((Object)string)) {
            this.G();
            return;
        }
        if ("resumereplay".equals((Object)string)) {
            this.H();
            return;
        }
        if ("addmarker".equals((Object)string)) {
            this.i(intent.getStringExtra("description"));
            return;
        }
        if ("launchmerger".equals((Object)string)) {
            if (intent.getBooleanExtra("clearMarkers", false)) {
                this.o.clear();
            }
            this.K();
            return;
        }
        if ("loadevents".equals((Object)string) && this.m()) {
            final String string2 = intent.getStringExtra("path") != null ? intent.getStringExtra("path") : (Object)this.getFilesDir() + File.separator + "record";
            Runnable runnable = new Runnable(){

                /*
                 * Enabled force condition propagation
                 * Lifted jumps to return sites
                 */
                public void run() {
                    boolean bl = EventManagerServicePro.this.b(string2);
                    if (bl2) return;
                    if (bl) {
                        EventManagerServicePro.this.startService(new Intent((Context)EventManagerServicePro.this, EventManagerServicePro.this.H).setAction("panelservice").putExtra("message", "loadsuccess").putExtra("path", string2));
                        return;
                    }
                    EventManagerServicePro.this.startService(new Intent((Context)EventManagerServicePro.this, EventManagerServicePro.this.H).setAction("panelservice").putExtra("message", "loadfail").putExtra("path", string2));
                }
            };
            if (bl3) {
                runnable.run();
                return;
            }
            new Thread(runnable).start();
            return;
        }
        if ("saveevents".equals((Object)string) && this.m()) {
            final String string3 = intent.getStringExtra("path") != null ? intent.getStringExtra("path") : (Object)this.getFilesDir() + File.separator + "record";
            Runnable runnable = new Runnable(){

                /*
                 * Enabled force condition propagation
                 * Lifted jumps to return sites
                 */
                public void run() {
                    boolean bl = EventManagerServicePro.this.a(string3);
                    if (bl2) return;
                    if (bl) {
                        EventManagerServicePro.this.startService(new Intent((Context)EventManagerServicePro.this, EventManagerServicePro.this.H).setAction("panelservice").putExtra("message", "savesuccess").putExtra("path", string3));
                        return;
                    }
                    EventManagerServicePro.this.startService(new Intent((Context)EventManagerServicePro.this, EventManagerServicePro.this.H).setAction("panelservice").putExtra("message", "savefail").putExtra("path", string3));
                }
            };
            if (bl3) {
                runnable.run();
                return;
            }
            new Thread(runnable).start();
            return;
        }
        if ("close".equals((Object)string)) {
            this.stopService(new Intent((Context)this, this.H));
            return;
        }
        if (this.l()) {
            com.cygery.utilities.a.c(N, "unknown message received: " + string);
            return;
        }
        com.cygery.utilities.a.c(N, "no action allowed. msg: " + string);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void a(boolean bl) {
        com.cygery.utilities.a.a(N, "stop record");
        if (!this.e) {
            com.cygery.utilities.a.c(N, "not initialised");
            return;
        }
        if (this.B != null && this.B.isAlive()) {
            this.B.a();
            try {
                this.B.join();
            }
            catch (InterruptedException var2_2) {
                com.cygery.utilities.a.c(N, (Throwable)var2_2);
            }
            this.t = SystemClock.elapsedRealtime() - this.r;
            com.cygery.utilities.a.a(N, "waiting for filter");
            this.q();
            com.cygery.utilities.a.a(N, "waiting done");
            if ("O".equals((Object)this.g)) {
                this.t -= 3000;
                if (this.t <= 0) {
                    this.t = 0;
                    this.w.clear();
                    com.cygery.utilities.a.b(N, "record duration <= 0 in legacy mode. clearing events.");
                }
            }
            com.cygery.utilities.a.a(N, "record duration in ms:" + this.t);
            if (bl) {
                this.n();
                com.cygery.utilities.a.a(N, "last click filtered");
            } else {
                com.cygery.utilities.a.a(N, "not filtering last click since record wasn't stopped by user click");
            }
            com.cygery.utilities.a.a(N, "events size: " + this.w.size());
            if (this.l) {
                com.cygery.utilities.a.a(N, "merging record");
                this.q.a(this.w, this.t);
                this.t = this.q.a();
                this.w = this.q.b();
                com.cygery.utilities.a.a(N, "merged duration in ms:" + this.t);
                com.cygery.utilities.a.a(N, "merged events:" + this.w.size());
                this.l = false;
                com.cygery.utilities.a.a(N, "merging done");
            }
            this.h();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    protected boolean a(Event event) {
        if (event == null) return false;
        event.a(this.u);
        if (this.A != null && event.a(this.A.d(), 0, 0, 0) && this.A.a(0, 0, 0)) {
            com.cygery.utilities.a.a(N, "filtered event: " + event);
            return false;
        }
        this.A = event;
        return true;
    }

    @Override
    protected void b() {
        com.cygery.utilities.a.a(N, "start record");
        if (this.e) {
            com.cygery.utilities.a.a(N, "init ready");
            if (!this.g.equals((Object)"O")) {
                this.r();
            }
            if (this.z) {
                this.y = EventManagerServicePro.b(this.f, this.h);
                com.cygery.utilities.a.a(N, "mUInputDeviceInfo: " + this.y);
                this.b(true);
            }
            com.cygery.utilities.a.a(N, "init done");
            if (this.B == null || !this.B.isAlive()) {
                com.cygery.utilities.a.a(N, "waiting for filter");
                this.q();
                com.cygery.utilities.a.a(N, "waiting done");
                this.w = new ArrayList(102400);
                if (!this.l) {
                    this.q.c();
                }
                com.cygery.utilities.a.a(N, "events cleared: " + this.w.size());
                this.B = new com.cygery.repetitouch.pro.b(this);
                this.E = new a(this);
                this.r = SystemClock.elapsedRealtime();
                com.cygery.utilities.a.a(N, "starting threads: " + this.r);
                this.E.start();
                this.B.start();
            }
            return;
        }
        com.cygery.utilities.a.c(N, "not initialised");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void i(String string) {
        if (this.f()) {
            Object object;
            Object object2 = object = this.D;
            synchronized (object2) {
                this.C.a(string);
                return;
            }
        }
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        this.H = PanelServicePro.class;
        this.F = new Runnable(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public void run() {
                Object object;
                Object object2 = object = EventManagerServicePro.this.D;
                synchronized (object2) {
                    EventManagerServicePro.this.p();
                    EventManagerServicePro.this.C = new com.cygery.repetitouch.pro.c(EventManagerServicePro.this);
                    EventManagerServicePro.this.C.start();
                    com.cygery.utilities.a.a(N, "replay thread created and started");
                    return;
                }
            }
        };
        super.onCreate();
        this.o = new TreeSet((Comparator)new Comparator<e>(){

            /*
             * Enabled aggressive block sorting
             * Lifted jumps to return sites
             */
            public int a(e e2, e e3) {
                long l2;
                if (e2 == null && e3 == null) {
                    return 0;
                }
                if (e2 == null) {
                    return -1;
                }
                if (e3 == null) {
                    return 1;
                }
                long l3 = e2.b();
                if (l3 == (l2 = e3.b())) return 0;
                if (l3 >= l2) return 1;
                return -1;
            }

            public /* synthetic */ int compare(Object object, Object object2) {
                return this.a((e)object, (e)object2);
            }
        });
        this.o();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String string) {
        super.onSharedPreferenceChanged(sharedPreferences, string);
        if (string.startsWith("additionalEventEnabled_")) {
            int n2 = Integer.parseInt((String)string.substring("additionalEventEnabled_".length()));
            int n3 = Integer.parseInt((String)sharedPreferences.getString("additionalEventIndexString_" + n2, "-1"));
            if (n3 != -1) {
                if (sharedPreferences.getBoolean(string, false)) {
                    if (!this.i.contains((Object)n3)) {
                        this.i.add((Object)n3);
                    }
                } else {
                    this.i.remove((Object)n3);
                }
                this.k = "" + this.h;
                Iterator iterator = this.i.iterator();
                while (iterator.hasNext()) {
                    int n4 = (Integer)iterator.next();
                    this.k = this.k + " " + n4;
                }
            }
            com.cygery.utilities.a.a(N, "event indices update: " + this.k);
            this.b(false);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public int onStartCommand(Intent intent, int n2, int n3) {
        if (intent == null) {
            com.cygery.utilities.a.c(N, "not normally started, calling stopSelf. intent=null flags=" + n2 + " startId=" + n3);
            this.stopSelf();
            return 2;
        }
        if ("eventmanagerservice".equals((Object)intent.getAction())) {
            this.a(intent);
            this.b = false;
            return 1;
        }
        int n4 = super.onStartCommand(intent, n2, n3);
        if (!this.b) return n4;
        Runnable runnable = new Runnable(){

            public void run() {
                EventManagerServicePro.this.j();
            }
        };
        if (intent.getBooleanExtra("block", false)) {
            runnable.run();
        } else {
            new Thread(runnable).start();
        }
        this.b = false;
        return n4;
    }

}

