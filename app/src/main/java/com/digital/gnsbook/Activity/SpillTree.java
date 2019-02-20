package com.digital.gnsbook.Activity;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.digital.gnsbook.Global;
import com.httpgnsbook.gnsbook.R;

public class SpillTree extends AppCompatActivity {

    WebView webview;

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spill_tree);
        webview=findViewById(R.id.webview);
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setDisplayZoomControls(true);
        webview.getSettings().setDisplayZoomControls(true);
        webview.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        webview.addJavascriptInterface(this, "Android");
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setLoadWithOverviewMode(true);

        webview.loadUrl("https://www.gnsbook.com/spillapp?id="+Global.customerid+"&name="+Global.name);
    }
}
