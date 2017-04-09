package com.afollestad.materialdialogs;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build.VERSION;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import com.afollestad.materialdialogs.MaterialDialog.ListType;
import com.afollestad.materialdialogs.internal.MDTintHelper;

class DefaultAdapter extends BaseAdapter {
    private final MaterialDialog dialog;
    private final GravityEnum itemGravity;
    @LayoutRes
    private final int layout;

    /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$afollestad$materialdialogs$MaterialDialog$ListType = new int[ListType.values().length];

        static {
            try {
                $SwitchMap$com$afollestad$materialdialogs$MaterialDialog$ListType[ListType.SINGLE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$afollestad$materialdialogs$MaterialDialog$ListType[ListType.MULTI.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    public DefaultAdapter(MaterialDialog materialDialog, @LayoutRes int i) {
        this.dialog = materialDialog;
        this.layout = i;
        this.itemGravity = materialDialog.mBuilder.itemsGravity;
    }

    public boolean hasStableIds() {
        return true;
    }

    public int getCount() {
        return this.dialog.mBuilder.items != null ? this.dialog.mBuilder.items.length : 0;
    }

    public Object getItem(int i) {
        return this.dialog.mBuilder.items[i];
    }

    public long getItemId(int i) {
        return (long) i;
    }

    @SuppressLint({"WrongViewCast"})
    public View getView(int i, View view, ViewGroup viewGroup) {
        View inflate;
        if (view == null) {
            inflate = LayoutInflater.from(this.dialog.getContext()).inflate(this.layout, viewGroup, false);
        } else {
            inflate = view;
        }
        TextView textView = (TextView) inflate.findViewById(R.id.title);
        boolean z;
        switch (AnonymousClass1.$SwitchMap$com$afollestad$materialdialogs$MaterialDialog$ListType[this.dialog.listType.ordinal()]) {
            case HighlightView.GROW_NONE /*1*/:
                RadioButton radioButton = (RadioButton) inflate.findViewById(R.id.control);
                if (this.dialog.mBuilder.selectedIndex == i) {
                    z = true;
                } else {
                    z = false;
                }
                MDTintHelper.setTint(radioButton, this.dialog.mBuilder.widgetColor);
                radioButton.setChecked(z);
                break;
            case HighlightView.GROW_LEFT_EDGE /*2*/:
                CheckBox checkBox = (CheckBox) inflate.findViewById(R.id.control);
                z = this.dialog.selectedIndicesList.contains(Integer.valueOf(i));
                MDTintHelper.setTint(checkBox, this.dialog.mBuilder.widgetColor);
                checkBox.setChecked(z);
                break;
        }
        textView.setText(this.dialog.mBuilder.items[i]);
        textView.setTextColor(this.dialog.mBuilder.itemColor);
        this.dialog.setTypeface(textView, this.dialog.mBuilder.regularFont);
        inflate.setTag(i + ":" + this.dialog.mBuilder.items[i]);
        setupGravity((ViewGroup) inflate);
        if (this.dialog.mBuilder.itemIds != null) {
            if (i < this.dialog.mBuilder.itemIds.length) {
                inflate.setId(this.dialog.mBuilder.itemIds[i]);
            } else {
                inflate.setId(-1);
            }
        }
        if (VERSION.SDK_INT >= 21) {
            ViewGroup viewGroup2 = (ViewGroup) inflate;
            if (viewGroup2.getChildCount() == 2) {
                if (viewGroup2.getChildAt(0) instanceof CompoundButton) {
                    viewGroup2.getChildAt(0).setBackground(null);
                } else if (viewGroup2.getChildAt(1) instanceof CompoundButton) {
                    viewGroup2.getChildAt(1).setBackground(null);
                }
            }
        }
        return inflate;
    }

    @TargetApi(17)
    private void setupGravity(ViewGroup viewGroup) {
        ((LinearLayout) viewGroup).setGravity(this.itemGravity.getGravityInt() | 16);
        if (viewGroup.getChildCount() != 2) {
            return;
        }
        CompoundButton compoundButton;
        TextView textView;
        if (this.itemGravity == GravityEnum.END && !isRTL() && (viewGroup.getChildAt(0) instanceof CompoundButton)) {
            compoundButton = (CompoundButton) viewGroup.getChildAt(0);
            viewGroup.removeView(compoundButton);
            textView = (TextView) viewGroup.getChildAt(0);
            viewGroup.removeView(textView);
            textView.setPadding(textView.getPaddingRight(), textView.getPaddingTop(), textView.getPaddingLeft(), textView.getPaddingBottom());
            viewGroup.addView(textView);
            viewGroup.addView(compoundButton);
        } else if (this.itemGravity == GravityEnum.START && isRTL() && (viewGroup.getChildAt(1) instanceof CompoundButton)) {
            compoundButton = (CompoundButton) viewGroup.getChildAt(1);
            viewGroup.removeView(compoundButton);
            textView = (TextView) viewGroup.getChildAt(0);
            viewGroup.removeView(textView);
            textView.setPadding(textView.getPaddingRight(), textView.getPaddingTop(), textView.getPaddingRight(), textView.getPaddingBottom());
            viewGroup.addView(compoundButton);
            viewGroup.addView(textView);
        }
    }

    @TargetApi(17)
    private boolean isRTL() {
        boolean z = true;
        if (VERSION.SDK_INT < 17) {
            return false;
        }
        if (this.dialog.getBuilder().getContext().getResources().getConfiguration().getLayoutDirection() != 1) {
            z = false;
        }
        return z;
    }
}
