package com.comscore.streaming;

import android.content.Context;
import com.comscore.analytics.Core;
import com.comscore.analytics.comScore;
import com.comscore.utils.CSLog;
import com.comscore.utils.Utils;
import com.gumtree.android.UrlMatcher;
import com.gumtree.android.postad.DraftAd;
import com.gumtree.android.postad.services.converter.PaymentConverter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class StreamSense {
    protected Core a;
    private HashMap<String, String> b;
    private String c = null;
    private long d;
    private long e;
    private StreamSenseState f = null;
    private int g;
    private StreamSensePlaylist h = null;
    private Runnable i = null;
    private boolean j = true;
    private Runnable k;
    private f l = null;
    private Runnable m;
    private long n;
    private int o;
    private long p;
    private boolean q;
    private StreamSenseState r;
    private String s;
    private String t;
    private HashMap<String, String> u;
    private List<StreamSenseListener> v;
    private List<HashMap<String, Long>> w;
    private int x;
    private int y;

    public StreamSense() {
        CSLog.d(this, "StreamSense()");
        this.a = comScore.getCore();
        this.b = new HashMap();
        this.g = 1;
        this.f = StreamSenseState.IDLE;
        this.h = new StreamSensePlaylist();
        this.i = null;
        this.j = true;
        this.m = null;
        this.o = 0;
        f();
        this.k = null;
        this.l = null;
        this.q = false;
        this.r = null;
        this.e = 0;
        this.x = 1200000;
        this.y = UrlMatcher.URI_CODE_SAVED_SEARCHES;
        this.v = new ArrayList();
        this.w = n();
        reset();
    }

    private long a(long j) {
        for (HashMap hashMap : this.w) {
            Long l = (Long) hashMap.get("playingtime");
            if (l != null) {
                if (j < l.longValue()) {
                }
            }
            return ((Long) hashMap.get("interval")).longValue();
        }
        return 0;
    }

    private StreamSenseState a(StreamSenseEventType streamSenseEventType) {
        return streamSenseEventType == StreamSenseEventType.PLAY ? StreamSenseState.PLAYING : streamSenseEventType == StreamSenseEventType.PAUSE ? StreamSenseState.PAUSED : streamSenseEventType == StreamSenseEventType.BUFFER ? StreamSenseState.BUFFERING : streamSenseEventType == StreamSenseEventType.END ? StreamSenseState.IDLE : null;
    }

    private HashMap<String, String> a(StreamSenseEventType streamSenseEventType, HashMap<String, String> hashMap) {
        HashMap hashMap2;
        if (!this.a.isEnabled()) {
            hashMap2 = new HashMap();
        }
        CSLog.d(this, "createMeasurementLabels(" + streamSenseEventType + ")");
        hashMap2 = new HashMap();
        if (hashMap != null) {
            hashMap2.putAll(hashMap);
        }
        if (!hashMap2.containsKey("ns_ts")) {
            hashMap2.put("ns_ts", String.valueOf(System.currentTimeMillis()));
        }
        if (!(streamSenseEventType == null || hashMap2.containsKey("ns_st_ev"))) {
            hashMap2.put("ns_st_ev", streamSenseEventType.toString());
        }
        hashMap2.putAll(getLabels());
        b(streamSenseEventType, hashMap2);
        this.h.a(streamSenseEventType, hashMap2);
        this.h.getClip().a(streamSenseEventType, hashMap2);
        if (!hashMap2.containsKey("ns_st_mp")) {
            hashMap2.put("ns_st_mp", this.s);
        }
        if (!hashMap2.containsKey("ns_st_mv")) {
            hashMap2.put("ns_st_mv", this.t);
        }
        if (!hashMap2.containsKey("ns_st_ub")) {
            hashMap2.put("ns_st_ub", DraftAd.NEW_AD_ID);
        }
        if (!hashMap2.containsKey("ns_st_br")) {
            hashMap2.put("ns_st_br", DraftAd.NEW_AD_ID);
        }
        if (!hashMap2.containsKey("ns_st_pn")) {
            hashMap2.put("ns_st_pn", PaymentConverter.DEFAULT_PAYMENT_METHOD_ID);
        }
        if (!hashMap2.containsKey("ns_st_tp")) {
            hashMap2.put("ns_st_tp", PaymentConverter.DEFAULT_PAYMENT_METHOD_ID);
        }
        if (!hashMap2.containsKey("ns_st_it")) {
            hashMap2.put("ns_st_it", "c");
        }
        hashMap2.put("ns_st_sv", "4.1508.28");
        return hashMap2;
    }

    private void a(StreamSenseState streamSenseState, HashMap<String, String> hashMap) {
        long j = 0;
        if (this.a.isEnabled()) {
            CSLog.d(this, "transitionTo(" + streamSenseState + ", " + hashMap + ")");
            l();
            if (c(streamSenseState)) {
                StreamSenseState state = getState();
                long j2 = this.d;
                long f = f(hashMap);
                if (j2 >= 0) {
                    j = f - j2;
                }
                b(getState(), (HashMap) hashMap);
                c(streamSenseState, hashMap);
                d(streamSenseState);
                for (StreamSenseListener onStateChange : this.v) {
                    onStateChange.onStateChange(state, streamSenseState, hashMap, j);
                }
                c((HashMap) hashMap);
                this.h.b(hashMap, streamSenseState);
                this.h.getClip().b(hashMap, streamSenseState);
                HashMap a = a(streamSenseState.toEventType(), (HashMap) hashMap);
                a.putAll(hashMap);
                if (b(this.f)) {
                    a(a);
                    this.r = this.f;
                    this.g++;
                }
            }
        }
    }

    private void a(StreamSenseState streamSenseState, HashMap<String, String> hashMap, long j) {
        if (this.a.isEnabled()) {
            CSLog.d(this, "transitionTo(" + streamSenseState + ", " + hashMap + ", " + j + ")");
            l();
            this.l = new d(this, streamSenseState, hashMap);
            this.a.getTaskExecutor().execute(this.l, j);
        }
    }

    private boolean a(StreamSenseState streamSenseState) {
        return !this.a.isEnabled() ? false : streamSenseState == StreamSenseState.PLAYING || streamSenseState == StreamSenseState.PAUSED;
    }

    @Deprecated
    public static StreamSense analyticsFor(StreamSenseMediaPlayer streamSenseMediaPlayer) {
        StreamSense streamSense = new StreamSense();
        streamSense.engageTo(streamSenseMediaPlayer);
        streamSense.setPausePlaySwitchDelayEnabled(true);
        return streamSense;
    }

    @Deprecated
    public static StreamSense analyticsFor(StreamSenseVideoView streamSenseVideoView) {
        StreamSense streamSense = new StreamSense();
        streamSense.engageTo(streamSenseVideoView);
        return streamSense;
    }

    private HashMap<String, String> b(StreamSenseEventType streamSenseEventType, HashMap<String, String> hashMap) {
        if (!this.a.isEnabled()) {
            return new HashMap();
        }
        if (hashMap == null) {
            hashMap = new HashMap();
        }
        hashMap.put("ns_st_ec", String.valueOf(this.g));
        if (!hashMap.containsKey("ns_st_po")) {
            long j = this.e;
            long f = f(hashMap);
            if (streamSenseEventType == StreamSenseEventType.PLAY || streamSenseEventType == StreamSenseEventType.KEEP_ALIVE || streamSenseEventType == StreamSenseEventType.HEART_BEAT || (streamSenseEventType == null && this.f == StreamSenseState.PLAYING)) {
                j += f - this.h.getClip().getPlaybackTimestamp();
            }
            hashMap.put("ns_st_po", String.valueOf(j));
        }
        if (streamSenseEventType != StreamSenseEventType.HEART_BEAT) {
            return hashMap;
        }
        hashMap.put("ns_st_hc", String.valueOf(this.o));
        hashMap.put("ns_st_pe", PaymentConverter.DEFAULT_PAYMENT_METHOD_ID);
        return hashMap;
    }

    private void b(StreamSenseState streamSenseState, HashMap<String, String> hashMap) {
        if (this.a.isEnabled()) {
            CSLog.d(this, "onExit(" + streamSenseState + ", " + hashMap + ")");
            long f = f(hashMap);
            if (streamSenseState == StreamSenseState.PLAYING) {
                this.h.a(f);
                e();
                h();
            } else if (streamSenseState == StreamSenseState.BUFFERING) {
                this.h.b(f);
                k();
            } else if (streamSenseState == StreamSenseState.IDLE) {
                getClip().reset(getClip().getLabels().keySet());
            }
        }
    }

    private void b(HashMap<String, String> hashMap) {
        if (this.a.isEnabled() && f(hashMap) < 0) {
            hashMap.put("ns_ts", String.valueOf(System.currentTimeMillis()));
        }
    }

    private boolean b(StreamSenseState streamSenseState) {
        return ((streamSenseState == StreamSenseState.PAUSED && (this.r == StreamSenseState.IDLE || this.r == null)) || streamSenseState == StreamSenseState.BUFFERING || this.r == streamSenseState) ? false : true;
    }

    private void c() {
        if (this.a.isEnabled()) {
            k();
            if (isPauseOnBufferingEnabled() && b(StreamSenseState.PAUSED)) {
                this.i = new a(this);
                this.a.getTaskExecutor().execute(this.i, (long) this.y);
            }
        }
    }

    private void c(StreamSenseState streamSenseState, HashMap<String, String> hashMap) {
        if (this.a.isEnabled()) {
            CSLog.d(this, "onEnter(" + streamSenseState + ", " + hashMap + ")");
            long f = f(hashMap);
            this.e = e(hashMap);
            if (streamSenseState == StreamSenseState.PLAYING) {
                d();
                g();
                this.h.getClip().c(f);
                if (b(streamSenseState)) {
                    this.h.getClip().d();
                    if (this.h.a() < 1) {
                        this.h.a(1);
                    }
                }
            } else if (streamSenseState == StreamSenseState.PAUSED) {
                if (b(streamSenseState)) {
                    this.h.f();
                }
            } else if (streamSenseState == StreamSenseState.BUFFERING) {
                this.h.getClip().d(f);
                if (this.j) {
                    c();
                }
            } else if (streamSenseState == StreamSenseState.IDLE) {
                f();
            }
        }
    }

    private void c(HashMap<String, String> hashMap) {
        if (this.a.isEnabled()) {
            String str = (String) hashMap.get("ns_st_mp");
            if (str != null) {
                this.s = str;
                hashMap.remove("ns_st_mp");
            }
            str = (String) hashMap.get("ns_st_mv");
            if (str != null) {
                this.t = str;
                hashMap.remove("ns_st_mv");
            }
            str = (String) hashMap.get("ns_st_ec");
            if (str != null) {
                try {
                    this.g = Integer.parseInt(str);
                    hashMap.remove("ns_st_ec");
                } catch (NumberFormatException e) {
                }
            }
        }
    }

    private boolean c(StreamSenseState streamSenseState) {
        return (streamSenseState == null || getState() == streamSenseState) ? false : true;
    }

    private void d() {
        if (this.a.isEnabled()) {
            i();
            if (this.w != null) {
                long j;
                if (this.n >= 0) {
                    j = this.n;
                    CSLog.d(this, "Resuming heart beat timer. Next event in " + j + " ms");
                } else {
                    j = a(this.h.getClip().f());
                    CSLog.d(this, "Starting heart beat timer. Next event in " + j + " ms");
                }
                if (j > 0) {
                    this.p = System.currentTimeMillis() + j;
                    this.m = new b(this);
                    this.a.getTaskExecutor().execute(this.m, j);
                }
            }
        }
    }

    private void d(StreamSenseState streamSenseState) {
        if (this.a.isEnabled()) {
            this.f = streamSenseState;
            this.d = System.currentTimeMillis();
        }
    }

    private void d(HashMap<String, String> hashMap) {
        if (this.a.isEnabled()) {
            this.u = g(null);
            this.u.putAll(hashMap);
        }
    }

    private long e(HashMap<String, String> hashMap) {
        if (!hashMap.containsKey("ns_st_po")) {
            return -1;
        }
        try {
            return Long.valueOf((String) hashMap.get("ns_st_po")).longValue();
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void e() {
        if (this.a.isEnabled()) {
            CSLog.d(this, "Pausing heartbeat timer.");
            i();
            this.n = this.p - System.currentTimeMillis();
            this.p = -1;
        }
    }

    private long f(HashMap<String, String> hashMap) {
        if (!hashMap.containsKey("ns_ts")) {
            return -1;
        }
        try {
            return Long.valueOf((String) hashMap.get("ns_ts")).longValue();
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void f() {
        if (this.a.isEnabled()) {
            CSLog.d(this, "Resetting heartbeat timer.");
            this.n = -1;
            this.p = -1;
            this.o = 0;
        }
    }

    private HashMap<String, String> g(HashMap<String, String> hashMap) {
        return a(this.f.toEventType(), (HashMap) hashMap);
    }

    private void g() {
        if (this.a.isEnabled()) {
            CSLog.d(this, "Starting keep alive timer");
            h();
            this.k = new c(this);
            this.a.getTaskExecutor().execute(this.k, (long) this.x, true, (long) this.x);
        }
    }

    public static String getVersion() {
        return "4.1508.28";
    }

    private void h() {
        if (this.a.isEnabled()) {
            CSLog.d(this, "stopKeepAliveTask()");
            if (this.k != null) {
                this.a.getTaskExecutor().removeEnqueuedTask(this.k);
                this.k = null;
            }
        }
    }

    private void i() {
        if (this.a.isEnabled()) {
            CSLog.d(this, "releaseHeartBeatTask()");
            if (this.m != null) {
                this.a.getTaskExecutor().removeEnqueuedTask(this.m);
                this.m = null;
            }
        }
    }

    private void j() {
        if (this.a.isEnabled()) {
            CSLog.d(this, "Firing paused on buffering event");
            if (this.r == StreamSenseState.PLAYING) {
                this.h.h();
                this.h.f();
                a(a(StreamSenseEventType.PAUSE, null));
                this.g++;
                this.r = StreamSenseState.PAUSED;
            }
        }
    }

    private void k() {
        if (this.a.isEnabled()) {
            CSLog.d(this, "stopPausedOnBufferingTask()");
            if (this.i != null) {
                this.a.getTaskExecutor().removeEnqueuedTask(this.i);
                this.i = null;
            }
        }
    }

    private void l() {
        if (this.a.isEnabled()) {
            CSLog.d(this, "stopDelayedTransitionTask()");
            if (this.l != null) {
                this.a.getTaskExecutor().removeEnqueuedTask(this.l);
                this.l = null;
            }
        }
    }

    private boolean m() {
        Context appContext = this.a.getAppContext();
        String publisherSecret = this.a.getPublisherSecret();
        String pixelURL = this.a.getPixelURL();
        return appContext == null || publisherSecret == null || publisherSecret.length() == 0 || pixelURL == null || pixelURL.length() == 0;
    }

    private List<HashMap<String, Long>> n() {
        List<HashMap<String, Long>> arrayList = new ArrayList();
        HashMap hashMap = new HashMap();
        hashMap.put("playingtime", Long.valueOf(60000));
        hashMap.put("interval", Long.valueOf(10000));
        arrayList.add(hashMap);
        hashMap = new HashMap();
        hashMap.put("playingtime", null);
        hashMap.put("interval", Long.valueOf(60000));
        arrayList.add(hashMap);
        return arrayList;
    }

    protected void a() {
        if (this.a.isEnabled()) {
            CSLog.d(this, "Firing heart beat");
            this.o++;
            a(a(StreamSenseEventType.HEART_BEAT, null));
            this.n = -1;
            d();
        }
    }

    protected void a(HashMap<String, String> hashMap) {
        a((HashMap) hashMap, true);
    }

    protected void a(HashMap<String, String> hashMap, boolean z) {
        if (this.a.isEnabled()) {
            CSLog.d(this, "dispatch(" + hashMap + ", " + z + ")");
            if (z) {
                d((HashMap) hashMap);
            }
            if (!m()) {
                this.a.getTaskExecutor().execute(new e(this, hashMap, this.c), true);
            }
        }
    }

    public void addListener(StreamSenseListener streamSenseListener) {
        if (this.a.isEnabled()) {
            this.v.add(streamSenseListener);
        }
    }

    protected void b() {
        if (this.a.isEnabled()) {
            CSLog.d(this, "Firing keep alive");
            a(a(StreamSenseEventType.KEEP_ALIVE, null));
            this.g++;
        }
    }

    @Deprecated
    public void engageTo(StreamSenseMediaPlayer streamSenseMediaPlayer) {
        streamSenseMediaPlayer.setStreamSense(this);
    }

    @Deprecated
    public void engageTo(StreamSenseVideoView streamSenseVideoView) {
        streamSenseVideoView.setStreamSense(this);
    }

    public HashMap<String, String> exportState() {
        return this.u;
    }

    public StreamSenseClip getClip() {
        return this.h.getClip();
    }

    public int getKeepAliveInterval() {
        return this.x;
    }

    public String getLabel(String str) {
        return (String) this.b.get(str);
    }

    public HashMap<String, String> getLabels() {
        return this.b;
    }

    public int getPauseOnBufferingInterval() {
        return this.y;
    }

    public String getPixelURL() {
        return this.c;
    }

    public StreamSensePlaylist getPlaylist() {
        return this.h;
    }

    public StreamSenseState getState() {
        return this.f;
    }

    public void importState(HashMap<String, String> hashMap) {
        if (this.a.isEnabled()) {
            reset();
            HashMap mapOfStrings = Utils.mapOfStrings(hashMap);
            this.h.b(mapOfStrings, null);
            this.h.getClip().b(mapOfStrings, null);
            c(mapOfStrings);
            this.g++;
        }
    }

    public boolean isPauseOnBufferingEnabled() {
        return this.j;
    }

    public boolean isPausePlaySwitchDelayEnabled() {
        return this.q;
    }

    public void notify(StreamSenseEventType streamSenseEventType, long j) {
        notify(streamSenseEventType, new HashMap(), j);
    }

    public void notify(StreamSenseEventType streamSenseEventType, HashMap<String, String> hashMap, long j) {
        if (this.a.isEnabled()) {
            CSLog.d(this, "notify(" + streamSenseEventType + ", " + hashMap + ")");
            StreamSenseState a = a(streamSenseEventType);
            HashMap mapOfStrings = Utils.mapOfStrings(hashMap);
            b(mapOfStrings);
            if (!mapOfStrings.containsKey("ns_st_po")) {
                mapOfStrings.put("ns_st_po", String.valueOf(j));
            }
            if (streamSenseEventType != StreamSenseEventType.PLAY && streamSenseEventType != StreamSenseEventType.PAUSE && streamSenseEventType != StreamSenseEventType.BUFFER && streamSenseEventType != StreamSenseEventType.END) {
                HashMap a2 = a(streamSenseEventType, mapOfStrings);
                a2.putAll(mapOfStrings);
                a(a2, false);
                this.g++;
            } else if (isPausePlaySwitchDelayEnabled() && a(this.f) && a(a) && (this.f != StreamSenseState.PLAYING || a != StreamSenseState.PAUSED || this.l != null)) {
                a(a, mapOfStrings, 500);
            } else {
                a(a, mapOfStrings);
            }
        }
    }

    public void removeListener(StreamSenseListener streamSenseListener) {
        if (this.a.isEnabled()) {
            this.v.remove(streamSenseListener);
        }
    }

    public void reset() {
        reset(null);
    }

    public void reset(Set<String> set) {
        if (this.a.isEnabled()) {
            CSLog.d(this, "Reset()");
            this.h.reset(set);
            this.h.d(0);
            this.h.setPlaylistId(System.currentTimeMillis() + "_1");
            this.h.getClip().reset(set);
            if (set == null || set.isEmpty()) {
                this.b.clear();
            } else {
                StreamSenseUtils.filterMap(this.b, set);
            }
            this.g = 1;
            this.o = 0;
            e();
            f();
            h();
            k();
            l();
            this.f = StreamSenseState.IDLE;
            this.d = -1;
            this.r = null;
            this.s = "android_puppet";
            this.t = "4.1508.28";
            this.u = null;
        }
    }

    public Boolean setClip(HashMap<String, String> hashMap) {
        return setClip(hashMap, false);
    }

    public Boolean setClip(HashMap<String, String> hashMap, boolean z) {
        if (!this.a.isEnabled()) {
            return Boolean.FALSE;
        }
        Boolean bool = Boolean.FALSE;
        if (this.f != StreamSenseState.IDLE) {
            return bool;
        }
        this.h.getClip().reset();
        this.h.getClip().a(Utils.mapOfStrings(hashMap), null);
        if (z) {
            this.h.b();
        }
        return Boolean.TRUE;
    }

    public void setHeartbeatIntervals(List<HashMap<String, Long>> list) {
        if (this.a.isEnabled()) {
            this.w = list;
        }
    }

    public void setKeepAliveInterval(int i) {
        if (this.a.isEnabled()) {
            this.x = i;
        }
    }

    public void setLabel(String str, String str2) {
        if (!this.a.isEnabled()) {
            return;
        }
        if (str2 == null) {
            this.b.remove(str);
        } else {
            this.b.put(str, str2);
        }
    }

    public void setLabels(HashMap<String, String> hashMap) {
        if (!this.a.isEnabled() || hashMap == null) {
            return;
        }
        if (this.b == null) {
            this.b = Utils.mapOfStrings(hashMap);
        } else {
            this.b.putAll(Utils.mapOfStrings(hashMap));
        }
    }

    public void setPauseOnBufferingEnabled(boolean z) {
        if (this.a.isEnabled()) {
            this.j = z;
        }
    }

    public void setPauseOnBufferingInterval(int i) {
        if (this.a.isEnabled()) {
            this.y = i;
        }
    }

    public void setPausePlaySwitchDelayEnabled(boolean z) {
        if (this.a.isEnabled()) {
            this.q = z;
        }
    }

    public String setPixelURL(String str) {
        if (!this.a.isEnabled()) {
            return this.c;
        }
        if (str == null || str.length() == 0) {
            return null;
        }
        int indexOf = str.indexOf(63);
        if (indexOf < 0) {
            str = str + '?';
        } else if (indexOf < str.length() - 1) {
            for (String split : str.substring(indexOf + 1).split("&")) {
                String[] split2 = split.split("=");
                if (split2.length == 2) {
                    setLabel(split2[0], split2[1]);
                } else if (split2.length == 1) {
                    setLabel("name", split2[0]);
                }
            }
            str = str.substring(0, indexOf + 1);
        }
        this.c = str;
        return this.c;
    }

    public Boolean setPlaylist(HashMap<String, String> hashMap) {
        if (!this.a.isEnabled()) {
            return Boolean.FALSE;
        }
        Boolean bool = Boolean.FALSE;
        if (this.f != StreamSenseState.IDLE) {
            return bool;
        }
        this.h.i();
        this.h.reset();
        this.h.getClip().reset();
        this.h.a(Utils.mapOfStrings(hashMap), null);
        return Boolean.TRUE;
    }
}
