package com.gumtree.android.vip;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
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
import com.gumtree.Log;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.category.NewPostAdCategoryActivity;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.common.activities.StatefulActivity;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.events.OnGalleryItemChangedEvent;
import com.gumtree.android.events.OnGalleryItemWatchEvent;
import com.gumtree.android.message_box.ConversationsIntentService;
import com.gumtree.android.model.Ads;
import com.gumtree.android.model.Pictures;
import com.squareup.otto.Subscribe;
import javax.inject.Inject;
import novoda.lib.sqliteprovider.cursor.EmptyCursor;
import uk.co.senab.photoview.IPhotoView;

public class VIPGalleryActivity extends BaseActivity implements LoaderCallbacks<Cursor> {
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
    private GalleryPagerAdapter pagerAdapter;
    private String title;
    private long vipId;

    class DetailOnPageChangeListener extends SimpleOnPageChangeListener {
        private DetailOnPageChangeListener() {
        }

        public void onPageSelected(int i) {
            VIPGalleryActivity.this.mCurrentPos = i;
            VIPGalleryActivity.this.pageIndicator.setText(VIPGalleryActivity.this.getString(2131165471, new Object[]{Integer.valueOf(VIPGalleryActivity.this.getNonEdgeImagePosition(i)), Integer.valueOf(VIPGalleryActivity.this.mImgsNum)}));
            VIPGalleryActivity.this.pageIndicator.setVisibility(0);
        }

        public void onPageScrollStateChanged(int i) {
            int access$400 = VIPGalleryActivity.this.getNonEdgeImagePosition(VIPGalleryActivity.this.mCurrentPos);
            if (access$400 != VIPGalleryActivity.this.mCurrentPos) {
                VIPGalleryActivity.this.pager.setCurrentItem(access$400, false);
            }
            VIPGalleryActivity.this.mEventBus.post(new OnGalleryItemWatchEvent(access$400 - 1, false));
        }
    }

    public static Intent createIntent(String str, String str2, String str3) {
        Intent intent = new Intent(StatefulActivity.ACTION_VIEW_GALLERY, Ads.URI.buildUpon().appendPath(str).build());
        intent.putExtra(NewPostAdCategoryActivity.EXTRA_TITLE, str2);
        intent.putExtra(StatefulActivity.EXTRA_CURRENT_GALLERY_PAGER_POS, str3);
        intent.setPackage(GumtreeApplication.getPackageNameForIntent());
        return intent;
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initVariables(bundle);
        GumtreeApplication.component().inject(this);
        setContentView(2130903117);
        initPager();
        this.pageIndicator = (TextView) findViewById(R.id.indicator);
        getSupportLoaderManager().initLoader(0, null, this);
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
                VIPGalleryActivity.this.pager.getViewTreeObserver().removeOnPreDrawListener(this);
                VIPGalleryActivity.this.updateImageTransitionParams(rect);
                VIPGalleryActivity.this.runEnterAnimation();
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
        this.vipId = bundle.getLong("key_id");
        this.title = bundle.getString("key_title");
        this.mCurrentPos = bundle.getInt("key_current_pos");
    }

    private void initializeWithDefaultValues() {
        this.vipId = Long.valueOf(getIntent().getData().getLastPathSegment()).longValue();
        this.title = getIntent().getStringExtra(NewPostAdCategoryActivity.EXTRA_TITLE);
        Object stringExtra = getIntent().getStringExtra(StatefulActivity.EXTRA_CURRENT_GALLERY_PAGER_POS);
        this.mCurrentPos = TextUtils.isEmpty(stringExtra) ? 0 : Integer.parseInt(stringExtra);
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putLong("key_id", this.vipId);
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
        this.pagerAdapter = new GalleryPagerAdapter(this, getLayoutInflater(), new EmptyCursor(), this.mEventBus);
        this.pager.setAdapter(this.pagerAdapter);
        this.pager.setOnPageChangeListener(new DetailOnPageChangeListener());
    }

    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        switch (i) {
            case ConversationsIntentService.DEFAULT_PAGE /*0*/:
                return new CursorLoader(getApplicationContext(), Pictures.URI, new String[]{"href", "_id"}, "ad_id=?", new String[]{String.valueOf(this.vipId)}, null);
            default:
                return null;
        }
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        switch (loader.getId()) {
            case ConversationsIntentService.DEFAULT_PAGE /*0*/:
                if (cursor.getCount() == 0) {
                    findViewById(16908292).setVisibility(0);
                    this.pageIndicator.setVisibility(8);
                    return;
                }
                findViewById(16908292).setVisibility(8);
                this.pagerAdapter.changeCursor(cursor);
                this.pager.setCurrentItem(this.mCurrentPos + 1, false);
                this.mImgsNum = cursor.getCount();
                if (this.mImgsNum == 1) {
                    this.pageIndicator.setText(getString(2131165471, new Object[]{Integer.valueOf(1), Integer.valueOf(1)}));
                } else {
                    this.pageIndicator.setText(getString(2131165471, new Object[]{Integer.valueOf(this.mCurrentPos), Integer.valueOf(this.mImgsNum)}));
                }
                this.pageIndicator.setVisibility(0);
                this.mEventBus.post(new OnGalleryItemWatchEvent(this.mCurrentPos - 1, true));
                return;
            default:
                Log.e(getClass().getSimpleName(), "case not supported " + loader.getId());
                return;
        }
    }

    public void onLoaderReset(Loader<Cursor> loader) {
    }

    private int getNonEdgeImagePosition(int i) {
        int count = this.pagerAdapter.getCount();
        if (i == 0) {
            return count - 2;
        }
        if (i == count - 1) {
            return 1;
        }
        return i;
    }

    protected void onResume() {
        super.onResume();
        this.mEventBus.register(this);
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
                    VIPGalleryActivity.this.finish();
                    VIPGalleryActivity.this.overridePendingTransition(0, 0);
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
        runExitAnimation(VIPGalleryActivity$$Lambda$1.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$onBackPressed$0() {
        finish();
        overridePendingTransition(0, 0);
    }
}
