package com.gumtree.android.common.utils.ui;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.NonNull;
import rx.Observable;

public class BlurredImageTransformation {
    private static final float BITMAP_SCALE = 0.4f;
    private static final float BLUR_RADIUS = 7.5f;

    @TargetApi(17)
    public static Observable<Bitmap> blur(RenderScript renderScript, String str) {
        return Observable.create(new 1(str, renderScript));
    }

    @TargetApi(17)
    private static Bitmap blurBitmap(RenderScript renderScript, @NonNull Bitmap bitmap) {
        Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bitmap, Math.round(((float) bitmap.getWidth()) * BITMAP_SCALE), Math.round(((float) bitmap.getHeight()) * BITMAP_SCALE), false);
        Bitmap createBitmap = Bitmap.createBitmap(createScaledBitmap);
        ScriptIntrinsicBlur create = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        Allocation createFromBitmap = Allocation.createFromBitmap(renderScript, createScaledBitmap);
        Allocation createFromBitmap2 = Allocation.createFromBitmap(renderScript, createBitmap);
        create.setRadius(BLUR_RADIUS);
        create.setInput(createFromBitmap);
        create.forEach(createFromBitmap2);
        createFromBitmap2.copyTo(createBitmap);
        if (!createScaledBitmap.isRecycled()) {
            createScaledBitmap.recycle();
        }
        return createBitmap;
    }
}
