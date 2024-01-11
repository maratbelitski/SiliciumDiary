package com.siliciumdiary.domain.usecases

import androidx.lifecycle.LiveData
import com.siliciumdiary.domain.Tasks

/**
 * @author Belitski Marat
 * @date  09.01.2024
 * @project SiliciumDiary
 */
interface DiaryRepository {
    fun getCurrentDateRep(): String
    fun getDefaultListTasksRep(): MutableList<Tasks>
    fun deleteTaskFromDbRep(taskDate: String, timeTask: String)
    fun getAllTasksRep(dateTask: String): LiveData<MutableList<Tasks>>
    fun getNewListTask(listFromDB: List<Tasks>):MutableList<Tasks>
    fun addTask(task: Tasks)
    fun checkTimeUC(timeTemplate: String, timeComplete: String):Boolean
    fun checkTextUC(name: String, description: String):Boolean
}