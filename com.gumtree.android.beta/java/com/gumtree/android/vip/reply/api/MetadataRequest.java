package com.gumtree.android.vip.reply.api;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import com.ebay.classifieds.capi.ICapiClient;
import com.ebay.classifieds.capi.executor.Request;
import com.ebay.classifieds.capi.executor.Result;
import com.ebay.classifieds.capi.executor.ResultError;
import com.ebay.classifieds.capi.users.cv.CVApi;
import com.ebay.classifieds.capi.users.cv.CVs;
import com.ebay.classifieds.capi.users.cv.StoredCV;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.common.http.CapiClientManager;
import com.gumtree.android.dagger.ComponentsManager;
import com.gumtree.android.model.AdsView;
import com.gumtree.android.reply.ReplyMetadataField;
import com.gumtree.android.reply.ReplyMetadataField.ReplyMetadataType;
import com.gumtree.android.reply.parser.ReplyMetadataParser;
import com.gumtree.android.vip.reply.ui.NoCVLayout;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;

public class MetadataRequest implements Request<List<ReplyMetadataField>> {
    private static final String REQUIRED = "required";
    @Inject
    BaseAccountManager accountManager;
    @Named("xmlClient")
    @Inject
    ICapiClient capiClient;
    @Inject
    CapiClientManager capiClientManager;
    @Inject
    Context context;
    private final String template;
    private final long vipId;

    public MetadataRequest(long j, String str) {
        this.vipId = j;
        this.template = str;
        ComponentsManager.get().getAppComponent().inject(this);
    }

    public Result<List<ReplyMetadataField>> execute() {
        Result<List<ReplyMetadataField>> result = new Result(ResultError.unknown());
        try {
            if (this.template == null) {
                return result;
            }
            Result execute = this.capiClientManager.getCapiClient().get("replies/reply-to-ad/metadata.json").withParam("template", this.template).execute(new ReplyMetadataParser());
            if (execute.hasError()) {
                return result;
            }
            Object updatedFields;
            List list = (List) execute.getData();
            if (hasCvs()) {
                updatedFields = getUpdatedFields(list);
            } else {
                List list2 = list;
            }
            return new Result(updatedFields);
        } catch (Exception e) {
            Exception exception = e;
            exception.printStackTrace();
            return new Result(ResultError.build(exception));
        }
    }

    private List<ReplyMetadataField> getUpdatedFields(List<ReplyMetadataField> list) {
        List<ReplyMetadataField> arrayList = new ArrayList(list);
        ReplyMetadataField replyMetadataFieldForCv = getReplyMetadataFieldForCv(getCVs());
        int positionForOptIn = getPositionForOptIn(arrayList);
        if (positionForOptIn > 0 && this.accountManager.isUserLoggedIn()) {
            arrayList.remove(positionForOptIn);
        }
        arrayList.add(positionForOptIn, replyMetadataFieldForCv);
        return arrayList;
    }

    private int getPositionForOptIn(List<ReplyMetadataField> list) {
        for (int i = 0; i < list.size(); i++) {
            if (((ReplyMetadataField) list.get(i)).getId().equals("opt-in-marketing")) {
                return i;
            }
        }
        return 0;
    }

    @NonNull
    private ReplyMetadataField getReplyMetadataFieldForCv(CVs cVs) {
        ReplyMetadataField replyMetadataField = new ReplyMetadataField();
        replyMetadataField.setId("reply-stored-cv-id");
        replyMetadataField.setType(ReplyMetadataType.BOOLEAN);
        if (cVs == null || cVs.getList().size() == 0) {
            replyMetadataField.setLabel(NoCVLayout.class.getSimpleName());
            replyMetadataField.setWritable(NoCVLayout.class.getSimpleName());
        } else {
            replyMetadataField.setLabel(((StoredCV) cVs.getList().get(0)).getFileName());
            replyMetadataField.setWritable(REQUIRED);
        }
        return replyMetadataField;
    }

    private boolean hasCvs() {
        Cursor query = this.context.getContentResolver().query(AdsView.URI, new String[]{"has_cv_posting"}, "_id=?", new String[]{String.valueOf(this.vipId)}, null);
        boolean z = query.moveToFirst() ? query.getInt(query.getColumnIndex("has_cv_posting")) == 1 : false;
        query.close();
        return z;
    }

    private CVs getCVs() {
        if (this.accountManager.isUserLoggedIn()) {
            return (CVs) ((CVApi) this.capiClient.api(CVApi.class)).storedCvs(this.accountManager.getContactEmail()).toBlocking().first();
        }
        return null;
    }
}
