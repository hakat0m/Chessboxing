package com.ebay.classifieds.capi.categories;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

public interface CategoriesApi {
    public static final String CATEGORY_NAMESPACE = "http://www.ebayclassifiedsgroup.com/schema/category/v1";

    @GET("/categories/guess/{adTitle}")
    Observable<Categories> getSuggestions(@Path("adTitle") String str);
}
