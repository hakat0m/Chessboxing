package com.gumtree.android.srp;

import android.view.View;
import android.view.View.OnClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class AdvertGridItemLayout$$Lambda$1 implements OnClickListener {
    private final AdvertGridItemLayout arg$1;

    private AdvertGridItemLayout$$Lambda$1(AdvertGridItemLayout advertGridItemLayout) {
        this.arg$1 = advertGridItemLayout;
    }

    public static OnClickListener lambdaFactory$(AdvertGridItemLayout advertGridItemLayout) {
        return new AdvertGridItemLayout$$Lambda$1(advertGridItemLayout);
    }

    @Hidden
    public void onClick(View view) {
        this.arg$1.lambda$new$0(view);
    }
}
