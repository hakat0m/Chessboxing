package com.gumtree.android.report.ad;

import com.ebay.classifieds.capi.ICapiClient;
import com.ebay.classifieds.capi.flags.FlagApi;
import com.gumtree.android.report.ad.presenters.DefaultReportAdPresenter;
import com.gumtree.android.report.ad.presenters.GatedReportAdFragmentView;
import com.gumtree.android.report.ad.presenters.ReportAdPresenter;
import com.gumtree.android.report.ad.services.ApiReportAdService;
import com.gumtree.android.report.ad.services.BackgroundReportAdService;
import com.gumtree.android.report.ad.services.ReportAdService;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

@Module
public class ReportAdModule {
    private final long vipId;

    public ReportAdModule(long j) {
        this.vipId = j;
    }

    @Provides
    @ReportAdScope
    public long provideVipId() {
        return this.vipId;
    }

    @Provides
    @ReportAdScope
    public ReportAdPresenter provideReportAdPresenter(ReportAdService reportAdService, GatedReportAdFragmentView gatedReportAdFragmentView) {
        return new DefaultReportAdPresenter(reportAdService, gatedReportAdFragmentView);
    }

    @Provides
    @ReportAdScope
    public ReportAdService provideReportAdService(@Named("xmlClient") ICapiClient iCapiClient, @Named("background") Scheduler scheduler) {
        return new BackgroundReportAdService(new ApiReportAdService((FlagApi) iCapiClient.api(FlagApi.class)), AndroidSchedulers.mainThread(), scheduler);
    }
}
