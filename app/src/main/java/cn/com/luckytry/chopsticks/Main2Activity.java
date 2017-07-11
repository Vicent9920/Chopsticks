package cn.com.luckytry.chopsticks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cn.com.luckytry.baselibrary.view.ShowImageWebView;
import cn.com.luckytry.chopsticks.util.bridge.BridgeWebView;

public class Main2Activity extends AppCompatActivity {

    private BridgeWebView bridgeWebView;
    private static final String TAG = "Main2Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);
        ShowImageWebView webView = (ShowImageWebView) findViewById(R.id.ShowImageWebView);
        webView.loadUrl("file:///android_asset/sina/index.html");

//        webView.loadUrl("http://www.jikedaohang.com/qianduan.html");

    }

}
