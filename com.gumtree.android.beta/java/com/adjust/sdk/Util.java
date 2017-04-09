package com.adjust.sdk;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Looper;
import com.adjust.sdk.AdjustFactory.URLGetConnection;
import com.apptentive.android.sdk.BuildConfig;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.NotSerializableException;
import java.io.ObjectInputStream.GetField;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Pattern;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONException;
import org.json.JSONObject;
import uk.co.senab.photoview.IPhotoView;

public class Util {
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'Z";
    public static final DecimalFormat SecondsDisplayFormat = new DecimalFormat("0.0");
    private static SimpleDateFormat dateFormat = null;
    private static final String fieldReadErrorMessage = "Unable to read '%s' field in migration device with message (%s)";

    final class AnonymousClass1 extends AsyncTask<Context, Void, String> {
        final /* synthetic */ OnDeviceIdsRead val$onDeviceIdRead;

        AnonymousClass1(OnDeviceIdsRead onDeviceIdsRead) {
            this.val$onDeviceIdRead = onDeviceIdsRead;
        }

        protected String doInBackground(Context... contextArr) {
            ILogger logger = AdjustFactory.getLogger();
            String playAdId = Util.getPlayAdId(contextArr[0]);
            logger.debug("GoogleAdId read " + playAdId, new Object[0]);
            return playAdId;
        }

        protected void onPostExecute(String str) {
            AdjustFactory.getLogger();
            this.val$onDeviceIdRead.onGoogleAdIdRead(str);
        }
    }

    private static ILogger getLogger() {
        return AdjustFactory.getLogger();
    }

    protected static String createUuid() {
        return UUID.randomUUID().toString();
    }

    public static String quote(String str) {
        if (str == null) {
            return null;
        }
        if (!Pattern.compile("\\s").matcher(str).find()) {
            return str;
        }
        return String.format(Locale.US, "'%s'", new Object[]{str});
    }

    public static String dateFormat(long j) {
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        }
        return dateFormat.format(Long.valueOf(j));
    }

    public static String getPlayAdId(Context context) {
        return Reflection.getPlayAdId(context);
    }

    public static void getGoogleAdId(Context context, OnDeviceIdsRead onDeviceIdsRead) {
        ILogger logger = AdjustFactory.getLogger();
        if (Looper.myLooper() != Looper.getMainLooper()) {
            logger.debug("GoogleAdId being read in the background", new Object[0]);
            String playAdId = getPlayAdId(context);
            logger.debug("GoogleAdId read " + playAdId, new Object[0]);
            onDeviceIdsRead.onGoogleAdIdRead(playAdId);
            return;
        }
        logger.debug("GoogleAdId being read in the foreground", new Object[0]);
        new AnonymousClass1(onDeviceIdsRead).execute(new Context[]{context});
    }

    public static Boolean isPlayTrackingEnabled(Context context) {
        return Reflection.isPlayTrackingEnabled(context);
    }

    public static String getMacAddress(Context context) {
        return Reflection.getMacAddress(context);
    }

    public static Map<String, String> getPluginKeys(Context context) {
        return Reflection.getPluginKeys(context);
    }

    public static String getAndroidId(Context context) {
        return Reflection.getAndroidId(context);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static <T> T readObject(android.content.Context r11, java.lang.String r12, java.lang.String r13, java.lang.Class<T> r14) {
        /*
        r1 = 0;
        r9 = 2;
        r8 = 1;
        r7 = 0;
        r2 = r11.openFileInput(r12);	 Catch:{ FileNotFoundException -> 0x00c5, Exception -> 0x00bb }
        r0 = new java.io.BufferedInputStream;	 Catch:{ FileNotFoundException -> 0x00c8, Exception -> 0x00be }
        r0.<init>(r2);	 Catch:{ FileNotFoundException -> 0x00c8, Exception -> 0x00be }
        r2 = new java.io.ObjectInputStream;	 Catch:{ FileNotFoundException -> 0x00cc, Exception -> 0x00c0 }
        r2.<init>(r0);	 Catch:{ FileNotFoundException -> 0x00cc, Exception -> 0x00c0 }
        r0 = r2.readObject();	 Catch:{ ClassNotFoundException -> 0x0032, ClassCastException -> 0x005e, Exception -> 0x008e, FileNotFoundException -> 0x00d2 }
        r0 = r14.cast(r0);	 Catch:{ ClassNotFoundException -> 0x0032, ClassCastException -> 0x005e, Exception -> 0x008e, FileNotFoundException -> 0x00d2 }
        r1 = getLogger();	 Catch:{ ClassNotFoundException -> 0x00db, ClassCastException -> 0x00d9, Exception -> 0x00d7, FileNotFoundException -> 0x004d }
        r3 = "Read %s: %s";
        r4 = 2;
        r4 = new java.lang.Object[r4];	 Catch:{ ClassNotFoundException -> 0x00db, ClassCastException -> 0x00d9, Exception -> 0x00d7, FileNotFoundException -> 0x004d }
        r5 = 0;
        r4[r5] = r13;	 Catch:{ ClassNotFoundException -> 0x00db, ClassCastException -> 0x00d9, Exception -> 0x00d7, FileNotFoundException -> 0x004d }
        r5 = 1;
        r4[r5] = r0;	 Catch:{ ClassNotFoundException -> 0x00db, ClassCastException -> 0x00d9, Exception -> 0x00d7, FileNotFoundException -> 0x004d }
        r1.debug(r3, r4);	 Catch:{ ClassNotFoundException -> 0x00db, ClassCastException -> 0x00d9, Exception -> 0x00d7, FileNotFoundException -> 0x004d }
    L_0x002c:
        if (r2 == 0) goto L_0x0031;
    L_0x002e:
        r2.close();	 Catch:{ Exception -> 0x00a9 }
    L_0x0031:
        return r0;
    L_0x0032:
        r0 = move-exception;
        r10 = r0;
        r0 = r1;
        r1 = r10;
    L_0x0036:
        r3 = getLogger();	 Catch:{ FileNotFoundException -> 0x004d, Exception -> 0x0079 }
        r4 = "Failed to find %s class (%s)";
        r5 = 2;
        r5 = new java.lang.Object[r5];	 Catch:{ FileNotFoundException -> 0x004d, Exception -> 0x0079 }
        r6 = 0;
        r5[r6] = r13;	 Catch:{ FileNotFoundException -> 0x004d, Exception -> 0x0079 }
        r6 = 1;
        r1 = r1.getMessage();	 Catch:{ FileNotFoundException -> 0x004d, Exception -> 0x0079 }
        r5[r6] = r1;	 Catch:{ FileNotFoundException -> 0x004d, Exception -> 0x0079 }
        r3.error(r4, r5);	 Catch:{ FileNotFoundException -> 0x004d, Exception -> 0x0079 }
        goto L_0x002c;
    L_0x004d:
        r1 = move-exception;
        r1 = r2;
    L_0x004f:
        r2 = getLogger();
        r3 = "%s file not found";
        r4 = new java.lang.Object[r8];
        r4[r7] = r13;
        r2.debug(r3, r4);
        r2 = r1;
        goto L_0x002c;
    L_0x005e:
        r0 = move-exception;
        r10 = r0;
        r0 = r1;
        r1 = r10;
    L_0x0062:
        r3 = getLogger();	 Catch:{ FileNotFoundException -> 0x004d, Exception -> 0x0079 }
        r4 = "Failed to cast %s object (%s)";
        r5 = 2;
        r5 = new java.lang.Object[r5];	 Catch:{ FileNotFoundException -> 0x004d, Exception -> 0x0079 }
        r6 = 0;
        r5[r6] = r13;	 Catch:{ FileNotFoundException -> 0x004d, Exception -> 0x0079 }
        r6 = 1;
        r1 = r1.getMessage();	 Catch:{ FileNotFoundException -> 0x004d, Exception -> 0x0079 }
        r5[r6] = r1;	 Catch:{ FileNotFoundException -> 0x004d, Exception -> 0x0079 }
        r3.error(r4, r5);	 Catch:{ FileNotFoundException -> 0x004d, Exception -> 0x0079 }
        goto L_0x002c;
    L_0x0079:
        r1 = move-exception;
        r10 = r1;
        r1 = r0;
        r0 = r10;
    L_0x007d:
        r3 = getLogger();
        r4 = "Failed to open %s file for reading (%s)";
        r5 = new java.lang.Object[r9];
        r5[r7] = r13;
        r5[r8] = r0;
        r3.error(r4, r5);
        r0 = r1;
        goto L_0x002c;
    L_0x008e:
        r0 = move-exception;
        r10 = r0;
        r0 = r1;
        r1 = r10;
    L_0x0092:
        r3 = getLogger();	 Catch:{ FileNotFoundException -> 0x004d, Exception -> 0x0079 }
        r4 = "Failed to read %s object (%s)";
        r5 = 2;
        r5 = new java.lang.Object[r5];	 Catch:{ FileNotFoundException -> 0x004d, Exception -> 0x0079 }
        r6 = 0;
        r5[r6] = r13;	 Catch:{ FileNotFoundException -> 0x004d, Exception -> 0x0079 }
        r6 = 1;
        r1 = r1.getMessage();	 Catch:{ FileNotFoundException -> 0x004d, Exception -> 0x0079 }
        r5[r6] = r1;	 Catch:{ FileNotFoundException -> 0x004d, Exception -> 0x0079 }
        r3.error(r4, r5);	 Catch:{ FileNotFoundException -> 0x004d, Exception -> 0x0079 }
        goto L_0x002c;
    L_0x00a9:
        r1 = move-exception;
        r2 = getLogger();
        r3 = "Failed to close %s file for reading (%s)";
        r4 = new java.lang.Object[r9];
        r4[r7] = r13;
        r4[r8] = r1;
        r2.error(r3, r4);
        goto L_0x0031;
    L_0x00bb:
        r0 = move-exception;
        r2 = r1;
        goto L_0x007d;
    L_0x00be:
        r0 = move-exception;
        goto L_0x007d;
    L_0x00c0:
        r2 = move-exception;
        r10 = r2;
        r2 = r0;
        r0 = r10;
        goto L_0x007d;
    L_0x00c5:
        r0 = move-exception;
        r0 = r1;
        goto L_0x004f;
    L_0x00c8:
        r0 = move-exception;
        r0 = r1;
        r1 = r2;
        goto L_0x004f;
    L_0x00cc:
        r2 = move-exception;
        r10 = r1;
        r1 = r0;
        r0 = r10;
        goto L_0x004f;
    L_0x00d2:
        r0 = move-exception;
        r0 = r1;
        r1 = r2;
        goto L_0x004f;
    L_0x00d7:
        r1 = move-exception;
        goto L_0x0092;
    L_0x00d9:
        r1 = move-exception;
        goto L_0x0062;
    L_0x00db:
        r1 = move-exception;
        goto L_0x0036;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.adjust.sdk.Util.readObject(android.content.Context, java.lang.String, java.lang.String, java.lang.Class):T");
    }

    public static <T> void writeObject(T t, Context context, String str, String str2) {
        Exception e;
        Closeable closeable = null;
        try {
            closeable = context.openFileOutput(str, 0);
            OutputStream bufferedOutputStream = new BufferedOutputStream(closeable);
            try {
                closeable = new ObjectOutputStream(bufferedOutputStream);
                try {
                    closeable.writeObject(t);
                    getLogger().debug("Wrote %s: %s", str2, t);
                } catch (NotSerializableException e2) {
                    getLogger().error("Failed to serialize %s", str2);
                }
            } catch (Exception e3) {
                e = e3;
                Object obj = bufferedOutputStream;
                getLogger().error("Failed to open %s for writing (%s)", str2, e);
                if (closeable == null) {
                    try {
                        closeable.close();
                    } catch (Exception e4) {
                        getLogger().error("Failed to close %s file for writing (%s)", str2, e4);
                        return;
                    }
                }
            }
        } catch (Exception e5) {
            e4 = e5;
            getLogger().error("Failed to open %s for writing (%s)", str2, e4);
            if (closeable == null) {
                closeable.close();
            }
        }
        if (closeable == null) {
            closeable.close();
        }
    }

    public static ResponseData readHttpResponse(HttpsURLConnection httpsURLConnection, ActivityPackage activityPackage) throws Exception {
        StringBuffer stringBuffer = new StringBuffer();
        ILogger logger = getLogger();
        try {
            InputStream errorStream;
            String readLine;
            Integer valueOf = Integer.valueOf(httpsURLConnection.getResponseCode());
            if (valueOf.intValue() >= 400) {
                errorStream = httpsURLConnection.getErrorStream();
            } else {
                errorStream = httpsURLConnection.getInputStream();
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(errorStream));
            while (true) {
                readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                stringBuffer.append(readLine);
            }
            if (httpsURLConnection != null) {
                httpsURLConnection.disconnect();
            }
            ResponseData buildResponseData = ResponseData.buildResponseData(activityPackage);
            readLine = stringBuffer.toString();
            logger.verbose("Response: %s", readLine);
            if (!(readLine == null || readLine.length() == 0)) {
                JSONObject jSONObject;
                String format;
                try {
                    jSONObject = new JSONObject(readLine);
                } catch (JSONException e) {
                    format = String.format("Failed to parse json response. (%s)", new Object[]{e.getMessage()});
                    logger.error(format, new Object[0]);
                    buildResponseData.message = format;
                    jSONObject = null;
                }
                if (jSONObject != null) {
                    buildResponseData.jsonResponse = jSONObject;
                    format = jSONObject.optString("message", null);
                    buildResponseData.message = format;
                    buildResponseData.timestamp = jSONObject.optString("timestamp", null);
                    buildResponseData.adid = jSONObject.optString("adid", null);
                    if (format == null) {
                        format = "No message found";
                    }
                    if (valueOf == null || valueOf.intValue() != IPhotoView.DEFAULT_ZOOM_DURATION) {
                        logger.error("%s", format);
                    } else {
                        logger.info("%s", format);
                        buildResponseData.success = true;
                    }
                }
            }
            return buildResponseData;
        } catch (Exception e2) {
            logger.error("Failed to read response. (%s)", e2.getMessage());
            throw e2;
        } catch (Throwable th) {
            if (httpsURLConnection != null) {
                httpsURLConnection.disconnect();
            }
        }
    }

    public static URLGetConnection createGETHttpsURLConnection(String str, String str2) throws IOException {
        URLGetConnection httpsURLGetConnection = AdjustFactory.getHttpsURLGetConnection(new URL(str));
        HttpsURLConnection httpsURLConnection = httpsURLGetConnection.httpsURLConnection;
        setDefaultHttpsUrlConnectionProperties(httpsURLConnection, str2);
        httpsURLConnection.setRequestMethod("GET");
        return httpsURLGetConnection;
    }

    public static HttpsURLConnection createPOSTHttpsURLConnection(String str, String str2, Map<String, String> map, int i) throws IOException {
        HttpsURLConnection httpsURLConnection = AdjustFactory.getHttpsURLConnection(new URL(str));
        setDefaultHttpsUrlConnectionProperties(httpsURLConnection, str2);
        httpsURLConnection.setRequestMethod("POST");
        httpsURLConnection.setUseCaches(false);
        httpsURLConnection.setDoInput(true);
        httpsURLConnection.setDoOutput(true);
        DataOutputStream dataOutputStream = new DataOutputStream(httpsURLConnection.getOutputStream());
        dataOutputStream.writeBytes(getPostDataString(map, i));
        dataOutputStream.flush();
        dataOutputStream.close();
        return httpsURLConnection;
    }

    private static String getPostDataString(Map<String, String> map, int i) throws UnsupportedEncodingException {
        String str;
        StringBuilder stringBuilder = new StringBuilder();
        for (Entry entry : map.entrySet()) {
            String encode = URLEncoder.encode((String) entry.getKey(), Constants.ENCODING);
            str = (String) entry.getValue();
            str = str != null ? URLEncoder.encode(str, Constants.ENCODING) : BuildConfig.FLAVOR;
            if (stringBuilder.length() > 0) {
                stringBuilder.append("&");
            }
            stringBuilder.append(encode);
            stringBuilder.append("=");
            stringBuilder.append(str);
        }
        str = dateFormat(System.currentTimeMillis());
        stringBuilder.append("&");
        stringBuilder.append(URLEncoder.encode("sent_at", Constants.ENCODING));
        stringBuilder.append("=");
        stringBuilder.append(URLEncoder.encode(str, Constants.ENCODING));
        stringBuilder.append("&");
        stringBuilder.append(URLEncoder.encode("queue_size", Constants.ENCODING));
        stringBuilder.append("=");
        stringBuilder.append(URLEncoder.encode(BuildConfig.FLAVOR + i, Constants.ENCODING));
        return stringBuilder.toString();
    }

    public static void setDefaultHttpsUrlConnectionProperties(HttpsURLConnection httpsURLConnection, String str) {
        httpsURLConnection.setRequestProperty("Client-SDK", str);
        httpsURLConnection.setConnectTimeout(Constants.SOCKET_TIMEOUT);
        httpsURLConnection.setReadTimeout(Constants.SOCKET_TIMEOUT);
    }

    public static boolean checkPermission(Context context, String str) {
        return context.checkCallingOrSelfPermission(str) == 0;
    }

    public static String readStringField(GetField getField, String str, String str2) {
        return (String) readObjectField(getField, str, str2);
    }

    public static <T> T readObjectField(GetField getField, String str, T t) {
        try {
            t = getField.get(str, t);
        } catch (Exception e) {
            getLogger().debug(fieldReadErrorMessage, str, e.getMessage());
        }
        return t;
    }

    public static boolean readBooleanField(GetField getField, String str, boolean z) {
        try {
            z = getField.get(str, z);
        } catch (Exception e) {
            getLogger().debug(fieldReadErrorMessage, str, e.getMessage());
        }
        return z;
    }

    public static int readIntField(GetField getField, String str, int i) {
        try {
            i = getField.get(str, i);
        } catch (Exception e) {
            getLogger().debug(fieldReadErrorMessage, str, e.getMessage());
        }
        return i;
    }

    public static long readLongField(GetField getField, String str, long j) {
        try {
            j = getField.get(str, j);
        } catch (Exception e) {
            getLogger().debug(fieldReadErrorMessage, str, e.getMessage());
        }
        return j;
    }

    public static boolean equalObject(Object obj, Object obj2) {
        if (obj == null || obj2 == null) {
            return obj == null && obj2 == null;
        } else {
            return obj.equals(obj2);
        }
    }

    public static boolean equalsMap(Map map, Map map2) {
        if (map == null || map2 == null) {
            return map == null && map2 == null;
        } else {
            return map.entrySet().equals(map2.entrySet());
        }
    }

    public static boolean equalsDouble(Double d, Double d2) {
        if (d == null || d2 == null) {
            if (d == null && d2 == null) {
                return true;
            }
            return false;
        } else if (Double.doubleToLongBits(d.doubleValue()) != Double.doubleToLongBits(d2.doubleValue())) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean equalString(String str, String str2) {
        return equalObject(str, str2);
    }

    public static boolean equalEnum(Enum enumR, Enum enumR2) {
        return equalObject(enumR, enumR2);
    }

    public static boolean equalLong(Long l, Long l2) {
        return equalObject(l, l2);
    }

    public static boolean equalInt(Integer num, Integer num2) {
        return equalObject(num, num2);
    }

    public static boolean equalBoolean(Boolean bool, Boolean bool2) {
        return equalObject(bool, bool2);
    }

    public static int hashBoolean(Boolean bool) {
        if (bool == null) {
            return 0;
        }
        return bool.hashCode();
    }

    public static int hashLong(Long l) {
        if (l == null) {
            return 0;
        }
        return l.hashCode();
    }

    public static int hashString(String str) {
        if (str == null) {
            return 0;
        }
        return str.hashCode();
    }

    public static int hashEnum(Enum enumR) {
        if (enumR == null) {
            return 0;
        }
        return enumR.hashCode();
    }

    public static int hashMap(Map map) {
        if (map == null) {
            return 0;
        }
        return map.entrySet().hashCode();
    }

    public static String sha1(String str) {
        return hash(str, Constants.SHA1);
    }

    public static String md5(String str) {
        return hash(str, Constants.MD5);
    }

    public static String hash(String str, String str2) {
        String str3 = null;
        try {
            byte[] bytes = str.getBytes(Constants.ENCODING);
            MessageDigest instance = MessageDigest.getInstance(str2);
            instance.update(bytes, 0, bytes.length);
            str3 = convertToHex(instance.digest());
        } catch (Exception e) {
        }
        return str3;
    }

    public static String convertToHex(byte[] bArr) {
        BigInteger bigInteger = new BigInteger(1, bArr);
        String str = "%0" + (bArr.length << 1) + "x";
        return String.format(Locale.US, str, new Object[]{bigInteger});
    }

    public static String[] getSupportedAbis() {
        return Reflection.getSupportedAbis();
    }

    public static String getCpuAbi() {
        return Reflection.getCpuAbi();
    }

    public static String getReasonString(String str, Throwable th) {
        if (th != null) {
            return String.format(Locale.US, "%s: %s", new Object[]{str, th});
        }
        return String.format(Locale.US, "%s", new Object[]{str});
    }

    public static long getWaitingTime(int i, BackoffStrategy backoffStrategy) {
        if (i < backoffStrategy.minRetries) {
            return 0;
        }
        return (long) (((double) Math.min(((long) Math.pow(2.0d, (double) (i - backoffStrategy.minRetries))) * backoffStrategy.milliSecondMultiplier, backoffStrategy.maxWait)) * randomInRange(backoffStrategy.minRange, backoffStrategy.maxRange));
    }

    private static double randomInRange(double d, double d2) {
        return (new Random().nextDouble() * (d2 - d)) + d;
    }
}
