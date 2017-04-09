package com.afollestad.materialdialogs;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration$Builder;

public enum GravityEnum {
    START,
    CENTER,
    END;
    
    private static final boolean HAS_RTL = false;

    /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$afollestad$materialdialogs$GravityEnum = null;

        static {
            $SwitchMap$com$afollestad$materialdialogs$GravityEnum = new int[GravityEnum.values().length];
            try {
                $SwitchMap$com$afollestad$materialdialogs$GravityEnum[GravityEnum.START.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$afollestad$materialdialogs$GravityEnum[GravityEnum.CENTER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$afollestad$materialdialogs$GravityEnum[GravityEnum.END.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    @SuppressLint({"RtlHardcoded"})
    public int getGravityInt() {
        switch (AnonymousClass1.$SwitchMap$com$afollestad$materialdialogs$GravityEnum[ordinal()]) {
            case HighlightView.GROW_NONE /*1*/:
                return HAS_RTL ? 8388611 : 3;
            case HighlightView.GROW_LEFT_EDGE /*2*/:
                return 1;
            case ImageLoaderConfiguration$Builder.DEFAULT_THREAD_POOL_SIZE /*3*/:
                return HAS_RTL ? 8388613 : 5;
            default:
                throw new IllegalStateException("Invalid gravity constant");
        }
    }

    @TargetApi(17)
    public int getTextAlignment() {
        switch (AnonymousClass1.$SwitchMap$com$afollestad$materialdialogs$GravityEnum[ordinal()]) {
            case HighlightView.GROW_LEFT_EDGE /*2*/:
                return 4;
            case ImageLoaderConfiguration$Builder.DEFAULT_THREAD_POOL_SIZE /*3*/:
                return 6;
            default:
                return 5;
        }
    }
}
