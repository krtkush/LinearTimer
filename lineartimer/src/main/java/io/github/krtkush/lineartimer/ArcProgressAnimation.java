package io.github.krtkush.lineartimer;

import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by kartikeykushwaha on 18/12/16.
 */

public class ArcProgressAnimation extends Animation {

    private LinearTimerView circle;

    private float startingAngle;
    private float endingAngle;

    public ArcProgressAnimation(LinearTimerView circle, int endingAngle) {
        this.startingAngle = circle.getPreFillAngle();
        this.endingAngle = endingAngle;
        this.circle = circle;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation transformation) {

        float finalAngle = startingAngle + ((endingAngle - startingAngle) * interpolatedTime);

        circle.setPreFillAngle(finalAngle);
        circle.requestLayout();
    }
}
