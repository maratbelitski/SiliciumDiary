package com.siliciumdiary

import com.siliciumdiary.domain.Tasks
import org.junit.Assert
import org.junit.Test

/**
 * @author Belitski Marat
 * @date  15.01.2024
 * @project SiliciumDiary
 */
class TasksTest {
    @Test
    fun task_IsValid_isTrue() {
        val template = Tasks(
            "12.1.2024",
            0,
            "12.00",
            "name",
            "doing somethings")

        val exampleTask = Tasks(
            "12.1.2024",
            timeTask = "12.00",
            nameTask = "name",
            descriptionTask = "doing somethings"
        )
        Assert.assertEquals(template, exampleTask)
    }
}