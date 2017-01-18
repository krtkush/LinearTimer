package io.github.krtkush.lineartimer;

import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by kartikeykushwaha on 18/12/16.
 */

public class ArcProgressAnimation extends Animation {

    private LinearTimerView linearTimerView;

    private float startingAngle;
    private float endingAngle;

    private TimerListener timerListener;

    public ArcProgressAnimation(LinearTimerView linearTimerView, int endingAngle,
                                TimerListener timerListener) {
        this.startingAngle = linearTimerView.getPreFillAngle();
        this.endingAngle = endingAngle;
        this.linearTimerView = linearTimerView;
        this.timerListener = timerListener;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation transformation) {

        float finalAngle = startingAngle + ((endingAngle - startingAngle) * interpolatedTime);

        linearTimerView.setPreFillAngle(finalAngle);
        linearTimerView.requestLayout();

        if(interpolatedTime == 1.0)
            timerListener.animationComplete();
    }

    /**
     * Interface to inform the implementing class that the animation has finished.
     */
    public interface TimerListener {
        void animationComplete();
    }
}
