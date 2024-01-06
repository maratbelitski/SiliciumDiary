package com.siliciumdiary.data

import androidx.room.Entity

/**
 * @author Belitski Marat
 * @date  25.12.2023
 * @project SiliciumDiary
 */

@Entity("tasks", primaryKeys = ["dateTask","numberTask"])
data class Tasks(

    val dateTask: String = "",
    val numberTask: Int = 0,
    var timeTask: String = "",
    val nameTask: String = "",
    val descriptionTask: String = ""
)