package cn.com.luckytry.chopsticks.mould;

/**
 * 活动页实体
 * Created by 魏兴 on 2017/6/27.
 */

public class PromotionBean {
    public String Promotion;
    public String Introduce;
    public int resId;

    public PromotionBean(String promotion, String introduce, int resId) {
        Promotion = promotion;
        Introduce = introduce;
        this.resId = resId;
    }
}
