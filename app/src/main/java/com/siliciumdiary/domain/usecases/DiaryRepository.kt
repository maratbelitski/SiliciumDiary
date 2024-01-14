package com.siliciumdiary.domain.usecases

import androidx.lifecycle.LiveData
import com.siliciumdiary.domain.Tasks
import io.reactivex.rxjava3.core.Completable

/**
 * @author Belitski Marat
 * @date  09.01.2024
 * @project SiliciumDiary
 */
interface DiaryRepository {
    fun getCurrentDateRep(): String
    fun getDefaultListTasksRep(): MutableList<Tasks>
    fun deleteTaskFromDbRep(taskDate: String, timeTask: String)
    fun getAllTasksRep(): LiveData<MutableList<Tasks>>
    fun getNewListTask(listFromDB: List<Tasks>, date:String):MutableList<Tasks>
     fun addTask(task: Tasks)
    fun checkTimeUC(timeTemplate: String, timeComplete: String):Boolean
    fun checkTextUC(name: String, description: String):Boolean
}