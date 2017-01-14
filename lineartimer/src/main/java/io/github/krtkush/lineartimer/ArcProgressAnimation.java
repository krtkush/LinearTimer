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

    private boolean isPause = false;

    public ArcProgressAnimation(LinearTimerView linearTimerView, int endingAngle) {
        this.startingAngle = linearTimerView.getPreFillAngle();
        this.endingAngle = endingAngle;
        this.linearTimerView = linearTimerView;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation transformation) {

        float finalAngle = startingAngle + ((endingAngle - startingAngle) * interpolatedTime);

        linearTimerView.setPreFillAngle(finalAngle);
        linearTimerView.requestLayout();
    }
}
