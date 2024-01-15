package com.siliciumdiary

import android.app.Application
import com.siliciumdiary.data.DiaryRepositoryImpl
import org.junit.Assert
import org.junit.Test

/**
 * @author Belitski Marat
 * @date  15.01.2024
 * @project SiliciumDiary
 */
class DefaultListTasksTest {
    @Test
    fun checkDefaultListTasks_isTrue() {
        val defaultListTasks = DiaryRepositoryImpl(Application()).getDefaultListTasksRep()

        Assert.assertTrue(defaultListTasks[0].timeTask == "00.00")
        Assert.assertTrue(defaultListTasks[9].timeTask == "09.00")
        Assert.assertTrue(defaultListTasks[23].timeTask == "23.00")
    }

    @Test
    fun defaultListTasks_size_isTrue() {
        val defaultListTasks = DiaryRepositoryImpl(Application()).getDefaultListTasksRep()
        val size = defaultListTasks.size

        Assert.assertTrue(size == 24)
    }
}