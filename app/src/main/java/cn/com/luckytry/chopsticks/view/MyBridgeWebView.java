package cn.com.luckytry.chopsticks.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import cn.com.luckytry.chopsticks.util.bridge.BridgeWebView;


/**
 * Created by Administrator on 2017/4/26 0026.
 */

public class MyBridgeWebView extends BridgeWebView {
    public MyBridgeWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyBridgeWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MyBridgeWebView(Context context) {
        super(context);
    }
    long last_time = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                long current_time = System.currentTimeMillis();
                long d_time = current_time - last_time;
                System.out.println(d_time);;
                if (d_time < 300) {
                    last_time = current_time;
                    return true;
                } else {
                    last_time = current_time;
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}
