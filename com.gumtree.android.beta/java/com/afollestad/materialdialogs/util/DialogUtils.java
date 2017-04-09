package com.afollestad.materialdialogs.util;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.ArrayRes;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.Builder;

public class DialogUtils {

    final class AnonymousClass1 implements Runnable {
        final /* synthetic */ Builder val$builder;
        final /* synthetic */ MaterialDialog val$dialog;

        AnonymousClass1(MaterialDialog materialDialog, Builder builder) {
            this.val$dialog = materialDialog;
            this.val$builder = builder;
        }

        public void run() {
            this.val$dialog.getInputEditText().requestFocus();
            InputMethodManager inputMethodManager = (InputMethodManager) this.val$builder.getContext().getSystemService("input_method");
            if (inputMethodManager != null) {
                inputMethodManager.showSoftInput(this.val$dialog.getInputEditText(), 2);
            }
        }
    }

    /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$afollestad$materialdialogs$GravityEnum = new int[GravityEnum.values().length];

        static {
            try {
                $SwitchMap$com$afollestad$materialdialogs$GravityEnum[GravityEnum.CENTER.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$afollestad$materialdialogs$GravityEnum[GravityEnum.END.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    public static int adjustAlpha(int i, float f) {
        return Color.argb(Math.round(((float) Color.alpha(i)) * f), Color.red(i), Color.green(i), Color.blue(i));
    }

    public static int resolveColor(Context context, @AttrRes int i) {
        return resolveColor(context, i, 0);
    }

    public static int resolveColor(Context context, @AttrRes int i, int i2) {
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(new int[]{i});
        try {
            int color = obtainStyledAttributes.getColor(0, i2);
            return color;
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    public static ColorStateList resolveActionTextColorStateList(Context context, @AttrRes int i, ColorStateList colorStateList) {
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(new int[]{i});
        try {
            TypedValue peekValue = obtainStyledAttributes.peekValue(0);
            if (peekValue == null) {
                return colorStateList;
            }
            if (peekValue.type < 28 || peekValue.type > 31) {
                ColorStateList colorStateList2 = obtainStyledAttributes.getColorStateList(0);
                if (colorStateList2 != null) {
                    obtainStyledAttributes.recycle();
                    return colorStateList2;
                }
                obtainStyledAttributes.recycle();
                return colorStateList;
            }
            colorStateList = getActionTextStateList(context, peekValue.data);
            obtainStyledAttributes.recycle();
            return colorStateList;
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    public static ColorStateList getActionTextColorStateList(Context context, @ColorRes int i) {
        TypedValue typedValue = new TypedValue();
        context.getResources().getValue(i, typedValue, true);
        if (typedValue.type >= 28 && typedValue.type <= 31) {
            return getActionTextStateList(context, typedValue.data);
        }
        if (VERSION.SDK_INT <= 22) {
            return context.getResources().getColorStateList(i);
        }
        return context.getColorStateList(i);
    }

    public static int getColor(Context context, @ColorRes int i) {
        if (VERSION.SDK_INT <= 22) {
            return context.getResources().getColor(i);
        }
        return context.getColor(i);
    }

    public static String resolveString(Context context, @AttrRes int i) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(i, typedValue, true);
        return (String) typedValue.string;
    }

    private static int gravityEnumToAttrInt(GravityEnum gravityEnum) {
        switch (AnonymousClass2.$SwitchMap$com$afollestad$materialdialogs$GravityEnum[gravityEnum.ordinal()]) {
            case HighlightView.GROW_NONE /*1*/:
                return 1;
            case HighlightView.GROW_LEFT_EDGE /*2*/:
                return 2;
            default:
                return 0;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.afollestad.materialdialogs.GravityEnum resolveGravityEnum(android.content.Context r3, @android.support.annotation.AttrRes int r4, com.afollestad.materialdialogs.GravityEnum r5) {
        /*
        r2 = 0;
        r0 = r3.getTheme();
        r1 = 1;
        r1 = new int[r1];
        r1[r2] = r4;
        r1 = r0.obtainStyledAttributes(r1);
        r0 = 0;
        r2 = gravityEnumToAttrInt(r5);	 Catch:{ all -> 0x002c }
        r0 = r1.getInt(r0, r2);	 Catch:{ all -> 0x002c }
        switch(r0) {
            case 1: goto L_0x0020;
            case 2: goto L_0x0026;
            default: goto L_0x001a;
        };	 Catch:{ all -> 0x002c }
    L_0x001a:
        r0 = com.afollestad.materialdialogs.GravityEnum.START;	 Catch:{ all -> 0x002c }
        r1.recycle();
    L_0x001f:
        return r0;
    L_0x0020:
        r0 = com.afollestad.materialdialogs.GravityEnum.CENTER;	 Catch:{ all -> 0x002c }
        r1.recycle();
        goto L_0x001f;
    L_0x0026:
        r0 = com.afollestad.materialdialogs.GravityEnum.END;	 Catch:{ all -> 0x002c }
        r1.recycle();
        goto L_0x001f;
    L_0x002c:
        r0 = move-exception;
        r1.recycle();
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.afollestad.materialdialogs.util.DialogUtils.resolveGravityEnum(android.content.Context, int, com.afollestad.materialdialogs.GravityEnum):com.afollestad.materialdialogs.GravityEnum");
    }

    public static Drawable resolveDrawable(Context context, @AttrRes int i) {
        return resolveDrawable(context, i, null);
    }

    private static Drawable resolveDrawable(Context context, @AttrRes int i, Drawable drawable) {
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(new int[]{i});
        try {
            Drawable drawable2 = obtainStyledAttributes.getDrawable(0);
            if (drawable2 != null || drawable == null) {
                drawable = drawable2;
            }
            obtainStyledAttributes.recycle();
            return drawable;
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
        }
    }

    public static int resolveDimension(Context context, @AttrRes int i) {
        return resolveDimension(context, i, -1);
    }

    private static int resolveDimension(Context context, @AttrRes int i, int i2) {
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(new int[]{i});
        try {
            int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(0, i2);
            return dimensionPixelSize;
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    public static boolean resolveBoolean(Context context, @AttrRes int i, boolean z) {
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(new int[]{i});
        try {
            boolean z2 = obtainStyledAttributes.getBoolean(0, z);
            return z2;
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    public static boolean resolveBoolean(Context context, @AttrRes int i) {
        return resolveBoolean(context, i, false);
    }

    public static boolean isColorDark(int i) {
        return 1.0d - ((((0.299d * ((double) Color.red(i))) + (0.587d * ((double) Color.green(i)))) + (0.114d * ((double) Color.blue(i)))) / 255.0d) >= 0.5d;
    }

    public static void setBackgroundCompat(View view, Drawable drawable) {
        if (VERSION.SDK_INT < 16) {
            view.setBackgroundDrawable(drawable);
        } else {
            view.setBackground(drawable);
        }
    }

    public static void showKeyboard(@NonNull DialogInterface dialogInterface, @NonNull Builder builder) {
        MaterialDialog materialDialog = (MaterialDialog) dialogInterface;
        if (materialDialog.getInputEditText() != null) {
            materialDialog.getInputEditText().post(new AnonymousClass1(materialDialog, builder));
        }
    }

    public static void hideKeyboard(@NonNull DialogInterface dialogInterface, @NonNull Builder builder) {
        MaterialDialog materialDialog = (MaterialDialog) dialogInterface;
        if (materialDialog.getInputEditText() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) builder.getContext().getSystemService("input_method");
            if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(materialDialog.getCurrentFocus().getWindowToken(), 0);
            }
        }
    }

    public static ColorStateList getActionTextStateList(Context context, int i) {
        int resolveColor = resolveColor(context, 16842806);
        if (i == 0) {
            i = resolveColor;
        }
        r0 = new int[2][];
        r0[0] = new int[]{-16842910};
        r0[1] = new int[0];
        return new ColorStateList(r0, new int[]{adjustAlpha(i, 0.4f), i});
    }

    public static int[] getColorArray(@NonNull Context context, @ArrayRes int i) {
        if (i == 0) {
            return null;
        }
        TypedArray obtainTypedArray = context.getResources().obtainTypedArray(i);
        int[] iArr = new int[obtainTypedArray.length()];
        for (int i2 = 0; i2 < obtainTypedArray.length(); i2++) {
            iArr[i2] = obtainTypedArray.getColor(i2, 0);
        }
        obtainTypedArray.recycle();
        return iArr;
    }
}
