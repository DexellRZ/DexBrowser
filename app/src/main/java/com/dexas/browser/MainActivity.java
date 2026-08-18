package com.dexas.browser;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Bitmap;
import android.net.Uri;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Button;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;

public class MainActivity extends Activity {

    WebView webView;
    EditText urlBar;

    Button goButton;
    Button backButton;
    Button homeButton;
    Button reloadButton;
    Button forwardButton;

    final String HOME = "https://www.google.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webView);
        urlBar = findViewById(R.id.urlBar);

        goButton = findViewById(R.id.goButton);
        backButton = findViewById(R.id.backButton);
        homeButton = findViewById(R.id.homeButton);
        reloadButton = findViewById(R.id.reloadButton);
        forwardButton = findViewById(R.id.forwardButton);

        WebSettings settings = webView.getSettings();

        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);

        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);

        settings.setLoadWithOverviewMode(false);
        settings.setUseWideViewPort(true);

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(
                    WebView view,
                    String url,
                    Bitmap favicon) {

                updateUrl(url);
            }

            @Override
            public void onPageFinished(
                    WebView view,
                    String url) {

                updateUrl(url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(
                    WebView view,
                    WebResourceRequest request) {

                return false;
            }
        });

        goButton.setOnClickListener(v -> openUrl());

        urlBar.setOnEditorActionListener(
                (v, actionId, event) -> {

                    if (actionId == EditorInfo.IME_ACTION_GO ||
                            (event != null &&
                            event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {

                        openUrl();
                        return true;
                    }

                    return false;
                });

        backButton.setOnClickListener(v -> {

            if (webView.canGoBack()) {
                webView.goBack();
            }
        });

        forwardButton.setOnClickListener(v -> {

            if (webView.canGoForward()) {
                webView.goForward();
            }
        });

        reloadButton.setOnClickListener(v -> webView.reload());

        homeButton.setOnClickListener(v -> {

            webView.loadUrl(HOME);
        });

        webView.loadUrl(HOME);
    }

    void updateUrl(String url) {

        if (url == null) {
            return;
        }

        urlBar.setText(url);
        urlBar.setSelection(urlBar.length());
    }

    void openUrl() {

        String input = urlBar.getText().toString().trim();

        if (input.isEmpty()) {
            return;
        }

        String url;

        if (input.startsWith("http://") ||
                input.startsWith("https://")) {

            url = input;

        } else if (input.contains(".") &&
                !input.contains(" ")) {

            url = "https://" + input;

        } else {

            url = "https://www.google.com/search?q=" +
                    Uri.encode(input);
        }

        webView.loadUrl(url);
    }

    @Override
    public void onBackPressed() {

        if (webView.canGoBack()) {

            webView.goBack();

        } else {

            super.onBackPressed();
        }
    }
}
