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
 *  android.os.Bundle
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Integer
 *  java.lang.NumberFormatException
 *  java.lang.Object
 *  java.lang.String
 */
package com.cygery.repetitouch.pro;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import com.cygery.repetitouch.pro.EventManagerServicePro;
import com.cygery.repetitouch.pro.PanelServicePro;
import com.cygery.repetitouch.r;
import com.cygery.utilities.a;
import com.cygery.utilities.e;

public class TranslucentActivityPro
extends r
implements e.b {
    private static final String q = TranslucentActivityPro.class.getName();

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected boolean a(Intent intent) {
        String string;
        if (super.a(intent) || (string = intent.getStringExtra("message")) == null) {
            return true;
        }
        if (!string.equals((Object)"showLoopCountDialog")) {
            return false;
        }
        final CharSequence[] arrcharSequence = intent.getCharSequenceArrayExtra("items");
        if (arrcharSequence != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder((Context)this);
            builder.setTitle(2131165253);
            builder.setItems(arrcharSequence, new DialogInterface.OnClickListener(){

                /*
                 * Loose catch block
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 * Lifted jumps to return sites
                 */
                public void onClick(DialogInterface dialogInterface, int n2) {
                    int n3;
                    String string = (String)arrcharSequence[n2];
                    if ("inf".equalsIgnoreCase(string)) {
                        n3 = -1;
                    } else {
                        int n4;
                        if ("edit".equalsIgnoreCase(string)) {
                            TranslucentActivityPro.this.f();
                            return;
                        }
                        n3 = n4 = Integer.parseInt((String)string);
                    }
                    TranslucentActivityPro.this.startService(new Intent((Context)TranslucentActivityPro.this, TranslucentActivityPro.this.m).setAction("panelservice").putExtra("message", "startreploop").putExtra("loopCount", n3));
                    TranslucentActivityPro.this.finish();
                    return;
                    catch (NumberFormatException numberFormatException) {
                        a.a(q, "aborting start loop replay via dialog. loopCount: " + string);
                        TranslucentActivityPro.this.finish();
                        return;
                    }
                }
            });
            builder.setOnCancelListener(new DialogInterface.OnCancelListener(){

                public void onCancel(DialogInterface dialogInterface) {
                    TranslucentActivityPro.this.finish();
                }
            });
            builder.create().show();
            return true;
        }
        a.c(q, "items==null for showLoopCountDialog");
        return true;
    }

    @Override
    protected void onCreate(Bundle bundle) {
        this.m = PanelServicePro.class;
        this.n = EventManagerServicePro.class;
        super.onCreate(bundle);
    }

}

