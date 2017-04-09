package com.gumtree.android.postad.promote;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import com.ebay.classifieds.capi.LocalisedTextProvider;
import javax.inject.Inject;
import org.apache.commons.lang3.Validate;

public class ResourceStringProvider implements StringProvider {
    private final LocalisedTextProvider common;
    private final Resources resources;

    @Inject
    public ResourceStringProvider(@NonNull LocalisedTextProvider localisedTextProvider, @NonNull Resources resources) {
        this.common = (LocalisedTextProvider) Validate.notNull(localisedTextProvider);
        this.resources = (Resources) Validate.notNull(resources);
    }

    public String unknownError() {
        return this.common.unknownError();
    }

    public String spotlightOnlyWithImages() {
        return this.resources.getString(2131165735);
    }
}
