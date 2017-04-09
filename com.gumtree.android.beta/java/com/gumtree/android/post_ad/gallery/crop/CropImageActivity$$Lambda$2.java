package com.gumtree.android.post_ad.gallery.crop;

import android.view.View;
import android.view.View.OnClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class CropImageActivity$$Lambda$2 implements OnClickListener {
    private final CropImageActivity arg$1;

    private CropImageActivity$$Lambda$2(CropImageActivity cropImageActivity) {
        this.arg$1 = cropImageActivity;
    }

    public static OnClickListener lambdaFactory$(CropImageActivity cropImageActivity) {
        return new CropImageActivity$$Lambda$2(cropImageActivity);
    }

    @Hidden
    public void onClick(View view) {
        this.arg$1.lambda$onCreate$1(view);
    }
}
