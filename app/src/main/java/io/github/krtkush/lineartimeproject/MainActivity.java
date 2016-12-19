package io.github.krtkush.lineartimeproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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

        linearTimer = new LinearTimer(50, 5, 270, "#D3D3D3", "#008000", linearTimerView);
        linearTimer.startTimer(0, 360, 60 * 1000);
    }
}
