package com.afollestad.materialdialogs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.DialogInterface.OnShowListener;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Handler;
import android.support.annotation.ArrayRes;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.UiThread;
import android.support.v4.content.res.ResourcesCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager.BadTokenException;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import com.afollestad.materialdialogs.internal.MDButton;
import com.afollestad.materialdialogs.internal.MDRootLayout;
import com.afollestad.materialdialogs.internal.MDTintHelper;
import com.afollestad.materialdialogs.internal.ThemeSingleton;
import com.afollestad.materialdialogs.util.DialogUtils;
import com.afollestad.materialdialogs.util.RippleHelper;
import com.afollestad.materialdialogs.util.TypefaceHelper;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration$Builder;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class MaterialDialog extends DialogBase implements OnClickListener, OnItemClickListener {
    protected TextView content;
    protected FrameLayout customViewFrame;
    protected ImageView icon;
    protected EditText input;
    protected TextView inputMinMax;
    protected ListType listType;
    protected ListView listView;
    protected final Builder mBuilder;
    private final Handler mHandler = new Handler();
    protected ProgressBar mProgress;
    protected TextView mProgressLabel;
    protected TextView mProgressMinMax;
    protected MDButton negativeButton;
    protected MDButton neutralButton;
    protected MDButton positiveButton;
    protected List<Integer> selectedIndicesList;
    protected TextView title;
    protected View titleFrame;

    public interface ListCallback {
        void onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence);
    }

    @Deprecated
    public abstract class ButtonCallback {
        @Deprecated
        public void onAny(MaterialDialog materialDialog) {
        }

        @Deprecated
        public void onPositive(MaterialDialog materialDialog) {
        }

        @Deprecated
        public void onNegative(MaterialDialog materialDialog) {
        }

        @Deprecated
        public void onNeutral(MaterialDialog materialDialog) {
        }

        protected final Object clone() throws CloneNotSupportedException {
            return super.clone();
        }

        public final boolean equals(Object obj) {
            return super.equals(obj);
        }

        protected final void finalize() throws Throwable {
            super.finalize();
        }

        public final int hashCode() {
            return super.hashCode();
        }

        public final String toString() {
            return super.toString();
        }
    }

    public interface ListCallbackMultiChoice {
        boolean onSelection(MaterialDialog materialDialog, Integer[] numArr, CharSequence[] charSequenceArr);
    }

    public interface ListCallbackSingleChoice {
        boolean onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence);
    }

    /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] $SwitchMap$com$afollestad$materialdialogs$DialogAction = new int[DialogAction.values().length];
        static final /* synthetic */ int[] $SwitchMap$com$afollestad$materialdialogs$MaterialDialog$ListType = new int[ListType.values().length];

        static {
            try {
                $SwitchMap$com$afollestad$materialdialogs$MaterialDialog$ListType[ListType.REGULAR.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$afollestad$materialdialogs$MaterialDialog$ListType[ListType.SINGLE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$afollestad$materialdialogs$MaterialDialog$ListType[ListType.MULTI.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$afollestad$materialdialogs$DialogAction[DialogAction.NEUTRAL.ordinal()] = 1;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$afollestad$materialdialogs$DialogAction[DialogAction.NEGATIVE.ordinal()] = 2;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$afollestad$materialdialogs$DialogAction[DialogAction.POSITIVE.ordinal()] = 3;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    public class Builder {
        protected ListAdapter adapter;
        protected boolean alwaysCallInputCallback;
        protected boolean alwaysCallMultiChoiceCallback = false;
        protected boolean alwaysCallSingleChoiceCallback = false;
        protected boolean autoDismiss = true;
        protected int backgroundColor;
        @DrawableRes
        protected int btnSelectorNegative;
        @DrawableRes
        protected int btnSelectorNeutral;
        @DrawableRes
        protected int btnSelectorPositive;
        @DrawableRes
        protected int btnSelectorStacked;
        protected GravityEnum btnStackedGravity = GravityEnum.END;
        protected int buttonRippleColor = 0;
        protected GravityEnum buttonsGravity = GravityEnum.START;
        protected ButtonCallback callback;
        protected OnCancelListener cancelListener;
        protected boolean cancelable = true;
        protected boolean canceledOnTouchOutside = true;
        protected CharSequence content;
        protected int contentColor = -1;
        protected boolean contentColorSet = false;
        protected GravityEnum contentGravity = GravityEnum.START;
        protected float contentLineSpacingMultiplier = 1.2f;
        protected final Context context;
        protected View customView;
        protected OnDismissListener dismissListener;
        protected int dividerColor;
        protected boolean dividerColorSet = false;
        protected boolean forceStacking;
        protected Drawable icon;
        protected boolean indeterminateIsHorizontalProgress;
        protected boolean indeterminateProgress;
        protected boolean inputAllowEmpty;
        protected InputCallback inputCallback;
        protected CharSequence inputHint;
        protected int inputMaxLength = -1;
        protected int inputMinLength = -1;
        protected CharSequence inputPrefill;
        protected int inputRangeErrorColor = 0;
        protected int inputType = -1;
        protected int itemColor;
        protected boolean itemColorSet = false;
        protected int[] itemIds;
        protected CharSequence[] items;
        protected GravityEnum itemsGravity = GravityEnum.START;
        protected OnKeyListener keyListener;
        protected boolean limitIconToDefaultSize;
        protected ColorStateList linkColor;
        protected ListCallback listCallback;
        protected ListCallback listCallbackCustom;
        protected ListCallbackMultiChoice listCallbackMultiChoice;
        protected ListCallbackSingleChoice listCallbackSingleChoice;
        @DrawableRes
        protected int listSelector;
        protected int maxIconSize = -1;
        protected Typeface mediumFont;
        protected ColorStateList negativeColor;
        protected boolean negativeColorSet = false;
        protected CharSequence negativeText;
        protected ColorStateList neutralColor;
        protected boolean neutralColorSet = false;
        protected CharSequence neutralText;
        protected SingleButtonCallback onAnyCallback;
        protected SingleButtonCallback onNegativeCallback;
        protected SingleButtonCallback onNeutralCallback;
        protected SingleButtonCallback onPositiveCallback;
        protected ColorStateList positiveColor;
        protected boolean positiveColorSet = false;
        protected CharSequence positiveText;
        protected int progress = -2;
        protected int progressMax = 0;
        protected String progressNumberFormat;
        protected NumberFormat progressPercentFormat;
        protected Typeface regularFont;
        protected int selectedIndex = -1;
        protected Integer[] selectedIndices = null;
        protected OnShowListener showListener;
        protected boolean showMinMax;
        protected Theme theme = Theme.LIGHT;
        protected CharSequence title;
        protected int titleColor = -1;
        protected boolean titleColorSet = false;
        protected GravityEnum titleGravity = GravityEnum.START;
        protected int widgetColor;
        protected boolean widgetColorSet = false;
        protected boolean wrapCustomViewInScroll;

        public final Context getContext() {
            return this.context;
        }

        public final int getItemColor() {
            return this.itemColor;
        }

        public final Typeface getRegularFont() {
            return this.regularFont;
        }

        public Builder(@NonNull Context context) {
            int i = 0;
            this.context = context;
            this.widgetColor = DialogUtils.resolveColor(context, R.attr.colorAccent, DialogUtils.getColor(context, R.color.md_material_blue_600));
            if (VERSION.SDK_INT >= 21) {
                this.widgetColor = DialogUtils.resolveColor(context, 16843829, this.widgetColor);
            }
            this.positiveColor = DialogUtils.getActionTextStateList(context, this.widgetColor);
            this.negativeColor = DialogUtils.getActionTextStateList(context, this.widgetColor);
            this.neutralColor = DialogUtils.getActionTextStateList(context, this.widgetColor);
            this.linkColor = DialogUtils.getActionTextStateList(context, DialogUtils.resolveColor(context, R.attr.md_link_color, this.widgetColor));
            if (VERSION.SDK_INT >= 21) {
                i = DialogUtils.resolveColor(context, 16843820);
            }
            this.buttonRippleColor = DialogUtils.resolveColor(context, R.attr.md_btn_ripple_color, DialogUtils.resolveColor(context, R.attr.colorControlHighlight, i));
            this.progressPercentFormat = NumberFormat.getPercentInstance();
            this.progressNumberFormat = "%1d/%2d";
            this.theme = DialogUtils.isColorDark(DialogUtils.resolveColor(context, 16842806)) ? Theme.LIGHT : Theme.DARK;
            checkSingleton();
            this.titleGravity = DialogUtils.resolveGravityEnum(context, R.attr.md_title_gravity, this.titleGravity);
            this.contentGravity = DialogUtils.resolveGravityEnum(context, R.attr.md_content_gravity, this.contentGravity);
            this.btnStackedGravity = DialogUtils.resolveGravityEnum(context, R.attr.md_btnstacked_gravity, this.btnStackedGravity);
            this.itemsGravity = DialogUtils.resolveGravityEnum(context, R.attr.md_items_gravity, this.itemsGravity);
            this.buttonsGravity = DialogUtils.resolveGravityEnum(context, R.attr.md_buttons_gravity, this.buttonsGravity);
            typeface(DialogUtils.resolveString(context, R.attr.md_medium_font), DialogUtils.resolveString(context, R.attr.md_regular_font));
            if (this.mediumFont == null) {
                try {
                    if (VERSION.SDK_INT >= 21) {
                        this.mediumFont = Typeface.create("sans-serif-medium", 0);
                    } else {
                        this.mediumFont = Typeface.create("sans-serif", 1);
                    }
                } catch (Exception e) {
                }
            }
            if (this.regularFont == null) {
                try {
                    this.regularFont = Typeface.create("sans-serif", 0);
                } catch (Exception e2) {
                }
            }
        }

        private void checkSingleton() {
            if (ThemeSingleton.get(false) != null) {
                ThemeSingleton themeSingleton = ThemeSingleton.get();
                if (themeSingleton.darkTheme) {
                    this.theme = Theme.DARK;
                }
                if (themeSingleton.titleColor != 0) {
                    this.titleColor = themeSingleton.titleColor;
                }
                if (themeSingleton.contentColor != 0) {
                    this.contentColor = themeSingleton.contentColor;
                }
                if (themeSingleton.positiveColor != null) {
                    this.positiveColor = themeSingleton.positiveColor;
                }
                if (themeSingleton.neutralColor != null) {
                    this.neutralColor = themeSingleton.neutralColor;
                }
                if (themeSingleton.negativeColor != null) {
                    this.negativeColor = themeSingleton.negativeColor;
                }
                if (themeSingleton.itemColor != 0) {
                    this.itemColor = themeSingleton.itemColor;
                }
                if (themeSingleton.icon != null) {
                    this.icon = themeSingleton.icon;
                }
                if (themeSingleton.backgroundColor != 0) {
                    this.backgroundColor = themeSingleton.backgroundColor;
                }
                if (themeSingleton.dividerColor != 0) {
                    this.dividerColor = themeSingleton.dividerColor;
                }
                if (themeSingleton.btnSelectorStacked != 0) {
                    this.btnSelectorStacked = themeSingleton.btnSelectorStacked;
                }
                if (themeSingleton.listSelector != 0) {
                    this.listSelector = themeSingleton.listSelector;
                }
                if (themeSingleton.btnSelectorPositive != 0) {
                    this.btnSelectorPositive = themeSingleton.btnSelectorPositive;
                }
                if (themeSingleton.btnSelectorNeutral != 0) {
                    this.btnSelectorNeutral = themeSingleton.btnSelectorNeutral;
                }
                if (themeSingleton.btnSelectorNegative != 0) {
                    this.btnSelectorNegative = themeSingleton.btnSelectorNegative;
                }
                if (themeSingleton.widgetColor != 0) {
                    this.widgetColor = themeSingleton.widgetColor;
                }
                if (themeSingleton.linkColor != null) {
                    this.linkColor = themeSingleton.linkColor;
                }
                this.titleGravity = themeSingleton.titleGravity;
                this.contentGravity = themeSingleton.contentGravity;
                this.btnStackedGravity = themeSingleton.btnStackedGravity;
                this.itemsGravity = themeSingleton.itemsGravity;
                this.buttonsGravity = themeSingleton.buttonsGravity;
            }
        }

        public Builder title(@StringRes int i) {
            title(this.context.getText(i));
            return this;
        }

        public Builder title(@NonNull CharSequence charSequence) {
            this.title = charSequence;
            return this;
        }

        public Builder titleGravity(@NonNull GravityEnum gravityEnum) {
            this.titleGravity = gravityEnum;
            return this;
        }

        public Builder buttonRippleColor(@ColorInt int i) {
            this.buttonRippleColor = i;
            return this;
        }

        public Builder buttonRippleColorRes(@ColorRes int i) {
            return buttonRippleColor(DialogUtils.getColor(this.context, i));
        }

        public Builder buttonRippleColorAttr(@AttrRes int i) {
            return buttonRippleColor(DialogUtils.resolveColor(this.context, i));
        }

        public Builder titleColor(@ColorInt int i) {
            this.titleColor = i;
            this.titleColorSet = true;
            return this;
        }

        public Builder titleColorRes(@ColorRes int i) {
            return titleColor(DialogUtils.getColor(this.context, i));
        }

        public Builder titleColorAttr(@AttrRes int i) {
            return titleColor(DialogUtils.resolveColor(this.context, i));
        }

        public Builder typeface(@Nullable Typeface typeface, @Nullable Typeface typeface2) {
            this.mediumFont = typeface;
            this.regularFont = typeface2;
            return this;
        }

        public Builder typeface(@Nullable String str, @Nullable String str2) {
            if (str != null) {
                this.mediumFont = TypefaceHelper.get(this.context, str);
                if (this.mediumFont == null) {
                    throw new IllegalArgumentException("No font asset found for " + str);
                }
            }
            if (str2 != null) {
                this.regularFont = TypefaceHelper.get(this.context, str2);
                if (this.regularFont == null) {
                    throw new IllegalArgumentException("No font asset found for " + str2);
                }
            }
            return this;
        }

        public Builder icon(@NonNull Drawable drawable) {
            this.icon = drawable;
            return this;
        }

        public Builder iconRes(@DrawableRes int i) {
            this.icon = ResourcesCompat.getDrawable(this.context.getResources(), i, null);
            return this;
        }

        public Builder iconAttr(@AttrRes int i) {
            this.icon = DialogUtils.resolveDrawable(this.context, i);
            return this;
        }

        public Builder content(@StringRes int i) {
            content(this.context.getText(i));
            return this;
        }

        public Builder content(@NonNull CharSequence charSequence) {
            if (this.customView != null) {
                throw new IllegalStateException("You cannot set content() when you're using a custom view.");
            }
            this.content = charSequence;
            return this;
        }

        public Builder content(@StringRes int i, Object... objArr) {
            content(this.context.getString(i, objArr));
            return this;
        }

        public Builder contentColor(@ColorInt int i) {
            this.contentColor = i;
            this.contentColorSet = true;
            return this;
        }

        public Builder contentColorRes(@ColorRes int i) {
            contentColor(DialogUtils.getColor(this.context, i));
            return this;
        }

        public Builder contentColorAttr(@AttrRes int i) {
            contentColor(DialogUtils.resolveColor(this.context, i));
            return this;
        }

        public Builder contentGravity(@NonNull GravityEnum gravityEnum) {
            this.contentGravity = gravityEnum;
            return this;
        }

        public Builder contentLineSpacing(float f) {
            this.contentLineSpacingMultiplier = f;
            return this;
        }

        public Builder items(@NonNull Collection collection) {
            if (collection.size() > 0) {
                CharSequence[] charSequenceArr = new String[collection.size()];
                int i = 0;
                for (Object obj : collection) {
                    charSequenceArr[i] = obj.toString();
                    i++;
                }
                items(charSequenceArr);
            }
            return this;
        }

        public Builder items(@ArrayRes int i) {
            items(this.context.getResources().getTextArray(i));
            return this;
        }

        public Builder items(@NonNull CharSequence... charSequenceArr) {
            if (this.customView != null) {
                throw new IllegalStateException("You cannot set items() when you're using a custom view.");
            }
            this.items = charSequenceArr;
            return this;
        }

        public Builder itemsCallback(@NonNull ListCallback listCallback) {
            this.listCallback = listCallback;
            this.listCallbackSingleChoice = null;
            this.listCallbackMultiChoice = null;
            return this;
        }

        public Builder itemsColor(@ColorInt int i) {
            this.itemColor = i;
            this.itemColorSet = true;
            return this;
        }

        @Deprecated
        public Builder itemColor(@ColorInt int i) {
            return itemsColor(i);
        }

        public Builder itemsColorRes(@ColorRes int i) {
            return itemsColor(DialogUtils.getColor(this.context, i));
        }

        @Deprecated
        public Builder itemColorRes(@ColorRes int i) {
            return itemsColorRes(i);
        }

        public Builder itemsColorAttr(@AttrRes int i) {
            return itemsColor(DialogUtils.resolveColor(this.context, i));
        }

        @Deprecated
        public Builder itemColorAttr(@AttrRes int i) {
            return itemsColorAttr(i);
        }

        public Builder itemsGravity(@NonNull GravityEnum gravityEnum) {
            this.itemsGravity = gravityEnum;
            return this;
        }

        public Builder itemsIds(@NonNull int[] iArr) {
            this.itemIds = iArr;
            return this;
        }

        public Builder itemsIds(@ArrayRes int i) {
            return itemsIds(this.context.getResources().getIntArray(i));
        }

        public Builder buttonsGravity(@NonNull GravityEnum gravityEnum) {
            this.buttonsGravity = gravityEnum;
            return this;
        }

        public Builder itemsCallbackSingleChoice(int i, @NonNull ListCallbackSingleChoice listCallbackSingleChoice) {
            this.selectedIndex = i;
            this.listCallback = null;
            this.listCallbackSingleChoice = listCallbackSingleChoice;
            this.listCallbackMultiChoice = null;
            return this;
        }

        public Builder alwaysCallSingleChoiceCallback() {
            this.alwaysCallSingleChoiceCallback = true;
            return this;
        }

        public Builder itemsCallbackMultiChoice(@Nullable Integer[] numArr, @NonNull ListCallbackMultiChoice listCallbackMultiChoice) {
            this.selectedIndices = numArr;
            this.listCallback = null;
            this.listCallbackSingleChoice = null;
            this.listCallbackMultiChoice = listCallbackMultiChoice;
            return this;
        }

        public Builder alwaysCallMultiChoiceCallback() {
            this.alwaysCallMultiChoiceCallback = true;
            return this;
        }

        public Builder positiveText(@StringRes int i) {
            if (i != 0) {
                positiveText(this.context.getText(i));
            }
            return this;
        }

        public Builder positiveText(@NonNull CharSequence charSequence) {
            this.positiveText = charSequence;
            return this;
        }

        public Builder positiveColor(@ColorInt int i) {
            return positiveColor(DialogUtils.getActionTextStateList(this.context, i));
        }

        public Builder positiveColorRes(@ColorRes int i) {
            return positiveColor(DialogUtils.getActionTextColorStateList(this.context, i));
        }

        public Builder positiveColorAttr(@AttrRes int i) {
            return positiveColor(DialogUtils.resolveActionTextColorStateList(this.context, i, null));
        }

        public Builder positiveColor(@NonNull ColorStateList colorStateList) {
            this.positiveColor = colorStateList;
            this.positiveColorSet = true;
            return this;
        }

        public Builder neutralText(@StringRes int i) {
            return i == 0 ? this : neutralText(this.context.getText(i));
        }

        public Builder neutralText(@NonNull CharSequence charSequence) {
            this.neutralText = charSequence;
            return this;
        }

        public Builder negativeColor(@ColorInt int i) {
            return negativeColor(DialogUtils.getActionTextStateList(this.context, i));
        }

        public Builder negativeColorRes(@ColorRes int i) {
            return negativeColor(DialogUtils.getActionTextColorStateList(this.context, i));
        }

        public Builder negativeColorAttr(@AttrRes int i) {
            return negativeColor(DialogUtils.resolveActionTextColorStateList(this.context, i, null));
        }

        public Builder negativeColor(@NonNull ColorStateList colorStateList) {
            this.negativeColor = colorStateList;
            this.negativeColorSet = true;
            return this;
        }

        public Builder negativeText(@StringRes int i) {
            return i == 0 ? this : negativeText(this.context.getText(i));
        }

        public Builder negativeText(@NonNull CharSequence charSequence) {
            this.negativeText = charSequence;
            return this;
        }

        public Builder neutralColor(@ColorInt int i) {
            return neutralColor(DialogUtils.getActionTextStateList(this.context, i));
        }

        public Builder neutralColorRes(@ColorRes int i) {
            return neutralColor(DialogUtils.getActionTextColorStateList(this.context, i));
        }

        public Builder neutralColorAttr(@AttrRes int i) {
            return neutralColor(DialogUtils.resolveActionTextColorStateList(this.context, i, null));
        }

        public Builder neutralColor(@NonNull ColorStateList colorStateList) {
            this.neutralColor = colorStateList;
            this.neutralColorSet = true;
            return this;
        }

        public Builder linkColor(@ColorInt int i) {
            return linkColor(DialogUtils.getActionTextStateList(this.context, i));
        }

        public Builder linkColorRes(@ColorRes int i) {
            return linkColor(DialogUtils.getActionTextColorStateList(this.context, i));
        }

        public Builder linkColorAttr(@AttrRes int i) {
            return linkColor(DialogUtils.resolveActionTextColorStateList(this.context, i, null));
        }

        public Builder linkColor(@NonNull ColorStateList colorStateList) {
            this.linkColor = colorStateList;
            return this;
        }

        public Builder listSelector(@DrawableRes int i) {
            this.listSelector = i;
            return this;
        }

        public Builder btnSelectorStacked(@DrawableRes int i) {
            this.btnSelectorStacked = i;
            return this;
        }

        public Builder btnSelector(@DrawableRes int i) {
            this.btnSelectorPositive = i;
            this.btnSelectorNeutral = i;
            this.btnSelectorNegative = i;
            return this;
        }

        public Builder btnSelector(@DrawableRes int i, @NonNull DialogAction dialogAction) {
            switch (AnonymousClass4.$SwitchMap$com$afollestad$materialdialogs$DialogAction[dialogAction.ordinal()]) {
                case HighlightView.GROW_NONE /*1*/:
                    this.btnSelectorNeutral = i;
                    break;
                case HighlightView.GROW_LEFT_EDGE /*2*/:
                    this.btnSelectorNegative = i;
                    break;
                default:
                    this.btnSelectorPositive = i;
                    break;
            }
            return this;
        }

        public Builder btnStackedGravity(@NonNull GravityEnum gravityEnum) {
            this.btnStackedGravity = gravityEnum;
            return this;
        }

        public Builder customView(@LayoutRes int i, boolean z) {
            return customView(LayoutInflater.from(this.context).inflate(i, null), z);
        }

        public Builder customView(@NonNull View view, boolean z) {
            if (this.content != null) {
                throw new IllegalStateException("You cannot use customView() when you have content set.");
            } else if (this.items != null) {
                throw new IllegalStateException("You cannot use customView() when you have items set.");
            } else if (this.inputCallback != null) {
                throw new IllegalStateException("You cannot use customView() with an input dialog");
            } else if (this.progress > -2 || this.indeterminateProgress) {
                throw new IllegalStateException("You cannot use customView() with a progress dialog");
            } else {
                if (view.getParent() != null && (view.getParent() instanceof ViewGroup)) {
                    ((ViewGroup) view.getParent()).removeView(view);
                }
                this.customView = view;
                this.wrapCustomViewInScroll = z;
                return this;
            }
        }

        public Builder progress(boolean z, int i) {
            if (this.customView != null) {
                throw new IllegalStateException("You cannot set progress() when you're using a custom view.");
            }
            if (z) {
                this.indeterminateProgress = true;
                this.progress = -2;
            } else {
                this.indeterminateProgress = false;
                this.progress = -1;
                this.progressMax = i;
            }
            return this;
        }

        public Builder progress(boolean z, int i, boolean z2) {
            this.showMinMax = z2;
            return progress(z, i);
        }

        public Builder progressNumberFormat(@NonNull String str) {
            this.progressNumberFormat = str;
            return this;
        }

        public Builder progressPercentFormat(@NonNull NumberFormat numberFormat) {
            this.progressPercentFormat = numberFormat;
            return this;
        }

        public Builder progressIndeterminateStyle(boolean z) {
            this.indeterminateIsHorizontalProgress = z;
            return this;
        }

        public Builder widgetColor(@ColorInt int i) {
            this.widgetColor = i;
            this.widgetColorSet = true;
            return this;
        }

        public Builder widgetColorRes(@ColorRes int i) {
            return widgetColor(DialogUtils.getColor(this.context, i));
        }

        public Builder widgetColorAttr(@AttrRes int i) {
            return widgetColorRes(DialogUtils.resolveColor(this.context, i));
        }

        public Builder dividerColor(@ColorInt int i) {
            this.dividerColor = i;
            this.dividerColorSet = true;
            return this;
        }

        public Builder dividerColorRes(@ColorRes int i) {
            return dividerColor(DialogUtils.getColor(this.context, i));
        }

        public Builder dividerColorAttr(@AttrRes int i) {
            return dividerColor(DialogUtils.resolveColor(this.context, i));
        }

        public Builder backgroundColor(@ColorInt int i) {
            this.backgroundColor = i;
            return this;
        }

        public Builder backgroundColorRes(@ColorRes int i) {
            return backgroundColor(DialogUtils.getColor(this.context, i));
        }

        public Builder backgroundColorAttr(@AttrRes int i) {
            return backgroundColor(DialogUtils.resolveColor(this.context, i));
        }

        public Builder callback(@NonNull ButtonCallback buttonCallback) {
            this.callback = buttonCallback;
            return this;
        }

        public Builder onPositive(@NonNull SingleButtonCallback singleButtonCallback) {
            this.onPositiveCallback = singleButtonCallback;
            return this;
        }

        public Builder onNegative(@NonNull SingleButtonCallback singleButtonCallback) {
            this.onNegativeCallback = singleButtonCallback;
            return this;
        }

        public Builder onNeutral(@NonNull SingleButtonCallback singleButtonCallback) {
            this.onNeutralCallback = singleButtonCallback;
            return this;
        }

        public Builder onAny(@NonNull SingleButtonCallback singleButtonCallback) {
            this.onAnyCallback = singleButtonCallback;
            return this;
        }

        public Builder theme(@NonNull Theme theme) {
            this.theme = theme;
            return this;
        }

        public Builder cancelable(boolean z) {
            this.cancelable = z;
            this.canceledOnTouchOutside = z;
            return this;
        }

        public Builder canceledOnTouchOutside(boolean z) {
            this.canceledOnTouchOutside = z;
            return this;
        }

        public Builder autoDismiss(boolean z) {
            this.autoDismiss = z;
            return this;
        }

        public Builder adapter(@NonNull ListAdapter listAdapter, @Nullable ListCallback listCallback) {
            if (this.customView != null) {
                throw new IllegalStateException("You cannot set adapter() when you're using a custom view.");
            }
            this.adapter = listAdapter;
            this.listCallbackCustom = listCallback;
            return this;
        }

        public Builder limitIconToDefaultSize() {
            this.limitIconToDefaultSize = true;
            return this;
        }

        public Builder maxIconSize(int i) {
            this.maxIconSize = i;
            return this;
        }

        public Builder maxIconSizeRes(@DimenRes int i) {
            return maxIconSize((int) this.context.getResources().getDimension(i));
        }

        public Builder showListener(@NonNull OnShowListener onShowListener) {
            this.showListener = onShowListener;
            return this;
        }

        public Builder dismissListener(@NonNull OnDismissListener onDismissListener) {
            this.dismissListener = onDismissListener;
            return this;
        }

        public Builder cancelListener(@NonNull OnCancelListener onCancelListener) {
            this.cancelListener = onCancelListener;
            return this;
        }

        public Builder keyListener(@NonNull OnKeyListener onKeyListener) {
            this.keyListener = onKeyListener;
            return this;
        }

        public Builder forceStacking(boolean z) {
            this.forceStacking = z;
            return this;
        }

        public Builder input(@Nullable CharSequence charSequence, @Nullable CharSequence charSequence2, boolean z, @NonNull InputCallback inputCallback) {
            if (this.customView != null) {
                throw new IllegalStateException("You cannot set content() when you're using a custom view.");
            }
            this.inputCallback = inputCallback;
            this.inputHint = charSequence;
            this.inputPrefill = charSequence2;
            this.inputAllowEmpty = z;
            return this;
        }

        public Builder input(@Nullable CharSequence charSequence, @Nullable CharSequence charSequence2, @NonNull InputCallback inputCallback) {
            return input(charSequence, charSequence2, true, inputCallback);
        }

        public Builder input(@StringRes int i, @StringRes int i2, boolean z, @NonNull InputCallback inputCallback) {
            CharSequence charSequence = null;
            CharSequence text = i == 0 ? null : this.context.getText(i);
            if (i2 != 0) {
                charSequence = this.context.getText(i2);
            }
            return input(text, charSequence, z, inputCallback);
        }

        public Builder input(@StringRes int i, @StringRes int i2, @NonNull InputCallback inputCallback) {
            return input(i, i2, true, inputCallback);
        }

        public Builder inputType(int i) {
            this.inputType = i;
            return this;
        }

        @Deprecated
        public Builder inputMaxLength(@IntRange(from = 1, to = 2147483647L) int i) {
            return inputRange(0, i, 0);
        }

        @Deprecated
        public Builder inputMaxLength(@IntRange(from = 1, to = 2147483647L) int i, @ColorInt int i2) {
            return inputRange(0, i, i2);
        }

        @Deprecated
        public Builder inputMaxLengthRes(@IntRange(from = 1, to = 2147483647L) int i, @ColorRes int i2) {
            return inputRangeRes(0, i, i2);
        }

        public Builder inputRange(@IntRange(from = 0, to = 2147483647L) int i, @IntRange(from = -1, to = 2147483647L) int i2) {
            return inputRange(i, i2, 0);
        }

        public Builder inputRange(@IntRange(from = 0, to = 2147483647L) int i, @IntRange(from = -1, to = 2147483647L) int i2, @ColorInt int i3) {
            if (i < 0) {
                throw new IllegalArgumentException("Min length for input dialogs cannot be less than 0.");
            }
            this.inputMinLength = i;
            this.inputMaxLength = i2;
            if (i3 == 0) {
                this.inputRangeErrorColor = DialogUtils.getColor(this.context, R.color.md_edittext_error);
            } else {
                this.inputRangeErrorColor = i3;
            }
            return this;
        }

        public Builder inputRangeRes(@IntRange(from = 0, to = 2147483647L) int i, @IntRange(from = 1, to = 2147483647L) int i2, @ColorRes int i3) {
            return inputRange(i, i2, DialogUtils.getColor(this.context, i3));
        }

        public Builder alwaysCallInputCallback() {
            this.alwaysCallInputCallback = true;
            return this;
        }

        @UiThread
        public MaterialDialog build() {
            return new MaterialDialog(this);
        }

        @UiThread
        public MaterialDialog show() {
            MaterialDialog build = build();
            build.show();
            return build;
        }
    }

    public class DialogException extends BadTokenException {
        public DialogException(String str) {
            super(str);
        }
    }

    public interface InputCallback {
        void onInput(@NonNull MaterialDialog materialDialog, CharSequence charSequence);
    }

    public enum ListType {
        REGULAR,
        SINGLE,
        MULTI;

        public static int getLayoutForType(ListType listType) {
            switch (AnonymousClass4.$SwitchMap$com$afollestad$materialdialogs$MaterialDialog$ListType[listType.ordinal()]) {
                case HighlightView.GROW_NONE /*1*/:
                    return R.layout.md_listitem;
                case HighlightView.GROW_LEFT_EDGE /*2*/:
                    return R.layout.md_listitem_singlechoice;
                case ImageLoaderConfiguration$Builder.DEFAULT_THREAD_POOL_SIZE /*3*/:
                    return R.layout.md_listitem_multichoice;
                default:
                    throw new IllegalArgumentException("Not a valid list type");
            }
        }
    }

    public class NotImplementedException extends Error {
        public NotImplementedException(String str) {
            super(str);
        }
    }

    public interface SingleButtonCallback {
        void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction);
    }

    public /* bridge */ /* synthetic */ View findViewById(int i) {
        return super.findViewById(i);
    }

    public /* bridge */ /* synthetic */ void setContentView(View view, LayoutParams layoutParams) throws IllegalAccessError {
        super.setContentView(view, layoutParams);
    }

    public final Builder getBuilder() {
        return this.mBuilder;
    }

    @SuppressLint({"InflateParams"})
    protected MaterialDialog(Builder builder) {
        super(builder.context, DialogInit.getTheme(builder));
        this.mBuilder = builder;
        this.view = (MDRootLayout) LayoutInflater.from(builder.context).inflate(DialogInit.getInflateLayout(builder), null);
        DialogInit.init(this);
    }

    public final void setTypeface(TextView textView, Typeface typeface) {
        if (typeface != null) {
            textView.setPaintFlags(textView.getPaintFlags() | 128);
            textView.setTypeface(typeface);
        }
    }

    protected final void checkIfListInitScroll() {
        if (this.listView != null) {
            this.listView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                public void onGlobalLayout() {
                    if (VERSION.SDK_INT < 16) {
                        MaterialDialog.this.listView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    } else {
                        MaterialDialog.this.listView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                    if (MaterialDialog.this.listType == ListType.SINGLE || MaterialDialog.this.listType == ListType.MULTI) {
                        int i;
                        if (MaterialDialog.this.listType == ListType.SINGLE) {
                            if (MaterialDialog.this.mBuilder.selectedIndex >= 0) {
                                i = MaterialDialog.this.mBuilder.selectedIndex;
                            } else {
                                return;
                            }
                        } else if (MaterialDialog.this.selectedIndicesList != null && MaterialDialog.this.selectedIndicesList.size() != 0) {
                            Collections.sort(MaterialDialog.this.selectedIndicesList);
                            i = ((Integer) MaterialDialog.this.selectedIndicesList.get(0)).intValue();
                        } else {
                            return;
                        }
                        if (MaterialDialog.this.listView.getLastVisiblePosition() < i) {
                            i -= (MaterialDialog.this.listView.getLastVisiblePosition() - MaterialDialog.this.listView.getFirstVisiblePosition()) / 2;
                            if (i < 0) {
                                i = 0;
                            }
                            MaterialDialog.this.listView.post(new Runnable() {
                                public void run() {
                                    MaterialDialog.this.listView.requestFocus();
                                    MaterialDialog.this.listView.setSelection(i);
                                }
                            });
                        }
                    }
                }
            });
        }
    }

    protected final void invalidateList() {
        if (this.listView != null) {
            if ((this.mBuilder.items != null && this.mBuilder.items.length != 0) || this.mBuilder.adapter != null) {
                this.listView.setAdapter(this.mBuilder.adapter);
                if (this.listType != null || this.mBuilder.listCallbackCustom != null) {
                    this.listView.setOnItemClickListener(this);
                }
            }
        }
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        boolean z = false;
        if (this.mBuilder.listCallbackCustom != null) {
            CharSequence charSequence = null;
            if (view instanceof TextView) {
                charSequence = ((TextView) view).getText();
            }
            this.mBuilder.listCallbackCustom.onSelection(this, view, i, charSequence);
        } else if (this.listType == null || this.listType == ListType.REGULAR) {
            if (this.mBuilder.autoDismiss) {
                dismiss();
            }
            if (this.mBuilder.listCallback != null) {
                this.mBuilder.listCallback.onSelection(this, view, i, this.mBuilder.items[i]);
            }
        } else if (this.listType == ListType.MULTI) {
            CheckBox checkBox = (CheckBox) view.findViewById(R.id.control);
            if (!this.selectedIndicesList.contains(Integer.valueOf(i))) {
                this.selectedIndicesList.add(Integer.valueOf(i));
                if (!this.mBuilder.alwaysCallMultiChoiceCallback) {
                    checkBox.setChecked(true);
                    return;
                } else if (sendMultichoiceCallback()) {
                    checkBox.setChecked(true);
                    return;
                } else {
                    this.selectedIndicesList.remove(Integer.valueOf(i));
                    return;
                }
            }
            this.selectedIndicesList.remove(Integer.valueOf(i));
            checkBox.setChecked(false);
            if (this.mBuilder.alwaysCallMultiChoiceCallback) {
                sendMultichoiceCallback();
            }
        } else if (this.listType == ListType.SINGLE) {
            DefaultAdapter defaultAdapter = (DefaultAdapter) this.mBuilder.adapter;
            RadioButton radioButton = (RadioButton) view.findViewById(R.id.control);
            if (this.mBuilder.autoDismiss && this.mBuilder.positiveText == null) {
                dismiss();
                this.mBuilder.selectedIndex = i;
                sendSingleChoiceCallback(view);
            } else if (this.mBuilder.alwaysCallSingleChoiceCallback) {
                int i2 = this.mBuilder.selectedIndex;
                this.mBuilder.selectedIndex = i;
                z = sendSingleChoiceCallback(view);
                this.mBuilder.selectedIndex = i2;
            } else {
                z = true;
            }
            if (z) {
                this.mBuilder.selectedIndex = i;
                radioButton.setChecked(true);
                defaultAdapter.notifyDataSetChanged();
            }
        }
    }

    protected final Drawable getListSelector() {
        if (this.mBuilder.listSelector != 0) {
            return ResourcesCompat.getDrawable(this.mBuilder.context.getResources(), this.mBuilder.listSelector, null);
        }
        Drawable resolveDrawable = DialogUtils.resolveDrawable(this.mBuilder.context, R.attr.md_list_selector);
        return resolveDrawable == null ? DialogUtils.resolveDrawable(getContext(), R.attr.md_list_selector) : resolveDrawable;
    }

    Drawable getButtonSelector(DialogAction dialogAction, boolean z) {
        Drawable resolveDrawable;
        if (!z) {
            switch (AnonymousClass4.$SwitchMap$com$afollestad$materialdialogs$DialogAction[dialogAction.ordinal()]) {
                case HighlightView.GROW_NONE /*1*/:
                    if (this.mBuilder.btnSelectorNeutral != 0) {
                        return ResourcesCompat.getDrawable(this.mBuilder.context.getResources(), this.mBuilder.btnSelectorNeutral, null);
                    }
                    resolveDrawable = DialogUtils.resolveDrawable(this.mBuilder.context, R.attr.md_btn_neutral_selector);
                    if (resolveDrawable != null) {
                        return resolveDrawable;
                    }
                    resolveDrawable = DialogUtils.resolveDrawable(getContext(), R.attr.md_btn_neutral_selector);
                    if (VERSION.SDK_INT < 21) {
                        return resolveDrawable;
                    }
                    RippleHelper.applyColor(resolveDrawable, this.mBuilder.buttonRippleColor);
                    return resolveDrawable;
                case HighlightView.GROW_LEFT_EDGE /*2*/:
                    if (this.mBuilder.btnSelectorNegative != 0) {
                        return ResourcesCompat.getDrawable(this.mBuilder.context.getResources(), this.mBuilder.btnSelectorNegative, null);
                    }
                    resolveDrawable = DialogUtils.resolveDrawable(this.mBuilder.context, R.attr.md_btn_negative_selector);
                    if (resolveDrawable != null) {
                        return resolveDrawable;
                    }
                    resolveDrawable = DialogUtils.resolveDrawable(getContext(), R.attr.md_btn_negative_selector);
                    if (VERSION.SDK_INT < 21) {
                        return resolveDrawable;
                    }
                    RippleHelper.applyColor(resolveDrawable, this.mBuilder.buttonRippleColor);
                    return resolveDrawable;
                default:
                    if (this.mBuilder.btnSelectorPositive != 0) {
                        return ResourcesCompat.getDrawable(this.mBuilder.context.getResources(), this.mBuilder.btnSelectorPositive, null);
                    }
                    resolveDrawable = DialogUtils.resolveDrawable(this.mBuilder.context, R.attr.md_btn_positive_selector);
                    if (resolveDrawable != null) {
                        return resolveDrawable;
                    }
                    resolveDrawable = DialogUtils.resolveDrawable(getContext(), R.attr.md_btn_positive_selector);
                    if (VERSION.SDK_INT < 21) {
                        return resolveDrawable;
                    }
                    RippleHelper.applyColor(resolveDrawable, this.mBuilder.buttonRippleColor);
                    return resolveDrawable;
            }
        } else if (this.mBuilder.btnSelectorStacked != 0) {
            return ResourcesCompat.getDrawable(this.mBuilder.context.getResources(), this.mBuilder.btnSelectorStacked, null);
        } else {
            resolveDrawable = DialogUtils.resolveDrawable(this.mBuilder.context, R.attr.md_btn_stacked_selector);
            if (resolveDrawable == null) {
                return DialogUtils.resolveDrawable(getContext(), R.attr.md_btn_stacked_selector);
            }
            return resolveDrawable;
        }
    }

    private boolean sendSingleChoiceCallback(View view) {
        if (this.mBuilder.listCallbackSingleChoice == null) {
            return false;
        }
        CharSequence charSequence = null;
        if (this.mBuilder.selectedIndex >= 0 && this.mBuilder.selectedIndex < this.mBuilder.items.length) {
            charSequence = this.mBuilder.items[this.mBuilder.selectedIndex];
        }
        return this.mBuilder.listCallbackSingleChoice.onSelection(this, view, this.mBuilder.selectedIndex, charSequence);
    }

    private boolean sendMultichoiceCallback() {
        if (this.mBuilder.listCallbackMultiChoice == null) {
            return false;
        }
        Collections.sort(this.selectedIndicesList);
        List arrayList = new ArrayList();
        for (Integer num : this.selectedIndicesList) {
            if (num.intValue() >= 0 && num.intValue() <= this.mBuilder.items.length - 1) {
                arrayList.add(this.mBuilder.items[num.intValue()]);
            }
        }
        return this.mBuilder.listCallbackMultiChoice.onSelection(this, (Integer[]) this.selectedIndicesList.toArray(new Integer[this.selectedIndicesList.size()]), (CharSequence[]) arrayList.toArray(new CharSequence[arrayList.size()]));
    }

    public final void onClick(View view) {
        DialogAction dialogAction = (DialogAction) view.getTag();
        switch (AnonymousClass4.$SwitchMap$com$afollestad$materialdialogs$DialogAction[dialogAction.ordinal()]) {
            case HighlightView.GROW_NONE /*1*/:
                if (this.mBuilder.callback != null) {
                    this.mBuilder.callback.onAny(this);
                    this.mBuilder.callback.onNeutral(this);
                }
                if (this.mBuilder.onNeutralCallback != null) {
                    this.mBuilder.onNeutralCallback.onClick(this, dialogAction);
                }
                if (this.mBuilder.autoDismiss) {
                    dismiss();
                    break;
                }
                break;
            case HighlightView.GROW_LEFT_EDGE /*2*/:
                if (this.mBuilder.callback != null) {
                    this.mBuilder.callback.onAny(this);
                    this.mBuilder.callback.onNegative(this);
                }
                if (this.mBuilder.onNegativeCallback != null) {
                    this.mBuilder.onNegativeCallback.onClick(this, dialogAction);
                }
                if (this.mBuilder.autoDismiss) {
                    dismiss();
                    break;
                }
                break;
            case ImageLoaderConfiguration$Builder.DEFAULT_THREAD_POOL_SIZE /*3*/:
                if (this.mBuilder.callback != null) {
                    this.mBuilder.callback.onAny(this);
                    this.mBuilder.callback.onPositive(this);
                }
                if (this.mBuilder.onPositiveCallback != null) {
                    this.mBuilder.onPositiveCallback.onClick(this, dialogAction);
                }
                if (!this.mBuilder.alwaysCallSingleChoiceCallback) {
                    sendSingleChoiceCallback(view);
                }
                if (!this.mBuilder.alwaysCallMultiChoiceCallback) {
                    sendMultichoiceCallback();
                }
                if (!(this.mBuilder.inputCallback == null || this.input == null || this.mBuilder.alwaysCallInputCallback)) {
                    this.mBuilder.inputCallback.onInput(this, this.input.getText());
                }
                if (this.mBuilder.autoDismiss) {
                    dismiss();
                    break;
                }
                break;
        }
        if (this.mBuilder.onAnyCallback != null) {
            this.mBuilder.onAnyCallback.onClick(this, dialogAction);
        }
    }

    @UiThread
    public void show() {
        try {
            super.show();
        } catch (BadTokenException e) {
            throw new DialogException("Bad window token, you cannot show a dialog before an Activity is created or after it's hidden.");
        }
    }

    public final MDButton getActionButton(@NonNull DialogAction dialogAction) {
        switch (AnonymousClass4.$SwitchMap$com$afollestad$materialdialogs$DialogAction[dialogAction.ordinal()]) {
            case HighlightView.GROW_NONE /*1*/:
                return this.neutralButton;
            case HighlightView.GROW_LEFT_EDGE /*2*/:
                return this.negativeButton;
            default:
                return this.positiveButton;
        }
    }

    public final View getView() {
        return this.view;
    }

    @Nullable
    public final ListView getListView() {
        return this.listView;
    }

    @Nullable
    public final EditText getInputEditText() {
        return this.input;
    }

    public final TextView getTitleView() {
        return this.title;
    }

    public ImageView getIconView() {
        return this.icon;
    }

    @Nullable
    public final TextView getContentView() {
        return this.content;
    }

    @Nullable
    public final View getCustomView() {
        return this.mBuilder.customView;
    }

    @UiThread
    public final void setActionButton(@NonNull DialogAction dialogAction, CharSequence charSequence) {
        int i = 8;
        MDButton mDButton;
        switch (AnonymousClass4.$SwitchMap$com$afollestad$materialdialogs$DialogAction[dialogAction.ordinal()]) {
            case HighlightView.GROW_NONE /*1*/:
                this.mBuilder.neutralText = charSequence;
                this.neutralButton.setText(charSequence);
                mDButton = this.neutralButton;
                if (charSequence != null) {
                    i = 0;
                }
                mDButton.setVisibility(i);
                return;
            case HighlightView.GROW_LEFT_EDGE /*2*/:
                this.mBuilder.negativeText = charSequence;
                this.negativeButton.setText(charSequence);
                mDButton = this.negativeButton;
                if (charSequence != null) {
                    i = 0;
                }
                mDButton.setVisibility(i);
                return;
            default:
                this.mBuilder.positiveText = charSequence;
                this.positiveButton.setText(charSequence);
                mDButton = this.positiveButton;
                if (charSequence != null) {
                    i = 0;
                }
                mDButton.setVisibility(i);
                return;
        }
    }

    public final void setActionButton(DialogAction dialogAction, @StringRes int i) {
        setActionButton(dialogAction, getContext().getText(i));
    }

    public final boolean hasActionButtons() {
        return numberOfActionButtons() > 0;
    }

    public final int numberOfActionButtons() {
        int i = 0;
        if (this.mBuilder.positiveText != null && this.positiveButton.getVisibility() == 0) {
            i = 1;
        }
        if (this.mBuilder.neutralText != null && this.neutralButton.getVisibility() == 0) {
            i++;
        }
        if (this.mBuilder.negativeText == null || this.negativeButton.getVisibility() != 0) {
            return i;
        }
        return i + 1;
    }

    @UiThread
    public final void setTitle(@NonNull CharSequence charSequence) {
        this.title.setText(charSequence);
    }

    @UiThread
    public final void setTitle(@StringRes int i) {
        setTitle(this.mBuilder.context.getString(i));
    }

    @UiThread
    public final void setTitle(@StringRes int i, @Nullable Object... objArr) {
        setTitle(this.mBuilder.context.getString(i, objArr));
    }

    @UiThread
    public void setIcon(@DrawableRes int i) {
        this.icon.setImageResource(i);
        this.icon.setVisibility(i != 0 ? 0 : 8);
    }

    @UiThread
    public void setIcon(Drawable drawable) {
        this.icon.setImageDrawable(drawable);
        this.icon.setVisibility(drawable != null ? 0 : 8);
    }

    @UiThread
    public void setIconAttribute(@AttrRes int i) {
        setIcon(DialogUtils.resolveDrawable(this.mBuilder.context, i));
    }

    @UiThread
    public final void setContent(CharSequence charSequence) {
        this.content.setText(charSequence);
        this.content.setVisibility(TextUtils.isEmpty(charSequence) ? 8 : 0);
    }

    @UiThread
    public final void setContent(@StringRes int i) {
        setContent(this.mBuilder.context.getString(i));
    }

    @UiThread
    public final void setContent(@StringRes int i, @Nullable Object... objArr) {
        setContent(this.mBuilder.context.getString(i, objArr));
    }

    @Deprecated
    public void setMessage(CharSequence charSequence) {
        setContent(charSequence);
    }

    @UiThread
    public final void setItems(CharSequence... charSequenceArr) {
        if (this.mBuilder.adapter == null) {
            throw new IllegalStateException("This MaterialDialog instance does not yet have an adapter set to it. You cannot use setItems().");
        }
        this.mBuilder.items = charSequenceArr;
        if (this.mBuilder.adapter instanceof DefaultAdapter) {
            this.mBuilder.adapter = new DefaultAdapter(this, ListType.getLayoutForType(this.listType));
            this.listView.setAdapter(this.mBuilder.adapter);
            return;
        }
        throw new IllegalStateException("When using a custom adapter, setItems() cannot be used. Set items through the adapter instead.");
    }

    public final int getCurrentProgress() {
        if (this.mProgress == null) {
            return -1;
        }
        return this.mProgress.getProgress();
    }

    public ProgressBar getProgressBar() {
        return this.mProgress;
    }

    public final void incrementProgress(int i) {
        setProgress(getCurrentProgress() + i);
    }

    public final void setProgress(int i) {
        if (this.mBuilder.progress <= -2) {
            throw new IllegalStateException("Cannot use setProgress() on this dialog.");
        }
        this.mProgress.setProgress(i);
        this.mHandler.post(new Runnable() {
            public void run() {
                if (MaterialDialog.this.mProgressLabel != null) {
                    MaterialDialog.this.mProgressLabel.setText(MaterialDialog.this.mBuilder.progressPercentFormat.format((double) (((float) MaterialDialog.this.getCurrentProgress()) / ((float) MaterialDialog.this.getMaxProgress()))));
                }
                if (MaterialDialog.this.mProgressMinMax != null) {
                    MaterialDialog.this.mProgressMinMax.setText(String.format(MaterialDialog.this.mBuilder.progressNumberFormat, new Object[]{Integer.valueOf(MaterialDialog.this.getCurrentProgress()), Integer.valueOf(MaterialDialog.this.getMaxProgress())}));
                }
            }
        });
    }

    public final void setMaxProgress(int i) {
        if (this.mBuilder.progress <= -2) {
            throw new IllegalStateException("Cannot use setMaxProgress() on this dialog.");
        }
        this.mProgress.setMax(i);
    }

    public final boolean isIndeterminateProgress() {
        return this.mBuilder.indeterminateProgress;
    }

    public final int getMaxProgress() {
        if (this.mProgress == null) {
            return -1;
        }
        return this.mProgress.getMax();
    }

    public final void setProgressPercentFormat(NumberFormat numberFormat) {
        this.mBuilder.progressPercentFormat = numberFormat;
        setProgress(getCurrentProgress());
    }

    public final void setProgressNumberFormat(String str) {
        this.mBuilder.progressNumberFormat = str;
        setProgress(getCurrentProgress());
    }

    public final boolean isCancelled() {
        return !isShowing();
    }

    public int getSelectedIndex() {
        if (this.mBuilder.listCallbackSingleChoice != null) {
            return this.mBuilder.selectedIndex;
        }
        return -1;
    }

    @Nullable
    public Integer[] getSelectedIndices() {
        if (this.mBuilder.listCallbackMultiChoice != null) {
            return (Integer[]) this.selectedIndicesList.toArray(new Integer[this.selectedIndicesList.size()]);
        }
        return null;
    }

    @UiThread
    public void setSelectedIndex(int i) {
        this.mBuilder.selectedIndex = i;
        if (this.mBuilder.adapter == null || !(this.mBuilder.adapter instanceof DefaultAdapter)) {
            throw new IllegalStateException("You can only use setSelectedIndex() with the default adapter implementation.");
        }
        ((DefaultAdapter) this.mBuilder.adapter).notifyDataSetChanged();
    }

    @UiThread
    public void setSelectedIndices(@NonNull Integer[] numArr) {
        this.selectedIndicesList = new ArrayList(Arrays.asList(numArr));
        if (this.mBuilder.adapter == null || !(this.mBuilder.adapter instanceof DefaultAdapter)) {
            throw new IllegalStateException("You can only use setSelectedIndices() with the default adapter implementation.");
        }
        ((DefaultAdapter) this.mBuilder.adapter).notifyDataSetChanged();
    }

    public void clearSelectedIndices() {
        clearSelectedIndices(true);
    }

    public void clearSelectedIndices(boolean z) {
        if (this.listType == null || this.listType != ListType.MULTI) {
            throw new IllegalStateException("You can only use clearSelectedIndices() with multi choice list dialogs.");
        } else if (this.mBuilder.adapter == null || !(this.mBuilder.adapter instanceof DefaultAdapter)) {
            throw new IllegalStateException("You can only use clearSelectedIndices() with the default adapter implementation.");
        } else {
            if (this.selectedIndicesList != null) {
                this.selectedIndicesList.clear();
            }
            ((DefaultAdapter) this.mBuilder.adapter).notifyDataSetChanged();
            if (z && this.mBuilder.listCallbackMultiChoice != null) {
                sendMultichoiceCallback();
            }
        }
    }

    public void selectAllIndicies() {
        selectAllIndicies(true);
    }

    public void selectAllIndicies(boolean z) {
        if (this.listType == null || this.listType != ListType.MULTI) {
            throw new IllegalStateException("You can only use selectAllIndicies() with multi choice list dialogs.");
        } else if (this.mBuilder.adapter == null || !(this.mBuilder.adapter instanceof DefaultAdapter)) {
            throw new IllegalStateException("You can only use selectAllIndicies() with the default adapter implementation.");
        } else {
            if (this.selectedIndicesList == null) {
                this.selectedIndicesList = new ArrayList();
            }
            for (int i = 0; i < this.mBuilder.adapter.getCount(); i++) {
                if (!this.selectedIndicesList.contains(Integer.valueOf(i))) {
                    this.selectedIndicesList.add(Integer.valueOf(i));
                }
            }
            ((DefaultAdapter) this.mBuilder.adapter).notifyDataSetChanged();
            if (z && this.mBuilder.listCallbackMultiChoice != null) {
                sendMultichoiceCallback();
            }
        }
    }

    public final void onShow(DialogInterface dialogInterface) {
        if (this.input != null) {
            DialogUtils.showKeyboard(this, this.mBuilder);
            if (this.input.getText().length() > 0) {
                this.input.setSelection(this.input.getText().length());
            }
        }
        super.onShow(dialogInterface);
    }

    protected void setInternalInputCallback() {
        if (this.input != null) {
            this.input.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    boolean z;
                    boolean z2 = true;
                    int length = charSequence.toString().length();
                    if (MaterialDialog.this.mBuilder.inputAllowEmpty) {
                        z = false;
                    } else {
                        z = length == 0;
                        View actionButton = MaterialDialog.this.getActionButton(DialogAction.POSITIVE);
                        if (z) {
                            z2 = false;
                        }
                        actionButton.setEnabled(z2);
                    }
                    MaterialDialog.this.invalidateInputMinMaxIndicator(length, z);
                    if (MaterialDialog.this.mBuilder.alwaysCallInputCallback) {
                        MaterialDialog.this.mBuilder.inputCallback.onInput(MaterialDialog.this, charSequence);
                    }
                }

                public void afterTextChanged(Editable editable) {
                }
            });
        }
    }

    protected void invalidateInputMinMaxIndicator(int i, boolean z) {
        if (this.inputMinMax != null) {
            int i2;
            boolean z2;
            if (this.mBuilder.inputMaxLength > 0) {
                this.inputMinMax.setText(String.format(Locale.getDefault(), "%d/%d", new Object[]{Integer.valueOf(i), Integer.valueOf(this.mBuilder.inputMaxLength)}));
                this.inputMinMax.setVisibility(0);
            } else {
                this.inputMinMax.setVisibility(8);
            }
            if (!(z && i == 0) && ((this.mBuilder.inputMaxLength <= 0 || i <= this.mBuilder.inputMaxLength) && i >= this.mBuilder.inputMinLength)) {
                i2 = 0;
            } else {
                i2 = 1;
            }
            int i3 = i2 != 0 ? this.mBuilder.inputRangeErrorColor : this.mBuilder.contentColor;
            int i4 = i2 != 0 ? this.mBuilder.inputRangeErrorColor : this.mBuilder.widgetColor;
            if (this.mBuilder.inputMaxLength > 0) {
                this.inputMinMax.setTextColor(i3);
            }
            MDTintHelper.setTint(this.input, i4);
            View actionButton = getActionButton(DialogAction.POSITIVE);
            if (i2 == 0) {
                z2 = true;
            } else {
                z2 = false;
            }
            actionButton.setEnabled(z2);
        }
    }

    public void dismiss() {
        if (this.input != null) {
            DialogUtils.hideKeyboard(this, this.mBuilder);
        }
        super.dismiss();
    }
}
