package io.github.krtkush.lineartimer;

import android.animation.ObjectAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by kartikeykushwaha on 18/12/16.
 */
public class LinearTimer implements ArcProgressAnimation.TimerListener {

    /**
     * Constant for builder method `progressDirection()`.
     */
    public static final int CLOCK_WISE_PROGRESSION = 0;

    /**
     * Constant for builder method `progressDirection()`.
     */
    public static final int COUNTER_CLOCK_WISE_PROGRESSION = 1;

    /**
     * Argument (arg1) for builder method `getCountUpdate()`.
     */
    public static final int COUNT_UP_TIMER = 2;

    /**
     * Argument (arg1) for builder method `getCountUpdate()`.
     */
    public static final int COUNT_DOWN_TIMER = 3;

    private LinearTimerView linearTimerView;
    private ArcProgressAnimation arcProgressAnimation;
    private TimerListener timerListener;
    private int endingAngle;
    private long totalDuration;
    private long timeElapsed;
    private float preFillAngle;
    private int countType;
    private long animationDuration;
    private long updateInterval;

    private LinearTimerCountDownTimer countDownTimer;
    private LinearTimerCountUpTimer countUpTimer;

    /**
     * A boolean to track which state LinerTimer is in currently. The boolean is updated
     * constantly as and when the state changes
     * Package level access given
     */
    static int intStatusCode;

    private LinearTimer(Builder builder) {

        this.linearTimerView = builder.linearTimerView;
        this.timerListener = builder.timerListener;
        this.endingAngle = builder.endingAngle;
        this.totalDuration = builder.totalDuration;
        this.preFillAngle = builder.preFillAngle;
        this.timeElapsed = builder.timeElapsed;
        this.countType = builder.countType;
        this.updateInterval = builder.updateInterval;

        if (basicParametersCheck()) {

            // Calculate the animation duration.
            animationDuration = totalDuration - timeElapsed;

            // timeElapsed = 0 does not need pre-fill angle to be determined.
            if (timeElapsed > 0)
                determinePreFillAngle();

            // Set the pre-fill angle.
            linearTimerView.setPreFillAngle(preFillAngle);

            // Store the current status code in intStatusCode integer
            intStatusCode = LinearTimerStates.INITIALIZED.getStaus();

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
    private void determinePreFillAngle() {

        float timeElapsedPercentage = (((float) timeElapsed / (float) totalDuration)) * 100;
        this.preFillAngle = (timeElapsedPercentage / 100) * 360;
    }

    /**
     * Method to start the timer.
     */
    public void startTimer() {

        if (basicParametersCheck()) {
            if (intStatusCode == LinearTimerStates.INITIALIZED.getStaus()) {
                // Store the current status code in intStatusCode integer
                intStatusCode = LinearTimerStates.ACTIVE.getStaus();
                arcProgressAnimation = new ArcProgressAnimation(linearTimerView, endingAngle, this);
                arcProgressAnimation.setDuration(animationDuration);
                linearTimerView.startAnimation(arcProgressAnimation);

                checkForCountUpdate();
            } else
                throw new IllegalStateException
                        ("LinearTimer is not in INITIALIZED state right now.");
        }
    }

    /**
     * A method to pause the running timer.
     * @throws IllegalStateException IllegalStateException is thrown if the user tries to pause
     * a timer that is not in the ACTIVE state.
     */
    public void pauseTimer() throws IllegalStateException {
        if (basicParametersCheck()) {
            // Timer may be paused only in active state.
            if (intStatusCode == LinearTimerStates.ACTIVE.getStaus()) {

                // Store the current status code in intStatusCode integer.
                intStatusCode = LinearTimerStates.PAUSED.getStaus();

                // Clear animations off of linearTimerView, set prefillAngle to current
                // state and refresh view.
                linearTimerView.clearAnimation();
                linearTimerView.setPreFillAngle(linearTimerView.getPreFillAngle());
                linearTimerView.invalidate();

                // Cancel the CountDown/CountUp timer so it stops counting up/down.
                if(countType == COUNT_DOWN_TIMER)
                    countDownTimer.cancel();
                else if(countType == COUNT_UP_TIMER)
                    countUpTimer.pause();
            } else
                throw new IllegalStateException("LinearTimer is not in active right now.");
        }
    }

    /**
     * Method to resume a paused timer.
     * @throws IllegalStateException thrown if the user attempts to resume a timer that has not been
     * paused.
     */
    public void resumeTimer() throws IllegalStateException {
        if (basicParametersCheck()) {
            if (intStatusCode == LinearTimerStates.PAUSED.getStaus()) {

                // Store the current status code in intStatusCode integer
                intStatusCode = LinearTimerStates.ACTIVE.getStaus();

                // Reinitialize the animations as it may not be simply continued.
                // The animation is reinitialized with the linearTimerView,
                // the ending angle and duration is set to pending time left from the timer.
                arcProgressAnimation = new ArcProgressAnimation(linearTimerView, endingAngle, this);
                if(countType == COUNT_DOWN_TIMER)
                    arcProgressAnimation.setDuration(countDownTimer.getMillisLeftUntilFinished());
                else if(countType == COUNT_UP_TIMER)
                    arcProgressAnimation.setDuration(countUpTimer.getTimeLeft());

                // re-initialize the countdown timer with the pending millis from previous instance
                // and start.
                if(countType == COUNT_DOWN_TIMER) {
                    countDownTimer = new LinearTimerCountDownTimer(
                            countDownTimer.getMillisLeftUntilFinished(),
                            updateInterval,
                            timerListener);
                    countDownTimer.start();
                } else if(countType == COUNT_UP_TIMER)
                    countUpTimer.resume();

                // The LinearTimerView's prefill is reset to as it was when it was paused.
                linearTimerView.setAnimation(arcProgressAnimation);

                // Start animation again.
                arcProgressAnimation.start();
            } else
                throw new IllegalStateException("LinearTimer is not in paused state right now.");
        }
    }

    /**
     * Reset the timer to start angle and then start the progress again.
     */
    public void restartTimer() {
        if (basicParametersCheck()) {
            if (arcProgressAnimation != null) {
                // Store the current status code in intStatusCode integer
                intStatusCode = LinearTimerStates.ACTIVE.getStaus();

                // Reset the pre filling angle as passed by user during initialization
                linearTimerView.setPreFillAngle(preFillAngle);

                arcProgressAnimation = new ArcProgressAnimation(linearTimerView, endingAngle, this);
                arcProgressAnimation.setDuration(animationDuration);
                // Cancel the circle animation
                arcProgressAnimation.cancel();
                // Start arc animation on the timerView
                linearTimerView.startAnimation(arcProgressAnimation);

                checkForCountUpdate();
            }
        }
    }

    /**
     * Method to reset the LinearTimer to start angle only
     */
    public void resetTimer() {
        if (basicParametersCheck()) {
            if (intStatusCode == LinearTimerStates.PAUSED.getStaus()
                    || intStatusCode == LinearTimerStates.FINISHED.getStaus()) {
                //Store the current status code in intStatusCode integer
                intStatusCode = LinearTimerStates.INITIALIZED.getStaus();

                //Cancel the circle animation
                arcProgressAnimation.cancel();
                //Reset the pre filling angle as passed by user during initialization
                linearTimerView.setPreFillAngle(preFillAngle);
                linearTimerView.invalidate();

                //Cancel the countdown timer so it stops counting up/down
                if(countType == COUNT_DOWN_TIMER)
                    countDownTimer.cancel();
                else if(countType == COUNT_UP_TIMER)
                    countUpTimer.stop();

                //Inform listeners the timer was reset
                timerListener.onTimerReset();
            } else
                throw new IllegalStateException
                        ("Cannot reset when LinearTimer is in ACTIVE or INITIALIZED state.");
        }
    }

    /**
     * A method that determines the current state of LinearTimer. One of the following is returned
     * 1) Initialized
     * 2) Active
     * 3) Paused
     * 4) Finished
     * @return Returns an enum that defines the current state of the LinearTimer.
     */
    public LinearTimerStates getState() {
        switch (intStatusCode) {
            case 0:
                return LinearTimerStates.INITIALIZED;

            case 1:
                return LinearTimerStates.ACTIVE;

            case 2:
                return LinearTimerStates.PAUSED;

            case 3:
                return LinearTimerStates.FINISHED;

            default:
                return LinearTimerStates.INITIALIZED;
        }
    }

    @Override
    public void animationComplete() {
        try {
            if (listenerCheck()) {
                // Store the current status code in intStatusCode integer
                intStatusCode = LinearTimerStates.FINISHED.getStaus();
                timerListener.animationComplete();
            }
        } catch (LinearTimerListenerMissingException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Interface to inform the implementing class about events wrt timer.
     */
    public interface TimerListener {
        /**
         * Animation complete.
         */
        void animationComplete();

        /**
         * Timer tick.
         *
         * @param tickUpdateInMillis the tick update in millis
         */
        void timerTick(long tickUpdateInMillis);
        void onTimerReset();
    }

    /**
     * Method to check whether the following basic params, needed to setup the timer,
     * have been passed by the user or not -
     * 1. LinearTimerView
     * 2. Duration
     */
    private boolean basicParametersCheck() {
        try {
            if (timerViewCheck() && durationCheck())
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
        if (linearTimerView == null)
            throw new LinearTimerViewMissingException("Reference to LinearTimer View missing.");
        else
            return true;
    }

    /**
     * This method checks whether totalDuration has been provided or not.
     */
    private boolean durationCheck() throws LinearTimerDurationMissingException {
        if (totalDuration == -1)
            throw new LinearTimerDurationMissingException("Timer totalDuration missing.");
        else
            return true;
    }

    /**
     * This method checks whether the user has provided reference to the class
     * implementing the listener interface.
     */
    private boolean listenerCheck() throws LinearTimerListenerMissingException {
        if (timerListener == null)
            throw new LinearTimerListenerMissingException("");
        else
            return true;
    }

    /**
     * Method to check if the user wants tick updates of the timer
     * and of what type - countdown or up.
     */
    private void checkForCountUpdate() {

        switch (countType) {

            case COUNT_DOWN_TIMER:
                countDownTimer = new
                        LinearTimerCountDownTimer(animationDuration,
                        updateInterval, timerListener);
                countDownTimer.start();
                break;

            case COUNT_UP_TIMER:
                countUpTimer = new LinearTimerCountUpTimer(animationDuration,
                        updateInterval, timerListener);
                countUpTimer.start();
                break;
        }
    }

    /**
     * Builder class to initialize LinearTimer.
     */
    public static class Builder {

        private int progressDirection = LinearTimer.CLOCK_WISE_PROGRESSION;
        private LinearTimerView linearTimerView = null;
        private TimerListener timerListener = null;
        private float preFillAngle = 0;
        private int endingAngle = 360;
        private long totalDuration = -1;
        private long timeElapsed = 0;
        private int countType = -1;
        private long updateInterval = 1000;

        /**
         * Not a mandatory field. Default is clock wise progression.
         *
         * @param progressDirection Clock wise or anti-clock wise direction of the progress.
         *                          LinearTimer.CLOCK_WISE_PROGRESSION
         *                          or LinearTimer.COUNTER_CLOCK_WISE_PROGRESSION
         * @return the builder
         */
        public Builder progressDirection(int progressDirection) {
            this.progressDirection = progressDirection;
            return this;
        }

        /**
         * A mandatory field.
         *
         * @param linearTimerView The reference to the view.
         * @return the builder
         */
        public Builder linearTimerView(LinearTimerView linearTimerView) {
            this.linearTimerView = linearTimerView;
            return this;
        }

        /**
         * Not a mandatory field. Use only if updates regarding animation and timer
         * ticks are needed.
         *
         * @param timerListener Reference of the class implementing the TimerListener interface.
         * @return the builder
         */
        public Builder timerListener(TimerListener timerListener) {
            this.timerListener = timerListener;
            return this;
        }

        /**
         * Not a mandatory field.
         *
         * @param preFillAngle Angle up-till which the circle should be pre-filled.
         * @return the builder
         */
        public Builder preFillAngle(float preFillAngle) {
            this.preFillAngle = preFillAngle;
            return this;
        }

        /**
         * A mandatory field. Duration for which the user wants
         * to run the timer for.
         *
         * @param totalDuration in milliseconds,
         * @return builder
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
         *
         * @param totalDuration in milliseconds.
         * @param timeElapsed   in milliseconds.
         * @return the builder
         */
        public Builder duration(long totalDuration, long timeElapsed) {
            this.totalDuration = totalDuration;
            this.timeElapsed = timeElapsed;
            return this;
        }

        /**
         * Not a mandatory field.
         *
         * @param endingAngle The angle at which the user wants the animation to end.
         * @return the builder
         */
        public Builder endingAngle(int endingAngle) {
            this.endingAngle = endingAngle;
            return this;
        }

        /**
         * Not a mandatory field.
         * This enables the LinearTimer library to return time elapsed or time left depending on the
         * type of timer applied.
         *
         * @param countType      The type of timer the user wants to show;
         *                       LinearTimer.COUNT_UP_TIMER or LinearTimer.COUNT_DOWN_TIMER.
         * @param updateInterval Duration (in millis) after which user wants the updates.
         *                       Should be > 0.
         * @return the count update
         */
        public Builder getCountUpdate(int countType, long updateInterval) {
            this.countType = countType;
            this.updateInterval = updateInterval;
            return this;
        }

        /**
         * Build linear timer.
         *
         * @return the linear timer
         */
        public LinearTimer build() {
            return new LinearTimer(this);
        }
    }

    /**
     * Exception thrown when user fails to provide reference to the LinearTimerView.
     */
    private class LinearTimerViewMissingException extends Exception {

        /**
         * Instantiates a new Linear timer view missing exception.
         *
         * @param message the message
         */
        LinearTimerViewMissingException (String message) {
            super(message);
        }
    }

    /**
     * Exception thrown when user fails to provide the totalDuration of the timer.
     */
    private class LinearTimerDurationMissingException extends Exception {

        /**
         * Instantiates a new Linear timer duration missing exception.
         *
         * @param message the message
         */
        LinearTimerDurationMissingException(String message) {
            super(message);
        }
    }

    /**
     * Exception thrown when user fails to reference to the class
     * implementing the listener interface.
     */
    private class LinearTimerListenerMissingException extends Exception {

        /**
         * Instantiates a new Linear timer listener missing exception.
         *
         * @param message the message
         */
        LinearTimerListenerMissingException(String message) {
            super(message);
        }
    }
}