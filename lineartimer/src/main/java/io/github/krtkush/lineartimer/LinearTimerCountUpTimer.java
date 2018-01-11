package io.github.krtkush.lineartimer;

public class LinearTimerCountUpTimer extends CountUpTimer {

    private long timerDuration;
    private long timeLeft;

    private LinearTimer.TimerListener timerListener;

    public LinearTimerCountUpTimer(long timerDuration,
                                   long countUpInterval, LinearTimer.TimerListener timerListener) {
        super(timerDuration, countUpInterval);
        this.timerListener = timerListener;
        // Reduce the time duration by 1000 millis so as to make sure that tha animation and
        // counter finish at the same time.
        this.timerDuration = timerDuration;
    }

    @Override
    public void onTick(long elapsedTime) {
        this.timeLeft = timerDuration - elapsedTime;
        if (timerListener != null)
            timerListener.timerTick(elapsedTime);
    }

    @Override
    public void onFinish() {
        if (LinearTimer.intStatusCode != LinearTimerStates.PAUSED.getStatus())
            if (timerListener != null)
                timerListener.timerTick(timerDuration);
    }

    /**
     * Returns the milliseconds remaining before the counter finishes itself
     * @return millisLeftUntilFinished
     */
    long getTimeLeft() {
        return timeLeft;
    }
}