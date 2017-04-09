package com.gumtree.android.category.suggest.service;

import android.support.annotation.NonNull;
import com.ebay.classifieds.capi.categories.CategoriesApi;
import com.ebay.classifieds.capi.categories.Category;
import com.gumtree.android.category.model.DraftCategory;
import com.gumtree.android.category.suggest.service.converter.CategoryConverter;
import java.util.List;
import javax.inject.Inject;
import org.apache.commons.lang3.Validate;
import rx.Observable;
import rx.observables.ConnectableObservable;

public class ApiCategoryService implements CategoryService {
    private CategoriesApi api;
    private ConnectableObservable<DraftCategory> cachedSuggestions;
    private final CategoryConverter categoryConverter;

    @Inject
    public ApiCategoryService(@NonNull CategoriesApi categoriesApi, @NonNull CategoryConverter categoryConverter) {
        this.api = (CategoriesApi) Validate.notNull(categoriesApi);
        this.categoryConverter = (CategoryConverter) Validate.notNull(categoryConverter);
    }

    public Observable<DraftCategory> getCategorySuggestions(String str) {
        if (this.cachedSuggestions == null) {
            this.cachedSuggestions = Observable.just(str).filter(ApiCategoryService$$Lambda$1.lambdaFactory$()).flatMap(ApiCategoryService$$Lambda$2.lambdaFactory$(this)).map(ApiCategoryService$$Lambda$3.lambdaFactory$()).filter(ApiCategoryService$$Lambda$4.lambdaFactory$()).flatMap(ApiCategoryService$$Lambda$5.lambdaFactory$()).map(ApiCategoryService$$Lambda$6.lambdaFactory$(this)).replay();
            this.cachedSuggestions.connect();
        }
        return this.cachedSuggestions;
    }

    /* synthetic */ Observable lambda$getCategorySuggestions$1(String str) {
        return this.api.getSuggestions(str);
    }

    static /* synthetic */ Boolean lambda$getCategorySuggestions$3(List list) {
        return Boolean.valueOf(list != null);
    }

    /* synthetic */ DraftCategory lambda$getCategorySuggestions$5(Category category) {
        return this.categoryConverter.apiToModel(category);
    }
}
