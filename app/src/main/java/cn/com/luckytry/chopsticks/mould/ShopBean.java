package cn.com.luckytry.chopsticks.mould;

import org.litepal.crud.DataSupport;

/**
 * 饭店数据
 * Created by 魏兴 on 2017/6/30.
 */

public class ShopBean extends DataSupport{

    //该店铺位置
    private String path;

    //是否是品牌
    private boolean isBrand;
    //名称
    private String title;
    //图片地址
    private String iconUrl;
    //质量保证
    //  0 无
    //  1 票
    //  2 保
    //  3 保票
    private int QA;
    //评分
    private float score;
    //销量
    private String Sales;
    //准时达
    private boolean isPunctual;
    //蜂鸟专送
    private boolean isHummingbird;
    //价格信息
    private String priceInfo;
    //位置信息
    private String locationInfo;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isBrand() {
        return isBrand;
    }

    public void setBrand(boolean brand) {
        isBrand = brand;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public int getQA() {
        return QA;
    }

    public void setQA(int QA) {
        this.QA = QA;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getSales() {
        return Sales;
    }

    public void setSales(String sales) {
        Sales = sales;
    }

    public boolean isPunctual() {
        return isPunctual;
    }

    public void setPunctual(boolean punctual) {
        isPunctual = punctual;
    }

    public boolean isHummingbird() {
        return isHummingbird;
    }

    public void setHummingbird(boolean hummingbird) {
        isHummingbird = hummingbird;
    }

    public String getPriceInfo() {
        return priceInfo;
    }

    public void setPriceInfo(String priceInfo) {
        this.priceInfo = priceInfo;
    }

    public String getLocationInfo() {
        return locationInfo;
    }

    public void setLocationInfo(String locationInfo) {
        this.locationInfo = locationInfo;
    }
}
