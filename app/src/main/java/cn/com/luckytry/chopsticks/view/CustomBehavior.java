package cn.com.luckytry.chopsticks.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
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
            minHeard = dependentView.get().findViewById(R.id.title_info).getHeight();
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
        clearAnimotor();
        isScroll = false;
        LUtil.e(TAG,"onNestedScrollAccepted");
    }

    /**
     * 在开始嵌套滑动之前，会执行此操作，dx、dy分别表示用户手指滑动的距离，consumed则表示在操作过程中，消耗掉的滑动距离
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param dx
     * @param dy
     * @param consumed
     */
    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        View view = dependentView.get();
        ViewGroup.LayoutParams params = view.getLayoutParams();
        int height = (int) child.getTranslationY();
        if (dy > 0 && height > minHeard) {
            if (height <= heardSize) {
                int h = height - dy;
                int H = (h < minHeard) ? minHeard : h;
                view.setTranslationY(-(heardSize-H));

                child.setTranslationY(H);
                consumed[1] = dy;
            }else if(height <= (heardSize-minHeard)){
                int h = height - dy;
                int H = (h < minHeard) ? minHeard : h;
                params.height = H;
                view.setLayoutParams(params);
                child.setTranslationY(H);
                consumed[1] = dy;
            }


        }


    }

    /**
     * 此方法在嵌套滑动时候调用，可以多滑动过程进行操作
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param dxConsumed
     * @param dyConsumed
     * @param dxUnconsumed
     * @param dyUnconsumed
     */
    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        if (dyUnconsumed > 0) {
            return;
        }
        View view = dependentView.get();
        ViewGroup.LayoutParams params = view.getLayoutParams();
        int height = (int) child.getTranslationY();
        if (dyUnconsumed < 0 && params!=null) {
            int h = height - dyUnconsumed;

            if (h >= 0 && h <= heardSize) {
                view.setTranslationY(-(heardSize-h));
                child.setTranslationY(h);
            }else if(h >= 0 && h <= (heardSize-minHeard)){
                params.height = h;
                view.setLayoutParams(params);
                child.setTranslationY(h);
            }

        }
    }

    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY) {
        return onStopDrag(child, velocityY);

    }

    private boolean onStopDrag(View child, float velocityY) {
        int height = dependentView.get().getHeight();
        if (height>minHeard){
            return true;
        }else {
            return false;
        }

    }


    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY, boolean consumed) {
        return true;
    }


    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target) {

        int height = dependentView.get().getHeight();
        float translationY = childeView.get().getTranslationY();
        if (translationY > height) {
            isExpand = true;
        } else {
            isExpand = false;
        }

        if (isExpand) {
            float pro = ((translationY - height) * 1.0f / heardSize);
            creatExpendAnimator(translationY, height, (int) (500 * pro));
        }


        if (!isScroll && height > minHeard && height < heardSize) {
            childeView.get().setScrollY(0);
            if (height < 0.7 * heardSize) {//上滑
                float pro = (height - minHeard) * 1.0f / (heardSize - minHeard);
                creatAnimation(height, minHeard, (int) (500 * pro));
            } else {//下滑
                float pro = (heardSize - height) * 1.0f / (heardSize - minHeard);
                creatAnimation(height, heardSize, (int) (500 * pro));
            }
            isScroll = true;
        }


    }


    private ValueAnimator animator;

    private void creatAnimation(float start, float end, int duration) {
        clearAnimotor();
        animator = ValueAnimator.ofFloat(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                View view = dependentView.get();
                ViewGroup.LayoutParams params = view.getLayoutParams();
                params.height = (int) value;
                view.setLayoutParams(params);
                childeView.get().setTranslationY(value);

            }
        });
        animator.setDuration(duration);
        animator.start();


    }

    private void creatExpendAnimator(float start, float end, int duration) {
        clearAnimotor();
        animator = ValueAnimator.ofFloat(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                View view = dependentView.get();
                ViewGroup.LayoutParams params = view.getLayoutParams();
                params.height = (int) value;
                view.setLayoutParams(params);
                childeView.get().setTranslationY(value);

            }
        });
        animator.setDuration(duration);
        animator.start();
    }


    private void clearAnimotor() {
        if (animator != null) {
            animator.cancel();
        }


        isScroll = false;
    }
}
