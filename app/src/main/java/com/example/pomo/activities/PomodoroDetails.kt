package com.example.pomo.activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pomo.R
import com.example.pomo.model.PomodoroSession
import com.example.pomo.viewmodel.PomodoroSessionViewModel
import com.example.pomo.util.Stopwatch
import java.util.*

class PomodoroDetails : AppCompatActivity() {
    private lateinit var countdownText: TextView
    private var timeLeftInMilliseconds: Float = 0.0F
    private var breakTimeLeftInMilliseconds: Float = 0.0F

    private lateinit var mPomodoroSessionViewModel: PomodoroSessionViewModel
    private lateinit var message: PomodoroDataItem
    private val startTime = Calendar.getInstance().time


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pomodoro_details)

        message = intent.getSerializableExtra("Message") as PomodoroDataItem
        mPomodoroSessionViewModel = ViewModelProvider(this).get(PomodoroSessionViewModel::class.java)

        if (message != null) {
            timeLeftInMilliseconds = message.studyTime.toFloat() * 60000
            breakTimeLeftInMilliseconds = message.breakTime.toFloat() * 60000
        }

        countdownText = findViewById(R.id.countdown_text)

        val breakStopWatch: Stopwatch = Stopwatch(breakTimeLeftInMilliseconds) {
            insertDataToDatabase()
            startActivity(Intent(this, MainActivity::class.java))
        }
        breakStopWatch.countDownText.observe(this, Observer<String> { countdown ->
            countdownText.text = countdown
        })

        val studyStopWatch: Stopwatch =
            Stopwatch(timeLeftInMilliseconds) {
                val currentActivity: RelativeLayout = findViewById(R.id.pomo_details)
                currentActivity.setBackgroundColor(Color.parseColor("#00ff00"))
                breakStopWatch.startTimer() 
            }
        studyStopWatch.startTimer()
        studyStopWatch.countDownText.observe(this, Observer<String> { countdown ->
            countdownText.text = countdown
        })
    }

    private fun insertDataToDatabase() {
        val title = message.title.toString()
        val description = message.description.toString()
        val studyTime = message.studyTime
        val breakTime = message.breakTime
        val startTime = startTime.toString()
        val endTime = Calendar.getInstance().time.toString()

        val pomodoroSessionItem = PomodoroSession(0, title.toString(), description, studyTime, breakTime, startTime, endTime)
        mPomodoroSessionViewModel.addPomodoroSession(pomodoroSessionItem)
    }
}