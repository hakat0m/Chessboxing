package com.gumtree.android.postad.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import com.gumtree.android.R;

public class EditTextSummaryValidationView extends BaseSummaryValidationView implements SummaryValidationView {
    private static final String BOLD = "bold";
    private static final String DEFAULT_VALUE = "";
    private int checkBackgroundFinalColor;
    private int checkBackgroundInitialColor;
    @Bind({2131624446})
    ImageView checkBackgroundView;
    @Bind({2131624420})
    ImageView checkView;
    @Bind({2131624442})
    EditText editText;
    @Bind({2131624715})
    TextView errorMessageView;
    private OnFieldValueChangeListener listener;
    private int paddingBottom;
    private int paddingLeft;
    private int paddingRight;
    private int paddingTop;
    @Bind({2131624716})
    TextInputLayout textInputLayoutView;

    public interface OnFieldValueChangeListener {
        void onFieldValueChange(String str);
    }

    public EditTextSummaryValidationView(Context context) {
        super(context);
        init(context);
    }

    public EditTextSummaryValidationView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
        initAttributes(context, attributeSet);
    }

    public EditTextSummaryValidationView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
        initAttributes(context, attributeSet);
    }

    protected void init(Context context) {
        setOrientation(1);
        inflate(context, 2130903287, this);
        ButterKnife.bind(this);
        setValue(DEFAULT_VALUE);
        setLayoutTransition(ValidFieldAnimationHelper.getDefaultLayoutTransition(this.errorMessageView));
        this.listener = new NoOpFieldChangeListener(null);
        this.checkBackgroundFinalColor = ContextCompat.getColor(context, 2131493018);
        this.checkBackgroundInitialColor = ContextCompat.getColor(context, 2131492927);
        this.paddingLeft = this.editText.getPaddingLeft();
        this.paddingTop = this.editText.getPaddingTop();
        this.paddingRight = this.editText.getPaddingRight();
        this.paddingBottom = this.editText.getPaddingBottom();
    }

    protected void initAttributes(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.SummaryValidationView, 0, 0);
        try {
            showTitle(obtainStyledAttributes.getString(2));
            Object string = obtainStyledAttributes.getString(3);
            if (!TextUtils.isEmpty(string) && string.equals(BOLD)) {
                this.editText.setTypeface(Typeface.DEFAULT_BOLD);
            }
            int i = obtainStyledAttributes.getInt(0, 0);
            if (i != 0) {
                this.editText.setInputType(i);
            }
            i = obtainStyledAttributes.getInt(1, 0);
            if (i != 0) {
                this.editText.setImeOptions(i);
            }
            obtainStyledAttributes.recycle();
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
        }
    }

    @OnTextChanged({2131624442})
    void changed(CharSequence charSequence) {
        String charSequence2 = charSequence.toString();
        setValue(charSequence2);
        this.listener.onFieldValueChange(charSequence2);
    }

    public void showTitle(String str) {
        setTitle(str);
        this.textInputLayoutView.setHint(str);
    }

    public void showValue(@Nullable String str) {
        if (str == null) {
            str = DEFAULT_VALUE;
        }
        if (!str.equals(getValue())) {
            setValue(str);
            this.editText.setText(str);
        }
    }

    public void showError(String str) {
        setErrorMessage(str);
        if (TextUtils.isEmpty(str)) {
            this.errorMessageView.setVisibility(8);
            updateEditTextBackground(false);
            return;
        }
        this.errorMessageView.setVisibility(0);
        this.errorMessageView.setText(str);
        updateEditTextBackground(true);
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
        updateEditTextBackground(false);
    }

    public void setOnFieldValueChangeListener(OnFieldValueChangeListener onFieldValueChangeListener) {
        if (onFieldValueChangeListener == null) {
            onFieldValueChangeListener = new NoOpFieldChangeListener(null);
        }
        this.listener = onFieldValueChangeListener;
    }

    protected void restoreTitle(String str) {
        this.textInputLayoutView.setHint(str);
    }

    protected void restoreValue(String str) {
        this.editText.setText(str);
    }

    protected void restoreError(String str) {
        if (TextUtils.isEmpty(str)) {
            this.errorMessageView.setVisibility(8);
            updateEditTextBackground(false);
            return;
        }
        this.errorMessageView.setVisibility(0);
        this.errorMessageView.setText(str);
        updateEditTextBackground(true);
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
        if (z) {
            this.editText.requestFocus();
        } else {
            this.editText.clearFocus();
        }
    }

    public boolean hasFocus() {
        return this.editText.hasFocus();
    }

    public void onFocusChanged(OnFocusChangeListener onFocusChangeListener) {
        this.editText.setOnFocusChangeListener(onFocusChangeListener);
    }

    private void updateEditTextBackground(boolean z) {
        if (z) {
            this.textInputLayoutView.setError("\u00a0");
        } else {
            this.textInputLayoutView.setError(null);
        }
        this.editText.setPadding(this.paddingLeft, this.paddingTop, this.paddingRight, this.paddingBottom);
    }

    public void setFilters(InputFilter[] inputFilterArr) {
        this.editText.setFilters(inputFilterArr);
    }
}
