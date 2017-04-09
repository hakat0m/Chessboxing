package com.gumtree.android.postad.services;

import android.support.annotation.NonNull;
import com.ebay.classifieds.capi.users.ads.MyAd;
import com.ebay.classifieds.capi.users.ads.UserAdsApi;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.postad.DraftAd;
import org.apache.commons.lang3.Validate;
import rx.Observable;
import rx.observables.ConnectableObservable;

public class ApiPostAdService implements PostAdService {
    private final UserAdsApi api;
    private final BaseAccountManager baseAccountManager;
    private final AdModelConverter myAdConverter;

    public ApiPostAdService(@NonNull UserAdsApi userAdsApi, @NonNull BaseAccountManager baseAccountManager, @NonNull AdModelConverter adModelConverter) {
        this.api = (UserAdsApi) Validate.notNull(userAdsApi);
        this.baseAccountManager = (BaseAccountManager) Validate.notNull(baseAccountManager);
        this.myAdConverter = (AdModelConverter) Validate.notNull(adModelConverter);
    }

    public Observable<DraftAd> postDraftAd(DraftAd draftAd) {
        return Observable.just(draftAd).map(ApiPostAdService$$Lambda$1.lambdaFactory$(this)).flatMap(ApiPostAdService$$Lambda$2.lambdaFactory$(this)).map(ApiPostAdService$$Lambda$3.lambdaFactory$(this));
    }

    /* synthetic */ MyAd lambda$postDraftAd$0(DraftAd draftAd) {
        return this.myAdConverter.draftAdToMyAd(draftAd);
    }

    /* synthetic */ Observable lambda$postDraftAd$1(MyAd myAd) {
        return this.api.post(this.baseAccountManager.getUsername(), myAd);
    }

    /* synthetic */ DraftAd lambda$postDraftAd$2(MyAd myAd) {
        return this.myAdConverter.myAdToDraftAd(myAd);
    }

    public Observable<DraftAd> editDraftAd(DraftAd draftAd) {
        return Observable.just(draftAd).map(ApiPostAdService$$Lambda$4.lambdaFactory$(this)).flatMap(ApiPostAdService$$Lambda$5.lambdaFactory$(this, draftAd)).map(ApiPostAdService$$Lambda$6.lambdaFactory$(this));
    }

    /* synthetic */ MyAd lambda$editDraftAd$3(DraftAd draftAd) {
        return this.myAdConverter.draftAdToMyAd(draftAd);
    }

    /* synthetic */ Observable lambda$editDraftAd$4(DraftAd draftAd, MyAd myAd) {
        return this.api.edit(this.baseAccountManager.getUsername(), myAd, draftAd.getId());
    }

    /* synthetic */ DraftAd lambda$editDraftAd$5(MyAd myAd) {
        return this.myAdConverter.myAdToDraftAd(myAd);
    }

    public Observable<DraftAd> getDraftAd(String str) {
        return getMyAdLive(str).map(ApiPostAdService$$Lambda$7.lambdaFactory$(this));
    }

    /* synthetic */ DraftAd lambda$getDraftAd$6(MyAd myAd) {
        return this.myAdConverter.myAdToDraftAd(myAd);
    }

    private Observable<MyAd> getMyAdLive(String str) {
        ConnectableObservable replay = this.api.getAd(this.baseAccountManager.getUsername(), str).replay();
        replay.connect();
        return replay;
    }
}
