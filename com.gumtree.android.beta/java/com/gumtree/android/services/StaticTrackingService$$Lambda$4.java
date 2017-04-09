package com.gumtree.android.services;

import com.gumtree.android.postad.promote.PromotionFeature;
import java.lang.invoke.LambdaForm.Hidden;
import org.apache.commons.collections4.Transformer;

final /* synthetic */ class StaticTrackingService$$Lambda$4 implements Transformer {
    private static final StaticTrackingService$$Lambda$4 instance = new StaticTrackingService$$Lambda$4();

    private StaticTrackingService$$Lambda$4() {
    }

    public static Transformer lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object transform(Object obj) {
        return ((PromotionFeature) obj).getFeature();
    }
}
