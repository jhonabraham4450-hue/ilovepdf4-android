package com.ilovepdf4.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.app.DownloadManager;
import android.os.Environment;
import android.webkit.URLUtil;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.webkit.WebSettingsCompat;
import androidx.webkit.WebViewFeature;

public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL =
            "https://ilovepdf4login.jhonabraham4450.workers.dev/";

    private static final String ADMIN_URL =
            "https://ilovepdf4login.jhonabraham4450.workers.dev/admin";

    private WebView webView;
    private ProgressBar progress;
    private ValueCallback<Uri[]> fileCallback;

    private static final int FILE_CHOOSER = 1001;

    private int dp(float value) {
        return (int) (
                value * getResources()
                        .getDisplayMetrics()
                        .density
        );
    }

    private GradientDrawable roundedBackground(
            int color,
            float radius
    ) {
        GradientDrawable drawable =
                new GradientDrawable();

        drawable.setColor(color);
        drawable.setCornerRadius(dp(radius));

        return drawable;
    }

    private GradientDrawable gradientBackground() {

        return new GradientDrawable(
                GradientDrawable.Orientation.TL_BR,
                new int[]{
                        Color.rgb(255, 235, 235),
                        Color.WHITE,
                        Color.rgb(235, 244, 255)
                }
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(
                Color.rgb(183, 28, 28)
        );

        getWindow().setNavigationBarColor(
                Color.WHITE
        );

        showWelcomeScreen();

        getOnBackPressedDispatcher().addCallback(
                this,
                new OnBackPressedCallback(true) {

                    @Override
                    public void handleOnBackPressed() {

                        if (webView != null &&
                                webView.getVisibility() ==
                                        View.VISIBLE &&
                                webView.canGoBack()) {

                            webView.goBack();

                        } else {

                            finish();
                        }
                    }
                }
        );
    }

    private void showWelcomeScreen() {

        ScrollView scrollView =
                new ScrollView(this);

        scrollView.setFillViewport(true);
        scrollView.setBackground(
                gradientBackground()
        );

        LinearLayout main =
                new LinearLayout(this);

        main.setOrientation(
                LinearLayout.VERTICAL
        );

        main.setGravity(
                Gravity.CENTER_HORIZONTAL
        );

        main.setPadding(
                dp(18),
                dp(24),
                dp(18),
                dp(22)
        );

        scrollView.addView(main);

        // LOGO
        TextView logo =
                new TextView(this);

        logo.setText("iLovePDF4");
        logo.setTextSize(32);
        logo.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );
        logo.setTextColor(
                Color.rgb(211, 47, 47)
        );
        logo.setGravity(Gravity.CENTER);

        main.addView(
                logo,
                new LinearLayout.LayoutParams(
                        -1,
                        dp(52)
                )
        );

        // TAGLINE
        TextView tagline =
                new TextView(this);

        tagline.setText(
                "Work Smarter • Not Harder"
        );

        tagline.setTextSize(14);
        tagline.setTextColor(
                Color.rgb(90, 90, 90)
        );
        tagline.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams taglineParams =
                new LinearLayout.LayoutParams(
                        -1,
                        dp(32)
                );

        taglineParams.bottomMargin = dp(12);

        main.addView(
                tagline,
                taglineParams
        );

        // IMAGE CARD
        LinearLayout imageCard =
                new LinearLayout(this);

        imageCard.setOrientation(
                LinearLayout.VERTICAL
        );

        imageCard.setGravity(Gravity.CENTER);

        imageCard.setPadding(
                dp(8),
                dp(8),
                dp(8),
                dp(8)
        );

        imageCard.setBackground(
                roundedBackground(
                        Color.WHITE,
                        24
                )
        );

        LinearLayout.LayoutParams imageCardParams =
                new LinearLayout.LayoutParams(
                        -1,
                        dp(270)
                );

        imageCardParams.bottomMargin = dp(18);

        main.addView(
                imageCard,
                imageCardParams
        );

        // ACTUAL IMAGE
        ImageView homeImage =
                new ImageView(this);

        homeImage.setImageResource(
                R.drawable.ilovepdf4_home
        );

        homeImage.setScaleType(
                ImageView.ScaleType.CENTER_CROP
        );

        homeImage.setAdjustViewBounds(true);

        imageCard.addView(
                homeImage,
                new LinearLayout.LayoutParams(
                        -1,
                        -1
                )
        );

        // WELCOME
        TextView welcome =
                new TextView(this);

        welcome.setText(
                "Welcome Back!"
        );

        welcome.setTextSize(26);
        welcome.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        welcome.setTextColor(
                Color.rgb(35, 35, 35)
        );

        welcome.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams welcomeParams =
                new LinearLayout.LayoutParams(
                        -1,
                        dp(45)
                );

        welcomeParams.bottomMargin = dp(10);

        main.addView(
                welcome,
                welcomeParams
        );

        // LOGIN
        Button login =
                createButton(
                        "Login",
                        Color.rgb(229, 57, 53),
                        Color.WHITE
                );

        LinearLayout.LayoutParams loginParams =
                new LinearLayout.LayoutParams(
                        -1,
                        dp(54)
                );

        loginParams.bottomMargin = dp(12);

        main.addView(
                login,
                loginParams
        );

        login.setOnClickListener(
                v -> openWebsite(BASE_URL)
        );

        // REGISTER + ADMIN
        LinearLayout accountRow =
                new LinearLayout(this);

        accountRow.setOrientation(
                LinearLayout.HORIZONTAL
        );

        accountRow.setGravity(
                Gravity.CENTER
        );

        Button register =
                createButton(
                        "Register",
                        Color.WHITE,
                        Color.rgb(211, 47, 47)
                );

        Button admin =
                createButton(
                        "Admin Login",
                        Color.rgb(35, 35, 35),
                        Color.WHITE
                );

        LinearLayout.LayoutParams registerParams =
                new LinearLayout.LayoutParams(
                        0,
                        dp(52),
                        1
                );

        registerParams.setMargins(
                0,
                0,
                dp(5),
                0
        );

        accountRow.addView(
                register,
                registerParams
        );

        LinearLayout.LayoutParams adminParams =
                new LinearLayout.LayoutParams(
                        0,
                        dp(52),
                        1
                );

        adminParams.setMargins(
                dp(5),
                0,
                0,
                0
        );

        accountRow.addView(
                admin,
                adminParams
        );

        main.addView(
                accountRow,
                new LinearLayout.LayoutParams(
                        -1,
                        dp(52)
                )
        );

        register.setOnClickListener(
                v -> openWebsite(
                        BASE_URL + "register"
                )
        );

        admin.setOnClickListener(
                v -> openWebsite(ADMIN_URL)
        );

        // FORGOT PASSWORD
        TextView forgot =
                new TextView(this);

        forgot.setText(
                "Forgot Password?"
        );

        forgot.setTextSize(14);
        forgot.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        forgot.setTextColor(
                Color.rgb(211, 47, 47)
        );

        forgot.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams forgotParams =
                new LinearLayout.LayoutParams(
                        -1,
                        dp(45)
                );

        forgotParams.topMargin = dp(6);

        main.addView(
                forgot,
                forgotParams
        );

        forgot.setOnClickListener(
                v -> openWebsite(
                        BASE_URL + "forgot"
                )
        );

        // TOOLS
        TextView tools =
                new TextView(this);

        tools.setText(
                "PDF  •  Word  •  Excel  •  PowerPoint\n" +
                "Merge  •  Split  •  Compress  •  Convert"
        );

        tools.setTextSize(13);
        tools.setTextColor(
                Color.rgb(100, 100, 100)
        );

        tools.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams toolsParams =
                new LinearLayout.LayoutParams(
                        -1,
                        dp(58)
                );

        toolsParams.topMargin = dp(8);

        main.addView(
                tools,
                toolsParams
        );

        // FOOTER
        TextView footer =
                new TextView(this);

        footer.setText(
                "Safe • Fast • Easy to Use\n\n" +
                "© iLovePDF4"
        );

        footer.setTextSize(12);
        footer.setTextColor(
                Color.rgb(130, 130, 130)
        );

        footer.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams footerParams =
                new LinearLayout.LayoutParams(
                        -1,
                        dp(65)
                );

        footerParams.topMargin = dp(5);

        main.addView(
                footer,
                footerParams
        );

        setContentView(scrollView);
    }

    private Button createButton(
            String text,
            int background,
            int textColor
    ) {

        Button button =
                new Button(this);

        button.setText(text);
        button.setTextSize(14);
        button.setTextColor(textColor);

        button.setAllCaps(false);

        button.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        button.setGravity(Gravity.CENTER);

        button.setPadding(
                dp(5),
                0,
                dp(5),
                0
        );

        button.setBackground(
                roundedBackground(
                        background,
                        16
                )
        );

        return button;
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void openWebsite(String url) {

        RelativeLayout root =
                new RelativeLayout(this);

        root.setBackgroundColor(
                Color.WHITE
        );

        webView =
                new WebView(this);

        RelativeLayout.LayoutParams webParams =
                new RelativeLayout.LayoutParams(
                        -1,
                        -1
                );

        root.addView(
                webView,
                webParams
        );

        progress =
                new ProgressBar(this);

        progress.setIndeterminate(true);

        RelativeLayout.LayoutParams progressParams =
                new RelativeLayout.LayoutParams(
                        dp(70),
                        dp(70)
                );

        progressParams.addRule(
                RelativeLayout.CENTER_IN_PARENT
        );

        root.addView(
                progress,
                progressParams
        );

        setContentView(root);

        WebSettings settings =
                webView.getSettings();

        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);

        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(true);

        settings.setMediaPlaybackRequiresUserGesture(
                false
        );

        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);

        settings.setJavaScriptCanOpenWindowsAutomatically(
                true
        );

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

        CookieManager cookies =
                CookieManager.getInstance();

        cookies.setAcceptCookie(true);

        cookies.setAcceptThirdPartyCookies(
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

                        Uri uri =
                                request.getUrl();

                        String host =
                                uri.getHost();

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
                            String page
                    ) {

                        if (progress != null) {

                            progress.setVisibility(
                                    View.GONE
                            );
                        }
                    }
                }
        );

   webView.setDownloadListener(
        (url, userAgent, contentDisposition, mimetype, contentLength) -> {

            try {

                DownloadManager.Request request =
                        new DownloadManager.Request(
                                Uri.parse(url)
                        );

                String cookies =
                        CookieManager.getInstance()
                                .getCookie(url);

                if (cookies != null) {
                    request.addRequestHeader(
                            "Cookie",
                            cookies
                    );
                }

                request.addRequestHeader(
                        "User-Agent",
                        userAgent
                );

                String fileName =
                        URLUtil.guessFileName(
                                url,
                                contentDisposition,
                                mimetype
                        );

                request.setTitle(fileName);

                request.setDescription(
                        "Downloading from iLovePDF4"
                );

                request.setNotificationVisibility(
                        DownloadManager.Request
                                .VISIBILITY_VISIBLE_NOTIFY_COMPLETED
                );

                request.setMimeType(mimetype);

                request.setDestinationInExternalPublicDir(
                        Environment.DIRECTORY_DOWNLOADS,
                        fileName
                );

                DownloadManager manager =
                        (DownloadManager)
                                getSystemService(
                                        DOWNLOAD_SERVICE
                                );

                if (manager != null) {
                    manager.enqueue(request);
                }

            } catch (Exception e) {
                e.printStackTrace();
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

                            fileCallback.onReceiveValue(
                                    null
                            );
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

                        if (progress != null) {

                            progress.setVisibility(
                                    newProgress >= 100
                                            ? View.GONE
                                            : View.VISIBLE
                            );
                        }
                    }
                }
        );

        webView.loadUrl(url);
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

                } else if (
                        data.getClipData() != null
                ) {

                    int count =
                            data.getClipData()
                                    .getItemCount();

                    result = new Uri[count];

                    for (int i = 0; i < count; i++) {

                        result[i] =
                                data.getClipData()
                                        .getItemAt(i)
                                        .getUri();
                    }
                }
            }

            fileCallback.onReceiveValue(
                    result
            );

            fileCallback = null;
        }
    }
}
