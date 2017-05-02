package io.github.krtkush.lineartimer;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

/**
 * Created by kartikeykushwaha on 02/02/17.
 * 
 * CountUpTimer is similar to the Android CountDownTimer in respect to implementation and
 * behaviour.
 */
public abstract class CountUpTimer {

    /**
     * Tracks when the counter is paused
     */
    private static long pauseStart;

    /**
     * Tracks when the counter is resumed
     */
    private static long pauseEnd;

    /**
     * tracks what was the elapsed pause time
     */
    private static long elapsedPausedTime;

    /**
     * Duration for which the timer should run.
     */
    private long duration;

    /**
     * Duration after which the time update should be sent.
     */
    private final long interval;
    private long base;

    public CountUpTimer(long duration, long interval) {
        this.duration = duration;
        this.interval = interval;
    }

    public void start() {
        base = SystemClock.elapsedRealtime();
        handler.sendMessage(handler.obtainMessage(MSG));
    }

    public void stop() {
        handler.removeMessages(MSG);
        onFinish();
    }

    /**
     * Pauses the count up timer by removing the callbacks to the handler and stores
     * the time stamp when the pause button was tapped
     */
    public void pause() {
        handler.removeMessages(MSG);
        pauseStart = SystemClock.elapsedRealtime();
    }

    /**
     * Resumes the count up timer by re initiating the callbacks to the handler and stores
     * when the resume button was tapped on.
     * Since we have the pause and resume times, we can calculate the elapsed time in paused state.
     * Once we retrieve the total time spent in paused state, we add it to the base variable. (simple maths)
     */
    public void resume() {
        // Store resume time
        pauseEnd = SystemClock.elapsedRealtime();
        // Calculate elapsed paused time
        elapsedPausedTime = pauseEnd - pauseStart;
        // Add paused time to base time stamp
        base += elapsedPausedTime;
        handler.sendMessage(handler.obtainMessage(MSG));
    }

    abstract public void onTick(long elapsedTime);

    abstract public void onFinish();

    private static final int MSG = 1;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            synchronized (CountUpTimer.this) {
                long elapsedTime;

                elapsedTime = SystemClock.elapsedRealtime() - base;

                // If elapsed paused time is not zero, reset them to zero so as to begin tracking
                // any further instances of pause button being tapped on
                if(elapsedPausedTime != 0L){
                    elapsedPausedTime = 0L;
                    pauseStart = 0L;
                    pauseEnd = 0L;
                }

                // If condition set up to hinder onTick callBacks being sent if
                // elapsedtime somehow is more than the duration.
                // Stop the timer if it has run for the required duration.
                if(elapsedTime >= duration) {
                    stop();
                } else {
                    onTick(elapsedTime);
                    sendMessageDelayed(obtainMessage(MSG), interval);
                }
            }
        }
    };
}