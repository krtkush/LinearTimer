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
    private int endingAngle;
    private long totalDuration;
    private long timeElapsed;
    private float preFillAngle;

    private LinearTimer(Builder builder) {

        this.linearTimerView = builder.linearTimerView;
        this.timerListener = builder.timerListener;
        this.endingAngle = builder.endingAngle;
        this.totalDuration = builder.totalDuration;
        this.preFillAngle = builder.preFillAngle;
        this.timeElapsed = builder.timeElapsed;

        if(basicParametersCheck()) {

            if(timeElapsed > 0)
                setInitialFill();

            // Set the pre-fill angle.
            linearTimerView.setPreFillAngle(preFillAngle);

            // If the user wants to show the progress in counter clock wise manner,
            // we flip the view on its Y-Axis and let it function as is.
            if (builder.progressDirection == COUNTER_CLOCK_WISE_PROGRESSION) {

                ObjectAnimator objectAnimator = ObjectAnimator
                        .ofFloat(linearTimerView, "rotationY", 0.0f, 180f);
                objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                objectAnimator.start();
            }
        }
    }

    /**
     * If the user has defined timeElapsed value, this method calculates the length of pre-fill.
     */
    private void setInitialFill() {

        float timeElapsedPercentage = (((float) timeElapsed / (float) totalDuration)) * 100;
        this.preFillAngle = (timeElapsedPercentage / 100) * 360;
    }

    /**
     * Method to start the timer.
     */
    public void startTimer() {

        if(basicParametersCheck()) {
            if(arcProgressAnimation == null) {
                arcProgressAnimation = new ArcProgressAnimation(linearTimerView, endingAngle, this);
                arcProgressAnimation.setDuration(totalDuration);
                linearTimerView.startAnimation(arcProgressAnimation);
            }
        }
    }

    /**
     * Method to reset the timer to start angle and then start the progress again.
     */
    public void restartTimer() {

        if(basicParametersCheck()) {
            if(arcProgressAnimation != null) {
                arcProgressAnimation.cancel();
                linearTimerView.startAnimation(arcProgressAnimation);
            }
        }
    }

    @Override
    public void animationComplete() {
        try {
            if(listenerCheck())
                timerListener.animationComplete();
        } catch (LinearTimerListenerMissingException ex) {
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
     * Method to check whether the following basic params, needed to setup the timer,
     * have been passed by the user or not -
     * 1. LinearTimerView
     * 2. Duration
     */
    private boolean basicParametersCheck() {
        try {
            if(timerViewCheck() && durationCheck())
                return true;
        } catch (LinearTimerViewMissingException | LinearTimerDurationMissingException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    /**
     * This method checks whether the LinearTimerView's reference has been passed or not.
     */
    private boolean timerViewCheck() throws LinearTimerViewMissingException {
        if(linearTimerView == null)
            throw new LinearTimerViewMissingException("Reference to LinearTimer View missing.");
        else
            return true;
    }

    /**
     * This method checks whether a totalDuration has been provided or not.
     */
    private boolean durationCheck() throws LinearTimerDurationMissingException {
        if(totalDuration == -1)
            throw new LinearTimerDurationMissingException("Timer totalDuration missing.");
        else
            return true;
    }

    /**
     * This method checks whether the user has provided reference to the class
     * implementing the listener interface.
     */
    private boolean listenerCheck() throws LinearTimerListenerMissingException {
        if(timerListener == null)
            throw new LinearTimerListenerMissingException("");
        else
            return true;
    }

    public static class Builder {

        private int progressDirection = LinearTimer.CLOCK_WISE_PROGRESSION;
        private LinearTimerView linearTimerView = null;
        private TimerListener timerListener = null;
        private float preFillAngle = 0;
        private int endingAngle = 360;
        private long totalDuration = -1;
        private long timeElapsed = 0;

        /**
         * Not a mandatory field. Default is clock wise progression.
         * @param progressDirection Clock wise or anti-clock wise direction of the progress.
         */
        public Builder progressDirection(int progressDirection) {
            this.progressDirection = progressDirection;
            return this;
        }

        /**
         * A mandatory field.
         * @param linearTimerView The reference to the view.
         * @return
         */
        public Builder linearTimerView(LinearTimerView linearTimerView) {
            this.linearTimerView = linearTimerView;
            return this;
        }

        /**
         * Not a mandatory field.
         * @param timerListener Reference of the class implementing the TimerListener interface.
         * @return
         */
        public Builder timerListener(TimerListener timerListener) {
            this.timerListener = timerListener;
            return this;
        }

        /**
         * Not a mandatory field.
         * @param preFillAngle Angle up-till which the circle should be pre-filled.
         * @return
         */
        public Builder preFillAngle(float preFillAngle) {
            this.preFillAngle = preFillAngle;
            return this;
        }

        /**
         * A mandatory field.
         * @param totalDuration Duration, in milliseconds, for which the user wants
         *                      to run the timer for.
         * @return
         */
        public Builder duration(long totalDuration) {
            this.totalDuration = totalDuration;
            return this;
        }

        /**
         * Overloaded method.
         * When the user wants to continue the timer animation from a certain point and that point
         * is in respect to the time elapsed. USe of this method will override the `preFillAngle`
         * value.
         * @param totalDuration in milliseconds.
         * @param timeElapsed in milliseconds.
         * @return
         */
        public Builder duration(long totalDuration, long timeElapsed) {
            this.totalDuration = totalDuration;
            this.timeElapsed = timeElapsed;
            return this;
        }

        /**
         * Not a mandatory field.
         * @param endingAngle The angle at which the user wants the animation to end.
         * @return
         */
        public Builder endingAngle(int endingAngle) {
            this.endingAngle = endingAngle;
            return this;
        }

        public LinearTimer build() {
            return new LinearTimer(this);
        }
    }

    /**
     * Exception thrown when user fails to provide reference to the LinearTimerView.
     */
    private class LinearTimerViewMissingException extends Exception {

        LinearTimerViewMissingException (String message) {
            super(message);
        }
    }

    /**
     * Exception thrown when user fails to provide the totalDuration of the timer.
     */
    private class LinearTimerDurationMissingException extends Exception {

        LinearTimerDurationMissingException(String message) {
            super(message);
        }
    }

    /**
     * Exception thrown when user fails to reference to the class
     * implementing the listener interface.
     */
    private class LinearTimerListenerMissingException extends Exception {

        LinearTimerListenerMissingException(String message) {
            super(message);
        }
    }
}
