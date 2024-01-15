package com.siliciumdiary.presentation.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.siliciumdiary.databinding.ActivityAllTasksBinding
import com.siliciumdiary.domain.Tasks
import com.siliciumdiary.presentation.adapters.TaskAdapter
import com.siliciumdiary.presentation.adapters.TaskAdapterAllTasks
import com.siliciumdiary.presentation.viewmodels.AllTaskViewModel

class AllTasksActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAllTasksBinding
    private val myViewModel: AllTaskViewModel by viewModels()
    private lateinit var taskAdapter: TaskAdapterAllTasks
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllTasksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

        listeners()
    }

    fun launchIntent(context: Context): Intent {
        return Intent(context, AllTasksActivity::class.java)
    }

    private fun initViews() {
        taskAdapter = TaskAdapterAllTasks()
        binding.recycler.adapter = taskAdapter
    }

    private fun listeners() {
        binding.btnClose.setOnClickListener { finish() }

        taskAdapter.myTaskClickListener = object : TaskAdapter.MyTaskClickListener,
            TaskAdapterAllTasks.MyTaskClickListener {
            override fun myClickAdd(tasks: Tasks) {
                val intent = DetailTaskActivity().launchIntent(
                    this@AllTasksActivity,
                    tasks.dateTask,
                    tasks.numberTask,
                    tasks.timeTask,
                    tasks.nameTask,
                    tasks.descriptionTask
                )
                startActivity(intent)
            }

            override fun myClickDelete(tasks: Tasks) {
                myViewModel.deleteTaskFromDB(tasks.dateTask, tasks.timeTask)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        myViewModel.getAllTasksLD().observe(this, Observer {
            taskAdapter.listTasks = it
        })
    }
}