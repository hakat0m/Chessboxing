/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 */
package com.cygery.repetitouch;

public class e {
    private static final String a = e.class.getName();
    private int b;
    private long c;
    private String d;

    public e(int n2, long l2) {
        this.b = n2;
        this.c = l2;
    }

    public e(int n2, long l2, String string) {
        super(n2, l2);
        this.d = string;
    }

    public e(e e2) {
        if (e2 == null) {
            return;
        }
        this.b = e2.b;
        this.c = e2.c;
        this.d = e2.d;
    }

    public int a() {
        return this.b;
    }

    public long b() {
        return this.c;
    }

    public String c() {
        return this.d;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) return false;
        if (this.getClass() != object.getClass()) {
            return false;
        }
        e e2 = (e)object;
        if (this.b != e2.b) {
            return false;
        }
        if (this.c == e2.c) return true;
        return false;
    }

    public int hashCode() {
        return 31 * this.b + (int)(this.c ^ this.c >>> 32);
    }
}

