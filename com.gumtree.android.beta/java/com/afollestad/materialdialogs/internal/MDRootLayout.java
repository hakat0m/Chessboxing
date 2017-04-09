package com.afollestad.materialdialogs.internal;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build.VERSION;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ScrollView;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog.NotImplementedException;
import com.afollestad.materialdialogs.R;
import com.afollestad.materialdialogs.util.DialogUtils;

public class MDRootLayout extends ViewGroup {
    private static final int INDEX_NEGATIVE = 1;
    private static final int INDEX_NEUTRAL = 0;
    private static final int INDEX_POSITIVE = 2;
    private OnScrollChangedListener mBottomOnScrollChangedListener;
    private int mButtonBarHeight;
    private GravityEnum mButtonGravity;
    private int mButtonHorizontalEdgeMargin;
    private int mButtonPaddingFull;
    private final MDButton[] mButtons;
    private View mContent;
    private Paint mDividerPaint;
    private int mDividerWidth;
    private boolean mDrawBottomDivider;
    private boolean mDrawTopDivider;
    private boolean mForceStack;
    private boolean mIsStacked;
    private boolean mNoTitleNoPadding;
    private int mNoTitlePaddingFull;
    private boolean mReducePaddingNoTitleNoButtons;
    private View mTitleBar;
    private OnScrollChangedListener mTopOnScrollChangedListener;
    private boolean mUseFullPadding;

    /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$afollestad$materialdialogs$GravityEnum = new int[GravityEnum.values().length];

        static {
            try {
                $SwitchMap$com$afollestad$materialdialogs$GravityEnum[GravityEnum.START.ordinal()] = MDRootLayout.INDEX_NEGATIVE;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$afollestad$materialdialogs$GravityEnum[GravityEnum.END.ordinal()] = MDRootLayout.INDEX_POSITIVE;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    public MDRootLayout(Context context) {
        super(context);
        this.mDrawTopDivider = false;
        this.mDrawBottomDivider = false;
        this.mButtons = new MDButton[3];
        this.mForceStack = false;
        this.mIsStacked = false;
        this.mUseFullPadding = true;
        this.mButtonGravity = GravityEnum.START;
        init(context, null, INDEX_NEUTRAL);
    }

    public MDRootLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDrawTopDivider = false;
        this.mDrawBottomDivider = false;
        this.mButtons = new MDButton[3];
        this.mForceStack = false;
        this.mIsStacked = false;
        this.mUseFullPadding = true;
        this.mButtonGravity = GravityEnum.START;
        init(context, attributeSet, INDEX_NEUTRAL);
    }

    @TargetApi(11)
    public MDRootLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mDrawTopDivider = false;
        this.mDrawBottomDivider = false;
        this.mButtons = new MDButton[3];
        this.mForceStack = false;
        this.mIsStacked = false;
        this.mUseFullPadding = true;
        this.mButtonGravity = GravityEnum.START;
        init(context, attributeSet, i);
    }

    @TargetApi(21)
    public MDRootLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mDrawTopDivider = false;
        this.mDrawBottomDivider = false;
        this.mButtons = new MDButton[3];
        this.mForceStack = false;
        this.mIsStacked = false;
        this.mUseFullPadding = true;
        this.mButtonGravity = GravityEnum.START;
        init(context, attributeSet, i);
    }

    private void init(Context context, AttributeSet attributeSet, int i) {
        Resources resources = context.getResources();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.MDRootLayout, i, INDEX_NEUTRAL);
        this.mReducePaddingNoTitleNoButtons = obtainStyledAttributes.getBoolean(R.styleable.MDRootLayout_md_reduce_padding_no_title_no_buttons, true);
        obtainStyledAttributes.recycle();
        this.mNoTitlePaddingFull = resources.getDimensionPixelSize(R.dimen.md_notitle_vertical_padding);
        this.mButtonPaddingFull = resources.getDimensionPixelSize(R.dimen.md_button_frame_vertical_padding);
        this.mButtonHorizontalEdgeMargin = resources.getDimensionPixelSize(R.dimen.md_button_padding_frame_side);
        this.mButtonBarHeight = resources.getDimensionPixelSize(R.dimen.md_button_height);
        this.mDividerPaint = new Paint();
        this.mDividerWidth = resources.getDimensionPixelSize(R.dimen.md_divider_height);
        this.mDividerPaint.setColor(DialogUtils.resolveColor(context, R.attr.md_divider_color));
        setWillNotDraw(false);
    }

    public void noTitleNoPadding() {
        this.mNoTitleNoPadding = true;
    }

    public void onFinishInflate() {
        super.onFinishInflate();
        for (int i = INDEX_NEUTRAL; i < getChildCount(); i += INDEX_NEGATIVE) {
            View childAt = getChildAt(i);
            if (childAt.getId() == R.id.titleFrame) {
                this.mTitleBar = childAt;
            } else if (childAt.getId() == R.id.buttonDefaultNeutral) {
                this.mButtons[INDEX_NEUTRAL] = (MDButton) childAt;
            } else if (childAt.getId() == R.id.buttonDefaultNegative) {
                this.mButtons[INDEX_NEGATIVE] = (MDButton) childAt;
            } else if (childAt.getId() == R.id.buttonDefaultPositive) {
                this.mButtons[INDEX_POSITIVE] = (MDButton) childAt;
            } else {
                this.mContent = childAt;
            }
        }
    }

    public void onMeasure(int i, int i2) {
        boolean z;
        int i3;
        MDButton[] mDButtonArr;
        int length;
        int i4;
        int i5;
        View view;
        int i6;
        int i7 = INDEX_NEUTRAL;
        int size = MeasureSpec.getSize(i);
        int size2 = MeasureSpec.getSize(i2);
        this.mUseFullPadding = true;
        if (this.mForceStack) {
            z = true;
            i3 = INDEX_NEUTRAL;
        } else {
            mDButtonArr = this.mButtons;
            length = mDButtonArr.length;
            i4 = INDEX_NEUTRAL;
            i3 = INDEX_NEUTRAL;
            for (i5 = INDEX_NEUTRAL; i5 < length; i5 += INDEX_NEGATIVE) {
                view = mDButtonArr[i5];
                if (view != null && isVisible(view)) {
                    view.setStacked(false, false);
                    measureChild(view, i, i2);
                    i4 += view.getMeasuredWidth();
                    i3 = true;
                }
            }
            z = i4 > size - (getContext().getResources().getDimensionPixelSize(R.dimen.md_neutral_button_margin) * INDEX_POSITIVE);
        }
        this.mIsStacked = z;
        if (z) {
            mDButtonArr = this.mButtons;
            length = mDButtonArr.length;
            i4 = INDEX_NEUTRAL;
            for (i5 = INDEX_NEUTRAL; i5 < length; i5 += INDEX_NEGATIVE) {
                view = mDButtonArr[i5];
                if (view != null && isVisible(view)) {
                    view.setStacked(true, false);
                    measureChild(view, i, i2);
                    i4 += view.getMeasuredHeight();
                    i3 = true;
                }
            }
            i6 = i3;
        } else {
            i4 = INDEX_NEUTRAL;
            i6 = i3;
        }
        if (i6 == 0) {
            i3 = (this.mButtonPaddingFull * INDEX_POSITIVE) + INDEX_NEUTRAL;
            i5 = size2;
            i4 = INDEX_NEUTRAL;
        } else if (this.mIsStacked) {
            i5 = size2 - i4;
            i3 = INDEX_NEUTRAL + (this.mButtonPaddingFull * INDEX_POSITIVE);
            i4 = (this.mButtonPaddingFull * INDEX_POSITIVE) + INDEX_NEUTRAL;
        } else {
            i5 = size2 - this.mButtonBarHeight;
            i3 = (this.mButtonPaddingFull * INDEX_POSITIVE) + INDEX_NEUTRAL;
            i4 = INDEX_NEUTRAL;
        }
        if (isVisible(this.mTitleBar)) {
            this.mTitleBar.measure(MeasureSpec.makeMeasureSpec(size, 1073741824), INDEX_NEUTRAL);
            i5 -= this.mTitleBar.getMeasuredHeight();
        } else if (!this.mNoTitleNoPadding) {
            i3 += this.mNoTitlePaddingFull;
        }
        if (isVisible(this.mContent)) {
            this.mContent.measure(MeasureSpec.makeMeasureSpec(size, 1073741824), MeasureSpec.makeMeasureSpec(i5 - i4, Integer.MIN_VALUE));
            if (this.mContent.getMeasuredHeight() > i5 - i3) {
                this.mUseFullPadding = false;
            } else if (this.mReducePaddingNoTitleNoButtons && !isVisible(this.mTitleBar) && i6 == 0) {
                this.mUseFullPadding = false;
                i7 = i5 - (i4 + this.mContent.getMeasuredHeight());
            } else {
                this.mUseFullPadding = true;
                i7 = i5 - (this.mContent.getMeasuredHeight() + i3);
            }
        } else {
            i7 = i5;
        }
        setMeasuredDimension(size, size2 - i7);
    }

    private static boolean isVisible(View view) {
        boolean z = (view == null || view.getVisibility() == 8) ? INDEX_NEUTRAL : true;
        if (z && (view instanceof MDButton)) {
            return ((MDButton) view).getText().toString().trim().length() > 0;
        } else {
            return z;
        }
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mContent != null) {
            int top;
            if (this.mDrawTopDivider) {
                top = this.mContent.getTop();
                canvas.drawRect(0.0f, (float) (top - this.mDividerWidth), (float) getMeasuredWidth(), (float) top, this.mDividerPaint);
            }
            if (this.mDrawBottomDivider) {
                top = this.mContent.getBottom();
                canvas.drawRect(0.0f, (float) top, (float) getMeasuredWidth(), (float) (top + this.mDividerWidth), this.mDividerPaint);
            }
        }
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (isVisible(this.mTitleBar)) {
            int measuredHeight = this.mTitleBar.getMeasuredHeight();
            this.mTitleBar.layout(i, i2, i3, i2 + measuredHeight);
            i2 += measuredHeight;
        } else if (!this.mNoTitleNoPadding && this.mUseFullPadding) {
            i2 += this.mNoTitlePaddingFull;
        }
        if (isVisible(this.mContent)) {
            this.mContent.layout(i, i2, i3, this.mContent.getMeasuredHeight() + i2);
        }
        int i5;
        if (this.mIsStacked) {
            measuredHeight = i4 - this.mButtonPaddingFull;
            MDButton[] mDButtonArr = this.mButtons;
            int length = mDButtonArr.length;
            for (i5 = INDEX_NEUTRAL; i5 < length; i5 += INDEX_NEGATIVE) {
                View view = mDButtonArr[i5];
                if (isVisible(view)) {
                    view.layout(i, measuredHeight - view.getMeasuredHeight(), i3, measuredHeight);
                    measuredHeight -= view.getMeasuredHeight();
                }
            }
        } else {
            int i6;
            if (this.mUseFullPadding) {
                i4 -= this.mButtonPaddingFull;
            }
            int i7 = i4 - this.mButtonBarHeight;
            int i8 = this.mButtonHorizontalEdgeMargin;
            if (isVisible(this.mButtons[INDEX_POSITIVE])) {
                if (this.mButtonGravity == GravityEnum.END) {
                    i5 = i + i8;
                    measuredHeight = this.mButtons[INDEX_POSITIVE].getMeasuredWidth() + i5;
                    i6 = -1;
                } else {
                    measuredHeight = i3 - i8;
                    i5 = measuredHeight - this.mButtons[INDEX_POSITIVE].getMeasuredWidth();
                    i6 = i5;
                }
                this.mButtons[INDEX_POSITIVE].layout(i5, i7, measuredHeight, i4);
                measuredHeight = this.mButtons[INDEX_POSITIVE].getMeasuredWidth() + i8;
            } else {
                i6 = -1;
                measuredHeight = i8;
            }
            if (isVisible(this.mButtons[INDEX_NEGATIVE])) {
                if (this.mButtonGravity == GravityEnum.END) {
                    i5 = i + measuredHeight;
                    measuredHeight = this.mButtons[INDEX_NEGATIVE].getMeasuredWidth() + i5;
                    i8 = -1;
                } else if (this.mButtonGravity == GravityEnum.START) {
                    measuredHeight = i3 - measuredHeight;
                    i5 = measuredHeight - this.mButtons[INDEX_NEGATIVE].getMeasuredWidth();
                    i8 = -1;
                } else {
                    i5 = i + this.mButtonHorizontalEdgeMargin;
                    measuredHeight = this.mButtons[INDEX_NEGATIVE].getMeasuredWidth() + i5;
                    i8 = measuredHeight;
                }
                this.mButtons[INDEX_NEGATIVE].layout(i5, i7, measuredHeight, i4);
            } else {
                i8 = -1;
            }
            if (isVisible(this.mButtons[INDEX_NEUTRAL])) {
                if (this.mButtonGravity == GravityEnum.END) {
                    i6 = i3 - this.mButtonHorizontalEdgeMargin;
                    i8 = i6 - this.mButtons[INDEX_NEUTRAL].getMeasuredWidth();
                } else if (this.mButtonGravity == GravityEnum.START) {
                    i8 = i + this.mButtonHorizontalEdgeMargin;
                    i6 = i8 + this.mButtons[INDEX_NEUTRAL].getMeasuredWidth();
                } else if (i8 == -1 && i6 != -1) {
                    i8 = i6 - this.mButtons[INDEX_NEUTRAL].getMeasuredWidth();
                } else if (i6 == -1 && i8 != -1) {
                    i6 = i8 + this.mButtons[INDEX_NEUTRAL].getMeasuredWidth();
                } else if (i6 == -1) {
                    i8 = ((i3 - i) / INDEX_POSITIVE) - (this.mButtons[INDEX_NEUTRAL].getMeasuredWidth() / INDEX_POSITIVE);
                    i6 = i8 + this.mButtons[INDEX_NEUTRAL].getMeasuredWidth();
                }
                this.mButtons[INDEX_NEUTRAL].layout(i8, i7, i6, i4);
            }
        }
        setUpDividersVisibility(this.mContent, true, true);
    }

    public void setForceStack(boolean z) {
        this.mForceStack = z;
        invalidate();
    }

    public void setDividerColor(int i) {
        this.mDividerPaint.setColor(i);
        invalidate();
    }

    public void setButtonGravity(GravityEnum gravityEnum) {
        this.mButtonGravity = gravityEnum;
        invertGravityIfNecessary();
    }

    private void invertGravityIfNecessary() {
        if (VERSION.SDK_INT >= 17 && getResources().getConfiguration().getLayoutDirection() == INDEX_NEGATIVE) {
            switch (AnonymousClass3.$SwitchMap$com$afollestad$materialdialogs$GravityEnum[this.mButtonGravity.ordinal()]) {
                case INDEX_NEGATIVE /*1*/:
                    this.mButtonGravity = GravityEnum.END;
                    return;
                case INDEX_POSITIVE /*2*/:
                    this.mButtonGravity = GravityEnum.START;
                    return;
                default:
                    return;
            }
        }
    }

    public void setButtonStackedGravity(GravityEnum gravityEnum) {
        MDButton[] mDButtonArr = this.mButtons;
        int length = mDButtonArr.length;
        for (int i = INDEX_NEUTRAL; i < length; i += INDEX_NEGATIVE) {
            MDButton mDButton = mDButtonArr[i];
            if (mDButton != null) {
                mDButton.setStackedGravity(gravityEnum);
            }
        }
    }

    private void setUpDividersVisibility(final View view, final boolean z, final boolean z2) {
        if (view != null) {
            if (view instanceof ScrollView) {
                ScrollView scrollView = (ScrollView) view;
                if (canScrollViewScroll(scrollView)) {
                    addScrollListener(scrollView, z, z2);
                    return;
                }
                if (z) {
                    this.mDrawTopDivider = false;
                }
                if (z2) {
                    this.mDrawBottomDivider = false;
                }
            } else if (view instanceof AdapterView) {
                AdapterView adapterView = (AdapterView) view;
                if (canAdapterViewScroll(adapterView)) {
                    addScrollListener(adapterView, z, z2);
                    return;
                }
                if (z) {
                    this.mDrawTopDivider = false;
                }
                if (z2) {
                    this.mDrawBottomDivider = false;
                }
            } else if (view instanceof WebView) {
                view.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
                    public boolean onPreDraw() {
                        if (view.getMeasuredHeight() != 0) {
                            if (MDRootLayout.canWebViewScroll((WebView) view)) {
                                MDRootLayout.this.addScrollListener((ViewGroup) view, z, z2);
                            } else {
                                if (z) {
                                    MDRootLayout.this.mDrawTopDivider = false;
                                }
                                if (z2) {
                                    MDRootLayout.this.mDrawBottomDivider = false;
                                }
                            }
                            view.getViewTreeObserver().removeOnPreDrawListener(this);
                        }
                        return true;
                    }
                });
            } else if (view instanceof RecyclerView) {
                boolean canRecyclerViewScroll = canRecyclerViewScroll((RecyclerView) view);
                if (z) {
                    this.mDrawTopDivider = canRecyclerViewScroll;
                }
                if (z2) {
                    this.mDrawBottomDivider = canRecyclerViewScroll;
                }
            } else if (view instanceof ViewGroup) {
                View topView = getTopView((ViewGroup) view);
                setUpDividersVisibility(topView, z, z2);
                View bottomView = getBottomView((ViewGroup) view);
                if (bottomView != topView) {
                    setUpDividersVisibility(bottomView, false, true);
                }
            }
        }
    }

    private void addScrollListener(final ViewGroup viewGroup, final boolean z, final boolean z2) {
        if ((!z2 && this.mTopOnScrollChangedListener == null) || (z2 && this.mBottomOnScrollChangedListener == null)) {
            OnScrollChangedListener anonymousClass2 = new OnScrollChangedListener() {
                public void onScrollChanged() {
                    boolean z;
                    MDButton[] access$400 = MDRootLayout.this.mButtons;
                    int length = access$400.length;
                    for (int i = MDRootLayout.INDEX_NEUTRAL; i < length; i += MDRootLayout.INDEX_NEGATIVE) {
                        MDButton mDButton = access$400[i];
                        if (mDButton != null && mDButton.getVisibility() != 8) {
                            z = true;
                            break;
                        }
                    }
                    z = false;
                    if (viewGroup instanceof WebView) {
                        MDRootLayout.this.invalidateDividersForWebView((WebView) viewGroup, z, z2, z);
                    } else {
                        MDRootLayout.this.invalidateDividersForScrollingView(viewGroup, z, z2, z);
                    }
                    MDRootLayout.this.invalidate();
                }
            };
            if (z2) {
                this.mBottomOnScrollChangedListener = anonymousClass2;
                viewGroup.getViewTreeObserver().addOnScrollChangedListener(this.mBottomOnScrollChangedListener);
            } else {
                this.mTopOnScrollChangedListener = anonymousClass2;
                viewGroup.getViewTreeObserver().addOnScrollChangedListener(this.mTopOnScrollChangedListener);
            }
            anonymousClass2.onScrollChanged();
        }
    }

    private void invalidateDividersForScrollingView(ViewGroup viewGroup, boolean z, boolean z2, boolean z3) {
        boolean z4 = true;
        if (z && viewGroup.getChildCount() > 0) {
            boolean z5 = (this.mTitleBar == null || this.mTitleBar.getVisibility() == 8 || viewGroup.getScrollY() + viewGroup.getPaddingTop() <= viewGroup.getChildAt(INDEX_NEUTRAL).getTop()) ? false : true;
            this.mDrawTopDivider = z5;
        }
        if (z2 && viewGroup.getChildCount() > 0) {
            if (!z3 || (viewGroup.getScrollY() + viewGroup.getHeight()) - viewGroup.getPaddingBottom() >= viewGroup.getChildAt(viewGroup.getChildCount() - 1).getBottom()) {
                z4 = false;
            }
            this.mDrawBottomDivider = z4;
        }
    }

    private void invalidateDividersForWebView(WebView webView, boolean z, boolean z2, boolean z3) {
        boolean z4 = true;
        if (z) {
            boolean z5 = (this.mTitleBar == null || this.mTitleBar.getVisibility() == 8 || webView.getScrollY() + webView.getPaddingTop() <= 0) ? false : true;
            this.mDrawTopDivider = z5;
        }
        if (z2) {
            if (!z3 || ((float) ((webView.getScrollY() + webView.getMeasuredHeight()) - webView.getPaddingBottom())) >= ((float) webView.getContentHeight()) * webView.getScale()) {
                z4 = false;
            }
            this.mDrawBottomDivider = z4;
        }
    }

    public static boolean canRecyclerViewScroll(RecyclerView recyclerView) {
        if (recyclerView == null || recyclerView.getAdapter() == null || recyclerView.getLayoutManager() == null) {
            return false;
        }
        LayoutManager layoutManager = recyclerView.getLayoutManager();
        int itemCount = recyclerView.getAdapter().getItemCount();
        if (layoutManager instanceof LinearLayoutManager) {
            int findLastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            if (findLastVisibleItemPosition == -1) {
                return false;
            }
            boolean z = (!(findLastVisibleItemPosition == itemCount + -1 ? INDEX_NEGATIVE : false) || (recyclerView.getChildCount() > 0 && recyclerView.getChildAt(recyclerView.getChildCount() - 1).getBottom() > recyclerView.getHeight() - recyclerView.getPaddingBottom())) ? INDEX_NEGATIVE : false;
            return z;
        }
        throw new NotImplementedException("Material Dialogs currently only supports LinearLayoutManager. Please report any new layout managers.");
    }

    private static boolean canScrollViewScroll(ScrollView scrollView) {
        if (scrollView.getChildCount() != 0 && (scrollView.getMeasuredHeight() - scrollView.getPaddingTop()) - scrollView.getPaddingBottom() < scrollView.getChildAt(INDEX_NEUTRAL).getMeasuredHeight()) {
            return true;
        }
        return false;
    }

    private static boolean canWebViewScroll(WebView webView) {
        return ((float) webView.getMeasuredHeight()) < ((float) webView.getContentHeight()) * webView.getScale();
    }

    private static boolean canAdapterViewScroll(AdapterView adapterView) {
        if (adapterView.getLastVisiblePosition() == -1) {
            return false;
        }
        boolean z = adapterView.getFirstVisiblePosition() == 0 ? true : INDEX_NEUTRAL;
        boolean z2 = adapterView.getLastVisiblePosition() == adapterView.getCount() + -1 ? true : INDEX_NEUTRAL;
        if (!z || !z2 || adapterView.getChildCount() <= 0 || adapterView.getChildAt(INDEX_NEUTRAL).getTop() < adapterView.getPaddingTop() || adapterView.getChildAt(adapterView.getChildCount() - 1).getBottom() > adapterView.getHeight() - adapterView.getPaddingBottom()) {
            return true;
        }
        return false;
    }

    @Nullable
    private static View getBottomView(ViewGroup viewGroup) {
        if (viewGroup == null || viewGroup.getChildCount() == 0) {
            return null;
        }
        for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = viewGroup.getChildAt(childCount);
            if (childAt.getVisibility() == 0 && childAt.getBottom() == viewGroup.getMeasuredHeight()) {
                return childAt;
            }
        }
        return null;
    }

    @Nullable
    private static View getTopView(ViewGroup viewGroup) {
        if (viewGroup == null || viewGroup.getChildCount() == 0) {
            return null;
        }
        for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = viewGroup.getChildAt(childCount);
            if (childAt.getVisibility() == 0 && childAt.getTop() == 0) {
                return childAt;
            }
        }
        return null;
    }
}
