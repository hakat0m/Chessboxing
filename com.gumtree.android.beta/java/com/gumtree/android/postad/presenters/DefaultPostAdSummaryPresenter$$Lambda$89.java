package com.gumtree.android.postad.presenters;

import java.lang.invoke.LambdaForm.Hidden;
import java.util.Comparator;

final /* synthetic */ class DefaultPostAdSummaryPresenter$$Lambda$89 implements Comparator {
    private static final DefaultPostAdSummaryPresenter$$Lambda$89 instance = new DefaultPostAdSummaryPresenter$$Lambda$89();

    private DefaultPostAdSummaryPresenter$$Lambda$89() {
    }

    public static Comparator lambdaFactory$() {
        return instance;
    }

    @Hidden
    public int compare(Object obj, Object obj2) {
        return ((String) obj).compareToIgnoreCase((String) obj2);
    }
}
