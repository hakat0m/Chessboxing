package com.gumtree.android.postad.presenters;

import com.gumtree.android.category.model.DraftCategory;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class MetadataBee$$Lambda$4 implements Func1 {
    private static final MetadataBee$$Lambda$4 instance = new MetadataBee$$Lambda$4();

    private MetadataBee$$Lambda$4() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return MetadataBee.lambda$load$3((DraftCategory) obj);
    }
}
