package com.example.pomo.util

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class Stopwatch constructor(timeLeftInMilliseconds: Long, finishedFn: () -> Unit) {
    private lateinit var countDownTimer: CountDownTimer
    private var timeLeftInMilliseconds: Long = timeLeftInMilliseconds
    private var finishedFn = finishedFn
    private var timerRunning: Boolean = false
    private val _countDownText = MutableLiveData<String>()
    var countDownText : LiveData<String> = _countDownText

    fun startTimer() {
        countDownTimer = object: CountDownTimer(timeLeftInMilliseconds, 1000) {
            override fun onTick(l: Long) {
                timeLeftInMilliseconds = l
                updateTimer()
            }
            override fun onFinish() {
                finishedFn()
            }
        }.start()

        timerRunning = true
    }

    fun stopTimer() {
        countDownTimer.cancel()
    }

    fun updateTimer() {
        val minutes: Int = (timeLeftInMilliseconds / 60000).toInt()
        val seconds: Int = ((timeLeftInMilliseconds % 60000) / 1000).toInt()

        var timeLeftText: String = "$minutes:"
        if (seconds < 10) {
            timeLeftText += "0"
        }
        timeLeftText += seconds
        _countDownText.value = timeLeftText
    }
}