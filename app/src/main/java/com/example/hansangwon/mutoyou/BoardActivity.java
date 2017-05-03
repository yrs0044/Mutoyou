package com.example.hansangwon.mutoyou;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class BoardActivity extends AppCompatActivity {

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        WebView webView =(WebView)findViewById(R.id.webView);
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        Intent intent = getIntent();
        int number = intent.getExtras().getInt("number");

        WebView webView =(WebView)findViewById(R.id.webView);
        WebSettings set = webView.getSettings();
        set.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient());
        if(number==1)           webView.loadUrl("http://hansangwon.ipdisk.co.kr:8001/xe/index.php?mid=board");
        else if(number==2)      webView.loadUrl("http://m.uos.ac.kr/mkor/html/04_commu/commu.do");
    }
}
