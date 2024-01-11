package com.siliciumdiary.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet.Layout
import androidx.recyclerview.widget.RecyclerView
import com.siliciumdiary.R
import com.siliciumdiary.domain.Tasks

/**
 * @author Belitski Marat
 * @date  25.12.2023
 * @project SiliciumDiary
 */
class TaskAdapter() : RecyclerView.Adapter<TaskAdapter.TaskHolder>() {
    companion object {
        private var LAYOUT_EMPTY = 0
        private var TASK_EMPTY = 0
        private var TASK_FILLED = 1
    }


    var listTasks = mutableListOf<Tasks>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var myTaskClickListener: MyTaskClickListener? = null

    class TaskHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val time: TextView = itemView.findViewById(R.id.tvValueTime)
        val name: TextView = itemView.findViewById(R.id.tvValueTaskName)
        val description: TextView = itemView.findViewById(R.id.tvValueDescription)
        val btnAdd: ImageView = itemView.findViewById(R.id.ivAddTask)
        val btnDell: ImageView = itemView.findViewById(R.id.ivDellTask)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        var layout = R.layout.item_recycler_empty

        if (viewType == TASK_FILLED) {
            layout = R.layout.item_recycler
            LAYOUT_EMPTY = TASK_FILLED
        }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return TaskHolder(view)
    }

    override fun getItemCount(): Int {
        return listTasks.size
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        if (LAYOUT_EMPTY == TASK_FILLED) {

            holder.name.text = listTasks[position].nameTask
            holder.description.text = listTasks[position].descriptionTask

            holder.btnDell.setOnClickListener {
                myTaskClickListener?.myClickDelete(listTasks[position])
            }
        }
        holder.time.text = listTasks[position].timeTask
        holder.btnAdd.setOnClickListener {
            myTaskClickListener?.myClickAdd(listTasks[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (listTasks[position].nameTask.isEmpty()) {
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
