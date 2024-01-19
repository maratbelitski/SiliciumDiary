package com.siliciumdiary.presentation

import androidx.recyclerview.widget.DiffUtil
import com.siliciumdiary.domain.Tasks

/**
 * @author Belitski Marat
 * @date  18.01.2024
 * @project SiliciumDiary
 */
class TaskDiffCallback : DiffUtil.ItemCallback<Tasks>() {
    override fun areItemsTheSame(oldItem: Tasks, newItem: Tasks): Boolean {
        return oldItem.numberTask == newItem.numberTask
    }
    override fun areContentsTheSame(oldItem: Tasks, newItem: Tasks): Boolean {
        return oldItem == newItem
    }
}