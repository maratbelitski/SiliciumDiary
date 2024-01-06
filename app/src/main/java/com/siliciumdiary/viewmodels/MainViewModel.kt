package com.siliciumdiary.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.siliciumdiary.data.Tasks
import com.siliciumdiary.database.TaskDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

/**
 * @author Belitski Marat
 * @date  25.12.2023
 * @project SiliciumDiary
 */
class MainViewModel(application: Application) : AndroidViewModel(application) {
    var currentDateLD: MutableLiveData<String> = MutableLiveData()
    var defaultTasksLD: MutableLiveData<MutableList<Tasks>> = MutableLiveData()
    private val closeDisplay: MutableLiveData<Boolean> = MutableLiveData(false)
    private val dao = TaskDataBase.getDB(application).getDao()

    init {
        getCurrentDate()
    }

    fun getAllTasksLD(dateTask: String): LiveData<List<Tasks>> {
        return dao.getAllTasks(dateTask)
    }

    fun deleteTaskFromDB(taskDate: String, timeTask: String) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.removeTask(taskDate, timeTask)
            closeDisplay.postValue(true)
        }
    }

    private fun getCurrentDate() {
        CoroutineScope(Dispatchers.IO).launch {
            val calendar = Calendar.getInstance()
            val month = calendar[Calendar.MONTH]
            currentDateLD.postValue("${calendar[Calendar.DAY_OF_MONTH]}.${month + 1}.${calendar[Calendar.YEAR]}")
        }
    }

    fun check(listFromDB: List<Tasks>) {
        CoroutineScope(Dispatchers.IO).launch {
            defaultTasksLD.postValue(getNewListTask(listFromDB))
        }
    }

    private fun getNewListTask(listFromDB: List<Tasks>): MutableList<Tasks> {
        val defaultTask = mutableListOf<Tasks>()

        CoroutineScope(Dispatchers.IO).launch {
            for ((index, task) in (0..23).withIndex()) {
                var time: String
                if (task in 0..<10) {
                    time = "0$task.00"
                } else {
                    time = "$task.00"
                }
                defaultTask.add(Tasks(numberTask = index, timeTask = time))
            }

            for (newTask in listFromDB) {
                val number = newTask.numberTask
                defaultTask[number] = newTask
            }
        }
        return defaultTask
    }
}