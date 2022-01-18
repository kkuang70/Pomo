package com.example.pomo.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.security.SecureRandom

@Entity(tableName = "pomodoro_session_table")
data class PomodoroSession (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val description: String,
    val studyMinutes: Float,
    val breakMinutes: Float,
    val startDateTime: String,
    val endDateTime: String
) : Serializable