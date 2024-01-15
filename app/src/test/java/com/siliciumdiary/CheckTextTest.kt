package com.siliciumdiary

import android.app.Application
import com.siliciumdiary.data.DiaryRepositoryImpl
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author Belitski Marat
 * @date  15.01.2024
 * @project SiliciumDiary
 */
class CheckTextTest {
    @Test
    fun checkTextUC_twoParamIsEmpty_isFalse() {
        assertEquals(DiaryRepositoryImpl(Application()).checkTextUC("",""), false)
    }
    @Test
    fun checkTextUC_nameParamIsEmpty_isFalse() {
        assertEquals(DiaryRepositoryImpl(Application()).checkTextUC("","some word"), false)
    }
    @Test
    fun checkTextUC_descrParamIsEmpty_isFalse() {
        assertEquals(DiaryRepositoryImpl(Application()).checkTextUC("some word",""), false)
    }
}