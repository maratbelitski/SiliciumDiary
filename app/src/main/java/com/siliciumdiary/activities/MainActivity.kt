package com.siliciumdiary.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.siliciumdiary.data.TaskAdapter
import com.siliciumdiary.data.Tasks
import com.siliciumdiary.databinding.ActivityMainBinding
import com.siliciumdiary.viewmodels.MainViewModel


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

            calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
                val dateClick = "$dayOfMonth.${month + 1}.$year"
                currentDate.text = dateClick
                myViewModel.currentDateLD.value = dateClick

                myViewModel.getAllTasksLD(dateClick)
                    .observe(this@MainActivity) { tasksDB ->
                        myViewModel.check(tasksDB)
                        myViewModel.defaultTasksLD.observe(
                            this@MainActivity,
                            Observer { newTasks -> taskAdapter.listTasks = newTasks })
                    }
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
                    myViewModel.deleteTaskFromDB(tasks.dateTask, tasks.timeTask)

                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        myViewModel.currentDateLD.observe(this@MainActivity, Observer { currentDate ->
            binding.currentDate.text = currentDate

            myViewModel.getAllTasksLD(currentDate).observe(this@MainActivity, Observer { tasksDB ->
                myViewModel.check(tasksDB)
                myViewModel.defaultTasksLD.observe(
                    this@MainActivity,
                    Observer { newTasks -> taskAdapter.listTasks = newTasks })
            })
        })
    }
}