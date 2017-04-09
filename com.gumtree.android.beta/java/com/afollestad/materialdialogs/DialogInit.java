package com.afollestad.materialdialogs;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build.VERSION;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.annotation.UiThread;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import com.afollestad.materialdialogs.MaterialDialog.Builder;
import com.afollestad.materialdialogs.MaterialDialog.ListType;
import com.afollestad.materialdialogs.internal.MDAdapter;
import com.afollestad.materialdialogs.internal.MDButton;
import com.afollestad.materialdialogs.internal.MDRootLayout;
import com.afollestad.materialdialogs.internal.MDTintHelper;
import com.afollestad.materialdialogs.util.DialogUtils;
import java.util.ArrayList;
import java.util.Arrays;
import me.zhanghai.android.materialprogressbar.HorizontalProgressDrawable;
import me.zhanghai.android.materialprogressbar.IndeterminateHorizontalProgressDrawable;
import me.zhanghai.android.materialprogressbar.IndeterminateProgressDrawable;

class DialogInit {
    DialogInit() {
    }

    @StyleRes
    public static int getTheme(@NonNull Builder builder) {
        boolean resolveBoolean = DialogUtils.resolveBoolean(builder.context, R.attr.md_dark_theme, builder.theme == Theme.DARK);
        builder.theme = resolveBoolean ? Theme.DARK : Theme.LIGHT;
        return resolveBoolean ? R.style.MD_Dark : R.style.MD_Light;
    }

    @LayoutRes
    public static int getInflateLayout(Builder builder) {
        if (builder.customView != null) {
            return R.layout.md_dialog_custom;
        }
        if ((builder.items != null && builder.items.length > 0) || builder.adapter != null) {
            return R.layout.md_dialog_list;
        }
        if (builder.progress > -2) {
            return R.layout.md_dialog_progress;
        }
        if (builder.indeterminateProgress) {
            if (builder.indeterminateIsHorizontalProgress) {
                return R.layout.md_dialog_progress_indeterminate_horizontal;
            }
            return R.layout.md_dialog_progress_indeterminate;
        } else if (builder.inputCallback != null) {
            return R.layout.md_dialog_input;
        } else {
            return R.layout.md_dialog_basic;
        }
    }

    @UiThread
    public static void init(MaterialDialog materialDialog) {
        int i;
        boolean resolveBoolean;
        Builder builder = materialDialog.mBuilder;
        materialDialog.setCancelable(builder.cancelable);
        materialDialog.setCanceledOnTouchOutside(builder.canceledOnTouchOutside);
        if (builder.backgroundColor == 0) {
            builder.backgroundColor = DialogUtils.resolveColor(builder.context, R.attr.md_background_color);
        }
        if (builder.backgroundColor != 0) {
            Drawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setCornerRadius(builder.context.getResources().getDimension(R.dimen.md_bg_corner_radius));
            gradientDrawable.setColor(builder.backgroundColor);
            DialogUtils.setBackgroundCompat(materialDialog.view, gradientDrawable);
        }
        if (!builder.positiveColorSet) {
            builder.positiveColor = DialogUtils.resolveActionTextColorStateList(builder.context, R.attr.md_positive_color, builder.positiveColor);
        }
        if (!builder.neutralColorSet) {
            builder.neutralColor = DialogUtils.resolveActionTextColorStateList(builder.context, R.attr.md_neutral_color, builder.neutralColor);
        }
        if (!builder.negativeColorSet) {
            builder.negativeColor = DialogUtils.resolveActionTextColorStateList(builder.context, R.attr.md_negative_color, builder.negativeColor);
        }
        if (!builder.widgetColorSet) {
            builder.widgetColor = DialogUtils.resolveColor(builder.context, R.attr.md_widget_color, builder.widgetColor);
        }
        if (!builder.titleColorSet) {
            builder.titleColor = DialogUtils.resolveColor(builder.context, R.attr.md_title_color, DialogUtils.resolveColor(materialDialog.getContext(), 16842806));
        }
        if (!builder.contentColorSet) {
            builder.contentColor = DialogUtils.resolveColor(builder.context, R.attr.md_content_color, DialogUtils.resolveColor(materialDialog.getContext(), 16842808));
        }
        if (!builder.itemColorSet) {
            builder.itemColor = DialogUtils.resolveColor(builder.context, R.attr.md_item_color, builder.contentColor);
        }
        materialDialog.title = (TextView) materialDialog.view.findViewById(R.id.title);
        materialDialog.icon = (ImageView) materialDialog.view.findViewById(R.id.icon);
        materialDialog.titleFrame = materialDialog.view.findViewById(R.id.titleFrame);
        materialDialog.content = (TextView) materialDialog.view.findViewById(R.id.content);
        materialDialog.listView = (ListView) materialDialog.view.findViewById(R.id.contentListView);
        materialDialog.positiveButton = (MDButton) materialDialog.view.findViewById(R.id.buttonDefaultPositive);
        materialDialog.neutralButton = (MDButton) materialDialog.view.findViewById(R.id.buttonDefaultNeutral);
        materialDialog.negativeButton = (MDButton) materialDialog.view.findViewById(R.id.buttonDefaultNegative);
        if (builder.inputCallback != null && builder.positiveText == null) {
            builder.positiveText = builder.context.getText(17039370);
        }
        materialDialog.positiveButton.setVisibility(builder.positiveText != null ? 0 : 8);
        MDButton mDButton = materialDialog.neutralButton;
        if (builder.neutralText != null) {
            i = 0;
        } else {
            i = 8;
        }
        mDButton.setVisibility(i);
        mDButton = materialDialog.negativeButton;
        if (builder.negativeText != null) {
            i = 0;
        } else {
            i = 8;
        }
        mDButton.setVisibility(i);
        if (builder.icon != null) {
            materialDialog.icon.setVisibility(0);
            materialDialog.icon.setImageDrawable(builder.icon);
        } else {
            gradientDrawable = DialogUtils.resolveDrawable(builder.context, R.attr.md_icon);
            if (gradientDrawable != null) {
                materialDialog.icon.setVisibility(0);
                materialDialog.icon.setImageDrawable(gradientDrawable);
            } else {
                materialDialog.icon.setVisibility(8);
            }
        }
        i = builder.maxIconSize;
        if (i == -1) {
            i = DialogUtils.resolveDimension(builder.context, R.attr.md_icon_max_size);
        }
        if (builder.limitIconToDefaultSize || DialogUtils.resolveBoolean(builder.context, R.attr.md_icon_limit_icon_to_default_size)) {
            i = builder.context.getResources().getDimensionPixelSize(R.dimen.md_icon_max_size);
        }
        if (i > -1) {
            materialDialog.icon.setAdjustViewBounds(true);
            materialDialog.icon.setMaxHeight(i);
            materialDialog.icon.setMaxWidth(i);
            materialDialog.icon.requestLayout();
        }
        if (!builder.dividerColorSet) {
            builder.dividerColor = DialogUtils.resolveColor(builder.context, R.attr.md_divider_color, DialogUtils.resolveColor(materialDialog.getContext(), R.attr.md_divider));
        }
        materialDialog.view.setDividerColor(builder.dividerColor);
        if (materialDialog.title != null) {
            materialDialog.setTypeface(materialDialog.title, builder.mediumFont);
            materialDialog.title.setTextColor(builder.titleColor);
            materialDialog.title.setGravity(builder.titleGravity.getGravityInt());
            if (VERSION.SDK_INT >= 17) {
                materialDialog.title.setTextAlignment(builder.titleGravity.getTextAlignment());
            }
            if (builder.title == null) {
                materialDialog.titleFrame.setVisibility(8);
            } else {
                materialDialog.title.setText(builder.title);
                materialDialog.titleFrame.setVisibility(0);
            }
        }
        if (materialDialog.content != null) {
            materialDialog.content.setMovementMethod(new LinkMovementMethod());
            materialDialog.setTypeface(materialDialog.content, builder.regularFont);
            materialDialog.content.setLineSpacing(0.0f, builder.contentLineSpacingMultiplier);
            if (builder.linkColor == null) {
                materialDialog.content.setLinkTextColor(DialogUtils.resolveColor(materialDialog.getContext(), 16842806));
            } else {
                materialDialog.content.setLinkTextColor(builder.linkColor);
            }
            materialDialog.content.setTextColor(builder.contentColor);
            materialDialog.content.setGravity(builder.contentGravity.getGravityInt());
            if (VERSION.SDK_INT >= 17) {
                materialDialog.content.setTextAlignment(builder.contentGravity.getTextAlignment());
            }
            if (builder.content != null) {
                materialDialog.content.setText(builder.content);
                materialDialog.content.setVisibility(0);
            } else {
                materialDialog.content.setVisibility(8);
            }
        }
        materialDialog.view.setButtonGravity(builder.buttonsGravity);
        materialDialog.view.setButtonStackedGravity(builder.btnStackedGravity);
        materialDialog.view.setForceStack(builder.forceStacking);
        if (VERSION.SDK_INT >= 14) {
            resolveBoolean = DialogUtils.resolveBoolean(builder.context, 16843660, true);
            if (resolveBoolean) {
                resolveBoolean = DialogUtils.resolveBoolean(builder.context, R.attr.textAllCaps, true);
            }
        } else {
            resolveBoolean = DialogUtils.resolveBoolean(builder.context, R.attr.textAllCaps, true);
        }
        TextView textView = materialDialog.positiveButton;
        materialDialog.setTypeface(textView, builder.mediumFont);
        textView.setAllCapsCompat(resolveBoolean);
        textView.setText(builder.positiveText);
        textView.setTextColor(builder.positiveColor);
        materialDialog.positiveButton.setStackedSelector(materialDialog.getButtonSelector(DialogAction.POSITIVE, true));
        materialDialog.positiveButton.setDefaultSelector(materialDialog.getButtonSelector(DialogAction.POSITIVE, false));
        materialDialog.positiveButton.setTag(DialogAction.POSITIVE);
        materialDialog.positiveButton.setOnClickListener(materialDialog);
        materialDialog.positiveButton.setVisibility(0);
        textView = materialDialog.negativeButton;
        materialDialog.setTypeface(textView, builder.mediumFont);
        textView.setAllCapsCompat(resolveBoolean);
        textView.setText(builder.negativeText);
        textView.setTextColor(builder.negativeColor);
        materialDialog.negativeButton.setStackedSelector(materialDialog.getButtonSelector(DialogAction.NEGATIVE, true));
        materialDialog.negativeButton.setDefaultSelector(materialDialog.getButtonSelector(DialogAction.NEGATIVE, false));
        materialDialog.negativeButton.setTag(DialogAction.NEGATIVE);
        materialDialog.negativeButton.setOnClickListener(materialDialog);
        materialDialog.negativeButton.setVisibility(0);
        textView = materialDialog.neutralButton;
        materialDialog.setTypeface(textView, builder.mediumFont);
        textView.setAllCapsCompat(resolveBoolean);
        textView.setText(builder.neutralText);
        textView.setTextColor(builder.neutralColor);
        materialDialog.neutralButton.setStackedSelector(materialDialog.getButtonSelector(DialogAction.NEUTRAL, true));
        materialDialog.neutralButton.setDefaultSelector(materialDialog.getButtonSelector(DialogAction.NEUTRAL, false));
        materialDialog.neutralButton.setTag(DialogAction.NEUTRAL);
        materialDialog.neutralButton.setOnClickListener(materialDialog);
        materialDialog.neutralButton.setVisibility(0);
        if (builder.listCallbackMultiChoice != null) {
            materialDialog.selectedIndicesList = new ArrayList();
        }
        if (materialDialog.listView != null && ((builder.items != null && builder.items.length > 0) || builder.adapter != null)) {
            materialDialog.listView.setSelector(materialDialog.getListSelector());
            if (builder.adapter == null) {
                if (builder.listCallbackSingleChoice != null) {
                    materialDialog.listType = ListType.SINGLE;
                } else if (builder.listCallbackMultiChoice != null) {
                    materialDialog.listType = ListType.MULTI;
                    if (builder.selectedIndices != null) {
                        materialDialog.selectedIndicesList = new ArrayList(Arrays.asList(builder.selectedIndices));
                        builder.selectedIndices = null;
                    }
                } else {
                    materialDialog.listType = ListType.REGULAR;
                }
                builder.adapter = new DefaultAdapter(materialDialog, ListType.getLayoutForType(materialDialog.listType));
            } else if (builder.adapter instanceof MDAdapter) {
                ((MDAdapter) builder.adapter).setDialog(materialDialog);
            }
        }
        setupProgressDialog(materialDialog);
        setupInputDialog(materialDialog);
        if (builder.customView != null) {
            View scrollView;
            ((MDRootLayout) materialDialog.view.findViewById(R.id.root)).noTitleNoPadding();
            FrameLayout frameLayout = (FrameLayout) materialDialog.view.findViewById(R.id.customViewFrame);
            materialDialog.customViewFrame = frameLayout;
            View view = builder.customView;
            if (view.getParent() != null) {
                ((ViewGroup) view.getParent()).removeView(view);
            }
            if (builder.wrapCustomViewInScroll) {
                Resources resources = materialDialog.getContext().getResources();
                int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.md_dialog_frame_margin);
                scrollView = new ScrollView(materialDialog.getContext());
                int dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.md_content_padding_top);
                int dimensionPixelSize3 = resources.getDimensionPixelSize(R.dimen.md_content_padding_bottom);
                scrollView.setClipToPadding(false);
                if (view instanceof EditText) {
                    scrollView.setPadding(dimensionPixelSize, dimensionPixelSize2, dimensionPixelSize, dimensionPixelSize3);
                } else {
                    scrollView.setPadding(0, dimensionPixelSize2, 0, dimensionPixelSize3);
                    view.setPadding(dimensionPixelSize, 0, dimensionPixelSize, 0);
                }
                scrollView.addView(view, new LayoutParams(-1, -2));
            } else {
                scrollView = view;
            }
            frameLayout.addView(scrollView, new ViewGroup.LayoutParams(-1, -2));
        }
        if (builder.showListener != null) {
            materialDialog.setOnShowListener(builder.showListener);
        }
        if (builder.cancelListener != null) {
            materialDialog.setOnCancelListener(builder.cancelListener);
        }
        if (builder.dismissListener != null) {
            materialDialog.setOnDismissListener(builder.dismissListener);
        }
        if (builder.keyListener != null) {
            materialDialog.setOnKeyListener(builder.keyListener);
        }
        materialDialog.setOnShowListenerInternal();
        materialDialog.invalidateList();
        materialDialog.setViewInternal(materialDialog.view);
        materialDialog.checkIfListInitScroll();
    }

    private static void setupProgressDialog(MaterialDialog materialDialog) {
        Builder builder = materialDialog.mBuilder;
        if (builder.indeterminateProgress || builder.progress > -2) {
            materialDialog.mProgress = (ProgressBar) materialDialog.view.findViewById(16908301);
            if (materialDialog.mProgress != null) {
                if (VERSION.SDK_INT < 14) {
                    MDTintHelper.setTint(materialDialog.mProgress, builder.widgetColor);
                } else if (!builder.indeterminateProgress) {
                    HorizontalProgressDrawable horizontalProgressDrawable = new HorizontalProgressDrawable(builder.getContext());
                    horizontalProgressDrawable.setTint(builder.widgetColor);
                    materialDialog.mProgress.setProgressDrawable(horizontalProgressDrawable);
                    materialDialog.mProgress.setIndeterminateDrawable(horizontalProgressDrawable);
                } else if (builder.indeterminateIsHorizontalProgress) {
                    IndeterminateHorizontalProgressDrawable indeterminateHorizontalProgressDrawable = new IndeterminateHorizontalProgressDrawable(builder.getContext());
                    indeterminateHorizontalProgressDrawable.setTint(builder.widgetColor);
                    materialDialog.mProgress.setProgressDrawable(indeterminateHorizontalProgressDrawable);
                    materialDialog.mProgress.setIndeterminateDrawable(indeterminateHorizontalProgressDrawable);
                } else {
                    IndeterminateProgressDrawable indeterminateProgressDrawable = new IndeterminateProgressDrawable(builder.getContext());
                    indeterminateProgressDrawable.setTint(builder.widgetColor);
                    materialDialog.mProgress.setProgressDrawable(indeterminateProgressDrawable);
                    materialDialog.mProgress.setIndeterminateDrawable(indeterminateProgressDrawable);
                }
                if (!builder.indeterminateProgress || builder.indeterminateIsHorizontalProgress) {
                    materialDialog.mProgress.setIndeterminate(builder.indeterminateIsHorizontalProgress);
                    materialDialog.mProgress.setProgress(0);
                    materialDialog.mProgress.setMax(builder.progressMax);
                    materialDialog.mProgressLabel = (TextView) materialDialog.view.findViewById(R.id.label);
                    if (materialDialog.mProgressLabel != null) {
                        materialDialog.mProgressLabel.setTextColor(builder.contentColor);
                        materialDialog.setTypeface(materialDialog.mProgressLabel, builder.mediumFont);
                        materialDialog.mProgressLabel.setText(builder.progressPercentFormat.format(0));
                    }
                    materialDialog.mProgressMinMax = (TextView) materialDialog.view.findViewById(R.id.minMax);
                    if (materialDialog.mProgressMinMax != null) {
                        materialDialog.mProgressMinMax.setTextColor(builder.contentColor);
                        materialDialog.setTypeface(materialDialog.mProgressMinMax, builder.regularFont);
                        if (builder.showMinMax) {
                            materialDialog.mProgressMinMax.setVisibility(0);
                            materialDialog.mProgressMinMax.setText(String.format(builder.progressNumberFormat, new Object[]{Integer.valueOf(0), Integer.valueOf(builder.progressMax)}));
                            MarginLayoutParams marginLayoutParams = (MarginLayoutParams) materialDialog.mProgress.getLayoutParams();
                            marginLayoutParams.leftMargin = 0;
                            marginLayoutParams.rightMargin = 0;
                            return;
                        }
                        materialDialog.mProgressMinMax.setVisibility(8);
                        return;
                    }
                    builder.showMinMax = false;
                }
            }
        }
    }

    private static void setupInputDialog(MaterialDialog materialDialog) {
        Builder builder = materialDialog.mBuilder;
        materialDialog.input = (EditText) materialDialog.view.findViewById(16908297);
        if (materialDialog.input != null) {
            materialDialog.setTypeface(materialDialog.input, builder.regularFont);
            if (builder.inputPrefill != null) {
                materialDialog.input.setText(builder.inputPrefill);
            }
            materialDialog.setInternalInputCallback();
            materialDialog.input.setHint(builder.inputHint);
            materialDialog.input.setSingleLine();
            materialDialog.input.setTextColor(builder.contentColor);
            materialDialog.input.setHintTextColor(DialogUtils.adjustAlpha(builder.contentColor, 0.3f));
            MDTintHelper.setTint(materialDialog.input, materialDialog.mBuilder.widgetColor);
            if (builder.inputType != -1) {
                materialDialog.input.setInputType(builder.inputType);
                if ((builder.inputType & 128) == 128) {
                    materialDialog.input.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
            materialDialog.inputMinMax = (TextView) materialDialog.view.findViewById(R.id.minMax);
            if (builder.inputMinLength > 0 || builder.inputMaxLength > -1) {
                materialDialog.invalidateInputMinMaxIndicator(materialDialog.input.getText().toString().length(), !builder.inputAllowEmpty);
                return;
            }
            materialDialog.inputMinMax.setVisibility(8);
            materialDialog.inputMinMax = null;
        }
    }
}
