package com.gumtree.android.postad.contactdetails;

import com.gumtree.android.postad.contactdetails.PostAdContactDetailsPresenter.View;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class GatedPostAdContactDetailsView$$Lambda$1 implements Action1 {
    private final GatedPostAdContactDetailsView arg$1;

    private GatedPostAdContactDetailsView$$Lambda$1(GatedPostAdContactDetailsView gatedPostAdContactDetailsView) {
        this.arg$1 = gatedPostAdContactDetailsView;
    }

    public static Action1 lambdaFactory$(GatedPostAdContactDetailsView gatedPostAdContactDetailsView) {
        return new GatedPostAdContactDetailsView$$Lambda$1(gatedPostAdContactDetailsView);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$new$0((View) obj);
    }
}
