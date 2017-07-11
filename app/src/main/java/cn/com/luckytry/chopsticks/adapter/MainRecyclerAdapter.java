package cn.com.luckytry.chopsticks.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import cn.com.luckytry.baselibrary.util.LUtil;
import cn.com.luckytry.chopsticks.R;
import cn.com.luckytry.chopsticks.mould.MainBannerBean;
import cn.com.luckytry.chopsticks.mould.PromotionBean;
import cn.com.luckytry.chopsticks.mould.ShopBean;
import cn.com.luckytry.chopsticks.view.DividerItemDecoration;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * 首页RecyclerView的适配器
 * Created by 魏兴 on 2017/6/27.
 */

public class MainRecyclerAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private int count = 2;

    public MainRecyclerAdapter(Context context){
        this.mContext = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        if (viewType == 0) {

            try {
                holder = new MainItem1(getRootView(1,parent));
            } catch (Exception e) {
                LUtil.e("第一个", e);
                e.printStackTrace();
            }
        } else if (viewType == 1) {

            try {
                holder = new MainItem2(getRootView(2,parent));
            } catch (Exception e) {
                LUtil.e("第二个", e);
                e.printStackTrace();
            }
        }else if(viewType == 2){
            holder = new MainItem3(getRootView(3,parent));
        }
        return holder;
    }

    private View getRootView(int index, ViewGroup parent) {
        int resId = R.layout.main_item1;
        if(index == 2)resId = R.layout.main_item2;
        if(index == 3) resId = R.layout.main_item3;
        return LayoutInflater.from(parent.getContext()).inflate(resId, parent, false);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else if (position == 1) {
            return 1;
        }else if(position == 2){
            return 2;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return count;
    }

    public void setCount(){
        count = 3;
        notifyDataSetChanged();
    }

    /**
     * 首页RecyclerView第一个Item
     */
    class MainItem1 extends RecyclerView.ViewHolder implements ViewPager.OnPageChangeListener {

        private List<MainBannerBean> bannerBeans = new ArrayList<>();
        private List<View> mViewPagerGridList = new ArrayList<>();
        private ViewPager mViewPagerGrid;
        private LinearLayout indexGroup;
        private ViewPager viewPager;
        private int currentIndex = 0;
        private int lastIndex = -1;

        public MainItem1(View view) {
            super(view);
            indexGroup = (LinearLayout) view.findViewById(R.id.ll_index);
            initDatas();
            //每一个banner的数据
            int pageSize = view.getContext().getResources().getInteger(R.integer.HomePageBannerColumn) * 2;
            //一共的页数等于 总数/每页数量，并取整。
            int pageCount = (int) Math.ceil(bannerBeans.size() * 1.0 / pageSize);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(9, 9);
            params.leftMargin = 5;
            params.rightMargin = 5;
            LayoutInflater inflater = LayoutInflater.from(view.getContext());
            for (int index = 0; index < pageCount; index++) {
                //每个页面都是inflate出一个新实例
                GridView grid = (GridView) inflater.inflate(R.layout.item_viewpager, mViewPagerGrid, false);
                grid.setAdapter(new GridViewAdapter(view.getContext(), bannerBeans, index));
                mViewPagerGridList.add(grid);
                ImageView img = new ImageView(view.getContext());
                if (index == 0) {
                    img.setBackgroundResource(R.drawable.point_selected_bg);
                } else {
                    img.setBackgroundResource(R.drawable.point_norml_bg);
                }
                img.setLayoutParams(params);
                indexGroup.addView(img);
            }
            viewPager = (ViewPager) view.findViewById(R.id.sl_vp_banner);
            viewPager.setAdapter(new MyViewPagerAdapter(mViewPagerGridList));
            viewPager.addOnPageChangeListener(this);
            viewPager.setCurrentItem(currentIndex);
            ImageView imgIcon1 = (ImageView) view.findViewById(R.id.roundImageView1);
            imgIcon1.setImageResource(R.mipmap.roundimageview1);
            ImageView imgIcon2 = (ImageView) view.findViewById(R.id.roundImageView2);
            imgIcon2.setImageResource(R.mipmap.roundimageview2);

        }

        /**
         * 实例化数据
         */
        private void initDatas() {
            bannerBeans.add(new MainBannerBean("美食", R.mipmap.b1));
            bannerBeans.add(new MainBannerBean("甜品饮品", R.mipmap.b2));
            bannerBeans.add(new MainBannerBean("商超便利", R.mipmap.b3));
            bannerBeans.add(new MainBannerBean("果蔬生鲜", R.mipmap.b4));
            bannerBeans.add(new MainBannerBean("新店特惠", R.mipmap.b5));
            bannerBeans.add(new MainBannerBean("准时达", R.mipmap.b6));
            bannerBeans.add(new MainBannerBean("晚餐", R.mipmap.b7));
            bannerBeans.add(new MainBannerBean("汉堡薯条", R.mipmap.b8));
            bannerBeans.add(new MainBannerBean("包子粥店", R.mipmap.b9));
            bannerBeans.add(new MainBannerBean("鲜花蛋糕", R.mipmap.ba));
            bannerBeans.add(new MainBannerBean("麻辣烫", R.mipmap.bb));
            bannerBeans.add(new MainBannerBean("川湘菜", R.mipmap.bc));
            bannerBeans.add(new MainBannerBean("披萨意面", R.mipmap.bd));
            bannerBeans.add(new MainBannerBean("异国料理", R.mipmap.be));
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (lastIndex != position) {
                if (lastIndex != -1) {
                    ImageView img = (ImageView) indexGroup.getChildAt(lastIndex);
                    img.setBackgroundResource(R.drawable.point_norml_bg);
                }
                ImageView cImg = (ImageView) indexGroup.getChildAt(position);
                cImg.setBackgroundResource(R.drawable.point_selected_bg);
            }
            lastIndex = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    /**
     * 首页RecyclerView第二个Item
     */
    class MainItem2 extends RecyclerView.ViewHolder {
        private List<PromotionBean> promotionBeans = new ArrayList<>();

        public MainItem2(View view) {
            super(view);
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rl_rl_grad);
            initData();
            recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
            recyclerView.setAdapter(new PromotionAdapter(promotionBeans));
            recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), -1));
        }

        private void initData() {
            promotionBeans.add(new PromotionBean("热卖套餐", "附近畅销美食", R.mipmap.p1));
            promotionBeans.add(new PromotionBean("霸王餐", "领20元红包", R.mipmap.p2));
            promotionBeans.add(new PromotionBean("营养快餐", "15元吃饱", R.mipmap.p3));
            promotionBeans.add(new PromotionBean("立减10元", "周二开胃菜", R.mipmap.p4));
        }
    }

    class MainItem3 extends RecyclerView.ViewHolder{

        public MainItem3(View itemView) {
            super(itemView);
            RecyclerView recyclerView = (RecyclerView) itemView.findViewById(R.id.ll_list);
            recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            List<ShopBean> data = DataSupport.findAll(ShopBean.class);
            recyclerView.setAdapter(new CommonAdapter<ShopBean>(itemView.getContext(),R.layout.item_shop,data) {

                @Override
                protected void convert(ViewHolder holder, ShopBean shopBean, int position) {
                    Glide.with(mContext).load(shopBean.getIconUrl()).into((ImageView) holder.getView(R.id.iv_icon));
                    if(shopBean.isBrand()){
                        holder.setVisible(R.id.tv_brand,true);
                    }else{
                        holder.setVisible(R.id.tv_brand,false);
                    }
                    holder.setText(R.id.tv_name,shopBean.getTitle());
                    int qa = shopBean.getQA();
                    if(qa == 0){
                        holder.setVisible(R.id.tv_brand,false);
                    }else{
                        holder.setVisible(R.id.tv_brand,true);
                        if(qa == 1){
                            holder.setText(R.id.tv_qa,"票");
                        }else if(qa == 2){
                            holder.setText(R.id.tv_qa,"保");
                        }else{
                            holder.setText(R.id.tv_qa,"保 票");
                        }
                    }
                    MaterialRatingBar ratingBar = holder.getView(R.id.library_wide_ratingbar);
                    if(shopBean.getScore() != -1){
                        ratingBar.setRating(shopBean.getScore());
                        holder.setText(R.id.tv_evaluation,shopBean.getScore()+"  "+shopBean.getSales());
                    }else{
                        ratingBar.setRating(0);
                        holder.setText(R.id.tv_evaluation,shopBean.getSales());
                    }
                    if(shopBean.isPunctual()){
                        holder.setVisible(R.id.tv_punctual,true);
                    }else{
                        holder.setVisible(R.id.tv_punctual,false);
                    }
                    if(shopBean.isHummingbird()){
                        holder.setVisible(R.id.tv_hummingbird,true);
                    }else{
                        holder.setVisible(R.id.tv_hummingbird,false);
                    }

                }
            });
        }

    }

}
