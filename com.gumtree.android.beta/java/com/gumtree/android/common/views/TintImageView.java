package com.gumtree.android.common.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.gumtree.android.R;

@Deprecated
public class TintImageView extends ImageView {
    public TintImageView(Context context) {
        super(context);
        init(context, null);
    }

    public TintImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public TintImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        int i = 0;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.TintImageView);
            i = obtainStyledAttributes.getColor(0, 0);
            obtainStyledAttributes.recycle();
        }
        if (i != 0) {
            setColorFilter(i);
        }
    }
}
