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
        (findViewById(R.id.startTimer)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimer();
            }
        });
    }

    private void startTimer() {

        linearTimer = new LinearTimer(linearTimerView);
        linearTimer.startTimer(360, 60 * 1000);
    }
}
