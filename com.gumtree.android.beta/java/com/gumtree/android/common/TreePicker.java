package com.gumtree.android.common;

import com.ebay.classifieds.capi.executor.TreeNode;

public interface TreePicker {
    boolean isTreeSelectionEnabled();

    void onLeafSelect(TreeNode treeNode);

    void onParentSelect(TreeNode treeNode);

    void onTreeSelect(TreeNode treeNode);
}
