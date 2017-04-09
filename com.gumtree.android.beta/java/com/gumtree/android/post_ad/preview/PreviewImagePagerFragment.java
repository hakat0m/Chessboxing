package com.gumtree.android.post_ad.preview;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.apptentive.android.sdk.BuildConfig;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.common.fragments.BaseFragment;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.events.OnGalleryItemChangedEvent;
import com.gumtree.android.events.OnGalleryItemWatchEvent;
import com.gumtree.android.message_box.ConversationsIntentService;
import com.gumtree.android.model.PostAdsImages;
import com.gumtree.android.vip.PicturesPagerAdapter;
import com.squareup.otto.Subscribe;
import javax.inject.Inject;

public class PreviewImagePagerFragment extends BaseFragment implements LoaderCallbacks<Cursor> {
    private static final String AD_ID = "ad_id";
    private static final int TOLERANCE = 50;
    private TextView counter;
    @Inject
    EventBus mEventBus;
    private ViewPager pager;
    private View pagerContainer;
    private float pointX;
    private float pointY;
    private final OnTouchListener touchListener = PreviewImagePagerFragment$$Lambda$1.lambdaFactory$(this);
    protected long vipId;

    /* synthetic */ boolean lambda$new$0(View view, MotionEvent motionEvent) {
        boolean z = true;
        switch (motionEvent.getAction()) {
            case ConversationsIntentService.DEFAULT_PAGE /*0*/:
                this.pointX = motionEvent.getX();
                this.pointY = motionEvent.getY();
                break;
            case HighlightView.GROW_NONE /*1*/:
                boolean z2 = this.pointX + 50.0f > motionEvent.getX() && this.pointX - 50.0f < motionEvent.getX();
                if (this.pointY + 50.0f <= motionEvent.getY() || this.pointY - 50.0f >= motionEvent.getY()) {
                    z = false;
                }
                if (z2 && r1) {
                    try {
                        openGallery();
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                        break;
                    }
                }
                break;
        }
        return false;
    }

    public static PreviewImagePagerFragment newInstance(String str) {
        PreviewImagePagerFragment previewImagePagerFragment = new PreviewImagePagerFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(AD_ID, Long.parseLong(str));
        previewImagePagerFragment.setArguments(bundle);
        return previewImagePagerFragment;
    }

    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this.context, PostAdsImages.URI, new String[]{"href", "_id"}, "ad_id=? AND href<>?", new String[]{String.valueOf(this.vipId), BuildConfig.FLAVOR}, "gallery_index");
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        GumtreeApplication.component().inject(this);
        this.vipId = getArguments().getLong(AD_ID);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(2130903248, viewGroup, false);
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.pagerContainer = view.findViewById(2131624633);
        this.pager = (ViewPager) view.findViewById(2131624634);
        this.pager.setOnTouchListener(this.touchListener);
        this.counter = (TextView) view.findViewById(2131624635);
        getLoaderManager().restartLoader(5, null, this);
    }

    public void onStart() {
        super.onStart();
        this.mEventBus.register(this);
    }

    public void onStop() {
        this.mEventBus.unregister(this);
        super.onStop();
    }

    @Subscribe
    public void onGalleryItemWatchEvent(OnGalleryItemWatchEvent onGalleryItemWatchEvent) {
        if (this.pager != null) {
            this.pager.setCurrentItem(onGalleryItemWatchEvent.getPosition(), false);
            if (!onGalleryItemWatchEvent.isJustOpened()) {
                this.mEventBus.post(new OnGalleryItemChangedEvent(getCurrentPosImageRect()));
            }
        }
    }

    private void openGallery() {
        if (!TextUtils.isEmpty(this.vipId + BuildConfig.FLAVOR)) {
            Intent createIntent = PreviewGalleryActivity.createIntent(getActivity(), this.vipId + BuildConfig.FLAVOR, BuildConfig.FLAVOR, this.pager.getCurrentItem() + BuildConfig.FLAVOR);
            String packageName = GumtreeApplication.getContext().getPackageName();
            createIntent.putExtra(packageName + ".imageBounds", getCurrentPosImageRect());
            createIntent.putExtra(packageName + ".orientation", getResources().getConfiguration().orientation);
            startActivity(createIntent);
            getActivity().overridePendingTransition(0, 0);
        }
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor.getCount() > 0) {
            try {
                PicturesPagerAdapter picturesPagerAdapter = new PicturesPagerAdapter(getChildFragmentManager(), this.context, cursor);
                this.counter.setText(BuildConfig.FLAVOR + picturesPagerAdapter.getCount());
                this.pager.setAdapter(picturesPagerAdapter);
                if (picturesPagerAdapter.areImagesToSmall()) {
                    this.pager.setOnTouchListener(new 1(this));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            getEmptyView().setVisibility(8);
            this.pager.setVisibility(0);
            return;
        }
        ((View) this.pagerContainer.getParent()).setVisibility(8);
    }

    public void onLoaderReset(Loader<Cursor> loader) {
    }

    private View getEmptyView() {
        return getView().findViewById(16908292);
    }

    private Rect getCurrentPosImageRect() {
        PicturesPagerAdapter picturesPagerAdapter = (PicturesPagerAdapter) this.pager.getAdapter();
        if (picturesPagerAdapter == null) {
            return null;
        }
        int[] iArr = new int[2];
        this.pager.getLocationOnScreen(iArr);
        int i = iArr[0];
        int i2 = iArr[1];
        int imageLeftX = picturesPagerAdapter.getImageLeftX(this.pager.getCurrentItem());
        int imageTopY = picturesPagerAdapter.getImageTopY(this.pager.getCurrentItem());
        i2 += imageTopY;
        i += imageLeftX;
        return new Rect(i, i2, i + picturesPagerAdapter.getImageWidth(this.pager.getCurrentItem()), i2 + picturesPagerAdapter.getImageHeight(this.pager.getCurrentItem()));
    }
}
