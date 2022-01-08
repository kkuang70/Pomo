package com.example.pomo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pomo.model.PomodoroSession

@Database(entities = [PomodoroSession::class], version = 1, exportSchema = false)
abstract class PomodoroSessionDatabase: RoomDatabase() {

    abstract fun pomodoroSessionDao(): PomodoroSessionDao

    companion object{
        @Volatile
        private var INSTANCE: PomodoroSessionDatabase? = null

        fun getDatabase(context: Context): PomodoroSessionDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PomodoroSessionDatabase::class.java,
                    "pomodoro_session_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}