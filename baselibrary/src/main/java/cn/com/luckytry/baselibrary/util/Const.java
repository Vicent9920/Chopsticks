package cn.com.luckytry.baselibrary.util;

import android.os.Environment;

/**
 * 常量类
 * Created by 魏兴 on 2017/6/28.
 */

public class Const {

    public static String BASE_BRL = "http://cd.meituan.com/";
    /**
     * 日志文件地址
     */
    public static String LOG_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/log/html.txt";
    //html文件下载时间
    public static long htmltime = 0;
    public static long dataTime = 0;
    //解析数据状态
    //  -1代表没有html文件
    //  0代表html文件还没有被解析或者解析失败
    //  1代表html文件已经解析成功
    public static int isReady = -1;

}
