package io.github.krtkush.lineartimer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by kartikeykushwaha on 18/12/16.
 */

public class LinearTimerView extends View {

    private Paint arcPaint;
    private RectF rectF;

    private String initialColorHex;
    private String finalColorHex;
    private int circleRadiusInDp;

    // The point from where the color-fill animation will start.
    private int startingPointInDegrees = 270;
    // The point up-till which user wants the circle to be pre-filled.
    private float degreesUpTillPreFill = 0;

    public LinearTimerView(Context context,
                           AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        try {
            // Grey Circle - This circle will be there by default.
            arcPaint.setColor(Color.parseColor(initialColorHex));
            canvas.drawCircle(rectF.centerX(), rectF.centerY(),
                    (int) convertDpIntoPixel(circleRadiusInDp), arcPaint);

            // Green Arc (Arc with 360 angle) - This circle will be animated as time progresses.
            arcPaint.setColor(Color.parseColor(finalColorHex));
            canvas.drawArc(rectF, startingPointInDegrees, degreesUpTillPreFill, false, arcPaint);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Method to define the basic dimension and color of timer.
     * @param strokeWidthInDp Thickness of the circle boundary.
     * @param circleRadiusInDp Radius of the circle.
     * @param initialColorHex The color of the initial circle on top which the progress
     *                        arc will be drawn.
     * @param finalColorHex The color of the progress arc.
     */
    public void defineView(int strokeWidthInDp,
                           int circleRadiusInDp,
                           String initialColorHex,
                           String finalColorHex,
                           int startingPointInDegrees) {

        int strokeWidthInPx = (int) convertDpIntoPixel(strokeWidthInDp);
        this.initialColorHex = initialColorHex;
        this.finalColorHex = finalColorHex;
        this.circleRadiusInDp = circleRadiusInDp;
        this.startingPointInDegrees = startingPointInDegrees;

        arcPaint = new Paint();
        arcPaint.setAntiAlias(true);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeWidth(strokeWidthInPx);

        // Define the size of the circle.
        rectF = new RectF(
                strokeWidthInPx,
                strokeWidthInPx,
                (int) convertDpIntoPixel(circleRadiusInDp * 2) + strokeWidthInPx,
                (int) convertDpIntoPixel(circleRadiusInDp * 2) + strokeWidthInPx);
    }

    /**
     * Method to get the degrees up-till which the arc is already pre-filled.
     * @return
     */
    public float getDegreesUpTillPreFill() {
        return degreesUpTillPreFill;
    }

    /**
     * Method to set the angle up-till witch the arc should already be drawn in
     * secondary color.
     * @param degreesUpTillPreFill
     */
    public void setDegreesUpTillPreFill(float degreesUpTillPreFill) {
        this.degreesUpTillPreFill = degreesUpTillPreFill;
    }

    /**
     * Method to convert DPs into Pixels.
     * @param dp
     * @return
     */
    private float convertDpIntoPixel(float dp) {
        float scale = getResources().getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }
}
