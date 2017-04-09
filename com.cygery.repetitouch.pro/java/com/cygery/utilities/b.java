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
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.pm.ResolveInfo
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.net.Uri
 *  android.text.Spannable
 *  android.text.SpannableString
 *  android.text.SpannableStringBuilder
 *  android.text.TextUtils
 *  android.text.method.LinkMovementMethod
 *  android.text.method.MovementMethod
 *  android.text.style.ClickableSpan
 *  android.text.util.Linkify
 *  android.util.DisplayMetrics
 *  android.util.TypedValue
 *  android.view.View
 *  android.widget.TextView
 *  java.lang.CharSequence
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.Throwable
 *  java.util.regex.Pattern
 */
package com.cygery.utilities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.ClickableSpan;
import android.text.util.Linkify;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import com.cygery.utilities.a;
import com.cygery.utilities.h;
import java.util.regex.Pattern;
import t4X4QwIV.HfM5AFD8kd7A;

public class b
extends AlertDialog {
    private static final String b = b.class.getName();
    protected Context a;

    public b(Context context) {
        super(context, true, true, null);
    }

    public b(Context context, boolean bl, boolean bl2, String string) {
        super(context, bl, bl2, string, null, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    public b(Context context, boolean bl, boolean bl2, String string, String string2, String string3) {
        SpannableString spannableString;
        super(context);
        this.a = context;
        String string4 = this.a.getString(h.d.label_about) + " " + this.a.getString(h.d.app_name) + " v" + this.a();
        TextView textView = new TextView(this.a);
        int n2 = (int)(0.5 + 16.0 * ((double)this.a.getResources().getDisplayMetrics().densityDpi / 160.0));
        textView.setPadding(n2, n2 / 2, n2, n2 / 2);
        textView.setTextSize(18.0f);
        TypedValue typedValue = new TypedValue();
        if (this.a.getTheme() != null) {
            this.a.getTheme().resolveAttribute(16842806, typedValue, true);
        }
        textView.setTextColor(this.a.getResources().getColorStateList(typedValue.resourceId));
        String string5 = this.a.getString(h.d.copyright_year_start);
        String string6 = this.a.getString(h.d.copyright_year_stop);
        if (!string5.equals((Object)string6)) {
            string5 = string5 + "\u2013" + string6;
        }
        String string7 = this.a.getString(h.d.text_about, new Object[]{string5});
        if (string2 != null && string3 != null) {
            string7 = string7 + "\n" + string2 + ": " + string3;
        }
        SpannableString spannableString2 = new SpannableString((CharSequence)string7);
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse((String)"mailto://test@example.com"));
        Intent intent2 = new Intent("android.intent.action.VIEW", Uri.parse((String)"http://www.example.com"));
        PackageManager packageManager = this.a.getPackageManager();
        int n3 = 0;
        if (packageManager != null) {
            ResolveInfo resolveInfo = this.a.getPackageManager().resolveActivity(intent, 0);
            n3 = 0;
            if (resolveInfo != null) {
                n3 = 2;
            }
            if (this.a.getPackageManager().resolveActivity(intent2, 0) != null) {
                n3 |= 1;
            }
        }
        Linkify.addLinks((Spannable)spannableString2, (int)n3);
        StringBuilder stringBuilder = new StringBuilder().append("market://details?id=");
        if (string == null) {
            string = this.a.getPackageName();
        }
        String string8 = stringBuilder.append(string).toString();
        String string9 = "market://search?q=pub:" + this.a.getString(h.d.dev_name);
        SpannableString spannableString3 = new SpannableString((CharSequence)("store: " + string8 + "\n" + this.a.getString(h.d.text_my_apps) + ": " + string9));
        Linkify.addLinks((Spannable)spannableString3, (Pattern)Pattern.compile((String)"market://[A-Za-z0-9?=.:+]*"), (String)"market://");
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append((CharSequence)spannableString2);
        spannableStringBuilder.append((CharSequence)"\n");
        spannableStringBuilder.append((CharSequence)spannableString3);
        if (bl) {
            SpannableString spannableString4 = new SpannableString((CharSequence)this.a.getString(h.d.label_eula));
            spannableString4.setSpan((Object)new ClickableSpan(){

                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(b.this.a);
                    builder.setTitle(h.d.label_eula);
                    builder.setMessage(h.d.eula);
                    builder.setPositiveButton(17039370, new DialogInterface.OnClickListener(){

                        public void onClick(DialogInterface dialogInterface, int n2) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.show();
                }

            }, 0, spannableString4.length(), 33);
            spannableStringBuilder.append((CharSequence)"\n\n");
            spannableStringBuilder.append((CharSequence)spannableString4);
        }
        if (bl2 && !TextUtils.isEmpty((CharSequence)(spannableString = new SpannableString((CharSequence)this.a.getString(h.d.label_privacy))))) {
            spannableString.setSpan((Object)new ClickableSpan(this.a.getString(h.d.url_privacy)){
                final /* synthetic */ String a;

                public void onClick(View view) {
                    Intent intent = new Intent("android.intent.action.VIEW", Uri.parse((String)this.a));
                    b.this.a.startActivity(intent);
                }
            }, 0, spannableString.length(), 33);
            spannableStringBuilder.append((CharSequence)"\n\n");
            spannableStringBuilder.append((CharSequence)spannableString);
        }
        textView.setText((CharSequence)spannableStringBuilder);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        this.setTitle((CharSequence)string4);
        this.setView((View)textView);
        this.setButton(-1, this.a.getText(17039370), new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n2) {
                dialogInterface.dismiss();
            }
        });
    }

    private String a() {
        String string = "";
        try {
            if (this.a.getPackageManager() != null) {
                string = HfM5AFD8kd7A.ra6kJ1Wnv83AG9X((PackageManager)this.a.getPackageManager(), (String)this.a.getPackageName(), (int)0).versionName;
            }
            return string;
        }
        catch (PackageManager.NameNotFoundException var2_2) {
            a.c(b, (Throwable)var2_2);
            return string;
        }
    }

}

