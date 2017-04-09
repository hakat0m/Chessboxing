package com.gumtree.android.postad.customdetails;

import com.gumtree.android.postad.views.attribute.FSBOAttributeView.PopupListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class CustomDetailsActivity$$Lambda$2 implements PopupListener {
    private final CustomDetailsActivity arg$1;

    private CustomDetailsActivity$$Lambda$2(CustomDetailsActivity customDetailsActivity) {
        this.arg$1 = customDetailsActivity;
    }

    public static PopupListener lambdaFactory$(CustomDetailsActivity customDetailsActivity) {
        return new CustomDetailsActivity$$Lambda$2(customDetailsActivity);
    }

    @Hidden
    public void popupShown() {
        this.arg$1.lambda$populateCustomAttributes$1();
    }
}
