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

    var closeDisplayLD: MutableLiveData<Boolean> = MutableLiveData(false)

    fun insertTaskToDBLD(task: Tasks) {
        viewModelScope.launch(Dispatchers.IO) {
            insertTask.addTaskUC(task)
            closeDisplayLD.postValue(true)
        }
    }

    fun deleteTaskFromDB(taskDate: String, timeTask: String) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteTask.deleteTaskFromDbUC(taskDate, timeTask)
            closeDisplayLD.postValue(true)
        }
    }

    fun checkTimeLD(
        timeTemplate: String,
        timeComplete: String
    ): Boolean {
        return checkTime.checkTimeUC(timeTemplate, timeComplete)
    }

    fun checkTextLD(name: String, description: String): Boolean {
        return checkText.checkTextUC(name, description)
    }

    fun toastTime(templateTime: Editable) {
        viewModelScope.launch(Dispatchers.Main) {
            Toast.makeText(
                getApplication(),
                "Введите время в формате ${templateTime.substring(0, 2)}.mm", Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun toastText() {
        viewModelScope.launch(Dispatchers.Main) {
            Toast.makeText(
                getApplication(),
                getString(getApplication(), R.string.error_checkText),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}