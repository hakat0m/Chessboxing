/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Service
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$OnSharedPreferenceChangeListener
 *  android.os.Handler
 *  android.telephony.PhoneStateListener
 *  android.telephony.TelephonyManager
 *  android.util.Pair
 *  android.widget.Toast
 *  java.io.BufferedReader
 *  java.io.DataOutputStream
 *  java.io.File
 *  java.io.FileInputStream
 *  java.io.FileNotFoundException
 *  java.io.FileOutputStream
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.InputStreamReader
 *  java.io.ObjectInputStream
 *  java.io.ObjectOutputStream
 *  java.io.OutputStream
 *  java.io.Reader
 *  java.io.StreamCorruptedException
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Double
 *  java.lang.Integer
 *  java.lang.InterruptedException
 *  java.lang.Long
 *  java.lang.NumberFormatException
 *  java.lang.Object
 *  java.lang.Process
 *  java.lang.Runnable
 *  java.lang.Runtime
 *  java.lang.Short
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.Thread
 *  java.lang.Throwable
 *  java.nio.ByteOrder
 *  java.util.ArrayList
 *  java.util.Iterator
 *  java.util.LinkedHashMap
 *  java.util.Map
 *  java.util.Map$Entry
 *  java.util.Set
 *  java.util.TreeSet
 *  java.util.concurrent.ArrayBlockingQueue
 */
package com.cygery.repetitouch;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Pair;
import android.widget.Toast;
import com.cygery.repetitouch.Event;
import com.cygery.repetitouch.MergeItem;
import com.cygery.repetitouch.UInputDeviceInfo;
import com.cygery.repetitouch.a;
import com.cygery.repetitouch.c;
import com.cygery.repetitouch.e;
import com.cygery.repetitouch.h;
import com.cygery.repetitouch.j;
import com.cygery.repetitouch.l;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StreamCorruptedException;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ArrayBlockingQueue;

public abstract class b
extends Service
implements SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String N = b.class.getName();
    protected static b a;
    protected static final ArrayList<MergeItem> p;
    protected Event A = null;
    protected j B;
    protected l C;
    protected final Object D = new Object();
    protected a E;
    protected Runnable F;
    protected Handler G;
    protected Class H;
    protected boolean I;
    protected boolean J;
    protected boolean K;
    protected boolean L;
    protected SharedPreferences M;
    protected boolean b;
    protected TelephonyManager c;
    protected PhoneStateListener d;
    protected boolean e = false;
    protected String f = "";
    protected String g = "";
    protected short h;
    protected ArrayList<Integer> i;
    protected double j;
    protected String k;
    protected boolean l;
    protected boolean m;
    protected boolean n;
    protected TreeSet<e> o;
    protected c q;
    protected long r;
    protected long s;
    protected long t;
    protected long u;
    protected ArrayBlockingQueue<Event> v;
    protected ArrayList<Event> w;
    protected Map<Short, String> x;
    protected UInputDeviceInfo y;
    protected boolean z;

    static {
        p = new ArrayList(100);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static String A() {
        boolean bl = b.g("find") && b.g("grep");
        String string = b.a("find /sys/ -name brightness -path '*wled:backlight*' ; exit\n", "ls -R /sys | while read l; do case $l in *:) d=${l%:};; \"\") d=;; *) echo \"$d/$l\";; esac; done | grep \"wled:backlight\" | grep '/brightness$' ; exit\n");
        if (string == null && bl) {
            string = b.a("find /sys/ -name brightness | grep \"wled:backlight\" ; exit\n", "exit\n");
        }
        if (string != null) {
            return string;
        }
        String string2 = b.a("find /sys/ -name brightness -path '*lcd-backlight*' ; exit\n", "ls -R /sys | while read l; do case $l in *:) d=${l%:};; \"\") d=;; *) echo \"$d/$l\";; esac; done | grep \"lcd-backlight\" | grep '/brightness$' ; exit\n");
        if (string2 == null && bl) {
            string2 = b.a("find /sys/ -name brightness | grep \"lcd-backlight\" ; exit\n", "exit\n");
        }
        if (string2 != null) {
            return string2;
        }
        String string3 = b.a("find /sys/ -name brightness -path '*-backlight*' ; exit\n", "ls -R /sys | while read l; do case $l in *:) d=${l%:};; \"\") d=;; *) echo \"$d/$l\";; esac; done | grep \"\\-backlight\" | grep '/brightness$' ; exit\n");
        String string4 = string3 == null && bl ? b.a("find /sys/ -name brightness | grep \"\\-backlight\" ; exit\n", "exit\n") : string3;
        if (string4 != null) return string4;
        return null;
    }

    public static int B() {
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"su"});
            DataOutputStream dataOutputStream = new DataOutputStream(process.getOutputStream());
            dataOutputStream.writeBytes("exit\n");
            dataOutputStream.flush();
            process.waitFor();
            dataOutputStream.close();
            process.destroy();
            int n2 = process.exitValue();
            return n2;
        }
        catch (IOException var1_3) {
            com.cygery.utilities.a.c(N, (Throwable)var1_3);
            return -1;
        }
        catch (InterruptedException var0_4) {
            com.cygery.utilities.a.c(N, (Throwable)var0_4);
            return -2;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static boolean C() {
        var0 = true;
        try {
            var5_1 = Runtime.getRuntime().exec(new String[]{"sh"});
            var6_2 = new DataOutputStream(var5_1.getOutputStream());
            var6_2.writeBytes("getenforce\n");
            var6_2.writeBytes("exit\n");
            var6_2.flush();
            var7_3 = new BufferedReader((Reader)new InputStreamReader(var5_1.getInputStream()));
            while ((var8_4 = var7_3.readLine()) != null) {
                var9_5 = "Enforcing".equals((Object)var8_4);
                if (!var9_5) continue;
                ** GOTO lbl23
            }
            ** GOTO lbl22
        }
        catch (IOException var3_6) {
            var0 = false;
            var4_7 = var3_6;
            ** GOTO lbl32
            catch (InterruptedException var1_9) {
                var0 = false;
                var2_10 = var1_9;
                ** GOTO lbl29
lbl22: // 1 sources:
                var0 = false;
lbl23: // 2 sources:
                try {
                    var5_1.waitFor();
                    var6_2.close();
                    var5_1.destroy();
                }
                catch (InterruptedException var2_11) {}
lbl29: // 2 sources:
                com.cygery.utilities.a.c(b.N, (Throwable)var2_10);
            }
            catch (IOException var4_8) {}
lbl32: // 2 sources:
            com.cygery.utilities.a.c(b.N, (Throwable)var4_7);
        }
        com.cygery.utilities.a.a(b.N, "isSELinuxEnforcing(): " + var0);
        return var0;
    }

    public static b a() {
        return a;
    }

    /*
     * Exception decompiling
     */
    public static String a(String var0_1, String var1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 6[CATCHBLOCK]
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

    /*
     * Exception decompiling
     */
    public static ArrayList<String> a(String var0_1, int var1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [1[TRYBLOCK]], but top level block is 7[CASE]
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

    public static Map<Short, Short> a(Map<Short, String> map, Map<Short, String> map2) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Iterator iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            short s2 = (Short)iterator.next();
            String string = (String)map.get((Object)s2);
            ArrayList arrayList = new ArrayList();
            for (Map.Entry entry : map2.entrySet()) {
                if (!string.equals(entry.getValue())) continue;
                arrayList.add(entry.getKey());
            }
            if (arrayList.isEmpty()) {
                com.cygery.utilities.a.b(N, "can't map " + s2 + " with name " + string);
                continue;
            }
            if (arrayList.contains((Object)s2)) {
                linkedHashMap.put((Object)s2, (Object)s2);
                continue;
            }
            linkedHashMap.put((Object)s2, arrayList.get(0));
        }
        return linkedHashMap;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void a(Map<Short, String> map, ArrayList<Event> arrayList) {
        Map.Entry entry;
        com.cygery.utilities.a.a(N, "remapInputEventIndices");
        Map<Short, Short> map2 = b.a(map, this.x);
        Iterator iterator = map2.entrySet().iterator();
        do {
            if (iterator.hasNext()) continue;
            return;
        } while (((Short)(entry = (Map.Entry)iterator.next()).getKey()).equals(entry.getValue()));
        boolean bl = true;
        if (bl) {
            for (Map.Entry entry2 : map2.entrySet()) {
                com.cygery.utilities.a.a(N, "mapping: " + entry2.getKey() + " -> " + entry2.getValue());
            }
            for (Event event : arrayList) {
                short s2 = event.d();
                if (map2.containsKey((Object)s2)) {
                    short s3 = (Short)map2.get((Object)s2);
                    if (s2 == s3) continue;
                    event.a(s3);
                    event.setDeviceIndicesInRawBytes(s3);
                    continue;
                }
                com.cygery.utilities.a.c(N, "can't remap oldIndex " + s2);
            }
        }
    }

    /*
     * Exception decompiling
     */
    public static UInputDeviceInfo b(String var0_1, int var1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Extractable last case doesn't follow previous
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:486)
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

    public static void b(String string, String string2) {
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"su"});
            DataOutputStream dataOutputStream = new DataOutputStream(process.getOutputStream());
            dataOutputStream.writeBytes("chcon " + string2 + " " + string + "\n");
            dataOutputStream.writeBytes("exit\n");
            dataOutputStream.flush();
            process.waitFor();
            dataOutputStream.close();
            process.destroy();
            return;
        }
        catch (IOException var3_4) {
            com.cygery.utilities.a.c(N, (Throwable)var3_4);
            return;
        }
        catch (InterruptedException var2_5) {
            com.cygery.utilities.a.c(N, (Throwable)var2_5);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static long c(String string) {
        com.cygery.utilities.a.a(N, "load duration from " + string);
        long l2 = -1;
        try {
            FileInputStream fileInputStream = new FileInputStream(string);
            l2 = new ObjectInputStream((InputStream)fileInputStream).readLong();
            fileInputStream.close();
        }
        catch (FileNotFoundException var6_3) {
            com.cygery.utilities.a.b(N, "load: file not found");
        }
        catch (StreamCorruptedException var5_4) {
            com.cygery.utilities.a.c(N, "StreamCorruptedException:");
            com.cygery.utilities.a.c(N, (Throwable)var5_4);
        }
        catch (IOException var4_5) {
            com.cygery.utilities.a.c(N, "IOException:");
            com.cygery.utilities.a.c(N, (Throwable)var4_5);
        }
        com.cygery.utilities.a.a(N, "load successful");
        return l2;
    }

    public static int d(String string) {
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"sh"});
            DataOutputStream dataOutputStream = new DataOutputStream(process.getOutputStream());
            dataOutputStream.writeBytes(string + " ; exit" + "\n");
            dataOutputStream.flush();
            process.waitFor();
            dataOutputStream.close();
            process.destroy();
            int n2 = process.exitValue();
            if (n2 == 2) {
                return 0;
            }
            return n2;
        }
        catch (IOException var2_4) {
            com.cygery.utilities.a.c(N, (Throwable)var2_4);
            return -1;
        }
        catch (InterruptedException var1_5) {
            com.cygery.utilities.a.c(N, (Throwable)var1_5);
            return -2;
        }
    }

    /*
     * Exception decompiling
     */
    public static ArrayList<String> e(String var0) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [1[TRYBLOCK]], but top level block is 7[CASE]
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

    public static Map<Short, String> f(String string) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        ArrayList<String> arrayList = b.e(string);
        if (arrayList == null) {
            com.cygery.utilities.a.c(N, "getEventIndexNameMap: devices == null");
            return null;
        }
        for (String string2 : arrayList) {
            String[] arrstring = string2.split("[:]+", 2);
            if (arrstring.length < 2) {
                com.cygery.utilities.a.c(N, "getEventIndexNameMap: tokens.length < 2 in line: " + string2);
                return null;
            }
            String string3 = arrstring[0];
            String string4 = arrstring[1];
            String string5 = string3.substring("/dev/input/event".length());
            try {
                linkedHashMap.put((Object)Short.parseShort((String)string5), (Object)string4);
            }
            catch (NumberFormatException var9_5) {
                com.cygery.utilities.a.c(N, "can't parse indexString: " + string5);
                com.cygery.utilities.a.c(N, (Throwable)var9_5);
            }
        }
        return linkedHashMap;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static boolean g(String string) {
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"sh"});
            DataOutputStream dataOutputStream = new DataOutputStream(process.getOutputStream());
            dataOutputStream.writeBytes("command -v " + string + " > /dev/null 2>&1 ; exit\n");
            dataOutputStream.flush();
            process.waitFor();
            dataOutputStream.close();
            process.destroy();
            int n2 = process.exitValue();
            if (n2 != 0) return false;
            return true;
        }
        catch (IOException var2_4) {
            com.cygery.utilities.a.c(N, "IOException:");
            com.cygery.utilities.a.c(N, (Throwable)var2_4);
        }
        return false;
        catch (InterruptedException interruptedException) {
            com.cygery.utilities.a.c(N, "InterruptedException:");
            com.cygery.utilities.a.c(N, (Throwable)interruptedException);
            return false;
        }
    }

    public static String z() {
        return b.a("find /sys/ -name bl_power ; exit\n", "ls -R /sys | while read l; do case $l in *:) d=${l%:};; \"\") d=;; *) echo \"$d/$l\";; esac; done | grep bl_power ; exit\n");
    }

    /*
     * Exception decompiling
     */
    public Pair<Long, ArrayList<Event>> a(String var1, boolean var2_2) {
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

    public void a(long l2) {
        this.s = l2;
    }

    protected abstract void a(Intent var1);

    public void a(ArrayList<Event> arrayList, long l2) {
        if (this.l()) {
            this.w = arrayList;
            this.t = l2;
        }
    }

    protected abstract void a(boolean var1);

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected boolean a(int var1) {
        if (!((Event)this.w.get(var1)).a(this.h, 0, 0, 0)) {
            return false;
        }
        var2_2 = var1 - 1;
        if (((Event)this.w.get(var2_2)).a(this.h, 0, 2, 0)) ** GOTO lbl-1000
        if (!((Event)this.w.get(var2_2)).a(this.h, 1, 330, 0)) {
            return false;
        }
        if (!((Event)this.w.get(--var2_2)).a(this.h, 0, 2, 0)) {
            if (!((Event)this.w.get(var2_2)).a(this.h, 0, 0, 0)) {
                return false;
            }
            var3_3 = var2_2 + 1;
        } else lbl-1000: // 2 sources:
        {
            var3_3 = var2_2;
        }
        var4_4 = var3_3 - 1;
        while (((Event)this.w.get(var4_4)).a(this.h, 0, 2, 0)) {
            --var4_4;
        }
        while (((Event)this.w.get(var4_4)).matchesIndexTypeCode(this.h, 3, 53) || ((Event)this.w.get(var4_4)).matchesIndexTypeCode(this.h, 3, 54) || ((Event)this.w.get(var4_4)).a(this.h, 3, 57, 0) || ((Event)this.w.get(var4_4)).a(this.h, 3, 52, 0) || ((Event)this.w.get(var4_4)).a(this.h, 3, 49, 0) || ((Event)this.w.get(var4_4)).a(this.h, 3, 48, 0) || ((Event)this.w.get(var4_4)).a(this.h, 3, 50, 0) || ((Event)this.w.get(var4_4)).a(this.h, 3, 58, 0)) {
            --var4_4;
        }
        if (((Event)this.w.get(var4_4)).a(this.h, 3, 48, 1)) {
            --var4_4;
        }
        if (((Event)this.w.get(var4_4)).a(this.h, 3, 57, 0)) {
            --var4_4;
        }
        if (!((Event)this.w.get(var4_4)).a(this.h, 0, 0, 0)) {
            if (!((Event)this.w.get(var4_4)).a(this.h, 1, 330, 0)) {
                return false;
            }
            if (!((Event)this.w.get(--var4_4)).a(this.h, 0, 0, 0)) {
                if (!((Event)this.w.get(var4_4)).a(this.h, 3, 24, 0)) {
                    return false;
                }
                if (((Event)this.w.get(--var4_4)).a(this.h, 3, 48, 0)) {
                    --var4_4;
                }
                if (!((Event)this.w.get(var4_4)).a(this.h, 0, 0, 0)) {
                    return false;
                }
            }
        }
        if (((Event)this.w.get(var5_5 = var4_4 - 1)).a(this.h, 0, 2, 0) != false) return true;
        if (!((Event)this.w.get(var5_5)).a(this.h, 1, 330, 1)) {
            return false;
        }
        var6_6 = var5_5 - 1;
        if (((Event)this.w.get(var6_6)).a(this.h, 0, 2, 0) != false) return true;
        return false;
    }

    protected abstract boolean a(Event var1);

    protected boolean a(String string) {
        return this.a(string, this.t, this.w);
    }

    public boolean a(String string, long l2, ArrayList<Event> arrayList) {
        com.cygery.utilities.a.a(N, "save events to " + string);
        this.K = true;
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream((OutputStream)new FileOutputStream(string));
            objectOutputStream.writeLong(l2);
            objectOutputStream.writeObject(arrayList);
            objectOutputStream.writeObject(this.x);
            objectOutputStream.writeObject((Object)this.y);
            objectOutputStream.writeObject((Object)ByteOrder.nativeOrder().toString());
            objectOutputStream.writeBoolean(Event.IS_64_BIT);
            objectOutputStream.flush();
            objectOutputStream.close();
        }
        catch (FileNotFoundException var7_5) {
            this.K = false;
            com.cygery.utilities.a.b(N, "save: file not found");
            return false;
        }
        catch (IOException var6_6) {
            this.K = false;
            com.cygery.utilities.a.c(N, "IOException:");
            com.cygery.utilities.a.c(N, (Throwable)var6_6);
            return false;
        }
        this.K = false;
        com.cygery.utilities.a.a(N, "save successful");
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected String b(int n2) {
        if (n2 == 2) {
            return "ERR_PARAMS";
        }
        if (n2 == 3) {
            return "ERR_INIT";
        }
        if (n2 == 4) {
            return "ERR_REC_POLL";
        }
        if (n2 == 5) {
            return "ERR_REC_READ";
        }
        if (n2 == 6) {
            return "ERR_REC_WRITE";
        }
        if (n2 == 7) {
            return "ERR_REP_READ";
        }
        if (n2 == 8) {
            return "ERR_REP_WRITE";
        }
        if (n2 == 9) {
            return "ERR_USER_INPUT";
        }
        if (n2 == 10) {
            return "ERR_IOCTL";
        }
        if (n2 == 11) {
            return "ERR_BUFFER_FULL";
        }
        String string = null;
        if (n2 != 12) return string;
        return "ERR_REP_INDEX";
    }

    protected abstract void b();

    protected void b(boolean bl) {
        com.cygery.utilities.a.a(N, "start createReplayThread()");
        if (bl) {
            this.F.run();
            return;
        }
        new Thread(this.F).start();
    }

    protected boolean b(String string) {
        Pair<Long, ArrayList<Event>> pair = this.a(string, true);
        if (pair != null) {
            this.t = (Long)pair.first;
            this.w = (ArrayList)pair.second;
            return true;
        }
        this.t = 0;
        this.w = new ArrayList(102400);
        return false;
    }

    protected void c() {
        this.a(false);
    }

    protected void c(final String string, final int n2) {
        this.G.post(new Runnable(){

            public void run() {
                Toast.makeText((Context)b.this, (CharSequence)string, (int)n2).show();
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void d() {
        if (this.e) {
            if (!this.g()) return;
            {
                Object object;
                Object object2 = object = this.D;
                synchronized (object2) {
                    this.C.c(true);
                    return;
                }
            }
        } else {
            com.cygery.utilities.a.c(N, "not initialised");
        }
    }

    protected boolean e() {
        if (this.B != null && this.B.isAlive()) {
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected boolean f() {
        Object object;
        Object object2 = object = this.D;
        synchronized (object2) {
            if (this.C == null) return false;
            if (!this.C.isAlive()) return false;
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected boolean g() {
        Object object;
        Object object2 = object = this.D;
        synchronized (object2) {
            if (!this.f()) return false;
            if (this.C.a()) return false;
            return true;
        }
    }

    protected void h() {
        com.cygery.utilities.a.a(N, "record stopped");
        if (this.L) {
            this.startService(new Intent((Context)this, this.H).setAction("panelservice").putExtra("message", "recordstopped"));
        }
    }

    protected void h(String string) {
        this.c(string, 0);
    }

    protected void i() {
        com.cygery.utilities.a.a(N, "replay stopped");
        if (this.L) {
            this.startService(new Intent((Context)this, this.H).setAction("panelservice").putExtra("message", "replaystopped"));
        }
    }

    protected void j() {
        this.b((Object)this.getFilesDir() + File.separator + "autosave.rt");
    }

    protected void k() {
        this.a((Object)this.getFilesDir() + File.separator + "autosave.rt");
    }

    public boolean l() {
        if (!(this.I || this.J || this.K || this.e() || this.g())) {
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected boolean m() {
        boolean bl = this.e();
        boolean bl2 = this.g();
        if (!(this.I || this.J || this.K || bl || bl2)) {
            return true;
        }
        int n2 = this.I ? h.e.info_phone_in_use_no_action_allowed : (this.J ? h.e.info_is_loading_file_no_action_allowed : (this.K ? h.e.info_is_saving_file_no_action_allowed : (bl ? h.e.info_is_recording_no_action_allowed : (bl2 ? h.e.info_is_replaying_no_action_allowed : 0))));
        Toast.makeText((Context)this, (int)n2, (int)0).show();
        String string = N;
        StringBuilder stringBuilder = new StringBuilder().append("checkAndNotifyIfActionIsAllowed: ");
        String string2 = this.I ? "mIsPhoneInUse" : "";
        StringBuilder stringBuilder2 = stringBuilder.append(string2);
        String string3 = this.J ? "mIsLoadingFile" : "";
        StringBuilder stringBuilder3 = stringBuilder2.append(string3);
        String string4 = this.K ? "mIsSavingFile" : "";
        StringBuilder stringBuilder4 = stringBuilder3.append(string4);
        String string5 = bl ? "isRecording" : "";
        StringBuilder stringBuilder5 = stringBuilder4.append(string5);
        String string6 = bl2 ? "isReplaying" : "";
        com.cygery.utilities.a.a(string, stringBuilder5.append(string6).toString());
        return false;
    }

    /*
     * Exception decompiling
     */
    protected void n() {
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

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void o() {
        try {
            this.j = Double.parseDouble((String)this.M.getString("replaySpeed", "1"));
        }
        catch (NumberFormatException var1_1) {
            this.j = 1.0;
        }
        if (this.j < 0.0) {
            this.j = 1.0;
        }
    }

    public void onCreate() {
        super.onCreate();
        com.cygery.utilities.a.a((Context)this);
        com.cygery.utilities.a.a(N, "onCreate()");
        a = this;
        this.b = true;
        this.M = this.getSharedPreferences("repetitouch_prefs", 0);
        this.M.registerOnSharedPreferenceChangeListener((SharedPreferences.OnSharedPreferenceChangeListener)this);
        this.v = new ArrayBlockingQueue(1024);
        this.w = new ArrayList(102400);
        this.E = null;
        this.l = false;
        this.m = this.M.getBoolean("replayUnchecked", false);
        this.n = this.M.getBoolean("ignoreCallState", false);
        this.z = this.M.getBoolean("useVirtualInputDevice", false);
        this.G = new Handler();
        this.q = new c();
        this.c = (TelephonyManager)this.getSystemService("phone");
        this.d = new PhoneStateListener(){

            /*
             * Enabled aggressive block sorting
             */
            public void onCallStateChanged(int n2, String string) {
                if (b.this.n) {
                    return;
                }
                switch (n2) {
                    default: {
                        b.this.I = false;
                        break;
                    }
                    case 1: 
                    case 2: {
                        b.this.I = true;
                        b.this.c();
                        b.this.d();
                    }
                }
                super.onCallStateChanged(n2, string);
            }
        };
        if (this.c != null) {
            this.c.listen(this.d, 32);
        }
        this.e = true;
        this.L = true;
        this.b(false);
    }

    public void onDestroy() {
        super.onDestroy();
        com.cygery.utilities.a.a(N, "onDestroy() start");
        this.L = false;
        this.M.unregisterOnSharedPreferenceChangeListener((SharedPreferences.OnSharedPreferenceChangeListener)this);
        if (this.c != null) {
            this.c.listen(this.d, 0);
        }
        this.c();
        this.p();
        new Thread(){

            public void run() {
                b.this.k();
            }
        }.start();
        if (b.C()) {
            b.b(this.f, this.getString(h.e.selinux_context_app_data_file));
        }
        com.cygery.utilities.a.a(N, "onDestroy() end");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String string) {
        if ("eventIndexString".equals((Object)string)) {
            String string2 = sharedPreferences.getString(string, null);
            if (string2 != null) {
                try {
                    this.h = Short.parseShort((String)string2);
                    this.k = "" + this.h;
                    Iterator iterator = this.i.iterator();
                    while (iterator.hasNext()) {
                        int n2 = (Integer)iterator.next();
                        this.k = this.k + " " + n2;
                    }
                    com.cygery.utilities.a.a(N, "event indices update: " + this.k);
                }
                catch (NumberFormatException var5_6) {
                    // empty catch block
                }
            }
            this.b(false);
            return;
        }
        if ("eventMultitouchType".equals((Object)string)) {
            String string3 = sharedPreferences.getString(string, null);
            if (string3 != null) {
                this.g = string3;
                com.cygery.utilities.a.a(N, "eventMultitouchType changed to: " + this.g);
            }
            this.b(false);
            return;
        }
        if ("replayUnchecked".equals((Object)string)) {
            this.m = sharedPreferences.getBoolean(string, false);
            this.b(false);
            return;
        }
        if ("ignoreCallState".equals((Object)string)) {
            this.n = sharedPreferences.getBoolean(string, false);
            if (this.n) {
                this.I = false;
                return;
            }
            switch (((TelephonyManager)this.getSystemService("phone")).getCallState()) {
                default: {
                    return;
                }
                case 1: 
                case 2: 
            }
            this.I = true;
            return;
        }
        if ("useVirtualInputDevice".equals((Object)string)) {
            this.z = sharedPreferences.getBoolean(string, false);
            this.b(false);
            return;
        }
        if (!string.equals((Object)"replaySpeed")) return;
        {
            this.o();
            this.b(false);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int onStartCommand(Intent intent, int n2, int n3) {
        if (intent == null) {
            com.cygery.utilities.a.c(N, "not normally started, calling stopSelf. intent=null flags=" + n2 + " startId=" + n3);
            this.stopSelf();
            return 2;
        }
        this.f = intent.getStringExtra("eventserverPath");
        this.g = intent.getStringExtra("eventMultitouchType");
        String string = intent.getStringExtra("eventIndex");
        this.i = intent.getIntegerArrayListExtra("additionalEventIndizes");
        if (string != null) {
            try {
                this.h = Short.parseShort((String)string);
            }
            catch (NumberFormatException var7_5) {
                this.h = -1;
            }
        } else {
            this.h = -1;
        }
        if (this.i == null) {
            this.i = new ArrayList();
        }
        this.k = "" + this.h;
        Iterator iterator = this.i.iterator();
        while (iterator.hasNext()) {
            int n4 = (Integer)iterator.next();
            this.k = this.k + " " + n4;
        }
        if (this.f != null) {
            this.x = b.f(this.f);
        }
        if (this.f == null) {
            com.cygery.utilities.a.c(N, "eventserverPath == null");
        }
        if (this.g == null) {
            com.cygery.utilities.a.c(N, "eventMultitouchType == null");
        }
        com.cygery.utilities.a.a(N, "event indices init: " + this.k);
        com.cygery.utilities.a.a(N, "eventMultitouchType init: " + this.g);
        return 3;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void p() {
        Object object;
        com.cygery.utilities.a.a(N, "start killReplayThread()");
        Object object2 = object = this.D;
        synchronized (object2) {
            if (this.f()) {
                this.C.c(false);
                try {
                    this.C.join();
                    com.cygery.utilities.a.a(N, "replay thread joined");
                }
                catch (InterruptedException var3_3) {
                    com.cygery.utilities.a.c(N, "joining replay thread failed");
                }
            }
            return;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected void q() {
        if (this.E == null || !this.E.isAlive()) return;
        try {
            this.E.join();
            return;
        }
        catch (InterruptedException var1_1) {
            com.cygery.utilities.a.c(N, "InterruptedException:");
            com.cygery.utilities.a.c(N, (Throwable)var1_1);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void r() {
        boolean bl = this.M.getBoolean("useAlternativeTouchscreenResetMethod", false);
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"su"});
            DataOutputStream dataOutputStream = new DataOutputStream(process.getOutputStream());
            StringBuilder stringBuilder = new StringBuilder().append(this.f).append(" ").append("/dev/input/event").append(" ");
            String string = bl ? "reset_alternative" : "reset";
            dataOutputStream.writeBytes(stringBuilder.append(string).append(" ").append(this.k).append(" ; exit").append("\n").toString());
            dataOutputStream.flush();
            process.waitFor();
            dataOutputStream.close();
            process.destroy();
            if (process.exitValue() != 0) {
                com.cygery.utilities.a.c(N, "resetAllTools exit: " + process.exitValue());
                return;
            }
            com.cygery.utilities.a.a(N, "resetAllTools exit: " + process.exitValue());
            return;
        }
        catch (IOException var3_6) {
            com.cygery.utilities.a.c(N, "IOException:");
            com.cygery.utilities.a.c(N, (Throwable)var3_6);
            return;
        }
        catch (InterruptedException var2_7) {
            com.cygery.utilities.a.c(N, "InterruptedException:");
            com.cygery.utilities.a.c(N, (Throwable)var2_7);
            return;
        }
    }

    public ArrayBlockingQueue<Event> s() {
        return this.v;
    }

    public long t() {
        return this.s;
    }

    public ArrayList<Event> u() {
        return this.w;
    }

    public long v() {
        return this.t;
    }

    public Handler w() {
        return this.G;
    }

    public Class x() {
        return this.H;
    }

    public SharedPreferences y() {
        return this.M;
    }

}

