package com.siliciumdiary.domain.usecases

/**
 * @author Belitski Marat
 * @date  11.01.2024
 * @project SiliciumDiary
 */
class CheckTime(private val repository: DiaryRepository) {
    fun checkTimeUC(timeTemplate: String, timeComplete: String):Boolean{
       return repository.checkTimeUC(timeTemplate,timeComplete)
    }
}