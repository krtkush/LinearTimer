package io.github.krtkush.lineartimeproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import io.github.krtkush.lineartimer.LinearTimer;
import io.github.krtkush.lineartimer.LinearTimerView;

public class MainActivity extends AppCompatActivity {

    LinearTimer linearTimer;
    LinearTimerView linearTimerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearTimerView = (LinearTimerView) findViewById(R.id.linearTimer);
        linearTimer = new LinearTimer(linearTimerView);

        // Start the timer.
        findViewById(R.id.startTimer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearTimer.startTimer(360, 60 * 1000);
            }
        });

        // Reset the timer.
        findViewById(R.id.resetTimer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearTimer.resetTimer();
            }
        });

        // Restart the timer.
        findViewById(R.id.restartTimer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearTimer.restartTimer();
            }
        });

        // Pause the timer
        findViewById(R.id.pauseTimer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearTimer.pauseTimer();
            }
        });

        // Resume the timer
        findViewById(R.id.resumeTimer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearTimer.resumeTimer();
            }
        });
    }
}
