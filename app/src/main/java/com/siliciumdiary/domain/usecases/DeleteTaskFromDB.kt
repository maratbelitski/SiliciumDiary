package com.siliciumdiary.domain.usecases

/**
 * @author Belitski Marat
 * @date  10.01.2024
 * @project SiliciumDiary
 */
class DeleteTaskFromDB(private val diaryRepository: DiaryRepository) {
    fun deleteTaskFromDbUC(taskDate: String, timeTask: String){
        diaryRepository.deleteTaskFromDbRep(taskDate,timeTask)
    }
}