package com.siliciumdiary.domain.usecases

import com.siliciumdiary.domain.Tasks

/**
 * @author Belitski Marat
 * @date  16.01.2024
 * @project SiliciumDiary
 */
class TaskToJson(private val repository: DiaryRepository) {
    fun convertToJsonUC(task: Tasks):String{
       return repository.convertToJsonRep(task)
    }
}