/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnCancelListener
 *  android.content.DialogInterface$OnClickListener
 *  android.content.Intent
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.os.Bundle
 *  android.os.Environment
 *  android.os.IBinder
 *  android.support.v4.app.l
 *  android.support.v4.app.p
 *  android.text.Editable
 *  android.text.TextUtils
 *  android.view.KeyEvent
 *  android.view.Menu
 *  android.view.View
 *  android.view.Window
 *  android.view.inputmethod.InputMethodManager
 *  android.widget.Button
 *  android.widget.EditText
 *  android.widget.TextView
 *  android.widget.TextView$OnEditorActionListener
 *  android.widget.Toast
 *  java.io.File
 *  java.io.IOException
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Integer
 *  java.lang.NumberFormatException
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 */
package com.cygery.repetitouch;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.l;
import android.support.v4.app.p;
import android.text.Editable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.cygery.repetitouch.d;
import com.cygery.repetitouch.h;
import com.cygery.utilities.a;
import com.cygery.utilities.e;
import com.cygery.utilities.f;
import java.io.File;
import java.io.IOException;

public class r
extends l
implements a.d,
e.b {
    private static final String q = r.class.getName();
    protected Class m;
    protected Class n;
    protected String o;
    protected int p;

    @Override
    public String a(Context context) {
        return d.d(context);
    }

    protected void a(String string, final Runnable runnable) {
        AlertDialog.Builder builder = new AlertDialog.Builder((Context)this);
        builder.setTitle(h.e.title_confirm_overwrite);
        builder.setMessage(h.e.text_confirm_overwrite);
        builder.setPositiveButton(17039370, new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n2) {
                runnable.run();
            }
        });
        builder.setNegativeButton(17039360, new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n2) {
                dialogInterface.cancel();
            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener(){

            public void onCancel(DialogInterface dialogInterface) {
                Toast.makeText((Context)r.this, (int)h.e.info_file_not_saved, (int)0).show();
                r.this.finish();
            }
        });
        builder.create().show();
    }

    /*
     * Enabled aggressive block sorting
     */
    protected boolean a(Intent intent) {
        String string = intent.getStringExtra("message");
        if (string == null) {
            return true;
        }
        if (string.equals((Object)"togglePanel")) {
            this.startService(new Intent((Context)this, this.m).setAction("panelservice").putExtra("message", "togglevisibility"));
            this.finish();
            return false;
        }
        if (string.equals((Object)"togglePanelDrag")) {
            this.startService(new Intent((Context)this, this.m).setAction("panelservice").putExtra("message", "toggledrag"));
            this.finish();
            return false;
        }
        if (string.equals((Object)"togglePanelRecordButton")) {
            this.startService(new Intent((Context)this, this.m).setAction("panelservice").putExtra("message", "togglerecordbutton"));
            this.finish();
            return false;
        }
        if (string.equals((Object)"showSaveReportDialog")) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("savedialog", true);
            bundle.putString("path", this.o);
            bundle.putInt("requestcode", 1);
            e e2 = new e();
            e2.g(bundle);
            e2.a(this.e(), null);
            return true;
        }
        if (string.equals((Object)"showSaveDialog")) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("savedialog", true);
            bundle.putString("path", this.o);
            bundle.putInt("requestcode", 3);
            e e3 = new e();
            e3.g(bundle);
            e3.a(this.e(), null);
            return true;
        }
        if (string.equals((Object)"showOpenDialog")) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("savedialog", false);
            bundle.putString("path", this.o);
            bundle.putInt("requestcode", 2);
            e e4 = new e();
            e4.g(bundle);
            e4.a(this.e(), null);
            return true;
        }
        if (!string.equals((Object)"showInputLoopCountDialog")) return false;
        this.f();
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean a(File file, int n2) {
        final String string = file != null ? file.getAbsolutePath() : null;
        if (string == null) {
            Toast.makeText((Context)this, (int)h.e.info_no_file_selected, (int)0).show();
            this.finish();
            return true;
        }
        switch (n2) {
            default: {
                return false;
            }
            case 1: {
                if (f.a(string)) {
                    this.a(string, new Runnable(){

                        public void run() {
                            a.a((Context)r.this, r.this, string);
                        }
                    });
                    return true;
                }
                a.a((Context)this, (a.d)this, string);
                return true;
            }
            case 2: {
                this.startService(new Intent((Context)this, this.n).setAction("eventmanagerservice").putExtra("message", "loadevents").putExtra("path", string));
                this.finish();
                this.o = string;
                return true;
            }
            case 3: 
        }
        if (f.a(string)) {
            this.a(string, new Runnable(){

                public void run() {
                    r.this.startService(new Intent((Context)r.this, r.this.n).setAction("eventmanagerservice").putExtra("message", "saveevents").putExtra("path", string));
                    r.this.finish();
                }
            });
        } else {
            this.startService(new Intent((Context)this, this.n).setAction("eventmanagerservice").putExtra("message", "saveevents").putExtra("path", string));
            this.finish();
        }
        this.o = string;
        return true;
    }

    @Override
    public void a_(boolean bl) {
        this.finish();
    }

    @Override
    public String c() {
        return q;
    }

    protected void f() {
        final EditText editText = new EditText((Context)this);
        final InputMethodManager inputMethodManager = (InputMethodManager)this.getSystemService("input_method");
        int n2 = h.e.hint_loop_count;
        Object[] arrobject = new Object[]{this.p};
        editText.setHint((CharSequence)this.getString(n2, arrobject));
        AlertDialog.Builder builder = new AlertDialog.Builder((Context)this);
        builder.setTitle(h.e.label_loop_count);
        builder.setView((View)editText);
        builder.setPositiveButton(17039370, new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n2) {
                int n3;
                Editable editable = editText.getText();
                String string = null;
                if (editable != null) {
                    string = editText.getText().toString();
                }
                if (TextUtils.isEmpty((CharSequence)string)) {
                    n3 = r.this.p;
                    while (n3 < -1 || n3 == 0) {
                        a.a(q, "aborting start loop replay via input loop time dialog. loopCount: " + string);
                        r.this.finish();
                        return;
                    }
                } else {
                    try {
                        int n4;
                        n3 = n4 = Integer.parseInt((String)string);
                    }
                    catch (NumberFormatException var5_7) {
                        a.a(q, "aborting start loop replay via input loop time dialog. loopCount: " + string);
                        r.this.finish();
                        return;
                    }
                }
                inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                r.this.startService(new Intent((Context)r.this, r.this.m).setAction("panelservice").putExtra("message", "startreploop").putExtra("loopCount", n3));
                r.this.p = n3;
                r.this.finish();
            }
        });
        builder.setNegativeButton(17039360, new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n2) {
                r.this.finish();
            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener(){

            public void onCancel(DialogInterface dialogInterface) {
                r.this.finish();
            }
        });
        final AlertDialog alertDialog = builder.create();
        editText.setSingleLine();
        editText.setInputType(4098);
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
        alertDialog.getWindow().setSoftInputMode(4);
        alertDialog.show();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    protected void onCreate(Bundle var1) {
        super.onCreate(var1);
        var2_2 = this.getSharedPreferences("repetitouch_prefs", 0);
        this.o = var2_2.getString("startDir", Environment.getExternalStorageDirectory().getPath());
        this.p = var2_2.getInt("lastEditLoopCount", 1);
        try {
            this.o = new File(this.o).getCanonicalPath();
        }
        catch (IOException var3_4) {
            ** continue;
        }
lbl9: // 2 sources:
        do {
            var4_3 = this.getIntent();
            if (var4_3 != null && (1048576 & var4_3.getFlags()) == 0) {
                this.a(var4_3);
                return;
            }
            this.finish();
            return;
            break;
        } while (true);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    protected void onStop() {
        super.onStop();
        SharedPreferences.Editor editor = this.getSharedPreferences("repetitouch_prefs", 0).edit();
        editor.putString("startDir", this.o);
        editor.putInt("lastEditLoopCount", this.p);
        editor.apply();
    }

}

