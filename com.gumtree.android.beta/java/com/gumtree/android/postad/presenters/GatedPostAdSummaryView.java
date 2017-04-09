package com.gumtree.android.postad.presenters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.ebay.classifieds.capi.ads.Attribute;
import com.gumtree.android.mvp.Gate;
import com.gumtree.android.postad.DraftAd;
import com.gumtree.android.postad.PostAdImage;
import com.gumtree.android.postad.contactdetails.models.ContactDetailsData;
import com.gumtree.android.postad.customdetails.models.CustomDetailsData;
import com.gumtree.android.postad.payment.models.OrderData;
import com.gumtree.android.postad.presenters.PostAdSummaryPresenter.View;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import rx.Subscription;
import rx.subjects.BehaviorSubject;

public class GatedPostAdSummaryView implements View {
    private final Gate<Triple<List<PostAdImage>, Integer, String>> addImage = new Gate();
    private final Gate<Void> closeView = new Gate();
    private final Gate<Void> closeViewWithOkResult = new Gate();
    private final Gate<Triple<List<PostAdImage>, Integer, String>> editImage = new Gate();
    private final Gate<Boolean> enablePromotions = new Gate();
    private final Gate<Void> hideCost = new Gate();
    private final Gate<Pair<Pair<CustomDetailsData, Boolean>, String>> openAttributesDetails = new Gate();
    private final Gate<Void> openCategoryDetails = new Gate();
    private final Gate<ContactDetailsData> openContactDetails = new Gate();
    private final Gate<Void> openHelp = new Gate();
    private final Gate<Void> openManageAds = new Gate();
    private final Gate<Void> openNewPostAd = new Gate();
    private final Gate<OrderData> openPaymentDetails = new Gate();
    private final Gate<DraftAd> promptPromotions = new Gate();
    private final Gate<Void> resetViews = new Gate();
    private final Gate<String> setSelectedPriceFrequency = new Gate();
    private final Gate<Void> showAdLoaded = new Gate();
    private final Gate<String> showAdLoadingError = new Gate();
    private final Gate<String> showAdSavingError = new Gate();
    private final Gate<String> showAttributeDetails = new Gate();
    private final Gate<String> showAttributeDetailsError = new Gate();
    private final Gate<List<Attribute>> showAttributes = new Gate();
    private final Gate<Boolean> showAttributesDetailsField = new Gate();
    private final Gate<Pair<String, Boolean>> showCategory = new Gate();
    private final Gate<String> showCategoryError = new Gate();
    private final Gate<String> showContactDetails = new Gate();
    private final Gate<String> showContactDetailsError = new Gate();
    private final Gate<Pair<Double, List<String>>> showCostPaidAd = new Gate();
    private final Gate<String> showDescription = new Gate();
    private final Gate<String> showDescriptionError = new Gate();
    private final Gate<Boolean> showDescriptionValid = new Gate();
    private final Gate<String> showErrorLoadingPriceInfo = new Gate();
    private final Gate<Pair<Pair<Boolean, Boolean>, Pair<Boolean, Boolean>>> showFeatures = new Gate();
    private final Gate<Void> showImageDownloadError = new Gate();
    private final Gate<Void> showImageUploadError = new Gate();
    private final Gate<List<PostAdImage>> showImages = new Gate();
    private final Gate<Boolean> showImagesLoading = new Gate();
    private final Gate<Boolean> showLoading = new Gate();
    private final Gate<Void> showLoginScreen = new Gate();
    private final Gate<Void> showNoNetworkError = new Gate();
    private final Gate<String> showPostAdFeedbackConfirmation = new Gate();
    private final Gate<Void> showPostFreeAd = new Gate();
    private final Gate<Void> showPostPaidAd = new Gate();
    private final Gate<Void> showPostValidationError = new Gate();
    private final Gate<String> showPrice = new Gate();
    private final Gate<String> showPriceError = new Gate();
    private final Gate<Boolean> showPriceField = new Gate();
    private final Gate<Pair<String, List<String>>> showPriceFrequency = new Gate();
    private final Gate<String> showPriceFrequencyError = new Gate();
    private final Gate<Boolean> showPriceFrequencyField = new Gate();
    private final Gate<Boolean> showPriceFrequencyValid = new Gate();
    private final Gate<Boolean> showPriceValid = new Gate();
    private final Gate<Boolean> showReset = new Gate();
    private final Gate<Void> showRestoreAd = new Gate();
    private final Gate<Boolean> showSending = new Gate();
    private final Gate<String> showTitle = new Gate();
    private final Gate<String> showTitleError = new Gate();
    private final Gate<Boolean> showTitleValid = new Gate();
    private final Gate<Void> showUpdateAd = new Gate();
    private final Subscription subscription = this.trigger.subscribe(GatedPostAdSummaryView$$Lambda$1.lambdaFactory$(this));
    private final BehaviorSubject<View> trigger = BehaviorSubject.create();

    /* synthetic */ void lambda$new$0(View view) {
        if (view == null) {
            close();
        } else {
            open(view);
        }
    }

    public void sealIt() {
        this.subscription.unsubscribe();
    }

    public void setDecorated(@Nullable View view) {
        this.trigger.onNext(view);
    }

    private void open(View view) {
        this.showReset.open(GatedPostAdSummaryView$$Lambda$2.lambdaFactory$(view));
        this.showLoginScreen.open(GatedPostAdSummaryView$$Lambda$3.lambdaFactory$(view));
        this.showAdLoadingError.open(GatedPostAdSummaryView$$Lambda$4.lambdaFactory$(view));
        this.showPostValidationError.open(GatedPostAdSummaryView$$Lambda$5.lambdaFactory$(view));
        this.showAdSavingError.open(GatedPostAdSummaryView$$Lambda$6.lambdaFactory$(view));
        this.showImages.open(GatedPostAdSummaryView$$Lambda$7.lambdaFactory$(view));
        this.showImagesLoading.open(GatedPostAdSummaryView$$Lambda$8.lambdaFactory$(view));
        this.showTitle.open(GatedPostAdSummaryView$$Lambda$9.lambdaFactory$(view));
        this.showTitleValid.open(GatedPostAdSummaryView$$Lambda$10.lambdaFactory$(view));
        this.showTitleError.open(GatedPostAdSummaryView$$Lambda$11.lambdaFactory$(view));
        this.openCategoryDetails.open(GatedPostAdSummaryView$$Lambda$12.lambdaFactory$(view));
        this.showCategory.open(GatedPostAdSummaryView$$Lambda$13.lambdaFactory$(view));
        this.showCategoryError.open(GatedPostAdSummaryView$$Lambda$14.lambdaFactory$(view));
        this.showPrice.open(GatedPostAdSummaryView$$Lambda$15.lambdaFactory$(view));
        this.showPriceValid.open(GatedPostAdSummaryView$$Lambda$16.lambdaFactory$(view));
        this.showPriceFrequency.open(GatedPostAdSummaryView$$Lambda$17.lambdaFactory$(view));
        this.showPriceFrequencyValid.open(GatedPostAdSummaryView$$Lambda$18.lambdaFactory$(view));
        this.showPriceField.open(GatedPostAdSummaryView$$Lambda$19.lambdaFactory$(view));
        this.showPriceFrequencyField.open(GatedPostAdSummaryView$$Lambda$20.lambdaFactory$(view));
        this.setSelectedPriceFrequency.open(GatedPostAdSummaryView$$Lambda$21.lambdaFactory$(view));
        this.showPriceError.open(GatedPostAdSummaryView$$Lambda$22.lambdaFactory$(view));
        this.showPriceFrequencyError.open(GatedPostAdSummaryView$$Lambda$23.lambdaFactory$(view));
        this.showDescription.open(GatedPostAdSummaryView$$Lambda$24.lambdaFactory$(view));
        this.showDescriptionValid.open(GatedPostAdSummaryView$$Lambda$25.lambdaFactory$(view));
        this.showDescriptionError.open(GatedPostAdSummaryView$$Lambda$26.lambdaFactory$(view));
        this.showContactDetails.open(GatedPostAdSummaryView$$Lambda$27.lambdaFactory$(view));
        this.showContactDetailsError.open(GatedPostAdSummaryView$$Lambda$28.lambdaFactory$(view));
        this.showAttributeDetails.open(GatedPostAdSummaryView$$Lambda$29.lambdaFactory$(view));
        this.showAttributeDetailsError.open(GatedPostAdSummaryView$$Lambda$30.lambdaFactory$(view));
        this.showLoading.open(GatedPostAdSummaryView$$Lambda$31.lambdaFactory$(view));
        this.showSending.open(GatedPostAdSummaryView$$Lambda$32.lambdaFactory$(view));
        this.showPostAdFeedbackConfirmation.open(GatedPostAdSummaryView$$Lambda$33.lambdaFactory$(view));
        this.showAttributes.open(GatedPostAdSummaryView$$Lambda$34.lambdaFactory$(view));
        this.editImage.open(GatedPostAdSummaryView$$Lambda$35.lambdaFactory$(view));
        this.addImage.open(GatedPostAdSummaryView$$Lambda$36.lambdaFactory$(view));
        this.showImageUploadError.open(GatedPostAdSummaryView$$Lambda$37.lambdaFactory$(view));
        this.showImageDownloadError.open(GatedPostAdSummaryView$$Lambda$38.lambdaFactory$(view));
        this.enablePromotions.open(GatedPostAdSummaryView$$Lambda$39.lambdaFactory$(view));
        this.showFeatures.open(GatedPostAdSummaryView$$Lambda$40.lambdaFactory$(view));
        this.openContactDetails.open(GatedPostAdSummaryView$$Lambda$41.lambdaFactory$(view));
        this.openAttributesDetails.open(GatedPostAdSummaryView$$Lambda$42.lambdaFactory$(view));
        this.openManageAds.open(GatedPostAdSummaryView$$Lambda$43.lambdaFactory$(view));
        this.openNewPostAd.open(GatedPostAdSummaryView$$Lambda$44.lambdaFactory$(view));
        this.openHelp.open(GatedPostAdSummaryView$$Lambda$45.lambdaFactory$(view));
        this.showAttributesDetailsField.open(GatedPostAdSummaryView$$Lambda$46.lambdaFactory$(view));
        this.showCostPaidAd.open(GatedPostAdSummaryView$$Lambda$47.lambdaFactory$(view));
        this.hideCost.open(GatedPostAdSummaryView$$Lambda$48.lambdaFactory$(view));
        this.showPostFreeAd.open(GatedPostAdSummaryView$$Lambda$49.lambdaFactory$(view));
        this.showPostPaidAd.open(GatedPostAdSummaryView$$Lambda$50.lambdaFactory$(view));
        this.showUpdateAd.open(GatedPostAdSummaryView$$Lambda$51.lambdaFactory$(view));
        this.showErrorLoadingPriceInfo.open(GatedPostAdSummaryView$$Lambda$52.lambdaFactory$(view));
        this.promptPromotions.open(GatedPostAdSummaryView$$Lambda$53.lambdaFactory$(view));
        this.showNoNetworkError.open(GatedPostAdSummaryView$$Lambda$54.lambdaFactory$(view));
        this.openPaymentDetails.open(GatedPostAdSummaryView$$Lambda$55.lambdaFactory$(view));
        this.resetViews.open(GatedPostAdSummaryView$$Lambda$56.lambdaFactory$(view));
        this.showAdLoaded.open(GatedPostAdSummaryView$$Lambda$57.lambdaFactory$(view));
        this.closeView.open(GatedPostAdSummaryView$$Lambda$58.lambdaFactory$(view));
        this.closeViewWithOkResult.open(GatedPostAdSummaryView$$Lambda$59.lambdaFactory$(view));
        this.showRestoreAd.open(GatedPostAdSummaryView$$Lambda$60.lambdaFactory$(view));
    }

    private void close() {
        this.showReset.close();
        this.showLoginScreen.close();
        this.showAdLoadingError.close();
        this.showPostValidationError.close();
        this.showAdSavingError.close();
        this.showImages.close();
        this.showImagesLoading.close();
        this.showTitle.close();
        this.showTitleValid.close();
        this.showTitleError.close();
        this.showCategory.close();
        this.showCategoryError.close();
        this.showPrice.close();
        this.showPriceValid.close();
        this.showPriceFrequency.close();
        this.showPriceFrequencyValid.close();
        this.showPriceField.close();
        this.showPriceFrequencyField.close();
        this.setSelectedPriceFrequency.close();
        this.showPriceError.close();
        this.showPriceFrequencyError.close();
        this.showDescription.close();
        this.showDescriptionValid.close();
        this.showDescriptionError.close();
        this.showContactDetails.close();
        this.showContactDetailsError.close();
        this.showAttributeDetails.close();
        this.showAttributeDetailsError.close();
        this.showLoading.close();
        this.showSending.close();
        this.showPostAdFeedbackConfirmation.close();
        this.showAttributes.close();
        this.editImage.close();
        this.addImage.close();
        this.showImageUploadError.close();
        this.showImageDownloadError.close();
        this.enablePromotions.close();
        this.showFeatures.close();
        this.openContactDetails.close();
        this.openAttributesDetails.close();
        this.openManageAds.close();
        this.openNewPostAd.close();
        this.openHelp.close();
        this.showAttributesDetailsField.close();
        this.showCostPaidAd.close();
        this.hideCost.close();
        this.showPostPaidAd.close();
        this.showPostFreeAd.close();
        this.showUpdateAd.close();
        this.showErrorLoadingPriceInfo.close();
        this.promptPromotions.close();
        this.showNoNetworkError.close();
        this.openPaymentDetails.close();
        this.resetViews.close();
        this.showAdLoaded.close();
        this.closeView.close();
        this.closeViewWithOkResult.close();
        this.showRestoreAd.close();
    }

    public void showReset(boolean z) {
        this.showReset.perform(Boolean.valueOf(z));
    }

    public void showLoginScreen() {
        this.showLoginScreen.perform(null);
    }

    public void showAdLoadingError(String str) {
        this.showAdLoadingError.perform(str);
    }

    public void showPostValidationError() {
        this.showPostValidationError.perform(null);
    }

    public void showAdSavingError(String str) {
        this.showAdSavingError.perform(str);
    }

    public void showImages(List<PostAdImage> list) {
        this.showImages.perform(list);
    }

    public void showImagesLoading(boolean z) {
        this.showImagesLoading.perform(Boolean.valueOf(z));
    }

    public void showTitle(String str) {
        this.showTitle.perform(str);
    }

    public void showTitleValid(boolean z) {
        this.showTitleValid.perform(Boolean.valueOf(z));
    }

    public void showTitleError(String str) {
        this.showTitleError.perform(str);
    }

    public void openCategoryDetails() {
        this.openCategoryDetails.perform(null);
    }

    public void showCategory(String str, boolean z) {
        this.showCategory.perform(Pair.of(str, Boolean.valueOf(z)));
    }

    public void showCategoryError(String str) {
        this.showCategoryError.perform(str);
    }

    public void showPrice(String str) {
        this.showPrice.perform(str);
    }

    public void showPriceValid(boolean z) {
        this.showPriceValid.perform(Boolean.valueOf(z));
    }

    public void showPriceField(boolean z) {
        this.showPriceField.perform(Boolean.valueOf(z));
    }

    public void showPriceFrequencyField(boolean z) {
        this.showPriceFrequencyField.perform(Boolean.valueOf(z));
    }

    public void setSelectedPriceFrequency(String str) {
        this.setSelectedPriceFrequency.perform(str);
    }

    public void showPriceError(String str) {
        this.showPriceError.perform(str);
    }

    public void showPriceFrequency(String str, List<String> list) {
        this.showPriceFrequency.perform(Pair.of(str, list));
    }

    public void showPriceFrequencyValid(boolean z) {
        this.showPriceFrequencyValid.perform(Boolean.valueOf(z));
    }

    public void showPriceFrequencyError(String str) {
        this.showPriceFrequencyError.perform(str);
    }

    public void showDescription(String str) {
        this.showDescription.perform(str);
    }

    public void showDescriptionValid(boolean z) {
        this.showDescriptionValid.perform(Boolean.valueOf(z));
    }

    public void showDescriptionError(String str) {
        this.showDescriptionError.perform(str);
    }

    public void showContactDetails(String str) {
        this.showContactDetails.perform(str);
    }

    public void showContactDetailsError(String str) {
        this.showContactDetailsError.perform(str);
    }

    public void showAttributeDetails(String str) {
        this.showAttributeDetails.perform(str);
    }

    public void showAttributeDetailsError(String str) {
        this.showAttributeDetailsError.perform(str);
    }

    public void showLoading(boolean z) {
        this.showLoading.perform(Boolean.valueOf(z));
    }

    public void showSending(boolean z) {
        this.showSending.perform(Boolean.valueOf(z));
    }

    public void showPostAdFeedbackConfirmation(String str) {
        this.showPostAdFeedbackConfirmation.perform(str);
    }

    public void showAttributes(List<Attribute> list) {
        this.showAttributes.perform(list);
    }

    public void editImage(List<PostAdImage> list, int i, String str) {
        this.editImage.perform(Triple.of(list, Integer.valueOf(i), str));
    }

    public void addImage(List<PostAdImage> list, int i, String str) {
        this.addImage.perform(Triple.of(list, Integer.valueOf(i), str));
    }

    public void showImageUploadError() {
        this.showImageUploadError.perform(null);
    }

    public void showImageDownloadError() {
        this.showImageDownloadError.perform(null);
    }

    public void enablePromotions(boolean z) {
        this.enablePromotions.perform(Boolean.valueOf(z));
    }

    public void showFeatures(boolean z, boolean z2, boolean z3, boolean z4) {
        this.showFeatures.perform(Pair.of(Pair.of(Boolean.valueOf(z), Boolean.valueOf(z2)), Pair.of(Boolean.valueOf(z3), Boolean.valueOf(z4))));
    }

    public void openContactDetails(ContactDetailsData contactDetailsData) {
        this.openContactDetails.perform(contactDetailsData);
    }

    public void openAttributeDetails(CustomDetailsData customDetailsData, boolean z, String str) {
        this.openAttributesDetails.perform(Pair.of(Pair.of(customDetailsData, Boolean.valueOf(z)), str));
    }

    public void openPaymentDetails(OrderData orderData) {
        this.openPaymentDetails.perform(orderData);
    }

    public void openManageAds() {
        this.openManageAds.perform(null);
    }

    public void openNewPostAd() {
        this.openNewPostAd.perform(null);
    }

    public void openHelp() {
        this.openHelp.perform(null);
    }

    public void showAttributeDetailsField(boolean z) {
        this.showAttributesDetailsField.perform(Boolean.valueOf(z));
    }

    public void showCostPaidAd(double d, @NonNull List<String> list) {
        this.showCostPaidAd.perform(Pair.of(Double.valueOf(d), list));
    }

    public void hideCost() {
        this.hideCost.perform(null);
    }

    public void showPostPaidAd() {
        this.showPostPaidAd.perform(null);
    }

    public void showPostFreeAd() {
        this.showPostFreeAd.perform(null);
    }

    public void showUpdateAd() {
        this.showUpdateAd.perform(null);
    }

    public void showErrorLoadingPriceInfo(String str) {
        this.showErrorLoadingPriceInfo.perform(str);
    }

    public void promptPromotions(DraftAd draftAd) {
        this.promptPromotions.perform(draftAd);
    }

    public void resetViews() {
        this.resetViews.perform(null);
    }

    public void showAdLoaded() {
        this.showAdLoaded.perform(null);
    }

    public void closeView() {
        this.closeView.perform(null);
    }

    public void closeViewWithOkResult() {
        this.closeViewWithOkResult.perform(null);
    }

    public void showRestoreAd() {
        this.showRestoreAd.perform(null);
    }

    public void showNoNetworkError() {
        this.showNoNetworkError.perform(null);
    }
}
