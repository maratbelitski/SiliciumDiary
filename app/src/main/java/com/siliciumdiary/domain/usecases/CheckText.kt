package com.siliciumdiary.domain.usecases

/**
 * @author Belitski Marat
 * @date  11.01.2024
 * @project SiliciumDiary
 */
class CheckText(private val repository: DiaryRepository) {
    fun checkTextUC(name: String, description: String):Boolean{
        return repository.checkTextUC(name,description)
    }
}