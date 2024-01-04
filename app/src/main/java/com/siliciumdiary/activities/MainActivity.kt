package com.siliciumdiary.activities

import android.os.Bundle
import android.widget.CalendarView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.siliciumdiary.data.TaskAdapter
import com.siliciumdiary.data.Tasks
import com.siliciumdiary.databinding.ActivityMainBinding
import com.siliciumdiary.viewmodels.MainViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val myViewModel: MainViewModel by viewModels()
    private var taskAdapter = TaskAdapter()
    private var dateForDB: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding) {
            recycler.adapter = taskAdapter

            myViewModel.currentDateLD.observe(this@MainActivity, Observer {
                binding.currentDate.text = it
                dateForDB = it
            })


            calendar.setOnDateChangeListener(CalendarView.OnDateChangeListener
            { view, year, month, dayOfMonth ->
                val dateClick = "$dayOfMonth.${month + 1}.$year"
                currentDate.text = dateClick
                myViewModel.currentDateLD.value = dateClick

                myViewModel.getAllTasksLD(dateClick).observe(this@MainActivity, Observer {
                    if (it.isEmpty()) {
                        taskAdapter.listTasks = myViewModel.getDefaultTask()
                    } else {
                        val tass = myViewModel.getDefaultTask()
                        for (task in it) {
                            val index = task.numberTask
                            tass[index] = task
                        }
                        taskAdapter.listTasks = tass
                    }
                })
            })

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
        myViewModel.getAllTasksLD(dateForDB).observe(this@MainActivity, Observer { it ->
            if (it.isEmpty()) {
                taskAdapter.listTasks = myViewModel.getDefaultTask()
            } else {
                taskAdapter.listTasks = myViewModel.getNewListTask(it)
            }
        })
    }
}