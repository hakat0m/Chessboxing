package com.gumtree.android.api.callisto;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import retrofit2.CallAdapter;
import retrofit2.CallAdapter.Factory;
import retrofit2.Retrofit;

public class RxCallistoErrorHandlingCallAdapterFactory extends Factory {
    private final Factory callAdapterFactory;

    private RxCallistoErrorHandlingCallAdapterFactory(Factory factory) {
        this.callAdapterFactory = factory;
    }

    public static Factory create(Factory factory) {
        return new RxCallistoErrorHandlingCallAdapterFactory(factory);
    }

    public CallAdapter<?> get(Type type, Annotation[] annotationArr, Retrofit retrofit) {
        return new RxCallistoCallAdapterWrapper(retrofit.responseBodyConverter(CallistoErrors.class, new Annotation[0]), this.callAdapterFactory.get(type, annotationArr, retrofit));
    }
}
