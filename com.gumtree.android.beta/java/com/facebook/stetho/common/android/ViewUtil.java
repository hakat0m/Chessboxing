package com.facebook.stetho.common.android;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.PointF;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.facebook.stetho.common.Predicate;
import com.facebook.stetho.common.Util;
import javax.annotation.Nullable;

public final class ViewUtil {
    private ViewUtil() {
    }

    private static boolean isHittable(View view) {
        if (view.getVisibility() == 0 && ViewCompat.getInstance().getAlpha(view) >= 0.001f) {
            return true;
        }
        return false;
    }

    @Nullable
    public static View hitTest(View view, float f, float f2) {
        return hitTest(view, f, f2, null);
    }

    @Nullable
    public static View hitTest(View view, float f, float f2, @Nullable Predicate<View> predicate) {
        View hitTestImpl = hitTestImpl(view, f, f2, predicate, false);
        if (hitTestImpl == null) {
            return hitTestImpl(view, f, f2, predicate, true);
        }
        return hitTestImpl;
    }

    private static View hitTestImpl(View view, float f, float f2, @Nullable Predicate<View> predicate, boolean z) {
        if (!isHittable(view) || !pointInView(view, f, f2)) {
            return null;
        }
        if (predicate != null && !predicate.apply(view)) {
            return null;
        }
        if (!(view instanceof ViewGroup)) {
            return view;
        }
        view = (ViewGroup) view;
        if (view.getChildCount() > 0) {
            PointF pointF = new PointF();
            for (int childCount = view.getChildCount() - 1; childCount >= 0; childCount--) {
                View childAt = view.getChildAt(childCount);
                if (isTransformedPointInView(view, childAt, f, f2, pointF)) {
                    childAt = hitTestImpl(childAt, pointF.x, pointF.y, predicate, z);
                    if (childAt != null) {
                        return childAt;
                    }
                }
            }
        }
        if (!z) {
            view = null;
        }
        return view;
    }

    public static boolean pointInView(View view, float f, float f2) {
        return f >= 0.0f && f < ((float) (view.getRight() - view.getLeft())) && f2 >= 0.0f && f2 < ((float) (view.getBottom() - view.getTop()));
    }

    public static boolean isTransformedPointInView(ViewGroup viewGroup, View view, float f, float f2, @Nullable PointF pointF) {
        Util.throwIfNull(viewGroup);
        Util.throwIfNull(view);
        float scrollX = (((float) viewGroup.getScrollX()) + f) - ((float) view.getLeft());
        float scrollY = (((float) viewGroup.getScrollY()) + f2) - ((float) view.getTop());
        boolean pointInView = pointInView(view, scrollX, scrollY);
        if (pointInView && pointF != null) {
            pointF.set(scrollX, scrollY);
        }
        return pointInView;
    }

    @Nullable
    public static Activity tryGetActivity(View view) {
        if (view == null) {
            return null;
        }
        Activity tryGetActivity = tryGetActivity(view.getContext());
        if (tryGetActivity != null) {
            return tryGetActivity;
        }
        ViewParent parent = view.getParent();
        return parent instanceof View ? tryGetActivity((View) parent) : null;
    }

    @Nullable
    private static Activity tryGetActivity(Context context) {
        for (Context context2 = context; context2 != null; context2 = ((ContextWrapper) context2).getBaseContext()) {
            if (context2 instanceof Activity) {
                return (Activity) context2;
            }
            if (!(context2 instanceof ContextWrapper)) {
                return null;
            }
        }
        return null;
    }
}
