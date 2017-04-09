package com.gumtree.android.postad.views.attribute;

import android.app.DatePickerDialog.OnDateSetListener;
import android.widget.DatePicker;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class CustomDateAttributeView$$Lambda$3 implements OnDateSetListener {
    private final CustomDateAttributeView arg$1;

    private CustomDateAttributeView$$Lambda$3(CustomDateAttributeView customDateAttributeView) {
        this.arg$1 = customDateAttributeView;
    }

    public static OnDateSetListener lambdaFactory$(CustomDateAttributeView customDateAttributeView) {
        return new CustomDateAttributeView$$Lambda$3(customDateAttributeView);
    }

    @Hidden
    public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
        this.arg$1.lambda$showDatePicker$2(datePicker, i, i2, i3);
    }
}
