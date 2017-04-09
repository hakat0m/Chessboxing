/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.graphics.Paint
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.preference.Preference
 *  android.preference.Preference$BaseSavedState
 *  android.text.TextPaint
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewTreeObserver
 *  android.view.ViewTreeObserver$OnPreDrawListener
 *  android.widget.LinearLayout
 *  android.widget.RelativeLayout
 *  android.widget.SeekBar
 *  android.widget.SeekBar$OnSeekBarChangeListener
 *  android.widget.TextView
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 */
package com.cygery.utilities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.Preference;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import com.cygery.utilities.h;

public class SeekBarPreference
extends Preference
implements SeekBar.OnSeekBarChangeListener {
    private static final String a = SeekBarPreference.class.getName();
    private SeekBar b;
    private int c;
    private int d;
    private String e;
    private boolean f;
    private boolean g;
    private String h;
    private String i;
    private String j;
    private boolean k;
    private int l;
    private RelativeLayout m;
    private LinearLayout n;
    private TextView o;
    private TextView p;
    private TextView q;
    private TextView r;
    private TextView s;
    private TextView t;
    private TextView u;
    private Paint v;

    public SeekBarPreference(Context context) {
        super(context, null);
    }

    public SeekBarPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.a(context, attributeSet);
    }

    public SeekBarPreference(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
        this.a(context, attributeSet);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void a() {
        if (this.g) {
            this.p.setText((CharSequence)this.e);
            if (this.i != null) {
                this.q.setText((CharSequence)this.e);
            }
            this.r.setText((CharSequence)this.e);
        }
        if (this.h != null) {
            this.s.setText((CharSequence)this.h);
        } else {
            this.s.setText((CharSequence)String.valueOf((int)this.c));
        }
        if (this.i != null) {
            this.t.setText((CharSequence)this.i);
        }
        if (this.j != null) {
            this.u.setText((CharSequence)this.j);
            return;
        }
        this.u.setText((CharSequence)String.valueOf((int)this.d));
    }

    /*
     * Enabled aggressive block sorting
     */
    @SuppressLint(value={"NewApi"})
    private void b() {
        TextView textView = this.o;
        StringBuilder stringBuilder = new StringBuilder().append(String.valueOf((int)this.l));
        String string = this.g && this.e != null ? this.e : "";
        textView.setText((CharSequence)stringBuilder.append(string).toString());
        float f2 = this.v.measureText(this.o.getText().toString());
        int n2 = this.b.getThumbOffset();
        int n3 = (int)(0.5f + ((float)this.b.getLeft() + (float)n2 + (float)((this.b.getRight() - this.b.getLeft() - n2 * 2) * this.b.getProgress() / this.b.getMax()) - f2 / 2.0f));
        if (Build.VERSION.SDK_INT >= 11) {
            this.n.setX((float)n3);
        }
        if (this.k) {
            this.persistInt(this.l);
        }
    }

    protected void a(Context context, AttributeSet attributeSet) {
        TypedArray typedArray = this.getContext().obtainStyledAttributes(attributeSet, h.e.SeekBarPreference);
        if (typedArray != null) {
            this.c = typedArray.getInt(h.e.SeekBarPreference_min, 0);
            this.d = typedArray.getInt(h.e.SeekBarPreference_max, 100);
            this.e = typedArray.getString(h.e.SeekBarPreference_unit);
            this.f = typedArray.getBoolean(h.e.SeekBarPreference_showCurrentValue, true);
            this.g = typedArray.getBoolean(h.e.SeekBarPreference_showUnit, true);
            this.h = typedArray.getString(h.e.SeekBarPreference_minLabel);
            this.i = typedArray.getString(h.e.SeekBarPreference_medLabel);
            this.j = typedArray.getString(h.e.SeekBarPreference_maxLabel);
            this.k = typedArray.getBoolean(h.e.SeekBarPreference_persistOnSeekBarChange, true);
            typedArray.recycle();
        }
        this.setWidgetLayoutResource(h.c.preference_seekbar);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onBindView(View view) {
        super.onBindView(view);
        this.b = (SeekBar)view.findViewById(h.b.seekBar);
        this.o = (TextView)view.findViewById(h.b.textCurValue);
        this.n = (LinearLayout)view.findViewById(h.b.layoutCurValue);
        RelativeLayout relativeLayout = this.m = (RelativeLayout)view.findViewById(h.b.wrapperLayoutCurValue);
        int n2 = this.f ? 0 : 8;
        relativeLayout.setVisibility(n2);
        this.p = (TextView)view.findViewById(h.b.textUnitMin);
        this.q = (TextView)view.findViewById(h.b.textUnitMed);
        this.r = (TextView)view.findViewById(h.b.textUnitMax);
        this.s = (TextView)view.findViewById(h.b.textMinValue);
        this.t = (TextView)view.findViewById(h.b.textMedValue);
        this.u = (TextView)view.findViewById(h.b.textMaxValue);
        this.v = new Paint((Paint)this.o.getPaint());
        if (this.b != null) {
            this.b.setMax(this.d - this.c);
            this.b.setOnSeekBarChangeListener((SeekBar.OnSeekBarChangeListener)this);
            this.b.setEnabled(view.isEnabled());
            this.b.setProgress(this.l - this.c);
            ViewTreeObserver viewTreeObserver = this.n.getViewTreeObserver();
            if (viewTreeObserver != null) {
                viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener(){

                    public boolean onPreDraw() {
                        SeekBarPreference.this.b();
                        SeekBarPreference.this.n.getViewTreeObserver().removeOnPreDrawListener((ViewTreeObserver.OnPreDrawListener)this);
                        return true;
                    }
                });
            }
        }
        super.a();
    }

    protected View onCreateView(ViewGroup viewGroup) {
        View view = super.onCreateView(viewGroup);
        LinearLayout linearLayout = (LinearLayout)view;
        if (linearLayout != null) {
            linearLayout.setOrientation(1);
        }
        return view;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onDependencyChanged(Preference preference, boolean bl) {
        super.onDependencyChanged(preference, bl);
        if (this.b != null) {
            SeekBar seekBar = this.b;
            boolean bl2 = !bl;
            seekBar.setEnabled(bl2);
        }
    }

    protected Object onGetDefaultValue(TypedArray typedArray, int n2) {
        return typedArray.getInt(n2, 0);
    }

    public void onProgressChanged(SeekBar seekBar, int n2, boolean bl) {
        int n3 = n2 + this.c;
        if (n3 > this.d) {
            n3 = this.d;
        }
        if (n3 < this.c) {
            n3 = this.c;
        }
        if (!this.callChangeListener((Object)n3)) {
            seekBar.setProgress(this.l - this.c);
            return;
        }
        this.l = n3;
        super.b();
    }

    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable == null || !parcelable.getClass().equals((Object)a.class)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        a a2 = (a)parcelable;
        this.l = a2.a;
        super.onRestoreInstanceState(a2.getSuperState());
    }

    protected Parcelable onSaveInstanceState() {
        Parcelable parcelable = super.onSaveInstanceState();
        if (this.isPersistent()) {
            return parcelable;
        }
        a a2 = new a(parcelable);
        a2.a = this.l;
        return a2;
    }

    protected void onSetInitialValue(boolean bl, Object object) {
        if (bl) {
            this.l = this.getPersistedInt(0);
            return;
        }
        this.l = (Integer)object;
        this.persistInt(this.l);
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
        if (!this.k) {
            this.persistInt(this.l);
        }
    }

    public void setEnabled(boolean bl) {
        super.setEnabled(bl);
        if (this.b != null) {
            this.b.setEnabled(bl);
        }
    }

    private static class a
    extends Preference.BaseSavedState {
        public static final Parcelable.Creator<a> CREATOR = new Parcelable.Creator<a>(){

            public a a(Parcel parcel) {
                return new a(parcel);
            }

            public a[] a(int n2) {
                return new a[n2];
            }

            public /* synthetic */ Object createFromParcel(Parcel parcel) {
                return this.a(parcel);
            }

            public /* synthetic */ Object[] newArray(int n2) {
                return this.a(n2);
            }
        };
        int a;

        public a(Parcel parcel) {
            super(parcel);
            this.a = parcel.readInt();
        }

        public a(Parcelable parcelable) {
            super(parcelable);
        }

        public void writeToParcel(Parcel parcel, int n2) {
            super.writeToParcel(parcel, n2);
            parcel.writeInt(this.a);
        }

    }

}

