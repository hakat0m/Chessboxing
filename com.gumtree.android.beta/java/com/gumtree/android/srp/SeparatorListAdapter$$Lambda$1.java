package com.gumtree.android.srp;

import com.gumtree.android.srp.SeparatorListAdapter.InjectedItem;
import java.lang.invoke.LambdaForm.Hidden;
import java.util.Comparator;

final /* synthetic */ class SeparatorListAdapter$$Lambda$1 implements Comparator {
    private static final SeparatorListAdapter$$Lambda$1 instance = new SeparatorListAdapter$$Lambda$1();

    private SeparatorListAdapter$$Lambda$1() {
    }

    public static Comparator lambdaFactory$() {
        return instance;
    }

    @Hidden
    public int compare(Object obj, Object obj2) {
        return SeparatorListAdapter.lambda$sortSection$0((InjectedItem) obj, (InjectedItem) obj2);
    }
}
