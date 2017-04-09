package com.facebook.widget;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import com.apptentive.android.sdk.ApptentiveInternal;
import com.facebook.FacebookException;
import com.facebook.FacebookGraphObjectException;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphObject.Factory;
import com.facebook.model.GraphObjectList;
import com.facebook.model.OpenGraphAction;
import com.facebook.model.OpenGraphObject;
import com.facebook.widget.FacebookDialog.Builder;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

abstract class FacebookDialog$OpenGraphDialogBuilderBase<CONCRETE extends FacebookDialog$OpenGraphDialogBuilderBase<?>> extends Builder<CONCRETE> {
    private OpenGraphAction action;
    private String actionType;
    private boolean dataErrorsFatal;
    private String previewPropertyName;

    @Deprecated
    public FacebookDialog$OpenGraphDialogBuilderBase(Activity activity, OpenGraphAction openGraphAction, String str, String str2) {
        super(activity);
        Validate.notNull(openGraphAction, ApptentiveInternal.PUSH_ACTION);
        Validate.notNullOrEmpty(str, "actionType");
        Validate.notNullOrEmpty(str2, "previewPropertyName");
        if (openGraphAction.getProperty(str2) == null) {
            throw new IllegalArgumentException("A property named \"" + str2 + "\" was not found on the action.  The name of " + "the preview property must match the name of an action property.");
        }
        String type = openGraphAction.getType();
        if (Utility.isNullOrEmpty(type) || type.equals(str)) {
            this.action = openGraphAction;
            this.actionType = str;
            this.previewPropertyName = str2;
            return;
        }
        throw new IllegalArgumentException("'actionType' must match the type of 'action' if it is specified. Consider using OpenGraphDialogBuilderBase(Activity activity, OpenGraphAction action, String previewPropertyName) instead.");
    }

    public FacebookDialog$OpenGraphDialogBuilderBase(Activity activity, OpenGraphAction openGraphAction, String str) {
        super(activity);
        Validate.notNull(openGraphAction, ApptentiveInternal.PUSH_ACTION);
        Validate.notNullOrEmpty(openGraphAction.getType(), "action.getType()");
        Validate.notNullOrEmpty(str, "previewPropertyName");
        if (openGraphAction.getProperty(str) == null) {
            throw new IllegalArgumentException("A property named \"" + str + "\" was not found on the action.  The name of " + "the preview property must match the name of an action property.");
        }
        this.action = openGraphAction;
        this.actionType = openGraphAction.getType();
        this.previewPropertyName = str;
    }

    public CONCRETE setDataErrorsFatal(boolean z) {
        this.dataErrorsFatal = z;
        return this;
    }

    public CONCRETE setImageAttachmentsForAction(List<Bitmap> list) {
        return setImageAttachmentsForAction(list, false);
    }

    public CONCRETE setImageAttachmentsForAction(List<Bitmap> list, boolean z) {
        Validate.containsNoNulls(list, "bitmaps");
        if (this.action == null) {
            throw new FacebookException("Can not set attachments prior to setting action.");
        }
        updateActionAttachmentUrls(addImageAttachments(list), z);
        return this;
    }

    public CONCRETE setImageAttachmentFilesForAction(List<File> list) {
        return setImageAttachmentFilesForAction(list, false);
    }

    public CONCRETE setImageAttachmentFilesForAction(List<File> list, boolean z) {
        Validate.containsNoNulls(list, "bitmapFiles");
        if (this.action == null) {
            throw new FacebookException("Can not set attachments prior to setting action.");
        }
        updateActionAttachmentUrls(addImageAttachmentFiles(list), z);
        return this;
    }

    private void updateActionAttachmentUrls(List<String> list, boolean z) {
        List arrayList;
        List image = this.action.getImage();
        if (image == null) {
            arrayList = new ArrayList(list.size());
        } else {
            arrayList = image;
        }
        for (String str : list) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("url", str);
                if (z) {
                    jSONObject.put("user_generated", true);
                }
                arrayList.add(jSONObject);
            } catch (Throwable e) {
                throw new FacebookException("Unable to attach images", e);
            }
        }
        this.action.setImage(arrayList);
    }

    public CONCRETE setImageAttachmentsForObject(String str, List<Bitmap> list) {
        return setImageAttachmentsForObject(str, list, false);
    }

    public CONCRETE setImageAttachmentsForObject(String str, List<Bitmap> list, boolean z) {
        Validate.notNull(str, "objectProperty");
        Validate.containsNoNulls(list, "bitmaps");
        if (this.action == null) {
            throw new FacebookException("Can not set attachments prior to setting action.");
        }
        updateObjectAttachmentUrls(str, addImageAttachments(list), z);
        return this;
    }

    public CONCRETE setImageAttachmentFilesForObject(String str, List<File> list) {
        return setImageAttachmentFilesForObject(str, list, false);
    }

    public CONCRETE setImageAttachmentFilesForObject(String str, List<File> list, boolean z) {
        Validate.notNull(str, "objectProperty");
        Validate.containsNoNulls(list, "bitmapFiles");
        if (this.action == null) {
            throw new FacebookException("Can not set attachments prior to setting action.");
        }
        updateObjectAttachmentUrls(str, addImageAttachmentFiles(list), z);
        return this;
    }

    void updateObjectAttachmentUrls(String str, List<String> list, boolean z) {
        try {
            OpenGraphObject openGraphObject = (OpenGraphObject) this.action.getPropertyAs(str, OpenGraphObject.class);
            if (openGraphObject == null) {
                throw new IllegalArgumentException("Action does not contain a property '" + str + "'");
            } else if (openGraphObject.getCreateObject()) {
                GraphObjectList createList;
                GraphObjectList image = openGraphObject.getImage();
                if (image == null) {
                    createList = Factory.createList(GraphObject.class);
                } else {
                    createList = image;
                }
                for (String str2 : list) {
                    GraphObject create = Factory.create();
                    create.setProperty("url", str2);
                    if (z) {
                        create.setProperty("user_generated", Boolean.valueOf(true));
                    }
                    createList.add(create);
                }
                openGraphObject.setImage(createList);
            } else {
                throw new IllegalArgumentException("The Open Graph object in '" + str + "' is not marked for creation");
            }
        } catch (FacebookGraphObjectException e) {
            throw new IllegalArgumentException("Property '" + str + "' is not a graph object");
        }
    }

    protected Bundle setBundleExtras(Bundle bundle) {
        putExtra(bundle, "com.facebook.platform.extra.PREVIEW_PROPERTY_NAME", this.previewPropertyName);
        putExtra(bundle, "com.facebook.platform.extra.ACTION_TYPE", this.actionType);
        bundle.putBoolean("com.facebook.platform.extra.DATA_FAILURES_FATAL", this.dataErrorsFatal);
        putExtra(bundle, "com.facebook.platform.extra.ACTION", flattenChildrenOfGraphObject(this.action.getInnerJSONObject()).toString());
        return bundle;
    }

    protected Bundle getMethodArguments() {
        Bundle bundle = new Bundle();
        putExtra(bundle, "PREVIEW_PROPERTY_NAME", this.previewPropertyName);
        putExtra(bundle, "ACTION_TYPE", this.actionType);
        bundle.putBoolean("DATA_FAILURES_FATAL", this.dataErrorsFatal);
        putExtra(bundle, "ACTION", flattenChildrenOfGraphObject(this.action.getInnerJSONObject()).toString());
        return bundle;
    }

    private JSONObject flattenChildrenOfGraphObject(JSONObject jSONObject) {
        try {
            JSONObject jSONObject2 = new JSONObject(jSONObject.toString());
            Iterator keys = jSONObject2.keys();
            while (keys.hasNext()) {
                String str = (String) keys.next();
                if (!str.equalsIgnoreCase("image")) {
                    jSONObject2.put(str, flattenObject(jSONObject2.get(str)));
                }
            }
            return jSONObject2;
        } catch (Throwable e) {
            throw new FacebookException(e);
        }
    }

    private Object flattenObject(Object obj) throws JSONException {
        if (obj == null) {
            return null;
        }
        if (obj instanceof JSONObject) {
            JSONObject jSONObject = (JSONObject) obj;
            if (jSONObject.optBoolean("fbsdk:create_object")) {
                return obj;
            }
            if (jSONObject.has("id")) {
                return jSONObject.getString("id");
            }
            if (jSONObject.has("url")) {
                return jSONObject.getString("url");
            }
            return obj;
        } else if (!(obj instanceof JSONArray)) {
            return obj;
        } else {
            JSONArray jSONArray = (JSONArray) obj;
            JSONArray jSONArray2 = new JSONArray();
            int length = jSONArray.length();
            for (int i = 0; i < length; i++) {
                jSONArray2.put(flattenObject(jSONArray.get(i)));
            }
            return jSONArray2;
        }
    }
}
