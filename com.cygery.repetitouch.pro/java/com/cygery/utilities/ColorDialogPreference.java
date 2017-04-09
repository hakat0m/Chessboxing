/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.graphics.drawable.Drawable
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.preference.DialogPreference
 *  android.preference.Preference
 *  android.preference.Preference$BaseSavedState
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.SeekBar
 *  android.widget.SeekBar$OnSeekBarChangeListener
 *  android.widget.TextView
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 */
package com.cygery.utilities;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.DialogPreference;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import com.cygery.utilities.ColorView;
import com.cygery.utilities.h;

public class ColorDialogPreference
extends DialogPreference {
    private static final String a = ColorDialogPreference.class.getName();
    private int b;
    private int c;
    private SeekBar d;
    private SeekBar e;
    private SeekBar f;
    private SeekBar g;
    private TextView h;
    private TextView i;
    private TextView j;
    private TextView k;
    private ColorView l;
    private ColorView m;
    private ColorView n;
    private SeekBar.OnSeekBarChangeListener o;

    public ColorDialogPreference(Context context) {
        super(context, null);
    }

    public ColorDialogPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.o = new SeekBar.OnSeekBarChangeListener(){

            /*
             * Enabled aggressive block sorting
             */
            public void onProgressChanged(SeekBar seekBar, int n2, boolean bl) {
                if (seekBar == ColorDialogPreference.this.d) {
                    ColorDialogPreference.this.a(n2);
                    ColorDialogPreference.this.h.setText((CharSequence)String.valueOf((int)n2));
                    ColorDialogPreference.this.c();
                    return;
                } else {
                    if (seekBar == ColorDialogPreference.this.e) {
                        ColorDialogPreference.this.b(n2);
                        ColorDialogPreference.this.i.setText((CharSequence)String.valueOf((int)n2));
                        ColorDialogPreference.this.c();
                        return;
                    }
                    if (seekBar == ColorDialogPreference.this.f) {
                        ColorDialogPreference.this.c(n2);
                        ColorDialogPreference.this.j.setText((CharSequence)String.valueOf((int)n2));
                        ColorDialogPreference.this.c();
                        return;
                    }
                    if (seekBar != ColorDialogPreference.this.g) return;
                    {
                        ColorDialogPreference.this.d(n2);
                        ColorDialogPreference.this.k.setText((CharSequence)String.valueOf((int)n2));
                        ColorDialogPreference.this.c();
                        return;
                    }
                }
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        };
        this.a(context, attributeSet);
    }

    public ColorDialogPreference(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
        this.o = new ;
        this.a(context, attributeSet);
    }

    private void a() {
        if (this.l != null) {
            this.l.setColor(this.b);
        }
    }

    private void a(int n2) {
        this.c = -16711681 & this.c | (n2 & 255) << 16;
    }

    private void b() {
        int n2 = this.d();
        int n3 = this.e();
        int n4 = this.f();
        int n5 = this.g();
        this.h.setText((CharSequence)String.valueOf((int)n2));
        this.i.setText((CharSequence)String.valueOf((int)n3));
        this.j.setText((CharSequence)String.valueOf((int)n4));
        this.k.setText((CharSequence)String.valueOf((int)n5));
        this.d.setProgress(n2);
        this.e.setProgress(n3);
        this.f.setProgress(n4);
        this.g.setProgress(n5);
    }

    private void b(int n2) {
        this.c = -65281 & this.c | (n2 & 255) << 8;
    }

    private void c() {
        if (this.m != null) {
            this.m.setColor(this.b);
        }
        if (this.n != null) {
            this.n.setColor(this.c);
        }
    }

    private void c(int n2) {
        this.c = -256 & this.c | n2 & 255;
    }

    private int d() {
        return 255 & this.b >> 16;
    }

    private void d(int n2) {
        this.c = 16777215 & this.c | (n2 & 255) << 24;
    }

    private int e() {
        return 255 & this.b >> 8;
    }

    private int f() {
        return 255 & this.b;
    }

    private int g() {
        return 255 & this.b >> 24;
    }

    protected void a(Context context, AttributeSet attributeSet) {
        this.setWidgetLayoutResource(h.c.preference_widget_color);
        this.setDialogLayoutResource(h.c.color_dialog);
        this.setPositiveButtonText(17039370);
        this.setNegativeButtonText(17039360);
        this.setDialogIcon(null);
        this.c = this.b = this.getPersistedInt(0);
    }

    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);
        this.d = (SeekBar)view.findViewById(h.b.seekBarRed);
        this.e = (SeekBar)view.findViewById(h.b.seekBarGreen);
        this.f = (SeekBar)view.findViewById(h.b.seekBarBlue);
        this.g = (SeekBar)view.findViewById(h.b.seekBarAlpha);
        this.h = (TextView)view.findViewById(h.b.textViewValueRed);
        this.i = (TextView)view.findViewById(h.b.textViewValueGreen);
        this.j = (TextView)view.findViewById(h.b.textViewValueBlue);
        this.k = (TextView)view.findViewById(h.b.textViewValueAlpha);
        this.d.setOnSeekBarChangeListener(this.o);
        this.e.setOnSeekBarChangeListener(this.o);
        this.f.setOnSeekBarChangeListener(this.o);
        this.g.setOnSeekBarChangeListener(this.o);
        this.m = (ColorView)view.findViewById(h.b.colorDialogCurrentPreviewView);
        this.n = (ColorView)view.findViewById(h.b.colorDialogNewPreviewView);
        super.b();
        super.c();
    }

    public void onBindView(View view) {
        super.onBindView(view);
        this.l = (ColorView)view.findViewById(h.b.colorPreferencePreviewView);
        super.a();
    }

    protected View onCreateView(ViewGroup viewGroup) {
        return super.onCreateView(viewGroup);
    }

    public void onDependencyChanged(Preference preference, boolean bl) {
        super.onDependencyChanged(preference, bl);
    }

    protected void onDialogClosed(boolean bl) {
        super.onDialogClosed(bl);
        if (bl) {
            this.persistInt(this.c);
            this.b = this.c;
            super.a();
        }
        this.c = 0;
    }

    protected Object onGetDefaultValue(TypedArray typedArray, int n2) {
        return typedArray.getInt(n2, 0);
    }

    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable == null || !parcelable.getClass().equals((Object)a.class)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        a a2 = (a)parcelable;
        this.b = a2.a;
        super.onRestoreInstanceState(a2.getSuperState());
    }

    protected Parcelable onSaveInstanceState() {
        Parcelable parcelable = super.onSaveInstanceState();
        if (this.isPersistent()) {
            return parcelable;
        }
        a a2 = new a(parcelable);
        a2.a = this.b;
        return a2;
    }

    protected void onSetInitialValue(boolean bl, Object object) {
        if (bl) {
            this.b = this.getPersistedInt(0);
            return;
        }
        this.b = (Integer)object;
        this.persistInt(this.b);
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

