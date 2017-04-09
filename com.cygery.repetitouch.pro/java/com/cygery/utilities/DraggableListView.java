/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.graphics.Bitmap
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Handler
 *  android.util.AttributeSet
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.ViewConfiguration
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.WindowManager
 *  android.view.WindowManager$LayoutParams
 *  android.widget.ImageView
 *  android.widget.ListView
 *  java.lang.AssertionError
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 */
package com.cygery.utilities;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;

public class DraggableListView
extends ListView {
    static final /* synthetic */ boolean a;
    private static final String b;
    private int A = 50;
    private Runnable B;
    private Context c;
    private WindowManager d;
    private Handler e;
    private boolean f;
    private a g;
    private int h;
    private boolean i;
    private Bitmap j;
    private WindowManager.LayoutParams k;
    private ImageView l;
    private int m;
    private int n;
    private int o;
    private int p;
    private int q;
    private int r;
    private int s;
    private int t;
    private int u;
    private int v;
    private int w;
    private int x;
    private Rect y = new Rect();
    private int z;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !DraggableListView.class.desiredAssertionStatus();
        a = bl;
        b = DraggableListView.class.getName();
    }

    public DraggableListView(Context context) {
        super(context);
        this.B = new Runnable(){

            public void run() {
                if (DraggableListView.this.z != 0) {
                    DraggableListView.this.smoothScrollBy(DraggableListView.this.z, DraggableListView.this.A);
                    DraggableListView.this.e.postDelayed(DraggableListView.this.B, (long)DraggableListView.this.A);
                }
            }
        };
        super.a(context, null, 0);
    }

    public DraggableListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.B = new ;
        super.a(context, attributeSet, 0);
    }

    public DraggableListView(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
        this.B = new ;
        super.a(context, attributeSet, n2);
    }

    private void a(int n2) {
        this.s = Math.min((int)(n2 - this.x), (int)(this.w / 3));
        this.t = Math.max((int)(n2 + this.x), (int)(2 * this.w / 3));
    }

    private void a(Context context, AttributeSet attributeSet, int n2) {
        this.c = context;
        this.d = (WindowManager)context.getSystemService("window");
        this.e = new Handler();
        this.x = ViewConfiguration.get((Context)context).getScaledTouchSlop();
        this.f = true;
    }

    private void b() {
        this.k.x = this.o + this.q;
    }

    private void b(int n2) {
        if (n2 > this.w / 3) {
            this.s = this.w / 3;
        }
        if (n2 < 2 * this.w / 3) {
            this.t = 2 * this.w / 3;
        }
    }

    private int c(int n2) {
        int n3 = this.r + (n2 - this.n);
        int n4 = this.getChildCount();
        for (int i2 = 0; i2 < n4; ++i2) {
            View view = this.getChildAt(i2);
            if (!a && view == null) {
                throw new AssertionError();
            }
            view.getHitRect(this.y);
            if (this.y.top < n3) continue;
            return i2 + this.getFirstVisiblePosition();
        }
        return n4 + this.getFirstVisiblePosition();
    }

    /*
     * Enabled aggressive block sorting
     */
    private int d(int n2) {
        int n3;
        if (n2 > this.t) {
            n3 = 8;
            if (n2 > this.t + 3 * (this.w - this.t) / 4) {
                n3 = 16;
            }
            if (n2 > this.t + 2 * (this.w - this.t) / 4) {
                n3 *= 2;
            }
            if (n2 <= this.t + (this.w - this.t) / 4) return n3;
            {
                n3 *= 2;
                return n3;
            }
        } else {
            int n4 = this.s;
            n3 = 0;
            if (n2 >= n4) return n3;
            {
                n3 = -8;
                if (n2 < 3 * this.s / 4) {
                    n3 = -16;
                }
                if (n2 < 2 * this.s / 4) {
                    n3 *= 2;
                }
                if (n2 >= this.s / 4) return n3;
                return n3 * 2;
            }
        }
    }

    protected void a() {
        this.i = false;
        if (this.l != null) {
            this.l.setVisibility(4);
            this.d.removeView((View)this.l);
            this.l.setImageDrawable(null);
            this.l = null;
        }
        if (this.j != null) {
            this.j.recycle();
            this.j = null;
        }
        if (this.g != null) {
            this.g.a(this.u, this.v);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void a(int n2, MotionEvent motionEvent) {
        TypedArray typedArray;
        int n3;
        this.i = true;
        this.u = n2;
        this.w = this.getHeight();
        View view = this.getChildAt(n2 - this.getFirstVisiblePosition());
        if (view == null) {
            this.a();
            return;
        }
        int n4 = (int)motionEvent.getX();
        int n5 = (int)motionEvent.getY();
        this.m = n4;
        this.n = n5;
        this.o = (int)motionEvent.getRawX() - n4;
        this.p = (int)motionEvent.getRawY() - n5;
        this.q = view.getLeft();
        this.r = view.getTop();
        super.a(n5);
        view.setDrawingCacheEnabled(true);
        view.invalidate();
        this.j = Bitmap.createBitmap((Bitmap)view.getDrawingCache());
        this.k = new WindowManager.LayoutParams(-2, -2, 2, 792, -3);
        this.k.gravity = 51;
        this.k.x = this.o + this.q + (n4 - this.m);
        this.k.y = this.p + this.r + (n5 - this.n);
        super.b();
        ImageView imageView = new ImageView(this.c);
        if (this.c.getTheme() != null && (typedArray = this.c.getTheme().obtainStyledAttributes(new int[]{16842801})) != null) {
            n3 = typedArray.getColor(0, -1);
            typedArray.recycle();
        } else {
            n3 = -1;
        }
        imageView.setBackgroundColor(-738197504 | n3 & 16777215);
        imageView.setPadding(0, 0, 0, 0);
        imageView.setImageBitmap(this.j);
        if (Build.VERSION.SDK_INT < 16) {
            imageView.setAlpha(212);
        } else {
            imageView.setImageAlpha(212);
        }
        this.d.addView((View)imageView, (ViewGroup.LayoutParams)this.k);
        this.l = imageView;
    }

    protected void a(MotionEvent motionEvent) {
        int n2 = (int)motionEvent.getX();
        int n3 = (int)motionEvent.getY();
        this.k.x = this.o + this.q + (n2 - this.m);
        this.k.y = this.p + this.r + (n3 - this.n);
        super.b();
        this.d.updateViewLayout((View)this.l, (ViewGroup.LayoutParams)this.k);
    }

    public int getDragHandleResId() {
        return this.h;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (!this.f) return super.onInterceptTouchEvent(motionEvent);
        {
            switch (motionEvent.getAction()) {
                default: {
                    return super.onInterceptTouchEvent(motionEvent);
                }
                case 0: {
                    int n2;
                    int n3 = (int)motionEvent.getX();
                    int n4 = this.pointToPosition(n3, n2 = (int)motionEvent.getY());
                    if (n4 == -1) return super.onInterceptTouchEvent(motionEvent);
                    if (this.h != 0) {
                        View view = this.getChildAt(n4 - this.getFirstVisiblePosition());
                        if (view == null) return super.onInterceptTouchEvent(motionEvent);
                        Rect rect = new Rect();
                        view.findViewById(this.h).getHitRect(rect);
                        if (!rect.contains(n3, n2 - view.getTop())) return super.onInterceptTouchEvent(motionEvent);
                        this.a(n4, motionEvent);
                        return true;
                    }
                    if (n3 >= 50) return super.onInterceptTouchEvent(motionEvent);
                    this.a(n4, motionEvent);
                    return true;
                }
            }
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean onTouchEvent(MotionEvent var1) {
        if (this.f == false) return super.onTouchEvent(var1);
        if (this.i == false) return super.onTouchEvent(var1);
        switch (var1.getAction()) {
            case 0: 
            case 2: {
                this.a(var1);
                var2_2 = (int)var1.getY();
                this.v = super.c(var2_2);
                super.b(var2_2);
                var3_3 = super.d(var2_2);
                if (var3_3 != 0) {
                    this.z = var3_3;
                    this.e.removeCallbacks(this.B);
                    this.e.post(this.B);
                    ** break;
                }
                this.e.removeCallbacks(this.B);
            }
lbl16: // 3 sources:
            default: {
                return true;
            }
            case 1: 
            case 3: 
        }
        this.e.removeCallbacks(this.B);
        this.a();
        return true;
    }

    public void setDragHandleResId(int n2) {
        this.h = n2;
    }

    public void setDraggingEnabled(boolean bl) {
        this.f = bl;
    }

    public void setDropListener(a a2) {
        this.g = a2;
    }

    public static interface a {
        public void a(int var1, int var2);
    }

}

