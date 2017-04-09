/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.Signature
 *  java.io.File
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.StackTraceElement
 *  java.lang.String
 *  java.lang.System
 *  java.lang.Throwable
 *  java.lang.reflect.Method
 */
package t4X4QwIV;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import java.io.File;
import java.lang.reflect.Method;
import t4X4QwIV.HfM5AFD8kd7A;
import t4X4QwIV.HhlojwA6iL;

public final class QTBJSU7Q7EA {
    /*
     * Enabled aggressive block sorting
     */
    public static Object KSFAAh4cW(Method method, Object object, Object[] arrobject) {
        Object[] arrobject2 = arrobject;
        Object object2 = object;
        Method method2 = method;
        do {
            String string = method2.getName();
            String string2 = object2 != null ? object2.getClass().getName() : method2.getDeclaringClass().getName();
            StackTraceElement[] arrstackTraceElement = new Throwable().getStackTrace();
            StackTraceElement[] arrstackTraceElement2 = new StackTraceElement[arrstackTraceElement.length - 1];
            System.arraycopy((Object)arrstackTraceElement, (int)1, (Object)arrstackTraceElement2, (int)0, (int)arrstackTraceElement2.length);
            if (string2.equals((Object)HhlojwA6iL.Rj2FFGWbJlirEMpRaK(0)) || string2.equals((Object)HhlojwA6iL.Rj2FFGWbJlirEMpRaK(1)) || string2.equals((Object)HhlojwA6iL.Rj2FFGWbJlirEMpRaK(2)) || string2.startsWith(HhlojwA6iL.Rj2FFGWbJlirEMpRaK(3)) && string2.contains((CharSequence)HhlojwA6iL.Rj2FFGWbJlirEMpRaK(4))) {
                if (string.equals((Object)HhlojwA6iL.Rj2FFGWbJlirEMpRaK(5))) {
                    return HfM5AFD8kd7A.H4lFKDe7b((PackageManager)object2, (String)arrobject2[0]);
                }
                if (string.equals((Object)HhlojwA6iL.Rj2FFGWbJlirEMpRaK(6))) {
                    int n2 = (Integer)arrobject2[1];
                    if (string2.equals((Object)HhlojwA6iL.Rj2FFGWbJlirEMpRaK(2))) {
                        return HfM5AFD8kd7A.ra6kJ1Wnv83AG9X((PackageManager)object2, (String)arrobject2[0], n2);
                    }
                    Object object3 = method2.invoke(object2, arrobject2);
                    if ((n2 & 64) == 64) {
                        System.arraycopy((Object)HfM5AFD8kd7A.L5VueOAnCSWs3CVd(), (int)0, (Object)((PackageInfo)object3).signatures, (int)0, (int)((PackageInfo)object3).signatures.length);
                        HhlojwA6iL.Rj2FFGWbJlirEMpRaK(7) + ((PackageInfo)object3).signatures + HhlojwA6iL.Rj2FFGWbJlirEMpRaK(8) + ((PackageInfo)object3).packageName;
                    }
                    return object3;
                }
                if (string.equals((Object)HhlojwA6iL.Rj2FFGWbJlirEMpRaK(9))) {
                    return HfM5AFD8kd7A.Kzb0sOALTOk9LguzG((PackageManager)object2, (String)arrobject2[0]);
                }
                if (!string.equals((Object)HhlojwA6iL.Rj2FFGWbJlirEMpRaK(10))) break;
                if (arrobject2[0].getClass().equals((Object)String.class)) {
                    return HfM5AFD8kd7A.NHaf5ta3nflZI((PackageManager)object2, (String)arrobject2[0], (String)arrobject2[1]);
                }
                return HfM5AFD8kd7A.NHaf5ta3nflZI((PackageManager)object2, (Integer)arrobject2[0], (Integer)arrobject2[1]);
            }
            if (string2.contains((CharSequence)HhlojwA6iL.Rj2FFGWbJlirEMpRaK(11))) {
                if (string.equals((Object)HhlojwA6iL.Rj2FFGWbJlirEMpRaK(12))) {
                    return null;
                }
                if (string.equals((Object)HhlojwA6iL.Rj2FFGWbJlirEMpRaK(13))) {
                    return null;
                }
                if (!string.equals((Object)HhlojwA6iL.Rj2FFGWbJlirEMpRaK(14))) break;
                return true;
            }
            if (string2.equals((Object)HhlojwA6iL.Rj2FFGWbJlirEMpRaK(15))) {
                if (!HfM5AFD8kd7A.L5VueOAnCSWs3CVd((File)object2)) break;
                if (string.equals((Object)HhlojwA6iL.Rj2FFGWbJlirEMpRaK(16))) {
                    return HfM5AFD8kd7A.JHWPpkgmOx((File)object2);
                }
                if (!string.equals((Object)HhlojwA6iL.Rj2FFGWbJlirEMpRaK(17))) break;
                return HfM5AFD8kd7A.FUvNekzfe((File)object2);
            }
            if (string2.equals((Object)HhlojwA6iL.Rj2FFGWbJlirEMpRaK(18))) {
                if (!string.equals((Object)HhlojwA6iL.Rj2FFGWbJlirEMpRaK(19))) break;
                return HfM5AFD8kd7A.QSsQwUGLp((Context)object2);
            }
            if (string2.equals((Object)HhlojwA6iL.Rj2FFGWbJlirEMpRaK(20))) {
                if (!string.equals((Object)HhlojwA6iL.Rj2FFGWbJlirEMpRaK(21))) break;
                return HfM5AFD8kd7A.jrcIEdjlXSNOK22a();
            }
            if (!string2.startsWith(HhlojwA6iL.Rj2FFGWbJlirEMpRaK(22)) || !string2.equals((Object)HhlojwA6iL.Rj2FFGWbJlirEMpRaK(23)) || !string.equals((Object)HhlojwA6iL.Rj2FFGWbJlirEMpRaK(24))) break;
            Method method3 = (Method)object2;
            Object object4 = arrobject2[0];
            arrobject2 = (Object[])arrobject2[1];
            method2 = method3;
            object2 = object4;
        } while (true);
        return method2.invoke(object2, arrobject2);
    }
}

