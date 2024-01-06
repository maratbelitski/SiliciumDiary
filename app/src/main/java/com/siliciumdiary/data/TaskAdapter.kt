package com.siliciumdiary.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.siliciumdiary.R

/**
 * @author Belitski Marat
 * @date  25.12.2023
 * @project SiliciumDiary
 */
class TaskAdapter() : RecyclerView.Adapter<TaskAdapter.TaskHolder>() {

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
        val background: ConstraintLayout = itemView.findViewById(R.id.itemBackground)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)
        return TaskHolder(view)
    }

    override fun getItemCount(): Int {
        return listTasks.size
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        if (listTasks[position].nameTask.isNotEmpty()) {
            holder.background.setBackgroundResource(R.drawable.style_item_green)
            holder.btnDell.visibility = View.VISIBLE
        } else {
            holder.background.setBackgroundResource(R.drawable.style_item_grey)
            holder.btnDell.visibility = View.INVISIBLE
        }

        holder.time.text = listTasks[position].timeTask
        holder.name.text = listTasks[position].nameTask
        holder.description.text = listTasks[position].descriptionTask

        holder.btnAdd.setOnClickListener {
            myTaskClickListener?.myClickAdd(listTasks[position])
        }

        holder.btnDell.setOnClickListener {
            myTaskClickListener?.myClickDelete(listTasks[position])
        }
    }

    interface MyTaskClickListener {
        fun myClickAdd(tasks: Tasks)
        fun myClickDelete(tasks: Tasks)
    }
}
