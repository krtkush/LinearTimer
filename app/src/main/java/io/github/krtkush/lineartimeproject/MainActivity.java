package io.github.krtkush.lineartimeproject;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import io.github.krtkush.lineartimer.LinearTimer;
import io.github.krtkush.lineartimer.LinearTimerView;

public class MainActivity extends AppCompatActivity implements LinearTimer.TimerListener {

    private LinearTimerView linearTimerView;
    private LinearTimer linearTimer;
    private TextView time;
    private long duration = 10 * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearTimerView = (LinearTimerView) findViewById(R.id.linearTimer);
        time = (TextView) findViewById(R.id.time);

        linearTimerView.setStrokeWidthInDp(5);
        linearTimerView.setCircleRadiusInDp(40);
        linearTimerView.setStartingPoint(90);
        linearTimerView.setInitialColor(Color.BLACK);
        linearTimerView.setProgressColor(Color.GREEN);

        linearTimer = new LinearTimer.Builder()
                .linearTimerView(linearTimerView)
                .duration(duration)
                .timerListener(this)
                .progressDirection(LinearTimer.COUNTER_CLOCK_WISE_PROGRESSION)
                .preFillAngle(0)
                .endingAngle(360)
                .getCountUpdate(LinearTimer.COUNT_UP_TIMER, 1000)
                .build();

        // Start the timer.
        findViewById(R.id.startTimer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearTimer.startTimer();
            }
        });

        // Restart the timer.
        findViewById(R.id.restartTimer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearTimer.restartTimer();
            }
        });
    }

    @Override
    public void animationComplete() {
        Log.i("Animation", "complete");
    }

    @Override
    public void timerTick(long tickUpdateInMillis) {
        Log.i("Time left", String.valueOf(tickUpdateInMillis));

        String formattedTime = String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(tickUpdateInMillis),
                TimeUnit.MILLISECONDS.toSeconds(tickUpdateInMillis)
                        - TimeUnit.MINUTES
                        .toSeconds(TimeUnit.MILLISECONDS.toHours(tickUpdateInMillis)));

        if(tickUpdateInMillis >= duration / 2)
            linearTimerView.setProgressColor(Color.RED);

        time.setText(formattedTime);
    }
}
