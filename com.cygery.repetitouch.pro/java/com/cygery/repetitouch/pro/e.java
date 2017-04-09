/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Dialog
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.DialogInterface$OnDismissListener
 *  android.content.SharedPreferences
 *  android.preference.CheckBoxPreference
 *  android.preference.EditTextPreference
 *  android.preference.Preference
 *  android.text.Html
 *  android.widget.EditText
 *  java.lang.CharSequence
 *  java.lang.Double
 *  java.lang.Integer
 *  java.lang.NumberFormatException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 */
package com.cygery.repetitouch.pro;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.text.Html;
import android.widget.EditText;
import com.cygery.repetitouch.p;
import com.cygery.utilities.a;

public class e
extends p
implements DialogInterface.OnClickListener,
DialogInterface.OnDismissListener {
    private static final String j = e.class.getName();
    CheckBoxPreference d;
    CheckBoxPreference e;
    EditTextPreference f;
    EditTextPreference g;
    Dialog h;
    Dialog i;
    private boolean k;
    private boolean l;

    public e(Context context) {
        this.a = context;
    }

    @Override
    public void a() {
        super.a();
        this.f.getEditText().setInputType(177);
        this.b();
        this.c();
        this.d();
    }

    @Override
    public boolean a(SharedPreferences sharedPreferences, String string) {
        if (super.a(sharedPreferences, string)) {
            return true;
        }
        if (string.equals((Object)this.f.getKey())) {
            this.e();
            this.b();
            return true;
        }
        if (string.equals((Object)this.g.getKey())) {
            this.c();
            return true;
        }
        if (string.equals((Object)"dimDelaySeconds")) {
            this.d();
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void b() {
        boolean bl;
        int n2;
        String string;
        String string2 = this.f.getText();
        try {
            int n3;
            n2 = n3 = Integer.parseInt((String)this.f.getText());
            bl = true;
        }
        catch (NumberFormatException var2_8) {
            if ("inf".equalsIgnoreCase(string2) || "".equals((Object)string2)) {
                n2 = -1;
                bl = true;
            }
            bl = false;
            n2 = 0;
        }
        if (bl) {
            if (n2 < 0) {
                Context context = this.a;
                Object[] arrobject = new Object[]{this.a.getString(2131165361)};
                string = context.getString(2131165283, arrobject);
            } else {
                Context context = this.a;
                Object[] arrobject = new Object[1];
                Context context2 = this.a;
                Object[] arrobject2 = new Object[]{String.valueOf((int)n2)};
                arrobject[0] = context2.getString(2131165385, arrobject2);
                string = context.getString(2131165283, arrobject);
            }
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            Context context = this.a;
            Object[] arrobject = new Object[]{this.a.getString(2131165385, new Object[]{string2})};
            string = stringBuilder.append(context.getString(2131165283, arrobject)).append(".").append(" ").append(this.a.getString(2131165380)).toString();
        }
        this.f.setSummary((CharSequence)Html.fromHtml((String)(this.a.getString(2131165332) + "<br />" + string)));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void c() {
        double d2;
        String string = "";
        String string2 = this.g.getText();
        try {
            double d3;
            d2 = d3 = Double.parseDouble((String)string2);
        }
        catch (NumberFormatException var3_5) {
            d2 = 1.0;
        }
        if (d2 < 0.0) {
            a.c(j, "negative replay speed");
        } else {
            string = d2 < 1.0 ? string2 + "x" : (d2 == 1.0 ? this.a.getString(2131165367) : string2 + "x");
        }
        this.g.setSummary((CharSequence)this.a.getString(2131165333, new Object[]{string}));
    }

    void d() {
        String string = ((EditTextPreference)this.a((CharSequence)"dimDelaySeconds")).getText();
        this.a((CharSequence)"dimDelaySeconds").setSummary((CharSequence)this.a.getString(2131165326, new Object[]{string}));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void e() {
        String string = "";
        String string2 = this.f.getText();
        if ("".equals((Object)string2)) {
            return;
        }
        String[] arrstring = string2.split("[,]+");
        int n2 = arrstring.length;
        int n3 = 0;
        boolean bl = true;
        boolean bl2 = false;
        do {
            boolean bl3;
            if (n3 >= n2) {
                this.f.setText(string);
                return;
            }
            String string3 = arrstring[n3];
            try {
                Integer.parseInt((String)string3);
                bl3 = true;
            }
            catch (NumberFormatException var9_9) {
                bl3 = false;
            }
            if (!(string3.equalsIgnoreCase("inf") || string3.equalsIgnoreCase("edit") || bl3)) {
                bl2 = true;
            } else {
                if (!bl) {
                    string = string + ",";
                }
                string = string + string3;
                bl = false;
            }
            ++n3;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onClick(DialogInterface dialogInterface, int n2) {
        if (dialogInterface == this.h) {
            if (n2 != -1) {
                this.d.setChecked(false);
                return;
            }
            this.k = true;
            return;
        }
        if (dialogInterface != this.i) return;
        {
            if (n2 == -1) {
                this.l = true;
                return;
            }
        }
        this.e.setChecked(false);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onDismiss(DialogInterface dialogInterface) {
        if (dialogInterface == this.h) {
            if (!this.k) {
                this.d.setChecked(false);
            }
            this.k = false;
            return;
        } else {
            if (dialogInterface != this.i) return;
            {
                if (!this.l) {
                    this.e.setChecked(false);
                }
                this.l = false;
                return;
            }
        }
    }
}

