package com.gumtree.android.postad.validation;

import android.content.Context;
import android.support.annotation.NonNull;
import javax.inject.Inject;

public class PostAdValidationMessages implements PostAdValidation {
    private Context context;

    @Inject
    public PostAdValidationMessages(@NonNull Context context) {
        this.context = context;
    }

    public String descriptionWordAmount() {
        return this.context.getResources().getString(2131165421);
    }

    public String descriptionUrls() {
        return this.context.getResources().getString(2131165420);
    }

    public String descriptionEmpty() {
        return this.context.getResources().getString(2131165419);
    }

    public String titleSize() {
        return this.context.getResources().getString(2131165930);
    }

    public String titleEmpty() {
        return this.context.getResources().getString(2131165923);
    }

    public String categoryEmpty() {
        return this.context.getResources().getString(2131165373);
    }

    public String locationEmpty() {
        return this.context.getResources().getString(2131165541);
    }

    public String priceEmpty() {
        return this.context.getResources().getString(2131165732);
    }

    public String priceSize() {
        return this.context.getResources().getString(2131165733);
    }

    public String missingDetails() {
        return this.context.getResources().getString(2131165642);
    }

    public String contactEmpty() {
        return this.context.getResources().getString(2131165382);
    }

    public String required() {
        return this.context.getResources().getString(2131165761);
    }
}
