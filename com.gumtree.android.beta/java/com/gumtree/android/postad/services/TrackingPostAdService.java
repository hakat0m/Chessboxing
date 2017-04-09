package com.gumtree.android.postad.services;

import com.gumtree.android.postad.DraftAd;
import com.gumtree.android.postad.PostAdImage;
import com.gumtree.android.postad.promote.PromotionFeature;
import java.util.List;

public interface TrackingPostAdService {
    void eventAddImage(List<PostAdImage> list);

    void eventAddImageBegin(DraftAd draftAd);

    void eventAttributesValidated();

    void eventCategoryValidated();

    void eventContactValidated();

    void eventDescriptionValidated();

    void eventFeatureAdAttempt(String str, List<PromotionFeature> list);

    void eventFeatureAdCancel(String str);

    void eventFeatureAdFailure(String str);

    void eventFeatureAdSuccess(String str);

    void eventPostAdAttempt(DraftAd draftAd);

    void eventPostAdBounce(DraftAd draftAd);

    void eventPostAdFail(DraftAd draftAd);

    void eventPostAdReset();

    void eventPostAdScreenBegin(DraftAd draftAd);

    void eventPostAdScreenResume(DraftAd draftAd);

    void eventPostAdStepPromote(List<PromotionFeature> list);

    void eventPostAdSuccess(String str, DraftAd draftAd);

    void eventPriceValidated();

    void eventTitleValidated();

    void eventVRMFindAttempt();

    void eventVRMFindFail();

    void eventVRMFindSuccess();
}
