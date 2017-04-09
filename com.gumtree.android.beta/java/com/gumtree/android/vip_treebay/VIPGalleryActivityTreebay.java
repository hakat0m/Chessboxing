package com.gumtree.android.vip_treebay;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;
import com.apptentive.android.sdk.R;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.category.NewPostAdCategoryActivity;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.common.activities.StatefulActivity;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.events.OnGalleryItemChangedEvent;
import com.gumtree.android.events.OnGalleryItemWatchEvent;
import com.gumtree.android.vip.TreebayGalleryPagerAdapter;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import javax.inject.Inject;
import uk.co.senab.photoview.IPhotoView;

public class VIPGalleryActivityTreebay extends BaseActivity {
    private static final String ALPHA = "alpha";
    private static final int END_ANIMATION = 255;
    private static final int IMG_BACKGROUND_TRANSITION_DURATION = 200;
    private static final int IMG_ENTER_ANIM_DURATION = 300;
    public static final int IMG_EXIT_ANIM_DURATION = 200;
    private static final int LOADER_PICTURES = 0;
    private AccelerateInterpolator mAccelerateInterpolator = new AccelerateInterpolator();
    private ColorDrawable mBackground;
    private int mCurrentPos;
    private DecelerateInterpolator mDecelerateInterpolator = new DecelerateInterpolator();
    @Inject
    EventBus mEventBus;
    private int mImgsNum;
    private int mPivotX;
    private int mPivotY;
    private float mScaleValue;
    int mTopDelta;
    private TextView pageIndicator;
    private ViewPager pager;
    private TreebayGalleryPagerAdapter pagerAdapter;
    private String title;

    class DetailOnPageChangeListener extends SimpleOnPageChangeListener {
        private DetailOnPageChangeListener() {
        }

        public void onPageSelected(int i) {
            VIPGalleryActivityTreebay.this.mCurrentPos = i;
            VIPGalleryActivityTreebay.this.pageIndicator.setText(VIPGalleryActivityTreebay.this.getString(2131165471, new Object[]{Integer.valueOf(i + 1), Integer.valueOf(VIPGalleryActivityTreebay.this.mImgsNum)}));
            VIPGalleryActivityTreebay.this.pageIndicator.setVisibility(0);
        }

        public void onPageScrollStateChanged(int i) {
            int access$300 = VIPGalleryActivityTreebay.this.mCurrentPos;
            if (access$300 != VIPGalleryActivityTreebay.this.mCurrentPos) {
                VIPGalleryActivityTreebay.this.pager.setCurrentItem(access$300, false);
            }
            VIPGalleryActivityTreebay.this.mEventBus.post(new OnGalleryItemWatchEvent(access$300 - 1, false));
        }
    }

    public static Intent createIntent(Context context, String str, String str2, ArrayList<String> arrayList) {
        Intent intent = new Intent(context, VIPGalleryActivityTreebay.class);
        intent.putExtra(NewPostAdCategoryActivity.EXTRA_TITLE, str);
        intent.putExtra(StatefulActivity.EXTRA_CURRENT_GALLERY_PAGER_POS, str2);
        intent.putStringArrayListExtra(StatefulActivity.EXTRA_IMAGES_URL, arrayList);
        intent.setPackage(GumtreeApplication.getPackageNameForIntent());
        return intent;
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initVariables(bundle);
        GumtreeApplication.component().inject(this);
        setContentView(2130903117);
        this.pageIndicator = (TextView) findViewById(R.id.indicator);
        initPager();
        this.mBackground = new ColorDrawable(-16777216);
        if (VERSION.SDK_INT < 16) {
            findViewById(2131624263).setBackgroundDrawable(this.mBackground);
        } else {
            ((ViewGroup) findViewById(2131624263)).setBackground(this.mBackground);
        }
        if (bundle == null) {
            initEnterAnimation();
        }
    }

    private void initEnterAnimation() {
        final Rect rect = (Rect) getIntent().getExtras().getParcelable(GumtreeApplication.getContext().getPackageName() + ".imageBounds");
        this.pager.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
            public boolean onPreDraw() {
                VIPGalleryActivityTreebay.this.pager.getViewTreeObserver().removeOnPreDrawListener(this);
                VIPGalleryActivityTreebay.this.updateImageTransitionParams(rect);
                VIPGalleryActivityTreebay.this.runEnterAnimation();
                return true;
            }
        });
    }

    private void initVariables(Bundle bundle) {
        if (bundle == null) {
            initializeWithDefaultValues();
        } else {
            restoreVariableState(bundle);
        }
    }

    private void restoreVariableState(Bundle bundle) {
        this.title = bundle.getString("key_title");
        this.mCurrentPos = bundle.getInt("key_current_pos");
    }

    private void initializeWithDefaultValues() {
        this.title = getIntent().getStringExtra(NewPostAdCategoryActivity.EXTRA_TITLE);
        Object stringExtra = getIntent().getStringExtra(StatefulActivity.EXTRA_CURRENT_GALLERY_PAGER_POS);
        this.mCurrentPos = TextUtils.isEmpty(stringExtra) ? 0 : Integer.parseInt(stringExtra);
    }

    private void loadPictures(ArrayList<String> arrayList) {
        if (arrayList.size() == 0) {
            findViewById(16908292).setVisibility(0);
            this.pageIndicator.setVisibility(8);
            return;
        }
        findViewById(16908292).setVisibility(8);
        this.pagerAdapter.changeCursor(arrayList);
        this.pager.setCurrentItem(this.mCurrentPos, false);
        this.mImgsNum = arrayList.size();
        if (this.mImgsNum == 1) {
            this.pageIndicator.setText(getString(2131165471, new Object[]{Integer.valueOf(1), Integer.valueOf(1)}));
        } else {
            this.pageIndicator.setText(getString(2131165471, new Object[]{Integer.valueOf(this.mCurrentPos + 1), Integer.valueOf(this.mImgsNum)}));
        }
        this.pageIndicator.setVisibility(0);
        this.mEventBus.post(new OnGalleryItemWatchEvent(this.mCurrentPos, true));
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString("key_title", this.title);
        bundle.putInt("key_current_pos", this.mCurrentPos);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private void initPager() {
        findViewById(16908292).setVisibility(0);
        this.pager = (ViewPager) findViewById(2131624264);
        this.pagerAdapter = new TreebayGalleryPagerAdapter(this, getLayoutInflater(), new ArrayList(), this.mEventBus);
        this.pager.setAdapter(this.pagerAdapter);
        this.pager.setOnPageChangeListener(new DetailOnPageChangeListener());
    }

    protected void onResume() {
        super.onResume();
        this.mEventBus.register(this);
        ArrayList stringArrayListExtra = getIntent().getStringArrayListExtra(StatefulActivity.EXTRA_IMAGES_URL);
        if (stringArrayListExtra != null) {
            loadPictures(stringArrayListExtra);
        }
    }

    protected void onPause() {
        super.onPause();
        this.mEventBus.unregister(this);
    }

    @Subscribe
    public void onGalleryItemChangedEvent(OnGalleryItemChangedEvent onGalleryItemChangedEvent) {
        Rect rect = onGalleryItemChangedEvent.getRect();
        if (rect != null) {
            updateImageTransitionParams(rect);
            this.pager.setPivotX((float) this.mPivotX);
            this.pager.setPivotY((float) this.mPivotY);
        }
    }

    private void updateImageTransitionParams(Rect rect) {
        int i = rect.left;
        int i2 = rect.top;
        int i3 = rect.right - i;
        int i4 = rect.bottom - i2;
        float width = ((float) i3) / ((float) this.pager.getWidth());
        float height = ((float) i4) / ((float) this.pager.getHeight());
        if (width <= height) {
            width = height;
        }
        this.mScaleValue = width;
        int[] iArr = new int[2];
        this.pager.getLocationOnScreen(iArr);
        int i5 = iArr[1];
        int measuredHeight = this.pager.getMeasuredHeight();
        int measuredWidth = this.pager.getMeasuredWidth();
        float f = ((float) i3) / this.mScaleValue;
        height = (((float) measuredHeight) - (((float) i4) / this.mScaleValue)) / 2.0f;
        this.mPivotY = ((int) ((((float) i2) - ((((float) i5) + height) * this.mScaleValue)) / (IPhotoView.DEFAULT_MIN_SCALE - this.mScaleValue))) - i5;
        this.mPivotX = (int) ((((float) i) - (((((float) measuredWidth) - f) / 2.0f) * this.mScaleValue)) / (IPhotoView.DEFAULT_MIN_SCALE - this.mScaleValue));
        this.mTopDelta = -((i5 + ((int) height)) - i2);
    }

    public void runEnterAnimation() {
        this.pager.setPivotX((float) this.mPivotX);
        this.pager.setPivotY((float) this.mPivotY);
        this.pager.setScaleX(this.mScaleValue);
        this.pager.setScaleY(this.mScaleValue);
        if (this.mScaleValue == IPhotoView.DEFAULT_MIN_SCALE) {
            this.pager.setTranslationY((float) this.mTopDelta);
        }
        this.pager.animate().setDuration(300).scaleX(IPhotoView.DEFAULT_MIN_SCALE).scaleY(IPhotoView.DEFAULT_MIN_SCALE).translationX(0.0f).translationY(0.0f).setInterpolator(this.mDecelerateInterpolator);
        ObjectAnimator ofInt = ObjectAnimator.ofInt(this.mBackground, ALPHA, new int[]{0, END_ANIMATION});
        ofInt.setDuration(200);
        ofInt.start();
        this.pageIndicator.setAlpha(0.0f);
        this.pageIndicator.animate().alpha(IPhotoView.DEFAULT_MIN_SCALE).setStartDelay(200).setDuration(200).setInterpolator(this.mAccelerateInterpolator);
    }

    public void runExitAnimation(Runnable runnable) {
        ViewPropertyAnimator translationY;
        this.pagerAdapter.zoomOut(this.mCurrentPos);
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.pageIndicator, ALPHA, new float[]{0.0f});
        ofFloat.setDuration(100);
        ofFloat.setInterpolator(this.mAccelerateInterpolator);
        ofFloat.start();
        if (this.mScaleValue == IPhotoView.DEFAULT_MIN_SCALE) {
            translationY = this.pager.animate().setDuration(200).scaleX(this.mScaleValue).scaleY(this.mScaleValue).translationY((float) this.mTopDelta);
        } else {
            translationY = this.pager.animate().setDuration(200).scaleX(this.mScaleValue).scaleY(this.mScaleValue);
        }
        if (VERSION.SDK_INT < 16) {
            translationY.setListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animator) {
                    VIPGalleryActivityTreebay.this.finish();
                    VIPGalleryActivityTreebay.this.overridePendingTransition(0, 0);
                }
            });
        } else {
            translationY.withEndAction(runnable);
        }
        ofFloat = ObjectAnimator.ofInt(this.mBackground, ALPHA, new int[]{0});
        ofFloat.setDuration(200);
        ofFloat.setInterpolator(this.mAccelerateInterpolator);
        ofFloat.start();
    }

    public void onBackPressed() {
        setResult(-1, new Intent());
        runExitAnimation(VIPGalleryActivityTreebay$$Lambda$1.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$onBackPressed$0() {
        finish();
        overridePendingTransition(0, 0);
    }
}
