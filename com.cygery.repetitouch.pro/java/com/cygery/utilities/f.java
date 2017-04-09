/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  java.io.File
 *  java.lang.Object
 *  java.lang.String
 */
package com.cygery.utilities;

import java.io.File;

public class f {
    private static final String a = f.class.getName();

    public static boolean a(String string) {
        return new File(string).exists();
    }
}

