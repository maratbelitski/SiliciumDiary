package com.siliciumdiary.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.siliciumdiary.R
import com.siliciumdiary.domain.Tasks
import com.siliciumdiary.presentation.TaskDiffCallback
import com.siliciumdiary.presentation.holders.AllTaskHolder

/**
 * @author Belitski Marat
 * @date  25.12.2023
 * @project SiliciumDiary
 */
class TaskAdapterAllTasks : androidx.recyclerview.widget.ListAdapter<Tasks, AllTaskHolder>(TaskDiffCallback()){

    var myTaskClickListener: MyTaskClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllTaskHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_all, parent, false)
        return AllTaskHolder(view)
    }

    override fun onBindViewHolder(holder: AllTaskHolder, position: Int) {

        holder.date.text = getItem(position).dateTask
        holder.name.text = getItem(position).nameTask
        holder.description.text = getItem(position).descriptionTask

        holder.btnDell.setOnClickListener {
            myTaskClickListener?.myClickDelete(getItem(position))
        }
        holder.time.text = getItem(position).timeTask
        holder.btnAdd.setOnClickListener {
            myTaskClickListener?.myClickAdd(getItem(position))
        }
    }

    interface MyTaskClickListener {
        fun myClickAdd(tasks: Tasks)
        fun myClickDelete(tasks: Tasks)
    }
}
