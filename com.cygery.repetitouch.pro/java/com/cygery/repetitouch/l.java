/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  java.io.DataInputStream
 *  java.io.DataOutputStream
 *  java.lang.Class
 *  java.lang.InterruptedException
 *  java.lang.Object
 *  java.lang.Process
 *  java.lang.String
 *  java.lang.Thread
 */
package com.cygery.repetitouch;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import com.cygery.repetitouch.b;
import com.cygery.repetitouch.q;
import com.cygery.repetitouch.s;
import com.cygery.utilities.a;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public abstract class l
extends Thread {
    private static final String n = l.class.getName();
    protected final Object a = new Object();
    protected Process b;
    protected DataOutputStream c;
    protected DataInputStream d;
    protected s e;
    protected q f;
    protected volatile boolean g;
    protected volatile boolean h;
    protected boolean i;
    protected boolean j;
    protected boolean k;
    protected boolean l;
    protected boolean m;
    private b o;

    protected l(b b2) {
        this.o = b2;
        this.g = true;
        this.h = false;
        this.m = true;
        this.i = false;
        this.j = false;
        this.k = false;
        this.l = true;
    }

    public void a(double d2) {
        this.e.a(d2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void a(int n2) {
        this.i = n2 < 0 || n2 > 1;
        this.e.a(n2);
    }

    public void a(String string) {
        void var3_2 = this;
        synchronized (var3_2) {
            if (this.e != null && this.e.isAlive()) {
                this.e.a(string);
            }
            return;
        }
    }

    public void a(boolean bl) {
        this.j = bl;
    }

    public boolean a() {
        return this.m;
    }

    protected void b() {
        if (!this.m) {
            this.o.i();
        }
    }

    public void b(boolean bl) {
        this.k = bl;
    }

    protected abstract void c();

    public void c(boolean bl) {
        void var3_2 = this;
        synchronized (var3_2) {
            this.l = bl;
            this.g = false;
            if (this.e != null && this.e.isAlive()) {
                this.e.a();
            }
            return;
        }
    }

    public void d() {
        l l2 = this;
        synchronized (l2) {
            if (this.e != null && this.e.isAlive()) {
                this.e.b();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void d(boolean bl) {
        Object object;
        boolean bl2;
        a.a(n, "startReplay() paused=" + bl + " before lock");
        Object object2 = object = this.a;
        // MONITORENTER : object2
        a.a(n, "startReplay() in lock");
        while (this.g && !(bl2 = this.h)) {
            try {
                a.a(n, "startReplay() before lock.wait()");
                this.a.wait();
                a.a(n, "startReplay() after lock.wait()");
            }
            catch (InterruptedException var11_4) {
                a.c(n, "startReplay lock InterruptedException");
            }
        }
        // MONITOREXIT : object2
        if (!this.g) {
            a.b(n, "startReplay() aborting because running=false");
            return;
        }
        a.a(n, "startReplay() isReadyForStartReplay=" + this.h);
        if (this.m) {
            this.m = false;
            Intent intent = new Intent((Context)this.o, this.o.H);
            intent.setAction("panelservice");
            if (bl) {
                intent.putExtra("message", "replaystartedpaused");
            } else {
                intent.putExtra("message", "replaystarted");
            }
            if (this.i) {
                intent.putExtra("isLooping", true);
            }
            this.o.startService(intent);
        }
        if (bl) return;
        this.e();
    }

    public void e() {
        l l2 = this;
        synchronized (l2) {
            a.a(n, "resumeReplay()");
            if (this.e != null && this.e.isAlive()) {
                this.e.c();
            }
            return;
        }
    }

    /*
     * Exception decompiling
     */
    public void run() {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.CannotPerformDecode: reachable test BLOCK was exited and re-entered.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.Misc.getFarthestReachableInRange(Misc.java:143)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:385)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:65)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:422)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:220)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:165)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:91)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:354)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:751)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:683)
        // org.benf.cfr.reader.Main.doJar(Main.java:128)
        // com.njlabs.showjava.processor.JavaExtractor$1.run(JavaExtractor.java:100)
        // java.lang.Thread.run(Thread.java:818)
        throw new IllegalStateException("Decompilation failed");
    }
}

