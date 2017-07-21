package cn.com.luckytry.chopsticks.background;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.List;

import cn.com.luckytry.baselibrary.util.Const;
import cn.com.luckytry.baselibrary.util.FileUtil;
import cn.com.luckytry.baselibrary.util.LUtil;
import cn.com.luckytry.chopsticks.mould.ShopBean;
import cn.com.luckytry.chopsticks.util.jsoup.JsoupUtil;

/**
 * 解析爬虫数据
 * Created by 魏兴 on 2017/6/30.
 */

public class AnalysisServices extends IntentService{

    public AnalysisServices() {
        super("AnalysisServices");
    }



    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        List<ShopBean> data = DataSupport.findAll(ShopBean.class);
        if(data.size() == 0){
            LUtil.e("开始解析数据");
            File file = new File(Const.LOG_PATH);
            if(file.exists() && file.length() > 1000)
                JsoupUtil.getData();
            else{
                while (Const.isReady != 0){ }//死循环，等待html文件下载
            }
            JsoupUtil.getData();
        }else{
            LUtil.e("数据不用解析");
            Const.isReady = 1;
            if(!FileUtil.isToday(Const.dataTime)){
                JsoupUtil.getData();
            }
        }
    }
}
