package com.siliciumdiary.domain.usecases

import com.siliciumdiary.domain.Tasks

/**
 * @author Belitski Marat
 * @date  10.01.2024
 * @project SiliciumDiary
 */
class GetNewListTask(private val diaryRepository: DiaryRepository) {
    fun getNewListTask(listFromDB: List<Tasks>, date:String):MutableList<Tasks>{
        return diaryRepository.getNewListTask(listFromDB,date)
    }
}