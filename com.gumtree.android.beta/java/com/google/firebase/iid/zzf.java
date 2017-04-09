package com.google.firebase.iid;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import com.adjust.sdk.Constants;
import com.google.android.gms.iid.MessengerCompat;
import com.gumtree.android.postad.payment.di.PostAdPaymentModule;
import com.gumtree.android.postad.services.converter.PaymentConverter;
import java.io.IOException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class zzf {
    static String acf = null;
    static int acg = 0;
    static int ach = 0;
    static int aci = 0;
    PendingIntent aaP;
    Messenger aaT;
    Map<String, Object> acj = new HashMap();
    Messenger ack;
    MessengerCompat acl;
    long acm;
    long acn;
    int aco;
    int acp;
    long acq;
    Context zzagf;

    public zzf(Context context) {
        this.zzagf = context;
    }

    static String zza(KeyPair keyPair, String... strArr) {
        String str = null;
        try {
            byte[] bytes = TextUtils.join("\n", strArr).getBytes(Constants.ENCODING);
            try {
                PrivateKey privateKey = keyPair.getPrivate();
                Signature instance = Signature.getInstance(privateKey instanceof RSAPrivateKey ? "SHA256withRSA" : "SHA256withECDSA");
                instance.initSign(privateKey);
                instance.update(bytes);
                str = FirebaseInstanceId.zzz(instance.sign());
            } catch (Throwable e) {
                Log.e("InstanceID/Rpc", "Unable to sign registration request", e);
            }
        } catch (Throwable e2) {
            Log.e("InstanceID/Rpc", "Unable to encode string", e2);
        }
        return str;
    }

    private void zzag(Object obj) {
        synchronized (getClass()) {
            for (String str : this.acj.keySet()) {
                Object obj2 = this.acj.get(str);
                this.acj.put(str, obj);
                zzh(obj2, obj);
            }
        }
    }

    private Intent zzb(Bundle bundle, KeyPair keyPair) throws IOException {
        Intent intent;
        ConditionVariable conditionVariable = new ConditionVariable();
        String zzbmz = zzbmz();
        synchronized (getClass()) {
            this.acj.put(zzbmz, conditionVariable);
        }
        zza(bundle, keyPair, zzbmz);
        conditionVariable.block(30000);
        synchronized (getClass()) {
            Object remove = this.acj.remove(zzbmz);
            if (remove instanceof Intent) {
                intent = (Intent) remove;
            } else if (remove instanceof String) {
                throw new IOException((String) remove);
            } else {
                String valueOf = String.valueOf(remove);
                Log.w("InstanceID/Rpc", new StringBuilder(String.valueOf(valueOf).length() + 12).append("No response ").append(valueOf).toString());
                throw new IOException("TIMEOUT");
            }
        }
        return intent;
    }

    public static synchronized String zzbmz() {
        String num;
        synchronized (zzf.class) {
            int i = aci;
            aci = i + 1;
            num = Integer.toString(i);
        }
        return num;
    }

    public static String zzdi(Context context) {
        ApplicationInfo applicationInfo;
        if (acf != null) {
            return acf;
        }
        acg = Process.myUid();
        PackageManager packageManager = context.getPackageManager();
        for (ResolveInfo resolveInfo : packageManager.queryIntentServices(new Intent("com.google.android.c2dm.intent.REGISTER"), 0)) {
            if (packageManager.checkPermission("com.google.android.c2dm.permission.RECEIVE", resolveInfo.serviceInfo.packageName) == 0) {
                try {
                    ApplicationInfo applicationInfo2 = packageManager.getApplicationInfo(resolveInfo.serviceInfo.packageName, 0);
                    Log.w("InstanceID/Rpc", "Found " + applicationInfo2.uid);
                    ach = applicationInfo2.uid;
                    acf = resolveInfo.serviceInfo.packageName;
                    return acf;
                } catch (NameNotFoundException e) {
                }
            } else {
                String valueOf = String.valueOf(resolveInfo.serviceInfo.packageName);
                String valueOf2 = String.valueOf("com.google.android.c2dm.intent.REGISTER");
                Log.w("InstanceID/Rpc", new StringBuilder((String.valueOf(valueOf).length() + 56) + String.valueOf(valueOf2).length()).append("Possible malicious package ").append(valueOf).append(" declares ").append(valueOf2).append(" without permission").toString());
            }
        }
        Log.w("InstanceID/Rpc", "Failed to resolve REGISTER intent, falling back");
        try {
            applicationInfo = packageManager.getApplicationInfo("com.google.android.gms", 0);
            acf = applicationInfo.packageName;
            ach = applicationInfo.uid;
            return acf;
        } catch (NameNotFoundException e2) {
            try {
                applicationInfo = packageManager.getApplicationInfo("com.google.android.gsf", 0);
                acf = applicationInfo.packageName;
                ach = applicationInfo.uid;
                return acf;
            } catch (NameNotFoundException e3) {
                Log.w("InstanceID/Rpc", "Both Google Play Services and legacy GSF package are missing");
                return null;
            }
        }
    }

    private static int zzdj(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(zzdi(context), 0).versionCode;
        } catch (NameNotFoundException e) {
            return -1;
        }
    }

    private void zzh(Object obj, Object obj2) {
        if (obj instanceof ConditionVariable) {
            ((ConditionVariable) obj).open();
        }
        if (obj instanceof Messenger) {
            Messenger messenger = (Messenger) obj;
            Message obtain = Message.obtain();
            obtain.obj = obj2;
            try {
                messenger.send(obtain);
            } catch (RemoteException e) {
                String valueOf = String.valueOf(e);
                Log.w("InstanceID/Rpc", new StringBuilder(String.valueOf(valueOf).length() + 24).append("Failed to send response ").append(valueOf).toString());
            }
        }
    }

    private void zzi(String str, Object obj) {
        synchronized (getClass()) {
            Object obj2 = this.acj.get(str);
            this.acj.put(str, obj);
            zzh(obj2, obj);
        }
    }

    private void zzkf(String str) {
        if ("com.google.android.gsf".equals(acf)) {
            this.aco++;
            if (this.aco >= 3) {
                if (this.aco == 3) {
                    this.acp = new Random().nextInt(PostAdPaymentModule.REDIRECT_TO_MANAGE_ADS_TIMEOUT) + PostAdPaymentModule.REDIRECT_TO_MANAGE_ADS_TIMEOUT;
                }
                this.acp *= 2;
                this.acq = SystemClock.elapsedRealtime() + ((long) this.acp);
                Log.w("InstanceID/Rpc", new StringBuilder(String.valueOf(str).length() + 31).append("Backoff due to ").append(str).append(" for ").append(this.acp).toString());
            }
        }
    }

    Intent zza(Bundle bundle, KeyPair keyPair) throws IOException {
        Intent zzb = zzb(bundle, keyPair);
        return (zzb == null || !zzb.hasExtra("google.messenger")) ? zzb : zzb(bundle, keyPair);
    }

    public void zza(Bundle bundle, KeyPair keyPair, String str) throws IOException {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        if (this.acq == 0 || elapsedRealtime > this.acq) {
            zzbmy();
            if (acf == null) {
                throw new IOException("MISSING_INSTANCEID_SERVICE");
            }
            this.acm = SystemClock.elapsedRealtime();
            Intent intent = new Intent("com.google.android.c2dm.intent.REGISTER");
            intent.setPackage(acf);
            bundle.putString("gmsv", Integer.toString(zzdj(this.zzagf)));
            bundle.putString("osv", Integer.toString(VERSION.SDK_INT));
            bundle.putString("app_ver", Integer.toString(FirebaseInstanceId.zzdf(this.zzagf)));
            bundle.putString("app_ver_name", FirebaseInstanceId.zzdg(this.zzagf));
            bundle.putString("cliv", PaymentConverter.DEFAULT_PAYMENT_METHOD_ID);
            bundle.putString("appid", FirebaseInstanceId.zza(keyPair));
            String zzej = FirebaseInstanceId.zzej(this.zzagf);
            if (zzej != null) {
                bundle.putString("gmp_app_id", zzej);
            }
            bundle.putString("pub2", FirebaseInstanceId.zzz(keyPair.getPublic().getEncoded()));
            bundle.putString("sig", zza(keyPair, this.zzagf.getPackageName(), zzej));
            intent.putExtras(bundle);
            zzr(intent);
            zzb(intent, str);
            return;
        }
        elapsedRealtime = this.acq - elapsedRealtime;
        Log.w("InstanceID/Rpc", "Backoff mode, next request attempt: " + elapsedRealtime + " interval: " + this.acp);
        throw new IOException("RETRY_LATER");
    }

    protected void zzb(Intent intent, String str) {
        this.acm = SystemClock.elapsedRealtime();
        intent.putExtra("kid", new StringBuilder(String.valueOf(str).length() + 5).append("|ID|").append(str).append("|").toString());
        intent.putExtra("X-kid", new StringBuilder(String.valueOf(str).length() + 5).append("|ID|").append(str).append("|").toString());
        boolean equals = "com.google.android.gsf".equals(acf);
        if (Log.isLoggable("InstanceID/Rpc", 3)) {
            String valueOf = String.valueOf(intent.getExtras());
            Log.d("InstanceID/Rpc", new StringBuilder(String.valueOf(valueOf).length() + 8).append("Sending ").append(valueOf).toString());
        }
        if (equals) {
            this.zzagf.startService(intent);
            return;
        }
        intent.putExtra("google.messenger", this.aaT);
        if (!(this.ack == null && this.acl == null)) {
            Message obtain = Message.obtain();
            obtain.obj = intent;
            try {
                if (this.ack != null) {
                    this.ack.send(obtain);
                    return;
                } else {
                    this.acl.send(obtain);
                    return;
                }
            } catch (RemoteException e) {
                if (Log.isLoggable("InstanceID/Rpc", 3)) {
                    Log.d("InstanceID/Rpc", "Messenger failed, fallback to startService");
                }
            }
        }
        this.zzagf.startService(intent);
    }

    void zzbmy() {
        if (this.aaT == null) {
            zzdi(this.zzagf);
            this.aaT = new Messenger(new 1(this, Looper.getMainLooper()));
        }
    }

    public void zze(Message message) {
        if (message != null) {
            if (message.obj instanceof Intent) {
                Intent intent = (Intent) message.obj;
                intent.setExtrasClassLoader(MessengerCompat.class.getClassLoader());
                if (intent.hasExtra("google.messenger")) {
                    Parcelable parcelableExtra = intent.getParcelableExtra("google.messenger");
                    if (parcelableExtra instanceof MessengerCompat) {
                        this.acl = (MessengerCompat) parcelableExtra;
                    }
                    if (parcelableExtra instanceof Messenger) {
                        this.ack = (Messenger) parcelableExtra;
                    }
                }
                zzu((Intent) message.obj);
                return;
            }
            Log.w("InstanceID/Rpc", "Dropping invalid message");
        }
    }

    synchronized void zzr(Intent intent) {
        if (this.aaP == null) {
            Intent intent2 = new Intent();
            intent2.setPackage("com.google.example.invalidpackage");
            this.aaP = PendingIntent.getBroadcast(this.zzagf, 0, intent2, 0);
        }
        intent.putExtra("app", this.aaP);
    }

    String zzs(Intent intent) throws IOException {
        if (intent == null) {
            throw new IOException("SERVICE_NOT_AVAILABLE");
        }
        String stringExtra = intent.getStringExtra("registration_id");
        if (stringExtra == null) {
            stringExtra = intent.getStringExtra("unregistered");
        }
        if (stringExtra != null) {
            return stringExtra;
        }
        stringExtra = intent.getStringExtra("error");
        if (stringExtra != null) {
            throw new IOException(stringExtra);
        }
        String valueOf = String.valueOf(intent.getExtras());
        Log.w("InstanceID/Rpc", new StringBuilder(String.valueOf(valueOf).length() + 29).append("Unexpected response from GCM ").append(valueOf).toString(), new Throwable());
        throw new IOException("SERVICE_NOT_AVAILABLE");
    }

    void zzt(Intent intent) {
        String stringExtra = intent.getStringExtra("error");
        if (stringExtra == null) {
            String valueOf = String.valueOf(intent.getExtras());
            Log.w("InstanceID/Rpc", new StringBuilder(String.valueOf(valueOf).length() + 49).append("Unexpected response, no error or registration id ").append(valueOf).toString());
            return;
        }
        if (Log.isLoggable("InstanceID/Rpc", 3)) {
            valueOf = "InstanceID/Rpc";
            String str = "Received InstanceID error ";
            String valueOf2 = String.valueOf(stringExtra);
            Log.d(valueOf, valueOf2.length() != 0 ? str.concat(valueOf2) : new String(str));
        }
        if (stringExtra.startsWith("|")) {
            String[] split = stringExtra.split("\\|");
            if (!"ID".equals(split[1])) {
                String str2 = "InstanceID/Rpc";
                String str3 = "Unexpected structured response ";
                valueOf2 = String.valueOf(stringExtra);
                Log.w(str2, valueOf2.length() != 0 ? str3.concat(valueOf2) : new String(str3));
            }
            if (split.length > 2) {
                valueOf2 = split[2];
                valueOf = split[3];
                if (valueOf.startsWith(":")) {
                    valueOf = valueOf.substring(1);
                }
            } else {
                valueOf = "UNKNOWN";
                valueOf2 = null;
            }
            intent.putExtra("error", valueOf);
        } else {
            valueOf2 = null;
            valueOf = stringExtra;
        }
        if (valueOf2 == null) {
            zzag(valueOf);
        } else {
            zzi(valueOf2, valueOf);
        }
        long longExtra = intent.getLongExtra("Retry-After", 0);
        if (longExtra > 0) {
            this.acn = SystemClock.elapsedRealtime();
            this.acp = ((int) longExtra) * PostAdPaymentModule.REDIRECT_TO_MANAGE_ADS_TIMEOUT;
            this.acq = SystemClock.elapsedRealtime() + ((long) this.acp);
            Log.w("InstanceID/Rpc", "Explicit request from server to backoff: " + this.acp);
        } else if ("SERVICE_NOT_AVAILABLE".equals(valueOf) || "AUTHENTICATION_FAILED".equals(valueOf)) {
            zzkf(valueOf);
        }
    }

    void zzu(Intent intent) {
        if (intent != null) {
            String stringExtra;
            String str;
            if ("com.google.android.c2dm.intent.REGISTRATION".equals(intent.getAction())) {
                stringExtra = intent.getStringExtra("registration_id");
                if (stringExtra == null) {
                    stringExtra = intent.getStringExtra("unregistered");
                }
                if (stringExtra == null) {
                    zzt(intent);
                    return;
                }
                this.acm = SystemClock.elapsedRealtime();
                this.acq = 0;
                this.aco = 0;
                this.acp = 0;
                if (Log.isLoggable("InstanceID/Rpc", 3)) {
                    String valueOf = String.valueOf(intent.getExtras());
                    Log.d("InstanceID/Rpc", new StringBuilder((String.valueOf(stringExtra).length() + 16) + String.valueOf(valueOf).length()).append("AppIDResponse: ").append(stringExtra).append(" ").append(valueOf).toString());
                }
                if (stringExtra.startsWith("|")) {
                    String[] split = stringExtra.split("\\|");
                    if (!"ID".equals(split[1])) {
                        str = "InstanceID/Rpc";
                        String str2 = "Unexpected structured response ";
                        stringExtra = String.valueOf(stringExtra);
                        Log.w(str, stringExtra.length() != 0 ? str2.concat(stringExtra) : new String(str2));
                    }
                    str = split[2];
                    if (split.length > 4) {
                        if ("SYNC".equals(split[3])) {
                            FirebaseInstanceId.zzdh(this.zzagf);
                        } else if ("RST".equals(split[3])) {
                            FirebaseInstanceId.zza(this.zzagf, zzd.zzb(this.zzagf, null).zzcxa());
                            intent.removeExtra("registration_id");
                            zzi(str, intent);
                            return;
                        }
                    }
                    stringExtra = split[split.length - 1];
                    if (stringExtra.startsWith(":")) {
                        stringExtra = stringExtra.substring(1);
                    }
                    intent.putExtra("registration_id", stringExtra);
                    stringExtra = str;
                } else {
                    stringExtra = null;
                }
                if (stringExtra == null) {
                    zzag(intent);
                } else {
                    zzi(stringExtra, intent);
                }
            } else if (Log.isLoggable("InstanceID/Rpc", 3)) {
                str = "InstanceID/Rpc";
                String str3 = "Unexpected response ";
                stringExtra = String.valueOf(intent.getAction());
                Log.d(str, stringExtra.length() != 0 ? str3.concat(stringExtra) : new String(str3));
            }
        } else if (Log.isLoggable("InstanceID/Rpc", 3)) {
            Log.d("InstanceID/Rpc", "Unexpected response: null");
        }
    }
}
