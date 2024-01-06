package com.siliciumdiary.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.siliciumdiary.data.Tasks
import com.siliciumdiary.database.TaskDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author Belitski Marat
 * @date  26.12.2023
 * @project SiliciumDiary
 */
class DetailTaskViewModel(application: Application) : AndroidViewModel(application) {
    val closeDisplay: MutableLiveData<Boolean> = MutableLiveData()
    private val dao = TaskDataBase.getDB(application).getDao()

    fun insertTaskToDB(tasks: Tasks) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.insertTask(tasks)
            closeDisplay.postValue(true)
        }
    }

    fun deleteTaskFromDB(taskDate: String, timeTask: String) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.removeTask(taskDate, timeTask)
            closeDisplay.postValue(true)
        }
    }

    suspend fun checkTime(timeTemplate: String, timeComplete: String): Boolean {
        var result = false
         result = withContext(Dispatchers.IO){
            val hour = timeTemplate.substring(0, 2)
            val listTime = mutableListOf<String>()

            for (minutes in 0..59) {
                if (minutes in 0..<10) {
                    listTime.add("$hour.0$minutes")
                } else {
                    listTime.add("$hour.$minutes")
                }
            }
            for (time in listTime) {
                if (timeComplete == time) {
                    result = true
                    break
                }
            }
             return@withContext result
        }
        return result
    }

    suspend fun checkInputText(name: String, description: String): Boolean {
        var result = false
       result = withContext(Dispatchers.IO) {
            if (name.isNotEmpty() && description.isNotEmpty()) {
                result = true
            }
            return@withContext result
        }
       return result
    }
}