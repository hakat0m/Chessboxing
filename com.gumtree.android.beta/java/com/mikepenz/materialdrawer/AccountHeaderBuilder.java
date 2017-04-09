package com.mikepenz.materialdrawer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import com.apptentive.android.sdk.BuildConfig;
import com.mikepenz.fastadapter.utils.IdDistributor;
import com.mikepenz.materialdrawer.AccountHeader.OnAccountHeaderItemLongClickListener;
import com.mikepenz.materialdrawer.AccountHeader.OnAccountHeaderListener;
import com.mikepenz.materialdrawer.AccountHeader.OnAccountHeaderProfileImageListener;
import com.mikepenz.materialdrawer.AccountHeader.OnAccountHeaderSelectionViewClickListener;
import com.mikepenz.materialdrawer.Drawer.OnDrawerItemClickListener;
import com.mikepenz.materialdrawer.Drawer.OnDrawerItemLongClickListener;
import com.mikepenz.materialdrawer.holder.ColorHolder;
import com.mikepenz.materialdrawer.holder.DimenHolder;
import com.mikepenz.materialdrawer.holder.ImageHolder;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader.Tags;
import com.mikepenz.materialdrawer.view.BezelImageView;
import com.mikepenz.materialize.holder.StringHolder;
import com.mikepenz.materialize.util.UIUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class AccountHeaderBuilder {
    protected View mAccountHeader;
    protected ImageView mAccountHeaderBackground;
    protected View mAccountHeaderContainer;
    protected View mAccountHeaderTextSection;
    protected int mAccountHeaderTextSectionBackgroundResource = -1;
    protected ImageView mAccountSwitcherArrow;
    protected Activity mActivity;
    protected boolean mAlternativeProfileHeaderSwitching = false;
    protected Boolean mCloseDrawerOnProfileListClick = null;
    protected boolean mCompactStyle = false;
    protected boolean mCurrentHiddenInList = false;
    protected IProfile mCurrentProfile;
    protected TextView mCurrentProfileEmail;
    protected TextView mCurrentProfileName;
    protected BezelImageView mCurrentProfileView;
    protected boolean mDividerBelowHeader = true;
    protected Drawer mDrawer;
    protected Typeface mEmailTypeface;
    protected ImageHolder mHeaderBackground;
    protected ScaleType mHeaderBackgroundScaleType = null;
    protected DimenHolder mHeight;
    protected Typeface mNameTypeface;
    protected OnAccountHeaderItemLongClickListener mOnAccountHeaderItemLongClickListener;
    protected OnAccountHeaderListener mOnAccountHeaderListener;
    protected OnAccountHeaderProfileImageListener mOnAccountHeaderProfileImageListener;
    protected OnAccountHeaderSelectionViewClickListener mOnAccountHeaderSelectionViewClickListener;
    protected int mOnProfileClickDrawerCloseDelay = 100;
    protected boolean mOnlyMainProfileImageVisible = false;
    protected boolean mOnlySmallProfileImagesVisible = false;
    protected boolean mPaddingBelowHeader = true;
    protected IProfile mProfileFirst;
    protected BezelImageView mProfileFirstView;
    protected boolean mProfileImagesClickable = true;
    protected boolean mProfileImagesVisible = true;
    protected IProfile mProfileSecond;
    protected BezelImageView mProfileSecondView;
    protected IProfile mProfileThird;
    protected BezelImageView mProfileThirdView;
    protected List<IProfile> mProfiles;
    protected boolean mResetDrawerOnProfileListClick = true;
    protected Bundle mSavedInstance;
    protected String mSelectionFirstLine;
    protected boolean mSelectionFirstLineShown = true;
    protected boolean mSelectionListEnabled = true;
    protected boolean mSelectionListEnabledForSingleProfile = true;
    protected boolean mSelectionListShown = false;
    protected String mSelectionSecondLine;
    protected boolean mSelectionSecondLineShown = true;
    protected ColorHolder mTextColor;
    protected boolean mThreeSmallProfileImages = false;
    protected boolean mTranslucentStatusBar = true;
    protected Typeface mTypeface;
    private OnClickListener onCurrentProfileClickListener = new 1(this);
    private OnLongClickListener onCurrentProfileLongClickListener = new 3(this);
    private OnDrawerItemClickListener onDrawerItemClickListener = new 7(this);
    private OnDrawerItemLongClickListener onDrawerItemLongClickListener = new 8(this);
    private OnClickListener onProfileClickListener = new 2(this);
    private OnLongClickListener onProfileLongClickListener = new 4(this);
    private OnClickListener onSelectionClickListener = new 6(this);

    public AccountHeaderBuilder withActivity(@NonNull Activity activity) {
        this.mActivity = activity;
        return this;
    }

    public AccountHeaderBuilder withCompactStyle(boolean z) {
        this.mCompactStyle = z;
        return this;
    }

    public AccountHeaderBuilder withTypeface(@NonNull Typeface typeface) {
        this.mTypeface = typeface;
        return this;
    }

    public AccountHeaderBuilder withNameTypeface(@NonNull Typeface typeface) {
        this.mNameTypeface = typeface;
        return this;
    }

    public AccountHeaderBuilder withEmailTypeface(@NonNull Typeface typeface) {
        this.mEmailTypeface = typeface;
        return this;
    }

    public AccountHeaderBuilder withHeightPx(int i) {
        this.mHeight = DimenHolder.fromPixel(i);
        return this;
    }

    public AccountHeaderBuilder withHeightDp(int i) {
        this.mHeight = DimenHolder.fromDp(i);
        return this;
    }

    public AccountHeaderBuilder withHeightRes(@DimenRes int i) {
        this.mHeight = DimenHolder.fromResource(i);
        return this;
    }

    public AccountHeaderBuilder withTextColor(@ColorInt int i) {
        this.mTextColor = ColorHolder.fromColor(i);
        return this;
    }

    public AccountHeaderBuilder withTextColorRes(@ColorRes int i) {
        this.mTextColor = ColorHolder.fromColorRes(i);
        return this;
    }

    public AccountHeaderBuilder withCurrentProfileHiddenInList(boolean z) {
        this.mCurrentHiddenInList = z;
        return this;
    }

    @Deprecated
    public AccountHeaderBuilder withSelectionFistLineShown(boolean z) {
        this.mSelectionFirstLineShown = z;
        return this;
    }

    public AccountHeaderBuilder withSelectionFirstLineShown(boolean z) {
        this.mSelectionFirstLineShown = z;
        return this;
    }

    public AccountHeaderBuilder withSelectionSecondLineShown(boolean z) {
        this.mSelectionSecondLineShown = z;
        return this;
    }

    public AccountHeaderBuilder withSelectionFirstLine(String str) {
        this.mSelectionFirstLine = str;
        return this;
    }

    public AccountHeaderBuilder withSelectionSecondLine(String str) {
        this.mSelectionSecondLine = str;
        return this;
    }

    public AccountHeaderBuilder withPaddingBelowHeader(boolean z) {
        this.mPaddingBelowHeader = z;
        return this;
    }

    public AccountHeaderBuilder withDividerBelowHeader(boolean z) {
        this.mDividerBelowHeader = z;
        return this;
    }

    public AccountHeaderBuilder withTranslucentStatusBar(boolean z) {
        this.mTranslucentStatusBar = z;
        return this;
    }

    public AccountHeaderBuilder withHeaderBackground(Drawable drawable) {
        this.mHeaderBackground = new ImageHolder(drawable);
        return this;
    }

    public AccountHeaderBuilder withHeaderBackground(@DrawableRes int i) {
        this.mHeaderBackground = new ImageHolder(i);
        return this;
    }

    public AccountHeaderBuilder withHeaderBackground(ImageHolder imageHolder) {
        this.mHeaderBackground = imageHolder;
        return this;
    }

    public AccountHeaderBuilder withHeaderBackgroundScaleType(ScaleType scaleType) {
        this.mHeaderBackgroundScaleType = scaleType;
        return this;
    }

    public AccountHeaderBuilder withProfileImagesVisible(boolean z) {
        this.mProfileImagesVisible = z;
        return this;
    }

    public AccountHeaderBuilder withOnlyMainProfileImageVisible(boolean z) {
        this.mOnlyMainProfileImageVisible = z;
        return this;
    }

    public AccountHeaderBuilder withOnlySmallProfileImagesVisible(boolean z) {
        this.mOnlySmallProfileImagesVisible = z;
        return this;
    }

    public AccountHeaderBuilder withCloseDrawerOnProfileListClick(boolean z) {
        this.mCloseDrawerOnProfileListClick = Boolean.valueOf(z);
        return this;
    }

    public AccountHeaderBuilder withResetDrawerOnProfileListClick(boolean z) {
        this.mResetDrawerOnProfileListClick = z;
        return this;
    }

    public AccountHeaderBuilder withProfileImagesClickable(boolean z) {
        this.mProfileImagesClickable = z;
        return this;
    }

    public AccountHeaderBuilder withAlternativeProfileHeaderSwitching(boolean z) {
        this.mAlternativeProfileHeaderSwitching = z;
        return this;
    }

    public AccountHeaderBuilder withThreeSmallProfileImages(boolean z) {
        this.mThreeSmallProfileImages = z;
        return this;
    }

    public AccountHeaderBuilder withOnProfileClickDrawerCloseDelay(int i) {
        this.mOnProfileClickDrawerCloseDelay = i;
        return this;
    }

    public AccountHeaderBuilder withOnAccountHeaderProfileImageListener(OnAccountHeaderProfileImageListener onAccountHeaderProfileImageListener) {
        this.mOnAccountHeaderProfileImageListener = onAccountHeaderProfileImageListener;
        return this;
    }

    public AccountHeaderBuilder withOnAccountHeaderSelectionViewClickListener(OnAccountHeaderSelectionViewClickListener onAccountHeaderSelectionViewClickListener) {
        this.mOnAccountHeaderSelectionViewClickListener = onAccountHeaderSelectionViewClickListener;
        return this;
    }

    public AccountHeaderBuilder withSelectionListEnabledForSingleProfile(boolean z) {
        this.mSelectionListEnabledForSingleProfile = z;
        return this;
    }

    public AccountHeaderBuilder withSelectionListEnabled(boolean z) {
        this.mSelectionListEnabled = z;
        return this;
    }

    public AccountHeaderBuilder withAccountHeader(@NonNull View view) {
        this.mAccountHeaderContainer = view;
        return this;
    }

    public AccountHeaderBuilder withAccountHeader(@LayoutRes int i) {
        if (this.mActivity == null) {
            throw new RuntimeException("please pass an activity first to use this call");
        }
        if (i != -1) {
            this.mAccountHeaderContainer = this.mActivity.getLayoutInflater().inflate(i, null, false);
        } else if (this.mCompactStyle) {
            this.mAccountHeaderContainer = this.mActivity.getLayoutInflater().inflate(R.layout.material_drawer_compact_header, null, false);
        } else {
            this.mAccountHeaderContainer = this.mActivity.getLayoutInflater().inflate(R.layout.material_drawer_header, null, false);
        }
        return this;
    }

    public AccountHeaderBuilder withProfiles(@NonNull List<IProfile> list) {
        this.mProfiles = IdDistributor.checkIds(list);
        return this;
    }

    public AccountHeaderBuilder addProfiles(@NonNull IProfile... iProfileArr) {
        if (this.mProfiles == null) {
            this.mProfiles = new ArrayList();
        }
        Collections.addAll(this.mProfiles, IdDistributor.checkIds(iProfileArr));
        return this;
    }

    public AccountHeaderBuilder withOnAccountHeaderListener(OnAccountHeaderListener onAccountHeaderListener) {
        this.mOnAccountHeaderListener = onAccountHeaderListener;
        return this;
    }

    public AccountHeaderBuilder withOnAccountHeaderItemLongClickListener(OnAccountHeaderItemLongClickListener onAccountHeaderItemLongClickListener) {
        this.mOnAccountHeaderItemLongClickListener = onAccountHeaderItemLongClickListener;
        return this;
    }

    public AccountHeaderBuilder withDrawer(@NonNull Drawer drawer) {
        this.mDrawer = drawer;
        drawer.getRecyclerView().setPadding(drawer.getRecyclerView().getPaddingLeft(), 0, drawer.getRecyclerView().getPaddingRight(), drawer.getRecyclerView().getPaddingBottom());
        return this;
    }

    public AccountHeaderBuilder withSavedInstance(Bundle bundle) {
        this.mSavedInstance = bundle;
        return this;
    }

    private void setHeaderHeight(int i) {
        if (this.mAccountHeaderContainer != null) {
            LayoutParams layoutParams;
            LayoutParams layoutParams2 = this.mAccountHeaderContainer.getLayoutParams();
            if (layoutParams2 != null) {
                layoutParams2.height = i;
                this.mAccountHeaderContainer.setLayoutParams(layoutParams2);
            }
            View findViewById = this.mAccountHeaderContainer.findViewById(R.id.material_drawer_account_header);
            if (findViewById != null) {
                layoutParams = findViewById.getLayoutParams();
                layoutParams.height = i;
                findViewById.setLayoutParams(layoutParams);
            }
            findViewById = this.mAccountHeaderContainer.findViewById(R.id.material_drawer_account_header_background);
            if (findViewById != null) {
                layoutParams = findViewById.getLayoutParams();
                layoutParams.height = i;
                findViewById.setLayoutParams(layoutParams);
            }
        }
    }

    private void handleSelectionView(IProfile iProfile, boolean z) {
        if (z) {
            if (VERSION.SDK_INT >= 21) {
                ((FrameLayout) this.mAccountHeaderContainer).setForeground(ContextCompat.getDrawable(this.mAccountHeaderContainer.getContext(), this.mAccountHeaderTextSectionBackgroundResource));
                this.mAccountHeaderContainer.setOnClickListener(this.onSelectionClickListener);
                this.mAccountHeaderContainer.setTag(R.id.material_drawer_profile_header, iProfile);
                return;
            }
            this.mAccountHeaderTextSection.setBackgroundResource(this.mAccountHeaderTextSectionBackgroundResource);
            this.mAccountHeaderTextSection.setOnClickListener(this.onSelectionClickListener);
            this.mAccountHeaderTextSection.setTag(R.id.material_drawer_profile_header, iProfile);
        } else if (VERSION.SDK_INT >= 21) {
            ((FrameLayout) this.mAccountHeaderContainer).setForeground(null);
            this.mAccountHeaderContainer.setOnClickListener(null);
        } else {
            UIUtils.setBackground(this.mAccountHeaderTextSection, null);
            this.mAccountHeaderTextSection.setOnClickListener(null);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.mikepenz.materialdrawer.AccountHeader build() {
        /*
        r10 = this;
        r9 = 1;
        r8 = -1;
        r0 = r10.mAccountHeaderContainer;
        if (r0 != 0) goto L_0x0009;
    L_0x0006:
        r10.withAccountHeader(r8);
    L_0x0009:
        r0 = r10.mAccountHeaderContainer;
        r1 = com.mikepenz.materialdrawer.R.id.material_drawer_account_header;
        r0 = r0.findViewById(r1);
        r10.mAccountHeader = r0;
        r0 = r10.mActivity;
        r0 = r0.getResources();
        r1 = com.mikepenz.materialdrawer.R.dimen.material_drawer_account_header_height;
        r2 = r0.getDimensionPixelSize(r1);
        r0 = r10.mActivity;
        r3 = com.mikepenz.materialize.util.UIUtils.getStatusBarHeight(r0, r9);
        r0 = r10.mHeight;
        if (r0 == 0) goto L_0x017d;
    L_0x0029:
        r0 = r10.mHeight;
        r1 = r10.mActivity;
        r0 = r0.asPixel(r1);
    L_0x0031:
        r1 = r10.mTranslucentStatusBar;
        if (r1 == 0) goto L_0x005e;
    L_0x0035:
        r1 = android.os.Build.VERSION.SDK_INT;
        r4 = 21;
        if (r1 < r4) goto L_0x005e;
    L_0x003b:
        r1 = r10.mAccountHeader;
        r4 = r10.mAccountHeader;
        r4 = r4.getPaddingLeft();
        r5 = r10.mAccountHeader;
        r5 = r5.getPaddingTop();
        r5 = r5 + r3;
        r6 = r10.mAccountHeader;
        r6 = r6.getPaddingRight();
        r7 = r10.mAccountHeader;
        r7 = r7.getPaddingBottom();
        r1.setPadding(r4, r5, r6, r7);
        r1 = r10.mCompactStyle;
        if (r1 == 0) goto L_0x01b4;
    L_0x005d:
        r0 = r0 + r3;
    L_0x005e:
        r10.setHeaderHeight(r0);
        r0 = r10.mAccountHeaderContainer;
        r1 = com.mikepenz.materialdrawer.R.id.material_drawer_account_header_background;
        r0 = r0.findViewById(r1);
        r0 = (android.widget.ImageView) r0;
        r10.mAccountHeaderBackground = r0;
        r0 = r10.mHeaderBackground;
        r1 = r10.mAccountHeaderBackground;
        r2 = com.mikepenz.materialdrawer.util.DrawerImageLoader.Tags.ACCOUNT_HEADER;
        r2 = r2.name();
        com.mikepenz.materialdrawer.holder.ImageHolder.applyTo(r0, r1, r2);
        r0 = r10.mHeaderBackgroundScaleType;
        if (r0 == 0) goto L_0x0085;
    L_0x007e:
        r0 = r10.mAccountHeaderBackground;
        r1 = r10.mHeaderBackgroundScaleType;
        r0.setScaleType(r1);
    L_0x0085:
        r0 = r10.mTextColor;
        r1 = r10.mActivity;
        r2 = com.mikepenz.materialdrawer.R.attr.material_drawer_header_selection_text;
        r3 = com.mikepenz.materialdrawer.R.color.material_drawer_header_selection_text;
        r1 = com.mikepenz.materialdrawer.holder.ColorHolder.color(r0, r1, r2, r3);
        r0 = r10.mCompactStyle;
        if (r0 == 0) goto L_0x01bc;
    L_0x0095:
        r0 = r10.mAccountHeader;
        r10.mAccountHeaderTextSection = r0;
    L_0x0099:
        r0 = r10.mActivity;
        r0 = com.mikepenz.materialize.util.UIUtils.getSelectableBackgroundRes(r0);
        r10.mAccountHeaderTextSectionBackgroundResource = r0;
        r0 = r10.mCurrentProfile;
        r10.handleSelectionView(r0, r9);
        r0 = r10.mAccountHeaderContainer;
        r2 = com.mikepenz.materialdrawer.R.id.material_drawer_account_header_text_switcher;
        r0 = r0.findViewById(r2);
        r0 = (android.widget.ImageView) r0;
        r10.mAccountSwitcherArrow = r0;
        r0 = r10.mAccountSwitcherArrow;
        r2 = new com.mikepenz.iconics.IconicsDrawable;
        r3 = r10.mActivity;
        r4 = com.mikepenz.materialdrawer.icons.MaterialDrawerFont.Icon.mdf_arrow_drop_down;
        r2.<init>(r3, r4);
        r3 = com.mikepenz.materialdrawer.R.dimen.material_drawer_account_header_dropdown;
        r2 = r2.sizeRes(r3);
        r3 = com.mikepenz.materialdrawer.R.dimen.material_drawer_account_header_dropdown_padding;
        r2 = r2.paddingRes(r3);
        r2 = r2.color(r1);
        r0.setImageDrawable(r2);
        r0 = r10.mAccountHeader;
        r2 = com.mikepenz.materialdrawer.R.id.material_drawer_account_header_current;
        r0 = r0.findViewById(r2);
        r0 = (com.mikepenz.materialdrawer.view.BezelImageView) r0;
        r10.mCurrentProfileView = r0;
        r0 = r10.mAccountHeader;
        r2 = com.mikepenz.materialdrawer.R.id.material_drawer_account_header_name;
        r0 = r0.findViewById(r2);
        r0 = (android.widget.TextView) r0;
        r10.mCurrentProfileName = r0;
        r0 = r10.mAccountHeader;
        r2 = com.mikepenz.materialdrawer.R.id.material_drawer_account_header_email;
        r0 = r0.findViewById(r2);
        r0 = (android.widget.TextView) r0;
        r10.mCurrentProfileEmail = r0;
        r0 = r10.mNameTypeface;
        if (r0 == 0) goto L_0x01c8;
    L_0x00f8:
        r0 = r10.mCurrentProfileName;
        r2 = r10.mNameTypeface;
        r0.setTypeface(r2);
    L_0x00ff:
        r0 = r10.mEmailTypeface;
        if (r0 == 0) goto L_0x01d5;
    L_0x0103:
        r0 = r10.mCurrentProfileEmail;
        r2 = r10.mEmailTypeface;
        r0.setTypeface(r2);
    L_0x010a:
        r0 = r10.mCurrentProfileName;
        r0.setTextColor(r1);
        r0 = r10.mCurrentProfileEmail;
        r0.setTextColor(r1);
        r0 = r10.mAccountHeader;
        r1 = com.mikepenz.materialdrawer.R.id.material_drawer_account_header_small_first;
        r0 = r0.findViewById(r1);
        r0 = (com.mikepenz.materialdrawer.view.BezelImageView) r0;
        r10.mProfileFirstView = r0;
        r0 = r10.mAccountHeader;
        r1 = com.mikepenz.materialdrawer.R.id.material_drawer_account_header_small_second;
        r0 = r0.findViewById(r1);
        r0 = (com.mikepenz.materialdrawer.view.BezelImageView) r0;
        r10.mProfileSecondView = r0;
        r0 = r10.mAccountHeader;
        r1 = com.mikepenz.materialdrawer.R.id.material_drawer_account_header_small_third;
        r0 = r0.findViewById(r1);
        r0 = (com.mikepenz.materialdrawer.view.BezelImageView) r0;
        r10.mProfileThirdView = r0;
        r10.calculateProfiles();
        r10.buildProfiles();
        r0 = r10.mSavedInstance;
        if (r0 == 0) goto L_0x0165;
    L_0x0142:
        r0 = r10.mSavedInstance;
        r1 = "bundle_selection_header";
        r0 = r0.getInt(r1, r8);
        if (r0 == r8) goto L_0x0165;
    L_0x014c:
        r1 = r10.mProfiles;
        if (r1 == 0) goto L_0x0165;
    L_0x0150:
        if (r0 <= r8) goto L_0x0165;
    L_0x0152:
        r1 = r10.mProfiles;
        r1 = r1.size();
        if (r0 >= r1) goto L_0x0165;
    L_0x015a:
        r1 = r10.mProfiles;
        r0 = r1.get(r0);
        r0 = (com.mikepenz.materialdrawer.model.interfaces.IProfile) r0;
        r10.switchProfiles(r0);
    L_0x0165:
        r0 = r10.mDrawer;
        if (r0 == 0) goto L_0x0174;
    L_0x0169:
        r0 = r10.mDrawer;
        r1 = r10.mAccountHeaderContainer;
        r2 = r10.mPaddingBelowHeader;
        r3 = r10.mDividerBelowHeader;
        r0.setHeader(r1, r2, r3);
    L_0x0174:
        r0 = 0;
        r10.mActivity = r0;
        r0 = new com.mikepenz.materialdrawer.AccountHeader;
        r0.<init>(r10);
        return r0;
    L_0x017d:
        r0 = r10.mCompactStyle;
        if (r0 == 0) goto L_0x018f;
    L_0x0181:
        r0 = r10.mActivity;
        r0 = r0.getResources();
        r1 = com.mikepenz.materialdrawer.R.dimen.material_drawer_account_header_height_compact;
        r0 = r0.getDimensionPixelSize(r1);
        goto L_0x0031;
    L_0x018f:
        r0 = r10.mActivity;
        r0 = com.mikepenz.materialdrawer.util.DrawerUIUtils.getOptimalDrawerWidth(r0);
        r0 = (double) r0;
        r4 = 4603241769126068224; // 0x3fe2000000000000 float:0.0 double:0.5625;
        r0 = r0 * r4;
        r1 = (int) r0;
        r0 = android.os.Build.VERSION.SDK_INT;
        r4 = 19;
        if (r0 >= r4) goto L_0x01b1;
    L_0x01a0:
        r0 = r1 - r3;
        r4 = (float) r0;
        r5 = (float) r2;
        r6 = 1090519040; // 0x41000000 float:8.0 double:5.38787994E-315;
        r7 = r10.mActivity;
        r6 = com.mikepenz.materialize.util.UIUtils.convertDpToPixel(r6, r7);
        r5 = r5 - r6;
        r4 = (r4 > r5 ? 1 : (r4 == r5 ? 0 : -1));
        if (r4 > 0) goto L_0x0031;
    L_0x01b1:
        r0 = r1;
        goto L_0x0031;
    L_0x01b4:
        r1 = r0 - r3;
        if (r1 > r2) goto L_0x005e;
    L_0x01b8:
        r0 = r2 + r3;
        goto L_0x005e;
    L_0x01bc:
        r0 = r10.mAccountHeaderContainer;
        r2 = com.mikepenz.materialdrawer.R.id.material_drawer_account_header_text_section;
        r0 = r0.findViewById(r2);
        r10.mAccountHeaderTextSection = r0;
        goto L_0x0099;
    L_0x01c8:
        r0 = r10.mTypeface;
        if (r0 == 0) goto L_0x00ff;
    L_0x01cc:
        r0 = r10.mCurrentProfileName;
        r2 = r10.mTypeface;
        r0.setTypeface(r2);
        goto L_0x00ff;
    L_0x01d5:
        r0 = r10.mTypeface;
        if (r0 == 0) goto L_0x010a;
    L_0x01d9:
        r0 = r10.mCurrentProfileEmail;
        r2 = r10.mTypeface;
        r0.setTypeface(r2);
        goto L_0x010a;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mikepenz.materialdrawer.AccountHeaderBuilder.build():com.mikepenz.materialdrawer.AccountHeader");
    }

    protected void calculateProfiles() {
        int i = 0;
        if (this.mProfiles == null) {
            this.mProfiles = new ArrayList();
        }
        int i2;
        if (this.mCurrentProfile == null) {
            i2 = 0;
            while (i < this.mProfiles.size()) {
                if (this.mProfiles.size() > i && ((IProfile) this.mProfiles.get(i)).isSelectable()) {
                    if (i2 == 0 && this.mCurrentProfile == null) {
                        this.mCurrentProfile = (IProfile) this.mProfiles.get(i);
                    } else if (i2 == 1 && this.mProfileFirst == null) {
                        this.mProfileFirst = (IProfile) this.mProfiles.get(i);
                    } else if (i2 == 2 && this.mProfileSecond == null) {
                        this.mProfileSecond = (IProfile) this.mProfiles.get(i);
                    } else if (i2 == 3 && this.mProfileThird == null) {
                        this.mProfileThird = (IProfile) this.mProfiles.get(i);
                    }
                    i2++;
                }
                i++;
            }
            return;
        }
        IProfile[] iProfileArr = new IProfile[]{this.mCurrentProfile, this.mProfileFirst, this.mProfileSecond, this.mProfileThird};
        IProfile[] iProfileArr2 = new IProfile[4];
        Stack stack = new Stack();
        for (i2 = 0; i2 < this.mProfiles.size(); i2++) {
            IProfile iProfile = (IProfile) this.mProfiles.get(i2);
            if (iProfile.isSelectable()) {
                int i3;
                for (i3 = 0; i3 < 4; i3++) {
                    if (iProfileArr[i3] == iProfile) {
                        iProfileArr2[i3] = iProfile;
                        i3 = 1;
                        break;
                    }
                }
                i3 = 0;
                if (i3 == 0) {
                    stack.push(iProfile);
                }
            }
        }
        Stack stack2 = new Stack();
        while (i < 4) {
            if (iProfileArr2[i] != null) {
                stack2.push(iProfileArr2[i]);
            } else if (!stack.isEmpty()) {
                stack2.push(stack.pop());
            }
            i++;
        }
        Stack stack3 = new Stack();
        while (!stack2.empty()) {
            stack3.push(stack2.pop());
        }
        if (stack3.isEmpty()) {
            this.mCurrentProfile = null;
        } else {
            this.mCurrentProfile = (IProfile) stack3.pop();
        }
        if (stack3.isEmpty()) {
            this.mProfileFirst = null;
        } else {
            this.mProfileFirst = (IProfile) stack3.pop();
        }
        if (stack3.isEmpty()) {
            this.mProfileSecond = null;
        } else {
            this.mProfileSecond = (IProfile) stack3.pop();
        }
        if (stack3.isEmpty()) {
            this.mProfileThird = null;
        } else {
            this.mProfileThird = (IProfile) stack3.pop();
        }
    }

    protected boolean switchProfiles(IProfile iProfile) {
        boolean z = true;
        if (iProfile == null) {
            return false;
        }
        if (this.mCurrentProfile == iProfile) {
            return true;
        }
        if (this.mAlternativeProfileHeaderSwitching) {
            if (this.mProfileFirst == iProfile) {
                z = true;
            } else if (this.mProfileSecond == iProfile) {
                z = true;
            } else if (this.mProfileThird == iProfile) {
                z = true;
            }
            IProfile iProfile2 = this.mCurrentProfile;
            this.mCurrentProfile = iProfile;
            if (z) {
                this.mProfileFirst = iProfile2;
            } else if (z) {
                this.mProfileSecond = iProfile2;
            } else if (z) {
                this.mProfileThird = iProfile2;
            }
        } else if (this.mProfiles != null) {
            ArrayList arrayList = new ArrayList(Arrays.asList(new IProfile[]{this.mCurrentProfile, this.mProfileFirst, this.mProfileSecond, this.mProfileThird}));
            if (arrayList.contains(iProfile)) {
                int i = 0;
                while (i < 4) {
                    if (arrayList.get(i) == iProfile) {
                        break;
                    }
                    i++;
                }
                i = -1;
                if (i != -1) {
                    arrayList.remove(i);
                    arrayList.add(0, iProfile);
                    this.mCurrentProfile = (IProfile) arrayList.get(0);
                    this.mProfileFirst = (IProfile) arrayList.get(1);
                    this.mProfileSecond = (IProfile) arrayList.get(2);
                    this.mProfileThird = (IProfile) arrayList.get(3);
                }
            } else {
                this.mProfileThird = this.mProfileSecond;
                this.mProfileSecond = this.mProfileFirst;
                this.mProfileFirst = this.mCurrentProfile;
                this.mCurrentProfile = iProfile;
            }
        }
        if (this.mOnlySmallProfileImagesVisible) {
            this.mProfileThird = this.mProfileSecond;
            this.mProfileSecond = this.mProfileFirst;
            this.mProfileFirst = this.mCurrentProfile;
        }
        buildProfiles();
        return false;
    }

    protected void buildProfiles() {
        this.mCurrentProfileView.setVisibility(4);
        this.mAccountHeaderTextSection.setVisibility(4);
        this.mAccountSwitcherArrow.setVisibility(8);
        this.mProfileFirstView.setVisibility(8);
        this.mProfileFirstView.setOnClickListener(null);
        this.mProfileSecondView.setVisibility(8);
        this.mProfileSecondView.setOnClickListener(null);
        this.mProfileThirdView.setVisibility(8);
        this.mProfileThirdView.setOnClickListener(null);
        this.mCurrentProfileName.setText(BuildConfig.FLAVOR);
        this.mCurrentProfileEmail.setText(BuildConfig.FLAVOR);
        if (!this.mCompactStyle) {
            this.mAccountHeaderTextSection.setPadding(0, 0, (int) UIUtils.convertDpToPixel(56.0f, this.mAccountHeaderTextSection.getContext()), 0);
        }
        handleSelectionView(this.mCurrentProfile, true);
        if (this.mCurrentProfile != null) {
            if ((this.mProfileImagesVisible || this.mOnlyMainProfileImageVisible) && !this.mOnlySmallProfileImagesVisible) {
                setImageOrPlaceholder(this.mCurrentProfileView, this.mCurrentProfile.getIcon());
                if (this.mProfileImagesClickable) {
                    this.mCurrentProfileView.setOnClickListener(this.onCurrentProfileClickListener);
                    this.mCurrentProfileView.setOnLongClickListener(this.onCurrentProfileLongClickListener);
                    this.mCurrentProfileView.disableTouchFeedback(false);
                } else {
                    this.mCurrentProfileView.disableTouchFeedback(true);
                }
                this.mCurrentProfileView.setVisibility(0);
                this.mCurrentProfileView.invalidate();
            } else if (this.mCompactStyle) {
                this.mCurrentProfileView.setVisibility(8);
            }
            this.mAccountHeaderTextSection.setVisibility(0);
            handleSelectionView(this.mCurrentProfile, true);
            this.mAccountSwitcherArrow.setVisibility(0);
            this.mCurrentProfileView.setTag(R.id.material_drawer_profile_header, this.mCurrentProfile);
            StringHolder.applyTo(this.mCurrentProfile.getName(), this.mCurrentProfileName);
            StringHolder.applyTo(this.mCurrentProfile.getEmail(), this.mCurrentProfileEmail);
            if (!(this.mProfileFirst == null || !this.mProfileImagesVisible || this.mOnlyMainProfileImageVisible)) {
                setImageOrPlaceholder(this.mProfileFirstView, this.mProfileFirst.getIcon());
                this.mProfileFirstView.setTag(R.id.material_drawer_profile_header, this.mProfileFirst);
                if (this.mProfileImagesClickable) {
                    this.mProfileFirstView.setOnClickListener(this.onProfileClickListener);
                    this.mProfileFirstView.setOnLongClickListener(this.onProfileLongClickListener);
                    this.mProfileFirstView.disableTouchFeedback(false);
                } else {
                    this.mProfileFirstView.disableTouchFeedback(true);
                }
                this.mProfileFirstView.setVisibility(0);
                this.mProfileFirstView.invalidate();
            }
            if (!(this.mProfileSecond == null || !this.mProfileImagesVisible || this.mOnlyMainProfileImageVisible)) {
                setImageOrPlaceholder(this.mProfileSecondView, this.mProfileSecond.getIcon());
                this.mProfileSecondView.setTag(R.id.material_drawer_profile_header, this.mProfileSecond);
                if (this.mProfileImagesClickable) {
                    this.mProfileSecondView.setOnClickListener(this.onProfileClickListener);
                    this.mProfileSecondView.setOnLongClickListener(this.onProfileLongClickListener);
                    this.mProfileSecondView.disableTouchFeedback(false);
                } else {
                    this.mProfileSecondView.disableTouchFeedback(true);
                }
                this.mProfileSecondView.setVisibility(0);
                this.mProfileSecondView.invalidate();
            }
            if (this.mProfileThird != null && this.mThreeSmallProfileImages && this.mProfileImagesVisible && !this.mOnlyMainProfileImageVisible) {
                setImageOrPlaceholder(this.mProfileThirdView, this.mProfileThird.getIcon());
                this.mProfileThirdView.setTag(R.id.material_drawer_profile_header, this.mProfileThird);
                if (this.mProfileImagesClickable) {
                    this.mProfileThirdView.setOnClickListener(this.onProfileClickListener);
                    this.mProfileThirdView.setOnLongClickListener(this.onProfileLongClickListener);
                    this.mProfileThirdView.disableTouchFeedback(false);
                } else {
                    this.mProfileThirdView.disableTouchFeedback(true);
                }
                this.mProfileThirdView.setVisibility(0);
                this.mProfileThirdView.invalidate();
            }
        } else if (this.mProfiles != null && this.mProfiles.size() > 0) {
            this.mAccountHeaderTextSection.setTag(R.id.material_drawer_profile_header, (IProfile) this.mProfiles.get(0));
            this.mAccountHeaderTextSection.setVisibility(0);
            handleSelectionView(this.mCurrentProfile, true);
            this.mAccountSwitcherArrow.setVisibility(0);
            if (this.mCurrentProfile != null) {
                StringHolder.applyTo(this.mCurrentProfile.getName(), this.mCurrentProfileName);
                StringHolder.applyTo(this.mCurrentProfile.getEmail(), this.mCurrentProfileEmail);
            }
        }
        if (!this.mSelectionFirstLineShown) {
            this.mCurrentProfileName.setVisibility(8);
        }
        if (!TextUtils.isEmpty(this.mSelectionFirstLine)) {
            this.mCurrentProfileName.setText(this.mSelectionFirstLine);
            this.mAccountHeaderTextSection.setVisibility(0);
        }
        if (!this.mSelectionSecondLineShown) {
            this.mCurrentProfileEmail.setVisibility(8);
        }
        if (!TextUtils.isEmpty(this.mSelectionSecondLine)) {
            this.mCurrentProfileEmail.setText(this.mSelectionSecondLine);
            this.mAccountHeaderTextSection.setVisibility(0);
        }
        if (!this.mSelectionListEnabled || (!this.mSelectionListEnabledForSingleProfile && this.mProfileFirst == null && (this.mProfiles == null || this.mProfiles.size() == 1))) {
            this.mAccountSwitcherArrow.setVisibility(8);
            handleSelectionView(null, false);
            if (!this.mCompactStyle) {
                this.mAccountHeaderTextSection.setPadding(0, 0, (int) UIUtils.convertDpToPixel(16.0f, this.mAccountHeaderTextSection.getContext()), 0);
            }
        }
        if (this.mOnAccountHeaderSelectionViewClickListener != null) {
            handleSelectionView(this.mCurrentProfile, true);
        }
    }

    private void setImageOrPlaceholder(ImageView imageView, ImageHolder imageHolder) {
        DrawerImageLoader.getInstance().cancelImage(imageView);
        imageView.setImageDrawable(DrawerImageLoader.getInstance().getImageLoader().placeholder(imageView.getContext(), Tags.PROFILE.name()));
        ImageHolder.applyTo(imageHolder, imageView, Tags.PROFILE.name());
    }

    private void onProfileImageClick(View view, boolean z) {
        boolean onProfileImageClick;
        IProfile iProfile = (IProfile) view.getTag(R.id.material_drawer_profile_header);
        if (this.mOnAccountHeaderProfileImageListener != null) {
            onProfileImageClick = this.mOnAccountHeaderProfileImageListener.onProfileImageClick(view, iProfile, z);
        } else {
            onProfileImageClick = false;
        }
        if (!onProfileImageClick) {
            onProfileClick(view, z);
        }
    }

    protected void onProfileClick(View view, boolean z) {
        boolean onProfileChanged;
        IProfile iProfile = (IProfile) view.getTag(R.id.material_drawer_profile_header);
        switchProfiles(iProfile);
        resetDrawerContent(view.getContext());
        if (!(this.mDrawer == null || this.mDrawer.getDrawerBuilder() == null || this.mDrawer.getDrawerBuilder().mMiniDrawer == null)) {
            this.mDrawer.getDrawerBuilder().mMiniDrawer.onProfileClick();
        }
        if (this.mOnAccountHeaderListener != null) {
            onProfileChanged = this.mOnAccountHeaderListener.onProfileChanged(view, iProfile, z);
        } else {
            onProfileChanged = false;
        }
        if (!onProfileChanged) {
            if (this.mOnProfileClickDrawerCloseDelay > 0) {
                new Handler().postDelayed(new 5(this), (long) this.mOnProfileClickDrawerCloseDelay);
            } else if (this.mDrawer != null) {
                this.mDrawer.closeDrawer();
            }
        }
    }

    protected int getCurrentSelection() {
        if (!(this.mCurrentProfile == null || this.mProfiles == null)) {
            int i = 0;
            for (IProfile iProfile : this.mProfiles) {
                if (iProfile == this.mCurrentProfile) {
                    return i;
                }
                i++;
            }
        }
        return -1;
    }

    protected void toggleSelectionList(Context context) {
        if (this.mDrawer == null) {
            return;
        }
        if (this.mDrawer.switchedDrawerContent()) {
            resetDrawerContent(context);
            this.mSelectionListShown = false;
            return;
        }
        buildDrawerSelectionList();
        this.mAccountSwitcherArrow.clearAnimation();
        ViewCompat.animate(this.mAccountSwitcherArrow).rotation(180.0f).start();
        this.mSelectionListShown = true;
    }

    protected void buildDrawerSelectionList() {
        int i;
        List arrayList = new ArrayList();
        if (this.mProfiles != null) {
            int i2 = 0;
            i = -1;
            for (IProfile iProfile : this.mProfiles) {
                int i3;
                if (iProfile != this.mCurrentProfile) {
                    i3 = i;
                } else if (!this.mCurrentHiddenInList) {
                    i3 = this.mDrawer.mDrawerBuilder.getItemAdapter().getGlobalPosition(i2);
                }
                if (iProfile instanceof IDrawerItem) {
                    ((IDrawerItem) iProfile).withSetSelected(false);
                    arrayList.add((IDrawerItem) iProfile);
                }
                i2++;
                i = i3;
            }
        } else {
            i = -1;
        }
        this.mDrawer.switchDrawerContent(this.onDrawerItemClickListener, this.onDrawerItemLongClickListener, arrayList, i);
    }

    private void resetDrawerContent(Context context) {
        if (this.mDrawer != null) {
            this.mDrawer.resetDrawerContent();
        }
        this.mAccountSwitcherArrow.clearAnimation();
        ViewCompat.animate(this.mAccountSwitcherArrow).rotation(0.0f).start();
    }

    protected void updateHeaderAndList() {
        calculateProfiles();
        buildProfiles();
        if (this.mSelectionListShown) {
            buildDrawerSelectionList();
        }
    }
}
