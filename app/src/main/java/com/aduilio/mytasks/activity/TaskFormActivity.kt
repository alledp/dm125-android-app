package com.aduilio.mytasks.activity

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.aduilio.mytasks.databinding.ActivityTaskFormBinding
import com.aduilio.mytasks.entity.Task
import com.aduilio.mytasks.service.TaskService
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.util.Locale

class TaskFormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTaskFormBinding

    private val taskService: TaskService by viewModels()

    private var taskId: Long? = null

    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.e("lifecycle", "TaskForm onCreate")

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        intent.extras?.getString(Intent.EXTRA_TEXT)?.let { text ->
            binding.etTitle.setText(text)
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }

        taskId = intent.getLongExtra("task_id", -1)

        Toast.makeText(this, " ID da tarefa é" + taskId.toString(), Toast.LENGTH_SHORT).show()

        initComponents()
        setValues()
    }

    private fun initComponents() {
        binding.btSave.setOnClickListener {
            if(formValidate()){
                val task = Task(title = binding.etTitle.text.toString(),
                    description = binding.etDescription.text.toString(),
                    date = if (binding.etDate.text.isNullOrEmpty()) null else LocalDate.parse(binding.etDate.text.toString()),
                    time = if (binding.etTime.text.isNullOrEmpty()) null else LocalTime.parse(binding.etTime.text.toString()),
                    id = taskId)
                taskService.save(task).observe(this) { responseDto ->
                    if (responseDto.isError) {
                        Toast.makeText(this, "Server Error", Toast.LENGTH_SHORT).show()
                    } else {
                        finish()
                    }
                }
            }
        }
        binding.etDate.setOnClickListener{
            showDatePicker()
        }
        binding.etTime.setOnClickListener{
            showTimePicker()
        }

    }

    private fun formValidate(): Boolean {
        if(binding.etTitle.text.toString().isEmpty()){
            Toast.makeText(this, "O Titulo é Obrigatório", Toast.LENGTH_SHORT).show()
            return false
        }
        //Data e Hora não precisam ser validados pois já estão se utilizando do Date e Time picker.
        return true
    }

    @SuppressLint("SimpleDateFormat")
    private fun showTimePicker() {
        val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            binding.etTime.text = SimpleDateFormat("HH:mm").format(calendar.time)
        }
        TimePickerDialog(this, timeSetListener,
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true).show()
    }

    private fun showDatePicker() {
        val datePickerDialog = DatePickerDialog(this, {_, year: Int,monthOfYear: Int, dayOfMonth: Int ->
            val selectedDate = Calendar.getInstance()

            selectedDate.set(year, monthOfYear, dayOfMonth)

            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            binding.etDate.text = dateFormat.format(selectedDate.time)

            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    @Suppress("deprecation")
    private fun setValues() {
        (intent.extras?.getSerializable("task") as Task?)?.let { task ->
            taskId = task.id
            binding.etTitle.setText(task.title)
            binding.etDescription.setText(task.description)
            binding.etDate.text = task.date?.toString()
            binding.etTime.text = task.time?.toString()
            if (task.completed) {
                binding.btSave.visibility = View.INVISIBLE
            }
        }
    }

    override fun onStart() {
        super.onStart()

        Log.e("lifecycle", "TaskForm onStart")
    }

    override fun onResume() {
        super.onResume()

        Log.e("lifecycle", "TaskForm onResume")
    }

    override fun onStop() {
        super.onStop()

        Log.e("lifecycle", "TaskForm onStop")
    }

    override fun onPause() {
        super.onPause()

        Log.e("lifecycle", "TaskForm onPause")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.e("lifecycle", "TaskForm onDestroy")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }
}