package com.example.pomo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.pomo.data.PomodoroSessionDatabase
import com.example.pomo.repository.PomodoroSessionRepository
import com.example.pomo.model.PomodoroSession
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PomodoroSessionViewModel(application: Application): AndroidViewModel(application) {
    val readAllData: LiveData<List<PomodoroSession>>
    private val repository: PomodoroSessionRepository

    fun addPomodoroSession(pomodoroSession: PomodoroSession) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addPomodoroSession(pomodoroSession)
        }
    }

    fun deletePomodoroSession(pomodoroSession: PomodoroSession) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deletePomodoroSession(pomodoroSession)
        }
    }

    init {
        val pomodoroSessionDao = PomodoroSessionDatabase.getDatabase(application).pomodoroSessionDao()
        repository = PomodoroSessionRepository(pomodoroSessionDao)
        readAllData = repository.readAllData

    }
}