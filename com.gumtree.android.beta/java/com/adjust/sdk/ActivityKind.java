package com.adjust.sdk;

import com.nostra13.universalimageloader.core.ImageLoaderConfiguration$Builder;

public enum ActivityKind {
    UNKNOWN,
    SESSION,
    EVENT,
    CLICK,
    ATTRIBUTION,
    REVENUE,
    REATTRIBUTION;

    /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$adjust$sdk$ActivityKind = null;

        static {
            $SwitchMap$com$adjust$sdk$ActivityKind = new int[ActivityKind.values().length];
            try {
                $SwitchMap$com$adjust$sdk$ActivityKind[ActivityKind.SESSION.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$adjust$sdk$ActivityKind[ActivityKind.EVENT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$adjust$sdk$ActivityKind[ActivityKind.CLICK.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$adjust$sdk$ActivityKind[ActivityKind.ATTRIBUTION.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public static ActivityKind fromString(String str) {
        if ("session".equals(str)) {
            return SESSION;
        }
        if ("event".equals(str)) {
            return EVENT;
        }
        if ("click".equals(str)) {
            return CLICK;
        }
        if ("attribution".equals(str)) {
            return ATTRIBUTION;
        }
        return UNKNOWN;
    }

    public String toString() {
        switch (AnonymousClass1.$SwitchMap$com$adjust$sdk$ActivityKind[ordinal()]) {
            case HighlightView.GROW_NONE /*1*/:
                return "session";
            case HighlightView.GROW_LEFT_EDGE /*2*/:
                return "event";
            case ImageLoaderConfiguration$Builder.DEFAULT_THREAD_POOL_SIZE /*3*/:
                return "click";
            case HighlightView.GROW_RIGHT_EDGE /*4*/:
                return "attribution";
            default:
                return "unknown";
        }
    }
}
