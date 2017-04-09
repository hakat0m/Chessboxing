package com.google.firebase.iid;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import com.apptentive.android.sdk.BuildConfig;
import com.gumtree.android.postad.services.converter.PaymentConverter;
import java.io.IOException;
import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;

public class zzd {
    static Map<String, zzd> abO = new HashMap();
    static String abU;
    private static zzg baQ;
    private static zzf baR;
    KeyPair abR;
    String abS = BuildConfig.FLAVOR;
    long abT;
    Context mContext;

    protected zzd(Context context, String str, Bundle bundle) {
        this.mContext = context.getApplicationContext();
        this.abS = str;
    }

    public static synchronized zzd zzb(Context context, Bundle bundle) {
        zzd com_google_firebase_iid_zzd;
        synchronized (zzd.class) {
            String string = bundle == null ? BuildConfig.FLAVOR : bundle.getString("subtype");
            String str = string == null ? BuildConfig.FLAVOR : string;
            Context applicationContext = context.getApplicationContext();
            if (baQ == null) {
                baQ = new zzg(applicationContext);
                baR = new zzf(applicationContext);
            }
            abU = Integer.toString(FirebaseInstanceId.zzdf(applicationContext));
            com_google_firebase_iid_zzd = (zzd) abO.get(str);
            if (com_google_firebase_iid_zzd == null) {
                com_google_firebase_iid_zzd = new zzd(applicationContext, str, bundle);
                abO.put(str, com_google_firebase_iid_zzd);
            }
        }
        return com_google_firebase_iid_zzd;
    }

    public long getCreationTime() {
        if (this.abT == 0) {
            String str = baQ.get(this.abS, "cre");
            if (str != null) {
                this.abT = Long.parseLong(str);
            }
        }
        return this.abT;
    }

    public String getToken(String str, String str2, Bundle bundle) throws IOException {
        Object obj = null;
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw new IOException("MAIN_THREAD");
        }
        Object obj2 = 1;
        String zzi = zzbmx() ? null : baQ.zzi(this.abS, str, str2);
        if (zzi == null) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            if (bundle.getString("ttl") != null) {
                obj2 = null;
            }
            if (!"jwt".equals(bundle.getString("type"))) {
                obj = obj2;
            }
            zzi = zzc(str, str2, bundle);
            if (!(zzi == null || r1 == null)) {
                baQ.zza(this.abS, str, str2, zzi, abU);
            }
        }
        return zzi;
    }

    public void zzb(String str, String str2, Bundle bundle) throws IOException {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw new IOException("MAIN_THREAD");
        }
        baQ.zzj(this.abS, str, str2);
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putString("sender", str);
        if (str2 != null) {
            bundle.putString("scope", str2);
        }
        bundle.putString("subscription", str);
        bundle.putString("delete", PaymentConverter.DEFAULT_PAYMENT_METHOD_ID);
        bundle.putString("X-delete", PaymentConverter.DEFAULT_PAYMENT_METHOD_ID);
        bundle.putString("subtype", BuildConfig.FLAVOR.equals(this.abS) ? str : this.abS);
        String str3 = "X-subtype";
        if (!BuildConfig.FLAVOR.equals(this.abS)) {
            str = this.abS;
        }
        bundle.putString(str3, str);
        baR.zzs(baR.zza(bundle, zzbmt()));
    }

    KeyPair zzbmt() {
        if (this.abR == null) {
            this.abR = baQ.zzki(this.abS);
        }
        if (this.abR == null) {
            this.abT = System.currentTimeMillis();
            this.abR = baQ.zze(this.abS, this.abT);
        }
        return this.abR;
    }

    public void zzbmu() {
        this.abT = 0;
        baQ.zzkj(this.abS);
        this.abR = null;
    }

    boolean zzbmx() {
        String str = baQ.get("appVersion");
        if (str == null || !str.equals(abU)) {
            return true;
        }
        str = baQ.get("lastToken");
        if (str == null) {
            return true;
        }
        return (System.currentTimeMillis() / 1000) - Long.valueOf(Long.parseLong(str)).longValue() > 604800;
    }

    public String zzc(String str, String str2, Bundle bundle) throws IOException {
        if (str2 != null) {
            bundle.putString("scope", str2);
        }
        bundle.putString("sender", str);
        String str3 = BuildConfig.FLAVOR.equals(this.abS) ? str : this.abS;
        if (!bundle.containsKey("legacy.register")) {
            bundle.putString("subscription", str);
            bundle.putString("subtype", str3);
            bundle.putString("X-subscription", str);
            bundle.putString("X-subtype", str3);
        }
        return baR.zzs(baR.zza(bundle, zzbmt()));
    }

    public zzg zzcxa() {
        return baQ;
    }

    public zzf zzcxb() {
        return baR;
    }
}
