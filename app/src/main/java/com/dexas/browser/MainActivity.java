package com.dexas.browser;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Bitmap;
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

    String HOME = "https://www.google.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(com.dexas.browser.R.layout.activity_main);

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

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(
                    WebView view,
                    String url,
                    Bitmap favicon) {

                urlBar.setText(url);
            }

            @Override
            public void onPageFinished(
                    WebView view,
                    String url) {

                urlBar.setText(url);
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

        homeButton.setOnClickListener(v -> webView.loadUrl(HOME));

        webView.loadUrl(HOME);
    }

    void openUrl() {

        String url = urlBar.getText().toString().trim();

        if (url.isEmpty()) {
            return;
        }

        if (!url.startsWith("http://") &&
            !url.startsWith("https://")) {

            if (url.contains(".")) {
                url = "https://" + url;
            } else {
                url = "https://www.google.com/search?q=" +
                        url.replace(" ", "+");
            }
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
