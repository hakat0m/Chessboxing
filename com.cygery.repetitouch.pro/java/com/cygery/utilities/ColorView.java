/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.graphics.BitmapShader
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Shader
 *  android.graphics.Shader$TileMode
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.view.View
 *  java.lang.String
 */
package com.cygery.utilities;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

public class ColorView
extends View {
    private static final String a = ColorView.class.getName();
    private Bitmap b;
    private BitmapShader c;
    private Paint d;
    private int e;
    private boolean f;
    private int g;
    private Bitmap h;
    private int i;
    private int j;

    public ColorView(Context context) {
        super(context, null);
    }

    public ColorView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.e = -16777216;
        this.f = true;
        this.a(context, attributeSet);
    }

    public ColorView(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
        this.e = -16777216;
        this.f = true;
        this.a(context, attributeSet);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void a() {
        float f2 = this.g;
        if (this.i <= 0 || this.j <= 0) {
            return;
        }
        int n2 = (int)(0.5f + (float)this.i / f2 / 2.0f);
        int n3 = (int)(0.5f + (float)this.j / f2 / 2.0f);
        int n4 = n2 < 1 ? 1 : n2;
        int n5 = n3 < 1 ? 1 : n3;
        Bitmap bitmap = Bitmap.createBitmap((int)(n4 * 2), (int)(n5 * 2), (Bitmap.Config)Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawRect(0.0f, 0.0f, (float)canvas.getWidth(), (float)canvas.getHeight(), this.d);
        this.h = Bitmap.createBitmap((int)this.i, (int)this.j, (Bitmap.Config)Bitmap.Config.ARGB_8888);
        Canvas canvas2 = new Canvas(this.h);
        canvas2.scale((float)this.i / (float)(n4 * 2), (float)this.j / (float)(n5 * 2));
        canvas2.drawBitmap(bitmap, 0.0f, 0.0f, null);
    }

    protected void a(Context context, AttributeSet attributeSet) {
        this.b = Bitmap.createBitmap((int)2, (int)2, (Bitmap.Config)Bitmap.Config.ARGB_8888);
        this.b.setPixel(0, 0, -4144960);
        this.b.setPixel(0, 1, -9408400);
        this.b.setPixel(1, 0, -9408400);
        this.b.setPixel(1, 1, -4144960);
        this.c = new BitmapShader(this.b, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        this.d = new Paint();
        this.d.setShader((Shader)this.c);
        this.g = (int)(0.5 + (double)(7.0f * context.getResources().getDisplayMetrics().density));
    }

    public boolean getDrawPattern() {
        return this.f;
    }

    public void onDraw(Canvas canvas) {
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        if (this.f) {
            canvas.drawBitmap(this.h, 0.0f, 0.0f, null);
        }
        canvas.drawColor(this.e);
    }

    protected void onSizeChanged(int n2, int n3, int n4, int n5) {
        super.onSizeChanged(n2, n3, n4, n5);
        this.i = n2;
        this.j = n3;
        if (this.f) {
            super.a();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setAlpha(float f2) {
        float f3 = 1.0f;
        float f4 = f2 FCMPG 0.0f;
        float f5 = 0.0f;
        if (f4 >= 0) {
            f5 = f2;
        }
        if (f5 <= f3) {
            f3 = f5;
        }
        this.e = (int)(f3 * 255.0f) << 24 | 16777215 & this.e;
        this.invalidate();
    }

    public void setColor(int n2) {
        this.e = n2;
        this.invalidate();
    }

    public void setDrawPattern(boolean bl) {
        if (!this.f && bl) {
            super.a();
        }
        this.f = bl;
    }

    public void setPatternSize(int n2) {
        if (n2 <= 0) {
            return;
        }
        this.g = n2;
        super.a();
    }
}

