/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.SharedPreferences
 *  android.os.Bundle
 *  android.support.v7.app.b
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.widget.ArrayAdapter
 *  android.widget.Spinner
 *  android.widget.SpinnerAdapter
 *  android.widget.Toast
 *  java.lang.Object
 *  java.lang.String
 */
package com.cygery.repetitouch.pro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.b;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

public class EditConditionActivity
extends b {
    private static final String m = EditConditionActivity.class.getName();
    private boolean n;
    private String o;
    private SharedPreferences p;
    private Spinner q;

    private int a(String string) {
        if ("is recording".equals((Object)string)) {
            return 0;
        }
        if ("is replaying".equals((Object)string)) {
            return 1;
        }
        if ("is running".equals((Object)string)) {
            return 2;
        }
        if ("is panel visible".equals((Object)string)) {
            return 3;
        }
        return -1;
    }

    private String b(int n2) {
        if (n2 == 0) {
            return "is recording";
        }
        if (n2 == 1) {
            return "is replaying";
        }
        if (n2 == 2) {
            return "is running";
        }
        if (n2 == 3) {
            return "is panel visible";
        }
        return null;
    }

    void a(Bundle bundle) {
        this.o = bundle.getString("condition");
        if (this.o == null) {
            this.o = "is recording";
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void finish() {
        if (this.n) {
            this.o = this.b(this.q.getSelectedItemPosition());
            Bundle bundle = new Bundle();
            bundle.putString("condition", this.o);
            String string = (String)this.q.getSelectedItem();
            Intent intent = new Intent();
            intent.putExtra("com.twofortyfouram.locale.intent.extra.BUNDLE", bundle);
            intent.putExtra("com.twofortyfouram.locale.intent.extra.BLURB", string);
            this.setResult(-1, intent);
        } else {
            this.setResult(0);
        }
        super.finish();
    }

    void j() {
        this.o = "is recording";
    }

    void k() {
        this.q.setSelection(this.a(this.o));
    }

    public void onBackPressed() {
        Toast.makeText((Context)this, (int)2131165371, (int)0).show();
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2130903067);
        this.o = "";
        this.n = false;
        this.q = (Spinner)this.findViewById(2131558490);
        this.p = this.getSharedPreferences("repetitouch_prefs", 0);
        ArrayAdapter arrayAdapter = new ArrayAdapter((Context)this, 17367048);
        arrayAdapter.setDropDownViewResource(17367049);
        this.q.setAdapter((SpinnerAdapter)arrayAdapter);
        arrayAdapter.add((Object)this.getString(2131165270));
        arrayAdapter.add((Object)this.getString(2131165271));
        arrayAdapter.add((Object)this.getString(2131165272));
        arrayAdapter.add((Object)this.getString(2131165269));
        Bundle bundle2 = this.getIntent().getBundleExtra("com.twofortyfouram.locale.intent.extra.BUNDLE");
        if (bundle2 != null) {
            this.a(bundle2);
        } else {
            this.j();
        }
        this.k();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(2131689472, menu);
        return true;
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
        if (!this.p.getBoolean("enableLocaleSupport", false)) {
            this.findViewById(2131558486).setVisibility(0);
            return;
        }
        this.findViewById(2131558486).setVisibility(8);
    }
}

