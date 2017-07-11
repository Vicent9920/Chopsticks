package cn.com.luckytry.baselibrary.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.AttributeSet;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.com.luckytry.baselibrary.util.FileUtil;
import cn.com.luckytry.baselibrary.util.LUtil;

/**
 * 获取html
 * Created by 魏兴 on 2017/6/29.
 */

public class ShowImageWebView extends WebView {

    private List<String> listImgSrc = new ArrayList<>();



    // 获取img标签正则
    private static final String IMAGE_URL_TAG = "<img.*src=(.*?)[^>]*?>";
    // 获取src路径的正则
    private static final String IMAGE_URL_CONTENT = "http:\"?(.*?)(\"|>|\\s+)";

    private String url;
    private String longClickUrl;
    private Context context;

    public ShowImageWebView(Context context) {
        super(context);
        init(context);
    }

    public ShowImageWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ShowImageWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    private void init(Context context){
        this.context=context;


        this.getSettings().setJavaScriptEnabled(true);
        this.getSettings().setDefaultTextEncodingName("UTF -8");
        //启用数据库
        this.getSettings().setDatabaseEnabled(true);

//设置定位的数据库路径
        String dir = this.getContext().getDir("database", Context.MODE_PRIVATE).getPath();
        this.getSettings().setGeolocationDatabasePath(dir);

//启用地理定位
        this.getSettings().setGeolocationEnabled(true);

//开启DomStorage缓存
        this.getSettings().setDomStorageEnabled(true);
        //载入js
        this.addJavascriptInterface(new MyJavascriptInterface(context), "imageListener");
        //获取 html
        this.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");

//配置权限
       setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);

            }

           @Override
           public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
               callback.invoke(origin, true, false);
               super.onGeolocationPermissionsShowPrompt(origin, callback);
           }
       });



        setWebViewClient(new WebViewClient(){
            // 网页加载结束
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                //解析 HTML
               parseHTML(view);
            }
        });



    }



    /**
     * 解析 HTML 该方法在 setWebViewClient 的 onPageFinished 方法中进行调用
     * @param view
     */
    public void parseHTML(final WebView view) {


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                post(new Runnable() {
                    @Override
                    public void run() {
                        view.loadUrl("javascript:window.local_obj.showSource('<head>'+"
                                + "document.getElementsByTagName('body')[0].innerHTML+'</head>');");
                    }
                });
            }
        },20000);


    }


    // js 通信接口，定义供 JavaScript 调用的交互接口
    private class MyJavascriptInterface {
        private Context context;
        public MyJavascriptInterface(Context context) {
            this.context = context;
        }

        /**
         * 点击图片启动新的 ShowImageFromWebActivity，并传入点击图片对应的 url 和页面所有图片
         * 对应的 url
         *
         * @param url 点击图片对应的 url
         */
        @android.webkit.JavascriptInterface
        public void startShowImageActivity(String url) {

        }
    }

    private class InJavaScriptLocalObj {
        /**
         * 获取要解析 WebView 加载对应的 Html 文本
         *
         * @param html WebView 加载对应的 Html 文本
         */
        @android.webkit.JavascriptInterface
        public void showSource(String html) {
            LUtil.e("JavascriptInterface",html);
            FileUtil.saveLog(html,"html.txt");

            Matcher matcher = Pattern.compile(IMAGE_URL_TAG).matcher(html);

            while (matcher.find()) {
//                LUtil.e("showSource",matcher.group());
            }
        }
    }

    /***
     * 获取页面所有图片对应的地址对象，
     * 例如 <img src="http://sc1.hao123img.com/data/f44d0aab7bc35b8767de3c48706d429e" />
     *
     * @param html WebView 加载的 html 文本
     * @return
     */
    private List<String> getAllImageUrlFromHtml(String html) {
        Matcher matcher = Pattern.compile(IMAGE_URL_TAG).matcher(html);
        List<String> listImgUrl = new ArrayList<String>();
        while (matcher.find()) {
            listImgUrl.add(matcher.group());
        }

        return listImgUrl;
    }

    /***
     * 从图片对应的地址对象中解析出 src 标签对应的内容,即 url
     * 例如 "http://sc1.hao123img.com/data/f44d0aab7bc35b8767de3c48706d429e"
     * @param listImageUrl 图片地址对象，
     *                     例如 <img src="http://sc1.hao123img.com/data/f44d0aab7bc35b8767de3c48706d429e" />
     */
    private List<String> getAllImageUrlFormSrcObject(List<String> listImageUrl) {
        for (String image : listImageUrl) {
            Matcher matcher = Pattern.compile(IMAGE_URL_CONTENT).matcher(image);
            while (matcher.find()) {
                listImgSrc.add(matcher.group().substring(0, matcher.group().length() - 1));
            }
        }
        return listImgSrc;
    }



}

