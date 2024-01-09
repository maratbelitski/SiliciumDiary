package com.siliciumdiary.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.siliciumdiary.domain.Tasks

/**
 * @author Belitski Marat
 * @date  27.12.2023
 * @project SiliciumDiary
 */

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks WHERE dateTask = :dateTask")
    fun getAllTasks(dateTask:String):LiveData<MutableList<Tasks>>


    @Query("DELETE FROM tasks WHERE dateTask = :dateTask AND timeTask = :timeTask")
   fun removeTask(dateTask: String, timeTask:String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
   fun insertTask(tasks: Tasks)
}