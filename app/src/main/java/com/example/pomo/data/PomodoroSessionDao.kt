package com.example.pomo.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.pomo.model.PomodoroSession

@Dao
interface PomodoroSessionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPomodoroSession(pomodoroSession: PomodoroSession)

    @Query("SELECT * FROM pomodoro_session_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<PomodoroSession>>

    @Delete
    suspend fun deletePomodoroSession(pomodoroSession: PomodoroSession)
}