package io.github.krtkush.lineartimeproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import io.github.krtkush.lineartimer.LinearTimer;
import io.github.krtkush.lineartimer.LinearTimerView;

public class MainActivity extends AppCompatActivity implements LinearTimer.TimerListener {

    private LinearTimer linearTimer;
    private TextView time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearTimerView linearTimerView = (LinearTimerView) findViewById(R.id.linearTimer);
        time = (TextView) findViewById(R.id.time);

        linearTimer = new LinearTimer.Builder()
                .linearTimerView(linearTimerView)
                .duration(15 * 1000)
                .timerListener(this)
                .progressDirection(LinearTimer.COUNTER_CLOCK_WISE_PROGRESSION)
                .preFillAngle(0)
                .endingAngle(360)
                .getCountUpdate(LinearTimer.COUNT_UP_TIMER, 500)
                .build();

        // Start the timer.
        findViewById(R.id.startTimer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    linearTimer.startTimer();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Restart the timer.
        findViewById(R.id.restartTimer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearTimer.restartTimer();
            }
        });

        //Pause the timer
        findViewById(R.id.pauseTimer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    linearTimer.pauseTimer();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        //resume the timer
        findViewById(R.id.resumeTimer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    linearTimer.resumeTimer();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Reset the timer
        findViewById(R.id.resetTimer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    linearTimer.resetTimer();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
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

        time.setText(formattedTime);
    }

    @Override
    public void onTimerReset() {
        time.setText("");
    }
}
