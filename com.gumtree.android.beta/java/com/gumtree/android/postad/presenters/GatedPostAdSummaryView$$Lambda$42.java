package com.gumtree.android.postad.presenters;

import com.gumtree.android.mvp.Gate.Action;
import com.gumtree.android.postad.customdetails.models.CustomDetailsData;
import com.gumtree.android.postad.presenters.PostAdSummaryPresenter.View;
import java.lang.invoke.LambdaForm.Hidden;
import org.apache.commons.lang3.tuple.Pair;

final /* synthetic */ class GatedPostAdSummaryView$$Lambda$42 implements Action {
    private final View arg$1;

    private GatedPostAdSummaryView$$Lambda$42(View view) {
        this.arg$1 = view;
    }

    public static Action lambdaFactory$(View view) {
        return new GatedPostAdSummaryView$$Lambda$42(view);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.openAttributeDetails((CustomDetailsData) ((Pair) ((Pair) obj).getLeft()).getLeft(), ((Boolean) ((Pair) ((Pair) obj).getLeft()).getRight()).booleanValue(), (String) ((Pair) obj).getRight());
    }
}
