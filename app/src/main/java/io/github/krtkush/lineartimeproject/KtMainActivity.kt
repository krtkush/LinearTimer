package io.github.krtkush.lineartimeproject

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import io.github.krtkush.lineartimer.LinearTimer
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class KtMainActivity : AppCompatActivity(), LinearTimer.TimerListener {

    private var linearTimer: LinearTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val duration = (10 * 1000).toLong()

        // Assign attribute values to the view programmatically
        // This will override the attributes set via the XML.
        /*linearTimerView.setStrokeWidthInDp(5);
        linearTimerView.setCircleRadiusInDp(40);
        linearTimerView.setStartingPoint(90);
        linearTimerView.setInitialColor(Color.BLACK);
        linearTimerView.setProgressColor(Color.GREEN);*/

        linearTimer = LinearTimer.Builder()
                .linearTimerView(linearTimerView)
                .duration(duration)
                .timerListener(this)
                .getCountUpdate(LinearTimer.COUNT_DOWN_TIMER, 1000)
                .build()

        // Start the timer.
        startTimer.setOnClickListener {
            try {
                linearTimer!!.startTimer()
            } catch (e: IllegalStateException) {
                e.printStackTrace()
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
        }

        // Restart the timer.
        restartTimer.setOnClickListener { linearTimer!!.restartTimer() }

        // Pause the timer
        pauseTimer.setOnClickListener {
            try {
                linearTimer!!.pauseTimer()
            } catch (e: IllegalStateException) {
                e.printStackTrace()
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
        }

        // Resume the timer
        resumeTimer.setOnClickListener {
            try {
                linearTimer!!.resumeTimer()
            } catch (e: IllegalStateException) {
                e.printStackTrace()
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
        }

        // Reset the timer
        resetTimer.setOnClickListener {
            try {
                linearTimer!!.resetTimer()
            } catch (e: IllegalStateException) {
                e.printStackTrace()
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun animationComplete() {
        Log.i("Animation", "complete")
    }

    override fun timerTick(tickUpdateInMillis: Long) {
        Log.i("Time left", tickUpdateInMillis.toString())

        val formattedTime = String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(tickUpdateInMillis),
                TimeUnit.MILLISECONDS.toSeconds(tickUpdateInMillis) - TimeUnit.MINUTES
                        .toSeconds(TimeUnit.MILLISECONDS.toHours(tickUpdateInMillis)))

        time!!.text = formattedTime
    }

    override fun onTimerReset() {
        time!!.text = ""
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.about_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        when (item.itemId) {
            R.id.about -> {

                val goToAbout = Intent(this, About::class.java)
                startActivity(goToAbout)

                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }
}