package com.gumtree.android.postad.services.converter;

import android.support.annotation.NonNull;
import com.ebay.classifieds.capi.features.Option;
import com.gumtree.android.postad.DraftOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.Validate;

public class DraftOptionConverter {
    private final DraftPeriodConverter periodConverter;

    @Inject
    public DraftOptionConverter(@NonNull DraftPeriodConverter draftPeriodConverter) {
        this.periodConverter = (DraftPeriodConverter) Validate.notNull(draftPeriodConverter);
    }

    @NonNull
    public List<DraftOption> apiToModel(List<Option> list) {
        List<DraftOption> arrayList = new ArrayList(list.size());
        for (Option apiToModel : ListUtils.emptyIfNull(list)) {
            arrayList.add(apiToModel(apiToModel));
        }
        return arrayList;
    }

    @NonNull
    public DraftOption apiToModel(@NonNull Option option) {
        return DraftOption.builder().currencyCode(option.getPrice().getCurrencyIsoCode().getValue()).amount(option.getPrice().getAmount()).period(this.periodConverter.apiToModel(option.getFeatureDuration().getPeriod())).duration(option.getFeatureDuration().getValue()).reasons(option.getPrice().getReason() != null ? Collections.singletonList(option.getPrice().getReason()) : Collections.emptyList()).build();
    }
}
