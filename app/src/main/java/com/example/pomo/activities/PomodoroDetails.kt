package com.example.pomo.activities

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pomo.R
import com.example.pomo.model.PomodoroSession
import com.example.pomo.viewmodel.PomodoroSessionViewModel
import com.example.pomo.util.Stopwatch
import java.util.*

class PomodoroDetails : AppCompatActivity() {
    private lateinit var countdownText: TextView
    private lateinit var mPomodoroSessionViewModel: PomodoroSessionViewModel
    private lateinit var message: PomodoroDataItem
    private lateinit var currentStopWatch: Stopwatch
    private val startTime = Calendar.getInstance().time
//    private var red = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pomodoro_details)

        message = intent.getSerializableExtra("Message") as PomodoroDataItem
        mPomodoroSessionViewModel = ViewModelProvider(this).get(PomodoroSessionViewModel::class.java)
        countdownText = findViewById(R.id.countdown_text)

        val breakStopwatch = createBreakStopWatch(message.studyTime * 60000)
        val studyStopWatch = createStudyStopWatch(message.breakTime * 60000, breakStopwatch)
        studyStopWatch.startTimer()
        currentStopWatch = studyStopWatch
        createPauseButton()
        createCancelButton()
    }

    private fun createBreakStopWatch(seconds: Float): Stopwatch {
        val breakStopWatch = Stopwatch(seconds) {
            insertDataToDatabase()
            startActivity(Intent(this, MainActivity::class.java))
        }
        breakStopWatch.countDownText.observe(this) { countdown ->
            countdownText.text = countdown
        }
        return breakStopWatch
    }

    private fun createStudyStopWatch(seconds: Float, breakStopwatch: Stopwatch): Stopwatch {
        val studyStopWatch =
            Stopwatch(seconds) {
                val currentActivity: RelativeLayout = findViewById(R.id.pomo_details)
                currentActivity.setBackgroundColor(Color.parseColor("#2E8B57"))
                breakStopwatch.startTimer()
                currentStopWatch = breakStopwatch
            }
        studyStopWatch.countDownText.observe(this) { countdown ->
            countdownText.text = countdown
        }
        return studyStopWatch
    }

    private fun createPauseButton() {
        val pauseTimer = findViewById<ImageButton>(R.id.pause_button)
        var paused = false

        pauseTimer.setOnClickListener {
            if (paused)
            {
                currentStopWatch.startTimer()
                pauseTimer.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24)
            } else {
                currentStopWatch.stopTimer()
                pauseTimer.setImageResource(R.drawable.ic_baseline_play_circle_outline_24)
            }
            paused = !paused
        }
    }

    private fun createCancelButton()
    {
//        val cancelTimer = findViewById<Button>(R.id.cancel_button)
//        cancelTimer.setOnClickListener {
//            fun check(x : Stopwatch, y : String){
//                val dialogBuilder = AlertDialog.Builder(this)
//                dialogBuilder.setMessage("Do you want to cancel $y timer?")
//                    .setCancelable(false)
//                    .setPositiveButton("Yes") { _, _ ->
//                        x.endTimer()
//                    }
//                    .setNegativeButton("No") { _, _ ->
//                        x.startTimer()
//                    }
//                val alert = dialogBuilder.create()
//                alert.show()
//            }
//
//
//            if(red){
//                studyStopWatch.stopTimer()
//                check(studyStopWatch, "study")
//            }else{
//                breakStopWatch.stopTimer()
//                check(breakStopWatch, "break")
//
//
//            }
//        }
    }

    private fun insertDataToDatabase() {
        val title = message.title.toString()
        val description = message.description.toString()
        val studyTime = message.studyTime
        val breakTime = message.breakTime
        val startTime = startTime.toString()
        val endTime = Calendar.getInstance().time.toString()
        val pomodoroSessionItem = PomodoroSession(0, title, description, studyTime, breakTime, startTime, endTime)
        mPomodoroSessionViewModel.addPomodoroSession(pomodoroSessionItem)
    }
}