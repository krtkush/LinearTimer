package io.github.krtkush.lineartimer;

public class LinearTimerCountUpTimer extends CountUpTimer {

    private long timerDuration;
    private long timeLeft;

    private LinearTimer.TimerListener timerListener;

    public LinearTimerCountUpTimer(long duration, long interval) {
        super(duration, interval);
    }

    public LinearTimerCountUpTimer(long timerDuration,
                                   long countUpInterval, LinearTimer.TimerListener timerListener) {
        super(timerDuration, countUpInterval);
        this.timerListener = timerListener;
        this.timerDuration = timerDuration;
    }

    @Override
    public void onTick(long elapsedTime) {
        this.timeLeft = timerDuration - elapsedTime;
        timerListener.timerTick(elapsedTime);
    }

    @Override
    public void onFinish() {
        if (LinearTimer.intStatusCode != LinearTimerStates.PAUSED.getStaus())
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