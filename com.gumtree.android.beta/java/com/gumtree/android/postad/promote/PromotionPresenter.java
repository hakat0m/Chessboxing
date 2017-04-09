package com.gumtree.android.postad.promote;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.gumtree.android.mvp.Presenter;
import com.gumtree.android.mvp.PresenterView;
import com.gumtree.android.postad.DraftFeature.Type;
import com.gumtree.android.postad.DraftOption;
import java.util.List;

public interface PromotionPresenter extends Presenter<View> {

    public interface View extends PresenterView {
        void cancelOnError(String str);

        void promptInfo(Type type);

        void showFeatures(List<PromotionFeature> list);

        void updateSelection(@NonNull List<PromotionFeature> list);
    }

    void onInfo(Type type);

    void onLoadFeatures(@Nullable String str, String str2, long j, @NonNull List<PromotionFeature> list, @NonNull List<Type> list2, int i, String str3);

    void onRefreshView();

    void onSelectFeature(@NonNull PromotionFeature promotionFeature, boolean z);

    void onSelectOption(@Nullable DraftOption draftOption, @NonNull PromotionFeature promotionFeature);

    void onViewCloses();
}
