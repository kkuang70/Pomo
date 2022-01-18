package com.example.pomo.activities

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.PrimaryKey
import com.example.pomo.R
import com.example.pomo.model.PomodoroSession
import kotlinx.android.synthetic.main.activity_create_pomo_item.*
import org.w3c.dom.Text
import java.io.Serializable
import java.util.*



class ItemActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_information)
        val item = intent.getSerializableExtra("POMOITEM") as PomodoroSession
       // Toast.makeText(this, item.toString(), Toast.LENGTH_SHORT).show()

        val titleView = findViewById<TextView>(R.id.pomo_title_itemActivity)
        val describeView = findViewById<TextView>(R.id.pomo_description_itemActivity)
        val studyTime = findViewById<TextView>(R.id.pomo_study_time_itemActivity)
        val breakTime = findViewById<TextView>(R.id.pomo_break_time_itemActivity)
        val startTime = findViewById<TextView>(R.id.start_time_itemActivity)
        val endTime = findViewById<TextView>(R.id.end_time_itemActivity)
        describeView.text = item.description
        titleView.text = item.title
        studyTime.text = item.studyMinutes.toString()
        breakTime.text = item.breakMinutes.toString()
        startTime.text = item.startDateTime
        endTime.text = item.endDateTime

    }
}
/*
data class PomodoroSession (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val description: String,
    val studyMinutes: Float,
    val breakMinutes: Float,
    val startDateTime: String,
    val endDateTime: String
) : Serializable*/