/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.app.Notification
 *  android.app.PendingIntent
 *  android.content.BroadcastReceiver
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.SharedPreferences
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Handler
 *  android.support.v4.app.ae
 *  android.support.v4.app.ae$d
 *  android.text.Editable
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.util.TypedValue
 *  android.view.KeyEvent
 *  android.view.LayoutInflater
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.View$OnClickListener
 *  android.view.View$OnLongClickListener
 *  android.view.View$OnTouchListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.Window
 *  android.view.WindowManager
 *  android.view.WindowManager$LayoutParams
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.ArrayAdapter
 *  android.widget.Button
 *  android.widget.EditText
 *  android.widget.FrameLayout
 *  android.widget.ImageButton
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.ListAdapter
 *  android.widget.ListPopupWindow
 *  android.widget.ListView
 *  android.widget.PopupWindow
 *  android.widget.PopupWindow$OnDismissListener
 *  android.widget.TextView
 *  android.widget.TextView$OnEditorActionListener
 *  android.widget.Toast
 *  java.lang.AssertionError
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.IllegalArgumentException
 *  java.lang.Integer
 *  java.lang.Math
 *  java.lang.NumberFormatException
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 *  java.lang.Throwable
 *  java.util.ArrayList
 *  java.util.List
 */
package com.cygery.repetitouch.pro;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ae;
import android.text.Editable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.cygery.repetitouch.g;
import com.cygery.repetitouch.pro.EventManagerServicePro;
import com.cygery.repetitouch.pro.MenuActivityPro;
import com.cygery.repetitouch.pro.QueryBackgroundService;
import com.cygery.repetitouch.pro.SettingsActivityPro;
import com.cygery.repetitouch.pro.TranslucentActivityPro;
import java.util.ArrayList;
import java.util.List;

public class PanelServicePro
extends g
implements AdapterView.OnItemClickListener {
    private static final String aa = PanelServicePro.class.getName();
    private int ab;
    private BroadcastReceiver ac;
    private c ad;
    private boolean ae;
    private boolean af;
    private boolean ag;
    private List<b> ah;
    private List<b> ai;
    private ListPopupWindow aj;
    private ListPopupWindow ak;
    private a al;
    private a am;
    private ImageView an;
    private ImageView ao;
    private WindowManager.LayoutParams ap;
    private boolean aq;
    private boolean ar;
    private boolean as;

    private int a(ListAdapter listAdapter) {
        int n2 = View.MeasureSpec.makeMeasureSpec((int)0, (int)0);
        int n3 = 0;
        for (int i2 = 0; i2 < listAdapter.getCount(); ++i2) {
            View view = listAdapter.getView(i2, null, (ViewGroup)new FrameLayout((Context)this));
            if (view == null) continue;
            view.setLayoutParams((ViewGroup.LayoutParams)new WindowManager.LayoutParams(-2, -2));
            view.measure(n2, n2);
            n3 = Math.max((int)n3, (int)view.getMeasuredWidth());
        }
        return n3;
    }

    static /* synthetic */ int a(PanelServicePro panelServicePro, int n2) {
        panelServicePro.z = n2;
        return n2;
    }

    static /* synthetic */ WindowManager.LayoutParams aA(PanelServicePro panelServicePro) {
        return panelServicePro.p;
    }

    static /* synthetic */ WindowManager.LayoutParams aB(PanelServicePro panelServicePro) {
        return panelServicePro.p;
    }

    static /* synthetic */ LinearLayout aC(PanelServicePro panelServicePro) {
        return panelServicePro.i;
    }

    static /* synthetic */ LinearLayout aD(PanelServicePro panelServicePro) {
        return panelServicePro.i;
    }

    static /* synthetic */ LinearLayout aE(PanelServicePro panelServicePro) {
        return panelServicePro.i;
    }

    static /* synthetic */ WindowManager.LayoutParams ac(PanelServicePro panelServicePro) {
        return panelServicePro.s;
    }

    static /* synthetic */ boolean ag(PanelServicePro panelServicePro) {
        return panelServicePro.C;
    }

    static /* synthetic */ LinearLayout ah(PanelServicePro panelServicePro) {
        return panelServicePro.i;
    }

    static /* synthetic */ LinearLayout ai(PanelServicePro panelServicePro) {
        return panelServicePro.i;
    }

    static /* synthetic */ WindowManager.LayoutParams ak(PanelServicePro panelServicePro) {
        return panelServicePro.p;
    }

    static /* synthetic */ WindowManager.LayoutParams al(PanelServicePro panelServicePro) {
        return panelServicePro.p;
    }

    static /* synthetic */ Handler an(PanelServicePro panelServicePro) {
        return panelServicePro.b;
    }

    static /* synthetic */ WindowManager.LayoutParams ao(PanelServicePro panelServicePro) {
        return panelServicePro.p;
    }

    static /* synthetic */ WindowManager.LayoutParams ap(PanelServicePro panelServicePro) {
        return panelServicePro.p;
    }

    static /* synthetic */ LinearLayout aq(PanelServicePro panelServicePro) {
        return panelServicePro.i;
    }

    static /* synthetic */ WindowManager.LayoutParams ar(PanelServicePro panelServicePro) {
        return panelServicePro.p;
    }

    static /* synthetic */ WindowManager as(PanelServicePro panelServicePro) {
        return panelServicePro.n;
    }

    static /* synthetic */ LinearLayout at(PanelServicePro panelServicePro) {
        return panelServicePro.i;
    }

    static /* synthetic */ LinearLayout au(PanelServicePro panelServicePro) {
        return panelServicePro.i;
    }

    static /* synthetic */ ListPopupWindow av(PanelServicePro panelServicePro) {
        return panelServicePro.ak;
    }

    static /* synthetic */ LinearLayout aw(PanelServicePro panelServicePro) {
        return panelServicePro.i;
    }

    static /* synthetic */ Handler ay(PanelServicePro panelServicePro) {
        return panelServicePro.b;
    }

    static /* synthetic */ void az(PanelServicePro panelServicePro) {
        panelServicePro.s();
    }

    void C() {
        this.af = true;
        this.a();
    }

    void D() {
        this.af = false;
        this.a();
    }

    void E() {
        this.ag = false;
        this.a();
    }

    void F() {
        if (this.b()) {
            com.cygery.utilities.a.b(aa, "startEditorMode() while record or replay is running");
            return;
        }
        this.ag = true;
        if (!this.U.getBoolean("replayUnchecked", false)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.a);
            builder.setTitle((CharSequence)this.getString(2131165402));
            builder.setMessage((CharSequence)this.getString(2131165318));
            builder.setPositiveButton(17039370, null);
            AlertDialog alertDialog = builder.create();
            alertDialog.getWindow().setType(2003);
            alertDialog.show();
        }
        this.a();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @SuppressLint(value={"NewApi"})
    void G() {
        this.ai = new ArrayList();
        String string = this.U.getString("loopTimes", null);
        if (string != null) {
            for (String string2 : string.split("[,]+")) {
                boolean bl;
                try {
                    Integer.parseInt((String)string2);
                    bl = true;
                }
                catch (NumberFormatException var6_7) {
                    bl = false;
                }
                if (string2.equalsIgnoreCase("inf") || string2.equalsIgnoreCase("edit") || bl) {
                    com.cygery.utilities.a.a(aa, "updateLoopCountArray: adding item " + string2);
                    this.ai.add((Object)new b(this, 2130837609, string2));
                    continue;
                }
                com.cygery.utilities.a.a(aa, "updateLoopCountArray: can't add item " + string2);
            }
        }
        if (Build.VERSION.SDK_INT >= 11) {
            this.ak = new ListPopupWindow((Context)this, null, 16843520);
            this.am = new a((Context)this, 2130903094, this.ai);
            this.ak.setAdapter((ListAdapter)this.am);
            this.ak.setModal(true);
            this.ak.setOnItemClickListener((AdapterView.OnItemClickListener)this);
        }
    }

    boolean H() {
        return this.a(this.aj) | this.a(this.ak);
    }

    void I() {
        if (Build.VERSION.SDK_INT < 11) {
            ArrayList arrayList = new ArrayList(this.ai.size());
            for (b b2 : this.ai) {
                if (b2.b == 0) {
                    arrayList.add((Object)b2.c);
                    continue;
                }
                arrayList.add((Object)this.getString(b2.b));
            }
            CharSequence[] arrcharSequence = (CharSequence[])arrayList.toArray((Object[])new CharSequence[arrayList.size()]);
            this.startActivity(new Intent(this.getApplicationContext(), (Class)TranslucentActivityPro.class).addFlags(335544320).putExtra("message", "showLoopCountDialog").putExtra("items", arrcharSequence));
            return;
        }
        this.a(this.ak, this.am, this.i.findViewById(2131558629));
    }

    /*
     * Enabled aggressive block sorting
     */
    int a(View view, int n2) {
        boolean bl = this.getResources().getConfiguration().orientation == 2;
        if (bl != this.U.getBoolean("flipPanelView", false)) return this.ab;
        return - view.getHeight() - this.ab;
    }

    /*
     * Enabled aggressive block sorting
     */
    int a(View view, int n2, float f2) {
        boolean bl = this.getResources().getConfiguration().orientation == 2;
        if (bl != this.U.getBoolean("flipPanelView", false)) return this.ab;
        if (f2 + (float)this.p.x + (float)this.i.getWidth() + (float)n2 + (float)this.ab > (float)this.j.getWidth()) {
            return - n2 + this.ab;
        }
        return view.getWidth() + this.ab;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void a() {
        if (this.O) {
            if (this.ae) {
                ((ImageButton)this.i.findViewById(2131558631)).setImageResource(2130837599);
            } else {
                ((ImageButton)this.i.findViewById(2131558631)).setImageResource(2130837598);
            }
        } else {
            ((ImageButton)this.i.findViewById(2131558631)).setImageResource(2130837596);
        }
        if (this.P) {
            this.i.findViewById(2131558630).setVisibility(0);
            if (this.af) {
                ((ImageButton)this.i.findViewById(2131558629)).setImageResource(2130837597);
            } else {
                ((ImageButton)this.i.findViewById(2131558629)).setImageResource(2130837595);
            }
            this.i.findViewById(2131558631).setVisibility(8);
        } else {
            this.i.findViewById(2131558630).setVisibility(8);
            ((ImageButton)this.i.findViewById(2131558629)).setImageResource(2130837597);
            this.i.findViewById(2131558631).setVisibility(0);
        }
        if (this.Q) {
            ((ImageButton)this.i.findViewById(2131558630)).setImageResource(2130837600);
        } else {
            ((ImageButton)this.i.findViewById(2131558630)).setImageResource(2130837598);
        }
        if (this.ag) {
            this.i.findViewById(2131558630).setVisibility(0);
            this.i.findViewById(2131558632).setVisibility(0);
            this.i.findViewById(2131558631).setVisibility(8);
        } else {
            this.i.findViewById(2131558632).setVisibility(8);
        }
        if (this.C) {
            this.i.findViewById(2131558627).setEnabled(true);
        } else {
            this.i.findViewById(2131558627).setEnabled(false);
        }
        if (this.D || this.O) {
            this.i.findViewById(2131558631).setEnabled(true);
        } else {
            this.i.findViewById(2131558631).setEnabled(false);
        }
        this.n();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void a(Intent intent) {
        String string = intent.getStringExtra("message");
        if ("overridehide".equals((Object)string)) {
            if (intent.hasExtra("hidepanel")) {
                this.ar = intent.getBooleanExtra("hidepanel", true);
            }
        } else if ("startreploop".equals((Object)string)) {
            int n2 = intent.getIntExtra("loopCount", -1);
            com.cygery.utilities.a.a(aa, "starting loop replay via dialog. loopCount: " + n2);
            this.startService(new Intent((Context)this, this.S).setAction("eventmanagerservice").putExtra("message", "startreplayloop").putExtra("loopCount", n2));
            this.Q = true;
        } else if ("replaystarted".equals((Object)string)) {
            if (intent.getBooleanExtra("isLooping", false)) {
                this.Q = true;
            }
            super.a(intent);
        } else if ("replaystartedpaused".equals((Object)string)) {
            if (intent.getBooleanExtra("isLooping", false)) {
                this.Q = true;
            }
            super.a(intent);
            this.C();
        } else if ("replaypaused".equals((Object)string)) {
            this.C();
        } else if ("replayresumed".equals((Object)string)) {
            this.D();
        } else if ("appendingrecordstarted".equals((Object)string)) {
            this.ae = true;
            this.c();
        } else if ("starteditor".equals((Object)string)) {
            this.F();
        } else {
            super.a(intent);
        }
        if ("recordstarted".equals((Object)string) || "replaystarted".equals((Object)string) || "replaystartedpaused".equals((Object)string) || "recordstopped".equals((Object)string) || "replaystopped".equals((Object)string)) {
            if ("replaystartedpaused".equals((Object)string)) {
                string = "replaystarted";
            }
            this.startService(new Intent((Context)this, (Class)QueryBackgroundService.class).setAction("querybackgroundservice").putExtra("message", string));
        }
    }

    @SuppressLint(value={"NewApi"})
    void a(ListPopupWindow listPopupWindow, View view, float f2, float f3) {
        if (Build.VERSION.SDK_INT >= 11 && listPopupWindow != null && listPopupWindow.isShowing()) {
            int n2 = listPopupWindow.getWidth();
            if (Build.VERSION.SDK_INT < 23) {
                n2 -= 2 * this.ab;
            }
            int n3 = this.a(view, n2, f2);
            int n4 = this.a(view, n2);
            int n5 = (int)(f2 + ((float)this.p.x + view.getX()));
            int n6 = (int)(f3 + ((float)this.p.y + view.getY()));
            if ((float)n5 < view.getX()) {
                n5 = (int)view.getX();
            }
            if ((float)n5 > (float)this.j.getWidth() - ((float)this.A - view.getX())) {
                n5 = (int)((float)this.j.getWidth() - ((float)this.A - view.getX()));
            }
            if ((float)n6 < view.getY()) {
                n6 = (int)view.getY();
            }
            if ((float)n6 > (float)this.l.getHeight() - ((float)this.z - view.getY())) {
                n6 = (int)((float)this.l.getHeight() - ((float)this.z - view.getY()));
            }
            this.ao.setX((float)n5);
            this.ao.setY((float)n6);
            listPopupWindow.setAnchorView((View)this.ao);
            listPopupWindow.setHorizontalOffset(n3);
            listPopupWindow.setVerticalOffset(n4);
            listPopupWindow.show();
        }
    }

    @SuppressLint(value={"NewApi"})
    void a(final ListPopupWindow listPopupWindow, a a2, View view) {
        int n2 = super.a((ListAdapter)a2);
        int n3 = this.a(view, n2, 0.0f);
        int n4 = this.a(view, n2);
        int n5 = (int)((float)this.p.x + view.getX());
        int n6 = (int)((float)this.p.y + view.getY());
        this.s.width = -1;
        this.n.updateViewLayout(this.l, (ViewGroup.LayoutParams)this.s);
        listPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener(){

            public void onDismiss() {
                PanelServicePro.ac((PanelServicePro)PanelServicePro.this).width = 0;
                PanelServicePro.this.n.updateViewLayout(PanelServicePro.this.l, (ViewGroup.LayoutParams)PanelServicePro.this.s);
            }
        });
        this.ao.setX((float)n5);
        this.ao.setY((float)n6);
        listPopupWindow.setAnchorView((View)this.ao);
        listPopupWindow.setContentWidth(n2);
        listPopupWindow.setHorizontalOffset(n3);
        listPopupWindow.setVerticalOffset(n4);
        listPopupWindow.setInputMethodMode(2);
        this.b.postDelayed(new Runnable(){

            public void run() {
                listPopupWindow.show();
            }
        }, 0);
    }

    @SuppressLint(value={"NewApi"})
    boolean a(ListPopupWindow listPopupWindow) {
        if (Build.VERSION.SDK_INT >= 11 && listPopupWindow != null && listPopupWindow.isShowing()) {
            listPopupWindow.dismiss();
            return true;
        }
        return false;
    }

    @Override
    protected void c() {
        super.c();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void d() {
        super.d();
        boolean bl = !this.B;
        this.as = bl;
        com.cygery.utilities.a.a(aa, "replayStarted()");
        com.cygery.utilities.a.a(aa, "panelHiddenOnStartReplay=" + this.as);
        com.cygery.utilities.a.a(aa, "hidePanelDuringReplay=" + this.ar);
        if (this.ar && this.B) {
            this.u();
            return;
        } else {
            if (this.ar || this.B) return;
            {
                this.v();
                return;
            }
        }
    }

    @Override
    protected void e() {
        this.ae = false;
        this.ag = false;
        super.e();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void f() {
        com.cygery.utilities.a.a(aa, "replayStopped()");
        com.cygery.utilities.a.a(aa, "panelHiddenOnStartReplay=" + this.as);
        com.cygery.utilities.a.a(aa, "hidePanelDuringReplay=" + this.ar);
        if (!this.as && !this.B) {
            this.v();
        } else if (this.as && this.B) {
            this.u();
        }
        this.ar = this.U.getBoolean("hidePanelDuringReplay", false);
        this.Q = false;
        this.af = false;
        super.f();
        if (this.ag) {
            this.E();
            this.startService(new Intent((Context)this, this.S).setAction("eventmanagerservice").putExtra("message", "launchmerger"));
        }
    }

    @Override
    protected void h() {
        super.h();
        this.i.findViewById(2131558627).setOnTouchListener((View.OnTouchListener)this.ad);
        this.i.findViewById(2131558631).setOnLongClickListener(this.Z);
        this.i.findViewById(2131558631).setOnTouchListener(new View.OnTouchListener(){

            /*
             * Enabled aggressive block sorting
             */
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (PanelServicePro.this.H()) {
                    return true;
                }
                if (PanelServicePro.this.N) {
                    Toast.makeText((Context)PanelServicePro.this, (int)2131165245, (int)0).show();
                    return true;
                }
                if (PanelServicePro.this.Y && motionEvent.getAction() == 1) {
                    if (PanelServicePro.this.a(view, motionEvent)) {
                        if (PanelServicePro.this.P) {
                            PanelServicePro.this.startService(new Intent((Context)PanelServicePro.this, PanelServicePro.this.S).setAction("eventmanagerservice").putExtra("message", "stopreplay"));
                            ((ImageButton)PanelServicePro.this.i.findViewById(2131558629)).setImageResource(2130837597);
                        }
                        if (PanelServicePro.this.O) {
                            PanelServicePro.this.startService(new Intent((Context)PanelServicePro.this, PanelServicePro.this.S).setAction("eventmanagerservice").putExtra("message", "stoprecordbyuser"));
                        } else {
                            PanelServicePro.this.startService(new Intent((Context)PanelServicePro.this, PanelServicePro.this.S).setAction("eventmanagerservice").putExtra("message", "startrecordappending"));
                            PanelServicePro.this.ae = true;
                        }
                    }
                    PanelServicePro.this.Y = false;
                    view.setPressed(false);
                    return true;
                }
                return false;
            }
        });
        this.i.findViewById(2131558629).setOnLongClickListener(this.Z);
        this.i.findViewById(2131558629).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if (PanelServicePro.this.N) {
                    Toast.makeText((Context)PanelServicePro.this, (int)2131165245, (int)0).show();
                    return;
                }
                if (PanelServicePro.this.O) {
                    PanelServicePro.this.startService(new Intent((Context)PanelServicePro.this, PanelServicePro.this.S).setAction("eventmanagerservice").putExtra("message", "stoprecordbyuser"));
                }
                if (PanelServicePro.this.P) {
                    if (PanelServicePro.this.af) {
                        PanelServicePro.this.startService(new Intent((Context)PanelServicePro.this, PanelServicePro.this.S).setAction("eventmanagerservice").putExtra("message", "resumereplay"));
                        return;
                    }
                    PanelServicePro.this.startService(new Intent((Context)PanelServicePro.this, PanelServicePro.this.S).setAction("eventmanagerservice").putExtra("message", "pausereplay"));
                    return;
                }
                if (PanelServicePro.this.ag) {
                    PanelServicePro.this.startService(new Intent((Context)PanelServicePro.this, PanelServicePro.this.S).setAction("eventmanagerservice").putExtra("message", "startreplayeditor"));
                    return;
                }
                PanelServicePro.this.startService(new Intent((Context)PanelServicePro.this, PanelServicePro.this.S).setAction("eventmanagerservice").putExtra("message", "startreplay"));
            }
        });
        this.i.findViewById(2131558629).setOnTouchListener(new View.OnTouchListener(){

            /*
             * Loose catch block
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            public boolean onTouch(View view, MotionEvent motionEvent) {
                String string;
                if (PanelServicePro.this.H()) {
                    return true;
                }
                if (PanelServicePro.this.N) {
                    Toast.makeText((Context)PanelServicePro.this, (int)2131165245, (int)0).show();
                    return true;
                }
                if (PanelServicePro.this.Y && PanelServicePro.this.ag) {
                    return false;
                }
                if (!PanelServicePro.this.Y) return false;
                if (motionEvent.getAction() != 1) return false;
                if (PanelServicePro.this.a(view, motionEvent)) {
                    if (PanelServicePro.this.O) {
                        PanelServicePro.this.startService(new Intent((Context)PanelServicePro.this, PanelServicePro.this.S).setAction("eventmanagerservice").putExtra("message", "stoprecordbyuser"));
                    }
                    if (PanelServicePro.this.P) {
                        PanelServicePro.this.startService(new Intent((Context)PanelServicePro.this, PanelServicePro.this.S).setAction("eventmanagerservice").putExtra("message", "stopreplay"));
                    } else if (PanelServicePro.this.ai.size() > 1) {
                        PanelServicePro.this.I();
                    } else {
                        int n2;
                        if (PanelServicePro.this.ai.size() == 1) {
                            b b2 = (b)PanelServicePro.this.ai.get(0);
                            string = b2.b == 0 ? b2.c : PanelServicePro.this.getString(b2.b);
                        } else {
                            string = "inf";
                        }
                        if ("inf".equalsIgnoreCase(string)) {
                            n2 = -1;
                        } else {
                            int n3;
                            if ("edit".equalsIgnoreCase(string)) {
                                PanelServicePro.this.A();
                                PanelServicePro.this.Y = false;
                                view.setPressed(false);
                                return true;
                            }
                            n2 = n3 = Integer.parseInt((String)string);
                        }
                        com.cygery.utilities.a.a(aa, "starting loop replay via click. loopCount: " + n2);
                        PanelServicePro.this.startService(new Intent((Context)PanelServicePro.this, PanelServicePro.this.S).setAction("eventmanagerservice").putExtra("message", "startreplayloop").putExtra("loopCount", n2));
                        PanelServicePro.this.Q = true;
                    }
                }
                PanelServicePro.this.Y = false;
                view.setPressed(false);
                return true;
                catch (NumberFormatException numberFormatException) {
                    com.cygery.utilities.a.a(aa, "aborting start loop replay via click. loopCount: " + string);
                    return true;
                }
            }
        });
        this.i.findViewById(2131558628).setOnLongClickListener(this.Z);
        this.i.findViewById(2131558628).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 11) {
                    PanelServicePro.this.a(PanelServicePro.this.aj, PanelServicePro.this.al, view);
                    return;
                }
                PanelServicePro.this.startActivity(new Intent(PanelServicePro.this.getApplicationContext(), PanelServicePro.this.R).addFlags(268435456));
            }
        });
        this.i.findViewById(2131558628).setOnTouchListener(new View.OnTouchListener(){

            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (PanelServicePro.this.H()) {
                    return true;
                }
                if (PanelServicePro.this.Y && motionEvent.getAction() == 1) {
                    if (PanelServicePro.this.a(view, motionEvent)) {
                        PanelServicePro.this.stopSelf();
                    }
                    PanelServicePro.this.Y = false;
                    view.setPressed(false);
                    return true;
                }
                return false;
            }
        });
        this.i.findViewById(2131558630).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if (PanelServicePro.this.N) {
                    Toast.makeText((Context)PanelServicePro.this, (int)2131165245, (int)0).show();
                    return;
                }
                if (PanelServicePro.this.P) {
                    PanelServicePro.this.startService(new Intent((Context)PanelServicePro.this, PanelServicePro.this.S).setAction("eventmanagerservice").putExtra("message", "stopreplay"));
                    return;
                }
                PanelServicePro.this.E();
            }
        });
        this.i.findViewById(2131558632).setOnClickListener(new View.OnClickListener(){

            /*
             * Enabled aggressive block sorting
             */
            public void onClick(View view) {
                if (PanelServicePro.this.N) {
                    Toast.makeText((Context)PanelServicePro.this, (int)2131165245, (int)0).show();
                    return;
                } else {
                    if (!PanelServicePro.this.ag) return;
                    {
                        PanelServicePro.this.startService(new Intent((Context)PanelServicePro.this, PanelServicePro.this.S).setAction("eventmanagerservice").putExtra("message", "addmarker"));
                        return;
                    }
                }
            }
        });
        this.i.findViewById(2131558632).setOnLongClickListener(this.Z);
        this.i.findViewById(2131558632).setOnTouchListener(new View.OnTouchListener(){

            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (PanelServicePro.this.H()) {
                    return true;
                }
                if (PanelServicePro.this.N) {
                    Toast.makeText((Context)PanelServicePro.this, (int)2131165245, (int)0).show();
                    return true;
                }
                if (PanelServicePro.this.Y && motionEvent.getAction() == 1) {
                    if (PanelServicePro.this.a(view, motionEvent) && PanelServicePro.this.ag && PanelServicePro.this.af) {
                        final EditText editText = new EditText(PanelServicePro.this.a);
                        editText.setHint((CharSequence)PanelServicePro.this.a.getString(2131165224));
                        AlertDialog.Builder builder = new AlertDialog.Builder(PanelServicePro.this.a);
                        builder.setTitle(2131165257);
                        builder.setView((View)editText);
                        builder.setPositiveButton(17039370, new DialogInterface.OnClickListener(){

                            public void onClick(DialogInterface dialogInterface, int n2) {
                                PanelServicePro.this.startService(new Intent((Context)PanelServicePro.this, PanelServicePro.this.S).setAction("eventmanagerservice").putExtra("message", "addmarker").putExtra("description", String.valueOf((Object)editText.getText())));
                            }
                        });
                        builder.setNegativeButton(17039360, null);
                        final AlertDialog alertDialog = builder.create();
                        editText.setSingleLine();
                        editText.setImeOptions(6);
                        editText.setOnEditorActionListener(new TextView.OnEditorActionListener(){

                            public boolean onEditorAction(TextView textView, int n2, KeyEvent keyEvent) {
                                if (n2 == 6) {
                                    alertDialog.getButton(-1).performClick();
                                    return true;
                                }
                                return false;
                            }
                        });
                        alertDialog.getWindow().setType(2003);
                        alertDialog.getWindow().setSoftInputMode(4);
                        alertDialog.show();
                    }
                    PanelServicePro.this.Y = false;
                    view.setPressed(false);
                    return true;
                }
                return false;
            }

        });
    }

    @Override
    protected void l() {
        super.l();
        int n2 = (int)TypedValue.applyDimension((int)1, (float)this.M, (DisplayMetrics)this.getResources().getDisplayMetrics());
        this.ao = (ImageView)this.l.findViewById(2131558588);
        ViewGroup.LayoutParams layoutParams = this.ao.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.height = n2;
            layoutParams.width = n2;
        }
        this.ao.setLayoutParams(layoutParams);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void m() {
        int n2 = 1;
        int n3 = this.U.getBoolean("flipPanelView", false);
        int n4 = this.getResources().getConfiguration().orientation == 2 ? n2 : 0;
        if (n4 != n3) {
            n2 = 0;
        }
        this.i.setOrientation(n2);
    }

    @SuppressLint(value={"NewApi"})
    @Override
    public void onCreate() {
        com.cygery.utilities.a.a((Context)this);
        this.U = this.getSharedPreferences("repetitouch_prefs", 0);
        this.ab = (int)(0.5 + (double)(8.0f * this.getResources().getDisplayMetrics().density));
        this.ad = new c();
        this.Q = false;
        this.ae = false;
        this.R = MenuActivityPro.class;
        this.S = EventManagerServicePro.class;
        this.T = TranslucentActivityPro.class;
        this.G();
        this.aq = this.U.getBoolean("ignoreStatusBar", false);
        this.ar = this.U.getBoolean("hidePanelDuringReplay", false);
        this.u = 51;
        this.v = 264;
        this.w = 264;
        this.startService(new Intent((Context)this, (Class)QueryBackgroundService.class));
        super.onCreate();
        this.g.a(2130837630, (CharSequence)this.getString(2131165306), PendingIntent.getActivity((Context)this.a, (int)2, (Intent)new Intent(this.a, this.T).putExtra("message", "togglePanel").addFlags(268435456), (int)0));
        this.g.a(2130837605, (CharSequence)this.getString(2131165305), PendingIntent.getActivity((Context)this.a, (int)3, (Intent)new Intent(this.a, this.T).putExtra("message", "togglePanelDrag").addFlags(268435456), (int)0));
        this.g.a(2130837601, (CharSequence)this.getString(2131165307), PendingIntent.getActivity((Context)this.a, (int)4, (Intent)new Intent(this.a, this.T).putExtra("message", "togglePanelRecordButton").addFlags(268435456), (int)0));
        this.startForeground(1, this.g.a());
        if (Build.VERSION.SDK_INT >= 11) {
            this.ah = new ArrayList();
            this.aj = new ListPopupWindow((Context)this, null, 16843520);
            this.al = new a((Context)this, 2130903093, this.ah);
            this.ah.add((Object)new b(this, 2130837610, 2131165294));
            this.ah.add((Object)new b(this, 2130837615, 2131165278));
            this.ah.add((Object)new b(this, 2130837606, 2131165259));
            this.ah.add((Object)new b(this, 2130837607, 2131165285));
            this.ah.add((Object)new b(this, 2130837612, 2131165297));
            this.ah.add((Object)new b(this, 2130837628, 2131165262));
            this.aj.setAdapter((ListAdapter)this.al);
            this.aj.setModal(true);
            this.aj.setOnItemClickListener((AdapterView.OnItemClickListener)this);
            this.ac = new BroadcastReceiver(){

                public void onReceive(Context context, Intent intent) {
                    if ("android.intent.action.CONFIGURATION_CHANGED".equals((Object)intent.getAction())) {
                        PanelServicePro.this.H();
                    }
                }
            };
            this.registerReceiver(this.ac, new IntentFilter("android.intent.action.CONFIGURATION_CHANGED"));
        }
        this.an = new ImageView((Context)this);
        this.an.setVisibility(4);
        this.ap = new WindowManager.LayoutParams(-2, -2, 2010, 264, -3);
        this.ap.gravity = 51;
        this.n.addView((View)this.an, (ViewGroup.LayoutParams)this.ap);
    }

    /*
     * Exception decompiling
     */
    @Override
    public void onDestroy() {
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
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @SuppressLint(value={"NewApi"})
    public void onItemClick(AdapterView<?> adapterView, View view, int n2, long l2) {
        int n3;
        if (adapterView.equals((Object)this.aj.getListView())) {
            int n4 = ((b)this.ah.get((int)n2)).b;
            if (n4 == 0) {
                com.cygery.utilities.a.c(aa, "textViewResourceId == 0 in onItemClick for mListPopupWindowMenu");
                return;
            }
            if (n4 == 2131165294) {
                this.startActivity(new Intent(this.getApplicationContext(), (Class)TranslucentActivityPro.class).addFlags(335544320).putExtra("message", "showSaveDialog"));
            } else if (n4 == 2131165278) {
                this.startActivity(new Intent(this.getApplicationContext(), (Class)TranslucentActivityPro.class).addFlags(335544320).putExtra("message", "showOpenDialog"));
            } else if (n4 == 2131165297) {
                this.startActivity(new Intent(this.getApplicationContext(), (Class)SettingsActivityPro.class).addFlags(335544320));
            } else if (n4 == 2131165262) {
                this.stopSelf();
            } else if (n4 == 2131165259) {
                this.F();
            } else if (n4 == 2131165285) {
                this.startService(new Intent((Context)this, this.S).setAction("eventmanagerservice").putExtra("message", "launchmerger").putExtra("clearMarkers", true));
            } else {
                com.cygery.utilities.a.c(aa, "unknown textViewResourceId: " + n4);
            }
            this.a(this.aj);
            return;
        }
        if (!adapterView.equals((Object)this.ak.getListView())) return;
        b b2 = (b)this.ai.get(n2);
        String string = b2.b == 0 ? b2.c : this.getString(b2.b);
        if ("inf".equalsIgnoreCase(string)) {
            n3 = -1;
        } else {
            int n5;
            if ("edit".equalsIgnoreCase(string)) {
                this.A();
                this.a(this.ak);
                return;
            }
            n3 = n5 = Integer.parseInt((String)string);
        }
        com.cygery.utilities.a.a(aa, "starting loop replay via popup. loopCount: " + n3);
        this.startService(new Intent((Context)this, this.S).setAction("eventmanagerservice").putExtra("message", "startreplayloop").putExtra("loopCount", n3));
        this.Q = true;
        this.a(this.ak);
        return;
        catch (NumberFormatException numberFormatException) {
            com.cygery.utilities.a.a(aa, "aborting start loop replay via popup. loopCount: " + string);
            this.a(this.ak);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String string) {
        super.onSharedPreferenceChanged(sharedPreferences, string);
        if (string.equals((Object)"flipPanelView")) {
            this.A = this.i.getHeight();
            this.z = this.i.getWidth();
            this.b.postDelayed(new Runnable(){

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 */
                public void run() {
                    try {
                        PanelServicePro.this.n.removeView((View)PanelServicePro.this.i);
                    }
                    catch (Exception var1_1) {
                        com.cygery.utilities.a.b(aa, "failed to remove panelView");
                    }
                    try {
                        PanelServicePro.this.n.removeView(PanelServicePro.this.m);
                    }
                    catch (Exception var2_2) {
                        com.cygery.utilities.a.b(aa, "failed to remove screenBrightnessDummyView");
                    }
                    PanelServicePro.this.l();
                    PanelServicePro.this.a();
                    PanelServicePro.this.s();
                    PanelServicePro.this.n.addView((View)PanelServicePro.this.i, (ViewGroup.LayoutParams)PanelServicePro.this.p);
                    PanelServicePro.this.n.addView(PanelServicePro.this.m, (ViewGroup.LayoutParams)PanelServicePro.this.t);
                }
            }, 50);
            return;
        }
        if (string.equals((Object)"ignoreStatusBar")) {
            this.aq = sharedPreferences.getBoolean(string, false);
            if (this.aq) return;
            {
                this.r();
                this.s();
                try {
                    this.n.updateViewLayout((View)this.i, (ViewGroup.LayoutParams)this.p);
                    return;
                }
                catch (IllegalArgumentException var6_3) {
                    com.cygery.utilities.a.c(aa, (Throwable)var6_3);
                    return;
                }
            }
        }
        if (string.equals((Object)"panelIconSizeInt")) {
            try {
                this.M = sharedPreferences.getInt(string, 35);
            }
            catch (NumberFormatException var3_4) {
                this.M = 35;
            }
            this.i();
            com.cygery.utilities.a.a(aa, "panelIconSize=" + this.M);
            try {
                this.n.removeView((View)this.i);
            }
            catch (Exception var4_5) {
                com.cygery.utilities.a.b(aa, "failed to remove panelView");
            }
            try {
                this.n.removeView(this.m);
            }
            catch (Exception var5_6) {
                com.cygery.utilities.a.b(aa, "failed to remove screenBrightnessDummyView");
            }
            this.l();
            this.i.setLayoutParams((ViewGroup.LayoutParams)this.p);
            this.i.measure(View.MeasureSpec.makeMeasureSpec((int)0, (int)0), View.MeasureSpec.makeMeasureSpec((int)0, (int)0));
            this.z = this.i.getMeasuredHeight();
            this.A = this.i.getMeasuredWidth();
            this.r();
            this.a();
            this.s();
            this.n.addView((View)this.i, (ViewGroup.LayoutParams)this.p);
            this.n.addView(this.m, (ViewGroup.LayoutParams)this.t);
            return;
        }
        if (string.equals((Object)"hidePanelDuringReplay")) {
            this.ar = sharedPreferences.getBoolean(string, false);
            return;
        }
        if (!string.equals((Object)"loopTimes")) return;
        {
            this.G();
            return;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int n2, int n3) {
        com.cygery.utilities.a.a(aa, "onStartCommand()");
        if (intent != null) {
            if ("panelservice".equals((Object)intent.getAction())) {
                if (this.H) {
                    this.a(intent);
                    this.startService(new Intent((Context)this, (Class)QueryBackgroundService.class).setAction("querybackgroundservice").putExtra("message", "start"));
                    String string = intent.getStringExtra("message");
                    if ("show".equals((Object)string) || "hide".equals((Object)string) || "togglevisibility".equals((Object)string) || "togglevisibilitynext".equals((Object)string)) {
                        return 3;
                    }
                    return 1;
                }
            } else {
                int n4 = super.onStartCommand(intent, n2, n3);
                if (this.H) {
                    this.startService(new Intent((Context)this, (Class)QueryBackgroundService.class).setAction("querybackgroundservice").putExtra("message", "start"));
                    return n4;
                }
            }
        }
        com.cygery.utilities.a.c(aa, "not normally started, calling stopSelf. intent=" + (Object)intent + " flags=" + n2 + " startId=" + n3);
        if (this.b != null) {
            this.b.removeCallbacksAndMessages((Object)null);
        }
        this.stopSelf();
        return 2;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void q() {
        if (this.L == 0) {
            this.p.x = this.j.getWidth() - this.y - this.A;
            this.p.y = this.x;
        } else if (this.L == 2) {
            this.p.x = this.y;
            this.p.y = this.J ? this.l.getHeight() + this.k() - this.x - this.z : this.l.getHeight() - this.x - this.z;
        } else if (this.L == 1) {
            this.p.x = this.x;
            this.p.y = this.y;
        } else {
            this.p.x = this.j.getWidth() - this.x - this.A;
            this.p.y = this.J ? this.l.getHeight() + this.k() - this.y - this.z : this.l.getHeight() - this.y - this.z;
        }
        com.cygery.utilities.a.a(aa, "updatePanelParams");
        com.cygery.utilities.a.a(aa, "rotation=" + this.L);
        com.cygery.utilities.a.a(aa, "relative coords: " + this.p.x + "," + this.p.y);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void r() {
        if (this.p.x < 0) {
            this.p.x = 0;
        }
        if (this.p.x > this.j.getWidth() - this.A) {
            this.p.x = this.j.getWidth() - this.A;
        }
        if (this.I && !this.aq) {
            if (this.p.y < this.K) {
                this.p.y = this.K;
            }
        } else if (this.p.y < 0) {
            this.p.y = 0;
        }
        if (this.p.y > this.l.getHeight() - this.z) {
            this.p.y = this.l.getHeight() - this.z;
        }
    }

    @Override
    protected void t() {
        super.t();
        if (this.B) {
            this.startService(new Intent((Context)this, (Class)QueryBackgroundService.class).setAction("querybackgroundservice").putExtra("message", "show"));
            return;
        }
        this.startService(new Intent((Context)this, (Class)QueryBackgroundService.class).setAction("querybackgroundservice").putExtra("message", "hide"));
    }

    private class a
    extends ArrayAdapter<b> {
        static final /* synthetic */ boolean a;
        private final int c;
        private final List<b> d;

        /*
         * Enabled aggressive block sorting
         */
        static {
            boolean bl = !PanelServicePro.class.desiredAssertionStatus();
            a = bl;
        }

        public a(Context context, int n2, List<b> list) {
            super(context, n2, list);
            this.c = n2;
            this.d = list;
        }

        /*
         * Enabled aggressive block sorting
         */
        public View getView(int n2, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = ((LayoutInflater)PanelServicePro.this.a.getSystemService("layout_inflater")).inflate(this.c, null);
            }
            if (!a && view == null) {
                throw new AssertionError();
            }
            TextView textView = (TextView)view.findViewById(2131558587);
            ImageView imageView = (ImageView)view.findViewById(2131558586);
            b b2 = (b)this.d.get(n2);
            if (b2 == null) return view;
            {
                if (b2.a != 0) {
                    imageView.setImageResource(b2.a);
                }
                if (b2.b != 0) {
                    textView.setText(b2.b);
                    return view;
                } else {
                    if (b2.c == null) return view;
                    {
                        textView.setText((CharSequence)b2.c);
                        return view;
                    }
                }
            }
        }
    }

    private class b {
        final int a;
        final int b;
        String c;
        final /* synthetic */ PanelServicePro d;

        public b(PanelServicePro panelServicePro, int n2, int n3) {
            this.d = panelServicePro;
            this.a = n2;
            this.b = n3;
        }

        public b(PanelServicePro panelServicePro, int n2, String string) {
            this.d = panelServicePro;
            this.a = n2;
            this.b = 0;
            this.c = string;
        }
    }

    class c
    implements View.OnTouchListener {
        float a;
        float b;
        float c;
        float d;
        boolean e;

        c() {
            this.e = false;
        }

        void a(float f2, float f3) {
            PanelServicePro.aF((PanelServicePro)PanelServicePro.this).x = (int)f2;
            PanelServicePro.aF((PanelServicePro)PanelServicePro.this).y = (int)f3;
            try {
                PanelServicePro.this.n.updateViewLayout((View)PanelServicePro.this.an, (ViewGroup.LayoutParams)PanelServicePro.this.ap);
                return;
            }
            catch (IllegalArgumentException var3_3) {
                com.cygery.utilities.a.c(aa, (Throwable)var3_3);
                return;
            }
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        public boolean onTouch(View var1, MotionEvent var2_2) {
            switch (var2_2.getAction()) {
                case 0: {
                    var13_4 = PanelServicePro.ag(PanelServicePro.this);
                    var4_3 = false;
                    if (var13_4 == false) return var4_3;
                    this.e = true;
                    PanelServicePro.a(PanelServicePro.this, PanelServicePro.ah(PanelServicePro.this).getHeight());
                    this.a = var2_2.getX();
                    this.b = var2_2.getY();
                    var15_5 = PanelServicePro.ai(PanelServicePro.this).getDrawingCache();
                    PanelServicePro.aj(PanelServicePro.this).setImageBitmap(var15_5);
                    this.a(PanelServicePro.ak((PanelServicePro)PanelServicePro.this).x, PanelServicePro.al((PanelServicePro)PanelServicePro.this).y);
                    PanelServicePro.aj(PanelServicePro.this).setVisibility(0);
                    PanelServicePro.an(PanelServicePro.this).postDelayed(new Runnable(){

                        public void run() {
                            PanelServicePro.this.i.setVisibility(4);
                        }
                    }, 50);
                    ** break;
                }
                case 1: {
                    if (this.e) {
                        this.c = var2_2.getX();
                        this.d = var2_2.getY();
                        var7_6 = this.c - this.a;
                        var8_7 = this.d - this.b;
                        var9_8 = PanelServicePro.ao(PanelServicePro.this);
                        var9_8.x = (int)(var7_6 + (float)var9_8.x);
                        var10_9 = PanelServicePro.ap(PanelServicePro.this);
                        var10_9.y = (int)(var8_7 + (float)var10_9.y);
                        PanelServicePro.this.r();
                        try {
                            PanelServicePro.as(PanelServicePro.this).updateViewLayout((View)PanelServicePro.aq(PanelServicePro.this), (ViewGroup.LayoutParams)PanelServicePro.ar(PanelServicePro.this));
                        }
                        catch (IllegalArgumentException var11_10) {
                            com.cygery.utilities.a.c(PanelServicePro.J(), (Throwable)var11_10);
                        }
                        PanelServicePro.at(PanelServicePro.this).setVisibility(0);
                        PanelServicePro.this.a(PanelServicePro.N(PanelServicePro.this), PanelServicePro.au(PanelServicePro.this).findViewById(2131558628), 0.0f, 0.0f);
                        PanelServicePro.this.a(PanelServicePro.av(PanelServicePro.this), PanelServicePro.aw(PanelServicePro.this).findViewById(2131558629), 0.0f, 0.0f);
                        PanelServicePro.ay(PanelServicePro.this).postDelayed(new Runnable(){

                            public void run() {
                                PanelServicePro.this.an.setVisibility(4);
                                PanelServicePro.this.i.setVisibility(0);
                            }
                        }, 100);
                        PanelServicePro.az(PanelServicePro.this);
                    }
                    this.e = false;
                    ** break;
                }
                case 2: {
                    if (this.e == false) return true;
                    this.c = var2_2.getX();
                    this.d = var2_2.getY();
                    var5_11 = this.c - this.a;
                    var6_12 = this.d - this.b;
                    this.a(var5_11 + (float)PanelServicePro.aA((PanelServicePro)PanelServicePro.this).x, var6_12 + (float)PanelServicePro.aB((PanelServicePro)PanelServicePro.this).y);
                    PanelServicePro.this.a(PanelServicePro.N(PanelServicePro.this), PanelServicePro.aC(PanelServicePro.this).findViewById(2131558628), var5_11, var6_12);
                    PanelServicePro.this.a(PanelServicePro.av(PanelServicePro.this), PanelServicePro.aD(PanelServicePro.this).findViewById(2131558629), var5_11, var6_12);
                }
lbl48: // 4 sources:
                default: {
                    return true;
                }
                case 3: 
            }
            this.e = false;
            PanelServicePro.aE(PanelServicePro.this).setVisibility(0);
            PanelServicePro.aj(PanelServicePro.this).setVisibility(4);
            PanelServicePro.this.H();
            return true;
        }

    }

}

