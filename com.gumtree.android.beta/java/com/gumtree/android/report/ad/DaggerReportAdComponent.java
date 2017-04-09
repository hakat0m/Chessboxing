package com.gumtree.android.report.ad;

import com.ebay.classifieds.capi.ICapiClient;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.dagger.components.ApplicationComponent;
import com.gumtree.android.report.ad.presenters.GatedReportAdFragmentView_Factory;
import com.gumtree.android.report.ad.presenters.ReportAdPresenter;
import com.gumtree.android.report.ad.services.ReportAdService;
import dagger.MembersInjector;
import dagger.internal.MembersInjectors;
import dagger.internal.ScopedProvider;
import javax.inject.Provider;
import rx.Scheduler;

public final class DaggerReportAdComponent implements ReportAdComponent {
    static final /* synthetic */ boolean $assertionsDisabled = (!DaggerReportAdComponent.class.desiredAssertionStatus());
    private Provider<Scheduler> backgroundSchedulerProvider;
    private Provider<BaseAccountManager> baseAccountManagerProvider;
    private Provider<ReportAdPresenter> provideReportAdPresenterProvider;
    private Provider<ReportAdService> provideReportAdServiceProvider;
    private Provider<Long> provideVipIdProvider;
    private MembersInjector<ReportAdFragment> reportAdFragmentMembersInjector;
    private Provider<ICapiClient> xmlClientProvider;

    public final class Builder {
        private ApplicationComponent applicationComponent;
        private ReportAdModule reportAdModule;

        private Builder() {
        }

        public ReportAdComponent build() {
            if (this.reportAdModule == null) {
                throw new IllegalStateException("reportAdModule must be set");
            } else if (this.applicationComponent != null) {
                return new DaggerReportAdComponent();
            } else {
                throw new IllegalStateException("applicationComponent must be set");
            }
        }

        public Builder reportAdModule(ReportAdModule reportAdModule) {
            if (reportAdModule == null) {
                throw new NullPointerException("reportAdModule");
            }
            this.reportAdModule = reportAdModule;
            return this;
        }

        public Builder applicationComponent(ApplicationComponent applicationComponent) {
            if (applicationComponent == null) {
                throw new NullPointerException("applicationComponent");
            }
            this.applicationComponent = applicationComponent;
            return this;
        }
    }

    private DaggerReportAdComponent(Builder builder) {
        if ($assertionsDisabled || builder != null) {
            initialize(builder);
            return;
        }
        throw new AssertionError();
    }

    public static Builder builder() {
        return new Builder();
    }

    private void initialize(Builder builder) {
        this.baseAccountManagerProvider = new 1(this, builder);
        this.xmlClientProvider = new 2(this, builder);
        this.backgroundSchedulerProvider = new 3(this, builder);
        this.provideReportAdServiceProvider = ScopedProvider.create(ReportAdModule_ProvideReportAdServiceFactory.create(builder.reportAdModule, this.xmlClientProvider, this.backgroundSchedulerProvider));
        this.provideReportAdPresenterProvider = ScopedProvider.create(ReportAdModule_ProvideReportAdPresenterFactory.create(builder.reportAdModule, this.provideReportAdServiceProvider, GatedReportAdFragmentView_Factory.create()));
        this.provideVipIdProvider = ScopedProvider.create(ReportAdModule_ProvideVipIdFactory.create(builder.reportAdModule));
        this.reportAdFragmentMembersInjector = ReportAdFragment_MembersInjector.create(MembersInjectors.noOp(), this.baseAccountManagerProvider, this.provideReportAdPresenterProvider, this.provideVipIdProvider);
    }

    public void inject(ReportAdFragment reportAdFragment) {
        this.reportAdFragmentMembersInjector.injectMembers(reportAdFragment);
    }
}
