package com.gumtree.android.postad.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.gumtree.android.R;
import com.gumtree.android.common.utils.ThemeUtils;

public class TextSummaryValidationView extends BaseSummaryValidationView implements SummaryValidationView {
    private static final String DEFAULT_VALUE = "";
    private int checkBackgroundFinalColor;
    private int checkBackgroundInitialColor;
    @Bind({2131624446})
    ImageView checkBackgroundView;
    @Bind({2131624420})
    ImageView checkView;
    private int errorColor;
    @Bind({2131624715})
    TextView errorMessageView;
    private boolean showCheckboxWithError;
    private int textColorDisabled;
    private int textColorPrimary;
    private int textColorSecondary;
    @Bind({2131624807})
    View textUnderline;
    @Bind({2131624805})
    TextView textViewTitle;
    @Bind({2131624806})
    TextView textViewValue;

    public TextSummaryValidationView(Context context) {
        super(context);
        init(context);
    }

    public TextSummaryValidationView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
        initAttributes(context, attributeSet);
    }

    public TextSummaryValidationView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
        initAttributes(context, attributeSet);
    }

    private void init(Context context) {
        setOrientation(1);
        inflate(context, 2130903324, this);
        ButterKnife.bind(this);
        setLayoutTransition(ValidFieldAnimationHelper.getDefaultLayoutTransition(this.errorMessageView));
        this.errorColor = ThemeUtils.getColor(context, 2130772480);
        this.textColorSecondary = ThemeUtils.getColor(context, 16842808);
        this.textColorPrimary = ThemeUtils.getColor(context, 16842806);
        this.textColorDisabled = ThemeUtils.getColor(context, 2130772486);
        this.checkBackgroundFinalColor = ContextCompat.getColor(context, 2131493018);
        this.checkBackgroundInitialColor = ContextCompat.getColor(context, 2131492927);
    }

    private void initAttributes(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.SummaryValidationView, 0, 0);
        try {
            showTitle(obtainStyledAttributes.getString(2));
            this.showCheckboxWithError = obtainStyledAttributes.getBoolean(4, true);
            showValue(DEFAULT_VALUE);
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    public void showTitle(String str) {
        setTitle(str);
        this.textViewTitle.setText(str);
    }

    public void showValue(@Nullable String str) {
        if (str == null) {
            str = DEFAULT_VALUE;
        }
        setValue(str);
        if (TextUtils.isEmpty(str)) {
            this.textViewTitle.setVisibility(4);
            this.textViewValue.setText(getTitle());
            this.textViewValue.setTextColor(this.textColorSecondary);
            return;
        }
        this.textViewTitle.setVisibility(0);
        this.textViewValue.setText(str);
        this.textViewValue.setTextColor(this.textColorPrimary);
    }

    public void showError(String str) {
        setErrorMessage(str);
        if (TextUtils.isEmpty(str)) {
            this.errorMessageView.setVisibility(8);
            if (isEnabled()) {
                this.textUnderline.setBackgroundColor(this.textColorSecondary);
            } else {
                this.textUnderline.setBackgroundColor(this.textColorDisabled);
            }
            if (this.showCheckboxWithError) {
                showValid(true);
                return;
            }
            return;
        }
        this.errorMessageView.setVisibility(0);
        this.errorMessageView.setText(str);
        this.textUnderline.setBackgroundColor(this.errorColor);
        if (this.showCheckboxWithError) {
            showValid(false);
        }
    }

    public void showValid(boolean z) {
        setIsValid(z);
        if (!z) {
            this.checkView.setVisibility(8);
            this.checkBackgroundView.setVisibility(8);
        } else if (this.checkBackgroundView.getVisibility() == 8) {
            ValidFieldAnimationHelper.showValidAnimation(this.checkBackgroundView, this.checkView, this.checkBackgroundInitialColor, this.checkBackgroundFinalColor);
        }
    }

    public void reset() {
        this.errorMessageView.setVisibility(8);
        showValid(false);
        if (isEnabled()) {
            this.textUnderline.setBackgroundColor(this.textColorSecondary);
        } else {
            this.textUnderline.setBackgroundColor(this.textColorDisabled);
        }
    }

    protected void restoreTitle(String str) {
        this.textViewTitle.setText(str);
    }

    protected void restoreValue(String str) {
        showValue(str);
    }

    protected void restoreError(String str) {
        if (TextUtils.isEmpty(str)) {
            this.errorMessageView.setVisibility(8);
            this.textUnderline.setBackgroundColor(this.textColorSecondary);
            return;
        }
        this.errorMessageView.setVisibility(0);
        this.errorMessageView.setText(str);
        this.textUnderline.setBackgroundColor(this.errorColor);
    }

    protected void restoreValid(boolean z) {
        if (z) {
            this.checkBackgroundView.setVisibility(0);
            this.checkView.setVisibility(0);
            return;
        }
        this.checkBackgroundView.setVisibility(8);
        this.checkView.setVisibility(8);
    }

    protected void restoreFocus(boolean z) {
    }

    public void setEnabled(boolean z) {
        super.setEnabled(z);
        this.textViewValue.setEnabled(z);
        if (z) {
            String value = getValue();
            if (value == null || !(value.equals(getTitle()) || value.isEmpty())) {
                this.textViewValue.setTextColor(this.textColorPrimary);
            } else {
                this.textViewValue.setTextColor(this.textColorSecondary);
            }
            this.textUnderline.setBackgroundColor(this.textColorSecondary);
            return;
        }
        this.textViewValue.setTextColor(this.textColorDisabled);
        this.textUnderline.setBackgroundColor(this.textColorDisabled);
    }
}
