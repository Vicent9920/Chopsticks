package cn.com.luckytry.chopsticks;

import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;

import cn.com.luckytry.baselibrary.mould.PassWord;
import cn.com.luckytry.baselibrary.util.LUtil;
import cn.com.luckytry.baselibrary.util.connection.NetWorkUtil;
import cn.com.luckytry.chopsticks.util.bridge.BridgeWebView;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;
import retrofit2.Call;
import retrofit2.Callback;

public class Main2Activity extends AppCompatActivity {

    private TextView tv;
    private BridgeWebView bridgeWebView;
    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("text/x-markdown; charset=utf-8");
    private static final String TAG = "Main2Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);
//        ShowImageWebView webView = (ShowImageWebView) findViewById(R.id.ShowImageWebView);
//        webView.loadUrl("file:///android_asset/sina/index.html");

//        webView.loadUrl("http://www.jikedaohang.com/qianduan.html");

        tv = (TextView) findViewById(R.id.text);
        getPassWord();
        new Thread(){
           @Override
           public void run() {
               super.run();
               getPassWord();
           }
       };
    }


    private String getPassword(){
        try {
            OkHttpClient client = new OkHttpClient();
            RequestBody body = new RequestBody() {
                @Override
                public MediaType contentType() {
                    return null;
                }

                @Override
                public void writeTo(BufferedSink sink) throws IOException {

                }
            } ;
            String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
            LUtil.e(androidId);
            Request request = new Request.Builder()
                    .url("http://192.168.167.234:8091/co/key.do")
                    .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, "deviceId:"+123))
                    .build();

            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new IOException("Unexpected code " + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

private void getPassWord(){
    Gson gson=new Gson();

    HashMap<String,String> paramsMap = new HashMap<>();

    paramsMap.put("deviceId","173");

    String strEntity = gson.toJson(paramsMap);

    RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), strEntity);

    Call<PassWord> call = NetWorkUtil.getIntance().service.getPassWord(173);
    call.clone();
    call.enqueue(new Callback<PassWord>() {
        @Override
        public void onResponse(Call<PassWord> call, retrofit2.Response<PassWord> response) {
            tv.setText(response.body().getData());
        }

        @Override
        public void onFailure(Call<PassWord> call, Throwable t) {

        }
    });

}

}
