package cn.com.luckytry.chopsticks.view;


import android.app.ProgressDialog;
import android.webkit.WebView;

import cn.com.luckytry.chopsticks.util.bridge.BridgeWebView;
import cn.com.luckytry.chopsticks.util.bridge.BridgeWebViewClient;

/**
 * Created by Administrator on 2017/4/11 0011.
 */

public class MyWebViewClient extends BridgeWebViewClient {
    private ProgressDialog mProgressDialog;

    public MyWebViewClient(BridgeWebView webView) {
        super(webView);
    }

    public MyWebViewClient(BridgeWebView webView,ProgressDialog pProgressDialog) {
        super(webView);
        mProgressDialog = pProgressDialog;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);

        if(mProgressDialog != null){
            mProgressDialog.dismiss();
            mProgressDialog= null;
        }
    }
}