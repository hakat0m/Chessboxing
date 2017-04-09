/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  java.io.Serializable
 *  java.lang.Class
 *  java.lang.Enum
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.util.ArrayList
 */
package com.cygery.repetitouch;

import android.content.Context;
import android.os.Bundle;
import com.cygery.repetitouch.Event;
import com.cygery.repetitouch.b;
import com.cygery.repetitouch.e;
import com.cygery.repetitouch.h;
import java.io.Serializable;
import java.util.ArrayList;

public class MergeItem {
    private static final String a = MergeItem.class.getName();
    private Type b;
    private String c;
    private long d;
    private int e;
    private double f;
    private boolean g;
    private boolean h;
    private String i;
    private ArrayList<Event> j;
    private e k;
    private e l;

    public MergeItem(Context context, long l2) {
        this.c = "";
        this.d = -1;
        this.e = 1;
        this.f = 1.0;
        this.g = false;
        this.h = false;
        this.a(Type.TYPE_PAUSE);
        this.a(l2);
    }

    public MergeItem(Context context, String string) {
        this.c = "";
        this.d = -1;
        this.e = 1;
        this.f = 1.0;
        this.g = false;
        this.h = false;
        this.a(Type.TYPE_FILE);
        this.b(string);
        this.a(b.c(string));
    }

    /*
     * Enabled aggressive block sorting
     */
    public MergeItem(Context context, ArrayList<Event> arrayList, long l2, e e2, e e3) {
        this(context, arrayList, l2, e2, e3, null);
        String string = e2 != null && e2.c() != null ? "" + e2.c() : "" + this.c(context.getString(h.e.text_start));
        String string2 = string + " .. ";
        String string3 = e3 != null && e3.c() != null ? string2 + e3.c() : string2 + this.d(context.getString(h.e.text_end));
        this.a(string3);
    }

    /*
     * Enabled aggressive block sorting
     */
    public MergeItem(Context context, ArrayList<Event> arrayList, long l2, e e2, e e3, String string) {
        this.c = "";
        this.d = -1;
        this.e = 1;
        this.f = 1.0;
        this.g = false;
        this.h = false;
        this.a(Type.TYPE_SUBLIST);
        this.a(arrayList);
        this.a(e2);
        this.b(e3);
        if (e2 != null || e3 != null) {
            l2 = e2 == null ? e3.b() : (e3 == null ? (l2 -= e2.b()) : e3.b() - e2.b());
        }
        this.a(l2);
        this.a(string);
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public MergeItem(MergeItem mergeItem) {
        this.c = "";
        this.d = -1;
        this.e = 1;
        this.f = 1.0;
        this.g = false;
        this.h = false;
        if (mergeItem == null) {
            return;
        }
        this.b = mergeItem.b;
        this.c = mergeItem.c;
        this.d = mergeItem.d;
        this.e = mergeItem.e;
        this.f = mergeItem.f;
        this.g = mergeItem.g;
        this.h = mergeItem.h;
        this.i = mergeItem.i;
        this.j = mergeItem.j;
        if (mergeItem.k != null) {
            this.k = new e(mergeItem.k);
        }
        if (mergeItem.l == null) return;
        this.l = new e(mergeItem.l);
    }

    private static int a(char c2, String string) {
        int n2 = 0;
        for (int i2 = 0; i2 < string.length(); ++i2) {
            if (string.charAt(i2) != c2) continue;
            ++n2;
        }
        return n2;
    }

    public static String b(long l2) {
        StringBuilder stringBuilder = new StringBuilder();
        Object[] arrobject = new Object[]{l2 % 1000};
        String string = stringBuilder.append(String.format((String)"%03d", (Object[])arrobject)).append("").toString();
        long l3 = l2 / 1000;
        StringBuilder stringBuilder2 = new StringBuilder();
        Object[] arrobject2 = new Object[]{l3 % 60};
        String string2 = stringBuilder2.append(String.format((String)"%02d.", (Object[])arrobject2)).append(string).toString();
        long l4 = l3 / 60;
        StringBuilder stringBuilder3 = new StringBuilder();
        Object[] arrobject3 = new Object[]{l4 % 60};
        String string3 = stringBuilder3.append(String.format((String)"%02d:", (Object[])arrobject3)).append(string2).toString();
        long l5 = l4 / 60;
        StringBuilder stringBuilder4 = new StringBuilder();
        Object[] arrobject4 = new Object[]{l5};
        return stringBuilder4.append(String.format((String)"%d:", (Object[])arrobject4)).append(string3).toString();
    }

    /*
     * Exception decompiling
     */
    public static long e(String var0) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // java.util.ConcurrentModificationException
        // java.util.LinkedList$ReverseLinkIterator.next(LinkedList.java:217)
        // org.benf.cfr.reader.bytecode.analysis.structured.statement.Block.extractLabelledBlocks(Block.java:212)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement$LabelledBlockExtractor.transform(Op04StructuredStatement.java:485)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.transform(Op04StructuredStatement.java:639)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.insertLabelledBlocks(Op04StructuredStatement.java:649)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:816)
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

    public Type a() {
        return this.b;
    }

    public void a(double d2) {
        if (d2 > 0.0) {
            this.f = d2;
        }
    }

    public void a(int n2) {
        this.e = n2;
    }

    public void a(long l2) {
        if (l2 >= 0) {
            this.d = l2;
        }
    }

    public void a(Bundle bundle) {
        this.a(bundle.getString("description"));
        this.a(bundle.getLong("duration", 0));
        this.a(bundle.getInt("loop_count", 1));
        this.a(bundle.getDouble("speed", 1.0));
        this.a(bundle.getBoolean("remove_pause_at_start", false));
        this.b(bundle.getBoolean("remove_pause_at_end", false));
        this.b(bundle.getString("path"));
    }

    public void a(Type type) {
        this.b = type;
    }

    public void a(e e2) {
        this.k = e2;
    }

    public void a(String string) {
        this.c = string;
    }

    public void a(ArrayList<Event> arrayList) {
        this.j = arrayList;
    }

    public void a(boolean bl) {
        this.g = bl;
    }

    public String b() {
        return this.c;
    }

    public void b(e e2) {
        this.l = e2;
    }

    public void b(String string) {
        this.i = string;
    }

    public void b(boolean bl) {
        this.h = bl;
    }

    public String c(String string) {
        if (this.k == null) {
            return string;
        }
        return MergeItem.b(this.k.b());
    }

    public boolean c() {
        return this.g;
    }

    public String d(String string) {
        if (this.l == null) {
            return string;
        }
        return MergeItem.b(this.l.b());
    }

    public boolean d() {
        return this.h;
    }

    public int e() {
        return this.e;
    }

    public double f() {
        return this.f;
    }

    public String g() {
        return this.i;
    }

    public ArrayList<Event> h() {
        return this.j;
    }

    public long i() {
        return this.d;
    }

    public void j() {
        this.a(b.c(this.i));
    }

    public e k() {
        return this.k;
    }

    public e l() {
        return this.l;
    }

    /*
     * Enabled aggressive block sorting
     */
    public String m() {
        String string = this.g ? "" + "(" : "" + "[";
        String string2 = string + " ";
        if (this.h) {
            return string2 + ")";
        }
        return string2 + "]";
    }

    public Bundle n() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("type", (Serializable)this.b);
        bundle.putString("description", this.c);
        bundle.putLong("duration", this.d);
        bundle.putInt("loop_count", this.e);
        bundle.putDouble("speed", this.f);
        bundle.putBoolean("remove_pause_at_start", this.g);
        bundle.putBoolean("remove_pause_at_end", this.h);
        bundle.putString("path", this.i);
        return bundle;
    }

    public static final class Type
    extends Enum<Type> {
        public static final /* enum */ Type TYPE_FILE = new Type();
        public static final /* enum */ Type TYPE_PAUSE;
        public static final /* enum */ Type TYPE_SUBLIST;
        private static final /* synthetic */ Type[] a;

        static {
            TYPE_SUBLIST = new Type();
            TYPE_PAUSE = new Type();
            Type[] arrtype = new Type[]{TYPE_FILE, TYPE_SUBLIST, TYPE_PAUSE};
            a = arrtype;
        }

        private Type() {
            super(string, n2);
        }

        public static Type valueOf(String string) {
            return (Type)Enum.valueOf((Class)Type.class, (String)string);
        }

        public static Type[] values() {
            return (Type[])a.clone();
        }
    }

}

