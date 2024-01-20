package com.siliciumdiary.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.siliciumdiary.data.DiaryRepositoryImpl
import com.siliciumdiary.domain.Tasks
import com.siliciumdiary.domain.usecases.DeleteTaskFromDB
import com.siliciumdiary.domain.usecases.GetAllTasks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author Belitski Marat
 * @date  26.12.2023
 * @project SiliciumDiary
 */
class AllTaskViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = DiaryRepositoryImpl(application)

    private val allTask = GetAllTasks(repository)
    private val deleteTask = DeleteTaskFromDB(repository)

    fun getAllTasksLD(): LiveData<MutableList<Tasks>> {
        return allTask.getAllTasksUC()
    }

    fun deleteTaskFromDB(taskDate: String, timeTask: String) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteTask.deleteTaskFromDbUC(taskDate, timeTask)
        }
    }
}