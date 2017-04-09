package com.gumtree.android.postad.promote;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.PluralsRes;
import android.support.annotation.StringRes;
import com.gumtree.android.postad.DraftOption;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration$Builder;
import java.beans.ConstructorProperties;
import java.text.NumberFormat;

final class OptionSpinnerItem {
    @NonNull
    private final NumberFormat currencyFormatter;
    @NonNull
    private final DraftOption option;
    @NonNull
    private final Resources resources;

    @ConstructorProperties({"currencyFormatter", "option", "resources"})
    public OptionSpinnerItem(@NonNull NumberFormat numberFormat, @NonNull DraftOption draftOption, @NonNull Resources resources) {
        if (numberFormat == null) {
            throw new NullPointerException("currencyFormatter");
        } else if (draftOption == null) {
            throw new NullPointerException("option");
        } else if (resources == null) {
            throw new NullPointerException("resources");
        } else {
            this.currencyFormatter = numberFormat;
            this.option = draftOption;
            this.resources = resources;
        }
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof OptionSpinnerItem)) {
            return false;
        }
        OptionSpinnerItem optionSpinnerItem = (OptionSpinnerItem) obj;
        NumberFormat currencyFormatter = getCurrencyFormatter();
        NumberFormat currencyFormatter2 = optionSpinnerItem.getCurrencyFormatter();
        if (currencyFormatter != null ? !currencyFormatter.equals(currencyFormatter2) : currencyFormatter2 != null) {
            return false;
        }
        DraftOption option = getOption();
        DraftOption option2 = optionSpinnerItem.getOption();
        if (option != null ? !option.equals(option2) : option2 != null) {
            return false;
        }
        Resources resources = getResources();
        Resources resources2 = optionSpinnerItem.getResources();
        if (resources == null) {
            if (resources2 == null) {
                return true;
            }
        } else if (resources.equals(resources2)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int i = 43;
        NumberFormat currencyFormatter = getCurrencyFormatter();
        int hashCode = (currencyFormatter == null ? 43 : currencyFormatter.hashCode()) + 59;
        DraftOption option = getOption();
        hashCode = (option == null ? 43 : option.hashCode()) + (hashCode * 59);
        Resources resources = getResources();
        hashCode *= 59;
        if (resources != null) {
            i = resources.hashCode();
        }
        return hashCode + i;
    }

    @NonNull
    public NumberFormat getCurrencyFormatter() {
        return this.currencyFormatter;
    }

    @NonNull
    public DraftOption getOption() {
        return this.option;
    }

    @NonNull
    public Resources getResources() {
        return this.resources;
    }

    public String toString() {
        return getString(2131165745, quantityStringFor(toStringResourceAsPeriod(this.option), this.option.getDuration()), this.currencyFormatter.format(this.option.getAmount()));
    }

    @PluralsRes
    private int toStringResourceAsPeriod(DraftOption draftOption) {
        switch (1.$SwitchMap$com$gumtree$android$postad$DraftPeriod[draftOption.getPeriod().ordinal()]) {
            case HighlightView.GROW_NONE /*1*/:
                return 2131689477;
            case HighlightView.GROW_LEFT_EDGE /*2*/:
                return 2131689476;
            case ImageLoaderConfiguration$Builder.DEFAULT_THREAD_POOL_SIZE /*3*/:
                return 2131689475;
            default:
                return 2131689478;
        }
    }

    private String getString(@StringRes int i, Object... objArr) {
        return this.resources.getString(i, objArr);
    }

    private String quantityStringFor(@PluralsRes int i, int i2) {
        return this.resources.getQuantityString(i, i2, new Object[]{Integer.valueOf(i2)});
    }
}
