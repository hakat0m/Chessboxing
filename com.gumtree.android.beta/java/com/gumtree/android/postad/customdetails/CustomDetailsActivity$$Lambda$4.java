package com.gumtree.android.postad.customdetails;

import com.gumtree.android.postad.views.attribute.VRMMileageWrapperAttributeView.VRMLookupRequestListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class CustomDetailsActivity$$Lambda$4 implements VRMLookupRequestListener {
    private final CustomDetailsActivity arg$1;

    private CustomDetailsActivity$$Lambda$4(CustomDetailsActivity customDetailsActivity) {
        this.arg$1 = customDetailsActivity;
    }

    public static VRMLookupRequestListener lambdaFactory$(CustomDetailsActivity customDetailsActivity) {
        return new CustomDetailsActivity$$Lambda$4(customDetailsActivity);
    }

    @Hidden
    public void requestVRMLookup(String str) {
        this.arg$1.lambda$populateCustomAttributes$3(str);
    }
}
