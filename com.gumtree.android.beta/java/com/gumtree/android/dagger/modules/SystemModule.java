package com.gumtree.android.dagger.modules;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gumtree.android.dagger.scope.ApplicationScope;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import rx.Scheduler;
import rx.schedulers.Schedulers;

@Module
public class SystemModule {
    @Provides
    @ApplicationScope
    public SharedPreferences provideSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    @ApplicationScope
    public Resources provideResources(Context context) {
        return context.getResources();
    }

    @Provides
    @ApplicationScope
    public ObjectMapper provideObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    @Provides
    @Named("background")
    @ApplicationScope
    public Scheduler provideBackgroundScheduler() {
        return Schedulers.io();
    }
}
