package com.gumtree.android.postad.contactdetails.validation;

import android.content.Context;
import android.support.annotation.NonNull;
import javax.inject.Inject;

public class PostAdContactDetailsValidationMessages implements PostAdContactDetailsValidation {
    private Context context;

    @Inject
    public PostAdContactDetailsValidationMessages(@NonNull Context context) {
        this.context = context;
    }

    public String locationEmpty() {
        return this.context.getResources().getString(2131165541);
    }

    public String phoneInvalid() {
        return this.context.getResources().getString(2131165476);
    }

    public String contactDetailsNotSelected() {
        return this.context.getResources().getString(2131165383);
    }
}
