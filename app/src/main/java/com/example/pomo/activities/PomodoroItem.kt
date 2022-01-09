package com.example.pomo.activities

import java.io.Serializable
import java.util.*

data class PomodoroItem(
    var imageResource: Int,
    var title: String?,
    var description: String?,
    var studyTime: Float?,
    var breakTime: Float?,
    var dateStart: Date,
    var dateEnd: Date
) : Serializable