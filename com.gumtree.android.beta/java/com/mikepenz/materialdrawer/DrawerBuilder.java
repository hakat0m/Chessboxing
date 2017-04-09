package com.mikepenz.materialdrawer;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ItemAnimator;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IItemAdapter;
import com.mikepenz.fastadapter.adapters.FooterAdapter;
import com.mikepenz.fastadapter.adapters.HeaderAdapter;
import com.mikepenz.fastadapter.adapters.ItemAdapter;
import com.mikepenz.iconics.utils.Utils;
import com.mikepenz.materialdrawer.Drawer.OnDrawerItemClickListener;
import com.mikepenz.materialdrawer.Drawer.OnDrawerItemLongClickListener;
import com.mikepenz.materialdrawer.Drawer.OnDrawerListener;
import com.mikepenz.materialdrawer.Drawer.OnDrawerNavigationListener;
import com.mikepenz.materialdrawer.holder.DimenHolder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.util.DrawerUIUtils;
import com.mikepenz.materialize.Materialize;
import com.mikepenz.materialize.MaterializeBuilder;
import com.mikepenz.materialize.util.UIUtils;
import com.mikepenz.materialize.view.ScrimInsetsRelativeLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import uk.co.senab.photoview.IPhotoView;

public class DrawerBuilder {
    protected AccountHeader mAccountHeader;
    protected boolean mAccountHeaderSticky = false;
    protected ActionBarDrawerToggle mActionBarDrawerToggle;
    protected boolean mActionBarDrawerToggleEnabled = true;
    protected Activity mActivity;
    protected FastAdapter<IDrawerItem> mAdapter;
    protected Adapter mAdapterWrapper;
    protected boolean mAnimateActionBarDrawerToggle = false;
    protected boolean mAppended = false;
    protected boolean mCloseOnClick = true;
    protected int mCurrentStickyFooterSelection = -1;
    protected View mCustomView;
    protected int mDelayDrawerClickEvent = 0;
    protected int mDelayOnDrawerClose = 50;
    protected Boolean mDisplayBelowStatusBar;
    protected Integer mDrawerGravity = Integer.valueOf(8388611);
    protected DrawerLayout mDrawerLayout;
    protected int mDrawerWidth = -1;
    protected boolean mFireInitialOnClick = false;
    protected FooterAdapter<IDrawerItem> mFooterAdapter = new FooterAdapter();
    protected boolean mFooterClickable = false;
    protected boolean mFooterDivider = true;
    protected View mFooterView;
    protected boolean mFullscreen = false;
    protected boolean mGenerateMiniDrawer = false;
    protected boolean mHasStableIds = false;
    protected HeaderAdapter<IDrawerItem> mHeaderAdapter = new HeaderAdapter();
    protected boolean mHeaderDivider = true;
    protected boolean mHeaderPadding = true;
    protected View mHeaderView;
    protected DimenHolder mHeiderHeight = null;
    private boolean mInnerShadow = false;
    protected ItemAdapter<IDrawerItem> mItemAdapter = new ItemAdapter();
    protected ItemAnimator mItemAnimator = new DefaultItemAnimator();
    protected LayoutManager mLayoutManager;
    protected Materialize mMaterialize;
    protected MiniDrawer mMiniDrawer = null;
    protected boolean mMultiSelect = false;
    protected OnDrawerItemClickListener mOnDrawerItemClickListener;
    protected OnDrawerItemLongClickListener mOnDrawerItemLongClickListener;
    protected OnDrawerListener mOnDrawerListener;
    protected OnDrawerNavigationListener mOnDrawerNavigationListener;
    protected boolean mPositionBasedStateManagement = true;
    protected RecyclerView mRecyclerView;
    protected ViewGroup mRootView;
    protected Bundle mSavedInstance;
    protected boolean mScrollToTopAfterClick = false;
    protected long mSelectedItemIdentifier = 0;
    protected int mSelectedItemPosition = 0;
    protected boolean mShowDrawerOnFirstLaunch = false;
    protected int mSliderBackgroundColor = 0;
    protected int mSliderBackgroundColorRes = -1;
    protected Drawable mSliderBackgroundDrawable = null;
    protected int mSliderBackgroundDrawableRes = -1;
    protected ScrimInsetsRelativeLayout mSliderLayout;
    protected List<IDrawerItem> mStickyDrawerItems = new ArrayList();
    protected boolean mStickyFooterDivider = false;
    protected boolean mStickyFooterShadow = true;
    protected View mStickyFooterShadowView;
    protected ViewGroup mStickyFooterView;
    protected boolean mStickyHeaderShadow = true;
    protected View mStickyHeaderView;
    protected boolean mSystemUIHidden = false;
    protected Toolbar mToolbar;
    protected boolean mTranslucentNavigationBar = false;
    protected boolean mTranslucentNavigationBarProgrammatically = false;
    protected boolean mTranslucentStatusBar = true;
    protected boolean mUsed = false;

    public DrawerBuilder() {
        getAdapter();
    }

    public DrawerBuilder(@NonNull Activity activity) {
        this.mRootView = (ViewGroup) activity.findViewById(16908290);
        this.mActivity = activity;
        this.mLayoutManager = new LinearLayoutManager(this.mActivity);
        getAdapter();
    }

    public DrawerBuilder withActivity(@NonNull Activity activity) {
        this.mRootView = (ViewGroup) activity.findViewById(16908290);
        this.mActivity = activity;
        this.mLayoutManager = new LinearLayoutManager(this.mActivity);
        return this;
    }

    public DrawerBuilder withRootView(@NonNull ViewGroup viewGroup) {
        this.mRootView = viewGroup;
        withTranslucentStatusBar(false);
        return this;
    }

    public DrawerBuilder withRootView(@IdRes int i) {
        if (this.mActivity != null) {
            return withRootView((ViewGroup) this.mActivity.findViewById(i));
        }
        throw new RuntimeException("please pass an activity first to use this call");
    }

    public DrawerBuilder withTranslucentStatusBar(boolean z) {
        this.mTranslucentStatusBar = z;
        return this;
    }

    public DrawerBuilder withDisplayBelowStatusBar(boolean z) {
        this.mDisplayBelowStatusBar = Boolean.valueOf(z);
        return this;
    }

    public DrawerBuilder withInnerShadow(boolean z) {
        this.mInnerShadow = z;
        return this;
    }

    public DrawerBuilder withToolbar(@NonNull Toolbar toolbar) {
        this.mToolbar = toolbar;
        return this;
    }

    public DrawerBuilder withTranslucentNavigationBar(boolean z) {
        this.mTranslucentNavigationBar = z;
        if (!z) {
            this.mTranslucentNavigationBarProgrammatically = false;
        }
        return this;
    }

    public DrawerBuilder withTranslucentNavigationBarProgrammatically(boolean z) {
        this.mTranslucentNavigationBarProgrammatically = z;
        if (z) {
            this.mTranslucentNavigationBar = true;
        }
        return this;
    }

    public DrawerBuilder withFullscreen(boolean z) {
        this.mFullscreen = z;
        if (z) {
            withTranslucentStatusBar(true);
            withTranslucentNavigationBar(false);
        }
        return this;
    }

    public DrawerBuilder withSystemUIHidden(boolean z) {
        this.mSystemUIHidden = z;
        if (z) {
            withFullscreen(z);
        }
        return this;
    }

    public DrawerBuilder withCustomView(@NonNull View view) {
        this.mCustomView = view;
        return this;
    }

    public DrawerBuilder withDrawerLayout(@NonNull DrawerLayout drawerLayout) {
        this.mDrawerLayout = drawerLayout;
        return this;
    }

    public DrawerBuilder withDrawerLayout(@LayoutRes int i) {
        if (this.mActivity == null) {
            throw new RuntimeException("please pass an activity first to use this call");
        }
        if (i != -1) {
            this.mDrawerLayout = (DrawerLayout) this.mActivity.getLayoutInflater().inflate(i, this.mRootView, false);
        } else if (VERSION.SDK_INT < 21) {
            this.mDrawerLayout = (DrawerLayout) this.mActivity.getLayoutInflater().inflate(R.layout.material_drawer_fits_not, this.mRootView, false);
        } else {
            this.mDrawerLayout = (DrawerLayout) this.mActivity.getLayoutInflater().inflate(R.layout.material_drawer, this.mRootView, false);
        }
        return this;
    }

    public DrawerBuilder withSliderBackgroundColor(@ColorInt int i) {
        this.mSliderBackgroundColor = i;
        return this;
    }

    public DrawerBuilder withSliderBackgroundColorRes(@ColorRes int i) {
        this.mSliderBackgroundColorRes = i;
        return this;
    }

    public DrawerBuilder withSliderBackgroundDrawable(@NonNull Drawable drawable) {
        this.mSliderBackgroundDrawable = drawable;
        return this;
    }

    public DrawerBuilder withSliderBackgroundDrawableRes(@DrawableRes int i) {
        this.mSliderBackgroundDrawableRes = i;
        return this;
    }

    public DrawerBuilder withDrawerWidthPx(int i) {
        this.mDrawerWidth = i;
        return this;
    }

    public DrawerBuilder withDrawerWidthDp(int i) {
        if (this.mActivity == null) {
            throw new RuntimeException("please pass an activity first to use this call");
        }
        this.mDrawerWidth = Utils.convertDpToPx(this.mActivity, (float) i);
        return this;
    }

    public DrawerBuilder withDrawerWidthRes(@DimenRes int i) {
        if (this.mActivity == null) {
            throw new RuntimeException("please pass an activity first to use this call");
        }
        this.mDrawerWidth = this.mActivity.getResources().getDimensionPixelSize(i);
        return this;
    }

    public DrawerBuilder withDrawerGravity(int i) {
        this.mDrawerGravity = Integer.valueOf(i);
        return this;
    }

    public DrawerBuilder withAccountHeader(@NonNull AccountHeader accountHeader) {
        return withAccountHeader(accountHeader, false);
    }

    public DrawerBuilder withAccountHeader(@NonNull AccountHeader accountHeader, boolean z) {
        this.mAccountHeader = accountHeader;
        this.mAccountHeaderSticky = z;
        return this;
    }

    public DrawerBuilder withActionBarDrawerToggleAnimated(boolean z) {
        this.mAnimateActionBarDrawerToggle = z;
        return this;
    }

    public DrawerBuilder withActionBarDrawerToggle(boolean z) {
        this.mActionBarDrawerToggleEnabled = z;
        return this;
    }

    public DrawerBuilder withActionBarDrawerToggle(@NonNull ActionBarDrawerToggle actionBarDrawerToggle) {
        this.mActionBarDrawerToggleEnabled = true;
        this.mActionBarDrawerToggle = actionBarDrawerToggle;
        return this;
    }

    public DrawerBuilder withScrollToTopAfterClick(boolean z) {
        this.mScrollToTopAfterClick = z;
        return this;
    }

    public DrawerBuilder withHeader(@NonNull View view) {
        this.mHeaderView = view;
        return this;
    }

    public DrawerBuilder withHeader(@LayoutRes int i) {
        if (this.mActivity == null) {
            throw new RuntimeException("please pass an activity first to use this call");
        }
        if (i != -1) {
            this.mHeaderView = this.mActivity.getLayoutInflater().inflate(i, null, false);
        }
        return this;
    }

    public DrawerBuilder withHeaderDivider(boolean z) {
        this.mHeaderDivider = z;
        return this;
    }

    public DrawerBuilder withHeaderPadding(boolean z) {
        this.mHeaderPadding = z;
        return this;
    }

    public DrawerBuilder withHeaderHeight(DimenHolder dimenHolder) {
        this.mHeiderHeight = dimenHolder;
        return this;
    }

    public DrawerBuilder withStickyHeader(@NonNull View view) {
        this.mStickyHeaderView = view;
        return this;
    }

    public DrawerBuilder withStickyHeader(@LayoutRes int i) {
        if (this.mActivity == null) {
            throw new RuntimeException("please pass an activity first to use this call");
        }
        if (i != -1) {
            this.mStickyHeaderView = this.mActivity.getLayoutInflater().inflate(i, null, false);
        }
        return this;
    }

    public DrawerBuilder withStickyHeaderShadow(boolean z) {
        this.mStickyHeaderShadow = z;
        return this;
    }

    public DrawerBuilder withFooter(@NonNull View view) {
        this.mFooterView = view;
        return this;
    }

    public DrawerBuilder withFooter(@LayoutRes int i) {
        if (this.mActivity == null) {
            throw new RuntimeException("please pass an activity first to use this call");
        }
        if (i != -1) {
            this.mFooterView = this.mActivity.getLayoutInflater().inflate(i, null, false);
        }
        return this;
    }

    public DrawerBuilder withFooterClickable(boolean z) {
        this.mFooterClickable = z;
        return this;
    }

    public DrawerBuilder withFooterDivider(boolean z) {
        this.mFooterDivider = z;
        return this;
    }

    public DrawerBuilder withStickyFooter(@NonNull ViewGroup viewGroup) {
        this.mStickyFooterView = viewGroup;
        return this;
    }

    public DrawerBuilder withStickyFooter(@LayoutRes int i) {
        if (this.mActivity == null) {
            throw new RuntimeException("please pass an activity first to use this call");
        }
        if (i != -1) {
            this.mStickyFooterView = (ViewGroup) this.mActivity.getLayoutInflater().inflate(i, null, false);
        }
        return this;
    }

    public DrawerBuilder withStickyFooterDivider(boolean z) {
        this.mStickyFooterDivider = z;
        return this;
    }

    public DrawerBuilder withStickyFooterShadow(boolean z) {
        this.mStickyFooterShadow = z;
        return this;
    }

    public DrawerBuilder withFireOnInitialOnClick(boolean z) {
        this.mFireInitialOnClick = z;
        return this;
    }

    public DrawerBuilder withMultiSelect(boolean z) {
        this.mMultiSelect = z;
        return this;
    }

    public DrawerBuilder withSelectedItemByPosition(int i) {
        this.mSelectedItemPosition = i;
        return this;
    }

    public DrawerBuilder withSelectedItem(long j) {
        this.mSelectedItemIdentifier = j;
        return this;
    }

    public DrawerBuilder withRecyclerView(@NonNull RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
        return this;
    }

    public DrawerBuilder withHasStableIds(boolean z) {
        this.mHasStableIds = z;
        if (this.mAdapter != null) {
            this.mAdapter.setHasStableIds(z);
        }
        return this;
    }

    public DrawerBuilder withPositionBasedStateManagement(boolean z) {
        this.mPositionBasedStateManagement = z;
        return this;
    }

    public DrawerBuilder withAdapter(@NonNull FastAdapter<IDrawerItem> fastAdapter) {
        this.mAdapter = fastAdapter;
        this.mHeaderAdapter.wrap(this.mItemAdapter.wrap(this.mFooterAdapter.wrap(this.mAdapter)));
        return this;
    }

    protected FastAdapter<IDrawerItem> getAdapter() {
        if (this.mAdapter == null) {
            this.mAdapter = new FastAdapter();
            this.mAdapter.withSelectable(true);
            this.mAdapter.withAllowDeselection(false);
            this.mAdapter.setHasStableIds(this.mHasStableIds);
            this.mAdapter.withPositionBasedStateManagement(this.mPositionBasedStateManagement);
            this.mHeaderAdapter.wrap(this.mItemAdapter.wrap(this.mFooterAdapter.wrap(this.mAdapter)));
        }
        return this.mAdapter;
    }

    protected IItemAdapter<IDrawerItem> getItemAdapter() {
        return this.mItemAdapter;
    }

    protected IItemAdapter<IDrawerItem> getHeaderAdapter() {
        return this.mHeaderAdapter;
    }

    protected IItemAdapter<IDrawerItem> getFooterAdapter() {
        return this.mFooterAdapter;
    }

    public DrawerBuilder withAdapterWrapper(@NonNull Adapter adapter) {
        if (this.mAdapter == null) {
            throw new RuntimeException("this adapter has to be set in conjunction to a normal adapter which is used inside this wrapper adapter");
        }
        this.mAdapterWrapper = adapter;
        return this;
    }

    public DrawerBuilder withItemAnimator(ItemAnimator itemAnimator) {
        this.mItemAnimator = itemAnimator;
        return this;
    }

    public DrawerBuilder withDrawerItems(@NonNull List<IDrawerItem> list) {
        getItemAdapter().set(list);
        return this;
    }

    public DrawerBuilder addDrawerItems(@NonNull IDrawerItem... iDrawerItemArr) {
        getItemAdapter().add(iDrawerItemArr);
        return this;
    }

    public DrawerBuilder withStickyDrawerItems(@NonNull List<IDrawerItem> list) {
        this.mStickyDrawerItems = list;
        return this;
    }

    public DrawerBuilder addStickyDrawerItems(@NonNull IDrawerItem... iDrawerItemArr) {
        if (this.mStickyDrawerItems == null) {
            this.mStickyDrawerItems = new ArrayList();
        }
        Collections.addAll(this.mStickyDrawerItems, iDrawerItemArr);
        return this;
    }

    public DrawerBuilder inflateMenu(@MenuRes int i) {
        SupportMenuInflater supportMenuInflater = new SupportMenuInflater(this.mActivity);
        MenuBuilder menuBuilder = new MenuBuilder(this.mActivity);
        supportMenuInflater.inflate(i, menuBuilder);
        addMenuItems(menuBuilder, false);
        return this;
    }

    private void addMenuItems(Menu menu, boolean z) {
        int i = R.id.material_drawer_menu_default_group;
        int i2 = 0;
        while (i2 < menu.size()) {
            MenuItem item = menu.getItem(i2);
            if (!(z || item.getGroupId() == r0 || item.getGroupId() == 0)) {
                i = item.getGroupId();
                DividerDrawerItem dividerDrawerItem = new DividerDrawerItem();
                getItemAdapter().add(new IDrawerItem[]{dividerDrawerItem});
            }
            int i3 = i;
            IDrawerItem iDrawerItem;
            if (item.hasSubMenu()) {
                iDrawerItem = (IDrawerItem) ((PrimaryDrawerItem) ((PrimaryDrawerItem) ((PrimaryDrawerItem) ((PrimaryDrawerItem) new PrimaryDrawerItem().withName(item.getTitle().toString())).withIcon(item.getIcon())).withIdentifier((long) item.getItemId())).withEnabled(item.isEnabled())).withSelectable(false);
                getItemAdapter().add(new IDrawerItem[]{iDrawerItem});
                addMenuItems(item.getSubMenu(), true);
            } else if (item.getGroupId() != 0 || z) {
                iDrawerItem = (IDrawerItem) ((SecondaryDrawerItem) ((SecondaryDrawerItem) ((SecondaryDrawerItem) new SecondaryDrawerItem().withName(item.getTitle().toString())).withIcon(item.getIcon())).withIdentifier((long) item.getItemId())).withEnabled(item.isEnabled());
                getItemAdapter().add(new IDrawerItem[]{iDrawerItem});
            } else {
                iDrawerItem = (IDrawerItem) ((PrimaryDrawerItem) ((PrimaryDrawerItem) ((PrimaryDrawerItem) new PrimaryDrawerItem().withName(item.getTitle().toString())).withIcon(item.getIcon())).withIdentifier((long) item.getItemId())).withEnabled(item.isEnabled());
                getItemAdapter().add(new IDrawerItem[]{iDrawerItem});
            }
            i2++;
            i = i3;
        }
    }

    public DrawerBuilder withCloseOnClick(boolean z) {
        this.mCloseOnClick = z;
        return this;
    }

    public DrawerBuilder withDelayOnDrawerClose(int i) {
        this.mDelayOnDrawerClose = i;
        return this;
    }

    public DrawerBuilder withDelayDrawerClickEvent(int i) {
        this.mDelayDrawerClickEvent = i;
        return this;
    }

    public DrawerBuilder withOnDrawerListener(@NonNull OnDrawerListener onDrawerListener) {
        this.mOnDrawerListener = onDrawerListener;
        return this;
    }

    public DrawerBuilder withOnDrawerItemClickListener(@NonNull OnDrawerItemClickListener onDrawerItemClickListener) {
        this.mOnDrawerItemClickListener = onDrawerItemClickListener;
        return this;
    }

    public DrawerBuilder withOnDrawerItemLongClickListener(@NonNull OnDrawerItemLongClickListener onDrawerItemLongClickListener) {
        this.mOnDrawerItemLongClickListener = onDrawerItemLongClickListener;
        return this;
    }

    public DrawerBuilder withOnDrawerNavigationListener(@NonNull OnDrawerNavigationListener onDrawerNavigationListener) {
        this.mOnDrawerNavigationListener = onDrawerNavigationListener;
        return this;
    }

    public DrawerBuilder withShowDrawerOnFirstLaunch(boolean z) {
        this.mShowDrawerOnFirstLaunch = z;
        return this;
    }

    public DrawerBuilder withGenerateMiniDrawer(boolean z) {
        this.mGenerateMiniDrawer = z;
        return this;
    }

    public DrawerBuilder withSavedInstance(Bundle bundle) {
        this.mSavedInstance = bundle;
        return this;
    }

    private void handleShowOnFirstLaunch() {
        if (this.mActivity != null && this.mDrawerLayout != null && this.mShowDrawerOnFirstLaunch) {
            SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.mActivity);
            if (!defaultSharedPreferences.getBoolean("navigation_drawer_learned", false)) {
                this.mDrawerLayout.openDrawer(this.mSliderLayout);
                Editor edit = defaultSharedPreferences.edit();
                edit.putBoolean("navigation_drawer_learned", true);
                edit.apply();
            }
        }
    }

    public Drawer build() {
        if (this.mUsed) {
            throw new RuntimeException("you must not reuse a DrawerBuilder builder");
        } else if (this.mActivity == null) {
            throw new RuntimeException("please pass an activity");
        } else {
            this.mUsed = true;
            if (this.mDrawerLayout == null) {
                withDrawerLayout(-1);
            }
            this.mMaterialize = new MaterializeBuilder().withActivity(this.mActivity).withRootView(this.mRootView).withFullscreen(this.mFullscreen).withSystemUIHidden(this.mSystemUIHidden).withUseScrimInsetsLayout(false).withTransparentStatusBar(this.mTranslucentStatusBar).withTranslucentNavigationBarProgrammatically(this.mTranslucentNavigationBarProgrammatically).withContainer(this.mDrawerLayout).build();
            handleDrawerNavigation(this.mActivity, false);
            Drawer buildView = buildView();
            this.mSliderLayout.setId(R.id.material_drawer_slider_layout);
            this.mDrawerLayout.addView(this.mSliderLayout, 1);
            return buildView;
        }
    }

    public Drawer buildForFragment() {
        if (this.mUsed) {
            throw new RuntimeException("you must not reuse a DrawerBuilder builder");
        } else if (this.mActivity == null) {
            throw new RuntimeException("please pass an activity");
        } else if (this.mRootView == null) {
            throw new RuntimeException("please pass the view which should host the DrawerLayout");
        } else {
            this.mUsed = true;
            if (this.mDrawerLayout == null) {
                withDrawerLayout(-1);
            }
            View childAt = this.mRootView.getChildAt(0);
            if (childAt.getId() == R.id.materialize_root) {
                this.mRootView.removeAllViews();
            } else {
                this.mRootView.removeView(childAt);
            }
            this.mRootView.addView(this.mDrawerLayout, new LayoutParams(-1, -1));
            this.mDrawerLayout.setId(R.id.materialize_root);
            handleDrawerNavigation(this.mActivity, false);
            Drawer buildView = buildView();
            this.mDrawerLayout.addView(childAt, 0);
            this.mSliderLayout.setId(R.id.material_drawer_slider_layout);
            this.mDrawerLayout.addView(this.mSliderLayout, 1);
            return buildView;
        }
    }

    protected void handleDrawerNavigation(Activity activity, boolean z) {
        1 1 = new 1(this);
        if (z) {
            this.mActionBarDrawerToggle = null;
        }
        if (this.mActionBarDrawerToggleEnabled && this.mActionBarDrawerToggle == null && this.mToolbar != null) {
            this.mActionBarDrawerToggle = new 2(this, activity, this.mDrawerLayout, this.mToolbar, R.string.material_drawer_open, R.string.material_drawer_close);
            this.mActionBarDrawerToggle.syncState();
        }
        if (this.mToolbar != null) {
            this.mToolbar.setNavigationOnClickListener(1);
        }
        if (this.mActionBarDrawerToggle != null) {
            this.mActionBarDrawerToggle.setToolbarNavigationClickListener(1);
            this.mDrawerLayout.addDrawerListener(this.mActionBarDrawerToggle);
            return;
        }
        this.mDrawerLayout.addDrawerListener(new 3(this));
    }

    public Drawer buildView() {
        this.mSliderLayout = (ScrimInsetsRelativeLayout) this.mActivity.getLayoutInflater().inflate(R.layout.material_drawer_slider, this.mDrawerLayout, false);
        this.mSliderLayout.setBackgroundColor(UIUtils.getThemeColorFromAttrOrRes(this.mActivity, R.attr.material_drawer_background, R.color.material_drawer_background));
        DrawerLayout.LayoutParams layoutParams = (DrawerLayout.LayoutParams) this.mSliderLayout.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.gravity = this.mDrawerGravity.intValue();
            this.mSliderLayout.setLayoutParams(DrawerUtils.processDrawerLayoutParams(this, layoutParams));
        }
        createContent();
        Drawer drawer = new Drawer(this);
        if (this.mAccountHeader != null) {
            this.mAccountHeader.setDrawer(drawer);
        }
        if (this.mSavedInstance != null && this.mSavedInstance.getBoolean("bundle_drawer_content_switched", false)) {
            this.mAccountHeader.toggleSelectionList(this.mActivity);
        }
        handleShowOnFirstLaunch();
        if (!this.mAppended && this.mGenerateMiniDrawer) {
            this.mMiniDrawer = new MiniDrawer().withDrawer(drawer).withAccountHeader(this.mAccountHeader).withPositionBasedStateManagement(this.mPositionBasedStateManagement);
        }
        this.mActivity = null;
        return drawer;
    }

    public Drawer append(@NonNull Drawer drawer) {
        if (this.mUsed) {
            throw new RuntimeException("you must not reuse a DrawerBuilder builder");
        } else if (this.mDrawerGravity == null) {
            throw new RuntimeException("please set the gravity for the drawer");
        } else {
            this.mUsed = true;
            this.mAppended = true;
            this.mDrawerLayout = drawer.getDrawerLayout();
            this.mSliderLayout = (ScrimInsetsRelativeLayout) this.mActivity.getLayoutInflater().inflate(R.layout.material_drawer_slider, this.mDrawerLayout, false);
            this.mSliderLayout.setBackgroundColor(UIUtils.getThemeColorFromAttrOrRes(this.mActivity, R.attr.material_drawer_background, R.color.material_drawer_background));
            DrawerLayout.LayoutParams layoutParams = (DrawerLayout.LayoutParams) this.mSliderLayout.getLayoutParams();
            layoutParams.gravity = this.mDrawerGravity.intValue();
            this.mSliderLayout.setLayoutParams(DrawerUtils.processDrawerLayoutParams(this, layoutParams));
            this.mSliderLayout.setId(R.id.material_drawer_slider_layout);
            this.mDrawerLayout.addView(this.mSliderLayout, 1);
            createContent();
            Drawer drawer2 = new Drawer(this);
            if (this.mSavedInstance != null && this.mSavedInstance.getBoolean("bundle_drawer_content_switched_appended", false)) {
                this.mAccountHeader.toggleSelectionList(this.mActivity);
            }
            this.mActivity = null;
            return drawer2;
        }
    }

    private void createContent() {
        if (this.mCustomView != null) {
            ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
            layoutParams.weight = IPhotoView.DEFAULT_MIN_SCALE;
            this.mSliderLayout.addView(this.mCustomView, layoutParams);
            return;
        }
        int statusBarHeight;
        View view;
        if (VERSION.SDK_INT < 21 && this.mDrawerLayout != null) {
            if (ViewCompat.getLayoutDirection(this.mRootView) == 0) {
                this.mDrawerLayout.setDrawerShadow(this.mDrawerGravity.intValue() == 8388611 ? R.drawable.material_drawer_shadow_right : R.drawable.material_drawer_shadow_left, this.mDrawerGravity.intValue());
            } else {
                this.mDrawerLayout.setDrawerShadow(this.mDrawerGravity.intValue() == 8388611 ? R.drawable.material_drawer_shadow_left : R.drawable.material_drawer_shadow_right, this.mDrawerGravity.intValue());
            }
        }
        if (this.mRecyclerView == null) {
            View inflate = LayoutInflater.from(this.mActivity).inflate(R.layout.material_drawer_recycler_view, this.mSliderLayout, false);
            this.mRecyclerView = (RecyclerView) inflate.findViewById(R.id.material_drawer_recycler_view);
            this.mRecyclerView.setItemAnimator(this.mItemAnimator);
            this.mRecyclerView.setFadingEdgeLength(0);
            this.mRecyclerView.setClipToPadding(false);
            this.mRecyclerView.setLayoutManager(this.mLayoutManager);
            if ((this.mDisplayBelowStatusBar == null || this.mDisplayBelowStatusBar.booleanValue()) && !this.mSystemUIHidden) {
                statusBarHeight = UIUtils.getStatusBarHeight(this.mActivity);
            } else {
                statusBarHeight = 0;
            }
            int i = this.mActivity.getResources().getConfiguration().orientation;
            if ((this.mTranslucentNavigationBar || this.mFullscreen) && VERSION.SDK_INT >= 21 && !this.mSystemUIHidden && (i == 1 || (i == 2 && DrawerUIUtils.isSystemBarOnBottom(this.mActivity)))) {
                i = UIUtils.getNavigationBarHeight(this.mActivity);
            } else {
                i = 0;
            }
            this.mRecyclerView.setPadding(0, statusBarHeight, 0, i);
            view = inflate;
        } else {
            view = this.mRecyclerView;
        }
        ViewGroup.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-1, -1);
        layoutParams2.weight = IPhotoView.DEFAULT_MIN_SCALE;
        this.mSliderLayout.addView(view, layoutParams2);
        if (this.mInnerShadow) {
            view = this.mSliderLayout.findViewById(R.id.material_drawer_inner_shadow);
            view.setVisibility(0);
            view.bringToFront();
            if (this.mDrawerGravity.intValue() == 8388611) {
                view.setBackgroundResource(R.drawable.material_drawer_shadow_left);
            } else {
                view.setBackgroundResource(R.drawable.material_drawer_shadow_right);
            }
        }
        if (this.mSliderBackgroundColor != 0) {
            this.mSliderLayout.setBackgroundColor(this.mSliderBackgroundColor);
        } else if (this.mSliderBackgroundColorRes != -1) {
            this.mSliderLayout.setBackgroundColor(ContextCompat.getColor(this.mActivity, this.mSliderBackgroundColorRes));
        } else if (this.mSliderBackgroundDrawable != null) {
            UIUtils.setBackground(this.mSliderLayout, this.mSliderBackgroundDrawable);
        } else if (this.mSliderBackgroundDrawableRes != -1) {
            UIUtils.setBackground(this.mSliderLayout, this.mSliderBackgroundDrawableRes);
        }
        DrawerUtils.handleHeaderView(this);
        DrawerUtils.handleFooterView(this, new 4(this));
        this.mAdapter.withMultiSelect(this.mMultiSelect);
        if (this.mMultiSelect) {
            this.mAdapter.withSelectOnLongClick(false);
            this.mAdapter.withAllowDeselection(true);
        }
        if (this.mAdapterWrapper == null) {
            this.mRecyclerView.setAdapter(this.mAdapter);
        } else {
            this.mRecyclerView.setAdapter(this.mAdapterWrapper);
        }
        if (this.mSelectedItemPosition == 0 && this.mSelectedItemIdentifier != 0) {
            this.mSelectedItemPosition = DrawerUtils.getPositionByIdentifier(this, this.mSelectedItemIdentifier);
        }
        if (this.mHeaderView != null && this.mSelectedItemPosition == 0) {
            this.mSelectedItemPosition = 1;
        }
        this.mAdapter.deselect();
        this.mAdapter.select(this.mSelectedItemPosition);
        this.mAdapter.withOnClickListener(new 5(this));
        this.mAdapter.withOnLongClickListener(new 6(this));
        if (this.mRecyclerView != null) {
            this.mRecyclerView.scrollToPosition(0);
        }
        if (this.mSavedInstance != null) {
            if (this.mAppended) {
                this.mAdapter.withSavedInstanceState(this.mSavedInstance, "_selection_appended");
                DrawerUtils.setStickyFooterSelection(this, this.mSavedInstance.getInt("bundle_sticky_footer_selection_appended", -1), null);
            } else {
                this.mAdapter.withSavedInstanceState(this.mSavedInstance, "_selection");
                DrawerUtils.setStickyFooterSelection(this, this.mSavedInstance.getInt("bundle_sticky_footer_selection", -1), null);
            }
        }
        if (this.mFireInitialOnClick && this.mOnDrawerItemClickListener != null) {
            statusBarHeight = this.mAdapter.getSelections().size() == 0 ? -1 : ((Integer) this.mAdapter.getSelections().iterator().next()).intValue();
            this.mOnDrawerItemClickListener.onItemClick(null, statusBarHeight, getDrawerItem(statusBarHeight));
        }
    }

    protected void closeDrawerDelayed() {
        if (this.mCloseOnClick && this.mDrawerLayout != null) {
            if (this.mDelayOnDrawerClose > -1) {
                new Handler().postDelayed(new 7(this), (long) this.mDelayOnDrawerClose);
            } else {
                this.mDrawerLayout.closeDrawers();
            }
        }
    }

    protected IDrawerItem getDrawerItem(int i) {
        return (IDrawerItem) getAdapter().getItem(i);
    }

    protected boolean checkDrawerItem(int i, boolean z) {
        return getAdapter().getItem(i) != null;
    }

    protected void resetStickyFooterSelection() {
        if (this.mStickyFooterView instanceof LinearLayout) {
            for (int i = 0; i < this.mStickyFooterView.getChildCount(); i++) {
                this.mStickyFooterView.getChildAt(i).setActivated(false);
                this.mStickyFooterView.getChildAt(i).setSelected(false);
            }
        }
    }
}
