package cn.j1angvei.cnbetareader.animation;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Wayne on 2016/7/26.
 */
public class AppBarLayoutNotEmptyBehavior extends AppBarLayout.Behavior {
    private boolean isNestedScrollViewScrollable = false;
    private AppBarLayout mAppBarLayout;

    public AppBarLayoutNotEmptyBehavior() {
    }

    public AppBarLayoutNotEmptyBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, AppBarLayout child, MotionEvent ev) {
        return isNestedScrollViewScrollable && super.onInterceptTouchEvent(parent, child, ev);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout parent, AppBarLayout child, View directTargetChild, View target, int nestedScrollAxes) {
        mAppBarLayout = child;
        updateScrollableFlag(target);
        return isNestedScrollViewScrollable && super.onStartNestedScroll(parent, child, directTargetChild, target, nestedScrollAxes);
    }

    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, float velocityX, float velocityY, boolean consumed) {
        return isNestedScrollViewScrollable && super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    }

    private void updateScrollableFlag(View targetChild) {
        if (targetChild instanceof NestedScrollView) {
            int nestScrollHeight = targetChild.getHeight();
            int appBarHeight = mAppBarLayout != null ? mAppBarLayout.getHeight() : 0;
            nestScrollHeight -= appBarHeight;
            isNestedScrollViewScrollable = ((NestedScrollView) targetChild).computeVerticalScrollRange() > nestScrollHeight;
        }

    }
}
