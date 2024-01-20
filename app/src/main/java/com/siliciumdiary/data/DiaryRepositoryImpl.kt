package com.siliciumdiary.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.google.gson.GsonBuilder
import com.siliciumdiary.data.database.TaskDataBase
import com.siliciumdiary.domain.Tasks
import com.siliciumdiary.domain.usecases.DiaryRepository
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

    override fun getAllTasksRep(): LiveData<MutableList<Tasks>> {
        return dao.getAllTasks()
    }

    override fun getNewListTask(listFromDB: List<Tasks>, date: String): MutableList<Tasks> {
        val defaultTask = getDefaultListTasksRep()


        for (newTask in listFromDB) {
            if (newTask.dateTask == date) {
                val number = newTask.numberTask
                defaultTask[number] = newTask
            }
        }
        return defaultTask
    }

    override fun addTask(task: Tasks) {
        return dao.insertTask(task)
    }

    override fun checkTimeUC(timeTemplate: String, timeComplete: String): Boolean {
        var result = false

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
        return result
    }

    override fun checkTextUC(name: String, description: String): Boolean {
        var result = false

        if (name.isNotEmpty() && description.isNotEmpty()) {
            result = true
        }
        return result
    }

    override fun convertToJsonRep(task: Tasks): String {
        val builder = GsonBuilder()
        val gson = builder.create()
        return gson.toJson(task)
    }

    override fun convertFromJsonRep(json: String): Tasks {
        val builder = GsonBuilder()
        val gson = builder.create()
        return gson.fromJson(json,Tasks::class.java)
    }
}