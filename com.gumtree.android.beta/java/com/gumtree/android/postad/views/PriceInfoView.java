package com.gumtree.android.postad.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.afollestad.materialdialogs.MaterialDialog.Builder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;

public class PriceInfoView extends LinearLayout {
    @Bind({2131624776})
    TextView amount;
    @Nullable
    private List<String> descriptionStrings;
    private OnPriceDetailListener onPriceDetailListener;
    @Bind({2131624774})
    View root;

    public interface OnPriceDetailListener {
        void showPriceInfoDetail(@NonNull PriceInfoView priceInfoView);
    }

    public class DetailDialog extends DialogFragment {
        private static final String MESSAGE = "message";

        @NonNull
        private static DetailDialog newInstance(String str) {
            DetailDialog detailDialog = new DetailDialog();
            Bundle bundle = new Bundle();
            bundle.putString(MESSAGE, str);
            detailDialog.setArguments(bundle);
            return detailDialog;
        }

        private String getMessage() {
            return getArguments().getString(MESSAGE);
        }

        @NonNull
        public Dialog onCreateDialog(Bundle bundle) {
            return new Builder(getActivity()).title(2131165704).autoDismiss(true).positiveText(2131165670).content(getMessage()).build();
        }
    }

    public PriceInfoView(Context context) {
        this(context, null, 0);
    }

    public PriceInfoView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PriceInfoView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    private void init(Context context) {
        inflate(context, 2130903308, this);
        ButterKnife.bind(this);
    }

    public void setOnPriceDetailListener(OnPriceDetailListener onPriceDetailListener) {
        this.onPriceDetailListener = onPriceDetailListener;
    }

    public void setAmount(double d) {
        this.amount.setText(String.format(Locale.UK, "TOTAL \u00a3%.2f", new Object[]{Double.valueOf(d)}));
    }

    public void setDescription(List<String> list) {
        this.descriptionStrings = list == null ? Collections.emptyList() : new ArrayList(list);
    }

    public boolean hasContent() {
        return !CollectionUtils.isEmpty(this.descriptionStrings);
    }

    @NonNull
    public DetailDialog createDetailDialog() {
        String join = StringUtils.join(ListUtils.emptyIfNull(this.descriptionStrings), "\n- ");
        return DetailDialog.newInstance(String.format("- %s", new Object[]{join}));
    }

    @OnClick({2131624777})
    void onShowDescription() {
        if (this.onPriceDetailListener != null) {
            this.onPriceDetailListener.showPriceInfoDetail(this);
        }
    }
}
