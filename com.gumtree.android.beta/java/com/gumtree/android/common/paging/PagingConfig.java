package com.gumtree.android.common.paging;

public final class PagingConfig {
    private static final int DEFAULT_PAGING_SIZE = 30;
    private static final int DEFAULT_PAGING_THRESHOLD = 5;
    private final int pagingSize;
    private final int pagingThreshold;

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof PagingConfig)) {
            return false;
        }
        PagingConfig pagingConfig = (PagingConfig) obj;
        if (getPagingSize() != pagingConfig.getPagingSize()) {
            return false;
        }
        return getPagingThreshold() == pagingConfig.getPagingThreshold();
    }

    public int hashCode() {
        return ((getPagingSize() + 59) * 59) + getPagingThreshold();
    }

    public String toString() {
        return "PagingConfig(pagingSize=" + getPagingSize() + ", pagingThreshold=" + getPagingThreshold() + ")";
    }

    public int getPagingSize() {
        return this.pagingSize;
    }

    public int getPagingThreshold() {
        return this.pagingThreshold;
    }

    public PagingConfig() {
        this.pagingSize = DEFAULT_PAGING_SIZE;
        this.pagingThreshold = DEFAULT_PAGING_THRESHOLD;
    }

    public PagingConfig(int i, int i2) {
        this.pagingSize = i;
        this.pagingThreshold = i2;
    }
}
