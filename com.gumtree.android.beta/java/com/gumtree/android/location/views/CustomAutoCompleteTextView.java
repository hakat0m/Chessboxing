package com.gumtree.android.location.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import com.gumtree.android.location.model.LocationData;

public class CustomAutoCompleteTextView extends AutoCompleteTextView {
    public CustomAutoCompleteTextView(Context context) {
        super(context);
    }

    public CustomAutoCompleteTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public CustomAutoCompleteTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    protected CharSequence convertSelectionToString(Object obj) {
        return ((LocationData) obj).getDisplayName();
    }

    public boolean onKeyPreIme(int i, KeyEvent keyEvent) {
        if (i == 4 && isPopupShowing() && ((InputMethodManager) getContext().getSystemService("input_method")).hideSoftInputFromWindow(findFocus().getWindowToken(), 2)) {
            return true;
        }
        return super.onKeyPreIme(i, keyEvent);
    }
}
