package io.github.krtkush.lineartimer;

/**
 * Created by kartikeykushwaha on 18/12/16.
 */

public class LinearTimer {

    private LinearTimerView linearTimerView;

    public LinearTimer(LinearTimerView linearTimerView) {

        this.linearTimerView = linearTimerView;
    }

    /**
     * @param endingAngle Value between 0-360; The point up-till which the user wants.
     * @param duration Values in milliseconds; Progress animation time period.
     */
    public void startTimer(int endingAngle, long duration) {

        ArcProgressAnimation arcProgressAnimation =
                new ArcProgressAnimation(linearTimerView, endingAngle);
        arcProgressAnimation.setDuration(duration);
        linearTimerView.startAnimation(arcProgressAnimation);
    }
}
