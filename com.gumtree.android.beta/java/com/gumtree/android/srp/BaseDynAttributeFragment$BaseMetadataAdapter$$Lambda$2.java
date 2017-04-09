package com.gumtree.android.srp;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import com.gumtree.android.common.views.fields.AutoCompleteStringField;
import com.gumtree.android.srp.BaseDynAttributeFragment.BaseMetadataAdapter;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class BaseDynAttributeFragment$BaseMetadataAdapter$$Lambda$2 implements OnItemClickListener {
    private final BaseMetadataAdapter arg$1;
    private final AutoCompleteStringField arg$2;

    private BaseDynAttributeFragment$BaseMetadataAdapter$$Lambda$2(BaseMetadataAdapter baseMetadataAdapter, AutoCompleteStringField autoCompleteStringField) {
        this.arg$1 = baseMetadataAdapter;
        this.arg$2 = autoCompleteStringField;
    }

    public static OnItemClickListener lambdaFactory$(BaseMetadataAdapter baseMetadataAdapter, AutoCompleteStringField autoCompleteStringField) {
        return new BaseDynAttributeFragment$BaseMetadataAdapter$$Lambda$2(baseMetadataAdapter, autoCompleteStringField);
    }

    @Hidden
    public void onItemClick(AdapterView adapterView, View view, int i, long j) {
        this.arg$1.lambda$onViewTypeString$1(this.arg$2, adapterView, view, i, j);
    }
}
