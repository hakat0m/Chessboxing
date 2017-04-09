package com.gumtree.android.postad.validation;

import com.gumtree.android.metadata.model.DraftAdMetadataAttribute;
import com.gumtree.android.postad.DraftAdAttribute;
import java.lang.invoke.LambdaForm.Hidden;
import org.apache.commons.collections4.Predicate;

final /* synthetic */ class PostAdValidationRules$$Lambda$1 implements Predicate {
    private final DraftAdMetadataAttribute arg$1;

    private PostAdValidationRules$$Lambda$1(DraftAdMetadataAttribute draftAdMetadataAttribute) {
        this.arg$1 = draftAdMetadataAttribute;
    }

    public static Predicate lambdaFactory$(DraftAdMetadataAttribute draftAdMetadataAttribute) {
        return new PostAdValidationRules$$Lambda$1(draftAdMetadataAttribute);
    }

    @Hidden
    public boolean evaluate(Object obj) {
        return PostAdValidationRules.lambda$attributeDetailsValidationMessage$0(this.arg$1, (DraftAdAttribute) obj);
    }
}
