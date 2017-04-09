package com.gumtree.android.report.ad;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.OnItemSelected.Callback;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.common.analytics.Track;
import com.gumtree.android.common.fragments.BaseFragment;
import com.gumtree.android.dagger.ComponentsManager;
import com.gumtree.android.report.ad.model.Report;
import com.gumtree.android.report.ad.presenters.ReportAdPresenter;
import com.gumtree.android.report.ad.presenters.ReportAdPresenter.View;
import java.util.List;
import javax.inject.Inject;

public class ReportAdFragment extends BaseFragment implements View {
    @Inject
    BaseAccountManager accountManager;
    @Inject
    Long adId;
    private ReportAdSpinnerAdapter adapter;
    @Bind({2131624582})
    TextView commentsTitle;
    @Bind({2131624583})
    TextView commentsView;
    @Inject
    ReportAdPresenter presenter;
    @Bind({2131624581})
    Spinner reasons;
    @Bind({2131624580})
    TextView reasonsTitle;
    @Bind({2131624579})
    Button sendReportButton;

    @OnClick({2131624579})
    void onSendClick() {
        if (!this.adapter.isEmpty()) {
            Track.eventReportAdAttempt(String.valueOf(this.adId));
            this.presenter.onSendReport(this.adId.longValue(), new Report(this.adapter.getItem(this.reasons.getSelectedItemPosition()).getName(), this.commentsView.getText().toString().trim(), this.accountManager.getUsername()));
        }
    }

    @OnItemSelected({2131624581})
    void onItemSelected(int i) {
        this.presenter.onReasonSelected(i);
    }

    @OnItemSelected(callback = Callback.NOTHING_SELECTED, value = {2131624581})
    void onNothingSelected() {
        this.presenter.onReasonSelected(-1);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.adapter = new ReportAdSpinnerAdapter();
    }

    public android.view.View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(2130903234, viewGroup, false);
    }

    public void onViewCreated(android.view.View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        ButterKnife.bind(this, view);
        this.reasons.setAdapter(this.adapter);
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        ((ReportAdComponent) ComponentsManager.get().getBaseComponent(ReportAdComponent.KEY)).inject(this);
    }

    public void onStart() {
        super.onStart();
        this.presenter.onFetchReasons();
    }

    public void onResume() {
        super.onResume();
        this.presenter.attachView(this);
    }

    public void onPause() {
        this.presenter.detachView();
        super.onPause();
    }

    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        ComponentsManager.get().removeBaseComponent(ReportAdComponent.KEY);
        this.presenter.destroy();
    }

    public void showSendButton(boolean z) {
        this.sendReportButton.setEnabled(z);
    }

    public void showReasons(List<ReportAdReason> list) {
        this.adapter.set(list);
    }

    public void showReportSent(ReportAdResponse reportAdResponse) {
        Toast.makeText(this.context, getString(2131165636), 0).show();
        Track.eventReportAdSuccess(String.valueOf(this.adId));
        finish();
    }

    public void showError(String str) {
        if (this.adId != null) {
            Track.eventReportAdFail(String.valueOf(this.adId));
        }
        showSnackBar(str);
    }
}
