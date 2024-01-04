package com.siliciumdiary.viewmodels

import android.app.Application
import android.content.Context
import android.text.Editable
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.siliciumdiary.data.Tasks
import com.siliciumdiary.database.TaskDataBase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * @author Belitski Marat
 * @date  26.12.2023
 * @project SiliciumDiary
 */
class DetailTaskViewModel(application: Application) : AndroidViewModel(application) {
    val closeDisplay:MutableLiveData<Boolean> = MutableLiveData(false)
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val dao = TaskDataBase.getDB(application).getDao()

    fun insertTaskToDB(tasks: Tasks) {
        val disposable = dao.insertTask(tasks)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
        closeDisplay.value = true
        compositeDisposable.add(disposable)
    }

    fun deleteTaskFromDB(dateTask:String, timeTask:String){
        val disposable = dao.removeTask(dateTask, timeTask)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
        closeDisplay.value = true
        compositeDisposable.add(disposable)
    }

    fun checkTime(timeTemplate: String, timeComplete: String): Boolean {
          val hour = timeTemplate.substring(0, 2)
          val listTime = mutableListOf<String>()

          for (minutes in 0..59) {
              if (minutes in 0..<10) {
                  listTime.add("$hour.0$minutes")
              } else {
                  listTime.add("$hour.$minutes")
              }
          }

          var result = false
          for (time in listTime) {
              if (timeComplete == time) {
                  result = true
                  break
              }
          }
          if (!result) {
              Toast.makeText(getApplication(), "Введите время в формате ${timeTemplate.substring(0, 2)}.mm", Toast.LENGTH_SHORT).show()
          }
          return result
      }

    fun checkInputText(context: Context, name:String, description:String):Boolean{
        var result = false
        if (name.isEmpty() || description.isEmpty()) {
            Toast.makeText(context, "Поля не могут быть пустыми!", Toast.LENGTH_SHORT).show()
        }else {
            result = true
        }
        return result
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}