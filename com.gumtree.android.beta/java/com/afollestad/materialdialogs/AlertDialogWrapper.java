package com.afollestad.materialdialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.DialogInterface.OnShowListener;
import android.graphics.drawable.Drawable;
import android.support.annotation.ArrayRes;
import android.support.annotation.AttrRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ListAdapter;
import com.afollestad.materialdialogs.MaterialDialog.ButtonCallback;
import com.afollestad.materialdialogs.MaterialDialog.ListCallback;
import com.afollestad.materialdialogs.MaterialDialog.ListCallbackMultiChoice;
import com.afollestad.materialdialogs.MaterialDialog.ListCallbackSingleChoice;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AlertDialogWrapper {

    public class Builder {
        private final com.afollestad.materialdialogs.MaterialDialog.Builder builder;
        private OnClickListener negativeDialogListener;
        private OnClickListener neutralDialogListener;
        private OnClickListener onClickListener;
        private OnClickListener positiveDialogListener;

        public Builder(@NonNull Context context) {
            this.builder = new com.afollestad.materialdialogs.MaterialDialog.Builder(context);
        }

        public Builder autoDismiss(boolean z) {
            this.builder.autoDismiss(z);
            return this;
        }

        public Builder setMessage(@StringRes int i) {
            this.builder.content(i);
            return this;
        }

        public Builder setMessage(@NonNull CharSequence charSequence) {
            this.builder.content(charSequence);
            return this;
        }

        public Builder setTitle(@StringRes int i) {
            this.builder.title(i);
            return this;
        }

        public Builder setTitle(@NonNull CharSequence charSequence) {
            this.builder.title(charSequence);
            return this;
        }

        public Builder setIcon(@DrawableRes int i) {
            this.builder.iconRes(i);
            return this;
        }

        public Builder setIcon(Drawable drawable) {
            this.builder.icon(drawable);
            return this;
        }

        public Builder setIconAttribute(@AttrRes int i) {
            this.builder.iconAttr(i);
            return this;
        }

        public Builder setNegativeButton(@StringRes int i, OnClickListener onClickListener) {
            this.builder.negativeText(i);
            this.negativeDialogListener = onClickListener;
            return this;
        }

        public Builder setNegativeButton(@NonNull CharSequence charSequence, OnClickListener onClickListener) {
            this.builder.negativeText(charSequence);
            this.negativeDialogListener = onClickListener;
            return this;
        }

        public Builder setPositiveButton(@StringRes int i, OnClickListener onClickListener) {
            this.builder.positiveText(i);
            this.positiveDialogListener = onClickListener;
            return this;
        }

        public Builder setPositiveButton(@NonNull CharSequence charSequence, OnClickListener onClickListener) {
            this.builder.positiveText(charSequence);
            this.positiveDialogListener = onClickListener;
            return this;
        }

        public Builder setNeutralButton(@StringRes int i, OnClickListener onClickListener) {
            this.builder.neutralText(i);
            this.neutralDialogListener = onClickListener;
            return this;
        }

        public Builder setNeutralButton(@NonNull CharSequence charSequence, OnClickListener onClickListener) {
            this.builder.neutralText(charSequence);
            this.neutralDialogListener = onClickListener;
            return this;
        }

        public Builder setCancelable(boolean z) {
            this.builder.cancelable(z);
            return this;
        }

        public Builder setItems(@ArrayRes int i, OnClickListener onClickListener) {
            this.builder.items(i);
            this.onClickListener = onClickListener;
            return this;
        }

        public Builder setItems(CharSequence[] charSequenceArr, OnClickListener onClickListener) {
            this.builder.items(charSequenceArr);
            this.onClickListener = onClickListener;
            return this;
        }

        @Deprecated
        public Builder setAdapter(ListAdapter listAdapter) {
            return setAdapter(listAdapter, null);
        }

        public Builder setAdapter(ListAdapter listAdapter, final OnClickListener onClickListener) {
            this.builder.adapter = listAdapter;
            this.builder.listCallbackCustom = new ListCallback() {
                public void onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {
                    onClickListener.onClick(materialDialog, i);
                }
            };
            return this;
        }

        @UiThread
        public Dialog create() {
            addButtonsCallback();
            addListCallbacks();
            return this.builder.build();
        }

        @UiThread
        public Dialog show() {
            Dialog create = create();
            create.show();
            return create;
        }

        private void addListCallbacks() {
            if (this.onClickListener != null) {
                this.builder.itemsCallback(new ListCallback() {
                    public void onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {
                        Builder.this.onClickListener.onClick(materialDialog, i);
                    }
                });
            }
        }

        private void addButtonsCallback() {
            if (this.positiveDialogListener != null || this.negativeDialogListener != null) {
                this.builder.callback(new ButtonCallback() {
                    public void onNeutral(MaterialDialog materialDialog) {
                        if (Builder.this.neutralDialogListener != null) {
                            Builder.this.neutralDialogListener.onClick(materialDialog, -3);
                        }
                    }

                    public void onPositive(MaterialDialog materialDialog) {
                        if (Builder.this.positiveDialogListener != null) {
                            Builder.this.positiveDialogListener.onClick(materialDialog, -1);
                        }
                    }

                    public void onNegative(MaterialDialog materialDialog) {
                        if (Builder.this.negativeDialogListener != null) {
                            Builder.this.negativeDialogListener.onClick(materialDialog, -2);
                        }
                    }
                });
            }
        }

        public Builder setView(@NonNull View view) {
            this.builder.customView(view, false);
            return this;
        }

        public Builder setMultiChoiceItems(@ArrayRes int i, @Nullable boolean[] zArr, OnMultiChoiceClickListener onMultiChoiceClickListener) {
            this.builder.items(i);
            setUpMultiChoiceCallback(zArr, onMultiChoiceClickListener);
            return this;
        }

        public Builder setMultiChoiceItems(@NonNull String[] strArr, @Nullable boolean[] zArr, OnMultiChoiceClickListener onMultiChoiceClickListener) {
            this.builder.items((CharSequence[]) strArr);
            setUpMultiChoiceCallback(zArr, onMultiChoiceClickListener);
            return this;
        }

        public Builder alwaysCallSingleChoiceCallback() {
            this.builder.alwaysCallSingleChoiceCallback();
            return this;
        }

        public Builder alwaysCallMultiChoiceCallback() {
            this.builder.alwaysCallMultiChoiceCallback();
            return this;
        }

        private void setUpMultiChoiceCallback(@Nullable final boolean[] zArr, final OnMultiChoiceClickListener onMultiChoiceClickListener) {
            Integer[] numArr = null;
            if (zArr != null) {
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < zArr.length; i++) {
                    if (zArr[i]) {
                        arrayList.add(Integer.valueOf(i));
                    }
                }
                numArr = (Integer[]) arrayList.toArray(new Integer[arrayList.size()]);
            }
            this.builder.itemsCallbackMultiChoice(numArr, new ListCallbackMultiChoice() {
                public boolean onSelection(MaterialDialog materialDialog, Integer[] numArr, CharSequence[] charSequenceArr) {
                    List asList = Arrays.asList(numArr);
                    if (zArr != null) {
                        for (int i = 0; i < zArr.length; i++) {
                            boolean z = zArr[i];
                            zArr[i] = asList.contains(Integer.valueOf(i));
                            if (z != zArr[i]) {
                                onMultiChoiceClickListener.onClick(materialDialog, i, zArr[i]);
                            }
                        }
                    }
                    return true;
                }
            });
        }

        public Builder setSingleChoiceItems(@NonNull String[] strArr, int i, final OnClickListener onClickListener) {
            this.builder.items((CharSequence[]) strArr);
            this.builder.itemsCallbackSingleChoice(i, new ListCallbackSingleChoice() {
                public boolean onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {
                    onClickListener.onClick(materialDialog, i);
                    return true;
                }
            });
            return this;
        }

        public Builder setSingleChoiceItems(@ArrayRes int i, int i2, final OnClickListener onClickListener) {
            this.builder.items(i);
            this.builder.itemsCallbackSingleChoice(i2, new ListCallbackSingleChoice() {
                public boolean onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {
                    onClickListener.onClick(materialDialog, i);
                    return true;
                }
            });
            return this;
        }

        public Builder setOnCancelListener(@NonNull OnCancelListener onCancelListener) {
            this.builder.cancelListener(onCancelListener);
            return this;
        }

        public Builder setOnDismissListener(@NonNull OnDismissListener onDismissListener) {
            this.builder.dismissListener(onDismissListener);
            return this;
        }

        public Builder setOnShowListener(@NonNull OnShowListener onShowListener) {
            this.builder.showListener(onShowListener);
            return this;
        }

        public Builder setOnKeyListener(@NonNull OnKeyListener onKeyListener) {
            this.builder.keyListener(onKeyListener);
            return this;
        }
    }
}
