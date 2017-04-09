/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  java.io.DataOutputStream
 *  java.lang.Process
 *  java.lang.String
 *  java.lang.Thread
 */
package com.cygery.repetitouch;

import com.cygery.repetitouch.b;
import com.cygery.repetitouch.i;
import com.cygery.repetitouch.q;
import java.io.DataOutputStream;

public abstract class j
extends Thread {
    private static final String f = j.class.getName();
    protected Process a;
    protected DataOutputStream b;
    protected i c;
    protected q d;
    protected volatile boolean e;
    private b g;

    public j(b b2) {
        this.g = b2;
        this.e = true;
    }

    public void a() {
        j j2 = this;
        synchronized (j2) {
            this.e = false;
            DataOutputStream dataOutputStream = this.b;
            if (dataOutputStream != null) {
                this.b.writeBytes("\n");
                this.b.flush();
            }
            return;
        }
    }

    protected abstract void b();

    /*
     * Exception decompiling
     */
    public void run() {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [5[TRYBLOCK]], but top level block is 16[SWITCH]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:394)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:446)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:2859)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:805)
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

