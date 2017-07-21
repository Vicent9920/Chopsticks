package cn.com.luckytry.baselibrary.util;

import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 文件工具类
 * Created by 魏兴 on 2017/6/30.
 */

public class FileUtil {

    private String SDPATH = Environment.getExternalStorageDirectory() + "/log/";

    /**
     * 保存日志到本地
     * @param log   日志文本
     * @param fileName  保存的文件名称
     */
    public static void saveLog(String log, String fileName) {


        try {
            write(getFilePath(fileName),log,"GBK");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * 日志保存路径
     * @param fileName
     * @return
     */
    public static String getFilePath(String fileName){
        //保存路径
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/log/"+fileName;

        File tempFile = new File(path);
        if (!tempFile.exists()) {
            tempFile.mkdirs();
        }

        Const.LOG_PATH = path;
        return Const.LOG_PATH;
    }

    /**
     * 写入文件并指定编码格式
     * @param path  路径
     * @param content   文件内容
     * @param encoding  编码格式
     * @throws IOException
     */
    public  static void write(String path, String content, String encoding)
            throws IOException {
        LUtil.e("开始写入数据");
        File file = new File(path);
        if(file.exists()){
            file.delete();

        }
        file.createNewFile();
        if(file.exists()){
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file), encoding));
            writer.write(content);
            writer.close();
            Const.isReady = 0;
            Const.htmltime = System.currentTimeMillis();
            LUtil.e("数据保存完整");
        }

    }






    public static void saveFile(String fileName,String str) {
        String filePath = null;
        boolean hasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (hasSDCard) { // SD卡根目录的hello.text
            filePath = Environment.getExternalStorageDirectory().toString() + File.separator + "log/"+fileName;
            Const.LOG_PATH = filePath;
        } else{ // 系统下载缓存根目录的hello.text
            filePath = Environment.getDownloadCacheDirectory().toString() + File.separator + "log/"+fileName;
            Const.LOG_PATH = filePath;
        }


        try {

//            String iso = new String(str.getBytes("UTF-8"),"ISO-8859-1");
//            str = new String(iso.getBytes("ISO-8859-1"),"UTF-8");
            File file = new File(filePath);
            if (!file.exists()) {
                File dir = new File(file.getParent());
                dir.mkdirs();
                file.createNewFile();
            }
            FileOutputStream outStream = new FileOutputStream(file);
            outStream.write(str.getBytes());
            outStream.close();
            Const.isReady = 0;
            Const.htmltime = System.currentTimeMillis();
            LUtil.e("数据保存完毕！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        /**
         * 检查时间是否为当天
         * @param time
         * @return
         */
    public static boolean isToday(long time){
        Date date = new Date(time);
        SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd");
        if(fmt.format(date).toString().equals(fmt.format(new Date()).toString())){//格式化为相同格式
            return true;
        }else {
            return false;
        }


    }
}
