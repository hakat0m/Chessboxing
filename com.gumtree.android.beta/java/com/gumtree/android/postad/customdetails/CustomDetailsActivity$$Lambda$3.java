package com.gumtree.android.postad.customdetails;

import com.gumtree.android.postad.customdetails.models.CustomAttributeData;
import com.gumtree.android.postad.views.attribute.AttributeView.OnFieldValueChangeListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class CustomDetailsActivity$$Lambda$3 implements OnFieldValueChangeListener {
    private final CustomDetailsActivity arg$1;
    private final CustomAttributeData arg$2;

    private CustomDetailsActivity$$Lambda$3(CustomDetailsActivity customDetailsActivity, CustomAttributeData customAttributeData) {
        this.arg$1 = customDetailsActivity;
        this.arg$2 = customAttributeData;
    }

    public static OnFieldValueChangeListener lambdaFactory$(CustomDetailsActivity customDetailsActivity, CustomAttributeData customAttributeData) {
        return new CustomDetailsActivity$$Lambda$3(customDetailsActivity, customAttributeData);
    }

    @Hidden
    public void onFieldValueChange(String str, String str2, String str3) {
        this.arg$1.lambda$populateCustomAttributes$2(this.arg$2, str, str2, str3);
    }
}
