package com.siliciumdiary.presentation.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.siliciumdiary.databinding.ActivityMainBinding
import com.siliciumdiary.domain.Tasks
import com.siliciumdiary.presentation.adapters.TaskAdapter
import com.siliciumdiary.presentation.viewmodels.MainViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val myViewModel: MainViewModel by viewModels()
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViews()

        listeners()

        doSwipe()

        myViewModel.upgradeListTaskLD.observe(this@MainActivity) { upgrade ->
            taskAdapter.listTasks = upgrade
        }
    }

    override fun onResume() {
        super.onResume()
        observeChanges()
    }

    private fun initViews() {
        taskAdapter = TaskAdapter()
        binding.recycler.adapter = taskAdapter
    }

    private fun listeners() {
        with(binding) {
            btnAllTasks.setOnClickListener {
                val intent = AllTasksActivity().launchIntent(this@MainActivity)
                startActivity(intent)
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
                    myViewModel.deleteTaskFromDB(tasks.dateTask, tasks.timeTask)
                }
            }
        }
    }

    private fun observeChanges() {
        myViewModel.getAllTasksLD().observe(this@MainActivity) {
            myViewModel.currentDateLD.observe(this@MainActivity) { date ->
                binding.currentDate.text = date
                myViewModel.getNewListTask(it, date)
            }
        }
    }
    private fun doSwipe() {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = taskAdapter.listTasks[viewHolder.adapterPosition]
                myViewModel.deleteTaskFromDB(item.dateTask,item.timeTask)
            }
        }
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(binding.recycler)
    }
}