package com.gumtree.android.api.treebay;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResult {
    private List<ItemSummary> itemSummaries;

    public void setItemSummaries(List<ItemSummary> list) {
        this.itemSummaries = list;
    }

    public List<ItemSummary> getItemSummaries() {
        return this.itemSummaries;
    }
}
