package com.soul0914.daumpopupdictionary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.soul0914.daumpopupdictionary.utils.HtmlHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SearchResultActivity extends Activity {

    public static final String KEY_SEARCHTEXT = "KEY_SEARCHTEXT";
    private  String searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result);

        Intent intent = getIntent();
        searchText = intent.getExtras().getString("search_text");

        WebView myWebView = (WebView) findViewById(R.id.webviewResult);
        myWebView.setWebViewClient(new MyWebViewClient());
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.loadUrl("http://m.dic.daum.net/search.do?q=" +
                            HtmlHelper.encodeURIComponent(searchText) +
                            "&dic=all");
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        View view = getWindow().getDecorView();
        WindowManager.LayoutParams lp = (WindowManager.LayoutParams) view.getLayoutParams();
        lp.gravity = Gravity.LEFT | Gravity.TOP;
        lp.x = 10;
        lp.y = 10;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int halfHeight = (int)(height * 0.8);

        lp.height = halfHeight;
        getWindowManager().updateViewLayout(view, lp);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            // 다음 모바일 페이지에 JavaScript를 추가하기 위함.
            StringBuilder sb = new StringBuilder();
            InputStream is = null;

            try {
                is = getAssets().open("inject.js");

                BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String str;
                while ((str = br.readLine()) != null) {
                    sb.append(str);
                }
                br.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            view.loadUrl("javascript:" + sb.toString());
        }
    }
}
