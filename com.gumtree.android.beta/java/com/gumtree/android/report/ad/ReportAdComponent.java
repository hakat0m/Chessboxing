package com.gumtree.android.report.ad;

import com.gumtree.android.dagger.components.ApplicationComponent;
import com.gumtree.android.dagger.components.BaseComponent;
import dagger.Component;

@ReportAdScope
@Component(dependencies = {ApplicationComponent.class}, modules = {ReportAdModule.class})
public interface ReportAdComponent extends BaseComponent {
    public static final String KEY = ReportAdComponent.class.getSimpleName();

    void inject(ReportAdFragment reportAdFragment);
}
