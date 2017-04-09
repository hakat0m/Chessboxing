package com.gumtree.android.postad.presenters;

import android.support.annotation.NonNull;
import com.ebay.classifieds.capi.ads.Attribute;
import com.gumtree.android.mvp.Presenter;
import com.gumtree.android.mvp.PresenterView;
import com.gumtree.android.postad.DraftAd;
import com.gumtree.android.postad.PostAdImage;
import com.gumtree.android.postad.contactdetails.models.ContactDetailsData;
import com.gumtree.android.postad.customdetails.models.CustomDetailsData;
import com.gumtree.android.postad.payment.models.OrderData;
import com.gumtree.android.postad.promote.PromotionFeature;
import java.util.List;

public interface PostAdSummaryPresenter extends Presenter<View> {

    public interface View extends PresenterView {
        void addImage(List<PostAdImage> list, int i, String str);

        void closeView();

        void closeViewWithOkResult();

        void editImage(List<PostAdImage> list, int i, String str);

        void enablePromotions(boolean z);

        void hideCost();

        void openAttributeDetails(CustomDetailsData customDetailsData, boolean z, String str);

        void openCategoryDetails();

        void openContactDetails(ContactDetailsData contactDetailsData);

        void openHelp();

        void openManageAds();

        void openNewPostAd();

        void openPaymentDetails(OrderData orderData);

        void promptPromotions(DraftAd draftAd);

        void resetViews();

        void setSelectedPriceFrequency(String str);

        void showAdLoaded();

        void showAdLoadingError(String str);

        void showAdSavingError(String str);

        void showAttributeDetails(String str);

        void showAttributeDetailsError(String str);

        void showAttributeDetailsField(boolean z);

        void showAttributes(List<Attribute> list);

        void showCategory(String str, boolean z);

        void showCategoryError(String str);

        void showContactDetails(String str);

        void showContactDetailsError(String str);

        void showCostPaidAd(double d, @NonNull List<String> list);

        void showDescription(String str);

        void showDescriptionError(String str);

        void showDescriptionValid(boolean z);

        void showErrorLoadingPriceInfo(String str);

        void showFeatures(boolean z, boolean z2, boolean z3, boolean z4);

        void showImageDownloadError();

        void showImageUploadError();

        void showImages(List<PostAdImage> list);

        void showImagesLoading(boolean z);

        void showLoading(boolean z);

        void showLoginScreen();

        void showNoNetworkError();

        void showPostAdFeedbackConfirmation(String str);

        void showPostFreeAd();

        void showPostPaidAd();

        void showPostValidationError();

        void showPrice(String str);

        void showPriceError(String str);

        void showPriceField(boolean z);

        void showPriceFrequency(String str, List<String> list);

        void showPriceFrequencyError(String str);

        void showPriceFrequencyField(boolean z);

        void showPriceFrequencyValid(boolean z);

        void showPriceValid(boolean z);

        void showReset(boolean z);

        void showRestoreAd();

        void showSending(boolean z);

        void showTitle(String str);

        void showTitleError(String str);

        void showTitleValid(boolean z);

        void showUpdateAd();
    }

    void onAddImage();

    void onAttributeDetailsClicked();

    void onAttributeDetailsSelected(CustomDetailsData customDetailsData);

    void onCategorySelected(String str, String str2, String str3);

    void onConfirmationScreenCloseSelected();

    void onConfirmationScreenFindOutMoreSelected();

    void onConfirmationScreenManageAdsSelected();

    void onConfirmationScreenPostAnotherAdSelected();

    void onContactDetailsChanged(ContactDetailsData contactDetailsData);

    void onContactDetailsClicked();

    void onDescriptionChanged(String str);

    void onDescriptionFocusLost();

    void onEditImage(int i);

    void onFeatureSelected(List<PromotionFeature> list);

    void onInitialise();

    void onMenuPrepared();

    void onPriceChanged(String str);

    void onPriceFocusLost();

    void onPriceFrequencyChanged(String str);

    void onPriceFrequencyFocusLost();

    void onPriceFrequencySpinnerChanged(String str);

    void onPromotion();

    void onRequestCloseView();

    void onResetMenuItemSelected();

    void onSelectCategory();

    void onSelectedImages(List<PostAdImage> list);

    void onSubmitWithImages();

    void onSubmitWithoutImages();

    void onTitleChanged(String str);

    void onTitleFocusLost();

    void retrySubmitWithImages();
}
