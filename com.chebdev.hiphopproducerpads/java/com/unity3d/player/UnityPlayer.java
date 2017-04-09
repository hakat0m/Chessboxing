package com.unity3d.player;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ProgressBar;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

public class UnityPlayer extends FrameLayout {
    public static Activity currentActivity = null;
    private static boolean j;
    b a = new b();
    f b = null;
    private boolean c = false;
    private boolean d = true;
    private h e = new h();
    private final ConcurrentLinkedQueue f = new ConcurrentLinkedQueue();
    private BroadcastReceiver g = null;
    private ContextWrapper h;
    private SurfaceView i;
    private boolean k;
    private Bundle l = new Bundle();
    private i m;
    private boolean n = false;
    private ProgressBar o = null;
    private Runnable p = new Runnable(this) {
        final /* synthetic */ UnityPlayer a;

        {
            this.a = r1;
        }

        public final void run() {
            int k = this.a.nativeActivityIndicatorStyle();
            if (k >= 0) {
                if (this.a.o == null) {
                    this.a.o = new ProgressBar(this.a.h, null, new int[]{16842874, 16843401, 16842873, 16843400}[k]);
                    this.a.o.setIndeterminate(true);
                    this.a.o.setLayoutParams(new LayoutParams(-2, -2, 51));
                    this.a.addView(this.a.o);
                }
                this.a.o.setVisibility(0);
                this.a.bringChildToFront(this.a.o);
            }
        }
    };
    private Runnable q = new Runnable(this) {
        final /* synthetic */ UnityPlayer a;

        {
            this.a = r1;
        }

        public final void run() {
            if (this.a.o != null) {
                this.a.o.setVisibility(8);
                this.a.removeView(this.a.o);
                this.a.o = null;
            }
        }
    };

    class AnonymousClass4 extends BroadcastReceiver {
        final /* synthetic */ UnityPlayer a;

        public void onReceive(Context context, Intent intent) {
            this.a.b();
        }
    }

    private abstract class c implements Runnable {
        final /* synthetic */ UnityPlayer e;

        private c(UnityPlayer unityPlayer) {
            this.e = unityPlayer;
        }

        public abstract void a();

        public final void run() {
            if (!this.e.isFinishing()) {
                a();
            }
        }
    }

    enum a {
        PAUSE,
        RESUME,
        QUIT,
        FOCUS_GAINED,
        FOCUS_LOST,
        NEXT_FRAME
    }

    private class b extends Thread {
        Handler a;
        boolean b;
        final /* synthetic */ UnityPlayer c;

        private b(UnityPlayer unityPlayer) {
            this.c = unityPlayer;
            this.b = false;
        }

        private void a(a aVar) {
            Message.obtain(this.a, 2269, aVar).sendToTarget();
        }

        public final void a() {
            a(a.QUIT);
        }

        public final void a(boolean z) {
            a(z ? a.FOCUS_GAINED : a.FOCUS_LOST);
        }

        public final void b() {
            a(a.RESUME);
        }

        public final void c() {
            a(a.PAUSE);
        }

        public final void run() {
            setName("UnityMain");
            Looper.prepare();
            this.a = new Handler(new Callback(this) {
                final /* synthetic */ b a;

                {
                    this.a = r1;
                }

                public final boolean handleMessage(Message message) {
                    if (message.what != 2269) {
                        return false;
                    }
                    a aVar = (a) message.obj;
                    if (aVar == a.QUIT) {
                        Looper.myLooper().quit();
                    } else if (aVar == a.RESUME) {
                        this.a.b = true;
                    } else if (aVar == a.PAUSE) {
                        this.a.b = false;
                        this.a.c.executeGLThreadJobs();
                    } else if (aVar == a.FOCUS_LOST) {
                        if (!this.a.b) {
                            this.a.c.executeGLThreadJobs();
                        }
                    } else if (aVar == a.NEXT_FRAME) {
                        this.a.c.executeGLThreadJobs();
                        if (!(this.a.c.isFinishing() || this.a.c.nativeRender())) {
                            this.a.c.b();
                        }
                    }
                    if (this.a.b) {
                        Message.obtain(this.a.a, 2269, a.NEXT_FRAME).sendToTarget();
                    }
                    return true;
                }
            });
            Looper.loop();
        }
    }

    static {
        new g().a();
        j = false;
        j = loadLibraryStatic("main");
    }

    public UnityPlayer(ContextWrapper contextWrapper) {
        super(contextWrapper);
        if (contextWrapper instanceof Activity) {
            currentActivity = (Activity) contextWrapper;
        }
        this.h = contextWrapper;
        a();
        if (e.b) {
            if (currentActivity != null) {
                e.c.a(currentActivity, new Runnable(this) {
                    final /* synthetic */ UnityPlayer a;

                    {
                        this.a = r1;
                    }

                    public final void run() {
                        this.a.a(new Runnable(this) {
                            final /* synthetic */ AnonymousClass1 a;

                            {
                                this.a = r1;
                            }

                            public final void run() {
                                this.a.a.e.d();
                                this.a.a.e();
                            }
                        });
                    }
                });
            } else {
                this.e.d();
            }
        }
        a(this.h.getApplicationInfo());
        if (h.c()) {
            initJni(contextWrapper);
            nativeFile(this.h.getPackageCodePath());
            g();
            this.i = new SurfaceView(contextWrapper);
            this.i.getHolder().setFormat(2);
            this.i.getHolder().addCallback(new SurfaceHolder.Callback(this) {
                final /* synthetic */ UnityPlayer a;

                {
                    this.a = r1;
                }

                public final void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
                    this.a.a(0, surfaceHolder.getSurface());
                }

                public final void surfaceCreated(SurfaceHolder surfaceHolder) {
                    this.a.a(0, surfaceHolder.getSurface());
                }

                public final void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                    this.a.a(0, null);
                }
            });
            this.i.setFocusable(true);
            this.i.setFocusableInTouchMode(true);
            addView(this.i);
            this.k = false;
            nativeInitWWW(WWW.class);
            nativeInitWebRequest(UnityWebRequest.class);
            h();
            this.a.start();
            return;
        }
        AlertDialog create = new Builder(this.h).setTitle("Failure to initialize!").setPositiveButton("OK", new OnClickListener(this) {
            final /* synthetic */ UnityPlayer a;

            {
                this.a = r1;
            }

            public final void onClick(DialogInterface dialogInterface, int i) {
                this.a.b();
            }
        }).setMessage("Your hardware does not support this application, sorry!").create();
        create.setCancelable(false);
        create.show();
    }

    public static void UnitySendMessage(String str, String str2, String str3) {
        if (h.c()) {
            nativeUnitySendMessage(str, str2, str3);
        } else {
            c.Log(5, "Native libraries not loaded - dropping message for " + str + "." + str2);
        }
    }

    private static String a(String str) {
        byte[] digest;
        int i = 0;
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            FileInputStream fileInputStream = new FileInputStream(str);
            long length = new File(str).length();
            fileInputStream.skip(length - Math.min(length, 65558));
            byte[] bArr = new byte[1024];
            for (int i2 = 0; i2 != -1; i2 = fileInputStream.read(bArr)) {
                instance.update(bArr, 0, i2);
            }
            digest = instance.digest();
        } catch (FileNotFoundException e) {
            digest = null;
        } catch (IOException e2) {
            digest = null;
        } catch (NoSuchAlgorithmException e3) {
            digest = null;
        }
        if (digest == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        while (i < digest.length) {
            stringBuffer.append(Integer.toString((digest[i] & 255) + 256, 16).substring(1));
            i++;
        }
        return stringBuffer.toString();
    }

    private void a() {
        try {
            File file = new File(this.h.getPackageCodePath(), "assets/bin/Data/settings.xml");
            InputStream fileInputStream = file.exists() ? new FileInputStream(file) : this.h.getAssets().open("bin/Data/settings.xml");
            XmlPullParserFactory newInstance = XmlPullParserFactory.newInstance();
            newInstance.setNamespaceAware(true);
            XmlPullParser newPullParser = newInstance.newPullParser();
            newPullParser.setInput(fileInputStream, null);
            String str = null;
            String str2 = null;
            for (int eventType = newPullParser.getEventType(); eventType != 1; eventType = newPullParser.next()) {
                if (eventType == 2) {
                    str2 = newPullParser.getName();
                    String str3 = str;
                    for (int i = 0; i < newPullParser.getAttributeCount(); i++) {
                        if (newPullParser.getAttributeName(i).equalsIgnoreCase("name")) {
                            str3 = newPullParser.getAttributeValue(i);
                        }
                    }
                    str = str3;
                } else if (eventType == 3) {
                    str2 = null;
                } else if (eventType == 4 && str != null) {
                    if (str2.equalsIgnoreCase("integer")) {
                        this.l.putInt(str, Integer.parseInt(newPullParser.getText()));
                    } else if (str2.equalsIgnoreCase("string")) {
                        this.l.putString(str, newPullParser.getText());
                    } else if (str2.equalsIgnoreCase("bool")) {
                        this.l.putBoolean(str, Boolean.parseBoolean(newPullParser.getText()));
                    } else if (str2.equalsIgnoreCase("float")) {
                        this.l.putFloat(str, Float.parseFloat(newPullParser.getText()));
                    }
                    str = null;
                }
            }
        } catch (Exception e) {
            c.Log(6, "Unable to locate player settings. " + e.getLocalizedMessage());
            b();
        }
    }

    private void a(int i, Surface surface) {
        if (!this.c) {
            b(0, surface);
        }
    }

    private static void a(ApplicationInfo applicationInfo) {
        if (j && NativeLoader.load(applicationInfo.nativeLibraryDir)) {
            h.a();
        }
    }

    private void a(c cVar) {
        if (!isFinishing()) {
            b((Runnable) cVar);
        }
    }

    private static String[] a(Context context) {
        String packageName = context.getPackageName();
        Vector vector = new Vector();
        try {
            int i = context.getPackageManager().getPackageInfo(packageName, 0).versionCode;
            if (Environment.getExternalStorageState().equals("mounted")) {
                File file = new File(Environment.getExternalStorageDirectory().toString() + "/Android/obb/" + packageName);
                if (file.exists()) {
                    if (i > 0) {
                        String str = file + File.separator + "main." + i + "." + packageName + ".obb";
                        if (new File(str).isFile()) {
                            vector.add(str);
                        }
                    }
                    if (i > 0) {
                        packageName = file + File.separator + "patch." + i + "." + packageName + ".obb";
                        if (new File(packageName).isFile()) {
                            vector.add(packageName);
                        }
                    }
                }
            }
            String[] strArr = new String[vector.size()];
            vector.toArray(strArr);
            return strArr;
        } catch (NameNotFoundException e) {
            return new String[0];
        }
    }

    private void b() {
        if ((this.h instanceof Activity) && !((Activity) this.h).isFinishing()) {
            ((Activity) this.h).finish();
        }
    }

    private void b(Runnable runnable) {
        if (!h.c()) {
            return;
        }
        if (Thread.currentThread() == this.a) {
            runnable.run();
        } else {
            this.f.add(runnable);
        }
    }

    private boolean b(int i, Surface surface) {
        if (!h.c()) {
            return false;
        }
        nativeRecreateGfxState(i, surface);
        return true;
    }

    private void c() {
        reportSoftInputStr(null, 1, true);
        if (this.e.g()) {
            if (h.c()) {
                final Semaphore semaphore = new Semaphore(0);
                if (isFinishing()) {
                    b(new Runnable(this) {
                        final /* synthetic */ UnityPlayer b;

                        public final void run() {
                            this.b.d();
                            semaphore.release();
                        }
                    });
                } else {
                    b(new Runnable(this) {
                        final /* synthetic */ UnityPlayer b;

                        public final void run() {
                            if (this.b.nativePause()) {
                                this.b.k = true;
                                this.b.d();
                                semaphore.release(2);
                                return;
                            }
                            semaphore.release();
                        }
                    });
                }
                try {
                    if (!semaphore.tryAcquire(4, TimeUnit.SECONDS)) {
                        c.Log(5, "Timeout while trying to pause the Unity Engine.");
                    }
                } catch (InterruptedException e) {
                    c.Log(5, "UI thread got interrupted while trying to pause the Unity Engine.");
                }
                if (semaphore.drainPermits() > 0) {
                    quit();
                }
            }
            this.e.c(false);
            this.e.b(true);
            this.a.c();
        }
    }

    private void d() {
        nativeDone();
    }

    private void e() {
        if (this.e.f()) {
            this.e.c(true);
            if (h.c()) {
                g();
            }
            b(new Runnable(this) {
                final /* synthetic */ UnityPlayer a;

                {
                    this.a = r1;
                }

                public final void run() {
                    this.a.nativeResume();
                }
            });
            this.a.b();
        }
    }

    private static void f() {
        if (!h.c()) {
            return;
        }
        if (NativeLoader.unload()) {
            h.b();
            return;
        }
        throw new UnsatisfiedLinkError("Unable to unload libraries from libmain.so");
    }

    private void g() {
        if (this.l.getBoolean("useObb")) {
            for (String str : a(this.h)) {
                String a = a(str);
                if (this.l.getBoolean(a)) {
                    nativeFile(str);
                }
                this.l.remove(a);
            }
        }
    }

    private void h() {
        if (this.h instanceof Activity) {
            ((Activity) this.h).getWindow().setFlags(1024, 1024);
        }
    }

    private final native void initJni(Context context);

    protected static boolean loadLibraryStatic(String str) {
        try {
            System.loadLibrary(str);
            return true;
        } catch (UnsatisfiedLinkError e) {
            c.Log(6, "Unable to find " + str);
            return false;
        } catch (Exception e2) {
            c.Log(6, "Unknown error " + e2);
            return false;
        }
    }

    private final native int nativeActivityIndicatorStyle();

    private final native void nativeDone();

    private final native void nativeFile(String str);

    private final native void nativeFocusChanged(boolean z);

    private final native void nativeInitWWW(Class cls);

    private final native void nativeInitWebRequest(Class cls);

    private final native boolean nativeInjectEvent(InputEvent inputEvent);

    private final native boolean nativePause();

    private final native void nativeRecreateGfxState(int i, Surface surface);

    private final native boolean nativeRender();

    private final native void nativeResume();

    private final native void nativeSetInputCanceled(boolean z);

    private final native void nativeSetInputString(String str);

    private final native void nativeSoftInputClosed();

    private static native void nativeUnitySendMessage(String str, String str2, String str3);

    final void a(Runnable runnable) {
        if (this.h instanceof Activity) {
            ((Activity) this.h).runOnUiThread(runnable);
        } else {
            c.Log(5, "Not running Unity from an Activity; ignored...");
        }
    }

    public void configurationChanged(Configuration configuration) {
        if (this.i instanceof SurfaceView) {
            this.i.getHolder().setSizeFromLayout();
        }
        if (this.m != null) {
            this.m.updateVideoLayout();
        }
    }

    protected void disableLogger() {
        c.a = true;
    }

    public boolean displayChanged(int i, Surface surface) {
        if (i == 0) {
            this.c = surface != null;
            a(new Runnable(this) {
                final /* synthetic */ UnityPlayer a;

                {
                    this.a = r1;
                }

                public final void run() {
                    if (this.a.c) {
                        this.a.removeView(this.a.i);
                    } else {
                        this.a.addView(this.a.i);
                    }
                }
            });
        }
        return b(i, surface);
    }

    protected void executeGLThreadJobs() {
        while (true) {
            Runnable runnable = (Runnable) this.f.poll();
            if (runnable != null) {
                runnable.run();
            } else {
                return;
            }
        }
    }

    public Bundle getSettings() {
        return this.l;
    }

    protected int getSplashMode() {
        return this.l.getInt("splash_mode");
    }

    public View getView() {
        return this;
    }

    protected void hideSoftInput() {
        final Runnable anonymousClass6 = new Runnable(this) {
            final /* synthetic */ UnityPlayer a;

            {
                this.a = r1;
            }

            public final void run() {
                if (this.a.b != null) {
                    this.a.b.dismiss();
                    this.a.b = null;
                }
            }
        };
        if (e.a) {
            a(new c(this) {
                final /* synthetic */ UnityPlayer b;

                public final void a() {
                    this.b.a(anonymousClass6);
                }
            });
        } else {
            a(anonymousClass6);
        }
    }

    public void init(int i, boolean z) {
    }

    public boolean injectEvent(InputEvent inputEvent) {
        return nativeInjectEvent(inputEvent);
    }

    protected boolean isFinishing() {
        if (!this.k) {
            boolean z = (this.h instanceof Activity) && ((Activity) this.h).isFinishing();
            this.k = z;
            if (!z) {
                return false;
            }
        }
        return true;
    }

    protected void kill() {
        Process.killProcess(Process.myPid());
    }

    protected boolean loadLibrary(String str) {
        return loadLibraryStatic(str);
    }

    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        return injectEvent(motionEvent);
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        return injectEvent(keyEvent);
    }

    public boolean onKeyMultiple(int i, int i2, KeyEvent keyEvent) {
        return injectEvent(keyEvent);
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        return injectEvent(keyEvent);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return injectEvent(motionEvent);
    }

    public void pause() {
        if (this.m != null) {
            this.n = this.m.a();
            if (!this.n) {
                this.m.pause();
                return;
            }
            return;
        }
        c();
    }

    public void quit() {
        this.k = true;
        if (!this.e.e()) {
            pause();
        }
        this.a.a();
        try {
            this.a.join(4000);
        } catch (InterruptedException e) {
            this.a.interrupt();
        }
        if (this.g != null) {
            this.h.unregisterReceiver(this.g);
        }
        this.g = null;
        if (h.c()) {
            removeAllViews();
        }
        kill();
        f();
    }

    protected void reportSoftInputStr(final String str, final int i, final boolean z) {
        if (i == 1) {
            hideSoftInput();
        }
        a(new c(this) {
            final /* synthetic */ UnityPlayer d;

            public final void a() {
                if (z) {
                    this.d.nativeSetInputCanceled(true);
                } else if (str != null) {
                    this.d.nativeSetInputString(str);
                }
                if (i == 1) {
                    this.d.nativeSoftInputClosed();
                }
            }
        });
    }

    public void resume() {
        this.e.b(false);
        if (this.m == null) {
            e();
        } else if (!this.n) {
            this.m.start();
        }
    }

    protected void setSoftInputStr(final String str) {
        a(new Runnable(this) {
            final /* synthetic */ UnityPlayer b;

            public final void run() {
                if (this.b.b != null && str != null) {
                    this.b.b.a(str);
                }
            }
        });
    }

    protected void showSoftInput(String str, int i, boolean z, boolean z2, boolean z3, boolean z4, String str2) {
        final UnityPlayer unityPlayer = this;
        final String str3 = str;
        final int i2 = i;
        final boolean z5 = z;
        final boolean z6 = z2;
        final boolean z7 = z3;
        final boolean z8 = z4;
        final String str4 = str2;
        a(new Runnable(this) {
            final /* synthetic */ UnityPlayer i;

            public final void run() {
                this.i.b = new f(this.i.h, unityPlayer, str3, i2, z5, z6, z7, str4);
                this.i.b.show();
            }
        });
    }

    protected boolean showVideoPlayer(String str, int i, int i2, int i3, boolean z, int i4, int i5) {
        final Semaphore semaphore = new Semaphore(0);
        final AtomicInteger atomicInteger = new AtomicInteger(-1);
        final String str2 = str;
        final int i6 = i;
        final int i7 = i2;
        final int i8 = i3;
        final boolean z2 = z;
        final int i9 = i4;
        final int i10 = i5;
        a(new Runnable(this) {
            final /* synthetic */ UnityPlayer j;

            public final void run() {
                if (this.j.m != null) {
                    c.Log(5, "Video already playing");
                    atomicInteger.set(2);
                    semaphore.release();
                    return;
                }
                this.j.m = new i(this.j.h, str2, i6, i7, i8, z2, (long) i9, (long) i10, new com.unity3d.player.i.a(this) {
                    final /* synthetic */ AnonymousClass10 a;

                    {
                        this.a = r1;
                    }

                    public final void a(int i) {
                        atomicInteger.set(i);
                        if (i == 3) {
                            if (this.a.j.i.getParent() == null) {
                                this.a.j.addView(this.a.j.i);
                            }
                            if (this.a.j.m != null) {
                                this.a.j.m.destroyPlayer();
                                this.a.j.removeView(this.a.j.m);
                                this.a.j.m = null;
                            }
                            this.a.j.resume();
                        }
                        if (i != 0) {
                            semaphore.release();
                        }
                    }
                });
                this.j.addView(this.j.m);
            }
        });
        boolean z3 = false;
        try {
            semaphore.acquire();
            z3 = atomicInteger.get() != 2;
        } catch (InterruptedException e) {
        }
        c.Log(2, "Video returned " + (z3 ? "OK" : "FAIL"));
        if (!z3) {
            c.Log(4, "Video failed");
            if (this.m != null) {
                a(new Runnable(this) {
                    final /* synthetic */ UnityPlayer a;

                    {
                        this.a = r1;
                    }

                    public final void run() {
                        if (this.a.i.getParent() == null) {
                            this.a.addView(this.a.i);
                        }
                        if (this.a.m != null) {
                            this.a.m.destroyPlayer();
                            this.a.removeView(this.a.m);
                            this.a.m = null;
                        }
                        this.a.resume();
                    }
                });
            }
        } else if (this.m != null) {
            a(new Runnable(this) {
                final /* synthetic */ UnityPlayer a;

                {
                    this.a = r1;
                }

                public final void run() {
                    if (this.a.m != null) {
                        this.a.c();
                        this.a.m.requestFocus();
                        this.a.removeView(this.a.i);
                    }
                }
            });
        }
        return z3;
    }

    protected void startActivityIndicator() {
        a(this.p);
    }

    protected void stopActivityIndicator() {
        a(this.q);
    }

    public void windowFocusChanged(final boolean z) {
        this.e.a(z);
        if (z && this.b != null) {
            reportSoftInputStr(null, 1, false);
        }
        b(new Runnable(this) {
            final /* synthetic */ UnityPlayer b;

            public final void run() {
                this.b.nativeFocusChanged(z);
            }
        });
        this.a.a(z);
        e();
    }
}
