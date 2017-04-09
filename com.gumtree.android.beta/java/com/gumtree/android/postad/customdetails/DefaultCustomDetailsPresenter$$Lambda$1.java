package com.gumtree.android.postad.customdetails;

import com.gumtree.android.metadata.model.DraftAdMetadataAttribute;
import java.lang.invoke.LambdaForm.Hidden;
import org.apache.commons.collections4.Predicate;

final /* synthetic */ class DefaultCustomDetailsPresenter$$Lambda$1 implements Predicate {
    private final String arg$1;

    private DefaultCustomDetailsPresenter$$Lambda$1(String str) {
        this.arg$1 = str;
    }

    public static Predicate lambdaFactory$(String str) {
        return new DefaultCustomDetailsPresenter$$Lambda$1(str);
    }

    @Hidden
    public boolean evaluate(Object obj) {
        return DefaultCustomDetailsPresenter.lambda$findDraftAdMetadataAttribute$0(this.arg$1, (DraftAdMetadataAttribute) obj);
    }
}
