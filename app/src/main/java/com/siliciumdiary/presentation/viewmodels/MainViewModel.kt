package com.siliciumdiary.presentation.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.siliciumdiary.data.DiaryRepositoryImpl
import com.siliciumdiary.domain.Tasks
import com.siliciumdiary.domain.usecases.DeleteTaskFromDB
import com.siliciumdiary.domain.usecases.GetAllTasks
import com.siliciumdiary.domain.usecases.GetCurrentDate
import com.siliciumdiary.domain.usecases.GetDefaultListTasks
import com.siliciumdiary.domain.usecases.GetNewListTask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author Belitski Marat
 * @date  25.12.2023
 * @project SiliciumDiary
 */
class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = DiaryRepositoryImpl(application)
    private val currentDate = GetCurrentDate(repository)
    private val defaultListTask = GetDefaultListTasks(repository)
    private val newListTask = GetNewListTask(repository)
    private val deleteTask = DeleteTaskFromDB(repository)
    private val allTask = GetAllTasks(repository)

    var currentDateLD: MutableLiveData<String> = MutableLiveData()
    var defaultTasksLD: MutableLiveData<MutableList<Tasks>> = MutableLiveData()
    var upgradeListTaskLD: MutableLiveData<MutableList<Tasks>> = MutableLiveData()

    init {
        getCurrentDateLD()
        getDefaultListTasksLD()
    }
    private fun getCurrentDateLD() {                                                 //В UseCase
        viewModelScope.launch (Dispatchers.IO){
            currentDateLD.postValue(currentDate.getCurrentDateUC())
        }
    }
    fun getAllTasksLD(dateTask: String): LiveData<MutableList<Tasks>> {             //В UseCase
        return allTask.getAllTasksUC(dateTask)
    }

    fun deleteTaskFromDB(taskDate: String, timeTask: String) {                      //В UseCase
        viewModelScope.launch (Dispatchers.IO){
           deleteTask.deleteTaskFromDbUC(taskDate,timeTask)
        }
    }

    fun getNewListTask(listFromDB: List<Tasks>) {                                    //В UseCase
        viewModelScope.launch(Dispatchers.IO) {
            upgradeListTaskLD.postValue(newListTask.getNewListTask(listFromDB))
        }
    }

    private fun getDefaultListTasksLD() {                                              //В UseCase
        viewModelScope.launch(Dispatchers.IO){
            defaultTasksLD.postValue(defaultListTask.getDefaultListTasksUC())
        }
    }
}