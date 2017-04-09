package com.gumtree.android.postad.confirmation;

import com.gumtree.android.postad.services.PostAdService;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class GetDraftAdHelper$$Lambda$1 implements Func1 {
    private final PostAdService arg$1;

    private GetDraftAdHelper$$Lambda$1(PostAdService postAdService) {
        this.arg$1 = postAdService;
    }

    public static Func1 lambdaFactory$(PostAdService postAdService) {
        return new GetDraftAdHelper$$Lambda$1(postAdService);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.getDraftAd((String) obj);
    }
}
