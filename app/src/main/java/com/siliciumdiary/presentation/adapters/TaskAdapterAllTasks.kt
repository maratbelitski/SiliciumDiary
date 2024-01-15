package com.siliciumdiary.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.siliciumdiary.R
import com.siliciumdiary.domain.Tasks

/**
 * @author Belitski Marat
 * @date  25.12.2023
 * @project SiliciumDiary
 */
class TaskAdapterAllTasks() : RecyclerView.Adapter<TaskAdapterAllTasks.TaskHolder>() {
    var listTasks = mutableListOf<Tasks>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var myTaskClickListener: MyTaskClickListener? = null

    class TaskHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date: TextView = itemView.findViewById(R.id.tvValueDate)
        val time: TextView = itemView.findViewById(R.id.tvValueTime)
        val name: TextView = itemView.findViewById(R.id.tvValueTaskName)
        val description: TextView = itemView.findViewById(R.id.tvValueDescription)
        val btnAdd: ImageView = itemView.findViewById(R.id.ivAddTask)
        val btnDell: ImageView = itemView.findViewById(R.id.ivDellTask)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_all, parent, false)
        return TaskHolder(view)
    }

    override fun getItemCount(): Int {
        return listTasks.size
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        holder.date.text = listTasks[position].dateTask
        holder.name.text = listTasks[position].nameTask
        holder.description.text = listTasks[position].descriptionTask

        holder.btnDell.setOnClickListener {
            myTaskClickListener?.myClickDelete(listTasks[position])
        }
        holder.time.text = listTasks[position].timeTask
        holder.btnAdd.setOnClickListener {
            myTaskClickListener?.myClickAdd(listTasks[position])
        }
    }

    interface MyTaskClickListener {
        fun myClickAdd(tasks: Tasks)
        fun myClickDelete(tasks: Tasks)
    }
}
