/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.app.ProgressDialog
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnCancelListener
 *  android.content.DialogInterface$OnClickListener
 *  android.content.Intent
 *  android.content.pm.ActivityInfo
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.ResolveInfo
 *  android.net.Uri
 *  android.os.AsyncTask
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Parcelable
 *  android.util.Log
 *  android.widget.Toast
 *  java.io.File
 *  java.io.FileInputStream
 *  java.io.FileNotFoundException
 *  java.io.FileOutputStream
 *  java.io.IOException
 *  java.lang.CharSequence
 *  java.lang.IllegalArgumentException
 *  java.lang.InterruptedException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.Thread
 *  java.lang.Throwable
 *  java.lang.Void
 *  java.text.SimpleDateFormat
 *  java.util.Date
 *  java.util.List
 *  java.util.concurrent.BlockingQueue
 *  java.util.concurrent.LinkedBlockingQueue
 *  java.util.logging.FileHandler
 *  java.util.logging.Formatter
 *  java.util.logging.Level
 *  java.util.logging.LogRecord
 */
package com.cygery.utilities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;
import com.cygery.utilities.g;
import com.cygery.utilities.h;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import t4X4QwIV.HfM5AFD8kd7A;

public class a {
    protected static final String a = a.class.getName();
    protected static boolean b = false;
    protected static Context c;
    protected static int d;
    protected static BlockingQueue<LogRecord> e;
    protected static c f;
    protected static SimpleDateFormat g;
    protected static String h;
    protected static String i;
    protected static String j;
    protected static final Object k;
    protected static boolean l;
    protected static boolean m;

    static {
        k = new Object();
        m = false;
    }

    public static void a() {
        if (f != null) {
            f.a();
        }
        m = false;
    }

    public static void a(Context context) {
        a.a(context, 256);
    }

    /*
     * Enabled aggressive block sorting
     */
    @SuppressLint(value={"SimpleDateFormat"})
    public static void a(Context context, int n2) {
        if (!m) {
            c = context;
            d = n2;
            h = c.getString(h.d.report_path);
            if (HfM5AFD8kd7A.QSsQwUGLp(c) != null) {
                boolean bl = (2 & HfM5AFD8kd7A.QSsQwUGLp((Context)a.c).flags) != 0;
                b = bl;
            }
            i = (Object)c.getFilesDir() + File.separator + h;
            j = (Object)c.getExternalCacheDir() + File.separator + h;
            g = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
            e = new LinkedBlockingQueue();
            l = false;
            f = new c();
            f.start();
            m = true;
        }
    }

    public static void a(Context context, d d2) {
        new b(context, d2, null, 1).execute((Object[])new Void[0]);
    }

    public static void a(Context context, d d2, String string) {
        new b(context, d2, string, 2).execute((Object[])new Void[0]);
    }

    public static void a(String string, String string2) {
        if (b) {
            Log.v((String)string, (String)string2);
        }
        a.a(Level.INFO, string, string2);
    }

    public static void a(String string, Throwable throwable) {
        if (b) {
            Log.v((String)string, (String)"", (Throwable)throwable);
        }
        a.a(Level.INFO, string, throwable);
    }

    private static void a(Level level, String string, String string2) {
        try {
            if (e != null) {
                e.put((Object)new LogRecord(level, string + ": " + string2));
            }
            return;
        }
        catch (InterruptedException var3_3) {
            return;
        }
    }

    private static void a(Level level, String string, Throwable throwable) {
        a.a(level, string, Log.getStackTraceString((Throwable)throwable));
    }

    static /* synthetic */ void b(Context context) {
        a.c(context);
    }

    public static void b(String string, String string2) {
        Log.w((String)string, (String)string2);
        a.a(Level.WARNING, string, string2);
    }

    public static void b(String string, Throwable throwable) {
        Log.w((String)string, (String)"", (Throwable)throwable);
        a.a(Level.WARNING, string, throwable);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static boolean b(Context context, String string) {
        try {
            int n2;
            FileInputStream fileInputStream = new FileInputStream(new File(i));
            FileOutputStream fileOutputStream = new FileOutputStream(new File(string));
            byte[] arrby = new byte[1024];
            while ((n2 = fileInputStream.read(arrby)) > 0) {
                fileOutputStream.write(arrby, 0, n2);
            }
            fileInputStream.close();
            fileOutputStream.close();
            return true;
        }
        catch (FileNotFoundException var5_6) {
            var5_6.printStackTrace();
            return false;
        }
        catch (IOException var4_7) {
            var4_7.printStackTrace();
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void c() {
        Object object;
        Object object2 = object = k;
        synchronized (object2) {
            boolean bl;
            while (!(bl = l)) {
                try {
                    k.wait();
                }
                catch (InterruptedException var10_2) {}
            }
        }
        l = false;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(i));
            for (int i2 = 1; i2 >= 0; --i2) {
                FileInputStream fileInputStream;
                try {
                    int n2;
                    fileInputStream = new FileInputStream(new File(i + "." + i2));
                    byte[] arrby = new byte[1024];
                    while ((n2 = fileInputStream.read(arrby)) > 0) {
                        fileOutputStream.write(arrby, 0, n2);
                    }
                }
                catch (FileNotFoundException var7_8) {
                    continue;
                }
                fileInputStream.close();
            }
            fileOutputStream.close();
            return;
        }
        catch (IOException var5_10) {
            var5_10.printStackTrace();
            return;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static void c(final Context var0) {
        var1_1 = new Intent("android.intent.action.SEND");
        var1_1.setType("message/rfc822");
        for (ResolveInfo var17_3 : var0.getPackageManager().queryIntentActivities(var1_1, 65536)) {
            if (!"com.google.android.gm".equals((Object)var17_3.activityInfo.packageName)) continue;
            var1_1.setClassName(var17_3.activityInfo.packageName, var17_3.activityInfo.name);
            var4_4 = true;
lbl7: // 2 sources:
            do {
                var5_5 = new String[]{var0.getString(h.d.dev_email)};
                var1_1.putExtra("android.intent.extra.EMAIL", var5_5);
                var1_1.putExtra("android.intent.extra.SUBJECT", var0.getString(h.d.app_name) + " " + var0.getString(h.d.string_bug_report));
                var1_1.putExtra("android.intent.extra.TEXT", var0.getString(h.d.report_body));
                if (var4_4 && Build.VERSION.SDK_INT >= 14) {
                    var1_1.putExtra("android.intent.extra.STREAM", (Parcelable)Uri.parse((String)("content://" + g.a + File.separator + a.h)));
                    var1_1.addFlags(1);
                    var0.startActivity(var1_1);
                    return;
                }
                var9_6 = new AlertDialog.Builder(var0);
                var9_6.setMessage(h.d.message_unable_to_attach_the_report_directly);
                var9_6.setTitle(h.d.title_confirm_file_copy_to_external_storage);
                var9_6.setPositiveButton(17039370, new DialogInterface.OnClickListener(){

                    /*
                     * Enabled aggressive block sorting
                     * Enabled unnecessary exception pruning
                     * Enabled aggressive exception aggregation
                     */
                    public void onClick(DialogInterface dialogInterface, int n2) {
                        try {
                            int n3;
                            FileInputStream fileInputStream = new FileInputStream(new File(a.i));
                            FileOutputStream fileOutputStream = new FileOutputStream(new File(a.j));
                            byte[] arrby = new byte[1024];
                            while ((n3 = fileInputStream.read(arrby)) > 0) {
                                fileOutputStream.write(arrby, 0, n3);
                            }
                            fileInputStream.close();
                            fileOutputStream.close();
                            var1_1.putExtra("android.intent.extra.STREAM", (Parcelable)Uri.parse((String)("file://" + a.j)));
                            var1_1.addFlags(1);
                            var0.startActivity(Intent.createChooser((Intent)var1_1, (CharSequence)var0.getString(h.d.title_send_report)));
                            return;
                        }
                        catch (IOException var5_7) {
                            var5_7.printStackTrace();
                            return;
                        }
                    }
                });
                var9_6.setNegativeButton(17039360, null);
                var9_6.show();
                return;
                break;
            } while (true);
        }
        var4_4 = false;
        ** while (true)
    }

    public static void c(String string, String string2) {
        Log.e((String)string, (String)string2);
        a.a(Level.SEVERE, string, string2);
    }

    public static void c(String string, Throwable throwable) {
        Log.e((String)string, (String)"", (Throwable)throwable);
        a.a(Level.SEVERE, string, throwable);
    }

    protected static class a
    extends Formatter {
        protected a() {
        }

        public String format(LogRecord logRecord) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(a.g.format(new Date(logRecord.getMillis()))).append(" ");
            stringBuilder.append(logRecord.getLevel().getName()).append(": ");
            stringBuilder.append(this.formatMessage(logRecord)).append("\n");
            return stringBuilder.toString();
        }
    }

    protected static class b
    extends AsyncTask<Void, Void, Void> {
        private Context a;
        private d b;
        private String c;
        private int d;
        private ProgressDialog e;
        private boolean f;

        public b(Context context, d d2, String string, int n2) {
            this.a = context;
            this.b = d2;
            this.c = string;
            this.d = n2;
            this.e = new ProgressDialog(context);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        protected /* varargs */ Void a(Void ... arrvoid) {
            if (this.b != null) {
                a.a(this.b.c(), this.b.a(this.a));
            }
            a.e.add((Object)new LogRecord(Level.OFF, ""));
            a.c();
            switch (this.d) {
                default: {
                    do {
                        return null;
                        break;
                    } while (true);
                }
                case 2: 
            }
            this.f = a.b(this.a, this.c);
            return null;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        protected void a(Void var1) {
            switch (this.d) {
                case 2: {
                    if (this.f) {
                        var3_2 = this.a;
                        var4_3 = this.a;
                        var5_4 = h.d.text_report_saved_as;
                        var6_5 = new Object[]{this.c};
                        Toast.makeText((Context)var3_2, (CharSequence)var4_3.getString(var5_4, var6_5), (int)1).show();
                        ** break;
                    }
                    Toast.makeText((Context)this.a, (CharSequence)this.a.getString(h.d.text_save_failed), (int)1).show();
                }
lbl11: // 3 sources:
                default: {
                    ** GOTO lbl15
                }
                case 1: 
            }
            a.b(this.a);
lbl15: // 2 sources:
            if (this.e != null && this.e.isShowing()) {
                try {
                    this.e.dismiss();
                }
                catch (IllegalArgumentException var2_6) {
                    a.b(a.a, "IllegalArgumentException on ProgressDialog.dismiss()");
                }
            }
            if (this.b == null) return;
            this.b.a_(true);
        }

        protected void b(Void void_) {
            if (this.e.isShowing()) {
                this.e.dismiss();
            }
            if (this.b != null) {
                this.b.a_(false);
            }
            Toast.makeText((Context)this.a, (CharSequence)this.a.getString(h.d.text_cancelled), (int)0).show();
        }

        protected /* synthetic */ Object doInBackground(Object[] arrobject) {
            return this.a((Void[])arrobject);
        }

        protected /* synthetic */ void onCancelled(Object object) {
            this.b((Void)object);
        }

        protected /* synthetic */ void onPostExecute(Object object) {
            this.a((Void)object);
        }

        protected void onPreExecute() {
            this.e.setMessage((CharSequence)this.a.getString(h.d.text_preparing_report));
            this.e.setOnCancelListener(new DialogInterface.OnCancelListener(){

                public void onCancel(DialogInterface dialogInterface) {
                    b.this.cancel(true);
                }
            });
            this.e.show();
        }

    }

    protected static class c
    extends Thread {
        protected volatile boolean a;
        protected FileHandler b;

        public c() {
            try {
                this.b = new FileHandler(a.i, 1024 * a.d, 2, true);
                this.b.setFormatter((Formatter)new a());
                this.a = true;
                return;
            }
            catch (IOException var1_1) {
                this.a = false;
                return;
            }
        }

        public void a() {
            c c2 = this;
            synchronized (c2) {
                this.a = false;
                return;
            }
        }

        /*
         * Exception decompiling
         */
        public void run() {
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
            // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:664)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:747)
            // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:683)
            // org.benf.cfr.reader.Main.doJar(Main.java:128)
            // com.njlabs.showjava.processor.JavaExtractor$1.run(JavaExtractor.java:100)
            // java.lang.Thread.run(Thread.java:818)
            throw new IllegalStateException("Decompilation failed");
        }
    }

    public static interface d {
        public String a(Context var1);

        public void a_(boolean var1);

        public String c();
    }

}

