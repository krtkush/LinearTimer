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
    private long duration;

    private LinearTimer(Builder builder) {

        this.linearTimerView = builder.linearTimerView;
        this.timerListener = builder.timerListener;
        this.endingAngle = builder.endingAngle;
        this.duration = builder.duration;

        // Set the pre-fill angle.
        linearTimerView.setPreFillAngle(builder.preFillAngle);

        // If the user wants to show the progress in counter clock wise manner,
        // we flip the view on its Y-Axis and let it function as is.
        if(builder.progressDirection == COUNTER_CLOCK_WISE_PROGRESSION) {

            ObjectAnimator objectAnimator = ObjectAnimator
                    .ofFloat(linearTimerView, "rotationY", 0.0f, 180f);
            objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            objectAnimator.start();
        }
    }

    /**
     * Method to start the timer.
     */
    public void startTimer() {

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

    public static class Builder {

        private int progressDirection;
        private LinearTimerView linearTimerView;
        private TimerListener timerListener;
        private float preFillAngle = 0;
        private int endingAngle = 360;
        private long duration;

        /**
         * Clock wise or anti-clock wise direction of the progress.
         * @param progressDirection
         */
        public Builder progressDirection(int progressDirection) {
            this.progressDirection = progressDirection;
            return this;
        }

        /**
         * The reference to the view.
         * @param linearTimerView
         * @return
         */
        public Builder linearTimerView(LinearTimerView linearTimerView) {
            this.linearTimerView = linearTimerView;
            return this;
        }

        /**
         * Pass the reference of the class implementing the TimerListener interface.
         * @param timerListener
         * @return
         */
        public Builder timerListener(TimerListener timerListener) {
            this.timerListener = timerListener;
            return this;
        }

        /**
         * Angle up-till which the circle should be pre-filled.
         * @param preFillAngle
         * @return
         */
        public Builder preFillAngle(float preFillAngle) {
            this.preFillAngle = preFillAngle;
            return this;
        }

        /**
         * Duration for which the user wants to run the timer for.
         * @param duration
         * @return
         */
        public Builder duration(long duration) {
            this.duration = duration;
            return this;
        }

        /**
         * The angle at which the user wnats the animation to end.
         * @param endingAngle
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
}
