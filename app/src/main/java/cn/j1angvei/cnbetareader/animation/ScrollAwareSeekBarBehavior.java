package cn.j1angvei.cnbetareader.animation;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;

/**
 * Created by Wayne on 2016/7/13.
 */
public class ScrollAwareSeekBarBehavior extends CoordinatorLayout.Behavior<SeekBar> {
    public ScrollAwareSeekBarBehavior() {
    }

    public ScrollAwareSeekBarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, SeekBar child, View dependency) {
        return dependency instanceof SeekBar;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, SeekBar child, View dependency) {
        return super.onDependentViewChanged(parent, child, dependency);
    }
}
