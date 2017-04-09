package com.gumtree.android.postad.contactdetails.di;

import com.gumtree.android.postad.contactdetails.DefaultPostAdContactDetailsPresenter;
import com.gumtree.android.postad.contactdetails.GatedPostAdContactDetailsView;
import com.gumtree.android.postad.contactdetails.PostAdContactDetailsPresenter;
import com.gumtree.android.postad.contactdetails.models.ContactDetailsData;
import com.gumtree.android.postad.contactdetails.validation.PostAdContactDetailsValidation;
import com.gumtree.android.postad.contactdetails.validation.PostAdContactDetailsValidationMessages;
import com.gumtree.android.postad.contactdetails.validation.PostAdContactDetailsValidationRules;
import com.gumtree.android.userprofile.UserProfileStatusService;
import com.gumtree.android.userprofile.services.UserService;
import dagger.Module;
import dagger.Provides;

@Module
public class PostAdContactDetailsModule {
    private ContactDetailsData contactDetailsData;

    public PostAdContactDetailsModule(ContactDetailsData contactDetailsData) {
        this.contactDetailsData = contactDetailsData;
    }

    @Provides
    @PostAdContactDetailsScope
    public ContactDetailsData provideContactDetailsData() {
        return this.contactDetailsData;
    }

    @Provides
    @PostAdContactDetailsScope
    public PostAdContactDetailsValidation providePostAdValidation(PostAdContactDetailsValidationMessages postAdContactDetailsValidationMessages) {
        return postAdContactDetailsValidationMessages;
    }

    @Provides
    @PostAdContactDetailsScope
    public PostAdContactDetailsValidationRules providePostAdValidationRules(PostAdContactDetailsValidationMessages postAdContactDetailsValidationMessages) {
        return new PostAdContactDetailsValidationRules(postAdContactDetailsValidationMessages);
    }

    @Provides
    @PostAdContactDetailsScope
    public PostAdContactDetailsPresenter providePostAdContactDetailsPresenter(GatedPostAdContactDetailsView gatedPostAdContactDetailsView, PostAdContactDetailsValidationRules postAdContactDetailsValidationRules, UserService userService, UserProfileStatusService userProfileStatusService) {
        return new DefaultPostAdContactDetailsPresenter(gatedPostAdContactDetailsView, this.contactDetailsData, postAdContactDetailsValidationRules, userService, userProfileStatusService);
    }
}
