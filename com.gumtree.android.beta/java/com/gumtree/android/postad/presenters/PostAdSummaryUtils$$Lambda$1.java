package com.gumtree.android.postad.presenters;

import com.gumtree.android.postad.DraftFeature;
import com.gumtree.android.postad.promote.PromotionFeature;
import java.lang.invoke.LambdaForm.Hidden;
import org.apache.commons.collections4.Predicate;

final /* synthetic */ class PostAdSummaryUtils$$Lambda$1 implements Predicate {
    private final DraftFeature arg$1;

    private PostAdSummaryUtils$$Lambda$1(DraftFeature draftFeature) {
        this.arg$1 = draftFeature;
    }

    public static Predicate lambdaFactory$(DraftFeature draftFeature) {
        return new PostAdSummaryUtils$$Lambda$1(draftFeature);
    }

    @Hidden
    public boolean evaluate(Object obj) {
        return PostAdSummaryUtils.lambda$lookupFeature$0(this.arg$1, (PromotionFeature) obj);
    }
}
