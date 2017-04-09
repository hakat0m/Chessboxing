package com.gumtree.android.postad.customdetails;

import com.gumtree.android.postad.views.attribute.VRMMileageWrapperAttributeView.VRMManualEntryListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class CustomDetailsActivity$$Lambda$5 implements VRMManualEntryListener {
    private final CustomDetailsActivity arg$1;

    private CustomDetailsActivity$$Lambda$5(CustomDetailsActivity customDetailsActivity) {
        this.arg$1 = customDetailsActivity;
    }

    public static VRMManualEntryListener lambdaFactory$(CustomDetailsActivity customDetailsActivity) {
        return new CustomDetailsActivity$$Lambda$5(customDetailsActivity);
    }

    @Hidden
    public void onManualEntry() {
        this.arg$1.lambda$populateCustomAttributes$4();
    }
}
