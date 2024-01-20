package com.siliciumdiary.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.siliciumdiary.R
import com.siliciumdiary.domain.Tasks
import com.siliciumdiary.presentation.TaskDiffCallback
import com.siliciumdiary.presentation.holders.TaskHolder

/**
 * @author Belitski Marat
 * @date  25.12.2023
 * @project SiliciumDiary
 */
class TaskAdapter :
    androidx.recyclerview.widget.ListAdapter<Tasks, TaskHolder>(TaskDiffCallback()) {

    companion object {
        private var LAYOUT_EMPTY = 0
        private const val TASK_EMPTY = 0
        private const val TASK_FILLED = 1
    }

    var myTaskClickListener: MyTaskClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        //Выбираем нужный макет
        var layout = R.layout.item_recycler_empty

        if (viewType == TASK_FILLED) {
            layout = R.layout.item_recycler
            LAYOUT_EMPTY = TASK_FILLED
        }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return TaskHolder(view)
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {

        if (LAYOUT_EMPTY == TASK_FILLED) {

            holder.name.text = getItem(position).nameTask
            holder.description.text = getItem(position).descriptionTask

            holder.btnDell.setOnClickListener {
                myTaskClickListener?.myClickDelete(getItem(position))
            }
        }
        holder.time.text = getItem(position).timeTask
        holder.btnAdd.setOnClickListener {
            myTaskClickListener?.myClickAdd(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).nameTask.isEmpty()) {
            TASK_EMPTY
        } else {
            TASK_FILLED
        }
    }

    interface MyTaskClickListener {
        fun myClickAdd(tasks: Tasks)
        fun myClickDelete(tasks: Tasks)
    }
}
