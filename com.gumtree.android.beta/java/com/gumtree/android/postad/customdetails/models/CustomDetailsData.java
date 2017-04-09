package com.gumtree.android.postad.customdetails.models;

import com.gumtree.android.metadata.model.DraftAdMetadata;
import com.gumtree.android.postad.DraftAdAttribute;
import java.io.Serializable;
import java.util.Map;

public class CustomDetailsData implements Serializable {
    private Map<String, DraftAdAttribute> draftAdAttributesMap;
    private DraftAdMetadata metadata;
    private boolean vrmValidated;

    public CustomDetailsData(DraftAdMetadata draftAdMetadata, Map<String, DraftAdAttribute> map) {
        this.metadata = draftAdMetadata;
        this.draftAdAttributesMap = map;
    }

    public DraftAdMetadata getMetadata() {
        return this.metadata;
    }

    public Map<String, DraftAdAttribute> getDraftAdAttributesMap() {
        return this.draftAdAttributesMap;
    }

    public boolean isVrmValidated() {
        return this.vrmValidated;
    }

    public void setVrmValidated(boolean z) {
        this.vrmValidated = z;
    }
}
