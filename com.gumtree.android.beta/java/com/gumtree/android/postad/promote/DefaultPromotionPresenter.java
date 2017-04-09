package com.gumtree.android.postad.promote;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.ebay.classifieds.capi.exceptions.ApiException;
import com.gumtree.Log;
import com.gumtree.android.category.model.DraftCategory;
import com.gumtree.android.postad.DraftFeature;
import com.gumtree.android.postad.DraftFeature.Type;
import com.gumtree.android.postad.DraftLocation;
import com.gumtree.android.postad.DraftOption;
import com.gumtree.android.postad.promote.PromotionFeature.PromotionFeatureBuilder;
import com.gumtree.android.postad.promote.PromotionPresenter.View;
import com.gumtree.android.priceinfo.PriceInfoService;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration$Builder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.Validate;
import rx.Observable;

public class DefaultPromotionPresenter implements PromotionPresenter {
    private Map<DraftFeature, PromotionFeature> features = new HashMap();
    private boolean inError;
    private final PriceInfoService service;
    private final StringProvider strings;
    private final GatedPromotionView view;

    @Inject
    public DefaultPromotionPresenter(PriceInfoService priceInfoService, @NonNull GatedPromotionView gatedPromotionView, @NonNull StringProvider stringProvider) {
        this.service = (PriceInfoService) Validate.notNull(priceInfoService);
        this.view = (GatedPromotionView) Validate.notNull(gatedPromotionView);
        this.strings = (StringProvider) Validate.notNull(stringProvider);
    }

    public void onLoadFeatures(String str, String str2, long j, @NonNull List<PromotionFeature> list, @NonNull List<Type> list2, int i, String str3) {
        Observable.defer(DefaultPromotionPresenter$$Lambda$1.lambdaFactory$(this, str, DraftCategory.builder().id(str2).build(), new DraftLocation(j, null, null, false, 0, null), str3)).filter(DefaultPromotionPresenter$$Lambda$2.lambdaFactory$(this)).filter(DefaultPromotionPresenter$$Lambda$3.lambdaFactory$(this, str)).map(DefaultPromotionPresenter$$Lambda$4.lambdaFactory$(this, list, list2, i)).toList().defaultIfEmpty(Collections.emptyList()).subscribe(DefaultPromotionPresenter$$Lambda$5.lambdaFactory$(this), DefaultPromotionPresenter$$Lambda$6.lambdaFactory$(this));
    }

    /* synthetic */ Observable lambda$onLoadFeatures$0(String str, DraftCategory draftCategory, DraftLocation draftLocation, String str2) {
        return callFeatureInfo(str, draftCategory, draftLocation, str2);
    }

    /* synthetic */ Boolean lambda$onLoadFeatures$1(DraftFeature draftFeature) {
        return Boolean.valueOf(canBeUsedAsAPromotion(draftFeature));
    }

    /* synthetic */ Boolean lambda$onLoadFeatures$2(String str, DraftFeature draftFeature) {
        return Boolean.valueOf(canShowBumpUp(str, draftFeature));
    }

    /* synthetic */ PromotionFeature lambda$onLoadFeatures$3(@NonNull List list, @NonNull List list2, int i, DraftFeature draftFeature) {
        return convertToPromotionFeature(draftFeature, list, list2, i);
    }

    /* synthetic */ void lambda$onLoadFeatures$4(List list) {
        setFeatures(list);
    }

    /* synthetic */ void lambda$onLoadFeatures$5(Throwable th) {
        setError(th);
    }

    private boolean canShowBumpUp(String str, DraftFeature draftFeature) {
        return (Type.BUMPED_UP == draftFeature.getType() && str == null) ? false : true;
    }

    private boolean canBeUsedAsAPromotion(DraftFeature draftFeature) {
        switch (1.$SwitchMap$com$gumtree$android$postad$DraftFeature$Type[((Type) ObjectUtils.defaultIfNull(draftFeature.getType(), Type.UNSUPPORTED)).ordinal()]) {
            case HighlightView.GROW_NONE /*1*/:
            case HighlightView.GROW_LEFT_EDGE /*2*/:
            case ImageLoaderConfiguration$Builder.DEFAULT_THREAD_POOL_SIZE /*3*/:
            case HighlightView.GROW_RIGHT_EDGE /*4*/:
                return true;
            default:
                return false;
        }
    }

    @NonNull
    private Observable<DraftFeature> callFeatureInfo(String str, DraftCategory draftCategory, DraftLocation draftLocation, String str2) {
        return this.service.getFeatureInfo(str, draftCategory, draftLocation, str2);
    }

    private String extractErrorMessage(Throwable th) {
        boolean z = th instanceof ApiException;
        if (!z) {
            Log.w(getClass().getSimpleName(), "Cannot display a message for this exception.", th);
        }
        return z ? th.getMessage() : this.strings.unknownError();
    }

    @NonNull
    private PromotionFeature convertToPromotionFeature(DraftFeature draftFeature, List<PromotionFeature> list, List<Type> list2, int i) {
        String str = null;
        boolean z = false;
        PromotionFeature promotionFeature = (PromotionFeature) IterableUtils.find(list, DefaultPromotionPresenter$$Lambda$7.lambdaFactory$(draftFeature));
        if (promotionFeature != null) {
            return promotionFeature;
        }
        boolean contains = list2.contains(draftFeature.getType());
        DraftOption draftOption = draftFeature.getOptions().size() == 0 ? null : (DraftOption) draftFeature.getOptions().get(0);
        if (draftFeature.getType() == Type.IN_SPOTLIGHT && i == 0) {
            str = this.strings.spotlightOnlyWithImages();
        }
        PromotionFeatureBuilder active = PromotionFeature.builder().feature(draftFeature).active(contains);
        if (!(contains || draftOption == null)) {
            z = true;
        }
        return active.enabled(z).highlighted(draftOption).disableReason(str).build();
    }

    public void onSelectFeature(@NonNull PromotionFeature promotionFeature, boolean z) {
        DraftFeature feature = promotionFeature.getFeature();
        PromotionFeature promotionFeature2 = (PromotionFeature) this.features.get(feature);
        this.features.put(feature, new PromotionFeature(promotionFeature2.getFeature(), promotionFeature2.isEnabled(), promotionFeature2.getDisableReason(), promotionFeature2.isActive(), z, promotionFeature2.getHighlighted()));
    }

    public void onSelectOption(@Nullable DraftOption draftOption, @NonNull PromotionFeature promotionFeature) {
        DraftFeature feature = promotionFeature.getFeature();
        PromotionFeature promotionFeature2 = (PromotionFeature) this.features.get(feature);
        this.features.put(feature, new PromotionFeature(promotionFeature2.getFeature(), promotionFeature2.isEnabled(), promotionFeature2.getDisableReason(), promotionFeature2.isActive(), promotionFeature2.isSelected(), draftOption));
    }

    public void onViewCloses() {
        if (!this.inError) {
            Object arrayList = new ArrayList(this.features.values());
            CollectionUtils.filter(arrayList, DefaultPromotionPresenter$$Lambda$8.lambdaFactory$());
            this.view.updateSelection(arrayList);
        }
    }

    public void onRefreshView() {
        updateView();
    }

    public void onInfo(Type type) {
        this.view.promptInfo(type);
    }

    public void attachView(View view) {
        this.view.setDecorated(view);
    }

    public void detachView() {
        this.view.setDecorated(null);
    }

    public void destroy() {
        this.view.sealIt();
    }

    private void setError(Throwable th) {
        this.inError = true;
        this.view.cancelOnError(extractErrorMessage(th));
    }

    private void setFeatures(@NonNull List<PromotionFeature> list) {
        this.inError = false;
        this.features.clear();
        for (PromotionFeature promotionFeature : ListUtils.emptyIfNull(list)) {
            this.features.put(promotionFeature.getFeature(), promotionFeature);
        }
        updateView();
    }

    private void updateView() {
        this.view.showFeatures(new ArrayList(this.features.values()));
    }
}
