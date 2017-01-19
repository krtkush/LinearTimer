package io.github.krtkush.lineartimer;

import android.animation.ObjectAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by kartikeykushwaha on 18/12/16.
 */

public class LinearTimer implements ArcProgressAnimation.TimerListener {

    public static final int CLOCK_WISE_PROGRESSION = 0;
    public static final int COUNTER_CLOCK_WISE_PROGRESSION = 1;

    private LinearTimerView linearTimerView;
    private ArcProgressAnimation arcProgressAnimation;

    private TimerListener timerListener;

    /**
     * Overloaded constructor for the case when user does not want to implement the
     * listener interface.
     * @param linearTimerView
     * @param progressDirection
     */
    public LinearTimer(LinearTimerView linearTimerView, int progressDirection) {

        initiateTimer(linearTimerView, progressDirection);
    }

    /**
     * Overloaded constructor for the case when user has implemented the listener interface.
     * @param linearTimerView
     * @param progressDirection
     * @param timerListener
     */
    public LinearTimer(LinearTimerView linearTimerView, int progressDirection,
                       TimerListener timerListener) {

        initiateTimer(linearTimerView, progressDirection);
        this.timerListener = timerListener;
    }

    /**
     * Method that contains the common code needed initiate the timer.
     * @param linearTimerView
     * @param progressDirection
     */
    private void initiateTimer(LinearTimerView linearTimerView, int progressDirection) {

        this.linearTimerView = linearTimerView;

        // If the user wants to show the progress in counter clock wise manner,
        // we flip the view on its Y-Axis and let it function as is.
        if(progressDirection == COUNTER_CLOCK_WISE_PROGRESSION) {

            ObjectAnimator objectAnimator = ObjectAnimator
                    .ofFloat(linearTimerView, "rotationY", 0.0f, 180f);
            objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            objectAnimator.start();
        }
    }

    /**
     * @param endingAngle Value between 0-360; The point up-till which the user wants the progression.
     * @param duration Value in milliseconds; Progress animation time period.
     */
    public void startTimer(int endingAngle, long duration) {

        if(arcProgressAnimation == null) {
            arcProgressAnimation = new ArcProgressAnimation(linearTimerView, endingAngle, this);
            arcProgressAnimation.setDuration(duration);
            linearTimerView.startAnimation(arcProgressAnimation);
        }
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

    @Override
    public void animationComplete() {
        try {
            if(timerListenerCheck())
                timerListener.animationComplete();
        } catch (TimerListenerMissingException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Interface to inform the implementing class about events wrt timer.
     */
    public interface TimerListener {
        void animationComplete();
    }

    /**
     * This method checks whether the timerListener is valid or not.
     * @throws TimerListenerMissingException This exception is thrown if the user fails
     * to provide correct reference to the class which has implemented TimerListener.
     */
    private boolean timerListenerCheck() throws TimerListenerMissingException {
        if(timerListener == null)
            throw new TimerListenerMissingException("TimerListener not found. " +
                    "Make sure TimerListener is implemented.");
        else
            return true;
    }
}
