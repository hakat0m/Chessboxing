/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.app.Notification
 *  android.app.NotificationManager
 *  android.app.PendingIntent
 *  android.app.Service
 *  android.content.BroadcastReceiver
 *  android.content.ComponentName
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.content.SharedPreferences$OnSharedPreferenceChangeListener
 *  android.content.pm.ApplicationInfo
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Handler
 *  android.os.IBinder
 *  android.provider.Settings
 *  android.provider.Settings$SettingNotFoundException
 *  android.provider.Settings$System
 *  android.support.v4.app.ae
 *  android.support.v4.app.ae$c
 *  android.support.v4.app.ae$d
 *  android.support.v4.app.ae$q
 *  android.telephony.TelephonyManager
 *  android.util.DisplayMetrics
 *  android.util.TypedValue
 *  android.view.Display
 *  android.view.LayoutInflater
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.View$OnClickListener
 *  android.view.View$OnLongClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.WindowManager
 *  android.view.WindowManager$LayoutParams
 *  android.widget.ImageButton
 *  android.widget.ImageView
 *  android.widget.ImageView$ScaleType
 *  android.widget.LinearLayout
 *  android.widget.RelativeLayout
 *  android.widget.Toast
 *  java.io.DataOutputStream
 *  java.io.IOException
 *  java.io.OutputStream
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.IllegalArgumentException
 *  java.lang.Integer
 *  java.lang.InterruptedException
 *  java.lang.NumberFormatException
 *  java.lang.Object
 *  java.lang.Process
 *  java.lang.Runnable
 *  java.lang.Runtime
 *  java.lang.String
 *  java.lang.Thread
 *  java.lang.Thread$State
 *  java.lang.Throwable
 */
package com.cygery.repetitouch;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.ae;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.cygery.repetitouch.h;
import com.cygery.utilities.c;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public abstract class g
extends Service
implements SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String aa = g.class.getName();
    protected int A;
    protected boolean B;
    protected boolean C;
    protected boolean D;
    protected int E = -1;
    protected int F;
    protected int G;
    protected boolean H = false;
    protected boolean I;
    protected boolean J;
    protected int K;
    protected int L;
    protected int M;
    protected boolean N;
    protected boolean O;
    protected boolean P;
    protected boolean Q;
    protected Class R;
    protected Class S;
    protected Class T;
    protected SharedPreferences U;
    protected boolean V;
    protected float W;
    protected int X;
    protected boolean Y;
    protected final View.OnLongClickListener Z;
    protected Context a;
    protected Handler b;
    protected BroadcastReceiver c;
    protected b d;
    protected a e;
    protected NotificationManager f;
    protected ae.d g;
    protected c h;
    protected LinearLayout i;
    protected View j;
    protected View k;
    protected View l;
    protected View m;
    protected WindowManager n;
    protected Display o;
    protected WindowManager.LayoutParams p;
    protected WindowManager.LayoutParams q;
    protected WindowManager.LayoutParams r;
    protected WindowManager.LayoutParams s;
    protected WindowManager.LayoutParams t;
    protected int u;
    protected int v;
    protected int w;
    protected int x;
    protected int y;
    protected int z;

    public g() {
        this.Z = new View.OnLongClickListener(){

            public boolean onLongClick(View view) {
                g.this.Y = true;
                return true;
            }
        };
    }

    protected void A() {
        this.startActivity(new Intent(this.getApplicationContext(), this.T).addFlags(335544320).putExtra("message", "showInputLoopCountDialog"));
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void a() {
        if (this.O) {
            ((ImageButton)this.i.findViewById(h.b.button_record)).setImageResource(h.a.bg_stop);
        } else {
            ((ImageButton)this.i.findViewById(h.b.button_record)).setImageResource(h.a.bg_record);
        }
        if (this.P) {
            ((ImageButton)this.i.findViewById(h.b.button_replay)).setImageResource(h.a.bg_stop);
            return;
        }
        ((ImageButton)this.i.findViewById(h.b.button_replay)).setImageResource(h.a.bg_replay);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void a(Intent intent) {
        String string = intent.getStringExtra("message");
        boolean bl = intent.getBooleanExtra("silent", false);
        String string2 = intent.getStringExtra("path");
        if ("recordstarted".equals((Object)string)) {
            this.c();
            return;
        }
        if ("replaystarted".equals((Object)string)) {
            this.d();
            return;
        }
        if ("replaystartedpaused".equals((Object)string)) {
            this.d();
            return;
        }
        if ("recordstopped".equals((Object)string)) {
            this.e();
            return;
        }
        if ("replaystopped".equals((Object)string)) {
            this.f();
            return;
        }
        if ("loadsuccess".equals((Object)string)) {
            if (bl) return;
            {
                Toast.makeText((Context)this.a, (CharSequence)this.a.getString(h.e.info_load_successful, new Object[]{string2}), (int)1).show();
                return;
            }
        }
        if ("loadfail".equals((Object)string)) {
            if (bl) return;
            {
                Toast.makeText((Context)this.a, (CharSequence)this.a.getString(h.e.info_load_error, new Object[]{string2}), (int)1).show();
                return;
            }
        }
        if ("savesuccess".equals((Object)string)) {
            if (bl) return;
            {
                Toast.makeText((Context)this.a, (CharSequence)this.a.getString(h.e.info_save_successful, new Object[]{string2}), (int)1).show();
                return;
            }
        }
        if ("savefail".equals((Object)string)) {
            if (bl) return;
            {
                Toast.makeText((Context)this.a, (CharSequence)this.a.getString(h.e.info_save_error, new Object[]{string2}), (int)1).show();
                return;
            }
        }
        if ("hide".equals((Object)string)) {
            this.u();
            return;
        }
        if ("show".equals((Object)string)) {
            this.v();
            return;
        }
        if ("togglevisibility".equals((Object)string)) {
            this.w();
            return;
        }
        if ("toggledrag".equals((Object)string)) {
            this.x();
            return;
        }
        if ("togglerecordbutton".equals((Object)string)) {
            this.y();
            return;
        }
        if ("togglevisibilitynext".equals((Object)string)) {
            this.z();
            return;
        }
        if (!"setbrightness".equals((Object)string) && !"resetbrightness".equals((Object)string)) {
            com.cygery.utilities.a.b(aa, "unknown message received: " + string);
            return;
        }
        int n2 = intent.getIntExtra("brightness", -1);
        String string3 = this.U.getString("backlightPowerPath", null);
        String string4 = this.U.getString("backlightBrightnessPath", null);
        com.cygery.utilities.a.a(aa, "backlightPowerPath: " + string3);
        com.cygery.utilities.a.a(aa, "backlightBrightnessPath: " + string4);
        if (n2 == 0 && (string3 != null || string4 != null)) {
            if (string.equals((Object)"setbrightness")) {
                try {
                    this.E = Settings.System.getInt((ContentResolver)this.getContentResolver(), (String)"screen_brightness");
                }
                catch (Settings.SettingNotFoundException var10_8) {
                    this.E = -1;
                }
                com.cygery.utilities.a.a(aa, "savedBrightness: " + this.E);
                com.cygery.utilities.a.a(aa, "set brightness to zero and power off backlight");
                if (string4 != null) {
                    this.b(string4, 0);
                }
                if (string3 == null || this.U.getBoolean("useAlternativeBacklightControl", false)) return;
                {
                    this.b(string3);
                    return;
                }
            }
            com.cygery.utilities.a.a(aa, "power on backlight");
            if (string3 != null && !this.U.getBoolean("useAlternativeBacklightControl", false)) {
                this.a(string3);
            }
            if (this.E <= 0 || string4 == null) return;
            {
                com.cygery.utilities.a.a(aa, "reset saved brightness: " + this.E);
                this.b(string4, this.E);
                return;
            }
        }
        if (string.equals((Object)"setbrightness")) {
            this.t.screenBrightness = (float)n2 / 100.0f;
            com.cygery.utilities.a.a(aa, "setting brightness: " + this.t.screenBrightness);
            try {
                this.n.updateViewLayout(this.m, (ViewGroup.LayoutParams)this.t);
                return;
            }
            catch (IllegalArgumentException var9_9) {
                com.cygery.utilities.a.c(aa, (Throwable)var9_9);
                return;
            }
        }
        this.t.screenBrightness = -1.0f;
        com.cygery.utilities.a.a(aa, "resetting brightness");
        try {
            this.n.updateViewLayout(this.m, (ViewGroup.LayoutParams)this.t);
            return;
        }
        catch (IllegalArgumentException var8_10) {
            com.cygery.utilities.a.c(aa, (Throwable)var8_10);
            return;
        }
    }

    protected void a(a a2) {
        this.e = a2;
    }

    protected void a(String string) {
        this.a(string, 0);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected void a(String string, int n2) {
        if (string == null) {
            com.cygery.utilities.a.c(aa, "setBacklightPower: backlightPowerPath == null");
            do {
                return;
                break;
            } while (true);
        }
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"su"});
            DataOutputStream dataOutputStream = new DataOutputStream(process.getOutputStream());
            dataOutputStream.writeBytes("echo " + n2 + " > " + string + "/bl_power" + " ; exit\n");
            dataOutputStream.flush();
            process.waitFor();
            dataOutputStream.close();
            process.destroy();
            switch (process.exitValue()) {
                case 0: {
                    return;
                }
            }
            com.cygery.utilities.a.c(aa, "setBacklightPower exit: " + process.exitValue());
            return;
        }
        catch (IOException var4_5) {
            com.cygery.utilities.a.c(aa, (Throwable)var4_5);
            return;
        }
        catch (InterruptedException var3_6) {
            com.cygery.utilities.a.c(aa, (Throwable)var3_6);
            return;
        }
    }

    protected void a(boolean bl) {
        if (bl) {
            this.G = this.F;
            this.F = 0;
        }
        if (this.F > 0) {
            this.F = -1 + this.F;
        }
        this.t();
    }

    protected boolean a(View view, MotionEvent motionEvent) {
        if (0.0f <= motionEvent.getX() && 0.0f <= motionEvent.getY() && (float)view.getWidth() > motionEvent.getX() && (float)view.getHeight() > motionEvent.getY()) {
            return true;
        }
        return false;
    }

    protected void b(String string) {
        this.a(string, 1);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected void b(String string, int n2) {
        if (string == null) {
            com.cygery.utilities.a.c(aa, "setBacklightBrightness: backlightBrightnessPath == null");
            do {
                return;
                break;
            } while (true);
        }
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"su"});
            DataOutputStream dataOutputStream = new DataOutputStream(process.getOutputStream());
            dataOutputStream.writeBytes("echo " + n2 + " > " + string + "/brightness" + " ; exit\n");
            dataOutputStream.flush();
            process.waitFor();
            dataOutputStream.close();
            process.destroy();
            switch (process.exitValue()) {
                case 0: {
                    return;
                }
            }
            com.cygery.utilities.a.c(aa, "setBacklightBrightness exit: " + process.exitValue());
            return;
        }
        catch (IOException var4_5) {
            com.cygery.utilities.a.c(aa, (Throwable)var4_5);
            return;
        }
        catch (InterruptedException var3_6) {
            com.cygery.utilities.a.c(aa, (Throwable)var3_6);
            return;
        }
    }

    protected boolean b() {
        if (this.O || this.P) {
            return true;
        }
        return false;
    }

    protected void c() {
        this.O = true;
        this.a(true);
        this.a();
    }

    protected void d() {
        this.P = true;
        this.a();
    }

    protected void e() {
        this.O = false;
        this.F = this.G;
        this.t();
        this.a();
    }

    protected void f() {
        this.P = false;
        this.a();
    }

    protected void g() {
        this.startService(new Intent((Context)this, this.S).setAction("eventmanagerservice").putExtra("message", "stoprecordbyservice"));
        this.startService(new Intent((Context)this, this.S).setAction("eventmanagerservice").putExtra("message", "stopreplay"));
    }

    protected void h() {
        this.i.findViewById(h.b.button_record).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if (g.this.N) {
                    Toast.makeText((Context)g.this, (int)h.e.info_phone_in_use_no_action_allowed, (int)0).show();
                    return;
                }
                if (g.this.P) {
                    g.this.startService(new Intent((Context)g.this, g.this.S).setAction("eventmanagerservice").putExtra("message", "stopreplay"));
                    ((ImageButton)g.this.i.findViewById(h.b.button_replay)).setImageResource(h.a.bg_replay);
                }
                if (g.this.O) {
                    g.this.startService(new Intent((Context)g.this, g.this.S).setAction("eventmanagerservice").putExtra("message", "stoprecordbyuser"));
                    return;
                }
                g.this.startService(new Intent((Context)g.this, g.this.S).setAction("eventmanagerservice").putExtra("message", "startrecord"));
            }
        });
        this.i.findViewById(h.b.button_replay).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if (g.this.N) {
                    Toast.makeText((Context)g.this, (int)h.e.info_phone_in_use_no_action_allowed, (int)0).show();
                    return;
                }
                if (g.this.O) {
                    g.this.startService(new Intent((Context)g.this, g.this.S).setAction("eventmanagerservice").putExtra("message", "stoprecordbyuser"));
                }
                if (g.this.P) {
                    g.this.startService(new Intent((Context)g.this, g.this.S).setAction("eventmanagerservice").putExtra("message", "stopreplay"));
                    return;
                }
                g.this.startService(new Intent((Context)g.this, g.this.S).setAction("eventmanagerservice").putExtra("message", "startreplay"));
            }
        });
    }

    protected void i() {
        if (this.M < 10) {
            this.M = 10;
        }
        if (this.M > 100) {
            this.M = 100;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected int j() {
        Resources resources = Resources.getSystem();
        if (resources == null) {
            com.cygery.utilities.a.c(aa, "Resources.getSystem() == null");
            return 0;
        } else {
            int n2 = resources.getIdentifier("status_bar_height", "dimen", "android");
            if (n2 <= 0) return 0;
            return resources.getDimensionPixelSize(n2);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected int k() {
        int n2 = 0;
        Resources resources = Resources.getSystem();
        if (resources == null) {
            com.cygery.utilities.a.c(aa, "Resources.getSystem() == null");
            return n2;
        } else {
            int n3 = this.getResources().getConfiguration().orientation == 2 ? resources.getIdentifier("navigation_bar_height_landscape", "dimen", "android") : resources.getIdentifier("navigation_bar_height", "dimen", "android");
            n2 = 0;
            if (n3 > 0 && (n2 = resources.getDimensionPixelSize(n3)) != 0) return n2;
            {
                com.cygery.utilities.a.c(aa, "getNavigationBarHeight returns 0");
                return n2;
            }
        }
    }

    protected void l() {
        this.i = (LinearLayout)LayoutInflater.from((Context)this).inflate(h.c.panel, null);
        int n2 = (int)TypedValue.applyDimension((int)1, (float)this.M, (DisplayMetrics)this.getResources().getDisplayMetrics());
        int n3 = this.i.getChildCount();
        for (int i2 = 0; i2 < n3; ++i2) {
            View view = this.i.getChildAt(i2);
            if (!(view instanceof ImageButton)) continue;
            ImageButton imageButton = (ImageButton)view;
            ViewGroup.LayoutParams layoutParams = imageButton.getLayoutParams();
            if (layoutParams != null) {
                layoutParams.height = n2;
                layoutParams.width = n2;
            }
            imageButton.setLayoutParams(layoutParams);
            imageButton.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        this.o();
        this.p();
        this.m();
        this.h();
        this.i.setDrawingCacheEnabled(true);
        if (!this.B) {
            this.i.setVisibility(4);
        }
        this.q();
        this.r();
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void m() {
        int n2 = 1;
        int n3 = this.getResources().getConfiguration().orientation == 2 ? n2 : 0;
        if (n3 != 0) {
            n2 = 0;
        }
        this.i.setOrientation(n2);
    }

    protected void n() {
        if (this.i != null && this.i.getLayoutParams() != null) {
            this.i.measure(View.MeasureSpec.makeMeasureSpec((int)0, (int)0), View.MeasureSpec.makeMeasureSpec((int)0, (int)0));
            this.z = this.i.getMeasuredHeight();
            this.A = this.i.getMeasuredWidth();
            this.r();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @SuppressLint(value={"NewApi"})
    protected void o() {
        if (this.W > 1.0f) {
            this.W = 1.0f;
        }
        if (this.W < 0.25f) {
            this.W = 0.25f;
        }
        int[] arrn = new int[]{h.b.button_drag, h.b.button_menu, h.b.button_replay, h.b.button_stop_replay, h.b.button_record, h.b.button_add_marker};
        int n2 = arrn.length;
        int n3 = 0;
        while (n3 < n2) {
            int n4 = arrn[n3];
            ImageButton imageButton = (ImageButton)this.i.findViewById(n4);
            if (Build.VERSION.SDK_INT >= 11) {
                imageButton.setAlpha(this.W);
            } else {
                imageButton.setAlpha((int)(255.0f * this.W));
            }
            ++n3;
        }
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onCreate() {
        super.onCreate();
        com.cygery.utilities.a.a((Context)this);
        this.a = this;
        this.n = (WindowManager)this.getSystemService("window");
        this.o = this.n.getDefaultDisplay();
        this.f = (NotificationManager)this.getSystemService("notification");
        this.Y = false;
        this.P = false;
        this.O = false;
        this.U = this.getSharedPreferences("repetitouch_prefs", 0);
        this.U.registerOnSharedPreferenceChangeListener((SharedPreferences.OnSharedPreferenceChangeListener)this);
        com.cygery.utilities.a.a(aa, "onCreate()");
        try {
            this.M = this.U.getInt("panelIconSizeInt", Integer.parseInt((String)this.U.getString("panelIconSize", "35")));
        }
        catch (NumberFormatException var1_3) {
            com.cygery.utilities.a.c(aa, "NumberFormatException: " + this.U.getInt("panelIconSizeInt", 35));
            com.cygery.utilities.a.c(aa, (Throwable)var1_3);
            this.M = 35;
        }
        this.i();
        this.V = this.U.getBoolean("ignoreCallState", false);
        this.W = (float)this.U.getInt("panelIconOpacity", 100) / 100.0f;
        this.X = this.U.getInt("panelBackgroundColor", -16777216);
        this.I = true;
        this.K = this.j();
        this.J = true;
        this.L = this.o.getRotation();
        this.F = 1;
        this.B = false;
        this.C = true;
        this.D = true;
        this.b = new Handler();
        this.d = new b(this, null);
        this.h = c.a((Context)this, "repetitouch_prefs", 0);
        this.h.a();
        this.p = new WindowManager.LayoutParams(-2, -2, 2010, this.v, -3);
        this.p.gravity = this.u;
        this.q = new WindowManager.LayoutParams(-1, 0, 2006, 8, -3);
        this.q.gravity = 83;
        this.r = new WindowManager.LayoutParams(0, -1, 2006, 8, -3);
        this.r.gravity = 51;
        this.s = new WindowManager.LayoutParams(0, -1, 2006, 256, -3);
        this.s.gravity = 51;
        this.t = new WindowManager.LayoutParams(-2, -2, 2010, this.w, -3);
        this.t.gravity = 51;
        this.t.x = 0;
        this.t.y = 0;
        this.t.height = 0;
        this.t.width = 0;
        this.j = new RelativeLayout((Context)this);
        this.k = new RelativeLayout((Context)this);
        this.l = LayoutInflater.from((Context)this).inflate(h.c.fullscreen_dummy, null);
        this.m = new RelativeLayout((Context)this);
        this.l();
        this.i.setLayoutParams((ViewGroup.LayoutParams)this.p);
        this.i.measure(View.MeasureSpec.makeMeasureSpec((int)0, (int)0), View.MeasureSpec.makeMeasureSpec((int)0, (int)0));
        this.i.setVisibility(4);
        this.z = this.i.getMeasuredHeight();
        this.A = this.i.getMeasuredWidth();
        this.n.addView(this.l, (ViewGroup.LayoutParams)this.s);
        this.n.addView(this.k, (ViewGroup.LayoutParams)this.r);
        this.n.addView(this.j, (ViewGroup.LayoutParams)this.q);
        this.a(new a(){

            @Override
            public void a() {
                g.this.b.postDelayed(new Runnable(){

                    public void run() {
                        com.cygery.utilities.a.a(aa, "OnSystemBarVisibilityChangeListener: status bar:" + g.this.I + " navigation bar:" + g.this.J);
                        g.this.r();
                        g.this.s();
                        try {
                            g.this.n.updateViewLayout((View)g.this.i, (ViewGroup.LayoutParams)g.this.p);
                            return;
                        }
                        catch (IllegalArgumentException var1_1) {
                            com.cygery.utilities.a.c(aa, (Throwable)var1_1);
                            return;
                        }
                    }
                }, 250);
            }

        });
        this.c = new BroadcastReceiver(){

            /*
             * Enabled aggressive block sorting
             */
            public void onReceive(Context context, Intent intent) {
                if ("android.intent.action.CONFIGURATION_CHANGED".equals((Object)intent.getAction())) {
                    int n2 = g.this.o.getRotation();
                    com.cygery.utilities.a.a(aa, "config changed. old=" + g.this.L + " new=" + n2);
                    if (n2 == g.this.L) return;
                    {
                        if ((g.this.L - n2) % 2 != 0) {
                            g.this.A = g.this.i.getHeight();
                            g.this.z = g.this.i.getWidth();
                        }
                        g.this.L = n2;
                        g.this.b.postDelayed(new Runnable(){

                            /*
                             * Enabled aggressive block sorting
                             * Enabled unnecessary exception pruning
                             * Enabled aggressive exception aggregation
                             */
                            public void run() {
                                try {
                                    g.this.n.removeView((View)g.this.i);
                                }
                                catch (Exception var1_1) {
                                    com.cygery.utilities.a.b(aa, "failed to remove panelView");
                                }
                                try {
                                    g.this.n.removeView(g.this.m);
                                }
                                catch (Exception var2_2) {
                                    com.cygery.utilities.a.b(aa, "failed to remove screenBrightnessDummyView");
                                }
                                g.this.l();
                                g.this.a();
                                g.this.s();
                                g.this.n.addView((View)g.this.i, (ViewGroup.LayoutParams)g.this.p);
                                g.this.n.addView(g.this.m, (ViewGroup.LayoutParams)g.this.t);
                            }
                        }, 50);
                        return;
                    }
                } else {
                    if (!"android.intent.action.SCREEN_OFF".equals((Object)intent.getAction())) return;
                    {
                        com.cygery.utilities.a.a(aa, "ACTION_SCREEN_OFF received. stopping record/replay");
                        g.this.g();
                        return;
                    }
                }
            }

        };
        this.b.postDelayed(new Runnable(){

            public void run() {
                g.this.l();
                g.this.a();
                g.this.s();
                g.this.n.addView((View)g.this.i, (ViewGroup.LayoutParams)g.this.p);
                g.this.n.addView(g.this.m, (ViewGroup.LayoutParams)g.this.t);
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.intent.action.CONFIGURATION_CHANGED");
                intentFilter.addAction("android.intent.action.SCREEN_OFF");
                g.this.registerReceiver(g.this.c, intentFilter);
                g.this.d.start();
            }
        }, 50);
        String string = "";
        if (this.getApplicationInfo() != null) {
            string = this.getText(this.getApplicationInfo().labelRes);
        }
        this.g = new ae.d((Context)this).a((CharSequence)string).b((CharSequence)this.getString(h.e.info_notification)).a((ae.q)new ae.c().a((CharSequence)this.getString(h.e.info_notification))).a(h.a.ic_stat_notify).a(0).a(PendingIntent.getActivity((Context)this.a, (int)1, (Intent)new Intent(this.a, this.R).addFlags(268435456), (int)0));
        if (Build.VERSION.SDK_INT >= 16) {
            int n2 = this.U.getInt("notificationPriority", 0);
            this.g.b(n2);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    public void onDestroy() {
        var1_1 = 0;
        super.onDestroy();
        com.cygery.utilities.a.a(g.aa, "onDestroy() start");
        if (this.H) {
            this.h.b();
            var8_2 = this.getSharedPreferences("repetitouch_prefs", 0).edit();
            var8_2.putInt("startGlobalX", this.x);
            var8_2.putInt("startGlobalY", this.y);
            var8_2.apply();
        }
        this.d.a();
        try {
            this.unregisterReceiver(this.c);
        }
        catch (IllegalArgumentException var2_3) {
            ** continue;
        }
lbl13: // 2 sources:
        do {
            this.U.unregisterOnSharedPreferenceChangeListener((SharedPreferences.OnSharedPreferenceChangeListener)this);
            var3_4 = new View[]{this.i, this.j, this.k, this.l, this.m};
            var4_5 = var3_4.length;
            block5 : while (var1_1 < var4_5) {
                var6_7 = var3_4[var1_1];
                try {
                    this.n.removeView(var6_7);
                }
                catch (Exception var7_6) {
                    com.cygery.utilities.a.b(g.aa, "failed to remove view: " + var6_7.getId());
                    ** continue;
                }
lbl24: // 2 sources:
                do {
                    ++var1_1;
                    continue block5;
                    break;
                } while (true);
            }
            this.stopService(new Intent((Context)this, this.S));
            com.cygery.utilities.a.a(g.aa, "onDestroy() end");
            com.cygery.utilities.a.a();
            return;
            break;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String string) {
        if ("ignoreCallState".equals((Object)string)) {
            this.V = sharedPreferences.getBoolean(string, false);
            if (this.V) {
                this.N = false;
                return;
            }
            switch (((TelephonyManager)this.getSystemService("phone")).getCallState()) {
                default: {
                    return;
                }
                case 1: 
                case 2: 
            }
            this.N = true;
            return;
        }
        if ("panelIconOpacity".equals((Object)string)) {
            this.W = (float)sharedPreferences.getInt(string, 100) / 100.0f;
            this.o();
            this.p();
            return;
        }
        if ("panelBackgroundColor".equals((Object)string)) {
            this.X = sharedPreferences.getInt(string, -16777216);
            this.o();
            this.p();
            return;
        }
        if (!"notificationPriority".equals((Object)string)) return;
        if (Build.VERSION.SDK_INT < 16) return;
        int n2 = this.U.getInt("notificationPriority", 0);
        this.g.b(n2);
        this.stopForeground(true);
        this.startForeground(1, this.g.a());
    }

    public int onStartCommand(Intent intent, int n2, int n3) {
        int n4 = 1;
        com.cygery.utilities.a.a(aa, "onStartCommand()");
        if (intent != null && !this.H) {
            this.x = intent.getIntExtra("x", 0);
            this.y = intent.getIntExtra("y", 0);
            this.H = n4;
            n4 = 3;
        }
        return n4;
    }

    protected void p() {
        this.i.setBackgroundColor(this.X);
    }

    protected abstract void q();

    /*
     * Enabled aggressive block sorting
     */
    protected void r() {
        if (this.p.x < 0) {
            this.p.x = 0;
        }
        if (this.p.x > this.j.getWidth() - this.A) {
            this.p.x = this.j.getWidth() - this.A;
        }
        if (this.I) {
            if (this.p.y < this.K) {
                this.p.y = this.K;
            }
        } else if (this.p.y < 0) {
            this.p.y = 0;
        }
        if (this.J) {
            if (this.p.y <= this.l.getHeight() + this.k() - this.z) return;
            {
                this.p.y = this.l.getHeight() + this.k() - this.z;
                return;
            }
        } else {
            if (this.p.y <= this.l.getHeight() - this.z) return;
            {
                this.p.y = this.l.getHeight() - this.z;
                return;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void s() {
        if (this.L == 0) {
            this.x = this.p.y;
            this.y = this.j.getWidth() - this.p.x - this.A;
            return;
        } else {
            if (this.L == 2) {
                this.x = this.l.getHeight() - this.p.y - this.z;
                if (this.J) {
                    this.x += this.k();
                }
                this.y = this.p.x;
                return;
            }
            if (this.L == 1) {
                this.x = this.p.x;
                this.y = this.p.y;
                return;
            }
            this.x = this.j.getWidth() - this.p.x - this.A;
            this.y = this.l.getHeight() - this.p.y - this.z;
            if (!this.J) return;
            {
                this.y += this.k();
                return;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void t() {
        if (this.F > 0) {
            this.i.setVisibility(4);
            this.B = false;
            return;
        } else {
            if (this.F != 0) return;
            {
                this.i.setVisibility(0);
                this.B = true;
                return;
            }
        }
    }

    protected void u() {
        if (!this.O) {
            this.F = 1 + this.F;
            this.t();
            return;
        }
        com.cygery.utilities.a.a(aa, "hidePanel while recording! counter=" + this.F);
    }

    protected void v() {
        this.a(false);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void w() {
        if (this.F > 0) {
            this.F = 0;
        } else if (!this.O) {
            this.F = 1;
        } else {
            com.cygery.utilities.a.a(aa, "togglePanel while recording! counter=" + this.F);
        }
        this.t();
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void x() {
        this.C = !this.C;
        this.a();
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void y() {
        if (this.O) {
            return;
        }
        this.D = !this.D;
        this.a();
    }

    protected void z() {
        if (this.F == 1) {
            this.F = 1 + this.F;
            return;
        }
        if (this.F == 2 && !this.O) {
            this.F = -1 + this.F;
            return;
        }
        com.cygery.utilities.a.a(aa, "togglePanelNext while recording! counter=" + this.F);
    }

    protected static interface a {
        public void a();
    }

    private class b
    extends Thread {
        final /* synthetic */ g a;
        private volatile boolean b;

        private b(g g2) {
            this.a = g2;
        }

        /* synthetic */ b(g g2,  var2_2) {
            super(g2);
        }

        public void a() {
            this.b = false;
            if (this.getState() == Thread.State.TIMED_WAITING) {
                this.interrupt();
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void run() {
            this.b = true;
            while (this.b) {
                int n2 = this.a.l.getHeight();
                int n3 = this.a.k.getHeight();
                boolean bl = this.a.I;
                boolean bl2 = this.a.J;
                if (n2 == n3) {
                    this.a.I = false;
                    g g2 = this.a;
                    boolean bl3 = n2 % 10 != 0;
                    g2.J = bl3;
                } else if (n2 - n3 == this.a.K) {
                    this.a.I = true;
                    g g3 = this.a;
                    boolean bl4 = n2 % 10 != 0;
                    g3.J = bl4;
                }
                if (bl != this.a.I || bl2 != this.a.J) {
                    this.a.e.a();
                }
                try {
                    b.sleep((long)50);
                }
                catch (InterruptedException var7_7) {
                }
            }
            return;
        }
    }

}

