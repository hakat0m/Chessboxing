package com.gumtree.android.vip.reply;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.ebay.classifieds.capi.executor.Result;
import com.gumtree.android.api.executor.RequestLoader;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.category.NewPostAdCategoryActivity;
import com.gumtree.android.common.activities.StatefulActivity;
import com.gumtree.android.common.contracts.EmailReply;
import com.gumtree.android.common.fragments.BaseFragment;
import com.gumtree.android.common.providers.AppProvider;
import com.gumtree.android.dagger.ComponentsManager;
import com.gumtree.android.reply.ReplyMetadataField;
import com.gumtree.android.reply.ReplyMetadataField.ReplyMetadataType;
import com.gumtree.android.vip.reply.api.MetadataRequest;
import com.gumtree.android.vip.reply.ui.BooleanLayout;
import com.gumtree.android.vip.reply.ui.ReplyFieldViewWrapper;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import javax.inject.Inject;

public class ReplyToVIPFragment extends BaseFragment implements LoaderCallbacks<Result<List<ReplyMetadataField>>> {
    private static final String REPLY_FIELDS = "reply_fields";
    @Inject
    BaseAccountManager accountManager;
    private String advertTitle;
    private final OnEditorActionListener editorActionListener = ReplyToVIPFragment$$Lambda$1.lambdaFactory$(this);
    private EmailReply emailReply;
    private HashMap<String, ReplyMetadataField> replyFields;
    private String replyTemplate;
    @Bind({2131624577})
    TextView title;
    private long vipId;

    /* synthetic */ boolean lambda$new$0(TextView textView, int i, KeyEvent keyEvent) {
        switch (textView.getId()) {
            case 2131624732:
                if (i == 4) {
                    sendEmailReply();
                }
                return true;
            default:
                return false;
        }
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ComponentsManager.get().getAppComponent().inject(this);
        if (isGumtreeAuthority(activity.getIntent().getData())) {
            initContent(activity);
        } else {
            handleAuthorityError(activity);
        }
        this.emailReply = (EmailReply) activity;
    }

    private boolean isGumtreeAuthority(Uri uri) {
        return uri != null && uri.getAuthority().equals(AppProvider.AUTHORITY);
    }

    private void initContent(Activity activity) {
        this.vipId = Long.parseLong(activity.getIntent().getData().getLastPathSegment());
        this.replyTemplate = activity.getIntent().getStringExtra(StatefulActivity.EXTRA_REPLY_TEMPLATE);
        this.advertTitle = activity.getIntent().getStringExtra(NewPostAdCategoryActivity.EXTRA_TITLE);
    }

    private void handleAuthorityError(Activity activity) {
        Toast.makeText(this.context, getString(2131165827), 0).show();
        activity.finish();
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        if (bundle != null) {
            this.replyFields = (HashMap) bundle.getSerializable(REPLY_FIELDS);
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(2130903233, viewGroup, false);
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        ButterKnife.bind(this, view);
        this.replyFields = new HashMap();
        this.title.setText(this.advertTitle);
        getLoaderManager().restartLoader(0, null, this);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        populateValueField();
        bundle.putSerializable(REPLY_FIELDS, this.replyFields);
    }

    private void populateValueField() {
        for (String str : this.replyFields.keySet()) {
            View findViewWithTag = getView().findViewWithTag(str);
            if (findViewWithTag != null) {
                ReplyMetadataField replyMetadataField = (ReplyMetadataField) this.replyFields.get(str);
                if (ReplyMetadataType.BOOLEAN == replyMetadataField.getType()) {
                    replyMetadataField.setValue(((BooleanLayout) findViewWithTag).isChecked());
                } else if ("reply-from-email".equals(replyMetadataField.getId())) {
                    replyMetadataField.setValue(((TextView) findViewWithTag).getText().toString().trim());
                } else {
                    replyMetadataField.setValue(((TextView) findViewWithTag).getText().toString());
                }
                this.replyFields.put(str, replyMetadataField);
            }
        }
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(2131755015, menu);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 2131624929:
                sendEmailReply();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private void sendEmailReply() {
        clearErrors();
        if (!checkEmptinessField()) {
            populateValueField();
            this.emailReply.sendReply(this.replyFields.values());
        }
    }

    private boolean checkEmptinessField() {
        boolean z = false;
        for (String str : this.replyFields.keySet()) {
            boolean z2;
            View findViewWithTag = getView().findViewWithTag(str);
            if (findViewWithTag instanceof TextView) {
                TextView textView = (TextView) findViewWithTag.findViewWithTag(str);
                if (TextUtils.isEmpty(textView.getText().toString().trim()) && ((ReplyMetadataField) this.replyFields.get(str)).isMandatory()) {
                    showErrorMessage(textView, getString(2131165757, new Object[]{textView.getHint().toString().toLowerCase(Locale.UK)}));
                    z2 = true;
                    z = z2;
                }
            }
            z2 = z;
            z = z2;
        }
        return z;
    }

    private void clearErrors() {
        for (String findViewWithTag : this.replyFields.keySet()) {
            View findViewWithTag2 = getView().findViewWithTag(findViewWithTag);
            if (findViewWithTag2 instanceof TextView) {
                ((TextView) findViewWithTag2).setError(null);
            }
        }
    }

    private void showErrorMessage(TextView textView, String str) {
        textView.setError(str);
    }

    public Loader<Result<List<ReplyMetadataField>>> onCreateLoader(int i, Bundle bundle) {
        return new RequestLoader(this.context, new MetadataRequest(this.vipId, this.replyTemplate));
    }

    public void onLoadFinished(Loader<Result<List<ReplyMetadataField>>> loader, Result<List<ReplyMetadataField>> result) {
        if (!result.hasError() && result.getData() != null) {
            for (ReplyMetadataField replyMetadataField : (List) result.getData()) {
                if (replyMetadataField.isVisible()) {
                    ViewGroup viewGroup = (ViewGroup) getView().findViewById(2131624578);
                    ReplyFieldViewWrapper createView = new ReplyFieldFactory(this, null).createView(replyMetadataField, viewGroup);
                    if (createView != null) {
                        if (this.replyFields.isEmpty() || this.replyFields.get(replyMetadataField.getId()) == null) {
                            if (this.accountManager.isUserLoggedIn()) {
                                if (replyMetadataField.getId().equals("reply-from-email")) {
                                    createView.populateField(this.accountManager.getContactEmail());
                                }
                                if (replyMetadataField.getId().equals("username")) {
                                    createView.populateField(this.accountManager.getDisplayName());
                                }
                            }
                            this.replyFields.put(replyMetadataField.getId(), replyMetadataField);
                        } else {
                            createView.populateField(((ReplyMetadataField) this.replyFields.get(replyMetadataField.getId())).getValue());
                        }
                        viewGroup.addView(createView.getView());
                    }
                }
            }
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void onLoaderReset(Loader<Result<List<ReplyMetadataField>>> loader) {
    }
}
