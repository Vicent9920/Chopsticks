package cn.com.luckytry.chopsticks.util.bridge;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;


/**
 * Created by bruce on 10/28/15.
 */
public class BridgeWebViewClient extends WebViewClient {
    private BridgeWebView webView;
    private String IMG = "/storage/emulated";

    public BridgeWebViewClient(BridgeWebView webView) {
        this.webView = webView;

    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        try {
            url = URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (url.startsWith(BridgeUtil.YY_RETURN_DATA)) { // 如果是返回数据
            webView.handlerReturnData(url);
            return true;
        } else if (url.startsWith(BridgeUtil.YY_OVERRIDE_SCHEMA)) { //
            webView.flushMessageQueue();
            return true;
        } else {
            return super.shouldOverrideUrlLoading(view, url);
        }
    }


//    @Override
//    public boolean shouldOverrideUrlLoading(WebView webView, String s) {
//        return super.shouldOverrideUrlLoading(webView, s);
//    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        view.loadUrl("javascript:window.local_obj.showSource('<head>'+"
                + "document.getElementsByTagName('html')[0].innerHTML+'</head>');");
        if (BridgeWebView.toLoadJs != null) {
            BridgeUtil.webViewLoadLocalJs(view, BridgeWebView.toLoadJs);
        }

        //
        if (webView.getStartupMessage() != null) {
            for (Message m : webView.getStartupMessage()) {
                webView.dispatchMessage(m);
            }
            webView.setStartupMessage(null);
        }
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView webView, String s) {
        //连接图片连接 判断是否为本地文件 如果是本地文件 则截取之后 从本地读取一个文件流进行加载
        if (s.indexOf(IMG) > 0) {

            String FileUri = s.substring(s.indexOf(IMG));
            return getWebResourceResponse(FileUri, "image/jpeg");
        }

        return super.shouldInterceptRequest(webView, s);
    }

    private WebResourceResponse getWebResourceResponse(String url, final String mime) {
        WebResourceResponse response = null;

        Bitmap bitmap = compressBitmap(url, 100, 100);
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 70, bos);
            byte[] bytes = bos.toByteArray();
            response = new WebResourceResponse(mime, "UTF-8", new ByteArrayInputStream(bytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }


    /**
     * 压缩指定路径的图片，并得到图片对象
     *
     * @param path bitmap source path
     * @return Bitmap {@link Bitmap}
     */
    public  Bitmap compressBitmap(String path, int rqsW, int rqsH) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = caculateInSampleSize(options, rqsW, rqsH);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    /**
     * caculate the bitmap sampleSize
     * 计算bitmap的尺寸
     *
     * @return
     */
    public  int caculateInSampleSize(BitmapFactory.Options options, int rqsW, int rqsH) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (rqsW == 0 || rqsH == 0) return 1;
        if (height > rqsH || width > rqsW) {
            final int heightRatio = Math.round((float) height / (float) rqsH);
            final int widthRatio = Math.round((float) width / (float) rqsW);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }
}