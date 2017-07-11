package cn.com.luckytry.baselibrary.util;

import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * 文件工具类
 * Created by 魏兴 on 2017/6/30.
 */

public class FileUtil {

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
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/log/";

        File tempFile = new File(path);
        if (!tempFile.exists()) {
            tempFile.mkdirs();
        }
        File saveFile = new File(tempFile.getAbsolutePath(),fileName);
        return saveFile.getAbsolutePath();
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
        File file = new File(path);
        file.delete();
        file.createNewFile();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(file), encoding));
        writer.write(content);
        writer.close();
    }
}
