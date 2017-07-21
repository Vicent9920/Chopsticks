package cn.com.luckytry.baselibrary.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.SystemClock;
import android.provider.Settings;

/**
 * Created by 魏兴 on 2017/7/14.
 */

public class Util {
    /**
     * 获取设备的mac地址
     *
     * @param ac
     * @param callback
     *      成功获取到mac地址之后会回调此方法
     */
    public static void getMacAddress(final Activity ac, final SimpleCallback callback) {
        @SuppressLint("WifiManagerLeak") final WifiManager wm = (WifiManager) ac .getSystemService(Service.WIFI_SERVICE);
        // 如果本次开机后打开过WIFI，则能够直接获取到mac信息。立刻返回数据。
        WifiInfo info = wm.getConnectionInfo();
        if (info != null && info.getMacAddress() != null) {
            if (callback != null) {
                callback.onComplete(info.getMacAddress());
            }
            return;
        }
        // 尝试打开WIFI，并获取mac地址
        if (!wm.isWifiEnabled()) {
            wm.setWifiEnabled(true);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                int tryCount = 0;
                final int MAX_COUNT = 10;
                while (tryCount < MAX_COUNT) {
                    final WifiInfo info = wm.getConnectionInfo();
                    if (info != null && info.getMacAddress() != null) {
                        if (callback != null) {
                            ac.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    callback.onComplete(info.getMacAddress());
                                }
                            });
                        }
                        return;
                    }
                    SystemClock.sleep(300);
                    tryCount++;
                }
                // 未获取到mac地址
                if (callback != null) {
                    callback.onComplete(null);
                }
            }
        }).start();
    }

    /**
     * 结果回调
     */
    public interface SimpleCallback {
        void onComplete(String result);
    }

    /**
     * 获取设备ID
     * @param context
     * @return
     */
    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }
}
