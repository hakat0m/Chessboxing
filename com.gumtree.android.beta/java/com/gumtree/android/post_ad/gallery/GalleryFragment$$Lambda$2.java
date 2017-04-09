package com.gumtree.android.post_ad.gallery;

import android.view.View;
import android.view.View.OnClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class GalleryFragment$$Lambda$2 implements OnClickListener {
    private final GalleryFragment arg$1;

    private GalleryFragment$$Lambda$2(GalleryFragment galleryFragment) {
        this.arg$1 = galleryFragment;
    }

    public static OnClickListener lambdaFactory$(GalleryFragment galleryFragment) {
        return new GalleryFragment$$Lambda$2(galleryFragment);
    }

    @Hidden
    public void onClick(View view) {
        this.arg$1.lambda$setDoneAction$1(view);
    }
}
