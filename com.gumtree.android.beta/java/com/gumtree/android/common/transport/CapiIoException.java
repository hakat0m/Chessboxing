package com.gumtree.android.common.transport;

import com.gumtree.android.common.http.model.CapiErrors;
import java.io.IOException;

public class CapiIoException extends IOException {
    private static final long serialVersionUID = 1;
    private final CapiErrors capiErrors;
    private final int statusCode;

    public CapiIoException(int i, CapiErrors capiErrors) {
        super(String.valueOf(i));
        this.statusCode = i;
        this.capiErrors = capiErrors;
    }

    public CapiIoException(String str, int i) {
        super(i + " " + str);
        this.statusCode = i;
        this.capiErrors = new CapiErrors();
    }

    public int statusCode() {
        return this.statusCode;
    }

    public String toString() {
        return "CapiIoException{statusCode=" + this.statusCode + ", capiErrors=" + this.capiErrors + '}';
    }
}
