package com.siliciumdiary.presentation.holders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.siliciumdiary.R

/**
 * @author Belitski Marat
 * @date  18.01.2024
 * @project SiliciumDiary
 */
class AllTaskHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val date: TextView = itemView.findViewById(R.id.tvValueDate)
    val time: TextView = itemView.findViewById(R.id.tvValueTime)
    val name: TextView = itemView.findViewById(R.id.tvValueTaskName)
    val description: TextView = itemView.findViewById(R.id.tvValueDescription)
    val btnAdd: ImageView = itemView.findViewById(R.id.ivAddTask)
    val btnDell: ImageView = itemView.findViewById(R.id.ivDellTask)
}