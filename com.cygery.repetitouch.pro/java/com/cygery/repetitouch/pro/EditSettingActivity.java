/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.Intent
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.graphics.Paint
 *  android.os.Bundle
 *  android.os.Environment
 *  android.os.IBinder
 *  android.os.ResultReceiver
 *  android.support.v4.app.p
 *  android.support.v7.app.b
 *  android.text.Editable
 *  android.text.TextPaint
 *  android.view.KeyEvent
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewTreeObserver
 *  android.view.ViewTreeObserver$OnGlobalLayoutListener
 *  android.view.inputmethod.InputMethodManager
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemSelectedListener
 *  android.widget.ArrayAdapter
 *  android.widget.CheckBox
 *  android.widget.EditText
 *  android.widget.Spinner
 *  android.widget.SpinnerAdapter
 *  android.widget.TextView
 *  android.widget.TextView$OnEditorActionListener
 *  android.widget.Toast
 *  java.io.File
 *  java.io.IOException
 *  java.lang.CharSequence
 *  java.lang.Double
 *  java.lang.Integer
 *  java.lang.NumberFormatException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 */
package com.cygery.repetitouch.pro;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.support.v4.app.p;
import android.support.v7.app.b;
import android.text.Editable;
import android.text.TextPaint;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.cygery.utilities.e;
import java.io.File;
import java.io.IOException;

public class EditSettingActivity
extends b
implements AdapterView.OnItemSelectedListener,
e.b {
    private static final String m = EditSettingActivity.class.getName();
    private String A;
    private boolean B;
    private boolean C;
    private boolean D;
    private boolean E;
    private int F;
    private boolean n;
    private boolean o;
    private boolean p;
    private String q;
    private int r;
    private SharedPreferences s;
    private InputMethodManager t;
    private Spinner u;
    private TextView v;
    private String w;
    private String x;
    private boolean y;
    private int z;

    private int b(String string) {
        if ("Start Record".equals((Object)string)) {
            return 0;
        }
        if ("Stop Record".equals((Object)string)) {
            return 1;
        }
        if ("Start Replay".equals((Object)string)) {
            return 2;
        }
        if ("Stop Replay".equals((Object)string)) {
            return 3;
        }
        if ("Load File".equals((Object)string)) {
            return 4;
        }
        if ("Save File".equals((Object)string)) {
            return 5;
        }
        return -1;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void b(int n2) {
        if (this.o) {
            this.findViewById(2131558519).setVisibility(0);
            if (this.E) {
                this.findViewById(2131558524).setVisibility(0);
            }
        } else {
            this.findViewById(2131558519).setVisibility(8);
            this.findViewById(2131558524).setVisibility(8);
        }
        if (n2 == 0) {
            this.findViewById(2131558535).setVisibility(0);
        } else {
            this.findViewById(2131558535).setVisibility(8);
        }
        if (n2 == 2) {
            this.findViewById(2131558529).setVisibility(0);
            this.findViewById(2131558532).setVisibility(0);
            this.findViewById(2131558526).setVisibility(0);
            this.t.showSoftInput(this.findViewById(2131558531), 0, null);
        } else {
            this.findViewById(2131558529).setVisibility(8);
            this.findViewById(2131558532).setVisibility(8);
            this.findViewById(2131558526).setVisibility(8);
            this.t.hideSoftInputFromWindow(this.findViewById(2131558531).getWindowToken(), 0);
        }
        if (n2 != 1 && n2 != 3 && n2 != 2) {
            this.findViewById(2131558538).setVisibility(8);
            return;
        }
        this.findViewById(2131558538).setVisibility(0);
    }

    private String c(int n2) {
        if (n2 == 0) {
            return "Start Record";
        }
        if (n2 == 1) {
            return "Stop Record";
        }
        if (n2 == 2) {
            return "Start Replay";
        }
        if (n2 == 3) {
            return "Stop Replay";
        }
        if (n2 == 4) {
            return "Load File";
        }
        if (n2 == 5) {
            return "Save File";
        }
        return null;
    }

    void a(Bundle bundle) {
        this.w = bundle.getString("action");
        if (this.w == null) {
            this.w = (String)this.getText(2131165301);
        }
        this.x = bundle.getString("filename");
        this.y = bundle.getBoolean("appendingrecord", false);
        this.z = bundle.getInt("looptimes", 1);
        this.A = bundle.getString("replayspeed");
        this.B = bundle.getBoolean("hidepanel", false);
        this.C = bundle.getBoolean("closeafteraction", false);
        this.D = bundle.getBoolean("silent", false);
    }

    void a(String string) {
        AlertDialog.Builder builder = new AlertDialog.Builder((Context)this);
        builder.setTitle(2131165404);
        builder.setMessage((CharSequence)string);
        builder.setPositiveButton(17039370, new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n2) {
                EditSettingActivity.this.p = true;
                EditSettingActivity.this.finish();
            }
        });
        builder.setNegativeButton(17039360, null);
        builder.create().show();
    }

    /*
     * Unable to fully structure code
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean a(File var1, int var2_2) {
        if (var1 != null) {
            this.x = var1.getAbsolutePath();
lbl3: // 2 sources:
            do {
                if (this.x == null) return true;
                switch (var2_2) {
                    default: 
                }
                this.q = this.x;
                ((TextView)this.findViewById(this.F)).setText((CharSequence)this.x);
                return true;
                break;
            } while (true);
        }
        this.x = null;
        ** while (true)
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void finish() {
        if (this.n) {
            EditText editText;
            SharedPreferences.Editor editor = this.s.edit();
            editor.putString("startDir", this.q);
            editor.apply();
            this.y = ((CheckBox)this.findViewById(2131558537)).isChecked();
            try {
                EditText editText2 = (EditText)this.findViewById(2131558531);
                if (editText2 != null && editText2.getText() != null) {
                    this.z = Integer.parseInt((String)editText2.getText().toString());
                }
            }
            catch (NumberFormatException var4_11) {
                this.z = 0;
            }
            if ((editText = (EditText)this.findViewById(2131558534)) != null) {
                this.A = editText.getText() == null || editText.getText().toString().isEmpty() ? null : editText.getText().toString();
            }
            this.B = ((CheckBox)this.findViewById(2131558528)).isChecked();
            this.C = ((CheckBox)this.findViewById(2131558540)).isChecked();
            this.D = ((CheckBox)this.findViewById(2131558543)).isChecked();
            this.w = this.c(this.u.getSelectedItemPosition());
            String string = "" + this.getString(2131165250) + ": " + this.u.getSelectedItem();
            int n2 = this.b(this.w);
            if (n2 == 0 && this.y) {
                string = string + " (" + this.getString(2131165342) + ")";
            }
            if (this.o && this.x != null) {
                if (n2 == 1) {
                    string = string + " (" + this.getString(2131165379) + " \"" + this.x + "\")";
                } else if (n2 == 2) {
                    string = string + " (" + this.getString(2131165389) + " \"" + this.x + "\")";
                }
            }
            if (n2 == 4 || n2 == 5) {
                if (this.x == null) {
                    Toast.makeText((Context)this, (int)2131165243, (int)0).show();
                    return;
                }
                string = string + " (\"" + this.x + "\"" + ")";
            }
            if (n2 == 2) {
                if (this.z == 0) {
                    Toast.makeText((Context)this, (int)2131165217, (int)0).show();
                    return;
                }
                if (this.z > 1) {
                    StringBuilder stringBuilder = new StringBuilder().append(string).append(" (");
                    Object[] arrobject = new Object[1];
                    Object[] arrobject2 = new Object[]{this.z};
                    arrobject[0] = this.getString(2131165385, arrobject2);
                    string = stringBuilder.append(this.getString(2131165375, arrobject)).append(")").toString();
                } else if (this.z == 1) {
                    StringBuilder stringBuilder = new StringBuilder().append(string).append(" (");
                    Object[] arrobject = new Object[]{this.getString(2131165368)};
                    string = stringBuilder.append(this.getString(2131165375, arrobject)).append(")").toString();
                } else {
                    StringBuilder stringBuilder = new StringBuilder().append(string).append(" (");
                    Object[] arrobject = new Object[]{this.getString(2131165361)};
                    string = stringBuilder.append(this.getString(2131165375, arrobject)).append(")").toString();
                }
                if (this.A != null) {
                    double d2;
                    try {
                        double d3;
                        d2 = d3 = Double.parseDouble((String)this.A);
                    }
                    catch (NumberFormatException var15_16) {
                        d2 = -1.0;
                    }
                    if (d2 <= 0.0) {
                        Toast.makeText((Context)this, (int)2131165218, (int)0).show();
                        return;
                    }
                    string = string + " (" + this.A + "x" + " " + this.getString(2131165382) + ")";
                }
                if (this.B) {
                    string = string + " " + this.getString(2131165390);
                }
            }
            if (!(n2 != 1 && n2 != 5 || this.x == null || this.p)) {
                String string2 = new File(this.x).exists() ? this.getString(2131165348) : this.getString(2131165349);
                this.a(string2);
                return;
            }
            if ((n2 == 1 || n2 == 3 || n2 == 2) && this.C) {
                string = string + ", " + this.getString(2131165347);
            }
            if (this.D) {
                string = string + " (" + this.getString(2131165381) + ")";
            }
            Bundle bundle = new Bundle();
            bundle.putString("action", this.w);
            bundle.putString("filename", this.x);
            bundle.putBoolean("appendingrecord", this.y);
            bundle.putInt("looptimes", this.z);
            bundle.putString("replayspeed", this.A);
            bundle.putBoolean("hidepanel", this.B);
            bundle.putBoolean("closeafteraction", this.C);
            bundle.putBoolean("silent", this.D);
            Intent intent = new Intent();
            intent.putExtra("com.twofortyfouram.locale.intent.extra.BUNDLE", bundle);
            intent.putExtra("com.twofortyfouram.locale.intent.extra.BLURB", string);
            this.setResult(-1, intent);
        } else {
            this.setResult(0);
        }
        this.t.hideSoftInputFromWindow(this.findViewById(2131558531).getWindowToken(), 0);
        super.finish();
    }

    void j() {
        this.w = (String)this.getText(2131165301);
        this.x = null;
        this.y = false;
        this.z = 1;
        this.A = null;
        this.B = false;
        this.C = false;
        this.D = false;
    }

    void k() {
        this.u.setSelection(this.b(this.w));
        ((TextView)this.findViewById(this.F)).setText((CharSequence)this.x);
        ((CheckBox)this.findViewById(2131558537)).setChecked(this.y);
        ((EditText)this.findViewById(2131558531)).setText((CharSequence)("" + this.z));
        ((EditText)this.findViewById(2131558534)).setText((CharSequence)this.A);
        ((CheckBox)this.findViewById(2131558528)).setChecked(this.B);
        ((CheckBox)this.findViewById(2131558540)).setChecked(this.C);
        ((CheckBox)this.findViewById(2131558543)).setChecked(this.D);
    }

    public void onBackPressed() {
        Toast.makeText((Context)this, (int)2131165371, (int)0).show();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    protected void onCreate(Bundle var1) {
        super.onCreate(var1);
        this.setContentView(2130903069);
        this.n = false;
        this.o = false;
        this.p = false;
        this.r = 1;
        this.E = false;
        this.F = 2131558521;
        var2_2 = this.findViewById(2131558521);
        if (var2_2 != null && var2_2.getViewTreeObserver() != null) {
            var2_2.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){

                /*
                 * Enabled aggressive block sorting
                 */
                public void onGlobalLayout() {
                    boolean bl = EditSettingActivity.this.E;
                    int n2 = EditSettingActivity.this.findViewById(2131558521).getWidth();
                    CharSequence charSequence = ((TextView)EditSettingActivity.this.findViewById(EditSettingActivity.this.F)).getText();
                    if (n2 < 200) {
                        EditSettingActivity.this.E = true;
                        EditSettingActivity.this.F = 2131558525;
                    } else {
                        EditSettingActivity.this.E = false;
                        EditSettingActivity.this.F = 2131558521;
                    }
                    if (EditSettingActivity.this.E != bl) {
                        ((TextView)EditSettingActivity.this.findViewById(EditSettingActivity.this.F)).setText(charSequence);
                        EditSettingActivity editSettingActivity = EditSettingActivity.this;
                        int n3 = EditSettingActivity.this.F == 2131558525 ? 2131558521 : 2131558525;
                        ((TextView)editSettingActivity.findViewById(n3)).setText(null);
                        EditSettingActivity.this.b(EditSettingActivity.this.u.getSelectedItemPosition());
                        return;
                    }
                    if (!EditSettingActivity.this.o) return;
                    {
                        if (EditSettingActivity.this.E) {
                            EditSettingActivity.this.findViewById(2131558524).setVisibility(0);
                            return;
                        }
                    }
                    EditSettingActivity.this.findViewById(2131558524).setVisibility(8);
                }
            });
        }
        var3_3 = new Paint((Paint)((TextView)this.findViewById(2131558517)).getPaint());
        this.findViewById(2131558517).setMinimumWidth((int)var3_3.measureText(this.getString(2131165255)));
        this.v = (TextView)this.findViewById(2131558520);
        this.u = (Spinner)this.findViewById(2131558518);
        this.u.setOnItemSelectedListener((AdapterView.OnItemSelectedListener)this);
        this.t = (InputMethodManager)this.getSystemService("input_method");
        this.s = this.getSharedPreferences("repetitouch_prefs", 0);
        this.q = this.s.getString("startDir", Environment.getExternalStorageDirectory().getPath());
        try {
            this.q = new File(this.q).getCanonicalPath();
        }
        catch (IOException var4_6) {
            ** continue;
        }
lbl22: // 2 sources:
        do {
            var5_4 = new ArrayAdapter((Context)this, 17367048);
            var5_4.setDropDownViewResource(17367049);
            this.u.setAdapter((SpinnerAdapter)var5_4);
            var5_4.add((Object)this.getText(2131165301));
            var5_4.add((Object)this.getText(2131165303));
            var5_4.add((Object)this.getText(2131165302));
            var5_4.add((Object)this.getText(2131165304));
            var5_4.add((Object)this.getText(2131165279));
            var5_4.add((Object)this.getText(2131165295));
            this.findViewById(2131558523).setOnClickListener(new View.OnClickListener(){

                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("savedialog", true);
                    bundle.putString("path", EditSettingActivity.this.q);
                    bundle.putInt("requestcode", EditSettingActivity.this.r);
                    e e2 = new e();
                    e2.g(bundle);
                    e2.a(EditSettingActivity.this.e(), null);
                }
            });
            this.findViewById(2131558522).setOnClickListener(new View.OnClickListener(){

                public void onClick(View view) {
                    EditSettingActivity.this.x = null;
                    ((TextView)EditSettingActivity.this.findViewById(EditSettingActivity.this.F)).setText(null);
                }
            });
            ((EditText)this.findViewById(2131558531)).setOnEditorActionListener(new TextView.OnEditorActionListener(){

                public boolean onEditorAction(TextView textView, int n2, KeyEvent keyEvent) {
                    if (n2 == 6) {
                        EditSettingActivity.this.n = true;
                        EditSettingActivity.this.finish();
                        return true;
                    }
                    return false;
                }
            });
            ((EditText)this.findViewById(2131558531)).setSelectAllOnFocus(true);
            var6_5 = this.getIntent().getBundleExtra("com.twofortyfouram.locale.intent.extra.BUNDLE");
            if (var6_5 != null) {
                this.a(var6_5);
lbl39: // 2 sources:
                do {
                    this.k();
                    return;
                    break;
                } while (true);
            }
            this.j();
            ** continue;
            break;
        } while (true);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(2131689474, menu);
        return true;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public void onItemSelected(AdapterView<?> var1_1, View var2_3, int var3_4, long var4) {
        switch (var3_4) {
            case 0: {
                this.o = false;
                ** break;
            }
            case 1: {
                this.o = true;
                this.r = 2;
                this.v.setText(2131165266);
                ** break;
            }
            case 2: {
                this.o = true;
                this.r = 1;
                this.v.setText(2131165264);
                ** break;
            }
            case 3: {
                this.o = false;
                ** break;
            }
            case 4: {
                this.o = true;
                this.r = 1;
                this.v.setText(2131165263);
            }
lbl22: // 6 sources:
            default: {
                ** GOTO lbl28
            }
            case 5: 
        }
        this.o = true;
        this.r = 2;
        this.v.setText(2131165265);
lbl28: // 2 sources:
        super.b(var3_4);
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            default: {
                return true;
            }
            case 2131558660: {
                this.n = true;
                this.finish();
                return true;
            }
            case 2131558661: 
        }
        this.n = false;
        this.finish();
        return true;
    }

    protected void onResume() {
        super.onResume();
        if (!this.s.getBoolean("enableLocaleSupport", false)) {
            this.findViewById(2131558486).setVisibility(0);
            return;
        }
        this.findViewById(2131558486).setVisibility(8);
    }

}

