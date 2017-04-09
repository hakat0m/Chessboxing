package com.gumtree.android.services;

import com.gumtree.android.metadata.model.DraftAdMetadataAttribute;
import com.gumtree.android.postad.DraftAdAttribute;
import java.lang.invoke.LambdaForm.Hidden;
import org.apache.commons.collections4.Predicate;

final /* synthetic */ class StaticTrackingService$$Lambda$2 implements Predicate {
    private final DraftAdMetadataAttribute arg$1;

    private StaticTrackingService$$Lambda$2(DraftAdMetadataAttribute draftAdMetadataAttribute) {
        this.arg$1 = draftAdMetadataAttribute;
    }

    public static Predicate lambdaFactory$(DraftAdMetadataAttribute draftAdMetadataAttribute) {
        return new StaticTrackingService$$Lambda$2(draftAdMetadataAttribute);
    }

    @Hidden
    public boolean evaluate(Object obj) {
        return this.arg$1.getName().equals(((DraftAdAttribute) obj).getName());
    }
}
