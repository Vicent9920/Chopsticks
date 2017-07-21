package cn.com.luckytry.chopsticks.util.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import cn.com.luckytry.baselibrary.util.Const;
import cn.com.luckytry.baselibrary.util.FileUtil;
import cn.com.luckytry.baselibrary.util.LUtil;
import cn.com.luckytry.chopsticks.mould.ShopBean;

/**
 * 网络爬虫工具类
 * Created by 魏兴 on 2017/6/28.
 */

public class JsoupUtil{




    /**
     * 获取数据
     *      待实现线程管理
     * @param url  网页地址
     * @param cls   calss类名
     */
    public static void getData(final String url,final String cls){


        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Document doc = Jsoup.connect("http://cd.meituan.com/category/meishi?mtt=1.index%2Ffloornew.im.8.j4gslw2z").get();
                    LUtil.e("daydayup",doc.html());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();


    }

    public static void getData(){
        try {
            File input = new File(FileUtil.getFilePath("html.txt"));
            Document doc = Jsoup.parse(input, "GBK", "https://www.ele.me");
            ArrayList<Element> baseDatas = doc.select("section.index-container_2XMzI");

            if(baseDatas != null && baseDatas.size() > 0){
                DataSupport.deleteAll(ShopBean.class);
                for (int i = 0; i < baseDatas.size(); i++) {
                    ShopBean bean = new ShopBean();
                    Element element = baseDatas.get(i);
                    Element elementTitle = element.select("img").first();
                    String path = elementTitle.attr("src");
                    LUtil.e(getString(path.substring(0, path.indexOf("?"))));
                    bean.setIconUrl( getString(path.substring(0, path.indexOf("?"))));
                    bean.setTitle(getString(element.select("h3").first().text()));
                    Element element1Score = element.select(".index-rate_2O_yP").first();
                    if(element1Score != null){
                        bean.setScore(getFloat(element1Score.text()));
                    }else{
                        bean.setScore(-1);
                    }

                    ArrayList<Element> es = element.select("div.index-rateWrap_3sCb3");
                    bean.setSales(getString(es.get(es.size()-1).text()));

                    //此处取所有元素的信息是因为直接取text文本的时候会有这种数据——“?50起送 配送费?18 ?34/人”或者“ ?68起送  配送费?15  ”
                    //上面的数据包含空格不容易处理，当然也可以使用这种方法，此处是笨办法
                    String info = element.select(".index-moneylimit_1xDf3 span").first().text().replace("?", "￥");
                    bean.setPriceInfo(updateValue(info));
                    String location = element.select(".index-timedistanceWrap_2RmAM").text().replace(" ", "|");
                    bean.setLocationInfo(getString(location));

                    Element brand = element.select("div.index-premium_eGB7C").first();
                    if(brand!=null){
                        bean.setBrand(true);
                    }else{
                        bean.setBrand(false);
                    }
                    Element qa = element.select(".index-supportWrap_VWrQG").first();
                    if(qa!=null){
                        String qaInfo = qa.text();
                        if(qaInfo.contains("票")& qaInfo.contains("保")){
                            bean.setQA(3);
                        }else if(qaInfo.contains("票")){
                            bean.setQA(1);
                        }else if(qaInfo.contains("保")){
                            bean.setQA(2);
                        }

                    }else{
                        bean.setQA(0);
                    }
                    Element transport = element.select(".index-deliveryWrap_1Q_ky").first();
                    if(transport!=null){
                        String transportInfo = transport.text();
                        if(transportInfo.contains("准时达")){
                            bean.setPunctual(true);
                        }
                        if(transportInfo.contains("蜂鸟专送")){
                            bean.setHummingbird(true);
                        }
                    }else{
                        bean.setPunctual(false);
                        bean.setHummingbird(false);
                    }
                    bean.save();
                }
                Const.isReady = 1;
                Const.dataTime = System.currentTimeMillis();
            }
        } catch (Exception e) {
            LUtil.e("getData",e);
            e.printStackTrace();
        }
    }

    private static String getString(String val) {
        return val != null ? val : "";
    }
    private static float getFloat(String val){
        if(val != null){
            return Float.valueOf(val);
        }
        return 0;
    }
    private static String updateValue(String info){
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<info.length();i++){
            if(info.charAt(i) == 32 && i< 14){
                sb.append('|');

            }else{
                sb.append(info.charAt(i));
            }

        }
        return sb.toString();
    }
}
