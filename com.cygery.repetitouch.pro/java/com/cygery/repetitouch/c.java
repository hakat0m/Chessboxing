/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.util.Pair
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 */
package com.cygery.repetitouch;

import android.util.Pair;
import com.cygery.repetitouch.Event;
import com.cygery.repetitouch.MergeItem;
import com.cygery.repetitouch.b;
import com.cygery.repetitouch.e;
import java.util.ArrayList;

public class c {
    private static final String a = c.class.getName();
    private final ArrayList<Event> b = new ArrayList();
    private long c = 0;

    public long a() {
        return this.c;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public void a(MergeItem var1) {
        if (var1 == null) {
            return;
        }
        var2_2 = var1.a();
        var3_3 = var1.i();
        var5_4 = var1.e();
        var6_5 = var1.f();
        var8_6 = var1.c();
        var9_7 = var1.d();
        var10_8 = b.a();
        if (var3_3 <= 0) return;
        if (var5_4 <= 0) return;
        if (var6_5 <= 0.0) return;
        if (var10_8 == null) return;
        var11_9 = 0;
        while (var11_9 < var5_4) {
            if (var2_2 != MergeItem.Type.TYPE_FILE) ** GOTO lbl31
            var29_19 = (ArrayList)var10_8.a((String)var1.g(), (boolean)false).second;
            if (var29_19 != null) {
                var30_18 = -1 + var29_19.size();
                var31_28 = var8_6 != false ? ((Event)var29_19.get(0)).b() / 1000 : 0;
                var33_10 = var9_7 != false ? ((Event)var29_19.get(var30_18)).b() / 1000 : var3_3;
                var35_26 = (long)((double)(var33_10 - var31_28) / var6_5);
                for (var37_11 = 0; var37_11 <= var30_18; ++var37_11) {
                    var38_24 = new Event((Event)var29_19.get(var37_11));
                    var38_24.setTimeInMicroSeconds((long)((double)(var38_24.b() - 1000 * var31_28) / var6_5));
                    var38_24.a(1000 * this.c);
                    this.b.add((Object)var38_24);
                }
                this.c = var35_26 + this.c;
            }
            ** GOTO lbl61
lbl31: // 1 sources:
            if (var2_2 != MergeItem.Type.TYPE_SUBLIST) ** GOTO lbl49
            var12_27 = var1.h();
            if (var12_27 == null) ** GOTO lbl61
            var13_17 = var1.k();
            var14_14 = var1.l();
            var15_12 = -1 + var12_27.size();
            var16_15 = 0;
            var18_23 = 0;
            if (var13_17 != null) {
                var18_23 = 1 + var13_17.a();
                var16_15 = var13_17.b();
            }
            var19_21 = var14_14 != null ? var14_14.a() : var15_12;
            var20_16 = var16_15 + var3_3;
            if (var8_6) {
                var16_15 = var19_21 >= var18_23 ? ((Event)var12_27.get(var18_23)).b() / 1000 : var20_16;
            }
            if (!var9_7) ** GOTO lbl52
            var22_20 = var19_21 >= var18_23 ? ((Event)var12_27.get(var19_21)).b() / 1000 : var16_15;
            ** GOTO lbl53
lbl49: // 1 sources:
            if (var2_2 == MergeItem.Type.TYPE_PAUSE && !var8_6 && !var9_7) {
                this.c = (long)((double)var3_3 / var6_5) + this.c;
            }
            ** GOTO lbl61
lbl52: // 1 sources:
            var22_20 = var20_16;
lbl53: // 2 sources:
            var24_25 = (long)((double)(var22_20 - var16_15) / var6_5);
            for (var26_13 = var18_23; var26_13 <= var19_21; ++var26_13) {
                var27_22 = new Event((Event)var12_27.get(var26_13));
                var27_22.setTimeInMicroSeconds((long)((double)(var27_22.b() - 1000 * var16_15) / var6_5));
                var27_22.a(1000 * this.c);
                this.b.add((Object)var27_22);
            }
            this.c = var24_25 + this.c;
lbl61: // 4 sources:
            ++var11_9;
        }
    }

    public void a(ArrayList<Event> arrayList, long l2) {
        if (arrayList != null) {
            for (Event event : arrayList) {
                event.a(1000 * this.c);
                this.b.add((Object)event);
            }
        }
        this.c = l2 + this.c;
    }

    public ArrayList<Event> b() {
        return this.b;
    }

    public void c() {
        this.b.clear();
        this.c = 0;
    }
}

