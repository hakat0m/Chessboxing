package com.facebook.android;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.os.Bundle;
import com.apptentive.android.sdk.BuildConfig;
import com.facebook.internal.Utility;
import com.gumtree.android.postad.payment.di.PostAdPaymentModule;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import org.json.JSONException;
import org.json.JSONObject;

public final class Util {
    private static final String UTF8 = "UTF-8";

    @Deprecated
    public static String encodePostBody(Bundle bundle, String str) {
        if (bundle == null) {
            return BuildConfig.FLAVOR;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String str2 : bundle.keySet()) {
            Object obj = bundle.get(str2);
            if (obj instanceof String) {
                stringBuilder.append("Content-Disposition: form-data; name=\"" + str2 + "\"\r\n\r\n" + ((String) obj));
                stringBuilder.append("\r\n--" + str + "\r\n");
            }
        }
        return stringBuilder.toString();
    }

    @Deprecated
    public static String encodeUrl(Bundle bundle) {
        if (bundle == null) {
            return BuildConfig.FLAVOR;
        }
        StringBuilder stringBuilder = new StringBuilder();
        Object obj = 1;
        for (String str : bundle.keySet()) {
            if (bundle.get(str) instanceof String) {
                if (obj != null) {
                    obj = null;
                } else {
                    stringBuilder.append("&");
                }
                stringBuilder.append(URLEncoder.encode(str) + "=" + URLEncoder.encode(bundle.getString(str)));
            }
        }
        return stringBuilder.toString();
    }

    @Deprecated
    public static Bundle decodeUrl(String str) {
        Bundle bundle = new Bundle();
        if (str != null) {
            for (String split : str.split("&")) {
                String[] split2 = split.split("=");
                try {
                    if (split2.length == 2) {
                        bundle.putString(URLDecoder.decode(split2[0], UTF8), URLDecoder.decode(split2[1], UTF8));
                    } else if (split2.length == 1) {
                        bundle.putString(URLDecoder.decode(split2[0], UTF8), BuildConfig.FLAVOR);
                    }
                } catch (UnsupportedEncodingException e) {
                }
            }
        }
        return bundle;
    }

    @Deprecated
    public static Bundle parseUrl(String str) {
        try {
            URL url = new URL(str.replace("fbconnect", "http"));
            Bundle decodeUrl = decodeUrl(url.getQuery());
            decodeUrl.putAll(decodeUrl(url.getRef()));
            return decodeUrl;
        } catch (MalformedURLException e) {
            return new Bundle();
        }
    }

    @Deprecated
    public static String openUrl(String str, String str2, Bundle bundle) throws MalformedURLException, IOException {
        String str3 = "3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f";
        String str4 = "\r\n";
        if (str2.equals("GET")) {
            str = str + "?" + encodeUrl(bundle);
        }
        Utility.logd("Facebook-Util", str2 + " URL: " + str);
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
        httpURLConnection.setRequestProperty("User-Agent", System.getProperties().getProperty("http.agent") + " FacebookAndroidSDK");
        if (!str2.equals("GET")) {
            Bundle bundle2 = new Bundle();
            for (String str5 : bundle.keySet()) {
                Object obj = bundle.get(str5);
                if (obj instanceof byte[]) {
                    bundle2.putByteArray(str5, (byte[]) obj);
                }
            }
            if (!bundle.containsKey("method")) {
                bundle.putString("method", str2);
            }
            if (bundle.containsKey(Facebook.TOKEN)) {
                bundle.putString(Facebook.TOKEN, URLDecoder.decode(bundle.getString(Facebook.TOKEN)));
            }
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + str3);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            httpURLConnection.connect();
            OutputStream bufferedOutputStream = new BufferedOutputStream(httpURLConnection.getOutputStream());
            try {
                bufferedOutputStream.write(("--" + str3 + str4).getBytes());
                bufferedOutputStream.write(encodePostBody(bundle, str3).getBytes());
                bufferedOutputStream.write((str4 + "--" + str3 + str4).getBytes());
                if (!bundle2.isEmpty()) {
                    for (String str52 : bundle2.keySet()) {
                        bufferedOutputStream.write(("Content-Disposition: form-data; filename=\"" + str52 + "\"" + str4).getBytes());
                        bufferedOutputStream.write(("Content-Type: content/unknown" + str4 + str4).getBytes());
                        bufferedOutputStream.write(bundle2.getByteArray(str52));
                        bufferedOutputStream.write((str4 + "--" + str3 + str4).getBytes());
                    }
                }
                bufferedOutputStream.flush();
            } finally {
                bufferedOutputStream.close();
            }
        }
        String str522 = BuildConfig.FLAVOR;
        try {
            return read(httpURLConnection.getInputStream());
        } catch (FileNotFoundException e) {
            return read(httpURLConnection.getErrorStream());
        }
    }

    @Deprecated
    private static String read(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream), PostAdPaymentModule.REDIRECT_TO_MANAGE_ADS_TIMEOUT);
        for (String readLine = bufferedReader.readLine(); readLine != null; readLine = bufferedReader.readLine()) {
            stringBuilder.append(readLine);
        }
        inputStream.close();
        return stringBuilder.toString();
    }

    @Deprecated
    public static JSONObject parseJson(String str) throws JSONException, FacebookError {
        if (str.equals("false")) {
            throw new FacebookError("request failed");
        }
        if (str.equals("true")) {
            str = "{value : true}";
        }
        JSONObject jSONObject = new JSONObject(str);
        if (jSONObject.has("error")) {
            jSONObject = jSONObject.getJSONObject("error");
            throw new FacebookError(jSONObject.getString("message"), jSONObject.getString("type"), 0);
        } else if (jSONObject.has("error_code") && jSONObject.has("error_msg")) {
            throw new FacebookError(jSONObject.getString("error_msg"), BuildConfig.FLAVOR, Integer.parseInt(jSONObject.getString("error_code")));
        } else if (jSONObject.has("error_code")) {
            throw new FacebookError("request failed", BuildConfig.FLAVOR, Integer.parseInt(jSONObject.getString("error_code")));
        } else if (jSONObject.has("error_msg")) {
            throw new FacebookError(jSONObject.getString("error_msg"));
        } else if (!jSONObject.has("error_reason")) {
            return jSONObject;
        } else {
            throw new FacebookError(jSONObject.getString("error_reason"));
        }
    }

    @Deprecated
    public static void showAlert(Context context, String str, String str2) {
        Builder builder = new Builder(context);
        builder.setTitle(str);
        builder.setMessage(str2);
        builder.create().show();
    }
}
