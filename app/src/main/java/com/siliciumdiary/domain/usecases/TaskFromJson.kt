package com.siliciumdiary.domain.usecases

import com.siliciumdiary.domain.Tasks

/**
 * @author Belitski Marat
 * @date  16.01.2024
 * @project SiliciumDiary
 */
class TaskFromJson(private val repository: DiaryRepository) {
    fun convertFromJsonUC(json: String):Tasks{
       return repository.convertFromJsonRep(json)
    }
}