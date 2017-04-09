package com.ebay.kleinanzeigen.imagepicker.platform;

import android.support.v4.view.ViewPager.PageTransformer;
import android.view.View;
import uk.co.senab.photoview.IPhotoView;

public class DepthPageTransformer implements PageTransformer {
    private static final float MIN_SCALE = 0.75f;

    public void transformPage(View view, float f) {
        int width = view.getWidth();
        if (f < -1.0f) {
            view.setAlpha(0.0f);
        } else if (f <= 0.0f) {
            view.setAlpha(IPhotoView.DEFAULT_MIN_SCALE);
            view.setTranslationX(0.0f);
            view.setScaleX(IPhotoView.DEFAULT_MIN_SCALE);
            view.setScaleY(IPhotoView.DEFAULT_MIN_SCALE);
        } else if (f <= IPhotoView.DEFAULT_MIN_SCALE) {
            view.setAlpha(IPhotoView.DEFAULT_MIN_SCALE - f);
            view.setTranslationX(((float) width) * (-f));
            float abs = MIN_SCALE + (0.25f * (IPhotoView.DEFAULT_MIN_SCALE - Math.abs(f)));
            view.setScaleX(abs);
            view.setScaleY(abs);
        } else {
            view.setAlpha(0.0f);
        }
    }
}
