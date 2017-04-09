package com.gumtree.android.srp;

import android.view.View;
import android.view.View.OnClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class AdvertGalleryItemLayout$$Lambda$1 implements OnClickListener {
    private final AdvertGalleryItemLayout arg$1;

    private AdvertGalleryItemLayout$$Lambda$1(AdvertGalleryItemLayout advertGalleryItemLayout) {
        this.arg$1 = advertGalleryItemLayout;
    }

    public static OnClickListener lambdaFactory$(AdvertGalleryItemLayout advertGalleryItemLayout) {
        return new AdvertGalleryItemLayout$$Lambda$1(advertGalleryItemLayout);
    }

    @Hidden
    public void onClick(View view) {
        this.arg$1.lambda$new$0(view);
    }
}
