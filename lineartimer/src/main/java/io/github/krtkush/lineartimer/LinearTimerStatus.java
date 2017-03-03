package io.github.krtkush.lineartimer;

/**
 * The enum status codes holds the various states of LinearTimer. It may be used to diversify
 * user flow depending on which state the timer is in.
 */
public enum LinearTimerStatus {

    /**
     * The initialized state is when LinearTimer has not run even once and/or .reset() method was executed.
     * Call .startTimer() to move into the active state.
     * Integer representation : 0
     */
    INITIALIZED(0),

    /**
     * The active state is when LinearTimer is ticking. From this state, the user may only call .pauseTimer().
     * Integer representation : 1
     */
    ACTIVE(1),

    /**
     * The pause state is when the user has intentionally paused the LinearTimer using the .pauseTimer() method.
     * From this state, only .restartTimer(), .reset() & .resume() may be called.
     * * Integer representation : 2
     */
    PAUSED(2),

    /**
     * The Finished state is when LinearTimer has finished ticking and .animationComplete() callback
     * method has been executed. Only .restartTimer() and .reset() may be called.
     * * Integer representation : 3
     */
    FINISHED(3);

    /**
     * An integer to hold the integer representation of each of the statuses defined in LinearTimerStatus enum.
     */
    private int intStatusCode;

    //The constructor for LinearTimerStatus enum that accepts an integer type parameter from each of the
    //statuses on the LinearTimerStatus enum and assigns it to intStatusCode integer which may be returned
    //by getStats() method
    LinearTimerStatus(int intStatus) {
        intStatusCode = intStatus;
    }

    /**
     * A method to return the affixed integer representation of the Status codes.
     * @return An integer that represents the LinearTimer current status
     */
    public int getStaus() {
        return intStatusCode;
    }
}
