package com.gumtree.android.postad.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;
import butterknife.OnTextChanged;
import com.gumtree.android.R;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import uk.co.senab.photoview.IPhotoView;

public class PriceFrequencySummaryValidationView extends BaseSummaryValidationView implements SummaryValidationView {
    private static final String BOLD = "bold";
    private static final String DEFAULT_VALUE = "";
    private static final float DEF_FLOAT = 0.5f;
    private static final float FOCUS_STROKE_HEIGHT = 1.5f;
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
    @Bind({2131624783})
    Spinner priceFrequency;
    private ArrayAdapter<String> spinnerArrayAdapter;
    private OnSpinnerItemSelectedListener spinnerSelectedListener;
    @Bind({2131624714})
    View stroke;
    @Bind({2131624716})
    TextInputLayout textInputLayoutView;

    public interface OnFieldValueChangeListener {
        void onFieldValueChange(String str);
    }

    public interface OnSpinnerItemSelectedListener {
        void onSpinnerValueSelected(String str);
    }

    public PriceFrequencySummaryValidationView(Context context) {
        super(context);
        init(context);
    }

    public PriceFrequencySummaryValidationView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
        initAttributes(context, attributeSet);
    }

    public PriceFrequencySummaryValidationView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
        initAttributes(context, attributeSet);
    }

    protected void init(Context context) {
        setOrientation(1);
        inflate(context, 2130903310, this);
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
        this.spinnerArrayAdapter = new ArrayAdapter(context, 17367048, new ArrayList());
        this.spinnerArrayAdapter.setDropDownViewResource(17367049);
        this.priceFrequency.setAdapter(this.spinnerArrayAdapter);
        this.editText.setOnClickListener(PriceFrequencySummaryValidationView$$Lambda$1.lambdaFactory$(this));
        this.editText.setOnFocusChangeListener(PriceFrequencySummaryValidationView$$Lambda$2.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$init$0(View view) {
        requestFocus();
    }

    /* synthetic */ void lambda$init$1(View view, boolean z) {
        setStrokeHeight(z);
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

    @OnItemSelected({2131624783})
    void selected(int i) {
        this.spinnerSelectedListener.onSpinnerValueSelected((String) this.priceFrequency.getItemAtPosition(i));
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

    public void showValue(@Nullable String str, @NonNull List<String> list) {
        if (str == null) {
            str = DEFAULT_VALUE;
        }
        if (!str.equals(getValue())) {
            setValue(str);
            this.editText.setText(str);
        }
        this.spinnerArrayAdapter.clear();
        this.spinnerArrayAdapter.addAll(list);
    }

    public void setSelectedFrequencyValue(@Nullable String str) {
        if (str != null) {
            int position = this.spinnerArrayAdapter.getPosition(StringUtils.capitalize(str.toLowerCase()));
            if (position >= 0) {
                this.priceFrequency.setSelection(position);
                return;
            }
            return;
        }
        this.priceFrequency.setSelection(0);
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

    public void setOnSpinnerItemSelectedListener(OnSpinnerItemSelectedListener onSpinnerItemSelectedListener) {
        if (onSpinnerItemSelectedListener == null) {
            onSpinnerItemSelectedListener = PriceFrequencySummaryValidationView$$Lambda$3.lambdaFactory$();
        }
        this.spinnerSelectedListener = onSpinnerItemSelectedListener;
    }

    static /* synthetic */ void lambda$setOnSpinnerItemSelectedListener$2(String str) {
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
    }

    public boolean isFocused() {
        return super.isFocused();
    }

    public void onFocusChanged(OnFocusChangeListener onFocusChangeListener) {
        this.editText.setOnFocusChangeListener(onFocusChangeListener);
    }

    private void updateEditTextBackground(boolean z) {
        if (z) {
            this.stroke.setBackgroundColor(getResources().getColor(17170455));
        } else {
            this.stroke.setBackgroundColor(getResources().getColor(2131492910));
        }
        this.editText.setPadding(this.paddingLeft, this.paddingTop, this.paddingRight, this.paddingBottom);
    }

    private void setStrokeHeight(boolean z) {
        float f;
        if (z) {
            f = FOCUS_STROKE_HEIGHT;
        } else {
            f = IPhotoView.DEFAULT_MIN_SCALE;
        }
        LayoutParams layoutParams = this.stroke.getLayoutParams();
        layoutParams.height = (int) ((f * getContext().getResources().getDisplayMetrics().density) + DEF_FLOAT);
        this.stroke.setLayoutParams(layoutParams);
    }

    public void setFilters(InputFilter[] inputFilterArr) {
        this.editText.setFilters(inputFilterArr);
    }
}
