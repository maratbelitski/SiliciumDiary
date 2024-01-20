package com.siliciumdiary.domain.usecases

import com.siliciumdiary.domain.Tasks

/**
 * @author Belitski Marat
 * @date  11.01.2024
 * @project SiliciumDiary
 */
class AddTaskInDB(private val repository: DiaryRepository) {
    fun addTaskUC(task: Tasks) {
        return repository.addTask(task)
    }
}