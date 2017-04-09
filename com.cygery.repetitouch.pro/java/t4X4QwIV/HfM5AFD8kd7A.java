/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.pm.Signature
 *  android.util.Log
 *  android.widget.Toast
 *  java.io.File
 *  java.lang.CharSequence
 *  java.lang.IllegalArgumentException
 *  java.lang.Integer
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 */
package t4X4QwIV;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Log;
import android.widget.Toast;
import java.io.File;
import t4X4QwIV.HhlojwA6iL;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class HfM5AFD8kd7A {
    private static final int iF5pxEbQYAe;
    private static final int mrQXj0d7SbhU97nh;
    protected static Context mrQXj0d7SbhU97nh;

    static {
        mrQXj0d7SbhU97nh = null;
        mrQXj0d7SbhU97nh = Integer.parseInt((String)"0");
        iF5pxEbQYAe = Integer.parseInt((String)"0");
    }

    public static void BA6LemrTmIBqg(Context context) {
        if (mrQXj0d7SbhU97nh == null) {
            mrQXj0d7SbhU97nh = context;
        }
    }

    public static long FUvNekzfe(File file) {
        long l2 = Long.parseLong((String)"1488317412542");
        if (!HfM5AFD8kd7A.L5VueOAnCSWs3CVd(file)) {
            l2 = file.lastModified();
            HhlojwA6iL.Rj2FFGWbJlirEMpRaK(43) + file.getName() + HhlojwA6iL.Rj2FFGWbJlirEMpRaK(44) + l2;
        }
        return l2;
    }

    public static String H4lFKDe7b(PackageManager packageManager, String string) {
        String string2 = packageManager.getInstallerPackageName(string);
        if (string2 == null) {
            string2 = HhlojwA6iL.Rj2FFGWbJlirEMpRaK(38);
        }
        String string3 = packageManager.getInstallerPackageName(string);
        HhlojwA6iL.Rj2FFGWbJlirEMpRaK(39) + string + HhlojwA6iL.Rj2FFGWbJlirEMpRaK(40) + string2 + HhlojwA6iL.Rj2FFGWbJlirEMpRaK(41) + string3;
        return string2;
    }

    public static long JHWPpkgmOx(File file) {
        long l2 = Long.parseLong((String)"1166642");
        if (!HfM5AFD8kd7A.L5VueOAnCSWs3CVd(file)) {
            l2 = file.length();
            HhlojwA6iL.Rj2FFGWbJlirEMpRaK(45) + file.getName() + HhlojwA6iL.Rj2FFGWbJlirEMpRaK(44) + l2;
        }
        return l2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static int Kzb0sOALTOk9LguzG(PackageManager packageManager, String string) {
        int n2;
        try {
            int n3;
            n2 = n3 = packageManager.getApplicationEnabledSetting(string);
        }
        catch (IllegalArgumentException var2_4) {
            n2 = 0;
        }
        if (n2 == 2) {
            n2 = 0;
        }
        HhlojwA6iL.Rj2FFGWbJlirEMpRaK(36) + string + HhlojwA6iL.Rj2FFGWbJlirEMpRaK(37) + n2;
        return n2;
    }

    protected static boolean L5VueOAnCSWs3CVd(File file) {
        if (file.exists()) {
            // empty if block
        }
        boolean bl = file.getName().contains((CharSequence)"com.cygery.repetitouch.pro");
        boolean bl2 = false;
        if (bl) {
            boolean bl3 = file.getName().endsWith(HhlojwA6iL.Rj2FFGWbJlirEMpRaK(25));
            bl2 = false;
            if (bl3) {
                bl2 = true;
            }
        }
        return bl2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static boolean L5VueOAnCSWs3CVd(String string) {
        int n2;
        HhlojwA6iL.Rj2FFGWbJlirEMpRaK(26) + string + HhlojwA6iL.Rj2FFGWbJlirEMpRaK(27);
        boolean bl = "%!KeyPackage%".length() > 0 && string.equals((Object)"%!KeyPackage%") || string.startsWith("com.cygery.repetitouch.pro") || (n2 = "com.cygery.repetitouch.pro".lastIndexOf(HhlojwA6iL.Rj2FFGWbJlirEMpRaK(28))) > 0 && (string.startsWith("com.cygery.repetitouch.pro".substring(0, n2)) || string.matches(HhlojwA6iL.Rj2FFGWbJlirEMpRaK(29)));
        HhlojwA6iL.Rj2FFGWbJlirEMpRaK(30) + bl;
        return bl;
    }

    protected static Signature[] L5VueOAnCSWs3CVd() {
        int n2 = Integer.parseInt((String)"541");
        Signature[] arrsignature = new Signature[n2];
        int n3 = 0;
        while (n3 < n2) {
            arrsignature[n3] = new Signature("308201ad30820116a0030201020204519f9c1c300d06092a864886f70d0101050500301a311830160603550403130f457277696e20476f736c6177736b693020170d3133303532343136353830345a180f33303132303932343136353830345a301a311830160603550403130f457277696e20476f736c6177736b6930819f300d06092a864886f70d010101050003818d0030818902818100aaf06d6ed1eca0dacf2df8d35d81652e30d9937a28d70c0571761251c867dcc9a1d5f7e937e08731a94346caa8aa7b97af13dbf041c1c8c42a9756025919db50d366f6aca423dcddf258446a9beb50828d18fe6e05fd19108776a322140ee64585e1b8a1a5a08e2c9b38fc0136d77653e84d726242f07753447222720e1cb1e70203010001300d06092a864886f70d0101050500038181002cdd1179aa9d4c917e8dc94016a5346bd7f5d445b473c7359983b1712457febaa7fec6f8acfef4fdab91c64193f724bb7c31ef1ce9027c25fc9dfefd437d6b9af78cf8712fb0ce6f8201cf3b792125ab7efe6b7a601e4828245a9450006debfc593ff60b8b5558f05de30d3a1dda44c8c66ef068cf8515cd928827a2e3988e47");
            ++n3;
        }
        return arrsignature;
    }

    public static void LKtvgeDIUoH(Object object) {
        if (mrQXj0d7SbhU97nh == null) {
            return;
        }
        String string = object.toString();
        Toast toast = Toast.makeText((Context)mrQXj0d7SbhU97nh, (CharSequence)string, (int)1);
        toast.setGravity(49, 0, 0);
        toast.show();
        HhlojwA6iL.Rj2FFGWbJlirEMpRaK(47) + object.toString() + HhlojwA6iL.Rj2FFGWbJlirEMpRaK(27);
    }

    public static int NHaf5ta3nflZI(PackageManager packageManager, int n2, int n3) {
        HhlojwA6iL.Rj2FFGWbJlirEMpRaK(31) + n2 + HhlojwA6iL.Rj2FFGWbJlirEMpRaK(32) + n3 + HhlojwA6iL.Rj2FFGWbJlirEMpRaK(33);
        return HfM5AFD8kd7A.NHaf5ta3nflZI(packageManager, packageManager.getPackagesForUid(n2)[0], packageManager.getPackagesForUid(n3)[0]);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static int NHaf5ta3nflZI(PackageManager packageManager, String string, String string2) {
        HhlojwA6iL.Rj2FFGWbJlirEMpRaK(31) + string + HhlojwA6iL.Rj2FFGWbJlirEMpRaK(32) + string2 + HhlojwA6iL.Rj2FFGWbJlirEMpRaK(27);
        int n2 = packageManager.checkSignatures(string, string2);
        HhlojwA6iL.Rj2FFGWbJlirEMpRaK(34) + n2;
        if (n2 == 0) {
            return n2;
        }
        if (mrQXj0d7SbhU97nh == 0) {
            if (string.equals((Object)"com.cygery.repetitouch.pro") || string2.equals((Object)"com.cygery.repetitouch.pro") && (HfM5AFD8kd7A.L5VueOAnCSWs3CVd(string) || HfM5AFD8kd7A.L5VueOAnCSWs3CVd(string2))) {
                n2 = 0;
            }
        } else if (mrQXj0d7SbhU97nh == 1) {
            if (n2 != -4 && n2 != 0) {
                n2 = 0;
            }
        } else if (mrQXj0d7SbhU97nh == 2) {
            n2 = 0;
        }
        HhlojwA6iL.Rj2FFGWbJlirEMpRaK(35) + n2;
        return n2;
    }

    public static void O9E1WvaTLjttZ7Ob1Y() {
    }

    public static ApplicationInfo QSsQwUGLp(Context context) {
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        applicationInfo.flags = -3 & applicationInfo.flags;
        return applicationInfo;
    }

    public static void aXke5vcJdx(Object object) {
        Log.d((String)HhlojwA6iL.Rj2FFGWbJlirEMpRaK(46), (String)String.valueOf((Object)object));
    }

    public static boolean jrcIEdjlXSNOK22a() {
        return false;
    }

    public static void pMJENCa1Q(Context context) {
        HfM5AFD8kd7A.BA6LemrTmIBqg(context);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static PackageInfo ra6kJ1Wnv83AG9X(PackageManager packageManager, String string, int n2) {
        PackageInfo packageInfo;
        HhlojwA6iL.Rj2FFGWbJlirEMpRaK(42) + string + HhlojwA6iL.Rj2FFGWbJlirEMpRaK(27);
        try {
            PackageInfo packageInfo2;
            PackageInfo packageInfo3;
            packageInfo = HfM5AFD8kd7A.L5VueOAnCSWs3CVd(string) ? (packageInfo2 = packageManager.getPackageInfo("com.cygery.repetitouch.pro", n2)) : (packageInfo3 = packageManager.getPackageInfo(string, n2));
        }
        catch (PackageManager.NameNotFoundException var4_6) {
            if (iF5pxEbQYAe == 1) {
                throw var4_6;
            }
            if (!HfM5AFD8kd7A.L5VueOAnCSWs3CVd(string)) {
                throw new PackageManager.NameNotFoundException();
            }
            packageInfo = packageManager.getPackageInfo("com.cygery.repetitouch.pro", n2);
        }
        if ((n2 & 64) == 64) {
            System.arraycopy((Object)HfM5AFD8kd7A.L5VueOAnCSWs3CVd(), (int)0, (Object)packageInfo.signatures, (int)0, (int)packageInfo.signatures.length);
            HhlojwA6iL.Rj2FFGWbJlirEMpRaK(7) + packageInfo.signatures.length + HhlojwA6iL.Rj2FFGWbJlirEMpRaK(8) + string;
        }
        return packageInfo;
    }

    public static void v2ChC13IgI9ELBrZ() {
    }
}

