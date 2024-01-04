package com.siliciumdiary.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.siliciumdiary.data.Tasks
import io.reactivex.rxjava3.core.Completable

/**
 * @author Belitski Marat
 * @date  27.12.2023
 * @project SiliciumDiary
 */

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks WHERE dateTask = :dateTask")
    fun getAllTasks(dateTask:String):LiveData<List<Tasks>>

    @Query("DELETE FROM tasks WHERE dateTask = :dateTask AND timeTask = :timeTask")
    fun removeTask(dateTask: String, timeTask:String):Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(tasks: Tasks):Completable
}