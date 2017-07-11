package cn.com.luckytry.chopsticks.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.luckytry.chopsticks.R;
import cn.com.luckytry.chopsticks.mould.PromotionBean;

/**
 * 促销活动适配器
 * Created by 魏兴 on 2017/6/27.
 */

public class PromotionAdapter extends RecyclerView.Adapter<PromotionAdapter.MyViewHolder>{

    private List<PromotionBean> data  = new ArrayList<>();
    public PromotionAdapter(List<PromotionBean> beans){
        this.data = beans;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gridview_promotion,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PromotionBean bean = data.get(position);
        holder.tvName.setText(bean.Promotion);
        holder.tvContent.setText(bean.Introduce);
        holder.ivIcon.setImageResource(bean.resId);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView tvName;
        TextView tvContent;
        ImageView ivIcon;

        public MyViewHolder(View view)
        {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tv_Promotion);
            tvContent = (TextView) view.findViewById(R.id.tv_Introduce);
            ivIcon = (ImageView) view.findViewById(R.id.iv_img);
        }
    }
}
