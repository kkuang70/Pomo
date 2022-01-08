package com.example.pomo.repository

import androidx.lifecycle.LiveData
import com.example.pomo.data.PomodoroSessionDao
import com.example.pomo.model.PomodoroSession

class PomodoroSessionRepository(private val pomodoroSessionDao: PomodoroSessionDao) {
    val readAllData: LiveData<List<PomodoroSession>> = pomodoroSessionDao.readAllData()

    suspend fun addPomodoroSession(pomodoroSession: PomodoroSession) {
        pomodoroSessionDao.addPomodoroSession(pomodoroSession)
    }

    suspend fun deletePomodoroSession(pomodoroSession: PomodoroSession) {
        pomodoroSessionDao.deletePomodoroSession(pomodoroSession)
    }
}