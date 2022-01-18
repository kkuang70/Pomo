package com.example.pomo.activities

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
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
    private var timeLeftInMilliseconds: Float = 0.0F
    private var breakTimeLeftInMilliseconds: Float = 0.0F

    private lateinit var mPomodoroSessionViewModel: PomodoroSessionViewModel
    private lateinit var message: PomodoroDataItem
    private val startTime = Calendar.getInstance().time
    private var red = true



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
                currentActivity.setBackgroundColor(Color.parseColor("#2E8B57"))
                breakStopWatch.startTimer()
                red=false
            }
        studyStopWatch.startTimer()
        studyStopWatch.countDownText.observe(this, Observer<String> { countdown ->
            countdownText.text = countdown
        })


        //CANCEL TIMER BUTTON START--------------------------------------------------------------------------------------
        val cancelTimer = findViewById<Button>(R.id.cancel_button)
        cancelTimer.setOnClickListener {
            fun check(x : Stopwatch, y : String){
                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setMessage("Do you want to cancel $y timer?")
                    .setCancelable(false)
                    .setPositiveButton("Yes",DialogInterface.OnClickListener{
                            dialog, id -> x.endTimer()
                    })
                    .setNegativeButton("No", DialogInterface.OnClickListener{
                            dialog, id -> x.startTimer()
                    })
                val alert = dialogBuilder.create()
                alert.show()
            }


            if(red){
                studyStopWatch.stopTimer()
                check(studyStopWatch, "study")

                //studyStopWatch.endTimer()
            }else{
                breakStopWatch.stopTimer()
                check(breakStopWatch, "break")


            }
        }

        //CANCEL TIMER BUTTON END ----------------------------------------------------------------------------------------

        //PAUSE TIMER BUTTON START----------------------------------------------------------------------------------------

        val pauseTimer = findViewById<Button>(R.id.pause_button)

        pauseTimer.setOnClickListener{
            if(pauseTimer.text == "PAUSE"){ //IF THE BUTTON says PAUSE
                if(red){ //red background PAUSE TIMER
                    studyStopWatch.stopTimer()
                    pauseTimer.text="RESUME"

                }else{  // green background PAUSE TIMER
                    breakStopWatch.stopTimer()
                    pauseTimer.text="RESUME"

                }
            } else{
                if(red){
                    studyStopWatch.startTimer()
                    pauseTimer.text="PAUSE"
                }else{
                    breakStopWatch.startTimer()
                    pauseTimer.text="PAUSE"
                }
            }


        }

        //PAUSE TIMER BUTTON END------------------------------------------------------------------------------------------------
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