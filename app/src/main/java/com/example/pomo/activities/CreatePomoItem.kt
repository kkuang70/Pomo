package com.example.pomo.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.example.pomo.R
import java.io.Serializable

class CreatePomoItem : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_pomo_item)
    }

    fun goToTimer(view: View) {
        val titleText = findViewById<EditText>(R.id.title).text.toString()
        val descriptionText = findViewById<EditText>(R.id.description).text.toString()
        val studyMinutesText = findViewById<EditText>(R.id.study_minutes).text.toString().toFloat()
        val breakMinutesText = findViewById<EditText>(R.id.break_minutes).text.toString().toFloat()
        val pomodoroDataItem: PomodoroDataItem = PomodoroDataItem(
            titleText,
            descriptionText,
            studyMinutesText,
            breakMinutesText)
        val intent = Intent(this, PomodoroDetails::class.java).apply {
            putExtra("Message", pomodoroDataItem)
        }
        startActivity(intent)
    }
}

data class PomodoroDataItem(
    val title: String?,
    val description: String?,
    val studyTime: Float,
    val breakTime: Float
) : Serializable