package com.ebay.classifieds.capi.executor;

import java.io.Serializable;

public interface TreeNode extends Serializable {
    Long getId();

    String getLocalizedName();
}
