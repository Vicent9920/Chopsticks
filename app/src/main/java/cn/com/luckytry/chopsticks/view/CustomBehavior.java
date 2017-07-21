package cn.com.luckytry.chopsticks.view;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import java.lang.ref.WeakReference;

import cn.com.luckytry.baselibrary.util.LUtil;
import cn.com.luckytry.chopsticks.R;

/**
 * Created by 魏兴 on 2017/7/14.
 */

public class CustomBehavior extends CoordinatorLayout.Behavior {


    private static final String TAG = "CustomBehavior";
    private WeakReference<View> dependentView;
    private WeakReference<View> childeView;
    private View hintView;
    private boolean isScroll;
    private boolean isExpand;
    private int heardSize = -1;
    private int minHeard = -1;

    private int translationY = 0;
    private int mminHeard = -1;
    private int displayHeight ;

    public CustomBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    /**
     * 用来判断child是否有一个对应的dependency
     * 如果有就返回true，默认情况下返回的是false
     * @param parent
     * @param child
     * @param dependency
     * @return
     */
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        if (child != null) {
            childeView = new WeakReference<View>(child);
        }
        if (dependency != null && dependency instanceof RelativeLayout) {
            dependentView = new WeakReference<>(dependency);
            hintView = dependency.findViewById(R.id.shop_hint);
            return true;
        }
        return super.layoutDependsOn(parent, child, dependency);
    }

    /**
     * 此方法可用于动态更改childView的布局
     * 如果自定义Behaior,这个方法一定返回true，否则将使用默认Behavior的布局
     * @param parent
     * @param child
     * @param layoutDirection
     * @return
     */
    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {


        child.layout(0, 0, parent.getWidth(), (parent.getHeight() - dependentView.get().getHeight()));
        if (heardSize == -1) {
            heardSize = dependentView.get().getHeight();
            minHeard = heardSize - dependentView.get().findViewById(R.id.title_info).getHeight();
            mminHeard = dependentView.get().findViewById(R.id.shop_hint).getHeight()+
                    child.getResources().getDimensionPixelSize(R.dimen.mminheard);
            displayHeight =  child.getResources().getDimensionPixelSize(R.dimen.displayHeight);
            child.setTranslationY(heardSize);
        }

        return true;

    }

    /**
     * 这里可以进行一些过滤操作，比如只接受垂直方向的滑动等   ViewCompat.SCROLL_AXIS_VERTICAL
     * @param coordinatorLayout
     * @param child
     * @param directTargetChild
     * @param target
     * @param nestedScrollAxes  滑动方向
     * @return
     */
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {

        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    /**
     * 滑动初始化
     * 在onStartNestedScroll返回为true的时候调用，此时表示CoordinatorLayout已经拦截了滑动（在每次滑动的时候都会调用）
     *
     * @param coordinatorLayout
     * @param child
     * @param directTargetChild
     * @param target
     * @param nestedScrollAxes
     */
    @Override
    public void onNestedScrollAccepted(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        //清除动画
//        clearAnimotor();
        isScroll = false;
        LUtil.e(TAG,"onNestedScrollAccepted");
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        View view = dependentView.get();
        int height = (int) target.getTranslationY();


        if ( Math.abs(translationY) <= minHeard) {

            int y = Math.abs(dy + translationY) >= minHeard ? minHeard - translationY:dy;


            LUtil.e(TAG,"dy:"+y);
//            view.setTranslationY( - (translationY + y));


        }else if(  Math.abs(translationY) <= (minHeard+displayHeight)){

            int moveDY = (translationY + dy) - minHeard;
            LUtil.e(TAG,"准备移动——dy:"+ dy +"translationY:"+ translationY);
            hintView.setTranslationY(-moveDY);

        }
        translationY += dy;
        LUtil.e(TAG,"translationY:" + translationY);

    }
}
