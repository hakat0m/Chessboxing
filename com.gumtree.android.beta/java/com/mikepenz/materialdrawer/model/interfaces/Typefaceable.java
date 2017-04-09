package com.mikepenz.materialdrawer.model.interfaces;

import android.graphics.Typeface;

public interface Typefaceable<T> {
    Typeface getTypeface();

    T withTypeface(Typeface typeface);
}
