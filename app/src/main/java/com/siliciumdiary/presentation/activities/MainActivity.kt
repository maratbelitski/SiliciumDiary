package com.siliciumdiary.presentation.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.siliciumdiary.R
import com.siliciumdiary.databinding.ActivityMainBinding
import com.siliciumdiary.domain.Tasks
import com.siliciumdiary.presentation.adapters.TaskAdapter
import com.siliciumdiary.presentation.viewmodels.MainViewModel
import java.util.Calendar


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val myViewModel: MainViewModel by viewModels()
    private var taskAdapter = TaskAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        with(binding) {

            recycler.adapter = taskAdapter

            myViewModel.currentDateLD.observe(this@MainActivity) { date ->
                binding.currentDate.text = date

                myViewModel.getAllTasksLD(date).observe(this@MainActivity) {
                    if (it.isNotEmpty()) {
                        myViewModel.getNewListTask(it)

                        myViewModel.upgradeListTaskLD.observe(this@MainActivity) { upgrade ->
                            taskAdapter.listTasks = upgrade

                        }
                    } else {
                        myViewModel.defaultTasksLD.observe(this@MainActivity) { default ->
                            taskAdapter.listTasks = default

                        }
                    }
                }
            }

            calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->

                val dateClick = "$dayOfMonth.${month + 1}.$year"
                currentDate.text = dateClick
                myViewModel.currentDateLD.value = dateClick

            }

            taskAdapter.myTaskClickListener = object : TaskAdapter.MyTaskClickListener {
                override fun myClickAdd(tasks: Tasks) {
                    val intent = DetailTaskActivity().launchIntent(
                        this@MainActivity,
                        currentDate.text.toString(),
                        tasks.numberTask,
                        tasks.timeTask,
                        tasks.nameTask,
                        tasks.descriptionTask
                    )
                    startActivity(intent)
                }

                override fun myClickDelete(tasks: Tasks) {
                   // myViewModel.currentDateLD.value = tasks.dateTask
                    myViewModel.deleteTaskFromDB(tasks.dateTask, tasks.timeTask)

                    myViewModel.currentDateLD.observe(this@MainActivity) { date ->

                        myViewModel.getAllTasksLD(date).observe(this@MainActivity) {
                            if (it.isNotEmpty()) {
                                myViewModel.getNewListTask(it)

                                myViewModel.upgradeListTaskLD.observe(this@MainActivity) { upgrade ->
                                    taskAdapter.listTasks = upgrade

                                }
                            } else {
                                myViewModel.defaultTasksLD.observe(this@MainActivity) { default ->
                                    taskAdapter.listTasks = default

                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }
}