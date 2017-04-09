package com.gumtree.android.postad;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.renderscript.RenderScript;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.apptentive.android.sdk.BuildConfig;
import com.apptentive.android.sdk.R;
import com.ebay.classifieds.capi.ads.Attribute;
import com.ebay.kleinanzeigen.imagepicker.EbkImagePicker;
import com.ebay.kleinanzeigen.imagepicker.image_library.Image;
import com.gumtree.DevelopmentFlags;
import com.gumtree.Log;
import com.gumtree.android.auth.AuthIdentifier;
import com.gumtree.android.auth.AuthenticatorActivity;
import com.gumtree.android.category.NewPostAdCategoryActivity;
import com.gumtree.android.category.NewPostAdCategoryActivity.Result;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.common.activities.StatefulActivity;
import com.gumtree.android.common.utils.ui.BlurredImageTransformation;
import com.gumtree.android.dagger.ComponentsManager;
import com.gumtree.android.dagger.components.ApplicationComponent;
import com.gumtree.android.help.HelpActivity;
import com.gumtree.android.locations.LocationActivity;
import com.gumtree.android.login.AuthActivity;
import com.gumtree.android.managead.ManageAdActivity;
import com.gumtree.android.manageads.ManageAdsActivity;
import com.gumtree.android.message_box.conversation.ConversationIntentService;
import com.gumtree.android.postad.confirmation.PostAdConfirmationScreenActivity;
import com.gumtree.android.postad.confirmation.models.ConfirmationScreenResults;
import com.gumtree.android.postad.contactdetails.PostAdContactDetailsActivity;
import com.gumtree.android.postad.contactdetails.models.ContactDetailsData;
import com.gumtree.android.postad.customdetails.CustomDetailsActivity;
import com.gumtree.android.postad.customdetails.models.CustomDetailsData;
import com.gumtree.android.postad.di.DaggerPostAdComponent;
import com.gumtree.android.postad.di.PostAdComponent;
import com.gumtree.android.postad.di.PostAdModule;
import com.gumtree.android.postad.dialogs.RetryPostDialog;
import com.gumtree.android.postad.dialogs.RetryPostDialog.Listener;
import com.gumtree.android.postad.payment.PostAdPaymentActivity;
import com.gumtree.android.postad.payment.di.PostAdPaymentModule;
import com.gumtree.android.postad.payment.models.OrderData;
import com.gumtree.android.postad.presenters.PostAdSummaryPresenter;
import com.gumtree.android.postad.presenters.PostAdSummaryPresenter.View;
import com.gumtree.android.postad.promote.PromotionActivity;
import com.gumtree.android.postad.views.EditTextSummaryValidationView;
import com.gumtree.android.postad.views.PriceFrequencySummaryValidationView;
import com.gumtree.android.postad.views.PriceInfoView;
import com.gumtree.android.postad.views.PriceInfoView.DetailDialog;
import com.gumtree.android.postad.views.ScrollViewListenerScrollView;
import com.gumtree.android.postad.views.ScrollViewListenerScrollView.ScrollViewListener;
import com.gumtree.android.postad.views.TextSummaryValidationView;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration$Builder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import uk.co.senab.photoview.IPhotoView;

public class PostAdActivity extends BaseActivity implements View, ScrollViewListener {
    private static final String AD_ID_QUERY_PARAM = "advertId";
    private static final String BLURRED_BACKGROUND_BITMAP = "blurred_background_bitmap";
    private static final int DIM_VALUE = 200;
    private static final int LOADING_ANIM_DURATION = 325;
    private static final int LOADING_ANIM_FIELDS_DELAY = 200;
    private static final int LOADING_ANIM_PICTURES_DELAY = 125;
    private static final int LOADING_ANIM_Y_TRANSLATION = 125;
    private static final float OVERSHOOT_TENSION = 3.0f;
    private static final int REQUEST_CONTACT_DETAILS = 21;
    private static final int REQUEST_PROMOTIONS = 22;
    @Inject
    String adId;
    @Bind({2131624175})
    EditTextSummaryValidationView adTitleView;
    @Bind({2131624179})
    TextSummaryValidationView attributesDetailView;
    private Bitmap backgroundBitmap;
    @Bind({2131624164})
    ImageView backgroundImage;
    @BindString(2131165690)
    String bumpedUpFeatureName;
    @Bind({2131624176})
    TextSummaryValidationView categoryView;
    @Bind({2131624181})
    TextSummaryValidationView contactDetailView;
    @Bind({2131624166})
    android.view.View content;
    private int contentBottomMarginBig;
    private int contentBottomMarginBigWithPrice;
    private int contentBottomMarginSmall;
    @Bind({2131624180})
    EditTextSummaryValidationView descriptionView;
    @BindString(2131165693)
    String featuredFeatureName;
    @Bind({2131624174})
    android.view.View fieldsContainer;
    private PostAdSummaryAdapter galleryImagesAdapter;
    @Bind({2131624172})
    android.view.View imageCaptureGalleryIcon;
    @Bind({2131624171})
    android.view.View imageCaptureGalleryLayout;
    @Bind({2131624168})
    android.view.View imageCaptureLayout;
    @Bind({2131624173})
    RecyclerView imageGalleryRow;
    @Bind({2131624170})
    android.view.View imageLoadingSpinner;
    @Bind({2131624191})
    FrameLayout loadingView;
    private Menu menu;
    @Bind({2131624167})
    android.view.View picturesContainer;
    @Bind({2131624190})
    Button postAdButtonView;
    @BindString(2131165720)
    String postFreeAdLabel;
    @BindString(2131165721)
    String postPaidAdLabel;
    @Bind({2131624187})
    Button postadScrollViewButton;
    @Inject
    PostAdSummaryPresenter presenter;
    @Bind({2131624178})
    PriceFrequencySummaryValidationView priceFrequencyView;
    @Bind({2131624188})
    android.view.View priceInfoContainer;
    @Bind({2131624189})
    PriceInfoView priceInfoView;
    @Bind({2131624186})
    PriceInfoView priceInfoView2;
    @Bind({2131624177})
    EditTextSummaryValidationView priceView;
    @BindString(2131165692)
    String promoteButtonDefaultText;
    @BindString(2131165691)
    String promoteButtonTemplate;
    @Bind({2131624184})
    TextView promoteButtonTextView;
    @Bind({2131624182})
    android.view.View promoteContainer;
    @BindString(2131165722)
    String restoreAdLabel;
    @Bind({2131624185})
    android.view.View scrollablePriceInfoContainer;
    @Bind({2131624165})
    ScrollViewListenerScrollView scrollview;
    @BindString(2131165694)
    String spotlightFeatureName;
    @Bind({2131624104})
    Toolbar toolbar;
    @BindString(2131165723)
    String updateAdLabel;
    @BindString(2131165695)
    String urgentFeatureName;

    /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$gumtree$android$postad$confirmation$models$ConfirmationScreenResults = new int[ConfirmationScreenResults.values().length];

        static {
            try {
                $SwitchMap$com$gumtree$android$postad$confirmation$models$ConfirmationScreenResults[ConfirmationScreenResults.POST_ANOTHER_AD.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$gumtree$android$postad$confirmation$models$ConfirmationScreenResults[ConfirmationScreenResults.CLOSE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$gumtree$android$postad$confirmation$models$ConfirmationScreenResults[ConfirmationScreenResults.MANAGE_ADS.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$gumtree$android$postad$confirmation$models$ConfirmationScreenResults[ConfirmationScreenResults.FIND_OUT_MORE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public class GalleryImageEditListener {
        public void editImageAt(int i) {
            PostAdActivity.this.editImage(i);
        }
    }

    public static Intent createIntent(Context context, String str) {
        Intent intent = new Intent(context, PostAdActivity.class);
        intent.setFlags(67108864);
        intent.putExtra(StatefulActivity.EXTRA_POST_AD_ID, str);
        return intent;
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        switch (i) {
            case REQUEST_CONTACT_DETAILS /*21*/:
                if (i2 == -1) {
                    ContactDetailsData analyseResult = PostAdContactDetailsActivity.analyseResult(intent);
                    if (analyseResult != null) {
                        this.presenter.onContactDetailsChanged(analyseResult);
                        return;
                    }
                    return;
                }
                return;
            case REQUEST_PROMOTIONS /*22*/:
                if (i2 == -1) {
                    this.presenter.onFeatureSelected(PromotionActivity.analyse(intent));
                    return;
                }
                return;
            case R.styleable.Toolbar_collapseContentDescription /*23*/:
                if (i2 == -1) {
                    CustomDetailsData analyseResult2 = CustomDetailsActivity.analyseResult(intent);
                    if (analyseResult2 != null) {
                        this.presenter.onAttributeDetailsSelected(analyseResult2);
                        return;
                    }
                    return;
                }
                return;
            case R.styleable.Toolbar_navigationIcon /*24*/:
                if (i2 == -1) {
                    ConfirmationScreenResults analyseResult3 = PostAdConfirmationScreenActivity.analyseResult(intent);
                    if (analyseResult3 != null) {
                        switch (AnonymousClass2.$SwitchMap$com$gumtree$android$postad$confirmation$models$ConfirmationScreenResults[analyseResult3.ordinal()]) {
                            case HighlightView.GROW_NONE /*1*/:
                                this.presenter.onConfirmationScreenPostAnotherAdSelected();
                                return;
                            case HighlightView.GROW_LEFT_EDGE /*2*/:
                                this.presenter.onConfirmationScreenCloseSelected();
                                return;
                            case ImageLoaderConfiguration$Builder.DEFAULT_THREAD_POOL_SIZE /*3*/:
                                this.presenter.onConfirmationScreenManageAdsSelected();
                                return;
                            case HighlightView.GROW_RIGHT_EDGE /*4*/:
                                this.presenter.onConfirmationScreenFindOutMoreSelected();
                                return;
                            default:
                                return;
                        }
                    }
                    return;
                }
                return;
            case ConversationIntentService.DEFAULT_TAIL /*100*/:
                if (i2 == -1) {
                    Result analyseResult4 = NewPostAdCategoryActivity.analyseResult(intent);
                    if (analyseResult4 != null) {
                        this.presenter.onCategorySelected(analyseResult4.getCategoryId(), analyseResult4.getCategoryName(), analyseResult4.getCategoryPathNames());
                        return;
                    }
                    return;
                }
                return;
            case LocationActivity.ACTIVITY_REQUEST_CODE /*999*/:
                if (i2 != -1) {
                    closeView();
                    return;
                } else if (DevelopmentFlags.getInstance().isNewLoginEnabled() && intent.hasExtra("displayname")) {
                    showSnackBar(getString(2131165552, new Object[]{intent.getStringExtra("displayname")}));
                    return;
                } else {
                    return;
                }
            case PostAdPaymentModule.REDIRECT_TO_MANAGE_ADS_TIMEOUT /*1000*/:
                if (i2 == -1 && intent != null && intent.hasExtra("EXTRA_IMAGE_LIST")) {
                    ArrayList arrayList = (ArrayList) intent.getSerializableExtra("EXTRA_IMAGE_LIST");
                    this.presenter.onSelectedImages(PostAdImageConverter.fromEbayKToPostAd(arrayList));
                    if (arrayList.size() > 0) {
                        getBlurredBitmap(((Image) arrayList.get(0)).getFinalPath());
                        return;
                    }
                    this.backgroundBitmap = null;
                    this.backgroundImage.setImageResource(2130837644);
                    return;
                }
                return;
            default:
                Log.e(getClass().getSimpleName(), "case not supported " + i);
                return;
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getPostAdComponent().inject(this);
        setContentView(2130903092);
        ButterKnife.bind(this);
        initialiseImageGallery();
        initPostAdButtons();
        initToolbar();
        initFocusListeners();
        initPriceInputFilter();
        this.priceInfoView.setOnPriceDetailListener(PostAdActivity$$Lambda$1.lambdaFactory$(this));
        this.priceInfoView2.setOnPriceDetailListener(PostAdActivity$$Lambda$2.lambdaFactory$(this));
        if (bundle != null) {
            Bitmap bitmap = (Bitmap) bundle.getParcelable(BLURRED_BACKGROUND_BITMAP);
            if (bitmap != null) {
                setBackgroundBitmap(bitmap);
            }
        }
    }

    /* synthetic */ void lambda$onCreate$0(PriceInfoView priceInfoView) {
        showPriceInfoDetail(priceInfoView);
    }

    /* synthetic */ void lambda$onCreate$1(PriceInfoView priceInfoView) {
        showPriceInfoDetail(priceInfoView);
    }

    private void showPriceInfoDetail(PriceInfoView priceInfoView) {
        DetailDialog createDetailDialog = priceInfoView.createDetailDialog();
        createDetailDialog.show(getSupportFragmentManager(), createDetailDialog.getClass().getSimpleName());
    }

    protected void onPostCreate(Bundle bundle) {
        super.onPostCreate(bundle);
        initTextListeners();
    }

    private PostAdComponent getPostAdComponent() {
        PostAdComponent postAdComponent = (PostAdComponent) ComponentsManager.get().getBaseComponent(PostAdComponent.KEY);
        if (postAdComponent != null) {
            return postAdComponent;
        }
        ApplicationComponent appComponent = ComponentsManager.get().getAppComponent();
        Object build = DaggerPostAdComponent.builder().applicationComponent(appComponent).postAdModule(new PostAdModule(getIntent().getStringExtra(StatefulActivity.EXTRA_POST_AD_ID))).build();
        ComponentsManager.get().putBaseComponent(PostAdComponent.KEY, build);
        return build;
    }

    private void initialiseImageGallery() {
        this.galleryImagesAdapter = new PostAdSummaryAdapter(this, new GalleryImageEditListener());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, 0, false);
        this.imageGalleryRow.setAdapter(this.galleryImagesAdapter);
        this.imageGalleryRow.setLayoutManager(linearLayoutManager);
    }

    protected void onStart() {
        super.onStart();
        this.presenter.onInitialise();
    }

    protected void onResume() {
        super.onResume();
        this.presenter.attachView(this);
    }

    protected void onPause() {
        this.presenter.detachView();
        clearFocus();
        super.onPause();
    }

    protected void onDestroy() {
        if (isFinishing()) {
            ComponentsManager.get().removeBaseComponent(PostAdComponent.KEY);
            this.presenter.destroy();
        }
        super.onDestroy();
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putParcelable(BLURRED_BACKGROUND_BITMAP, this.backgroundBitmap);
    }

    protected boolean isHomeAsUp() {
        return true;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(2131755013, menu);
        this.menu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        this.presenter.onMenuPrepared();
        return super.onPrepareOptionsMenu(menu);
    }

    public void showReset(boolean z) {
        if (this.menu != null) {
            MenuItem findItem = this.menu.findItem(2131624935);
            boolean z2 = z && this.loadingView.getVisibility() != 0;
            findItem.setVisible(z2);
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                onBackPressed();
                break;
            case 2131624935:
                this.presenter.onResetMenuItemSelected();
                break;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void onBackPressed() {
        this.presenter.onRequestCloseView();
    }

    public void showLoginScreen() {
        if (DevelopmentFlags.getInstance().isNewLoginEnabled()) {
            startActivityForResult(AuthActivity.createIntent(AuthIdentifier.POST_AD, this), LocationActivity.ACTIVITY_REQUEST_CODE);
        } else {
            AuthenticatorActivity.startActivity((Activity) this, 1);
        }
    }

    public void showLoading(boolean z) {
        this.loadingView.setVisibility(z ? 0 : 8);
        supportInvalidateOptionsMenu();
    }

    public void showAdLoadingError(String str) {
        promptSnackbar(str);
    }

    public void showPostValidationError() {
        hideKeyboard();
        Toast.makeText(this, 2131165711, 1).show();
    }

    private void promptSnackbar(String str) {
        Snackbar.make(this.scrollview, str, 0).show();
    }

    public void showAdSavingError(String str) {
        promptSnackbar(str);
    }

    public void showImages(List<PostAdImage> list) {
        if (list == null || list.size() == 0) {
            displayLayoutWithNoImages();
            return;
        }
        displayLayoutWithImages(list);
        getBlurredBitmap(((PostAdImage) list.get(0)).getPath());
    }

    public void showImagesLoading(boolean z) {
        if (z) {
            this.imageCaptureLayout.setVisibility(8);
            this.imageLoadingSpinner.setVisibility(0);
            return;
        }
        this.imageLoadingSpinner.setVisibility(8);
        this.imageCaptureLayout.setVisibility(0);
    }

    private void displayLayoutWithNoImages() {
        this.imageCaptureGalleryLayout.setVisibility(8);
        this.imageCaptureLayout.setVisibility(0);
    }

    private void displayLayoutWithImages(@NonNull List<PostAdImage> list) {
        this.imageCaptureLayout.setVisibility(8);
        this.imageCaptureGalleryLayout.setVisibility(0);
        this.galleryImagesAdapter.setImages(list);
    }

    public void editImage(List<PostAdImage> list, int i, String str) {
        new EbkImagePicker().forEditImage(i).selectedImages(PostAdImageConverter.fromPostAdToEbayK(list)).storageDirectoryName(str).start(this);
    }

    public void addImage(List<PostAdImage> list, int i, String str) {
        new EbkImagePicker().maxNumber(i).selectedImages(PostAdImageConverter.fromPostAdToEbayK(list)).storageDirectoryName(str).galleryEnabled(true).start(this);
    }

    public void openContactDetails(ContactDetailsData contactDetailsData) {
        startActivityForResult(PostAdContactDetailsActivity.createIntent(this, contactDetailsData), REQUEST_CONTACT_DETAILS);
    }

    public void promptPromotions(DraftAd draftAd) {
        startActivityForResult(PromotionActivity.createIntent(this, draftAd), REQUEST_PROMOTIONS);
    }

    public void resetViews() {
        this.adTitleView.reset();
        this.categoryView.reset();
        this.priceView.reset();
        this.priceFrequencyView.reset();
        this.descriptionView.reset();
        this.contactDetailView.reset();
        this.attributesDetailView.reset();
        this.galleryImagesAdapter = new PostAdSummaryAdapter(this, new GalleryImageEditListener());
        this.galleryImagesAdapter.notifyDataSetChanged();
        this.imageGalleryRow.setAdapter(this.galleryImagesAdapter);
        this.backgroundImage.setImageResource(2130837644);
        this.backgroundBitmap = null;
        displayLayoutWithNoImages();
    }

    public void showAdLoaded() {
        this.picturesContainer.setTranslationY(125.0f);
        this.picturesContainer.setAlpha(0.0f);
        this.picturesContainer.animate().translationY(0.0f).alpha(IPhotoView.DEFAULT_MIN_SCALE).setInterpolator(new OvershootInterpolator()).setDuration(325).setStartDelay(125);
        this.fieldsContainer.setTranslationY(125.0f);
        this.fieldsContainer.setAlpha(0.0f);
        this.fieldsContainer.animate().translationY(0.0f).alpha(IPhotoView.DEFAULT_MIN_SCALE).setInterpolator(new OvershootInterpolator(OVERSHOOT_TENSION)).setDuration(325).setStartDelay(200);
    }

    public void closeView() {
        finish();
    }

    public void closeViewWithOkResult() {
        setResult(-1);
        finish();
    }

    private void clearFocus() {
        android.view.View currentFocus = getCurrentFocus();
        if (currentFocus != null) {
            currentFocus.clearFocus();
        }
    }

    public void openAttributeDetails(CustomDetailsData customDetailsData, boolean z, String str) {
        startActivityForResult(CustomDetailsActivity.createIntent(this, customDetailsData, z, str), 23);
    }

    public void openPaymentDetails(OrderData orderData) {
        startActivity(PostAdPaymentActivity.createIntent(this, orderData));
    }

    public void openManageAds() {
        if (DevelopmentFlags.getInstance().isNewManageAdsEnabled()) {
            Intent createIntent = ManageAdsActivity.createIntent(this);
            createIntent.putExtra(ManageAdsActivity.REFRESH_ADS, true);
            startActivity(createIntent);
        } else {
            startActivity(ManageAdActivity.createIntent());
        }
        overridePendingTransition(0, 0);
    }

    public void openNewPostAd() {
        this.presenter.onResetMenuItemSelected();
    }

    public void openHelp() {
        startActivity(HelpActivity.createIntent(this));
        overridePendingTransition(0, 0);
    }

    public void showAttributeDetailsField(boolean z) {
        this.attributesDetailView.setVisibility(z ? 0 : 8);
    }

    public void showCostPaidAd(double d, @NonNull List<String> list) {
        this.priceInfoView.setVisibility(0);
        this.priceInfoView.setAmount(d);
        this.priceInfoView.setDescription(list);
        this.priceInfoView2.setVisibility(0);
        this.priceInfoView2.setAmount(d);
        this.priceInfoView2.setDescription(list);
        updateMainContentBottomMargin();
        setupFixedBottomButton();
    }

    public void hideCost() {
        this.priceInfoView.setVisibility(8);
        this.priceInfoView.setAmount(0.0d);
        this.priceInfoView.setDescription(null);
        this.priceInfoView2.setVisibility(8);
        this.priceInfoView2.setAmount(0.0d);
        this.priceInfoView2.setDescription(null);
        updateMainContentBottomMargin();
        setupFixedBottomButton();
    }

    private void updateMainContentBottomMargin() {
        updateMainContentBottomMargin(((LayoutParams) this.content.getLayoutParams()).bottomMargin == this.contentBottomMarginSmall);
    }

    public void showPostPaidAd() {
        updatePostLabel(this.postPaidAdLabel);
    }

    public void showPostFreeAd() {
        updatePostLabel(this.postFreeAdLabel);
    }

    public void showUpdateAd() {
        updatePostLabel(this.updateAdLabel);
        supportInvalidateOptionsMenu();
    }

    public void showRestoreAd() {
        updatePostLabel(this.restoreAdLabel);
        supportInvalidateOptionsMenu();
    }

    private void updatePostLabel(String str) {
        this.postAdButtonView.setText(str);
        this.postadScrollViewButton.setText(str);
        setupFixedBottomButton();
    }

    public void showErrorLoadingPriceInfo(String str) {
        if (str != null) {
            this.priceInfoView.setVisibility(8);
            this.priceInfoView2.setVisibility(8);
            setupFixedBottomButton();
            promptSnackbar(str);
        }
    }

    public void showTitle(String str) {
        this.adTitleView.showValue(str);
    }

    public void showTitleValid(boolean z) {
        this.adTitleView.showValid(z);
    }

    public void showTitleError(String str) {
        this.adTitleView.showError(str);
    }

    public void showCategory(String str, boolean z) {
        this.categoryView.showValue(str);
        this.categoryView.setEnabled(z);
    }

    public void showCategoryError(String str) {
        this.categoryView.showError(str);
    }

    public void showPrice(String str) {
        this.priceView.showValue(str);
    }

    public void showPriceValid(boolean z) {
        this.priceView.showValid(z);
    }

    public void showPriceFrequencyValid(boolean z) {
        this.priceFrequencyView.showValid(z);
    }

    public void showPriceField(boolean z) {
        this.priceView.setVisibility(z ? 0 : 8);
    }

    public void showPriceError(String str) {
        this.priceView.showError(str);
    }

    public void showPriceFrequency(String str, List<String> list) {
        this.priceFrequencyView.showValue(str, list);
    }

    public void showPriceFrequencyField(boolean z) {
        this.priceFrequencyView.setVisibility(z ? 0 : 8);
    }

    public void setSelectedPriceFrequency(String str) {
        this.priceFrequencyView.setSelectedFrequencyValue(str);
    }

    public void showPriceFrequencyError(String str) {
        this.priceFrequencyView.showError(str);
    }

    public void showDescription(String str) {
        this.descriptionView.showValue(str);
    }

    public void showDescriptionValid(boolean z) {
        this.descriptionView.showValid(z);
    }

    public void showDescriptionError(String str) {
        this.descriptionView.showError(str);
    }

    public void showContactDetails(String str) {
        this.contactDetailView.showValue(str);
    }

    public void showContactDetailsError(String str) {
        this.contactDetailView.showError(str);
    }

    public void showAttributeDetails(String str) {
        this.attributesDetailView.showValue(str);
    }

    public void showAttributeDetailsError(String str) {
        this.attributesDetailView.showError(str);
    }

    public void showAttributes(List<Attribute> list) {
        throw new UnsupportedOperationException("to be implemented");
    }

    public void showSending(boolean z) {
        this.loadingView.setVisibility(z ? 0 : 8);
    }

    public void showPostAdFeedbackConfirmation(String str) {
        startActivityForResult(PostAdConfirmationScreenActivity.createIntent(this, str), 24);
    }

    void editImage(int i) {
        this.presenter.onEditImage(i);
    }

    @OnClick({2131624168})
    void addFirstImage() {
        this.presenter.onAddImage();
    }

    @OnClick({2131624172})
    void addMoreImages() {
        this.presenter.onAddImage();
    }

    @OnClick({2131624182})
    void promotionButtonPressed() {
        this.presenter.onPromotion();
    }

    public void showImageUploadError() {
        new RetryPostDialog(this, new Listener() {
            public void onPostWithoutImages() {
                PostAdActivity.this.presenter.onSubmitWithoutImages();
            }

            public void onRetryPostWithImages() {
                PostAdActivity.this.presenter.retrySubmitWithImages();
            }
        }).showDialog();
    }

    public void showImageDownloadError() {
        promptSnackbar("Image download error");
    }

    public void enablePromotions(boolean z) {
        this.promoteContainer.setVisibility(z ? 0 : 8);
    }

    public void showFeatures(boolean z, boolean z2, boolean z3, boolean z4) {
        CharSequence charSequence;
        Iterable arrayList = new ArrayList();
        if (z) {
            arrayList.add(this.spotlightFeatureName);
        }
        if (z2) {
            arrayList.add(this.urgentFeatureName);
        }
        if (z3) {
            arrayList.add(this.featuredFeatureName);
        }
        if (z4) {
            arrayList.add(this.bumpedUpFeatureName);
        }
        if (arrayList.isEmpty()) {
            charSequence = this.promoteButtonDefaultText;
        } else {
            charSequence = String.format(this.promoteButtonTemplate, new Object[]{StringUtils.join(arrayList, ", ")});
        }
        this.promoteButtonTextView.setText(charSequence);
    }

    @OnClick({2131624176})
    void onChooseCategory() {
        this.presenter.onSelectCategory();
    }

    public void openCategoryDetails() {
        startActivityForResult(NewPostAdCategoryActivity.createIntent(this, this.adId, this.adTitleView.getValue()), 100);
    }

    public void showNoNetworkError() {
        promptSnackbar(getResources().getString(2131165438));
    }

    @OnClick({2131624181})
    void onContactDetailsClicked() {
        this.presenter.onContactDetailsClicked();
    }

    @OnClick({2131624179})
    void onAttributesDetailsClicked() {
        this.presenter.onAttributeDetailsClicked();
    }

    @OnClick({2131624190, 2131624187})
    void postAd(android.view.View view) {
        clearFocus();
        this.presenter.onSubmitWithImages();
    }

    private void initTextListeners() {
        this.adTitleView.setOnFieldValueChangeListener(PostAdActivity$$Lambda$3.lambdaFactory$(this));
        this.priceView.setOnFieldValueChangeListener(PostAdActivity$$Lambda$4.lambdaFactory$(this));
        this.priceFrequencyView.setOnFieldValueChangeListener(PostAdActivity$$Lambda$5.lambdaFactory$(this));
        this.priceFrequencyView.setOnSpinnerItemSelectedListener(PostAdActivity$$Lambda$6.lambdaFactory$(this));
        this.descriptionView.setOnFieldValueChangeListener(PostAdActivity$$Lambda$7.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$initTextListeners$2(String str) {
        this.presenter.onTitleChanged(str);
    }

    /* synthetic */ void lambda$initTextListeners$3(String str) {
        this.presenter.onPriceChanged(str);
    }

    /* synthetic */ void lambda$initTextListeners$4(String str) {
        this.presenter.onPriceFrequencyChanged(str);
    }

    /* synthetic */ void lambda$initTextListeners$5(String str) {
        this.presenter.onPriceFrequencySpinnerChanged(str);
    }

    /* synthetic */ void lambda$initTextListeners$6(String str) {
        this.presenter.onDescriptionChanged(str);
    }

    public void onScrollChanged(ScrollViewListenerScrollView scrollViewListenerScrollView, int i, int i2, int i3, int i4) {
        if (i2 == 0) {
            if (this.scrollablePriceInfoContainer.getVisibility() == 0) {
                this.scrollablePriceInfoContainer.setVisibility(8);
                updateMainContentBottomMargin(false);
            }
        } else if (this.scrollablePriceInfoContainer.getVisibility() == 8) {
            this.scrollablePriceInfoContainer.setVisibility(0);
            updateMainContentBottomMargin(true);
        }
    }

    private void updateMainContentBottomMargin(boolean z) {
        int i = z ? this.contentBottomMarginSmall : (this.priceInfoView.getVisibility() == 0 ? 1 : null) != null ? this.contentBottomMarginBigWithPrice : this.contentBottomMarginBig;
        LayoutParams layoutParams = (LayoutParams) this.content.getLayoutParams();
        layoutParams.setMargins(layoutParams.leftMargin, layoutParams.topMargin, layoutParams.rightMargin, i);
        this.content.setLayoutParams(layoutParams);
    }

    private void initPostAdButtons() {
        this.scrollview.setScrollViewListener(this);
        this.contentBottomMarginBig = getResources().getDimensionPixelSize(2131231148);
        this.contentBottomMarginBigWithPrice = getResources().getDimensionPixelSize(2131230801);
        this.contentBottomMarginSmall = getResources().getDimensionPixelSize(2131231149);
        setupFixedBottomButton();
    }

    private void initToolbar() {
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setToolbarBackground(getToolbarGradientBackground());
    }

    private void initPriceInputFilter() {
        InputFilter lambdaFactory$ = PostAdActivity$$Lambda$8.lambdaFactory$(Pattern.compile("[0-9]{1,10}((\\.)(([0-9]){0,2}))?"));
        this.priceView.setFilters(new InputFilter[]{lambdaFactory$});
        this.priceFrequencyView.setFilters(new InputFilter[]{lambdaFactory$});
    }

    static /* synthetic */ CharSequence lambda$initPriceInputFilter$7(Pattern pattern, CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
        String obj = spanned.toString();
        if (pattern.matcher(obj.substring(0, i3) + charSequence + obj.substring(i3, obj.length())).matches()) {
            return null;
        }
        return BuildConfig.FLAVOR;
    }

    private void initFocusListeners() {
        this.adTitleView.onFocusChanged(PostAdActivity$$Lambda$9.lambdaFactory$(this));
        this.priceView.onFocusChanged(PostAdActivity$$Lambda$10.lambdaFactory$(this));
        this.priceFrequencyView.onFocusChanged(PostAdActivity$$Lambda$11.lambdaFactory$(this));
        this.descriptionView.onFocusChanged(PostAdActivity$$Lambda$12.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$initFocusListeners$8(android.view.View view, boolean z) {
        if (!z) {
            this.presenter.onTitleFocusLost();
        }
    }

    /* synthetic */ void lambda$initFocusListeners$9(android.view.View view, boolean z) {
        if (!z) {
            this.presenter.onPriceFocusLost();
        }
    }

    /* synthetic */ void lambda$initFocusListeners$10(android.view.View view, boolean z) {
        if (!z) {
            this.presenter.onPriceFrequencyFocusLost();
        }
    }

    /* synthetic */ void lambda$initFocusListeners$11(android.view.View view, boolean z) {
        if (!z) {
            this.presenter.onDescriptionFocusLost();
        }
    }

    private void setToolbarBackground(Drawable drawable) {
        if (VERSION.SDK_INT >= 16) {
            this.toolbar.setBackground(drawable);
        } else {
            this.toolbar.setBackgroundDrawable(drawable);
        }
    }

    private Drawable getToolbarGradientBackground() {
        return ResourcesCompat.getDrawable(getResources(), 2130837979, null);
    }

    private void setupFixedBottomButton() {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.priceInfoContainer.getLayoutParams();
        layoutParams.setMargins(layoutParams.leftMargin, ((getScreenHeight() - getResources().getDimensionPixelSize(2131231157)) - (this.priceInfoView.getVisibility() == 0 ? getResources().getDimensionPixelSize(2131230802) : 0)) - getStatusBarHeight(), layoutParams.rightMargin, layoutParams.bottomMargin);
        this.priceInfoContainer.setLayoutParams(layoutParams);
    }

    private int getStatusBarHeight() {
        int identifier = getResources().getIdentifier("status_bar_height", "dimen", "android");
        return identifier > 0 ? getResources().getDimensionPixelSize(identifier) : 0;
    }

    private int getScreenHeight() {
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        return point.y;
    }

    private void getBlurredBitmap(String str) {
        if (VERSION.SDK_INT >= 17 && !TextUtils.isEmpty(str)) {
            BlurredImageTransformation.blur(RenderScript.create(this), str).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(PostAdActivity$$Lambda$13.lambdaFactory$(this), PostAdActivity$$Lambda$14.lambdaFactory$(this));
        }
    }

    /* synthetic */ void lambda$getBlurredBitmap$12(Bitmap bitmap) {
        setBackgroundBitmap(bitmap);
    }

    /* synthetic */ void lambda$getBlurredBitmap$13(Throwable th) {
        Log.e(getClass().getSimpleName(), th.getMessage());
    }

    private void setBackgroundBitmap(Bitmap bitmap) {
        this.backgroundBitmap = bitmap;
        if (this.backgroundImage != null) {
            this.backgroundImage.setImageBitmap(bitmap);
        }
    }
}
