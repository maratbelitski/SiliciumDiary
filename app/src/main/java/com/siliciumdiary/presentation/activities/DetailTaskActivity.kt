package com.siliciumdiary.presentation.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.siliciumdiary.domain.Tasks
import com.siliciumdiary.databinding.ActivityDetailTaskBinding
import com.siliciumdiary.presentation.viewmodels.DetailTaskViewModel

class DetailTaskActivity : AppCompatActivity() {

    companion object {
        private const val DATE = "date"
        private const val TIME = "time"
        private const val NUMBER = "number"
        private const val NAME = "name"
        private const val DESCRIPTION = "description"
    }

    private lateinit var binding: ActivityDetailTaskBinding
    private val myViewModel: DetailTaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailTaskBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        with(binding) {

            initViews()

            val templateTime =
                Editable.Factory.getInstance().newEditable(intent.getStringExtra(TIME))
            etTime.text = templateTime

            btnDelete.visibility = if (intent.getStringExtra(NAME)!!.isNotEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }

            btnSave.setOnClickListener {
                val number = intent.getIntExtra(NUMBER, 0)
                val date = tvDateTask.text.toString()
                val time = etTime.text.toString().trim()
                val name = etNameTask.text.toString().trim()
                val description = etDescription.text.toString().trim()

                val checkTime = myViewModel.checkTimeLD(templateTime.toString(), time)
                val checkText = myViewModel.checkTextLD(name, description)

                if (checkTime && checkText) {

                    myViewModel.insertTaskToDBLD(Tasks(date, number, time, name, description))
                    myViewModel.closeDisplay.observe(this@DetailTaskActivity, Observer {
                        if (it) finish()
                    })

                } else if (!checkTime) {
                    myViewModel.toastTime(templateTime)
                } else {
                    myViewModel.toastText()
                }
            }

            btnDelete.setOnClickListener {
                val date = tvDateTask.text.toString()
                val time = etTime.text.toString().trim()

                myViewModel.deleteTaskFromDB(date, time)

                myViewModel.closeDisplay.observe(this@DetailTaskActivity, Observer {
                    if (it) finish()
                })
            }
        }
    }

    fun launchIntent(
        context: Context, date: String, number: Int, time: String, name: String, description: String
    ): Intent {
        val intent = Intent(context, DetailTaskActivity::class.java)
        intent.putExtra(DATE, date)
        intent.putExtra(NUMBER, number)
        intent.putExtra(TIME, time)
        intent.putExtra(NAME, name)
        intent.putExtra(DESCRIPTION, description)
        return intent
    }

    private fun initViews() {
        with(binding) {
            tvDateTask.text = intent.getStringExtra(DATE)

            etNameTask.text =
                Editable.Factory.getInstance().newEditable(intent.getStringExtra(NAME))

            etDescription.text =
                Editable.Factory.getInstance().newEditable(intent.getStringExtra(DESCRIPTION))
        }
    }
}