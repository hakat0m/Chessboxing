package com.gumtree.android.postad.presenters;

import android.support.annotation.NonNull;
import com.apptentive.android.sdk.BuildConfig;
import com.ebay.classifieds.capi.LocalisedTextProvider;
import com.ebay.classifieds.capi.exceptions.ApiException;
import com.ebay.classifieds.capi.users.ads.MyAd;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.auth.UserLocation;
import com.gumtree.android.category.model.DraftCategory;
import com.gumtree.android.common.utils.Log;
import com.gumtree.android.metadata.model.DraftAdMetadata;
import com.gumtree.android.metadata.model.DraftAdMetadataAttribute;
import com.gumtree.android.postad.DraftAd;
import com.gumtree.android.postad.DraftAdAttribute;
import com.gumtree.android.postad.DraftLocation;
import com.gumtree.android.postad.PostAdImage;
import com.gumtree.android.postad.contactdetails.models.ContactDetailsData;
import com.gumtree.android.postad.customdetails.models.CustomDetailsData;
import com.gumtree.android.postad.payment.models.OrderData;
import com.gumtree.android.postad.presenters.PostAdSummaryPresenter.View;
import com.gumtree.android.postad.promote.PromotionFeature;
import com.gumtree.android.postad.services.Association;
import com.gumtree.android.postad.services.ContactDetailsDataDraftAdModelConverter;
import com.gumtree.android.postad.services.ImageDownloadService;
import com.gumtree.android.postad.services.ImageUploadService;
import com.gumtree.android.postad.services.PostAdService;
import com.gumtree.android.postad.services.TrackingPostAdService;
import com.gumtree.android.postad.validation.EmittingValidationRules;
import com.gumtree.android.repositories.Repository;
import com.gumtree.android.services.NetworkState;
import com.gumtree.android.services.NetworkStateService;
import com.gumtree.android.userprofile.services.UserService;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Actions;
import rx.subjects.BehaviorSubject;
import rx.subscriptions.CompositeSubscription;

public class DefaultPostAdSummaryPresenter implements PostAdSummaryPresenter {
    private static final String IMAGES_FOLDER = (Log.TAG + File.separator + "PostAdImages");
    private static final int IMAGE_FINAL_UPLOAD_DELAY_TIME_MS = 1000;
    private static final int IMAGE_FINAL_UPLOAD_MAX_RETRIES = 1;
    private static final int IMAGE_UPLOAD_DELAY_TIME_MS = 1500;
    private static final int IMAGE_UPLOAD_MAX_RETRIES = 5;
    private static final int MAX_NUM_IMAGES = 9;
    private static final String SELLER_TYPE = "seller_type";
    private final BaseAccountManager accountManager;
    private String adId;
    private boolean autoValidateDetailsScreen;
    private boolean canPost;
    private final BehaviorSubject<DraftCategory> categoryChanged = BehaviorSubject.create();
    private BehaviorSubject<ContactDetailsData> contactDetailsDataBehaviorSubject = BehaviorSubject.create();
    private boolean delayedPostAd;
    private DraftAd draftAd;
    private final BehaviorSubject<List<DraftAdAttribute>> draftAdAttributesValuesSet = BehaviorSubject.create();
    private final BehaviorSubject<DraftAd> draftAdChanged = BehaviorSubject.create();
    private final BehaviorSubject<DraftAd> draftAdLoaded = BehaviorSubject.create();
    private final BehaviorSubject<DraftAdMetadata> draftAdMetadataAttributesSet = BehaviorSubject.create();
    private final ContactDetailsDataDraftAdModelConverter draftAddConverter;
    private final BehaviorSubject<Boolean> featuresLoaded = BehaviorSubject.create();
    private final BehaviorSubject<List<PromotionFeature>> featuresSelected = BehaviorSubject.create();
    private final ImageDownloadService imageDownloadService;
    private final ImageUploadService imageUploadService;
    private ImageUploadStatus imageUploadStatus = ImageUploadStatus.IDLE;
    private Subscription imageUploadSubscription;
    private final BehaviorSubject<Boolean> isDraftAdLoading = BehaviorSubject.create(Boolean.valueOf(false));
    private boolean isOnline;
    private final BehaviorSubject<DraftLocation> locationSelected = BehaviorSubject.create();
    private final MetadataBee metadataBee;
    private final NetworkStateService networkStateService;
    private Subscription networkStateSubscription;
    private BehaviorSubject<CustomDetailsData> onAttributesValuesUpdated = BehaviorSubject.create();
    private BehaviorSubject<Triple<String, String, String>> onCategorySelected = BehaviorSubject.create();
    private BehaviorSubject<CustomDetailsData> onMetadataAttributesUpdated = BehaviorSubject.create();
    private final BehaviorSubject<Object> onRequestCloseView = BehaviorSubject.create();
    private BehaviorSubject<List<PromotionFeature>> onSelectedFeaturesUpdated = BehaviorSubject.create();
    private Map<String, PostAdImage> postAdImageMap;
    private final PostAdService postAdService;
    private boolean posted;
    private final PriceInfoBee priceInfoBee;
    private final Repository<DraftAd> repository;
    private BehaviorSubject<List<PromotionFeature>> requestPromotionsBehaviorSubject = BehaviorSubject.create();
    private int retryCount;
    private BehaviorSubject<List<PostAdImage>> selectedImagesBehaviorSubject = BehaviorSubject.create();
    private boolean showAttributesDetailsError;
    private boolean showCategoryErrorMessage;
    private boolean showContactDetailsErrorMessage;
    private final LocalisedTextProvider strings;
    private final CompositeSubscription subscriptions;
    private final TrackingPostAdService trackingService;
    private final UserService userService;
    private final EmittingValidationRules validationRules;
    private final Observable<Boolean> validity;
    private final GatedPostAdSummaryView view;

    static /* synthetic */ int access$704(DefaultPostAdSummaryPresenter defaultPostAdSummaryPresenter) {
        int i = defaultPostAdSummaryPresenter.retryCount + IMAGE_FINAL_UPLOAD_MAX_RETRIES;
        defaultPostAdSummaryPresenter.retryCount = i;
        return i;
    }

    public DefaultPostAdSummaryPresenter(@NonNull GatedPostAdSummaryView gatedPostAdSummaryView, @NonNull PostAdService postAdService, @NonNull Repository<DraftAd> repository, @NonNull BaseAccountManager baseAccountManager, @NonNull ImageUploadService imageUploadService, @NonNull ImageDownloadService imageDownloadService, @NonNull UserService userService, @NonNull EmittingValidationRules emittingValidationRules, @NonNull LocalisedTextProvider localisedTextProvider, @NonNull PriceInfoBee priceInfoBee, @NonNull ContactDetailsDataDraftAdModelConverter contactDetailsDataDraftAdModelConverter, @NonNull NetworkStateService networkStateService, @NonNull MetadataBee metadataBee, @NonNull TrackingPostAdService trackingPostAdService, String str) {
        this.view = (GatedPostAdSummaryView) Validate.notNull(gatedPostAdSummaryView);
        this.postAdService = (PostAdService) Validate.notNull(postAdService);
        this.repository = (Repository) Validate.notNull(repository);
        this.accountManager = (BaseAccountManager) Validate.notNull(baseAccountManager);
        this.imageUploadService = (ImageUploadService) Validate.notNull(imageUploadService);
        this.imageDownloadService = (ImageDownloadService) Validate.notNull(imageDownloadService);
        this.userService = (UserService) Validate.notNull(userService);
        this.validationRules = (EmittingValidationRules) Validate.notNull(emittingValidationRules);
        this.strings = (LocalisedTextProvider) Validate.notNull(localisedTextProvider);
        this.priceInfoBee = (PriceInfoBee) Validate.notNull(priceInfoBee);
        this.draftAddConverter = (ContactDetailsDataDraftAdModelConverter) Validate.notNull(contactDetailsDataDraftAdModelConverter);
        this.networkStateService = (NetworkStateService) Validate.notNull(networkStateService);
        this.trackingService = (TrackingPostAdService) Validate.notNull(trackingPostAdService);
        this.adId = str;
        this.metadataBee = (MetadataBee) Validate.notNull(metadataBee);
        this.validity = Observable.combineLatest(emittingValidationRules.getTitleValidity(), emittingValidationRules.getCategoryValidity(), emittingValidationRules.getPriceValidity(), emittingValidationRules.getDescriptionValidity(), emittingValidationRules.getAttributeDetailsValidity(), emittingValidationRules.getContactDetailsValidity(), DefaultPostAdSummaryPresenter$$Lambda$1.lambdaFactory$());
        r2 = new Subscription[23];
        r2[0] = connectLoadingIndicators(DefaultPostAdSummaryPresenter$$Lambda$2.lambdaFactory$(gatedPostAdSummaryView), metadataBee.isLoading(), this.isDraftAdLoading);
        r2[IMAGE_FINAL_UPLOAD_MAX_RETRIES] = connectEnablingPromotions();
        r2[2] = connectChangeDraftAd();
        r2[3] = connectMetadataService();
        r2[4] = connectPopulateScreen();
        r2[IMAGE_UPLOAD_MAX_RETRIES] = connectShowFeatures();
        r2[6] = connectSelectedFeaturesUpdated();
        r2[7] = connectSelectedFeaturesAndPriceInfo();
        r2[8] = connectEnablePosting();
        r2[MAX_NUM_IMAGES] = connectContactDetails();
        r2[10] = connectFeatureClear();
        r2[11] = connectPriceFrequencyClear();
        r2[12] = connectContactDetailsChanged();
        r2[13] = connectSelectedImages();
        r2[14] = connectCategoryChanged();
        r2[15] = connectRequestFeatures();
        r2[16] = connectMetadataAttributesUpdated();
        r2[17] = connectAttributesValuesUpdated();
        r2[18] = connectUpdateAttributesView();
        r2[19] = connectRequestCloseView(DefaultPostAdSummaryPresenter$$Lambda$3.lambdaFactory$(this), metadataBee.isLoading(), this.onRequestCloseView.asObservable());
        r2[20] = connectClearCategory(DefaultPostAdSummaryPresenter$$Lambda$4.lambdaFactory$(this), metadataBee.errors(), this.onRequestCloseView.asObservable(), metadataBee.isLoading());
        r2[21] = connectCancelMetadataLoad(DefaultPostAdSummaryPresenter$$Lambda$5.lambdaFactory$(metadataBee), metadataBee.isLoading(), this.onRequestCloseView.asObservable());
        r2[22] = connectClearAttributes(DefaultPostAdSummaryPresenter$$Lambda$6.lambdaFactory$(this), this.categoryChanged.asObservable());
        this.subscriptions = new CompositeSubscription(r2);
    }

    static /* synthetic */ Boolean lambda$new$0(Boolean bool, Boolean bool2, Boolean bool3, Boolean bool4, Boolean bool5, Boolean bool6) {
        boolean z;
        if (bool.booleanValue() && bool2.booleanValue() && bool3.booleanValue() && bool4.booleanValue() && bool5.booleanValue() && bool6.booleanValue()) {
            z = true;
        } else {
            z = false;
        }
        return Boolean.valueOf(z);
    }

    /* synthetic */ void lambda$new$2(Boolean bool) {
        onCloseScreen();
    }

    /* synthetic */ void lambda$new$3(Boolean bool) {
        this.onCategorySelected.onNext(null);
    }

    /* synthetic */ void lambda$new$5(Object obj) {
        this.onMetadataAttributesUpdated.onNext(null);
    }

    private Subscription connectClearAttributes(Action1<Object> action1, Observable<DraftCategory> observable) {
        return observable.subscribe(action1);
    }

    private Subscription connectRequestCloseView(Action1<Boolean> action1, Observable<Boolean> observable, Observable<Object> observable2) {
        return observable2.withLatestFrom(observable, DefaultPostAdSummaryPresenter$$Lambda$7.lambdaFactory$()).filter(DefaultPostAdSummaryPresenter$$Lambda$8.lambdaFactory$()).take(IMAGE_FINAL_UPLOAD_MAX_RETRIES).subscribe(action1);
    }

    static /* synthetic */ Boolean lambda$connectRequestCloseView$6(Object obj, Boolean bool) {
        return Boolean.valueOf(!bool.booleanValue());
    }

    static /* synthetic */ Boolean lambda$connectRequestCloseView$7(Boolean bool) {
        return bool;
    }

    private Subscription connectCancelMetadataLoad(Action1<Boolean> action1, Observable<Boolean> observable, Observable<Object> observable2) {
        return observable2.withLatestFrom(observable, DefaultPostAdSummaryPresenter$$Lambda$9.lambdaFactory$()).filter(DefaultPostAdSummaryPresenter$$Lambda$10.lambdaFactory$()).subscribe(action1);
    }

    static /* synthetic */ Boolean lambda$connectCancelMetadataLoad$8(Object obj, Boolean bool) {
        return bool;
    }

    static /* synthetic */ Boolean lambda$connectCancelMetadataLoad$9(Boolean bool) {
        return bool;
    }

    private Subscription connectClearCategory(Action1<Boolean> action1, Observable<Throwable> observable, Observable<Object> observable2, Observable<Boolean> observable3) {
        return Observable.merge(observable.map(DefaultPostAdSummaryPresenter$$Lambda$11.lambdaFactory$()), observable2.withLatestFrom(observable3, DefaultPostAdSummaryPresenter$$Lambda$12.lambdaFactory$())).filter(DefaultPostAdSummaryPresenter$$Lambda$13.lambdaFactory$()).subscribe(action1);
    }

    static /* synthetic */ Boolean lambda$connectClearCategory$11(Object obj, Boolean bool) {
        return bool;
    }

    static /* synthetic */ Boolean lambda$connectClearCategory$12(Boolean bool) {
        return bool;
    }

    @SafeVarargs
    private final Subscription connectLoadingIndicators(Action1<Boolean> action1, Observable<Boolean>... observableArr) {
        return Observable.combineLatest(Arrays.asList(observableArr), DefaultPostAdSummaryPresenter$$Lambda$14.lambdaFactory$()).distinctUntilChanged().subscribe(action1);
    }

    static /* synthetic */ Boolean lambda$connectLoadingIndicators$13(Object[] objArr) {
        boolean z = false;
        int i = 0;
        while (!z && i < objArr.length) {
            boolean toBoolean = BooleanUtils.toBoolean((Boolean) objArr[i]);
            i += IMAGE_FINAL_UPLOAD_MAX_RETRIES;
            z = toBoolean;
        }
        return Boolean.valueOf(z);
    }

    private Subscription connectContactDetails() {
        return this.draftAdChanged.filter(DefaultPostAdSummaryPresenter$$Lambda$15.lambdaFactory$()).subscribe(DefaultPostAdSummaryPresenter$$Lambda$16.lambdaFactory$(this));
    }

    static /* synthetic */ Boolean lambda$connectContactDetails$14(DraftAd draftAd) {
        return Boolean.valueOf(draftAd != null);
    }

    /* synthetic */ void lambda$connectContactDetails$15(DraftAd draftAd) {
        this.validationRules.contactValidationMessage(draftAd);
    }

    private Subscription connectFeatureClear() {
        return Observable.merge(this.categoryChanged.distinctUntilChanged(), this.locationSelected.distinctUntilChanged(), Observable.merge(this.draftAdChanged.filter(DefaultPostAdSummaryPresenter$$Lambda$17.lambdaFactory$()).map(DefaultPostAdSummaryPresenter$$Lambda$18.lambdaFactory$()), this.draftAdAttributesValuesSet).distinctUntilChanged().flatMap(DefaultPostAdSummaryPresenter$$Lambda$19.lambdaFactory$()).distinctUntilChanged()).subscribe(DefaultPostAdSummaryPresenter$$Lambda$20.lambdaFactory$(this));
    }

    static /* synthetic */ Boolean lambda$connectFeatureClear$16(DraftAd draftAd) {
        return Boolean.valueOf(draftAd != null);
    }

    /* synthetic */ void lambda$connectFeatureClear$21(Object obj) {
        this.onSelectedFeaturesUpdated.onNext(null);
    }

    private Subscription connectPriceFrequencyClear() {
        return this.categoryChanged.distinctUntilChanged().map(DefaultPostAdSummaryPresenter$$Lambda$21.lambdaFactory$(this)).subscribe(DefaultPostAdSummaryPresenter$$Lambda$22.lambdaFactory$());
    }

    /* synthetic */ DraftAd lambda$connectPriceFrequencyClear$22(DraftCategory draftCategory) {
        return this.draftAd;
    }

    private Subscription connectSelectedFeaturesAndPriceInfo() {
        Observable refCount = createPromotionFeaturesTrigger().concatMap(DefaultPostAdSummaryPresenter$$Lambda$23.lambdaFactory$(this)).filter(DefaultPostAdSummaryPresenter$$Lambda$24.lambdaFactory$(this)).publish().refCount();
        Observable flatMap = refCount.flatMap(DefaultPostAdSummaryPresenter$$Lambda$25.lambdaFactory$(this));
        refCount = this.priceInfoBee.calculatePriceAndReasons(refCount);
        Observable completedWithoutErrors = this.priceInfoBee.completedWithoutErrors();
        Observable errors = this.priceInfoBee.errors();
        return new CompositeSubscription(new Subscription[]{completedWithoutErrors.subscribe(DefaultPostAdSummaryPresenter$$Lambda$26.lambdaFactory$(this)), errors.subscribe(DefaultPostAdSummaryPresenter$$Lambda$27.lambdaFactory$(this)), flatMap.subscribe(DefaultPostAdSummaryPresenter$$Lambda$28.lambdaFactory$(this)), refCount.subscribe(DefaultPostAdSummaryPresenter$$Lambda$29.lambdaFactory$(this))});
    }

    /* synthetic */ Observable lambda$connectSelectedFeaturesAndPriceInfo$24(PromotionFeaturesTrigger promotionFeaturesTrigger) {
        return this.priceInfoBee.fetchPromotionFeatures(promotionFeaturesTrigger.getDraftCategory(), promotionFeaturesTrigger.getDraftLocation(), promotionFeaturesTrigger.getSellerType(), promotionFeaturesTrigger.getSelectedFeatures(), promotionFeaturesTrigger.getAdId());
    }

    /* synthetic */ Boolean lambda$connectSelectedFeaturesAndPriceInfo$25(List list) {
        boolean z = (this.draftAd == null || this.draftAd.getCategory() == null) ? false : true;
        return Boolean.valueOf(z);
    }

    /* synthetic */ Observable lambda$connectSelectedFeaturesAndPriceInfo$27(List list) {
        return Observable.from(list).mergeWith(Observable.from(this.draftAd.getSelectedFeatures())).distinct(DefaultPostAdSummaryPresenter$$Lambda$100.lambdaFactory$()).toList();
    }

    /* synthetic */ void lambda$connectSelectedFeaturesAndPriceInfo$28(Boolean bool) {
        this.featuresLoaded.onNext(bool);
    }

    /* synthetic */ void lambda$connectSelectedFeaturesAndPriceInfo$29(Throwable th) {
        this.view.showErrorLoadingPriceInfo(extractApiMessage(th));
    }

    /* synthetic */ void lambda$connectSelectedFeaturesAndPriceInfo$30(List list) {
        this.onSelectedFeaturesUpdated.onNext(list);
    }

    private Observable<PromotionFeaturesTrigger> createPromotionFeaturesTrigger() {
        Observable distinctUntilChanged = Observable.merge(this.draftAdChanged.filter(DefaultPostAdSummaryPresenter$$Lambda$30.lambdaFactory$()).map(DefaultPostAdSummaryPresenter$$Lambda$31.lambdaFactory$()), this.categoryChanged).filter(DefaultPostAdSummaryPresenter$$Lambda$32.lambdaFactory$()).distinctUntilChanged();
        Observable distinctUntilChanged2 = Observable.merge(this.draftAdChanged.filter(DefaultPostAdSummaryPresenter$$Lambda$33.lambdaFactory$()).map(DefaultPostAdSummaryPresenter$$Lambda$34.lambdaFactory$()), this.locationSelected).filter(DefaultPostAdSummaryPresenter$$Lambda$35.lambdaFactory$()).distinctUntilChanged();
        return Observable.combineLatest(this.draftAdLoaded, Observable.merge(this.draftAdChanged.filter(DefaultPostAdSummaryPresenter$$Lambda$36.lambdaFactory$()).map(DefaultPostAdSummaryPresenter$$Lambda$37.lambdaFactory$()), this.featuresSelected).distinctUntilChanged(), distinctUntilChanged, distinctUntilChanged2, Observable.merge(this.draftAdChanged.filter(DefaultPostAdSummaryPresenter$$Lambda$38.lambdaFactory$()).map(DefaultPostAdSummaryPresenter$$Lambda$39.lambdaFactory$()), this.draftAdAttributesValuesSet).distinctUntilChanged().flatMap(DefaultPostAdSummaryPresenter$$Lambda$40.lambdaFactory$()), DefaultPostAdSummaryPresenter$$Lambda$41.lambdaFactory$());
    }

    static /* synthetic */ Boolean lambda$createPromotionFeaturesTrigger$31(DraftAd draftAd) {
        return Boolean.valueOf(draftAd != null);
    }

    static /* synthetic */ Boolean lambda$createPromotionFeaturesTrigger$33(DraftCategory draftCategory) {
        return Boolean.valueOf(draftCategory != null);
    }

    static /* synthetic */ Boolean lambda$createPromotionFeaturesTrigger$34(DraftAd draftAd) {
        return Boolean.valueOf(draftAd != null);
    }

    static /* synthetic */ Boolean lambda$createPromotionFeaturesTrigger$36(DraftLocation draftLocation) {
        return Boolean.valueOf(draftLocation != null);
    }

    static /* synthetic */ Boolean lambda$createPromotionFeaturesTrigger$37(DraftAd draftAd) {
        return Boolean.valueOf(draftAd != null);
    }

    static /* synthetic */ Boolean lambda$createPromotionFeaturesTrigger$39(DraftAd draftAd) {
        return Boolean.valueOf(draftAd != null);
    }

    private void updateWithAmountsAndReasons(Pair<Double, List<String>> pair) {
        double doubleValue = ((Double) pair.getLeft()).doubleValue();
        List list = (List) pair.getRight();
        updateDraftAdTotalAdPriceAndReasons(doubleValue, list);
        showCost(doubleValue, list);
        showPostButton(this.draftAd);
    }

    private Subscription connectEnablingPromotions() {
        return this.validity.subscribe(DefaultPostAdSummaryPresenter$$Lambda$42.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$connectEnablingPromotions$45(Boolean bool) {
        this.view.enablePromotions(bool.booleanValue());
    }

    private Subscription connectContactDetailsChanged() {
        return Observable.combineLatest(this.contactDetailsDataBehaviorSubject, this.draftAdLoaded, DefaultPostAdSummaryPresenter$$Lambda$43.lambdaFactory$()).subscribe(DefaultPostAdSummaryPresenter$$Lambda$44.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$connectContactDetailsChanged$47(Pair pair) {
        ContactDetailsData contactDetailsData = (ContactDetailsData) pair.getLeft();
        DraftAd draftAd = (DraftAd) pair.getRight();
        if (contactDetailsData != null) {
            DraftAd updateDraftAdWithContactDetailsData = this.draftAddConverter.updateDraftAdWithContactDetailsData(draftAd, contactDetailsData);
            this.showContactDetailsErrorMessage = true;
            showContactDetails();
            this.locationSelected.onNext(updateDraftAdWithContactDetailsData.getLocation());
            if (this.validationRules.contactValidationMessage(updateDraftAdWithContactDetailsData) == null) {
                this.trackingService.eventContactValidated();
            }
        }
    }

    private Subscription connectSelectedImages() {
        return Observable.combineLatest(this.selectedImagesBehaviorSubject, this.draftAdLoaded, DefaultPostAdSummaryPresenter$$Lambda$45.lambdaFactory$()).subscribe(DefaultPostAdSummaryPresenter$$Lambda$46.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$connectSelectedImages$52(Pair pair) {
        List list = (List) pair.getLeft();
        if (list != null) {
            this.view.showImages(list);
            ((DraftAd) pair.getRight()).setPostAdImages(list);
            this.trackingService.eventAddImage(list);
            this.postAdImageMap = new HashMap();
            this.imageUploadSubscription = this.imageUploadService.upload(Observable.from(list).filter(DefaultPostAdSummaryPresenter$$Lambda$95.lambdaFactory$()).map(DefaultPostAdSummaryPresenter$$Lambda$96.lambdaFactory$(this)).map(DefaultPostAdSummaryPresenter$$Lambda$97.lambdaFactory$())).retryWhen(new RetryWithDelay(this, IMAGE_UPLOAD_MAX_RETRIES, IMAGE_UPLOAD_DELAY_TIME_MS)).subscribe(new ImageUploadObserver(this, null));
            this.imageUploadStatus = ImageUploadStatus.UPLOADING;
        }
    }

    static /* synthetic */ Boolean lambda$null$49(PostAdImage postAdImage) {
        boolean z = postAdImage.isModifiedByUser() || postAdImage.getUrl() == null;
        return Boolean.valueOf(z);
    }

    /* synthetic */ String lambda$null$50(PostAdImage postAdImage) {
        String path = postAdImage.getPath();
        this.postAdImageMap.put(path, postAdImage);
        return path;
    }

    private Subscription connectCategoryChanged() {
        Observable refCount = Observable.combineLatest(this.onCategorySelected, this.draftAdLoaded, DefaultPostAdSummaryPresenter$$Lambda$47.lambdaFactory$()).publish().refCount().map(DefaultPostAdSummaryPresenter$$Lambda$48.lambdaFactory$()).publish().refCount();
        return new CompositeSubscription(new Subscription[]{refCount.filter(DefaultPostAdSummaryPresenter$$Lambda$49.lambdaFactory$(this)).subscribe(DefaultPostAdSummaryPresenter$$Lambda$50.lambdaFactory$(this)), refCount.subscribe(DefaultPostAdSummaryPresenter$$Lambda$51.lambdaFactory$(this))});
    }

    static /* synthetic */ Triple lambda$connectCategoryChanged$53(Triple triple, DraftAd draftAd) {
        return triple;
    }

    static /* synthetic */ DraftCategory lambda$connectCategoryChanged$54(Triple triple) {
        if (triple == null) {
            return null;
        }
        return DraftCategory.builder().id((String) triple.getLeft()).localisedName((String) triple.getMiddle()).path((String) triple.getRight()).build();
    }

    /* synthetic */ Boolean lambda$connectCategoryChanged$55(DraftCategory draftCategory) {
        return Boolean.valueOf(!equals(draftCategory, this.draftAd.getCategory()));
    }

    /* synthetic */ void lambda$connectCategoryChanged$56(DraftCategory draftCategory) {
        this.draftAd.setCategory(draftCategory);
        this.draftAd.setAttributesValues(null);
        updateDraftAdTotalAdPriceAndReasons(0.0d, new ArrayList());
        this.view.showAttributeDetails(null);
        showCategory(this.draftAd.getCategory());
        this.autoValidateDetailsScreen = false;
        this.categoryChanged.onNext(draftCategory);
        this.showCategoryErrorMessage = true;
        showCategory(this.draftAd.getCategory());
    }

    /* synthetic */ void lambda$connectCategoryChanged$57(DraftCategory draftCategory) {
        if (this.validationRules.categoryValidationMessage(this.draftAd) == null) {
            this.trackingService.eventCategoryValidated();
        }
    }

    private Subscription connectRequestFeatures() {
        return Observable.combineLatest(this.requestPromotionsBehaviorSubject, this.draftAdLoaded, DefaultPostAdSummaryPresenter$$Lambda$52.lambdaFactory$()).subscribe(DefaultPostAdSummaryPresenter$$Lambda$53.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$connectRequestFeatures$59(Pair pair) {
        List list = (List) pair.getLeft();
        if (list != null) {
            this.featuresSelected.onNext(list);
            this.trackingService.eventPostAdStepPromote(list);
        }
    }

    private Subscription connectAttributesValuesUpdated() {
        return this.onAttributesValuesUpdated.subscribe(DefaultPostAdSummaryPresenter$$Lambda$54.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$connectAttributesValuesUpdated$60(CustomDetailsData customDetailsData) {
        updateDraftAdAttributesValues(customDetailsData != null ? new ArrayList(customDetailsData.getDraftAdAttributesMap().values()) : null);
        DraftAd draftAd = this.draftAd;
        boolean z = customDetailsData != null && customDetailsData.isVrmValidated();
        draftAd.setVrmValidated(z);
        if (customDetailsData != null && this.validationRules.attributeDetailsValidationMessage(this.draftAd) == null) {
            this.trackingService.eventAttributesValidated();
        }
    }

    private Subscription connectMetadataAttributesUpdated() {
        return Observable.combineLatest(this.onMetadataAttributesUpdated, this.draftAdLoaded, DefaultPostAdSummaryPresenter$$Lambda$55.lambdaFactory$()).subscribe(DefaultPostAdSummaryPresenter$$Lambda$56.lambdaFactory$(this));
    }

    static /* synthetic */ CustomDetailsData lambda$connectMetadataAttributesUpdated$61(CustomDetailsData customDetailsData, DraftAd draftAd) {
        return customDetailsData;
    }

    /* synthetic */ void lambda$connectMetadataAttributesUpdated$62(CustomDetailsData customDetailsData) {
        updateMetadataAttributes(customDetailsData != null ? customDetailsData.getMetadata() : null);
        this.onAttributesValuesUpdated.onNext(customDetailsData);
    }

    private Subscription connectSelectedFeaturesUpdated() {
        return Observable.merge(this.onSelectedFeaturesUpdated, this.featuresSelected).subscribe(DefaultPostAdSummaryPresenter$$Lambda$57.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$connectSelectedFeaturesUpdated$63(List list) {
        updateDraftAdSelectedFeatures(list);
    }

    private Subscription connectEnablePosting() {
        return Observable.combineLatest(this.validity, this.featuresLoaded, DefaultPostAdSummaryPresenter$$Lambda$58.lambdaFactory$()).subscribe(DefaultPostAdSummaryPresenter$$Lambda$59.lambdaFactory$(this));
    }

    static /* synthetic */ Boolean lambda$connectEnablePosting$64(Boolean bool, Boolean bool2) {
        boolean z = bool.booleanValue() && bool2.booleanValue();
        return Boolean.valueOf(z);
    }

    /* synthetic */ void lambda$connectEnablePosting$65(Boolean bool) {
        this.canPost = bool.booleanValue();
    }

    private Subscription connectShowFeatures() {
        return this.draftAdChanged.subscribe(DefaultPostAdSummaryPresenter$$Lambda$60.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$connectShowFeatures$66(DraftAd draftAd) {
        if (draftAd == null) {
            this.view.showFeatures(false, false, false, false);
        } else {
            this.view.showFeatures(draftAd.isInSpotlightSelected(), draftAd.isUrgentSelected(), draftAd.isFeaturedSelected(), draftAd.isBumpedUpSelected());
        }
    }

    private Subscription connectPopulateScreen() {
        return this.draftAdChanged.filter(DefaultPostAdSummaryPresenter$$Lambda$61.lambdaFactory$()).subscribe(DefaultPostAdSummaryPresenter$$Lambda$62.lambdaFactory$(this));
    }

    static /* synthetic */ Boolean lambda$connectPopulateScreen$67(DraftAd draftAd) {
        return Boolean.valueOf(draftAd != null);
    }

    /* synthetic */ void lambda$connectPopulateScreen$68(DraftAd draftAd) {
        populateScreen(draftAd);
    }

    private Subscription connectChangeDraftAd() {
        return this.draftAdChanged.subscribe(DefaultPostAdSummaryPresenter$$Lambda$63.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$connectChangeDraftAd$69(DraftAd draftAd) {
        this.draftAd = draftAd;
    }

    private Subscription connectMetadataService() {
        Observable filter = Observable.merge(this.draftAdLoaded.map(DefaultPostAdSummaryPresenter$$Lambda$64.lambdaFactory$()), this.categoryChanged).filter(DefaultPostAdSummaryPresenter$$Lambda$65.lambdaFactory$());
        return new CompositeSubscription(new Subscription[]{this.metadataBee.load(filter).subscribe(DefaultPostAdSummaryPresenter$$Lambda$66.lambdaFactory$(this)), this.metadataBee.errors().subscribe(DefaultPostAdSummaryPresenter$$Lambda$67.lambdaFactory$(this))});
    }

    static /* synthetic */ Boolean lambda$connectMetadataService$71(DraftCategory draftCategory) {
        return Boolean.valueOf(draftCategory != null);
    }

    /* synthetic */ void lambda$connectMetadataService$72(DraftAdMetadata draftAdMetadata) {
        updateMetadataAttributes(draftAdMetadata);
    }

    /* synthetic */ void lambda$connectMetadataService$73(Throwable th) {
        metadataLoadError(th);
    }

    private void metadataLoadError(Throwable th) {
        com.gumtree.Log.e(getClass().getSimpleName(), "Error loading metadataBee Service", th);
        this.categoryChanged.onNext(null);
        this.view.showAdLoadingError(th.getMessage());
    }

    public void attachView(View view) {
        this.view.setDecorated(view);
        this.networkStateSubscription = this.networkStateService.getNetworkState().subscribe(DefaultPostAdSummaryPresenter$$Lambda$68.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$attachView$74(NetworkState networkState) {
        this.isOnline = networkState.isOnline();
    }

    public void detachView() {
        this.view.setDecorated(null);
        saveDraftAd();
        if (this.networkStateSubscription != null && !this.networkStateSubscription.isUnsubscribed()) {
            this.networkStateSubscription.unsubscribe();
        }
    }

    public void destroy() {
        this.subscriptions.unsubscribe();
        this.view.sealIt();
        clearLocalImages();
    }

    public void onInitialise() {
        if (!this.accountManager.isUserLoggedIn()) {
            this.view.showLoginScreen();
        } else if (StringUtils.isBlank(this.adId)) {
            this.view.showAdLoadingError("Invalid Ad Id");
            this.isDraftAdLoading.onNext(Boolean.valueOf(false));
        } else if (this.draftAd != null) {
            this.isDraftAdLoading.onNext(Boolean.valueOf(false));
            this.draftAdChanged.onNext(this.draftAd);
        } else {
            (PostAdSummaryUtils.isNewAd(this.adId) ? this.repository.load(this.adId) : this.postAdService.getDraftAd(this.adId)).doOnSubscribe(DefaultPostAdSummaryPresenter$$Lambda$69.lambdaFactory$(this)).doOnNext(DefaultPostAdSummaryPresenter$$Lambda$70.lambdaFactory$(this)).doOnNext(DefaultPostAdSummaryPresenter$$Lambda$71.lambdaFactory$(this)).subscribe(DefaultPostAdSummaryPresenter$$Lambda$72.lambdaFactory$(this), DefaultPostAdSummaryPresenter$$Lambda$73.lambdaFactory$(this));
        }
    }

    /* synthetic */ void lambda$onInitialise$75() {
        this.isDraftAdLoading.onNext(Boolean.valueOf(true));
    }

    /* synthetic */ void lambda$onInitialise$76(DraftAd draftAd) {
        this.draftAdChanged.onNext(draftAd);
    }

    /* synthetic */ void lambda$onInitialise$77(DraftAd draftAd) {
        this.draftAdLoaded.onNext(draftAd);
    }

    /* synthetic */ void lambda$onInitialise$78(DraftAd draftAd) {
        downloadAdImages(draftAd);
        this.trackingService.eventPostAdScreenResume(draftAd);
        this.isDraftAdLoading.onNext(Boolean.valueOf(false));
        this.view.showAdLoaded();
    }

    /* synthetic */ void lambda$onInitialise$79(Throwable th) {
        onAdLoadingFailed(th);
    }

    private String extractApiMessage(Throwable th) {
        if (th instanceof ApiException) {
            String message = th.getMessage();
            return message == null ? this.strings.unknownError() : message;
        } else {
            com.gumtree.Log.w(getClass().getSimpleName(), "Cannot display a message for this exception.", th);
            return this.strings.unknownError();
        }
    }

    private void saveDraftAd() {
        if (!this.posted && this.draftAd != null && PostAdSummaryUtils.isNewAd(this.draftAd.getId())) {
            this.repository.save(this.draftAd).subscribe();
        }
    }

    private void clearDraftAd() {
        String id = this.draftAd.getId();
        if (this.draftAd != null && PostAdSummaryUtils.isNewAd(id)) {
            this.repository.clear(DraftAd.NEW_AD_ID).subscribe();
        }
    }

    private void clearAndInitializeDraftAd() {
        if (this.draftAd != null) {
            this.repository.clear(DraftAd.NEW_AD_ID).doOnTerminate(DefaultPostAdSummaryPresenter$$Lambda$74.lambdaFactory$(this)).subscribe(Actions.empty(), Actions.empty());
        }
    }

    /* synthetic */ void lambda$clearAndInitializeDraftAd$80() {
        clearSummaryScreen();
    }

    private void clearSummaryScreen() {
        this.draftAd = null;
        this.adId = DraftAd.NEW_AD_ID;
        this.showAttributesDetailsError = false;
        this.autoValidateDetailsScreen = false;
        this.showContactDetailsErrorMessage = false;
        onInitialise();
    }

    private void clearLocalImages() {
        List list;
        if (this.draftAd == null) {
            list = null;
        } else {
            list = this.draftAd.getPostAdImages();
        }
        for (PostAdImage postAdImage : ListUtils.emptyIfNull(r0)) {
            if (postAdImage.getUrl() != null) {
                String path = postAdImage.getPath();
                if (path != null) {
                    try {
                        FileUtils.forceDelete(new File(path));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void onAdLoadingFailed(Throwable th) {
        if (th instanceof FileNotFoundException) {
            this.userService.userUpdates().map(DefaultPostAdSummaryPresenter$$Lambda$75.lambdaFactory$()).take(IMAGE_FINAL_UPLOAD_MAX_RETRIES).subscribe(DefaultPostAdSummaryPresenter$$Lambda$76.lambdaFactory$(this));
            return;
        }
        if (!(th instanceof ApiException)) {
            com.gumtree.Log.d(getClass().getSimpleName(), "unexpected exception", th);
        }
        this.view.showAdLoadingError(th.getMessage());
        this.view.showAdLoaded();
        this.isDraftAdLoading.onNext(Boolean.valueOf(false));
    }

    /* synthetic */ void lambda$onAdLoadingFailed$82(DraftAd draftAd) {
        this.draftAdChanged.onNext(draftAd);
        resetInputBehaviorSubjects();
        this.draftAdLoaded.onNext(draftAd);
        resetBehaviorSubjects();
        this.view.resetViews();
        this.draftAdMetadataAttributesSet.onNext(draftAd.getMetadata());
        this.trackingService.eventPostAdScreenBegin(draftAd);
        this.view.showAdLoaded();
        this.isDraftAdLoading.onNext(Boolean.valueOf(false));
    }

    private void resetInputBehaviorSubjects() {
        this.contactDetailsDataBehaviorSubject.onNext(null);
        this.selectedImagesBehaviorSubject.onNext(null);
        this.onCategorySelected.onNext(null);
        this.requestPromotionsBehaviorSubject.onNext(null);
        this.onMetadataAttributesUpdated.onNext(null);
    }

    private void resetBehaviorSubjects() {
        this.categoryChanged.onNext(null);
        this.locationSelected.onNext(null);
        this.featuresLoaded.onNext(Boolean.valueOf(false));
        this.featuresSelected.onNext(null);
    }

    private void downloadAdImages(DraftAd draftAd) {
        if (ListUtils.emptyIfNull(draftAd.getPostAdImages()).isEmpty()) {
            this.view.showImages(null);
            return;
        }
        this.subscriptions.add(this.imageDownloadService.download(Observable.just(draftAd.getPostAdImages()).filter(DefaultPostAdSummaryPresenter$$Lambda$77.lambdaFactory$()).flatMap(DefaultPostAdSummaryPresenter$$Lambda$78.lambdaFactory$()).map(DefaultPostAdSummaryPresenter$$Lambda$79.lambdaFactory$()).filter(DefaultPostAdSummaryPresenter$$Lambda$80.lambdaFactory$()).map(DefaultPostAdSummaryPresenter$$Lambda$81.lambdaFactory$()).filter(DefaultPostAdSummaryPresenter$$Lambda$82.lambdaFactory$())).doOnSubscribe(DefaultPostAdSummaryPresenter$$Lambda$83.lambdaFactory$(this)).doOnTerminate(DefaultPostAdSummaryPresenter$$Lambda$84.lambdaFactory$(this)).subscribe(DefaultPostAdSummaryPresenter$$Lambda$85.lambdaFactory$(this), DefaultPostAdSummaryPresenter$$Lambda$86.lambdaFactory$(this), DefaultPostAdSummaryPresenter$$Lambda$87.lambdaFactory$(this)));
    }

    static /* synthetic */ Boolean lambda$downloadAdImages$83(List list) {
        return Boolean.valueOf(list != null);
    }

    static /* synthetic */ Boolean lambda$downloadAdImages$86(String str) {
        return Boolean.valueOf(str != null);
    }

    static /* synthetic */ Boolean lambda$downloadAdImages$88(URL url) {
        return Boolean.valueOf(url != null);
    }

    /* synthetic */ void lambda$downloadAdImages$89() {
        this.view.showImagesLoading(true);
    }

    /* synthetic */ void lambda$downloadAdImages$90() {
        this.view.showImagesLoading(false);
    }

    /* synthetic */ void lambda$downloadAdImages$91(Association association) {
        for (PostAdImage postAdImage : this.draftAd.getPostAdImages()) {
            if (postAdImage.getUrl().equals(association.getRemote().toExternalForm())) {
                postAdImage.setPath(association.getLocal().getAbsolutePath());
                return;
            }
        }
    }

    /* synthetic */ void lambda$downloadAdImages$92(Throwable th) {
        this.view.showImageDownloadError();
    }

    /* synthetic */ void lambda$downloadAdImages$93() {
        List postAdImages = this.draftAd.getPostAdImages();
        if (postAdImages != null) {
            this.view.showImages(postAdImages);
        }
    }

    private Subscription connectUpdateAttributesView() {
        return Observable.merge(this.draftAdMetadataAttributesSet, this.draftAdAttributesValuesSet).subscribe(DefaultPostAdSummaryPresenter$$Lambda$88.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$connectUpdateAttributesView$94(Object obj) {
        populateAttributes(this.draftAd);
        validateAttributes(this.draftAd);
    }

    private void validateAttributes(DraftAd draftAd) {
        if (!(draftAd.getMetadataAttributes() == null || calculateAttributesValuesStrings(draftAd.getAttributesValues()) == null)) {
            this.showAttributesDetailsError = true;
        }
        String attributeDetailsValidationMessage = this.validationRules.attributeDetailsValidationMessage(draftAd);
        if (this.showAttributesDetailsError) {
            this.view.showAttributeDetailsError(attributeDetailsValidationMessage);
        }
        this.validationRules.priceValidationMessage(draftAd);
    }

    private void populateScreen(DraftAd draftAd) {
        this.view.showTitle(draftAd.getTitle());
        showCategory(draftAd.getCategory());
        this.view.showPrice(draftAd.getPrice());
        this.view.showDescription(draftAd.getDescription());
        showCost(draftAd.getTotalAdPrice(), draftAd.getSelectedFeaturesReasons());
        showPostButton(draftAd);
        showContactDetails();
        showImages(draftAd);
        populateAttributes(draftAd);
    }

    private void showImages(DraftAd draftAd) {
        List<PostAdImage> postAdImages = draftAd.getPostAdImages();
        if (postAdImages != null) {
            Object obj;
            for (PostAdImage path : postAdImages) {
                if (StringUtils.isBlank(path.getPath())) {
                    obj = null;
                    break;
                }
            }
            obj = IMAGE_FINAL_UPLOAD_MAX_RETRIES;
            if (obj != null) {
                this.view.showImages(postAdImages);
            }
        }
    }

    private void populateAttributes(DraftAd draftAd) {
        boolean z = draftAd.getMetadataAttributes() != null;
        this.view.showAttributeDetailsField(z);
        if (z) {
            Iterable calculateAttributesValuesStrings = calculateAttributesValuesStrings(draftAd.getAttributesValues());
            if (calculateAttributesValuesStrings == null) {
                this.view.showAttributeDetails(null);
            } else {
                Collections.sort(calculateAttributesValuesStrings, DefaultPostAdSummaryPresenter$$Lambda$89.lambdaFactory$());
                this.view.showAttributeDetails(StringUtils.join(calculateAttributesValuesStrings, ", "));
            }
            this.view.showPriceFrequency(draftAd.getPrice(), new ArrayList(getListOfFrequencies()));
        }
        this.view.showPriceFrequencyField(PostAdSummaryUtils.isPriceFrequencyAvailable(draftAd));
        this.view.setSelectedPriceFrequency(draftAd.getSelectedPriceFrequency());
        this.view.showPriceField(PostAdSummaryUtils.isPriceAvailable(draftAd));
    }

    private List<String> getListOfFrequencies() {
        DraftAdMetadataAttribute priceFrequencyAttribute = this.draftAd.getMetadata().getPriceFrequencyAttribute();
        if (priceFrequencyAttribute != null) {
            return priceFrequencyAttribute.getAttributeValuesAsList();
        }
        return Collections.emptyList();
    }

    private List<String> calculateAttributesValuesStrings(List<DraftAdAttribute> list) {
        if (list == null) {
            return null;
        }
        List<String> arrayList = new ArrayList();
        for (DraftAdAttribute draftAdAttribute : list) {
            CharSequence valueLocalisedLabel = draftAdAttribute.getValueLocalisedLabel();
            CharSequence value = draftAdAttribute.getValue();
            if (StringUtils.isNotBlank(valueLocalisedLabel)) {
                arrayList.add(valueLocalisedLabel);
            } else if (StringUtils.isNotBlank(value)) {
                arrayList.add(value);
            }
        }
        return arrayList;
    }

    private void showCost(double d, List<String> list) {
        if (isPaidAd(d)) {
            this.view.showCostPaidAd(d, list);
        } else {
            this.view.hideCost();
        }
    }

    private void showPostButton(DraftAd draftAd) {
        if (PostAdSummaryUtils.isNewAd(draftAd.getId())) {
            if (isPaidAd(draftAd.getTotalAdPrice())) {
                this.view.showPostPaidAd();
            } else {
                this.view.showPostFreeAd();
            }
        } else if (draftAd.getStatus() == null || !MyAd.DELETED.equalsIgnoreCase(draftAd.getStatus().getValue())) {
            this.view.showUpdateAd();
        } else {
            this.view.showRestoreAd();
        }
    }

    private String calculateContactDetailsString(DraftAd draftAd) {
        String contactEmail = draftAd.isContactEmailSelected() ? draftAd.getContactEmail() : BuildConfig.FLAVOR;
        String phone = draftAd.isContactPhoneSelected() ? draftAd.getPhone() : BuildConfig.FLAVOR;
        CharSequence zipCode = draftAd.getZipCode();
        if (StringUtils.isBlank(zipCode)) {
            String localisedName;
            DraftLocation location = draftAd.getLocation();
            if (location != null) {
                localisedName = location.getLocalisedName();
            } else {
                localisedName = null;
            }
            zipCode = StringUtils.join(new String[]{draftAd.getNeighborhood(), localisedName});
        }
        Iterable arrayList = new ArrayList(Arrays.asList(new String[]{zipCode, contactEmail, phone}));
        arrayList.removeAll(Arrays.asList(new String[]{BuildConfig.FLAVOR, null}));
        return StringUtils.join(arrayList, ", ");
    }

    public void onTitleChanged(String str) {
        this.draftAd.setTitle(StringUtils.trimToEmpty(str));
        this.view.showTitleValid(this.validationRules.titleValidationMessage(this.draftAd) == null);
        this.view.showTitleError(null);
    }

    public void onSelectCategory() {
        if (this.isOnline) {
            this.view.openCategoryDetails();
        } else {
            this.view.showNoNetworkError();
        }
    }

    public void onCategorySelected(String str, String str2, String str3) {
        this.onCategorySelected.onNext(Triple.of(str, str2, str3));
    }

    public void onPriceChanged(String str) {
        this.draftAd.setPrice(StringUtils.trimToEmpty(str));
        this.view.showPriceValid(this.validationRules.priceValidationMessage(this.draftAd) == null);
        this.view.showPriceError(null);
    }

    public void onPriceFrequencyChanged(String str) {
        this.draftAd.setPrice(StringUtils.trimToEmpty(str));
        this.view.showPriceFrequencyValid(this.validationRules.priceValidationMessage(this.draftAd) == null);
        this.view.showPriceFrequencyError(null);
    }

    public void onPriceFrequencySpinnerChanged(String str) {
        this.draftAd.setSelectedPriceFrequency(str.toUpperCase());
    }

    public void onDescriptionChanged(String str) {
        this.draftAd.setDescription(StringUtils.trimToEmpty(str));
        this.view.showDescriptionValid(this.validationRules.descriptionValidationMessage(this.draftAd) == null);
        this.view.showDescriptionError(null);
    }

    public void onSubmitWithImages() {
        switch (1.$SwitchMap$com$gumtree$android$postad$presenters$DefaultPostAdSummaryPresenter$ImageUploadStatus[this.imageUploadStatus.ordinal()]) {
            case IMAGE_FINAL_UPLOAD_MAX_RETRIES /*1*/:
                this.view.showSending(false);
                this.view.showImageUploadError();
                return;
            case HighlightView.GROW_LEFT_EDGE /*2*/:
                this.view.showSending(true);
                this.delayedPostAd = true;
                return;
            default:
                this.view.showSending(false);
                if (!areDraftAdImagesValid()) {
                    this.view.showImageUploadError();
                    return;
                } else if (areDraftAdFieldsValid() && this.canPost) {
                    submit();
                    return;
                } else {
                    this.view.showPostValidationError();
                    return;
                }
        }
    }

    public void onSubmitWithoutImages() {
        if (areDraftAdFieldsValid() && this.canPost) {
            this.draftAd.setPostAdImages(null);
            this.view.showImages(null);
            submit();
        }
    }

    private void submit() {
        Observable postDraftAd;
        this.trackingService.eventPostAdAttempt(this.draftAd);
        this.view.showSending(true);
        this.posted = false;
        if (!(PostAdSummaryUtils.isPriceAvailable(this.draftAd) || PostAdSummaryUtils.isPriceFrequencyAvailable(this.draftAd))) {
            this.draftAd.setPrice(null);
        }
        if (PostAdSummaryUtils.isNewAd(this.adId)) {
            postDraftAd = this.postAdService.postDraftAd(this.draftAd);
        } else {
            postDraftAd = this.postAdService.editDraftAd(this.draftAd);
        }
        postDraftAd.subscribe(DefaultPostAdSummaryPresenter$$Lambda$90.lambdaFactory$(this), DefaultPostAdSummaryPresenter$$Lambda$91.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$submit$96(DraftAd draftAd) {
        onPostAdSuccessful(draftAd);
    }

    /* synthetic */ void lambda$submit$97(Throwable th) {
        this.view.showSending(false);
        onPostAdFailed(th);
    }

    private void showContactDetails() {
        String contactValidationMessage = this.validationRules.contactValidationMessage(this.draftAd);
        this.view.showContactDetails(calculateContactDetailsString(this.draftAd));
        if (this.showContactDetailsErrorMessage || contactValidationMessage == null) {
            this.view.showContactDetailsError(contactValidationMessage);
        }
    }

    public void retrySubmitWithImages() {
        Iterable postAdImages = this.draftAd.getPostAdImages();
        this.postAdImageMap = new HashMap();
        Observable upload = this.imageUploadService.upload(Observable.from(postAdImages).filter(DefaultPostAdSummaryPresenter$$Lambda$92.lambdaFactory$()).map(DefaultPostAdSummaryPresenter$$Lambda$93.lambdaFactory$(this)).map(DefaultPostAdSummaryPresenter$$Lambda$94.lambdaFactory$()));
        this.delayedPostAd = true;
        this.imageUploadSubscription = upload.retryWhen(new RetryWithDelay(this, IMAGE_FINAL_UPLOAD_MAX_RETRIES, IMAGE_FINAL_UPLOAD_DELAY_TIME_MS)).subscribe(new ImageUploadObserver(this, null));
        this.imageUploadStatus = ImageUploadStatus.UPLOADING;
        this.view.showSending(true);
    }

    static /* synthetic */ Boolean lambda$retrySubmitWithImages$98(PostAdImage postAdImage) {
        boolean z = postAdImage.isModifiedByUser() || postAdImage.getUrl() == null;
        return Boolean.valueOf(z);
    }

    /* synthetic */ String lambda$retrySubmitWithImages$99(PostAdImage postAdImage) {
        String path = postAdImage.getPath();
        this.postAdImageMap.put(path, postAdImage);
        return path;
    }

    private void onPostAdSuccessful(DraftAd draftAd) {
        String id = draftAd.getId();
        this.trackingService.eventPostAdSuccess(id, this.draftAd);
        List selectedFeatures = this.draftAd.getSelectedFeatures();
        if (selectedFeatures.isEmpty()) {
            this.view.showPostAdFeedbackConfirmation(id);
            updateUserServiceWithLocation();
        } else {
            this.view.openPaymentDetails(OrderData.builder().adId(id).promotionFeatures(selectedFeatures).build());
            this.view.closeView();
            this.trackingService.eventFeatureAdAttempt(id, this.draftAd.getSelectedFeatures());
            updateUserServiceWithLocation();
            clearDraftAd();
        }
        this.posted = true;
    }

    private void updateUserServiceWithLocation() {
        DraftLocation location = this.draftAd.getLocation();
        this.userService.setFallbackLocation(UserLocation.builder().locId(String.valueOf(location.getId())).locName(location.getLocalisedName()).locText(null).locMap(this.draftAd.isVisibleOnMap() ? "true" : "false").locPostcode(this.draftAd.getZipCode()).build());
    }

    private void onPostAdFailed(Throwable th) {
        com.gumtree.Log.d(getClass().getSimpleName(), "PostAdd failed", th);
        this.view.showAdSavingError(extractApiMessage(th));
        this.posted = false;
        this.trackingService.eventPostAdFail(this.draftAd);
    }

    public void onAddImage() {
        if (!(this.imageUploadSubscription == null || this.imageUploadSubscription.isUnsubscribed())) {
            this.imageUploadSubscription.unsubscribe();
        }
        this.view.addImage(this.draftAd.getPostAdImages(), MAX_NUM_IMAGES, IMAGES_FOLDER);
        this.trackingService.eventAddImageBegin(this.draftAd);
    }

    public void onEditImage(int i) {
        if (!(this.imageUploadSubscription == null || this.imageUploadSubscription.isUnsubscribed())) {
            this.imageUploadSubscription.unsubscribe();
        }
        this.view.editImage(this.draftAd.getPostAdImages(), i, IMAGES_FOLDER);
    }

    public void onSelectedImages(List<PostAdImage> list) {
        this.selectedImagesBehaviorSubject.onNext(list);
    }

    public void onContactDetailsClicked() {
        this.view.openContactDetails(this.draftAddConverter.createContactDetailsDataFromDraftAd(this.draftAd));
    }

    public void onContactDetailsChanged(ContactDetailsData contactDetailsData) {
        this.contactDetailsDataBehaviorSubject.onNext(contactDetailsData);
    }

    public void onAttributeDetailsSelected(CustomDetailsData customDetailsData) {
        this.onAttributesValuesUpdated.onNext(customDetailsData);
    }

    public void onAttributeDetailsClicked() {
        Map hashMap = new HashMap();
        List<DraftAdAttribute> attributesValues = this.draftAd.getAttributesValues();
        if (attributesValues != null) {
            for (DraftAdAttribute draftAdAttribute : attributesValues) {
                hashMap.put(draftAdAttribute.getName(), draftAdAttribute);
            }
        }
        CustomDetailsData customDetailsData = new CustomDetailsData(this.draftAd.getMetadata(), hashMap);
        customDetailsData.setVrmValidated(this.draftAd.isVrmValidated());
        if (!PostAdSummaryUtils.isNewAd(this.adId)) {
            this.autoValidateDetailsScreen = true;
        }
        this.view.openAttributeDetails(customDetailsData, this.autoValidateDetailsScreen, this.draftAd.getCategory().getId());
        this.autoValidateDetailsScreen = true;
    }

    public void onPromotion() {
        this.view.promptPromotions(this.draftAd);
    }

    public void onFeatureSelected(@NonNull List<PromotionFeature> list) {
        this.requestPromotionsBehaviorSubject.onNext(new ArrayList(ListUtils.emptyIfNull(list)));
    }

    public void onConfirmationScreenPostAnotherAdSelected() {
        clearDraftAd();
        this.view.openNewPostAd();
    }

    public void onConfirmationScreenManageAdsSelected() {
        clearDraftAd();
        this.view.openManageAds();
        this.view.closeView();
    }

    public void onConfirmationScreenFindOutMoreSelected() {
        clearDraftAd();
        this.view.openHelp();
        this.view.closeView();
    }

    public void onConfirmationScreenCloseSelected() {
        clearDraftAd();
        this.view.closeViewWithOkResult();
    }

    public void onResetMenuItemSelected() {
        clearAndInitializeDraftAd();
        this.trackingService.eventPostAdReset();
    }

    private void onCloseScreen() {
        this.trackingService.eventPostAdBounce(this.draftAd);
        this.view.closeView();
    }

    public void onTitleFocusLost() {
        String titleValidationMessage = this.validationRules.titleValidationMessage(this.draftAd);
        this.view.showTitleError(titleValidationMessage);
        if (titleValidationMessage == null) {
            this.trackingService.eventTitleValidated();
        }
    }

    public void onPriceFocusLost() {
        String priceValidationMessage = this.validationRules.priceValidationMessage(this.draftAd);
        this.view.showPriceError(priceValidationMessage);
        if (priceValidationMessage == null) {
            this.trackingService.eventPriceValidated();
        }
    }

    public void onPriceFrequencyFocusLost() {
        this.view.showPriceFrequencyError(this.validationRules.priceValidationMessage(this.draftAd));
    }

    public void onDescriptionFocusLost() {
        String descriptionValidationMessage = this.validationRules.descriptionValidationMessage(this.draftAd);
        this.view.showDescriptionError(descriptionValidationMessage);
        if (descriptionValidationMessage == null) {
            this.trackingService.eventDescriptionValidated();
        }
    }

    public void onRequestCloseView() {
        this.onRequestCloseView.onNext(null);
    }

    public void onMenuPrepared() {
        this.view.showReset(PostAdSummaryUtils.isNewAd(this.adId));
    }

    private boolean areDraftAdFieldsValid() {
        String titleValidationMessage = this.validationRules.titleValidationMessage(this.draftAd);
        this.view.showTitleError(titleValidationMessage);
        String categoryValidationMessage = this.validationRules.categoryValidationMessage(this.draftAd);
        this.view.showCategoryError(categoryValidationMessage);
        String priceValidationMessage = this.validationRules.priceValidationMessage(this.draftAd);
        this.view.showPriceError(priceValidationMessage);
        String descriptionValidationMessage = this.validationRules.descriptionValidationMessage(this.draftAd);
        this.view.showDescriptionError(descriptionValidationMessage);
        List metadataAttributes = this.draftAd.getMetadataAttributes();
        String attributeDetailsValidationMessage = this.validationRules.attributeDetailsValidationMessage(this.draftAd);
        if (metadataAttributes != null) {
            this.view.showAttributeDetailsError(attributeDetailsValidationMessage);
        }
        this.showAttributesDetailsError = true;
        String contactValidationMessage = this.validationRules.contactValidationMessage(this.draftAd);
        this.view.showContactDetailsError(contactValidationMessage);
        if (titleValidationMessage == null && categoryValidationMessage == null && priceValidationMessage == null && descriptionValidationMessage == null && contactValidationMessage == null) {
            return true;
        }
        return false;
    }

    private boolean areDraftAdImagesValid() {
        List<PostAdImage> postAdImages = this.draftAd.getPostAdImages();
        if (postAdImages == null) {
            return true;
        }
        for (PostAdImage url : postAdImages) {
            if (url.getUrl() == null) {
                return false;
            }
        }
        return true;
    }

    private void showCategory(DraftCategory draftCategory) {
        this.view.showCategory(draftCategory == null ? null : draftCategory.getLocalisedName(), PostAdSummaryUtils.isNewAd(this.adId));
        String categoryValidationMessage = this.validationRules.categoryValidationMessage(this.draftAd);
        if (this.showCategoryErrorMessage || categoryValidationMessage == null) {
            this.view.showCategoryError(categoryValidationMessage);
        }
    }

    private void updateMetadataAttributes(DraftAdMetadata draftAdMetadata) {
        this.draftAd.setMetadata(draftAdMetadata);
        this.draftAdMetadataAttributesSet.onNext(draftAdMetadata);
    }

    private void updateDraftAdAttributesValues(List<DraftAdAttribute> list) {
        this.draftAd.setAttributesValues(list);
        this.draftAdAttributesValuesSet.onNext(list);
    }

    private void updateDraftAdTotalAdPriceAndReasons(double d, List<String> list) {
        this.draftAd.setTotalAdPrice(d);
        this.draftAd.setSelectedFeaturesReasons(list);
    }

    private void updateDraftAdSelectedFeatures(List<PromotionFeature> list) {
        this.draftAd.setSelectedFeatures(list);
    }

    private boolean isPaidAd(double d) {
        return d != 0.0d;
    }

    private <T> boolean equals(T t, T t2) {
        return ObjectUtils.equals(t, t2);
    }
}
