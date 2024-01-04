package com.siliciumdiary.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.siliciumdiary.data.Tasks

/**
 * @author Belitski Marat
 * @date  27.12.2023
 * @project SiliciumDiary
 */
@Database(entities = [Tasks::class], version = 1, exportSchema = false)
abstract class TaskDataBase: RoomDatabase() {

   abstract fun getDao(): TaskDao

    companion object{
        fun getDB(context: Context): TaskDataBase {
            return Room.databaseBuilder(context, TaskDataBase::class.java,"TaskDataBase").build()
        }
    }
}