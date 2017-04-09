/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.SharedPreferences
 *  android.os.Bundle
 *  android.os.Environment
 *  android.support.v4.app.p
 *  android.support.v7.app.b
 *  android.text.Editable
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewTreeObserver
 *  android.view.ViewTreeObserver$OnGlobalLayoutListener
 *  android.widget.CheckBox
 *  android.widget.EditText
 *  android.widget.TableRow
 *  android.widget.TextView
 *  android.widget.Toast
 *  java.io.File
 *  java.io.IOException
 *  java.io.Serializable
 *  java.lang.CharSequence
 *  java.lang.Double
 *  java.lang.Integer
 *  java.lang.NumberFormatException
 *  java.lang.Object
 *  java.lang.String
 *  java.text.DecimalFormat
 */
package com.cygery.repetitouch.pro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.p;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import com.cygery.repetitouch.MergeItem;
import com.cygery.repetitouch.b;
import com.cygery.utilities.a;
import com.cygery.utilities.e;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;

public class EditMergeItemActivity
extends android.support.v7.app.b
implements e.b {
    private static final String m = EditMergeItemActivity.class.getName();
    private CheckBox A;
    private TableRow B;
    private TableRow C;
    private TableRow D;
    private TableRow E;
    private Context n;
    private Bundle o;
    private SharedPreferences p;
    private boolean q;
    private String r;
    private boolean s;
    private int t;
    private String u;
    private EditText v;
    private EditText w;
    private EditText x;
    private EditText y;
    private CheckBox z;

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean a(File file, int n2) {
        if (file == null) return true;
        String string = file.getAbsolutePath();
        if (b.c(string) >= 0) {
            this.u = string;
            ((TextView)this.findViewById(this.t)).setText((CharSequence)this.u);
            return true;
        }
        Toast.makeText((Context)this, (int)2131165239, (int)0).show();
        return true;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    public void finish() {
        if (this.q) {
            var1_1 = 1;
            var2_2 = 1.0;
            var1_1 = var9_3 = Integer.parseInt((String)String.valueOf((Object)this.x.getText()));
lbl7: // 3 sources:
            var2_2 = var7_4 = Double.parseDouble((String)String.valueOf((Object)this.y.getText()));
lbl8: // 2 sources:
            do {
                this.o.putString("description", String.valueOf((Object)this.v.getText()));
                this.o.putLong("duration", MergeItem.e(String.valueOf((Object)this.w.getText())));
                this.o.putInt("loop_count", var1_1);
                this.o.putDouble("speed", var2_2);
                this.o.putBoolean("remove_pause_at_start", this.z.isChecked());
                this.o.putBoolean("remove_pause_at_end", this.A.isChecked());
                this.o.putString("path", this.u);
                this.getIntent().putExtras(this.o);
                this.setResult(-1, this.getIntent());
lbl18: // 2 sources:
                do {
                    super.finish();
                    return;
                    break;
                } while (true);
                break;
            } while (true);
        }
        this.setResult(0);
        ** while (true)
        catch (NumberFormatException var5_5) {
            ** continue;
        }
        catch (NumberFormatException var4_6) {
            ** GOTO lbl7
        }
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
        this.n = this;
        a.a((Context)this);
        this.setContentView(2130903068);
        this.q = false;
        this.o = this.getIntent().getExtras();
        this.s = false;
        this.t = 2131558497;
        var2_2 = this.findViewById(2131558497);
        if (var2_2 != null && var2_2.getViewTreeObserver() != null) {
            var2_2.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){

                /*
                 * Enabled aggressive block sorting
                 * Lifted jumps to return sites
                 */
                public void onGlobalLayout() {
                    boolean bl = EditMergeItemActivity.this.s;
                    int n2 = EditMergeItemActivity.this.findViewById(2131558497).getWidth();
                    CharSequence charSequence = ((TextView)EditMergeItemActivity.this.findViewById(EditMergeItemActivity.this.t)).getText();
                    if (n2 < 200) {
                        EditMergeItemActivity.this.s = true;
                        EditMergeItemActivity.this.t = 2131558501;
                    } else {
                        EditMergeItemActivity.this.s = false;
                        EditMergeItemActivity.this.t = 2131558497;
                    }
                    if (EditMergeItemActivity.this.s == bl) return;
                    ((TextView)EditMergeItemActivity.this.findViewById(EditMergeItemActivity.this.t)).setText(charSequence);
                    EditMergeItemActivity editMergeItemActivity = EditMergeItemActivity.this;
                    int n3 = EditMergeItemActivity.this.t == 2131558501 ? 2131558497 : 2131558501;
                    ((TextView)editMergeItemActivity.findViewById(n3)).setText(null);
                    if (EditMergeItemActivity.this.s && EditMergeItemActivity.this.findViewById(2131558495).getVisibility() == 0) {
                        EditMergeItemActivity.this.findViewById(2131558500).setVisibility(0);
                        return;
                    }
                    EditMergeItemActivity.this.findViewById(2131558500).setVisibility(8);
                }
            });
        }
        this.p = this.getSharedPreferences("repetitouch_prefs", 0);
        this.r = this.p.getString("startDir", Environment.getExternalStorageDirectory().getPath());
        try {
            this.r = new File(this.r).getCanonicalPath();
        }
        catch (IOException var3_4) {
            ** continue;
        }
lbl16: // 2 sources:
        do {
            this.v = (EditText)this.findViewById(2131558494);
            this.w = (EditText)this.findViewById(2131558505);
            this.x = (EditText)this.findViewById(2131558508);
            this.y = (EditText)this.findViewById(2131558511);
            this.z = (CheckBox)this.findViewById(2131558514);
            this.A = (CheckBox)this.findViewById(2131558515);
            this.B = (TableRow)this.findViewById(2131558495);
            this.C = (TableRow)this.findViewById(2131558500);
            this.D = (TableRow)this.findViewById(2131558502);
            this.E = (TableRow)this.findViewById(2131558503);
            this.u = this.o.getString("path");
            this.v.setText((CharSequence)this.o.getString("description"));
            this.w.setText((CharSequence)MergeItem.b(this.o.getLong("duration")));
            this.x.setText((CharSequence)("" + this.o.getInt("loop_count")));
            this.y.setText((CharSequence)new DecimalFormat("#.###").format(this.o.getDouble("speed")));
            this.z.setChecked(this.o.getBoolean("remove_pause_at_start"));
            this.A.setChecked(this.o.getBoolean("remove_pause_at_end"));
            this.B.setVisibility(8);
            this.C.setVisibility(8);
            this.D.setVisibility(8);
            this.E.setVisibility(8);
            var4_3 = (MergeItem.Type)this.o.getSerializable("type");
            if (MergeItem.Type.TYPE_FILE.equals((Object)var4_3)) {
                this.B.setVisibility(0);
                ((TextView)this.findViewById(this.t)).setText((CharSequence)this.u);
lbl42: // 3 sources:
                do {
                    this.findViewById(2131558499).setOnClickListener(new View.OnClickListener(){

                        /*
                         * Enabled aggressive block sorting
                         */
                        public void onClick(View view) {
                            Bundle bundle = new Bundle();
                            bundle.putBoolean("savedialog", false);
                            String string = EditMergeItemActivity.this.u != null ? EditMergeItemActivity.this.u : EditMergeItemActivity.this.r;
                            bundle.putString("path", string);
                            e e2 = new e();
                            e2.g(bundle);
                            e2.a(EditMergeItemActivity.this.e(), null);
                        }
                    });
                    this.findViewById(2131558498).setOnClickListener(new View.OnClickListener(){

                        public void onClick(View view) {
                            EditMergeItemActivity.this.u = null;
                            ((TextView)EditMergeItemActivity.this.findViewById(EditMergeItemActivity.this.t)).setText(null);
                        }
                    });
                    this.x.setSelectAllOnFocus(true);
                    this.y.setSelectAllOnFocus(true);
                    return;
                    break;
                } while (true);
            }
            if (MergeItem.Type.TYPE_SUBLIST.equals((Object)var4_3) || !MergeItem.Type.TYPE_PAUSE.equals((Object)var4_3)) ** GOTO lbl42
            this.E.setVisibility(0);
            ** continue;
            break;
        } while (true);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(2131689473, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            default: {
                return true;
            }
            case 2131558660: {
                this.q = true;
                this.finish();
                return true;
            }
            case 2131558661: 
        }
        this.q = false;
        this.finish();
        return true;
    }

}

