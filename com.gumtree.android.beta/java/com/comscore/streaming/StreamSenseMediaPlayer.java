package com.comscore.streaming;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.net.Uri;
import android.os.Build.VERSION;
import com.gumtree.android.UrlMatcher;
import com.gumtree.android.postad.DraftAd;
import java.io.FileDescriptor;
import java.util.HashMap;
import java.util.Timer;

@Deprecated
public class StreamSenseMediaPlayer extends MediaPlayer {
    Timer a;
    Timer b;
    private final String c = "local_file";
    private final int d = UrlMatcher.URI_CODE_SAVED_SEARCHES;
    private final boolean e;
    private final boolean f;
    private final boolean g;
    private final boolean h;
    private final int i;
    private StreamSense j;
    private String k;
    private String l;
    private boolean m;
    private boolean n;
    private String o;
    private OnCompletionListener p;
    private final OnCompletionListener q;
    private OnInfoListener r;
    private final OnInfoListener s;
    private OnSeekCompleteListener t;
    private final OnSeekCompleteListener u;
    private OnPreparedListener v;
    private final OnPreparedListener w;
    private Timer x;
    private Timer y;

    public StreamSenseMediaPlayer() {
        this.e = VERSION.SDK_INT < 9;
        this.f = false;
        this.g = false;
        this.h = true;
        this.i = UrlMatcher.URI_CODE_SAVED_SEARCHES;
        this.j = null;
        this.k = DraftAd.NEW_AD_ID;
        this.l = "0x0";
        this.m = false;
        this.n = false;
        this.p = null;
        this.q = new g(this);
        this.r = null;
        this.s = new h(this);
        this.t = null;
        this.u = new i(this);
        this.v = null;
        this.w = new j(this);
        this.x = null;
        this.a = null;
        this.b = null;
        this.y = null;
        c();
    }

    public StreamSenseMediaPlayer(boolean z) {
        this.e = VERSION.SDK_INT < 9;
        this.f = false;
        this.g = false;
        this.h = true;
        this.i = UrlMatcher.URI_CODE_SAVED_SEARCHES;
        this.j = null;
        this.k = DraftAd.NEW_AD_ID;
        this.l = "0x0";
        this.m = false;
        this.n = false;
        this.p = null;
        this.q = new g(this);
        this.r = null;
        this.s = new h(this);
        this.t = null;
        this.u = new i(this);
        this.v = null;
        this.w = new j(this);
        this.x = null;
        this.a = null;
        this.b = null;
        this.y = null;
        c();
    }

    private void a() {
        this.m = true;
        if (n() && !isPlayerPausedForSeeking()) {
            k();
        }
    }

    private void a(MediaPlayer mediaPlayer) {
        l();
    }

    private synchronized void a(HashMap<String, String> hashMap) {
        long m = m();
        f();
        boolean g = g();
        if (this.x == null && !g) {
            this.x = new Timer();
            this.x.schedule(new k(this, m, hashMap), 500);
        }
    }

    private void a(HashMap<String, String> hashMap, long j) {
        boolean d = d();
        boolean f = f();
        g();
        if (!d && !f && this.j != null) {
            this.j.notify(StreamSenseEventType.PAUSE, hashMap, j);
        }
    }

    private synchronized void a(boolean z) {
        Object obj = this.a != null ? 1 : null;
        d();
        boolean g = g();
        if (obj == null && !g) {
            f(o());
        }
    }

    private void b() {
        this.m = false;
        if (n() && !isPlayerPausedForSeeking()) {
            a(true);
        }
    }

    private void b(MediaPlayer mediaPlayer) {
        if (n()) {
            a(true);
        }
    }

    private synchronized void b(HashMap<String, String> hashMap) {
        boolean d = d();
        boolean f = f();
        if (!((this.b != null ? 1 : null) != null || d || f)) {
            d((HashMap) hashMap);
            d((HashMap) hashMap);
        }
    }

    private void c() {
        super.setOnCompletionListener(this.q);
        super.setOnInfoListener(this.s);
        super.setOnSeekCompleteListener(this.u);
        super.setOnPreparedListener(this.w);
    }

    private void c(HashMap<String, String> hashMap) {
        d();
        f();
        g();
        if (this.j != null) {
            this.j.notify(StreamSenseEventType.BUFFER, hashMap, m());
        }
    }

    private void d(HashMap<String, String> hashMap) {
        a((HashMap) hashMap, m());
    }

    private synchronized boolean d() {
        boolean z;
        if (this.x != null) {
            this.x.cancel();
            this.x = null;
            z = true;
        } else {
            z = false;
        }
        return z;
    }

    private void e() {
        a(o());
    }

    private void e(HashMap<String, String> hashMap) {
        f();
        d();
        g();
        i();
        if (this.j != null) {
            this.j.notify(StreamSenseEventType.END, hashMap, m());
        }
    }

    private void f(HashMap<String, String> hashMap) {
        d();
        if (!g() && this.j != null) {
            this.j.notify(StreamSenseEventType.PLAY, hashMap, m());
        }
    }

    private synchronized boolean f() {
        boolean z;
        if (this.a != null) {
            this.a.cancel();
            this.a = null;
            z = true;
        } else {
            z = false;
        }
        return z;
    }

    private synchronized boolean g() {
        boolean z;
        if (this.b != null) {
            this.b.cancel();
            this.b = null;
            z = true;
        } else {
            z = false;
        }
        return z;
    }

    private void h() {
        b(o());
    }

    private synchronized boolean i() {
        boolean z;
        if (this.y != null) {
            this.y.cancel();
            this.y = null;
            z = true;
        } else {
            z = false;
        }
        return z;
    }

    private synchronized void j() {
        if (this.e && this.y == null) {
            long m = m();
            this.y = new Timer();
            this.y.schedule(new l(this, m), 250);
        }
    }

    private void k() {
        c(o());
    }

    private void l() {
        e(o());
    }

    private long m() {
        long j = 0;
        try {
            return (long) getCurrentPosition();
        } catch (IllegalStateException e) {
            return j;
        }
    }

    private boolean n() {
        try {
            return isPlaying();
        } catch (IllegalStateException e) {
            return false;
        }
    }

    private HashMap<String, String> o() {
        HashMap<String, String> hashMap = new HashMap();
        hashMap.put("ns_ts", String.valueOf(System.currentTimeMillis()));
        hashMap.putAll(p());
        return hashMap;
    }

    private HashMap<String, String> p() {
        HashMap<String, String> hashMap = new HashMap();
        hashMap.put("ns_st_cl", this.k);
        hashMap.put("ns_st_cs", this.l);
        hashMap.put("ns_st_cu", this.o);
        hashMap.put("ns_st_mp", StreamSenseMediaPlayer.class.getSimpleName());
        hashMap.put("ns_st_mv", Integer.toString(VERSION.SDK_INT));
        return hashMap;
    }

    public boolean isPlayerPausedForBuffering() {
        return this.m;
    }

    public boolean isPlayerPausedForSeeking() {
        return this.n;
    }

    public void pause() {
        super.pause();
        i();
        this.m = false;
        this.n = false;
        h();
    }

    public void prepare() {
        super.prepare();
        this.l = getVideoWidth() + "x" + getVideoHeight();
    }

    public void prepareAsync() {
        super.prepareAsync();
        this.l = getVideoWidth() + "x" + getVideoHeight();
    }

    public void release() {
        super.release();
        l();
    }

    public void seekTo(int i) {
        super.seekTo(i);
        this.n = true;
        if (n()) {
            a(o(), m());
        }
    }

    public void setDataSource(Context context, Uri uri) {
        super.setDataSource(context, uri);
        this.o = uri.toString();
    }

    public void setDataSource(FileDescriptor fileDescriptor) {
        super.setDataSource(fileDescriptor);
        this.o = "local_file";
    }

    public void setDataSource(FileDescriptor fileDescriptor, long j, long j2) {
        super.setDataSource(fileDescriptor, j, j2);
        this.o = "local_file";
    }

    public void setDataSource(String str) {
        super.setDataSource(str);
        this.o = str;
    }

    public void setOnCompletionListener(OnCompletionListener onCompletionListener) {
        super.setOnCompletionListener(this.q);
        this.p = onCompletionListener;
    }

    public void setOnInfoListener(OnInfoListener onInfoListener) {
        super.setOnInfoListener(this.s);
        this.r = onInfoListener;
    }

    public void setOnPreparedListener(OnPreparedListener onPreparedListener) {
        super.setOnPreparedListener(this.w);
        this.v = onPreparedListener;
    }

    public void setOnSeekCompleteListener(OnSeekCompleteListener onSeekCompleteListener) {
        super.setOnSeekCompleteListener(this.u);
        this.t = onSeekCompleteListener;
    }

    public void setStreamSense(StreamSense streamSense) {
        this.j = streamSense;
        this.j.setLabel("ns_st_pv", "4.1307.02");
    }

    public void start() {
        super.start();
        e();
    }

    public void stop() {
        super.stop();
        l();
    }
}
