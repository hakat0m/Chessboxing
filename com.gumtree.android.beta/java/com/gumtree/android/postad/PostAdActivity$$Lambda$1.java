package com.gumtree.android.postad;

import com.gumtree.android.postad.views.PriceInfoView;
import com.gumtree.android.postad.views.PriceInfoView.OnPriceDetailListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class PostAdActivity$$Lambda$1 implements OnPriceDetailListener {
    private final PostAdActivity arg$1;

    private PostAdActivity$$Lambda$1(PostAdActivity postAdActivity) {
        this.arg$1 = postAdActivity;
    }

    public static OnPriceDetailListener lambdaFactory$(PostAdActivity postAdActivity) {
        return new PostAdActivity$$Lambda$1(postAdActivity);
    }

    @Hidden
    public void showPriceInfoDetail(PriceInfoView priceInfoView) {
        this.arg$1.lambda$onCreate$0(priceInfoView);
    }
}
