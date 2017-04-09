package com.google.firebase.iid;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.support.v4.util.ArrayMap;
import android.util.Base64;
import android.util.Log;
import com.apptentive.android.sdk.BuildConfig;
import com.apptentive.android.sdk.R;
import com.google.firebase.FirebaseApp;
import java.io.IOException;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class FirebaseInstanceId {
    private static Map<String, FirebaseInstanceId> abO = new ArrayMap();
    private static zze baE;
    private final FirebaseApp baF;
    private final zzd baG;
    private final String baH = zzcwu();

    private FirebaseInstanceId(FirebaseApp firebaseApp, zzd com_google_firebase_iid_zzd) {
        this.baF = firebaseApp;
        this.baG = com_google_firebase_iid_zzd;
        if (this.baH == null) {
            throw new IllegalStateException("IID failing to initialize, FirebaseApp is missing project ID");
        }
        FirebaseInstanceIdService.zza(this.baF.getApplicationContext(), this);
    }

    public static FirebaseInstanceId getInstance() {
        return getInstance(FirebaseApp.getInstance());
    }

    @Keep
    public static synchronized FirebaseInstanceId getInstance(@NonNull FirebaseApp firebaseApp) {
        FirebaseInstanceId firebaseInstanceId;
        synchronized (FirebaseInstanceId.class) {
            firebaseInstanceId = (FirebaseInstanceId) abO.get(firebaseApp.getOptions().getApplicationId());
            if (firebaseInstanceId == null) {
                zzd zzb = zzd.zzb(firebaseApp.getApplicationContext(), null);
                if (baE == null) {
                    baE = new zze(zzb.zzcxa());
                }
                firebaseInstanceId = new FirebaseInstanceId(firebaseApp, zzb);
                abO.put(firebaseApp.getOptions().getApplicationId(), firebaseInstanceId);
            }
        }
        return firebaseInstanceId;
    }

    static String zza(KeyPair keyPair) {
        try {
            byte[] digest = MessageDigest.getInstance("SHA1").digest(keyPair.getPublic().getEncoded());
            digest[0] = (byte) (((digest[0] & 15) + R.styleable.AppCompatTheme_spinnerStyle) & 255);
            return Base64.encodeToString(digest, 0, 8, 11);
        } catch (NoSuchAlgorithmException e) {
            Log.w("FirebaseInstanceId", "Unexpected error, device missing required alghorithms");
            return null;
        }
    }

    static void zza(Context context, zzg com_google_firebase_iid_zzg) {
        com_google_firebase_iid_zzg.zzbna();
        Intent intent = new Intent();
        intent.putExtra("CMD", "RST");
        context.sendBroadcast(FirebaseInstanceIdInternalReceiver.zzh(context, intent));
    }

    static int zzdf(Context context) {
        int i = 0;
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            String valueOf = String.valueOf(e);
            Log.w("FirebaseInstanceId", new StringBuilder(String.valueOf(valueOf).length() + 38).append("Never happens: can't find own package ").append(valueOf).toString());
            return i;
        }
    }

    static String zzdg(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            String valueOf = String.valueOf(e);
            Log.w("FirebaseInstanceId", new StringBuilder(String.valueOf(valueOf).length() + 38).append("Never happens: can't find own package ").append(valueOf).toString());
            return null;
        }
    }

    static void zzdh(Context context) {
        Intent intent = new Intent();
        intent.setPackage(context.getPackageName());
        intent.putExtra("CMD", "SYNC");
        context.sendBroadcast(FirebaseInstanceIdInternalReceiver.zzh(context, intent));
    }

    static String zzej(Context context) {
        return getInstance().baF.getOptions().getApplicationId();
    }

    static String zzz(byte[] bArr) {
        return Base64.encodeToString(bArr, 11);
    }

    public void deleteInstanceId() throws IOException {
        this.baG.zzb("*", "*", null);
        this.baG.zzbmu();
    }

    @WorkerThread
    public void deleteToken(String str, String str2) throws IOException {
        this.baG.zzb(str, str2, null);
    }

    public long getCreationTime() {
        return this.baG.getCreationTime();
    }

    public String getId() {
        return zza(this.baG.zzbmt());
    }

    @Nullable
    public String getToken() {
        String zzcwv = zzcwv();
        if (zzcwv == null) {
            FirebaseInstanceIdService.zzek(this.baF.getApplicationContext());
        }
        return zzcwv;
    }

    @WorkerThread
    public String getToken(String str, String str2) throws IOException {
        return this.baG.getToken(str, str2, null);
    }

    String zzcwu() {
        String gcmSenderId = this.baF.getOptions().getGcmSenderId();
        if (gcmSenderId != null) {
            return gcmSenderId;
        }
        gcmSenderId = this.baF.getOptions().getApplicationId();
        if (!gcmSenderId.startsWith("1:")) {
            return gcmSenderId;
        }
        String[] split = gcmSenderId.split(":");
        if (split.length < 2) {
            return null;
        }
        gcmSenderId = split[1];
        return gcmSenderId.isEmpty() ? null : gcmSenderId;
    }

    @Nullable
    String zzcwv() {
        return this.baG.zzcxa().zzi(BuildConfig.FLAVOR, this.baH, "*");
    }

    String zzcww() throws IOException {
        return getToken(this.baH, "*");
    }

    zze zzcwx() {
        return baE;
    }

    public void zzsf(String str) {
        baE.zzsf(str);
        FirebaseInstanceIdService.zzek(this.baF.getApplicationContext());
    }

    void zzsg(String str) throws IOException {
        if (getToken() == null) {
            throw new IOException("token not available");
        }
        Bundle bundle = new Bundle();
        String str2 = "gcm.topic";
        String valueOf = String.valueOf("/topics/");
        String valueOf2 = String.valueOf(str);
        bundle.putString(str2, valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
        zzd com_google_firebase_iid_zzd = this.baG;
        valueOf = getToken();
        String valueOf3 = String.valueOf("/topics/");
        valueOf2 = String.valueOf(str);
        com_google_firebase_iid_zzd.getToken(valueOf, valueOf2.length() != 0 ? valueOf3.concat(valueOf2) : new String(valueOf3), bundle);
    }

    void zzsh(String str) throws IOException {
        if (getToken() == null) {
            throw new IOException("token not available");
        }
        Bundle bundle = new Bundle();
        String str2 = "gcm.topic";
        String valueOf = String.valueOf("/topics/");
        String valueOf2 = String.valueOf(str);
        bundle.putString(str2, valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
        zzd com_google_firebase_iid_zzd = this.baG;
        valueOf = getToken();
        String valueOf3 = String.valueOf("/topics/");
        valueOf2 = String.valueOf(str);
        com_google_firebase_iid_zzd.zzb(valueOf, valueOf2.length() != 0 ? valueOf3.concat(valueOf2) : new String(valueOf3), bundle);
    }
}
