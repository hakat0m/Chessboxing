package com.mikepenz.materialdrawer.holder;

import android.support.annotation.StringRes;

public class StringHolder extends com.mikepenz.materialize.holder.StringHolder {
    public StringHolder(String str) {
        super(str);
    }

    public StringHolder(@StringRes int i) {
        super(i);
    }
}
