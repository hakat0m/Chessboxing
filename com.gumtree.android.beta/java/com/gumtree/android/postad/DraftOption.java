package com.gumtree.android.postad;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public final class DraftOption implements Serializable {
    public static final String DEFAULT_CURRENCY_CODE = "GBP";
    public static final DraftOption NONE = builder().currencyCode(DEFAULT_CURRENCY_CODE).period(DraftPeriod.DAY).reasons(Collections.emptyList()).build();
    private final double amount;
    @NonNull
    private final String currencyCode;
    private final int duration;
    @NonNull
    private final DraftPeriod period;
    @Nullable
    private final List<String> reasons;

    public String toString() {
        return "DraftOption(amount=" + getAmount() + ", currencyCode=" + getCurrencyCode() + ", duration=" + getDuration() + ", period=" + getPeriod() + ", reasons=" + getReasons() + ")";
    }

    public static DraftOptionBuilder builder() {
        return new DraftOptionBuilder();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof DraftOption)) {
            return false;
        }
        DraftOption draftOption = (DraftOption) obj;
        if (Double.compare(getAmount(), draftOption.getAmount()) != 0) {
            return false;
        }
        String currencyCode = getCurrencyCode();
        String currencyCode2 = draftOption.getCurrencyCode();
        if (currencyCode != null ? !currencyCode.equals(currencyCode2) : currencyCode2 != null) {
            return false;
        }
        if (getDuration() != draftOption.getDuration()) {
            return false;
        }
        DraftPeriod period = getPeriod();
        DraftPeriod period2 = draftOption.getPeriod();
        if (period == null) {
            if (period2 == null) {
                return true;
            }
        } else if (period.equals(period2)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int i = 43;
        long doubleToLongBits = Double.doubleToLongBits(getAmount());
        int i2 = ((int) (doubleToLongBits ^ (doubleToLongBits >>> 32))) + 59;
        String currencyCode = getCurrencyCode();
        i2 = (((currencyCode == null ? 43 : currencyCode.hashCode()) + (i2 * 59)) * 59) + getDuration();
        DraftPeriod period = getPeriod();
        i2 *= 59;
        if (period != null) {
            i = period.hashCode();
        }
        return i2 + i;
    }

    @ConstructorProperties({"amount", "currencyCode", "duration", "period", "reasons"})
    public DraftOption(double d, @NonNull String str, int i, @NonNull DraftPeriod draftPeriod, @Nullable List<String> list) {
        if (str == null) {
            throw new NullPointerException("currencyCode");
        } else if (draftPeriod == null) {
            throw new NullPointerException("period");
        } else {
            this.amount = d;
            this.currencyCode = str;
            this.duration = i;
            this.period = draftPeriod;
            this.reasons = list;
        }
    }

    public double getAmount() {
        return this.amount;
    }

    @NonNull
    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public int getDuration() {
        return this.duration;
    }

    @NonNull
    public DraftPeriod getPeriod() {
        return this.period;
    }

    @Nullable
    public List<String> getReasons() {
        return this.reasons;
    }
}
