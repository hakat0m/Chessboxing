package com.google.firebase.iid;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;
import android.util.Log;
import com.google.android.gms.common.util.zzx;
import java.io.File;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class zzg {
    SharedPreferences acs;
    Context zzagf;

    public zzg(Context context) {
        this(context, "com.google.android.gms.appid");
    }

    public zzg(Context context, String str) {
        this.zzagf = context;
        this.acs = context.getSharedPreferences(str, 4);
        String valueOf = String.valueOf(str);
        String valueOf2 = String.valueOf("-no-backup");
        zzkg(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
    }

    private String zzh(String str, String str2, String str3) {
        String valueOf = String.valueOf("|T|");
        return new StringBuilder((((String.valueOf(str).length() + 1) + String.valueOf(valueOf).length()) + String.valueOf(str2).length()) + String.valueOf(str3).length()).append(str).append(valueOf).append(str2).append("|").append(str3).toString();
    }

    private void zzkg(String str) {
        File file = new File(zzx.getNoBackupFilesDir(this.zzagf), str);
        if (!file.exists()) {
            try {
                if (file.createNewFile() && !isEmpty()) {
                    Log.i("InstanceID/Store", "App restored, clearing state");
                    FirebaseInstanceId.zza(this.zzagf, this);
                }
            } catch (IOException e) {
                if (Log.isLoggable("InstanceID/Store", 3)) {
                    String str2 = "InstanceID/Store";
                    String str3 = "Error creating file in no backup dir: ";
                    String valueOf = String.valueOf(e.getMessage());
                    Log.d(str2, valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
                }
            }
        }
    }

    synchronized String get(String str) {
        return this.acs.getString(str, null);
    }

    synchronized String get(String str, String str2) {
        SharedPreferences sharedPreferences;
        String valueOf;
        sharedPreferences = this.acs;
        valueOf = String.valueOf("|S|");
        return sharedPreferences.getString(new StringBuilder(((String.valueOf(str).length() + 0) + String.valueOf(valueOf).length()) + String.valueOf(str2).length()).append(str).append(valueOf).append(str2).toString(), null);
    }

    public boolean isEmpty() {
        return this.acs.getAll().isEmpty();
    }

    synchronized void zza(Editor editor, String str, String str2, String str3) {
        String valueOf = String.valueOf("|S|");
        editor.putString(new StringBuilder(((String.valueOf(str).length() + 0) + String.valueOf(valueOf).length()) + String.valueOf(str2).length()).append(str).append(valueOf).append(str2).toString(), str3);
    }

    public synchronized void zza(String str, String str2, String str3, String str4, String str5) {
        String zzh = zzh(str, str2, str3);
        Editor edit = this.acs.edit();
        edit.putString(zzh, str4);
        edit.putString("appVersion", str5);
        edit.putString("lastToken", Long.toString(System.currentTimeMillis() / 1000));
        edit.commit();
    }

    public synchronized void zzbna() {
        this.acs.edit().clear().commit();
    }

    public SharedPreferences zzcxd() {
        return this.acs;
    }

    synchronized KeyPair zze(String str, long j) {
        KeyPair zzbms;
        zzbms = zza.zzbms();
        Editor edit = this.acs.edit();
        zza(edit, str, "|P|", FirebaseInstanceId.zzz(zzbms.getPublic().getEncoded()));
        zza(edit, str, "|K|", FirebaseInstanceId.zzz(zzbms.getPrivate().getEncoded()));
        zza(edit, str, "cre", Long.toString(j));
        edit.commit();
        return zzbms;
    }

    public synchronized String zzi(String str, String str2, String str3) {
        return this.acs.getString(zzh(str, str2, str3), null);
    }

    public synchronized void zzj(String str, String str2, String str3) {
        String zzh = zzh(str, str2, str3);
        Editor edit = this.acs.edit();
        edit.remove(zzh);
        edit.commit();
    }

    public synchronized void zzkh(String str) {
        Editor edit = this.acs.edit();
        for (String str2 : this.acs.getAll().keySet()) {
            if (str2.startsWith(str)) {
                edit.remove(str2);
            }
        }
        edit.commit();
    }

    public KeyPair zzki(String str) {
        return zzkl(str);
    }

    void zzkj(String str) {
        zzkh(String.valueOf(str).concat("|"));
    }

    public void zzkk(String str) {
        zzkh(String.valueOf(str).concat("|T|"));
    }

    KeyPair zzkl(String str) {
        Object e;
        String str2 = get(str, "|P|");
        String str3 = get(str, "|K|");
        if (str3 == null) {
            return null;
        }
        try {
            byte[] decode = Base64.decode(str2, 8);
            byte[] decode2 = Base64.decode(str3, 8);
            KeyFactory instance = KeyFactory.getInstance("RSA");
            return new KeyPair(instance.generatePublic(new X509EncodedKeySpec(decode)), instance.generatePrivate(new PKCS8EncodedKeySpec(decode2)));
        } catch (InvalidKeySpecException e2) {
            e = e2;
            str2 = String.valueOf(e);
            Log.w("InstanceID/Store", new StringBuilder(String.valueOf(str2).length() + 19).append("Invalid key stored ").append(str2).toString());
            FirebaseInstanceId.zza(this.zzagf, this);
            return null;
        } catch (NoSuchAlgorithmException e3) {
            e = e3;
            str2 = String.valueOf(e);
            Log.w("InstanceID/Store", new StringBuilder(String.valueOf(str2).length() + 19).append("Invalid key stored ").append(str2).toString());
            FirebaseInstanceId.zza(this.zzagf, this);
            return null;
        }
    }
}
