package com.example.h.learn;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebViewActivity extends AppCompatActivity {

    @BindView(R.id.my_web_view)WebView webview;
    @BindView(R.id.edit_info)EditText editInfo;
    @BindView(R.id.log_text_view)TextView logTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        ButterKnife.bind(this);
        //webview = (WebView) findViewById(R.id.my_web_view);
        Log.d("flag--","onCreate(WebViewActivity.java:20)-->>"+webview);

        initView();
    }

    @OnClick(R.id.btn_confirm)
    public void confirm(){
        Log.d("flag--","confirm(WebViewActivity.java:35)-->>"+"javascript:showEditInfo('"+editInfo.getText()+"')");
        webview.loadUrl("javascript:showEditInfo('"+editInfo.getText()+"')");
    }

    private void initView() {

        webview.loadUrl("file:///android_asset/webview.html");
        webview.setWebChromeClient(new WebChromeClient());
        WebSettings webSettings= webview.getSettings();
        //允许运行js
        webSettings.setJavaScriptEnabled(true);
        webview.addJavascriptInterface(this, "webview");
    }

    @android.webkit.JavascriptInterface
    public void actionFromJsWithParam(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String text = logTextView.getText() +  "\njs调用了Native函数传递参数：" + str;
                logTextView.setText(text);
            }
        });

    }

}
