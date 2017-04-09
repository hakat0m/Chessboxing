package com.gumtree.android.post_ad.validation;

import android.content.res.Resources;
import android.text.TextUtils;
import android.widget.TextView;
import com.gumtree.android.category.NewPostAdCategoryActivity;
import com.gumtree.android.post_ad.model.PostAdAttributeItem;
import com.gumtree.android.post_ad.model.PostAdData;
import com.gumtree.android.vip.VIPContactFragment;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewValidator implements IViewValidator {
    private static final int STEP_ONE = 1;
    private static final int STEP_THREE = 3;
    private static final int STEP_TWO = 2;

    public boolean validateView(String str, TextView textView, Resources resources, PostAdData postAdData) {
        if (TextUtils.isEmpty(postAdData.isValueValid(str))) {
            textView.setTextColor(resources.getColor(ValidationStateColor.VALID.getColor()));
            return true;
        }
        textView.setTextColor(resources.getColor(ValidationStateColor.INVALID.getColor()));
        return false;
    }

    public void validateStep(TextView textView, Resources resources, PostAdData postAdData, int i) {
        int color;
        if (isStepValid(i, postAdData)) {
            color = resources.getColor(ValidationStateColor.VALID.getColor());
        } else {
            color = resources.getColor(ValidationStateColor.INVALID.getColor());
        }
        textView.setTextColor(color);
    }

    private boolean isStepValid(int i, PostAdData postAdData) {
        switch (i) {
            case STEP_ONE /*1*/:
                return isStepOneValid(postAdData);
            case STEP_TWO /*2*/:
                return isStepTwoValid(postAdData);
            case STEP_THREE /*3*/:
                return isStepThreeValid(postAdData);
            default:
                return false;
        }
    }

    private boolean isStepThreeValid(PostAdData postAdData) {
        return TextUtils.isEmpty(postAdData.isValueValid("poster-contact-email")) && TextUtils.isEmpty(postAdData.isValueValid("phone"));
    }

    private boolean isStepTwoValid(PostAdData postAdData) {
        return TextUtils.isEmpty(postAdData.isValueValid("price")) && TextUtils.isEmpty(postAdData.isValueValid("description"));
    }

    private boolean isStepOneValid(PostAdData postAdData) {
        boolean isEmpty = TextUtils.isEmpty(postAdData.isValueValid(VIPContactFragment.CATEGORY_ID));
        boolean isEmpty2 = TextUtils.isEmpty(postAdData.isValueValid(NewPostAdCategoryActivity.EXTRA_TITLE));
        boolean isEmpty3 = TextUtils.isEmpty(postAdData.isValueValid("location"));
        boolean z = true;
        for (PostAdAttributeItem key : getListOfAttributes(postAdData)) {
            boolean z2;
            if (TextUtils.isEmpty(postAdData.isValueValid(key.getKey()))) {
                z2 = z;
            } else {
                z2 = false;
            }
            z = z2;
        }
        if (isEmpty && isEmpty2 && isEmpty3 && z) {
            return true;
        }
        return false;
    }

    public List<PostAdAttributeItem> getListOfAttributes(PostAdData postAdData) {
        HashMap attributeMap = postAdData.getAttributeMap();
        List arrayList = new ArrayList();
        for (String str : attributeMap.keySet()) {
            if (((PostAdAttributeItem) attributeMap.get(str)).isAttribute()) {
                arrayList.add(attributeMap.get(str));
            }
        }
        return arrayList;
    }
}
