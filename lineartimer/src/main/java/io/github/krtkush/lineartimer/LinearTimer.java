package io.github.krtkush.lineartimer;

/**
 * Created by kartikeykushwaha on 18/12/16.
 */

public class LinearTimer {

    private LinearTimerView linearTimerView;
    private ArcProgressAnimation arcProgressAnimation;

    public LinearTimer(LinearTimerView linearTimerView) {

        this.linearTimerView = linearTimerView;
    }

    /**
     * @param endingAngle Value between 0-360; The point up-till which the user wants the progression.
     * @param duration Value in milliseconds; Progress animation time period.
     */
    public void startTimer(int endingAngle, long duration) {

        arcProgressAnimation = new ArcProgressAnimation(linearTimerView, endingAngle);
        arcProgressAnimation.setDuration(duration);
        linearTimerView.startAnimation(arcProgressAnimation);
    }

    public void restartTimer() {
        if(arcProgressAnimation != null) {
            linearTimerView.setPreFillAngle(0);
            arcProgressAnimation.cancel();
            linearTimerView.startAnimation(arcProgressAnimation);
        }
    }

    public void resetTimer() {
        if(arcProgressAnimation != null)
            arcProgressAnimation.cancel();
    }
}
