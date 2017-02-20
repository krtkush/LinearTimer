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

    /**
     * Instantiates a new Arc progress animation.
     *
     * @param linearTimerView the linear timer view
     * @param endingAngle     the ending angle
     * @param timerListener   the timer listener
     */
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

        // If interpolatedTime = 0.0 -> Animation has started.
        // If interpolatedTime = 1.0 -> Animation has completed.
        if(interpolatedTime == 1.0)
            timerListener.animationComplete();
    }

    /**
     * Interface to inform the implementing class about events wrt timer.
     */
    public interface TimerListener {
        /**
         * Animation complete.
         */
        void animationComplete();
    }
}
