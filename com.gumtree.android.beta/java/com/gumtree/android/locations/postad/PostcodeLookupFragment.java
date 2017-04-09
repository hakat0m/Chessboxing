package com.gumtree.android.locations.postad;

import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import com.ebay.classifieds.capi.locations.Location;
import com.gumtree.android.common.fragments.BaseFragment;

public class PostcodeLookupFragment extends BaseFragment {
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        initVisibleOnMap();
        initLocationBreadcrumb();
        initPostcodeLookup();
        initFreeText();
    }

    private void initFreeText() {
        if (getParentActivity().getPostcode() == null || getParentActivity().getPostcode().length() <= 0) {
            getViewById(2131624558).setVisibility(8);
            getViewById(16908295).setVisibility(8);
            return;
        }
        ((EditText) getViewById(2131624558)).setText(getParentActivity().getLocationText());
        getViewById(2131624558).setVisibility(0);
    }

    private void initPostcodeLookup() {
        EditText editText = (EditText) getViewById(2131624555);
        ((Button) getViewById(2131624556)).setOnClickListener(PostcodeLookupFragment$$Lambda$1.lambdaFactory$(this, editText));
        editText.setText(getPostcode());
        editText.addTextChangedListener(new 1(this));
        editText.setOnEditorActionListener(PostcodeLookupFragment$$Lambda$2.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$initPostcodeLookup$0(EditText editText, View view) {
        getParentActivity().setLocationText(null);
        getParentActivity().setVisibleOnMap(null);
        getParentActivity().onQueryTextSubmit(editText.getText().toString());
    }

    /* synthetic */ boolean lambda$initPostcodeLookup$1(TextView textView, int i, KeyEvent keyEvent) {
        if (i != 2) {
            return false;
        }
        getParentActivity().setLocationText(null);
        getParentActivity().setVisibleOnMap(null);
        getParentActivity().onQueryTextSubmit(textView.getText().toString());
        return true;
    }

    private void initVisibleOnMap() {
        if (getParentActivity().isPostcodeValid() && getParentActivity().isVisibleOnMapValid()) {
            getViewById(2131624559).setVisibility(0);
            ((CheckBox) getViewById(2131624559)).setOnCheckedChangeListener(PostcodeLookupFragment$$Lambda$3.lambdaFactory$(this));
            ((CheckBox) getViewById(2131624559)).setChecked(Boolean.valueOf(getVisibleOnMap()).booleanValue());
            return;
        }
        getViewById(2131624559).setVisibility(8);
    }

    /* synthetic */ void lambda$initVisibleOnMap$2(CompoundButton compoundButton, boolean z) {
        getParentActivity().setVisibleOnMap(Boolean.toString(compoundButton.isChecked()));
    }

    private void initLocationBreadcrumb() {
        if (getParentActivity().isLocationNameValid()) {
            getLocationBreadCrumb().setText(getLocationName());
        } else {
            getViewById(2131624557).setVisibility(8);
        }
    }

    private TextView getLocationBreadCrumb() {
        return (TextView) getViewById(2131624557);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(2130903229, viewGroup, false);
    }

    public void populatePostcodeLookup(Location location) {
        initVisibleOnMap();
        if (location != null) {
            getLocationBreadCrumb().setText(Html.fromHtml(normalizedBreadcrumb(location.getLocationBreadcrumb())));
            ((EditText) getViewById(2131624558)).setText(getParentActivity().getLocationText());
            setVisiblity(0);
            hideKeyboard();
            return;
        }
        getLocationBreadCrumb().setText(null);
        ((EditText) getViewById(2131624558)).setText(null);
        setVisiblity(8);
    }

    public PostAdLocationActivity getParentActivity() {
        return (PostAdLocationActivity) getActivity();
    }

    public String getPostcode() {
        return getParentActivity().getPostcode();
    }

    public String getVisibleOnMap() {
        return getParentActivity().getVisibleOnMap();
    }

    public String getLocationName() {
        return getParentActivity().getLocationName();
    }

    public String getLocationFreeText() {
        return ((EditText) getViewById(2131624558)).getText().toString();
    }

    public boolean getVisibleOnMapCheckbox() {
        return ((CheckBox) getViewById(2131624559)).isChecked();
    }

    public void setVisiblity(int i) {
        getViewById(2131624557).setVisibility(i);
        getViewById(2131624558).setVisibility(i);
        getViewById(2131624559).setVisibility(i);
        getViewById(16908295).setVisibility(i);
        getViewById(2131624560).setVisibility(i);
    }

    public Button getDoneButton() {
        return (Button) getView().findViewById(2131624560);
    }

    private View getViewById(int i) {
        return getView().findViewById(i);
    }

    public String normalizedBreadcrumb(String str) {
        String[] strArr;
        StringBuilder stringBuilder = new StringBuilder();
        if (str == null || str.length() <= 0) {
            strArr = null;
        } else {
            strArr = str.split(";");
        }
        if (strArr != null && strArr.length > 0) {
            String str2 = " > ";
            for (int i = 1; i < strArr.length; i++) {
                if (i == strArr.length - 1) {
                    stringBuilder.append("<b>").append(strArr[i]).append("</b>");
                } else {
                    stringBuilder.append(strArr[i]).append(str2);
                }
            }
        }
        return stringBuilder.toString();
    }
}
