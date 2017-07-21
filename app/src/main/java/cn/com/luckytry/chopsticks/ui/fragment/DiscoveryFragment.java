package cn.com.luckytry.chopsticks.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.com.luckytry.baselibrary.util.Const;
import cn.com.luckytry.baselibrary.util.LUtil;
import cn.com.luckytry.chopsticks.R;
import cn.com.luckytry.chopsticks.adapter.MainRecyclerAdapter;
import cn.com.luckytry.chopsticks.view.DividerDecorationList;

/**
 * 发现
 * Created by 魏兴 on 2017/7/13.
 */

public class DiscoveryFragment extends Fragment{

    private MainRecyclerAdapter mAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_discovery, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.nsl_rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new MainRecyclerAdapter(getContext());
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new DividerDecorationList(getContext(), DividerDecorationList.VERTICAL_LIST));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        final Handler handler = new Handler();
        if(Const.isReady == 1 ){
            mAdapter.setCount();
            LUtil.e("Adapter刷新");
        }else{
            LUtil.e("没有数据，等待解析");
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    while (Const.isReady != 1){

                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.setCount();
                            LUtil.e("Adapter刷新");
                        }
                    });
                }
            }.start();
        }
    }
}
