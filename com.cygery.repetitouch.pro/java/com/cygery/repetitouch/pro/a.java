/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.graphics.drawable.Drawable
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.ArrayAdapter
 *  android.widget.LinearLayout
 *  android.widget.TextView
 *  java.lang.AssertionError
 *  java.lang.CharSequence
 *  java.lang.Object
 *  java.lang.String
 *  java.text.DecimalFormat
 *  java.util.List
 */
package com.cygery.repetitouch.pro;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.cygery.repetitouch.MergeItem;
import java.text.DecimalFormat;
import java.util.List;

public class a
extends ArrayAdapter<MergeItem> {
    static final /* synthetic */ boolean a;
    private static final String b;
    private static final DecimalFormat f;
    private final int c;
    private final List<MergeItem> d;
    private final LayoutInflater e;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !a.class.desiredAssertionStatus();
        a = bl;
        b = a.class.getName();
        f = new DecimalFormat("#.###");
    }

    public a(Context context, int n2, List<MergeItem> list) {
        super(context, n2, list);
        this.e = (LayoutInflater)context.getSystemService("layout_inflater");
        this.c = n2;
        this.d = list;
    }

    /*
     * Enabled aggressive block sorting
     */
    public View getView(int n2, View view, ViewGroup viewGroup) {
        MergeItem mergeItem = (MergeItem)this.getItem(n2);
        if (view == null) {
            view = this.e.inflate(this.c, null);
        }
        if (!a && view == null) {
            throw new AssertionError();
        }
        TextView textView = (TextView)view.findViewById(2131558590);
        TextView textView2 = (TextView)view.findViewById(2131558591);
        TextView textView3 = (TextView)view.findViewById(2131558599);
        TextView textView4 = (TextView)view.findViewById(2131558497);
        TextView textView5 = (TextView)view.findViewById(2131558596);
        TextView textView6 = (TextView)view.findViewById(2131558597);
        TextView textView7 = (TextView)view.findViewById(2131558600);
        TextView textView8 = (TextView)view.findViewById(2131558601);
        TextView textView9 = (TextView)view.findViewById(2131558602);
        LinearLayout linearLayout = (LinearLayout)view.findViewById(2131558593);
        LinearLayout linearLayout2 = (LinearLayout)view.findViewById(2131558594);
        LinearLayout linearLayout3 = (LinearLayout)view.findViewById(2131558598);
        textView.setText((CharSequence)("" + (n2 + 1) + "."));
        if (mergeItem.b() != null && !mergeItem.b().isEmpty()) {
            textView2.setTextColor(this.getContext().getResources().getColor(17170433));
            textView2.setText((CharSequence)mergeItem.b());
        } else {
            textView2.setTextColor(this.getContext().getResources().getColor(17170437));
            textView2.setText((CharSequence)this.getContext().getString(2131165365));
        }
        textView3.setText((CharSequence)MergeItem.b(mergeItem.i()));
        textView7.setText((CharSequence)("" + mergeItem.e()));
        Drawable drawable = this.getContext().getResources().getDrawable(2130837609);
        if (drawable != null) {
            drawable.setBounds(0, 0, textView7.getLineHeight(), textView7.getLineHeight());
            textView7.setCompoundDrawables(null, null, drawable, null);
        }
        textView8.setText((CharSequence)f.format(mergeItem.f()));
        Drawable drawable2 = this.getContext().getResources().getDrawable(2130837637);
        if (drawable2 != null) {
            drawable2.setBounds(0, 0, textView8.getLineHeight(), textView8.getLineHeight());
            textView8.setCompoundDrawables(null, null, drawable2, null);
        }
        textView9.setText((CharSequence)mergeItem.m());
        linearLayout.setVisibility(4);
        linearLayout2.setVisibility(4);
        linearLayout3.setVisibility(4);
        if (this.getContext().getTheme() != null) {
            if (mergeItem.a().equals((Object)MergeItem.Type.TYPE_FILE)) {
                textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 2130837616);
                linearLayout.setVisibility(0);
                textView4.setText((CharSequence)mergeItem.g());
            } else if (mergeItem.a().equals((Object)MergeItem.Type.TYPE_SUBLIST)) {
                textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 2130837624);
                linearLayout2.setVisibility(0);
                textView5.setText((CharSequence)mergeItem.c(this.getContext().getString(2131165383)));
                textView6.setText((CharSequence)mergeItem.d(this.getContext().getString(2131165356)));
            } else if (mergeItem.a().equals((Object)MergeItem.Type.TYPE_PAUSE)) {
                textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 2130837608);
                linearLayout3.setVisibility(0);
            }
        }
        if (!a && view == null) {
            throw new AssertionError();
        }
        view.setBackgroundResource(2130837644);
        return view;
    }
}

