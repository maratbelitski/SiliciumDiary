package com.siliciumdiary.domain.usecases

import androidx.lifecycle.LiveData
import com.siliciumdiary.domain.Tasks

/**
 * @author Belitski Marat
 * @date  10.01.2024
 * @project SiliciumDiary
 */
class GetAllTasks(private val diaryRepository: DiaryRepository) {
  fun getAllTasksUC(): LiveData<MutableList<Tasks>> {
       return diaryRepository.getAllTasksRep()
    }
}