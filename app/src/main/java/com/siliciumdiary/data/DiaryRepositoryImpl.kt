package com.siliciumdiary.data

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.siliciumdiary.data.database.TaskDataBase
import com.siliciumdiary.domain.Tasks
import com.siliciumdiary.domain.usecases.DiaryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

/**
 * @author Belitski Marat
 * @date  09.01.2024
 * @project SiliciumDiary
 */
class DiaryRepositoryImpl(application: Application) : DiaryRepository {
    private val dao = TaskDataBase.getDB(application).getDao()

    override fun getCurrentDateRep(): String {
        val calendar = Calendar.getInstance()
        val month = calendar[Calendar.MONTH]
        return "${calendar[Calendar.DAY_OF_MONTH]}.${month + 1}.${calendar[Calendar.YEAR]}"
    }

    override fun getDefaultListTasksRep(): MutableList<Tasks> {
        val defaultTask = mutableListOf<Tasks>()

        for ((index, task) in (0..23).withIndex()) {
            var time: String
            if (task in 0..<10) {
                time = "0$task.00"
            } else {
                time = "$task.00"
            }
            defaultTask.add(Tasks(numberTask = index, timeTask = time))
        }
        return defaultTask
    }

    override fun deleteTaskFromDbRep(taskDate: String, timeTask: String) {
        dao.removeTask(taskDate, timeTask)
    }

    override fun getAllTasksRep(dateTask: String): LiveData<MutableList<Tasks>> {
       return dao.getAllTasks(dateTask)
    }

    override fun getNewListTask(listFromDB: List<Tasks>): MutableList<Tasks> {
        val defaultTask = mutableListOf<Tasks>()

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
        return defaultTask
    }
}