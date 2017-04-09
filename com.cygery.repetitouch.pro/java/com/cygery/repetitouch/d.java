/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.app.ProgressDialog
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnCancelListener
 *  android.content.DialogInterface$OnClickListener
 *  android.content.Intent
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 *  android.content.res.Resources$Theme
 *  android.net.Uri
 *  android.os.AsyncTask
 *  android.os.AsyncTask$Status
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.preference.PreferenceManager
 *  android.text.Html
 *  android.text.method.LinkMovementMethod
 *  android.text.method.MovementMethod
 *  android.util.DisplayMetrics
 *  android.util.Pair
 *  android.util.TypedValue
 *  android.view.View
 *  android.view.WindowManager
 *  android.view.WindowManager$BadTokenException
 *  android.widget.TextView
 *  java.io.DataOutputStream
 *  java.io.File
 *  java.io.FileNotFoundException
 *  java.io.FileOutputStream
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.OutputStream
 *  java.lang.Boolean
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.IllegalArgumentException
 *  java.lang.Integer
 *  java.lang.InterruptedException
 *  java.lang.Object
 *  java.lang.Process
 *  java.lang.Runtime
 *  java.lang.String
 *  java.lang.Throwable
 *  java.lang.Void
 *  java.util.ArrayList
 *  java.util.Iterator
 *  java.util.List
 *  java.util.Locale
 */
package com.cygery.repetitouch;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import com.cygery.repetitouch.b;
import com.cygery.repetitouch.h;
import com.cygery.utilities.a;
import com.cygery.utilities.c;
import com.cygery.utilities.d;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import t4X4QwIV.HfM5AFD8kd7A;

public abstract class d
extends Activity
implements a.d,
d.a {
    protected static final String[] a;
    public static boolean b;
    private static final String q;
    protected Context c;
    protected String d;
    protected File e;
    protected String f;
    protected String g;
    protected int h;
    protected boolean i;
    protected com.cygery.utilities.d j;
    protected a k;
    protected List<Pair<String, Boolean>> l;
    protected List<String> m;
    protected final Class n;
    protected final Class o;
    protected boolean p;

    static {
        q = d.class.getName();
        a = new String[]{"touch", "ts"};
    }

    public d(Class class_, Class class_2) {
        this.n = class_;
        this.o = class_2;
        this.h = 0;
        this.l = new ArrayList();
        this.m = new ArrayList();
    }

    public static void a(String string) {
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"su"});
            DataOutputStream dataOutputStream = new DataOutputStream(process.getOutputStream());
            dataOutputStream.writeBytes("rm " + string + " ; exit\n");
            dataOutputStream.flush();
            process.waitFor();
            dataOutputStream.close();
            process.destroy();
            return;
        }
        catch (IOException var2_3) {
            com.cygery.utilities.a.c(q, (Throwable)var2_3);
            return;
        }
        catch (InterruptedException var1_4) {
            com.cygery.utilities.a.c(q, (Throwable)var1_4);
            return;
        }
    }

    public static void a(String string, SharedPreferences sharedPreferences) {
        ArrayList<String> arrayList = b.e(string);
        if (arrayList.size() > 0) {
            String string2 = (String)arrayList.get(0);
            for (int i2 = 1; i2 < arrayList.size(); ++i2) {
                String string3 = string2 + "\n" + (String)arrayList.get(i2);
                string2 = string3;
            }
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("storedInputEventIndicesAndNames", string2);
            editor.apply();
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public static boolean a(ArrayList<String> var0) {
        var1_1 = true;
        var2_2 = var0.iterator();
        var3_3 = false;
        var4_4 = false;
        while (var2_2.hasNext()) {
            var5_7 = ((String)var2_2.next()).split("[:]+");
            if (var5_7.length < 3) {
                return false;
            }
            var6_9 = Integer.parseInt((String)var5_7[0]);
            var7_8 = Integer.parseInt((String)var5_7[2]);
            switch (var6_9) {
                case 53: {
                    if (var7_8 <= 0) ** GOTO lbl17
                    var8_5 = var3_3;
                    var9_6 = var1_1;
                    ** GOTO lbl26
                }
lbl17: // 2 sources:
                default: {
                    ** GOTO lbl-1000
                }
                case 54: 
            }
            if (var7_8 > 0) {
                var8_5 = var1_1;
                var9_6 = var4_4;
            } else lbl-1000: // 2 sources:
            {
                var8_5 = var3_3;
                var9_6 = var4_4;
            }
lbl26: // 3 sources:
            var4_4 = var9_6;
            var3_3 = var8_5;
        }
        if (var4_4 == false) return false;
        if (var3_3 == false) return false;
        return var1_1;
    }

    public static ArrayList<Pair<String, String>> b(String string) {
        ArrayList arrayList = new ArrayList(10);
        ArrayList<String> arrayList2 = b.e(string);
        if (arrayList2 == null) {
            return null;
        }
        Iterator iterator = arrayList2.iterator();
        while (iterator.hasNext()) {
            String[] arrstring = ((String)iterator.next()).split("[:]+", 2);
            if (arrstring.length < 2) {
                return null;
            }
            String string2 = arrstring[0];
            arrayList.add((Object)new Pair((Object)arrstring[1], (Object)string2.substring("/dev/input/event".length())));
        }
        return arrayList;
    }

    /*
     * Exception decompiling
     */
    public static void b(String var0_1, SharedPreferences var1) {
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
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    public static boolean b(Context var0) {
        var1_1 = var0.getSharedPreferences("repetitouch_prefs", 0).getInt("lastAppVersion", -1);
        var2_2 = Integer.MAX_VALUE;
        try {
            var4_3 = var0.getPackageManager();
            if (var4_3 != null) {
                var2_2 = HfM5AFD8kd7A.ra6kJ1Wnv83AG9X((PackageManager)var4_3, (String)var0.getPackageName(), (int)0).versionCode;
            }
lbl7: // 4 sources:
            do {
                if (var2_2 > var1_1) {
                    return true;
                }
                return false;
                break;
            } while (true);
        }
        catch (PackageManager.NameNotFoundException var3_4) {
            ** continue;
        }
    }

    public static ArrayList<Pair<String, String>> c(String string) {
        ArrayList arrayList = new ArrayList(10);
        ArrayList<Pair<String, String>> arrayList2 = d.b(string);
        ArrayList arrayList3 = new ArrayList(10);
        if (arrayList2 == null) {
            return null;
        }
        for (Pair pair : arrayList2) {
            for (String string2 : a) {
                if (!((String)pair.first).toLowerCase(Locale.getDefault()).contains((CharSequence)string2)) continue;
                arrayList3.add((Object)pair);
            }
        }
        for (Pair pair2 : arrayList3) {
            ArrayList<String> arrayList4 = b.a(string, Integer.parseInt((String)((String)pair2.second)));
            if (arrayList4 == null || !d.a(arrayList4)) continue;
            arrayList.add((Object)pair2);
        }
        if (arrayList.isEmpty()) {
            for (Pair pair3 : arrayList2) {
                ArrayList<String> arrayList5 = b.a(string, Integer.parseInt((String)((String)pair3.second)));
                if (arrayList5 == null || !d.a(arrayList5)) continue;
                arrayList.add((Object)pair3);
            }
        }
        return arrayList;
    }

    /*
     * Exception decompiling
     */
    protected static void c(Context var0) {
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
     * Exception decompiling
     */
    public static String d(Context var0) {
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

    @Override
    public String a(Context context) {
        return d.d(context);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected String a(String var1, String var2_2) {
        if ("-1".equals((Object)var2_2)) {
            return "O";
        }
        var3_3 = d.c(var1);
        if (var3_3 == null || var3_3.isEmpty()) {
            this.b(this.getString(h.e.error_no_usable_input_event), true);
            com.cygery.utilities.a.c(d.q, "touchDevices null or empty in getMultitouchType");
            return null;
        }
        var4_4 = var3_3.iterator();
        do {
            if (!var4_4.hasNext()) {
                this.b(this.getString(h.e.error_no_usable_input_event), true);
                com.cygery.utilities.a.c(d.q, "indexString in getMultitouchType not in touchDevices: " + var2_2);
                return null;
            }
            var5_5 = (Pair)var4_4.next();
        } while (!((String)var5_5.second).equals((Object)var2_2));
        var6_6 = b.a(var1, Integer.parseInt((String)((String)var5_5.second))).iterator();
        var7_7 = false;
        var8_8 = false;
        var9_9 = false;
        do {
            if (!var6_6.hasNext()) ** GOTO lbl44
            var10_14 = (String)var6_6.next();
            var11_10 = var10_14.split("[:]+");
            if (var11_10.length < 3) {
                com.cygery.utilities.a.c(d.q, "tokens.length < 3 in getMultitouchType for String: " + var10_14);
                return null;
            }
            var12_13 = Integer.parseInt((String)var11_10[0]);
            var13_11 = Integer.parseInt((String)var11_10[2]);
            if (var12_13 != 57 || var13_11 <= 0) ** GOTO lbl34
            var14_15 = var7_7;
            var15_12 = var8_8;
            var16_16 = true;
            ** GOTO lbl55
lbl34: // 1 sources:
            if (var12_13 != 47) ** GOTO lbl39
            var14_15 = var7_7;
            var16_16 = var9_9;
            var15_12 = true;
            ** GOTO lbl55
lbl39: // 1 sources:
            if (var12_13 != 48) ** GOTO lbl52
            var14_15 = true;
            var15_12 = var8_8;
            var16_16 = var9_9;
            ** GOTO lbl55
lbl44: // 1 sources:
            if (Build.VERSION.SDK_INT >= 14) {
                if (var9_9 == false) return "A";
                if (var8_8 == false) return "A";
                return "B";
            }
            if (var9_9 && var8_8) {
                return "B";
            }
            if (var7_7 == false) return "O";
            return "H";
lbl52: // 1 sources:
            var14_15 = var7_7;
            var15_12 = var8_8;
            var16_16 = var9_9;
lbl55: // 4 sources:
            var9_9 = var16_16;
            var8_8 = var15_12;
            var7_7 = var14_15;
        } while (true);
    }

    @Override
    public void a() {
        this.j();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected boolean a(String string, boolean bl) {
        int n2;
        int n3;
        int n4 = Build.VERSION.SDK_INT;
        if ("armeabi-v7a".equals((Object)string)) {
            n3 = n4 >= 16 && !bl ? h.d.eventserver_armeabi_v7a_16 : h.d.eventserver_armeabi_v7a_9;
        } else if ("armeabi".equals((Object)string)) {
            n3 = n4 >= 16 && !bl ? h.d.eventserver_armeabi_16 : h.d.eventserver_armeabi_9;
        } else if ("mips".equals((Object)string)) {
            n3 = n4 >= 16 && !bl ? h.d.eventserver_mips_16 : h.d.eventserver_mips_9;
        } else if ("x86".equals((Object)string)) {
            n3 = n4 >= 16 && !bl ? h.d.eventserver_x86_16 : h.d.eventserver_x86_9;
        } else if ("arm64-v8a".equals((Object)string)) {
            n3 = n4 >= 16 && !bl ? h.d.eventserver_arm64_v8a_16 : h.d.eventserver_arm64_v8a_9;
        } else if ("x86_64".equals((Object)string)) {
            n3 = n4 >= 16 && !bl ? h.d.eventserver_x86_64_16 : h.d.eventserver_x86_64_9;
        } else if ("mips64".equals((Object)string)) {
            n3 = n4 >= 16 && !bl ? h.d.eventserver_mips64_16 : h.d.eventserver_mips64_9;
        } else {
            if (string == null || !string.startsWith("armeabi")) {
                this.b(this.getString(h.e.error_unkown_architecture) + ": " + string, true);
                return false;
            }
            n3 = n4 >= 16 && !bl ? h.d.eventserver_armeabi_16 : h.d.eventserver_armeabi_9;
            this.d(this.getString(h.e.error_unkown_architecture) + ": " + string + "\n" + this.getString(h.e.text_using_fallback_mode));
        }
        InputStream inputStream = this.getResources().openRawResource(n3);
        FileOutputStream fileOutputStream = new FileOutputStream(this.e);
        byte[] arrby = new byte[1024];
        while ((n2 = inputStream.read(arrby)) != -1) {
            fileOutputStream.write(arrby, 0, n2);
        }
        try {
            inputStream.close();
            fileOutputStream.flush();
            fileOutputStream.close();
            return true;
        }
        catch (Resources.NotFoundException var7_9) {
            com.cygery.utilities.a.c(q, (Throwable)var7_9);
            this.b(this.getString(h.e.error_eventserver_not_found), true);
            return false;
        }
        catch (FileNotFoundException var6_10) {
            com.cygery.utilities.a.c(q, (Throwable)var6_10);
            this.b(this.getString(h.e.error_cant_open_eventserver_output_file), true);
            return false;
        }
        catch (IOException var5_11) {
            com.cygery.utilities.a.c(q, (Throwable)var5_11);
            this.b(this.getString(h.e.error_extracting_eventserver), true);
            return false;
        }
    }

    @Override
    public void a_(boolean bl) {
        this.l();
    }

    @Override
    public void b() {
        PreferenceManager.setDefaultValues((Context)this.c, (String)"repetitouch_prefs", (int)0, (int)h.f.preferences, (boolean)false);
        this.k = (a)((Object)this.getLastNonConfigurationInstance());
        if (this.k == null) {
            this.k = new a(this);
            this.k.execute((Object[])new Void[0]);
            return;
        }
        this.k.a(this);
    }

    protected void b(String string, boolean bl) {
        if (this.p) {
            bl = false;
        }
        this.l.add((Object)new Pair((Object)string, (Object)bl));
    }

    @Override
    public String c() {
        return q;
    }

    protected void c(String string, boolean bl) {
        this.k();
        com.cygery.utilities.a.a(q, "fatal error: " + string);
        AlertDialog.Builder builder = new AlertDialog.Builder((Context)this);
        builder.setMessage((CharSequence)string);
        builder.setPositiveButton(h.e.label_exit, new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n2) {
                dialogInterface.cancel();
            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener(){

            public void onCancel(DialogInterface dialogInterface) {
                d.this.l();
            }
        });
        if (bl) {
            builder.setNeutralButton(h.e.title_send_report, new DialogInterface.OnClickListener(){

                public void onClick(DialogInterface dialogInterface, int n2) {
                    com.cygery.utilities.a.a(d.this.c, d.this);
                }
            });
        }
        builder.create().show();
    }

    protected abstract void d();

    protected void d(String string) {
        this.m.add((Object)string);
    }

    protected void e(String string) {
        this.k();
        com.cygery.utilities.a.a(q, "message: " + string);
        AlertDialog.Builder builder = new AlertDialog.Builder((Context)this);
        builder.setMessage((CharSequence)string);
        builder.setPositiveButton(h.e.label_ok, new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n2) {
                dialogInterface.cancel();
            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener(){

            public void onCancel(DialogInterface dialogInterface) {
                d.this.l();
            }
        });
        builder.create().show();
    }

    protected boolean e() {
        boolean bl = true;
        if (!this.e.setExecutable(bl)) {
            this.b(this.getString(h.e.error_could_not_make_eventserver_executable), bl);
            bl = false;
        }
        return bl;
    }

    protected boolean f() {
        return this.e.canExecute();
    }

    protected boolean g() {
        return this.e.exists();
    }

    protected void h() {
        for (Pair pair : this.l) {
            this.c((String)pair.first, (Boolean)pair.second);
        }
    }

    protected void i() {
        Iterator iterator = this.m.iterator();
        while (iterator.hasNext()) {
            this.e((String)iterator.next());
        }
    }

    public void j() {
        this.finish();
    }

    protected void k() {
        this.h = 1 + this.h;
    }

    protected void l() {
        if (this.h > 0) {
            this.h = -1 + this.h;
        }
        if (this.h == 0) {
            this.finish();
        }
    }

    protected void m() {
        final c c2 = c.a((Context)this, "repetitouch_prefs", 0);
        if (c2.c()) {
            this.k();
            TextView textView = new TextView((Context)this);
            int n2 = (int)(0.5 + 16.0 * ((double)this.getResources().getDisplayMetrics().densityDpi / 160.0));
            textView.setPadding(n2, n2 / 2, n2, n2 / 2);
            textView.setTextSize(18.0f);
            TypedValue typedValue = new TypedValue();
            if (this.getTheme() != null) {
                this.getTheme().resolveAttribute(16842806, typedValue, true);
            }
            textView.setTextColor(this.getResources().getColorStateList(typedValue.resourceId));
            AlertDialog.Builder builder = new AlertDialog.Builder((Context)this);
            textView.setText((CharSequence)Html.fromHtml((String)this.getString(h.e.text_please_rate_this_app)));
            textView.setMovementMethod(LinkMovementMethod.getInstance());
            builder.setView((View)textView);
            builder.setTitle(h.e.title_please_rate);
            builder.setPositiveButton(h.e.label_rate_now, new DialogInterface.OnClickListener(){

                public void onClick(DialogInterface dialogInterface, int n2) {
                    Intent intent = new Intent("android.intent.action.VIEW");
                    intent.setData(Uri.parse((String)("market://details?id=" + d.this.getPackageName())));
                    d.this.startActivity(intent);
                    c2.e();
                    d.this.l();
                }
            });
            builder.setNeutralButton(h.e.label_ask_later, new DialogInterface.OnClickListener(){

                public void onClick(DialogInterface dialogInterface, int n2) {
                    c2.d();
                    d.this.l();
                }
            });
            builder.setNegativeButton(h.e.label_never, new DialogInterface.OnClickListener(){

                public void onClick(DialogInterface dialogInterface, int n2) {
                    c2.e();
                    d.this.l();
                }
            });
            builder.setOnCancelListener(new DialogInterface.OnCancelListener(){

                public void onCancel(DialogInterface dialogInterface) {
                    c2.d();
                    d.this.l();
                }
            });
            builder.show();
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.c = this;
        com.cygery.utilities.a.a(this.c);
        com.cygery.utilities.a.a(q, "STARTED");
        this.p = false;
        if (HfM5AFD8kd7A.QSsQwUGLp((Context)this) != null) {
            int n2 = 2 & HfM5AFD8kd7A.QSsQwUGLp((Context)this).flags;
            boolean bl = false;
            if (n2 != 0) {
                bl = true;
            }
            b = bl;
        }
        this.j = new com.cygery.utilities.d((Context)this, (d.a)this, "repetitouch_prefs");
        this.j.a(this.getString(h.e.eula_version));
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.j != null) {
            this.j.a();
        }
    }

    public Object onRetainNonConfigurationInstance() {
        if (this.k != null) {
            this.k.a();
        }
        return this.k;
    }

    protected class a
    extends AsyncTask<Void, Void, Boolean> {
        static final /* synthetic */ boolean g;
        d a;
        SharedPreferences b;
        SharedPreferences.Editor c;
        ProgressDialog d;
        boolean e;
        boolean f;

        /*
         * Enabled aggressive block sorting
         */
        static {
            boolean bl = !d.class.desiredAssertionStatus();
            g = bl;
        }

        public a(d d3) {
            this.a(d3);
            this.b = d3.getSharedPreferences("repetitouch_prefs", 0);
            this.c = this.b.edit();
            this.e = this.b.getBoolean("noLoadingScreen", false);
            this.f = this.b.getBoolean("firstLaunch", true);
        }

        /*
         * Exception decompiling
         */
        protected /* varargs */ Boolean a(Void ... var1) {
            // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
            // java.util.ConcurrentModificationException
            // java.util.LinkedList$ReverseLinkIterator.next(LinkedList.java:217)
            // org.benf.cfr.reader.bytecode.analysis.structured.statement.Block.extractLabelledBlocks(Block.java:212)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement$LabelledBlockExtractor.transform(Op04StructuredStatement.java:485)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.transform(Op04StructuredStatement.java:639)
            // org.benf.cfr.reader.bytecode.analysis.structured.statement.StructuredDo.transformStructuredChildren(StructuredDo.java:53)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement$LabelledBlockExtractor.transform(Op04StructuredStatement.java:487)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.transform(Op04StructuredStatement.java:639)
            // org.benf.cfr.reader.bytecode.analysis.structured.statement.Block.transformStructuredChildren(Block.java:378)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement$LabelledBlockExtractor.transform(Op04StructuredStatement.java:487)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.transform(Op04StructuredStatement.java:639)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.insertLabelledBlocks(Op04StructuredStatement.java:649)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:816)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:220)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:165)
            // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:91)
            // org.benf.cfr.reader.entities.Method.analyse(Method.java:354)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:751)
            // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:664)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:747)
            // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:683)
            // org.benf.cfr.reader.Main.doJar(Main.java:128)
            // com.njlabs.showjava.processor.JavaExtractor$1.run(JavaExtractor.java:100)
            // java.lang.Thread.run(Thread.java:818)
            throw new IllegalStateException("Decompilation failed");
        }

        void a() {
            this.a = null;
            this.b();
        }

        void a(d d2) {
            this.a = d2;
            this.d = new ProgressDialog((Context)d2);
            this.d.setMessage((CharSequence)d.this.getString(h.e.text_starting_up));
            this.d.setOnCancelListener(new DialogInterface.OnCancelListener(){

                public void onCancel(DialogInterface dialogInterface) {
                    a.this.cancel(false);
                }
            });
            if (this.getStatus() == AsyncTask.Status.RUNNING) {
                this.d.show();
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        protected void a(Boolean bl) {
            if (this.a == null) {
                com.cygery.utilities.a.b(q, "InitAsyncTask: onPostExecute(): activity is null, skipping method");
                return;
            }
            try {
                d.this.i();
                d.this.h();
            }
            catch (WindowManager.BadTokenException var2_2) {
                com.cygery.utilities.a.b(q, (Throwable)var2_2);
            }
            if (bl.booleanValue()) {
                d.this.m();
                this.a.d();
            } else {
                d.this.l();
            }
            this.b();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        void b() {
            if (this.d != null && this.d.isShowing()) {
                try {
                    this.d.dismiss();
                }
                catch (IllegalArgumentException var1_1) {
                    com.cygery.utilities.a.b(q, "IllegalArgumentException on ProgressDialog.dismiss()");
                }
            }
            this.d = null;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        protected void b(Boolean bl) {
            if (this.a == null) {
                com.cygery.utilities.a.b(q, "InitAsyncTask: onCancelled(): activity is null, skipping method");
                return;
            }
            try {
                d.this.i();
                d.this.h();
            }
            catch (WindowManager.BadTokenException var2_2) {
                com.cygery.utilities.a.b(q, (Throwable)var2_2);
            }
            d.this.l();
            this.b();
        }

        protected /* synthetic */ Object doInBackground(Object[] arrobject) {
            return this.a((Void[])arrobject);
        }

        protected /* synthetic */ void onCancelled(Object object) {
            this.b((Boolean)object);
        }

        protected /* synthetic */ void onPostExecute(Object object) {
            this.a((Boolean)object);
        }

        protected void onPreExecute() {
            d.this.k();
            if (!this.e) {
                this.d.show();
            }
        }

    }

}

