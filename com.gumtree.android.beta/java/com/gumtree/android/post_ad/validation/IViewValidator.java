package com.gumtree.android.post_ad.validation;

import android.content.res.Resources;
import android.widget.TextView;
import com.gumtree.android.post_ad.model.PostAdData;

public interface IViewValidator {
    void validateStep(TextView textView, Resources resources, PostAdData postAdData, int i);

    boolean validateView(String str, TextView textView, Resources resources, PostAdData postAdData);
}
