package io.github.krtkush.lineartimer;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by kartikeykushwaha on 18/12/16.
 */

public class LinearTimerView extends View {

    private Paint arcPaint;
    private RectF rectF;

    private int initialColorHex;
    private int progressColorHex;
    private int circleRadiusInDp;
    private int strokeWidthInDp;

    // The point from where the color-fill animation will start.
    private int startingPointInDegrees = 270;

    // The point up-till which user wants the circle to be pre-filled.
    private float preFillAngle = 0;

    public LinearTimerView(Context context,
                           AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.LinearTimerView);

        Log.i("test", typedArray.getString(R.styleable.LinearTimerView_radius));

        this.circleRadiusInDp = (int) typedArray.getDimension(R.styleable.LinearTimerView_radius, 5);
        this.strokeWidthInDp = (int) typedArray.getDimension(R.styleable.LinearTimerView_strokeWidth, 2);
        this.initialColorHex = typedArray.getColor(R.styleable.LinearTimerView_initialColor, 0);
        this.progressColorHex = typedArray.getColor(R.styleable.LinearTimerView_progressColor, 0);
        this.startingPointInDegrees = typedArray.getInt(R.styleable.LinearTimerView_startingPoint, 270);
        this.preFillAngle = typedArray.getInt(R.styleable.LinearTimerView_preFillPoint, 0);

        // Define the size of the circle.
        rectF = new RectF(
                (int) convertDpIntoPixel(strokeWidthInDp),
                (int) convertDpIntoPixel(strokeWidthInDp),
                (int) convertDpIntoPixel(circleRadiusInDp * 2)
                        + (int) convertDpIntoPixel(strokeWidthInDp),
                (int) convertDpIntoPixel(circleRadiusInDp * 2)
                        + (int) convertDpIntoPixel(strokeWidthInDp));

        arcPaint = new Paint();
        arcPaint.setAntiAlias(true);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeWidth((int) convertDpIntoPixel(strokeWidthInDp));

        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        try {
            // Grey Circle - This circle will be there by default.
            arcPaint.setColor(initialColorHex);
            canvas.drawCircle(rectF.centerX(), rectF.centerY(),
                    (int) convertDpIntoPixel(circleRadiusInDp), arcPaint);

            // Green Arc (Arc with 360 angle) - This circle will be animated as time progresses.
            arcPaint.setColor(progressColorHex);
            canvas.drawArc(rectF, startingPointInDegrees, preFillAngle, false, arcPaint);
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
     * @param startingPointInDegrees The point from where the arc highlight should start
     * @param preFillAngle The point up-till which user wants the circle to be pre-filled.
     */
    public void defineView(int strokeWidthInDp,
                           int circleRadiusInDp,
                           String initialColorHex,
                           String finalColorHex,
                           int startingPointInDegrees,
                           int preFillAngle) {

        //int strokeWidthInPx = (int) convertDpIntoPixel(strokeWidthInDp);
        //this.initialColorHex = initialColorHex;
        //this.progressColorHex = finalColorHex;
        //this.circleRadiusInDp = circleRadiusInDp;
        //this.startingPointInDegrees = startingPointInDegrees;
        //this.preFillAngle = preFillAngle;

    }

    /**
     * Method to get the degrees up-till which the arc is already pre-filled.
     * @return
     */
    public float getPreFillAngle() {
        return preFillAngle;
    }

    public void setPreFillAngle(float preFillAngle) {
        this.preFillAngle = preFillAngle;
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
