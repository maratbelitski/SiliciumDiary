package com.siliciumdiary.domain.usecases

import com.siliciumdiary.domain.Tasks

/**
 * @author Belitski Marat
 * @date  10.01.2024
 * @project SiliciumDiary
 */
class GetDefaultTask(private val diaryRepository: DiaryRepository) {
    fun getDefaultListTask():MutableList<Tasks>{
        return diaryRepository.getDefaultListTasksRep()
    }
}