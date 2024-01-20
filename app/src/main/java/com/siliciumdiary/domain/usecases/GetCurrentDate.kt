package com.siliciumdiary.domain.usecases

/**
 * @author Belitski Marat
 * @date  09.01.2024
 * @project SiliciumDiary
 */
class GetCurrentDate(private val diaryRepository: DiaryRepository) {
    fun getCurrentDateUC():String {
       return diaryRepository.getCurrentDateRep()
    }
}