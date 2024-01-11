package com.siliciumdiary.presentation.viewmodels

import android.app.Application
import android.text.Editable
import android.widget.Toast
import androidx.core.content.ContextCompat.getString
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.siliciumdiary.R
import com.siliciumdiary.data.DiaryRepositoryImpl
import com.siliciumdiary.domain.Tasks
import com.siliciumdiary.domain.usecases.AddTaskInDB
import com.siliciumdiary.domain.usecases.CheckText
import com.siliciumdiary.domain.usecases.CheckTime
import com.siliciumdiary.domain.usecases.DeleteTaskFromDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author Belitski Marat
 * @date  26.12.2023
 * @project SiliciumDiary
 */
class DetailTaskViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = DiaryRepositoryImpl(application)

    private val deleteTask = DeleteTaskFromDB(repository)
    private val insertTask = AddTaskInDB(repository)
    private val checkTime = CheckTime(repository)
    private val checkText = CheckText(repository)

    val closeDisplay: MutableLiveData<Boolean> = MutableLiveData()


    fun insertTaskToDBLD(task: Tasks) {                                               //В UseCase
        viewModelScope.launch(Dispatchers.IO) {
            insertTask.addTaskUC(task)
            closeDisplay.postValue(true)
        }
    }

    fun deleteTaskFromDB(taskDate: String, timeTask: String) {                       //В UseCase
        viewModelScope.launch(Dispatchers.IO) {
            deleteTask.deleteTaskFromDbUC(taskDate,timeTask)
            closeDisplay.postValue(true)
        }
    }

    fun checkTimeLD(timeTemplate: String, timeComplete: String): Boolean {                  //В UseCase
     return  checkTime.checkTimeUC(timeTemplate,timeComplete)
    }

   fun checkTextLD(name: String, description: String): Boolean {                        //В UseCase
     return checkText.checkTextUC(name, description)
    }

    fun toastTime(templateTime: Editable) {
        Toast.makeText(
            getApplication(),
            "Введите время в формате ${templateTime.substring(0, 2)}.mm", Toast.LENGTH_SHORT
        ).show()
    }

    fun toastText() {
        Toast.makeText(
            getApplication(),getString(getApplication(),R.string.error_checkText), Toast.LENGTH_SHORT)
            .show()
    }
}