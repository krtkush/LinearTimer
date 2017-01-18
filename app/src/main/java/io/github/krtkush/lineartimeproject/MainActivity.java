package io.github.krtkush.lineartimeproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import io.github.krtkush.lineartimer.LinearTimer;
import io.github.krtkush.lineartimer.LinearTimerView;

public class MainActivity extends AppCompatActivity implements LinearTimer.TimerListener {

    LinearTimer linearTimer;
    LinearTimerView linearTimerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearTimerView = (LinearTimerView) findViewById(R.id.linearTimer);
        linearTimer = new LinearTimer(linearTimerView, LinearTimer.COUNTER_CLOCK_WISE_PROGRESSION);

        // Start the timer.
        findViewById(R.id.startTimer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearTimer.startTimer(360, 10 * 1000);
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
}
