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
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
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

        GradientDrawable drawable =
                new GradientDrawable(
                        GradientDrawable.Orientation.TL_BR,
                        new int[]{
                                Color.rgb(255, 235, 235),
                                Color.WHITE,
                                Color.rgb(235, 244, 255)
                        }
                );

        return drawable;
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
                                webView.getVisibility()
                                        == View.VISIBLE &&
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

        main.setGravity(Gravity.CENTER_HORIZONTAL);

        main.setPadding(
                dp(22),
                dp(30),
                dp(22),
                dp(25)
        );

        scrollView.addView(main);

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
                        dp(55)
                )
        );

        TextView tagline =
                new TextView(this);

        tagline.setText(
                "Work Smarter • Not Harder"
        );

        tagline.setTextSize(15);
        tagline.setTextColor(
                Color.rgb(90, 90, 90)
        );
        tagline.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams tagParams =
                new LinearLayout.LayoutParams(
                        -1,
                        dp(35)
                );

        tagParams.bottomMargin = dp(12);

        main.addView(tagline, tagParams);

        LinearLayout hero =
                new LinearLayout(this);

        hero.setOrientation(
                LinearLayout.VERTICAL
        );

        hero.setGravity(Gravity.CENTER);

        hero.setPadding(
                dp(20),
                dp(22),
                dp(20),
                dp(22)
        );

        hero.setBackground(
                roundedBackground(
                        Color.WHITE,
                        26
                )
        );

        LinearLayout.LayoutParams heroParams =
                new LinearLayout.LayoutParams(
                        -1,
                        dp(190)
                );

        heroParams.bottomMargin = dp(18);

        main.addView(hero, heroParams);

        TextView pdfIcon =
                new TextView(this);

        pdfIcon.setText("📄");
        pdfIcon.setTextSize(55);
        pdfIcon.setGravity(Gravity.CENTER);

        hero.addView(
                pdfIcon,
                new LinearLayout.LayoutParams(
                        -1,
                        dp(75)
                )
        );

        TextView heroTitle =
                new TextView(this);

        heroTitle.setText(
                "All Your PDF Tools\nIn One Place"
        );

        heroTitle.setTextSize(22);
        heroTitle.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );
        heroTitle.setTextColor(
                Color.rgb(35, 35, 35)
        );
        heroTitle.setGravity(Gravity.CENTER);

        hero.addView(heroTitle);

        TextView heroText =
                new TextView(this);

        heroText.setText(
                "Fast • Simple • Secure"
        );

        heroText.setTextSize(14);
        heroText.setTextColor(
                Color.rgb(110, 110, 110)
        );
        heroText.setGravity(Gravity.CENTER);

        hero.addView(heroText);

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

        LinearLayout.LayoutParams smallButton =
                new LinearLayout.LayoutParams(
                        0,
                        dp(52),
                        1
                );

        smallButton.setMargins(
                0,
                0,
                dp(6),
                0
        );

        accountRow.addView(
                register,
                smallButton
        );

        LinearLayout.LayoutParams adminParams =
                new LinearLayout.LayoutParams(
                        0,
                        dp(52),
                        1
                );

        adminParams.setMargins(
                dp(6),
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

        TextView forgot =
                new TextView(this);

        forgot.setText(
                "Forgot Password?"
        );

        forgot.setTextSize(14);
        forgot.setTextColor(
                Color.rgb(211, 47, 47)
        );
        forgot.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams forgotParams =
                new LinearLayout.LayoutParams(
                        -1,
                        dp(45)
                );

        forgotParams.topMargin = dp(8);

        main.addView(
                forgot,
                forgotParams
        );

        forgot.setOnClickListener(
                v -> openWebsite(
                        BASE_URL + "forgot"
                )
        );

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
                        dp(60)
                );

        toolsParams.topMargin = dp(12);

        main.addView(
                tools,
                toolsParams
        );

        TextView footer =
                new TextView(this);

        footer.setText(
                "Safe • Fast • Easy to Use\n\n© iLovePDF4"
        );

        footer.setTextSize(12);
        footer.setTextColor(
                Color.rgb(130, 130, 130)
        );
        footer.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams footerParams =
                new LinearLayout.LayoutParams(
                        -1,
                        dp(70)
                );

        footerParams.topMargin = dp(8);

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

        webView = new WebView(this);

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

                        progress.setVisibility(
                                newProgress >= 100
                                        ? View.GONE
                                        : View.VISIBLE
                        );
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
