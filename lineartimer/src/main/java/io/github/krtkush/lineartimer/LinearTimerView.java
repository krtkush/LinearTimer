package io.github.krtkush.lineartimer;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by kartikeykushwaha on 18/12/16.
 */

public class LinearTimerView extends View {

    private Paint arcPaint;
    private RectF rectF;

    private int initialColor;
    private int progressColor;
    private int circleRadiusInDp;
    private int strokeWidthInDp;

    // The point from where the color-fill animation will start.
    private int startingAngle = 270;

    // The point up-till which user wants the circle to be pre-filled.
    private float preFillAngle;

    public LinearTimerView(Context context,
                           AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs,
                R.styleable.LinearTimerView, 0, 0);

        // Retrieve the view attributes.
        try {
            this.circleRadiusInDp =
                    (int) typedArray.getDimension(R.styleable.LinearTimerView_radius, 5);
            this.strokeWidthInDp =
                    (int) typedArray.getDimension(R.styleable.LinearTimerView_strokeWidth, 2);
            this.initialColor =
                    typedArray.getColor(R.styleable.LinearTimerView_initialColor,
                            ContextCompat.getColor(getContext(), R.color.colorInitial));
            this.progressColor =
                    typedArray.getColor(R.styleable.LinearTimerView_progressColor,
                            ContextCompat.getColor(getContext(), R.color.colorProgress));
            this.startingAngle =
                    typedArray.getInt(R.styleable.LinearTimerView_startingPoint, 270);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            typedArray.recycle();
        }

        init();
    }

    /**
     *  Define the size of the circle and
     */
    protected void init() {

        rectF = new RectF(
                (int) convertDpIntoPixel(strokeWidthInDp),
                (int) convertDpIntoPixel(strokeWidthInDp),
                (int) convertDpIntoPixel(circleRadiusInDp * 2) + (int) convertDpIntoPixel(strokeWidthInDp),
                (int) convertDpIntoPixel(circleRadiusInDp * 2) + (int) convertDpIntoPixel(strokeWidthInDp));

        arcPaint = new Paint();
        arcPaint.setAntiAlias(true);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeWidth((int) convertDpIntoPixel(strokeWidthInDp));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        try {
            // Grey Circle - This circle will be there by default.
            arcPaint.setColor(initialColor);
            canvas.drawCircle(rectF.centerX(), rectF.centerY(),
                    (int) convertDpIntoPixel(circleRadiusInDp), arcPaint);

            // Green Arc (Arc with 360 angle) - This circle will be animated as time progresses.
            arcPaint.setColor(progressColor);
            canvas.drawArc(rectF, startingAngle, preFillAngle, false, arcPaint);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }

        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int desiredWidth = (int) convertDpIntoPixel(circleRadiusInDp)
                + (int) convertDpIntoPixel(strokeWidthInDp);
        int desiredHeight = (int) convertDpIntoPixel(circleRadiusInDp)
                + (int) convertDpIntoPixel(strokeWidthInDp);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            width = Math.min(desiredWidth, widthSize);
        } else {
            //Be whatever you want
            width = desiredWidth;
        }

        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            //Must be this size
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            height = Math.min(desiredHeight, heightSize);
        } else {
            //Be whatever you want
            height = desiredHeight;
        }

        //MUST CALL THIS
        setMeasuredDimension(width * 2, height * 2);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
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
     * Method to get the starting point of the angle
     * @return
     */
    public int getStartingPoint() {
        return startingAngle;
    }

    public void setStartingPoint(int startingPointInDegrees) {
        this.startingAngle = startingPointInDegrees;
    }

    /**
     * Method to convert DPs into Pixels.
     */
    private float convertDpIntoPixel(float dp) {

        Resources resources = getResources();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                resources.getDisplayMetrics());
    }
}
