package com.siliciumdiary.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.siliciumdiary.database.TaskDataBase
import com.siliciumdiary.data.Tasks
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.Calendar

/**
 * @author Belitski Marat
 * @date  25.12.2023
 * @project SiliciumDiary
 */
class MainViewModel(application: Application) : AndroidViewModel(application) {
    var currentDateLD: MutableLiveData<String> = MutableLiveData()
    val closeDisplay: MutableLiveData<Boolean> = MutableLiveData(false)
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val dao = TaskDataBase.getDB(application).getDao()

    init {
        getCurrentDate()
    }

    fun getAllTasksLD(dateTask: String): LiveData<List<Tasks>> {
        return dao.getAllTasks(dateTask)
    }

    fun deleteTaskFromDB(taskDate: String, timeTask: String) {
        val disposable = dao.removeTask(taskDate, timeTask)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
        closeDisplay.value = true
        compositeDisposable.add(disposable)
    }

    private fun getCurrentDate() {
        val calendar = Calendar.getInstance()
        val month = calendar[Calendar.MONTH]
        currentDateLD.value =
            "${calendar[Calendar.DAY_OF_MONTH]}.${month + 1}.${calendar[Calendar.YEAR]}"
    }

    fun getDefaultTask(): MutableList<Tasks> {
        val defaultTask = mutableListOf<Tasks>()
        var time = ""
        for ((index, task) in (0..23).withIndex()) {
            if (task in 0..<10) {
                time = "0$task.00"
            } else {
                time = "$task.00"
            }
            defaultTask.add(Tasks(numberTask = index, timeTask = time))
        }
        return defaultTask
    }

    fun getNewListTask(defaultListTasks: List<Tasks>): MutableList<Tasks> {
        val newListTasks = getDefaultTask()

        for (task in defaultListTasks) {
            val number = task.numberTask
            newListTasks[number] = task
        }
        return newListTasks
    }
}