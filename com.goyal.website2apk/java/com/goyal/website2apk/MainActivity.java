/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.content.ActivityNotFoundException
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.Intent
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.graphics.Paint
 *  android.net.Uri
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Environment
 *  android.os.Handler
 *  android.view.LayoutInflater
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnLongClickListener
 *  android.view.ViewGroup
 *  android.view.ViewTreeObserver
 *  android.view.ViewTreeObserver$OnGlobalLayoutListener
 *  android.view.Window
 *  android.webkit.ConsoleMessage
 *  android.webkit.CookieManager
 *  android.webkit.DownloadListener
 *  android.webkit.GeolocationPermissions
 *  android.webkit.GeolocationPermissions$Callback
 *  android.webkit.JavascriptInterface
 *  android.webkit.JsResult
 *  android.webkit.ValueCallback
 *  android.webkit.WebBackForwardList
 *  android.webkit.WebChromeClient
 *  android.webkit.WebChromeClient$CustomViewCallback
 *  android.webkit.WebChromeClient$FileChooserParams
 *  android.webkit.WebSettings
 *  android.webkit.WebSettings$PluginState
 *  android.webkit.WebSettings$RenderPriority
 *  android.webkit.WebView
 *  android.webkit.WebViewClient
 *  android.widget.FrameLayout
 *  android.widget.ImageView
 *  android.widget.ProgressBar
 *  android.widget.Toast
 *  java.io.File
 *  java.lang.Boolean
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 *  java.lang.Void
 *  java.net.URL
 *  java.text.SimpleDateFormat
 *  java.util.Date
 *  java.util.regex.Matcher
 *  java.util.regex.Pattern
 */
package com.goyal.website2apk;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.webkit.ConsoleMessage;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity
extends Activity {
    private static final String h = MainActivity.class.getSimpleName();
    WebView a;
    ImageView b;
    String c;
    String d;
    ProgressBar e;
    WebChromeClient f;
    public boolean g = false;
    private String i;
    private ValueCallback<Uri> j;
    private ValueCallback<Uri[]> k;
    private FrameLayout l;
    private WebChromeClient.CustomViewCallback m;
    private View n;

    static /* synthetic */ ValueCallback a(MainActivity mainActivity, ValueCallback valueCallback) {
        mainActivity.k = valueCallback;
        return valueCallback;
    }

    static /* synthetic */ String a(MainActivity mainActivity, String string) {
        mainActivity.i = string;
        return string;
    }

    static /* synthetic */ ValueCallback d(MainActivity mainActivity) {
        return mainActivity.k;
    }

    static /* synthetic */ String d() {
        return h;
    }

    private File e() {
        String string = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return File.createTempFile((String)("img_" + string + "_"), (String)".jpg", (File)Environment.getExternalStoragePublicDirectory((String)Environment.DIRECTORY_PICTURES));
    }

    static /* synthetic */ File e(MainActivity mainActivity) {
        return mainActivity.e();
    }

    static /* synthetic */ String f(MainActivity mainActivity) {
        return mainActivity.i;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void a() {
        if (!this.getResources().getBoolean(2131034124)) return;
        {
            if (Build.VERSION.SDK_INT < 16) {
                this.getWindow().setFlags(1024, 1024);
                return;
            } else {
                View view = this.getWindow().getDecorView();
                if (Build.VERSION.SDK_INT >= 16 && Build.VERSION.SDK_INT < 19) {
                    view.setSystemUiVisibility(1796);
                    return;
                }
                if (Build.VERSION.SDK_INT < 19) return;
                {
                    view.setSystemUiVisibility(3332);
                    return;
                }
            }
        }
    }

    public Void b() {
        if (this.getResources().getBoolean(2131034121)) {
            new AlertDialog.Builder((Context)this).setTitle((CharSequence)"Are you sure to exit?").setNegativeButton(17039360, null).setPositiveButton(17039370, new DialogInterface.OnClickListener(){

                public void onClick(DialogInterface dialogInterface, int n) {
                    MainActivity.this.finish();
                }
            }).create().show();
            return null;
        }
        this.finish();
        return null;
    }

    public void c() {
        AlertDialog.Builder builder = new AlertDialog.Builder((Context)this);
        builder.setMessage(2131165196);
        builder.setTitle((CharSequence)"About");
        builder.setPositiveButton((CharSequence)"OK", null);
        builder.setCancelable(true);
        builder.create().show();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected void onActivityResult(int var1, int var2_3, Intent var3_2) {
        super.onActivityResult(var1, var2_3, var3_2);
        if (Build.VERSION.SDK_INT < 21) ** GOTO lbl14
        if (var2_3 != -1 || var1 != 1) ** GOTO lbl20
        if (this.k == null) {
            return;
        }
        if (var3_2 != null) ** GOTO lbl10
        if (this.i == null) ** GOTO lbl20
        var5_4 = new Uri[]{Uri.parse((String)this.i)};
        ** GOTO lbl21
lbl10: // 1 sources:
        var6_5 = var3_2.getDataString();
        if (var6_5 == null) ** GOTO lbl20
        var5_4 = new Uri[]{Uri.parse((String)var6_5)};
        ** GOTO lbl21
lbl14: // 1 sources:
        if (var1 != 1) return;
        if (this.j == null) return;
        var4_6 = var3_2 == null || var2_3 != -1 ? null : var3_2.getData();
        this.j.onReceiveValue((Object)var4_6);
        this.j = null;
        return;
lbl20: // 3 sources:
        var5_4 = null;
lbl21: // 3 sources:
        this.k.onReceiveValue((Object)var5_4);
        this.k = null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onBackPressed() {
        if (this.g && this.getResources().getBoolean(2131034122)) {
            this.b();
        } else if (this.a.canGoBack()) {
            this.a.goBack();
            this.g = true;
        } else {
            this.b();
        }
        new Handler().postDelayed(new Runnable(){

            public void run() {
                MainActivity.this.g = false;
            }
        }, 200);
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2130903040);
        this.a = (WebView)this.findViewById(2131361793);
        this.b = (ImageView)this.findViewById(2131361795);
        this.l = (FrameLayout)this.findViewById(2131361792);
        this.e = (ProgressBar)this.findViewById(2131361794);
        final Boolean bl = this.getResources().getBoolean(2131034123);
        this.a();
        this.c = "http://ddd.stefanomenci.com/";
        try {
            this.d = new URL(this.c).getHost();
        }
        catch (Exception var3_5) {
            this.d = "Error";
        }
        WebSettings webSettings = this.a.getSettings();
        this.a.setVisibility(8);
        this.b.setVisibility(0);
        new Handler().postDelayed(new Runnable(){

            public void run() {
                MainActivity.this.b.setVisibility(8);
                MainActivity.this.a.setVisibility(0);
            }
        }, (long)this.getResources().getInteger(2131099648));
        webSettings.setAppCacheEnabled(this.getResources().getBoolean(2131034112));
        if (this.getResources().getString(2131165184).equals((Object)"NoCache")) {
            webSettings.setCacheMode(2);
        } else if (this.getResources().getString(2131165184).equals((Object)"HighCache")) {
            webSettings.setCacheMode(1);
        } else {
            webSettings.setCacheMode(-1);
        }
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        if (Build.VERSION.SDK_INT >= 19) {
            this.a.setLayerType(2, null);
        } else if (Build.VERSION.SDK_INT >= 11) {
            this.a.setLayerType(1, null);
        }
        webSettings.setAppCachePath("/data/data" + this.getPackageName() + "/cache");
        webSettings.setAllowFileAccess(true);
        this.a.setScrollBarStyle(33554432);
        webSettings.setSaveFormData(this.getResources().getBoolean(2131034116));
        webSettings.setUseWideViewPort(this.getResources().getBoolean(2131034119));
        webSettings.setLoadWithOverviewMode(this.getResources().getBoolean(2131034114));
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        this.a.setHapticFeedbackEnabled(true);
        this.a.setHorizontalScrollBarEnabled(this.getResources().getBoolean(2131034117));
        this.a.setVerticalScrollBarEnabled(this.getResources().getBoolean(2131034117));
        this.a.setLongClickable(this.getResources().getBoolean(2131034113));
        webSettings.setGeolocationEnabled(true);
        if (Build.VERSION.SDK_INT >= 19 && this.getResources().getBoolean(2131034126)) {
            WebView.setWebContentsDebuggingEnabled((boolean)true);
        }
        this.a.addJavascriptInterface((Object)(MainActivity)this.new a((Context)this), "Website2APK");
        this.a.setOnLongClickListener(new View.OnLongClickListener(){

            public boolean onLongClick(View view) {
                return MainActivity.this.getResources().getBoolean(2131034118);
            }
        });
        if (Build.VERSION.SDK_INT >= 16) {
            webSettings.setAllowFileAccessFromFileURLs(true);
            webSettings.setAllowUniversalAccessFromFileURLs(true);
        }
        this.a.loadUrl(this.c);
        this.a.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){

            public void onGlobalLayout() {
                if (MainActivity.this.a.getRootView().getHeight() - MainActivity.this.a.getHeight() < 100) {
                    MainActivity.this.a();
                }
            }
        });
        this.a.setScrollBarStyle(33554432);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(this.getResources().getBoolean(2131034125));
        webSettings.setDisplayZoomControls(this.getResources().getBoolean(2131034120));
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.acceptCookie();
        if (Build.VERSION.SDK_INT > 21) {
            cookieManager.setAcceptThirdPartyCookies(this.a, true);
        }
        if (bundle != null) {
            this.a.restoreState(bundle);
        }
        this.f = new WebChromeClient((Context)this){
            final /* synthetic */ Context a;
            private View c;

            public View getVideoLoadingProgressView() {
                if (this.c == null) {
                    this.c = LayoutInflater.from((Context)MainActivity.this).inflate(2130903042, null);
                }
                return this.c;
            }

            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                this.onConsoleMessage(consoleMessage.message(), consoleMessage.lineNumber(), consoleMessage.sourceId());
                return true;
            }

            public void onGeolocationPermissionsShowPrompt(String string, GeolocationPermissions.Callback callback) {
                callback.invoke(string, true, false);
            }

            public void onHideCustomView() {
                super.onHideCustomView();
                if (MainActivity.this.n == null) {
                    return;
                }
                MainActivity.this.a.setVisibility(0);
                MainActivity.this.l.setVisibility(8);
                MainActivity.this.n.setVisibility(8);
                MainActivity.this.l.removeView(MainActivity.this.n);
                MainActivity.this.m.onCustomViewHidden();
                MainActivity.this.n = null;
            }

            public boolean onJsAlert(WebView webView, String string, String string2, final JsResult jsResult) {
                new AlertDialog.Builder((Context)MainActivity.this).setTitle((CharSequence)MainActivity.this.getResources().getString(2131165187)).setMessage((CharSequence)string2).setPositiveButton(17039370, new DialogInterface.OnClickListener(){

                    public void onClick(DialogInterface dialogInterface, int n) {
                        jsResult.confirm();
                    }
                }).setCancelable(false).create().show();
                return true;
            }

            public boolean onJsConfirm(WebView webView, String string, String string2, final JsResult jsResult) {
                new AlertDialog.Builder(this.a).setTitle((CharSequence)MainActivity.this.getResources().getString(2131165187)).setMessage((CharSequence)string2).setPositiveButton(17039370, new DialogInterface.OnClickListener(){

                    public void onClick(DialogInterface dialogInterface, int n) {
                        jsResult.confirm();
                    }
                }).setNegativeButton(17039360, new DialogInterface.OnClickListener(){

                    public void onClick(DialogInterface dialogInterface, int n) {
                        jsResult.cancel();
                    }
                }).create().show();
                return true;
            }

            public void onProgressChanged(WebView webView, int n) {
                if (n < 100 && MainActivity.this.e.getVisibility() == 8) {
                    MainActivity.this.e.setVisibility(0);
                }
                MainActivity.this.e.setProgress(n);
                if (n == 100) {
                    MainActivity.this.e.setVisibility(8);
                }
            }

            public void onShowCustomView(View view, int n, WebChromeClient.CustomViewCallback customViewCallback) {
                this.onShowCustomView(view, customViewCallback);
            }

            public void onShowCustomView(View view, WebChromeClient.CustomViewCallback customViewCallback) {
                if (MainActivity.this.n != null) {
                    customViewCallback.onCustomViewHidden();
                    return;
                }
                MainActivity.this.n = view;
                MainActivity.this.a.setVisibility(8);
                MainActivity.this.l.setVisibility(0);
                MainActivity.this.l.addView(view);
                MainActivity.this.m = customViewCallback;
            }

            /*
             * Exception decompiling
             */
            public boolean onShowFileChooser(WebView var1, ValueCallback<Uri[]> var2_3, WebChromeClient.FileChooserParams var3_2) {
                // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
                // java.util.ConcurrentModificationException
                // java.util.LinkedList$ReverseLinkIterator.next(LinkedList.java:217)
                // org.benf.cfr.reader.bytecode.analysis.structured.statement.Block.extractLabelledBlocks(Block.java:212)
                // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement$LabelledBlockExtractor.transform(Op04StructuredStatement.java:485)
                // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.transform(Op04StructuredStatement.java:639)
                // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.insertLabelledBlocks(Op04StructuredStatement.java:649)
                // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:816)
                // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:220)
                // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:165)
                // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:91)
                // org.benf.cfr.reader.entities.Method.analyse(Method.java:354)
                // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:751)
                // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:664)
                // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:747)
                // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:683)
                // org.benf.cfr.reader.Main.doJar(Main.java:128)
                // com.njlabs.showjava.processor.JavaExtractor$1.run(JavaExtractor.java:100)
                // java.lang.Thread.run(Thread.java:818)
                throw new IllegalStateException("Decompilation failed");
            }

        };
        this.a.setWebChromeClient(this.f);
        this.a.setWebViewClient(new WebViewClient(){

            /*
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            public Boolean a(String string) {
                if (bl.booleanValue()) {
                    if (string.startsWith("market://")) {
                        try {
                            MainActivity.this.a.getContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse((String)string)));
                        }
                        catch (ActivityNotFoundException var12_2) {
                            var12_2.printStackTrace();
                            return true;
                        }
                        do {
                            return true;
                            break;
                        } while (true);
                    }
                    if (string.startsWith("tel:")) {
                        Intent intent = new Intent("android.intent.action.DIAL", Uri.parse((String)string));
                        MainActivity.this.startActivity(intent);
                        return true;
                    }
                    if (string.startsWith("exit:")) {
                        MainActivity.this.finish();
                        return true;
                    }
                    if (string.startsWith("intent://") && string.contains((CharSequence)"scheme=http")) {
                        String string2 = Uri.decode((String)string);
                        Matcher matcher = Pattern.compile((String)"intent://(.*?)#").matcher((CharSequence)string2);
                        if (!matcher.find()) return false;
                        String string3 = matcher.group(1);
                        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse((String)("http://" + string3)));
                        MainActivity.this.startActivity(intent);
                        return true;
                    }
                    if (string.startsWith("whatsapp:") || string.startsWith("skype:") || string.startsWith("geo:")) {
                        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse((String)string));
                        MainActivity.this.startActivity(intent);
                        return true;
                    }
                    if (string.startsWith("sms:")) {
                        Intent intent = new Intent("android.intent.action.SENDTO", Uri.parse((String)string));
                        MainActivity.this.startActivity(intent);
                        return true;
                    }
                    if (string.startsWith("mailto:")) {
                        Intent intent = new Intent("android.intent.action.SENDTO", Uri.parse((String)string));
                        MainActivity.this.startActivity(intent);
                        return true;
                    }
                    if (!string.contains((CharSequence)"#___external")) return false;
                    Intent intent = new Intent("android.intent.action.VIEW", Uri.parse((String)string));
                    MainActivity.this.startActivity(intent);
                    return true;
                }
                if (string.contains((CharSequence)MainActivity.this.d)) {
                    return false;
                }
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse((String)string));
                MainActivity.this.startActivity(intent);
                return true;
            }

            public void onPageFinished(WebView webView, String string) {
                super.onPageFinished(webView, string);
            }

            public void onReceivedError(WebView webView, int n, String string, String string2) {
                webView.loadUrl("file:///android_asset/404r.html");
            }

            public boolean shouldOverrideUrlLoading(WebView webView, String string) {
                super.shouldOverrideUrlLoading(webView, string);
                if (this.a(string).booleanValue()) {
                    return true;
                }
                webView.loadUrl(string);
                return false;
            }
        });
        this.a.setDownloadListener(new DownloadListener(){

            public void onDownloadStart(String string, String string2, String string3, String string4, long l) {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setData(Uri.parse((String)string));
                MainActivity.this.startActivity(intent);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(2131296256, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int n = menuItem.getItemId();
        Intent intent = new Intent("android.intent.action.SEND");
        if (n == 2131361798) {
            this.finish();
            return true;
        }
        if (n == 2131361797) {
            this.c();
            return true;
        }
        if (n == 2131361799) {
            intent.setType("text/plain");
            intent.putExtra("android.intent.extra.SUBJECT", this.getResources().getString(2131165193));
            intent.putExtra("android.intent.extra.TEXT", this.getResources().getString(2131165194));
            this.startActivity(Intent.createChooser((Intent)intent, (CharSequence)"Share via"));
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void onPause() {
        this.a.onPause();
        super.onPause();
    }

    public void onResume() {
        this.a();
        this.a.onResume();
        super.onResume();
    }

    public class a {
        Context a;

        a(Context context) {
            this.a = context;
        }

        @JavascriptInterface
        public void exitApp() {
            MainActivity.this.b();
        }

        @JavascriptInterface
        public void openExternal(String string) {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse((String)string));
            MainActivity.this.startActivity(intent);
        }

        @JavascriptInterface
        public void shareIntent() {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("text/plain");
            intent.putExtra("android.intent.extra.SUBJECT", this.a.getResources().getString(2131165193));
            intent.putExtra("android.intent.extra.TEXT", this.a.getResources().getString(2131165194));
            this.a.startActivity(Intent.createChooser((Intent)intent, (CharSequence)"Share App via"));
        }

        @JavascriptInterface
        public void showAboutDialog() {
            MainActivity.this.c();
        }

        @JavascriptInterface
        public void showToast(String string) {
            Toast.makeText((Context)this.a, (CharSequence)string, (int)0).show();
        }
    }

}

