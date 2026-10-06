package com.ilovepdf4.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.webkit.WebSettingsCompat;
import androidx.webkit.WebViewFeature;

public class MainActivity extends AppCompatActivity {

    private static final String START_URL =
            "https://ilovepdf4login.jhonabraham4450.workers.dev/";

    private WebView webView;
    private ProgressBar progress;
    private ValueCallback<Uri[]> fileCallback;

    private static final int FILE_CHOOSER = 1001;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(Color.rgb(183, 28, 28));
        getWindow().setNavigationBarColor(Color.WHITE);

        RelativeLayout root = new RelativeLayout(this);
        root.setBackgroundColor(Color.WHITE);

        webView = new WebView(this);

        RelativeLayout.LayoutParams webParams =
                new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.MATCH_PARENT
                );

        root.addView(webView, webParams);

        progress = new ProgressBar(this);
        progress.setIndeterminate(true);

        RelativeLayout.LayoutParams progressParams =
                new RelativeLayout.LayoutParams(80, 80);

        progressParams.addRule(RelativeLayout.CENTER_IN_PARENT);

        root.addView(progress, progressParams);

        TextView brand = new TextView(this);
        brand.setText("iLovePDF4");
        brand.setTextColor(Color.WHITE);
        brand.setTextSize(18);
        brand.setGravity(android.view.Gravity.CENTER);
        brand.setTypeface(null, android.graphics.Typeface.BOLD);
        brand.setBackgroundColor(Color.rgb(229, 57, 53));

        RelativeLayout.LayoutParams brandParams =
                new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        54
                );

        brandParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        root.addView(brand, brandParams);

        brand.setVisibility(View.GONE);

        setContentView(root);

        WebSettings settings = webView.getSettings();

        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);

        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(true);

        settings.setMediaPlaybackRequiresUserGesture(false);

        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);

        settings.setJavaScriptCanOpenWindowsAutomatically(true);

        settings.setMixedContentMode(
                WebSettings.MIXED_CONTENT_NEVER_ALLOW
        );

        if (WebViewFeature.isFeatureSupported(
                WebViewFeature.FORCE_DARK
        )) {
            WebSettingsCompat.setForceDark(
                    settings,
                    WebSettingsCompat.FORCE_DARK_OFF
            );
        }

        CookieManager cookieManager =
                CookieManager.getInstance();

        cookieManager.setAcceptCookie(true);

        cookieManager.setAcceptThirdPartyCookies(
                webView,
                true
        );

        webView.setWebViewClient(
                new WebViewClient() {

                    @Override
                    public boolean shouldOverrideUrlLoading(
                            WebView view,
                            WebResourceRequest request
                    ) {

                        Uri uri = request.getUrl();

                        String host = uri.getHost();

                        if (host != null &&
                                host.endsWith(
                                        "ilovepdf4login.jhonabraham4450.workers.dev"
                                )) {

                            return false;
                        }

                        try {

                            Intent intent =
                                    new Intent(
                                            Intent.ACTION_VIEW,
                                            uri
                                    );

                            startActivity(intent);

                        } catch (Exception ignored) {
                        }

                        return true;
                    }

                    @Override
                    public void onPageFinished(
                            WebView view,
                            String url
                    ) {

                        progress.setVisibility(
                                View.GONE
                        );
                    }
                }
        );

        webView.setWebChromeClient(
                new WebChromeClient() {

                    @Override
                    public boolean onShowFileChooser(
                            WebView webView,
                            ValueCallback<Uri[]> callback,
                            FileChooserParams params
                    ) {

                        if (fileCallback != null) {
                            fileCallback.onReceiveValue(null);
                        }

                        fileCallback = callback;

                        try {

                            Intent intent =
                                    params.createIntent();

                            startActivityForResult(
                                    intent,
                                    FILE_CHOOSER
                            );

                            return true;

                        } catch (Exception e) {

                            fileCallback = null;

                            return false;
                        }
                    }

                    @Override
                    public void onProgressChanged(
                            WebView view,
                            int newProgress
                    ) {

                        if (newProgress >= 100) {

                            progress.setVisibility(
                                    View.GONE
                            );

                        } else {

                            progress.setVisibility(
                                    View.VISIBLE
                            );
                        }
                    }
                }
        );

        webView.loadUrl(START_URL);

        getOnBackPressedDispatcher().addCallback(
                this,
                new OnBackPressedCallback(true) {

                    @Override
                    public void handleOnBackPressed() {

                        if (webView.canGoBack()) {

                            webView.goBack();

                        } else {

                            finish();
                        }
                    }
                }
        );
    }

    @Override
    protected void onActivityResult(
            int requestCode,
            int resultCode,
            Intent data
    ) {

        super.onActivityResult(
                requestCode,
                resultCode,
                data
        );

        if (requestCode == FILE_CHOOSER &&
                fileCallback != null) {

            Uri[] result = null;

            if (resultCode == Activity.RESULT_OK &&
                    data != null) {

                Uri uri = data.getData();

                if (uri != null) {

                    result = new Uri[]{uri};

                } else if (data.getClipData() != null) {

                    int count =
                            data.getClipData().getItemCount();

                    result = new Uri[count];

                    for (int i = 0; i < count; i++) {

                        result[i] =
                                data.getClipData()
                                        .getItemAt(i)
                                        .getUri();
                    }
                }
            }

            fileCallback.onReceiveValue(result);

            fileCallback = null;
        }
    }
}
