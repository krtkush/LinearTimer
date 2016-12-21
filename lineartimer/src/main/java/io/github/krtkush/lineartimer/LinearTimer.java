package io.github.krtkush.lineartimer;

/**
 * Created by kartikeykushwaha on 18/12/16.
 */

public class LinearTimer {

    private String initialColorHex;
    private String finalColorHex;
    private int circleRadiusInDp;
    private int strokeWidth;
    private int startingPointInDegrees;
    private LinearTimerView linearTimerView;

    public LinearTimer(int circleRadiusInDp,
                       int strokeWidth,
                       int startingPointInDegrees,
                       String initialColorHex,
                       String finalColorHex,
                       LinearTimerView linearTimerView) {

        this.circleRadiusInDp = circleRadiusInDp;
        this.strokeWidth = strokeWidth;
        this.initialColorHex = initialColorHex;
        this.finalColorHex = finalColorHex;
        this.linearTimerView = linearTimerView;
        this.startingPointInDegrees = startingPointInDegrees;
    }

    /**
     * @param preFillAngle Value between 0-360; The point up-till which user wants
     *                     the circle to be pre-filled.
     * @param endingAngle Value between 0-360; The point up-till which the user wants.
     * @param duration Values in milliseconds; Progress animation time period.
     */
    public void startTimer(int preFillAngle, int endingAngle, long duration) {

        linearTimerView.defineView(strokeWidth,
                circleRadiusInDp, initialColorHex,
                finalColorHex, startingPointInDegrees, preFillAngle);

        ArcProgressAnimation arcProgressAnimation =
                new ArcProgressAnimation(linearTimerView, endingAngle);
        arcProgressAnimation.setDuration(duration);
        linearTimerView.startAnimation(arcProgressAnimation);
    }
}
