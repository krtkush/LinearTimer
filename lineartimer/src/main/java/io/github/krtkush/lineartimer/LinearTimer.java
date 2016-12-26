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

    /**
     * Method to reset the timer to start angle and then start the progress again.
     */
    public void restartTimer() {
        if(arcProgressAnimation != null) {
            arcProgressAnimation.cancel();
            linearTimerView.startAnimation(arcProgressAnimation);
        }
    }

    /**
     * Method to reset the timer to start angle.
     */
    public void resetTimer() {
        if(arcProgressAnimation != null)
            arcProgressAnimation.cancel();
    }
}
